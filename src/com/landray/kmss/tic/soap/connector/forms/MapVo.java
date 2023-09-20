package com.landray.kmss.tic.soap.connector.forms;

public class MapVo {
	
	private String key;
	private Object value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public MapVo(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public MapVo(){}
	
	

}
