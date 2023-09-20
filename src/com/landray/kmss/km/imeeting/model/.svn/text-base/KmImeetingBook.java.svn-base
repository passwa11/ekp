package com.landray.kmss.km.imeeting.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingBookForm;
import com.landray.kmss.sys.authorization.interfaces.ISysAuthAreaModel;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
 * 会议室预约
 */
public class KmImeetingBook extends BaseModel implements ISysNotifyModel, ISysAuthAreaModel
{

	/**
	 * 会议名称
	 */
	protected String fdName;

	/**
	 * @return 会议名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            会议名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 召开日期
	 */
	protected Date fdHoldDate = new Date();

	/**
	 * @return 召开日期
	 */
	public Date getFdHoldDate() {
		return fdHoldDate;
	}

	/**
	 * @param fdHoldDate
	 *            召开日期
	 */
	public void setFdHoldDate(Date fdHoldDate) {
		this.fdHoldDate = fdHoldDate;
	}

	/**
	 * 结束日期
	 */
	protected Date fdFinishDate = new Date();

	/**
	 * @return 结束日期
	 */
	public Date getFdFinishDate() {
		return fdFinishDate;
	}

	/**
	 * @param fdFinishDate
	 *            结束日期
	 */
	public void setFdFinishDate(Date fdFinishDate) {
		this.fdFinishDate = fdFinishDate;
	}

	/**
	 * 会议历时
	 */
	protected Double fdHoldDuration = 0.0;

	/**
	 * @return 会议历时
	 */
	public Double getFdHoldDuration() {
		return fdHoldDuration;
	}

	/**
	 * @param fdHoldDuration
	 *            会议历时
	 */
	public void setFdHoldDuration(Double fdHoldDuration) {
		this.fdHoldDuration = fdHoldDuration;
	}

	/**
	 * 备注
	 */
	protected String fdRemark;

	/**
	 * @return 备注
	 */
	public String getFdRemark() {
		return fdRemark;
	}

	/**
	 * @param fdRemark
	 *            备注
	 */
	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}

	/**
	 * 会议登记人
	 */
	protected SysOrgElement docCreator;

	/**
	 * @return 会议登记人
	 */
	public SysOrgElement getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            会议登记人
	 */
	public void setDocCreator(SysOrgElement docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 会议地点
	 */
	protected KmImeetingRes fdPlace;

	/**
	 * @return 会议地点
	 */
	public KmImeetingRes getFdPlace() {
		return fdPlace;
	}

	/**
	 * @param fdPlace
	 *            会议地点
	 */
	public void setFdPlace(KmImeetingRes fdPlace) {
		this.fdPlace = fdPlace;
	}

	private String fdRecurrenceStr;

	public String getFdRecurrenceStr() {
		return fdRecurrenceStr;
	}

	public void setFdRecurrenceStr(String fdRecurrenceStr) {
		this.fdRecurrenceStr = fdRecurrenceStr;
	}

	private Date fdRecurrenceLastStart;

	public Date getFdRecurrenceLastStart() {
		return fdRecurrenceLastStart;
	}

	public void setFdRecurrenceLastStart(Date fdRecurrenceLastStart) {
		this.fdRecurrenceLastStart = fdRecurrenceLastStart;
	}

	private Date fdRecurrenceLastEnd;

	public Date getFdRecurrenceLastEnd() {
		return fdRecurrenceLastEnd;
	}

	public void setFdRecurrenceLastEnd(Date fdRecurrenceLastEnd) {
		this.fdRecurrenceLastEnd = fdRecurrenceLastEnd;
	}

	@Override
    public Class getFormClass() {
		return KmImeetingBookForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("authArea.fdId", "authAreaId");
			toFormPropertyMap.put("authArea.fdName", "authAreaName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdPlace.fdId", "fdPlaceId");
			toFormPropertyMap.put("fdPlace.fdName", "fdPlaceName");
			toFormPropertyMap.put("fdPlace.docKeeper.fdName", "docKeeperName");
			toFormPropertyMap.put("fdPlace.fdDetail", "fdPlaceDetail");
			toFormPropertyMap.put("fdPlace.fdSeats", "fdPlaceSeats");
			toFormPropertyMap.put("fdPlace.fdAddressFloor", "fdPlaceAddressFloor");
			toFormPropertyMap.put("fdPlace.fdUserTime", "fdUserTime");
			toFormPropertyMap.put("fdExamer.fdId", "fdExamerId");
			toFormPropertyMap.put("fdExamer.fdName", "fdExamerName");
		}
		return toFormPropertyMap;
	}

	private Boolean fdHasExam;

	public Boolean getFdHasExam() {
		return fdHasExam;
	}

	public void setFdHasExam(Boolean fdHasExam) {
		this.fdHasExam = fdHasExam;
	}

	private String fdExamRemark;

	public String getFdExamRemark() {
		return fdExamRemark;
	}

	public void setFdExamRemark(String fdExamRemark) {
		this.fdExamRemark = fdExamRemark;
	}

	protected Boolean isNotify;

	public Boolean getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(Boolean isNotify) {
		this.isNotify = isNotify;
	}

	private SysOrgElement fdExamer;

	public SysOrgElement getFdExamer() {
		return fdExamer;
	}

	public void setFdExamer(SysOrgElement fdExamer) {
		this.fdExamer = fdExamer;
	}

	/*
	 * 所属场所
	 */
	protected SysAuthArea authArea;

	@Override
    public SysAuthArea getAuthArea() {
		return authArea;
	}

	@Override
    public void setAuthArea(SysAuthArea authArea) {
		this.authArea = authArea;
	}
}

