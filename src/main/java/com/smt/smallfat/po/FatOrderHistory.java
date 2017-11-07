package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatOrderHistory实体
 * 
 * @author 系统生成
 *
 */
public class FatOrderHistory extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_ORDER_HISTORY";
	/**订单状态*/
	private Integer  state = 0;
	/**订单状态 对应的静态变量值*/
	public static final String FIELD_STATE = "state";
	/**订单id*/
	private Integer  orderId = 0;
	/**订单id 对应的静态变量值*/
	public static final String FIELD_ORDER_ID = "orderId";
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
}
