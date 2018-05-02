package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;
/**
 * FatSucculentReport实体
 * 
 * @author 系统生成
 *
 */
public class FatSucculentReport extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FAT_SUCCULENT_REPORT";
	/**举报内容*/
	private String  reportContent = "";
	/**举报内容 对应的静态变量值*/
	public static final String FIELD_REPORT_CONTENT = "reportContent";
	/**举报原因，逗号隔开*/
	private String  reportReason = "";
	/**举报原因，逗号隔开 对应的静态变量值*/
	public static final String FIELD_REPORT_REASON = "reportReason";
	/**举报用户ID*/
	private Integer  reportUserId = 0;
	/**举报用户ID 对应的静态变量值*/
	public static final String FIELD_REPORT_USER_ID = "reportUserId";
	/**被举报内容ID*/
	private Integer  articleId = 0;
	/**被举报内容ID 对应的静态变量值*/
	public static final String FIELD_ARTICLE_ID = "articleId";
	/**举报状态：0新建；1举报成功；2举报驳回*/
	private Integer  reportStatus = 0;
	/**举报状态：0新建；1举报成功；2举报驳回 对应的静态变量值*/
	public static final String FIELD_REPORT_STATUS = "reportStatus";
	/**举报反馈*/
	private String  reportFeedback = "";
	/**举报反馈 对应的静态变量值*/
	public static final String FIELD_REPORT_FEEDBACK = "reportFeedback";
	
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public String getReportReason() {
		return reportReason;
	}
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	public Integer getReportUserId() {
		return reportUserId;
	}
	public void setReportUserId(Integer reportUserId) {
		this.reportUserId = reportUserId;
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getReportFeedback() {
		return reportFeedback;
	}
	public void setReportFeedback(String reportFeedback) {
		this.reportFeedback = reportFeedback;
	}
}
