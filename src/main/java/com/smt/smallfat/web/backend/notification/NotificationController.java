package com.smt.smallfat.web.backend.notification;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatNotification;
import com.smt.smallfat.service.NotificationService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/notification")
public class NotificationController extends BaseController{
    @Autowired
    private NotificationService notificationService;

    @RequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatNotification.FIELD_TITLE,FatNotification
                .FIELD_CONTENT,FatNotification.FIELD_IS_ALL);
        FatNotification notification = notificationService.addNotification(param);
        printWriter(response,successResultJSON(notification));
    }

    @RequestMapping("/page")
    public void page(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        Pagination<FatNotification> page = notificationService.pageNotification(param);
        printWriter(response,successResultJSON(page));

    }

    @RequestMapping("/pushNotification")
    public void pushNotification(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatNotification.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatNotification.FIELD_ID));
        notificationService.pushNotification(id);
        printWriter(response,successResultJSON());
    }

    @RequestMapping("/deleteNotification")
    public void deleteNotification(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request,FatNotification.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatNotification.FIELD_ID));
        notificationService.deleteNotification(id);
        printWriter(response,successResultJSON());
    }
}
