package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatGoodsCategory实体
 * 
 * @author 系统生成
 *
 */
public class FatGoodsCategory extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_GOODS_CATEGORY";
	/**商品种类名称*/
	private String  categoryName = "";
	/**商品种类名称 对应的静态变量值*/
	public static final String FIELD_CATEGORY_NAME = "categoryName";
	/**种类图标*/
	private String  categoryIcon = "";
	/**种类图标 对应的静态变量值*/
	public static final String FIELD_CATEGORY_ICON = "categoryIcon";
	/**父ID*/
	private Integer  parentId = 0;
	/**父ID 对应的静态变量值*/
	public static final String FIELD_PARENT_ID = "parentId";
	/**发布状态：0未发布；1发布；2下架*/
	private Integer  categoryStatus = 0;
	/**发布状态：0未发布；1发布；2下架 对应的静态变量值*/
	public static final String FIELD_CATEGORY_STATUS = "categoryStatus";
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryIcon() {
		return categoryIcon;
	}
	public void setCategoryIcon(String categoryIcon) {
		this.categoryIcon = categoryIcon;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getCategoryStatus() {
		return categoryStatus;
	}
	public void setCategoryStatus(Integer categoryStatus) {
		this.categoryStatus = categoryStatus;
	}
}
