package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * SysDic实体
 * 
 * @author 系统生成
 *
 */
public class SysDic extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "SYS_DIC";
	/**字典名称*/
	private String  dicName = "";
	/**字典名称 对应的静态变量值*/
	public static final String FIELD_DIC_NAME = "dicName";
	/**字典编码*/
	private String  dicCode = "";
	/**字典编码 对应的静态变量值*/
	public static final String FIELD_DIC_CODE = "dicCode";
	/**系统ID*/
	private Integer  sysId = 0;
	/**系统ID 对应的静态变量值*/
	public static final String FIELD_SYS_ID = "sysId";
	/**父id*/
	private Integer  parentId = 0;
	/**父id 对应的静态变量值*/
	public static final String FIELD_PARENT_ID = "parentId";
	
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public Integer getSysId() {
		return sysId;
	}
	public void setSysId(Integer sysId) {
		this.sysId = sysId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
