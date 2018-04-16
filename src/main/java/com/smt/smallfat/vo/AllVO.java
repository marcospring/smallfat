package com.smt.smallfat.vo;

public class AllVO {
    private int id;
    private String uuid;
    private String  littleImg = "";
    /**小图标题*/
    private String  littleTitle = "";
    /**收藏1是0否*/
    private Integer  isLove = 0;
    /**XX科*/
    private String  subject = "";
    /**XX属*/
    private String  category = "";
    /**底层大图*/
    private String  bigImg = "";
    /**拉丁名称*/
    private String  latinName = "";
    /**科属描述*/
    private String  subjectDesc = "";
    /**内容简介*/
    private String  description = "";
    /**点赞数*/
    private Integer  clickCount = 0;
    /**分享的url*/
    private String  shareUrl = "";
    /**排序字母组合*/
    private String  orderCombin = "";

    private long favoriteCount;

    public AllVO(int id, String uuid, String littleImg, String littleTitle, Integer isLove, String subject, String category, String bigImg, String latinName, String subjectDesc, String description, Integer clickCount, String shareUrl, String orderCombin) {
        this.id = id;
        this.uuid = uuid;
        this.littleImg = littleImg;
        this.littleTitle = littleTitle;
        this.isLove = isLove;
        this.subject = subject;
        this.category = category;
        this.bigImg = bigImg;
        this.latinName = latinName;
        this.subjectDesc = subjectDesc;
        this.description = description;
        this.clickCount = clickCount;
        this.shareUrl = shareUrl;
        this.orderCombin = orderCombin;
    }

    public AllVO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLittleImg() {
        return littleImg;
    }

    public void setLittleImg(String littleImg) {
        this.littleImg = littleImg;
    }

    public String getLittleTitle() {
        return littleTitle;
    }

    public void setLittleTitle(String littleTitle) {
        this.littleTitle = littleTitle;
    }

    public Integer getIsLove() {
        return isLove;
    }

    public void setIsLove(Integer isLove) {
        this.isLove = isLove;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getSubjectDesc() {
        return subjectDesc;
    }

    public void setSubjectDesc(String subjectDesc) {
        this.subjectDesc = subjectDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getOrderCombin() {
        return orderCombin;
    }

    public void setOrderCombin(String orderCombin) {
        this.orderCombin = orderCombin;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}
