package com.smt.smallfat.web.app;

/**
 * Created by hanyangyang on 16/8/1.
 */
import com.smt.smallfat.exception.InfoEmptyException;
import com.smt.smallfat.service.base.VerService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * App_version Controller
 */
@Controller
@RequestMapping("/app/version")
public class AppVerController extends BaseController {
    @Autowired
    private VerService verService;
    /**
     *通过系统类型查询最新App_versionNo
     * @param request
     * @param response
     */
    @RequestMapping("/getSysVerBySysType")
    public void getSysVerBySysType(HttpServletRequest request,HttpServletResponse response){
        Map<String,Object> param = (Map<String,Object>) validation(request, new Validator() {
            @Override
            public Object valid(Map<String, Object> map) {
                if (StringUtils.isEmpty(map.get("sysType"))||StringUtils.isEmpty(map.get("versionNo"))){
                    throw new InfoEmptyException();
                }
                return map;
            }
        });
        Map<String,Object> versionNo = verService.getSysVerBySysType(param);
        printWriter(response,successResultJSON(versionNo));
    }

}
