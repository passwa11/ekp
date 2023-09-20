<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				com.landray.kmss.hr.staff.util.ProvinceUtil,
				com.landray.kmss.hr.staff.util.CitiesUtil,
				com.landray.kmss.hr.staff.util.AreasUtil"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@ page import="com.landray.kmss.common.forms.ExtendForm" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeConfig" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeField" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeFieldEnum" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="java.util.Map,java.util.Iterator,java.util.List,java.util.ArrayList" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffAlertIdcardSetting"%>
<%  HrStaffAlertIdcardSetting hrStaffAlertIdcardSetting = new HrStaffAlertIdcardSetting(); 
	   	String isIdcardValidate = hrStaffAlertIdcardSetting.getisIdcardValidate();
	   	request.setAttribute("isIdcardValidate", isIdcardValidate);
%>	

<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ hrStaffPersonInfoForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('hr-staff:hrStaffPersonInfo.create.title') } - ${ lfn:message('hr-staff:module.hr.staff') }"></c:out>	
			</c:when>
			<c:otherwise>
				${ hrStaffPersonInfoForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="head">
		<style>
			.hr_select{
				width: 50%;
				max-width: 80%;
			}
		</style>
		<link rel="stylesheet" href="../resource/css/common_view.css">
  	  <link rel="stylesheet" href="../resource/css/person_info.css">
  	  <style>
  	  	.com_qrcode, .com_gototop{
	  	  	display:none !important
	  	  }
  	  	.lui-personnel-file-baseInfo-main-content .inputsgl{
  	  		   border: 1px solid #b4b4b4;
  	  		   height:28px;
  	  		   border-radius:4px;
  	  	}
  	  	.newTable tr{
  	  		height:40px;
  	  		border-spacing:0px 10px;
  	  	}
  	  	.datawidth .inputselectsgl,.lui-custom-Prop .inputselectsgl{
  	  		width:205px!important;
  	  		height:30px!important;
  	  		border-radius:4px;
  	  		border:none;
  	  		background-color:#F7F7F8;
  	  	}
  	  	.inputwidth .inputsgl,.lui-custom-Prop .inputsgl{
  	  		height:28px;
  	  		width:200px!important;
  	  		background-color:#F7F7F8;
  	  		border:none;
  	  		border-radius:4px;
  	  	}
  	  	.hr_select{
  	  		width: 200px;
  	  		height:30px;
  	  		border-radius:4px;
  	  		background-color:#F7F7F8;
  	  		border:none!important;
  	  	}
  	  	.datawidth input,.lui-custom-Prop .inputselectsgl input{
  	  		background-color:#F7F7F8!important;
  	  	}
  	  	.lui-personnel-file-edit-text{
  	  		
  	  	}
  	  	.inputselectsgl{
  	  		width:200px!important;
  	  	}
		.newTable textarea{
			width:200px;
			border:none;
			margin:4px 0;
			background-color:#f7f7f7;
		}
		.lui_form_path_frame{
			max-width:1200px!important;
			
		}
		.lui_form_body{
		background-color:white;
		}
  	  </style>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" >
			<html:hidden property="fdId" value="${hrStaffPersonInfoForm.fdId}"/>
			<html:hidden property="fdOrgPersonId" value="${hrStaffPersonInfoForm.fdOrgPersonId }"/>
			<html:hidden property="fdOrgParentOrgId" value="${hrStaffPersonInfoForm.fdOrgParentOrgId }"/>
			<html:hidden property="fdOrgParentId" value="${hrStaffPersonInfoForm.fdOrgParentId }"/>
			<html:hidden property="fdOrgPostIds" value="${hrStaffPersonInfoForm.fdOrgPostIds }"/>
			<html:hidden property="fdStaffingLevelId" value="${hrStaffPersonInfoForm.fdStaffingLevelId }"/>
			<html:hidden property="fdOrgParentDeptName" value="${hrStaffPersonInfoForm.fdOrgParentDeptName }"/>
			<html:hidden property="fdStaffNo" value="${hrStaffPersonInfoForm.fdStaffNo }"/>
			<html:hidden property="fdTrialExpirationTime" value="${hrStaffPersonInfoForm.fdTrialExpirationTime }"/>
			<html:hidden property="fdEmploymentPeriod" value="${hrStaffPersonInfoForm.fdEmploymentPeriod }"/>
			<html:hidden property="fdStaffType" value="${hrStaffPersonInfoForm.fdStaffType }"/>
			<html:hidden property="fdMobileNo" value="${hrStaffPersonInfoForm.fdMobileNo }"/>
			<html:hidden property="fdEmail" value="${hrStaffPersonInfoForm.fdEmail }"/>
			<html:hidden property="fdOfficeLocation" value="${hrStaffPersonInfoForm.fdOfficeLocation }"/>
			<html:hidden property="fdWorkPhone" value="${hrStaffPersonInfoForm.fdWorkPhone }"/>
			<html:hidden property="fdEmergencyContact" value="${hrStaffPersonInfoForm.fdEmergencyContact }"/>
			<html:hidden property="fdEmergencyContactPhone" value="${hrStaffPersonInfoForm.fdEmergencyContactPhone }"/>
			<html:hidden property="fdOtherContact" value="${hrStaffPersonInfoForm.fdOtherContact }"/>
			<html:hidden property="fdStatus" value="${hrStaffPersonInfoForm.fdStatus }"/>
			<html:hidden property="fdStaffEntryId" value="${hrStaffPersonInfoForm.fdStaffEntryId }"/>
			<html:hidden property="fdTrialOperationPeriod" value="${hrStaffPersonInfoForm.fdTrialOperationPeriod }"/>
			<html:hidden property="fdEntryTime" value="${hrStaffPersonInfoForm.fdEntryTime }"/>
			<html:hidden property="fdPositiveTime" value="${hrStaffPersonInfoForm.fdPositiveTime }"/>
			<html:hidden property="fdActualPositiveTime" value="${hrStaffPersonInfoForm.fdActualPositiveTime }"/>
			<html:hidden property="fdPositiveRemark" value="${hrStaffPersonInfoForm.fdPositiveRemark }"/>
			<html:hidden property="fdLeaveTime" value="${hrStaffPersonInfoForm.fdLeaveTime }"/>
			<html:hidden property="fdIsRehire" value="${hrStaffPersonInfoForm.fdIsRehire }"/>
			<html:hidden property="fdRehireTime" value="${hrStaffPersonInfoForm.fdRehireTime }"/>
			<html:hidden property="fdStaffEntryName" value="${hrStaffPersonInfoForm.fdStaffEntryName }"/>
			<html:hidden property="fdLeaveApplyDate" value="${hrStaffPersonInfoForm.fdLeaveApplyDate }"/>
			<html:hidden property="fdLeavePlanDate" value="${hrStaffPersonInfoForm.fdLeavePlanDate }"/>
			<html:hidden property="fdLeaveSalaryEndDate" value="${hrStaffPersonInfoForm.fdLeaveSalaryEndDate }"/>
			<html:hidden property="fdLeaveReason" value="${hrStaffPersonInfoForm.fdLeaveReason }"/>
			<html:hidden property="fdLeaveRemark" value="${hrStaffPersonInfoForm.fdLeaveRemark }"/>
			<html:hidden property="fdNextCompany" value="${hrStaffPersonInfoForm.fdNextCompany }"/>
			<html:hidden property="fdNatureWork" value="${hrStaffPersonInfoForm.fdNatureWork }"/>
			<html:hidden property="fdReportLeaderId" value="${hrStaffPersonInfoForm.fdReportLeaderId }"/>
			<html:hidden property="fdWorkAddress" value="${hrStaffPersonInfoForm.fdWorkAddress }"/>
			<html:hidden property="fdLoginName" value="${hrStaffPersonInfoForm.fdLoginName }"/>
				<table class="staffInfo newTable" style="margin: 20px 0" >
					<tr>
						<!-- 姓名 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:text property="fdName" style="width:95%" showStatus="view"  className="inputsgl"/>
						</td>
						<!-- 曾用名 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNameUsedBefore" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:text showStatus="edit" property="fdNameUsedBefore" style="width:98%;" className="inputsgl" />
						</td>

					</tr>
<%-- 					<tr>
 						<!-- 工号 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffNo" />
						</td>
						<td width="35%">
							<xform:text property="fdStaffNo" showStatus="edit" style="width:95%;" required="true" validators="uniqueStaffNo" className="inputsgl" />
						</td> 
						<!-- 出生日期 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfBirth" />
						</td>
						<td width="35%">
							<xform:datetime property="fdDateOfBirth"   showStatus="edit" dateTimeType="date"></xform:datetime>
						</td>
					</tr> --%>
					<tr>
						<!-- 身份证号码 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIdCard" />
						</td>
						<td width="35%" class="inputwidth">
						<c:if test="${isIdcardValidate == true }">
							<xform:text property="fdIdCard"  style="width:99%;"  showStatus="edit" className="inputsgl" validators="validateCard"  required="true" onValueChange="idCardChange"/>
						</c:if>
							<c:if test="${isIdcardValidate == false }">
							<xform:text property="fdIdCard" style="width:99%;" showStatus="edit" className="inputsgl"  required="true" onValueChange="idCardChange"/>
						</c:if>
						</td>
						<!-- 性别 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSex" />
						</td>
						<td width="35%" class="inputwidth">
					       <sunbor:enums property="fdSex" enumsType="sys_org_person_sex" elementType="radio" />	
						</td>
					</tr>
					<tr>
						<!-- 出生日期 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfBirth" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime property="fdDateOfBirth" showStatus="edit" onValueChange="staffBirthDateChange" dateTimeType="date"></xform:datetime>
						</td>
						<td width="160"><bean:message key="hrStaffPersonInfo.fdAge" bundle="hr-staff"/></td>
		             	<td class="inputwidth">	   
		             		<span class="hr-person-info-fdage">${hrStaffPersonInfoForm.fdAge }</span>
		             	</td>
						
					</tr>
<%-- 					<tr>
						<!-- 到本单位时间 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTimeOfEnterprise" />
						</td>
						<td width="35%">
							<xform:datetime showStatus="edit" property="fdTimeOfEnterprise" dateTimeType="date" validators="timeOfEnterprise"></xform:datetime>
						</td>
						<!-- 试用到期时间 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialExpirationTime" />
						</td>
						<td width="35%">
							<xform:datetime property="fdTrialExpirationTime" dateTimeType="date" showStatus="edit" validators="trialExpirationTime"></xform:datetime>
						</td>
					</tr> --%>
<%-- 					<tr>
						<!-- 用工期限（年） -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmploymentPeriod" />
						</td>
						<td width="35%">
							<xform:text property="fdEmploymentPeriod" showStatus="edit" style="width:95%;" validators="digits min(0)" className="inputsgl" />
						</td>
						<!-- 试用期限 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialOperationPeriod" />
						</td>
						<td width="35%">
							<xform:text  showStatus="edit" property="fdTrialOperationPeriod" style="width:95%;" validators="digits min(0)" className="inputsgl" />
						</td>
					</tr> --%>
<%-- 					<tr>
						<!-- 入职日期-->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEntryTime" />
						</td>
						<td width="35%">
							<xform:datetime showStatus="edit" property="fdEntryTime" dateTimeType="date"></xform:datetime>
						</td>
						<!-- 转正日期 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPositiveTime" />
						</td>
						<td width="35%">
							<xform:datetime showStatus="edit" property="fdPositiveTime" dateTimeType="date"></xform:datetime>
						</td>
					</tr> --%>
<%-- 					<tr>
						<!-- 离职日期 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveTime" />
						</td>
						<td colspan="3">
							<xform:datetime showStatus="edit" property="fdLeaveTime" dateTimeType="date"></xform:datetime>
						</td>
					</tr> --%>
<%-- 					<tr>
						<!-- 人员类别 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffType" />
						</td>
						<td width="35%">
							<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdStaffType", request)%>
						</td>

					</tr> --%>
					<tr>
						<!-- 民族 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNation" />
						</td>
						<td width="35%" class="inputwidth">
							<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdNation", request)%>
						</td>
						<!-- 政治面貌 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPoliticalLandscape" />
						</td>
						<td width="35%" class="inputwidth">
							<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdPoliticalLandscape", request)%>
						</td>
					</tr>
					
					<tr>
						<!-- 最高学历 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestEducation" />
						</td>
						<td width="35%" class="inputwidth">
							<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHighestEducation", request)%>
						</td>
						<!-- 最高学位 -->
<!-- 						<td width="15%" class="td_normal_title"> -->
<%-- 							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestDegree" /> --%>
<!-- 						</td> -->
<!-- 						<td width="35%" class="inputwidth"> -->
<%-- 							<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHighestDegree", request)%> --%>
<!-- 						</td> -->
</td>
							<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkTime" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime required="true" showStatus="edit" property="fdWorkTime" dateTimeType="date" validators="workTime workTimeAndTrialExpirationTime" onValueChange="workTimeChange"></xform:datetime>
						</td>
					</tr>
					<tr>
						<!-- 婚姻情况 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdMaritalStatus" />
						</td>
						<td width="35%" class="inputwidth">
							<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdMaritalStatus", request)%>
						</td>
						<!-- 健康情况 -->
						
						<!-- 籍贯 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNativePlace" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:text showStatus="edit"  property="fdNativePlace" style="width:98%;" className="inputsgl" />
						</td>
					</tr>
					<tr>
							<!-- 现居地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPostalAddress" />
							</td>
						<td width="35%">
							<%=ProvinceUtil.buildProvinceHtml("fdPostalAddressProvinceId",request)%>
								<html:hidden property="fdPostalAddressProvinceName"/>
								<html:hidden property="fdPostalAddressCityName"/>
								<html:hidden property="fdPostalAddressAreaName"/>
								<html:hidden property="fdHomeAddressProvinceName"/>
								<html:hidden property="fdHomeAddressCityName"/>
								<html:hidden property="fdHomeAddressAreaName"/>
						</td>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
						<td width="35%" >
							<%=CitiesUtil.buildCitiesHtml("fdPostalAddressCityId","fdPostalAddressProvinceId",request)%>
						</td>
						</tr>
						<tr>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
						<td width="35%" >
							<%=AreasUtil.buildAreasHtml("fdPostalAddressAreaId","fdPostalAddressCityId", request)%>
						</td>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
							
							<td width="35%" class="inputwidth">
							
								<xform:text property="fdPostalAddress" showStatus="edit"  required="true" style="width:98%;" className="inputsgl" />
							</td>
							</tr>
							
						<tr>
							<!-- 现居地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHomeAddress" />
							</td>
						<td width="35%">
							<%=ProvinceUtil.buildProvinceHtml("fdHomeAddressProvinceId",request)%>
						</td>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
						<td width="35%">
							<%=CitiesUtil.buildCitiesHtml("fdHomeAddressCityId","fdHomeAddressProvinceId",request)%>
						</td>
						</tr>
						<tr>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
						<td width="35%">
							<%=AreasUtil.buildAreasHtml("fdHomeAddressAreaId","fdHomeAddressCityId", request)%>
						</td>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
							<td width="35%" class="inputwidth">
								<xform:text showStatus="edit" property="fdHomeAddress"  required="true" style="width:98%;" className="inputsgl" />
							</td>
							</tr>
							<tr>
							<!-- 现居地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdForeignLanguageLevel" />
							</td>
							<td width="35%" class="inputwidth">
								<xform:text showStatus="edit" property="fdForeignLanguageLevel" style="width:98%;" className="inputsgl" />
							</td>
							<!-- 籍贯 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsRetiredSoldier" />
							</td>
							<td width="35%" class="inputwidth">
							<xform:select property="fdIsRetiredSoldier" showStatus="edit" required="true">
							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdYesNo"></xform:enumsDataSource>
							</xform:select >
							</td>
				</tr>
					<tr>
						
						<!-- 户口性质 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAccountProperties" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:text showStatus="edit"  property="fdAccountProperties" style="width:98%;" className="inputsgl" />
						</td>
						<!-- 户口所在地 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdRegisteredResidence" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:text showStatus="edit"  property="fdRegisteredResidence" style="width:98%;" className="inputsgl" />
						</td>
<!-- 						<tr> -->
<!-- 						户口所在派出所 -->
<!-- 						<td width="15%" class="td_normal_title"> -->
<%-- 							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResidencePoliceStation" /> --%>
<!-- 						</td> -->
<!-- 						<td width="35%" class="inputwidth"> -->
<%-- 							<xform:text showStatus="edit"  property="fdResidencePoliceStation" style="width:98%;" className="inputsgl" required="true"/> --%>
						
<!-- 					</tr> -->
					
					<tr>
						<!-- 本企业工龄 -->
						<td width="15%" class="td_normal_title">
						<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkingYears"/>
						</td>
						<td width="35%" class="">
							<div class="input">
								<xform:text  showStatus="edit" property="fdWorkingYearsYear" onValueChange="workYearChange" validators="number digits min(0)" className="workYear"></xform:text><bean:message key="resource.period.type.year.name"/>
								<xform:text  showStatus="edit" property="fdWorkingYearsMonth" onValueChange="workYearChange" validators="number digits min(0) max(11)" className="workYear"></xform:text><bean:message key="resource.period.type.month.name"/>
							</div>
							<html:hidden property="fdWorkingYearsDiff"/>
							<html:hidden property="fdWorkingYearsValue"/>
							<html:hidden property="fdWorkingYears"/>
						</td>
					   <!-- 连续工龄 -->
						<td width="15%" class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdUninterruptedWorkTime" /></td>
						<td width="35%" class="">
							<div class="input">
								<xform:text  showStatus="edit" property="fdUninterruptedWorkTimeYear" onValueChange="uninterruptedWorkTimeChange" validators="number digits min(0)" className="workYear"></xform:text><bean:message key="resource.period.type.year.name"/>
								<xform:text  showStatus="edit" property="fdUninterruptedWorkTimeMonth" onValueChange="uninterruptedWorkTimeChange" validators="number digits min(0) max(11)" className="workYear"></xform:text><bean:message key="resource.period.type.month.name"/>
							</div>
							
							<html:hidden property="fdWorkTimeDiff"/>
							<html:hidden property="fdUninterruptedWorkTimeValue"/>
							<html:hidden property="fdUninterruptedWorkTime"/>
						</td>
	            	</tr>
					<%-- 引入动态属性 --%>
				<tr>
						<td class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.att" />
						</td>
						<!--附件  -->
						<td width="85%" colspan="3">
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="hrStaffPerson" />
							</c:import>
						</td>
					</tr>
				</table>
				<%
				Object form = TagUtils.getFormBean(request);
				if(form instanceof ExtendForm) {
					ExtendForm extendForm = (ExtendForm)form;
					String modelName = extendForm.getModelClass().getName();
					Map<String, String> customPropMap = extendForm.getCustomPropMap();
					DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
					if(dynamicConfig != null) {
						// 复制一份出来操作，否则会删除原有的数据
						List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
						for(int i=0; i<_list.size(); i++) {
							DynamicAttributeField field = _list.get(i);
							String __fieldName = "customPropMap(" + field.getFieldName() + ")";
							String __fieldText = field.getFieldTextByCurrentLang();
							
							pageContext.setAttribute("__fieldName", __fieldName);
							pageContext.setAttribute("__fieldText", __fieldText);
					%>
						<%-- <html:hidden property="${__fieldName }"></html:hidden> --%>
					<%
								}
							}
						}
					%>
		
			
		</html:form>
		<%@ include file="/hr/staff/hr_staff_person_info/hrStaffPersonInfo_edit_script.jsp"%>
		<script>
			//根据身份证号自动填充出生日期
			function idCardChange(value) {
				var sexcode = "";
				var birth = "";
				var flag = false;
				if (value.length == 15) {
					sexcode = value.substring(14, 15);
					birth ="19" + value.substring(6, 8) + "-" + value.substring(8, 10) + "-" + value.substring(10, 12);
					flag = true;
				}
				if (value.length == 18) {
					sexcode = value.substring(16, 17);
					birth = value.substring(6, 10) + "-" + value.substring(10, 12) + "-" + value.substring(12, 14);
					flag = true;
				}
				if(flag){
					var idefctdate = new Date(Date.parse(birth.replace(/-/g, "/"))).valueOf();
					if(!isNaN(idefctdate)){
						var fdDateOfBirth = $("input[name='fdDateOfBirth']")[0].value;
						if(fdDateOfBirth == ""){
							$("input[name='fdDateOfBirth']").val(birth);
							staffBirthDateChange(birth);
						}
					}
					//偶数为女性，奇数为男性
					if ((sexcode & 1) == 0) {
						$("input[name='fdSex'][value='F']").prop("checked",true);
					} else {
						$("input[name='fdSex'][value='M']").prop("checked",true);
					}
				}
			}
			window.submitBtn = function (){		
				// Com_Submit(document.hrStaffPersonInfoForm, 'update');

				 if(_validator.validate()){
				 	 //附件列表值获取
					 if($("#attachmentObject_hrStaffPerson_content_div")){
						 attachmentObject_hrStaffPerson.updateInput();
					 }
					 var filed = decodeURI($(document.hrStaffPersonInfoForm).serialize());
					 var dataArr = filed.split("&");
					 var data ={}
					 $.each(dataArr,function(index,item){
						 var itemData = item.split("=")
						 data[itemData[0]]= itemData.length>1?decodeURIComponent(itemData[1]):""
					 })
					 data['type'] = "basic"

	 				 $.ajax({
						 url:"<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=updatePersonInfo&fdId=${hrStaffPersonInfoForm.fdId}",
						 method:'post',
						 data:data,
						 success:function(res){
							 if(res.status){
								 window.parent.dialogObj.hide()
								 window.parent.location.reload();
							 }
						 }
					 }) 
				 }
				 <%-- var filed = decodeURI($(document.hrStaffPersonInfoForm).serialize());
				 var dataArr = filed.split("&");
				 var data ={}
				 $.each(dataArr,function(index,item){
					 var itemData = item.split("=")
					 data[itemData[0]]= itemData.length>1?itemData[1]:""
				 })
				 data['type'] = "basic"
				if(_validator.validate()){
	 				 $.ajax({
						 url:"<%=request.getContextPath()%>/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=updatePersonInfo&fdId=${hrStaffPersonInfoForm.fdId}",
						 method:'post',
						 data:data,
						 success:function(res){
							 if(res.status){
								 window.parent.dialogObj.hide()
								 window.parent.location.reload();
							 }else{
								 window.parent.dialogObj.hide()
							 }
						 }
					 })
				} --%>
			}

	  		function staffBirthDateChange (value){
	  			var birthYear = value.split("-").shift();
	  			var nowYear = new Date().getFullYear();
	  			$(".hr-person-info-fdage").html(nowYear-birthYear);
	  		}
	  		function setCities(_this){
				var text = _this.options[_this.selectedIndex].text;
				var name = _this.name;
				$("input[name='"+name.slice(0,-2)+'Name'+"']").val(text);
				var sel = ''+$(_this).parent().next().next().find('select')[0].name;
				var json = {};
				json[name] = $(_this).val();
				json ["prevFieldName"] = name;
				json ["fieldName"] = sel;
				console.log(name);
				$.ajax({
	                url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=buildCitiesHtml',
	                data: json,
	                dataType: 'json',
	                type: 'POST',
	                async:false,
	                success: function(data) {
						if(data){
							if(data.html){
								$(_this).parent().next().next().empty();
								$(_this).parent().next().next().append(data.html);
							}
						}
	                },
	                error: function(req) {
						$("#staffLevingBox").empty();
	                }
	            });
			}
			function setAreasValue(_this){
				var name=_this.name; 
				$("input[name='"+name.slice(0,-2)+'Name'+"']").val(_this.options[_this.selectedIndex].text);
			}
			function setAreas(_this){
				var name=_this.name; 
				$("input[name='"+name.slice(0,-2)+'Name'+"']").val(_this.options[_this.selectedIndex].text);
				var sel = $(_this).parent().parent().next().children().next().find('select')[0].name;
				var json = {};
				json[name] = $(_this).val();
				json ["prevFieldName"] = name;
				json ["fieldName"] = sel;
				
				console.log(json);
				$.ajax({
	                url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=buildAreasHtml',
	                data: json,
	                dataType: 'json',
	                type: 'POST',
	                async:false,
	                success: function(data) {
						if(data){
							if(data.html){
								$(_this).parent().parent().next().children(":first").next().empty();
								$(_this).parent().parent().next().children(":first").next().append(data.html);
							}
						}
	                },
	                error: function(req) {
						$("#staffLevingBox").empty();
	                }
	            });
			}
		</script>
	</template:replace>
</template:include>