package com.landray.kmss.common.model;

import java.util.Date;

import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * @author 苏轶 有“创建者”和“创建时间”的域模型，可以继承此Class
 */
public abstract class BaseCreateInfoModel extends BaseModel implements IBaseCreateInfoModel {
	private SysOrgPerson docCreator;

	private Date docCreateTime = new Date();

	@Override
    public Date getDocCreateTime() {
		return docCreateTime;
	}

	@Override
    public void setDocCreateTime(Date createTime) {
		this.docCreateTime = createTime;
	}

	@Override
    public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	@Override
    public void setDocCreator(SysOrgPerson creator) {
		this.docCreator = creator;
	}
}
