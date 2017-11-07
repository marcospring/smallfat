package com.smt.smallfat.web.backend.system;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.service.system.SysRoleService;
import com.smt.smallfat.vo.SysRoleVo;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/13.
 */
@Controller
@RequestMapping("/sysRole")
public class RoleController extends BaseController {

    @Autowired
    private SysRoleService roleService;

    /**
     * 添加系统角色
     * @param request
     * @param response
     */
    @RequestMapping("/addSysRole")
    public void addSysRole(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> param = nullAbleValidation(request, SysRoleVo.FIELD_ROLE_NAME,SysRoleVo.FIELD_REMARK,
                SysRoleVo.FIELD_STATUS);
        roleService.addSysRole(param);
        printWriter(response, successResultJSON());
    }

    /**
     * 通过Id获得系统角色
     * @param request
     * @param response
     */
    @RequestMapping("/getSysRoleById")
    public void getSysRoleById(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> param = nullAbleValidation(request, SysRoleVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysRoleVo.FIELD_ID));
        Map<String,Object> vo = roleService.getSysRoleById(id);
        printWriter(response, successResultJSON(vo));
    }

    /**
     * 修改系统角色
     * @param request
     * @param response
     */
    @RequestMapping("/updateSysRole")
    public void updateSysRole(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> param = getRequestParams(request);
        roleService.updateSysRole(param);
        printWriter(response, successResultJSON());
    }

    /**
     * 获得所有系统角色
     * @param request
     * @param response
     */
    @RequestMapping("/getSysRoles")
    public void getSysRoles(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> param = getRequestParams(request);
        List<SysRoleVo> roleVoList = roleService.getSysRoles(param);
        printWriter(response, successResultJSON(roleVoList));
    }

}
