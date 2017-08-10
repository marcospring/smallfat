package com.smt.smallfat.po;
import com.csyy.core.obj.BasePo;
/**
 * SysRole实体
 * 
 * @author 系统生成
 *
 */
public class SysRole extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "SYS_ROLE";
	/**角色名称*/
	private String  roleName = "";
	/**角色名称 对应的静态变量值*/
	public static final String FIELD_ROLE_NAME = "roleName";
	/**状态 0启用 1禁用*/
	private Integer  status = 0;
	/**状态 0启用 1禁用 对应的静态变量值*/
	public static final String FIELD_STATUS = "status";
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
