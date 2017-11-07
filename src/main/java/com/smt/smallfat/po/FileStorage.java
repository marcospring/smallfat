package com.smt.smallfat.po;

import com.csyy.core.obj.BasePo;

/**
 * FileStorage实体
 * 
 * @author 系统生成
 *
 */
public class FileStorage extends BasePo {
	private static final long serialVersionUID = 1L;
	/**表名*/
	public static final String tableName = "FILE_STORAGE";
	/**文件名*/
	private String  fileName = "";
	/**文件名 对应的静态变量值*/
	public static final String FIELD_FILE_NAME = "fileName";
	/**文件类型*/
	private String  fileType = "";
	/**文件类型 对应的静态变量值*/
	public static final String FIELD_FILE_TYPE = "fileType";
	/**文件大小*/
	private String  fileSize = "";
	/**文件大小 对应的静态变量值*/
	public static final String FIELD_FILE_SIZE = "fileSize";
	/**文件存储路径*/
	private String  fileUrl = "";
	/**文件存储路径 对应的静态变量值*/
	public static final String FIELD_FILE_URL = "fileUrl";
	/**0:未处理,1:已处理,2:已归档*/
	private int  status =0;
	/**0:未处理,1:已处理,2:已归档 对应的静态变量值*/
	public static final String FIELD_STATUS = "status";
	/**传入参数json串*/
	private String  params = "";
	/**传入参数json串 对应的静态变量值*/
	public static final String FIELD_PARAMS = "params";
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
}
