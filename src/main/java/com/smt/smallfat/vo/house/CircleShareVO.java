package com.smt.smallfat.vo.house;

import com.smt.smallfat.po.FatSucculentCircle;
import com.smt.smallfat.po.FatSucculentImage;

import java.util.List;

public class CircleShareVO {
    private FlowerHouseItemVO item;
    private List<CircleVO> list;

    public FlowerHouseItemVO getItem() {
        return item;
    }

    public void setItem(FlowerHouseItemVO item) {
        this.item = item;
    }

    public List<CircleVO> getList() {
        return list;
    }

    public void setList(List<CircleVO> list) {
        this.list = list;
    }

    public class CircleVO {
        private FatSucculentCircle circle;
        private FatSucculentImage firstImage;

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
}

