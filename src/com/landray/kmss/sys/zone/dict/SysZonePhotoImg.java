package com.landray.kmss.sys.zone.dict;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "img")
public class SysZonePhotoImg {
	private String src;
	
	private String width;
	
	private String height;
	
	private String usemap;
	
	@XmlAttribute(name = "src")
	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	@XmlAttribute(name = "width")
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
	
	@XmlAttribute(name = "height")
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	
	@XmlAttribute(name = "usemap")
	public String getUsemap() {
		return usemap;
	}

	public void setUsemap(String usemap) {
		this.usemap = usemap;
	}
}
