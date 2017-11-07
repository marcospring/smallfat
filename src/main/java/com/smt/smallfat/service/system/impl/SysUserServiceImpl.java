package com.smt.smallfat.service.system.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.exception.BusinessException;
import com.smt.smallfat.constant.Constant;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.result.Result;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.SysUser;
import com.smt.smallfat.po.SysUserRole;
import com.smt.smallfat.service.system.SysUserService;
import com.smt.smallfat.utils.GetIpAddress;
import com.smt.smallfat.utils.PassSecret;
import com.smt.smallfat.utils.SystemSessionUtil;
import com.smt.smallfat.utils.TransUtil;
import com.smt.smallfat.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xindongwang on 17/3/13.
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public SysUserVo addSysUser(Map<String, Object> param) {
        //添加用户
        SysUser sysUser;
        sysUser = CommonBeanUtils.transMap2BasePO(param, SysUser.class);
        Param params = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(SysUser.FIELD_USERNAME, StringDefaultValue.StringValue(param.get(SysUser.FIELD_USERNAME))))
                .add(ParamBuilder.nv(SysUser.FIELD_DISABLED, 0));
        List<SysUser> users = factory.getCacheReadDataSession().queryListResult(SysUser.class, params);
        if (users != null && users.size() > 0) {
            throw new CommonException(ResultConstant.SysUserResult.SYSUSER_IS_EXISTS);
        }
        params.clean();
        params = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(SysUser.FIELD_MOBILE, StringDefaultValue.StringValue(param.get(SysUser.FIELD_MOBILE))))
                .add(ParamBuilder.nv(SysUser.FIELD_DISABLED, 0));
        List<SysUser> user = factory.getCacheReadDataSession().queryListResult(SysUser.class, params);
        if (user != null && user.size() > 0) {
            throw new CommonException(ResultConstant.SysUserResult.MOBILE_IS_NULL);
        }
        //密码加密
        sysUser.setPassword(PassSecret.passMd5(sysUser.getUsername(), sysUser.getPassword()));
        sysUser = factory.getCacheWriteDataSession().save(SysUser.class, sysUser);

        if (!StringDefaultValue.isEmpty(sysUser)) {
            //添加权限
            param.put(SysUserRole.FIELD_USER_ID, sysUser.getId());

            if (StringDefaultValue.isEmpty(param.get("roleIds"))) {
                int userId = StringDefaultValue.intValue(param.get(SysUserRole.FIELD_USER_ID));
                CustomSQL whereSql = SQLCreator.where().cloumn(SysUserRole.FIELD_USER_ID).operator(ESQLOperator.EQ).v(userId);
                factory.getCacheWriteDataSession().logicWhereDelete(SysUserRole.class, whereSql);
            } else {
                int userId = StringDefaultValue.intValue(param.get(SysUserRole.FIELD_USER_ID));
                String roleIds = param.get("roleIds").toString();
                params.clean();
                params.add(ParamBuilder.nv(SysUserRole.FIELD_USER_ID, userId));
                List<SysUserRole> existsList = factory.getCacheReadDataSession().queryListResult(SysUserRole
                        .class, params);
                List<String> inputIds = Arrays.asList(roleIds.split(","));
                dealWithSysUserRole(userId, existsList, inputIds);
            }
        } else {
            throw new CommonException(ResultConstant.SysUserResult.UPDATE_SYSUSER_IS_FAILD);
        }
        return CommonBeanUtils.getBeanBySameProperty(SysUserVo.class, sysUser);
    }

    private void dealWithSysUserRole(int userId, List<SysUserRole> existsList, List<String> inputIds) {
        if (existsList == null || existsList.size() == 0) {
            for (String roleId : inputIds) {
                SysUserRole sysUserRole = SysUserRole.getInstance(SysUserRole.class);
                sysUserRole.setRoleId(StringDefaultValue.intValue(roleId));
                sysUserRole.setUserId(userId);
                sysUserRole.setCreateTime(new Date());
                factory.getCacheWriteDataSession().save(SysUserRole.class, sysUserRole);
            }
        } else {
            //修改操作.
            Set<String> before = new HashSet<String>();
            Set<String> after = new HashSet<String>(inputIds);
            for (SysUserRole sysUserRole : existsList) {
                before.add(StringDefaultValue.StringValue(sysUserRole.getRoleId()));
            }
            Set<String> temp = new HashSet<String>(before);
            temp.retainAll(after);
            before.removeAll(temp);

            for (String roleId : before) {
                for (SysUserRole sysUserRole : existsList) {
                    if (roleId.equals(StringDefaultValue.StringValue(sysUserRole.getRoleId()))) {
                        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(sysUserRole.FIELD_ID, sysUserRole.getId()));
                        factory.getCacheWriteDataSession().logicDelete(SysUserRole.class, param);
                    }
                }
            }
            after.removeAll(temp);
            for (String roleId : after) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(StringDefaultValue.intValue(roleId));
                sysUserRole.setUserId(userId);
                sysUserRole.setCreateTime(new Date());
                factory.getCacheWriteDataSession().save(SysUserRole.class, sysUserRole);
            }
        }
    }

    @Override
    public SysUserVo getSysUserById(int id) {
        SysUser sysUser = factory.getCacheReadDataSession().querySingleResultById(SysUser.class, id);
        SysUserVo sysUserVo = CommonBeanUtils.getBeanBySameProperty(SysUserVo.class, sysUser);
        return sysUserVo;
    }


    @Override
    public void updateSysUserAndRole(Map<String, Object> param) {
        int userId = StringDefaultValue.intValue(param.get(SysUserRole.FIELD_USER_ID));
        SysUser sysUser;
        sysUser = factory.getCacheReadDataSession().querySingleResultById(SysUser.class, userId);
        if (sysUser == null) {
            throw new CommonException(ResultConstant.SysUserResult.SYSUSER_IS_NOT_FOUND);
        }
        try {
            param.remove(SysUser.FIELD_USERNAME);
        } catch (Exception e) {
        }
        sysUser = CommonBeanUtils.transMap2BasePO(param, sysUser);
        int count = factory.getCacheWriteDataSession().update(SysUser.class, sysUser);
        if (count < 1) {
            throw new CommonException(ResultConstant.SysUserResult.UPDATE_SYSUSER_IS_FAILD);
        }

        String roleIds = param.get("roleIds").toString();
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysUserRole.FIELD_USER_ID,
                userId));
        List<SysUserRole> existsList = factory.getCacheReadDataSession().queryListResult(SysUserRole.class, params);
        List<String> inputIds = Arrays.asList(roleIds.split(","));
        dealWithSysUserRole(userId, existsList, inputIds);
    }

    @Override
    public PaginationVo<SysUserVo> paginationSysUser(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get("pageNo"));
        int pageSize = StringDefaultValue.intValue(param.get("pageSize"));
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        Param params = ParamBuilder.getInstance().getParam();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringDefaultValue.isEmpty(value)) {
                continue;
            }
            if (key.equals("usernameLike"))
                params.add(ParamBuilder.nv("usernameLike", value));
            if (key.equals("realnameLike"))
                params.add(ParamBuilder.nv("realnameLike", value));
            if (key.equals("mobileLike"))
                params.add(ParamBuilder.nv("mobileLike", value));

            if (key.equals(SysUser.FIELD_REALNAME))
                params.add(ParamBuilder.nv(SysUser.FIELD_REALNAME, value));
            if (key.equals(SysUser.FIELD_USERNAME))
                params.add(ParamBuilder.nv(SysUser.FIELD_USERNAME, value));
            if (key.equals(SysUser.FIELD_MOBILE))
                params.add(ParamBuilder.nv(SysUser.FIELD_MOBILE, value));

            if (key.equals(SysUserRole.FIELD_ROLE_ID)) {
                params.add(ParamBuilder.nv(SysUserRole.FIELD_ROLE_ID, StringDefaultValue.intValue(value)));
            }
            if (key.equals(Constant.PAGE_NO)) {
                params.add(ParamBuilder.nv(Constant.PAGE_NO,
                        Integer.valueOf(String.valueOf(value)) < 1 ? 1
                                : Integer.valueOf(String.valueOf(value))));
            }
            if (key.equals(Constant.PAGE_SIZE))
                params.add(ParamBuilder.nv(Constant.PAGE_SIZE,
                        Integer.valueOf(String.valueOf(value))));

        }
        List<SysUserVo> sysUserVOs = factory.getCacheReadDataSession().queryVOList(SysUserVo.class, new Throwable
                (), params);
        for (SysUserVo vo : sysUserVOs) {
            StringBuilder stb = new StringBuilder();
            Map<String, Object> map = new HashMap<>();
            map.put("userId", vo.getId());

            List<UserRoleVO> sysUserRoleVOs = getRolesByUserId(map);

            for (UserRoleVO userRoleVO : sysUserRoleVOs) {
                stb.append(userRoleVO.getId() + "|" + userRoleVO.getRoleName() + ",");
            }
            if (stb.length() > 0) {
                vo.setRoles(stb.toString().substring(0, stb.toString().length() - 1));
            }
        }

        PaginationVo<SysUserVo> pageResult = new PaginationVo<SysUserVo>(sysUserVOs, pageNo, pageSize);
        Integer counts = countOrdUserVos(params);
        if (counts > 0) {
            pageResult.setRecordsTotal(counts);
        }

        return pageResult;
    }

    public List<UserRoleVO> getRolesByUserId(Map<String, Object> map) throws BusinessException {
        Param param = ParamBuilder.getInstance().getParam();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringDefaultValue.isEmpty(value)) {
                continue;
            }
            if (key.equals(SysUserRole.FIELD_USER_ID))
                param.add(ParamBuilder.nv(SysUserRole.FIELD_USER_ID, StringDefaultValue.intValue(value)));

        }
        List<UserRoleVO> sysUserRoleVOs = factory.getCacheReadDataSession().queryVOList(UserRoleVO.class, new Throwable(), param);
        return sysUserRoleVOs;
    }

    @Override
    public Integer countOrdUserVos(Param param) {
        return factory.getCacheReadDataSession().querySingleVO(Integer.class,
                new Throwable(), param);
    }

    @Override
    public List<SysUserRoleVo> getRolesByUserId(int userId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysUserRole.FIELD_USER_ID, userId));
        List<SysUserRole> sysUserRoleList = factory.getCacheReadDataSession().queryListResult(SysUserRole.class, param);
        List<SysUserRoleVo> userRoleVoList = TransUtil.transPoListToVoList(sysUserRoleList, SysUserRoleVo.class);
        return userRoleVoList;
    }

    @Override
    public void changePassword(Map<String, Object> map) {
        map.put(SysUserVo.FIELD_PASSWORD, PassSecret.passMd5(StringDefaultValue.StringValue(map.get(SysUserVo
                .FIELD_USERNAME)), Constant.DEFAULT_PASSWORD));
        int count = updateUser(map);
        if (count == 0) {
            throw new CommonException();
        }
    }

    @Override
    public Result forceChangePassword(Map<String, Object> map) {
        return null;
    }

    @Override
    public SysUserVo getUserByParam(Map<String, Object> map) {
        Param param = ParamBuilder.getInstance().getParam();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringDefaultValue.isEmpty(value)) {
                continue;
            }
            if (key.equals(SysUser.FIELD_REALNAME))
                param.add(ParamBuilder.nv(SysUser.FIELD_REALNAME, value));
            if (key.equals(SysUser.FIELD_USERNAME))
                param.add(ParamBuilder.nv(SysUser.FIELD_USERNAME, value));
            if (key.equals(SysUser.FIELD_PASSWORD))
                param.add(ParamBuilder.nv(SysUser.FIELD_PASSWORD, value));
            if (key.equals(SysUser.FIELD_MOBILE))
                param.add(ParamBuilder.nv(SysUser.FIELD_MOBILE, value));
            if (key.equals(SysUser.FIELD_STATUS))
                param.add(ParamBuilder.nv(SysUser.FIELD_STATUS, StringDefaultValue.intValue(value)));
        }
        SysUser sysUser = factory.getCacheReadDataSession().querySingleResultByParams(SysUser.class, param);
        if (StringDefaultValue.isEmpty(sysUser)) {
            return null;
        }
        SysUserVo sysUserVo = CommonBeanUtils.getBeanBySameProperty(SysUserVo.class, sysUser);
        return sysUserVo;
    }

    @Override
    public int updateUser(Map<String, Object> map) {
        int id = StringDefaultValue.intValue(map.get(SysUser.FIELD_ID));
        SysUser sysUser;
        sysUser = factory.getCacheReadDataSession().querySingleResultById(SysUser.class, id);
        if (sysUser == null) {
            throw new CommonException(ResultConstant.SysUserResult.SYSUSER_IS_NOT_FOUND);
        }
        try {
            map.remove(SysUser.FIELD_USERNAME);
        } catch (Exception e) {
        }
        sysUser = CommonBeanUtils.transMap2BasePO(map, sysUser);
        int count = factory.getCacheWriteDataSession().update(SysUser.class, sysUser);
        if (count < 1) {
            throw new CommonException(ResultConstant.SysUserResult.UPDATE_SYSUSER_IS_FAILD);
        }
        return count;
    }

    @Override
    public List<SysUserVo> getSysUserByRoleId(Map<String, Object> map) {
        Param param = ParamBuilder.getInstance().getParam();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (StringDefaultValue.isEmpty(value)) {
                continue;
            }
            if (key.equals(SysUser.FIELD_REALNAME))
                param.add(ParamBuilder.nv(SysUser.FIELD_REALNAME, value));
            if (key.equals(SysUserRole.FIELD_ROLE_ID))
                param.add(ParamBuilder.nv(SysUserRole.FIELD_ROLE_ID, StringDefaultValue.intValue(value)));

        }
        List<SysUser> sysUserList = factory.getCacheReadDataSession().queryVOList(SysUser.class, new Throwable(), param);
        List<SysUserVo> sysUserVoList = TransUtil.transPoListToVoList(sysUserList, SysUserVo.class);
        sysUserVoList = sysUserVoList.stream().filter(p -> p.getStatus() == Constant.CommonConstant.STATUS_ENABLE).collect(Collectors.toList());
        return sysUserVoList;

    }

    @Override
    public LoginVO login(Map<String, Object> params, HttpServletRequest request) {
        String username = StringDefaultValue.StringValue(params.get(SysUserVo.FIELD_USERNAME));
        String password = StringDefaultValue.StringValue(params.get(SysUserVo.FIELD_PASSWORD));
        String secretPass = PassSecret.passMd5(username, password);
        params.put(SysUserVo.FIELD_PASSWORD, secretPass);
        SysUserVo sysUserVo = getUserByParam(params);
        if (sysUserVo == null)
            throw new CommonException(ResultConstant.SysUserResult.LOGIN_ERROR);

        if (sysUserVo.getStatus() == Constant.status.close) {
            throw new CommonException(ResultConstant.SysUserResult.USER_IS_CLOSED);
        }
        String secretPassD = PassSecret.passMd5(username, Constant.DEFULT_PASSWORD);
        //数据库中为系统默认密码则强制修改
        if (sysUserVo.getPassword().equals(secretPassD)) {
            throw new CommonException(ResultConstant.SysUserResult.PLEASE_CHANGED_PASSWORD);
        }
        //增加登陆ip和登陆时间
        params.put(SysUserVo.FIELD_ID, sysUserVo.getId());
        params.put(SysUserVo.FIELD_IP_ADDRESS, GetIpAddress.getIpAddr(request));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        logger.info("用户ip：" + GetIpAddress.getIpAddr(request) + "，登陆时间：" + format.format(new Date()));
        int updateCount = updateUser(params);
        if (updateCount == 0) {
            throw new CommonException(ResultConstant.SysUserResult.UPDATE_SYSUSER_IS_FAILD);
        }
        //查询用户角色信息
        List<SysUserRoleVo> userRoleVoList = getRolesByUserId(sysUserVo.getId());
        List<Integer> roleIds = new ArrayList<>();
        for (SysUserRoleVo ur : userRoleVoList) {
            roleIds.add(ur.getRoleId());
        }
        LoginVO vo = new LoginVO(sysUserVo, roleIds);
        vo = SystemSessionUtil.createSysUserSession(vo);
        return vo;
    }

    @Override
    public void logout(String sesionId) {
        factory.getRedisSessionFactory().getSession().delete(sesionId);
    }
}
