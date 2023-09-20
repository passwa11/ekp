package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.model.BaseModel;

/**
 * 创建日期 2006-12-14
 * 
 * @author 吴兵
 */
public class SysOMSCache extends BaseModel {

	private String fdOrgElementId;

	public void setFdOrgElementId(String fdOrgElementId) {
		this.fdOrgElementId = fdOrgElementId;
	}

	public String getFdOrgElementId() {
		return fdOrgElementId;
	}

	private String fdAppName;

	public void setFdAppName(String fdAppName) {
		this.fdAppName = fdAppName;
	}

	public String getFdAppName() {
		return fdAppName;
	}

	private Integer fdOpType = 0;

	public void setFdOpType(Integer fdOpType) {
		this.fdOpType = fdOpType;
	}

	public Integer getFdOpType() {
		return fdOpType;
	}

	@Override
    public Class getFormClass() {
		return SysOMSCache.class;
	}

}
