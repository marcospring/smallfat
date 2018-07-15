package com.smt.smallfat.service.house;

import com.csyy.common.*;
import com.csyy.constant.Constants;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.base.CommentService;
import com.smt.smallfat.service.base.CustomerService;
import com.smt.smallfat.utils.FileUtils;
import com.smt.smallfat.utils.push.IPush;
import com.smt.smallfat.utils.push.PushMessage;
import com.smt.smallfat.utils.push.PushPayloadBuilder;
import com.smt.smallfat.utils.push.pushenum.PlatForm;
import com.smt.smallfat.vo.FatCommentVO;
import com.smt.smallfat.vo.house.*;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private IPush push;

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
            //推送
            if (fromUserId != toUserId) {
                FatCustomer fromCustomer = customerService.getCustomerById(fromUserId);
                FatCustomer toCustomer = customerService.getCustomerById(toUserId);

                String title = "{0}给你点赞";
                PushMessage message = PushMessage.get()
                        .title(PushMessage.buildMessage(title, fromCustomer.getNickName()))
                        .platform(PlatForm.ALL)
                        .content(PushMessage.buildMessage(title, fromCustomer.getNickName()))
                        .addAlias(toCustomer.getUuid())
                        .addExtras(Constant.PUSH_TYPE, Constant.PushType.PRAISE);
                logger.info("================>:{}", push);
                push.push(PushPayloadBuilder.newInstance().build(message));
            }
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
            //推送
            FatCustomer fromCustomer = customerService.getCustomerById(followUserId);
            FatCustomer toCustomer = customerService.getCustomerById(beFollowedUserId);
            String title = "{0}关注了你";
            PushMessage message = PushMessage.get()
                    .title(PushMessage.buildMessage(title, fromCustomer.getNickName()))
                    //.platform(PlatForm.IOS)
                    .platform(PlatForm.ALL)
                    .content(PushMessage.buildMessage(title, fromCustomer.getNickName()))
                    .addAlias(toCustomer.getUuid())
                    .addExtras(Constant.PUSH_TYPE, Constant.PushType.FOLLOW);
            logger.info("================>:{}", push);
            push.push(PushPayloadBuilder.newInstance().build(message));
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
    public Pagination<FollowVO> followList(int userId, int pageNo, int pageSize) {
        return getPageCustomer(userId, pageNo, pageSize, FOLLOW);
    }

    @Override
    public Pagination<FollowVO> beFollowList(int userId, int pageNo, int pageSize) {
        return getPageCustomer(userId, pageNo, pageSize, BE_FOLLOW);
    }

    private Pagination<FollowVO> getPageCustomer(int userId, int pageNo, int pageSize, int type) {
        Param param = ParamBuilder.getInstance().getParam();
        if (type == FOLLOW) {
            param.add(ParamBuilder.nv(FatSucculentFollow
                    .FIELD_FOLLOW_USER_ID, userId));
        } else {
            param.add(ParamBuilder.nv(FatSucculentFollow
                    .FIELD_BE_FOLLOW_USER_ID, userId));
        }
        param.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC)).add(ParamBuilder
                .nv(Constant.SQLConstants.ORDER_COLUMN, FatSucculentFollow.FIELD_CREATE_TIME));
        Pagination<FatSucculentFollow> pageFollow = queryClassPagination(FatSucculentFollow.class, param, pageNo,
                pageSize);
        List<FatSucculentFollow> list = pageFollow.getData();
        List<FollowVO> followVO = new ArrayList<>(list.size());
        for (FatSucculentFollow follow : list) {
            int followUserId;
            if (type == FOLLOW)
                followUserId = follow.getBeFollowUserId();
            else
                followUserId = follow.getFollowUserId();
            FatCustomer customer = customerService.getCustomerById(followUserId);
            FollowVO vo = new FollowVO(customer, follow);
            followVO.add(vo);
        }
        Pagination<FollowVO> pageResult = new Pagination<>(followVO, pageNo, pageSize);
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
        return follow == null ? 0 : 1;
    }

    @Override
    public List<FatCustomer> topFivePraises(int circleId) {
        CustomSQL where = SQLCreator.where().cloumn(FatSucculentPraise.FIELD_CIRCLE_ID).operator(ESQLOperator.EQ).v
                (circleId).operator(ESQLOperator.ORDER_BY).cloumn
                (FatSucculentPraise.FIELD_CREATE_TIME).operator(ESQLOperator.DESC).operator(ESQLOperator.LIMIT).v(10);
        List<FatSucculentPraise> list = factory.getCacheReadDataSession().queryListResultByWhere(FatSucculentPraise
                .class, where);
        List<FatCustomer> result = new ArrayList<>(5);
        for (FatSucculentPraise praise : list) {
            FatCustomer customer;
            try {
                customer = customerService.getCustomerById(praise.getFromUserId());
                if (result.size() < 5)
                    result.add(customer);
                else
                    break;
            } catch (CommonException e) {
                if (!ResultConstant.Customer.CUSTOMER_IS_NULL.equals(e.getExceptionKey()))
                    throw e;
            }
        }
        return result;
    }


    @Override
    public FlowerHouseItemVO circleItemView(int userId, int circleId) {
        FatSucculentCircle circle = getCircleById(circleId);
        FatCustomer author = customerService.getCustomerById(circle.getUserId());
        long praiseCount = praiseCount(circleId);
        List<FatSucculentImage> images = getCircleImages(circleId);
        Pagination<FatCommentVO> pageComment = pageCircleComment(circleId);
        FlowerHouseItemVO vo = new FlowerHouseItemVO();
        vo.setId(circleId);
        vo.setAuthor(author);
        vo.setContent(circle.getContent());
        vo.setCreateTime(circle.getCreateTime());
        vo.setPraiseCount(praiseCount);
        vo.setPraises(topFivePraises(circleId));
        vo.setImgs(images);
        vo.setShareUrl(Constant.CIRCLE_SHARE_URL + circleId);
        if (userId != 0)
            vo.setIsPraise(isPraise(userId, circleId));
        vo.setCommentPagination(pageComment);
        vo.setCommentCount(pageComment.getRecordsTotal());
        return vo;
    }

    @Override
    public List<FatSucculentImage> getCircleImages(int circleId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentImage.FIELD_CIRCLE_ID,
                circleId));
        return factory.getCacheReadDataSession().queryListResult(FatSucculentImage.class, param);
    }

    @Override
    public Pagination<FlowerHouseItemVO> circleList(int userId, int pageNo, int pageSize, int visitorId) {
        Param param = ParamBuilder.getInstance().getParam();
        if (visitorId != 0)
            param.add(ParamBuilder.nv(FatSucculentCircle.FIELD_USER_ID, visitorId));
        param.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_COLUMN, FatSucculentCircle.FIELD_CREATE_TIME))
                .add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatSucculentCircle> page = queryClassPagination(FatSucculentCircle.class, param, pageNo, pageSize);
        List<FatSucculentCircle> data = page.getData();
        Pagination<FlowerHouseItemVO> pageResult = new Pagination<>(pageNo, pageSize);
        pageResult.setRecordsTotal(page.getRecordsTotal());
        List<FlowerHouseItemVO> resultData = buildFlowerHouseItemVOData(userId, pageSize, data);
        pageResult.setData(resultData);
        return pageResult;
    }

    private Pagination<FatCommentVO> pageCircleComment(int circleId) {
        Map<String, Object> params = new HashMap<>();
        params.put(FatComment.FIELD_ARTICLE_ID, circleId);
        params.put(FatComment.FIELD_COMMENT_TYPE, 1);
        params.put(Constant.SQLConstants.ORDER_COLUMN, FatComment.FIELD_CREATE_TIME);
        params.put(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC);
        params.put(Constant.PAGE_SIZE, 10);
        params.put(Constant.PAGE_NO, 1);
        return commentService.pageComment(params);
    }

    @Override
    public Pagination<PraiseUserVO> praiseUserList(int circleId, int pageNo, int pageSize) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise.FIELD_CIRCLE_ID,
                circleId));
        List<FatSucculentImage> imageList = getCircleImages(circleId);
        FatSucculentImage firstImg = imageList.stream().filter(image -> image.getImgIndex() == 1).findFirst().get();
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
        index.setPageCircle(circleList(userId, 1, 10, visitorId));
        if (userId == visitorId || userId == Constant.WrapperExtend.ZERO)
            index.setIsFollow(Constant.WrapperExtend.ZERO);
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
        param.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        param.add(ParamBuilder.nv(Constant.SQLConstants.ORDER_COLUMN, FatSucculentCircle.FIELD_UPDATE_TIME));
        Pagination<FatSucculentCircle> page = queryClassPagination(FatSucculentCircle.class, param, pageNo, pageSize);
        List<FatSucculentCircle> data = page.getData();
        Pagination<FlowerHouseItemVO> pageResult = new Pagination<>(pageNo, pageSize);
        List<FlowerHouseItemVO> resultData = new ArrayList<>(pageSize);
        for (FatSucculentCircle circle : data) {
            FlowerHouseItemVO vo = circleItemView(userId, circle.getId());
            vo.setImageCount(vo.getImgs().size());
            vo.setShareUrl(Constant.CIRCLE_SHARE_URL + circle.getId());
            resultData.add(vo);
        }
        pageResult.setData(resultData);
        return pageResult;
    }

    @Override
    public ImageUploadInfo uploadImage(MultipartHttpServletRequest request) {
        ImageUploadInfo image = null;
        //取得request中的所有文件名
        int userId = StringDefaultValue.intValue(request.getParameter(Constant.USER_ID));
        String uuid = customerService.getCustomerById(userId).getUuid();
        Iterator<String> iterator = request.getFileNames();
        String baseTempUrl = propertiesUtils.get("houseImageUrl");
        String userTempPath = baseTempUrl + File.separator + uuid;
        File userTempPathFile = new File(userTempPath);
        //判断用户只能上传6张图片，用户缓存文件如果存在且小于六张图片的时候才可以上传
//        if (userTempPathFile.exists() && userTempPathFile.listFiles().length < 21) {
            while (iterator.hasNext()) {
                image = new ImageUploadInfo();
                //取得上传文件
                MultipartFile file = request.getFile(iterator.next());
                //取得当前上传文件的文件名称
                String myFileName = file.getOriginalFilename();
                image.setOriginName(myFileName);
                String postfix = file.getContentType().substring(file.getContentType().indexOf("/") + 1);
                //request.getContentType().substring(request.getContentType().indexOf("/"));
//            myFileName
//                    .substring
//                    (myFileName.lastIndexOf("" +
//                    "."));
                logger.info("1.=====>{}的{}存入{}缓存", uuid, myFileName, baseTempUrl);
                //创建用户的缓存目录，如果存在使用，如果不存在创建
                String tempImageName = UUIDProvider.uuid() + Constant.Separator.DOT + postfix;
                String imgRelativePath = File.separator + uuid + File.separator + tempImageName;
                String imgTempPath = baseTempUrl + imgRelativePath;
                image.setImageName(tempImageName);
                image.setImagePath(imgRelativePath);
                logger.info("2.=====>图片名称：{}；图片相对路径：{}；图片存储路径：{}", tempImageName, imgRelativePath, imgTempPath);
                File userTempFile = new File(imgTempPath);
                if (!userTempFile.getParentFile().exists() && !userTempFile.getParentFile().isDirectory()) {
                    logger.info("3.=====>创建目录:{}", userTempFile.getParentFile().getPath());
                    userTempFile.getParentFile().mkdirs();
                }
                //保存图片至缓存目录
                try {
                    file.transferTo(userTempFile);
                    //压缩
//                Thumbnails.of(userTempFile.getPath()).scale(1f).outputQuality(0.5f).toFile
//                        (userTempFile.getPath().substring(0, userTempFile.getPath().lastIndexOf("/") + 1) + userTempFile.getName());
                } catch (IOException e) {
                    ExceptionInfo.exceptionInfo(e, logger);
                    throw new CommonException(ResultConstant.Circle.IMAGE_UPLOAD_FAILED);
                }
                //将缓存数据存入redis，过期后删除
                String redisData = uuid + Constant.Separator.UPDERLINE + imgTempPath;
                factory.getRedisSessionFactory().getSession().setData(Constant.HOUSE_IMAGE_PREFIX + imgRelativePath,
                        redisData, 60 * 30);
            }
//        }else{
//
//        }


        return image;
    }

    @Override
    public int isPraise(int userId, int circleId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise
                .FIELD_FROM_USER_ID, userId)).add(ParamBuilder.nv(FatSucculentPraise.FIELD_CIRCLE_ID, circleId));
        FatSucculentPraise praise = factory.getCacheReadDataSession().querySingleResultByParams(FatSucculentPraise
                .class, param);
        return praise == null ? 0 : 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FlowerHouseItemVO addCircleItem(Map<String, Object> param) {
        String[] images = StringDefaultValue.StringValue(param.get("imageUrls")).split(Constant.Separator.COMMA);
        if(images.length > 9)
            throw new CommonException(ResultConstant.Circle.TOO_MANY_CIRCLE_IMAGE);
        int userId = StringDefaultValue.intValue(param.get(FatSucculentCircle.FIELD_USER_ID));
        String content = StringDefaultValue.StringValue(param.get(FatSucculentCircle.FIELD_CONTENT));
        int articleType = StringDefaultValue.intValue(param.get(FatSucculentCircle.FIELD_ARTICLE_TYPE));
        FatSucculentCircle circle = FatSucculentCircle.getInstance(FatSucculentCircle.class);
        circle.setArticleType(articleType);
        circle.setContent(content);
        circle.setUserId(userId);
        circle = factory.getCacheWriteDataSession().save(FatSucculentCircle.class, circle);
        for (String image : images) {
            //添加花房图片
            FatSucculentImage circleImage = moveTempImage(image, userId);
            circleImage.setCircleId(circle.getId());

            circleImage.setUserId(userId);
            circleImage.setCreateUser(userId);
            factory.getCacheWriteDataSession().save(FatSucculentImage.class, circleImage);
        }
        return circleItemView(userId, circle.getId());
    }

    private FatSucculentImage moveTempImage(String image, int userId) {
        FatSucculentImage circleImage = FatSucculentCircle.getInstance(FatSucculentImage
                .class);
        int imageIndex = StringDefaultValue.intValue(image.substring(image.lastIndexOf(Constant.Separator
                .UPDERLINE) + 1, image.length()));
        String imageRelativePath = image.substring(0, image.lastIndexOf(Constant.Separator.UPDERLINE));
        //验证路径的有效性
        validUrl(userId, imageRelativePath);
        //转存至图片文件夹
        String imgTempPath = propertiesUtils.get("houseImageUrl") + imageRelativePath;
        String tempFileName = imgTempPath.split("\\/")[imgTempPath.split("\\/").length - 1];
        String baseUrl = propertiesUtils.get("baseUrl") + File.separator + "house";
        String newFilePath = FileUtils.getRandomPath(tempFileName, baseUrl);
        logger.info("tempFilePath:{}", imgTempPath);
        logger.info("filePath:{}", newFilePath);
        try {
            File imgTempFile = new File(imgTempPath);
            //如果缓存文件不存在，则返回
            if (!imgTempFile.exists())
                throw new CommonException(ResultConstant.Circle.CIRCLE_ADD_TIMEOUT);
            File targetPath = new File(newFilePath);//获取文件夹路径
            if (!targetPath.exists()) {//判断文件夹是否创建，没有创建则创建新文件夹
                targetPath.mkdirs();
            }
            if (imgTempFile.renameTo(new File(newFilePath + imgTempFile.getName()))) {
                //  System.out.println("File is moved successful!");
                logger.info("文件移动成功！目标路径：{}", newFilePath);
            } else {
                throw new CommonException(ResultConstant.Circle.CIRCLE_ADD_TIMEOUT);
            }
        } catch (Exception e) {
            throw new CommonException(ResultConstant.Circle.CIRCLE_ADD_FILE);
        }
        circleImage.setImgIndex(imageIndex);
        //图片路径需要保存到新的路径
        newFilePath = newFilePath.substring(newFilePath.indexOf("/imgs"));
        circleImage.setImgUrl(newFilePath + tempFileName);
        return circleImage;
    }

    //验证路径的有效性
    private void validUrl(int userId, String image) {
        String userUUID = customerService.getCustomerById(userId).getUuid();
        String[] pathArray = image.split("\\/");
        String imageUserUUID = pathArray[1];
        String redisUserUUID = factory.getRedisSessionFactory().getSession().getData(Constant.HOUSE_IMAGE_PREFIX +
                image);
        if (redisUserUUID == null)
            throw new CommonException(ResultConstant.Circle.IMAGE_OVERDUE);
        redisUserUUID = redisUserUUID.split(Constant.Separator.UPDERLINE)[0];
        if (userUUID != null && imageUserUUID != null && redisUserUUID != null && (!userUUID.equals(imageUserUUID)
                || !imageUserUUID.equals(redisUserUUID)))
            throw new CommonException(ResultConstant.Circle.ILLEGAL);
    }

    @Override
    public void imageOverdueCallBack(String message) {
        if (!message.startsWith(Constant.HOUSE_IMAGE_PREFIX)) {
            return;
        }
        String imgRelativePath = message.replace(Constant.HOUSE_IMAGE_PREFIX, "");
        String imgTempPath = propertiesUtils.get("houseImageUrl") + imgRelativePath;
        File file = new File(imgTempPath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            file.delete();
            logger.info("删除文件成功！删除文件为：{}", imgTempPath);
        }
        // handOrderOverdue(orderNO);

    }

    @Override
    public void readPraise(String ids) {
        String[] idArray = ids.split(",");
        for (String idStr : idArray) {
            int id = StringDefaultValue.intValue(idStr);
            FatSucculentPraise praise = id(FatSucculentPraise.class, id, ResultConstant.Circle.PRAISE_IS_NULL);
            praise.setIsRead(READ);
            factory.getCacheWriteDataSession().update(FatSucculentPraise.class, praise);
        }
    }

    @Override
    public void readFollow(String ids) {
        String[] idArray = ids.split(",");
        for (String idStr : idArray) {
            int id = StringDefaultValue.intValue(idStr);
            FatSucculentFollow follow = id(FatSucculentFollow.class, id, ResultConstant.Circle.FOLLOW_IS_NULL);
            follow.setIsRead(READ);
            factory.getCacheWriteDataSession().update(FatSucculentFollow.class, follow);
        }
    }

    @Override
    public Pagination<PraiseVO> myPraise(int userId, int pageNo, int pageSize) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise.FIELD_TO_USER_ID,
                userId)).add(ParamBuilder.nv(Constant.SQLConstants.ORDER_COLUMN, FatSucculentPraise.FIELD_CREATE_TIME)
        ).add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatSucculentPraise> pagePraise = queryClassPagination(FatSucculentPraise.class, param, pageNo,
                pageSize);
        List<FatSucculentPraise> list = pagePraise.getData();
        List<PraiseVO> volist = new ArrayList<>(list.size());
        for (FatSucculentPraise praise : list) {
            FatCustomer customer = customerService.getCustomerById(praise.getFromUserId());
            FatSucculentImage firstImage = getCircleFirstImage(praise.getCircleId());
            PraiseVO vo = new PraiseVO(customer, firstImage, praise);
            volist.add(vo);
        }
        Pagination<PraiseVO> pageVO = new Pagination(volist, pagePraise.getPageNo(), pagePraise.getPageSize());
        pageVO.setRecordsTotal(pagePraise.getRecordsTotal());
        return pageVO;
    }

    private FatSucculentImage getCircleFirstImage(int circleId) {
        List<FatSucculentImage> images = getCircleImages(circleId);
        FatSucculentImage firstImage = images.stream().filter(image -> image.getImgIndex() == 1).findFirst().get();
        return firstImage;
    }

    @Override
    public CircleShareVO circleShare(int id) {
        FatSucculentCircle circle = getCircleById(id);
        FlowerHouseItemVO itemVO = circleItemView(circle.getUserId(), circle.getId());
        CircleShareVO vo = new CircleShareVO();
        vo.setItem(itemVO);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentCircle.FIELD_USER_ID,
                circle.getUserId())).add(ParamBuilder.nv(Constant.SQLConstants.ORDER_COLUMN, FatSucculentCircle
                .FIELD_CREATE_TIME)).add(ParamBuilder.nv(Constant.SQLConstants.ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        Pagination<FatSucculentCircle> userCircles = queryClassPagination(FatSucculentCircle.class, param, 1, 6);
        List<FatSucculentCircle> data = userCircles.getData();
        List<CircleShareVO.CircleVO> circleVOList = new ArrayList<>(data.size());
        for (FatSucculentCircle cir : data) {
            CircleShareVO.CircleVO circleVO = vo.new CircleVO();
            circleVO.setCircle(cir);
            circleVO.setFirstImage(getCircleFirstImage(cir.getId()));
            circleVOList.add(circleVO);
        }
        vo.setList(circleVOList);
        return vo;
    }

    @Override
    @Transactional
    public void deleteCircle(int id, int userId) {
        FatSucculentCircle circle = getCircleById(id);
        //-2为管理员删除
        if (userId != -2 && circle.getUserId() != userId)
            throw new CommonException(ResultConstant.Circle.DELETE_INVALID);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentImage.FIELD_CIRCLE_ID, id));
        List<FatSucculentImage> imageList = factory.getCacheReadDataSession().queryListResult(FatSucculentImage
                .class, param);
        String baseUrl = propertiesUtils.get("baseUrl");
        baseUrl = baseUrl.substring(0, baseUrl.indexOf("/imgs"));
        File file;
        //删图片
        for (FatSucculentImage image : imageList) {
            file = new File(baseUrl + image.getImgUrl());
            factory.getCacheWriteDataSession().physicalDelete(FatSucculentImage.class, image.getId());
            if (file.exists())
                file.delete();
        }
        //删点赞
        CustomSQL where = SQLCreator.where().cloumn(FatSucculentPraise.FIELD_CIRCLE_ID).operator(ESQLOperator.EQ).v(id);
        factory.getCacheWriteDataSession().physicalWhereDelete(FatSucculentPraise.class, where);
        //删评论
        CustomSQL commentWhere = SQLCreator.where().cloumn(FatComment.FIELD_ARTICLE_ID).operator(ESQLOperator.EQ).v(id)
                .operator(ESQLOperator.AND).cloumn(FatComment.FIELD_COMMENT_TYPE).operator(ESQLOperator.EQ).v
                        (Constant.CommentType.CIRCLE);
        factory.getCacheWriteDataSession().physicalWhereDelete(FatComment.class, commentWhere);
        //删除花房
        factory.getCacheWriteDataSession().physicalDelete(FatSucculentCircle.class, id);
    }

    @Override
    public long notReadPraise(int userId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise.FIELD_TO_USER_ID,
                userId)).add(ParamBuilder.nv(FatSucculentPraise.FIELD_IS_READ, Constant.WrapperExtend.ZERO));
        return factory.getCacheWriteDataSession().queryListResultCount(FatSucculentPraise.class, param);
    }

    @Override
    public long notReadFollow(int userId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID,
                userId)).add(ParamBuilder.nv(FatSucculentFollow.FIELD_IS_READ, Constant.WrapperExtend.ZERO));
        return factory.getCacheWriteDataSession().queryListResultCount(FatSucculentFollow.class, param);
    }

    public void reportCircle(Map<String, Object> param) {
        int articleId = StringDefaultValue.intValue(param.get(FatSucculentReport.FIELD_ARTICLE_ID));
        Param params = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentReport.FIELD_ARTICLE_ID,
                articleId));
        List<FatSucculentReport> reportList = factory.getWriteDataSession().queryListResult(FatSucculentReport.class
                ,params);
        if(reportList.size() > 0)
            return;
        FatSucculentReport report = CommonBeanUtils.transMap2BasePO(param, FatSucculentReport.class);
        report.setReportStatus(0);
        factory.getCacheWriteDataSession().save(FatSucculentReport.class, report);
    }

    @Override
    public Pagination<FlowerHouseItemVO> followUserCircle(int userId, int pageNo, int pageSize) {
        Pagination<FlowerHouseItemVO> pageResult = new Pagination<>(pageNo, pageSize);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentFollow
                .FIELD_FOLLOW_USER_ID, userId));
        List<FatSucculentFollow> follows = factory.getCacheReadDataSession().queryListResult(FatSucculentFollow
                .class, param);
        if (follows == null || follows.size() == 0)
            return pageResult;
        Integer[] userids = new Integer[follows.size()];
        for (int i = 0; i < follows.size(); i++) {
            userids[i] = follows.get(i).getBeFollowUserId();
        }
        CustomSQL where = SQLCreator.where().cloumn(FatSucculentCircle.FIELD_USER_ID).operator(ESQLOperator.IN).v
                (userids).operator(ESQLOperator.ORDER_BY).cloumn(FatSucculentCircle.FIELD_CREATE_TIME).operator(ESQLOperator.DESC);
        Pagination<FatSucculentCircle> page = queryClassPagination(FatSucculentCircle.class, where, pageNo, pageSize);

        List<FatSucculentCircle> data = page.getData();
        // Pagination<FlowerHouseItemVO> pageResult = new Pagination<>(pageNo, pageSize);
        pageResult.setRecordsTotal(page.getRecordsTotal());
        List<FlowerHouseItemVO> resultData = buildFlowerHouseItemVOData(userId, pageSize, data);
        pageResult.setData(resultData);
        return pageResult;
    }

    private List<FlowerHouseItemVO> buildFlowerHouseItemVOData(int userId, int pageSize, List<FatSucculentCircle> data) {
        List<FlowerHouseItemVO> resultData = new ArrayList<>(pageSize);
        for (FatSucculentCircle circle : data) {
            int circleId = circle.getId();
            FlowerHouseItemVO vo = circleItemView(userId, circleId);
            vo.setShareUrl(Constant.CIRCLE_SHARE_URL + circleId);
            resultData.add(vo);
        }
        return resultData;
    }

    @Override
    public void readAllPraise(int userId) {
        CustomSQL where = SQLCreator.where().cloumn(FatSucculentPraise.FIELD_TO_USER_ID).operator(ESQLOperator.EQ).v
                (userId).operator(ESQLOperator.AND).cloumn(FatSucculentPraise.FIELD_IS_READ).operator(ESQLOperator
                .EQ).v(Constant.WrapperExtend.ZERO);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentPraise.FIELD_IS_READ, 1));
        factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatSucculentPraise.class, param, where);
    }

    @Override
    public void readAllFollow(int userId) {
        CustomSQL where = SQLCreator.where().cloumn(FatSucculentFollow.FIELD_BE_FOLLOW_USER_ID).operator(ESQLOperator.EQ).v
                (userId).operator(ESQLOperator.AND).cloumn(FatSucculentFollow.FIELD_IS_READ).operator(ESQLOperator
                .EQ).v(Constant.WrapperExtend.ZERO);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatSucculentFollow.FIELD_IS_READ, 1));
        factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatSucculentFollow.class, param, where);
    }

    @Override
    public Pagination<FatSucculentReport> pageReport(Map<String, Object> params) {
        Param param = ParamBuilder.getInstance().getParam().add(params).add(ParamBuilder.nv(Constant.SQLConstants
                .ORDER_COLUMN, FatSucculentReport.FIELD_CREATE_TIME)).add(ParamBuilder.nv(Constant.SQLConstants
                .ORDER_BY_TYPE, Constant.SQLConstants.DESC));
        int pageNo = StringDefaultValue.intValue(params.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(params.get(Constant.PAGE_SIZE));
        return queryClassPagination(FatSucculentReport.class, param, pageNo, pageSize);
    }

    @Override
    public FatSucculentReport getReportById(int id) {
        return id(FatSucculentReport.class, id, ResultConstant.Circle.CIRCLE_REPORT_IS_NULL);
    }

    @Override
    @Transactional
    public void pass(int id, String feedBack) {
        //更新report为举报成功状态
        FatSucculentReport report = getReportById(id);
        report.setReportStatus(1);
        report.setReportFeedback(feedBack);
        factory.getCacheWriteDataSession().update(FatSucculentReport.class, report);
        //推送
        logger.info("=========>article_id:{}", report.getArticleId());
        FatSucculentCircle circle = getCircleById(report.getArticleId());
        FatCustomer customer = customerService.getCustomerById(circle.getUserId());
        PushMessage message = PushMessage.get()
                .title("举报反馈")
                .platform(PlatForm.ALL)
                .content(feedBack)
                .addAlias(customer.getUuid())
                .addExtras(Constant.PUSH_TYPE, Constant.PushType.COMMENT);
        push.push(PushPayloadBuilder.newInstance().build(message));
        //删除花房数据
        deleteCircle(report.getArticleId(), -2);
    }

    @Override
    public void unPass(int id) {
        FatSucculentReport report = getReportById(id);
        report.setReportStatus(2);
        factory.getCacheWriteDataSession().update(FatSucculentReport.class, report);
    }

}
