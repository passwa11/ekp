package com.landray.kmss.km.imeeting.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseCreateInfoModel;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainFeedbackForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;

/**
 * 会议回执
 */
public class KmImeetingMainFeedback extends BaseCreateInfoModel implements
		IAttachment {

	/**
	 * 类型，0或空表示与会人员，1表示别人邀请
	 */
	protected String fdFromType;

	public String getFdFromType() {
		return fdFromType;
	}

	public void setFdFromType(String fdFromType) {
		this.fdFromType = fdFromType;
	}

	/**
	 * 邀请人
	 */
	protected SysOrgPerson fdInvitePerson;

	public SysOrgPerson getFdInvitePerson() {
		return fdInvitePerson;
	}

	public void setFdInvitePerson(SysOrgPerson fdInvitePerson) {
		this.fdInvitePerson = fdInvitePerson;
	}

	/**
	 * 回执类型（主持人，组织人，与会人员，列席人员，议题汇报人，议题会议联络员）
	 */
	protected String fdType;

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	/**
	 * 所属议题，铂恩对接需要根据议题查对应的参加回执
	 */
	protected String fdAgendaId;

	public String getFdAgendaId() {
		return fdAgendaId;
	}

	public void setFdAgendaId(String fdAgendaId) {
		this.fdAgendaId = fdAgendaId;
	}

	/**
	 * 所属议题的单位，铂恩对接用
	 */
	protected String fdUnitName;

	public String getFdUnitName() {
		return fdUnitName;
	}

	public void setFdUnitName(String fdUnitName) {
		this.fdUnitName = fdUnitName;
	}

	/**
	 * 回执操作类型
	 */
	protected String fdOperateType;

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
	protected String fdReason;

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
	protected Date docCreateTime;

	/**
	 * @return 创建时间
	 */
	@Override
    public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            创建时间
	 */
	@Override
    public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * 修改时间
	 */
	protected Date docAlterTime;

	public Date getDocAlterTime() {
		return docAlterTime;
	}

	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 回执次数
	 */
	protected Integer fdFeedbackCount;

	/**
	 * @return 回执次数
	 */
	public Integer getFdFeedbackCount() {
		return fdFeedbackCount;
	}

	/**
	 * @param fdFeedbackCount
	 *            回执次数
	 */
	public void setFdFeedbackCount(Integer fdFeedbackCount) {
		this.fdFeedbackCount = fdFeedbackCount;
	}

	/**
	 * 所属会议
	 */
	protected KmImeetingMain fdMeeting;

	/**
	 * @return 所属会议
	 */
	public KmImeetingMain getFdMeeting() {
		return fdMeeting;
	}

	/**
	 * @param fdMeeting
	 *            所属会议
	 */
	public void setFdMeeting(KmImeetingMain fdMeeting) {
		this.fdMeeting = fdMeeting;
	}

	/**
	 * 实际参与人员
	 */
	protected SysOrgPerson docAttend;

	/**
	 * @return 实际参与人员
	 */
	public SysOrgPerson getDocAttend() {
		return docAttend;
	}

	/**
	 * @param docAttendId
	 *            实际参与人员
	 */
	public void setDocAttend(SysOrgPerson docAttend) {
		this.docAttend = docAttend;
	}

	@Override
    public Class getFormClass() {
		return KmImeetingMainFeedbackForm.class;
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
	 * 客户端类型标识
	 */
	protected Integer clientType = null;

	public Integer getClientType() {
		return clientType;
	}

	public void setClientType(Integer clientType) {
		this.clientType = clientType;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdMeeting.fdId", "fdMeetingId");
			toFormPropertyMap.put("fdMeeting.fdName", "fdMeetingName");
			toFormPropertyMap.put("docAttend.fdId", "docAttendId");
			toFormPropertyMap.put("docAttend.fdName", "docAttendName");
			toFormPropertyMap.put("fdInvitePerson.fdId", "fdInvitePersonId");
			toFormPropertyMap.put("fdInvitePerson.fdName", "fdInvitePersonName");
		}
		return toFormPropertyMap;
	}

	/**
	 * 附件实现
	 */
	private AutoHashMap autoHashMap = new AutoHashMap(
			AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {
		return autoHashMap;
	}
}
