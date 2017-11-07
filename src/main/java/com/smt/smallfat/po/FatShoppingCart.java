package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

import java.math.BigDecimal;

/**
 * FatShoppingCart实体
 * 
 * @author 系统生成
 *
 */
public class FatShoppingCart extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SHOPPING_CART";
	/**用户id*/
	private Integer  userId = 0;
	/**用户id 对应的静态变量值*/
	public static final String FIELD_USER_ID = "userId";
	/**商品id*/
	private Integer  goodsDetailId = 0;
	/**商品id 对应的静态变量值*/
	public static final String FIELD_GOODS_DETAIL_ID = "goodsDetailId";
	/**商品数量*/
	private Integer  goodsCount = 0;
	/**商品数量 对应的静态变量值*/
	public static final String FIELD_GOODS_COUNT = "goodsCount";
	/**商品单价*/
	private BigDecimal  goodsPrice = new BigDecimal(0);
	/**商品单价 对应的静态变量值*/
	public static final String FIELD_GOODS_PRICE = "goodsPrice";
	/**商品总价*/
	private BigDecimal  goodsTotalPrice = new BigDecimal(0);
	/**商品总价 对应的静态变量值*/
	public static final String FIELD_GOODS_TOTAL_PRICE = "goodsTotalPrice";
	/**是否选中*/
	private Integer  isSelected = 0;
	/**是否选中 对应的静态变量值*/
	public static final String FIELD_IS_SELECTED = "isSelected";
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGoodsDetailId() {
		return goodsDetailId;
	}
	public void setGoodsDetailId(Integer goodsDetailId) {
		this.goodsDetailId = goodsDetailId;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public BigDecimal getGoodsTotalPrice() {
		return goodsTotalPrice;
	}
	public void setGoodsTotalPrice(BigDecimal goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}
	public Integer getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}
}
