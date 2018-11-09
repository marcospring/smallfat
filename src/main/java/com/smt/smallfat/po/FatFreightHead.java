package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatFreightHead实体
 * 
 * @author 系统生成
 *
 */
public class FatFreightHead extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_FREIGHT_HEAD";
	/**商户ID*/
	private Integer  shopId = 0;
	/**商户ID 对应的静态变量值*/
	public static final String FIELD_SHOP_ID = "shopId";
	/**快递公司code*/
	private String  companyCode = "";
	/**快递公司code 对应的静态变量值*/
	public static final String FIELD_COMPANY_CODE = "companyCode";
	/**快递类型，KG，件*/
	private Integer  expressType = 0;
	/**快递类型，KG，件 对应的静态变量值*/
	public static final String FIELD_EXPRESS_TYPE = "expressType";
	
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Integer getExpressType() {
		return expressType;
	}
	public void setExpressType(Integer expressType) {
		this.expressType = expressType;
	}
}
