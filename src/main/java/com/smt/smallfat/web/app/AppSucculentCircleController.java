package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.house.CircleService;
import com.smt.smallfat.vo.house.FlowerHouseItemVO;
import com.smt.smallfat.vo.house.PraiseUserVO;
import com.smt.smallfat.vo.house.UserIndexPage;
import com.smt.smallfat.vo.upload.UploadInfo;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/app/circle")
public class AppSucculentCircleController extends BaseController {

    @Autowired
    private CircleService service;


    @RequestMapping("/index")
    public void indexCircleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<FlowerHouseItemVO> page = service.indexCircleList(0, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/circleView")
    public void circleView(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatSucculentCircle.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatSucculentCircle.FIELD_ID));
        int userId = StringDefaultValue.intValue(params.get(Constant.USER_ID));
        FlowerHouseItemVO vo = service.circleItemView(userId, id);
        printWriter(response, successResultJSON(vo));
    }

    @RequestMapping("/praise")
    public void praise(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatSucculentPraise.FIELD_FROM_USER_ID,
                FatSucculentCircle.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatSucculentCircle.FIELD_ID));
//        int type = StringDefaultValue.intValue(params.get("praiseType"));
        int fromUserId = StringDefaultValue.intValue(FatSucculentPraise.FIELD_FROM_USER_ID);
        int state = service.praise(fromUserId, id);
        printWriter(response, successResultJSON(state));
    }

    @RequestMapping("/userIndex")
    public void userIndex(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID, "visitorId");
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int visitorId = StringDefaultValue.intValue(param.get("visitorId"));
        UserIndexPage page = service.userIndex(userId, visitorId);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/praiseUserList")
    public void praiseUserList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatSucculentPraise.FIELD_CIRCLE_ID);
        int circleId = StringDefaultValue.intValue(param.get(FatSucculentPraise.FIELD_CIRCLE_ID));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<PraiseUserVO> page = service.praiseUserList(circleId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/circleList")
    public void circleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getRequestParams(request);
        int pageNo = StringDefaultValue.intValue(params.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(params.get(Constant.PAGE_SIZE));
        Pagination<FlowerHouseItemVO> page = service.circleList(0, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/myCircleList")
    public void myCircleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getRequestParams(request);
        int userId = StringDefaultValue.intValue(params.get(Constant.USER_ID));
        int pageNo = StringDefaultValue.intValue(params.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(params.get(Constant.PAGE_SIZE));
        Pagination<FlowerHouseItemVO> page = service.circleList(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/uploadImage")
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) {
       // 创建一个通用的多部分解析器
         CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                 .getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            service.uploadImage(multiRequest);
        }
    }

    @RequestMapping("/follow")
    public void follow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID,
                FatSucculentFollow.FIELD_FOLLOW_USER_ID);
        int followUserId = StringDefaultValue.intValue(params.get(FatSucculentFollow.FIELD_FOLLOW_USER_ID));
        int beFollowedUserId = StringDefaultValue.intValue(params.get(FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID));
        int state = service.follow(followUserId, beFollowedUserId);
        printWriter(response, successResultJSON(state));

    }

    @RequestMapping("/followList")
    public void followList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "personId");
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int userId = StringDefaultValue.intValue(param.get("personId"));
        Pagination<FatCustomer> page = service.followList(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/beFollowedList")
    public void beFollowedList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "personId");
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int userId = StringDefaultValue.intValue(param.get("personId"));
        Pagination<FatCustomer> page = service.beFollowList(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }


}
