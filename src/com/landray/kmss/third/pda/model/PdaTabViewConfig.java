package com.landray.kmss.third.pda.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.util.ResourceUtil;

/**
 * pda标签扩展
 * 
 * @author
 * 
 */
public class PdaTabViewConfig {

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(PdaTabViewConfig.class);
	private String tabName;
	private Integer tabOrder;
	private String tabUrl;
	private String tabIcon;
	private String tabType;
	private String modelName;
	private String model;
	private IXMLDataBean tabBean;
	private String templateClass;
	private String tabNameMessageKey;
	
	public String getTabNameMessageKey() {
		return tabNameMessageKey;
	}

	public void setTabNameMessageKey(String tabNameMessageKey) {
		this.tabNameMessageKey = tabNameMessageKey;
	}

	public String getTemplateClass() {
		return templateClass;
	}

	public void setTemplateClass(String templateClass) {
		this.templateClass = templateClass;
	}

	public IXMLDataBean getTabBean() {
		return tabBean;
	}

	public void setTabBean(IXMLDataBean tabBean) {
		this.tabBean = tabBean;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getTabOrder() {
		return tabOrder;
	}

	public void setTabOrder(Integer tabOrder) {
		this.tabOrder = tabOrder;
	}

	public String getTabUrl() {
		return tabUrl;
	}

	public void setTabUrl(String tabUrl) {
		this.tabUrl = tabUrl;
	}

	public String getTabIcon() {
		return tabIcon;
	}

	public void setTabIcon(String tabIcon) {
		this.tabIcon = tabIcon;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTabName() {
		try {
			tabName = ResourceUtil.getString(tabNameMessageKey);
		} catch (Exception e) {
			logger.error("获取资源messageKey:" + tabNameMessageKey + "发生错误");
		}
		return tabName;
	}

}
