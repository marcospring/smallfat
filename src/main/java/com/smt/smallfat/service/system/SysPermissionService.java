package com.smt.smallfat.service.system;




import com.csyy.core.result.Result;
import com.smt.smallfat.vo.PermissionTreeVo;
import com.smt.smallfat.vo.SysPermissionVo;

import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/11.
 */
public interface SysPermissionService {

    /**
     *
     * @param param
     * @return
     */
    SysPermissionVo addPermission(Map<String, Object> param);

    /**
     *
     * @param id
     */
    void deletePermission(int id);

    /**
     * @param param
     * @return
     */
    SysPermissionVo updatePermission(Map<String, Object> param);

    /**
     *
     * @param id
     * @return
     */
    SysPermissionVo getPermissionById(int id);

    /**
     *
     * @param uuid
     * @return
     */
    SysPermissionVo getPermissionByUUID(String uuid);

    /**
     * 根据用户获取用回权限
     * @param userId
     * @return
     */
    List<PermissionTreeVo> getPermissionTreeByUserId(int userId);

    /**
     * 获取全部菜单的树形结构
     * @return
     */
    List<PermissionTreeVo> getPermissionTree();


    /**
     * 获取role获取权限列表
     * @param roleId
     * @return
     */
    List<PermissionTreeVo> getRolePermissionTree(int roleId);
}
