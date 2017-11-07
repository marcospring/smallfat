package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.po.FatFavorite;

public class ArticleVO{
    private String articleTypeName;
    private int isFavorite;
    private FatArticle article;
    private long commentCount;
    private long favoriteCount;
    private String shareUrl;

    public ArticleVO() {
    }

    public ArticleVO(FatArticle article,String articleTypeName,long commentCount,long favoriteCount) {
        this.articleTypeName = articleTypeName;
        this.article = article;
        this.commentCount = commentCount;
        this.favoriteCount = favoriteCount;
    }

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public FatArticle getArticle() {
        return article;
    }

    public void setArticle(FatArticle article) {
        this.article = article;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
