package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.vo.AppTopArticleVO;
import com.smt.smallfat.vo.ArticleVO;
import com.smt.smallfat.vo.FavoriteVO;
import com.smt.smallfat.vo.IndexVO;

import java.util.List;
import java.util.Map;

/**
 * 精选文章管理
 */
public interface ArticleService {

    int ARTICLE_TYPE_DIC_ID = 1;

    int ARTICLE_FAVORITE_TYPE = 2;

    ArticleVO addArticle(Map<String, Object> param);

    void deleteArticle(int id);

    void deleteArticle(String uuid);

    ArticleVO updateArticle(Map<String, Object> param);

    ArticleVO getArticleVOById(int id);

    FatArticle getArticleById(int id);

    ArticleVO getArticleVOByUUID(String uuid);

    FatArticle getArticleByUUID(String uuid);

    Pagination<ArticleVO> getArticlesByParam(Map<String, Object> param);

    void addToAppIndex(int id);

    void orderArticle(int articleId);

    List<ArticleVO> topThreeArticle(int articleType);

    Pagination<ArticleVO> appArticlePage(Map<String, Object> param);

    List<AppTopArticleVO> appTopArticleList();

    Map<String,Object> getArticlePropertyCount(int userId,int articleId);

    Pagination<FavoriteVO> getFavoriteArticles(Map<String,Object> param);

    void addReadCount(int articleId);

    void addArticleObject(FatArticle article);

    IndexVO index(Map<String, Object> param);
}
