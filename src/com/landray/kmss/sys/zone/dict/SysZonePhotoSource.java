package com.landray.kmss.sys.zone.dict;

import java.util.List;
import java.util.Map;


/**
 * 
 * 生成照片墙的数据源模型
 */
public class SysZonePhotoSource {
	private String id;
	
	private String name;
	
	private List<Map<String , Object>> imgs;


	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public List<Map<String, Object>> getImgs() {
		return imgs;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setImgs(List<Map<String, Object>> imgs) {
		this.imgs = imgs;
	}
}
