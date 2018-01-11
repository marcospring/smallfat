package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatComment实体
 * 
 * @author 系统生成
 *
 */
public class FatComment extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_COMMENT";
	/**文章id*/
	private Integer  articleId = 0;
	/**文章id 对应的静态变量值*/
	public static final String FIELD_ARTICLE_ID = "articleId";
	/**评价用户*/
	private Integer  fromUserid = 0;
	/**评价用户 对应的静态变量值*/
	public static final String FIELD_FROM_USERID = "fromUserid";
	/**被评价用户*/
	private Integer  toUserid = 0;
	/**被评价用户 对应的静态变量值*/
	public static final String FIELD_TO_USERID = "toUserid";
	/**评论内容*/
	private String  content = "";
	/**评论内容 对应的静态变量值*/
	public static final String FIELD_CONTENT = "content";
	/**是否已读*/
	private Integer  isRead = 0;
	/**是否已读 对应的静态变量值*/
	public static final String FIELD_IS_READ = "isRead";
	/**评论类型*/
	private Integer  commentType = 0;
	/**评论类型 对应的静态变量值*/
	public static final String FIELD_COMMENT_TYPE = "commentType";
	
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getFromUserid() {
		return fromUserid;
	}
	public void setFromUserid(Integer fromUserid) {
		this.fromUserid = fromUserid;
	}
	public Integer getToUserid() {
		return toUserid;
	}
	public void setToUserid(Integer toUserid) {
		this.toUserid = toUserid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getCommentType() {
		return commentType;
	}
	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}
}
