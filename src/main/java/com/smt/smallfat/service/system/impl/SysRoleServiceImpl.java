package com.smt.smallfat.service.system.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.result.Result;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.SysRole;
import com.smt.smallfat.po.SysRolePermission;
import com.smt.smallfat.po.SysUserRole;
import com.smt.smallfat.service.system.SysPermissionService;
import com.smt.smallfat.service.system.SysRoleService;
import com.smt.smallfat.vo.PermissionTreeVo;
import com.smt.smallfat.vo.SysRolePermissionVo;
import com.smt.smallfat.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xindongwang on 17/3/13.
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl implements SysRoleService {

    @Autowired
    private SysPermissionService permissionService;

    @Override
    @Transactional
    public void addSysRole(Map<String, Object> param) {
        SysRole sysRole;
        Param params = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(SysRole.FIELD_ROLE_NAME, StringDefaultValue.StringValue(param.get("roleName"))));

        long count = factory.getCacheReadDataSession().queryListResultCount(SysRole.class, params);
        if (count > 0) {
            throw new CommonException(ResultConstant.SysRoleResult.SYSROLE_IS_EXISTS);
        }
        sysRole = CommonBeanUtils.transMap2BasePO(param, SysRole.class);
        sysRole = factory.getCacheWriteDataSession().save(SysRole.class, sysRole);
        param.put(SysRolePermission.FIELD_ROLE_ID, sysRole.getId());

        if (StringDefaultValue.isEmpty(param.get("permissionIds"))) {
            String roleId = param.get("roleId").toString();
            CustomSQL whereSql = SQLCreator.where().cloumn(SysRolePermission.FIELD_ROLE_ID).operator(ESQLOperator.EQ).v(roleId);
            factory.getCacheWriteDataSession().logicWhereDelete(SysRolePermission.class, whereSql);
        } else {
            int roleId = Integer.parseInt(param.get("roleId").toString());
            String permissionIds = param.get("permissionIds").toString();
            params.clean();
            params.add(ParamBuilder.nv(SysRolePermission.FIELD_ROLE_ID, roleId));
            List<SysRolePermission> existsList = factory.getCacheReadDataSession().queryListResult
                    (SysRolePermission.class, params);
            if (existsList == null) {
                throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NULL);
            }
            String[] rights = permissionIds.split(",");
            List<String> inputIds = Arrays.asList(rights);
            dealWithSysRolePermission(roleId, existsList, inputIds);
        }
    }


    @Override
    public List<SysRoleVo> getSysRoles(Map<String, Object> param) {
        List<SysRole> roles;
        Param params = ParamBuilder.getInstance().getParam();
        roles = factory.getCacheReadDataSession().queryListResult(SysRole.class, params);

        if (roles == null) {
            throw new CommonException(ResultConstant.SysRoleResult.SYSROLE_IS_NULL);
        }
        List<SysRoleVo> roleVOs = new ArrayList<>();
        int userId = 0;
        if (!StringDefaultValue.isEmpty(param.get(SysUserRole.FIELD_USER_ID))) {
            userId = StringDefaultValue.intValue(param.get(SysUserRole.FIELD_USER_ID));
        }
        if (userId > 0) {
            params.clean();
            params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysUserRole.FIELD_USER_ID,
                    userId));
            List<SysUserRole> sysUserRoles = factory.getCacheReadDataSession().queryListResult(SysUserRole.class, params);
            for (SysRole sysRole : roles) {
                SysRoleVo roleVO = CommonBeanUtils.getBeanBySameProperty(SysRoleVo.class, sysRole);
                if (sysUserRoles != null && sysUserRoles.size() > 0) {
                    for (SysUserRole sysUserRole : sysUserRoles) {
                        if (sysRole.getId() == sysUserRole.getRoleId()) {
                            roleVO.setTag("Y");
                            break;
                        } else {
                            roleVO.setTag("N");
                        }
                    }
                }
                roleVOs.add(roleVO);
            }
        } else {
            for (SysRole sysRole : roles) {
                SysRoleVo roleVO = CommonBeanUtils.getBeanBySameProperty(SysRoleVo.class, sysRole);
                roleVO.setTag("N");
                roleVOs.add(roleVO);
            }
        }
        return roleVOs;
    }

    @Override
    public Map<String,Object> getSysRoleById(int id) {
        SysRole sysRole;
        sysRole = factory.getCacheReadDataSession().querySingleResultById(SysRole.class, id);
        if (sysRole == null) {
            throw new CommonException(ResultConstant.SysRoleResult.SYSROLE_IS_NOT_FOUND);
        }
        SysRoleVo sysRoleVo = CommonBeanUtils.getBeanBySameProperty(SysRoleVo.class, sysRole);
        Map<String,Object> param = new HashMap<>();
        param.put(SysRolePermissionVo.FIELD_ROLE_ID,param.get(SysRoleVo.FIELD_ID));
        List<PermissionTreeVo> tree = permissionService.getRolePermissionTree(sysRole.getId());
        Map<String,Object> result = new HashMap<>(2);
        result.put("sysRole",sysRoleVo);
        result.put("permissionTreeVo",tree);
        return result;
    }

    @Override
    public void updateSysRole(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(SysRole.FIELD_ID));
        SysRole sysRole;
        sysRole = factory.getCacheReadDataSession().querySingleResultById(SysRole.class, id);
        if (sysRole == null) {
            throw new CommonException(ResultConstant.SysRoleResult.SYSROLE_IS_NOT_FOUND);
        }
        Param params = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(SysRole.FIELD_ROLE_NAME, StringDefaultValue.StringValue(param.get("roleName"))))
                .add(ParamBuilder.nv(SysRole.FIELD_DISABLED, 0));
        List<SysRole> roles = factory.getCacheReadDataSession().queryListResult(SysRole.class, params);
        SysRole finalSysRole = sysRole;
        try {
            SysRole self = null;
            self = roles.stream().filter(p -> p.getId() == finalSysRole.getId()).findFirst().get();
            roles.remove(self);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        if (roles != null && roles.size() > 0) {
            throw new CommonException(ResultConstant.SysRoleResult.SYSROLE_IS_EXISTS);
        }
        sysRole = CommonBeanUtils.transMap2BasePO(param, sysRole);
        factory.getCacheWriteDataSession().update(SysRole.class, sysRole);

        param.put(SysRolePermission.FIELD_ROLE_ID, id);

        int roleId = Integer.parseInt(param.get("roleId").toString());
        String permissionIds = param.get("permissionIds").toString();
        params.clean();
        params.add(ParamBuilder.nv(SysRolePermission.FIELD_ROLE_ID, roleId));
        List<SysRolePermission> permissions = factory.getCacheReadDataSession().queryListResult(SysRolePermission
                .class, params);
        if (permissions == null) {
            throw new CommonException(ResultConstant.SysPermission.PERMISSION_IS_NULL);
        }

        String[] rights = permissionIds.split(",");
        List<String> inputIds = Arrays.asList(rights);
        dealWithSysRolePermission(roleId, permissions, inputIds);
    }

    @Override
    public Result getRoleIdByUserId(Map<String, Object> param) {
        return null;
    }

    private List<SysRolePermission> dealWithSysRolePermission(int roleId, List<SysRolePermission> existsList, List<String> inputIds) {
        List<SysRolePermission> permissions = new ArrayList<SysRolePermission>();
        if (existsList == null || existsList.size() == 0) {
            for (String permissionId : inputIds) {
                SysRolePermission permission = SysRolePermission.getInstance(SysRolePermission.class);
                permission.setRoleId(roleId);
                permission.setPermissionId(Integer.parseInt(permissionId));
                permission.setCreateTime(new Date());
                permission = factory.getCacheWriteDataSession().save(SysRolePermission.class, permission);
                permissions.add(permission);
            }
        } else {
            //修改操作.
            Set<String> before = new HashSet<String>();
            Set<String> after = new HashSet<String>(inputIds);
            for (SysRolePermission permission : existsList) {
                before.add(permission.getPermissionId() + "");
            }
            Set<String> temp = new HashSet<String>(before);
            temp.retainAll(after);
            before.removeAll(temp);

            for (String permissionId : before) {
                for (SysRolePermission permission : existsList) {
                    if (permissionId.equals(permission.getPermissionId() + "")) {
                        //factory.getCacheWriteDataSession().physicalDelete(SysRolePermission.class, permission.getId());
                        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(SysRolePermission.FIELD_ID, permission.getId()));
                        factory.getCacheWriteDataSession().logicDelete(SysRolePermission.class, param);
                    }
                }
            }
            after.removeAll(temp);
            for (String permissionId : after) {
                SysRolePermission permission = new SysRolePermission();
                permission.setRoleId(roleId);
                permission.setPermissionId(Integer.parseInt(permissionId));
                permission.setCreateTime(new Date());
                permission = factory.getCacheWriteDataSession().save(SysRolePermission.class, permission);
                permissions.add(permission);
            }
        }
        return permissions;
    }
}
