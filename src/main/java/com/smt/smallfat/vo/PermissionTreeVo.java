package com.smt.smallfat.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xindongwang on 17/3/12.
 */
public class PermissionTreeVo implements Serializable {


    private static final long serialVersionUID = -1552847687759660785L;

    public enum NODE_STATE {
        CLOSED("closed"), OPEN("open");
        private String state;

        NODE_STATE(String state) {
            this.state = state;
        }

        public String state() {
            return state;
        }
    }

    private int id;
    private String permissionName;
    private String permissionUrl;
    private int permissionType;
    private String permissionCode;
    private String remark;
    private String checkbox;
    private String state = NODE_STATE.OPEN.state();
    private List<PermissionTreeVo> children;
    private boolean isCheck = false;


    public PermissionTreeVo() {
    }

    public PermissionTreeVo(int id, String permissionName) {
        this.id = id;
        this.permissionName = permissionName;
    }

    public PermissionTreeVo(int id, String permissionName, String permissionUrl, int permissionType, String remark, String permissionCode) {
        this.id = id;
        this.permissionName = permissionName;
        this.permissionUrl = permissionUrl;
        this.permissionType = permissionType;
        this.permissionCode = permissionCode;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<PermissionTreeVo> getChildren() {

        if(children == null)
            children = new ArrayList<>();
        return children;
    }

    public void setChildren(List<PermissionTreeVo> children) {
        this.children = children;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
}
