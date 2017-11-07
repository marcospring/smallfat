package com.smt.smallfat.exception;

import com.smt.smallfat.po.FileStorage;

/**
 * Created by zhangbin on 2016/11/8.
 */
public class ImgHandleResultException extends RuntimeException {

    //原图对象
    private FileStorage info;

    //原图的流
    private byte[] stream;

    //是否存储
    private boolean hasStoraged;

    //是否有缩略图
    private boolean hasThumbnail;

    public boolean isHasStoraged() {
        return hasStoraged;
    }

    public void setHasStoraged(boolean hasStoraged) {
        this.hasStoraged = hasStoraged;
    }

    public boolean isHasThumbnail() {
        return hasThumbnail;
    }

    public void setHasThumbnail(boolean hasThumbnail) {
        this.hasThumbnail = hasThumbnail;
    }

    public FileStorage getInfo() {
        return info;
    }

    public void setInfo(FileStorage info) {
        this.info = info;
    }

    public byte[] getStream() {
        return stream;
    }

    public void setStream(byte[] stream) {
        this.stream = stream;
    }
}
