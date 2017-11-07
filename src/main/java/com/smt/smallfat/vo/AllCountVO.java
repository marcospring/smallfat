package com.smt.smallfat.vo;


import java.util.List;

public class AllCountVO {
    private String code;
    private String name;
    private int plantCount;
    private int type;
    private List<AllCountVO> sons;
    private String parentCode;
    public static int SUBJECT =1;
    public static int CATEGORY = 2;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public void setPlantCount(int plantCount) {
        this.plantCount = plantCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<AllCountVO> getSons() {
        return sons;
    }

    public void setSons(List<AllCountVO> sons) {
        this.sons = sons;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
