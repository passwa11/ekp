package com.landray.kmss.sys.ui.xml.model;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ForSystemType;
import com.landray.kmss.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SysUiPortlet {
	protected String fdId;
	protected String fdName;
	protected String fdSource;
	protected String fdModule;
	protected String fdDescription;
	/**
	 * 所属系统
	 */
	protected ForSystemType fdForSystem;
	/**
	 * var参数继承自另一个portlet的ID
	 */
	protected String fdVarExtend;
	/**
	 * 是否支持匿名
	 */
	protected Boolean fdAnonymous;
	//来源于哪个xml文件
	protected String xmlPath;

	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}

	public String getFdVarExtend() {
		return fdVarExtend;
	}

	public void setFdVarExtend(String fdVarExtend) {
		this.fdVarExtend = fdVarExtend;
	}

	public ForSystemType getFdForSystem() {
		return fdForSystem;
	}

	public void setFdForSystem(ForSystemType fdForSystem) {
		this.fdForSystem = fdForSystem;
	}

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	public String getFdModule() {
		return fdModule;
	}

	public void setFdModule(String fdModule) {
		this.fdModule = fdModule;
	}

	protected List<SysUiOperation> fdOperations;
	/**
	 * 变量赋值的配置页面URL
	 */
	protected String fdAttribute;
	protected List<SysUiVar> fdVars;

	public String getFdAttribute() {
		return fdAttribute;
	}

	public void setFdAttribute(String fdAttribute) {
		this.fdAttribute = fdAttribute;
	}

	/**
	 * 这里是获取方法，新增var请使用{@code addVar}或{@code addAllVar}
	 * 
	 * @return
	 */
	public List<SysUiVar> getFdVars() {
		if (this.fdVars == null) {
			this.fdVars = new ArrayList<SysUiVar>();
		}
		return fdVars;
	}

	/**
	 * 新增var，相同key会覆盖原有var
	 * 
	 * @param var
	 */
	public void addVar(SysUiVar var) {
		if (this.fdVars == null) {
			this.fdVars = new ArrayList<SysUiVar>();
		}
		if (this.fdVars.contains(var)) {
			this.fdVars.remove(var);
		}
		this.fdVars.add(var);
	}

	public void addAllVar(List<SysUiVar> fdVars) {
		for (SysUiVar var : fdVars) {
			addVar(var);
		}
	}

	public void setFdVars(List<SysUiVar> fdVars) {
		this.fdVars = fdVars;
	}
	public SysUiPortlet() {

	}
	public SysUiPortlet(String id, String name,String module) {
		this(id, name, module, null, null, null);
	}

	public SysUiPortlet(String id, String name, String module,
			String varExtend, String forSystem, String anonymous) {
		this.fdId = id;
		this.fdName = name;
		this.fdModule = module;
		this.fdSource = this.fdId + ".source";
		this.fdVarExtend = varExtend;
		if (StringUtil.isNotNull(this.fdVarExtend)) {
			SysUiPortlet portlet = SysUiPluginUtil
					.getPortletById(this.fdVarExtend);
			if (portlet != null) {
				// 这里是初始化，所以采用这种新增var方式
				getFdVars().addAll(portlet.getFdVars());
			}
		}
		if (StringUtil.isNull(forSystem)) {
			forSystem = "ekp";
		}
		this.fdForSystem = ForSystemType.valueOf(forSystem);
		this.fdAnonymous = "true".equals(anonymous);
	}

	public List<SysUiOperation> getFdOperations() {
		if (this.fdOperations == null) {
			this.fdOperations = new ArrayList<SysUiOperation>();
		}
		return fdOperations;
	}

	public void setFdOperations(List<SysUiOperation> fdOperations) {
		this.fdOperations = fdOperations;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdSource() {
		return fdSource;
	}

	public void setFdSource(String fdSource) {
		this.fdSource = fdSource;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}
}
