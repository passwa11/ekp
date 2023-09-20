package com.landray.kmss.third.ding.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.util.AutoArrayList;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 流程考勤日志
  */
public class ThirdDingLeavelogForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String docSubject;

    private String fdEkpUserid;

	private String fdEkpUsername;

    private String fdUserid;

    private String fdBizType;

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

    private String fdSendTime;

    private String docCreateTime;

    private String docAlterTime;

    private String fdIstrue;

    private String fdReason;

    private String docCreatorId;

    private String docCreatorName;

    private AutoArrayList fdLeaveDetail_Form = new AutoArrayList(ThirdDingLeaveForm.class);

    private String fdLeaveDetail_Flag = "0";

    private AutoArrayList fdBussDetail_Form = new AutoArrayList(ThirdDingBussForm.class);

    private String fdBussDetail_Flag = "0";

	private AutoArrayList fdOvertimeDetail_Form = new AutoArrayList(
			ThirdDingOvertimeForm.class);

	private String fdOvertimeDetail_Flag = "0";

	private String fdIsDingSuit; // 钉钉套件

	public String getFdIsDingSuit() {
		return fdIsDingSuit;
	}

	public void setFdIsDingSuit(String fdIsDingSuit) {
		this.fdIsDingSuit = fdIsDingSuit;
	}

	/**
	 * 是否是批量
	 */
	private String fdIsBatch;

	public String getFdIsBatch() {
		return fdIsBatch;
	}

	public void setFdIsBatch(String fdIsBatch) {
		this.fdIsBatch = fdIsBatch;
	}

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        docSubject = null;
        fdEkpUserid = null;
		fdEkpUsername = null;
        fdUserid = null;
        fdBizType = null;
        fdTagName = null;
        fdSubType = null;
		fdDuration = null;
		fdDurationUnit = null;
		fdFromTime = null;
		fdToTime = null;
        fdApproveId = null;
        fdJumpUrl = null;
		fdParamMap = null;
        fdParams = null;
        fdResult = null;
        fdSendTime = null;
        docCreateTime = null;
        docAlterTime = null;
        fdIstrue = null;
        fdReason = null;
        docCreatorId = null;
        docCreatorName = null;
        fdLeaveDetail_Form = new AutoArrayList(ThirdDingLeaveForm.class);
        fdLeaveDetail_Flag = null;
        fdBussDetail_Form = new AutoArrayList(ThirdDingBussForm.class);
        fdBussDetail_Flag = null;
		fdOvertimeDetail_Form = new AutoArrayList(ThirdDingOvertimeForm.class);
		fdOvertimeDetail_Flag = null;
		fdIsDingSuit = null;
		fdIsBatch = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<ThirdDingLeavelog> getModelClass() {
        return ThirdDingLeavelog.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.addNoConvertProperty("docCreateTime");
            toModelPropertyMap.addNoConvertProperty("docAlterTime");
            toModelPropertyMap.put("fdLeaveDetail_Form", new FormConvertor_FormListToModelList("fdLeaveDetail", "docMain", "fdLeaveDetail_Flag"));
            toModelPropertyMap.put("fdBussDetail_Form", new FormConvertor_FormListToModelList("fdBussDetail", "docMain", "fdBussDetail_Flag"));
			toModelPropertyMap.put("fdOvertimeDetail_Form",
					new FormConvertor_FormListToModelList("fdOvertimeDetail",
							"docMain", "fdOvertimeDetail_Flag"));
        }
        return toModelPropertyMap;
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
	 * EKP用户名(申请人，不一定是操作人)
	 */
	public String getFdEkpUsername() {
		return fdEkpUsername;
	}

    /**
	 * EKP用户名(申请人，不一定是操作人)
	 */
	public void setFdEkpUsername(String fdEkpUsername) {
		this.fdEkpUsername = fdEkpUsername;
	}

	/**
	 * 钉钉用户ID
	 */
    public String getFdUserid() {
        return this.fdUserid;
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
    public String getFdBizType() {
        return this.fdBizType;
    }

    /**
     * 业务类型
     */
    public void setFdBizType(String fdBizType) {
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
    public String getFdSendTime() {
        return this.fdSendTime;
    }

    /**
     * 发送次数
     */
    public void setFdSendTime(String fdSendTime) {
        this.fdSendTime = fdSendTime;
    }

    /**
     * 发送时间
     */
    public String getDocCreateTime() {
        return this.docCreateTime;
    }

    /**
     * 发送时间
     */
    public void setDocCreateTime(String docCreateTime) {
        this.docCreateTime = docCreateTime;
    }

    /**
     * 同步时间
     */
    public String getDocAlterTime() {
        return this.docAlterTime;
    }

    /**
     * 同步时间
     */
    public void setDocAlterTime(String docAlterTime) {
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
    public String getDocCreatorId() {
        return this.docCreatorId;
    }

    /**
     * 提交人
     */
    public void setDocCreatorId(String docCreatorId) {
        this.docCreatorId = docCreatorId;
    }

    /**
     * 提交人
     */
    public String getDocCreatorName() {
        return this.docCreatorName;
    }

    /**
     * 提交人
     */
    public void setDocCreatorName(String docCreatorName) {
        this.docCreatorName = docCreatorName;
    }

    /**
     * 请假明细
     */
    public AutoArrayList getFdLeaveDetail_Form() {
        return this.fdLeaveDetail_Form;
    }

    /**
     * 请假明细
     */
    public void setFdLeaveDetail_Form(AutoArrayList fdLeaveDetail_Form) {
        this.fdLeaveDetail_Form = fdLeaveDetail_Form;
    }

    /**
     * 请假明细
     */
    public String getFdLeaveDetail_Flag() {
        return this.fdLeaveDetail_Flag;
    }

    /**
     * 请假明细
     */
    public void setFdLeaveDetail_Flag(String fdLeaveDetail_Flag) {
        this.fdLeaveDetail_Flag = fdLeaveDetail_Flag;
    }

    /**
     * 出差明细
     */
    public AutoArrayList getFdBussDetail_Form() {
        return this.fdBussDetail_Form;
    }

    /**
     * 出差明细
     */
    public void setFdBussDetail_Form(AutoArrayList fdBussDetail_Form) {
        this.fdBussDetail_Form = fdBussDetail_Form;
    }

    /**
     * 出差明细
     */
    public String getFdBussDetail_Flag() {
        return this.fdBussDetail_Flag;
    }

    /**
     * 出差明细
     */
    public void setFdBussDetail_Flag(String fdBussDetail_Flag) {
        this.fdBussDetail_Flag = fdBussDetail_Flag;
    }

	/**
	 * 加班明细
	 */
	public AutoArrayList getFdOvertimeDetail_Form() {
		return this.fdOvertimeDetail_Form;
	}

	/**
	 * 加班明细
	 */
	public void setFdOvertimeDetail_Form(AutoArrayList fdOvertimeDetail_Form) {
		this.fdOvertimeDetail_Form = fdOvertimeDetail_Form;
	}

	/**
	 * 加班明细
	 */
	public String getFdOvertimeDetail_Flag() {
		return this.fdOvertimeDetail_Flag;
	}

	/**
	 * 加班明细
	 */
	public void setFdOvertimeDetail_Flag(String fdOvertimeDetail_Flag) {
		this.fdOvertimeDetail_Flag = fdOvertimeDetail_Flag;
	}
}
