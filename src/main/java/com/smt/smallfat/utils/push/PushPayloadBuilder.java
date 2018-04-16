package com.smt.smallfat.utils.push;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PushPayloadBuilder {

    private static PushPayloadBuilder instance = null;
    private Logger logger = LoggerFactory.getLogger(getClass());

    private PushPayloadBuilder() {
    }

    public static PushPayloadBuilder newInstance() {
        if (instance == null)
            instance = new PushPayloadBuilder();
        return instance;
    }

    public PushPayload build(PushMessage message) {
        PushPayload.Builder builder = PushPayload.newBuilder();
        return buildLoadBuilder(builder, message);
    }

    private PushPayload buildLoadBuilder(PushPayload.Builder builder, PushMessage message) {
        if (message.getPlatForm() == null)
            return builder.build();
        switch (message.getPlatForm()) {
            case ALL:
                builder.setPlatform(Platform.android_ios());
//                builder.setMessage(Message.newBuilder().setMsgContent(message.getContent()).setTitle(message.getTitle()).addExtras
//                        (message.getExtras()).build()).setMessage(Message.content(message.getContent()));
                builder = androidMessage(builder, message);
                iosMessage(builder, message);
                break;
            case IOS:
                builder.setPlatform(Platform.ios());
                iosMessage(builder, message);
                break;
            case ANDROID:
                builder.setPlatform(Platform.android());
                androidMessage(builder, message);
                break;
            case WINDOWS:
                builder.setPlatform(Platform.winphone());
                break;
        }
        if (message.getAlias() != null) {
            builder.setAudience(Audience.alias(message.getAlias()));
        } else {
            builder.setAudience(Audience.all());
        }
         builder.setMessage(Message.content(message.getContent()));
        builder.setOptions(Options.newBuilder().setApnsProduction(false).build());
        return builder.build();
    }

    private PushPayload.Builder androidMessage(PushPayload.Builder builder, PushMessage message) {
        builder.setNotification(Notification.newBuilder().addPlatformNotification(AndroidNotification.newBuilder
                ().setAlert(message.getTitle()).addExtras(message.getExtras()).build()).build());
        return builder;
    }

    private void iosMessage(PushPayload.Builder builder, PushMessage message) {
        builder.setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder
                ().setAlert(message.getTitle()).addExtras(message.getExtras()).build()).build());

    }
}
