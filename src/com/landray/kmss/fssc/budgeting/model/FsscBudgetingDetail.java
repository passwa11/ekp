package com.landray.kmss.fssc.budgeting.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem;
import com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataWbs;
import com.landray.kmss.fssc.budgeting.forms.FsscBudgetingDetailForm;
import com.landray.kmss.sys.organization.model.SysOrgPerson;

/**
  * 预算编制明细
  */
public class FsscBudgetingDetail extends BaseModel {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private EopBasedataBudgetItem fdBudgetItem;

    private EopBasedataProject fdProject;

    private EopBasedataInnerOrder fdInnerOrder;

    private EopBasedataWbs fdWbs;

    private SysOrgPerson fdPerson;

    private Double fdPeriodOne;

    private Double fdPeriodTwo;

    private Double fdPeriodThree;

    private Double fdPeriodFour;

    private Double fdPeriodFive;

    private Double fdPeriodSix;

    private Double fdPeriodSeven;

    private Double fdPeriodEight;

    private Double fdPeriodNine;

    private Double fdPeriodTen;

    private Double fdPeriodEleven;

    private Double fdPeriodTwelve;

    private Double fdTotal;
    
    private String fdIsLastStage;
    
    private String fdParentId;

    private FsscBudgetingMain docMain;
    
    private String fdStatus;

    @Override
    public Class<FsscBudgetingDetailForm> getFormClass() {
        return FsscBudgetingDetailForm.class;
    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdBudgetItem.fdName", "fdBudgetItemName");
            toFormPropertyMap.put("fdBudgetItem.fdId", "fdBudgetItemId");
            toFormPropertyMap.put("fdProject.fdName", "fdProjectName");
            toFormPropertyMap.put("fdProject.fdId", "fdProjectId");
            toFormPropertyMap.put("fdInnerOrder.fdName", "fdInnerOrderName");
            toFormPropertyMap.put("fdInnerOrder.fdId", "fdInnerOrderId");
            toFormPropertyMap.put("fdWbs.fdName", "fdWbsName");
            toFormPropertyMap.put("fdWbs.fdId", "fdWbsId");
            toFormPropertyMap.put("fdPerson.fdName", "fdPersonName");
            toFormPropertyMap.put("fdPerson.fdId", "fdPersonId");
            toFormPropertyMap.put("docMain.fdYear", "docMainName");
            toFormPropertyMap.put("docMain.fdId", "docMainId");
        }
        return toFormPropertyMap;
    }

    /**
     * 预算科目
     */
    public EopBasedataBudgetItem getFdBudgetItem() {
        return this.fdBudgetItem;
    }

    /**
     * 预算科目
     */
    public void setFdBudgetItem(EopBasedataBudgetItem fdBudgetItem) {
        this.fdBudgetItem = fdBudgetItem;
    }

    /**
     * 项目
     */
    public EopBasedataProject getFdProject() {
        return this.fdProject;
    }

    /**
     * 项目
     */
    public void setFdProject(EopBasedataProject fdProject) {
        this.fdProject = fdProject;
    }

    /**
     * 内部订单
     */
    public EopBasedataInnerOrder getFdInnerOrder() {
        return this.fdInnerOrder;
    }

    /**
     * 内部订单
     */
    public void setFdInnerOrder(EopBasedataInnerOrder fdInnerOrder) {
        this.fdInnerOrder = fdInnerOrder;
    }

    /**
     * WBS
     */
    public EopBasedataWbs getFdWbs() {
        return this.fdWbs;
    }

    /**
     * WBS
     */
    public void setFdWbs(EopBasedataWbs fdWbs) {
        this.fdWbs = fdWbs;
    }

    /**
     * 员工
     */
    public SysOrgPerson getFdPerson() {
        return this.fdPerson;
    }

    /**
     * 员工
     */
    public void setFdPerson(SysOrgPerson fdPerson) {
        this.fdPerson = fdPerson;
    }

    /**
     * 1期
     */
    public Double getFdPeriodOne() {
        return this.fdPeriodOne;
    }

    /**
     * 1期
     */
    public void setFdPeriodOne(Double fdPeriodOne) {
        this.fdPeriodOne = fdPeriodOne;
    }

    /**
     * 2期
     */
    public Double getFdPeriodTwo() {
        return this.fdPeriodTwo;
    }

    /**
     * 2期
     */
    public void setFdPeriodTwo(Double fdPeriodTwo) {
        this.fdPeriodTwo = fdPeriodTwo;
    }

    /**
     * 3期
     */
    public Double getFdPeriodThree() {
        return this.fdPeriodThree;
    }

    /**
     * 3期
     */
    public void setFdPeriodThree(Double fdPeriodThree) {
        this.fdPeriodThree = fdPeriodThree;
    }

    /**
     * 4期
     */
    public Double getFdPeriodFour() {
        return this.fdPeriodFour;
    }

    /**
     * 4期
     */
    public void setFdPeriodFour(Double fdPeriodFour) {
        this.fdPeriodFour = fdPeriodFour;
    }

    /**
     * 5期
     */
    public Double getFdPeriodFive() {
        return this.fdPeriodFive;
    }

    /**
     * 5期
     */
    public void setFdPeriodFive(Double fdPeriodFive) {
        this.fdPeriodFive = fdPeriodFive;
    }

    /**
     * 6期
     */
    public Double getFdPeriodSix() {
        return this.fdPeriodSix;
    }

    /**
     * 6期
     */
    public void setFdPeriodSix(Double fdPeriodSix) {
        this.fdPeriodSix = fdPeriodSix;
    }

    /**
     * 7期
     */
    public Double getFdPeriodSeven() {
        return this.fdPeriodSeven;
    }

    /**
     * 7期
     */
    public void setFdPeriodSeven(Double fdPeriodSeven) {
        this.fdPeriodSeven = fdPeriodSeven;
    }

    /**
     * 8期
     */
    public Double getFdPeriodEight() {
        return this.fdPeriodEight;
    }

    /**
     * 8期
     */
    public void setFdPeriodEight(Double fdPeriodEight) {
        this.fdPeriodEight = fdPeriodEight;
    }

    /**
     * 9期
     */
    public Double getFdPeriodNine() {
        return this.fdPeriodNine;
    }

    /**
     * 9期
     */
    public void setFdPeriodNine(Double fdPeriodNine) {
        this.fdPeriodNine = fdPeriodNine;
    }

    /**
     * 10期
     */
    public Double getFdPeriodTen() {
        return this.fdPeriodTen;
    }

    /**
     * 10期
     */
    public void setFdPeriodTen(Double fdPeriodTen) {
        this.fdPeriodTen = fdPeriodTen;
    }

    /**
     * 11期
     */
    public Double getFdPeriodEleven() {
        return this.fdPeriodEleven;
    }

    /**
     * 11期
     */
    public void setFdPeriodEleven(Double fdPeriodEleven) {
        this.fdPeriodEleven = fdPeriodEleven;
    }

    /**
     * 12期
     */
    public Double getFdPeriodTwelve() {
        return this.fdPeriodTwelve;
    }

    /**
     * 12期
     */
    public void setFdPeriodTwelve(Double fdPeriodTwelve) {
        this.fdPeriodTwelve = fdPeriodTwelve;
    }

    /**
     * 全年
     */
    public Double getFdTotal() {
        return this.fdTotal;
    }

    /**
     * 全年
     */
    public void setFdTotal(Double fdTotal) {
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
    public FsscBudgetingMain getDocMain() {
        return this.docMain;
    }

    /**
     * 主表
     */
    public void setDocMain(FsscBudgetingMain docMain) {
        this.docMain = docMain;
    }
    /**
     * 明细表审核状态
     */
	public String getFdStatus() {
		return fdStatus;
	}
	/**
     * 明细表审核状态
     */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}
    
}
