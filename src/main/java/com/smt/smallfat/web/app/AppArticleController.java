package com.smt.smallfat.web.app;


import com.csyy.common.StringDefaultValue;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.service.base.ArticleService;
import com.smt.smallfat.vo.AppTopArticleVO;
import com.smt.smallfat.vo.ArticleVO;
import com.smt.smallfat.vo.IndexVO;
import com.smt.smallfat.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/article")
public class AppArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        Pagination<ArticleVO> page = articleService.appArticlePage(param);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/index")
    public void index(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = getRequestParams(request);
        IndexVO page = articleService.index(param);
        printWriter(response, successResultJSON(page));
    }

    @RequestMapping("/topList")
    public void topList(HttpServletResponse response) {
        List<AppTopArticleVO> result = articleService.appTopArticleList();
        printWriter(response, successResultJSON(result));
    }

    @RequestMapping("/articlePropertyCount")
    public void articlePropertyCount(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        int articleId = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        Map<String, Object> result = articleService.getArticlePropertyCount(userId, articleId);
        printWriter(response, successResultJSON(result));
    }


    @RequestMapping("/addReadCount")
    public void addReadCount(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        articleService.addReadCount(id);
        printWriter(response, successResultJSON());
    }

    @RequestMapping("/getArticleById")
    public void getArticleById(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> param = nullAbleValidation(request, FatArticle.FIELD_ID);
        int id = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        ArticleVO vo = articleService.getArticleVOById(id);
        vo.setShareUrl(Constant.ARTICLE_SHARE_URL + id);
        printWriter(response, successResultJSON(vo));
    }
}
