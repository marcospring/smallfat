package com.smt.smallfat.service;

import com.csyy.core.obj.Pagination;
import com.smt.smallfat.po.FatComment;
import com.smt.smallfat.vo.FatCommentVO;

import java.util.Map;

public interface CommentService {
    int READ=1;

    FatCommentVO addComment(Map<String, Object> param);

    void deleteComment(int id);

    Pagination<FatCommentVO> pageComment(Map<String, Object> param);

    FatCommentVO getCommentVOById(int id);

    FatCommentVO getCommentByVOUUID(String uuid);

    FatComment getCommentById(int id);

    FatComment getCommentByUUID(String uuid);

    long getArticleCommentCount(int articleId);

    void readComment(String ids);

    Pagination<FatCommentVO> myComment(int userId,int pageNo,int pageSize);

    long nonReadCommentCount(int userId);
}
