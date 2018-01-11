package com.smt.smallfat.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.smt.smallfat.service.pay.PayConstant;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpXmlClient {


    public static String post(String url, Map<String, String> params) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;
        HttpPost post = postForm(url, params);
        body = invoke(httpclient, post);
        httpclient.getConnectionManager().shutdown();
        return body;
    }

    public static String get(String url) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String body = null;
        HttpGet get = new HttpGet(url);
        body = invoke(httpclient, get);
        httpclient.getConnectionManager().shutdown();
        return body;
    }

    private static String invoke(DefaultHttpClient httpclient,
                                 HttpUriRequest httpost) {
        HttpResponse response = sendRequest(httpclient, httpost);
        String body = paseResponse(response);
        return body;
    }

    private static String paseResponse(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String charset = EntityUtils.getContentCharSet(entity);
        String body = null;
        try {
            body = EntityUtils.toString(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

    private static HttpResponse sendRequest(DefaultHttpClient httpclient,
                                            HttpUriRequest httpost) {
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private static HttpPost postForm(String url, Map<String, String> params) {

        HttpPost httpost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return httpost;
    }

    public static void main(String[] args) {
//        //获取access_token
//        String xml = HttpXmlClient.get("https://api.weixin.qq" +
//                ".com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET);
//        System.out.println(xml);
//        JSONObject jsonMap = JSONObject.fromObject(xml);
//        Map<String, String> map = new HashMap();
//        Iterator<String> it = jsonMap.keys();
//        while(it.hasNext()) {
//            String key = (String) it.next();
//            String u = jsonMap.get(key).toString();
//            map.put(key, u);
//        }
//        String access_token = map.get("access_token");
//        System.out.println("access_token=" + access_token);
//
//        //获取ticket
//        xml = HttpXmlClient.get("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi");
//        jsonMap = JSONObject.fromObject(xml);
//        map = new HashMap();
//        it = jsonMap.keys();
//        while(it.hasNext()) {
//            String key = (String) it.next();
//            String u = jsonMap.get(key).toString();
//            map.put(key, u);
//        }
//        String jsapi_ticket = map.get("ticket");
//        System.out.println("jsapi_ticket=" + jsapi_ticket);
//
//        //获取签名signature
//        String noncestr = UUID.randomUUID().toString();
//        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
//        String url="http://mp.weixin.qq.com";
//        String str = "jsapi_ticket=" + jsapi_ticket +
//                "&noncestr=" + noncestr +
//                "×tamp=" + timestamp +
//                "&url=" + url;
//        //sha1加密
//        String signature = PassSecret.SHA1(str);
//        System.out.println("noncestr=" + noncestr);
//        System.out.println("timestamp=" + timestamp);
//        System.out.println("signature=" + signature);
//        //最终获得调用微信js接口验证需要的三个参数noncestr、timestamp、signature
    }


}
