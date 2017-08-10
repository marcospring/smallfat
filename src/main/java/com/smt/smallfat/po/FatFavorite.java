package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatFavorite实体
 * 
 * @author 系统生成
 *
 */
public class FatFavorite extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_FAVORITE";
	/**用户id*/
	private Integer  userId = 0;
	/**用户id 对应的静态变量值*/
	public static final String FIELD_USER_ID = "userId";
	/**文章id*/
	private Integer  articleId = 0;
	/**文章id 对应的静态变量值*/
	public static final String FIELD_ARTICLE_ID = "articleId";
	/**收藏类型*/
	private Integer  favoriteType = 0;
	/**收藏类型 对应的静态变量值*/
	public static final String FIELD_FAVORITE_TYPE = "favoriteType";
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getFavoriteType() {
		return favoriteType;
	}
	public void setFavoriteType(Integer favoriteType) {
		this.favoriteType = favoriteType;
	}
}
