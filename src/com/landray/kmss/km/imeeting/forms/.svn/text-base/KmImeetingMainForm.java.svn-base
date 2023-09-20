package com.landray.kmss.km.imeeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.model.KmImeetingDevice;
import com.landray.kmss.km.imeeting.model.KmImeetingEquipment;
import com.landray.kmss.km.imeeting.model.KmImeetingMain;
import com.landray.kmss.km.imeeting.model.KmImeetingRes;
import com.landray.kmss.km.imeeting.model.KmImeetingTemplate;
import com.landray.kmss.sys.agenda.forms.SysAgendaMainForm;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.news.forms.SysNewsPublishMainForm;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishMainForm;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindMainContextForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
 * 会议安排 Form
 */
public class KmImeetingMainForm extends ExtendAuthForm implements
		ISysWfMainForm, ISysNewsPublishMainForm, ISysAgendaMainForm,
		ISysReadLogForm, ISysCirculationForm, IAttachmentForm {

	private static final long serialVersionUID = -4176229346343577760L;

	/**
	 * 是否视频会议
	 */
	private String fdIsVideo;

	/**
	 * 是否需要会议地点
	 */
	private String fdNeedPlace;

	/**
	 * 是否需要回执，快速发起的会议可以不需要回执
	 */
	private String fdNeedFeedback;

	public String getFdNeedFeedback() {
		if (StringUtil.isNull(fdNeedFeedback)) {
			fdNeedFeedback = "true";
		}
		return fdNeedFeedback;
	}

	public void setFdNeedFeedback(String fdNeedFeedback) {
		this.fdNeedFeedback = fdNeedFeedback;
	}

	public String getFdIsVideo() {
		if (StringUtil.isNull(fdIsVideo)) {
			fdIsVideo = "false";
		}
		return fdIsVideo;
	}

	public void setFdIsVideo(String fdIsVideo) {
		this.fdIsVideo = fdIsVideo;
	}

	public String getFdNeedPlace() {
		if (StringUtil.isNull(fdNeedPlace)) {
			fdNeedPlace = "true";
		}
		return fdNeedPlace;
	}

	public void setFdNeedPlace(String fdNeedPlace) {
		this.fdNeedPlace = fdNeedPlace;
	}

	/**
	 * bam2集成字段
	 */
	protected String fdModelId = null;

	protected String fdModelName = null;

	protected String fdWorkId = null;

	protected String fdPhaseId = null;

	public String getFdModelId() {
		return fdModelId;
	}

	public void setFdModelId(String fdModelId) {
		this.fdModelId = fdModelId;
	}

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}

	public String getFdWorkId() {
		return fdWorkId;
	}

	public void setFdWorkId(String fdWorkId) {
		this.fdWorkId = fdWorkId;
	}

	public String getFdPhaseId() {
		return fdPhaseId;
	}

	public void setFdPhaseId(String fdPhaseId) {
		this.fdPhaseId = fdPhaseId;
	}

	/**
	 * 会议名称
	 */
	protected String fdName = null;

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
	 * 是否开启了会议，铂恩用
	 */
	private String fdIsTopic;

	public String getFdIsTopic() {
		return fdIsTopic;
	}

	public void setFdIsTopic(String fdIsTopic) {
		this.fdIsTopic = fdIsTopic;
	}

	/**
	 * 是否开启了会议，铂恩用
	 */
	private String isBegin;

	public String getIsBegin() {
		return isBegin;
	}

	public void setIsBegin(String isBegin) {
		this.isBegin = isBegin;
	}

	private String isFace;
	
	public String getIsFace() {
		if (StringUtil.isNull(isFace)) {
			isFace = "false";
		}
		return isFace;
	}

	public void setIsFace(String isFace) {
		this.isFace = isFace;
	}
	

	private String isCloud;

	public String getIsCloud() {
		if (StringUtil.isNull(isCloud)) {
			isCloud = "false";
		}
		return isCloud;
	}

	public void setIsCloud(String isCloud) {
		this.isCloud = isCloud;
	}

	/**
	 * 召开日期
	 */
	protected String fdHoldDate = null;

	/**
	 * @return 召开日期
	 */
	public String getFdHoldDate() {
		return fdHoldDate;
	}

	/**
	 * @param fdHoldDate
	 *            召开日期
	 */
	public void setFdHoldDate(String fdHoldDate) {
		this.fdHoldDate = fdHoldDate;
	}

	/**
	 * 提前结束日期
	 */
	protected String fdEarlyFinishDate = null;

	/**
	 * @return 提前结束日期
	 */
	public String getFdEarlyFinishDate() {
		return fdEarlyFinishDate;
	}

	/**
	 * @param fdEarlyFinishDate
	 *            提前结束日期
	 */
	public void setFdEarlyFinishDate(String fdEarlyFinishDate) {
		this.fdEarlyFinishDate = fdEarlyFinishDate;
	}

	/**
	 * 结束日期
	 */
	protected String fdFinishDate = null;

	/**
	 * @return 结束日期
	 */
	public String getFdFinishDate() {
		return fdFinishDate;
	}

	/**
	 * @param fdFinishDate
	 *            结束日期
	 */
	public void setFdFinishDate(String fdFinishDate) {
		this.fdFinishDate = fdFinishDate;
	}

	/**
	 * 会议历时
	 */
	protected String fdHoldDuration = null;

	/**
	 * @return 会议历时
	 */
	public String getFdHoldDuration() {
		return fdHoldDuration;
	}

	/**
	 * @param fdHoldDuration
	 *            会议历时
	 */
	public void setFdHoldDuration(String fdHoldDuration) {
		this.fdHoldDuration = fdHoldDuration;
	}

	protected String fdHoldDurationHour = null;

	public String getFdHoldDurationHour() {
		Double hour = 0d;
		if (StringUtil.isNotNull(fdHoldDuration)) {
			Double time = new Double(fdHoldDuration);
			Double division = 3600d * 1000d;
			hour = time / division;
		}
		return String.format("%.1f", hour);
	}

	public void setFdHoldDurationHour(String fdHoldDurationHour) {
		this.fdHoldDurationHour = fdHoldDurationHour;
		if (StringUtil.isNull(this.fdHoldDuration)
				&& StringUtil.isNotNull(fdHoldDurationHour)) {
			Double hour = new Double(fdHoldDurationHour);
			hour = hour * 3600d * 1000d;
			this.fdHoldDuration = hour.toString();
		}
	}

	/**
	 * 会议目的
	 */
	protected String fdMeetingAim = null;

	/**
	 * @return 会议目的
	 */
	public String getFdMeetingAim() {
		return fdMeetingAim;
	}

	/**
	 * @param fdMeetingAim
	 *            会议目的
	 */
	public void setFdMeetingAim(String fdMeetingAim) {
		this.fdMeetingAim = fdMeetingAim;
	}

	/**
	 * 会议编号
	 */
	protected String fdMeetingNum = null;

	/**
	 * @return 会议编号
	 */
	public String getFdMeetingNum() {
		return fdMeetingNum;
	}

	/**
	 * @param fdMeetingNum
	 *            会议编号
	 */
	public void setFdMeetingNum(String fdMeetingNum) {
		this.fdMeetingNum = fdMeetingNum;
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
	 * 外部与会人员
	 */
	protected String fdOtherAttendPerson = null;

	/**
	 * @return 外部与会人员
	 */
	public String getFdOtherAttendPerson() {
		return fdOtherAttendPerson;
	}

	/**
	 * @param fdOtherAttendPerson
	 *            外部与会人员
	 */
	public void setFdOtherAttendPerson(String fdOtherAttendPerson) {
		this.fdOtherAttendPerson = fdOtherAttendPerson;
	}

	/**
	 * 外部列席人员
	 */
	protected String fdOtherParticipantPerson = null;

	/**
	 * @return 外部列席人员
	 */
	public String getFdOtherParticipantPerson() {
		return fdOtherParticipantPerson;
	}

	/**
	 * @param fdOtherParticipantPerson
	 *            外部列席人员
	 */
	public void setFdOtherParticipantPerson(String fdOtherParticipantPerson) {
		this.fdOtherParticipantPerson = fdOtherParticipantPerson;
	}

	/**
	 * 外部抄送人员
	 */
	protected String fdOtherCopyToPerson = null;

	/**
	 * @return 外部抄送人员
	 */
	public String getFdOtherCopyToPerson() {
		return fdOtherCopyToPerson;
	}

	/**
	 * @param fdOtherCopyToPerson
	 *            外部抄送人员
	 */
	public void setFdOtherCopyToPerson(String fdOtherCopyToPerson) {
		this.fdOtherCopyToPerson = fdOtherCopyToPerson;
	}

	private String fdOtherAssistPersons;

	public String getFdOtherAssistPersons() {
		return fdOtherAssistPersons;
	}

	public void setFdOtherAssistPersons(String fdOtherAssistPersons) {
		this.fdOtherAssistPersons = fdOtherAssistPersons;
	}

	/**
	 * 纪要完成时间
	 */
	protected String fdSummaryCompleteTime = null;

	/**
	 * @return 纪要完成时间
	 */
	public String getFdSummaryCompleteTime() {
		return fdSummaryCompleteTime;
	}

	/**
	 * @param fdSummaryCompleteTime
	 *            纪要完成时间
	 */
	public void setFdSummaryCompleteTime(String fdSummaryCompleteTime) {
		this.fdSummaryCompleteTime = fdSummaryCompleteTime;
	}

	/**
	 * 是否催办纪要
	 */
	protected String fdIsHurrySummary = null;

	/**
	 * @return 是否催办纪要
	 */
	public String getFdIsHurrySummary() {
		return fdIsHurrySummary;
	}

	/**
	 * @param fdIsHurrySummary
	 *            是否催办纪要
	 */
	public void setFdIsHurrySummary(String fdIsHurrySummary) {
		this.fdIsHurrySummary = fdIsHurrySummary;
	}

	/**
	 * 提前几天催办
	 */
	protected String fdHurryDate = null;

	/**
	 * @return 提前几天催办
	 */
	public String getFdHurryDate() {
		return fdHurryDate;
	}

	/**
	 * @param fdHurryDate
	 *            提前几天催办
	 */
	public void setFdHurryDate(String fdHurryDate) {
		this.fdHurryDate = fdHurryDate;
	}

	/**
	 * 预计与会人员
	 */
	protected String fdAttendNum = null;

	/**
	 * @return 预计与会人员
	 */
	public String getFdAttendNum() {
		return fdAttendNum;
	}

	/**
	 * @param fdAttendNum
	 *            预计与会人员
	 */
	public void setFdAttendNum(String fdAttendNum) {
		this.fdAttendNum = fdAttendNum;
	}

	/**
	 * 会场布置要求
	 */
	protected String fdArrange = null;

	/**
	 * @return 会场布置要求
	 */
	public String getFdArrange() {
		return fdArrange;
	}

	/**
	 * @param fdArrange
	 *            会场布置要求
	 */
	public void setFdArrange(String fdArrange) {
		this.fdArrange = fdArrange;
	}

	/**
	 * 是否已发送会议通知
	 */
	protected String isNotify = null;

	public String getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(String isNotify) {
		this.isNotify = isNotify;
	}

	/**
	 * 会议通知选项
	 */
	protected String fdNotifyType = null;

	/**
	 * @return 会议通知选项
	 */
	public String getFdNotifyType() {
		return fdNotifyType;
	}

	/**
	 * @param fdNotifyType
	 *            会议通知选项
	 */
	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	/**
	 * 会议通知方式
	 */
	private String fdNotifyWay = null;

	public String getFdNotifyWay() {
		return fdNotifyWay;
	}

	public void setFdNotifyWay(String fdNotifyWay) {
		this.fdNotifyWay = fdNotifyWay;
	}

	/**
	 * 同步时机
	 */
	protected String syncDataToCalendarTime = null;

	/**
	 * @return 同步时机
	 */
	public String getSyncDataToCalendarTime() {
		return syncDataToCalendarTime;
	}

	/**
	 * @param syncDataToCalendarTime
	 *            同步时机
	 */
	public void setSyncDataToCalendarTime(String syncDataToCalendarTime) {
		this.syncDataToCalendarTime = syncDataToCalendarTime;
	}

	/**
	 * 会议是否已纪要
	 */
	protected String fdSummaryFlag = null;

	/**
	 * @return 会议是否已纪要
	 */
	public String getFdSummaryFlag() {
		return fdSummaryFlag;
	}

	/**
	 * @param fdSummaryFlag
	 *            会议是否已纪要
	 */
	public void setFdSummaryFlag(String fdSummaryFlag) {
		this.fdSummaryFlag = fdSummaryFlag;
	}

	/**
	 * 会议取消原因
	 */
	protected String cancelMeetingReason = null;

	public String getCancelMeetingReason() {
		return cancelMeetingReason;
	}

	public void setCancelMeetingReason(String cancelMeetingReason) {
		this.cancelMeetingReason = cancelMeetingReason;
	}

	/**
	 * 会议是否变更
	 */
	protected String fdChangeMeetingFlag = null;

	/**
	 * @return 会议是否变更
	 */
	public String getFdChangeMeetingFlag() {
		return fdChangeMeetingFlag;
	}

	/**
	 * @param fdChangeMeeting
	 *            会议是否变更
	 */
	public void setFdChangeMeetingFlag(String fdChangeMeetingFlag) {
		this.fdChangeMeetingFlag = fdChangeMeetingFlag;
	}

	/**
	 * 会议变更原因
	 */
	protected String changeMeetingReason = null;

	public String getChangeMeetingReason() {
		return changeMeetingReason;
	}

	public void setChangeMeetingReason(String changeMeetingReason) {
		this.changeMeetingReason = changeMeetingReason;
	}

	/**
	 * 记录会议变更前的内容（JSON）
	 */
	protected String beforeChangeContent = null;

	public String getBeforeChangeContent() {
		return beforeChangeContent;
	}

	public void setBeforeChangeContent(String beforeChangeContent) {
		this.beforeChangeContent = beforeChangeContent;
	}

	/**
	 * 备注
	 */
	private String fdRemark;

	public String getFdRemark() {
		return fdRemark;
	}

	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}

	/**
	 * 会议过期清除待办标记
	 */
	private String fdCleanTodoFlag = null;

	public String getFdCleanTodoFlag() {
		return fdCleanTodoFlag;
	}

	public void setFdCleanTodoFlag(String fdCleanTodoFlag) {
		this.fdCleanTodoFlag = fdCleanTodoFlag;
	}

	/**
	 * 会议地点的ID
	 */
	protected String fdPlaceId = null;

	/**
	 * @return 会议地点的ID
	 */
	public String getFdPlaceId() {
		return fdPlaceId;
	}

	/**
	 * @param fdPlaceId
	 *            会议地点的ID
	 */
	public void setFdPlaceId(String fdPlaceId) {
		this.fdPlaceId = fdPlaceId;
	}

	/**
	 * 会议地点的名称
	 */
	protected String fdPlaceName = null;

	/**
	 * @return 会议地点的名称
	 */
	public String getFdPlaceName() {
		return fdPlaceName;
	}

	/**
	 * @param fdPlaceName
	 *            会议地点的名称
	 */
	public void setFdPlaceName(String fdPlaceName) {
		this.fdPlaceName = fdPlaceName;
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

	private String fdUserTime;

	public String getFdUserTime() {
		return fdUserTime;
	}

	public void setFdUserTime(String fdUserTime) {
		this.fdUserTime = fdUserTime;
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
	 * 创建时间
	 */
	protected String docCreateTime = null;

	public String getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
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

	// 组织人标识
	protected String emccType;

	public String getEmccType() {
		return emccType;
	}

	public void setEmccType(String emccType) {
		this.emccType = emccType;
	}


	/**
	 * 发送会议通知者
	 */
	protected String fdNotifyerId;

	protected String fdNotifyerName;

	public String getFdNotifyerId() {
		return fdNotifyerId;
	}

	public void setFdNotifyerId(String fdNotifyerId) {
		this.fdNotifyerId = fdNotifyerId;
	}

	public String getFdNotifyerName() {
		return fdNotifyerName;
	}

	public void setFdNotifyerName(String fdNotifyerName) {
		this.fdNotifyerName = fdNotifyerName;
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
	 * 纪要录入人的ID
	 */
	protected String fdSummaryInputPersonId = null;

	public String getFdSummaryInputPersonId() {
		return fdSummaryInputPersonId;
	}

	public void setFdSummaryInputPersonId(String fdSummaryInputPersonId) {
		this.fdSummaryInputPersonId = fdSummaryInputPersonId;
	}

	/**
	 * 纪要录入人的名称
	 */
	protected String fdSummaryInputPersonName = null;

	public String getFdSummaryInputPersonName() {
		return fdSummaryInputPersonName;
	}

	public void setFdSummaryInputPersonName(String fdSummaryInputPersonName) {
		this.fdSummaryInputPersonName = fdSummaryInputPersonName;
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

	protected String fdControlPersonId = null;
	protected String fdControlPersonName = null;

	public String getFdControlPersonId() {
		return fdControlPersonId;
	}

	public void setFdControlPersonId(String fdControlPersonId) {
		this.fdControlPersonId = fdControlPersonId;
	}

	public String getFdControlPersonName() {
		return fdControlPersonName;
	}

	public void setFdControlPersonName(String fdControlPersonName) {
		this.fdControlPersonName = fdControlPersonName;
	}

	/**
	 * 主持人号码
	 */
	protected String fdHostMobileNo = null;

	public String getFdHostMobileNo() {
		return this.fdHostMobileNo;
	}

	public void setFdHostMobileNo(String fdHostMobileNo) {
		this.fdHostMobileNo = fdHostMobileNo;
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
	 * 所属会议的表单
	 */
	protected AutoArrayList kmImeetingMainFeedbackForms = new AutoArrayList(
			KmImeetingMainFeedbackForm.class);

	/**
	 * @return 所属会议的表单
	 */
	public AutoArrayList getKmImeetingMainFeedbackForms() {
		return kmImeetingMainFeedbackForms;
	}

	/**
	 * @param kmImeetingMainFeedbackForms
	 *            所属会议的表单
	 */
	public void setKmImeetingMainFeedbackForms(
			AutoArrayList kmImeetingMainFeedbackForms) {
		this.kmImeetingMainFeedbackForms = kmImeetingMainFeedbackForms;
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
	 * 回执截止时间
	 */
	protected String fdFeedBackDeadline;

	public String getFdFeedBackDeadline() {
		return fdFeedBackDeadline;
	}

	public void setFdFeedBackDeadline(String fdFeedBackDeadline) {
		this.fdFeedBackDeadline = fdFeedBackDeadline;
	}

	/**
	 * 会议辅助设备的ID列表
	 */
	protected String kmImeetingDeviceIds = null;

	/**
	 * @return 会议辅助设备的ID列表
	 */
	public String getKmImeetingDeviceIds() {
		return kmImeetingDeviceIds;
	}

	/**
	 * @param kmImeetingDeviceIds
	 *            会议辅助设备的ID列表
	 */
	public void setKmImeetingDeviceIds(String kmImeetingDeviceIds) {
		this.kmImeetingDeviceIds = kmImeetingDeviceIds;
	}

	/**
	 * 会议辅助设备的名称列表
	 */
	protected String kmImeetingDeviceNames = null;

	/**
	 * @return 会议辅助设备的名称列表
	 */
	public String getKmImeetingDeviceNames() {
		return kmImeetingDeviceNames;
	}

	/**
	 * @param kmImeetingDeviceNames
	 *            会议辅助设备的名称列表
	 */
	public void setKmImeetingDeviceNames(String kmImeetingDeviceNames) {
		this.kmImeetingDeviceNames = kmImeetingDeviceNames;
	}

	protected String kmImeetingEquipmentIds = null;

	public String getKmImeetingEquipmentIds() {
		return kmImeetingEquipmentIds;
	}

	public void setKmImeetingEquipmentIds(String kmImeetingEquipmentIds) {
		this.kmImeetingEquipmentIds = kmImeetingEquipmentIds;
	}

	protected String kmImeetingEquipmentNames = null;

	public String getKmImeetingEquipmentNames() {
		return StringUtil.XMLEscape(kmImeetingEquipmentNames);
	}

	public void setKmImeetingEquipmentNames(String kmImeetingEquipmentNames) {
		String xmlEscape = StringUtil.XMLEscape(kmImeetingEquipmentNames);
		this.kmImeetingEquipmentNames = xmlEscape;
	}

	/**
	 * 与会人员的ID列表
	 */
	protected String fdAttendPersonIds = null;

	/**
	 * @return 与会人员的ID列表
	 */
	public String getFdAttendPersonIds() {
		return fdAttendPersonIds;
	}

	/**
	 * @param fdAttendPersonIds
	 *            与会人员的ID列表
	 */
	public void setFdAttendPersonIds(String fdAttendPersonIds) {
		this.fdAttendPersonIds = fdAttendPersonIds;
	}

	/**
	 * 与会人员的名称列表
	 */
	protected String fdAttendPersonNames = null;

	/**
	 * @return 与会人员的名称列表
	 */
	public String getFdAttendPersonNames() {
		return fdAttendPersonNames;
	}

	/**
	 * @param fdAttendPersonNames
	 *            与会人员的名称列表
	 */
	public void setFdAttendPersonNames(String fdAttendPersonNames) {
		this.fdAttendPersonNames = fdAttendPersonNames;
	}

	/**
	 * 监票人员的ID列表
	 */
	protected String fdBallotPersonIds = null;
	protected String fdBallotPersonNames = null;

	public String getFdBallotPersonIds() {
		return fdBallotPersonIds;
	}

	public void setFdBallotPersonIds(String fdBallotPersonIds) {
		this.fdBallotPersonIds = fdBallotPersonIds;
	}

	public String getFdBallotPersonNames() {
		return fdBallotPersonNames;
	}

	public void setFdBallotPersonNames(String fdBallotPersonNames) {
		this.fdBallotPersonNames = fdBallotPersonNames;
	}

	/**
	 * 会议列席人员的ID列表
	 */
	protected String fdParticipantPersonIds = null;

	/**
	 * @return 会议列席人员的ID列表
	 */
	public String getFdParticipantPersonIds() {
		return fdParticipantPersonIds;
	}

	/**
	 * @param fdParticipantPersonIds
	 *            会议列席人员的ID列表
	 */
	public void setFdParticipantPersonIds(String fdParticipantPersonIds) {
		this.fdParticipantPersonIds = fdParticipantPersonIds;
	}

	/**
	 * 会议列席人员的名称列表
	 */
	protected String fdParticipantPersonNames = null;

	/**
	 * @return 会议列席人员的名称列表
	 */
	public String getFdParticipantPersonNames() {
		return fdParticipantPersonNames;
	}

	/**
	 * @param fdParticipantPersonNames
	 *            会议列席人员的名称列表
	 */
	public void setFdParticipantPersonNames(String fdParticipantPersonNames) {
		this.fdParticipantPersonNames = fdParticipantPersonNames;
	}

	/**
	 * 其他参加人员(代理人员、受邀参加人员)
	 */
	protected String fdOtherPersonIds;

	public String getFdOtherPersonIds() {
		return fdOtherPersonIds;
	}

	public void setFdOtherPersonIds(String fdOtherPersonIds) {
		this.fdOtherPersonIds = fdOtherPersonIds;
	}

	protected String fdOtherPersonNames;

	public String getFdOtherPersonNames() {
		return fdOtherPersonNames;
	}

	public void setFdOtherPersonNames(String fdOtherPersonNames) {
		this.fdOtherPersonNames = fdOtherPersonNames;
	}

	/**
	 * 抄送人员的ID列表
	 */
	protected String fdCopyToPersonIds = null;

	/**
	 * @return 抄送人员的ID列表
	 */
	public String getFdCopyToPersonIds() {
		return fdCopyToPersonIds;
	}

	/**
	 * @param fdCopyToPersonIds
	 *            抄送人员的ID列表
	 */
	public void setFdCopyToPersonIds(String fdCopyToPersonIds) {
		this.fdCopyToPersonIds = fdCopyToPersonIds;
	}

	/**
	 * 抄送人员的名称列表
	 */
	protected String fdCopyToPersonNames = null;

	/**
	 * @return 抄送人员的名称列表
	 */
	public String getFdCopyToPersonNames() {
		return fdCopyToPersonNames;
	}

	/**
	 * @param fdCopyToPersonNames
	 *            抄送人员的名称列表
	 */
	public void setFdCopyToPersonNames(String fdCopyToPersonNames) {
		this.fdCopyToPersonNames = fdCopyToPersonNames;
	}

	protected String fdInvitePersonIds = null;
	protected String fdInvitePersonNames = null;
	protected String fdOtherInvitePersons = null;

	public String getFdInvitePersonIds() {
		return fdInvitePersonIds;
	}

	public void setFdInvitePersonIds(String fdInvitePersonIds) {
		this.fdInvitePersonIds = fdInvitePersonIds;
	}

	public String getFdInvitePersonNames() {
		return fdInvitePersonNames;
	}

	public void setFdInvitePersonNames(String fdInvitePersonNames) {
		this.fdInvitePersonNames = fdInvitePersonNames;
	}

	public String getFdOtherInvitePersons() {
		return fdOtherInvitePersons;
	}

	public void setFdOtherInvitePersons(String fdOtherInvitePersons) {
		this.fdOtherInvitePersons = fdOtherInvitePersons;
	}

	/**
	 * 会议协助人的ID列表
	 */
	protected String fdAssistPersonIds = null;

	/**
	 * @return 会议协助人的ID列表
	 */
	public String getFdAssistPersonIds() {
		return fdAssistPersonIds;
	}

	/**
	 * @param fdAssistPersonIds
	 *            会议协助人的ID列表
	 */
	public void setFdAssistPersonIds(String fdAssistPersonIds) {
		this.fdAssistPersonIds = fdAssistPersonIds;
	}

	/**
	 * 会议协助人的名称列表
	 */
	protected String fdAssistPersonNames = null;

	/**
	 * @return 会议协助人的名称列表
	 */
	public String getFdAssistPersonNames() {
		return fdAssistPersonNames;
	}

	/**
	 * @param fdAssistPersonNames
	 *            会议协助人的名称列表
	 */
	public void setFdAssistPersonNames(String fdAssistPersonNames) {
		this.fdAssistPersonNames = fdAssistPersonNames;
	}

	/**
	 * 所属会议安排的表单
	 */
	protected AutoArrayList kmImeetingAgendaForms = new AutoArrayList(
			KmImeetingAgendaForm.class);

	/**
	 * @return 所属会议安排的表单
	 */
	public AutoArrayList getKmImeetingAgendaForms() {
		return kmImeetingAgendaForms;
	}

	/**
	 * @param kmImeetingAgendaForms
	 *            所属会议安排的表单
	 */
	public void setKmImeetingAgendaForms(AutoArrayList kmImeetingAgendaForms) {
		this.kmImeetingAgendaForms = kmImeetingAgendaForms;
	}

	/**
	 * 所属会议的表单
	 */
	protected AutoArrayList kmImeetingSummaryForms = new AutoArrayList(
			KmImeetingSummaryForm.class);

	/**
	 * @return 所属会议的表单
	 */
	public AutoArrayList getKmImeetingSummaryForms() {
		return kmImeetingSummaryForms;
	}

	/**
	 * @param kmImeetingSummaryForms
	 *            所属会议的表单
	 */
	public void setKmImeetingSummaryForms(AutoArrayList kmImeetingSummaryForms) {
		this.kmImeetingSummaryForms = kmImeetingSummaryForms;
	}

	/**
	 * 操作历史
	 */
	protected AutoArrayList kmImeetingMainHistoryForms = new AutoArrayList(
			KmImeetingMainHistoryForm.class);

	public AutoArrayList getKmImeetingMainHistoryForms() {
		return kmImeetingMainHistoryForms;
	}

	public void setKmImeetingMainHistoryForms(
			AutoArrayList kmImeetingMainHistoryForms) {
		this.kmImeetingMainHistoryForms = kmImeetingMainHistoryForms;
	}

	@Override
	public String getAuthReaderNoteFlag() {
		return "2";
	}

	private String isCycle;

	public String getIsCycle() {
		return isCycle;
	}

	public void setIsCycle(String isCycle) {
		this.isCycle = isCycle;
	}

	private String fdRecurrenceStr;

	public String getFdRecurrenceStr() {
		return fdRecurrenceStr;
	}

	public void setFdRecurrenceStr(String fdRecurrenceStr) {
		this.fdRecurrenceStr = fdRecurrenceStr;
	}

	private String fdRepeatType;// 重复类型

	private String fdRepeatFrequency;// 重复频率

	private String fdRepeatTime;// 重复时间

	private String fdRepeatUtil;// 结束条件

	private String fdChangeType;// 周期性会议变更时使用

	public String getFdChangeType() {
		return fdChangeType;
	}

	public void setFdChangeType(String fdChangeType) {
		this.fdChangeType = fdChangeType;
	}

	public String getFdRepeatType() {
		return fdRepeatType;
	}

	public void setFdRepeatType(String fdRepeatType) {
		this.fdRepeatType = fdRepeatType;
	}

	public String getFdRepeatFrequency() {
		return fdRepeatFrequency;
	}

	public void setFdRepeatFrequency(String fdRepeatFrequency) {
		this.fdRepeatFrequency = fdRepeatFrequency;
	}

	public String getFdRepeatTime() {
		return fdRepeatTime;
	}

	public void setFdRepeatTime(String fdRepeatTime) {
		this.fdRepeatTime = fdRepeatTime;
	}

	public String getFdRepeatUtil() {
		return fdRepeatUtil;
	}

	public void setFdRepeatUtil(String fdRepeatUtil) {
		this.fdRepeatUtil = fdRepeatUtil;
	}

	/**
	 * 重复周期
	 */
	protected String RECURRENCE_FREQ = ImeetingConstant.RECURRENCE_FREQ_NO;

	/**
	 * 重复频率
	 */
	protected String RECURRENCE_INTERVAL = "1";

	/**
	 * 重复几次后结束
	 */
	protected String RECURRENCE_COUNT = "5";

	/**
	 * 重复到某天结束
	 */
	protected String RECURRENCE_UNTIL = null;

	protected String RECURRENCE_BYDAY = null;

	/**
	 * 重复提醒信息
	 */
	protected String RECURRENCE_SUMMARY = null;

	/**
	 * 重复结束类型
	 */
	protected String RECURRENCE_END_TYPE = "NEVER";

	/**
	 * 重复星期的值，比如每周周一、周三重复，则该值为: MO,WE
	 */
	protected String RECURRENCE_WEEKS = "SU";

	/**
	 * 重复周期为每月时，重复类型：一周的某天，一月的某天
	 */
	protected String RECURRENCE_MONTH_TYPE = "month";

	/**
	 * 重复开始时间
	 */
	protected String RECURRENCE_START = null;

	public String getRECURRENCE_FREQ() {
		return RECURRENCE_FREQ;
	}

	public void setRECURRENCE_FREQ(String recurrence_freq) {
		RECURRENCE_FREQ = recurrence_freq;
	}

	public String getRECURRENCE_INTERVAL() {
		return RECURRENCE_INTERVAL;
	}

	public void setRECURRENCE_INTERVAL(String recurrence_interval) {
		RECURRENCE_INTERVAL = recurrence_interval;
	}

	public String getRECURRENCE_COUNT() {
		return RECURRENCE_COUNT;
	}

	public void setRECURRENCE_COUNT(String recurrence_count) {
		RECURRENCE_COUNT = recurrence_count;
	}

	public String getRECURRENCE_UNTIL() {
		return RECURRENCE_UNTIL;
	}

	public void setRECURRENCE_UNTIL(String recurrence_until) {
		RECURRENCE_UNTIL = recurrence_until;
	}

	public String getRECURRENCE_BYDAY() {
		return RECURRENCE_BYDAY;
	}

	public void setRECURRENCE_BYDAY(String recurrence_byday) {
		RECURRENCE_BYDAY = recurrence_byday;
	}

	public String getRECURRENCE_SUMMARY() {
		return RECURRENCE_SUMMARY;
	}

	public void setRECURRENCE_SUMMARY(String recurrence_summary) {
		RECURRENCE_SUMMARY = recurrence_summary;
	}

	public String getRECURRENCE_END_TYPE() {
		return RECURRENCE_END_TYPE;
	}

	public void setRECURRENCE_END_TYPE(String recurrence_end_type) {
		RECURRENCE_END_TYPE = recurrence_end_type;
	}

	public String getRECURRENCE_WEEKS() {
		return RECURRENCE_WEEKS;
	}

	public void setRECURRENCE_WEEKS(String recurrence_weeks) {
		RECURRENCE_WEEKS = recurrence_weeks;
	}

	public String getRECURRENCE_MONTH_TYPE() {
		return RECURRENCE_MONTH_TYPE;
	}

	public void setRECURRENCE_MONTH_TYPE(String recurrence_month_type) {
		RECURRENCE_MONTH_TYPE = recurrence_month_type;
	}

	public String getRECURRENCE_START() {
		return RECURRENCE_START;
	}

	public void setRECURRENCE_START(String recurrence_start) {
		RECURRENCE_START = recurrence_start;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdHoldDate = null;
		fdEarlyFinishDate = null;
		fdFinishDate = null;
		fdHoldDuration = null;
		fdMeetingAim = null;
		fdMeetingNum = null;
		fdOtherHostPerson = null;
		fdOtherAttendPerson = null;
		fdOtherParticipantPerson = null;
		fdOtherCopyToPerson = null;
		fdOtherAssistPersons = null;
		fdSummaryCompleteTime = null;
		fdIsHurrySummary = null;
		fdHurryDate = null;
		fdAttendNum = null;
		fdArrange = null;
		fdNotifyType = null;
		fdNotifyType = null;
		fdNotifyWay = null;
		syncDataToCalendarTime = null;
		fdSummaryFlag = null;
		cancelMeetingReason = null;
		fdChangeMeetingFlag = null;
		changeMeetingReason = null;
		beforeChangeContent = null;
		fdRemark = null;
		authAttNocopy = null;
		authAttNodownload = null;
		authAttNoprint = null;
		fdPlaceId = null;
		fdPlaceName = null;
		fdOtherPlace = null;
		fdOtherPlaceCoordinate = null;
		docCreatorId = null;
		docCreatorName = null;
		docCreateTime = null;
		fdEmceeId = null;
		fdEmceeName = null;
		emccType = null;
		docDeptId = null;
		docDeptName = null;
		fdSummaryInputPersonId = null;
		fdSummaryInputPersonName = null;
		fdHostId = null;
		fdHostName = null;
		fdControlPersonId = null;
		fdControlPersonName = null;
		fdHostMobileNo = null;
		fdTemplateId = null;
		fdTemplateName = null;
		docPublishTime = null;
		kmImeetingMainFeedbackForms = new AutoArrayList(
				KmImeetingMainFeedbackForm.class);
		kmImeetingDeviceIds = null;
		kmImeetingDeviceNames = null;
		kmImeetingEquipmentIds = null;
		kmImeetingEquipmentNames = null;
		fdAttendPersonIds = null;
		fdAttendPersonNames = null;
		fdBallotPersonIds = null;
		fdBallotPersonNames = null;
		fdParticipantPersonIds = null;
		fdParticipantPersonNames = null;
		fdCopyToPersonIds = null;
		fdCopyToPersonNames = null;
		fdInvitePersonIds = null;
		fdInvitePersonNames = null;
		fdOtherInvitePersons = null;
		fdAssistPersonIds = null;
		fdAssistPersonNames = null;
		kmImeetingAgendaForms = new AutoArrayList(KmImeetingAgendaForm.class);
		kmImeetingSummaryForms = new AutoArrayList(KmImeetingSummaryForm.class);
		kmImeetingMainHistoryForms = new AutoArrayList(
				KmImeetingMainHistoryForm.class);
		sysWfBusinessForm = new SysWfBusinessForm();
		fdVicePlaceIds = null;
		fdVicePlaceNames = null;
		fdOtherVicePlace = null;
		fdOtherVicePlaceCoord = null;
		fdRecurrenceStr = null;
		fdChangeType = null;
		fdOriId = null;
		super.reset(mapping, request);
	}

	@Override
	public Class getModelClass() {
		return KmImeetingMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdPlaceId", new FormConvertor_IDToModel(
					"fdPlace", KmImeetingRes.class));
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("fdEmceeId", new FormConvertor_IDToModel(
					"fdEmcee", SysOrgPerson.class));
			toModelPropertyMap.put("docDeptId", new FormConvertor_IDToModel(
					"docDept", SysOrgElement.class));
			toModelPropertyMap.put("fdSummaryInputPersonId",
					new FormConvertor_IDToModel("fdSummaryInputPerson",
							SysOrgElement.class));
			toModelPropertyMap.put("fdHostId", new FormConvertor_IDToModel(
					"fdHost", SysOrgPerson.class));
			toModelPropertyMap.put("fdControlPersonId",
					new FormConvertor_IDToModel("fdControlPerson", SysOrgPerson.class));
			toModelPropertyMap.put("fdNotifyerId", new FormConvertor_IDToModel(
					"fdNotifyer", SysOrgPerson.class));
			toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel(
					"fdTemplate", KmImeetingTemplate.class));
			toModelPropertyMap.put("kmImeetingMainFeedbackForms",
					new FormConvertor_FormListToModelList(
							"kmImeetingMainFeedbacks", "fdMeeting"));
			toModelPropertyMap.put("kmImeetingDeviceIds",
					new FormConvertor_IDsToModelList("kmImeetingDevices",
							KmImeetingDevice.class));
			toModelPropertyMap.put("kmImeetingEquipmentIds",
					new FormConvertor_IDsToModelList("kmImeetingEquipments",
							KmImeetingEquipment.class));
			toModelPropertyMap.put("fdAttendPersonIds",
					new FormConvertor_IDsToModelList("fdAttendPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("fdBallotPersonIds",
					new FormConvertor_IDsToModelList("fdBallotPersons", SysOrgElement.class));
			toModelPropertyMap.put("fdParticipantPersonIds",
					new FormConvertor_IDsToModelList("fdParticipantPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("fdCopyToPersonIds",
					new FormConvertor_IDsToModelList("fdCopyToPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("fdInvitePersonIds",
					new FormConvertor_IDsToModelList("fdInvitePersons", SysOrgElement.class));
			toModelPropertyMap.put("fdAssistPersonIds",
					new FormConvertor_IDsToModelList("fdAssistPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("fdOtherPersonIds",
					new FormConvertor_IDsToModelList("fdOtherPersons",
							SysOrgElement.class));
			toModelPropertyMap.put("kmImeetingAgendaForms",
					new FormConvertor_FormListToModelList("kmImeetingAgendas",
							"fdMain"));
			toModelPropertyMap.put("kmImeetingSummaryForms",
					new FormConvertor_FormListToModelList("kmImeetingSummarys",
							"fdMeeting"));
			toModelPropertyMap.put("kmImeetingMainHistoryForms",
					new FormConvertor_FormListToModelList(
							"kmImeetingMainHistorys", "fdMeeting"));
			toModelPropertyMap.put("kmImeetingVicePlaceDetailForms",
					new FormConvertor_FormListToModelList(
							"kmImeetingVicePlaceDetails", "fdMeeting"));
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
	 * 发布机制
	 */
	SysNewsPublishMainForm SysNewsPublishMainForm = new SysNewsPublishMainForm();

	@Override
	public SysNewsPublishMainForm getSysNewsPublishMainForm() {
		return SysNewsPublishMainForm;
	}

	/**
	 * 日程机制
	 */
	private SysAgendaMainForm sysAgendaMainForm = new SysAgendaMainForm();

	@Override
	public SysAgendaMainForm getSysAgendaMainForm() {
		return sysAgendaMainForm;
	}

	public void setSysAgendaMainForm(SysAgendaMainForm sysAgendaMainForm) {
		this.sysAgendaMainForm = sysAgendaMainForm;
	}

	private SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm = new SysNotifyRemindMainContextForm();

	@Override
	public SysNotifyRemindMainContextForm getSysNotifyRemindMainContextForm() {
		return sysNotifyRemindMainContextForm;
	}

	public void setSysNotifyRemindMainContextForm(
			SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm) {
		this.sysNotifyRemindMainContextForm = sysNotifyRemindMainContextForm;
	}

	/**
	 * 阅读机制
	 */
	private String docReadCount = null;

	public String getDocReadCount() {
		return docReadCount;
	}

	public void setDocReadCount(String docReadCount) {
		this.docReadCount = docReadCount;
	}

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
	
	@Override
	public Boolean getAuthChangeEditorFlag() {
		if (authChangeEditorFlag == null) {
			authChangeEditorFlag = false;
		}
		return super.getAuthChangeEditorFlag();
	}
	
	private String fdWeiXinAttendPerson = null;
	private String fdWeiXinAttendPersonJson = "";
	private JSONObject json = null;

	public String getFdWeiXinAttendPersonJson() {
		if (StringUtil.isNotNull(fdWeiXinAttendPerson)) {
			String[] executors = fdWeiXinAttendPerson.split(";");
			for (int i = 0; i < executors.length; i++) {
				if (StringUtil.isNotNull(executors[i])) {
					json = JSONObject.fromObject(executors[i]);
					fdWeiXinAttendPersonJson += ",&nbsp;<img style=\"vertical-align: middle;\" height=\"20px\" src='"
							+ json.getString("h") + "'> " + json.getString("n");
				}
			}
		}
		if (fdWeiXinAttendPersonJson.startsWith(",")) {
            fdWeiXinAttendPersonJson = fdWeiXinAttendPersonJson.replaceFirst(
                    ",", "");
        }
		return fdWeiXinAttendPersonJson;
	}

	public String getFdWeiXinAttendPerson() {
		return fdWeiXinAttendPerson;
	}

	public void setFdWeiXinAttendPerson(String fdWeiXinAttendPerson) {
		this.fdWeiXinAttendPerson = fdWeiXinAttendPerson;
	}

	// #57321 需求调整：分会场明细废弃，改为分会场列表+单个外部分会场字段
	@Deprecated
	private AutoArrayList kmImeetingVicePlaceDetailForms = new AutoArrayList(
			KmImeetingVicePlaceDetailForm.class);

	public AutoArrayList getKmImeetingVicePlaceDetailForms() {
		return kmImeetingVicePlaceDetailForms;
	}

	public void setKmImeetingVicePlaceDetailForms(
			AutoArrayList kmImeetingVicePlaceDetailForms) {
		this.kmImeetingVicePlaceDetailForms = kmImeetingVicePlaceDetailForms;
	}

	/**
	 * 分会场的列表
	 */
	protected String fdVicePlaceIds = null;

	protected String fdVicePlaceNames = null;

	protected String fdVicePlaceUserTimes = null;

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

	public String getFdVicePlaceUserTimes() {
		return fdVicePlaceUserTimes;
	}

	public void setFdVicePlaceUserTimes(String fdVicePlaceUserTimes) {
		this.fdVicePlaceUserTimes = fdVicePlaceUserTimes;
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

	private String fdSeatPlanId; // 坐席安排ID

	/**
	 * 坐席安排ID
	 */
	public String getFdSeatPlanId() {
		return fdSeatPlanId;
	}

	/**
	 * 坐席安排ID
	 */
	public void setFdSeatPlanId(String fdSeatPlanId) {
		this.fdSeatPlanId = fdSeatPlanId;
	}

	private String fdIsSeatPlan;// 是否安排坐席

	public String getFdIsSeatPlan() {
		return fdIsSeatPlan;
	}

	public void setFdIsSeatPlan(String fdIsSeatPlan) {
		this.fdIsSeatPlan = fdIsSeatPlan;
	}

	private String fdVoteEnable;// 是否开启投票

	public String getFdVoteEnable() {
		return fdVoteEnable;
	}

	public void setFdVoteEnable(String fdVoteEnable) {
		this.fdVoteEnable = fdVoteEnable;
	}

	private String fdBallotEnable;// 是否开启表决

	public String getFdBallotEnable() {
		return fdBallotEnable;
	}

	public void setFdBallotEnable(String fdBallotEnable) {
		this.fdBallotEnable = fdBallotEnable;
	}

	/**
	 * 原始会议ID（周期性会议在取消后续会议时新创建一个会议，此时查看该会议时，引用的流程会报错，原因是找不到流程实例，这里增加原始会议，用于在查看取消的会议时，显示原会议的流程）
	 */
	private String fdOriId;

	public String getFdOriId() {
		return fdOriId;
	}

	public void setFdOriId(String fdOriId) {
		this.fdOriId = fdOriId;
	}

}
