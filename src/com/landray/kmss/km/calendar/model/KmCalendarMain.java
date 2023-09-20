package com.landray.kmss.km.calendar.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.calendar.constant.KmCalendarConstant;
import com.landray.kmss.km.calendar.forms.KmCalendarMainForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyRemindMainModel;
import com.landray.kmss.sys.notify.interfaces.SysNotifyRemindMainContextModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 时间管理主文档
 * 
 * @author
 * @version 1.0 2013-10-14
 */
@SuppressWarnings("serial")
public class KmCalendarMain extends BaseAuthModel implements
		InterceptFieldEnabled, ISysNotifyRemindMainModel, IAttachment,
		ISysNotifyModel {

	/**
	 * 分组
	 */
	protected List<KmCalendarMainGroup> fdKmCalendarMainGroups;

	/**
	 * 详情
	 */
	protected List<KmCalendarDetails> fdKmCalendarDetails;

	public List<KmCalendarDetails> getFdKmCalendarDetails() {
		return fdKmCalendarDetails;
	}

	public void setFdKmCalendarDetails(List<KmCalendarDetails> fdKmCalendarDetails) {
		this.fdKmCalendarDetails = fdKmCalendarDetails;
	}

	public List<KmCalendarMainGroup> getFdKmCalendarMainGroups() {
		return fdKmCalendarMainGroups;
	}

	public void setFdKmCalendarMainGroups(List<KmCalendarMainGroup> fdKmCalendarMainGroups) {
		this.fdKmCalendarMainGroups = fdKmCalendarMainGroups;
	}

	/**
	 * 标题
	 */
	protected String docSubject;

	public KmCalendarMain() {
	}

	/**
	 * @return 标题
	 */
	@Override
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
	protected String docContent;

	/**
	 * @return 内容
	 */
	public String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
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

	protected String docCreateTimeStr;

	public String getDocCreateTimeStr() {
		return DateUtil.convertDateToString(docCreateTime, DateUtil.TYPE_DATETIME, null);
	}

	/**
	 * 最后修改时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 最后修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            最后修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 所属标签
	 */
	protected KmCalendarLabel docLabel;

	/**
	 * @return docLabel 所属标签
	 */
	public KmCalendarLabel getDocLabel() {
		return docLabel;
	}

	/**
	 * @param docLabel
	 *            所属标签
	 */
	public void setDocLabel(KmCalendarLabel docLabel) {
		this.docLabel = docLabel;
	}

	/**
	 * 创建者
	 */
	protected SysOrgPerson docCreator;

	/**
	 * @return 创建者
	 */
	@Override
	public SysOrgPerson getDocCreator() {
		return docCreator;
	}

	/**
	 * @param docCreator
	 *            创建者
	 */
	@Override
	public void setDocCreator(SysOrgPerson docCreator) {
		this.docCreator = docCreator;
	}

	/**
	 * 所有者
	 */
	protected SysOrgPerson docOwner;

	/**
	 * @return 所有者
	 */
	public SysOrgPerson getDocOwner() {
		return docOwner;
	}

	/**
	 * @param docOwner
	 *            所有者
	 */
	public void setDocOwner(SysOrgPerson docOwner) {
		this.docOwner = docOwner;
	}

	/**
	 * 开始时间
	 */
	protected Date docStartTime;

	/**
	 * @return 开始时间
	 */
	public Date getDocStartTime() {
		return docStartTime;
	}

	/**
	 * @param docStartTime
	 *            开始时间
	 */
	public void setDocStartTime(Date docStartTime) {
		this.docStartTime = docStartTime;
	}

	protected String docStartTimeStr;

	public String getDocStartTimeStr() {
		String pattern = fdIsAlldayevent ? DateUtil.TYPE_DATE : DateUtil.TYPE_DATETIME;
		return DateUtil.convertDateToString(docStartTime, pattern, null);
	}

	/**
	 * 结束时间
	 */
	protected Date docFinishTime;

	/**
	 * @return 结束时间
	 */
	public Date getDocFinishTime() {
		return docFinishTime;
	}

	/**
	 * @param docFinishTime
	 *            结束时间
	 */
	public void setDocFinishTime(Date docFinishTime) {
		this.docFinishTime = docFinishTime;
	}

	protected String docFinishTimeStr;

	public String getDocFinishTimeStr() {
		String pattern = fdIsAlldayevent ? DateUtil.TYPE_DATE : DateUtil.TYPE_DATETIME;
		return DateUtil.convertDateToString(docFinishTime, pattern, null);
	}

	/**
	 * 是否全天事件
	 */
	protected Boolean fdIsAlldayevent;

	/**
	 * @return 是否全天事件
	 */
	public Boolean getFdIsAlldayevent() {
		return fdIsAlldayevent;
	}

	/**
	 * @param fdIsAlldayevent
	 *            是否全天事件
	 */
	public void setFdIsAlldayevent(Boolean fdIsAlldayevent) {
		this.fdIsAlldayevent = fdIsAlldayevent;
	}

	/**
	 * 日程重复设置
	 */
	protected String fdRecurrenceStr;

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
	protected Boolean fdIsLunar;

	/**
	 * @return 是否农历
	 */
	public Boolean getFdIsLunar() {
		return fdIsLunar;
	}

	/**
	 * @param fdIsLunar
	 *            是否农历
	 */
	public void setFdIsLunar(Boolean fdIsLunar) {
		this.fdIsLunar = fdIsLunar;
	}

	/**
	 * 活动性质
	 */
	protected String fdAuthorityType;

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
	protected String fdLocation;

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

	/**
	 * 关联url
	 */
	protected String fdRelationUrl;

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

	protected String viewurl;

	public String getViewurl() {
		return StringUtil.formatUrl("/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId=" + fdId);
	}

	/**
	 * 类型（活动、笔记）
	 */
	protected String fdType;

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

	private String fdCompatibleState;

	public void setFdCompatibleState(String fdCompatibleState) {
		this.fdCompatibleState = fdCompatibleState;
	}

	public String getFdCompatibleState() {
		return fdCompatibleState;
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
	 * 来源APP
	 */
	private String createdFrom;

	public String getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Class getFormClass() {
		return KmCalendarMainForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("docOwner.fdId", "docOwnerId");
			toFormPropertyMap.put("docOwner.fdName", "docOwnerName");
			toFormPropertyMap.put("docLabel.fdId", "labelId");
			toFormPropertyMap.put("docLabel.fdName", "labelName");
			toFormPropertyMap.put("docLabel.fdColor", "labelColor");
			toFormPropertyMap.put("fdRelatedPersons",
					new ModelConvertor_ModelListToString(
							"fdRelatedPersonIds:fdRelatedPersonNames",
							"fdId:fdName"));

		}
		return toFormPropertyMap;
	}

	// =======提醒机制开始==========
	private SysNotifyRemindMainContextModel sysNotifyRemindMainContextModel = new SysNotifyRemindMainContextModel();

	@Override
	public SysNotifyRemindMainContextModel getSysNotifyRemindMainContextModel() {
		return sysNotifyRemindMainContextModel;
	}

	@SuppressWarnings("unchecked")
	public void setSysNotifyRemindMainContextModel(List sysNotifyRemindMainList) {
		sysNotifyRemindMainContextModel
				.setSysNotifyRemindMainList(sysNotifyRemindMainList);
	}

	@Override
	public void setSysNotifyRemindMainContextModel(
			SysNotifyRemindMainContextModel sysNotifyRemindMainContextModel) {
		this.sysNotifyRemindMainContextModel = sysNotifyRemindMainContextModel;
	}

	// =======提醒机制结束==========

	AutoHashMap autoHashMap = new AutoHashMap(AttachmentDetailsForm.class);

	@Override
	public AutoHashMap getAttachmentForms() {

		return autoHashMap;

	}

	public void setAttachmentForms(AutoHashMap autoHashMap) {

		this.autoHashMap = autoHashMap;

	}

	/*
	 * 重复日历，最后一次事件的开始时间
	 */
	private Date fdRecurrenceLastStart = null;

	public Date getFdRecurrenceLastStart() {
		return fdRecurrenceLastStart;
	}

	public void setFdRecurrenceLastStart(Date fdRecurrenceLastStart) {
		this.fdRecurrenceLastStart = fdRecurrenceLastStart;
	}

	public Date getFdRecurrenceLastEnd() {
		return fdRecurrenceLastEnd;
	}

	public void setFdRecurrenceLastEnd(Date fdRecurrenceLastEnd) {
		this.fdRecurrenceLastEnd = fdRecurrenceLastEnd;
	}

	/*
	 * 重复日历，最后一次事件的结束时间
	 */
	private Date fdRecurrenceLastEnd = null;

	@Override
	protected void recalculateReaderField() {
		// 重新计算可阅读者
		if (authAllReaders == null) {
            authAllReaders = new ArrayList();
        } else {
            authAllReaders.clear();
        }

		authAllReaders.add(getDocCreator());
		SysOrgPerson owner = getDocOwner();
		if (owner != null && !owner.getFdId().equals(getDocCreator().getFdId())
				&& !authAllReaders.contains(owner)) {
			authAllReaders.add(getDocOwner());
		}

		String authorityType = getFdAuthorityType();

		if (authorityType != null
				&& authorityType
						.equals(KmCalendarConstant.AUTHORITY_TYPE_DEFAULT)) {
			ArrayUtil.concatTwoList(getAuthReaders(), authAllReaders);
		}
		// authAllReaders.addAll(authAllEditors);
		ArrayUtil.concatTwoList(authAllEditors, authAllReaders);
	}

	@Override
	protected void recalculateEditorField() {
		// 重新计算可编辑者
		if (authAllEditors == null) {
            authAllEditors = new ArrayList();
        } else {
            authAllEditors.clear();
        }

		authAllEditors.add(getDocCreator());
		SysOrgPerson owner = getDocOwner();
		if (owner != null && !owner.getFdId().equals(getDocCreator().getFdId())
				&& !authAllEditors.contains(owner)) {
			authAllEditors.add(getDocOwner());
		}

		List tmpList = getAuthEditors();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllEditors);
		}

	}

	public String getDocContent(boolean lazy) {
		if (lazy) {
			return (String) readLazyField("docContent", docContent);
		} else {
			return docContent;
		}
	}

	public void setDocContent(String docContent, boolean lazy) {
		if (lazy) {
			this.docContent = (String) writeLazyField("docContent",
					this.docContent, docContent);
		} else {
			this.docContent = docContent;
		}
	}

	private Boolean fdIsGroup;

	public Boolean getFdIsGroup() {
		return fdIsGroup;
	}

	public void setFdIsGroup(Boolean fdIsGroup) {
		this.fdIsGroup = fdIsGroup;
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

	/**
	 * 日程相关人员
	 */
	private List<SysOrgPerson> fdRelatedPersons;

	public List<SysOrgPerson> getFdRelatedPersons() {
		return fdRelatedPersons;
	}

	public void setFdRelatedPersons(List<SysOrgPerson> fdRelatedPersons) {
		this.fdRelatedPersons = fdRelatedPersons;
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
	 * 日程描述
	 */
	private String fdDesc;

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

}
