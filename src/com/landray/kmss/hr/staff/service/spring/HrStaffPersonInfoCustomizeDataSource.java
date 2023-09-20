package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.web.taglib.xform.ICustomizeDataSource;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuyang
 * @date 2022-10-04
 */
public class HrStaffPersonInfoCustomizeDataSource implements ICustomizeDataSource {
    @Override
    public Map<String, String> getOptions() {
        Map<String, String> returnMap = new LinkedHashMap(15);
//        SysDictModel sysDataDict = SysDataDict.getInstance().getModel(HrStaffPersonInfo.class.getName());
//        Map<String, SysDictCommonProperty> map = sysDataDict.getPropertyMap();
//        map.forEach((key,value)->{
//            if(StringUtil.isNotNull(value.getMessageKey())){
//                String s = ResourceUtil.getString(value.getMessageKey());
//                String k = key;
//                if(value.getType().startsWith("com")){
//                    k = key + ".fdName";
//                }
//                if(StringUtil.isNotNull(s) && value.isCanDisplay()){
//                    returnMap.put(k, s);
//                }
//
//            }
//        });
        returnMap.put("fdOrgParent.fdName","部门名称");
        returnMap.put("fdFirstLevelDepartment.fdName","一级部门");
        returnMap.put("fdSecondLevelDepartment.fdName","二级部门");
        returnMap.put("fdThirdLevelDepartment.fdName","三级部门");
        returnMap.put("fdStaffNo","工号");
        returnMap.put("fdName","姓名");
        returnMap.put("fdStaffType","人员类别");
        returnMap.put("fdStatus","人员状态");
        returnMap.put("fdOrgPosts.fdName","岗位名称");
        returnMap.put("fdOrgRank.fdName","职级");
        returnMap.put("fdStaffingLevel.fdName","职务");
        returnMap.put("fdCategory","职类");
        returnMap.put("fdAffiliatedCompany","所属公司");
        returnMap.put("fdEntryTime","入职日期");
        returnMap.put("fdProposedEmploymentConfirmationDate","拟转正日期");
        returnMap.put("fdPositiveTime","转正日期");
        returnMap.put("fdReportLeader.fdName","直接上级");
        returnMap.put("fdDirectSuperiorJobNumber","直接上级工号");
        returnMap.put("fdDepartmentHead.fdName","部门负责人");
        returnMap.put("fdHeadOfFirstLevelDepartment.fdName","一级部门负责人");
        returnMap.put("fdPrincipalIdentification","负责人标识");
        returnMap.put("fdPlaceOfInsurancePayment","保险缴纳地");
//        returnMap.put("fdSocialInsuranceCompany","社保参保公司");
//        returnMap.put("fdProvidentFundInsuranceCompany","公积金参保公司");
        returnMap.put("fdFixedShift","固定班次");
        returnMap.put("fdTimeCardNo","考勤卡号");
        returnMap.put("fdIsAttendance","是否考勤");
        returnMap.put("fdNameUsedBefore","英文名");
        returnMap.put("fdIdCard","身份证号码");
        returnMap.put("fdSex","性别");
        returnMap.put("fdDateOfBirth","出生日期");
        returnMap.put("fdAge","年龄");
        returnMap.put("fdNation","民族");
        returnMap.put("fdPoliticalLandscape","政治面貌");
        returnMap.put("fdHighestEducation","最高学历");
        returnMap.put("fdHighestDegree","最高学位");
        returnMap.put("fdMaritalStatus","婚姻状况");
        returnMap.put("fdPostalAddress","通讯地址");
        returnMap.put("fdHomeAddress","家庭住址");
        returnMap.put("fdNativePlace","籍贯");
        returnMap.put("fdAccountProperties","户口性质");
        returnMap.put("fdRegisteredResidence","户口所在地");
        returnMap.put("fdWorkTime","参加工作日期");
        returnMap.put("fdWorkingYears","本企业司龄");
        returnMap.put("fdUninterruptedWorkTime","工龄");
        returnMap.put("fdResidencePoliceStation","身份证签发机关");
        returnMap.put("fdForeignLanguageLevel","外语级别");
        returnMap.put("fdIsRetiredSoldier","是否退役军人");
        returnMap.put("fdProbationPeriod","试用期限（月）");
        returnMap.put("fdCostAttribution","费用归属");
        returnMap.put("fdReasonForResignation","离职原因");
        returnMap.put("fdResignationDate","离职日期");
        returnMap.put("fdResignationType","离职类型");
        returnMap.put("fdOAAccount","OA账户");
        returnMap.put("fdIsOAUser","是否OA用户");
        returnMap.put("fdResignationDate","离职时间");
        returnMap.put("fdOfficeArea","办公所属区域");
        returnMap.put("fdOfficeLocation","办公详细地址");
        returnMap.put("fdWorkPhone","办公总机");
        returnMap.put("fdOfficeLine","办公直线");
        returnMap.put("fdOfficeExtension","办公分机");
        returnMap.put("fdEmail","公司邮箱");
        returnMap.put("fdPrivateMailbox","私人邮箱");
        returnMap.put("fdMobileNo","手机号码");
        returnMap.put("fdEmergencyContact","紧急联系人姓名");
        returnMap.put("fdEmergencyContactPhone","紧急联系人电话");
        returnMap.put("fdRelationsOfEmergencyContactAndEmployee","紧急联系人与员工关系");
        returnMap.put("fdEmergencyContactAddress","紧急联系人地址");
        returnMap.put("fdContType","合同类型");
        returnMap.put("fdBeginDate","合同开始日期");
        returnMap.put("fdEndDate","合同结束日期");
        returnMap.put("fdContractPeriod","合同期限");
        returnMap.put("fdContractUnit","合同所属单位");
        returnMap.put("fdLoginName","系统账号");
        return returnMap;
    }

    @Override
    public String getDefaultValue() {
        return "";
    }
}
