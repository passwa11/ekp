package com.landray.kmss.sys.attend.dao;

import java.io.Serializable;

import org.hibernate.type.Type;

/**
 *
 * @author cuiwj
 * @version 1.0 2019-02-23
 */
public class SQLParameter implements Serializable {

	private String name;

	private Object value;

	private Type type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public SQLParameter(String name, Object value, Type type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public SQLParameter(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}

}
