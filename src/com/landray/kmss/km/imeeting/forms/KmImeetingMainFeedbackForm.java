package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingMainFeedback;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 会议回执 Form
 */
public class KmImeetingMainFeedbackForm extends ExtendForm implements
		IAttachmentForm {

	/**
	 * 类型，0或空表示与会人员，1表示别人邀请
	 */
	protected String fdFromType = null;

	public String getFdFromType() {
		return fdFromType;
	}

	public void setFdFromType(String fdFromType) {
		this.fdFromType = fdFromType;
	}

	/**
	 * 回执类型
	 */
	protected String fdType = null;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 所属议题，铂恩对接需要根据议题查对应的参加回执
	 */
	protected String fdAgendaId = null;

	public String getFdAgendaId() {
		return fdAgendaId;
	}

	public void setFdAgendaId(String fdAgendaId) {
		this.fdAgendaId = fdAgendaId;
	}

	/**
	 * 所属议题的单位，铂恩对接用
	 */
	protected String fdUnitName = null;

	public String getFdUnitName() {
		return fdUnitName;
	}

	public void setFdUnitName(String fdUnitName) {
		this.fdUnitName = fdUnitName;
	}

	/**
	 * 回执操作类型
	 */
	protected String fdOperateType = null;

	/**
	 * @return 回执类型
	 */
	public String getFdOperateType() {
		return fdOperateType;
	}

	/**
	 * @param fdOperateType
	 *            回执类型
	 */
	public void setFdOperateType(String fdOperateType) {
		this.fdOperateType = fdOperateType;
	}

	/**
	 * 原因
	 */
	protected String fdReason = null;

	/**
	 * @return 原因
	 */
	public String getFdReason() {
		return fdReason;
	}

	/**
	 * @param fdReason
	 *            原因
	 */
	public void setFdReason(String fdReason) {
		this.fdReason = fdReason;
	}

	/**
	 * 创建时间
	 */
	protected String docCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 修改时间
	 */
	protected String docAlterTime = null;

	public String getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 回执次数
	 */
	protected String fdFeedbackCount = null;

	/**
	 * @return 回执次数
	 */
	public String getFdFeedbackCount() {
		return fdFeedbackCount;
	}

	/**
	 * @param fdFeedbackCount
	 *            回执次数
	 */
	public void setFdFeedbackCount(String fdFeedbackCount) {
		this.fdFeedbackCount = fdFeedbackCount;
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
	 * 实际参与人员的ID
	 */
	protected String docAttendId = null;

	/**
	 * @return 实际参与人员的ID
	 */
	public String getDocAttendId() {
		return docAttendId;
	}

	/**
	 * @param docAttendIdId
	 *            实际参与人员的ID
	 */
	public void setDocAttendId(String docAttendId) {
		this.docAttendId = docAttendId;
	}

	/**
	 * 实际参与人员的名称
	 */
	protected String docAttendName = null;

	/**
	 * @return 实际参与人员的名称
	 */
	public String getDocAttendName() {
		return docAttendName;
	}

	/**
	 * @param docAttendIdName
	 *            实际参与人员的名称
	 */
	public void setDocAttendName(String docAttendName) {
		this.docAttendName = docAttendName;
	}
	
	/**
	 * 邀请人
	 */
	private String fdInvitePersonId;
	
	/**
	 * 邀请人
	 */
	private String fdInvitePersonName;
	
	/**
	 * 邀请人
	 */
	public String getFdInvitePersonId() {
		return fdInvitePersonId;
	}

	/**
	 * 邀请人
	 */
	public void setFdInvitePersonId(String fdInvitePersonId) {
		this.fdInvitePersonId = fdInvitePersonId;
	}

	/**
	 * 邀请人
	 */
	public String getFdInvitePersonName() {
		return fdInvitePersonName;
	}

	/**
	 * 邀请人
	 */
	public void setFdInvitePersonName(String fdInvitePersonName) {
		this.fdInvitePersonName = fdInvitePersonName;
	}
	
	/**
	 * 议题人员（除联络员）议题ID
	 */
	private String fdAttendAgendaId;
	
	/**
	 * 议题人员（除联络员）议题ID
	 */
	public String getFdAttendAgendaId() {
		return fdAttendAgendaId;
	}

	/**
	 * 议题人员（除联络员）议题ID
	 */
	public void setFdAttendAgendaId(String fdAttendAgendaId) {
		this.fdAttendAgendaId = fdAttendAgendaId;
	}

	/*
	 * UA
	 */
	private Integer clientType = null;

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdType = null;
		fdAgendaId = null;
		fdUnitName = null;
		fdFromType = null;
		fdOperateType = null;
		fdReason = null;
		docCreateTime = null;
		docAlterTime = null;
		fdFeedbackCount = null;
		docCreatorId = null;
		docCreatorName = null;
		fdMeetingId = null;
		fdMeetingName = null;
		docAttendId = null;
		docAttendName = null;
		fdInvitePersonId = null;
		fdInvitePersonName = null;
		fdAttendAgendaId = null;
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return KmImeetingMainFeedback.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdMeetingId", new FormConvertor_IDToModel(
					"fdMeeting", KmImeetingMain.class));
			toModelPropertyMap.put("docAttendId", new FormConvertor_IDToModel(
					"docAttend", SysOrgPerson.class));
			toModelPropertyMap.put("fdInvitePersonId", new FormConvertor_IDToModel(
					"fdInvitePerson", SysOrgPerson.class));
		}
		return toModelPropertyMap;
	}

	/**
	 * 附件实现
	 */
	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}

	private String fdOtherPlace;

	public String getFdOtherPlace() {
		return fdOtherPlace;
	}

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
}
