package com.smt.smallfat.exception;

import com.csyy.core.exception.BusinessException;

public class InfoEmptyException extends BusinessException{
    public InfoEmptyException() {
        super.exceptionKey = "INF_EMPTY";
    }
}
