package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatArticle;
import com.smt.smallfat.po.FatComment;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatSucculentCircle;

public class FatCommentVO {
    private FatCustomer fromUser;
    private FatCustomer toUser;
    private FatComment comment;
    private FatArticle article;
    private FatSucculentCircle circle;

    public FatCommentVO() {
    }

    public FatCommentVO(FatComment comment, FatArticle article, FatCustomer fromUser, FatCustomer toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.article = article;
        this.comment = comment;
    }

    public FatCommentVO(FatComment comment, FatSucculentCircle circle, FatCustomer fromUser, FatCustomer toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.circle = circle;
        this.comment = comment;
    }

    public FatCustomer getFromUser() {
        return fromUser;
    }

    public void setFromUser(FatCustomer fromUser) {
        this.fromUser = fromUser;
    }

    public FatCustomer getToUser() {
        return toUser;
    }

    public void setToUser(FatCustomer toUser) {
        this.toUser = toUser;
    }

    public FatArticle getArticle() {
        return article;
    }

    public void setArticle(FatArticle article) {
        this.article = article;
    }

    public FatComment getComment() {
        return comment;
    }

    public void setComment(FatComment comment) {
        this.comment = comment;
    }

    public FatSucculentCircle getCircle() {
        return circle;
    }

    public void setCircle(FatSucculentCircle circle) {
        this.circle = circle;
    }
}
