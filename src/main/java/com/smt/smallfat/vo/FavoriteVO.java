package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.po.FatFavorite;
import com.smt.smallfat.po.FatGoods;

public class FavoriteVO extends FatFavorite{
    private FatArticle article;
    private FatAll all;
    private FatGoods goods;

    public FavoriteVO() {
    }

    public FavoriteVO(FatFavorite favorite,FatArticle article) {
        this.article = article;
        this.setArticleId(favorite.getArticleId());
        this.setUserId(favorite.getUserId());
        this.setCreateTime(favorite.getCreateTime());
        this.setFavoriteType(favorite.getFavoriteType());
    }
    public FavoriteVO(FatFavorite favorite,FatAll all) {
        this.all = all;
        this.setArticleId(favorite.getArticleId());
        this.setUserId(favorite.getUserId());
        this.setCreateTime(favorite.getCreateTime());
        this.setFavoriteType(favorite.getFavoriteType());
    }
    public FavoriteVO(FatFavorite favorite,FatGoods goods) {
        this.goods = goods;
        this.setArticleId(favorite.getArticleId());
        this.setUserId(favorite.getUserId());
        this.setCreateTime(favorite.getCreateTime());
        this.setFavoriteType(favorite.getFavoriteType());
    }

    public FatArticle getArticle() {
        return article;
    }

    public void setArticle(FatArticle article) {
        this.article = article;
    }

    public FatAll getAll() {
        return all;
    }

    public void setAll(FatAll all) {
        this.all = all;
    }
}
