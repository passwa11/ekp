package com.landray.kmss.sys.ui.xml.model;

public class SysUiOperation {
	public String name;
	public String href;
	public String target;
	public String icon;
	public String type;
	public String align;
	private String mobileHref;
	private String windowFeatures;
	public SysUiOperation(){
		
	}
	public SysUiOperation(String name, String href, String target, String icon,
			String type, String align) {
		this(name, href, target, icon, type, align, null);
	}

	public SysUiOperation(String name, String href, String target, String icon,
			String type, String align, String mobileHref) {
		this(name, href, target, icon, type, align, null, null);
	}

	public SysUiOperation(String name, String href, String target, String icon,
			String type, String align, String mobileHref, String windowFeatures) {
		this.name = name;
		this.href = href;
		this.target = target;
		this.icon = icon;
		this.type = type;
		this.align = align;
		this.mobileHref = mobileHref;
		this.windowFeatures = windowFeatures;
	}

	public String getMobileHref() {
		return mobileHref;
	}

	public void setMobileHref(String mobileHref) {
		this.mobileHref = mobileHref;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getWindowFeatures() {
		return windowFeatures;
	}

	public void setWindowFeatures(String windowFeatures) {
		this.windowFeatures = windowFeatures;
	}

}
