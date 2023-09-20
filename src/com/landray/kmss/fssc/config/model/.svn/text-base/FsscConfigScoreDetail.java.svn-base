package com.landray.kmss.fssc.config.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import java.util.Date;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.config.forms.FsscConfigScoreDetailForm;

/**
  * 积分使用记录
  */
public class FsscConfigScoreDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdName;

    private SysOrgPerson docCreator;//建单人
    
    private SysOrgPerson fdAddScorePerson;//加分人

    private Integer fdScoreUse;

    private Date docCreateTime;

    private Date docPublishTime;

    private String fdModelName;

    private String fdModelId;
    
    private String fdDesc;
    
    private int flag;
    
    private FsscConfigScore docMain;

    public Class<FsscConfigScoreDetailForm> getFormClass() {
        return FsscConfigScoreDetailForm.class;
    }

    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
            toFormPropertyMap.put("fdAddScorePerson.fdName", "fdAddScorePersonName");
            toFormPropertyMap.put("fdAddScorePerson.fdId", "fdAddScorePersonId");
            
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docPublishTime", new ModelConvertor_Common("docPublishTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docMain.fdMonth", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 创建人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 创建时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 创建时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 发布时间
     */
    public Date getDocPublishTime() {
        return this.docPublishTime;
    }

    /**
     * 发布时间
     */
    public void setDocPublishTime(Date docPublishTime) {
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
    public Integer getFdScoreUse() {
    	if(fdScoreUse==null){
    		fdScoreUse=0;
    	}
        return this.fdScoreUse;
    }

    /**
     * 使用积分
     */
    public void setFdScoreUse(Integer fdScoreUse) {
        this.fdScoreUse = fdScoreUse;
    }

    public FsscConfigScore getDocMain() {
        return this.docMain;
    }

    public void setDocMain(FsscConfigScore docMain) {
        this.docMain = docMain;
    }

	public SysOrgPerson getFdAddScorePerson() {
		return fdAddScorePerson;
	}

	public void setFdAddScorePerson(SysOrgPerson fdAddScorePerson) {
		this.fdAddScorePerson = fdAddScorePerson;
	}

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	
}
