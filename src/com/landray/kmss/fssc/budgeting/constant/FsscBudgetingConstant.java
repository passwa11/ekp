package com.landray.kmss.fssc.budgeting.constant;

public interface FsscBudgetingConstant {
    /**
     * 机构类型：部门<br>
     */
    public static final String FSSC_BUDGETING_ORGTYPE_DEPT = "1";
    /**
     * 机构类型：成本中心<br>
     */
    public static final String FSSC_BUDGETING_ORGTYPE_COSTCENTER = "2";
    /**
     * 机构类型：记账公司<br>
     */
    public static final String FSSC_BUDGETING_ORGTYPE_COMPANY = "3";
    /**
     * 机构类型：记账公司组<br>
     */
    public static final String FSSC_BUDGETING_ORGTYPE_COMPANY_GROUP = "4";
    /**
     * 是否最末级预算科目：是<br>
     */
    public static final String FSSC_BUDGETING_LASTSTAGE_YES = "1";
    /**
     * 是否最末级预算科目：否<br>
     */
    public static final String FSSC_BUDGETING_LASTSTAGE_NO = "0";
    /**
     * 预算状态：草稿<br>
     */
    public static final String FD_STATUS_DRAFT = "1";
    /**
     * 预算状态：审核中<br>
     */
    public static final String FD_STATUS_EXAMINE = "2";
    /**
     * 预算状态：驳回<br>
     */
    public static final String FD_STATUS_REFUSE = "3";
    /**
     * 预算状态：已审核<br>
     */
    public static final String FD_STATUS_AUDITED = "4";
    /**
     * 预算状态：生效<br>
     */
    public static final String FD_STATUS_EFFECT = "5";
    /**
     * 预算状态：废弃<br>
     */
    public static final String FD_STATUS_DISCARD = "6";
    /**
     * 预算编制模式：自上而下<br>
     */
    public static final String FD_BUDTING_TYPE_DOWN = "1";
    /**
     * 预算编制模式：自下而上<br>
     */
    public static final String FD_BUDTING_TYPE_UP = "2";
    /**
     * 预算编制模式：独立编制<br>
     */
    public static final String FD_BUDTING_TYPE_INDEPENDENT = "3";
    /**
     * 预算编制操作：提交<br>
     */
    public static final String FD_BUDTING_OPERAT_SUBMIT = "submit";
    /**
     * 预算编制操作：驳回<br>
     */
    public static final String FD_BUDTING_OPERAT_REJECT = "reject";
    /**
     * 预算编制操作：审批通过<br>
     */
    public static final String FD_BUDTING_OPERAT_PASS = "pass";
    /**
     * 预算编制操作：预算生效<br>
     */
    public static final String FD_BUDTING_OPERAT_EFFECT = "effect";

    
    
}
