package com.landray.kmss.hr.staff.model;

import java.util.ArrayList;
import java.util.List;
import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.model.IAttachment;
import java.util.Date;
import com.landray.kmss.hr.staff.forms.HrStaffMoveRecordForm;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;

/**
  * 异动信息
  */
public class HrStaffMoveRecord extends BaseModel implements IAttachment {

    private static ModelToFormPropertyMap toFormPropertyMap;

    private String fdStaffNumber;
    private String fdFlag;
    
    public String getFdFlag() {
		return fdFlag;
	}

	public void setFdFlag(String fdFlag) {
		this.fdFlag = fdFlag;
	}

	private String fdStaffName;

    private String fdIsExplore;

    private Date fdInternStartDate;

    private Date fdInternEndDate;

    private String fdMoveType;

    private String fdBeforeRank;

    private String fdAfterRank;

    private Date fdMoveDate;

    private String fdBeforeFirstDeptName;

    private String fdBeforeSecondDeptName;

    private String fdBeforeThirdDeptName;

    private String fdAfterFirstDeptName;

    private String fdAfterSecondDeptName;

    private String fdAfterThirdDeptName;

    /**
     * 是否跨一级部门
     * 1:表示跨一级部门异动，0:表示不跨一级部门异动
     */
    private String fdTransDept = "0";

    private SysOrgElement fdBeforeLeader;

    private SysOrgElement fdBeforeDept;

    private SysOrgElement fdAfterDept;

    private SysOrgElement fdAfterLeader;

    private HrStaffPersonInfo fdPersonInfo;

    private List<SysOrgPost> fdBeforePosts = new ArrayList<SysOrgPost>();

    private List<SysOrgPost> fdAfterPosts = new ArrayList<SysOrgPost>();

    /**
     * 异动生效时间
     */
    private Date fdEffectiveDate;

    public Date getFdEffectiveDate() {
        return fdEffectiveDate;
    }

    public void setFdEffectiveDate(Date fdEffectiveDate) {
        this.fdEffectiveDate = fdEffectiveDate;
    }

    private AutoHashMap attachmentForms = new AutoHashMap(AttachmentDetailsForm.class);

    @Override
    public Class<HrStaffMoveRecordForm> getFormClass() {
        return HrStaffMoveRecordForm.class;
    }

    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
            toFormPropertyMap.put("fdInternStartDate", new ModelConvertor_Common("fdInternStartDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdInternEndDate", new ModelConvertor_Common("fdInternEndDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdMoveDate", new ModelConvertor_Common("fdMoveDate").setDateTimeType(DateUtil.TYPE_DATE));
            toFormPropertyMap.put("fdBeforeLeader.fdName", "fdBeforeLeaderName");
            toFormPropertyMap.put("fdBeforeLeader.fdId", "fdBeforeLeaderId");
            toFormPropertyMap.put("fdBeforeDept.fdName", "fdBeforeDeptName");
            toFormPropertyMap.put("fdBeforeDept.fdId", "fdBeforeDeptId");
            toFormPropertyMap.put("fdAfterDept.fdName", "fdAfterDeptName");
            toFormPropertyMap.put("fdAfterDept.fdId", "fdAfterDeptId");
            toFormPropertyMap.put("fdAfterLeader.fdName", "fdAfterLeaderName");
            toFormPropertyMap.put("fdAfterLeader.fdId", "fdAfterLeaderId");
            toFormPropertyMap.put("fdPersonInfo.fdName", "fdPersonInfoName");
            toFormPropertyMap.put("fdPersonInfo.fdId", "fdPersonInfoId");
            toFormPropertyMap.put("fdBeforePosts", new ModelConvertor_ModelListToString("fdBeforePostIds:fdBeforePostNames", "fdId:fdName"));
            toFormPropertyMap.put("fdAfterPosts", new ModelConvertor_ModelListToString("fdAfterPostIds:fdAfterPostNames", "fdId:fdName"));
        }
        return toFormPropertyMap;
    }

    public void recalculateFields() {
        super.recalculateFields();
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
    public Date getFdInternStartDate() {
        return this.fdInternStartDate;
    }

    /**
     * 见习开始日期
     */
    public void setFdInternStartDate(Date fdInternStartDate) {
        this.fdInternStartDate = fdInternStartDate;
    }

    /**
     * 见习结束日期
     */
    public Date getFdInternEndDate() {
        return this.fdInternEndDate;
    }

    /**
     * 见习结束日期
     */
    public void setFdInternEndDate(Date fdInternEndDate) {
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
    public Date getFdMoveDate() {
        return this.fdMoveDate;
    }

    /**
     * 异动时间
     */
    public void setFdMoveDate(Date fdMoveDate) {
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
     * 1:表示跨一级部门异动，0:表示不跨一级部门异动
     */
    public void setFdTransDept(String fdTransDept) {
        this.fdTransDept = fdTransDept;
    }

    /**
     * 异动前直接上级
     */
    public SysOrgElement getFdBeforeLeader() {
        return this.fdBeforeLeader;
    }

    /**
     * 异动前直接上级
     */
    public void setFdBeforeLeader(SysOrgElement fdBeforeLeader) {
        this.fdBeforeLeader = fdBeforeLeader;
    }

    /**
     * 异动前部门
     */
    public SysOrgElement getFdBeforeDept() {
        return this.fdBeforeDept;
    }

    /**
     * 异动前部门
     */
    public void setFdBeforeDept(SysOrgElement fdBeforeDept) {
        this.fdBeforeDept = fdBeforeDept;
    }

    /**
     * 异动后部门
     */
    public SysOrgElement getFdAfterDept() {
        return this.fdAfterDept;
    }

    /**
     * 异动后部门
     */
    public void setFdAfterDept(SysOrgElement fdAfterDept) {
        this.fdAfterDept = fdAfterDept;
    }

    /**
     * 异动后直接上级
     */
    public SysOrgElement getFdAfterLeader() {
        return this.fdAfterLeader;
    }

    /**
     * 异动后直接上级
     */
    public void setFdAfterLeader(SysOrgElement fdAfterLeader) {
        this.fdAfterLeader = fdAfterLeader;
    }

    /**
     * 人员
     */
    public HrStaffPersonInfo getFdPersonInfo() {
        return this.fdPersonInfo;
    }

    /**
     * 人员
     */
    public void setFdPersonInfo(HrStaffPersonInfo fdPersonInfo) {
        this.fdPersonInfo = fdPersonInfo;
    }

    /**
     * 异动前岗位
     */
    public List<SysOrgPost> getFdBeforePosts() {
        return this.fdBeforePosts;
    }

    /**
     * 异动前岗位
     */
    public void setFdBeforePosts(List<SysOrgPost> fdBeforePosts) {
        this.fdBeforePosts = fdBeforePosts;
    }

    /**
     * 异动后岗位
     */
    public List<SysOrgPost> getFdAfterPosts() {
        return this.fdAfterPosts;
    }

    /**
     * 异动后岗位
     */
    public void setFdAfterPosts(List<SysOrgPost> fdAfterPosts) {
        this.fdAfterPosts = fdAfterPosts;
    }

    @Override
    public AutoHashMap getAttachmentForms() {
        return attachmentForms;
    }
}
