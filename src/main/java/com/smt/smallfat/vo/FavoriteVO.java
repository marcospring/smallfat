package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatAll;
import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.po.FatFavorite;
import com.smt.smallfat.po.FatGoods;

public class FavoriteVO extends FatFavorite {
    private FatArticle article;
    private String articleTypeName;
    private FatAll all;
    private FatGoods goods;
    private long commentCount;

    public FavoriteVO() {
    }

    public FavoriteVO(FatFavorite favorite, FatArticle article, long commentCount,String articleTypeName) {
        this.article = article;
        this.setArticleId(favorite.getArticleId());
        this.setUserId(favorite.getUserId());
        this.setCreateTime(favorite.getCreateTime());
        this.setFavoriteType(favorite.getFavoriteType());
        this.commentCount = commentCount;
        this.articleTypeName = articleTypeName;
    }

//    public FavoriteVO(FatFavorite favorite, FatArticle article) {
//        this.article = article;
//        this.setArticleId(favorite.getArticleId());
//        this.setUserId(favorite.getUserId());
//        this.setCreateTime(favorite.getCreateTime());
//        this.setFavoriteType(favorite.getFavoriteType());
//    }

    public FavoriteVO(FatFavorite favorite, FatAll all) {
        this.all = all;
        this.setArticleId(favorite.getArticleId());
        this.setUserId(favorite.getUserId());
        this.setCreateTime(favorite.getCreateTime());
        this.setFavoriteType(favorite.getFavoriteType());
    }

    public FavoriteVO(FatFavorite favorite, FatGoods goods) {
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

    public FatGoods getGoods() {
        return goods;
    }

    public void setGoods(FatGoods goods) {
        this.goods = goods;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }
}
