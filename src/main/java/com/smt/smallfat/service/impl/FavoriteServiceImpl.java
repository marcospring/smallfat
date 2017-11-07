package com.smt.smallfat.service.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.Param;
import com.csyy.core.datasource.param.ParamBuilder;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.AllService;
import com.smt.smallfat.service.ArticleService;
import com.smt.smallfat.service.FavoriteService;
import com.smt.smallfat.service.GoodsService;
import com.smt.smallfat.vo.FavoriteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl extends BaseServiceImpl implements FavoriteService {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private AllService allService;
    @Autowired
    private GoodsService goodsService;

    @Override
    public FavoriteVO addFavorite(Map<String, Object> param) {
        int userId = StringDefaultValue.intValue(param.get(FatFavorite.FIELD_USER_ID));
        int articleId = StringDefaultValue.intValue(param.get(FatFavorite.FIELD_ARTICLE_ID));
        int favoriteType = StringDefaultValue.intValue(param.get(FatFavorite.FIELD_FAVORITE_TYPE));
        Param params = ParamBuilder.getInstance().getParam();
        params.add(ParamBuilder.nv(FatFavorite.FIELD_USER_ID, userId));
        params.add(ParamBuilder.nv(FatFavorite.FIELD_ARTICLE_ID, articleId));
        params.add(ParamBuilder.nv(FatFavorite.FIELD_FAVORITE_TYPE, favoriteType));
        FatFavorite favorite = factory.getCacheReadDataSession().querySingleResultByParams(FatFavorite.class, params);
        FavoriteVO vo = new FavoriteVO();
        if (favorite == null) {
            favorite = CommonBeanUtils.transMap2BasePO(param, FatFavorite.class);
            favorite = factory.getCacheWriteDataSession().save(FatFavorite.class, favorite);
            vo = fillFavoriteVO(favorite);
        }
        return vo;
    }

    private FavoriteVO fillFavoriteVO(FatFavorite favorite) {
        int articleId = favorite.getArticleId();
        switch (favorite.getFavoriteType()) {
            case ARTICLE:
                FatArticle article = articleService.getArticleById(articleId);
                return new FavoriteVO(favorite, article);
            case ALL:
                FatAll all = allService.getAllById(articleId);
                return new FavoriteVO(favorite, all);
            case GOODS:
                FatGoods goods = goodsService.getGoodsById(articleId);
                return new FavoriteVO(favorite, goods);
        }
        return new FavoriteVO();
    }

    @Override
    public void deleteFavorite(int id) {
        factory.getCacheWriteDataSession().physicalDelete(FatFavorite.class, id);
    }

    @Override
    public Pagination<FavoriteVO> pageFavorite(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatFavorite> page = queryClassPagination(FatFavorite.class, params, pageNo, pageSize);
        List<FatFavorite> data = page.getData();
        List<FavoriteVO> dataVo = new ArrayList<>(data.size());
        for (FatFavorite favorite : data) {
            FavoriteVO vo = fillFavoriteVO(favorite);
            dataVo.add(vo);
        }
        Pagination<FavoriteVO> pageVO = new Pagination<>(dataVo, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());
        return pageVO;
    }

    @Override
    public FavoriteVO getFavoriteVOById(int id) {
        FatFavorite favorite = getFavoriteById(id);
        return fillFavoriteVO(favorite);
    }

    @Override
    public FavoriteVO getFavoriteVOByUUID(String uuid) {
        FatFavorite favorite = getFavoriteByUUID(uuid);
        return fillFavoriteVO(favorite);
    }

    @Override
    public FatFavorite getFavoriteById(int id) {
        FatFavorite favorite = factory.getCacheReadDataSession().querySingleResultById(FatFavorite.class, id);
        if (favorite == null)
            throw new CommonException(ResultConstant.Favorite.FAVORITE_IS_NULL);
        return favorite;
    }

    @Override
    public FatFavorite getFavoriteByUUID(String uuid) {
        FatFavorite favorite = factory.getCacheReadDataSession().querySingleResultByUUID(FatFavorite.class, uuid);
        if (favorite == null)
            throw new CommonException(ResultConstant.Favorite.FAVORITE_IS_NULL);
        return favorite;
    }

    @Override
    public List<FavoriteVO> getFavoritesByParams(Map<String, Object> param) {
        Param params = ParamBuilder.getInstance().getParam().add(param);
        List<FatFavorite> listFavorite = factory.getCacheReadDataSession().queryListResult(FatFavorite.class, params);
        List<FavoriteVO> listVO = new ArrayList<>(listFavorite.size());
        for (FatFavorite favorite : listFavorite) {
            FavoriteVO vo = fillFavoriteVO(favorite);
            listVO.add(vo);
        }
        return listVO;
    }

    @Override
    public FatFavorite getIsFavorite(int userId, int articleId, int favoriteType) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatFavorite.FIELD_USER_ID, userId))
                .add(ParamBuilder.nv(FatFavorite.FIELD_ARTICLE_ID, articleId)).add(ParamBuilder.nv(FatFavorite
                        .FIELD_FAVORITE_TYPE, favoriteType));
        FatFavorite favorite = factory.getCacheReadDataSession().querySingleResultByParams(FatFavorite.class, param);
        return favorite;
    }

    @Override
    public long getFavoriteCount(int articleId, int favoriteType) {
        Param param = ParamBuilder.getInstance().getParam()
                .add(ParamBuilder.nv(FatFavorite.FIELD_ARTICLE_ID, articleId)).add(ParamBuilder.nv(FatFavorite
                        .FIELD_FAVORITE_TYPE, favoriteType));
        long count = factory.getCacheReadDataSession().queryListResultCount(FatFavorite.class, param);
        return count;
    }

    @Override
    public void operateFavorite(Map<String, Object> param) {
        int operatorType = StringDefaultValue.intValue(param.get("operateType"));
        switch (operatorType) {
            case FAVORITE:
                addFavorite(param);
                break;
            case CANCEL_FAVORITE:
                Param params = ParamBuilder.getInstance().getParam().add(param);
                FatFavorite favorite = factory.getCacheReadDataSession().querySingleResultByParams(FatFavorite.class,
                        params);
                if (favorite == null)
                    throw new CommonException(ResultConstant.Favorite.FAVORITE_IS_NULL);
                deleteFavorite(favorite.getId());
                break;
        }
    }
}
