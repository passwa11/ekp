package com.landray.kmss.third.ding.model;

import java.util.Date;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.third.ding.forms.ThirdDingLeavelogForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
  * 流程考勤日志
  */
public class ThirdDingLeavelog extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String docSubject;

    private String fdEkpUserid;

	private String fdEkpUsername;

    private String fdUserid;

    private Integer fdBizType;

    private String fdTagName;

    private String fdSubType;

	private String fdDuration;

	private String fdDurationUnit;

	private String fdFromTime;

	private String fdToTime;

    private String fdApproveId;

    private String fdJumpUrl;

	private String fdParamMap;

    private String fdParams;

    private String fdResult;

    private Integer fdSendTime;

    private Date docCreateTime;

    private Date docAlterTime;

    private String fdIstrue;

	/**
	 * 是否钉钉套件 -> 1:是 0:否
	 */
	private String fdIsDingSuit;

	public String getFdIsDingSuit() {
		return fdIsDingSuit;
	}

	public void setFdIsDingSuit(String fdIsDingSuit) {
		this.fdIsDingSuit = fdIsDingSuit;
	}

	/**
	 * 是否是批量 1:是 0：否
	 */
	private String fdIsBatch;

	public String getFdIsBatch() {
		return fdIsBatch;
	}

	public void setFdIsBatch(String fdIsBatch) {
		this.fdIsBatch = fdIsBatch;
	}

	private String fdReason;

    private SysOrgPerson docCreator;

    private List<ThirdDingLeave> fdLeaveDetail;

    private List<ThirdDingBuss> fdBussDetail;

	private List<ThirdDingOvertime> fdOvertimeDetail;

    @Override
    public Class<ThirdDingLeavelogForm> getFormClass() {
        return ThirdDingLeavelogForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("docCreateTime", new ModelConvertor_Common("docCreateTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docAlterTime", new ModelConvertor_Common("docAlterTime").setDateTimeType(DateUtil.TYPE_DATETIME));
            toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			if (StringUtil.isNotNull(fdEkpUserid)) {
				SysOrgPerson fdEkpUser = UserUtil.getUser(fdEkpUserid);
				toFormPropertyMap.put("fdEkpUsername",
					fdEkpUser == null ? "" : fdEkpUser.getFdName());
			}

            toFormPropertyMap.put("fdLeaveDetail", new ModelConvertor_ModelListToFormList("fdLeaveDetail_Form"));
            toFormPropertyMap.put("fdBussDetail", new ModelConvertor_ModelListToFormList("fdBussDetail_Form"));
			toFormPropertyMap.put("fdOvertimeDetail",
					new ModelConvertor_ModelListToFormList(
							"fdOvertimeDetail_Form"));
        }
        return toFormPropertyMap;
    }

    @Override
    public void recalculateFields() {
        super.recalculateFields();
    }

    /**
     * 流程主题
     */
    public String getDocSubject() {
        return this.docSubject;
    }

    /**
     * 流程主题
     */
    public void setDocSubject(String docSubject) {
        this.docSubject = docSubject;
    }

    /**
     * EKP用户ID
     */
    public String getFdEkpUserid() {
        return this.fdEkpUserid;
    }

    /**
     * EKP用户ID
     */
    public void setFdEkpUserid(String fdEkpUserid) {
        this.fdEkpUserid = fdEkpUserid;
    }

    /**
     * 钉钉用户ID
     */
    public String getFdUserid() {
        return this.fdUserid;
    }

	public String getFdEkpUsername() {
		return fdEkpUsername;
	}

	public void setFdEkpUsername(String fdEkpUsername) {
		this.fdEkpUsername = fdEkpUsername;
	}

	/**
	 * 钉钉用户ID
	 */
    public void setFdUserid(String fdUserid) {
        this.fdUserid = fdUserid;
    }

    /**
     * 业务类型
     */
    public Integer getFdBizType() {
        return this.fdBizType;
    }

    /**
     * 业务类型
     */
    public void setFdBizType(Integer fdBizType) {
        this.fdBizType = fdBizType;
    }

    /**
     * 审批单类型
     */
    public String getFdTagName() {
        return this.fdTagName;
    }

    /**
     * 审批单类型
     */
    public void setFdTagName(String fdTagName) {
        this.fdTagName = fdTagName;
    }

    /**
     * 审批单子类型
     */
    public String getFdSubType() {
        return this.fdSubType;
    }

    /**
     * 审批单子类型
     */
    public void setFdSubType(String fdSubType) {
        this.fdSubType = fdSubType;
    }

    /**
	 * 请假单位
	 */
	public String getFdDurationUnit() {
		return this.fdDurationUnit;
	}

	/**
	 * 请假单位
	 */
	public void setFdDurationUnit(String fdDurationUnit) {
		this.fdDurationUnit = fdDurationUnit;
	}

	/**
	 * 请假时长
	 */
	public String getFdDuration() {
		return fdDuration;
	}

	/**
	 * 请假时长
	 */
	public void setFdDuration(String fdDuration) {
		this.fdDuration = fdDuration;
	}

	/**
	 * 请假开始时间
	 */
	public String getFdFromTime() {
		return this.fdFromTime;
	}

	/**
	 * 请假开始时间
	 */
	public void setFdFromTime(String fdFromTime) {
		this.fdFromTime = fdFromTime;
	}

	/**
	 * 请假结束时间
	 */
	public String getFdToTime() {
		return this.fdToTime;
	}

	/**
	 * 请假结束时间
	 */
	public void setFdToTime(String fdToTime) {
		this.fdToTime = fdToTime;
	}

	/**
	 * 审批单ID
	 */
    public String getFdApproveId() {
        return this.fdApproveId;
    }

    /**
     * 审批单ID
     */
    public void setFdApproveId(String fdApproveId) {
        this.fdApproveId = fdApproveId;
    }

    /**
     * 跳转地址
     */
    public String getFdJumpUrl() {
        return this.fdJumpUrl;
    }

    /**
     * 跳转地址
     */
    public void setFdJumpUrl(String fdJumpUrl) {
        this.fdJumpUrl = fdJumpUrl;
    }

    /**
	 * 参数映射
	 */
	public String getFdParamMap() {
		return this.fdParamMap;
	}

	/**
	 * 参数映射
	 */
	public void setFdParamMap(String fdParamMap) {
		this.fdParamMap = fdParamMap;
	}

	/**
	 * 入参
	 */
    public String getFdParams() {
        return this.fdParams;
    }

    /**
     * 入参
     */
    public void setFdParams(String fdParams) {
        this.fdParams = fdParams;
    }

    /**
     * 出参
     */
    public String getFdResult() {
        return this.fdResult;
    }

    /**
     * 出参
     */
    public void setFdResult(String fdResult) {
        this.fdResult = fdResult;
    }

    /**
     * 发送次数
     */
    public Integer getFdSendTime() {
        return this.fdSendTime;
    }

    /**
     * 发送次数
     */
    public void setFdSendTime(Integer fdSendTime) {
        this.fdSendTime = fdSendTime;
    }

    /**
     * 发送时间
     */
    public Date getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 发送时间
     */
    public void setDocCreateTime(Date docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 同步时间
     */
    public Date getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 同步时间
     */
    public void setDocAlterTime(Date docAlterTime) {
        this.docAlterTime = docAlterTime;
    }

    /**
     * 同步状态
     */
    public String getFdIstrue() {
        return this.fdIstrue;
    }

    /**
     * 同步状态
     */
    public void setFdIstrue(String fdIstrue) {
        this.fdIstrue = fdIstrue;
    }

    /**
     * 同步结果
     */
    public String getFdReason() {
        return this.fdReason;
    }

    /**
     * 同步结果
     */
    public void setFdReason(String fdReason) {
        this.fdReason = fdReason;
    }

    /**
     * 提交人
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * 提交人
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

    /**
     * 请假明细
     */
    public List<ThirdDingLeave> getFdLeaveDetail() {
        return this.fdLeaveDetail;
    }

    /**
     * 请假明细
     */
    public void setFdLeaveDetail(List<ThirdDingLeave> fdLeaveDetail) {
        this.fdLeaveDetail = fdLeaveDetail;
    }

    /**
     * 出差明细
     */
    public List<ThirdDingBuss> getFdBussDetail() {
        return this.fdBussDetail;
    }

    /**
     * 出差明细
     */
    public void setFdBussDetail(List<ThirdDingBuss> fdBussDetail) {
        this.fdBussDetail = fdBussDetail;
    }

	/**
	 * 加班明细
	 */
	public List<ThirdDingOvertime> getFdOvertimeDetail() {
		return this.fdOvertimeDetail;
	}

	/**
	 * 加班明细
	 */
	public void setFdOvertimeDetail(List<ThirdDingOvertime> fdOvertimeDetail) {
		this.fdOvertimeDetail = fdOvertimeDetail;
	}
}
