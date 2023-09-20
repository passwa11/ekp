package com.landray.kmss.fssc.config.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.fssc.config.model.FsscConfigScore;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.right.interfaces.ExtendAuthForm;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

/**
  * 点赞积分配置
  */
public class FsscConfigScoreForm extends ExtendAuthForm implements IAttachmentForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docCreateTime;

    private String fdMonth;
    private String fdYear;

    private String fdScoreInit;

    private String fdScoreRemain;

    private String fdScoreUse;

    private String docCreatorId;

    private String docCreatorName;

    private String fdPersonId;

    private String fdPersonName;

    private AutoArrayList fdDetail_Form = new AutoArrayList(FsscConfigScoreDetailForm.class);

    private String fdDetail_Flag = "0";

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        docCreateTime = null;
        fdMonth = null;
        fdMonth = null;
        fdScoreInit = null;
        fdScoreRemain = null;
        fdScoreUse = null;
        docCreatorId = null;
        docCreatorName = null;
        fdPersonId = null;
        fdPersonName = null;
        fdDetail_Form = new AutoArrayList(FsscConfigScoreDetailForm.class);
        fdDetail_Flag = null;
        super.reset(mapping, request);
    }

    public Class<FsscConfigScore> getModelClass() {
        return FsscConfigScore.class;
    }

    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("fdScoreRemain");
            toModelPropertyMap.addNoConvertProperty("fdScoreUse");
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgPerson.class));
            toModelPropertyMap.put("authReaderIds", new FormConvertor_IDsToModelList("authReaders", SysOrgElement.class));
            toModelPropertyMap.put("fdDetail_Form", new FormConvertor_FormListToModelList("fdDetail", "docMain", "fdDetail_Flag"));
        }
        return toModelPropertyMap;
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

    public String getFdYear() {
		return fdYear;
	}

	public void setFdYear(String fdYear) {
		this.fdYear = fdYear;
	}

	/**
     * 月份
     */
    public String getFdMonth() {
        return this.fdMonth;
    }

    /**
     * 月份
     */
    public void setFdMonth(String fdMonth) {
        this.fdMonth = fdMonth;
    }

    /**
     * 初始积分
     */
    public String getFdScoreInit() {
        return this.fdScoreInit;
    }

    /**
     * 初始积分
     */
    public void setFdScoreInit(String fdScoreInit) {
        this.fdScoreInit = fdScoreInit;
    }

    /**
     * 剩余积分
     */
    public String getFdScoreRemain() {
        return this.fdScoreRemain;
    }

    /**
     * 剩余积分
     */
    public void setFdScoreRemain(String fdScoreRemain) {
        this.fdScoreRemain = fdScoreRemain;
    }

    /**
     * 已使用积分
     */
    public String getFdScoreUse() {
        return this.fdScoreUse;
    }

    /**
     * 已使用积分
     */
    public void setFdScoreUse(String fdScoreUse) {
        this.fdScoreUse = fdScoreUse;
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
     * 岗位
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 岗位
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 岗位
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 岗位
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
    }

    /**
     * 积分使用记录
     */
    public AutoArrayList getFdDetail_Form() {
        return this.fdDetail_Form;
    }

    /**
     * 积分使用记录
     */
    public void setFdDetail_Form(AutoArrayList fdDetail_Form) {
        this.fdDetail_Form = fdDetail_Form;
    }

    /**
     * 积分使用记录
     */
    public String getFdDetail_Flag() {
        return this.fdDetail_Flag;
    }

    /**
     * 积分使用记录
     */
    public void setFdDetail_Flag(String fdDetail_Flag) {
        this.fdDetail_Flag = fdDetail_Flag;
    }

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getAuthReaderNoteFlag() {
        return "2";
    }
    
    private FormFile formFile;

	public FormFile getFormFile() {
		return formFile;
	}

	public void setFormFile(FormFile formFile) {
		this.formFile = formFile;
	}
}
