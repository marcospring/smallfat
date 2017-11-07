package com.smt.smallfat.web.app;

import com.smt.smallfat.web.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class  AppConfigController extends BaseController{

    @RequestMapping("/config")
    public void config(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = new HashMap<>(1);
        param.put("imagePath","http://images.huaxi0.com");
        printWriter(response,successResultJSON(param));
    }
}
