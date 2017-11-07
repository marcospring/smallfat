package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatSuggest实体
 * 
 * @author 系统生成
 *
 */
public class FatSuggest extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SUGGEST";
	/**用户id*/
	private Integer  userId = 0;
	/**用户id 对应的静态变量值*/
	public static final String FIELD_USER_ID = "userId";
	/**昵称*/
	private String  nickName = "";
	/**昵称 对应的静态变量值*/
	public static final String FIELD_NICK_NAME = "nickName";
	/**电话*/
	private String  mobileTelephone = "";
	/**电话 对应的静态变量值*/
	public static final String FIELD_MOBILE_TELEPHONE = "mobileTelephone";
	/**反馈内容*/
	private String  content = "";
	/**反馈内容 对应的静态变量值*/
	public static final String FIELD_CONTENT = "content";
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getMobileTelephone() {
		return mobileTelephone;
	}
	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
