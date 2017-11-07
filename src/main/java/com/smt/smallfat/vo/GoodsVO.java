package com.smt.smallfat.vo;

import com.smt.smallfat.po.FatGoods;
import com.smt.smallfat.po.FatGoodsDetail;
import com.smt.smallfat.po.FatGoodsResource;

import java.util.List;

public class GoodsVO {
    private int id;
    private String title;
    private String memo;
    private String summary;
    private String expCompany;
    private String flag;
    private String flagKeys;
    private List<FatGoodsResource> resource;
    private List<FatGoodsDetail> details;
    private String priceArea;
    private String shareUrl;

    public GoodsVO() {
    }

    public GoodsVO(FatGoods goods, List<FatGoodsResource> resource, List<FatGoodsDetail> details) {
        this.id =  goods.getId();
        this.title = goods.getTitle();
        this.memo = goods.getMemo();
        this.summary = goods.getSummary();
        this.flag = goods.getFlag();
        this.resource = resource;
        this.details = details;
    }


    public List<FatGoodsResource> getResource() {
        return resource;
    }

    public void setResource(List<FatGoodsResource> resource) {
        this.resource = resource;
    }

    public List<FatGoodsDetail> getDetails() {
        return details;
    }

    public void setDetails(List<FatGoodsDetail> details) {
        this.details = details;
    }

    public String getPriceArea() {
        return priceArea;
    }

    public void setPriceArea(String priceArea) {
        this.priceArea = priceArea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getExpCompany() {
        return expCompany;
    }

    public void setExpCompany(String expCompany) {
        this.expCompany = expCompany;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlagKeys() {
        return flagKeys;
    }

    public void setFlagKeys(String flagKeys) {
        this.flagKeys = flagKeys;
    }


    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}