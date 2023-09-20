package com.landray.kmss.km.calendar.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.convertor.KmCalendar_FormConvertor_IDToModel;
import com.landray.kmss.km.calendar.model.KmCalendarLabel;
import com.landray.kmss.km.calendar.model.KmCalendarMain;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyRemindMainForm;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindMainContextForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.BaseAuthForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;

/**
 * 日程管理主文档 Form
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarMainForm extends BaseAuthForm implements
		ISysNotifyRemindMainForm, IAttachmentForm {

	/**
	 * 标题
	 */
	protected String docSubject = null;

	/**
	 * @return 标题
	 */
	public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * 内容
	 */
	protected String docContent = null;

	/**
	 * @return 内容
	 */
	public String getDocContent() {
		return docContent;
	}

	/**
	 * @param docContent
	 *            内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = docContent;
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
	 * 最后修改时间
	 */
	protected String docAlterTime = null;

	/**
	 * @return 最后修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 开始时间
	 */
	protected String docStartTime = null;

	/**
	 * @return 开始时间
	 */
	public String getDocStartTime() {
		return docStartTime;
	}

	/**
	 * @param docStartTime
	 *            开始时间
	 */
	public void setDocStartTime(String docStartTime) {
		this.docStartTime = docStartTime;
	}

	/**
	 * 结束时间
	 */
	protected String docFinishTime = null;

	/**
	 * @return 结束时间
	 */
	public String getDocFinishTime() {
		return docFinishTime;
	}

	/**
	 * @param docFinishTime
	 *            结束时间
	 */
	public void setDocFinishTime(String docFinishTime) {
		this.docFinishTime = docFinishTime;
	}

	/**
	 * 是否全天事件
	 */
	protected String fdIsAlldayevent = null;

	/**
	 * @return 是否全天事件
	 */
	public String getFdIsAlldayevent() {
		return fdIsAlldayevent;
	}

	/**
	 * @param fdIsAlldayevent
	 *            是否全天事件
	 */
	public void setFdIsAlldayevent(String fdIsAlldayevent) {
		this.fdIsAlldayevent = fdIsAlldayevent;
	}

	/**
	 * 日程重复设置
	 */
	protected String fdRecurrenceStr = null;

	/**
	 * @return 日程重复设置
	 */
	public String getFdRecurrenceStr() {
		return fdRecurrenceStr;
	}

	/**
	 * @param fdRecurrenceStr
	 *            日程重复设置
	 */
	public void setFdRecurrenceStr(String fdRecurrenceStr) {
		this.fdRecurrenceStr = fdRecurrenceStr;
	}

	/**
	 * 是否农历
	 */
	protected String fdIsLunar = null;

	/**
	 * @return 是否农历
	 */
	public String getFdIsLunar() {
		return fdIsLunar;
	}

	/**
	 * @param fdIsLunar
	 *            是否农历
	 */
	public void setFdIsLunar(String fdIsLunar) {
		this.fdIsLunar = fdIsLunar;
	}

	/**
	 * 活动性质
	 */
	protected String fdAuthorityType = KmCalendarConstant.AUTHORITY_TYPE_DEFAULT;

	/**
	 * @return 活动性质
	 */
	public String getFdAuthorityType() {
		return fdAuthorityType;
	}

	/**
	 * @param fdAuthorityType
	 *            活动性质
	 */
	public void setFdAuthorityType(String fdAuthorityType) {
		this.fdAuthorityType = fdAuthorityType;
	}


	/**
	 * 地点
	 */
	protected String fdLocation = null;

	/**
	 * @return 地点
	 */
	public String getFdLocation() {
		return fdLocation;
	}

	/**
	 * @param fdLocation
	 *            地点
	 */
	public void setFdLocation(String fdLocation) {
		this.fdLocation = fdLocation;
	}

	public String getFdRelatedPersonIds() {
		return fdRelatedPersonIds;
	}

	public void setFdRelatedPersonIds(String fdRelatedPersonIds) {
		this.fdRelatedPersonIds = fdRelatedPersonIds;
	}

	/**
	 * 日程相关人id
	 */
	private String fdRelatedPersonIds;

	public String getFdRelatedPersonNames() {
		return fdRelatedPersonNames;
	}

	public void setFdRelatedPersonNames(String fdRelatedPersonNames) {
		this.fdRelatedPersonNames = fdRelatedPersonNames;
	}

	/**
	 * 日程相关人Name
	 */
	private String fdRelatedPersonNames;

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	/**
	 * 日程描述
	 */
	protected String fdDesc;
	/**
	 * 关联url
	 */
	protected String fdRelationUrl = null;

	/**
	 * @return 关联url
	 */
	public String getFdRelationUrl() {
		return fdRelationUrl;
	}

	/**
	 * @param fdRelationUrl
	 *            关联url
	 */
	public void setFdRelationUrl(String fdRelationUrl) {
		this.fdRelationUrl = fdRelationUrl;
	}

	/**
	 * 类型（活动、笔记）
	 */
	protected String fdType = null;

	/**
	 * @return 类型（活动、笔记）
	 */
	public String getFdType() {
		return fdType;
	}

	/**
	 * @param fdType
	 *            类型（活动、笔记）
	 */
	public void setFdType(String fdType) {
		this.fdType = fdType;
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
	 * 所有者Id
	 */
	protected String docOwnerId = null;

	/**
	 * @return 所有者的ID
	 */
	public String getDocOwnerId() {
		return docOwnerId;
	}

	/**
	 * @param docOwnerId
	 *            所有者的ID
	 */
	public void setDocOwnerId(String docOwnerId) {
		this.docOwnerId = docOwnerId;
	}

	/**
	 * 所有者的名称
	 */
	protected String docOwnerName = null;

	/**
	 * @return 所有者的名称
	 */
	public String getDocOwnerName() {
		return docOwnerName;
	}

	/**
	 * @param docOwnerName
	 *            所有者的名称
	 */
	public void setDocOwnerName(String docOwnerName) {
		this.docOwnerName = docOwnerName;
	}

	/*
	 * 所有可选的所有者列表，表示当前用户可以帮其维护日程的用户列表
	 */
	protected Map<String, String> docOwnersMap = new HashMap<String, String>();

	public Map<String, String> getDocOwnersMap() {
		return docOwnersMap;
	}

	public void setDocOwnersMap(Map<String, String> docOwnersMap) {
		this.docOwnersMap = docOwnersMap;
	}

	/**
	 * 所属标签的ID
	 */
	protected String labelId = null;

	/**
	 * @return 所属标签的ID
	 */
	public String getLabelId() {
		return labelId;
	}

	/**
	 * @param labelId
	 *            所属标签的ID
	 */
	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	/**
	 * 所属标签的名称
	 */
	protected String labelName = null;

	/**
	 * @return 所属标签的名称
	 */
	public String getLabelName() {
		return labelName;
	}

	/**
	 * @param labelName
	 *            所属标签的名称
	 */
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	/**
	 * 是否为共享日程
	 */
	private Boolean isShared = false;

	public Boolean getIsShared() {
		return isShared;
	}

	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}

	/**
	 * 来源APP
	 */
	private String createdFrom;

	public String getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}

	/**
	 * 关联日程
	 */
	private String fdRefererId = null;

	public String getFdRefererId() {
		return fdRefererId;
	}

	public void setFdRefererId(String fdRefererId) {
		this.fdRefererId = fdRefererId;
	}

	/**
	 * 标签颜色
	 */
	protected String labelColor = null;

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	/*
	 * 缺省可阅读者
	 */
	protected String authReaderIdsDefault = null;

	public String getAuthReaderIdsDefault() {
		return authReaderIdsDefault;
	}

	public void setAuthReaderIdsDefault(String authReaderIdsDefault) {
		this.authReaderIdsDefault = authReaderIdsDefault;
	}

	/*
	 * 缺省可阅读者名称
	 */
	protected String authReaderNamesDefault = null;

	public String getAuthReaderNamesDefault() {
		return authReaderNamesDefault;
	}

	public void setAuthReaderNamesDefault(String authReaderNamesDefault) {
		this.authReaderNamesDefault = authReaderNamesDefault;
	}

	/*
	 * 缺省可编辑者
	 */
	protected String authEditorIdsDefault = null;

	public String getAuthEditorIdsDefault() {
		return authEditorIdsDefault;
	}

	public void setAuthEditorIdsDefault(String authEditorIdsDefault) {
		this.authEditorIdsDefault = authEditorIdsDefault;
	}

	/*
	 * 缺省可编辑者名称
	 */
	protected String authEditorNamesDefault = null;

	public String getAuthEditorNamesDefault() {
		return authEditorNamesDefault;
	}

	public void setAuthEditorNamesDefault(String authEditorNamesDefault) {
		this.authEditorNamesDefault = authEditorNamesDefault;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		docSubject = null;
		docContent = null;
		docCreateTime = null;
		docAlterTime = null;
		docStartTime = null;
		docFinishTime = null;
		fdIsAlldayevent = null;
		fdRecurrenceStr = null;
		fdIsLunar = null;
		fdAuthorityType = KmCalendarConstant.AUTHORITY_TYPE_DEFAULT;
		fdLocation = null;
		fdRelationUrl = null;
		fdType = null;
		docCreatorId = null;
		docCreatorName = null;
		docOwnerId = null;
		docOwnerName = null;
		labelId = null;
		labelName = null;
		isShared = false;
		fdRefererId = null;
		createdFrom = null;
		authReaderIdsDefault = null;
		authReaderNamesDefault = null;
		authEditorIdsDefault = null;
		authEditorNamesDefault = null;
		fdIsGroup = null;
		fdModelName=null;
		fdSourceSubject = null;
		fdSessionId = null;
		fdSessionType = null;
		fdTypeId = null;
		fdMessageSenderId = null;
		fdMessageReceiverId = null;
		fdRelatedPersonIds=null;
		fdRelatedPersonNames=null;
		super.reset(mapping, request);
	}

	@Override
    @SuppressWarnings("unchecked")
	public Class getModelClass() {
		return KmCalendarMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId",
					new KmCalendar_FormConvertor_IDToModel(
					"docCreator", SysOrgPerson.class));
			toModelPropertyMap.put("docOwnerId",
					new KmCalendar_FormConvertor_IDToModel(
					"docOwner", SysOrgPerson.class));
			toModelPropertyMap.put("labelId", new FormConvertor_IDToModel(
					"docLabel", KmCalendarLabel.class));
			toModelPropertyMap.put("fdRelatedPersonIds",
					new FormConvertor_IDsToModelList("fdRelatedPersons",
							SysOrgPerson.class));
			// toModelPropertyMap.put("authReaderIdsDefault",
			// new FormConvertor_IDsToModelList("authReadersDefault",
			// SysOrgElement.class));
			// toModelPropertyMap.put("authEditorIdsDefault",
			// new FormConvertor_IDsToModelList("authEditorsDefault",
			// SysOrgElement.class));
		}
		return toModelPropertyMap;
	}

	// ==============提醒机制 开始====================
	private SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm = new SysNotifyRemindMainContextForm();

	@Override
    public SysNotifyRemindMainContextForm getSysNotifyRemindMainContextForm() {
		return sysNotifyRemindMainContextForm;
	}

	public void setSysNotifyRemindMainContextForm(
			SysNotifyRemindMainContextForm sysNotifyRemindMainContextForm) {
		this.sysNotifyRemindMainContextForm = sysNotifyRemindMainContextForm;
	}

	// ==============提醒机制 结束====================

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
    public AutoHashMap getAttachmentForms() {

		return autoHashMap;

	}

	protected boolean hasSetRemind = false;

	public boolean isHasSetRemind() {
		return hasSetRemind;
	}

	public void setHasSetRemind(boolean hasSetRemind) {
		this.hasSetRemind = hasSetRemind;
	}

	/**
	 * 重复周期
	 */
	protected String RECURRENCE_FREQ = KmCalendarConstant.RECURRENCE_FREQ_NO;

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

	/**
	 * 农历年
	 */
	protected String lunarYear = null;

	public String getLunarYear() {
		return lunarYear;
	}

	public void setLunarYear(String lunarYear) {
		this.lunarYear = lunarYear;
	}

	public String getLunarMonth() {
		return lunarMonth;
	}

	public void setLunarMonth(String lunarMonth) {
		this.lunarMonth = lunarMonth;
	}

	public String getLunarDay() {
		return lunarDay;
	}

	public void setLunarDay(String lunarDay) {
		this.lunarDay = lunarDay;
	}

	/**
	 * 农历月
	 * 
	 * @return
	 */
	protected String lunarMonth = null;

	/**
	 * 农历日
	 */
	protected String lunarDay = null;

	protected String startHour = null;

	public String getStartHour() {
		return startHour;
	}

	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	public String getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(String startMinute) {
		this.startMinute = startMinute;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}

	public String getLunarStartYear() {
		return lunarStartYear;
	}

	public void setLunarStartYear(String lunarStartYear) {
		this.lunarStartYear = lunarStartYear;
	}

	public String getLunarStartMonth() {
		return lunarStartMonth;
	}

	public void setLunarStartMonth(String lunarStartMonth) {
		this.lunarStartMonth = lunarStartMonth;
	}

	public String getLunarStartDay() {
		return lunarStartDay;
	}

	public void setLunarStartDay(String lunarStartDay) {
		this.lunarStartDay = lunarStartDay;
	}

	public String getLunarStartHour() {
		return lunarStartHour;
	}

	public void setLunarStartHour(String lunarStartHour) {
		this.lunarStartHour = lunarStartHour;
	}

	public String getLunarStartMinute() {
		return lunarStartMinute;
	}

	public void setLunarStartMinute(String lunarStartMinute) {
		this.lunarStartMinute = lunarStartMinute;
	}

	public String getLunarEndYear() {
		return lunarEndYear;
	}

	public void setLunarEndYear(String lunarEndYear) {
		this.lunarEndYear = lunarEndYear;
	}

	public String getLunarEndMonth() {
		return lunarEndMonth;
	}

	public void setLunarEndMonth(String lunarEndMonth) {
		this.lunarEndMonth = lunarEndMonth;
	}

	public String getLunarEndDay() {
		return lunarEndDay;
	}

	public void setLunarEndDay(String lunarEndDay) {
		this.lunarEndDay = lunarEndDay;
	}

	public String getLunarEndHour() {
		return lunarEndHour;
	}

	public void setLunarEndHour(String lunarEndHour) {
		this.lunarEndHour = lunarEndHour;
	}

	public String getLunarEndMinute() {
		return lunarEndMinute;
	}

	public void setLunarEndMinute(String lunarEndMinute) {
		this.lunarEndMinute = lunarEndMinute;
	}

	public String getRECURRENCE_INTERVAL_LUNAR() {
		return RECURRENCE_INTERVAL_LUNAR;
	}

	public void setRECURRENCE_INTERVAL_LUNAR(String recurrence_interval_lunar) {
		RECURRENCE_INTERVAL_LUNAR = recurrence_interval_lunar;
	}

	public String getRECURRENCE_END_TYPE_LUNAR() {
		return RECURRENCE_END_TYPE_LUNAR;
	}

	public void setRECURRENCE_END_TYPE_LUNAR(String recurrence_end_type_lunar) {
		RECURRENCE_END_TYPE_LUNAR = recurrence_end_type_lunar;
	}

	public String getRECURRENCE_COUNT_LUNAR() {
		return RECURRENCE_COUNT_LUNAR;
	}

	public void setRECURRENCE_COUNT_LUNAR(String recurrence_count_lunar) {
		RECURRENCE_COUNT_LUNAR = recurrence_count_lunar;
	}

	public String getRECURRENCE_UNTIL_LUNAR() {
		return RECURRENCE_UNTIL_LUNAR;
	}

	public void setRECURRENCE_UNTIL_LUNAR(String recurrence_until_lunar) {
		RECURRENCE_UNTIL_LUNAR = recurrence_until_lunar;
	}

	protected String startMinute = null;
	protected String endHour = null;
	protected String endMinute = null;
	protected String lunarStartYear = null;
	protected String lunarStartMonth = null;
	protected String lunarStartDay = null;
	protected String lunarStartHour = null;
	protected String lunarStartMinute = null;
	protected String lunarEndYear = null;
	protected String lunarEndMonth = null;
	protected String lunarEndDay = null;
	protected String lunarEndHour = null;
	protected String lunarEndMinute = null;
	protected String RECURRENCE_INTERVAL_LUNAR = "1";
	protected String RECURRENCE_END_TYPE_LUNAR = null;
	protected String RECURRENCE_COUNT_LUNAR = "5";
	protected String RECURRENCE_UNTIL_LUNAR = null;
	protected String docStartTimeNote = null;
	protected String RECURRENCE_FREQ_LUNAR = KmCalendarConstant.RECURRENCE_FREQ_NO;

	public String getRECURRENCE_FREQ_LUNAR() {
		return RECURRENCE_FREQ_LUNAR;
	}

	public void setRECURRENCE_FREQ_LUNAR(String recurrence_freq_lunar) {
		RECURRENCE_FREQ_LUNAR = recurrence_freq_lunar;
	}

	public String getDocStartTimeNote() {
		return docStartTimeNote;
	}

	public void setDocStartTimeNote(String docStartTimeNote) {
		this.docStartTimeNote = docStartTimeNote;
	}

	/**
	 * 是否群组日程
	 */
	protected String fdIsGroup = null;

	public String getFdIsGroup() {
		return fdIsGroup;
	}

	public void setFdIsGroup(String fdIsGroup) {
		this.fdIsGroup = fdIsGroup;
	}

	/**
	 * modeleName
	 */
	private String fdModelName;

	public String getFdModelName() {
		return fdModelName;
	}

	public void setFdModelName(String fdModelName) {
		this.fdModelName = fdModelName;
	}


	/**
	 * kk群应用集成
	 */
	private String fdSourceSubject;// 来源名称
	private String fdSessionId;// 被转消息所在会话的ID
	private String fdSessionType;// 被转消息所在会话的类型 0代表是p2p, 1代表群,2代表讨论组
	private String fdTypeId;// p2p会话为对方的ID，群组/讨论组为组ID
	private String fdMessageIndex;// 代表被转为任务的IM消息的唯一编号,用于从来源返回定位到具体消息
	private String fdMessageSenderId;// 消息发送方的id(此id是KK系统中的用户id或群组id)
	private String fdMessageReceiverId;// 消息接收方的id(此id是KK系统中的用户id或群组id)

	public String getFdSourceSubject() {
		return fdSourceSubject;
	}

	public void setFdSourceSubject(String fdSourceSubject) {
		this.fdSourceSubject = fdSourceSubject;
	}

	public String getFdSessionId() {
		return fdSessionId;
	}

	public void setFdSessionId(String fdSessionId) {
		this.fdSessionId = fdSessionId;
	}

	public String getFdSessionType() {
		return fdSessionType;
	}

	public void setFdSessionType(String fdSessionType) {
		this.fdSessionType = fdSessionType;
	}

	public String getFdTypeId() {
		return fdTypeId;
	}

	public void setFdTypeId(String fdTypeId) {
		this.fdTypeId = fdTypeId;
	}

	public String getFdMessageIndex() {
		return fdMessageIndex;
	}

	public void setFdMessageIndex(String fdMessageIndex) {
		this.fdMessageIndex = fdMessageIndex;
	}

	public String getFdMessageSenderId() {
		return fdMessageSenderId;
	}

	public void setFdMessageSenderId(String fdMessageSenderId) {
		this.fdMessageSenderId = fdMessageSenderId;
	}

	public String getFdMessageReceiverId() {
		return fdMessageReceiverId;
	}

	public void setFdMessageReceiverId(String fdMessageReceiverId) {
		this.fdMessageReceiverId = fdMessageReceiverId;
	}

}
