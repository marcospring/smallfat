package com.smt.smallfat.web.backend.article;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
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
@RequestMapping("/backend/comment")
public class CommentController extends BaseController{
    @Autowired
    private CommentService commentService;
    @RequestMapping("/pageComment")
    public void pageComment(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> param = getRequestParams(request);
        Pagination<FatCommentVO> page = commentService.pageComment(param);
        printWriter(response,successResultJSON(page));
    }

    @RequestMapping("/delComment")
    public void delComment(HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> params = nullAbleValidation(request, FatComment.FIELD_ID);
        int id = StringDefaultValue.intValue(params.get(FatComment.FIELD_ID));
        commentService.deleteComment(id);
        printWriter(response,successResultJSON());
    }

    public void test(HttpServletRequest request, HttpServletResponse response){
       validation(request, new Validator() {
            @Override
            public Object valid(Map<String, Object> var1) {

                return null;
            }
        });
    }
}
