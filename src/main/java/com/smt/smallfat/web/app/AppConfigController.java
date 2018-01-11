package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.smt.smallfat.service.pay.PayConstant;
import com.smt.smallfat.service.share.ShareService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class  AppConfigController extends BaseController{

    @Autowired
    private ShareService shareService;

    @RequestMapping("/config")
    public void config(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = new HashMap<>(1);
        param.put("imagePath","http://images.huaxi0.com");
        param.put("sharePath","http://share.huaxi0.com");
        printWriter(response,successResultJSON(param));
    }

    @RequestMapping("/shareConfig")
    public void shareConfig(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,"url");
        Map<String,String> result = shareService.getShareConfig(StringDefaultValue.StringValue(param.get("url")));
        printWriter(response,successResultJSON(result));
    }
}
