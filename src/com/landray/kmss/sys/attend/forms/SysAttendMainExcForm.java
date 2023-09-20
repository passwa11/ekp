package com.landray.kmss.sys.attend.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoHashMap;



/**
 * 签到异常表 Form
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public class SysAttendMainExcForm extends ExtendAuthForm
		implements IAttachmentForm, ISysLbpmMainForm {

	/**
	 * 状态
	 */
	private String fdStatus;
	
	/**
	 * @return 状态
	 */
	public String getFdStatus() {
		return this.fdStatus;
	}
	
	/**
	 * @param fdStatus 状态
	 */
	public void setFdStatus(String fdStatus) {
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
	 * 创建时间
	 */
	private String docCreateTime;
	
	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 处理时间
	 */
	private String docHandleTime;
	
	/**
	 * @return 处理时间
	 */
	public String getDocHandleTime() {
		return this.docHandleTime;
	}
	
	/**
	 * @param docHandleTime 处理时间
	 */
	public void setDocHandleTime(String docHandleTime) {
		this.docHandleTime = docHandleTime;
	}
	
	/**
	 * 签到记录的ID
	 */
	private String fdAttendMainId;
	
	/**
	 * @return 签到记录的ID
	 */
	public String getFdAttendMainId() {
		return this.fdAttendMainId;
	}
	
	/**
	 * @param fdAttendMainId 签到记录的ID
	 */
	public void setFdAttendMainId(String fdAttendMainId) {
		this.fdAttendMainId = fdAttendMainId;
	}
	
	/**
	 * 签到记录的名称
	 */
	private String fdAttendMainName;
	
	/**
	 * @return 签到记录的名称
	 */
	public String getFdAttendMainName() {
		return this.fdAttendMainName;
	}
	
	/**
	 * @param fdAttendMainName 签到记录的名称
	 */
	public void setFdAttendMainName(String fdAttendMainName) {
		this.fdAttendMainName = fdAttendMainName;
	}
	
	/**
	 * 处理人的ID
	 */
	private String fdHandlerId;
	
	/**
	 * @return 处理人的ID
	 */
	public String getFdHandlerId() {
		return this.fdHandlerId;
	}
	
	/**
	 * @param fdHandlerId 处理人的ID
	 */
	public void setFdHandlerId(String fdHandlerId) {
		this.fdHandlerId = fdHandlerId;
	}
	
	/**
	 * 处理人的名称
	 */
	private String fdHandlerName;
	
	/**
	 * @return 处理人的名称
	 */
	public String getFdHandlerName() {
		return this.fdHandlerName;
	}
	
	/**
	 * @param fdHandlerName 处理人的名称
	 */
	public void setFdHandlerName(String fdHandlerName) {
		this.fdHandlerName = fdHandlerName;
	}

	private String fdManagerId;

	public String getFdManagerId() {
		return fdManagerId;
	}

	public void setFdManagerId(String fdManagerId) {
		this.fdManagerId = fdManagerId;
	}

	private String fdAttendMainCateName;
	private String fdAttendMainState;
	private String fdAttendMainStatus;
	private String fdAttendMainLocation;
	private String fdAttendMainCreateTime;
	private String fdAttendMainDocCreatorId;
	private String fdAttendMainDocCreatorName;
	private String fdAttendMainDocCreatorDeptName;
	private String fdAttendMainLng;
	private String fdAttendMainLat;
	private String fdAttendTime;

	public String getFdAttendMainDocCreatorDeptName() {
		return fdAttendMainDocCreatorDeptName;
	}

	public void setFdAttendMainDocCreatorDeptName(
			String fdAttendMainDocCreatorDeptName) {
		this.fdAttendMainDocCreatorDeptName = fdAttendMainDocCreatorDeptName;
	}

	public String getFdAttendMainDocCreatorName() {
		return fdAttendMainDocCreatorName;
	}

	public void
			setFdAttendMainDocCreatorName(String fdAttendMainDocCreatorName) {
		this.fdAttendMainDocCreatorName = fdAttendMainDocCreatorName;
	}

	public String getFdAttendMainCateName() {
		return fdAttendMainCateName;
	}

	public void setFdAttendMainCateName(String fdAttendMainCateName) {
		this.fdAttendMainCateName = fdAttendMainCateName;
	}

	public String getFdAttendMainState() {
		return fdAttendMainState;
	}

	public void setFdAttendMainState(String fdAttendMainState) {
		this.fdAttendMainState = fdAttendMainState;
	}

	public String getFdAttendMainStatus() {
		return fdAttendMainStatus;
	}

	public void setFdAttendMainStatus(String fdAttendMainStatus) {
		this.fdAttendMainStatus = fdAttendMainStatus;
	}

	public String getFdAttendMainLocation() {
		return fdAttendMainLocation;
	}

	public void setFdAttendMainLocation(String fdAttendMainLocation) {
		this.fdAttendMainLocation = fdAttendMainLocation;
	}

	public String getFdAttendMainCreateTime() {
		return fdAttendMainCreateTime;
	}

	public void setFdAttendMainCreateTime(String fdAttendMainCreateTime) {
		this.fdAttendMainCreateTime = fdAttendMainCreateTime;
	}

	public String getFdAttendMainLng() {
		return fdAttendMainLng;
	}

	public void setFdAttendMainLng(String fdAttendMainLng) {
		this.fdAttendMainLng = fdAttendMainLng;
	}

	public String getFdAttendMainLat() {
		return fdAttendMainLat;
	}

	public void setFdAttendMainLat(String fdAttendMainLat) {
		this.fdAttendMainLat = fdAttendMainLat;
	}

	public String getFdAttendTime() {
		return fdAttendTime;
	}

	public void setFdAttendTime(String fdAttendTime) {
		this.fdAttendTime = fdAttendTime;
	}

	public String fdAttendOutside;

	public String getFdAttendOutside() {
		return fdAttendOutside;
	}

	public void setFdAttendOutside(String fdAttendOutside) {
		this.fdAttendOutside = fdAttendOutside;
	}

	private String docSubject;

	public String getDocSubject() {
		return docSubject;
	}

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	private String fdCateTemplId;

	public String getFdCateTemplId() {
		return fdCateTemplId;
	}

	public void setFdCateTemplId(String fdCateTemplId) {
		this.fdCateTemplId = fdCateTemplId;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdStatus = null;
		fdDesc = null;
		docCreateTime = null;
		docHandleTime = null;
		fdAttendMainId = null;
		fdAttendMainName = null;
		fdHandlerId = null;
		fdHandlerName = null;
		fdAttendMainCateName = null;
		fdAttendMainCreateTime = null;
		fdAttendMainLocation = null;
		fdAttendMainStatus = null;
		fdAttendMainState = null;
		fdAttendMainDocCreatorName = null;
		fdAttendMainDocCreatorDeptName = null;
		fdAttendMainLng = null;
		fdAttendMainLat = null;
		fdAttendOutside = null;
		fdAttendTime = null;
		docStatus = null;
		fdCateTemplId = null;
		sysWfBusinessForm = new LbpmProcessForm();
		super.reset(mapping, request);
	}

	@Override
    public Class<SysAttendMainExc> getModelClass() {
		return SysAttendMainExc.class;
	}
	
	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdAttendMainId",
					new FormConvertor_IDToModel("fdAttendMain",
						SysAttendMain.class));
			toModelPropertyMap.put("fdHandlerId",
					new FormConvertor_IDToModel("fdHandler",
							SysOrgElement.class));
			toModelPropertyMap.put("fdManagerId",
					new FormConvertor_IDToModel("fdManager",
							SysOrgElement.class));
			toModelPropertyMap.put("fdCateTemplId",
					new FormConvertor_IDToModel("fdCateTempl",
							SysAttendCategoryATemplate.class));
		}
		return toModelPropertyMap;
	}

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	private LbpmProcessForm sysWfBusinessForm = new LbpmProcessForm();

	@Override
    public LbpmProcessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	public String getFdAttendMainDocCreatorId() {
		return fdAttendMainDocCreatorId;
	}

	public void setFdAttendMainDocCreatorId(String fdAttendMainDocCreatorId) {
		this.fdAttendMainDocCreatorId = fdAttendMainDocCreatorId;
	}

}
