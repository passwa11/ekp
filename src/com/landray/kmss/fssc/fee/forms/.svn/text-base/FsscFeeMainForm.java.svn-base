package com.landray.kmss.fssc.fee.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.fssc.fee.model.FsscFeeTemplate;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.circulation.forms.CirculationForm;
import com.landray.kmss.sys.circulation.interfaces.ISysCirculationForm;
import com.landray.kmss.sys.metadata.forms.ExtendDataFormInfo;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.recycle.forms.ISysRecycleModelForm;
import com.landray.kmss.sys.relation.forms.SysRelationMainForm;
import com.landray.kmss.sys.relation.interfaces.ISysRelationMainForm;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm;
import com.landray.kmss.sys.workflow.interfaces.SysWfBusinessForm;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 费用申请
  */
public class FsscFeeMainForm extends ExtendAuthForm implements  IAttachmentForm, IExtendDataForm, ISysRelationMainForm, ISysCirculationForm,ISysWfMainForm ,ISysRecycleModelForm{

    private static FormToModelPropertyMap toModelPropertyMap;
    
    private String fdIsClosed;

    private String docNumber;

    private String docCreateTime;

    private String docSubject;

    private String fdControlType;

    private String docXform;

    private String docUseXform;

    private String docCreatorId;

    private String docCreatorName;

    private String docTemplateId;

    private String docTemplateName;

    private SysWfBusinessForm sysWfBusinessForm = new SysWfBusinessForm();

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    private ExtendDataFormInfo extendDataFormInfo = new ExtendDataFormInfo();

    private SysRelationMainForm sysRelationMainForm = new SysRelationMainForm();

    public CirculationForm circulationForm = new CirculationForm();
    
    private String docPublishTime;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	fdIsClosed = null;
        docNumber = null;
        docCreateTime = null;
        docSubject = null;
        fdControlType = null;
        docXform = null;
        docUseXform = null;
        docCreatorId = null;
        docCreatorName = null;
        docTemplateId = null;
        docTemplateName = null;
        sysWfBusinessForm = new SysWfBusinessForm();
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        extendDataFormInfo = new ExtendDataFormInfo();
        docPublishTime = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscFeeMain> getModelClass() {
        return FsscFeeMain.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docNumber");
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("docTemplateId", new FormConvertor_IDToModel("docTemplate", FsscFeeTemplate.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("docPublishTime", new FormConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toModelPropertyMap;
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
     * 刚柔控制
     */
    public String getFdControlType() {
        return this.fdControlType;
    }

    /**
     * 刚柔控制
     */
    public void setFdControlType(String fdControlType) {
        this.fdControlType = fdControlType;
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
    public SysRelationMainForm getSysRelationMainForm() {
        return sysRelationMainForm;
    }

    @Override
    public CirculationForm getCirculationForm() {
        return circulationForm;
    }

	public String getFdIsClosed() {
		return fdIsClosed;
	}

	public void setFdIsClosed(String fdIsClosed) {
		this.fdIsClosed = fdIsClosed;
	}
	/*回收站机制开始*/
	private Integer docDeleteFlag;

	@Override
    public Integer getDocDeleteFlag() {
		return docDeleteFlag;
	}

	@Override
    public void setDocDeleteFlag(Integer docDeleteFlag) {
		this.docDeleteFlag = docDeleteFlag;
	}
	/*回收站机制结束*/

	public String getDocPublishTime() {
		return docPublishTime;
	}

	public void setDocPublishTime(String docPublishTime) {
		this.docPublishTime = docPublishTime;
	}

}
