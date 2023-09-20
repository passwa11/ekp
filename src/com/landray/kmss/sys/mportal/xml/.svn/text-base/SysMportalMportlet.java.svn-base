package com.landray.kmss.sys.mportal.xml;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.sys.ui.xml.model.SysUiVar;
import com.landray.kmss.util.ResourceUtil;

/**
 * 部件模型
 * 
 * @author
 *
 */
public class SysMportalMportlet {

	public SysMportalMportlet() {
	}

	public SysMportalMportlet(String id, String name, String fdModule, String fdJspUrl,
			String jsUrl, String cssUrl) {
		this.id = id;
		this.name = name;
		this.fdModule = fdModule;
		this.fdJspUrl = fdJspUrl;
		this.jsUrl = jsUrl;
		this.cssUrl = cssUrl;
	}

	private String cssUrl;

	public String getCssUrl() {
		return cssUrl;
	}

	protected String id;

	public String getId() {
		return id;
	}

	protected String jsUrl;

	public String getJsUrl() {
		return jsUrl;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return ResourceUtil.getMessage(this.name);
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String name;

	protected String fdModule;

	protected String fdJspUrl;

	protected String description;

	protected String fdModuleUrl;

	public String getFdModuleUrl() {
		return fdModuleUrl;
	}

	public void setFdModuleUrl(String fdModuleUrl) {
		this.fdModuleUrl = fdModuleUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected List<SysUiVar> fdVars;

	protected List<MportletOperation> fdOperations;

	public String getFdModule() {
		return fdModule;
	}

	public void setFdModule(String fdModule) {
		this.fdModule = fdModule;
	}

	public String getFdJspUrl() {
		return fdJspUrl;
	}

	public void setFdJspUrl(String fdJspUrl) {
		this.fdJspUrl = fdJspUrl;
	}

	public List<SysUiVar> getFdVars() {
		if (this.fdVars == null) {
			this.fdVars = new ArrayList<SysUiVar>();
		}
		return fdVars;
	}

	public void setFdVars(List<SysUiVar> fdVars) {
		this.fdVars = fdVars;
	}

	public List<MportletOperation> getFdOperations() {
		if (this.fdOperations == null) {
			this.fdOperations = new ArrayList<MportletOperation>();
		}
		return fdOperations;
	}

	public void setFdOperations(List<MportletOperation> fdOperations) {
		this.fdOperations = fdOperations;
	}

}
