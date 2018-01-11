package com.smt.smallfat.service.share;

import java.util.Map;

public interface ShareService {
    String APPID = "wx8145a6e95609872a";
    String SECRET = "133a34d86839b1f05e7a7b72cea2e006";
    String TOKEN_KEY = "shareToken";
    String TICKET_KEY = "shareTicket";
    String KEY_NONCESTR = "noncestr";
    String KEY_TIMESTAMP = "timestamp";
    String KEY_SIGNATURE = "signature";
    int TIME_OUT = 7000;


    Map<String, String> getShareConfig(String url);
}
