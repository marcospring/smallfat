package com.smt.smallfat.vo;


import com.csyy.core.obj.Pagination;

public class AppTopArticleVO {
    private String typeId;
    private String typeName;
    private Pagination<ArticleVO> articleList;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Pagination<ArticleVO> getArticleList() {
        return articleList;
    }

    public void setArticleList(Pagination<ArticleVO> articleList) {
        this.articleList = articleList;
    }
}
