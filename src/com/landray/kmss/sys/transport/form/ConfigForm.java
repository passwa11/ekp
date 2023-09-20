package com.landray.kmss.sys.transport.form;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.transport.model.Config;

public class ConfigForm extends ExtendForm
{
	private String fdName;
	private String fdModelName;
	private SysOrgElement creator;
	private Date createTime;
	private Integer fdImportType;
	
	private List optionList; // 可选属性列表
	private List propertyList; // 已选属性列表
	private String foreignKeyString; // 外键属性名串，半角分号隔开
	private Map foreignKeyPropertyOptionHtmlMap; // 外键属性可选项列表，key：外键属性名；value：外键对象属性列表的option部分的html代码
	
	private String selectedPropertyNames; // 已选属性列表，分号隔开
	private String primaryKey1; // 主数据关键字1
	private String primaryKey2; // 主数据关键字2
	private String primaryKey3; // 主数据关键字3
	private List primaryKeyOptionList; // 主数据关键字可选项列表
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public SysOrgElement getCreator() {
		return creator;
	}
	public void setCreator(SysOrgElement creator) {
		this.creator = creator;
	}
	public String getFdModelName() {
		return fdModelName;
	}
	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}
	public String getFdName() {
		return fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	@Override
    public Class getModelClass() {
		return Config.class;
	}
	public List getOptionList() {
		return optionList;
	}
	public void setOptionList(List options) {
		this.optionList = options;
	}
	public List getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List selectedOptions) {
		this.propertyList = selectedOptions;
	}
	public String getForeignKeyString() {
		return foreignKeyString;
	}
	public void setForeignKeyString(String foreignKeyString) {
		this.foreignKeyString = foreignKeyString;
	}
	public Map getForeignKeyPropertyOptionHtmlMap() {
		return foreignKeyPropertyOptionHtmlMap;
	}
	public void setForeignKeyPropertyOptionHtmlMap(Map foreignKeyPropertyOptionHtmlMap) {
		this.foreignKeyPropertyOptionHtmlMap = foreignKeyPropertyOptionHtmlMap;
	}
	public String getPrimaryKey1() {
		return primaryKey1;
	}
	public void setPrimaryKey1(String primaryKey1) {
		this.primaryKey1 = primaryKey1;
	}
	public String getPrimaryKey2() {
		return primaryKey2;
	}
	public void setPrimaryKey2(String primaryKey2) {
		this.primaryKey2 = primaryKey2;
	}
	public String getPrimaryKey3() {
		return primaryKey3;
	}
	public void setPrimaryKey3(String primaryKey3) {
		this.primaryKey3 = primaryKey3;
	}
	public String getSelectedPropertyNames() {
		return selectedPropertyNames;
	}
	public void setSelectedPropertyNames(String selectedOptions) {
		this.selectedPropertyNames = selectedOptions;
	}
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		super.reset(mapping, request);
	}
	public Integer getFdImportType() {
		return fdImportType;
	}
	public void setFdImportType(Integer importType) {
		this.fdImportType = importType;
	}
	public List getPrimaryKeyOptionList() {
		return primaryKeyOptionList;
	}
	public void setPrimaryKeyOptionList(List primaryKeyOptionList) {
		this.primaryKeyOptionList = primaryKeyOptionList;
	}
}
