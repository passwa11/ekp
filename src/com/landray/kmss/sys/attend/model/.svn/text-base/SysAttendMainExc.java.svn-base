package com.landray.kmss.sys.attend.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.attend.forms.SysAttendMainExcForm;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainModel;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.util.AutoHashMap;



/**
 * 签到异常表
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendMainExc extends ExtendAuthModel
		implements ISysNotifyModel, IAttachment, ISysLbpmMainModel {

	/**
	 * 状态
	 */
	private Integer fdStatus;
	
	/**
	 * @return 状态
	 */
	public Integer getFdStatus() {
		return this.fdStatus;
	}
	
	/**
	 * @param fdStatus 状态
	 */
	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}
	
	/**
	 * 备注
	 */
	private String fdDesc;
	
	/**
	 * @return 备注
	 */
	public String getFdDesc() {
		return this.fdDesc;
	}
	
	/**
	 * @param fdDesc 备注
	 */
	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}
	
	/**
	 * 处理时间
	 */
	private Date docHandleTime;
	
	/**
	 * @return 处理时间
	 */
	public Date getDocHandleTime() {
		return this.docHandleTime;
	}
	
	/**
	 * @param docHandleTime 处理时间
	 */
	public void setDocHandleTime(Date docHandleTime) {
		this.docHandleTime = docHandleTime;
	}
	
	/**
	 * 签到记录
	 */
	private SysAttendMain fdAttendMain;
	
	/**
	 * @return 签到记录
	 */
	public SysAttendMain getFdAttendMain() {
		return this.fdAttendMain;
	}
	
	/**
	 * @param fdAttendMain 签到记录
	 */
	public void setFdAttendMain(SysAttendMain fdAttendMain) {
		this.fdAttendMain = fdAttendMain;
	}
	
	/**
	 * 处理人
	 */
	private SysOrgElement fdHandler;
	
	/**
	 * @return 处理人
	 */
	public SysOrgElement getFdHandler() {
		return this.fdHandler;
	}
	
	/**
	 * @param fdHandler 处理人
	 */
	public void setFdHandler(SysOrgElement fdHandler) {
		this.fdHandler = fdHandler;
	}
	
	/**
	 * 选择的打卡时间
	 */
	private Date fdAttendTime;

	public Date getFdAttendTime() {
		return fdAttendTime;
	}

	public void setFdAttendTime(Date fdAttendTime) {
		this.fdAttendTime = fdAttendTime;
	}

	private String docSubject;

	@Override
    public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 考勤组负责人
	 */
	private SysOrgElement fdManager;

	public SysOrgElement getFdManager() {
		return fdManager;
	}

	public void setFdManager(SysOrgElement fdManager) {
		this.fdManager = fdManager;
	}

	private SysAttendCategoryATemplate fdCateTempl;

	public SysAttendCategoryATemplate getFdCateTempl() {
		return fdCateTempl;
	}

	public void setFdCateTempl(SysAttendCategoryATemplate fdCateTempl) {
		this.fdCateTempl = fdCateTempl;
	}

	@Override
    public Class<SysAttendMainExcForm> getFormClass() {
		return SysAttendMainExcForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdHandler.fdId", "fdHandlerId");
			toFormPropertyMap.put("fdHandler.fdName", "fdHandlerName");
			toFormPropertyMap.put("fdAttendMain.fdId", "fdAttendMainId");
			toFormPropertyMap.put("fdAttendMain.docCreateTime",
					"fdAttendMainCreateTime");
			toFormPropertyMap.put("fdAttendMain.fdLocation",
					"fdAttendMainLocation");
			toFormPropertyMap.put("fdAttendMain.fdStatus",
					"fdAttendMainStatus");
			toFormPropertyMap.put("fdAttendMain.fdState",
					"fdAttendMainState");
			toFormPropertyMap.put("fdAttendMain.fdCategory.fdName",
					"fdAttendMainCateName");
			toFormPropertyMap.put("fdAttendMain.docCreator.fdId",
					"fdAttendMainDocCreatorId");
			toFormPropertyMap.put("fdAttendMain.docCreator.fdName",
					"fdAttendMainDocCreatorName");
			toFormPropertyMap.put("fdAttendMain.docCreator.fdParent.deptLevelNames",
					"fdAttendMainDocCreatorDeptName");
			toFormPropertyMap.put("fdAttendMain.fdLng",
					"fdAttendMainLng");
			toFormPropertyMap.put("fdAttendMain.fdLat",
					"fdAttendMainLat");
			toFormPropertyMap.put("fdAttendMain.fdOutside",
					"fdAttendOutside");
			toFormPropertyMap.put("fdManager.fdId", "fdManagerId");
			toFormPropertyMap.put("fdCateTempl.fdId", "fdCateTemplId");
		}
		return toFormPropertyMap;
	}

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	private LbpmProcessForm sysWfBusinessModel = new LbpmProcessForm();

	@Override
    public LbpmProcessForm getSysWfBusinessModel() {
		return sysWfBusinessModel;
	}
}
