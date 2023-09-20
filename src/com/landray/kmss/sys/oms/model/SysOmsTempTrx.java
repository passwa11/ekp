package com.landray.kmss.sys.oms.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.oms.forms.SysOmsTempTrxForm;
import com.landray.kmss.util.DateUtil;

/**
  * 事务表
  */
public class SysOmsTempTrx extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private Date beginTime;

    private Date endTime;

    private Integer fdSynModel;

    private Integer fdSynStatus;
    
    /**
     * 部门是否正序
     */
    private Boolean fdDeptIsAsc;
    
    /**
     * 人员是否正序
     */
    private Boolean fdPersonIsAsc;
    
    /**
     * 本次同步事务中，同步配置
     */
    private String fdSynConfigJson;
    
    private String fdLogError;
    
    private String fdLogDetail;

    @Override
    public Class<SysOmsTempTrxForm> getFormClass() {
        return SysOmsTempTrxForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("beginTime", new ModelConvertor_Common("beginTime").setDateTimeType(DateUtil.TYPE_TIME_SEC));
            toFormPropertyMap.put("endTime", new ModelConvertor_Common("endTime").setDateTimeType(DateUtil.TYPE_TIME_SEC));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 事务开始时间
     */
    public Date getBeginTime() {
        return this.beginTime;
    }

    /**
     * 事务开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 事务结束时间
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * 事务结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 同步模式
     */
    public Integer getFdSynModel() {
        return this.fdSynModel;
    }

    /**
     * 同步模式
     */
    public void setFdSynModel(Integer fdSynModel) {
        this.fdSynModel = fdSynModel;
    }

    /**
     * 同步状态
     */
    public Integer getFdSynStatus() {
        return this.fdSynStatus;
    }

    /**
     * 同步状态
     */
    public void setFdSynStatus(Integer fdSynStatus) {
        this.fdSynStatus = fdSynStatus;
    }

	public Boolean getFdDeptIsAsc() {
		return fdDeptIsAsc;
	}

	public void setFdDeptIsAsc(Boolean fdDeptIsAsc) {
		this.fdDeptIsAsc = fdDeptIsAsc;
	}

	public Boolean getFdPersonIsAsc() {
		return fdPersonIsAsc;
	}

	public void setFdPersonIsAsc(Boolean fdPersonIsAsc) {
		this.fdPersonIsAsc = fdPersonIsAsc;
	}

	public String getFdSynConfigJson() {
		return fdSynConfigJson;
	}

	public void setFdSynConfigJson(String fdSynConfigJson) {
		this.fdSynConfigJson = fdSynConfigJson;
	}

	public String getFdLogError() {
		return fdLogError;
	}

	public void setFdLogError(String fdLogError) {
		this.fdLogError = fdLogError;
	}

	public String getFdLogDetail() {
		return fdLogDetail;
	}

	public void setFdLogDetail(String fdLogDetail) {
		this.fdLogDetail = fdLogDetail;
	}
    
    
}
