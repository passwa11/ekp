package com.landray.kmss.sys.zone.dict;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "body")
public class SysZonePhotoTemplate {
	
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	
	private SysZonePhotoMap map;
	
	@XmlElement(name = "map", type = SysZonePhotoMap.class)
	public SysZonePhotoMap getMap() {
		return map;
	}

	public void setMap(SysZonePhotoMap map) {
		this.map = map;
	}
	
	private SysZonePhotoImg img;

	@XmlElement(name = "img", type = SysZonePhotoImg.class)	
	public SysZonePhotoImg getImg() {
		return img;
	}

	public void setImg(SysZonePhotoImg img) {
		this.img = img;
	}
	
	private Boolean isDefault = Boolean.FALSE;

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	
}
