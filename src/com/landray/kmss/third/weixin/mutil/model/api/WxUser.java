package com.landray.kmss.third.weixin.mutil.model.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class WxUser {
	@JSONField(name = "userid")
	private String userId;
	private String name;
	@JSONField(name = "department")
	private Long[] departIds;
	private Long[] order;
	private String position;
	private String mobile;
	private String gender;
	private String telephone;
	private String email;
	@JSONField(name = "qr_code")
	private String weiXinId;
	private String avatar;
	private Integer status;
	private final List<Attr> extAttrs = new ArrayList();
	private Integer enable;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long[] getOrder() {
		return this.order;
	}

	public void setOrder(Long[] order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long[] getDepartIds() {
		return departIds;
	}

	public void setDepartIds(Long[] departIds) {
		this.departIds = departIds;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeiXinId() {
		return weiXinId;
	}

	public void setWeiXinId(String weiXinId) {
		this.weiXinId = weiXinId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public List<Attr> getExtAttrs() {
		return extAttrs;
	}

	public static class Attr {
		private String name;
		private String value;

		public Attr(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
