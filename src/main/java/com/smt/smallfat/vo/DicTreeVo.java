package com.smt.smallfat.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xindongwang on 17/3/12.
 */
public class DicTreeVo implements Serializable {


    private static final long serialVersionUID = 4480898739987017498L;

    private int id;
    private String dicCode;
    private String dicName;
    private List<DicTreeVo> children;

    public DicTreeVo() {
    }

    public DicTreeVo(int id, String dicCode, String dicName) {
        this.id = id;
        this.dicCode = dicCode;
        this.dicName = dicName;
    }

    public DicTreeVo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public List<DicTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<DicTreeVo> children) {
        this.children = children;
    }
}
