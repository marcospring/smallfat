package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatSucculentImage实体
 * 
 * @author 系统生成
 *
 */
public class FatSucculentImage extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SUCCULENT_IMAGE";
	/**发布用户id*/
	private Integer  userId = 0;
	/**发布用户id 对应的静态变量值*/
	public static final String FIELD_USER_ID = "userId";
	/**花房文章id*/
	private Integer  circleId = 0;
	/**花房文章id 对应的静态变量值*/
	public static final String FIELD_CIRCLE_ID = "circleId";
	/**图片索引*/
	private Integer  imgIndex = 0;
	/**图片索引 对应的静态变量值*/
	public static final String FIELD_IMG_INDEX = "imgIndex";
	/**图片路径*/
	private String  imgUrl = "";
	/**图片路径 对应的静态变量值*/
	public static final String FIELD_IMG_URL = "imgUrl";
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Integer getImgIndex() {
		return imgIndex;
	}
	public void setImgIndex(Integer imgIndex) {
		this.imgIndex = imgIndex;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
