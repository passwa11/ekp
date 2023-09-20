package com.landray.kmss.fssc.budgeting.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingDetail;
import com.landray.kmss.fssc.budgeting.model.FsscBudgetingMain;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 预算编制明细
  */
public class FsscBudgetingDetailForm extends ExtendForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdBudgetItemId;

    private String fdBudgetItemName;

    private String fdProjectId;

    private String fdProjectName;

    private String fdInnerOrderId;

    private String fdInnerOrderName;

    private String fdWbsId;

    private String fdWbsName;

    private String fdPersonId;

    private String fdPersonName;

    private String fdPeriodOne;

    private String fdPeriodTwo;

    private String fdPeriodThree;

    private String fdPeriodFour;

    private String fdPeriodFive;

    private String fdPeriodSix;

    private String fdPeriodSeven;

    private String fdPeriodEight;

    private String fdPeriodNine;

    private String fdPeriodTen;

    private String fdPeriodEleven;

    private String fdPeriodTwelve;

    private String fdTotal;
    
    private String fdIsLastStage;
    
    private String fdParentId;

    private String docMainId;

    private String docMainName;
    
    private String fdStatus;

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        fdBudgetItemId = null;
        fdBudgetItemName = null;
        fdProjectId = null;
        fdProjectName = null;
        fdInnerOrderId = null;
        fdInnerOrderName = null;
        fdWbsId = null;
        fdWbsName = null;
        fdPersonId = null;
        fdPersonName = null;
        fdPeriodOne = null;
        fdPeriodTwo = null;
        fdPeriodThree = null;
        fdPeriodFour = null;
        fdPeriodFive = null;
        fdPeriodSix = null;
        fdPeriodSeven = null;
        fdPeriodEight = null;
        fdPeriodNine = null;
        fdPeriodTen = null;
        fdPeriodEleven = null;
        fdPeriodTwelve = null;
        fdTotal = null;
        fdIsLastStage=null;
        fdParentId=null;
        docMainId = null;
        docMainName = null;
        fdStatus=null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscBudgetingDetail> getModelClass() {
        return FsscBudgetingDetail.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdBudgetItemId", new FormConvertor_IDToModel("fdBudgetItem", EopBasedataBudgetItem.class));
            toModelPropertyMap.put("fdProjectId", new FormConvertor_IDToModel("fdProject", EopBasedataProject.class));
            toModelPropertyMap.put("fdInnerOrderId", new FormConvertor_IDToModel("fdInnerOrder", EopBasedataInnerOrder.class));
            toModelPropertyMap.put("fdWbsId", new FormConvertor_IDToModel("fdWbs", EopBasedataWbs.class));
            toModelPropertyMap.put("fdPersonId", new FormConvertor_IDToModel("fdPerson", SysOrgPerson.class));
            toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel("docMain", FsscBudgetingMain.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 预算科目
     */
    public String getFdBudgetItemId() {
        return this.fdBudgetItemId;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItemId(String fdBudgetItemId) {
        this.fdBudgetItemId = fdBudgetItemId;
    }

    /**
     * 预算科目
     */
    public String getFdBudgetItemName() {
        return this.fdBudgetItemName;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItemName(String fdBudgetItemName) {
        this.fdBudgetItemName = fdBudgetItemName;
    }

    /**
     * 项目
     */
    public String getFdProjectId() {
        return this.fdProjectId;
    }

    /**
     * 项目
     */
    public void setFdProjectId(String fdProjectId) {
        this.fdProjectId = fdProjectId;
    }

    /**
     * 项目
     */
    public String getFdProjectName() {
        return this.fdProjectName;
    }

    /**
     * 项目
     */
    public void setFdProjectName(String fdProjectName) {
        this.fdProjectName = fdProjectName;
    }

    /**
     * 内部订单
     */
    public String getFdInnerOrderId() {
        return this.fdInnerOrderId;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrderId(String fdInnerOrderId) {
        this.fdInnerOrderId = fdInnerOrderId;
    }

    /**
     * 内部订单
     */
    public String getFdInnerOrderName() {
        return this.fdInnerOrderName;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrderName(String fdInnerOrderName) {
        this.fdInnerOrderName = fdInnerOrderName;
    }

    /**
     * WBS
     */
    public String getFdWbsId() {
        return this.fdWbsId;
    }

    /**
     * WBS
     */
    public void setFdWbsId(String fdWbsId) {
        this.fdWbsId = fdWbsId;
    }

    /**
     * WBS
     */
    public String getFdWbsName() {
        return this.fdWbsName;
    }

    /**
     * WBS
     */
    public void setFdWbsName(String fdWbsName) {
        this.fdWbsName = fdWbsName;
    }

    /**
     * 员工
     */
    public String getFdPersonId() {
        return this.fdPersonId;
    }

    /**
     * 员工
     */
    public void setFdPersonId(String fdPersonId) {
        this.fdPersonId = fdPersonId;
    }

    /**
     * 员工
     */
    public String getFdPersonName() {
        return this.fdPersonName;
    }

    /**
     * 员工
     */
    public void setFdPersonName(String fdPersonName) {
        this.fdPersonName = fdPersonName;
    }

    /**
     * 1期
     */
    public String getFdPeriodOne() {
        return this.fdPeriodOne;
    }

    /**
     * 1期
     */
    public void setFdPeriodOne(String fdPeriodOne) {
        this.fdPeriodOne = fdPeriodOne;
    }

    /**
     * 2期
     */
    public String getFdPeriodTwo() {
        return this.fdPeriodTwo;
    }

    /**
     * 2期
     */
    public void setFdPeriodTwo(String fdPeriodTwo) {
        this.fdPeriodTwo = fdPeriodTwo;
    }

    /**
     * 3期
     */
    public String getFdPeriodThree() {
        return this.fdPeriodThree;
    }

    /**
     * 3期
     */
    public void setFdPeriodThree(String fdPeriodThree) {
        this.fdPeriodThree = fdPeriodThree;
    }

    /**
     * 4期
     */
    public String getFdPeriodFour() {
        return this.fdPeriodFour;
    }

    /**
     * 4期
     */
    public void setFdPeriodFour(String fdPeriodFour) {
        this.fdPeriodFour = fdPeriodFour;
    }

    /**
     * 5期
     */
    public String getFdPeriodFive() {
        return this.fdPeriodFive;
    }

    /**
     * 5期
     */
    public void setFdPeriodFive(String fdPeriodFive) {
        this.fdPeriodFive = fdPeriodFive;
    }

    /**
     * 6期
     */
    public String getFdPeriodSix() {
        return this.fdPeriodSix;
    }

    /**
     * 6期
     */
    public void setFdPeriodSix(String fdPeriodSix) {
        this.fdPeriodSix = fdPeriodSix;
    }

    /**
     * 7期
     */
    public String getFdPeriodSeven() {
        return this.fdPeriodSeven;
    }

    /**
     * 7期
     */
    public void setFdPeriodSeven(String fdPeriodSeven) {
        this.fdPeriodSeven = fdPeriodSeven;
    }

    /**
     * 8期
     */
    public String getFdPeriodEight() {
        return this.fdPeriodEight;
    }

    /**
     * 8期
     */
    public void setFdPeriodEight(String fdPeriodEight) {
        this.fdPeriodEight = fdPeriodEight;
    }

    /**
     * 9期
     */
    public String getFdPeriodNine() {
        return this.fdPeriodNine;
    }

    /**
     * 9期
     */
    public void setFdPeriodNine(String fdPeriodNine) {
        this.fdPeriodNine = fdPeriodNine;
    }

    /**
     * 10期
     */
    public String getFdPeriodTen() {
        return this.fdPeriodTen;
    }

    /**
     * 10期
     */
    public void setFdPeriodTen(String fdPeriodTen) {
        this.fdPeriodTen = fdPeriodTen;
    }

    /**
     * 11期
     */
    public String getFdPeriodEleven() {
        return this.fdPeriodEleven;
    }

    /**
     * 11期
     */
    public void setFdPeriodEleven(String fdPeriodEleven) {
        this.fdPeriodEleven = fdPeriodEleven;
    }

    /**
     * 12期
     */
    public String getFdPeriodTwelve() {
        return this.fdPeriodTwelve;
    }

    /**
     * 12期
     */
    public void setFdPeriodTwelve(String fdPeriodTwelve) {
        this.fdPeriodTwelve = fdPeriodTwelve;
    }

    /**
     * 全年
     */
    public String getFdTotal() {
        return this.fdTotal;
    }

    /**
     * 全年
     */
    public void setFdTotal(String fdTotal) {
        this.fdTotal = fdTotal;
    }

    /**
     * 
     * @return 当前预算科目是否为最末级  0：不是，1：是
     */
    public String getFdIsLastStage() {
		return fdIsLastStage;
	}
    /**
     * 
     * @param fdIsLastStage 当前预算科目是否为最末级  0：不是，1：是
     */
	public void setFdIsLastStage(String fdIsLastStage) {
		this.fdIsLastStage = fdIsLastStage;
	}
	
	/**
	 * 父级预算科目ID，用于统计
	 * @return
	 */

	public String getFdParentId() {
		return fdParentId;
	}
	/**
	 * 父级预算科目ID，用于统计
	 * @param fdParentId
	 */
	public void setFdParentId(String fdParentId) {
		this.fdParentId = fdParentId;
	}

	/**
     * 主表
     */
    public String getDocMainId() {
        return this.docMainId;
    }

    /**
     * 主表
     */
    public void setDocMainId(String docMainId) {
        this.docMainId = docMainId;
    }

    /**
     * 主表
     */
    public String getDocMainName() {
        return this.docMainName;
    }

    /**
     * 主表
     */
    public void setDocMainName(String docMainName) {
        this.docMainName = docMainName;
    }
    /**
     * 明细的审核状态
     */
	public String getFdStatus() {
		return fdStatus;
	}
	 /**
     * 明细的审核状态
     */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}
    
    
}
