package com.smt.smallfat.web.backend.article;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.service.base.ArticleService;
import com.smt.smallfat.utils.push.IPush;
import com.smt.smallfat.utils.push.PushMessage;
import com.smt.smallfat.utils.push.PushPayloadBuilder;
import com.smt.smallfat.utils.push.pushenum.PlatForm;
import com.smt.smallfat.vo.ArticleVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/backend/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private IPush push;

    @RequestMapping("/addArticle")
    public void addArticle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_TITLE, FatArticle.FIELD_CONTENT,
                FatArticle.FIELD_AUTHOR);
        ArticleVO article = articleService.addArticle(param);
        printWriter(response, successResultJSON(article));
    }

    @RequestMapping("/delArticle")
    public void delArticle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        articleService.deleteArticle(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/updateArticle")
    public void updateArticle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        ArticleVO article = articleService.updateArticle(param);
        printWriter(response, successResultJSON(article));
    }

    @RequestMapping("/pageArticle")
    public void pageArticle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        Pagination<ArticleVO> page = articleService.getArticlesByParam(param);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/getArticleById")
    public void getArticleById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        ArticleVO article = articleService.getArticleVOById(id);
        printWriter(response, successResultJSON(article));
    }

    @RequestMapping("/addToApp")
    public void addToApp(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        articleService.addToAppIndex(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/orderArticle")
    public void orderArticle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        articleService.orderArticle(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/pushArticle")
    public void pushArticle(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID, FatArticle.FIELD_TITLE);
        String title = StringDefaultValue.StringValue(param.get(FatArticle.FIELD_TITLE));
        String id = StringDefaultValue.StringValue(param.get(FatArticle.FIELD_ID));
        PushMessage message = PushMessage.get().content(title).platform(PlatForm.ALL).title(title).addExtras(Constant
                .PUSH_TYPE, Constant.PushType.ARTICLE).addExtras(FatArticle.FIELD_TITLE, title).addExtras(FatArticle.FIELD_ID, id);
        push.push(PushPayloadBuilder.newInstance().build(message));
        printWriter(response, successResultJSON());
    }
}
