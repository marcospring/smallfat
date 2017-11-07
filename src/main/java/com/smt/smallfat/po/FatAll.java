package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatAll实体
 * 
 * @author 系统生成
 *
 */
public class FatAll extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_ALL";
	/**列表小图*/
	private String  littleImg = "";
	/**列表小图 对应的静态变量值*/
	public static final String FIELD_LITTLE_IMG = "littleImg";
	/**小图标题*/
	private String  littleTitle = "";
	/**小图标题 对应的静态变量值*/
	public static final String FIELD_LITTLE_TITLE = "littleTitle";
	/**收藏1是0否*/
	private Integer  isLove = 0;
	/**收藏1是0否 对应的静态变量值*/
	public static final String FIELD_IS_LOVE = "isLove";
	/**XX科*/
	private String  subject = "";
	/**XX科 对应的静态变量值*/
	public static final String FIELD_SUBJECT = "subject";
	/**XX属*/
	private String  category = "";
	/**XX属 对应的静态变量值*/
	public static final String FIELD_CATEGORY = "category";
	/**底层大图*/
	private String  bigImg = "";
	/**底层大图 对应的静态变量值*/
	public static final String FIELD_BIG_IMG = "bigImg";
	/**拉丁名称*/
	private String  latinName = "";
	/**拉丁名称 对应的静态变量值*/
	public static final String FIELD_LATIN_NAME = "latinName";
	/**科属描述*/
	private String  subjectDesc = "";
	/**科属描述 对应的静态变量值*/
	public static final String FIELD_SUBJECT_DESC = "subjectDesc";
	/**内容简介*/
	private String  description = "";
	/**内容简介 对应的静态变量值*/
	public static final String FIELD_DESCRIPTION = "description";
	/**点赞数*/
	private Integer  clickCount = 0;
	/**点赞数 对应的静态变量值*/
	public static final String FIELD_CLICK_COUNT = "clickCount";
	/**分享的url*/
	private String  shareUrl = "";
	/**分享的url 对应的静态变量值*/
	public static final String FIELD_SHARE_URL = "shareUrl";
	/**排序字母组合*/
	private String  orderCombin = "";
	/**排序字母组合 对应的静态变量值*/
	public static final String FIELD_ORDER_COMBIN = "orderCombin";

	public String getLittleImg() {
		return littleImg;
	}
	public void setLittleImg(String littleImg) {
		this.littleImg = littleImg;
	}
	public String getLittleTitle() {
		return littleTitle;
	}
	public void setLittleTitle(String littleTitle) {
		this.littleTitle = littleTitle;
	}
	public Integer getIsLove() {
		return isLove;
	}
	public void setIsLove(Integer isLove) {
		this.isLove = isLove;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBigImg() {
		return bigImg;
	}
	public void setBigImg(String bigImg) {
		this.bigImg = bigImg;
	}
	public String getLatinName() {
		return latinName;
	}
	public void setLatinName(String latinName) {
		this.latinName = latinName;
	}
	public String getSubjectDesc() {
		return subjectDesc;
	}
	public void setSubjectDesc(String subjectDesc) {
		this.subjectDesc = subjectDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getOrderCombin() {
		return orderCombin;
	}
	public void setOrderCombin(String orderCombin) {
		this.orderCombin = orderCombin;
	}
}
