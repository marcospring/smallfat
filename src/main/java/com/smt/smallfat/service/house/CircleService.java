package com.smt.smallfat.service.house;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatCustomer;
import com.smt.smallfat.po.FatSucculentCircle;
import com.smt.smallfat.po.FatSucculentImage;
import com.smt.smallfat.vo.house.FlowerHouseItemVO;
import com.smt.smallfat.vo.house.PraiseUserVO;
import com.smt.smallfat.vo.house.UserIndexPage;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


public interface CircleService {
    int ADD_PRAISE = 1;
    int CANCEL_PRAISE = 0;
    int FOLLOW = 1;
    int BE_FOLLOW = 2;


    int praise(int fromUserId, int circleId);

    FatSucculentCircle getCircleById(int circleId);

    int follow(int followUserId, int beFollowedUserId);

    long followCount(int followUserId);

    long beFollowedCount(int beFollowedUserId);

    long praiseCount(int circleId);

    Pagination<FatCustomer> followList(int userId, int pageNo, int pageSize);

    Pagination<FatCustomer> beFollowList(int userId, int pageNo, int pageSize);

    int isFollow(int userId, int followUserId);

    List<FatCustomer> topFivePraises(int circleId);

    FlowerHouseItemVO circleItemView(int userId, int circleId);

    List<FatSucculentImage> getCircleImages(int circleId);

    Pagination<FlowerHouseItemVO> circleList(int userId, int pageNo, int pageSize);

    Pagination<PraiseUserVO> praiseUserList(int circleId, int pageNo, int pageSize);

    UserIndexPage userIndex(int userId, int visitorId);

    long userPraiseCount(int userId);

    Pagination<FlowerHouseItemVO> indexCircleList(int userId,int pageNo, int pageSize);

    String uploadImage(MultipartHttpServletRequest request);

    int isPraise(int userId,int circleId);
}
