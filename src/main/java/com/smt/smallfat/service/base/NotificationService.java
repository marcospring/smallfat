package com.smt.smallfat.service.base;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatNotification;

import java.util.Map;

public interface NotificationService {
    FatNotification addNotification(Map<String, Object> param);

    FatNotification addNotification(FatNotification notification);

    void deleteNotification(int id);

    FatNotification getNotification(int id);

    FatNotification read(int id);

    Pagination<FatNotification> pageNotification(Map<String, Object> param);

    Pagination<FatNotification> userPageNotification(int userId, int pageNo, int pageSize);

    Map<String, Object> haveNonReadNotification(int userId);

    void pushNotification(int id);
}
