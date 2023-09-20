package com.landray.kmss.third.weixin.work.model.api;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class WxUser {
	@JSONField(name = "userid")
	private String userId;
	private String name;
	private String alias; // 别名
	@JSONField(name = "department")
	private Long[] departIds;
	private Long[] order;
	private String position;
	private String mobile;
	private String gender;
	private String telephone;
	private String email;
	@JSONField(name = "biz_mail")
	private String bizMail;
	private String address;
	@JSONField(name = "qr_code")
	private String weiXinId;
	private String avatar;
	private Integer status;
	@JSONField(name = "extattr")
	private ExtAttrsClass extattr;
	@JSONField(name = "is_leader_in_dept")
	private Long[] isLeaderInDept;

	@JSONField(name = "main_department")
	private Long mainDepartment;

	public String getOpenUserid() {
		return openUserid;
	}

	public void setOpenUserid(String openUserid) {
		this.openUserid = openUserid;
	}

	@JSONField(name = "open_userid")
	private String openUserid;

	public String getBizMail() {
		return bizMail;
	}

	public void setBizMail(String bizMail) {
		this.bizMail = bizMail;
	}

	public Long getMainDepartment() {
		return mainDepartment;
	}

	public void setMainDepartment(Long mainDepartment) {
		this.mainDepartment = mainDepartment;
	}

	public Long[] getIsLeaderInDept() {
		return isLeaderInDept;
	}

	public void setIsLeaderInDept(Long[] isLeaderInDept) {
		this.isLeaderInDept = isLeaderInDept;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ExtAttrsClass getExtattr() {
		return extattr;
	}

	public void setExtattr(ExtAttrsClass extattr) {
		this.extattr = extattr;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

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

	public void addExtAttr(String key, String val) {
		if (extattr == null) {
			extattr = new ExtAttrsClass(new ArrayList<Attr>());
		}
		Attr attr = new Attr(key, val);
		extattr.getAttrs().add(attr);
	}

	public void addExtAttr(int type, String name, String url, String title) {
		if (extattr == null) {
			extattr = new ExtAttrsClass(new ArrayList<Attr>());
		}
		Attr attr = new Attr(type, name, url, title);
		extattr.getAttrs().add(attr);
	}

	public static class Attr {
		@JSONField(name = "type")
		private int type;
		@JSONField(name = "name")
		private String name;
		@JSONField(name = "text")
		private TextClass text;
		@JSONField(name = "web")
		private WebClass web;


		public WebClass getWeb() {
			return web;
		}

		public void setWeb(WebClass web) {
			this.web = web;
		}

		public TextClass getText() {
			return text;
		}

		public void setText(TextClass text) {
			this.text = text;
		}


		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public Attr(String name, String value) {
			type = 0;
			this.name = name;
			this.text = new TextClass(value);
		}

		public Attr(int type, String name, String url, String title) {
			this.type = type;
			this.name = name;
			this.web = new WebClass(url, title);
		}

		public Attr() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public static class TextClass {

		@JSONField(name = "value")
		private String value;

		public TextClass(String value) {
			this.value = value;
		}

		public TextClass() {
			super();
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	public static class ExtAttrsClass {

		@JSONField(name = "attrs")
		private List<Attr> attrs = new ArrayList<Attr>();

		public ExtAttrsClass() {
			super();
		}

		public ExtAttrsClass(List<Attr> list) {
			this.attrs = list;
		}

		public List<Attr> getAttrs() {
			return attrs;
		}

		public void setAttrs(List<Attr> list) {
			this.attrs = list;
		}

	}



	public static class WebClass {
		@JSONField(name = "url")
		private String url;
		@JSONField(name = "title")
		private String title;

		public WebClass(String url, String title) {
			this.url = url;
			this.title = title;
		}

		public WebClass() {
			super();
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

	}

}
