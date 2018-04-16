package com.smt.smallfat.service.spider.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.csyy.constant.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.exception.SpiderFileNotExistsException;
import com.smt.smallfat.po.FatSpider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SpiderTool {

    private Gson gson = new Gson();
    Logger logger = LoggerFactory.getLogger(getClass());

    public String fileReader(InputStream in) {
        String s = "";
        String json = "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            while ((s = reader.readLine()) != null) {
                if (!Constant.WrapperExtend.EMPTY.equals(s)
                        && !s.contains("Content")
                        && !s.contains("WebKit")) {
                    logger.info(s);
                    json += s;
                }
            }
            return json;
        } catch (FileNotFoundException e) {
            throw new SpiderFileNotExistsException("");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    public List<FatSpider> fromJsonToList(String json) {
        List<FatSpider> result = new ArrayList<>();
        List<MessageObj> list = gson.fromJson(json, new TypeToken<ArrayList<MessageObj>>() {
        }.getType());
        for (MessageObj o : list) {
            FatSpider spider = FatSpider.getInstance(FatSpider.class);
            spider.setContent(o.getContent());
            spider.setCover(o.getCover());
            spider.setTag(o.getTag());
            spider.setTitle(o.getTitle());
            spider.setSpiderUrl(o.getUrl());
            result.add(spider);
        }
        return result;
    }

}
