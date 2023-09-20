package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.attend.forms.SysAttendSignStatForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
 * 签到统计
 * 
 * @author linxiuxian
 *
 */
public class SysAttendSignStat extends BaseModel {

	private Date fdDate = new Date();
	// 签到组id
	private String fdCategoryId;

	private Date docCreateTime;
	// 签到次数
	private Integer fdSignCount = 0;

	private SysOrgPerson docCreator;

	public Date getFdDate() {
		return fdDate;
	}

	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
	}

	public String getFdCategoryId() {
		return fdCategoryId;
	}

	public void setFdCategoryId(String fdCategoryId) {
		this.fdCategoryId = fdCategoryId;
	}

	public Date getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public Integer getFdSignCount() {
		return fdSignCount;
	}

	public void setFdSignCount(Integer fdSignCount) {
		this.fdSignCount = fdSignCount;
	}

	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	@Override
    public Class<SysAttendSignStatForm> getFormClass() {
		return SysAttendSignStatForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
		}
		return toFormPropertyMap;
	}

}
