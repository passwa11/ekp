package com.landray.kmss.km.imeeting.integrate.interfaces;

public class CommonVideoMettingPerson {

	public CommonVideoMettingPerson(String name, String loginName) {
		this.name = name;
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}

	public boolean isExternal() {
		return isExternal;
	}

	private String name;

	private String loginName;

	private String email;

	private String mobile;
	
	private boolean isExternal = false;

}
