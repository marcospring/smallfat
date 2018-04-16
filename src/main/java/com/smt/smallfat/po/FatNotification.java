package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatNotification实体
 * 
 * @author 系统生成
 *
 */
public class FatNotification extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_NOTIFICATION";
	/**用户id*/
	private Integer  userId = 0;
	/**用户id 对应的静态变量值*/
	public static final String FIELD_USER_ID = "userId";
	/**用户id*/
	private String  title = "";
	/**用户id 对应的静态变量值*/
	public static final String FIELD_TITLE = "title";
	/**昵称*/
	private String  nickName = "";
	/**昵称 对应的静态变量值*/
	public static final String FIELD_NICK_NAME = "nickName";
	/**是否已读*/
	private Integer  isRead = 0;
	/**是否已读 对应的静态变量值*/
	public static final String FIELD_IS_READ = "isRead";
	/**通知内容*/
	private String  content = "";
	/**通知内容 对应的静态变量值*/
	public static final String FIELD_CONTENT = "content";
	/**是否推送*/
	private Integer  isPush = 0;
	/**是否推送 对应的静态变量值*/
	public static final String FIELD_IS_PUSH = "isPush";
	/**是否全部推送*/
	private Integer  isAll = 0;
	/**是否全部推送 对应的静态变量值*/
	public static final String FIELD_IS_ALL = "isAll";
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsPush() {
		return isPush;
	}
	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}
	public Integer getIsAll() {
		return isAll;
	}
	public void setIsAll(Integer isAll) {
		this.isAll = isAll;
	}
}
