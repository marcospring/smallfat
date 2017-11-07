package com.smt.smallfat.utils.push;

import cn.jpush.api.push.model.PushPayload;

public interface IPush {

    String TITLE = "花之物语";

    boolean push(PushPayload pushPayload);
}
