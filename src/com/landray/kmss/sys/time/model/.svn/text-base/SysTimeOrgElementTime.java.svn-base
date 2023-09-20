package com.landray.kmss.sys.time.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 个人排班信息
 * 
 * @author ADai00 2018年12月28日
 */
public class SysTimeOrgElementTime extends BaseModel {

	private SysTimeArea sysTimeArea = null;

	private SysOrgElement sysOrgElement = null;

	/** 班次设置列表 */
	protected List<SysTimeWork> sysTimeWorkList = new ArrayList<SysTimeWork>();

	/** 休假设置列表 */
	protected List<SysTimeVacation> sysTimeVacationList = new ArrayList<SysTimeVacation>();

	/** 补班设置列表 */
	protected List<SysTimePatchwork> sysTimePatchworkList = new ArrayList<SysTimePatchwork>();

	public SysTimeArea getSysTimeArea() {
		return sysTimeArea;
	}

	public void setSysTimeArea(SysTimeArea sysTimeArea) {
		this.sysTimeArea = sysTimeArea;
	}

	public SysOrgElement getSysOrgElement() {
		return sysOrgElement;
	}

	public void setSysOrgElement(SysOrgElement sysOrgElement) {
		this.sysOrgElement = sysOrgElement;
	}

	public List<SysTimeWork> getSysTimeWorkList() {
		return sysTimeWorkList;
	}

	public void setSysTimeWorkList(List<SysTimeWork> sysTimeWorkList) {
		this.sysTimeWorkList = sysTimeWorkList;
	}

	public List<SysTimeVacation> getSysTimeVacationList() {
		return sysTimeVacationList;
	}

	public void
			setSysTimeVacationList(List<SysTimeVacation> sysTimeVacationList) {
		this.sysTimeVacationList = sysTimeVacationList;
	}

	public List<SysTimePatchwork> getSysTimePatchworkList() {
		return sysTimePatchworkList;
	}

	public void
			setSysTimePatchworkList(
					List<SysTimePatchwork> sysTimePatchworkList) {
		this.sysTimePatchworkList = sysTimePatchworkList;
	}

	@Override
	public Class getFormClass() {
		return null;
	}

}
