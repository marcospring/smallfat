package com.smt.smallfat.vo.house;

import com.smt.smallfat.po.FatSucculentCircle;
import com.smt.smallfat.po.FatSucculentImage;

public class CircleCommentVO {
    private FatSucculentCircle circle;
    private FatSucculentImage firstImage;

    public CircleCommentVO() {
    }

    public CircleCommentVO(FatSucculentCircle circle, FatSucculentImage firstImage) {
        this.circle = circle;
        this.firstImage = firstImage;
    }

    public FatSucculentCircle getCircle() {
        return circle;
    }

    public void setCircle(FatSucculentCircle circle) {
        this.circle = circle;
    }

    public FatSucculentImage getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(FatSucculentImage firstImage) {
        this.firstImage = firstImage;
    }
}
