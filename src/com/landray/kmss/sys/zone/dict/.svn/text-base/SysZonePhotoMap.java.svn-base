package com.landray.kmss.sys.zone.dict;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "map")
public class SysZonePhotoMap {
	private String id;
	
	@XmlAttribute(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	
	private ArrayList<SysZonePhotoArea> photoAreas;
	
	@XmlElement(name = "area", type = SysZonePhotoArea.class)
	public ArrayList<SysZonePhotoArea> getPhotoAreas() {
		return photoAreas;
	}

	public void setPhotoAreas(ArrayList<SysZonePhotoArea> photoAreas) {
		this.photoAreas = photoAreas;
	}
	
}
