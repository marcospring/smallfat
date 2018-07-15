package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.csyy.core.utils.SQLUtil;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.po.FatComment;
import com.smt.smallfat.po.FatFavorite;
import com.smt.smallfat.service.base.ArticleService;
import com.smt.smallfat.service.base.CommentService;
import com.smt.smallfat.service.base.FavoriteService;
import com.smt.smallfat.service.house.CircleService;
import com.smt.smallfat.service.system.SysDicItemService;
import com.smt.smallfat.vo.*;
import com.smt.smallfat.vo.house.FlowerHouseItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArticleServiceImpl extends BaseServiceImpl implements ArticleService {

    @Autowired
    private SysDicItemService itemService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CircleService circleService;

    @Override
    public ArticleVO addArticle(Map<String, Object> param) {
        String title = StringDefaultValue.StringValue(param.get(FatArticle.FIELD_TITLE));
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatArticle.FIELD_TITLE, title));
        List<FatArticle> list = factory.getCacheReadDataSession().queryListResult(FatArticle.class, params);
        if (list.size() > 0)
            throw new CommonException(ResultConstant.Article.ARTICLE_IS_NOT_NULL);
        FatArticle article = CommonBeanUtils.transMap2BasePO(param, FatArticle.class);
        article = factory.getCacheWriteDataSession().save(FatArticle.class, article);
        return fillArticleVO(article);
    }

    private ArticleVO fillArticleVO(FatArticle article) {
        SysDicItemVo item = itemService.getDicItemById(article.getArticleType());
        long commentCount = commentService.getArticleCommentCount(article.getId());
        long favoriteCount = favoriteService.getFavoriteCount(article.getId(), ARTICLE_FAVORITE_TYPE);

        return new ArticleVO(article, item.getDicItemName(), commentCount, favoriteCount);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteArticle(int id) {
        factory.getCacheWriteDataSession().physicalDelete(FatArticle.class, id);
        CustomSQL where = SQLCreator.where().cloumn(FatComment.FIELD_ARTICLE_ID).operator(ESQLOperator.EQ).v(id);
        factory.getCacheWriteDataSession().physicalWhereDelete(FatComment.class, where);
    }

    @Override
    public void deleteArticle(String uuid) {
        FatArticle article = getArticleByUUID(uuid);
        deleteArticle(article.getId());
    }

    @Override
    public ArticleVO updateArticle(Map<String, Object> param) {
        int id = StringDefaultValue.intValue(param.get(FatArticle.FIELD_ID));
        FatArticle article = getArticleById(id);
        article = CommonBeanUtils.transMap2BasePO(param, article);
        factory.getCacheWriteDataSession().update(FatArticle.class, article);
        return fillArticleVO(article);
    }

    @Override
    public ArticleVO getArticleVOById(int id) {
        FatArticle article = getArticleById(id);
        return fillArticleVO(article);
    }

    @Override
    public ArticleVO getArticleVOByUUID(String uuid) {
        FatArticle article = getArticleByUUID(uuid);
        return fillArticleVO(article);
    }

    @Override
    public Pagination<ArticleVO> getArticlesByParam(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Param params = ParamBuilder.getInstance().getParam();
        params.add(param);
        params.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_COLUMN, FatArticle.FIELD_UPDATE_TIME));
        params.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatArticle> page = queryClassPagination(FatArticle.class, params, pageNo, pageSize);

        Pagination<ArticleVO> pageVO = fillArticleVOPagination(page);
        return pageVO;
    }

    private Pagination<ArticleVO> fillArticleVOPagination(Pagination<FatArticle> page) {
        List<FatArticle> data = page.getData();
        List<ArticleVO> datavo = new ArrayList<>(data.size());
        for (FatArticle article : data) {
            ArticleVO vo = fillArticleVO(article);
            datavo.add(vo);
        }
        Pagination<ArticleVO> pageVO = new Pagination<>(datavo, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());
        return pageVO;
    }

    @Override
    public void addToAppIndex(int id) {
        FatArticle article = getArticleById(id);
        article.setIsApp(Constant.APP_ARTICLE);
        article.setUpdateTime(new Date());
        factory.getCacheWriteDataSession().update(FatArticle.class, article);
    }

    @Override
    public void orderArticle(int articleId) {
        FatArticle article = getArticleById(articleId);
        article.setOrderBy(article.getOrderBy() + 1);
        article.setUpdateTime(new Date());
        factory.getCacheWriteDataSession().update(FatArticle.class, article);
    }

    @Override
    public List<ArticleVO> topThreeArticle(int articleType) {
        return null;
    }

    @Override
    public FatArticle getArticleById(int id) {
        FatArticle article = factory.getCacheReadDataSession().querySingleResultById(FatArticle.class, id);
        if (article == null)
            throw new CommonException(ResultConstant.Article.ARTICLE_IS_NULL);
        return article;
    }

    @Override
    public FatArticle getArticleByUUID(String uuid) {
        FatArticle article = factory.getCacheReadDataSession().querySingleResultByUUID(FatArticle.class, uuid);
        if (article == null)
            throw new CommonException(ResultConstant.Article.ARTICLE_IS_NULL);
        return article;
    }

    @Override
    public Pagination<ArticleVO> appArticlePage(Map<String, Object> param) {
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        param.put(FatArticle.FIELD_IS_APP, 1);
        Pagination<ArticleVO> page = getArticlesByParam(param);
        List<ArticleVO> data = page.getData();
        List<ArticleVO> datavo = new ArrayList<>(data.size());
        for (ArticleVO article : data) {
            FatFavorite favorite = favoriteService.getIsFavorite(userId, article.getArticle().getId(), ARTICLE_FAVORITE_TYPE);
            if (favorite == null)
                article.setIsFavorite(FavoriteService.NOT_FAVORITE);
            else
                article.setIsFavorite(FavoriteService.IS_FAVORITE);
            datavo.add(article);
        }
        Pagination<ArticleVO> pageVO = new Pagination<>(datavo, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());
        return pageVO;
    }

    @Override
    public List<AppTopArticleVO> appTopArticleList() {
        List<SysDicItemVo> typeList = itemService.getDicItemByDicId(ArticleService.ARTICLE_TYPE_DIC_ID);
        Map<String, Object> param = new HashMap<>(3);
        List<AppTopArticleVO> result = new ArrayList<>(typeList.size());
        for (SysDicItemVo itemVo : typeList) {
            AppTopArticleVO vo = new AppTopArticleVO();
            vo.setTypeId(itemVo.getDicItemValue());
            vo.setTypeName(itemVo.getDicItemName());
            if (param.size() > 0)
                param.clear();
            param.put(Constant.PAGE_NO, 1);
            param.put(Constant.PAGE_SIZE, 3);
            param.put(FatArticle.FIELD_ARTICLE_TYPE, vo.getTypeId());
            param.put(FatArticle.FIELD_IS_APP, 1);
            param.put(Constant.SQLConstants.ORDER_COLUMN, FatArticle.FIELD_UPDATE_TIME);
            param.put(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC);
            Pagination<ArticleVO> page = getArticlesByParam(param);
            vo.setArticleList(page);
            result.add(vo);
        }

        return result;
    }

    @Override
    public Map<String, Object> getArticlePropertyCount(int userId, int articleId) {
        FatArticle article = getArticleById(articleId);
        long commentCount = commentService.getArticleCommentCount(articleId);
        long favoriteCount = favoriteService.getFavoriteCount(articleId, ARTICLE_FAVORITE_TYPE);
        int readCount = article.getReadCount();
        int isFavorite = FavoriteService.NOT_FAVORITE;
        if (userId != 0) {
            FatFavorite favorite = favoriteService.getIsFavorite(userId, articleId, ARTICLE_FAVORITE_TYPE);
            if (favorite != null)
                isFavorite = FavoriteService.IS_FAVORITE;
        }

        Map<String, Object> result = new HashMap<>(4);
        result.put("commentCount", commentCount);
        result.put("favoriteCount", favoriteCount);
        result.put("isFavorite", isFavorite);
        result.put("readCount", readCount);
        return result;
    }

    @Override
    public Pagination<FavoriteVO> getFavoriteArticles(Map<String, Object> param) {
        Pagination<FavoriteVO> favorites = favoriteService.pageFavorite(param);
        return favorites;
    }

    @Override
    public void addReadCount(int articleId) {
        FatArticle article = getArticleById(articleId);
        article.setReadCount(article.getReadCount() + 1);
        factory.getCacheWriteDataSession().update(FatArticle.class, article);
    }

    @Override
    public void addArticleObject(FatArticle article) {
        factory.getCacheWriteDataSession().save(FatArticle.class, article);
    }

    @Override
    public IndexVO index(Map<String, Object> param) {
        Pagination<ArticleVO> pageVO = appArticlePage(param);
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
//        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int userId = StringDefaultValue.intValue(param.get(Constant.USER_ID));
        Pagination<FlowerHouseItemVO> page = circleService.indexCircleList(userId, pageNo, 5);
        IndexVO vo = new IndexVO(pageVO, page);
        return vo;
    }

    @Override
    public Pagination<ArticleVO> search(String title, int pageNo, int pageSize) {
        CustomSQL where = SQLCreator.where();
        where.cloumn(FatArticle.FIELD_TITLE).operator(ESQLOperator.LIKE).v(SQLUtil.likeValue(title, SQLUtil
                .ALL)).operator(ESQLOperator.ORDER_BY).cloumn(FatArticle.FIELD_UPDATE_TIME).operator(ESQLOperator.DESC);
        Pagination<FatArticle> page = queryClassPagination(FatArticle.class, where, pageNo, pageSize);
        Pagination<ArticleVO> pageVO = fillArticleVOPagination(page);
        return pageVO;
    }
}
