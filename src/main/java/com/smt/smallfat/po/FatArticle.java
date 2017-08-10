package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatArticle实体
 * 
 * @author 系统生成
 *
 */
public class FatArticle extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_ARTICLE";
	/**标题*/
	private String  title = "";
	/**标题 对应的静态变量值*/
	public static final String FIELD_TITLE = "title";
	/**内容*/
	private String  content = "";
	/**内容 对应的静态变量值*/
	public static final String FIELD_CONTENT = "content";
	/**作者*/
	private String  author = "";
	/**作者 对应的静态变量值*/
	public static final String FIELD_AUTHOR = "author";
	/**小图*/
	private String  pic = "";
	/**小图 对应的静态变量值*/
	public static final String FIELD_PIC = "pic";
	/**阅读数量*/
	private Integer  readCount = 0;
	/**阅读数量 对应的静态变量值*/
	public static final String FIELD_READ_COUNT = "readCount";
	/**文章类型*/
	private Integer  articleType = 0;
	/**文章类型 对应的静态变量值*/
	public static final String FIELD_ARTICLE_TYPE = "articleType";
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getReadCount() {
		return readCount;
	}
	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}
	public Integer getArticleType() {
		return articleType;
	}
	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}
}
