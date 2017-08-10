package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatSpider实体
 * 
 * @author 系统生成
 *
 */
public class FatSpider extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SPIDER";
	/**标题*/
	private String  title = "";
	/**标题 对应的静态变量值*/
	public static final String FIELD_TITLE = "title";
	/**小图*/
	private String  cover = "";
	/**小图 对应的静态变量值*/
	public static final String FIELD_COVER = "cover";
	/**内容*/
	private String  content = "";
	/**内容 对应的静态变量值*/
	public static final String FIELD_CONTENT = "content";
	/**作者*/
	private String  tag = "";
	/**作者 对应的静态变量值*/
	public static final String FIELD_TAG = "tag";
	/**文章链接*/
	private String  spiderUrl = "";
	/**文章链接 对应的静态变量值*/
	public static final String FIELD_SPIDER_URL = "spiderUrl";
	/**是否推送到精选*/
	private Integer  isArticle = 0;
	/**是否推送到精选 对应的静态变量值*/
	public static final String FIELD_IS_ARTICLE = "isArticle";
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getSpiderUrl() {
		return spiderUrl;
	}
	public void setSpiderUrl(String spiderUrl) {
		this.spiderUrl = spiderUrl;
	}
	public Integer getIsArticle() {
		return isArticle;
	}
	public void setIsArticle(Integer isArticle) {
		this.isArticle = isArticle;
	}
}
