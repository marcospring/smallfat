package com.smt.smallfat.web.backend.user;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.service.system.SysUserService;
import com.smt.smallfat.vo.PaginationVo;
import com.smt.smallfat.vo.SysUserRoleVo;
import com.smt.smallfat.vo.SysUserVo;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by xindongwang on 17/3/13.
 */
@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService userService;

    /**
     * 添加系统用户
     *
     * @param request
     * @param response
     */
    @RequestMapping("/addSysUser")
    public void addSysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysUserVo.FIELD_REALNAME,SysUserVo.FIELD_USERNAME,SysUserVo.FIELD_MOBILE,SysUserVo.FIELD_STATUS);
        SysUserVo result = userService.addSysUser(param);
        printWriter(response, successResultJSON(result));
    }

    /**
     * 通过Id获得系统用户
     *
     * @param request
     * @param response
     */
    @RequestMapping("/getSysUserById")
    public void getSysUserById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request,SysUserVo.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(SysUserVo.FIELD_ID));
        SysUserVo user = userService.getSysUserById(id);
        printWriter(response, successResultJSON(user));
    }

    /**
     * 修改系统用户
     * userId
     * roleIds选填
     * @param request
     * @param response
     */
    @RequestMapping("/updateSysUser")
    public void updateSysUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysUserRoleVo.FIELD_USER_ID);
        userService.updateSysUserAndRole(param);
        printWriter(response, successResultJSON("修改系统用户成功"));
    }

    /**
     * 分页获得系统用户
     *
     * @param request
     * @param response
     */
    @RequestMapping("/getSysUserByPage")
    public void getSysUserByPage(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        int pageNo = StringDefaultValue.intValue(param.get(PAGE_NO));
        if(pageNo<1){
            pageNo=1;
        }
        param.put("pageNo",pageNo);
        PaginationVo<SysUserVo> sysUserVoPaginationVo = userService.paginationSysUser(param);
        printWriter(response, successResultJSON(sysUserVoPaginationVo));
    }


    @RequestMapping("/resetPassword")
    public void reserPassword(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> param = nullAbleValidation(request, SysUserVo.FIELD_ID,SysUserVo.FIELD_PASSWORD,
                "newPassword");
        userService.changePassword(param);
        printWriter(response,successResultJSON());
    }

}
