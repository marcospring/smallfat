package com.smt.smallfat.service.system.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.constant.Constants;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.SysPermission;
import com.smt.smallfat.po.SysRole;
import com.smt.smallfat.po.SysRolePermission;
import com.smt.smallfat.po.SysUserRole;
import com.smt.smallfat.service.system.SysPermissionService;
import com.smt.smallfat.vo.PermissionTreeVo;
import com.smt.smallfat.vo.SysPermissionVo;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by xindongwang on 17/3/12.
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl implements SysPermissionService {

    @Override
    public SysPermissionVo addPermission(Map<String, Object> param) {
        //根据名称验证权限是否存在
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysPermission
                .FIELD_PERMISSION_NAME, StringDefaultValue.StringValue(param.get(SysPermission.FIELD_PERMISSION_NAME))));
        SysPermission permission =  factory.getCacheReadDataSession().querySingleResultByParams(SysPermission
                .class, params);
        if (permission != null)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NOT_NULL);
        //根据code验证权限是否存在
        params.clean();
        params.add(ParamBuilder.nv(SysPermission.FIELD_PERMISSION_CODE, StringDefaultValue.StringValue(param.get(SysPermission.FIELD_PERMISSION_CODE))));
        permission = factory.getCacheReadDataSession().querySingleResultByParams(SysPermission.class, params);
        if (permission != null)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NOT_NULL);
        //转化前台数据
        permission = CommonBeanUtils.transMap2BasePO(param, SysPermission.class);
        int parentId = StringDefaultValue.intValue(permission.getParentId());
        if (parentId != 0) {
            SysPermission parentPermission = factory.getCacheReadDataSession().querySingleResultById
                    (SysPermission.class, parentId);
            if(parentPermission == null)
                throw new CommonException(ResultConstant.SysPermission.PARENT_PERMISSION_IS_NULL);
        }
        permission.setParentId(parentId);
        SysPermission sysPermission = factory.getCacheWriteDataSession().save(SysPermission.class, permission);
        SysPermissionVo permissionVo = CommonBeanUtils.getBeanBySameProperty(SysPermissionVo.class, sysPermission);
        return permissionVo;
    }

    @Override
    public void deletePermission(int id) {
        SysPermission permission = factory.getCacheReadDataSession().querySingleResultById(SysPermission.class, id);
        if (permission == null)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NULL);
        Param param = ParamBuilder.getInstance().getParam();
        param.add(ParamBuilder.nv(SysPermission.FIELD_PARENT_ID, id));
        List<SysPermission> list = factory.getCacheReadDataSession().queryListResult(SysPermission.class, param);
        if (list != null && list.size() > 0)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_HAS_CHILDREN);
        param.clean();
        param.add(ParamBuilder.nv(SysRolePermission.FIELD_PERMISSION_ID, id));
        long count = factory.getCacheReadDataSession().queryListResultCount(SysRolePermission.class, param);
        if (count > 0)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_HAS_USED);
        param.clean().add(ParamBuilder.nv(SysPermission.FIELD_ID, id));
        factory.getCacheWriteDataSession().logicDelete(SysPermission.class, param);
    }

    @Override
    public SysPermissionVo updatePermission(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(SysPermission.FIELD_ID));
        SysPermission permission = factory.getCacheReadDataSession().querySingleResultById(SysPermission.class, id);
        if (permission == null && id != 0)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NULL);
        permission = CommonBeanUtils.transMap2BasePO(param, permission);
        factory.getCacheWriteDataSession().update(SysPermission.class, permission);
        SysPermissionVo permissionVo = CommonBeanUtils.getBeanBySameProperty(SysPermissionVo.class, permission);
        return permissionVo;
    }

    @Override
    public SysPermissionVo getPermissionById(int id) {
        SysPermission sysPermission = factory.getCacheReadDataSession().querySingleResultById(SysPermission.class, id);
        if (sysPermission == null && id != 0)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NULL);
        SysPermissionVo permissionVo = CommonBeanUtils.getBeanBySameProperty(SysPermissionVo.class, sysPermission);
        return permissionVo;
    }

    @Override
    public SysPermissionVo getPermissionByUUID(String uuid) {
        SysPermission sysPermission = factory.getCacheReadDataSession().querySingleResultByUUID(SysPermission.class, uuid);
        if (sysPermission == null)
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NULL);
        SysPermissionVo permissionVo = CommonBeanUtils.getBeanBySameProperty(SysPermissionVo.class, sysPermission);
        return permissionVo;
    }

    @Override
    public List<PermissionTreeVo>  getPermissionTreeByUserId(int userId) {
        List<PermissionTreeVo> result = new ArrayList<>();
        //根据用户查询用户所有的角色
        List<Integer> list = new ArrayList<>();
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysUserRole.FIELD_USER_ID,userId));
        List<SysUserRole> usrRoleList = factory.getCacheReadDataSession().queryListResult(SysUserRole.class,param);
        list.addAll(usrRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));

        //去除禁用角色
        List<Integer> enabled = new ArrayList<>();
        for (Integer id: list) {
            SysRole sysRole;
            sysRole = factory.getCacheReadDataSession().querySingleResultById(SysRole.class, id);
            if (sysRole == null) {
                throw new CommonException(ResultConstant.SysRoleResult.SYSROLE_IS_NOT_FOUND);
            }
            if (sysRole.getStatus() == Constant.CommonConstant.STATUS_DISABLE){
                enabled.add(id);
            }
        }
        list.removeAll(enabled);
        //根据角色ID可以获取角色ID下的权限列表
        if (list != null && list.size() > 0) {
            CustomSQL where = SQLCreator.where().cloumn(SysRolePermission.FIELD_ROLE_ID).operator(ESQLOperator.IN).v(list.toArray(new Integer[list.size()]));
            List<SysRolePermission> authList = factory.getCacheReadDataSession().queryListResultByWhere(SysRolePermission.class, where);
            if (authList != null && authList.size() > 0) {
                Set<Integer> permissionIdList = new HashSet<>(authList.size());
                permissionIdList.addAll(authList.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList()));
                where = SQLCreator.where().cloumn(SysPermission.FIELD_ID).operator(ESQLOperator.IN).v(permissionIdList.toArray(new Integer[permissionIdList.size()]));
                List<SysPermission> permissionList = factory.getCacheReadDataSession().queryListResultByWhere(SysPermission.class, where);
                //去除所有的顶级菜单,并去重
                List<SysPermission> topMenus = new ArrayList<>();
                topMenus.addAll(permissionList.stream().filter(sysPermission -> sysPermission.getParentId() == 0).distinct().collect(Collectors.toList()));
                permissionList.removeAll(topMenus);
                for (SysPermission permission : topMenus) {
                    PermissionTreeVo top = new PermissionTreeVo(permission.getId(), permission.getPermissionName(), permission.getPermissionUrl(), permission.getPermissionType(),permission.getRemark(), permission.getPermissionCode());
                    for (SysPermission childPermission : permissionList) {
                        if (childPermission.getParentId() == permission.getId()) {
                            PermissionTreeVo child = new PermissionTreeVo(childPermission.getId(), childPermission.getPermissionName(), childPermission.getPermissionUrl(), childPermission.getPermissionType(),childPermission.getRemark(), childPermission.getPermissionCode());
                            for (SysPermission grandsonPermission : permissionList) {
                                if (grandsonPermission.getParentId() == child.getId()) {
                                    child.getChildren().add(new PermissionTreeVo(grandsonPermission.getId(), grandsonPermission.getPermissionName(), grandsonPermission.getPermissionUrl(), grandsonPermission.getPermissionType(),grandsonPermission.getRemark(), grandsonPermission.getPermissionCode()));
                                }
                            }
                            top.getChildren().add(child);
                        }
                    }
                    result.add(top);
                }
            }
        }
        return result;
    }

    @Override
    public List<PermissionTreeVo> getPermissionTree() {
        Param param = ParamBuilder.getInstance().getParam();
        param.add(ParamBuilder.nv(SysPermission.FIELD_PARENT_ID, Constants.WrapperExtend.ZERO));
        List<SysPermission> top = factory.getCacheReadDataSession().queryListResult(SysPermission.class, param);
        List<PermissionTreeVo> result = new ArrayList<>(top.size());
        result.addAll(top.stream().map(permission -> build(new PermissionTreeVo(permission.getId(), permission.getPermissionName(), permission.getPermissionUrl(),permission.getPermissionType(), permission.getRemark(),permission.getPermissionCode()))).collect(Collectors.toList()));
        return result;
    }

    @Override
    public List<PermissionTreeVo> getRolePermissionTree(int roleId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysRolePermission.FIELD_ROLE_ID, roleId));
        List<SysRolePermission> rolePermission = factory.getCacheReadDataSession().queryListResult(SysRolePermission.class, param);
        List<PermissionTreeVo> result = getPermissionTree();
        for (PermissionTreeVo vo : result) {
            for (SysRolePermission rp : rolePermission) {
                if (vo.getId() == rp.getPermissionId()) {
                    vo.setCheck(true);
                    continue;
                }
                if (!StringDefaultValue.isEmpty(vo.getChildren()) && vo.getChildren().size() > 0) {
                    for (PermissionTreeVo child : vo.getChildren()) {
                        if (child.getId() == rp.getPermissionId()) {
                            child.setCheck(true);
                            continue;
                        }
                        if (!StringDefaultValue.isEmpty(child.getChildren()) && child.getChildren().size() > 0) {
                            for (PermissionTreeVo grandson : child.getChildren()) {
                                if (grandson.getId() == rp.getPermissionId()) {
                                    child.setCheck(true);
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
    private PermissionTreeVo build(PermissionTreeVo vo) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysPermission.FIELD_PARENT_ID, vo
                .getId()));
        List<SysPermission> permissionList = factory.getCacheReadDataSession().queryListResult(SysPermission.class, param);
        if (permissionList != null && permissionList.size() > 0) {
            List<PermissionTreeVo> tree = new ArrayList<>();
            for (SysPermission permission : permissionList) {
                PermissionTreeVo treeVO = new PermissionTreeVo(permission.getId(), permission
                        .getPermissionName(), permission.getPermissionUrl(),  permission.getPermissionType(),permission.getRemark(),permission.getPermissionCode());
                treeVO = build(treeVO);
                treeVO.setCheckbox(Boolean.TRUE.toString());
                tree.add(treeVO);
                vo.setChildren(tree);
            }
            vo.setState(PermissionTreeVo.NODE_STATE.CLOSED.state());
            vo.setChildren(tree);
        }

        return vo;
    }
}
