package com.smt.smallfat.service.house;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatSucculentCircle;
import com.smt.smallfat.po.FatSucculentImage;
import com.smt.smallfat.po.FatSucculentReport;
import com.smt.smallfat.vo.house.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Map;


public interface CircleService {
    int ADD_PRAISE = 1;
    int CANCEL_PRAISE = 0;
    int FOLLOW = 1;
    int BE_FOLLOW = 2;
    int READ = 1;


    int praise(int fromUserId, int circleId);

    FatSucculentCircle getCircleById(int circleId);

    int follow(int followUserId, int beFollowedUserId);

    long followCount(int followUserId);

    long beFollowedCount(int beFollowedUserId);

    long praiseCount(int circleId);

    Pagination<FollowVO> followList(int userId, int pageNo, int pageSize);

    Pagination<FollowVO> beFollowList(int userId, int pageNo, int pageSize);

    int isFollow(int userId, int followUserId);

    List<FatCustomer> topFivePraises(int circleId);

    FlowerHouseItemVO circleItemView(int userId, int circleId);

    List<FatSucculentImage> getCircleImages(int circleId);

    Pagination<FlowerHouseItemVO> circleList(int userId, int pageNo, int pageSize,int visitorId);

    Pagination<PraiseUserVO> praiseUserList(int circleId, int pageNo, int pageSize);

    UserIndexPage userIndex(int userId, int visitorId);

    long userPraiseCount(int userId);

    Pagination<FlowerHouseItemVO> indexCircleList(int userId, int pageNo, int pageSize);

    ImageUploadInfo uploadImage(MultipartHttpServletRequest request);

    int isPraise(int userId, int circleId);

    FlowerHouseItemVO addCircleItem(Map<String, Object> param);

    void imageOverdueCallBack(String message);

    void readPraise(String ids);

    void readFollow(String ids);

    Pagination<PraiseVO> myPraise(int userId, int pageNo, int pageSize);

    CircleShareVO circleShare(int id);

    void deleteCircle(int id, int userId);

    long notReadPraise(int userId);

    long notReadFollow(int userId);

    void reportCircle(Map<String,Object> param);

    Pagination<FlowerHouseItemVO> followUserCircle(int userId, int pageNo, int pageSize);

    void readAllPraise(int userId);

    void readAllFollow(int userId);

    Pagination<FatSucculentReport> pageReport(Map<String,Object> params);

    FatSucculentReport getReportById(int id);

    void pass(int id,String feedback);

    void unPass(int id);
}
