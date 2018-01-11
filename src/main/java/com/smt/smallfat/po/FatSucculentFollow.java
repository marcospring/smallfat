package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatSucculentFollow实体
 * 
 * @author 系统生成
 *
 */
public class FatSucculentFollow extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SUCCULENT_FOLLOW";
	/**关注用户ID*/
	private Integer  followUserId = 0;
	/**关注用户ID 对应的静态变量值*/
	public static final String FIELD_FOLLOW_USER_ID = "followUserId";
	/**被关注用户ID*/
	private Integer  beFollowUserId = 0;
	/**被关注用户ID 对应的静态变量值*/
	public static final String FIELD_BE_FOLLOW_USER_ID = "beFollowUserId";
	
	public Integer getFollowUserId() {
		return followUserId;
	}
	public void setFollowUserId(Integer followUserId) {
		this.followUserId = followUserId;
	}
	public Integer getBeFollowUserId() {
		return beFollowUserId;
	}
	public void setBeFollowUserId(Integer beFollowUserId) {
		this.beFollowUserId = beFollowUserId;
	}
}
