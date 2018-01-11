package com.smt.smallfat.vo;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.vo.house.FlowerHouseItemVO;

public class IndexVO {
    Pagination<ArticleVO> articles;
    Pagination<FlowerHouseItemVO> flowers;

    public IndexVO() {
    }

    public IndexVO(Pagination<ArticleVO> articles, Pagination<FlowerHouseItemVO> flowers) {
        this.articles = articles;
        this.flowers = flowers;
    }

    public Pagination<ArticleVO> getArticles() {
        return articles;
    }

    public void setArticles(Pagination<ArticleVO> articles) {
        this.articles = articles;
    }

    public Pagination<FlowerHouseItemVO> getFlowers() {
        return flowers;
    }

    public void setFlowers(Pagination<FlowerHouseItemVO> flowers) {
        this.flowers = flowers;
    }
}
