package com.landray.kmss.sys.ui.xml.model;

import com.landray.kmss.sys.ui.taglib.fn.LuiFunctions;

public class SysUiXmlBase {
	protected String fdId;
	protected String fdName;
	protected String fdHelp;
	protected String fdThumb;
	// 部件的来源的xml路径 eg.lux-ext-component/design-xml/format.xml
	protected String xmlPath;
	//用于存储ui的类型，默认空值 eg.render、layout、header、footer、template;暂时用于前端显示时进行数据的区分，删除时便于快速定位缓存位置
	protected String uiType;

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getFdName() {
		return LuiFunctions.msg(this.fdName);
	}
	public String getFdNameKey() {
		return this.fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdHelp() {
		return fdHelp;
	}

	public void setFdHelp(String fdHelp) {
		this.fdHelp = fdHelp;
	}

	public String getFdThumb() {
		return fdThumb;
	}

	public void setFdThumb(String fdThumb) {
		this.fdThumb = fdThumb;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
	}
}
