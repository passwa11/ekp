package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingSummary;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议纪要 Form
 * 
 * @author
 * @version 1.0 2014-07-21
 */
public class KmImeetingSummaryForm extends ExtendAuthForm implements
		ISysWfMainForm, ISysReadLogForm, IAttachmentForm, ISysCirculationForm {

	/**
	 * 纪要名称
	 */
	protected String fdName = null;

	/**
	 * @return 纪要名称
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            纪要名称
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 录入时间
	 */
	protected String docCreateTime = null;

	/**
	 * @return 录入时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            录入时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 发布时间
	 */
	protected String docPublishTime;

	public String getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(String docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	/**
	 * 召开时间
	 */
	protected String fdHoldDate = null;

	/**
	 * @return 召开时间
	 */
	public String getFdHoldDate() {
		return fdHoldDate;
	}

	/**
	 * @param fdHoldDate
	 *            召开时间
	 */
	public void setFdHoldDate(String fdHoldDate) {
		this.fdHoldDate = fdHoldDate;
	}

	/**
	 * 结束时间
	 */
	protected String fdFinishDate = null;

	public String getFdFinishDate() {
		return fdFinishDate;
	}

	public void setFdFinishDate(String fdFinishDate) {
		this.fdFinishDate = fdFinishDate;
	}

	/**
	 * 会议历时
	 */
	protected String fdHoldDuration = null;

	public String getFdHoldDuration() {
		return fdHoldDuration;
	}

	public void setFdHoldDuration(String fdHoldDuration) {
		this.fdHoldDuration = fdHoldDuration;
	}

	/**
	 * 外部召开地点
	 */
	protected String fdOtherPlace = null;

	/**
	 * @return 外部召开地点
	 */
	public String getFdOtherPlace() {
		return fdOtherPlace;
	}

	/**
	 * @param fdOtherPlace
	 *            外部召开地点
	 */
	public void setFdOtherPlace(String fdOtherPlace) {
		this.fdOtherPlace = fdOtherPlace;
	}

	private String fdOtherPlaceCoordinate;

	public String getFdOtherPlaceCoordinate() {
		return fdOtherPlaceCoordinate;
	}

	public void setFdOtherPlaceCoordinate(String fdOtherPlaceCoordinate) {
		this.fdOtherPlaceCoordinate = fdOtherPlaceCoordinate;
	}

	/**
	 * 其他主持人
	 */
	protected String fdOtherHostPerson = null;

	/**
	 * @return 其他主持人
	 */
	public String getFdOtherHostPerson() {
		return fdOtherHostPerson;
	}

	/**
	 * @param fdOtherHostPerson
	 *            其他主持人
	 */
	public void setFdOtherHostPerson(String fdOtherHostPerson) {
		this.fdOtherHostPerson = fdOtherHostPerson;
	}

	/**
	 * 会议组织人
	 */
	protected String fdEmceeId;

	protected String fdEmceeName;

	public String getFdEmceeId() {
		return fdEmceeId;
	}

	public void setFdEmceeId(String fdEmceeId) {
		this.fdEmceeId = fdEmceeId;
	}

	public String getFdEmceeName() {
		return fdEmceeName;
	}

	public void setFdEmceeName(String fdEmceeName) {
		this.fdEmceeName = fdEmceeName;
	}

	/**
	 * 纪要内容
	 */
	protected String docContent = null;

	/**
	 * @return 纪要内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            纪要内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}

	/**
	 * 文档内容的HTML
	 */
	private String fdHtmlContent;

	public String getFdHtmlContent() {
		return fdHtmlContent;
	}

	public void setFdHtmlContent(String fdHtmlContent) {
		this.fdHtmlContent = fdHtmlContent;
	}

	/**
	 * 文档内容的编辑方式
	 */
	private String fdContentType;

	public String getFdContentType() {
		return fdContentType;
	}

	public void setFdContentType(String fdContentType) {
		this.fdContentType = fdContentType;
	}

	/**
	 * 计划其他与会人员
	 */
	protected String fdPlanOtherAttendPerson = null;

	/**
	 * @return 计划其他与会人员
	 */
	public String getFdPlanOtherAttendPerson() {
		return fdPlanOtherAttendPerson;
	}

	/**
	 * @param fdPlanOtherAttendPerson
	 *            计划其他与会人员
	 */
	public void setFdPlanOtherAttendPerson(String fdPlanOtherAttendPerson) {
		this.fdPlanOtherAttendPerson = fdPlanOtherAttendPerson;
	}

	/**
	 * 纪要通知方式
	 */
	protected String fdNotifyType = null;

	/**
	 * @return 纪要通知方式
	 */
	public String getFdNotifyType() {
		return fdNotifyType;
	}

	/**
	 * @param fdNotifyType
	 *            纪要通知方式
	 */
	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	/**
	 * 创建者的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建者的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建者的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建者的名称
	 */
	protected String docCreatorName = null;

	/**
	 * @return 创建者的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建者的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 会议模板的ID
	 */
	protected String fdTemplateId = null;

	/**
	 * @return 会议模板的ID
	 */
	public String getFdTemplateId() {
		return fdTemplateId;
	}

	/**
	 * @param fdTemplateId
	 *            会议模板的ID
	 */
	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}

	/**
	 * 会议模板的名称
	 */
	protected String fdTemplateName = null;

	/**
	 * @return 会议模板的名称
	 */
	public String getFdTemplateName() {
		return fdTemplateName;
	}

	/**
	 * @param fdTemplateName
	 *            会议模板的名称
	 */
	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}

	/**
	 * 召开地点的ID
	 */
	protected String fdPlaceId = null;

	/**
	 * @return 召开地点的ID
	 */
	public String getFdPlaceId() {
		return fdPlaceId;
	}

	/**
	 * @param fdPlaceId
	 *            召开地点的ID
	 */
	public void setFdPlaceId(String fdPlaceId) {
		this.fdPlaceId = fdPlaceId;
	}

	/**
	 * 召开地点的名称
	 */
	protected String fdPlaceName = null;

	/**
	 * @return 召开地点的名称
	 */
	public String getFdPlaceName() {
		return fdPlaceName;
	}

	/**
	 * @param fdPlaceName
	 *            召开地点的名称
	 */
	public void setFdPlaceName(String fdPlaceName) {
		this.fdPlaceName = fdPlaceName;
	}

	/**
	 * 所属部门的ID
	 */
	protected String docDeptId = null;

	/**
	 * @return 所属部门的ID
	 */
	public String getDocDeptId() {
		return docDeptId;
	}

	/**
	 * @param docDeptId
	 *            所属部门的ID
	 */
	public void setDocDeptId(String docDeptId) {
		this.docDeptId = docDeptId;
	}

	/**
	 * 所属部门的名称
	 */
	protected String docDeptName = null;

	/**
	 * @return 所属部门的名称
	 */
	public String getDocDeptName() {
		return docDeptName;
	}

	/**
	 * @param docDeptName
	 *            所属部门的名称
	 */
	public void setDocDeptName(String docDeptName) {
		this.docDeptName = docDeptName;
	}

	/**
	 * 主持人的ID
	 */
	protected String fdHostId = null;

	/**
	 * @return 主持人的ID
	 */
	public String getFdHostId() {
		return fdHostId;
	}

	/**
	 * @param fdHostId
	 *            主持人的ID
	 */
	public void setFdHostId(String fdHostId) {
		this.fdHostId = fdHostId;
	}

	/**
	 * 主持人的名称
	 */
	protected String fdHostName = null;

	/**
	 * @return 主持人的名称
	 */
	public String getFdHostName() {
		return fdHostName;
	}

	/**
	 * @param fdHostName
	 *            主持人的名称
	 */
	public void setFdHostName(String fdHostName) {
		this.fdHostName = fdHostName;
	}

	/**
	 * 所属会议的ID
	 */
	protected String fdMeetingId = null;

	/**
	 * @return 所属会议的ID
	 */
	public String getFdMeetingId() {
		return fdMeetingId;
	}

	/**
	 * @param fdMeetingId
	 *            所属会议的ID
	 */
	public void setFdMeetingId(String fdMeetingId) {
		this.fdMeetingId = fdMeetingId;
	}

	/**
	 * 所属会议的名称
	 */
	protected String fdMeetingName = null;

	/**
	 * @return 所属会议的名称
	 */
	public String getFdMeetingName() {
		return fdMeetingName;
	}

	/**
	 * @param fdMeetingName
	 *            所属会议的名称
	 */
	public void setFdMeetingName(String fdMeetingName) {
		this.fdMeetingName = fdMeetingName;
	}

	/**
	 * 实际会员人员的ID列表
	 */
	protected String fdActualAttendPersonIds = null;

	/**
	 * @return 实际会员人员的ID列表
	 */
	public String getFdActualAttendPersonIds() {
		return fdActualAttendPersonIds;
	}

	/**
	 * @param fdActualAttendPersonIds
	 *            实际会员人员的ID列表
	 */
	public void setFdActualAttendPersonIds(String fdActualAttendPersonIds) {
		this.fdActualAttendPersonIds = fdActualAttendPersonIds;
	}

	/**
	 * 实际会员人员的名称列表
	 */
	protected String fdActualAttendPersonNames = null;

	/**
	 * @return 实际会员人员的名称列表
	 */
	public String getFdActualAttendPersonNames() {
		return fdActualAttendPersonNames;
	}

	/**
	 * @param fdActualAttendPersonNames
	 *            实际会员人员的名称列表
	 */
	public void setFdActualAttendPersonNames(String fdActualAttendPersonNames) {
		this.fdActualAttendPersonNames = fdActualAttendPersonNames;
	}

	/**
	 * 签署人员
	 */
	private String fdSignPersonIds;
	/**
	 * 签署人员
	 */
	private String fdSignPersonNames;

	/**
	 * 签署人员
	 */
	public String getFdSignPersonIds() {
		return fdSignPersonIds;
	}

	/**
	 * 签署人员
	 */
	public void setFdSignPersonIds(String fdSignPersonIds) {
		this.fdSignPersonIds = fdSignPersonIds;
	}

	/**
	 * 签署人员
	 */
	public String getFdSignPersonNames() {
		return fdSignPersonNames;
	}

	/**
	 * 签署人员
	 */
	public void setFdSignPersonNames(String fdSignPersonNames) {
		this.fdSignPersonNames = fdSignPersonNames;
	}

	protected String fdActualOtherAttendPersons = null;

	public String getFdActualOtherAttendPersons() {
		return fdActualOtherAttendPersons;
	}

	public void setFdActualOtherAttendPersons(String fdActualOtherAttendPersons) {
		this.fdActualOtherAttendPersons = fdActualOtherAttendPersons;
	}

	/**
	 * 计划与会人员的ID列表
	 */
	protected String fdPlanAttendPersonIds = null;

	/**
	 * @return 计划与会人员的ID列表
	 */
	public String getFdPlanAttendPersonIds() {
		return fdPlanAttendPersonIds;
	}

	/**
	 * @param fdPlanAttendPersonIds
	 *            计划与会人员的ID列表
	 */
	public void setFdPlanAttendPersonIds(String fdPlanAttendPersonIds) {
		this.fdPlanAttendPersonIds = fdPlanAttendPersonIds;
	}

	/**
	 * 计划与会人员的名称列表
	 */
	protected String fdPlanAttendPersonNames = null;

	/**
	 * @return 计划与会人员的名称列表
	 */
	public String getFdPlanAttendPersonNames() {
		return fdPlanAttendPersonNames;
	}

	/**
	 * @param fdPlanAttendPersonNames
	 *            计划与会人员的名称列表
	 */
	public void setFdPlanAttendPersonNames(String fdPlanAttendPersonNames) {
		this.fdPlanAttendPersonNames = fdPlanAttendPersonNames;
	}

	/**
	 * 纪要抄送人的ID列表
	 */
	protected String fdCopyToPersonIds = null;

	/**
	 * @return 纪要抄送人的ID列表
	 */
	public String getFdCopyToPersonIds() {
		return fdCopyToPersonIds;
	}

	/**
	 * @param fdCopyToPersonIds
	 *            纪要抄送人的ID列表
	 */
	public void setFdCopyToPersonIds(String fdCopyToPersonIds) {
		this.fdCopyToPersonIds = fdCopyToPersonIds;
	}

	/**
	 * 纪要抄送人的名称列表
	 */
	protected String fdCopyToPersonNames = null;

	/**
	 * @return 纪要抄送人的名称列表
	 */
	public String getFdCopyToPersonNames() {
		return fdCopyToPersonNames;
	}

	/**
	 * @param fdCopyToPersonNames
	 *            纪要抄送人的名称列表
	 */
	public void setFdCopyToPersonNames(String fdCopyToPersonNames) {
		this.fdCopyToPersonNames = fdCopyToPersonNames;
	}

	/**
	 * 计划列席人员的ID列表
	 */
	protected String fdPlanParticipantPersonIds = null;

	/**
	 * @return 计划列席人员的ID列表
	 */
	public String getFdPlanParticipantPersonIds() {
		return fdPlanParticipantPersonIds;
	}

	/**
	 * @param fdPlanParticipantPersonIds
	 *            计划列席人员的ID列表
	 */
	public void setFdPlanParticipantPersonIds(String fdPlanParticipantPersonIds) {
		this.fdPlanParticipantPersonIds = fdPlanParticipantPersonIds;
	}

	/**
	 * 计划列席人员的名称列表
	 */
	protected String fdPlanParticipantPersonNames = null;

	/**
	 * @return 计划列席人员的名称列表
	 */
	public String getFdPlanParticipantPersonNames() {
		return fdPlanParticipantPersonNames;
	}

	/**
	 * @param fdPlanParticipantPersonNames
	 *            计划列席人员的名称列表
	 */
	public void setFdPlanParticipantPersonNames(
			String fdPlanParticipantPersonNames) {
		this.fdPlanParticipantPersonNames = fdPlanParticipantPersonNames;
	}

	protected String fdPlanOtherParticipantPersons = null;

	public String getFdPlanOtherParticipantPersons() {
		return fdPlanOtherParticipantPersons;
	}

	public void setFdPlanOtherParticipantPersons(
			String fdPlanOtherParticipantPersons) {
		this.fdPlanOtherParticipantPersons = fdPlanOtherParticipantPersons;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		docCreateTime = null;
		docPublishTime = null;
		fdHoldDate = null;
		fdFinishDate = null;
		fdHoldDuration = null;
		fdPlaceId = null;
		fdPlaceName = null;
		fdOtherPlace = null;
		fdOtherPlaceCoordinate = null;
		fdHostId = null;
		fdHostName = null;
		fdEmceeId = null;
		fdEmceeName = null;
		fdOtherHostPerson = null;
		docContent = null;
		fdHtmlContent = null;
		fdContentType = ImeetingConstant.FDCONTENTTYPE_RTF;

		fdPlanAttendPersonIds = null;
		fdPlanAttendPersonNames = null;
		fdPlanOtherAttendPerson = null;

		fdPlanParticipantPersonIds = null;
		fdPlanParticipantPersonNames = null;
		fdPlanOtherParticipantPersons = null;

		fdActualAttendPersonIds = null;
		fdActualAttendPersonNames = null;
		fdActualOtherAttendPersons = null;

		fdSignPersonIds = null;
		fdSignPersonNames = null;

		fdNotifyType = null;
		authAttNocopy = null;
		authAttNodownload = null;
		authAttNoprint = null;
		docCreatorId = null;
		docCreatorName = null;
		fdTemplateId = null;
		fdTemplateName = null;

		docDeptId = null;
		docDeptName = null;

		fdMeetingId = null;
		fdMeetingName = null;

		fdCopyToPersonIds = null;
		fdCopyToPersonNames = null;

		fdVicePlaceIds = null;
		fdVicePlaceNames = null;
		fdOtherVicePlace = null;
		fdOtherVicePlaceCoord = null;

		sysWfBusinessForm = new SysWfBusinessForm();
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingSummary.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel(
					"fdTemplate", KmImeetingTemplate.class));
			toModelPropertyMap.put("fdPlaceId", new FormConvertor_IDToModel(
					"fdPlace", KmImeetingRes.class));
			toModelPropertyMap.put("docDeptId", new FormConvertor_IDToModel(
					"docDept", SysOrgElement.class));
			toModelPropertyMap.put("fdHostId", new FormConvertor_IDToModel(
					"fdHost", SysOrgElement.class));
			toModelPropertyMap.put("fdEmceeId", new FormConvertor_IDToModel(
					"fdEmcee", SysOrgElement.class));
			toModelPropertyMap.put("fdMeetingId", new FormConvertor_IDToModel(
					"fdMeeting", KmImeetingMain.class));
			toModelPropertyMap.put("fdActualAttendPersonIds",
					new FormConvertor_IDsToModelList("fdActualAttendPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("fdSignPersonIds",
					new FormConvertor_IDsToModelList("fdSignPersons",
							SysOrgPerson.class));
			toModelPropertyMap.put("fdPlanAttendPersonIds",
					new FormConvertor_IDsToModelList("fdPlanAttendPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("fdPlanParticipantPersonIds",
					new FormConvertor_IDsToModelList(
							"fdPlanParticipantPersons", SysOrgElement.class));
			toModelPropertyMap.put("fdCopyToPersonIds",
					new FormConvertor_IDsToModelList("fdCopyToPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("fdVicePlaceIds",
					new FormConvertor_IDsToModelList("fdVicePlaces",
							KmImeetingRes.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 流程机制
	 */
	private SysWfBusinessForm sysWfBusinessForm = new SysWfBusinessForm();

	@Override
	public SysWfBusinessForm getSysWfBusinessForm() {
		return sysWfBusinessForm;
	}

	public void setSysWfBusinessForm(SysWfBusinessForm sysWfBusinessForm) {
		this.sysWfBusinessForm = sysWfBusinessForm;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	/**
	 * 文档阅读次数
	 */
	private String docReadCount = null;

	public String getDocReadCount() {
		return docReadCount;
	}

	public void setDocReadCount(String docReadCount) {
		this.docReadCount = docReadCount;
	}

	/**
	 * 阅读机制
	 */
	private ReadLogForm readLogForm = new ReadLogForm();

	@Override
    public ReadLogForm getReadLogForm() {
		return readLogForm;
	}

	/**
	 * 传阅机制
	 */
	public CirculationForm circulationForm = new CirculationForm();

	@Override
    public CirculationForm getCirculationForm() {
		return circulationForm;
	}

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

	@Override
	public Boolean getAuthChangeAtt() {
		if (authChangeAtt == null) {
			authChangeAtt = false;
		}
		return super.getAuthChangeAtt();
	}

	@Override
	public Boolean getAuthChangeReaderFlag() {
		if (authChangeReaderFlag == null) {
			authChangeReaderFlag = false;
		}
		return super.getAuthChangeReaderFlag();
	}

	/**
	 * 纪要通知人
	 */
	protected String fdNotifyPerson;

	public String getFdNotifyPerson() {
		String[] notifyPersonList = this.fdNotifyPersonList;
		this.fdNotifyPerson = "";
		for (int i = 0; i < notifyPersonList.length; i++) {
			if (notifyPersonList[i] != null) {
				this.fdNotifyPerson += notifyPersonList[i];
			}
		}
		return fdNotifyPerson;
	}

	public void setFdNotifyPerson(String fdNotifyPerson) {
		this.fdNotifyPerson = fdNotifyPerson;
	}

	/**
	 * 纪要通知人
	 */
	private String[] fdNotifyPersonList = new String[5];

	public String[] getFdNotifyPersonList() {
		return fdNotifyPersonList;
	}

	public void setFdNotifyPersonList(String[] fdNotifyPersonList) {
		this.fdNotifyPersonList = fdNotifyPersonList;
	}

	/**
	 * 分会场的列表
	 */
	protected String fdVicePlaceIds = null;

	protected String fdVicePlaceNames = null;

	public String getFdVicePlaceIds() {
		return fdVicePlaceIds;
	}

	public void setFdVicePlaceIds(String fdVicePlaceIds) {
		this.fdVicePlaceIds = fdVicePlaceIds;
	}

	public String getFdVicePlaceNames() {
		return fdVicePlaceNames;
	}

	public void setFdVicePlaceNames(String fdVicePlaceNames) {
		this.fdVicePlaceNames = fdVicePlaceNames;
	}

	private String fdOtherVicePlace;

	private String fdOtherVicePlaceCoord;

	public String getFdOtherVicePlace() {
		return fdOtherVicePlace;
	}

	public void setFdOtherVicePlace(String fdOtherVicePlace) {
		this.fdOtherVicePlace = fdOtherVicePlace;
	}

	public String getFdOtherVicePlaceCoord() {
		return fdOtherVicePlaceCoord;
	}

	public void setFdOtherVicePlaceCoord(String fdOtherVicePlaceCoord) {
		this.fdOtherVicePlaceCoord = fdOtherVicePlaceCoord;
	}

	/**
	 * 是否启用电子签章
	 */
	private String fdSignEnable;

	/**
	 * 是否启用电子签章
	 */
	public String getFdSignEnable() {
		return fdSignEnable;
	}

	/**
	 * 是否启用电子签章
	 */
	public void setFdSignEnable(String fdSignEnable) {
		this.fdSignEnable = fdSignEnable;
	}

}
