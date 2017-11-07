package com.smt.smallfat.vo;

import java.util.List;

/**
 * Created by zhangkui on 16/5/15.
 */
public class LoginVO {
    private String session;
    private SysUserVo user;
    private List<Integer> roles;
	private String isAdmin="0"; // 1是;0不是.
	private String isChannel = "0";// 渠道人员  1是;0不是.
	private List<String> channelNo ;//渠道编号

	public LoginVO() {
	}

	public LoginVO(String session, SysUserVo user, List<Integer> roles, String isAdmin) {
		this.session = session;
		this.user = user;
		this.roles = roles;
		this.isAdmin = isAdmin;
	}

	public LoginVO(String session, SysUserVo user, List<Integer> roles) {
		this.session = session;
		this.user = user;
		this.roles = roles;
	}

	public LoginVO(SysUserVo user, List<Integer> roles, String isAdmin, String isChannel, List<String> channelNo) {
		this.user = user;
		this.roles = roles;
		this.isAdmin = isAdmin;
		this.isChannel = isChannel;
		this.channelNo = channelNo;
	}

	public LoginVO(SysUserVo user, List<Integer> roles) {
		this.user = user;
		this.roles = roles;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public SysUserVo getUser() {
		return user;
	}

	public void setUser(SysUserVo user) {
		this.user = user;
	}

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getIsChannel() {
		return isChannel;
	}

	public void setIsChannel(String isChannel) {
		this.isChannel = isChannel;
	}

	public List<String> getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(List<String> channelNo) {
		this.channelNo = channelNo;
	}
}
