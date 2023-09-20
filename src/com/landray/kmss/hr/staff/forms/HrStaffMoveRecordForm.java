package com.landray.kmss.hr.staff.forms;

import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffMoveRecord;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.web.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
  * 异动信息
  */
public class HrStaffMoveRecordForm extends HrStaffBaseForm {

    private static FormToModelPropertyMap toModelPropertyMap;

    private String fdStaffNumber;

    private String fdStaffName;

    private String fdIsExplore;

    private String fdInternStartDate;

    private String fdInternEndDate;

    private String fdMoveType;

    private String fdBeforeRank;

    private String fdAfterRank;

    private String fdMoveDate;

    private String fdBeforeFirstDeptName;

    private String fdBeforeSecondDeptName;

    private String fdBeforeThirdDeptName;

    private String fdAfterFirstDeptName;

    private String fdAfterSecondDeptName;

    private String fdAfterThirdDeptName;

    private String fdTransDept;

    private String fdBeforeLeaderId;

    private String fdBeforeLeaderName;

    private String fdBeforeDeptId;

    private String fdBeforeDeptName;

    private String fdAfterDeptId;

    private String fdAfterDeptName;

    private String fdAfterLeaderId;

    private String fdAfterLeaderName;

    private String fdPersonInfoId;

    private String fdPersonInfoName;

    private String fdBeforePostIds;

    private String fdBeforePostNames;

    private String fdAfterPostIds;

    private String fdAfterPostNames;
    /**
     * 异动生效时间
     */
    private String fdEffectiveDate;

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    public String getFdEffectiveDate() {
        return fdEffectiveDate;
    }

    public void setFdEffectiveDate(String fdEffectiveDate) {
        this.fdEffectiveDate = fdEffectiveDate;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);
        fdStaffNumber = null;
        fdStaffName = null;
        fdIsExplore = null;
        fdInternStartDate = null;
        fdInternEndDate = null;
        fdMoveType = null;
        fdBeforeRank = null;
        fdAfterRank = null;
        fdMoveDate = null;
        fdBeforeFirstDeptName = null;
        fdBeforeSecondDeptName = null;
        fdBeforeThirdDeptName = null;
        fdAfterFirstDeptName = null;
        fdAfterSecondDeptName = null;
        fdAfterThirdDeptName = null;
        fdTransDept = null;
        fdBeforeLeaderId = null;
        fdBeforeLeaderName = null;
        fdBeforeDeptId = null;
        fdBeforeDeptName = null;
        fdAfterDeptId = null;
        fdAfterDeptName = null;
        fdAfterLeaderId = null;
        fdAfterLeaderName = null;
        fdPersonInfoId = null;
        fdPersonInfoName = null;
        fdBeforePostIds = null;
        fdBeforePostNames = null;
        fdAfterPostIds = null;
        fdAfterPostNames = null;
        fdEffectiveDate = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<HrStaffMoveRecord> getModelClass() {
        return HrStaffMoveRecord.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
            toModelPropertyMap.put("fdInternStartDate", new FormConvertor_Common("fdInternStartDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdInternEndDate", new FormConvertor_Common("fdInternEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdMoveDate", new FormConvertor_Common("fdMoveDate").setDateTimeType(DateUtil.TYPE_DATE));
            toModelPropertyMap.put("fdBeforeLeaderId", new FormConvertor_IDToModel("fdBeforeLeader", SysOrgElement.class));
            toModelPropertyMap.put("fdBeforeDeptId", new FormConvertor_IDToModel("fdBeforeDept", SysOrgElement.class));
            toModelPropertyMap.put("fdAfterDeptId", new FormConvertor_IDToModel("fdAfterDept", SysOrgElement.class));
            toModelPropertyMap.put("fdAfterLeaderId", new FormConvertor_IDToModel("fdAfterLeader", SysOrgElement.class));
            toModelPropertyMap.put("fdPersonInfoId", new FormConvertor_IDToModel("fdPersonInfo", SysOrgPerson.class));
            toModelPropertyMap.put("fdBeforePostIds", new FormConvertor_IDsToModelList("fdBeforePosts", SysOrgElement.class));
            toModelPropertyMap.put("fdAfterPostIds", new FormConvertor_IDsToModelList("fdAfterPosts", SysOrgElement.class));
        }
        return toModelPropertyMap;
    }

    /**
     * 人员编号
     */
    public String getFdStaffNumber() {
        return this.fdStaffNumber;
    }

    /**
     * 人员编号
     */
    public void setFdStaffNumber(String fdStaffNumber) {
        this.fdStaffNumber = fdStaffNumber;
    }

    /**
     * 姓名
     */
    public String getFdStaffName() {
        return this.fdStaffName;
    }

    /**
     * 姓名
     */
    public void setFdStaffName(String fdStaffName) {
        this.fdStaffName = fdStaffName;
    }

    /**
     * 是否考察
     */
    public String getFdIsExplore() {
        return this.fdIsExplore;
    }

    /**
     * 是否考察
     */
    public void setFdIsExplore(String fdIsExplore) {
        this.fdIsExplore = fdIsExplore;
    }

    /**
     * 见习开始日期
     */
    public String getFdInternStartDate() {
        return this.fdInternStartDate;
    }

    /**
     * 见习开始日期
     */
    public void setFdInternStartDate(String fdInternStartDate) {
        this.fdInternStartDate = fdInternStartDate;
    }

    /**
     * 见习结束日期
     */
    public String getFdInternEndDate() {
        return this.fdInternEndDate;
    }

    /**
     * 见习结束日期
     */
    public void setFdInternEndDate(String fdInternEndDate) {
        this.fdInternEndDate = fdInternEndDate;
    }

    /**
     * 异动类型
     */
    public String getFdMoveType() {
        return this.fdMoveType;
    }

    /**
     * 异动类型
     */
    public void setFdMoveType(String fdMoveType) {
        this.fdMoveType = fdMoveType;
    }

    /**
     * 异动前职级
     */
    public String getFdBeforeRank() {
        return this.fdBeforeRank;
    }

    /**
     * 异动前职级
     */
    public void setFdBeforeRank(String fdBeforeRank) {
        this.fdBeforeRank = fdBeforeRank;
    }

    /**
     * 异动后职级
     */
    public String getFdAfterRank() {
        return this.fdAfterRank;
    }

    /**
     * 异动后职级
     */
    public void setFdAfterRank(String fdAfterRank) {
        this.fdAfterRank = fdAfterRank;
    }

    /**
     * 异动时间
     */
    public String getFdMoveDate() {
        return this.fdMoveDate;
    }

    /**
     * 异动时间
     */
    public void setFdMoveDate(String fdMoveDate) {
        this.fdMoveDate = fdMoveDate;
    }

    /**
     * 异动前一级部门
     */
    public String getFdBeforeFirstDeptName() {
        return this.fdBeforeFirstDeptName;
    }

    /**
     * 异动前一级部门
     */
    public void setFdBeforeFirstDeptName(String fdBeforeFirstDeptName) {
        this.fdBeforeFirstDeptName = fdBeforeFirstDeptName;
    }

    /**
     * 异动前二级部门
     */
    public String getFdBeforeSecondDeptName() {
        return this.fdBeforeSecondDeptName;
    }

    /**
     * 异动前二级部门
     */
    public void setFdBeforeSecondDeptName(String fdBeforeSecondDeptName) {
        this.fdBeforeSecondDeptName = fdBeforeSecondDeptName;
    }

    /**
     * 异动前三级部门
     */
    public String getFdBeforeThirdDeptName() {
        return this.fdBeforeThirdDeptName;
    }

    /**
     * 异动前三级部门
     */
    public void setFdBeforeThirdDeptName(String fdBeforeThirdDeptName) {
        this.fdBeforeThirdDeptName = fdBeforeThirdDeptName;
    }

    /**
     * 异动后一级部门
     */
    public String getFdAfterFirstDeptName() {
        return this.fdAfterFirstDeptName;
    }

    /**
     * 异动后一级部门
     */
    public void setFdAfterFirstDeptName(String fdAfterFirstDeptName) {
        this.fdAfterFirstDeptName = fdAfterFirstDeptName;
    }

    /**
     * 异动后二级部门
     */
    public String getFdAfterSecondDeptName() {
        return this.fdAfterSecondDeptName;
    }

    /**
     * 异动后二级部门
     */
    public void setFdAfterSecondDeptName(String fdAfterSecondDeptName) {
        this.fdAfterSecondDeptName = fdAfterSecondDeptName;
    }

    /**
     * 异动后三级部门
     */
    public String getFdAfterThirdDeptName() {
        return this.fdAfterThirdDeptName;
    }

    /**
     * 异动后三级部门
     */
    public void setFdAfterThirdDeptName(String fdAfterThirdDeptName) {
        this.fdAfterThirdDeptName = fdAfterThirdDeptName;
    }

    /**
     * 是否跨一级部门
     */
    public String getFdTransDept() {
        return this.fdTransDept;
    }

    /**
     * 是否跨一级部门
     */
    public void setFdTransDept(String fdTransDept) {
        this.fdTransDept = fdTransDept;
    }

    /**
     * 异动前直接上级
     */
    public String getFdBeforeLeaderId() {
        return this.fdBeforeLeaderId;
    }

    /**
     * 异动前直接上级
     */
    public void setFdBeforeLeaderId(String fdBeforeLeaderId) {
        this.fdBeforeLeaderId = fdBeforeLeaderId;
    }

    /**
     * 异动前直接上级
     */
    public String getFdBeforeLeaderName() {
        return this.fdBeforeLeaderName;
    }

    /**
     * 异动前直接上级
     */
    public void setFdBeforeLeaderName(String fdBeforeLeaderName) {
        this.fdBeforeLeaderName = fdBeforeLeaderName;
    }

    /**
     * 异动前部门
     */
    public String getFdBeforeDeptId() {
        return this.fdBeforeDeptId;
    }

    /**
     * 异动前部门
     */
    public void setFdBeforeDeptId(String fdBeforeDeptId) {
        this.fdBeforeDeptId = fdBeforeDeptId;
    }

    /**
     * 异动前部门
     */
    public String getFdBeforeDeptName() {
        return this.fdBeforeDeptName;
    }

    /**
     * 异动前部门
     */
    public void setFdBeforeDeptName(String fdBeforeDeptName) {
        this.fdBeforeDeptName = fdBeforeDeptName;
    }

    /**
     * 异动后部门
     */
    public String getFdAfterDeptId() {
        return this.fdAfterDeptId;
    }

    /**
     * 异动后部门
     */
    public void setFdAfterDeptId(String fdAfterDeptId) {
        this.fdAfterDeptId = fdAfterDeptId;
    }

    /**
     * 异动后部门
     */
    public String getFdAfterDeptName() {
        return this.fdAfterDeptName;
    }

    /**
     * 异动后部门
     */
    public void setFdAfterDeptName(String fdAfterDeptName) {
        this.fdAfterDeptName = fdAfterDeptName;
    }

    /**
     * 异动后直接上级
     */
    public String getFdAfterLeaderId() {
        return this.fdAfterLeaderId;
    }

    /**
     * 异动后直接上级
     */
    public void setFdAfterLeaderId(String fdAfterLeaderId) {
        this.fdAfterLeaderId = fdAfterLeaderId;
    }

    /**
     * 异动后直接上级
     */
    public String getFdAfterLeaderName() {
        return this.fdAfterLeaderName;
    }

    /**
     * 异动后直接上级
     */
    public void setFdAfterLeaderName(String fdAfterLeaderName) {
        this.fdAfterLeaderName = fdAfterLeaderName;
    }

    /**
     * 人员
     */
    @Override
    public String getFdPersonInfoId() {
        return this.fdPersonInfoId;
    }

    /**
     * 人员
     */
    @Override
    public void setFdPersonInfoId(String fdPersonInfoId) {
        this.fdPersonInfoId = fdPersonInfoId;
    }

    /**
     * 人员
     */
    @Override
    public String getFdPersonInfoName() {
        return this.fdPersonInfoName;
    }

    /**
     * 人员
     */
    @Override
    public void setFdPersonInfoName(String fdPersonInfoName) {
        this.fdPersonInfoName = fdPersonInfoName;
    }

    /**
     * 异动前岗位
     */
    public String getFdBeforePostIds() {
        return this.fdBeforePostIds;
    }

    /**
     * 异动前岗位
     */
    public void setFdBeforePostIds(String fdBeforePostIds) {
        this.fdBeforePostIds = fdBeforePostIds;
    }

    /**
     * 异动前岗位
     */
    public String getFdBeforePostNames() {
        return this.fdBeforePostNames;
    }

    /**
     * 异动前岗位
     */
    public void setFdBeforePostNames(String fdBeforePostNames) {
        this.fdBeforePostNames = fdBeforePostNames;
    }

    /**
     * 异动后岗位
     */
    public String getFdAfterPostIds() {
        return this.fdAfterPostIds;
    }

    /**
     * 异动后岗位
     */
    public void setFdAfterPostIds(String fdAfterPostIds) {
        this.fdAfterPostIds = fdAfterPostIds;
    }

    /**
     * 异动后岗位
     */
    public String getFdAfterPostNames() {
        return this.fdAfterPostNames;
    }

    /**
     * 异动后岗位
     */
    public void setFdAfterPostNames(String fdAfterPostNames) {
        this.fdAfterPostNames = fdAfterPostNames;
    }

    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
