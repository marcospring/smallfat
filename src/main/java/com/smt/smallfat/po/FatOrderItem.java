package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

import java.math.BigDecimal;

/**
 * FatOrderItem实体
 * 
 * @author 系统生成
 *
 */
public class FatOrderItem extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_ORDER_ITEM";
	/**商品id*/
	private Integer  goodsDetailId = 0;
	/**商品id 对应的静态变量值*/
	public static final String FIELD_GOODS_DETAIL_ID = "goodsDetailId";
	/**商品价格*/
	private BigDecimal  price = new BigDecimal(0);
	/**商品价格 对应的静态变量值*/
	public static final String FIELD_PRICE = "price";
	/**购买数量*/
	private Integer  totalCount = 0;
	/**购买数量 对应的静态变量值*/
	public static final String FIELD_TOTAL_COUNT = "totalCount";
	/**商品总价*/
	private BigDecimal  totalPrice = new BigDecimal(0);
	/**商品总价 对应的静态变量值*/
	public static final String FIELD_TOTAL_PRICE = "totalPrice";
	/**商品总重*/
	private BigDecimal  totalWeight = new BigDecimal(0);
	/**商品总重 对应的静态变量值*/
	public static final String FIELD_TOTAL_WEIGHT = "totalWeight";
	/**订单id*/
	private Integer  orderId = 0;
	/**订单id 对应的静态变量值*/
	public static final String FIELD_ORDER_ID = "orderId";
	/**客户id*/
	private Integer  customerId = 0;
	/**客户id 对应的静态变量值*/
	public static final String FIELD_CUSTOMER_ID = "customerId";
	
	public Integer getGoodsDetailId() {
		return goodsDetailId;
	}
	public void setGoodsDetailId(Integer goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}
