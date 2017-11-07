package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FatGoodsResource实体
 * 
 * @author 系统生成
 *
 */
public class FatGoodsResource extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_GOODS_RESOURCE";
	/**商品id*/
	private Integer  goodsId = 0;
	/**商品id 对应的静态变量值*/
	public static final String FIELD_GOODS_ID = "goodsId";
	/**资源类型，字典*/
	private Integer  resourceType = 0;
	/**资源类型，字典 对应的静态变量值*/
	public static final String FIELD_RESOURCE_TYPE = "resourceType";
	/**资源地址*/
	private String  resourceUrl = "";
	/**资源地址 对应的静态变量值*/
	public static final String FIELD_RESOURCE_URL = "resourceUrl";
	/**视频方向：1横，2竖*/
	private Integer  videoDirect = 0;
	/**视频方向：1横，2竖 对应的静态变量值*/
	public static final String FIELD_VIDEO_DIRECT = "videoDirect";
	
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getResourceType() {
		return resourceType;
	}
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public Integer getVideoDirect() {
		return videoDirect;
	}
	public void setVideoDirect(Integer videoDirect) {
		this.videoDirect = videoDirect;
	}
}
