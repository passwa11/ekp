package com.landray.kmss.sys.transport.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.transport.form.ConfigForm;

public class Config extends BaseModel
{
	private String fdName;
	private String fdModelName;
	private SysOrgElement creator;
	private Date createTime;
	private List propertyList; // 已选导入属性列表
	private List primaryKeyPropertyList; // 主数据关键字列表
	
	@Override
    public Class getFormClass() {
		return ConfigForm.class;
	}
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
	public List getPrimaryKeyPropertyList() {
		return primaryKeyPropertyList;
	}
	public void setPrimaryKeyPropertyList(List primaryKeyPropertyList) {
		this.primaryKeyPropertyList = primaryKeyPropertyList;
	}
	public List getPropertyList() {
		return propertyList;
	}
	public void setPropertyList(List propertyList) {
		this.propertyList = propertyList;
	}

}
