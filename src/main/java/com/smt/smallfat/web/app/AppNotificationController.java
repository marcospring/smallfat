package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatNotification;
import com.smt.smallfat.service.base.CommentService;
import com.smt.smallfat.service.base.NotificationService;
import com.smt.smallfat.service.house.CircleService;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/app/notification")
public class AppNotificationController extends BaseController{

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CircleService circleService;

    @RequestMapping("/read")
    public void read(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, FatNotification.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatNotification.FIELD_ID));
        FatNotification notification = notificationService.read(id);
        printWriter(response,successResultJSON(notification));
    }
    @RequestMapping("/pageNotification")
    public void pageNotification(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<FatNotification> page = notificationService.userPageNotification(userId,pageNo,pageSize);
        printWriter(response,successResultJSON(page));
    }

    @RequestMapping("/isReadNotification")
    public void isReadNotification(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        long count = commentService.nonReadCommentCount(userId);
        Map<String,Object> result = notificationService.haveNonReadNotification(userId);
        result.put("unReadCommentCount",count);
        result.put("unReadCirclePraise",circleService.notReadPraise(userId));
        result.put("unReadCircleFollow",circleService.notReadFollow(userId));
        printWriter(response,successResultJSON(result));
    }
}
