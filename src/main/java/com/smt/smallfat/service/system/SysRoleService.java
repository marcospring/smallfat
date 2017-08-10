package com.smt.smallfat.service.system;


import com.csyy.core.result.Result;
import com.smt.smallfat.vo.SysRoleVo;

import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/11.
 */
public interface SysRoleService {

    /**
     * 添加系统角色.
     * @param param
     * @return
     */
    void addSysRole(Map<String, Object> param);

    /**
     * 通过id获得系统角色.
     * @param id
     * @return
     */
    SysRoleVo getSysRoleById(int id);
//
//    /**
//     * 通过uuid获得系统角色.
//     * @param uuid
//     * @return
//     */
//    SysRolePoVo getSysRoleByUUID(String uuid);

    /**
     * 修改系统角色.
     * @param param
     * @return
     */
    void updateSysRole(Map<String, Object> param);


    /**
     * 获得一个list集合系统角色.
     * param
     * @return
     */
    List<SysRoleVo> getSysRoles(Map<String, Object> param);
//
//    /**
//     * 分页返回系统角色.
//     * @param param
//     * @param pageNo
//     * @param pageSize
//     * @return
//     */
//    Pagination<SysRolePoVo> paginationSysRole(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 根据用户ID获取用户所有角色ID
     * @param userId
     * @return
     */
    Result getRoleIdByUserId(Map<String, Object> param);
}
