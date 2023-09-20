package com.landray.kmss.fssc.config.forms;

import javax.servlet.http.HttpServletRequest;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.fssc.config.model.FsscConfigScore;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.config.model.FsscConfigScoreDetail;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.upload.FormFile;
import com.landray.kmss.util.DateUtil;

/**
  * 积分使用记录
  */
public class FsscConfigScoreDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdName;

    private String docCreatorId;

    private String docCreatorName;
    
    private String fdAddScorePersonId;

    private String fdAddScorePersonName;

    private String docCreateTime;

    private String docPublishTime;

    private String fdModelName;

    private String fdModelId;

    private String fdScoreUse;

    private String docMainId;

    private String docMainName;

    private FormFile file;

    private String fdImportType;
    
    private String fdDesc;

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdName = null;
        docCreatorId = null;
        docCreatorName = null;
        fdAddScorePersonId = null;
        fdAddScorePersonName = null;

        docCreateTime = null;
        docPublishTime = null;
        fdModelName = null;
        fdModelId = null;
        fdScoreUse = null;
        fdDesc=null;
        super.reset(mapping, request);
    }

    public Class<FsscConfigScoreDetail> getModelClass() {
        return FsscConfigScoreDetail.class;
    }

    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.put("docPublishTime", new FormConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscConfigScore.class));
            toModelPropertyMap.put("fdAddScorePersonId", new FormConvertor_IDToModel("fdAddScorePerson", SysOrgPerson.class));

        }
        return toModelPropertyMap;
    }

    /**
     * 名称
     */
    public String getFdName() {
        return this.fdName;
    }

    /**
     * 名称
     */
    public void setFdName(String fdName) {
        this.fdName = fdName;
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
     * 发布时间
     */
    public String getDocPublishTime() {
        return this.docPublishTime;
    }

    /**
     * 发布时间
     */
    public void setDocPublishTime(String docPublishTime) {
        this.docPublishTime = docPublishTime;
    }

    /**
     * 关联流程
     */
    public String getFdModelName() {
        return this.fdModelName;
    }

    /**
     * 关联流程
     */
    public void setFdModelName(String fdModelName) {
        this.fdModelName = fdModelName;
    }

    /**
     * 关联流程ID
     */
    public String getFdModelId() {
        return this.fdModelId;
    }

    /**
     * 关联流程ID
     */
    public void setFdModelId(String fdModelId) {
        this.fdModelId = fdModelId;
    }

    /**
     * 使用积分
     */
    public String getFdScoreUse() {
        return this.fdScoreUse;
    }

    /**
     * 使用积分
     */
    public void setFdScoreUse(String fdScoreUse) {
        this.fdScoreUse = fdScoreUse;
    }

    public String getDocMainId() {
        return this.docMainId;
    }

    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    public String getDocMainName() {
        return this.docMainName;
    }

    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }

    public FormFile getFile() {
        return this.file;
    }

    public void setFile(FormFile file) {
        this.file = file;
    }

    public String getFdImportType() {
        return this.fdImportType;
    }

    public void setFdImportType(String fdImportType) {
        this.fdImportType = fdImportType;
    }

	public String getFdAddScorePersonId() {
		return fdAddScorePersonId;
	}

	public void setFdAddScorePersonId(String fdAddScorePersonId) {
		this.fdAddScorePersonId = fdAddScorePersonId;
	}

	public String getFdAddScorePersonName() {
		return fdAddScorePersonName;
	}

	public void setFdAddScorePersonName(String fdAddScorePersonName) {
		this.fdAddScorePersonName = fdAddScorePersonName;
	}

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}
    
    
}
