package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
import  java.math.BigDecimal;
/**
 * FatFreightBody实体
 * 
 * @author 系统生成
 *
 */
public class FatFreightBody extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_FREIGHT_BODY";
	/**表头ID*/
	private Integer  headId = 0;
	/**表头ID 对应的静态变量值*/
	public static final String FIELD_HEAD_ID = "headId";
	/**首重/件*/
	private BigDecimal  firstCount = new BigDecimal(0);
	/**首重/件 对应的静态变量值*/
	public static final String FIELD_FIRST_COUNT = "firstCount";
	/**首重/件的价格*/
	private BigDecimal  firstPrice = new BigDecimal(0);
	/**首重/件的价格 对应的静态变量值*/
	public static final String FIELD_FIRST_PRICE = "firstPrice";
	/**继重/件*/
	private BigDecimal  otherCount = new BigDecimal(0);
	/**继重/件 对应的静态变量值*/
	public static final String FIELD_OTHER_COUNT = "otherCount";
	/**继重/件的价格*/
	private BigDecimal  otherPrice = new BigDecimal(0);
	/**继重/件的价格 对应的静态变量值*/
	public static final String FIELD_OTHER_PRICE = "otherPrice";
	
	public Integer getHeadId() {
		return headId;
	}
	public void setHeadId(Integer headId) {
		this.headId = headId;
	}
	public BigDecimal getFirstCount() {
		return firstCount;
	}
	public void setFirstCount(BigDecimal firstCount) {
		this.firstCount = firstCount;
	}
	public BigDecimal getFirstPrice() {
		return firstPrice;
	}
	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}
	public BigDecimal getOtherCount() {
		return otherCount;
	}
	public void setOtherCount(BigDecimal otherCount) {
		this.otherCount = otherCount;
	}
	public BigDecimal getOtherPrice() {
		return otherPrice;
	}
	public void setOtherPrice(BigDecimal otherPrice) {
		this.otherPrice = otherPrice;
	}
}
