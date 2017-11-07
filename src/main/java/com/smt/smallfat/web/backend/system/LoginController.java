package com.smt.smallfat.web.backend.system;


import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.service.system.SysUserService;
import com.smt.smallfat.vo.LoginVO;
import com.smt.smallfat.vo.SysUserVo;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class LoginController extends BaseController{

    @Autowired
    private SysUserService userService;
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, SysUserVo.FIELD_USERNAME, SysUserVo.FIELD_PASSWORD);
        LoginVO result = userService.login(param,request);
        printWriter(response, successResultJSON(result));
    }

    /**
     * 登出
     * @param request
     * @param response
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "SessionId");
        userService.logout(StringDefaultValue.StringValue(param.get("SessionId")));
        printWriter(response,successResultJSON());
    }
}
