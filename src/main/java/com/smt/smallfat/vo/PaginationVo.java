package com.smt.smallfat.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xindongwang on 17/3/13.
 */
public class PaginationVo<T> implements Serializable{


    private static final long serialVersionUID = 977808069671875655L;

    private int pageNo = 1;
    private int pageSize = 10;
    private long pageCount = 1L;
    private long recordsTotal = 0L;
    private List<T> data = null;

    public PaginationVo() {
    }

    public PaginationVo(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PaginationVo(List<T> data, int pageNo, int pageSize) {
        this.data = data;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PaginationVo(List<T> data, int pageNo) {
        this.data = data;
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getRecordsTotal() {
        return this.recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.pageCount = recordsTotal > 0L?(recordsTotal % (long)this.pageSize == 0L?recordsTotal / (long)this.pageSize:recordsTotal / (long)this.pageSize + 1L):0L;
        this.recordsTotal = recordsTotal;
    }
}
