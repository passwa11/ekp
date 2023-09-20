package com.landray.kmss.third.weixin.work.model.api;

import com.alibaba.fastjson.annotation.JSONField;

public class WxAttend {
	@JSONField(name = "opencheckindatatype")
	private Integer opencheckindatatype;
	@JSONField(name = "starttime")
	private Long starttime;
	@JSONField(name = "endtime")
	private Long endtime;
	@JSONField(name = "useridlist")
	private String[] useridlist;

	public Integer getOpencheckindatatype() {
		return opencheckindatatype;
	}

	public void setOpencheckindatatype(Integer opencheckindatatype) {
		this.opencheckindatatype = opencheckindatatype;
	}

	public Long getStarttime() {
		return starttime;
	}

	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public String[] getUseridlist() {
		return useridlist;
	}

	public void setUseridlist(String[] useridlist) {
		this.useridlist = useridlist;
	}


}
