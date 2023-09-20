package com.landray.kmss.sys.oms.model;

import java.util.Date;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.oms.forms.SysOmsTempDeptForm;
import com.landray.kmss.util.DateUtil;

/**
  * 组织架构部门临时表
  */
public class SysOmsTempDept extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    /**
     * 部门名称，必填
     */
    private String fdName;
    
    /**
     * 有效状态，必填
     */
    private Boolean fdIsAvailable;
    
    /**
     * 部门ID，必填
     */
    private String fdDeptId;
    
    /**
     * 修改时间，必填
     */
    private Long fdAlterTime;

    /**
     * 父部门ID，非必填，为空则表示没有父部门
     */
    private String fdParentid;

    /**
     * 部门排序号，非必填
     */
    private Integer fdOrder;
    
    private Date fdCreateTime;

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

	private String fdTrxId;

    @Override
    public Class<SysOmsTempDeptForm> getFormClass() {
        return SysOmsTempDeptForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCreateTime", new ModelConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATE));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
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
     * 父部门id
     */
    public String getFdParentid() {
        return this.fdParentid;
    }

    /**
     * 父部门id
     */
    public void setFdParentid(String fdParentid) {
        this.fdParentid = fdParentid;
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
     * 是否有效
     */
    public Boolean getFdIsAvailable() {
        return this.fdIsAvailable;
    }

    /**
     * 是否有效
     */
    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /**
     * 创建时间
     */
    public Date getFdCreateTime() {
        return this.fdCreateTime;
    }

    /**
     * 创建时间
     */
    public void setFdCreateTime(Date fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    /**
     * 状态
     */
    public Integer getFdStatus() {
        return this.fdStatus;
    }

    /**
     * 状态
     */
    public void setFdStatus(Integer fdStatus) {
        this.fdStatus = fdStatus;
    }

    /**
     * 部门id
     */
    public String getFdDeptId() {
        return this.fdDeptId;
    }

    /**
     * 部门id
     */
    public void setFdDeptId(String fdDeptId) {
        this.fdDeptId = fdDeptId;
    }

    /**
     * 修改时间
     */
    public Long getFdAlterTime() {
        return this.fdAlterTime;
    }

    /**
     * 修改时间
     */
    public void setFdAlterTime(Long fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
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

	public String getFdFailReason() {
		return fdFailReason;
	}

	public void setFdFailReason(String fdFailReason) {
		this.fdFailReason = fdFailReason;
	}

	public String getFdExtend() {
		return fdExtend;
	}

	public void setFdExtend(String fdExtend) {
		this.fdExtend = fdExtend;
	}

	

	
    
}
