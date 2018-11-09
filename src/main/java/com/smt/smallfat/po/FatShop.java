package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatShop实体
 * 
 * @author 系统生成
 *
 */
public class FatShop extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SHOP";
	/**商户名称*/
	private String  shopName = "";
	/**商户名称 对应的静态变量值*/
	public static final String FIELD_SHOP_NAME = "shopName";
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
