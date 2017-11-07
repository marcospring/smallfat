package com.smt.smallfat.service.system;


import com.csyy.core.datasource.param.Param;
import com.csyy.core.result.Result;
import com.smt.smallfat.vo.LoginVO;
import com.smt.smallfat.vo.PaginationVo;
import com.smt.smallfat.vo.SysUserRoleVo;
import com.smt.smallfat.vo.SysUserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/11.
 */
public interface SysUserService {

    /**
     * 添加系统用户.
     * @param param
     * @return
     */
    SysUserVo addSysUser(Map<String, Object> param);

    /**
     * 通过id获得系统用户
     * @param id
     * @return
     */
    SysUserVo getSysUserById(int id);


    /**
     * 修改系统用户.
     * @param param
     * @return
     */
    void updateSysUserAndRole(Map<String, Object> param);


    /**
     * 分页获得系统用户.
     * @param param
     * @param
     * @param
     * @return
     */
    PaginationVo<SysUserVo> paginationSysUser(Map<String, Object> param);

    Integer countOrdUserVos(Param param);
    /**
     * 根据用户id查询所有权限
     * @param userId
     * @return
     */
    List<SysUserRoleVo> getRolesByUserId(int userId);


    /**
     * 修改密码
     * @param map
     * @return
     */
    void changePassword(Map<String, Object> map);

    /**
     * 强制修改用户密码
     */
    Result forceChangePassword(Map<String, Object> map);

    /**
     * 动态根据条件查询用户
     * @param map
     * @return
     */
    SysUserVo getUserByParam(Map<String, Object> map);

    /**
     * 更新系统用户
     * @param map
     * @return
     */
    int updateUser(Map<String, Object> map);

    /**
     * 根据角色获取用户
     * @param param
     * @return
     */
    List<SysUserVo> getSysUserByRoleId(Map<String, Object> param);

    /**
     * 根据用户名密码查询用户
     * @param params
     * @param request
     * @return
     */
    LoginVO login(Map<String,Object> params, HttpServletRequest request);
    /**
     *
     * @param sesionId
     */
    void logout(String sesionId);
}
