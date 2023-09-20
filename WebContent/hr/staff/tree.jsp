<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/tree.jsp">
    <template:replace name="content">
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.hr.staff" bundle="hr-staff"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n11, n12, defaultNode;
	n1 =  LKSTree.treeRoot;
	
	<kmss:authShow roles="ROLE_HRSTAFF_BACKGROUND">
	n1.AppendURLChild('<bean:message bundle="hr-staff" key="py.MoRenQuanXianKaiGuan"/>','<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.staff.model.HrStaffConfig"/>');
	
	n1.AppendURLChild('<bean:message bundle="hr-staff" key="py.NowHrNumber"/>','<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.staff.model.HrNumberConfig"/>');
	<!-- 档案授权 -->
	n2 = n1.AppendURLChild(
		"<bean:message key="table.hrStaffFileAuthor" bundle="hr-staff"/>"
		
	);
	n2.AppendBeanData(
	"organizationTree&parent=!{value}&orgType="+(ORG_TYPE_ORGORDEPT)+"&sys_page=true&fdIsExternal=false",
	"<c:url value="/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do?method=config"/>&parentId=!{value}", 
	null, true);
	
	<!-- 个人信息设置 -->
	defaultNode = n1.AppendURLChild(
		"<bean:message key="hr.staff.tree.info.setting" bundle="hr-staff"/>"
		
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hr.staff.tree.category" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdStaffType"/>"
	);					
	defaultNode.AppendURLChild(
		"<bean:message key="hr.staff.tree.healthy" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdHealth"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hr.staff.tree.nation" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdNation"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hr.staff.tree.politicalStatus" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdPoliticalLandscape"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hr.staff.tree.education" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdHighestEducation"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hr.staff.tree.degree" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdHighestDegree"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hr.staff.tree.maritalStatus" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdMaritalStatus"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hrStaffPersonInfo.fdNatureWork" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdNatureWork"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hrStaffPersonInfo.fdWorkAddress" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdWorkAddress"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hrStaffPersonExperience.fdBonusMalusType" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdBonusMalusType"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hrStaffPersonInfo.fdLeaveReason" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdLeaveReason"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hrStaffPersonInfo.fdResignationType" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdResignationType"/>"
	);
	defaultNode.AppendURLChild(
		"<bean:message key="hrStaffPersonInfo.fdAffiliatedCompany" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info_setting/hrStaffInfoSetNew.do?method=initView&i.fdType=fdAffiliatedCompany"/>"
	);
	
	n1.AppendURLChild("<bean:message key="table.hrStaffContractType" bundle="hr-staff"/>","<c:url value="/hr/staff/hr_staff_contract_type/index.jsp"/>");
	
	<kmss:authShow roles="ROLE_HRSTAFF_WARNING">
	<!-- 提醒预警 -->
	n2 = n1.AppendURLChild(
		"<bean:message key="hr.staff.tree.alert.warning" bundle="hr-staff"/>"
	);
	<!-- 生日提醒 -->
	n2.AppendURLChild(
		"<bean:message key="hr.staff.tree.birthday.reminder" bundle="hr-staff"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.staff.model.HrStaffAlertWarningBirthday" />"
	);
	<!-- 合同到期提醒 -->
	n2.AppendURLChild(
		"<bean:message key="hr.staff.tree.contract.expiration.reminder" bundle="hr-staff"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.staff.model.HrStaffAlertWarningContract" />"
	);
	<!-- 试用到期提醒 -->
	n2.AppendURLChild(
		"<bean:message key="hr.staff.tree.trial.expiration.reminder" bundle="hr-staff"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.staff.model.HrStaffAlertWarningTrial" />"
	);
	</kmss:authShow>
	
	<kmss:authShow roles="ROLE_SYSZONE_ADMIN"> 
	<!-- 员工黄页配置 -->
	n3 = n1.AppendURLChild(
		"<bean:message key="hr.staff.tree.staff.config.pages" bundle="hr-staff"/>"
	);
	<!-- 员工信息 -->
	n4 = n3.AppendURLChild(
		"<bean:message key="hr.staff.tree.employee.info" bundle="hr-staff"/>",
		"<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=listPersons" />"
	);
	n5 = n4.AppendOrgData("organizationTree&fdId=!{value}",
		"<c:url value="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=listPersons&parentId=!{value}"/>"
	);
	<!-- 隐私设置 -->
	n3.AppendURLChild(
		"<bean:message key="hr.staff.tree.privacy.settings" bundle="hr-staff"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.staff.model.HrStaffPrivateConfig" />"
	);
	</kmss:authShow>
	<kmss:authShow roles="ROLE_SYSZONE_ADMIN"> 
		<!-- 身份验证设置 -->
	n4 = n1.AppendURLChild(
		"<bean:message key="hr.staff.tree.staff.idcard.setting" bundle="hr-staff"/>",
		"<c:url value="/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.hr.staff.model.HrStaffAlertIdcardSetting" />"
	);
	</kmss:authShow>
	
	</kmss:authShow>
	
	<kmss:auth requestURL="/sys/property/custom_field/index.jsp?modelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo">
	//========== 自定义设置 ==========
	n11 = n1.AppendURLChild(
		"<bean:message key="custom.field.settings" bundle="sys-property" />"
	);
	
	//========== 人员卡片自定义 ==========
	n11.AppendURLChild(
		"<bean:message key="custom.field.settings.personInfo" bundle="hr-staff"/>",
		"<c:url value="/sys/property/custom_field/index.jsp?modelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo"/>"
	);
	</kmss:auth>
	
	<kmss:authShow roles="ROLE_HRSTAFF_SEARCH">
	n12 = n1.AppendURLChild(
		"<bean:message key="settings.personInfo.search" bundle="hr-staff"/>"
	);
	
	<!-- 搜索设置（员工） -->
	n12.AppendURLChild(
		"<bean:message key="settings.personInfo.search.setting" bundle="hr-staff"/>",
		"<c:url value="/sys/search/sys_search_main/index.jsp?fdModelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo"/>"
	);
	
	<!-- 数据查询（员工） -->
	n12.AppendURLChild(
		"<bean:message key="settings.personInfo.search.data" bundle="hr-staff"/>",
		"<c:url value="/sys/search/tree.jsp?fdModelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo&canClose=false"/>",
		2
	);
	</kmss:authShow>
	
	<!-- 员工信息导入 -->
	<kmss:authShow roles="ROLE_HRSTAFF_TRANSPORT">
	n1.AppendURLChild(
		"<bean:message key="settings.personInfo.import" bundle="hr-staff"/>",
		"<c:url value="/sys/transport/sys_transport_import/index.jsp?fdModelName=com.landray.kmss.hr.staff.model.HrStaffPersonInfo"/>"
	);
	</kmss:authShow>
	
	LKSTree.ExpandNode(n2);
	LKSTree.ExpandNode(n3);
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	LKSTree.ClickNode(defaultNode);
}
 </template:replace>
</template:include>