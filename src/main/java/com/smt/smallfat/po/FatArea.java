package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatArea实体
 * 
 * @author 系统生成
 *
 */
public class FatArea extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_AREA";
	/**城市编码*/
	private String  areaCode = "";
	/**城市编码 对应的静态变量值*/
	public static final String FIELD_AREA_CODE = "areaCode";
	/**城市名称*/
	private String  areaName = "";
	/**城市名称 对应的静态变量值*/
	public static final String FIELD_AREA_NAME = "areaName";
	/**父id*/
	private Integer  parentId = 0;
	/**父id 对应的静态变量值*/
	public static final String FIELD_PARENT_ID = "parentId";
	/**城市等级*/
	private Integer  level = 0;
	/**城市等级 对应的静态变量值*/
	public static final String FIELD_LEVEL = "level";
	/***/
	private String  firstLetter = "";
	/** 对应的静态变量值*/
	public static final String FIELD_FIRST_LETTER = "firstLetter";
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
}
