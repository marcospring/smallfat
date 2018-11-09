package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatCustomerAddress实体
 * 
 * @author 系统生成
 *
 */
public class FatCustomerAddress extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_CUSTOMER_ADDRESS";
	/**收件人姓名*/
	private String  receiveName = "";
	/**收件人姓名 对应的静态变量值*/
	public static final String FIELD_RECEIVE_NAME = "receiveName";
	/**收件人电话*/
	private String  mobileNumber = "";
	/**收件人电话 对应的静态变量值*/
	public static final String FIELD_MOBILE_NUMBER = "mobileNumber";
	/**收件人省市县*/
	private String  areaAddress = "";
	/**收件人省市县 对应的静态变量值*/
	public static final String FIELD_AREA_ADDRESS = "areaAddress";
	/**具体地址*/
	private String  address = "";
	/**具体地址 对应的静态变量值*/
	public static final String FIELD_ADDRESS = "address";
	/**是否默认*/
	private Integer  isDefault = 0;
	/**是否默认 对应的静态变量值*/
	public static final String FIELD_IS_DEFAULT = "isDefault";
	/**客户ID*/
	private Integer  userId = 0;
	/**客户ID 对应的静态变量值*/
	public static final String FIELD_USER_ID = "userId";
	/**省CODE*/
	private String  areaCode = "";
	/**省CODE 对应的静态变量值*/
	public static final String FIELD_AREA_CODE = "areaCode";
	
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getAreaAddress() {
		return areaAddress;
	}
	public void setAreaAddress(String areaAddress) {
		this.areaAddress = areaAddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
