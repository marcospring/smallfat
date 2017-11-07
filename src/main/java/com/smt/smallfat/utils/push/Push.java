package com.smt.smallfat.utils.push;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Push implements IPush{
    @Autowired
    private JPushClient jpushClient;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean push(PushPayload pushPayload) {
        try {
            logger.info("==========>jpushClient:{}",jpushClient);
            PushResult result = jpushClient.sendPush(pushPayload);
            logger.info("Got result - " + result);
            return true;
        } catch (APIConnectionException e) {
            // Connection error, should retry later
            logger.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            logger.error("Should review the error, and fix the request", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
        }
        return false;
    }
}
