package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatGoods实体
 * 
 * @author 系统生成
 *
 */
public class FatGoods extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_GOODS";
	/**商品名称*/
	private String  title = "";
	/**商品名称 对应的静态变量值*/
	public static final String FIELD_TITLE = "title";
	/**描述*/
	private String  memo = "";
	/**描述 对应的静态变量值*/
	public static final String FIELD_MEMO = "memo";
	/**简介(富文本)*/
	private String  summary = "";
	/**简介(富文本) 对应的静态变量值*/
	public static final String FIELD_SUMMARY = "summary";
	/**标签（多个字典数字，逗号隔开）*/
	private String  flag = "";
	/**标签（多个字典数字，逗号隔开） 对应的静态变量值*/
	public static final String FIELD_FLAG = "flag";
	/**快递公司，引用字典*/
	private String  expCompany = "";
	/**快递公司，引用字典 对应的静态变量值*/
	public static final String FIELD_EXP_COMPANY = "expCompany";
	/**是否推送*/
	private Integer  isPush = 0;
	/**是否推送 对应的静态变量值*/
	public static final String FIELD_IS_PUSH = "isPush";
	/**是否为推送至app*/
	private Integer  isApp = 0;
	/**是否为推送至app 对应的静态变量值*/
	public static final String FIELD_IS_APP = "isApp";
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getExpCompany() {
		return expCompany;
	}
	public void setExpCompany(String expCompany) {
		this.expCompany = expCompany;
	}
	public Integer getIsPush() {
		return isPush;
	}
	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	public Integer getIsApp() {
		return isApp;
	}
	public void setIsApp(Integer isApp) {
		this.isApp = isApp;
	}
}
