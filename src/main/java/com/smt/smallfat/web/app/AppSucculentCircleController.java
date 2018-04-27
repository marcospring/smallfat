package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.exception.InfoEmptyException;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.house.CircleService;
import com.smt.smallfat.vo.house.*;
import com.smt.smallfat.web.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/app/circle")
@Api(tags = "花房")
public class AppSucculentCircleController extends BaseController {

    @Autowired
    private CircleService service;

    @ApiOperation(value = "花房首页接口(已经调用aritcle/index接口)", notes = "花房首页接口(已经调用aritcle/index接口)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType =
                    "int"),
    })
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public void indexCircleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<FlowerHouseItemVO> page = service.indexCircleList(0, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @ApiOperation(value = "花房详细页", notes = "花房详细页")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "花房项ID", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/circleView", method = RequestMethod.POST)
    public void circleView(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatSucculentCircle.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatSucculentCircle.FIELD_ID));
        int userId = StringDefaultValue.intValue(params.get(Constant.USER_ID));
        FlowerHouseItemVO vo = service.circleItemView(userId, id);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "给花房点赞", notes = "如果用户没有点赞则该接口为点赞，如果已经点赞则为取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户Id", required = true, dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "花房项ID", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/praise", method = RequestMethod.POST)
    public void praise(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, Constant.USER_ID,
                FatSucculentCircle.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatSucculentCircle.FIELD_ID));
//        int type = StringDefaultValue.intValue(params.get("praiseType"));
        int fromUserId = StringDefaultValue.intValue(params.get(Constant.USER_ID));
        int state = service.praise(fromUserId, id);
        printWriter(response, successResultJSON(state));
    }

    @ApiOperation(value = "给花房点赞", notes = "如果用户没有点赞则该接口为点赞，如果已经点赞则为取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户Id", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "visitorId", value = "花房项ID", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/userIndex", method = RequestMethod.POST)
    public void userIndex(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "visitorId");
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int visitorId = StringDefaultValue.intValue(param.get("visitorId"));
        UserIndexPage page = service.userIndex(userId, visitorId);
        printWriter(response, successResultJSON(page));
    }

    @ApiOperation(value = "单个花房的点赞用户的列表", notes = "单个花房的点赞用户的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "花房项ID", required = true, dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数",
                    dataType =
                            "int"),
    })
    @RequestMapping(value = "/praiseUserList", method = RequestMethod.POST)
    public void praiseUserList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatSucculentPraise.FIELD_CIRCLE_ID);
        int circleId = StringDefaultValue.intValue(param.get(FatSucculentPraise.FIELD_CIRCLE_ID));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<PraiseUserVO> page = service.praiseUserList(circleId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @ApiOperation(value = "花房列表", notes = "花房列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数",
                    dataType =
                            "int"),
    })
    @RequestMapping(value = "/circleList", method = RequestMethod.POST)
    public void circleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getRequestParams(request);
        int userId = StringDefaultValue.intValue(params.get(Constant.USER_ID));
        int pageNo = StringDefaultValue.intValue(params.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(params.get(Constant.PAGE_SIZE));
        Pagination<FlowerHouseItemVO> page = service.circleList(userId, pageNo, pageSize, 0);
        printWriter(response, successResultJSON(page));
    }

    @ApiOperation(value = "我的花房列表", notes = "个人花房列表，可查看自己的所有花房")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "visitorId", value = "访问的花房用户Id", required = true, dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户的Id",dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数",
                    dataType =
                            "int"),
    })
    @RequestMapping(value = "/myCircleList", method = RequestMethod.POST)
    public void myCircleList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = getRequestParams(request);
        int userId = StringDefaultValue.intValue(params.get(Constant.USER_ID));
        int visitorId = StringDefaultValue.intValue(params.get("visitorId"));
        int pageNo = StringDefaultValue.intValue(params.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(params.get(Constant.PAGE_SIZE));
        Pagination<FlowerHouseItemVO> page = service.circleList(userId, pageNo, pageSize, visitorId);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public void uploadImage(HttpServletRequest request, HttpServletResponse response) {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            ImageUploadInfo image = service.uploadImage(multiRequest);
            printWriter(response, successResultJSON(image));
        }
        throw new CommonException(ResultConstant.Circle.IMAGE_UPLOAD_FAILED);
    }

    @ApiOperation(value = "关注一个用户", notes = "当用户已经关注该用户，调用则取消关注，如果没有关注则关注该用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", required = true, dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "beFollowUserId", value = "被关注的用户Id", required = true,
                    dataType =
                            "int"),
    })
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public void follow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = nullAbleValidation(request, FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID,
                Constant.USER_ID);
        int followUserId = StringDefaultValue.intValue(params.get(Constant.USER_ID));
        int beFollowedUserId = StringDefaultValue.intValue(params.get(FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID));
        int state = service.follow(followUserId, beFollowedUserId);
        printWriter(response, successResultJSON(state));

    }

    @ApiOperation(value = "用户关注的用户列表", notes = "用户关注的用户的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "personId", value = "用户Id", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/followList", method = RequestMethod.POST)
    public void followList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "personId");
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int userId = StringDefaultValue.intValue(param.get("personId"));
        Pagination<FollowVO> page = service.followList(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @ApiOperation(value = "关注用户的用户列表", notes = "关注用户的用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "personId", value = "被关注用户Id", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/beFollowedList", method = RequestMethod.POST)
    public void beFollowedList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "personId");
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int userId = StringDefaultValue.intValue(param.get("personId"));
        Pagination<FollowVO> page = service.beFollowList(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping(value = "/addCircleItem", method = RequestMethod.POST)
    public void addCircleItem(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "imageUrls", FatSucculentCircle.FIELD_USER_ID,
                FatSucculentCircle.FIELD_ARTICLE_TYPE);
        FlowerHouseItemVO vo = service.addCircleItem(param);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "我的点赞列表", notes = "我的点赞列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户ID", required = true, dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页码", dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数",
                    dataType = "int"),
    })
    @RequestMapping(value = "/myPraise", method = RequestMethod.POST)
    public void myPraise(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<PraiseVO> page = service.myPraise(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @ApiOperation(value = "标记点赞已读", notes = "标记点赞已读")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "标记已读的id，多个用'" + "," + "'隔开", required = true,
                    dataType =
                            "int"),
    })
    @RequestMapping(value = "/readPraise", method = RequestMethod.POST)
    public void readPraise(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "ids");
        String ids = StringDefaultValue.StringValue(param.get("ids"));
        service.readPraise(ids);
        printWriter(response, successResultJSON());
    }

    @ApiOperation(value = "标记关注为已读", notes = "标记关注为已读")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "标记已读的id，多个用'" + "," + "'隔开", required = true,
                    dataType =
                            "int"),
    })
    @RequestMapping(value = "/readFollow", method = RequestMethod.POST)
    public void readFollow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, "ids");
        String ids = StringDefaultValue.StringValue(param.get("ids"));
        service.readFollow(ids);
        printWriter(response, successResultJSON());
    }

    @RequestMapping(value = "/circleShare", method = RequestMethod.POST)
    public void circleShare(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatSucculentCircle.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatSucculentCircle.FIELD_ID));
        CircleShareVO vo = service.circleShare(id);
        printWriter(response, successResultJSON(vo));
    }

    @ApiOperation(value = "删除花房项", notes = "删除花房项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "标记已读的id，多个用'" + "," + "'隔开", required = true,
                    dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户的Id", required = true,
                    dataType = "int"),
    })
    @RequestMapping(value = "/deleteCircle", method = RequestMethod.POST)
    public void deleteCircle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatSucculentCircle.FIELD_ID, Constant.USER_ID);
        int id = StringDefaultValue.intValue(param.get(FatSucculentCircle.FIELD_ID));
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        service.deleteCircle(id, userId);
        printWriter(response, successResultJSON());
    }
    @ApiOperation(value = "举报花房", notes = "举报花房")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "articleId", value = "被举报的花房的ID", required = true, dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "reportReason", value = "举报原因，与举报内容填一即可，都不填会参数不全",  dataType =
                    "int"),
            @ApiImplicitParam(paramType = "query", name = "reportContent", value = "举报内容，与举报原因填一即可，都不填会参数不全",
                    dataType =
                    "int"),
    })
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public void report(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatSucculentReport.FIELD_ARTICLE_ID);
        String reason = StringDefaultValue.StringValue(param.get(FatSucculentReport.FIELD_REPORT_REASON));
        String content = StringDefaultValue.StringValue(param.get(FatSucculentReport.FIELD_REPORT_CONTENT));
        if (StringUtils.isEmpty(reason) && StringUtils.isEmpty(content))
            throw new InfoEmptyException();
        service.reportCircle(param);
        printWriter(response, successResultJSON());
    }

    @ApiOperation(value = "查看关注用户的花房", notes = "查看关注用户的花房")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户ID", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/followUserCircle", method = RequestMethod.POST)
    public void followUserCircle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Pagination<FlowerHouseItemVO> page = service.followUserCircle(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));

    }

    @ApiOperation(value = "标记所有的点赞为已读", notes = "标记所有的点赞为已读")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户ID", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/readAllPraise", method = RequestMethod.POST)
    public void readAllPraise(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        service.readAllPraise(userId);
        printWriter(response, successResultJSON());
    }

    @ApiOperation(value = "标记所有的关注为已读", notes = "标记所有的关注为已读")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "登录用户ID", required = true, dataType =
                    "int"),
    })
    @RequestMapping(value = "/readAllFollow", method = RequestMethod.POST)
    public void readAllFollow(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, Constant.USER_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        service.readAllFollow(userId);
        printWriter(response, successResultJSON());
    }


}
