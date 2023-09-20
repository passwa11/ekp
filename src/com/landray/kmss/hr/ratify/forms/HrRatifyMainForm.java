package com.landray.kmss.hr.ratify.forms;

import com.landray.kmss.common.convertor.*;
import com.landray.kmss.hr.ratify.model.HrRatifyMKeyword;
import com.landray.kmss.hr.ratify.model.HrRatifyMain;
import com.landray.kmss.hr.ratify.model.HrRatifyTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.bookmark.interfaces.ISysBookmarkForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.metadata.forms.ExtendDataFormInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.readlog.forms.ReadLogForm;
import com.landray.kmss.sys.readlog.interfaces.ISysReadLogForm;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 人事流程主文档
 */
public class HrRatifyMainForm extends ExtendAuthForm implements ISysWfMainForm,
        IAttachmentForm, IExtendDataForm, ISysReadLogForm,
        ISysRelationMainForm, ISysCirculationForm, ISysRecycleModelForm,
        ISysBookmarkForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docReadCount;

    private String docNumber;

    private String docCreateTime;

    private String docSubject;

    private String docPublishTime;

    private String docContent;

    private String docXform;

    private String docUseXform;

    private String docTemplateId;

    private String docTemplateName;

    private String docCreatorId;

    private String docCreatorName;

    private String fdDepartmentId;

    private String fdDepartmentName;

    private String fdFeedbackIds;

    private String fdFeedbackNames;

    /*
     * 文档在发布状态下是否指定过反馈人标记
     */
    private String fdFeedbackExecuted = null;

    private SysWfBusinessForm sysWfBusinessForm = new SysWfBusinessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private ExtendDataFormInfo extendDataFormInfo = new ExtendDataFormInfo();

    private ReadLogForm readLogForm = new ReadLogForm();

    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

    public CirculationForm circulationForm = new CirculationForm();

    /*
     * 关键字
     */
    private String fdKeywordNames = null;

    private String fdKeywordIds = null;

    private String titleRegulation = null;

    protected String fdSubclassModelname = null;

    private Boolean fdIsFiling = Boolean.FALSE;
    /**
     * 调岗、调薪生效状态 【true、生效；false、未生效;】
     */
    private Boolean fdIsEffective =Boolean.FALSE;


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docReadCount = null;
        docNumber = null;
        docCreateTime = null;
        docSubject = null;
        docPublishTime = null;
        docContent = null;
        docXform = null;
        docUseXform = null;
        docTemplateId = null;
        docTemplateName = null;
        docCreatorId = null;
        docCreatorName = null;
        fdDepartmentId = null;
        fdDepartmentName = null;
        fdFeedbackIds = null;
        fdFeedbackNames = null;
        fdFeedbackExecuted = null;
        sysWfBusinessForm = new SysWfBusinessForm();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        extendDataFormInfo = new ExtendDataFormInfo();
        fdKeywordNames = null;
        fdKeywordIds = null;
        titleRegulation = null;
        fdSubclassModelname = null;
        fdIsFiling = Boolean.FALSE;
        fdReviewFeedbackInfoCount = null;
        fdIsEffective = Boolean.FALSE;
        super.reset(mapping, request);
    }

    @Override
    public Class getModelClass() {
        return HrRatifyMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            // toModelPropertyMap.addNoConvertProperty("docStatus");
            toModelPropertyMap.addNoConvertProperty("docReadCount");
            toModelPropertyMap.addNoConvertProperty("docNumber");
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("docPublishTime", new FormConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", HrRatifyTemplate.class));
            toModelPropertyMap.put("fdDepartmentId", new FormConvertor_IDToModel("fdDepartment", SysOrgElement.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("fdFeedbackIds", new FormConvertor_IDsToModelList("fdFeedback", SysOrgElement.class));
            toModelPropertyMap.put("authAttCopyIds",
                    new FormConvertor_IDsToModelList("authAttCopys",
                            SysOrgElement.class));
            toModelPropertyMap.put("authAttDownloadIds",
                    new FormConvertor_IDsToModelList("authAttDownloads",
                            SysOrgElement.class));
            toModelPropertyMap.put("authAttPrintIds",
                    new FormConvertor_IDsToModelList("authAttPrints",
                            SysOrgElement.class));
            // 关键字
            toModelPropertyMap.put("fdKeywordNames",
                    new FormConvertor_NamesToModelList("docKeyword", "fdObject",
                            HrRatifyMain.class, "docKeyword",
                            HrRatifyMKeyword.class).setSplitStr(" "));
        }
        return toModelPropertyMap;
    }

    /**
     * 阅读次数
     */
    public String getDocReadCount() {
        return this.docReadCount;
    }
    /**
     * 调岗、调薪生效状态 【true、生效；false、未生效;】
     */
    public Boolean getFdIsEffective() {
        return fdIsEffective;
    }

    public void setFdIsEffective(Boolean fdIsEffective) {
        this.fdIsEffective = fdIsEffective;
    }

    /**
     * 阅读次数
     */
    public void setDocReadCount(String docReadCount) {
        this.docReadCount = docReadCount;
    }

    /**
     * 编号
     */
    public String getDocNumber() {
        return this.docNumber;
    }

    /**
     * 编号
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /**
     * 创建时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 标题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 标题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * 结束时间
     */
    public String getDocPublishTime() {
        return this.docPublishTime;
    }

    /**
     * 结束时间
     */
    public void setDocPublishTime(String docPublishTime) {
        this.docPublishTime = docPublishTime;
    }

    /**
     * 文档内容
     */
    public String getDocContent() {
        return this.docContent;
    }

    /**
     * 文档内容
     */
    public void setDocContent(String docContent) {
        this.docContent = docContent;
    }

    /**
     * 扩展属性
     */
    public String getDocXform() {
        return this.docXform;
    }

    /**
     * 扩展属性
     */
    public void setDocXform(String docXform) {
        this.docXform = docXform;
    }

    /**
     * 是否使用表单
     */
    public String getDocUseXform() {
        return this.docUseXform;
    }

    /**
     * 是否使用表单
     */
    public void setDocUseXform(String docUseXform) {
        this.docUseXform = docUseXform;
    }

    /**
     * 分类模板
     */
    public String getDocTemplateId() {
        return this.docTemplateId;
    }

    /**
     * 分类模板
     */
    public void setDocTemplateId(String docTemplateId) {
        this.docTemplateId = docTemplateId;
    }

    /**
     * 分类模板
     */
    public String getDocTemplateName() {
        return this.docTemplateName;
    }

    /**
     * 分类模板
     */
    public void setDocTemplateName(String docTemplateName) {
        this.docTemplateName = docTemplateName;
    }

    /**
     * 创建人
     */
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 创建人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 创建人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 创建人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 部门
     */
    public String getFdDepartmentId() {
        return this.fdDepartmentId;
    }

    /**
     * 部门
     */
    public void setFdDepartmentId(String fdDepartmentId) {
        this.fdDepartmentId = fdDepartmentId;
    }

    /**
     * 部门
     */
    public String getFdDepartmentName() {
        return this.fdDepartmentName;
    }

    /**
     * 部门
     */
    public void setFdDepartmentName(String fdDepartmentName) {
        this.fdDepartmentName = fdDepartmentName;
    }

    /**
     * 实施反馈人
     */
    public String getFdFeedbackIds() {
        return this.fdFeedbackIds;
    }

    /**
     * 实施反馈人
     */
    public void setFdFeedbackIds(String fdFeedbackIds) {
        this.fdFeedbackIds = fdFeedbackIds;
    }

    /**
     * 实施反馈人
     */
    public String getFdFeedbackNames() {
        return this.fdFeedbackNames;
    }

    /**
     * 实施反馈人
     */
    public void setFdFeedbackNames(String fdFeedbackNames) {
        this.fdFeedbackNames = fdFeedbackNames;
    }

    public String getFdFeedbackExecuted() {
        return fdFeedbackExecuted;
    }

    public void setFdFeedbackExecuted(String fdFeedbackExecuted) {
        this.fdFeedbackExecuted = fdFeedbackExecuted;
    }

    @Override
    public String getAuthReaderNoteFlag() {
        return "2";
    }

    @Override
    public SysWfBusinessForm getSysWfBusinessForm() {
        return sysWfBusinessForm;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    @Override
    public ExtendDataFormInfo getExtendDataFormInfo() {
        return extendDataFormInfo;
    }

    @Override
    public ReadLogForm getReadLogForm() {
        return readLogForm;
    }

    public void setReadLogForm(ReadLogForm readLogForm) {
        this.readLogForm = readLogForm;
    }

    @Override
    public SysRelationMainForm getSysRelationMainForm() {
        return sysRelationMainForm;
    }

    @Override
    public CirculationForm getCirculationForm() {
        return circulationForm;
    }

    public String getFdKeywordIds() {
        return fdKeywordIds;
    }

    public void setFdKeywordIds(String fdKeywordIds) {
        this.fdKeywordIds = fdKeywordIds;
    }

    public String getFdKeywordNames() {
        return fdKeywordNames;
    }

    public void setFdKeywordNames(String fdKeywordNames) {
        this.fdKeywordNames = fdKeywordNames;
    }

    public String getTitleRegulation() {
        return titleRegulation;
    }

    public void setTitleRegulation(String titleRegulation) {
        this.titleRegulation = titleRegulation;
    }

    public String getFdSubclassModelname() {
        return fdSubclassModelname;
    }

    public void setFdSubclassModelname(String fdSubclassModelname) {
        this.fdSubclassModelname = fdSubclassModelname;
    }

    /** 软删除 部署 **/
    private Integer docDeleteFlag;

    @Override
    public Integer getDocDeleteFlag() {
        return docDeleteFlag;
    }

    @Override
    public void setDocDeleteFlag(Integer docDeleteFlag) {
        this.docDeleteFlag = docDeleteFlag;
    }
    /** 软删除 结束 **/

    /**
     * 是否归档
     */
    public Boolean getFdIsFiling() {
        return fdIsFiling;
    }

    /**
     * 是否归档
     */
    public void setFdIsFiling(Boolean fdIsFiling) {
        this.fdIsFiling = fdIsFiling;
    }

    /**
     * 被收藏次数
     */
    private String docMarkCount;

    @Override
    public String getDocMarkCount() {
        return docMarkCount;
    }

    @Override
    public void setDocMarkCount(String count) {
        this.docMarkCount = count;
    }

    private String fdNotifyType = null;

    public String getFdNotifyType() {
        return fdNotifyType;
    }

    public void setFdNotifyType(String fdNotifyType) {
        this.fdNotifyType = fdNotifyType;
    }

    /*
     * 反馈数
     */
    private String fdReviewFeedbackInfoCount = null;

    public String getFdReviewFeedbackInfoCount() {
        return fdReviewFeedbackInfoCount;
    }

    public void setFdReviewFeedbackInfoCount(String fdReviewFeedbackInfoCount) {
        this.fdReviewFeedbackInfoCount = fdReviewFeedbackInfoCount;
    }

}
