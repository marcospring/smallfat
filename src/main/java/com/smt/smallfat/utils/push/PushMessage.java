package com.smt.smallfat.utils.push;

import com.smt.smallfat.utils.push.pushenum.PlatForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PushMessage {
    //平台
    private PlatForm platForm;
    //推送列表
    private List<String> alias;
    //内容
    private String content;
    //标题
    private String title;
    //数据
    private Map<String, String> extras;

    Logger logger = LoggerFactory.getLogger(getClass());


    public static PushMessage get() {
        return new PushMessage();
    }

    private PushMessage() {
    }

    public PushMessage platform(PlatForm platForm) {
        this.platForm = platForm;
        return this;
    }

    public PushMessage alias(List<String> alias) {
        this.alias = alias;
        return this;
    }

    public PushMessage addAlias(String alia) {
        if (this.alias == null)
            alias = new ArrayList<>();
        alias.add(alia);
        return this;
    }

    public PushMessage content(String content) {
        this.content = content;
        return this;
    }

    public PushMessage title(String title) {
        this.title = title;
        return this;
    }

    public PushMessage extras(Map<String, String> extras) {
        this.extras = extras;
        return this;
    }

    public PushMessage addExtras(String key,String value) {
        if(this.extras == null)
            this.extras = new HashMap<>();
        extras.put(key,value);
        return this;
    }

    public PlatForm getPlatForm() {
        return platForm;
    }

    public String [] getAlias() {
        String [] aliasStringArr = new String[alias.size()];
        logger.info("message:alias=======>{}",alias);
        if(alias != null){
            for(int i =0 ;i<alias.size();i++){
                aliasStringArr[i] = alias.get(i);
            }
            logger.info("message:aliasStringArr=======>{}",aliasStringArr);
            return aliasStringArr;
        }else{
            logger.info("message:null");
            return null;
        }
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Map<String, String> getExtras() {
        return extras;
    }

    public static String buildMessage(String result,String ... args ){
        return MessageFormat.format(result, args);
    }

    public static void main(String[] args) {
        PushMessage m = PushMessage.get();
        m.addAlias("asdfasdfasdf");
        System.out.println(m.getAlias()[0]);
        System.out.println(m.getAlias()[0]);
    }
}
