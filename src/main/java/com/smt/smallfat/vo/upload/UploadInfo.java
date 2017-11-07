package com.smt.smallfat.vo.upload;

/**
 * Created by zhangbin on 16/5/19.
 */
public class UploadInfo {

    //返回上传状态
    private String code;

    //消息
    private String msg;

    //成功的个数
    private Integer successNum;

    //错误的个数
    private Integer errorNum;

    //错误的文件名数组
    private String[] errorFileNames;

    //成功的文件uuid数组
    private String[] successFileUUID;

    //成功文件的url地址数组
    private String[] url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String[] getErrorFileNames() {
        return errorFileNames;
    }

    public void setErrorFileNames(String[] errorFileNames) {
        this.errorFileNames = errorFileNames;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getSuccessNum() {
        return successNum;
    }

    public String[] getSuccessFileUUID() {
        return successFileUUID;
    }

    public void setSuccessFileUUID(String[] successFileUUID) {
        this.successFileUUID = successFileUUID;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
    }
}
