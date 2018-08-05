package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatGoodsThemeRelation实体
 * 
 * @author 系统生成
 *
 */
public class FatGoodsThemeRelation extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_GOODS_THEME_RELATION";
	/**商品ID*/
	private Integer  goodsId = 0;
	/**商品ID 对应的静态变量值*/
	public static final String FIELD_GOODS_ID = "goodsId";
	/**主题ID*/
	private Integer  themeId = 0;
	/**主题ID 对应的静态变量值*/
	public static final String FIELD_THEME_ID = "themeId";
	
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getThemeId() {
		return themeId;
	}
	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}
}
