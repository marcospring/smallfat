package com.smt.smallfat.exception;

public class SpiderFileNotExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path = "";
	public SpiderFileNotExistsException(String path){
		this.path = path;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
