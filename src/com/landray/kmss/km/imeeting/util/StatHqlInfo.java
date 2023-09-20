package com.landray.kmss.km.imeeting.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.type.Type;

import com.landray.kmss.common.dao.HQLParameter;

public class StatHqlInfo {

	private String hqlBlock;

	public String getHqlBlock() {
		return hqlBlock;
	}

	public void setHqlBlock(String hqlBlock) {
		this.hqlBlock = hqlBlock;
	}

	private List<HQLParameter> parameterList = new ArrayList<HQLParameter>();

	public List<HQLParameter> getParameterList() {
		return parameterList;
	}

	public void setParameter(String key, Object value) {
		parameterList.add(new HQLParameter(key, value));
	}

	public void setParameter(String key, Object value, Type type) {
		parameterList.add(new HQLParameter(key, value, type));
	}

	public void setParameter(HQLParameter param) {
		parameterList.add(param);
	}

	public void setParameter(List<HQLParameter> parameterList) {
		this.parameterList.addAll(parameterList);
	}
}
