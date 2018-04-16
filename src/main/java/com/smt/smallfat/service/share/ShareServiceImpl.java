package com.smt.smallfat.service.share;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.smt.smallfat.utils.HttpXmlClient;
import com.smt.smallfat.utils.PassSecret;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Service
public class ShareServiceImpl extends BaseServiceImpl implements ShareService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Map<String, String> getShareConfig(String url) {
        Map<String, String> result = new HashMap<>(3);
        String token = getToken();
        String ticket = getTicket(token);
        String nonce = getNonceStr();
        String timestamp = getTimestamp();
        result.put(KEY_TIMESTAMP, timestamp);
        result.put(KEY_SIGNATURE, getSignature(url, ticket, nonce, timestamp));
        result.put(KEY_NONCESTR, nonce);
        return result;
    }

    private String getToken() {
        String token = factory.getRedisSessionFactory().getSession().getData(TOKEN_KEY);
        if (!StringDefaultValue.isEmpty(token))
            return token;
        String xml = HttpXmlClient.get("https://api.weixin.qq" +
                ".com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + SECRET);
        logger.info("获取token的xml：{}", xml);
        JSONObject jsonMap = JSONObject.fromObject(xml);
        Map<String, String> map = new HashMap();
        Iterator<String> it = jsonMap.keys();
        while (it.hasNext()) {
            String key = it.next();
            String u = jsonMap.get(key).toString();
            map.put(key, u);
        }
        String tokenKey = map.get("access_token");
        factory.getRedisSessionFactory().getSession().setData(TOKEN_KEY, tokenKey, TIME_OUT);
        return tokenKey;
    }

    private String getTicket(String token) {
        String ticket = factory.getRedisSessionFactory().getSession().getData(TICKET_KEY);
        if (!StringDefaultValue.isEmpty(ticket))
            return ticket;
        String xml = HttpXmlClient.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi");
        logger.info("获取ticket的xml：{}", xml);
        JSONObject jsonMap = JSONObject.fromObject(xml);
        Map<String, String> map = new HashMap();
        Iterator<String> it = jsonMap.keys();
        while (it.hasNext()) {
            String key = it.next();
            String u = jsonMap.get(key).toString();
            map.put(key, u);
        }
        String ticketKey = map.get("ticket");
        factory.getRedisSessionFactory().getSession().setData(TICKET_KEY, ticketKey, TIME_OUT);
        return ticketKey;
    }

    private String getNonceStr() {
        return UUID.randomUUID().toString();
    }

    private String getTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private String getSignature(String url, String ticket, String nonceStr, String timestamp) {
        //获取签名signature

        String str = "jsapi_ticket=" + ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        logger.info("str={}", str);
        //sha1加密
        return PassSecret.SHA1(str);
    }

}
