package com.landray.kmss.sys.oms.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.oms.forms.SysOmsTempDpForm;

/**
  * 组织架构部门人员关系临时表
  */
public class SysOmsTempDp extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;
    
    /**
     * 部门ID，必填
     */
    private String fdDeptId;
    
    private String fdDeptName;


	/**
     * 人员ID，必填
     */
    private String fdPersonId;
    
    private String fdPersonName;
    
    /**
     * 有效状态：非必填
     */
    private Boolean fdIsAvailable;
    
    /**
     * 修改时间，非必填
     */
    private Long fdAlterTime;
    
    /**
     * 排序号，非必填  取值范围0~1_000_000_000之间
     */
    private Integer fdOrder;

    private String fdTrxId;
    
    //1 处理成功  0处理失败 
    private Integer fdStatus;
    
    //失败原因
    private String fdFailReason;
    
    private String fdFailReasonDesc;
    
    //扩展属性，不做业务处理，只做保存
    private String fdExtend;

    public String getFdFailReasonDesc() {
		return fdFailReasonDesc;
	}

	public void setFdFailReasonDesc(String fdFailReasonDesc) {
		this.fdFailReasonDesc = fdFailReasonDesc;
	}

    @Override
    public Class<SysOmsTempDpForm> getFormClass() {
        return SysOmsTempDpForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 排序号
     */
    public Integer getFdOrder() {
        return this.fdOrder;
    }

    /**
     * 排序号
     */
    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /**
     * 事务号
     */
    public String getFdTrxId() {
        return this.fdTrxId;
    }

    /**
     * 事务号
     */
    public void setFdTrxId(String fdTrxId) {
        this.fdTrxId = fdTrxId;
    }

    /**
     * 部门ID
     */
    public String getFdDeptId() {
        return this.fdDeptId;
    }

    /**
     * 部门ID
     */
    public void setFdDeptId(String fdDeptId) {
        this.fdDeptId = fdDeptId;
    }

    /**
     * 人员ID
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 人员ID
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 最后更新时间
     */
    public Long getFdAlterTime() {
        return this.fdAlterTime;
    }

    /**
     * 最后更新时间
     */
    public void setFdAlterTime(Long fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
    }

	public Boolean getFdIsAvailable() {
		return fdIsAvailable;
	}

	public void setFdIsAvailable(Boolean fdIsAvailable) {
		this.fdIsAvailable = fdIsAvailable;
	}

	public Integer getFdStatus() {
		return fdStatus;
	}

	public void setFdStatus(Integer fdStatus) {
		this.fdStatus = fdStatus;
	}

	public String getFdFailReason() {
		return fdFailReason;
	}

	public void setFdFailReason(String fdFailReason) {
		this.fdFailReason = fdFailReason;
	}

	public String getFdDeptName() {
		return fdDeptName;
	}

	public void setFdDeptName(String fdDeptName) {
		this.fdDeptName = fdDeptName;
	}

	public String getFdPersonName() {
		return fdPersonName;
	}

	public void setFdPersonName(String fdPersonName) {
		this.fdPersonName = fdPersonName;
	}

	public String getFdExtend() {
		return fdExtend;
	}

	public void setFdExtend(String fdExtend) {
		this.fdExtend = fdExtend;
	}

	

    	
    
}
