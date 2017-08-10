package com.smt.smallfat.po;
import com.csyy.core.obj.BasePo;
/**
 * SysRolePermission实体
 * 
 * @author 系统生成
 *
 */
public class SysRolePermission extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "SYS_ROLE_PERMISSION";
	/**角色ID*/
	private Integer  roleId = 0;
	/**角色ID 对应的静态变量值*/
	public static final String FIELD_ROLE_ID = "roleId";
	/**权限ID*/
	private Integer  permissionId = 0;
	/**权限ID 对应的静态变量值*/
	public static final String FIELD_PERMISSION_ID = "permissionId";
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
}
