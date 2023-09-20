package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.ImeetingConstant;
import com.landray.kmss.km.imeeting.forms.KmImeetingSummaryForm;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogAutoSaveModel;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainModel;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.StringUtil;

import com.landray.kmss.sys.hibernate.spi.InterceptFieldEnabled;

/**
 * 会议纪要
 */
public class KmImeetingSummary extends ExtendAuthModel implements
		InterceptFieldEnabled, ISysWfMainModel, ISysCirculationModel,
		ISysReadLogAutoSaveModel, IAttachment {

	/**
	 * 纪要名称
	 */
	protected String fdName;

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
	protected Date docCreateTime;

	/**
	 * @return 录入时间
	 */
	@Override
    public Date getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            录入时间
	 */
	@Override
    public void setDocCreateTime(Date docCreateTime) {
		this.docCreateTime = docCreateTime;
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
	 * 召开时间
	 */
	protected Date fdHoldDate;

	/**
	 * @return 召开时间
	 */
	public Date getFdHoldDate() {
		return fdHoldDate;
	}

	/**
	 * @param fdHoldDate
	 *            召开时间
	 */
	public void setFdHoldDate(Date fdHoldDate) {
		this.fdHoldDate = fdHoldDate;
	}

	/**
	 * 结束时间
	 */
	protected Date fdFinishDate;

	public Date getFdFinishDate() {
		return fdFinishDate;
	}

	public void setFdFinishDate(Date fdFinishDate) {
		this.fdFinishDate = fdFinishDate;
	}

	/**
	 * 会议历时
	 */
	protected Double fdHoldDuration;

	public Double getFdHoldDuration() {
		return fdHoldDuration;
	}

	public void setFdHoldDuration(Double fdHoldDuration) {
		this.fdHoldDuration = fdHoldDuration;
	}

	/**
	 * 召开地点
	 */
	protected KmImeetingRes fdPlace;

	/**
	 * @return 召开地点
	 */
	public KmImeetingRes getFdPlace() {
		return fdPlace;
	}

	/**
	 * @param fdPlace
	 *            召开地点
	 */
	public void setFdPlace(KmImeetingRes fdPlace) {
		this.fdPlace = fdPlace;
	}

	/**
	 * 外部召开地点
	 */
	protected String fdOtherPlace;

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

	public String getFdOtherVicePlace() {
		return fdOtherVicePlace;
	}

	public void setFdOtherVicePlace(String fdOtherVicePlace) {
		this.fdOtherVicePlace = fdOtherVicePlace;
	}

	private String fdOtherVicePlaceCoord;

	public String getFdOtherVicePlaceCoord() {
		return fdOtherVicePlaceCoord;
	}

	public void setFdOtherVicePlaceCoord(String fdOtherVicePlaceCoord) {
		this.fdOtherVicePlaceCoord = fdOtherVicePlaceCoord;
	}

	/**
	 * 主持人
	 */
	protected SysOrgElement fdHost;

	/**
	 * @return 主持人
	 */
	public SysOrgElement getFdHost() {
		return fdHost;
	}

	/**
	 * @param fdHost
	 *            主持人
	 */
	public void setFdHost(SysOrgElement fdHost) {
		this.fdHost = fdHost;
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
	 * 与会人员
	 */
	protected List<SysOrgElement> fdPlanAttendPersons;

	/**
	 * @return 与会人员
	 */
	public List<SysOrgElement> getFdPlanAttendPersons() {
		return fdPlanAttendPersons;
	}

	/**
	 * @param fdPlanAttendPersons
	 *            与会人员
	 */
	public void setFdPlanAttendPersons(List<SysOrgElement> fdPlanAttendPersons) {
		this.fdPlanAttendPersons = fdPlanAttendPersons;
	}

	/**
	 * 其他与会人员
	 */
	protected String fdPlanOtherAttendPerson;

	public String getFdPlanOtherAttendPerson() {
		return fdPlanOtherAttendPerson;
	}

	public void setFdPlanOtherAttendPerson(String fdPlanOtherAttendPerson) {
		this.fdPlanOtherAttendPerson = fdPlanOtherAttendPerson;
	}

	/**
	 * 列席人员
	 */
	protected List<SysOrgElement> fdPlanParticipantPersons;

	/**
	 * @return 列席人员
	 */
	public List<SysOrgElement> getFdPlanParticipantPersons() {
		return fdPlanParticipantPersons;
	}

	/**
	 * @param fdPlanParticipantPersons
	 *            列席人员
	 */
	public void setFdPlanParticipantPersons(
			List<SysOrgElement> fdPlanParticipantPersons) {
		this.fdPlanParticipantPersons = fdPlanParticipantPersons;
	}

	/**
	 * 其他列席人员
	 */
	protected String fdPlanOtherParticipantPersons;

	public String getFdPlanOtherParticipantPersons() {
		return fdPlanOtherParticipantPersons;
	}

	public void setFdPlanOtherParticipantPersons(
			String fdPlanOtherParticipantPersons) {
		this.fdPlanOtherParticipantPersons = fdPlanOtherParticipantPersons;
	}

	/**
	 * 实际与员人员（手动填写，默认继承自计划参加人员+计划列席人员）
	 */
	protected List<SysOrgElement> fdActualAttendPersons;

	/**
	 * @return 实际会员人员
	 */
	public List<SysOrgElement> getFdActualAttendPersons() {
		return fdActualAttendPersons;
	}

	/**
	 * @param fdActualAttendPersons
	 *            实际会员人员
	 */
	public void setFdActualAttendPersons(
			List<SysOrgElement> fdActualAttendPersons) {
		this.fdActualAttendPersons = fdActualAttendPersons;
	}

	/**
	 * 实际外部与员人员（手动填写，默认继承自计划参加人员+计划列席人员）
	 */
	protected String fdActualOtherAttendPersons;

	public String getFdActualOtherAttendPersons() {
		return fdActualOtherAttendPersons;
	}

	public void setFdActualOtherAttendPersons(String fdActualOtherAttendPersons) {
		this.fdActualOtherAttendPersons = fdActualOtherAttendPersons;
	}

	/**
	 * 签署人员
	 */
	protected List<SysOrgPerson> fdSignPersons;

	public List<SysOrgPerson> getFdSignPersons() {
		return fdSignPersons;
	}

	public void setFdSignPersons(List<SysOrgPerson> fdSignPersons) {
		this.fdSignPersons = fdSignPersons;
	}

	/**
	 * 纪要内容
	 */
	protected String docContent;

	/**
	 * @return 纪要内容
	 */
	public String getDocContent() {
		return (String) readLazyField("docContent", docContent);
	}

	/**
	 * @param docContent
	 *            纪要内容
	 */
	public void setDocContent(String docContent) {
		this.docContent = (String) writeLazyField("docContent",
				this.docContent, docContent);
	}

	/**
	 * 文档内容的HTML
	 */
	protected String fdHtmlContent;

	public String getFdHtmlContent() {
		return (String) readLazyField("fdHtmlContent", fdHtmlContent);
	}

	public void setFdHtmlContent(String fdHtmlContent) {
		this.fdHtmlContent = (String) writeLazyField("fdHtmlContent",
				this.fdHtmlContent, fdHtmlContent);
	}

	/**
	 * 文档内容的编辑方式
	 */
	protected String fdContentType;

	public String getFdContentType() {
		if (StringUtil.isNull(fdContentType)) {
			return ImeetingConstant.FDCONTENTTYPE_RTF;
		}
		return fdContentType;
	}

	public void setFdContentType(String fdContentType) {
		this.fdContentType = fdContentType;
	}

	/**
	 * 纪要通知方式
	 */
	protected String fdNotifyType;

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
	 * 所属部门
	 */
	protected SysOrgElement docDept;

	/**
	 * @return 所属部门
	 */
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
	 * 纪要抄送人
	 */
	protected List<SysOrgElement> fdCopyToPersons;

	/**
	 * @return 纪要抄送人
	 */
	public List<SysOrgElement> getFdCopyToPersons() {
		return fdCopyToPersons;
	}

	/**
	 * @param fdCopyToPersons
	 *            纪要抄送人
	 */
	public void setFdCopyToPersons(List<SysOrgElement> fdCopyToPersons) {
		this.fdCopyToPersons = fdCopyToPersons;
	}

	@Override
    public Boolean getAuthReaderFlag() {
		return new Boolean(false);
	}

	@Override
    public Class getFormClass() {
		return KmImeetingSummaryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
			toFormPropertyMap.put("fdTemplate.fdName", "fdTemplateName");
			toFormPropertyMap.put("fdPlace.fdId", "fdPlaceId");
			toFormPropertyMap.put("fdPlace.fdName", "fdPlaceName");
			toFormPropertyMap.put("docDept.fdId", "docDeptId");
			toFormPropertyMap.put("docDept.deptLevelNames", "docDeptName");
			toFormPropertyMap.put("fdHost.fdId", "fdHostId");
			toFormPropertyMap.put("fdHost.fdName", "fdHostName");
			toFormPropertyMap.put("fdEmcee.fdId", "fdEmceeId");
			toFormPropertyMap.put("fdEmcee.fdName", "fdEmceeName");
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdMeeting.fdId", "fdMeetingId");
			toFormPropertyMap.put("fdMeeting.fdName", "fdMeetingName");
			toFormPropertyMap.put("fdPlanAttendPersons",
					new ModelConvertor_ModelListToString(
							"fdPlanAttendPersonIds:fdPlanAttendPersonNames",
							"fdId:deptLevelNames"));
			toFormPropertyMap
					.put(
							"fdPlanParticipantPersons",
							new ModelConvertor_ModelListToString(
									"fdPlanParticipantPersonIds:fdPlanParticipantPersonNames",
									"fdId:deptLevelNames"));
			toFormPropertyMap
					.put(
							"fdActualAttendPersons",
							new ModelConvertor_ModelListToString(
									"fdActualAttendPersonIds:fdActualAttendPersonNames",
									"fdId:deptLevelNames"));
			toFormPropertyMap
					.put(
							"fdSignPersons",
							new ModelConvertor_ModelListToString(
									"fdSignPersonIds:fdSignPersonNames",
									"fdId:fdName"));
			toFormPropertyMap.put("fdCopyToPersons",
					new ModelConvertor_ModelListToString(
							"fdCopyToPersonIds:fdCopyToPersonNames",
							"fdId:deptLevelNames"));
			toFormPropertyMap.put("fdVicePlaces",
					new ModelConvertor_ModelListToString(
							"fdVicePlaceIds:fdVicePlaceNames",
							"fdId:fdName"));				
		}
		return toFormPropertyMap;
	}

	@Override
	public String getDocSubject() {
		return this.fdName;
	}

	@Override
	public void recalculateFields() {
		super.recalculateFields();
		String tempStatus = getDocStatus();
		if (StringUtil.isNotNull(tempStatus) && tempStatus.charAt(0) >= '3') {
			List<SysOrgElement> tmpList = new ArrayList<SysOrgElement>();
			// 主持人
			if (this.getFdHost() != null) {
				tmpList.add(this.getFdHost());
			}
			// 会议组织人
			if (this.getFdEmcee() != null) {
				tmpList.add(this.getFdEmcee());
			}
			// 实际与会参与人
			if (this.getFdActualAttendPersons() != null
					&& !this.getFdActualAttendPersons().isEmpty()) {
				tmpList.addAll(this.getFdActualAttendPersons());
			}
			// 计划参与人员
			if (this.getFdPlanAttendPersons() != null
					&& !this.getFdPlanAttendPersons().isEmpty()) {
				tmpList.addAll(this.getFdPlanAttendPersons());
			}
			// 计划列席人员
			if (this.getFdPlanParticipantPersons() != null
					&& !this.getFdPlanParticipantPersons().isEmpty()) {
				tmpList.addAll(this.getFdPlanParticipantPersons());
			}
			// 抄送人员
			if (this.getFdCopyToPersons() != null
					&& !this.getFdCopyToPersons().isEmpty()) {
				tmpList.addAll(this.getFdCopyToPersons());
			}
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
	 * 传阅机制
	 */
	@Override
    public String getCirculationSeparate() {
		return null;
	}

	@Override
    public void setCirculationSeparate(String circulationSeparate) {
	}

	/**
	 * 文档阅读次数
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
	 * 纪要通知人
	 */
	protected String fdNotifyPerson;

	private String[] fdNotifyPersonList = new String[5];

	public String getFdNotifyPerson() {
		return fdNotifyPerson;
	}

	public void setFdNotifyPerson(String fdNotifyPerson) {
		this.fdNotifyPerson = fdNotifyPerson;
	}

	public String[] getFdNotifyPersonList() {
		String notifyPerson = this.fdNotifyPerson;
		String[] temps = new String[5];
		if (notifyPerson != null) {
			if (notifyPerson
					.contains(ImeetingConstant.MEETING_SUMMARY_NOTITY_EMCEE)) {
				temps[0] = ImeetingConstant.MEETING_SUMMARY_NOTITY_EMCEE;
			}
			if (notifyPerson
					.contains(ImeetingConstant.MEETING_SUMMARY_NOTITY_HOST)) {
				temps[1] = ImeetingConstant.MEETING_SUMMARY_NOTITY_HOST;
			}
			if (notifyPerson.contains(
					ImeetingConstant.MEETING_SUMMARY_NOTITY_ACTUAL_ATTEND)) {
				temps[2] = ImeetingConstant.MEETING_SUMMARY_NOTITY_ACTUAL_ATTEND;
			}
			if (notifyPerson
					.contains(ImeetingConstant.MEETING_SUMMARY_NOTITY_COPY)) {
				temps[3] = ImeetingConstant.MEETING_SUMMARY_NOTITY_COPY;
			}
			if (notifyPerson
					.contains(ImeetingConstant.MEETING_SUMMARY_NOTITY_PLAN)) {
				temps[4] = ImeetingConstant.MEETING_SUMMARY_NOTITY_PLAN;
			}
		}
		this.fdNotifyPersonList = temps;
		return fdNotifyPersonList;
	}

	public void setFdNotifyPersonList(String[] fdNotifyPersonList) {
		this.fdNotifyPersonList = fdNotifyPersonList;
	}

	/**
	 * 是否启用电子签章
	 */

	private Boolean fdSignEnable;

	/**
	 * 是否启用电子签章
	 */
	public Boolean getFdSignEnable() {
		if (fdSignEnable == null) {
			return false;
		}
		return fdSignEnable;
	}

	/**
	 * 是否启用电子签章
	 */
	public void setFdSignEnable(Boolean fdSignEnable) {
		this.fdSignEnable = fdSignEnable;
	}
}
