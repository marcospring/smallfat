package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatFreightBodyArea实体
 * 
 * @author 系统生成
 *
 */
public class FatFreightBodyArea extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_FREIGHT_BODY_AREA";
	/**表体ID*/
	private Integer  bodyId = 0;
	/**表体ID 对应的静态变量值*/
	public static final String FIELD_BODY_ID = "bodyId";
	/**省市县code*/
	private String  areaCode = "";
	/**省市县code 对应的静态变量值*/
	public static final String FIELD_AREA_CODE = "areaCode";
	
	public Integer getBodyId() {
		return bodyId;
	}
	public void setBodyId(Integer bodyId) {
		this.bodyId = bodyId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
