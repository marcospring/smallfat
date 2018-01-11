package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatFavorite;
import com.smt.smallfat.vo.FavoriteVO;

import java.util.List;
import java.util.Map;

public interface FavoriteService {
    /**
     * 资源没有收藏
     */
    int NOT_FAVORITE = 0;
    /**
     * 资源已经收藏
     */
    int IS_FAVORITE =1;

    /**
     * 收藏
     */
    int FAVORITE = 1;
    /**
     * 取消收藏
     */
    int CANCEL_FAVORITE = 0;


    /**
     * 文章，精选
     */
    int ARTICLE = 2;
    /**
     * 大全
     */
    int ALL=1;
    /**
     * 商品，推荐
     */
    int GOODS=3;


    FavoriteVO addFavorite(Map<String, Object> param);

    void deleteFavorite(int id);

    Pagination<FavoriteVO> pageFavorite(Map<String, Object> param);

    FavoriteVO getFavoriteVOById(int id);

    FavoriteVO getFavoriteVOByUUID(String uuid);

    FatFavorite getFavoriteById(int id);

    FatFavorite getFavoriteByUUID(String uuid);

    List<FavoriteVO> getFavoritesByParams(Map<String, Object> param);

    FatFavorite getIsFavorite(int userId,int articleId,int favoriteType);

    long getFavoriteCount(int articleId,int favoriteType);

    void operateFavorite(Map<String, Object> param);
}
