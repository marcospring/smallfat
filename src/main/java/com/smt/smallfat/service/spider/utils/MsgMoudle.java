package com.smt.smallfat.service.spider.utils;

public class MsgMoudle {
	private String msg;
	private String content;
	public String getMsg() {
		return msg;
	}
	public MsgMoudle(String msg,String content) {
		this.msg = msg;
		this.content = content;
	}
	public MsgMoudle() {
		
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
