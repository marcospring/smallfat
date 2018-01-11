package com.smt.smallfat.web.app;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatComment;
import com.smt.smallfat.service.base.CommentService;
import com.smt.smallfat.vo.FatCommentVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/app/comment")
public class AppCommentController extends BaseController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/addComment")
    public void addComment(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatComment.FIELD_ARTICLE_ID, FatComment
                .FIELD_FROM_USERID, FatComment.FIELD_CONTENT);
        FatCommentVO comment = commentService.addComment(param);
        printWriter(response, successResultJSON(comment));
    }

    @RequestMapping("/pageComment")
    public void pageComment(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = nullAbleValidation(request, FatComment.FIELD_ARTICLE_ID);
        Pagination<FatCommentVO> page = commentService.pageComment(map);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/readComment")
    public void readComment(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = nullAbleValidation(request, FatComment.FIELD_ID);
        String ids = StringDefaultValue.StringValue(map.get(FatComment.FIELD_ID));
        commentService.readComment(ids);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/myComment")
    public void myComment(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = nullAbleValidation(request, "userId");
        int userId = StringDefaultValue.intValue(map.get("userId"));
        int pageNo = StringDefaultValue.intValue(map.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(map.get(Constant.PAGE_SIZE));
        Pagination<FatCommentVO> page = commentService.myComment(userId, pageNo, pageSize);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/delComment")
    public void delComment(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = nullAbleValidation(request, FatComment.FIELD_ID);
        int id = StringDefaultValue.intValue(map.get(FatComment.FIELD_ID));
        commentService.deleteComment(id);
        printWriter(response, successResultJSON());
    }

}
