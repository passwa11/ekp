package com.landray.kmss.third.wechat.dto;

import java.util.List;
import java.util.Map;

public class SearchResult {

	private Map<String, Integer> modelMap ;
	private List<SearchBean> list;
	
	public Map<String, Integer> getModelMap() {
		return modelMap;
	}
	public void setModelMap(Map<String, Integer> modelMap) {
		this.modelMap = modelMap;
	}
	public List<SearchBean> getList() {
		return list;
	}
	public void setList(List<SearchBean> list) {
		this.list = list;
	}
	
	
}
