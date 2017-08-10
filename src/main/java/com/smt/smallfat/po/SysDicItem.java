package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * SysDicItem实体
 * 
 * @author 系统生成
 *
 */
public class SysDicItem extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "SYS_DIC_ITEM";
	/**字典ID*/
	private Integer  dicId = 0;
	/**字典ID 对应的静态变量值*/
	public static final String FIELD_DIC_ID = "dicId";
	/**字典项值*/
	private String  dicItemValue = "";
	/**字典项值 对应的静态变量值*/
	public static final String FIELD_DIC_ITEM_VALUE = "dicItemValue";
	/**字典项名称*/
	private String  dicItemName = "";
	/**字典项名称 对应的静态变量值*/
	public static final String FIELD_DIC_ITEM_NAME = "dicItemName";
	
	public Integer getDicId() {
		return dicId;
	}
	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}
	public String getDicItemValue() {
		return dicItemValue;
	}
	public void setDicItemValue(String dicItemValue) {
		this.dicItemValue = dicItemValue;
	}
	public String getDicItemName() {
		return dicItemName;
	}
	public void setDicItemName(String dicItemName) {
		this.dicItemName = dicItemName;
	}
}
