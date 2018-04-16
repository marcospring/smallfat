package com.smt.smallfat.service.base.impl;

import com.csyy.common.StringDefaultValue;
import com.csyy.core.apisupport.impl.BaseServiceImpl;
import com.csyy.core.datasource.param.*;
import com.csyy.core.exception.CommonException;
import com.csyy.core.obj.Pagination;
import com.csyy.core.utils.CommonBeanUtils;
import com.smt.smallfat.constant.Constant;
import com.smt.smallfat.constant.ResultConstant;
import com.smt.smallfat.po.*;
import com.smt.smallfat.service.base.ArticleService;
import com.smt.smallfat.service.base.CommentService;
import com.smt.smallfat.service.base.CustomerService;
import com.smt.smallfat.service.house.CircleService;
import com.smt.smallfat.utils.push.IPush;
import com.smt.smallfat.utils.push.PushMessage;
import com.smt.smallfat.utils.push.PushPayloadBuilder;
import com.smt.smallfat.utils.push.pushenum.PlatForm;
import com.smt.smallfat.vo.FatCommentVO;
import com.smt.smallfat.vo.house.CircleCommentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends BaseServiceImpl implements CommentService {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private IPush push;
    @Autowired
    private CircleService circleService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public FatCommentVO addComment(Map<String, Object> param) {
        FatComment comment = CommonBeanUtils.transMap2BasePO(param, FatComment.class);
        comment = factory.getCacheWriteDataSession().save(FatComment.class, comment);
        FatCommentVO commentVO = fillFatCommentVO(comment, comment.getCommentType());
        //推送
        String title;
        String titleOrContent;
        FatCustomer toCustomer = null;
        boolean pushFlag = false;
        if (commentVO.getArticle() != null) {
            title = "{0}在文章《{1}》中回复了你";
            titleOrContent = PushMessage.buildMessage(title, commentVO.getFromUser().getNickName(), commentVO
                    .getArticle().getTitle());
            if (commentVO.getToUser().getId() != 0) {
                toCustomer = commentVO.getToUser();
                pushFlag = !pushFlag;
            }
        } else {
            title = "{0}在{1}中回复了你";
            titleOrContent = PushMessage.buildMessage(title, commentVO.getFromUser().getNickName(), "花房");
            if (commentVO.getToUser().getId() != 0) {
                toCustomer = commentVO.getToUser();
            } else {
                toCustomer = customerService.getCustomerById(commentVO.getCircle().getCircle().getUserId());
                comment.setToUserid(toCustomer.getId());
                factory.getCacheWriteDataSession().update(FatComment.class, comment);
            }
            pushFlag = !pushFlag;
        }
        if (pushFlag) {
            PushMessage message = PushMessage.get()
                    .title(titleOrContent)
                    .platform(PlatForm.ALL)
                    .content(titleOrContent)
                    .addAlias(toCustomer.getUuid())
                    .addExtras(Constant.PUSH_TYPE, Constant.PushType.COMMENT);
            logger.info("================>:{}<=============", titleOrContent);
            push.push(PushPayloadBuilder.newInstance().build(message));
        }
        return commentVO;
    }

    private FatCommentVO fillFatCommentVO(FatComment comment, int commentType) {
        int fromUserId = comment.getFromUserid();
        int toUserId = comment.getToUserid();
        int articleId = comment.getArticleId();
        FatCustomer fromUser = customerService.getCustomerById(fromUserId);
        FatCustomer toUser = new FatCustomer();
        if (toUserId != 0)
            toUser = customerService.getCustomerById(toUserId);
        if (commentType == 0) {
            FatArticle article = articleService.getArticleById(articleId);
            return new FatCommentVO(comment, article, fromUser, toUser);
        } else {
            FatSucculentCircle circle = circleService.getCircleById(articleId);
            List<FatSucculentImage> imageList = circleService.getCircleImages(articleId);
            CircleCommentVO vo = new CircleCommentVO(circle, imageList.stream().filter(image -> image.getImgIndex() ==
                    1).findFirst().get());
            return new FatCommentVO(comment, vo, fromUser, toUser);
        }

    }

    @Override
    public void deleteComment(int id) {
        factory.getCacheWriteDataSession().physicalDelete(FatComment.class, id);
    }

    @Override
    public Pagination<FatCommentVO> pageComment(Map<String, Object> param) {
        int pageNo = StringDefaultValue.intValue(param.get(Constant.PAGE_NO));
        int pageSize = StringDefaultValue.intValue(param.get(Constant.PAGE_SIZE));
        //兼容花房评论列表
        int commentType = StringDefaultValue.intValue(param.get(FatComment.FIELD_COMMENT_TYPE));
        if (commentType == 0)
            param.put(FatComment.FIELD_COMMENT_TYPE, Constant.WrapperExtend.ZERO);
        Param params = ParamBuilder.getInstance().getParam().add(param);
        Pagination<FatComment> page = queryClassPagination(FatComment.class, params, pageNo, pageSize);
        Pagination<FatCommentVO> pageVO = fillCommentVO(page);
        return pageVO;
    }

    private Pagination<FatCommentVO> fillCommentVO(Pagination<FatComment> page) {
        List<FatComment> data = page.getData();
        List<FatCommentVO> dataVO = new ArrayList<>(data.size());
        for (FatComment comment : data) {
            FatCommentVO vo = fillFatCommentVO(comment, comment.getCommentType());
            dataVO.add(vo);
        }
        Pagination<FatCommentVO> pageVO = new Pagination<>(dataVO, page.getPageNo(), page.getPageSize());
        pageVO.setRecordsTotal(page.getRecordsTotal());
        return pageVO;
    }

    @Override
    public FatComment getCommentById(int id) {
        FatComment comment = factory.getCacheReadDataSession().querySingleResultById(FatComment.class, id);
        if (comment == null)
            throw new CommonException(ResultConstant.Comment.COMMENT_IS_NULL);
        return comment;
    }

    @Override
    public FatComment getCommentByUUID(String uuid) {
        FatComment comment = factory.getCacheReadDataSession().querySingleResultByUUID(FatComment.class, uuid);
        if (comment == null)
            throw new CommonException(ResultConstant.Comment.COMMENT_IS_NULL);
        return comment;
    }

    @Override
    public FatCommentVO getCommentVOById(int id) {
        FatComment comment = getCommentById(id);
        return fillFatCommentVO(comment, comment.getCommentType());
    }

    @Override
    public FatCommentVO getCommentByVOUUID(String uuid) {
        FatComment comment = getCommentByUUID(uuid);
        return fillFatCommentVO(comment, comment.getCommentType());
    }

    @Override
    public long getArticleCommentCount(int articleId) {
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatComment.FIELD_ARTICLE_ID, articleId));
        long count = factory.getCacheReadDataSession().queryListResultCount(FatComment.class, param);
        return count;
    }

    @Override
    public void readComment(String ids) {
        String[] idArray = ids.split(",");
        for (String idStr : idArray) {
            int id = StringDefaultValue.intValue(idStr);
            FatComment comment = getCommentById(id);
            comment.setIsRead(READ);
            factory.getCacheWriteDataSession().update(FatComment.class, comment);
        }

    }

    @Override
    public Pagination<FatCommentVO> myComment(int userId, int pageNo, int pageSize) {
        CustomSQL where = SQLCreator.where().cloumn(FatComment.FIELD_DISABLED).operator(ESQLOperator.EQ).v
                (Constant.WrapperExtend.ZERO).operator(ESQLOperator.AND).cloumn(FatComment
                .FIELD_FROM_USERID).operator(ESQLOperator.EQ).v(userId).operator(ESQLOperator.OR)
                .cloumn(FatComment.FIELD_TO_USERID).operator(ESQLOperator.EQ).v(userId).operator(ESQLOperator.ORDER_BY)
                .cloumn(FatComment.FIELD_UPDATE_TIME).operator(ESQLOperator.DESC);
        Pagination<FatComment> page = queryClassPagination(FatComment.class, where, pageNo, pageSize);
        Pagination<FatCommentVO> pageVO = fillCommentVO(page);
        return pageVO;
    }

    @Override
    public long nonReadCommentCount(int userId) {
        CustomSQL where = SQLCreator.where().cloumn(FatComment.FIELD_DISABLED).operator(ESQLOperator.EQ).v
                (Constant.WrapperExtend.ZERO).operator(ESQLOperator.AND).cloumn(FatComment.FIELD_TO_USERID)
                .operator(ESQLOperator.EQ).v(userId).operator(ESQLOperator.AND).cloumn(FatComment
                        .FIELD_IS_READ).operator(ESQLOperator.EQ).v(Constant.WrapperExtend.ZERO);
        return factory.getCacheReadDataSession().queryListResultCountByWhere(FatComment.class, where);
    }

    @Override
    public void readAllUserComment(int userId) {
        CustomSQL where = SQLCreator.where().cloumn(FatComment.FIELD_TO_USERID).operator(ESQLOperator.EQ).v(userId)
                .operator(ESQLOperator.AND).cloumn(FatComment.FIELD_IS_READ).operator(ESQLOperator.EQ).v
                        (Constant.WrapperExtend.ZERO);
        Param param = ParamBuilder.getInstance().getParam().add(ParamBuilder.nv(FatComment.FIELD_IS_READ, READ));
        factory.getCacheWriteDataSession().updateCustomColumnByWhere(FatComment.class, param, where);
    }
}
