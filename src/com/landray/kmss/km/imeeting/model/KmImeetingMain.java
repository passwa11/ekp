package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingMainForm;
import com.landray.kmss.sys.agenda.interfaces.ISysAgendaMainModel;
import com.landray.kmss.sys.agenda.model.SysAgendaMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishMainModel;
import com.landray.kmss.sys.news.model.SysNewsPublishMain;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindMainContextModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogAutoSaveModel;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.unit.model.KmImissiveUnit;
import com.landray.kmss.sys.vote.model.IVoteMainModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainModel;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;

/**
 * 会议安排
 */
public class KmImeetingMain extends ExtendAuthModel implements ISysWfMainModel,
		ISysNewsPublishMainModel, ISysAgendaMainModel,
		ISysReadLogAutoSaveModel, ISysCirculationModel, IAttachment,
		InterceptFieldEnabled, IVoteMainModel {

	private static final long serialVersionUID = 3178330312639450139L;

	/**
	 * 是否视频会议
	 */
	private Boolean fdIsVideo;

	/**
	 * 是否需要会议地点
	 */
	private Boolean fdNeedPlace;

	/**
	 * 是否需要回执
	 */
	private Boolean fdNeedFeedback;

	public Boolean getFdNeedFeedback() {
		return fdNeedFeedback;
	}

	public void setFdNeedFeedback(Boolean fdNeedFeedback) {
		this.fdNeedFeedback = fdNeedFeedback;
	}

	public Boolean getFdIsVideo() {
		if (fdIsVideo == null) {
			return false;
		}
		return fdIsVideo;
	}

	public void setFdIsVideo(Boolean fdIsVideo) {
		this.fdIsVideo = fdIsVideo;
	}

	public Boolean getFdNeedPlace() {
		return fdNeedPlace;
	}

	public void setFdNeedPlace(Boolean fdNeedPlace) {
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
	 * 标题
	 */
	protected String docSubject;

	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

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
	 * 是否开启了议题
	 */
	private Boolean fdIsTopic;

	public Boolean getFdIsTopic() {
		return fdIsTopic;
	}

	public void setFdIsTopic(Boolean fdIsTopic) {
		this.fdIsTopic = fdIsTopic;
	}

	/**
	 * 是否开启了会议，铂恩用
	 */
	private Boolean isBegin;

	public Boolean getIsBegin() {
		return isBegin;
	}

	public void setIsBegin(Boolean isBegin) {
		this.isBegin = isBegin;
	}

	/**
	 * 是否面对面会议
	 */
	private Boolean isFace;
	

	public Boolean getIsFace() {
		return isFace;
	}

	public void setIsFace(Boolean isFace) {
		this.isFace = isFace;
	}

	/**
	 * 是否云会议
	 */
	private Boolean isCloud;

	public Boolean getIsCloud() {
		if (isCloud == null) {
			isCloud = false;
		}
		return isCloud;
	}

	public void setIsCloud(Boolean isCloud) {
		this.isCloud = isCloud;
	}

	/**
	 * 召开日期
	 */
	protected Date fdHoldDate;

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
	 * 提前结束日期
	 */
	protected Date fdEarlyFinishDate;

	/**
	 * @return 提前结束日期
	 */
	public Date getFdEarlyFinishDate() {
		return fdEarlyFinishDate;
	}

	/**
	 * @param fdEarlyFinishDate
	 *            提前结束日期
	 */
	public void setFdEarlyFinishDate(Date fdEarlyFinishDate) {
		this.fdEarlyFinishDate = fdEarlyFinishDate;
	}

	/**
	 * 结束日期
	 */
	protected Date fdFinishDate;

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
	protected Double fdHoldDuration;

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
	 * 会议目的
	 */
	protected String fdMeetingAim;

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
	protected String fdMeetingNum;

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
	protected String fdOtherHostPerson;

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
	protected String fdOtherAttendPerson;

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
	protected String fdOtherParticipantPerson;

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
	protected String fdOtherCopyToPerson;

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

	/**
	 * 纪要完成时间
	 */
	protected Date fdSummaryCompleteTime;

	/**
	 * @return 纪要完成时间
	 */
	public Date getFdSummaryCompleteTime() {
		return fdSummaryCompleteTime;
	}

	/**
	 * @param fdSummaryCompleteTime
	 *            纪要完成时间
	 */
	public void setFdSummaryCompleteTime(Date fdSummaryCompleteTime) {
		this.fdSummaryCompleteTime = fdSummaryCompleteTime;
	}

	/**
	 * 是否催办纪要
	 */
	protected Boolean fdIsHurrySummary;

	/**
	 * @return 是否催办纪要
	 */
	public Boolean getFdIsHurrySummary() {
		return fdIsHurrySummary;
	}

	/**
	 * @param fdIsHurrySummary
	 *            是否催办纪要
	 */
	public void setFdIsHurrySummary(Boolean fdIsHurrySummary) {
		this.fdIsHurrySummary = fdIsHurrySummary;
	}

	/**
	 * 提前几天催办
	 */
	protected Long fdHurryDate;

	/**
	 * @return 提前几天催办
	 */
	public Long getFdHurryDate() {
		return fdHurryDate;
	}

	/**
	 * @param fdHurryDate
	 *            提前几天催办
	 */
	public void setFdHurryDate(Long fdHurryDate) {
		this.fdHurryDate = fdHurryDate;
	}

	/**
	 * 预计与会人员
	 */
	protected Integer fdAttendNum;

	/**
	 * @return 预计与会人员
	 */
	public Integer getFdAttendNum() {
		return fdAttendNum;
	}

	/**
	 * @param fdAttendNum
	 *            预计与会人员
	 */
	public void setFdAttendNum(Integer fdAttendNum) {
		this.fdAttendNum = fdAttendNum;
	}

	/**
	 * 会场布置要求
	 */
	protected String fdArrange;

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
	protected Boolean isNotify;

	public Boolean getIsNotify() {
		return isNotify;
	}

	public void setIsNotify(Boolean isNotify) {
		this.isNotify = isNotify;
	}

	/**
	 * 会议通知选项
	 */
	protected String fdNotifyType;

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
	protected String syncDataToCalendarTime;

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
	protected Boolean fdSummaryFlag;

	/**
	 * @return 会议是否已纪要
	 */
	public Boolean getFdSummaryFlag() {
		return fdSummaryFlag;
	}

	/**
	 * @param fdSummaryFlag
	 *            会议是否已纪要
	 */
	public void setFdSummaryFlag(Boolean fdSummaryFlag) {
		this.fdSummaryFlag = fdSummaryFlag;
	}

	/**
	 * 会议取消原因
	 */
	protected String cancelMeetingReason;

	public String getCancelMeetingReason() {
		return (String) readLazyField("cancelMeetingReason",
				cancelMeetingReason);
	}

	public void setCancelMeetingReason(String cancelMeetingReason) {
		this.cancelMeetingReason = (String) writeLazyField(
				"cancelMeetingReason", this.cancelMeetingReason,
				cancelMeetingReason);
	}

	/**
	 * 会议是否变更
	 */
	protected Boolean fdChangeMeetingFlag;

	/**
	 * @return 会议是否变更
	 */
	public Boolean getFdChangeMeetingFlag() {
		return fdChangeMeetingFlag;
	}

	/**
	 * @param fdChangeMeeting
	 *            会议是否变更
	 */
	public void setFdChangeMeetingFlag(Boolean fdChangeMeetingFlag) {
		this.fdChangeMeetingFlag = fdChangeMeetingFlag;
	}

	/**
	 * 会议变更原因
	 */
	protected String changeMeetingReason;

	public String getChangeMeetingReason() {
		return (String) readLazyField("changeMeetingReason",
				changeMeetingReason);
	}

	public void setChangeMeetingReason(String changeMeetingReason) {
		this.changeMeetingReason = (String) writeLazyField(
				"changeMeetingReason", this.changeMeetingReason,
				changeMeetingReason);
	}

	/**
	 * 记录会议变更前的内容（JSON）
	 */
	protected String beforeChangeContent;

	public String getBeforeChangeContent() {
		return (String) readLazyField("beforeChangeContent",
				beforeChangeContent);
	}

	public void setBeforeChangeContent(String beforeChangeContent) {
		this.beforeChangeContent = (String) writeLazyField(
				"beforeChangeContent", this.beforeChangeContent,
				beforeChangeContent);
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
	protected Boolean fdCleanTodoFlag;

	public Boolean getFdCleanTodoFlag() {
		return fdCleanTodoFlag;
	}

	public void setFdCleanTodoFlag(Boolean fdCleanTodoFlag) {
		this.fdCleanTodoFlag = fdCleanTodoFlag;
	}

	/**
	 * @return 附件不可拷贝标记
	 */
	@Override
    public Boolean getAuthAttNocopy() {
		return authAttNocopy;
	}

	/**
	 * @return 附件不可下载标记
	 */
	@Override
    public Boolean getAuthAttNodownload() {
		return authAttNodownload;
	}

	/**
	 * @return 附件不可打印标记
	 */
	@Override
    public Boolean getAuthAttNoprint() {
		return authAttNoprint;
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

	/**
	 * 会议组织人
	 */
	protected SysOrgElement fdEmcee = null;

	public SysOrgElement getFdEmcee() {
		return fdEmcee;
	}

	public void setFdEmcee(SysOrgElement fdEmcee) {
		this.fdEmcee = fdEmcee;
	}

	/**
	 * 所属部门
	 */
	protected SysOrgElement docDept;

	/**
	 * @return 所属部门
	 */
	@Override
    public SysOrgElement getDocDept() {
		return docDept;
	}

	/**
	 * @param docDept
	 *            所属部门
	 */
	public void setDocDept(SysOrgElement docDept) {
		this.docDept = docDept;
	}

	/**
	 * 纪要录入人
	 */
	protected SysOrgElement fdSummaryInputPerson;

	public SysOrgElement getFdSummaryInputPerson() {
		return fdSummaryInputPerson;
	}

	public void setFdSummaryInputPerson(SysOrgElement fdSummaryInputPerson) {
		this.fdSummaryInputPerson = fdSummaryInputPerson;
	}

	/**
	 * 主持人
	 */
	protected SysOrgPerson fdHost;

	/**
	 * 会控人员
	 */
	protected SysOrgPerson fdControlPerson;

	/**
	 * @return 主持人
	 */
	public SysOrgPerson getFdHost() {
		return fdHost;
	}

	/**
	 * @param fdHost
	 *            主持人
	 */
	public void setFdHost(SysOrgPerson fdHost) {
		this.fdHost = fdHost;
	}

	public SysOrgPerson getFdControlPerson() {
		return fdControlPerson;
	}

	public void setFdControlPerson(SysOrgPerson fdControlPerson) {
		this.fdControlPerson = fdControlPerson;
	}

	/**
	 * 发送通知者
	 */
	protected SysOrgPerson fdNotifyer;

	public SysOrgPerson getFdNotifyer() {
		return fdNotifyer;
	}

	public void setFdNotifyer(SysOrgPerson fdNotifyer) {
		this.fdNotifyer = fdNotifyer;
	}

	/**
	 * 会议模板
	 */
	protected KmImeetingTemplate fdTemplate;

	/**
	 * @return 会议模板
	 */
	public KmImeetingTemplate getFdTemplate() {
		return fdTemplate;
	}

	/**
	 * @param fdTemplate
	 *            会议模板
	 */
	public void setFdTemplate(KmImeetingTemplate fdTemplate) {
		this.fdTemplate = fdTemplate;
	}

	/**
	 * 会议回执
	 */
	protected List<KmImeetingMainFeedback> kmImeetingMainFeedbacks;

	/**
	 * @return 会议回执
	 */
	public List<KmImeetingMainFeedback> getKmImeetingMainFeedbacks() {
		return kmImeetingMainFeedbacks;
	}

	/**
	 * @param kmImeetingMainFeedbacks
	 *            会议回执
	 */
	public void setKmImeetingMainFeedbacks(
			List<KmImeetingMainFeedback> kmImeetingMainFeedbacks) {
		this.kmImeetingMainFeedbacks = kmImeetingMainFeedbacks;
	}

	/**
	 * 会议辅助服务
	 */
	protected List<KmImeetingDevice> kmImeetingDevices;

	/**
	 * @return 会议辅助服务
	 */
	public List<KmImeetingDevice> getKmImeetingDevices() {
		return kmImeetingDevices;
	}

	/**
	 * @param kmImeetingDevices
	 *            会议辅助服务
	 */
	public void setKmImeetingDevices(List<KmImeetingDevice> kmImeetingDevices) {
		this.kmImeetingDevices = kmImeetingDevices;
	}

	/**
	 * @param kmImeetingEquipments
	 *            会议辅助设备
	 */
	protected List<KmImeetingEquipment> kmImeetingEquipments;

	public List<KmImeetingEquipment> getKmImeetingEquipments() {
		return kmImeetingEquipments;
	}

	public void setKmImeetingEquipments(
			List<KmImeetingEquipment> kmImeetingEquipments) {
		this.kmImeetingEquipments = kmImeetingEquipments;
	}

	@Override
    public Boolean getAuthReaderFlag() {
		return new Boolean(false);
	}

	/**
	 * 与会人员
	 */
	protected List<SysOrgElement> fdAttendPersons;
	/**
	 * 监票人员
	 */

	protected List<SysOrgElement> fdBallotPersons;

	/**
	 * @return 与会人员
	 */
	public List<SysOrgElement> getFdAttendPersons() {
		return fdAttendPersons;
	}

	/**
	 * @param fdAttendPersons
	 *            与会人员
	 */
	public void setFdAttendPersons(List<SysOrgElement> fdAttendPersons) {
		this.fdAttendPersons = fdAttendPersons;
	}

	public List<SysOrgElement> getFdBallotPersons() {
		return fdBallotPersons;
	}

	public void setFdBallotPersons(List<SysOrgElement> fdBallotPersons) {
		this.fdBallotPersons = fdBallotPersons;
	}

	/**
	 * 会议列席人员
	 */
	protected List<SysOrgElement> fdParticipantPersons;

	/**
	 * @return 会议列席人员
	 */
	public List<SysOrgElement> getFdParticipantPersons() {
		return fdParticipantPersons;
	}

	/**
	 * @param fdParticipantPersons
	 *            会议列席人员
	 */
	public void setFdParticipantPersons(List<SysOrgElement> fdParticipantPersons) {
		this.fdParticipantPersons = fdParticipantPersons;
	}

	/**
	 * 抄送人员
	 */
	protected List<SysOrgElement> fdCopyToPersons;

	/**
	 * @return 抄送人员
	 */
	public List<SysOrgElement> getFdCopyToPersons() {
		return fdCopyToPersons;
	}

	/**
	 * @param fdCopyToPersons
	 *            抄送人员
	 */
	public void setFdCopyToPersons(List<SysOrgElement> fdCopyToPersons) {
		this.fdCopyToPersons = fdCopyToPersons;
	}

	/**
	 * 邀请人员
	 */
	protected List<SysOrgElement> fdInvitePersons;
	/**
	 * 外部邀请人员
	 */
	protected String fdOtherInvitePersons;

	public List<SysOrgElement> getFdInvitePersons() {
		return fdInvitePersons;
	}

	public void setFdInvitePersons(List<SysOrgElement> fdInvitePersons) {
		this.fdInvitePersons = fdInvitePersons;
	}

	public String getFdOtherInvitePersons() {
		return fdOtherInvitePersons;
	}

	public void setFdOtherInvitePersons(String fdOtherInvitePersons) {
		this.fdOtherInvitePersons = fdOtherInvitePersons;
	}

	/**
	 * 会议其他人(代理人、邀请参加人)
	 */
	private List<SysOrgElement> fdOtherPersons;

	public List<SysOrgElement> getFdOtherPersons() {
		return fdOtherPersons;
	}

	public void setFdOtherPersons(List<SysOrgElement> fdOtherPersons) {
		this.fdOtherPersons = fdOtherPersons;
	}

	/**
	 * 会议协助人
	 */
	protected List<SysOrgElement> fdAssistPersons;

	/**
	 * @return 会议协助人
	 */
	public List<SysOrgElement> getFdAssistPersons() {
		return fdAssistPersons;
	}

	/**
	 * @param fdAssistPersons
	 *            会议协助人
	 */
	public void setFdAssistPersons(List<SysOrgElement> fdAssistPersons) {
		this.fdAssistPersons = fdAssistPersons;
	}

	/**
	 * 其他会议协助人
	 */
	private String fdOtherAssistPersons;

	public String getFdOtherAssistPersons() {
		return fdOtherAssistPersons;
	}

	public void setFdOtherAssistPersons(String fdOtherAssistPersons) {
		this.fdOtherAssistPersons = fdOtherAssistPersons;
	}

	/**
	 * 会议议程
	 */
	protected List<KmImeetingAgenda> kmImeetingAgendas;

	/**
	 * @return 会议议程
	 */
	public List<KmImeetingAgenda> getKmImeetingAgendas() {
		return kmImeetingAgendas;
	}

	/**
	 * @param kmImeetingAgendas
	 *            会议议程
	 */
	public void setKmImeetingAgendas(List<KmImeetingAgenda> kmImeetingAgendas) {
		this.kmImeetingAgendas = kmImeetingAgendas;
	}

	/**
	 * 会议纪要
	 */
	protected List<KmImeetingSummary> kmImeetingSummarys;

	/**
	 * @return 会议纪要
	 */
	public List<KmImeetingSummary> getKmImeetingSummarys() {
		return kmImeetingSummarys;
	}

	/**
	 * @param kmImeetingSummarys
	 *            会议纪要
	 */
	public void setKmImeetingSummarys(List<KmImeetingSummary> kmImeetingSummarys) {
		this.kmImeetingSummarys = kmImeetingSummarys;
	}

	/**
	 * 发布时间
	 */
	protected java.util.Date docPublishTime;

	public java.util.Date getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(java.util.Date docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	/**
	 * 回执结束时间
	 */
	protected Date fdFeedBackDeadline;

	public Date getFdFeedBackDeadline() {
		return fdFeedBackDeadline;
	}

	public void setFdFeedBackDeadline(Date fdFeedBackDeadline) {
		this.fdFeedBackDeadline = fdFeedBackDeadline;
	}

	// 组织人承接标识
	protected String emccType;

	public String getEmccType() {
		return emccType;
	}

	public void setEmccType(String emccType) {
		this.emccType = emccType;
	}

	/**
	 * 操作历史
	 */
	List<KmImeetingMainHistory> kmImeetingMainHistorys = new ArrayList<KmImeetingMainHistory>();

	public List<KmImeetingMainHistory> getKmImeetingMainHistorys() {
		return kmImeetingMainHistorys;
	}

	public void setKmImeetingMainHistorys(
			List<KmImeetingMainHistory> kmImeetingMainHistorys) {
		this.kmImeetingMainHistorys = kmImeetingMainHistorys;
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

	private String fdChangeType;// 周期性会议变更时使用

	public String getFdChangeType() {
		return fdChangeType;
	}

	public void setFdChangeType(String fdChangeType) {
		this.fdChangeType = fdChangeType;
	}

	@Override
    public Class getFormClass() {
		return KmImeetingMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdPlace.fdId", "fdPlaceId");
			toFormPropertyMap.put("fdPlace.fdName", "fdPlaceName");
			toFormPropertyMap.put("fdPlace.fdUserTime", "fdUserTime");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdNotifyer.fdId", "fdNotifyerId");
			toFormPropertyMap.put("fdNotifyer.fdName", "fdNotifyerName");
			toFormPropertyMap.put("fdEmcee.fdId", "fdEmceeId");
			toFormPropertyMap.put("fdEmcee.fdName", "fdEmceeName");
			toFormPropertyMap.put("docDept.fdId", "docDeptId");
			toFormPropertyMap.put("docDept.deptLevelNames", "docDeptName");
			toFormPropertyMap.put("fdSummaryInputPerson.fdId",
					"fdSummaryInputPersonId");
			toFormPropertyMap.put("fdSummaryInputPerson.fdName",
					"fdSummaryInputPersonName");
			toFormPropertyMap.put("fdHost.fdId", "fdHostId");
			toFormPropertyMap.put("fdHost.fdName", "fdHostName");
			toFormPropertyMap.put("fdControlPerson.fdId", "fdControlPersonId");
			toFormPropertyMap.put("fdControlPerson.fdName", "fdControlPersonName");

			toFormPropertyMap.put("fdHost.fdMobileNo", "fdHostMobileNo");
			toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
			toFormPropertyMap.put("fdTemplate.fdName", "fdTemplateName");
			toFormPropertyMap.put("kmImeetingDevices",
					new ModelConvertor_ModelListToString(
							"kmImeetingDeviceIds:kmImeetingDeviceNames",
							"fdId:fdName"));
			toFormPropertyMap.put("kmImeetingEquipments",
					new ModelConvertor_ModelListToString(
							"kmImeetingEquipmentIds:kmImeetingEquipmentNames",
							"fdId:fdName"));
			toFormPropertyMap.put("fdAttendPersons",
					new ModelConvertor_ModelListToString(
							"fdAttendPersonIds:fdAttendPersonNames",
							"fdId:deptLevelNames"));
			toFormPropertyMap.put("fdBallotPersons", new ModelConvertor_ModelListToString(
					"fdBallotPersonIds:fdBallotPersonNames", "fdId:deptLevelNames"));

			toFormPropertyMap.put("fdParticipantPersons",
					new ModelConvertor_ModelListToString(
							"fdParticipantPersonIds:fdParticipantPersonNames",
							"fdId:deptLevelNames"));
			toFormPropertyMap.put("fdOtherPersons",
					new ModelConvertor_ModelListToString(
							"fdOtherPersonIds:fdOtherPersonNames",
							"fdId:fdName"));
			toFormPropertyMap.put("fdCopyToPersons",
					new ModelConvertor_ModelListToString(
							"fdCopyToPersonIds:fdCopyToPersonNames",
							"fdId:deptLevelNames"));
			toFormPropertyMap.put("fdInvitePersons", new ModelConvertor_ModelListToString(
					"fdInvitePersonIds:fdInvitePersonNames", "fdId:deptLevelNames"));
			toFormPropertyMap.put("fdAssistPersons",
					new ModelConvertor_ModelListToString(
							"fdAssistPersonIds:fdAssistPersonNames",
							"fdId:deptLevelNames"));
			toFormPropertyMap.put("kmImeetingAgendas",
					new ModelConvertor_ModelListToFormList(
							"kmImeetingAgendaForms"));
			toFormPropertyMap.put("kmImeetingSummarys",
					new ModelConvertor_ModelListToFormList(
							"kmImeetingSummaryForms"));
			toFormPropertyMap.put("kmImeetingMainHistorys",
					new ModelConvertor_ModelListToFormList(
							"kmImeetingMainHistoryForms"));
			toFormPropertyMap.put("kmImeetingVicePlaceDetails",
					new ModelConvertor_ModelListToFormList(
							"kmImeetingVicePlaceDetailForms"));
			toFormPropertyMap.put("fdVicePlaces",
					new ModelConvertor_ModelListToString(
							"fdVicePlaceIds:fdVicePlaceNames:fdVicePlaceUserTimes",
							"fdId:fdName:fdUserTime"));
			toFormPropertyMap.addNoConvertProperty("kmImeetingVotes");
			toFormPropertyMap.put("kmImeetingMainFeedbacks",
					new ModelConvertor_ModelListToFormList(
							"kmImeetingMainFeedbackForms"));
		}
		return toFormPropertyMap;
	}

	@Override
	public void recalculateFields() {
		super.recalculateFields();
		String tempStatus = getDocStatus();
		if (StringUtil.isNotNull(tempStatus) && tempStatus.charAt(0) >= '3') {
			List<SysOrgElement> tmpList = new ArrayList<SysOrgElement>();
			// 会议组织人
			if (this.getFdEmcee() != null) {
				tmpList.add(this.getFdEmcee());
			}
			// #9178 发送会议通知后这些人才看得到会议
			// if (this.getIsNotify() != null && this.getIsNotify()) {
				// 主持人
			if (this.getFdHost() != null) {
				tmpList.add(this.getFdHost());
			}
			// 与会人员
			if (this.getFdAttendPersons() != null && !this.getFdAttendPersons().isEmpty()) {
				tmpList.addAll(this.getFdAttendPersons());
			}
			// 列席人员
			if (this.getFdParticipantPersons() != null && !this.getFdParticipantPersons().isEmpty()) {
				tmpList.addAll(this.getFdParticipantPersons());
			}
			// 抄送人员
			if (this.getFdCopyToPersons() != null && !this.getFdCopyToPersons().isEmpty()) {
				tmpList.addAll(this.getFdCopyToPersons());
			}
			// 邀请人员
			if (this.getFdInvitePersons() != null && !this.getFdInvitePersons().isEmpty()) {
				tmpList.addAll(this.getFdInvitePersons());
			}
			// 会议纪要人员
			if (this.getFdSummaryInputPerson() != null) {
				tmpList.add(this.getFdSummaryInputPerson());
			}
			// 会议协助人员
			if (this.getFdAssistPersons() != null && !this.getFdAssistPersons().isEmpty()) {
				tmpList.addAll(this.getFdAssistPersons());
			}
			// 会议室保管员
			if (this.getFdPlace() != null && this.getFdPlace().getDocKeeper() != null) {
				tmpList.add(this.getFdPlace().getDocKeeper());
			}

			// 会控人员
			if (this.getFdControlPerson() != null) {
				tmpList.add(this.getFdControlPerson());
			}

			// 监票人员
			if (this.getFdBallotPersons() != null
					&& !this.getFdBallotPersons().isEmpty()) {
				tmpList.addAll(this.getFdBallotPersons());
			}

			for (KmImeetingAgenda kmImeetingAgenda : this.getKmImeetingAgendas()) {
				//材料责任人
				if (kmImeetingAgenda.getDocRespons() != null) {
					tmpList.add(kmImeetingAgenda.getDocRespons());
				}
				//汇报人
				if (kmImeetingAgenda.getDocReporter() != null) {
					tmpList.add(kmImeetingAgenda.getDocReporter());
				}
				// 建议列席单位的会议联络员
				if (kmImeetingAgenda.getFdAttendUnit() != null && !kmImeetingAgenda.getFdAttendUnit().isEmpty()) {
					List fdAttendUnit = kmImeetingAgenda.getFdAttendUnit();
					for (int i = 0; i < fdAttendUnit.size(); i++) {
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdAttendUnit.get(i);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							tmpList.addAll(kmImissiveUnit.getFdMeetingLiaison());
						}
					}
				}
				// 建议旁听单位的会议联络员
				if (kmImeetingAgenda.getFdListenUnit() != null && !kmImeetingAgenda.getFdListenUnit().isEmpty()) {
					List fdListenUnit = kmImeetingAgenda.getFdListenUnit();
					for (int i = 0; i < fdListenUnit.size(); i++) {
						KmImissiveUnit kmImissiveUnit = (KmImissiveUnit) fdListenUnit.get(i);
						if (kmImissiveUnit.getFdMeetingLiaison() != null) {
							tmpList.addAll(kmImissiveUnit.getFdMeetingLiaison());
						}
					}
				}
			}
			// }
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		}
	}

	/**
	 * 流程域模型信息
	 */
	private SysWfBusinessModel sysWfBusinessModel = new SysWfBusinessModel();

	@Override
	public SysWfBusinessModel getSysWfBusinessModel() {
		return sysWfBusinessModel;
	}

	public void setSysWfBusinessModel(SysWfBusinessModel sysWfBusinessModel) {
		this.sysWfBusinessModel = sysWfBusinessModel;
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

	/**
	 * 发布机制
	 */
	private SysNewsPublishMain sysNewsPublishMain = null;

	@Override
    public SysNewsPublishMain getSysNewsPublishMain() {
		return sysNewsPublishMain;
	}

	@Override
    public void setSysNewsPublishMain(SysNewsPublishMain sysNewsPublishMain) {
		this.sysNewsPublishMain = sysNewsPublishMain;
	}
	
	// **********会议是否结束 开始* **********
	
    // 1:结束 0：未结束
	private String fdWorkStatus="0" ;

	public String getFdWorkStatus() {
		// 若是会议管理，则会议状态为发布且已召开，则该工作项可设置为完成状态
		// 得到当前时间的LongValue
//		if(hbmHoldDate!=null && hbmHoldTime!=null){
//			long currentTimeLongValue = System.currentTimeMillis();
//			if (hbmHoldDate + hbmHoldTime <= currentTimeLongValue) {
//				fdWorkStatus = "1";
//			}	
//		}
		//若是会议管理，则会议状态为发布，则该工作项可设置为完成状态
		if (SysDocConstant.DOC_STATUS_PUBLISH.equals(docStatus)) {
			fdWorkStatus = "1";
		}
		return fdWorkStatus;
	}

	public void setFdWorkStatus(String fdWorkStatus) {
		this.fdWorkStatus = fdWorkStatus;
	}
	// **********会议是否结束 结束* **********

	/**
	 * 日程机制
	 */
	private SysAgendaMain sysAgendaMain = null;

	@Override
    public SysAgendaMain getSysAgendaMain() {
		return sysAgendaMain;
	}

	@Override
    public void setSysAgendaMain(SysAgendaMain sysAgendaMain) {
		this.sysAgendaMain = sysAgendaMain;
	}

	private SysNotifyRemindMainContextModel sysNotifyRemindMainContextModel = null;

	@Override
    public SysNotifyRemindMainContextModel getSysNotifyRemindMainContextModel() {
		return sysNotifyRemindMainContextModel;
	}

	@Override
    public void setSysNotifyRemindMainContextModel(
			SysNotifyRemindMainContextModel sysNotifyRemindMainContextModel) {
		this.sysNotifyRemindMainContextModel = sysNotifyRemindMainContextModel;
	}

	/**
	 * 阅读机制
	 */
	private Long docReadCount;

	@Override
	public Long getDocReadCount() {
		return docReadCount;
	}

	@Override
	public void setDocReadCount(Long docReadCount) {
		this.docReadCount = docReadCount;
	}

	private String readLogSSeparate = null;

	/**
	 * 获取阅读分表字段
	 * 
	 * @return readLogSSeparate
	 */
	public String getReadLogSeparate() {
		return readLogSSeparate;
	}

	/**
	 * 设置阅读分表字段
	 */
	public void setReadLogSSeparate(String readLogSSeparate) {
		this.readLogSSeparate = readLogSSeparate;
	}

	/**
	 * 传阅
	 */
	@Override
	public String getCirculationSeparate() {
		return null;
	}

	@Override
	public void setCirculationSeparate(String circulationSeparate) {

	}

	@Override
	public String getDocSubject() {
		return this.fdName;
	}

	private String fdWeiXinAttendPerson = null;

	public String getFdWeiXinAttendPerson() {
		return (String) readLazyField("fdWeiXinAttendPerson",
				fdWeiXinAttendPerson);
	}

	public void setFdWeiXinAttendPerson(String fdWeiXinAttendPerson) {
		this.fdWeiXinAttendPerson = (String) writeLazyField(
				"fdWeiXinAttendPerson", this.fdWeiXinAttendPerson,
				fdWeiXinAttendPerson);
	}

	/**
	 * 投票机制相关
	 */
	@Override
	public List<SysOrgElement> getFdVoters() {
		List<SysOrgElement> result = new ArrayList<SysOrgElement>();
		if (getFdAttendPersons() != null && getFdAttendPersons().size() > 0) {
			result.addAll(getFdAttendPersons());
		}
		if (getFdParticipantPersons() != null
				&& getFdParticipantPersons().size() > 0) {
			result.addAll(getFdParticipantPersons());
		}
		return result;
	}

	@Override
	public List<SysOrgElement> getfdViewer() {
		List<SysOrgElement> result = new ArrayList<SysOrgElement>();
		if (getFdEmcee() != null) {
			result.add(getFdEmcee());
		}
		if (getDocCreator() != null) {
			result.add(this.getDocCreator());
		}
		return result;
	}

	@Override
	public String getFdVoteCategoryId() {
		if (this.getFdTemplate() != null && StringUtil
				.isNotNull(this.getFdTemplate().getFdVoteCategoryId())) {
			return this.getFdTemplate().getFdVoteCategoryId();
		}
		return null;
	}

	// #57321 需求调整：分会场明细废弃，改为分会场列表+单个外部分会场字段
	@Deprecated
	protected List<KmImeetingVicePlaceDetail> kmImeetingVicePlaceDetails;

	@Deprecated
	public List<KmImeetingVicePlaceDetail> getKmImeetingVicePlaceDetails() {
		return kmImeetingVicePlaceDetails;
	}

	@Deprecated
	public void setKmImeetingVicePlaceDetails(
			List<KmImeetingVicePlaceDetail> kmImeetingVicePlaceDetails) {
		this.kmImeetingVicePlaceDetails = kmImeetingVicePlaceDetails;
	}

	// 分外场列表
	protected List<KmImeetingRes> fdVicePlaces;

	public List<KmImeetingRes> getFdVicePlaces() {
		return fdVicePlaces;
	}

	public void setFdVicePlaces(List<KmImeetingRes> fdVicePlaces) {
		this.fdVicePlaces = fdVicePlaces;
	}

	// 外部分会场
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

	public boolean isSynchroIn() {
		return synchroIn;
	}

	public void setSynchroIn(boolean synchroIn) {
		this.synchroIn = synchroIn;
	}

	private boolean synchroIn = false;

	private String fdSeatPlanId; // 坐席安排ID

	public String getFdSeatPlanId() {
		return fdSeatPlanId;
	}

	public void setFdSeatPlanId(String fdSeatPlanId) {
		this.fdSeatPlanId = fdSeatPlanId;
	}

	private Boolean fdIsSeatPlan;// 是否安排坐席

	public Boolean getFdIsSeatPlan() {
		if (fdIsSeatPlan == null) {
			fdIsSeatPlan = Boolean.FALSE;
		}
		return fdIsSeatPlan;
	}

	public void setFdIsSeatPlan(Boolean fdIsSeatPlan) {
		this.fdIsSeatPlan = fdIsSeatPlan;
	}


	private Boolean fdVoteEnable;// 是否开启投票

	public Boolean getFdVoteEnable() {
		if (fdVoteEnable == null) {
			fdVoteEnable = Boolean.FALSE;
		}
		return fdVoteEnable;
	}

	public void setFdVoteEnable(Boolean fdVoteEnable) {
		this.fdVoteEnable = fdVoteEnable;
	}

	private List<KmImeetingVote> kmImeetingVotes = new ArrayList<>();// 投票配置

	public List<KmImeetingVote> getKmImeetingVotes() {
		return kmImeetingVotes;
	}

	public void setKmImeetingVotes(List<KmImeetingVote> kmImeetingVotes) {
		this.kmImeetingVotes = kmImeetingVotes;
	}

	private Boolean fdBallotEnable;// 是否开启表决

	public Boolean getFdBallotEnable() {
		if (fdBallotEnable == null) {
			fdBallotEnable = Boolean.FALSE;
		}
		return fdBallotEnable;
	}

	public void setFdBallotEnable(Boolean fdBallotEnable) {
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
