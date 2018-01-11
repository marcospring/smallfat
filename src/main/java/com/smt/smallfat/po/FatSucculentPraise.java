package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatSucculentPraise实体
 * 
 * @author 系统生成
 *
 */
public class FatSucculentPraise extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SUCCULENT_PRAISE";
	/**花房文章ID*/
	private Integer  circleId = 0;
	/**花房文章ID 对应的静态变量值*/
	public static final String FIELD_CIRCLE_ID = "circleId";
	/**点赞用户ID*/
	private Integer  fromUserId = 0;
	/**点赞用户ID 对应的静态变量值*/
	public static final String FIELD_FROM_USER_ID = "fromUserId";
	/**被点赞用户ID*/
	private Integer  toUserId = 0;
	/**被点赞用户ID 对应的静态变量值*/
	public static final String FIELD_TO_USER_ID = "toUserId";
	
	public Integer getCircleId() {
		return circleId;
	}
	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
}
