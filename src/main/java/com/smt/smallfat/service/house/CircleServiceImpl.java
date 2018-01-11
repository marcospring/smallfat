package com.smt.smallfat.service.house;

import com.csyy.common.PropertiesUtils;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.base.CommentService;
import com.smt.smallfat.service.base.CustomerService;
import com.smt.smallfat.vo.FatCommentVO;
import com.smt.smallfat.vo.house.FlowerHouseItemVO;
import com.smt.smallfat.vo.house.PraiseUserVO;
import com.smt.smallfat.vo.house.UserIndexPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CircleServiceImpl extends BaseServiceImpl implements CircleService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private PropertiesUtils propertiesUtils;
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int praise(int fromUserId, int circleId) {
        FatSucculentCircle circle = getCircleById(circleId);
        int toUserId = circle.getUserId();
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise
                .FIELD_FROM_USER_ID, fromUserId)).add(ParamBuilder.nv(FatSucculentPraise.FIELD_CIRCLE_ID, circleId))
                .add(ParamBuilder.nv(FatSucculentPraise.FIELD_TO_USER_ID, toUserId));

        FatSucculentPraise praise = factory.getCacheReadDataSession().querySingleResultByParams(FatSucculentPraise
                .class, params);
        if (praise != null) {
            factory.getCacheWriteDataSession().physicalDelete(FatSucculentPraise.class, praise.getId());
            return CANCEL_PRAISE;
        } else {
            praise = FatSucculentPraise.getInstance(FatSucculentPraise.class);
            praise.setCircleId(circleId);
            praise.setFromUserId(fromUserId);
            praise.setToUserId(toUserId);
            factory.getCacheWriteDataSession().save(FatSucculentPraise.class, praise);
            return ADD_PRAISE;
        }

    }

    @Override
    public FatSucculentCircle getCircleById(int circleId) {
        FatSucculentCircle circle = factory.getCacheReadDataSession().querySingleResultById(FatSucculentCircle.class,
                circleId);
        if (circle == null)
            throw new CommonException(ResultConstant.Circle.CIRCLE_IS_NULL);
        return circle;
    }

    @Override
    public int follow(int followUserId, int beFollowedUserId) {
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentFollow
                .FIELD_FOLLOW_USER_ID, followUserId)).add(ParamBuilder.nv(FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID,
                beFollowedUserId));
        FatSucculentFollow follow = factory.getCacheReadDataSession().querySingleResultByParams(FatSucculentFollow
                .class, params);
        if (follow == null) {
            follow = FatSucculentFollow.getInstance(FatSucculentFollow.class);
            follow.setFollowUserId(followUserId);
            follow.setBeFollowUserId(beFollowedUserId);
            factory.getCacheWriteDataSession().save(FatSucculentFollow.class, follow);
            return ADD_PRAISE;
        } else {
            factory.getCacheWriteDataSession().physicalDelete(FatSucculentFollow.class, follow.getId());
            return CANCEL_PRAISE;
        }
    }

    @Override
    public long followCount(int followUserId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentFollow
                .FIELD_FOLLOW_USER_ID, followUserId));
        return factory.getCacheReadDataSession().queryListResultCount(FatSucculentFollow.class, param);
    }

    @Override
    public long beFollowedCount(int beFollowedUserId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentFollow
                .FIELD_BE_FOLLOW_USER_ID, beFollowedUserId));
        return factory.getCacheReadDataSession().queryListResultCount(FatSucculentFollow.class, param);
    }

    @Override
    public long praiseCount(int circleId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise.FIELD_CIRCLE_ID,
                circleId));
        return factory.getCacheReadDataSession().queryListResultCount(FatSucculentPraise.class, param);
    }

    @Override
    public Pagination<FatCustomer> followList(int userId, int pageNo, int pageSize) {
        return getPageCustomer(userId, pageNo, pageSize, FOLLOW);
    }

    @Override
    public Pagination<FatCustomer> beFollowList(int userId, int pageNo, int pageSize) {
        return getPageCustomer(userId, pageNo, pageSize, BE_FOLLOW);
    }

    private Pagination<FatCustomer> getPageCustomer(int userId, int pageNo, int pageSize, int type) {
        Param param = ParamBuilder.getInstance().getParam();
        if (type == FOLLOW) {
            param.add(ParamBuilder.nv(FatSucculentFollow
                    .FIELD_FOLLOW_USER_ID, userId));
        } else {
            param.add(ParamBuilder.nv(FatSucculentFollow
                    .FIELD_BE_FOLLOW_USER_ID, userId));
        }
        Pagination<FatSucculentFollow> pageFollow = queryClassPagination(FatSucculentFollow.class, param, pageNo,
                pageSize);
        List<FatSucculentFollow> list = pageFollow.getData();
        List<FatCustomer> customers = new ArrayList<>(list.size());
        for (FatSucculentFollow follow : list) {
            int beFollowUserId = follow.getBeFollowUserId();
            FatCustomer customer = customerService.getCustomerById(beFollowUserId);
            customers.add(customer);
        }
        Pagination<FatCustomer> pageResult = new Pagination<>(customers, pageNo, pageSize);
        pageResult.setRecordsTotal(pageFollow.getRecordsTotal());
        return pageResult;
    }

    @Override
    public int isFollow(int userId, int followUserId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentFollow
                .FIELD_FOLLOW_USER_ID, followUserId)).add(ParamBuilder.nv(FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID,
                userId));
        FatSucculentFollow follow = factory.getCacheReadDataSession().querySingleResultByParams(FatSucculentFollow
                .class, param);
        return follow == null ? 1 : 2;
    }

    @Override
    public List<FatCustomer> topFivePraises(int circleId) {
        CustomSQL where = SQLCreator.where().cloumn(FatSucculentPraise.FIELD_CIRCLE_ID).operator(ESQLOperator.EQ).v
                (circleId).operator(ESQLOperator.LIMIT).v(5);
        List<FatSucculentPraise> list = factory.getCacheReadDataSession().queryListResultByWhere(FatSucculentPraise
                .class, where);
        List<FatCustomer> result = new ArrayList<>(5);
        for (FatSucculentPraise praise : list) {
            FatCustomer customer = null;
            try {
                customer = customerService.getCustomerById(praise.getFromUserId());
            } catch (CommonException e) {
                if (!ResultConstant.Customer.CUSTOMER_IS_NULL.equals(e.getExceptionKey())) ;
                throw e;
            }
            result.add(customer);
        }
        //TODO 如果不够五个则填够5个再返回
        return result;
    }


    @Override
    public FlowerHouseItemVO circleItemView(int userId, int circleId) {
        FatSucculentCircle circle = getCircleById(circleId);
        FatCustomer author = customerService.getCustomerById(circle.getUserId());
        long praiseCount = praiseCount(circleId);
        List<FatSucculentImage> images = getCircleImages(circleId);
        FlowerHouseItemVO vo = new FlowerHouseItemVO();
        vo.setId(circleId);
        vo.setAuthor(author);
        vo.setContent(circle.getContent());
        vo.setCreateTime(circle.getCreateTime());
        vo.setPraiseCount(praiseCount);
        vo.setPraises(topFivePraises(circleId));
        vo.setImgs(images);
        vo.setIsPraise(isPraise(userId, circleId));
        return vo;
    }

    @Override
    public List<FatSucculentImage> getCircleImages(int circleId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentImage.FIELD_CIRCLE_ID,
                circleId));
        return factory.getCacheReadDataSession().queryListResult(FatSucculentImage.class, param);
    }

    @Override
    public Pagination<FlowerHouseItemVO> circleList(int userId, int pageNo, int pageSize) {
        Param param = ParamBuilder.getInstance().getParam();
        if (userId != 0)
            param.add(ParamBuilder.nv(FatSucculentCircle.FIELD_USER_ID, userId));
        Pagination<FatSucculentCircle> page = queryClassPagination(FatSucculentCircle.class, param, pageNo, pageSize);
        List<FatSucculentCircle> data = page.getData();
        Pagination<FlowerHouseItemVO> pageResult = new Pagination<>(pageNo, pageSize);
        pageResult.setRecordsTotal(page.getRecordsTotal());
        Map<String, Object> params = new HashMap<>();
        List<FlowerHouseItemVO> resultData = new ArrayList<>(pageSize);
        for (FatSucculentCircle circle : data) {
            params.clear();
            int circleId = circle.getId();
            params.put(FatComment.FIELD_ARTICLE_ID, circleId);
            params.put(FatComment.FIELD_COMMENT_TYPE, 1);
            params.put(Constant.PAGE_SIZE, 10);
            params.put(Constant.PAGE_NO, 1);
            Pagination<FatCommentVO> pageComment = commentService.pageComment(params);
            FlowerHouseItemVO vo = circleItemView(userId, circleId);
            vo.setCommentCount(pageComment.getRecordsTotal());
            vo.setCommentPagination(pageComment);
            resultData.add(vo);
        }
        pageResult.setData(resultData);
        return pageResult;
    }

    @Override
    public Pagination<PraiseUserVO> praiseUserList(int circleId, int pageNo, int pageSize) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise.FIELD_CIRCLE_ID,
                circleId));
        List<FatSucculentImage> imageList = getCircleImages(circleId);
        FatSucculentImage firstImg = null;
        for (FatSucculentImage img : imageList) {
            if (img.getImgIndex() == 1) {
                firstImg = img;
                break;
            }
        }
        Pagination<FatSucculentPraise> page = queryClassPagination(FatSucculentPraise.class, param, pageNo, pageSize);
        List<FatSucculentPraise> data = page.getData();
        Pagination<PraiseUserVO> result = new Pagination<>(pageNo, pageSize);
        List<PraiseUserVO> resultData = new ArrayList<>(data.size());
        for (FatSucculentPraise praise : data) {
            PraiseUserVO vo = new PraiseUserVO();
            vo.setCircleId(circleId);
            vo.setCustomer(customerService.getCustomerById(praise.getFromUserId()));
            vo.setImage(firstImg);
            resultData.add(vo);
        }
        result.setData(resultData);
        return result;
    }

    @Override
    public UserIndexPage userIndex(int userId, int visitorId) {
        UserIndexPage index = new UserIndexPage();
        FatCustomer customer = customerService.getCustomerById(visitorId);
        index.setCustomer(customer);
        index.setFollowCount(followCount(visitorId));
        index.setFollowedCount(beFollowedCount(visitorId));
        index.setPraiseCount(userPraiseCount(visitorId));
        index.setPageCircle(circleList(visitorId, 1, 10));
        if (userId == visitorId)
            index.setIsFollow(0);
        else
            index.setIsFollow(isFollow(visitorId, userId));
        return index;
    }

    @Override
    public long userPraiseCount(int userId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise.FIELD_TO_USER_ID,
                userId));
        return factory.getCacheReadDataSession().queryListResultCount(FatSucculentPraise.class, param);
    }

    @Override
    public Pagination<FlowerHouseItemVO> indexCircleList(int userId, int pageNo, int pageSize) {
        Param param = ParamBuilder.getInstance().getParam();
        Pagination<FatSucculentCircle> page = queryClassPagination(FatSucculentCircle.class, param, pageNo, pageSize);
        List<FatSucculentCircle> data = page.getData();
        Pagination<FlowerHouseItemVO> pageResult = new Pagination<>(pageNo, pageSize);
        List<FlowerHouseItemVO> resultData = new ArrayList<>(pageSize);
        for (FatSucculentCircle circle : data) {
            FlowerHouseItemVO vo = circleItemView(userId, circle.getId());
            vo.setImageCount(vo.getImgs().size());
            resultData.add(vo);
        }
        pageResult.setData(resultData);
        return pageResult;
    }

    @Override
    public String uploadImage(MultipartHttpServletRequest request) {
        //取得request中的所有文件名
        String userId = request.getParameter(Constant.USER_ID);
        Iterator<String> iterator = request.getFileNames();
        while (iterator.hasNext()) {
            //取得上传文件
            MultipartFile file = request.getFile(iterator.next());
            String tempUrl = propertiesUtils.get("houseImageUrl");
            //取得当前上传文件的文件名称
            String myFileName = file.getOriginalFilename();
            logger.info("1.=====>{}的{}存入{}缓存", userId, myFileName, tempUrl);

//            //构建fileStorage实体
//            FileStorage info = FileStorage.getInstance(FileStorage.class);
//            info.setFileName(myFileName);
//            info.setFileType(file.getContentType());
//            info.setFileSize(String.valueOf(file.getSize()));
//            String fileName = FileUtils.getFileName(myFileName);
//            info.setFileUrl(FileUtils.getFilePath(fileName, info.getFileType(), this.baseUrl));


            //如果名称不为“”,说明该文件存在，否则说明该文件不存在
//            if (myFileName.trim() != "") {
//                String path = info.getFileUrl();
//                logger.info("存储的路径:" + path);
//                File localFile = new File(path);
//
//                //如果目录不存在,创建目录
//                if (!localFile.getParentFile().exists() && !localFile.isDirectory()) {
//                    logger.info("创建目录:{}=========", localFile.getParentFile().getPath());
//                    localFile.getParentFile().mkdirs();
//                }
//
//                try {
//                    file.transferTo(localFile);
//                } catch (IOException e) {
//                    logger.error("文件:" + myFileName + "操作失败!" + e);
//                    return null;
//                }
//            }

        }


        return null;
    }

    @Override
    public int isPraise(int userId, int circleId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise
                .FIELD_FROM_USER_ID, userId)).add(ParamBuilder.nv(FatSucculentPraise.FIELD_CIRCLE_ID, circleId));
        FatSucculentPraise praise = factory.getCacheReadDataSession().querySingleResultByParams(FatSucculentPraise
                .class, param);
        return praise == null ? 0 : 1;
    }
}
