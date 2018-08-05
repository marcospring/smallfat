package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
import  java.util.Date;
/**
 * FatGoodsTheme实体
 * 
 * @author 系统生成
 *
 */
public class FatGoodsTheme extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_GOODS_THEME";
	/**主题标题*/
	private String  themeTitle = "";
	/**主题标题 对应的静态变量值*/
	public static final String FIELD_THEME_TITLE = "themeTitle";
	/**主题图片*/
	private String  themeImage = "";
	/**主题图片 对应的静态变量值*/
	public static final String FIELD_THEME_IMAGE = "themeImage";
	/**发布状态：0未发布；1发布；2下架*/
	private Integer  publishStatus = 0;
	/**发布状态：0未发布；1发布；2下架 对应的静态变量值*/
	public static final String FIELD_PUBLISH_STATUS = "publishStatus";
	/**发布时间*/
	private Date  publishTime = new Date();
	/**发布时间 对应的静态变量值*/
	public static final String FIELD_PUBLISH_TIME = "publishTime";
	/**推荐状态：0不推荐；1推荐*/
	private Integer  recommendStatus = 0;
	/**推荐状态：0不推荐；1推荐 对应的静态变量值*/
	public static final String FIELD_RECOMMEND_STATUS = "recommendStatus";
	
	public String getThemeTitle() {
		return themeTitle;
	}
	public void setThemeTitle(String themeTitle) {
		this.themeTitle = themeTitle;
	}
	public String getThemeImage() {
		return themeImage;
	}
	public void setThemeImage(String themeImage) {
		this.themeImage = themeImage;
	}
	public Integer getPublishStatus() {
		return publishStatus;
	}
	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getRecommendStatus() {
		return recommendStatus;
	}
	public void setRecommendStatus(Integer recommendStatus) {
		this.recommendStatus = recommendStatus;
	}
}
