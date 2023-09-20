package com.landray.kmss.km.smissive.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.km.smissive.model.KmSmissiveMain;
import com.landray.kmss.km.smissive.model.KmSmissiveTemplate;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkForm;
import com.landray.kmss.sys.category.model.SysCategoryProperty;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.news.interfaces.ISysNewsPublishMainForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.print.forms.SysPrintLogForm;
import com.landray.kmss.sys.print.interfaces.ISysPrintLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.tag.interfaces.ISysTagMainForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2010-五月-04
 * 
 * @author 张鹏xn
 */
@SuppressWarnings("serial")
public class KmSmissiveMainForm extends KmSmissiveMainMechanismBaseForm
		implements IAttachmentForm, // 附件
		ISysWfMainForm, // 流程
		ISysRelationMainForm,// 关联
		ISysReadLogForm, // 阅读
		ISysBookmarkForm,// 收藏
		ISysNewsPublishMainForm,// 发布
		ISysTagMainForm, // 标签
		ISysCirculationForm, // 传阅
		ISysRecycleModelForm,
		ISysPrintLogForm // 打印日志

{
	
	/*
	 * 是否需要正文
	 */
	private String fdNeedContent;
	public String getFdNeedContent() {
		return fdNeedContent;
	}

	public void setFdNeedContent(String fdNeedContent) {
		this.fdNeedContent = fdNeedContent;
	}

	/*
	 * 所属类别
	 */
	private String fdTemplateId = null;
	private String fdTemplateName = null;
	/*
	 * 表头
	 */
	private String fdTitle = null;
	/*
	 * 文件标题
	 */
	private String docSubject = null;
	/*
	 * 紧急程度
	 */
	private String fdUrgency = null;
	/*
	 * 密级等级
	 */
	private String fdSecret = null;
	/*
	 * 文件编号
	 */
	private String fdFileNo = null;
	/*
	 * 发文单位
	 */
	private String fdMainDeptId = null;
	private String fdMainDeptName = null;
	/*
	 * 主送单位
	 */
	private String fdSendDeptIds = null;
	private String fdSendDeptNames = null;
	/*
	 * 抄送单位
	 */
	private String fdCopyDeptIds = null;
	private String fdCopyDeptNames = null;
	/*
	 * 签发人
	 */
	private String fdIssuerId = null;
	private String fdIssuerName = null;
	/*
	 * 流程信息保密
	 */
	private String fdFlowFlag = "false";

	/*
	 * 拟稿人
	 */
	private String docAuthorId = null;
	private String docAuthorName = null;
	/*
	 * 创建人
	 */
	private String docCreatorName = null;
	/*
	 * 修改人
	 */
	private String docAlterorName = null;
	/*
	 * 录入单位
	 */
	private String docDeptId = null;
	private String docDeptName = null;
	/*
	 * 拟稿时间
	 */
	private String docCreateTime = null;
	/*
	 * 更新时间
	 */
	private String docAlterTime = null;
	/*
	 * 发布时间
	 */
	private String docPublishTime = null;
	/*
	 * 最后修改时间
	 */
	private String fdLastModifiedTime = null;
	/*
	 * 阅读标记
	 */
	private String authReaderFlag = null;

	private String docPropertyIds = null;
	private String docPropertyNames = null;

	// 授权方式
	private String rightType = "modify"; // add 新增 modify 修改

	private String fdNotifyType = "todo";

	private String newReaderIds = null;

	private String newReaderNames = null;

	private String circulationReason = null;

	@Override
    public String getAuthReaderNoteFlag() {
		return "2";
	}

	public String getNewReaderNames() {
		return newReaderNames;
	}

	public void setNewReaderNames(String newReaderNames) {
		this.newReaderNames = newReaderNames;
	}

	public String getCirculationReason() {
		return circulationReason;
	}

	public void setCirculationReason(String circulationReason) {
		this.circulationReason = circulationReason;
	}

	public String getNewReaderIds() {
		return newReaderIds;
	}

	public void setNewReaderIds(String newReaderIds) {
		this.newReaderIds = newReaderIds;
	}

	public String getFdNotifyType() {
		return fdNotifyType;
	}

	public void setFdNotifyType(String fdNotifyType) {
		this.fdNotifyType = fdNotifyType;
	}

	public String getRightType() {
		return rightType;
	}

	public void setRightType(String rightType) {
		this.rightType = rightType;
	}

	/**
	 * @return 返回 所属类别
	 */
	public String getFdTemplateId() {
		return fdTemplateId;
	}

	/**
	 * @param fdTemplateId
	 *            要设置的 所属类别
	 */
	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
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
	 * @return 返回 紧急程度中文
	 */
	public String getFdUrgencyName() {
		try {
			return EnumerationTypeUtil.getColumnEnumsLabel("km_smissive_urgency", fdUrgency);
		} catch (Exception e) {
			e.printStackTrace();
			return "获取紧级程度中文异常";
		}
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
	 * @return 返回 密级等级汉字
	 */
	public String getFdSecretName() {
		try {
			return EnumerationTypeUtil.getColumnEnumsLabel(
					"km_smissive_secret", fdSecret);
		} catch (Exception e) {
			e.printStackTrace();
			return "获取密级等级中文异常";
		}
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
	 * @return 返回 发文单位
	 */
	public String getFdMainDeptId() {
		return fdMainDeptId;
	}

	/**
	 * @param fdMainDeptId
	 *            要设置的 发文单位
	 */
	public void setFdMainDeptId(String fdMainDeptId) {
		this.fdMainDeptId = fdMainDeptId;
	}

	/**
	 * @return 返回 主送单位
	 */
	public String getFdSendDeptIds() {
		return fdSendDeptIds;
	}

	/**
	 * @param fdSendDeptId
	 *            要设置的 主送单位
	 */
	public void setFdSendDeptIds(String fdSendDeptIds) {
		this.fdSendDeptIds = fdSendDeptIds;
	}

	/**
	 * @return 返回 抄送单位
	 */
	public String getFdCopyDeptIds() {
		return fdCopyDeptIds;
	}

	/**
	 * @param fdCopyDeptId
	 *            要设置的 抄送单位
	 */
	public void setFdCopyDeptIds(String fdCopyDeptIds) {
		this.fdCopyDeptIds = fdCopyDeptIds;
	}

	/**
	 * @return 返回 签发人
	 */
	public String getFdIssuerId() {
		return fdIssuerId;
	}

	/**
	 * @param fdIssuerId
	 *            要设置的 签发人
	 */
	public void setFdIssuerId(String fdIssuerId) {
		this.fdIssuerId = fdIssuerId;
	}

	/**
	 * @return 返回 流程信息保密
	 */
	public String getFdFlowFlag() {
		return fdFlowFlag;
	}

	/**
	 * @param fdFlowFlag
	 *            要设置的 流程信息保密
	 */
	public void setFdFlowFlag(String fdFlowFlag) {
		this.fdFlowFlag = fdFlowFlag;
	}

	/**
	 * @return 返回 拟稿人
	 */
	public String getDocAuthorId() {
		return docAuthorId;
	}

	/**
	 * @param docAuthorId
	 *            要设置的 拟稿人
	 */
	public void setDocAuthorId(String docAuthorId) {
		this.docAuthorId = docAuthorId;
	}

	/**
	 * @return 返回 录入单位
	 */
	public String getDocDeptId() {
		return docDeptId;
	}

	/**
	 * @param docDeptId
	 *            要设置的 录入单位
	 */
	public void setDocDeptId(String docDeptId) {
		this.docDeptId = docDeptId;
	}

	/**
	 * @return 返回 拟稿时间
	 */
	public String getDocCreateTime() {
		return docCreateTime;
	}

	/**
	 * @param docCreateTime
	 *            要设置的 拟稿时间
	 */
	public void setDocCreateTime(String docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	/**
	 * @return 返回 更新时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            要设置的 更新时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * @return 返回 发布时间
	 */
	public String getDocPublishTime() {
		return docPublishTime;
	}

	/**
	 * @param docPublishTime
	 *            要设置的 发布时间
	 */
	public void setDocPublishTime(String docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

	/**
	 * @return 返回 最后修改时间
	 */
	public String getFdLastModifiedTime() {
		return fdLastModifiedTime;
	}

	/**
	 * @param fdLastModifiedTime
	 *            要设置的 最后修改时间
	 */
	public void setFdLastModifiedTime(String fdLastModifiedTime) {
		this.fdLastModifiedTime = fdLastModifiedTime;
	}

	/**
	 * @return 返回 阅读标记
	 */
	public String getAuthReaderFlag() {
		return authReaderFlag;
	}

	/**
	 * @param authReaderFlag
	 *            要设置的 阅读标记
	 */
	public void setAuthReaderFlag(String authReaderFlag) {
		this.authReaderFlag = authReaderFlag;
	}

	public String getFdTemplateName() {
		return fdTemplateName;
	}

	public void setFdTemplateName(String fdTemplateName) {
		this.fdTemplateName = fdTemplateName;
	}

	public String getFdMainDeptName() {
		return fdMainDeptName;
	}

	public void setFdMainDeptName(String fdMainDeptName) {
		this.fdMainDeptName = fdMainDeptName;
	}

	public String getFdSendDeptNames() {
		return fdSendDeptNames;
	}

	public void setFdSendDeptNames(String fdSendDeptNames) {
		this.fdSendDeptNames = fdSendDeptNames;
	}

	public String getFdCopyDeptNames() {
		return fdCopyDeptNames;
	}

	public void setFdCopyDeptNames(String fdCopyDeptNames) {
		this.fdCopyDeptNames = fdCopyDeptNames;
	}

	public String getFdIssuerName() {
		return fdIssuerName;
	}

	public void setFdIssuerName(String fdIssuerName) {
		this.fdIssuerName = fdIssuerName;
	}

	public String getDocAuthorName() {
		return docAuthorName;
	}

	public void setDocAuthorName(String docAuthorName) {
		this.docAuthorName = docAuthorName;
	}

	public String getDocCreatorName() {
		return docCreatorName;
	}

	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	public String getDocAlterorName() {
		return docAlterorName;
	}

	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	public String getDocDeptName() {
		return docDeptName;
	}

	public void setDocDeptName(String docDeptName) {
		this.docDeptName = docDeptName;
	}

	public String getDocPropertyIds() {
		return docPropertyIds;
	}

	public void setDocPropertyIds(String docPropertyIds) {
		this.docPropertyIds = docPropertyIds;
	}

	public String getDocPropertyNames() {
		return docPropertyNames;
	}

	public void setDocPropertyNames(String docPropertyNames) {
		this.docPropertyNames = docPropertyNames;
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @seecom.landray.kmss.web.action.ActionForm#reset(org.apache.struts.action.
	 * ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdNeedContent = null;
		fdTemplateId = null;
		fdTemplateName = null;
		fdTitle = null;
		docSubject = null;
		fdUrgency = null;
		fdSecret = null;
		fdFileNo = null;
		fdMainDeptId = null;
		fdMainDeptName = null;
		fdSendDeptIds = null;
		fdSendDeptNames = null;
		fdCopyDeptIds = null;
		fdCopyDeptNames = null;
		fdIssuerId = null;
		fdIssuerName = null;
		fdFlowFlag = "false";
		docPropertyIds = null;
		docPropertyNames = null;
		docAuthorId = null;
		docAuthorName = null;
		docCreatorName = UserUtil.getUser().getFdName();
		docAlterorName = UserUtil.getUser().getFdName();
		docCreateTime = DateUtil.convertDateToString(new Date(), "date",
				request.getLocale());
		docAlterTime = DateUtil.convertDateToString(new Date(), "datetime",
				request.getLocale());
		docPublishTime = null;
		fdLastModifiedTime = null;
		authReaderFlag = null;

		docStatus = "10";
		if (UserUtil.getUser().getFdParent() != null) {
			docDeptId = UserUtil.getUser().getFdParent().getFdId();
			docDeptName = UserUtil.getUser().getFdParent().getFdName();
		} else {
			docDeptId = null;
			docDeptName = null;
		}
		rightType = "modify";// 用于修改权限
		fdNotifyType = "todo";
		newReaderIds = null;
		newReaderNames = null;
		circulationReason = null;

		super.reset(mapping, request);
	}

	@Override
    public Class<KmSmissiveMain> getModelClass() {
		return KmSmissiveMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("fdTemplateId", new FormConvertor_IDToModel(
					"fdTemplate", KmSmissiveTemplate.class));
			toModelPropertyMap.put("docDeptId", new FormConvertor_IDToModel(
					"docDept", SysOrgElement.class));
			toModelPropertyMap.put("fdMainDeptId", new FormConvertor_IDToModel(
					"fdMainDept", SysOrgElement.class));
			toModelPropertyMap.put("fdSendDeptIds",
					new FormConvertor_IDsToModelList("fdSendDepts",
							SysOrgElement.class));
			toModelPropertyMap.put("fdCopyDeptIds",
					new FormConvertor_IDsToModelList("fdCopyDepts",
							SysOrgElement.class));

			toModelPropertyMap.put("docAuthorId", new FormConvertor_IDToModel(
					"docAuthor", SysOrgPerson.class));
			toModelPropertyMap.put("fdIssuerId", new FormConvertor_IDToModel(
					"fdIssuer", SysOrgPerson.class));

			toModelPropertyMap.put("docPropertyIds",
					new FormConvertor_IDsToModelList("docProperties",
							SysCategoryProperty.class));

		}
		return toModelPropertyMap;
	}

	public CirculationForm circulationForm = new CirculationForm();

	@Override
    public CirculationForm getCirculationForm() {
		return circulationForm;
	}

	// 打印机制日志
	private SysPrintLogForm sysPrintLogForm = new SysPrintLogForm();

	@Override
	public SysPrintLogForm getSysPrintLogForm() {
		return sysPrintLogForm;
	}

	@Override
	public void setSysPrintLogForm(SysPrintLogForm sysPrintLogForm) {
		this.sysPrintLogForm = sysPrintLogForm;
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

	
}
