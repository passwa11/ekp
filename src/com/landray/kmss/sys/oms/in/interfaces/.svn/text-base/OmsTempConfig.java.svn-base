package com.landray.kmss.sys.oms.in.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.sys.appconfig.model.BaseAppConfig;
import com.landray.sso.client.util.StringUtil;


public class OmsTempConfig extends BaseAppConfig {
	
	public OmsTempConfig() throws Exception {
		super();
	}

	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(OmsTempConfig.class);

	@Override
	public String getJSPUrl() {
		// TODO 自动生成的方法存根
		return null;
	}

	public String getRequiredOmsOrg() {
		return getValue("requiredOmsOrg");
	}

	public void setRequiredOmsOrg(String requiredOmsOrg) {
		setValue("requiredOmsOrg", requiredOmsOrg);
	}

	public String getRequiredOmsDept() {
		return getValue("requiredOmsDept");
	}

	public void setRequiredOmsDept(String requiredOmsDept) {
		setValue("requiredOmsDept", requiredOmsDept);
	}

	public String getRequiredOmsPerson() {
		return getValue("requiredOmsPerson");
	}

	public void setRequiredOmsPerson(String requiredOmsPerson) {
		setValue("requiredOmsPerson", requiredOmsPerson);
	}

	public String getRequiredOmsPost() {
		return getValue("requiredOmsPost");
	}

	public void setRequiredOmsPost(String requiredOmsPost) {
		setValue("requiredOmsPost", requiredOmsPost);
	}

	public String getRequiredOmsGroup() {
		return getValue("requiredOmsGroup");
	}

	public void setRequiredOmsGroup(String requiredOmsGroup) {
		setValue("requiredOmsGroup", requiredOmsGroup);
	}


	@Override
	public String getModelDesc() {
		return null;
	}

	private Map<Integer, List<String>> requiredOmsMap = new HashMap<Integer, List<String>>();

	private void initRequiredOms(Integer type) {
		String requiredOms = null;
		switch (type) {
		case 1:
			requiredOms = getRequiredOmsOrg();
			break;
		case 2:
			requiredOms = getRequiredOmsDept();
			break;
		case 4:
			requiredOms = getRequiredOmsPost();
			break;
		case 8:
			requiredOms = getRequiredOmsPerson();
			break;
		case 16:
			requiredOms = getRequiredOmsGroup();
			break;
		}
		if(StringUtil.isNull(requiredOms)){
			requiredOmsMap.put(1, null);
		}else{
			List list = JSONArray.parseArray(requiredOms, String.class);
			requiredOmsMap.put(type, list);
		}
	}

	/**
	 * 初始化同步字段信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, List<String>> initRequiredOms() throws Exception {
		requiredOmsMap.clear();
		initRequiredOms(1);
		initRequiredOms(2);
		initRequiredOms(4);
		initRequiredOms(8);
		initRequiredOms(16);
		return requiredOmsMap;
	}

	/**
	 * 设置同步字段
	 * 
	 * @param ele
	 */
	public void setRequiredOms(IOrgElement ele) {
		Integer orgType = ele.getOrgType();
		if (!requiredOmsMap.containsKey(orgType)) {
			requiredOmsMap.put(orgType, ele.getRequiredOms());
		}
	}

	/**
	 * 保存同步字段信息
	 * 
	 * @throws Exception
	 */
	public void saveRequiredOms() throws Exception {
		for (Integer key : requiredOmsMap.keySet()) {
			List<String> value = requiredOmsMap.get(key);
			String str = JSON.toJSONString(value);
			switch (key) {
			case 1:
				setRequiredOmsOrg(str);
				break;
			case 2:
				setRequiredOmsDept(str);
				break;
			case 4:
				setRequiredOmsPost(str);
				break;
			case 8:
				setRequiredOmsPerson(str);
				break;
			case 16:
				setRequiredOmsGroup(str);
				break;
			}
		}
		this.save();
	}

}
