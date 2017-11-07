package com.smt.smallfat.service.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.constant.Constants;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.FatNotification;
import com.smt.smallfat.service.NotificationService;
import com.smt.smallfat.utils.push.IPush;
import com.smt.smallfat.utils.push.PushMessage;
import com.smt.smallfat.utils.push.PushPayloadBuilder;
import com.smt.smallfat.utils.push.pushenum.PlatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl extends BaseServiceImpl implements NotificationService {

    @Autowired
    private IPush push;

    @Override
    public FatNotification addNotification(Map<String, Object> param) {
        FatNotification notification = CommonBeanUtils.transMap2BasePO(param, FatNotification.class);
        notification = factory.getCacheWriteDataSession().save(FatNotification.class, notification);
        return notification;
    }

    @Override
    public void deleteNotification(int id) {
        factory.getCacheWriteDataSession().physicalDelete(FatNotification.class, id);
    }

    @Override
    public FatNotification getNotification(int id) {
        FatNotification notification = factory.getCacheReadDataSession().querySingleResultById(FatNotification.class,
                id);
        if (notification == null)
            throw new CommonException(ResultConstant.Notification.NOTIFICATION_IS_NULL);
        return notification;
    }

    @Override
    public Pagination<FatNotification> pageNotification(Map<String, Object> param) {
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatNotification> page = queryClassPagination(FatNotification.class, params, pageNo, pageSize);
        return page;
    }

    @Override
    public FatNotification addNotification(FatNotification notification) {
        return factory.getCacheWriteDataSession().save(FatNotification.class, notification);
    }

    @Override
    public Pagination<FatNotification> userPageNotification(int userId, int pageNo, int pageSize) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatNotification.FIELD_USER_ID, userId)
        ).add(ParamBuilder.nv(Constant.SQLConstants
                .ORDER_COLUMN, FatNotification.FIELD_CREATE_TIME)).add(ParamBuilder.nv(Constant.SQLConstants
                .ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatNotification> page = queryClassPagination(FatNotification.class, param, pageNo, pageSize);
        CustomSQL where = SQLCreator.where().cloumn(FatNotification.FIELD_IS_READ).operator(ESQLOperator.EQ).v
                (Constant.WrapperExtend.ZERO).operator(ESQLOperator.AND).cloumn(FatNotification.FIELD_USER_ID).operator
                (ESQLOperator.EQ).v(userId);
        param.clean();
        param.add(ParamBuilder.nv(FatNotification.FIELD_IS_READ, 1));
        factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatNotification.class, param, where);
        return page;
    }

    @Override
    public FatNotification read(int id) {
        FatNotification notification = getNotification(id);
        notification.setIsRead(1);
        factory.getCacheWriteDataSession().update(FatNotification.class, notification);
        return notification;
    }

    @Override
    public Map<String, Object> haveNonReadNotification(int userId) {
        Map<String, Object> result = new HashMap<>();
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatNotification.FIELD_USER_ID, userId)
        ).add(ParamBuilder.nv(FatNotification.FIELD_IS_READ, 0));
        long count = factory.getCacheReadDataSession().queryListResultCount(FatNotification.class, param);
        result.put("hasNotification", count);
        return result;
    }

    @Override
    public void pushNotification(int id) {
        FatNotification notification = getNotification(id);
        if (notification.getIsAll() != 1)
            throw new CommonException(ResultConstant.Notification.NOT_ALL_NOTIFICATION);
        push.push(PushPayloadBuilder.newInstance().build(PushMessage.get()
                .title(notification.getTitle())
                .content(notification.getContent())
                .platform(PlatForm.IOS)
                .addExtras(Constant.PUSH_TYPE, Constant.PushType.SYSTEM_NOTICE)
                .addExtras(FatNotification.FIELD_TITLE, notification.getTitle())
                .addExtras(FatNotification.FIELD_ID, StringDefaultValue.StringValue(notification.getId()))
                .addExtras(FatNotification.FIELD_CONTENT, notification.getContent())));
        notification.setIsPush(1);
        factory.getCacheWriteDataSession().update(FatNotification.class,notification);
    }
}
