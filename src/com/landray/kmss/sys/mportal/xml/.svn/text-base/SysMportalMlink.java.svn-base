package com.landray.kmss.sys.mportal.xml;

import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;


/**
 * 快捷方式模型
 * @author 
 *
 */
public class SysMportalMlink {
	
	
	public SysMportalMlink() {};
	
	public SysMportalMlink(String id, String msgKey, String url) {
		this.id = id;
		this.msgKey = msgKey;
		this.url = url;
	}
	
	private String type;
	

	private String id;
	
	private String msgKey;
	
	private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgKey() {
		return msgKey;
	}

	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		String name = ResourceUtil.getString(this.getMsgKey());
		if (StringUtil.isNull(name)) {
			name = this.getMsgKey();
		}
		return name;
	}
}
