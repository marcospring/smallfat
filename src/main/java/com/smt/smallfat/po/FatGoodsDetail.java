package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

import java.math.BigDecimal;

/**
 * FatGoodsDetail实体
 * 
 * @author 系统生成
 *
 */
public class FatGoodsDetail extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_GOODS_DETAIL";
	/**价格*/
	private BigDecimal  price = new BigDecimal(0);
	/**价格 对应的静态变量值*/
	public static final String FIELD_PRICE = "price";
	/**库存*/
	private Integer  stock = 0;
	/**库存 对应的静态变量值*/
	public static final String FIELD_STOCK = "stock";
	/**大小*/
	private String  modelSize = "";
	/**大小 对应的静态变量值*/
	public static final String FIELD_MODEL_SIZE = "modelSize";
	/**运费*/
	private BigDecimal  carriage = new BigDecimal(0);
	/**运费 对应的静态变量值*/
	public static final String FIELD_CARRIAGE = "carriage";
	/**重量*/
	private BigDecimal  weight = new BigDecimal(0);
	/**重量 对应的静态变量值*/
	public static final String FIELD_WEIGHT = "weight";
	/**商品Id*/
	private Integer  goodsId = 0;
	/**商品Id 对应的静态变量值*/
	public static final String FIELD_GOODS_ID = "goodsId";
	/**乐观锁*/
	private String  detailLock = "";
	/**乐观锁 对应的静态变量值*/
	public static final String FIELD_DETAIL_LOCK = "detailLock";
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getModelSize() {
		return modelSize;
	}
	public void setModelSize(String modelSize) {
		this.modelSize = modelSize;
	}
	public BigDecimal getCarriage() {
		return carriage;
	}
	public void setCarriage(BigDecimal carriage) {
		this.carriage = carriage;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public String getDetailLock() {
		return detailLock;
	}
	public void setDetailLock(String detailLock) {
		this.detailLock = detailLock;
	}
}
