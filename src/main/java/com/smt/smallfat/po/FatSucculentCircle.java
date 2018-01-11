package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatSucculentCircle实体
 * 
 * @author 系统生成
 *
 */
public class FatSucculentCircle extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SUCCULENT_CIRCLE";
	/**花房文章类型*/
	private Integer  articleType = 0;
	/**花房文章类型 对应的静态变量值*/
	public static final String FIELD_ARTICLE_TYPE = "articleType";
	/**花房文章内容*/
	private String  content = "";
	/**花房文章内容 对应的静态变量值*/
	public static final String FIELD_CONTENT = "content";
	/**用户ID*/
	private Integer  userId = 0;
	/**用户ID 对应的静态变量值*/
	public static final String FIELD_USER_ID = "userId";
	
	public Integer getArticleType() {
		return articleType;
	}
	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
