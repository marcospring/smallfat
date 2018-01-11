package com.smt.smallfat.web.backend.system;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.service.system.SysPermissionService;
import com.smt.smallfat.vo.PermissionTreeVo;
import com.smt.smallfat.vo.SysPermissionVo;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/12.
 */
@Controller
@RequestMapping("/backend/permission")
public class PermissionController extends BaseController{

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 增加权限
     * @param request
     * @param response
     */
    @RequestMapping("addPermission")
    public void addPermission(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysPermissionVo.FIELD_PERMISSION_NAME,SysPermissionVo.FIELD_PERMISSION_CODE);
        SysPermissionVo result = permissionService.addPermission(param);
        printWriter(response, successResultJSON(result));
    }

    /**
     * 删除权限
     * @param request
     * @param response
     */
    @RequestMapping("deletePermission")
    public void deletePermission(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,SysPermissionVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysPermissionVo.FIELD_ID));
        permissionService.deletePermission(id);
        printWriter(response, successResultJSON());
    }

    /**
     * 更新权限
     * @param request
     * @param response
     */
    @RequestMapping("updatePermission")
    public void updatePermission(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,SysPermissionVo.FIELD_ID);
        SysPermissionVo permission = permissionService.updatePermission(param);
        printWriter(response, successResultJSON(permission));
    }

    /**
     * 根据权限id查询权限
     * @param request
     * @param response
     */
    @RequestMapping("permissionById")
    public void getPermissionById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,SysPermissionVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysPermissionVo.FIELD_ID));
        SysPermissionVo permission = permissionService.getPermissionById(id);
        printWriter(response, successResultJSON(permission));
    }

    /**
     * 根据权限uuid查询权限
     * @param request
     * @param response
     */
    @RequestMapping("permissionByUUID")
    public void getPermissionByUUID(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,SysPermissionVo.FIELD_UUID);
        String uuid = StringDefaultValue.StringValue(param.get(SysPermissionVo.FIELD_UUID));
        SysPermissionVo permission = permissionService.getPermissionByUUID(uuid);
        printWriter(response, successResultJSON(permission));
    }

    @RequestMapping("userPermissionTree")
    public void userPermissionTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,"userId");
        int userId = StringDefaultValue.intValue(param.get("userId"));
        List<PermissionTreeVo> list = permissionService.getPermissionTreeByUserId(userId);
        printWriter(response, successResultJSON(list));
    }

    /**
     * 获取权限树
     * @param request
     * @param response
     */
    @RequestMapping("permissionTree")
    public void permissionTree(HttpServletRequest request,HttpServletResponse response) {
      //  Map<String, Object> param = getRequestParams(request);
        List<PermissionTreeVo> list = permissionService.getPermissionTree();
        printWriter(response, successResultJSON(list));
    }

    /**
     * 根据role获取权限列表
     * @param request
     * @param response
     */
    @RequestMapping("getRolePermissionTree")
    public void getRolePermissionTree(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "roleId");
        int roleId = StringDefaultValue.intValue(param.get("roleId"));
        List<PermissionTreeVo> list = permissionService.getRolePermissionTree(roleId);
        printWriter(response, successResultJSON(list));
    }

}
