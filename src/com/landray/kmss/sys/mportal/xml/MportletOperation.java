package com.landray.kmss.sys.mportal.xml;

import com.landray.kmss.util.ResourceUtil;

public class MportletOperation {

	public String name;
	public String type;
	public String href;
	public String body;
	

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public MportletOperation(String name, String type, String href, String body) {
		this.name = name;
		this.type = type;
		this.href = href;
		this.body = body;
	}

	public String getName() {
		return ResourceUtil
				.getMessage(this.name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
