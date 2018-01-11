package com.smt.smallfat.service.spider.utils;

public enum SpiderType {
	TOUTIAO("toutiao","头条"),WECHAT("wechat","微信");
	private String path;
	private String name;
	SpiderType(String path,String name){
		this.path = path;
		this.name = name;
	}
	public static SpiderType getSpiderType(String path){
		for(SpiderType type : SpiderType.values()){
			if(path.equals(type.getPath()))
				return type;
		}
		return null;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

