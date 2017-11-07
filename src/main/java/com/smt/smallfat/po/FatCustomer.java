package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
import  java.util.Date;
/**
 * FatCustomer实体
 * 
 * @author 系统生成
 *
 */
public class FatCustomer extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_CUSTOMER";
	/**用户名*/
	private String  username = "";
	/**用户名 对应的静态变量值*/
	public static final String FIELD_USERNAME = "username";
	/**用户头像*/
	private String  userImg = "";
	/**用户头像 对应的静态变量值*/
	public static final String FIELD_USER_IMG = "userImg";
	/**密码*/
	private String  password = "";
	/**密码 对应的静态变量值*/
	public static final String FIELD_PASSWORD = "password";
	/**性别*/
	private Integer  sex = 0;
	/**性别 对应的静态变量值*/
	public static final String FIELD_SEX = "sex";
	/**生日*/
	private Date  birthday = new Date();
	/**生日 对应的静态变量值*/
	public static final String FIELD_BIRTHDAY = "birthday";
	/**用户来源*/
	private Integer  userFrom = 0;
	/**用户来源 对应的静态变量值*/
	public static final String FIELD_USER_FROM = "userFrom";
	/**用户电话*/
	private String  telephone = "";
	/**用户电话 对应的静态变量值*/
	public static final String FIELD_TELEPHONE = "telephone";
	/**电子邮件*/
	private String  email = "";
	/**电子邮件 对应的静态变量值*/
	public static final String FIELD_EMAIL = "email";
	/**昵称*/
	private String  nickName = "";
	/**昵称 对应的静态变量值*/
	public static final String FIELD_NICK_NAME = "nickName";
	/***/
	private String  openId = "";
	/** 对应的静态变量值*/
	public static final String FIELD_OPEN_ID = "openId";
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Integer getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(Integer userFrom) {
		this.userFrom = userFrom;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
