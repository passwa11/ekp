package com.landray.kmss.fssc.config.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.sys.right.interfaces.ExtendAuthModel;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;

import java.util.ArrayList;
import java.util.List;
import com.landray.kmss.fssc.config.forms.FsscConfigScoreForm;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.sys.attachment.model.IAttachment;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 点赞积分配置
  */
public class FsscConfigScore extends ExtendAuthModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdMonth;
    private String fdYear;

    private Integer fdScoreInit;

    private Integer fdScoreRemain;

    private Integer fdScoreUse;

    private SysOrgPerson fdPerson;

    private List<FsscConfigScoreDetail> fdDetail;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public Class<FsscConfigScoreForm> getFormClass() {
        return FsscConfigScoreForm.class;
    }

    public ModelToFormPropertyMap getToFormPropertyMap() {
    	
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.addNoConvertProperty("authReaderFlag");
            toFormPropertyMap.addNoConvertProperty("authEditorFlag");
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("authReaders", new ModelConvertor_ModelListToString("authReaderIds:authReaderNames", "fdId:fdName"));
            toFormPropertyMap.put("fdDetail", new ModelConvertor_ModelListToFormList("fdDetail_Form"));
        }
        return toFormPropertyMap;
    }

    public void recalculateFields() {
        super.recalculateFields();
        if (!getAuthReaderFlag()) {
        }
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
    public Integer getFdScoreInit() {
    	if(fdScoreInit==null){
    		fdScoreInit=0;
    	}
        return this.fdScoreInit;
    }

    /**
     * 初始积分
     */
    public void setFdScoreInit(Integer fdScoreInit) {
        this.fdScoreInit = fdScoreInit;
    }

    /**
     * 剩余积分
     */
    public Integer getFdScoreRemain() {
    	this.fdScoreRemain=getFdScoreInit()-getFdScoreUse();
        return this.fdScoreRemain;
    }

    /**
     * 剩余积分
     */
    public void setFdScoreRemain(Integer fdScoreRemain) {
        this.fdScoreRemain = fdScoreRemain;
    }

    /**
     * 已使用积分
     */
    public Integer getFdScoreUse() {
    	this.fdScoreUse=0;
    	if(this.fdDetail==null){
    		return this.fdScoreUse;
    	}
    	//统计明细行数量
    	for (FsscConfigScoreDetail detail : fdDetail) {
    		this.fdScoreUse+=detail.getFdScoreUse();
		}
        return this.fdScoreUse;
    }

    /**
     * 已使用积分
     */
    public void setFdScoreUse(Integer fdScoreUse) {
        this.fdScoreUse = fdScoreUse;
    }

    /**
     * 岗位
     */
    public SysOrgPerson getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 岗位
     */
    public void setFdPerson(SysOrgPerson fdPerson) {
        this.fdPerson = fdPerson;
    }

    /**
     * 积分使用记录
     */
    public List<FsscConfigScoreDetail> getFdDetail() {
        return this.fdDetail;
    }

    /**
     * 积分使用记录
     */
    public void setFdDetail(List<FsscConfigScoreDetail> fdDetail) {
        this.fdDetail = fdDetail;
    }

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }

    public String getDocStatus() {
        return "30";
    }

    public String getDocSubject() {
        return getFdMonth();
    }
}
