package com.smt.smallfat.utils;

import com.csyy.common.MD5;
import com.csyy.constant.Constants;

public class PassSecret {

    public static String passMd5(String username,String password){
        StringBuilder builder = new StringBuilder();
        builder.append(username).append(Constants.Separator.UPDERLINE).append(password);
        return MD5.md5(builder.toString());
    }
}
