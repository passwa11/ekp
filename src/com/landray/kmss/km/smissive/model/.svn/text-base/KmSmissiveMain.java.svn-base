package com.landray.kmss.km.smissive.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.smissive.forms.KmSmissiveMainForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkModel;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationModel;
import com.landray.kmss.sys.ftsearch.interfaces.INeedIndex;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishMainModel;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogModel;
import com.landray.kmss.sys.print.model.SysPrintLog;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogAutoSaveModel;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogModel;
import com.landray.kmss.sys.recycle.model.ISysRecycleModel;
import com.landray.kmss.sys.recycle.model.SysRecycleConstant;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainModel;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainModel;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn 公文管理
 */
@SuppressWarnings("serial")
public class KmSmissiveMain extends KmSmissiveMainMechanismBase implements
		IAttachment,// 附件
		ISysWfMainModel,// 流程
		ISysNotifyModel,// 通知
		ISysNewsPublishMainModel,// 发布到新闻
		ISysReadLogModel,// 阅读
		ISysReadLogAutoSaveModel,// 阅读统计
		ISysBookmarkModel,// 收藏
		ISysTagMainModel,// 标签
		ISysRelationMainModel,// 关联
		ISysCirculationModel, // 传阅
		ISysRecycleModel,
		INeedIndex,
		ISysPrintLogModel // 打印日志
{
	
	
	/*
	 * 是否需要正文
	 */
	private String fdNeedContent;

	public String getFdNeedContent() {
		if (StringUtil.isNull(fdNeedContent)) {
			return "1";
		}
		return fdNeedContent;
	}

	public void setFdNeedContent(String fdNeedContent) {
		this.fdNeedContent = fdNeedContent;
	}
	/*
	 * 所属类别
	 */
	protected KmSmissiveTemplate fdTemplate;

	/*
	 * 表头
	 */
	protected String fdTitle;

	/*
	 * 文件标题
	 */
	protected String docSubject;

	/*
	 * 紧急程度
	 */
	protected String fdUrgency;

	/*
	 * 密级等级
	 */
	protected String fdSecret;

	/*
	 * 文件编号
	 */
	protected String fdFileNo;

	/*
	 * 发文单位
	 */
	protected SysOrgElement fdMainDept;

	/*
	 * 主送单位
	 */
	protected SysOrgElement fdSendDept;
	/*
	 * 主送单位(变成多值，为兼容老数据保留原单值字段)
	 */
	protected List fdSendDepts = new ArrayList();;

	/*
	 * 抄送单位
	 */
	protected SysOrgElement fdCopyDept;

	/*
	 * 抄送单位(变成多值，为兼容老数据保留原单值字段)
	 */
	protected List fdCopyDepts = new ArrayList();

	/*
	 * 签发人
	 */
	protected SysOrgPerson fdIssuer;

	/*
	 * 流程信息保密
	 */
	protected Boolean fdFlowFlag;

	/*
	 * 拟稿人
	 */
	protected SysOrgPerson docAuthor;

	/*
	 * 修改人
	 */
	protected SysOrgPerson docAlteror;

	/*
	 * 录入单位
	 */
	protected SysOrgElement docDept;

	/*
	 * 更新时间
	 */
	protected Date docAlterTime;

	/*
	 * 发布时间
	 */
	protected Date docPublishTime;

	protected String fdNotifyType;

	public KmSmissiveMain() {
		super();
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	/**
	 * @return 返回 表头
	 */
	public String getFdTitle() {
		return fdTitle;
	}

	/**
	 * @param fdTitle
	 *            要设置的 表头
	 */
	public void setFdTitle(String fdTitle) {
		this.fdTitle = fdTitle;
	}

	/**
	 * @return 返回 文件标题
	 */
	@Override
    public String getDocSubject() {
		return docSubject;
	}

	/**
	 * @param docSubject
	 *            要设置的 文件标题
	 */
	public void setDocSubject(String docSubject) {
		this.docSubject = docSubject;
	}

	/**
	 * @return 返回 紧急程度
	 */
	public String getFdUrgency() {
		return fdUrgency;
	}

	/**
	 * @param fdUrgency
	 *            要设置的 紧急程度
	 */
	public void setFdUrgency(String fdUrgency) {
		this.fdUrgency = fdUrgency;
	}

	/**
	 * @return 返回 密级等级
	 */
	public String getFdSecret() {
		return fdSecret;
	}

	/**
	 * @param fdSecret
	 *            要设置的 密级等级
	 */
	public void setFdSecret(String fdSecret) {
		this.fdSecret = fdSecret;
	}

	/**
	 * @return 返回 文件编号
	 */
	public String getFdFileNo() {
		return fdFileNo;
	}

	/**
	 * @param fdFileNo
	 *            要设置的 文件编号
	 */
	public void setFdFileNo(String fdFileNo) {
		this.fdFileNo = fdFileNo;
	}

	/**
	 * @return 返回 更新时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 更新时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * @return 返回 发布时间
	 */
	public Date getDocPublishTime() {
		return docPublishTime;
	}

	/**
	 * @param docPublishTime
	 *            要设置的 发布时间
	 */
	public void setDocPublishTime(Date docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	public KmSmissiveTemplate getFdTemplate() {
		return fdTemplate;
	}

	public void setFdTemplate(KmSmissiveTemplate fdTemplate) {
		this.fdTemplate = fdTemplate;
	}

	public SysOrgElement getFdMainDept() {
		return fdMainDept;
	}

	public void setFdMainDept(SysOrgElement fdMainDept) {
		this.fdMainDept = fdMainDept;
	}

	public SysOrgElement getFdSendDept() {
		return fdSendDept;
	}

	public void setFdSendDept(SysOrgElement fdSendDept) {
		this.fdSendDept = fdSendDept;
	}

	public List getFdSendDepts() {
		if (fdSendDept != null) {
			fdSendDepts.add(fdSendDept);
			fdSendDept = null;
		}
		return fdSendDepts;
	}

	public void setFdSendDepts(List fdSendDepts) {
		this.fdSendDepts = fdSendDepts;
	}

	public SysOrgElement getFdCopyDept() {
		return fdCopyDept;
	}

	public void setFdCopyDept(SysOrgElement fdCopyDept) {
		this.fdCopyDept = fdCopyDept;
	}

	public List getFdCopyDepts() {
		if (fdCopyDept != null) {
			fdCopyDepts.add(fdCopyDept);
			fdCopyDept = null;
		}
		return fdCopyDepts;
	}

	public void setFdCopyDepts(List fdCopyDepts) {
		this.fdCopyDepts = fdCopyDepts;
	}

	public SysOrgPerson getFdIssuer() {
		return fdIssuer;
	}

	public void setFdIssuer(SysOrgPerson fdIssuer) {
		this.fdIssuer = fdIssuer;
	}

	public Boolean getFdFlowFlag() {
		return fdFlowFlag;
	}

	public void setFdFlowFlag(Boolean fdFlowFlag) {
		this.fdFlowFlag = fdFlowFlag;
	}

	public SysOrgPerson getDocAuthor() {
		return docAuthor;
	}

	public void setDocAuthor(SysOrgPerson docAuthor) {
		this.docAuthor = docAuthor;
	}

	public SysOrgPerson getDocAlteror() {
		return docAlteror;
	}

	public void setDocAlteror(SysOrgPerson docAlteror) {
		this.docAlteror = docAlteror;
	}

	@Override
    public SysOrgElement getDocDept() {
		return docDept;
	}

	public void setDocDept(SysOrgElement docDept) {
		this.docDept = docDept;
	}

	/*
	 * 多对多关联 辅助类别
	 */
	protected List<SysCategoryProperty> docProperties = new ArrayList<SysCategoryProperty>();

	/**
	 * @return 返回多对多关联 辅助类别
	 */
	public List<SysCategoryProperty> getDocProperties() {
		return docProperties;
	}

	/**
	 * @param toKhrusers
	 *            要设置的 多对多关联辅助类别
	 */
	public void setDocProperties(List<SysCategoryProperty> docProperties) {
		this.docProperties = docProperties;
	}

	@Override
    public Class<KmSmissiveMainForm> getFormClass() {
		return KmSmissiveMainForm.class;
	}

	/**
	 * @return 返回 所有人可阅读标记
	 */
	@Override
    public java.lang.Boolean getAuthReaderFlag() {
		if (authReaderFlag == null) {
			return new Boolean(false);
		}
		return authReaderFlag;
	}

	/**
	 * @param authReaderFlag
	 *            要设置的 所有人可阅读标记
	 */
	@Override
    public void setAuthReaderFlag(java.lang.Boolean authReaderFlag) {
		this.authReaderFlag = authReaderFlag;
	}
	
	@Override
    protected void recalculateReaderField() {
		super.recalculateReaderField();
		if (getDocStatus().equals(SysDocConstant.DOC_STATUS_PUBLISH)) {
			authAllReaders.addAll(getFdSendDepts());
			authAllReaders.addAll(getFdCopyDepts());
		}
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			// 创建者
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			// 更新者
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			// 作者
			toFormPropertyMap.put("docAuthor.fdId", "docAuthorId");
			toFormPropertyMap.put("docAuthor.fdName", "docAuthorName");
			// 签发人fdIssuer
			toFormPropertyMap.put("fdIssuer.fdId", "fdIssuerId");
			toFormPropertyMap.put("fdIssuer.fdName", "fdIssuerName");
			// 录入单位
			toFormPropertyMap.put("docDept.fdId", "docDeptId");
			toFormPropertyMap.put("docDept.fdName", "docDeptName");
			// 发文单位
			toFormPropertyMap.put("fdMainDept.fdId", "fdMainDeptId");
			toFormPropertyMap.put("fdMainDept.fdName", "fdMainDeptName");
			// 主送单位
			// toFormPropertyMap.put("fdSendDept.fdId", "fdSendDeptId");
			// toFormPropertyMap.put("fdSendDept.fdName", "fdSendDeptName");

			toFormPropertyMap.put("fdSendDepts",
					new ModelConvertor_ModelListToString(
							"fdSendDeptIds:fdSendDeptNames", "fdId:fdName"));

			// 抄送单位
			// toFormPropertyMap.put("fdCopyDept.fdId", "fdCopyDeptId");
			// toFormPropertyMap.put("fdCopyDept.fdName", "fdCopyDeptName");

			toFormPropertyMap.put("fdCopyDepts",
					new ModelConvertor_ModelListToString(
							"fdCopyDeptIds:fdCopyDeptNames", "fdId:fdName"));

			// 类别
			toFormPropertyMap.put("fdTemplate.fdId", "fdTemplateId");
			toFormPropertyMap.put("fdTemplate.fdName", "fdTemplateName");
			// 辅助类别
			toFormPropertyMap.put("docProperties",
					new ModelConvertor_ModelListToString(
							"docPropertyIds:docPropertyNames", "fdId:fdName"));
			toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common(
			"docCreateTime").setDateTimeType(DateUtil.TYPE_DATE));
		}
		return toFormPropertyMap;
	}

	@Override
    public String getCirculationSeparate() {
		return null;
	}

	@Override
    public void setCirculationSeparate(String circulationSeparate) {

	}

	// 打印机制日志
	private SysPrintLog sysPrintLog = null;

	@Override
	public SysPrintLog getSysPrintLog() {
		return sysPrintLog;
	}

	@Override
	public void setSysPrintLog(SysPrintLog sysPrintLog) {
		this.sysPrintLog = sysPrintLog;
	}
	/*软删除配置*/
	private Integer docDeleteFlag;

	@Override
	public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
	public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
	}

	private Date docDeleteTime;

	@Override
	public Date getDocDeleteTime() {
		return docDeleteTime;
	}

	@Override
	public void setDocDeleteTime(Date docDeleteTime) {
		this.docDeleteTime = docDeleteTime;
	}

	private SysOrgPerson docDeleteBy;

	@Override
	public SysOrgPerson getDocDeleteBy() {
		return docDeleteBy;
	}

	@Override
	public void setDocDeleteBy(SysOrgPerson docDeleteBy) {
		this.docDeleteBy = docDeleteBy;
	}

	@Override
	public boolean isNeedIndex() {
		return docDeleteFlag == null || docDeleteFlag == SysRecycleConstant.OPT_TYPE_RECOVER;
	}

}
