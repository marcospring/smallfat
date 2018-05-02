package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
import  java.util.Date;
/**
 * SysAppVersion实体
 * 
 * @author 系统生成
 *
 */
public class SysAppVersion extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "SYS_APP_VERSION";
	/**系统类型（1.Android  2.iOS）*/
	private Integer  sysType = 0;
	/**系统类型（1.Android  2.iOS） 对应的静态变量值*/
	public static final String FIELD_SYS_TYPE = "sysType";
	/**版本号*/
	private String  versionNo = "";
	/**版本号 对应的静态变量值*/
	public static final String FIELD_VERSION_NO = "versionNo";
	/**app更新时间*/
	private Date  appUpdateDate = new Date();
	/**app更新时间 对应的静态变量值*/
	public static final String FIELD_APP_UPDATE_DATE = "appUpdateDate";
	/**是否强制更新（1.不强制；2.强制）*/
	private Integer  isForce = 0;
	/**是否强制更新（1.不强制；2.强制） 对应的静态变量值*/
	public static final String FIELD_IS_FORCE = "isForce";
	/**是否可用（1.启用；2.禁用）*/
	private Integer  status = 0;
	/**是否可用（1.启用；2.禁用） 对应的静态变量值*/
	public static final String FIELD_STATUS = "status";
	/**版本升级说明*/
	private String  memo = "";
	/**版本升级说明 对应的静态变量值*/
	public static final String FIELD_MEMO = "memo";
	/**app类型(1.麦芽app)*/
	private Integer  appType = 0;
	/**app类型(1.麦芽app) 对应的静态变量值*/
	public static final String FIELD_APP_TYPE = "appType";
	/**标题*/
	private String  title = "";
	/**标题 对应的静态变量值*/
	public static final String FIELD_TITLE = "title";
	/**左按钮*/
	private String  leftButton = "";
	/**左按钮 对应的静态变量值*/
	public static final String FIELD_LEFT_BUTTON = "leftButton";
	/**右按钮*/
	private String  rightButton = "";
	/**右按钮 对应的静态变量值*/
	public static final String FIELD_RIGHT_BUTTON = "rightButton";
	/**下载地址*/
	private String  downloadAddress = "";
	/**下载地址 对应的静态变量值*/
	public static final String FIELD_DOWNLOAD_ADDRESS = "downloadAddress";
	
	public Integer getSysType() {
		return sysType;
	}
	public void setSysType(Integer sysType) {
		this.sysType = sysType;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public Date getAppUpdateDate() {
		return appUpdateDate;
	}
	public void setAppUpdateDate(Date appUpdateDate) {
		this.appUpdateDate = appUpdateDate;
	}
	public Integer getIsForce() {
		return isForce;
	}
	public void setIsForce(Integer isForce) {
		this.isForce = isForce;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Integer getAppType() {
		return appType;
	}
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLeftButton() {
		return leftButton;
	}
	public void setLeftButton(String leftButton) {
		this.leftButton = leftButton;
	}
	public String getRightButton() {
		return rightButton;
	}
	public void setRightButton(String rightButton) {
		this.rightButton = rightButton;
	}
	public String getDownloadAddress() {
		return downloadAddress;
	}
	public void setDownloadAddress(String downloadAddress) {
		this.downloadAddress = downloadAddress;
	}
}
