package com.landray.kmss.sys.oms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.oms.forms.SysOmsTempPersonForm;
import com.landray.kmss.util.DateUtil;

/**
  * 组织架构人员临时表
  */
public class SysOmsTempPerson extends BaseModel {
	@com.fasterxml.jackson.annotation.JsonIgnore
    private static ModelToFormPropertyMap toFormPropertyMap;
    
    /**
     * 人员名称，必填
     */
    private String fdName;
    
    /**
     * 人员ID，必填
     */
    private String fdPersonId;

    /**
     * 修改时间：必填
     */
    private Long fdAlterTime;

    /**
     * 有效状态：必填
     */
    private Boolean fdIsAvailable;
    
    /**
     * 主部门ID，非必填，为空则无主部门
     */
    private String fdParentid;
    
    /**
     * 人员在主部门的排序号，非必填
     */
    private Integer fdOrder;
    
    /**
     * 手机号，非必填
     */
    private String fdMobileNo;
    
    /**
     * 登录名，非必填
     */
    private String fdLoginName;

    /**
     * 邮箱，非必填
     */
    private String fdEmail;

    /**
     * 性别，1：男，0：女，非必填
     */
    private String fdSex;
    
    /**
     * 工号，非必填
     */
	private String fdNo;
	
	/**
	 * 分机号（办公电话），非必填
	 */
	private String fdWorkPhone;
	
	/**
	 * 职位信息，非必填
	 */
	private String fdPosition;
	
	/**
	 * 备注，非必填
	 */
	private String fdDesc;
	
	/**   
	 * 入职时间
	 */
	private Long fdHireDate;
    
    /**
     * 扩展字段，JSON字符串 如{"fdExra1":1,"fdExtra2":2} 非必填
     */
    private String fdExtra;

    private Date fdCreateTime;

    private String fdTrxId;
    
    //1 处理成功  0处理失败
    private Integer fdStatus;
    
    //失败原因
    private String fdFailReason;
    
    private String fdFailReasonDesc;
    
    //扩展属性，不做业务处理，只做保存，注意和fdExtra的区别
    private String fdExtend;

    public String getFdFailReasonDesc() {
		return fdFailReasonDesc;
	}

	public void setFdFailReasonDesc(String fdFailReasonDesc) {
		this.fdFailReasonDesc = fdFailReasonDesc;
	}

    /**
     * 岗位ID列表
     */
    private List<SysOmsTempPp> postIdList;
    /**
     * 部门ID列表
     */
    private List<SysOmsTempDp> deptIdList;
    
    /**
     * 在对应的部门中的排序：key是部门的Id，value是人员在这个部门的排序值，
     * 在蓝桥中，人员的排序号越大越靠前
     */
    //private Map<String, Long> fdOrderInDepts;
    
    @Override
    public Class<SysOmsTempPersonForm> getFormClass() {
        return SysOmsTempPersonForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdCreateTime", new ModelConvertor_Common("fdCreateTime").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdAlterTime", new ModelConvertor_Common("fdAlterTime").setDateTimeType(DateUtil.TYPE_DATE));
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
     * 所属部门ID
     */
    public String getFdParentid() {
        return this.fdParentid;
    }

    /**
     * 所属部门ID
     */
    public void setFdParentid(String fdParentid) {
        this.fdParentid = fdParentid;
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
     * 手机号
     */
    public String getFdMobileNo() {
        return this.fdMobileNo;
    }

    /**
     * 手机号
     */
    public void setFdMobileNo(String fdMobileNo) {
        this.fdMobileNo = fdMobileNo;
    }

    /**
     * 登录名
     */
    public String getFdLoginName() {
		return fdLoginName;
	}

    /**
     * 登录名
     */
	public void setFdLoginName(String fdLoginName) {
		this.fdLoginName = fdLoginName;
	}

	/**
     * 邮件
     */
    public String getFdEmail() {
        return this.fdEmail;
    }

    /**
     * 邮件
     */
    public void setFdEmail(String fdEmail) {
        this.fdEmail = fdEmail;
    }

    /**
     * 性别
     */
    public String getFdSex() {
        return this.fdSex;
    }

    /**
     * 性别
     */
    public void setFdSex(String fdSex) {
        this.fdSex = fdSex;
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
     * 源数据id
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 源数据id
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 源数据修改时间
     */
    public Long getFdAlterTime() {
        return this.fdAlterTime;
    }

    /**
     * 源数据修改时间
     */
    public void setFdAlterTime(Long fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
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
     * 扩展类型
     */
    public String getFdExtra() {
        return this.fdExtra;
    }

    /**
     * 扩展类型
     */
    public void setFdExtra(String fdExtra) {
        this.fdExtra = fdExtra;
    }

	public List<SysOmsTempPp> getPostIdList() {
		if(postIdList == null) {
            postIdList = new ArrayList<SysOmsTempPp>();
        }
		return postIdList;
	}

	public void setPostIdList(List<SysOmsTempPp> postIdList) {
		this.postIdList = postIdList;
	}

	public List<SysOmsTempDp> getDeptIdList() {
		if(deptIdList == null) {
            deptIdList = new ArrayList<SysOmsTempDp>();
        }
		return deptIdList;
	}

	public void setDeptIdList(List<SysOmsTempDp> deptIdList) {
		this.deptIdList = deptIdList;
	}

/*	public Map<String, Long> getFdOrderInDepts() {
		if(fdOrderInDepts == null)
			fdOrderInDepts = new HashMap<String, Long>();
		return fdOrderInDepts;
	}

	public void setFdOrderInDepts(Map<String, Long> fdOrderInDepts) {
		this.fdOrderInDepts = fdOrderInDepts;
	}*/

	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

	public String getFdWorkPhone() {
		return fdWorkPhone;
	}

	public void setFdWorkPhone(String fdWorkPhone) {
		this.fdWorkPhone = fdWorkPhone;
	}

	public String getFdPosition() {
		return fdPosition;
	}

	public void setFdPosition(String fdPosition) {
		this.fdPosition = fdPosition;
	}

	public String getFdDesc() {
		return fdDesc;
	}

	public void setFdDesc(String fdDesc) {
		this.fdDesc = fdDesc;
	}

	public Long getFdHireDate() {
		return fdHireDate;
	}

	public void setFdHireDate(Long fdHireDate) {
		this.fdHireDate = fdHireDate;
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
