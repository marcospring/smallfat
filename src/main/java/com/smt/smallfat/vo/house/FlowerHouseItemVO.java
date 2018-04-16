package com.smt.smallfat.vo.house;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatSucculentImage;
import com.smt.smallfat.vo.FatCommentVO;

import java.util.Date;
import java.util.List;

public class FlowerHouseItemVO {
    private FatCustomer author;
    private List<FatSucculentImage> imgs;
    private Date createTime;
    private List<FatCustomer> praises;
    private long praiseCount;
    private String content;
    private int id;
    private long commentCount;
    private Pagination<FatCommentVO> commentPagination;
    private int imageCount;
    private int isPraise;
    private String shareUrl;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public FatCustomer getAuthor() {
        return author;
    }

    public void setAuthor(FatCustomer author) {
        this.author = author;
    }

    public List<FatSucculentImage> getImgs() {
        return imgs;
    }

    public void setImgs(List<FatSucculentImage> imgs) {
        this.imgs = imgs;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<FatCustomer> getPraises() {
        return praises;
    }

    public void setPraises(List<FatCustomer> praises) {
        this.praises = praises;
    }

    public long getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(long praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public Pagination<FatCommentVO> getCommentPagination() {
        return commentPagination;
    }

    public void setCommentPagination(Pagination<FatCommentVO> commentPagination) {
        this.commentPagination = commentPagination;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public int getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }
}
