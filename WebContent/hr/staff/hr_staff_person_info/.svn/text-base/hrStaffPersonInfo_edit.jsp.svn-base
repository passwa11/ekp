<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				com.landray.kmss.hr.staff.util.ProvinceUtil,
				com.landray.kmss.hr.staff.util.CitiesUtil,
				com.landray.kmss.hr.staff.util.AreasUtil,
				com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting,
				com.landray.kmss.hr.staff.model.HrStaffAlertIdcardSetting"
%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>
<% 
		HrStaffAlertIdcardSetting hrStaffAlertIdcardSetting = new HrStaffAlertIdcardSetting(); 
	   	String isIdcardValidate = hrStaffAlertIdcardSetting.getisIdcardValidate();
	   	request.setAttribute("isIdcardValidate", isIdcardValidate);
%>	
	<template:include ref="default.edit" sidebar="auto" showQrcode="false">
	<template:replace name="title">
		<c:if test="${param.method eq 'add'}">
			 ${ lfn:message('hr-staff:module.hr.staff') }
		</c:if>
		<c:if test="${param.method eq 'edit'}">
			${ hrStaffPersonInfoForm.fdName } - ${ lfn:message('hr-staff:module.hr.staff') }
		</c:if>		
	</template:replace>
	<template:replace name="head">
	  <link rel="stylesheet" href="../resource/css/common_view.css">
  	  <link rel="stylesheet" href="../resource/css/person_info.css">
  	  <style>
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
		select[name=fdOrgRankId]{
			width:200px;
		}
  	  </style>
  	<script>
		Com_IncludeFile('plugin.js');
	</script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<c:if test="${ hrStaffPersonInfoForm.method_GET == 'add' }">
				<ui:button text="${lfn:message('button.submit')}" 
					onclick="onSubmitForm(document.hrStaffPersonInfoForm, 'save');" order="1">
				</ui:button>
			</c:if>
			<c:if test="${ hrStaffPersonInfoForm.method_GET == 'edit' }">
				<ui:button text="${lfn:message('button.save')}" 
					onclick="onSubmitUpdate(document.hrStaffPersonInfoForm, 'update');" order="1">
				</ui:button>
			</c:if>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" ></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.HrStaffPersonInfo') }"></ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content"> 
	  <div class="lui-personnel-file">
	    <!-- main -->
	    <div class="lui-personnel-file-baseInfo-main">
	      <div class="lui-personnel-file-baseInfo-main-aside">
	        <ul class="titleList">
	          <li class="staffInfoLi"><a href="javascript:void(0);"><bean:message key="mobile.hr.staff.view.2" bundle="hr-staff"/></a></li>
	          	<c:if test="${(hrStaffPersonInfoForm.fdStatus != 'leave')&&(hrStaffPersonInfoForm.fdStatus != 'retire')&&(hrStaffPersonInfoForm.fdStatus != 'dismissal')}">
	          		<li class="dutyInfoLi"><a href="javascript:void(0);"><bean:message key="mobile.hr.staff.view.4" bundle="hr-staff"/></a></li>
	          	</c:if>
         		<c:if test="${(hrStaffPersonInfoForm.fdStatus == 'leave' )||( hrStaffPersonInfoForm.fdStatus =='retire')||(hrStaffPersonInfoForm.fdStatus =='dismissal')}">
	          		<li class="departureInfoLi"><a href="javascript:void(0);"><bean:message key="mobile.hr.staff.view.5" bundle="hr-staff"/></a></li>
	        	</c:if>
	          <li class="staffStatusLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonInfo.fdStatus" bundle="hr-staff"/></a></li>
	          <li class="contactInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonInfo.contactInfo" bundle="hr-staff"/></a></li>
	          <c:if test="${hrStaffPersonInfoForm.method_GET=='edit'}">
		          <kmss:authShow roles="ROLE_HRSTAFF_EXPERIENCE">
			          <li class="familyInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPerson.family" bundle="hr-staff"/></a></li>
			          <li class="onDutyLi"><a href="javascript:void(0);"><bean:message key="hrStaffTrackRecord.record" bundle="hr-staff"/></a></li>
			          <li class="personnelExperienceLi"><a href="javascript:void(0);"><bean:message key="table.hrStaffPersonExperience" bundle="hr-staff"/></a></li>
			          <li class="contractInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonExperience.type.contract" bundle="hr-staff"/></a></li>
			          <li class="rewardAndPunishmentInfoLi"><a href="javascript:void(0);"><bean:message key="hrStaffPersonExperience.type.bonusMalus" bundle="hr-staff"/></a></li>
		          </kmss:authShow>
		          <kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
		          	<li class="personnelSalaryLi"><a href="javascript:void(0);"><bean:message key="hr.staff.nav.benefits" bundle="hr-staff"/></a></li>
		          </kmss:authShow>
		          <kmss:authShow roles="ROLE_HRSTAFF_ATTENDANCE">
		          	<li class="personnelHolidayLi"><a href="javascript:void(0);"><bean:message key="hr.staff.nav.attendance.management" bundle="hr-staff"/></a></li>
		          </kmss:authShow>
		          <kmss:authShow roles="ROLE_HRSTAFF_LOG_VIEW">
		          	<li class="staffDynamicLi"><a href="javascript:void(0);"><bean:message key="porlet.employee.dynamic" bundle="hr-staff"/></a></li>
		          </kmss:authShow>
	          </c:if>
	        </ul>
	        <div class="verticalLine"></div>
	      </div>
	      <div class="lui-personnel-file-baseInfo-main-content">
	    <html:form action="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do" >
	       		<html:hidden property="fdId" value="${hrStaffPersonInfoForm.fdId}"/>
	       		<html:hidden property="fdHomeAddressAreaName" value="${hrStaffPersonInfoForm.fdHomeAddressAreaName}"/>
	       		<html:hidden property="fdOrgPersonId" value="${hrStaffPersonInfoForm.fdOrgPersonId}"/>
	        <!-- 个人信息 -->
			<div class="lui-personnel-file-staffInfo" id="staffInfo">
		       <div class="lui-personnel-file-header-title">
		         <div class="lui-personnel-file-header-title-left">
		           <div class="lui-personnel-file-header-title-text"><bean:message key="mobile.hr.staff.view.2" bundle="hr-staff"/></div> 
		         </div>
		       </div>
		       <table class="staffInfo newTable">
		         <tbody>
		             <tr>
	             		<!-- 姓名 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName" />
						</td>
						<td width="35%" class="inputwidth">
							<c:choose>
								<c:when test="${param.method eq 'add'}">
									<xform:text required="true" property="fdName" onValueChange="nameChange" style="width:95%;" className="inputsgl" showStatus="edit" />
								</c:when>
								<c:otherwise>
									<xform:text required="true" property="fdName"  style="width:95%;" className="inputsgl" showStatus="view" />
									<!-- #134129 修复全局编辑新增账号密码 姓名会被清空 -->
									<input type="hidden" name="fdName" value="${hrStaffPersonInfoForm.fdName }"/>
								</c:otherwise>
							</c:choose>
						</td>
		             	<!-- 曾用名 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNameUsedBefore" />
						</td>
						<td width="35%" class="inputwidth">
							<xform:text property="fdNameUsedBefore" style="width:98%;" className="inputsgl" />
						</td>
		             </tr>
		             <tr>
		             	<!-- 身份证号码 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIdCard" />
						</td>
						<td class="inputwidth">
						<c:if test="${isIdcardValidate == true }">
							<xform:text property="fdIdCard" onValueChange="idCardChange" style="width:95%;" className="inputsgl" required="true" validators="idCard"/>
						</c:if>
							<c:if test="${isIdcardValidate == false }">
							<xform:text property="fdIdCard" onValueChange="idCardChange" style="width:95%;" className="inputsgl" required="true"/>
						</c:if>
						</td>
	             		<!-- 性别 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSex" />
						</td>
						<td width="35%">
							<sunbor:enums property="fdSex" enumsType="sys_org_person_sex" elementType="radio" />
						</td>
		             </tr>
		             <tr>
	             		<!-- 出生日期 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfBirth" />
						</td>
						<td width="35%" class="datawidth">
							<xform:datetime onValueChange="staffBirthDateChange" showStatus="edit" property="fdDateOfBirth" dateTimeType="date" required="true"></xform:datetime>
						</td>
		             	<td width="160"><bean:message key="hrStaffPersonInfo.fdAge" bundle="hr-staff"/></td>
		             	<td class="inputwidth">	   
		             		<span class="hr-person-info-fdage">${hrStaffPersonInfoForm.fdAge }</span>
		             	</td>
		             </tr>
		             <tr>
	             		<!-- 民族 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNation" />
						</td>
						<td width="35%">
						<xform:xselect property="fdNation" showStatus="edit" htmlElementProperties="class='hr_select'" required="true">
						   <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdNation'" orderBy="fdOrder"></xform:beanDataSource>
						</xform:xselect>
						</td>
						<!-- 政治面貌 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPoliticalLandscape" />
						</td>
						<td width="35%">
						<xform:xselect property="fdPoliticalLandscape" showStatus="edit" htmlElementProperties="class='hr_select'" required="true">
						   <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdPoliticalLandscape'" orderBy="fdOrder"></xform:beanDataSource>
						</xform:xselect>
						</td>
		             </tr>
							            <tr>
						<!-- 最高学历 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestEducation" />
						</td>
						<td width="35%">
						<xform:xselect property="fdHighestEducation" showStatus="edit" htmlElementProperties="class='hr_select'" required="true">
						   <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdHighestEducation'" orderBy="fdOrder"></xform:beanDataSource>
						</xform:xselect>
						</td>
						<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkTime" />
							</td>
							<td width="35%" class="datawidth">
								<xform:datetime property="fdWorkTime" dateTimeType="date" required="true" validators="workTimeAndTrialExpirationTime compareTime(work)" onValueChange="workTimeChange"></xform:datetime>
							</td>
						<!-- 最高学位 -->
<!-- 						<td width="15%" class="td_normal_title"> -->
<%-- 							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestDegree" /> --%>
<!-- 						</td> -->
<!-- 						<td width="35%"> -->
<%-- 							<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHighestDegree", request)%> --%>
<!-- 						</td> -->
					</tr>
					<tr>
						<!-- 婚姻情况 -->
						<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdMaritalStatus" />
						</td>
						<td width="35%">
						<xform:xselect property="fdMaritalStatus" showStatus="edit" htmlElementProperties="class='hr_select'" required="true">
						   <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdMaritalStatus'" orderBy="fdOrder"></xform:beanDataSource>
						</xform:xselect>
						</td>
					
						
							<!-- 籍贯 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNativePlace" />
							</td>
							<td width="35%" class="inputwidth">
								<xform:text property="fdNativePlace" style="width:98%;" className="inputsgl" required="true"/>
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
								<html:hidden property="fdOfficeAreaProvinceName"/>
								<html:hidden property="fdOfficeAreaCityName"/>
								<html:hidden property="fdOfficeAreaAreaName"/>
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
							
								<xform:text property="fdPostalAddress"  required="true" htmlElementProperties="placeholder='详细地址（例如**街**号**）'" style="width:98%;" className="inputsgl" />
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
								<xform:text property="fdHomeAddress" htmlElementProperties="placeholder='详细地址（例如**街**号**）'" required="true" style="width:98%;" className="inputsgl" />
							</td>
							</tr>
						<tr>
							<!-- 现居地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdForeignLanguageLevel" />
							</td>
							<td width="35%" class="inputwidth">
								<xform:text property="fdForeignLanguageLevel" style="width:98%;" className="inputsgl" />
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
							<td  width="35%" class="inputwidth">
							<xform:select property="fdAccountProperties" showStatus="edit" required="true">
							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdAccountProperties"></xform:enumsDataSource>
							</xform:select >
							</td>
							<!-- 户口所在地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdRegisteredResidence" />
							</td>
							<td width="35%" class="inputwidth">
								<xform:text property="fdRegisteredResidence" style="width:98%;" className="inputsgl" required="true"/>
							</td>
						</tr>
<!-- 						<tr> -->
<!-- 							户口所在派出所 -->
<!-- 							<td width="15%" class="td_normal_title"> -->
<%-- 								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResidencePoliceStation" /> --%>
<!-- 							</td> -->
<!-- 							<td width="35%" class="inputwidth"> -->
<%-- 								<xform:text property="fdResidencePoliceStation" style="width:98%;" className="inputsgl" required="true"/> --%>
<!-- 							</td> -->
<!-- 		            		参加工作时间 -->
							
<!-- 						 </tr> -->
		            	<tr>
							<!-- 本企业工龄 -->
							<td width="15%" class="td_normal_title">
							<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkingYears"/>
							</td>
							<td width="35%" class="">
								<div class="input">
									<xform:text  showStatus="readonly" property="fdWorkingYearsYear" onValueChange="workYearChange" validators="number digits min(0)" className="workYear"></xform:text><bean:message key="resource.period.type.year.name"/>
									<xform:text  showStatus="readonly" property="fdWorkingYearsMonth" onValueChange="workYearChange" validators="number digits min(0) max(11)" className="workYear"></xform:text><bean:message key="resource.period.type.month.name"/>
								</div>
								<html:hidden property="fdWorkingYearsDiff"/>
								<html:hidden property="fdWorkingYearsValue"/>
								<html:hidden property="fdWorkingYears"/>
								
						</td>
						   <!-- 连续工龄 -->
							<td width="15%" class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdUninterruptedWorkTime" /></td>
							<td width="35%" class="">
								<div class="input">
									<xform:text  showStatus="readonly" property="fdUninterruptedWorkTimeYear" onValueChange="uninterruptedWorkTimeChange" validators="number digits min(0)" className="workYear"></xform:text><bean:message key="resource.period.type.year.name"/>
									<xform:text  showStatus="readonly" property="fdUninterruptedWorkTimeMonth" onValueChange="uninterruptedWorkTimeChange" validators="number digits min(0) max(11)" className="workYear"></xform:text><bean:message key="resource.period.type.month.name"/>
								</div>
								
								<html:hidden property="fdWorkTimeDiff"/>
								<html:hidden property="fdUninterruptedWorkTimeValue"/>
								<html:hidden property="fdUninterruptedWorkTime"/>
							</td>
		            	</tr>
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
		         </tbody>
		       </table>
		     </div>
		    <c:if test="${(hrStaffPersonInfoForm.fdStatus != 'leave')&&(hrStaffPersonInfoForm.fdStatus != 'retire')&&(hrStaffPersonInfoForm.fdStatus != 'dismissal')}">
	        <!-- 在职信息 -->
			<div class="lui-personnel-file-staffInfo" id="dutyInfo">
		           <div class="lui-personnel-file-header-title">
		              <div class="lui-personnel-file-header-title-left">
		                <div class="lui-personnel-file-header-title-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.view.4" /></div> 
		              </div>
		              <div class="lui-personnel-file-edit">
		         <!--        <span class="lui-personnel-file-edit-icon"></span>
		                <span class="lui-personnel-file-edit-text">编辑</span> -->
		              </div>
		            </div>
		            <table class="newTable">
		                <tbody>
		                    <tr>
			                    <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffNo" />
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdStaffNo" style="width:95%;" required="true" validators="uniqueStaffNo" className="inputsgl" />
								</td>
								<td width="15%"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAffiliatedCompany" /></td>
		                    		<td>
						<xform:xselect property="fdAffiliatedCompany" showStatus="edit" htmlElementProperties="class='hr_select'" required="true">
						   <xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdAffiliatedCompany'" orderBy="fdOrder"></xform:beanDataSource>
						</xform:xselect></td>
		                    		<!-- <td width="45%" class="inputwidth">
									<xform:select property="fdAffiliatedCompany" showStatus="edit" onValueChange="affiliatedCompanyChange" required="true" >
									   <xform:enumsDataSource enumsType="hr_staff_person_info_fdAffiliatedCompany" ></xform:enumsDataSource>
									</xform:select >
		                    		</td>
									 -->
									  </tr>
									  <tr>
									  <!-- 职务 -->
									<td width="15%" class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffingLevel" />
									</td>
									<td width="45%" class="inputwidth">
										<xform:select property="fdStaffingLevelId" showStatus="edit" required="true">
											<xform:beanDataSource serviceBean="sysOrganizationStaffingLevelService" selectBlock="fdId,fdName"></xform:beanDataSource>
										</xform:select >
									</td>
									<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdCategory" /></td>
		                    		<td width="45" class="inputwidth">
											    			<xform:select property="fdCategory" showStatus="edit" required="true">
							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdCategory"></xform:enumsDataSource>
							</xform:select>
		                    		</td>
		                    		
									  </tr>
									  <tr>
									  <c:if test="${hrStaffPersonInfoForm.method_GET=='add'}">
			                    	<td width="15%">
			                    		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgPosts" />
			                    	</td>
			                    	<td>
<%-- 										<xform:address orgType="ORG_TYPE_POST" showStatus="edit" propertyName="fdOrgPostNames" propertyId="fdOrgPostIds"></xform:address> --%>
			                    		<c:choose>
											<c:when test="${hrToEkpEnable == true }">
												<xform:address  orgType="ORG_TYPE_POST" onValueChange="window.hrPostsChange" required="true" showStatus="edit" isHrAddress="true" propertyName="fdPostNames" propertyId="fdPostIds" ></xform:address>
											</c:when>
											<c:otherwise>
												<xform:address orgType="ORG_TYPE_POST" showStatus="edit" propertyName="fdOrgPostNames" propertyId="fdOrgPostIds" required="true"></xform:address>
											</c:otherwise>
										</c:choose>
			                    	</td>
									</c:if>
									<c:if test="${hrStaffPersonInfoForm.method_GET=='edit'}">
										<td width="15%">
											<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgPosts" />
										</td>
										<td>
<%-- 											<xform:address isHrAddress="true" propertyId="fdPostIds" propertyName="fdPostNames" onValueChange="window.hrPostsChange" --%>
<%-- 														   mulSelect="true" showStatus="edit" orgType="ORG_TYPE_POST" required="true"></xform:address> --%>
<%-- 											<c:set value="${hrStaffPersonInfoForm.fdPostIds }" var="fdPostIds"/> --%>
												<xform:address orgType="ORG_TYPE_POST" mulSelect="true" showStatus="edit" propertyName="fdOrgPostNames" propertyId="fdOrgPostIds" required="true"></xform:address>
										</td>
									</c:if>
								<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgRank" /></td>
							<td width="45" class="inputwidth">
								<xform:select property="fdOrgRankId" showStatus="edit" onValueChange="orgRankChange" required="true">
									<xform:beanDataSource serviceBean="hrOrganizationRankService" selectBlock="fdId,fdName"></xform:beanDataSource>
								</xform:select >
								  		<html:hidden property="fdOrgRankName" value="${hrStaffPersonInfoForm.fdOrgRankName}"/>
	      
							</td>
									  </tr>
		                    <tr>
		                    	<td width="15%"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgPost" /></td>
		                    	
		                    	<td width="35">
		                    		<c:choose>
										<c:when test="${hrToEkpEnable == true }">
											<%-- <c:choose>
												<c:when test="${not empty hrStaffPersonInfoForm.fdParentId}">
													<xform:address isHrAddress="true" propertyId="fdParentId" propertyName="fdParentName" showStatus="readOnly" 
														orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT" style="width:55%;"></xform:address>
												</c:when>
												<c:otherwise> --%>
													<xform:address isHrAddress="true" propertyId="fdOrgParentId" propertyName="fdOrgParentName" showStatus="edit" 
														orgType="ORG_TYPE_ORG|ORG_TYPE_DEPT" required="true" onValueChange="OrgParentChange"></xform:address>
													<c:set value="${hrStaffPersonInfoForm.fdParentId }" var="fdParentId"/>
												<%-- </c:otherwise>
											</c:choose> --%>
										</c:when>
										<c:otherwise>
											<%-- <c:choose>
												<c:when test="${not empty hrStaffPersonInfoForm.fdOrgParentId}">
													<xform:address propertyId="fdOrgParentId" propertyName="fdOrgParentName" showStatus="readOnly" 
														orgType="ORG_TYPE_DEPT" required="true" style="width:55%;"></xform:address>
												</c:when>
												<c:otherwise> --%>
													<xform:address propertyId="fdOrgParentId" propertyName="fdOrgParentName" showStatus="edit" 
														orgType="ORG_TYPE_DEPT" required="true" onValueChange="OrgParentChange"></xform:address>
													<c:set value="${hrStaffPersonInfoForm.fdOrgParentId }" var="fdParentId"/>
												<%-- </c:otherwise>
											</c:choose> --%>
										</c:otherwise>
									</c:choose>
		                    	</td>
		                    	<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDepartmentHead" /></td>
		                    		<td width="45" class="inputwidth">
		                    			<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdDepartmentHeadName" propertyId="fdDepartmentHeadId" showStatus="readOnly" ></xform:address>
									
<%-- 											<xform:text  property="fdDepartmentHead" required="true"></xform:text> --%>
		                    		</td>
							
<%-- 			          <html:hidden property="fdHeadOfFirstLevelDepartmentId" value="${hrStaffPersonInfoForm.fdHeadOfFirstLevelDepartmentId}"/> --%>
<%-- 			          <html:hidden property="fdDepartmentHeadId" value="${hrStaffPersonInfoForm.fdDepartmentHeadId}"/> --%>
		                    </tr>
		                    <tr>
		                    <td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPrincipalIdentification" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text  property="fdPrincipalIdentification" ></xform:text>
		                    		</td>
		                    		<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdReportLeader" /></td>
		                    	
							<td width="35">
		                    		<c:choose>
										<c:when test="${hrToEkpEnable == true }">
											<xform:address  orgType="ORG_TYPE_PERSON" onValueChange="fdReportLeaderIdChange" propertyName="fdReportLeaderName" propertyId="fdReportLeaderId" required="true"></xform:address>
<%-- 										<xform:address isHrAddress="true" orgType="ORG_TYPE_PERSON" propertyName="fdReportLeaderName" propertyId="fdReportLeaderId" required="true"></xform:address> --%>
										</c:when>
										<c:otherwise>	
										<xform:address  orgType="ORG_TYPE_PERSON" onValueChange="fdReportLeaderIdChange" propertyName="fdReportLeaderName" propertyId="fdReportLeaderId" required="true"></xform:address>
									</c:otherwise>
									</c:choose>
		                    		
		                    	</td>
		                    </tr>
		                    <tr>
		                    
		                    	<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDirectSuperiorJobNumber" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text  property="fdDirectSuperiorJobNumber"  showStatus="edit" required="true"></xform:text>
		                    		</td>
		                    <td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdFirstLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdFirstLevelDepartmentName" propertyId="fdFirstLevelDepartmentId" showStatus="readOnly"></xform:address>
<%-- 									<xform:text  property="fdFirstLevelDepartment" showStatus="readonly" /> --%> 
		                    		</td>
		                    </tr>
		                    <tr>
		                    
		                    		<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHeadOfFirstLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">
		                    			<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdHeadOfFirstLevelDepartmentName" propertyId="fdHeadOfFirstLevelDepartmentId" showStatus="readOnly"></xform:address>
									
<%-- 											<xform:text  property="fdHeadOfFirstLevelDepartment" required="true"></xform:text> --%>
		                    		</td>
		                    		<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSecondLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">
														<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdSecondLevelDepartmentName" propertyId="fdSecondLevelDepartmentId" showStatus="readOnly"></xform:address>
<%-- 									<xform:text  property="fdSecondLevelDepartment" showStatus="readonly" ></xform:text> --%>
		                    		</td>
		                    </tr>
		                    <tr>
							
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdThirdLevelDepartment" /></td>
		                    		<td width="45" class="inputwidth">
															<xform:address  orgType="ORG_TYPE_PERSON"  propertyName="fdThirdLevelDepartmentName" propertyId="fdThirdLevelDepartmentId" showStatus="readOnly"></xform:address>
<%-- 							<xform:text  property="fdThirdLevelDepartment" showStatus="readonly" ></xform:text> --%>
		                    		</td>
		                    	
							</tr>
						
					
							
							
							
					
							<tr>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdFixedShift" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text  property="fdFixedShift" ></xform:text>
		                    		</td>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPlaceOfInsurancePayment" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text  property="fdPlaceOfInsurancePayment" ></xform:text>
		                    		</td>
							</tr>
			                  <tr>
		                  
		                    	<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLoginName" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text validators="uniqueLoginName invalidLoginName normalLoginName" property="fdLoginName" required="true"></xform:text>
		                    		</td>
		                    	<c:if test="${empty hrStaffPersonInfoForm.fdOrgPersonId}">
		                    		<td width="15%">${lfn:message('hr-staff:hrStaffPersonInfo.fdPassword') }</td>
			                    	<td width="45%" class="fdNewPassword inputwidth">
											<xform:text required="true" showStatus="edit" property="fdNewPassword" subject="密码"></xform:text>
											<%--<span style="display: none;" class="txtstrong" id="isRequiredFlag">*</span>--%>
			                    	</td>
		                    	</c:if>
		                    </tr>
		                    <tr>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSocialSecurityParticipatingCompany" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text  property="fdSocialSecurityParticipatingCompany" ></xform:text>
		                    		</td>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdProvidentFundInsuranceCompany" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text  property="fdProvidentFundInsuranceCompany" ></xform:text>
		                    		</td>
							</tr>
			                    <tr>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsAttendance" /></td>
		                    		<td width="45" class="inputwidth" >
							<sunbor:enums property="fdIsAttendance" enumsType="hr_staff_person_info_fdYesNo" elementType="radio" />
		                    		</td>
							<td width="15"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTimeCardNo" /></td>
		                    		<td width="45" class="inputwidth">
											<xform:text  property="fdTimeCardNo" required="true"></xform:text>
		                    		</td>
							</tr>
<%-- 								<c:if test="${hrToEkpEnable == true }"> --%>
<!-- 									<tr id="staffLevingBox"> -->
<%-- 										<td><bean:message key="hrOrganizationGrade.fdName" bundle="hr-organization"/></td> --%>
<!-- 										<td class="grade_add_box"> -->
<%-- 											<c:if test="${not empty rankList}"> --%>
<%-- 												${ranGrade eq null?"":ranGrade } --%>
<%-- 											</c:if> --%>
<!-- 										</td> -->
<%-- 										<td><bean:message key="hrOrganizationRank.fdName" bundle="hr-organization"/></td> --%>
<!-- 										<td id="rank_edit"> -->
<%-- 											${rankList } --%>
<!-- 										</td> -->
<!-- 									</tr> -->
<%-- 								</c:if> --%>
							<tr>
								<td width="15%" class="td_normal_title">
									<bean:message key="hrOrganizationElement.fdIsBusiness" bundle="hr-organization"/></td>
								</td>
								<td width="35%">
									<ui:switch property="fdIsBusiness"></ui:switch>
								</td>
								<td>
									<bean:message key="hrStaffPersonInfo.fdCanLogin" bundle="hr-staff"/></td>
								</td>
								<td width="35%">
									<ui:switch property="fdCanLogin"></ui:switch>
								</td>
							</tr>
		                </tbody>
		            </table>
		      </div>
		    </c:if>
		    <c:if test="${hrStaffPersonInfoForm.method_GET=='edit'}">
		     	<c:if test="${(hrStaffPersonInfoForm.fdStatus == 'leave' )||( hrStaffPersonInfoForm.fdStatus =='retire')||(hrStaffPersonInfoForm.fdStatus =='dismissal')}">
		         <!-- 离职信息 -->
			     <div class="lui-personnel-file-staffInfo" id="departureInfo">
			        <div class="lui-personnel-file-header-title">
			            <div class="lui-personnel-file-header-title-left">
			              <div class="lui-personnel-file-header-title-text"><bean:message bundle="hr-staff" key="mobile.hr.staff.view.5" /></div> 
			            </div>
			          </div>
			          <table class="newTable">
			              <tbody>
			                  <tr>
			                  	<td width="15%" class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveApplyDate" /></td>
			                  	<td width="35%" class="td_normal_title datawidth">
			                  		<xform:datetime property="fdLeaveApplyDate" dateTimeType="date"></xform:datetime>
			                  	<td width="15%" class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveTime" /></td>
			                  	<td width="35%" class="datawidth td_normal_title">
			                  		<xform:datetime property="fdLeaveTime" dateTimeType="date"></xform:datetime>
								</td>
			                  </tr>
			                  <tr>
			                  	<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveSalaryEndDate" /></td>
			                  	<td class="datawidth"><xform:datetime property="fdLeaveSalaryEndDate" dateTimeType="date"></xform:datetime></td>
			                  	 <td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveReason" /></td>
			                  	<td>
			                  		<xform:xselect property="fdLeaveReason" showStatus="edit" htmlElementProperties="class='hr_select'">
										<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdLeaveReason'" orderBy="fdOrder"></xform:beanDataSource>
									</xform:xselect>
			                  	</td> 
			          			<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLeaveRemark" /></td>
			                  	<td ><xform:textarea property="fdLeaveRemark" showStatus="edit"></xform:textarea></td>
			                  </tr>
			                  <tr>
			                  		<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNextCompany" /></td>
			                  		<td>
			                  			<xform:textarea property="fdNextCompany"></xform:textarea>
			                  		</td>
			                  </tr>
			              </tbody>
			          </table>
			          <html:hidden property="fdParentId" value="${hrStaffPersonInfoForm.fdParentId}"/>
			          <html:hidden property="fdPostIds" value="${hrStaffPersonInfoForm.fdPostIds}"/>
			     </div>
			     </c:if>
			   </c:if>
	        <!-- 员工状态 -->
			<div class="lui-personnel-file-staffInfo" id="staffStatus">
		          <div class="lui-personnel-file-header-title">
		             <div class="lui-personnel-file-header-title-left">
		               <div class="lui-personnel-file-header-title-text"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus" /></div> 
		             </div>
		           </div>
		           <table class="newTable">
		               <tbody>
		                   <tr>
							
		                   		<!-- 人员类别 -->
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaff.staffCategory" />
								</td>
								<td width="45%" class="inputwidth">
								<xform:select property="fdStaffType" showStatus="edit" onValueChange="staffTypeChange" required="true">
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdStaffType'"></xform:beanDataSource>
								</xform:select >
							</td>
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaff.staffStatus" />
								</td>
								<td width="45%" class="inputwidth">
								<xform:select  showStatus="edit" property="fdStatus">
								<xform:enumsDataSource enumsType="hrStaffPersonInfo_fdStatus"></xform:enumsDataSource>
							</xform:select >
							</td>
								<!-- 入职日期-->
							
		                   </tr>
		                   <tr>
		                   	<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEntryTime" />
								</td>
								<td width="35%" class="inputwidth">
									<xform:datetime property="fdEntryTime" dateTimeType="date" required="true" onValueChange="entryTimeChange" validators="compareTime(entry) compareTime(work)"></xform:datetime>
								</td>
								<!-- 入职日期-->
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdProposedEmploymentConfirmationDate" />
								</td>
								<td width="35%" class="inputwidth">
									<xform:datetime property="fdProposedEmploymentConfirmationDate" dateTimeType="date"  validators="compareTime(entry)"></xform:datetime>
								</td>
								<!-- 转正日期 -->
								
		                   </tr>
		                   <tr>
		                   <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPositiveTime" />
								</td>
								<td width="35%" class="datawidth">
									<xform:datetime property="fdPositiveTime" dateTimeType="date" validators="compareTime(entry)" required="true"></xform:datetime>
								</td>
		                   <!-- 转正日期 -->
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdProbationPeriod" />
								</td>
								<td width="45%" class="inputwidth">
										<xform:text  property="fdProbationPeriod"  showStatus="edit" required="true"></xform:text>
		                    		</td>
									
		                   </tr>
		                   <tr>
		                   <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialExpirationTime" />
								</td>
								<td width="35%" class="datawidth">
									<xform:datetime property="fdTrialExpirationTime" dateTimeType="date" validators="compareTime(entry)"></xform:datetime>
								</td>
		                   <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResignationDate" />
								</td>
								<td width="35%" class="datawidth">
									<xform:datetime property="fdResignationDate" dateTimeType="date" validators="compareTime(resignation)"></xform:datetime>
								</td>
								
		                   </tr>
		                   <tr>
		                    <td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResignationType" />
								</td>
								<td width="45%" class="inputwidth">
							
<%-- 							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdResignationType"></xform:enumsDataSource> --%>

								<xform:select property="fdResignationType" showStatus="edit" >
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdResignationType'"></xform:beanDataSource>
								</xform:select >
							</td>
		                   <td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdReasonForResignation" /></td>
			                  		<td  class="inputwidth">
			                  		
								<xform:select property="fdReasonForResignation" showStatus="edit" >
									<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdLeaveReason'"></xform:beanDataSource>
								</xform:select >
			                  	</td>
								
		                   </tr>
		                   <tr>
		                  
		                    		<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdCostAttribution" />
								</td>
								<td width="45%" class="inputwidth">
										<xform:text  property="fdCostAttribution" required="true"></xform:text>
		                    		</td>
		                   </tr>
		                   <tr>
		                   		
		                   		<td width="15%" class="td_normal_title">
		                   			<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIsOAUser"/>
		                   		</td>
		                   		<td width="35%" class="inputwidth">
		                   		
							<xform:select property="fdIsOAUser" showStatus="edit" required="true">
							   <xform:enumsDataSource enumsType="hr_staff_person_info_fdYesNo"></xform:enumsDataSource>
							</xform:select >
		</td>
								<!-- 入职日期-->
								<td width="15%" class="td_normal_title">
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOAAccount" />
								</td>
								<td width="35%" class="inputwidth">
									<xform:text  property="fdOAAccount"  required="true"></xform:text>
		                    		</td>
		                   	</tr>
		               
		               </tbody>
		           </table>
		     </div>
	         <!-- 联系信息 -->
		      <div class="lui-personnel-file-staffInfo" id="contactInfo">
		         <div class="lui-personnel-file-header-title">
		             <div class="lui-personnel-file-header-title-left">
		               <div class="lui-personnel-file-header-title-text"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.contactInfo"/></div> 
		             </div>
		           </div>
		           <table class="newTable" style="width:100%;">
		               <tbody>
		               	<tr>
							<!-- 现居地 -->
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOfficeArea" />
							</td>
						<td width="35%" >
							<%=ProvinceUtil.buildProvinceHtml1("fdOfficeAreaProvinceId",request)%>
						</td>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
						<td width="35%">
							<%=CitiesUtil.buildCitiesHtml("fdOfficeAreaCityId","fdOfficeAreaProvinceId",request)%>
						</td>
						</tr>
						<tr>
							<td width="15%" class="td_normal_title">
								<bean:message bundle="hr-staff" key="" />
							</td>
						<td width="35%">
							<%=AreasUtil.buildAreasHtml("fdOfficeAreaAreaId","fdOfficeAreaCityId", request)%>
						</td>
								<!-- 办公地点 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeLocation') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdOfficeLocation" style="width:98%;" className="inputsgl"  required="true"/>
								</td>
								
							</tr>
							
							<tr>
								
								<!-- 办公电话 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdWorkPhone') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdWorkPhone" style="width:98%;" className="inputsgl" 
											htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'"/>
								</td>
								<!-- 办公电话 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeLine') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdOfficeLine" style="width:98%;" className="inputsgl" 
											htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'"/>
								</td>
							</tr>
							<tr>
								<!-- 办公电话 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdOfficeExtension') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdOfficeExtension" style="width:98%;" className="inputsgl" 
											htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.workPhoneNo.tips') }'"/>
								</td>
								<!-- 邮箱 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmail') }
								</td>
								<td width="35%" class="inputwidth">
										<xform:text property="fdEmail" validators="email" style="width:98%;" className="inputsgl"  required="true"/>
								</td>
							</tr>
						
						
							<tr>
								<!-- 办公地点 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdPrivateMailbox') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdPrivateMailbox" style="width:98%;" className="inputsgl" />
								</td>
								
								<!-- 手机 -->
								<td width="15%" class="td_normal_title" >
									${ lfn:message('hr-staff:hrStaffEntry.fdMobileNo') }
								</td>
								<td width="35%" class="inputwidth inputTextHolder">
									<xform:text required="true" property="fdMobileNo" validators="phoneNumber uniqueMobileNo" style="width:98%;" className="inputsgl"
											htmlElementProperties="placeholder='${ lfn:message('hr-staff:hrStaffPersonInfo.moblieNo.tips') }'"/>
								</td>
							</tr>
							<tr>
								<!-- 紧急联系人 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContact') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdEmergencyContact" style="width:98%;" className="inputsgl"  required="true"/>
								</td>
								<!-- 紧急联系人电话 -->
								<td width="20%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContactPhone') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdEmergencyContactPhone" style="width:98%;" className="inputsgl" validators="phoneNumber"  required="true"/>
								</td>
							</tr>
						                 
		
							<tr>
								<!-- 办公地点 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdRelationsOfEmergencyContactAndEmployee') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdRelationsOfEmergencyContactAndEmployee" style="width:98%;" className="inputsgl"  required="true"/>
								</td>
								<!-- 办公电话 -->
								<td width="15%" class="td_normal_title">
									${ lfn:message('hr-staff:hrStaffPersonInfo.fdEmergencyContactAddress') }
								</td>
								<td width="35%" class="inputwidth">
									<xform:text property="fdEmergencyContactAddress" style="width:98%;" className="inputsgl" 
											/>
								</td>
							</tr>               </tbody>
		           </table>
		     </div>
		     <input id="fdAccountFlag" name="fdAccountFlag" type="hidden" value="${hrStaffPersonInfoForm.fdAccountFlag}"/>
		    </html:form>
		    
		   <c:if test="${hrStaffPersonInfoForm.method_GET=='edit'}">
			   <kmss:authShow roles="ROLE_HRSTAFF_EXPERIENCE">
		   		<!-- 家庭信息 -->
		   		<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_familyInfo.jsp" charEncoding="UTF-8">
					<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
				</c:import>
			   	 <!-- 任职记录 -->
				 <c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_recordJob.jsp" charEncoding="UTF-8">
					<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
				 </c:import>
		         <!-- 个人经历 -->
		        <c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_workExper.jsp" charEncoding="UTF-8">
					<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name="anchor" value="${param.anchor}" />
				</c:import>
		         <!-- 合同信息 -->
		        <c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_docInfo.jsp" charEncoding="UTF-8">
		        	<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name="anchor" value="${param.anchor}" />
		        </c:import>
		         <!-- 奖惩信息 -->
		         <c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_reward.jsp" charEncoding="UTF-8">
		         	<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
					<c:param name="anchor" value="${param.anchor}" />
		         </c:import>
				</kmss:authShow>
		         <!-- 薪酬福利 -->
		         <kmss:authShow roles="ROLE_HRSTAFF_EMOLUMENT">
					<c:import url="/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=getByPerson&&personInfoId=${hrStaffPersonInfoForm.fdId}" charEncoding="UTF-8" >
						<c:param name="paramKey" value="welfare" />
					</c:import>
				 </kmss:authShow>
		         <!-- 个人假期 -->
		         <kmss:authShow roles="ROLE_HRSTAFF_ATTENDANCE">
				 	<%@ include file="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_vacation.jsp"%>
				 </kmss:authShow>
		         <!-- 员工动态 -->
		         <kmss:authShow roles="ROLE_HRSTAFF_LOG_VIEW">
		         	<c:import url="/hr/staff/hr_staff_person_info/view/hrStaffPersonInfo_view_staffRecord.jsp" charEncoding="UTF-8">
		         		<c:param name="personInfoId" value="${hrStaffPersonInfoForm.fdId}" />
		         	</c:import>
				 </kmss:authShow>
			 </c:if>
	      </div>
	    </div>
	  </div>
	<input id="personInfoId" type="hidden" value="${hrStaffPersonInfoForm.fdId}"/>
	<%-- <input id="fdParentId" type="hidden" value="${param.fdParentId}"/> --%>
	<script src="${LUI_ContextPath}/hr/staff/resource/js/personInfo_part.js"></script>
	<%@ include file="/hr/staff/hr_staff_person_info/hrStaffPersonInfo_edit_script.jsp"%>
	<script>
		var hrToEkpEnable = "${hrToEkpEnable}";
		
  		function onSubmitForm(form,method){
  			if(_validator.validate()){
  				checkSyncOrg(form,method);
  			}
  		}

		$("#staffLevingBox select").change(function(e){
			var gradename = $(e.target).find("option:selected").attr("gradeName");
			if(gradename){
				$("#staffLevingBox .grade_add_box").text(gradename);
			}
		})

  		seajs.use(['lui/jquery', 'lui/dialog'],function($, dialog){
  			window.hrPostsChange=function(v){
  				if(v){
	  				 $.ajax({
		                    url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=buildRankHtml',
		                    data: {"fdId":v[0]},
		                    dataType: 'json',
		                    type: 'POST',
		                    async:false,
		                    success: function(data) {
								if(data){
									var tdChild = $("#staffLevingBox td");
									$("#staffLevingBox").empty();
									if(data.html){
										$("#staffLevingBox").append("<td><bean:message key="hrOrganizationRank.fdName" bundle="hr-organization"/></td><td>"+data.html+"</td>");
									}
									var gradename = $("#staffLevingBox select option:selected").attr("gradename");

									if(gradename){
										$("#staffLevingBox").prepend("<td><bean:message key="hrOrganizationGrade.fdName" bundle="hr-organization"/></td><td class='grade_add_box'>"+gradename+"</td>")
									}

									$("#staffLevingBox select").change(function(e){
										var gradename = $(e.target).find("option:selected").attr("gradeName");
										if(gradename){
											$("#staffLevingBox .grade_add_box").text(gradename);
										}

									})
								}
		                    },
		                    error: function(req) {
								$("#staffLevingBox").empty();
		                    }
		                });
  				}else{
  					if($("#staffLevingBox td").length==4){
  						$("#staffLevingBox").get(0).removeChild($("#staffLevingBox td").last().get(0));
  						$("#staffLevingBox").get(0).removeChild($("#staffLevingBox td").last().get(0));
  						$("#staffLevingBox_add").get(0).removeChild($("#staffLevingBox td").last().get(0));
  						$("#staffLevingBox_add").get(0).removeChild($("#staffLevingBox td").last().get(0));
  					}
  				}
  			}
	  		function onSubmitUpdate(form,method){
	  			if(_validator.validate()){
		  			var newStatus = $("select[name='fdStatus']").val();
		  			var oldStatus = "${hrStaffPersonInfoForm.fdStatus}";
		  			if((oldStatus == 'dismissal' || oldStatus == 'leave' || oldStatus == 'retire') 
		  					&& newStatus != 'dismissal' && newStatus != 'leave' && newStatus != 'retire'){
		  				onPostStatus(form, method);
		  			}else if((oldStatus != 'dismissal' || oldStatus != 'leave' || oldStatus != 'retire') && 
		  					(newStatus == 'dismissal' || newStatus == 'leave' || newStatus == 'retire')){
		  				leaveStatus(form, method);
		  			}else{
		  				cutDept(form,method);
		  			}
	  			}
	  		}
	  		
	  		//切换部门
	  		window.cutDept = function(form,method){
	  			var newDeptId = null;
	  			var newPostId = null;
	  			if(hrToEkpEnable  == 'true'){
	  				newDeptId = $("input[name='fdParentId']").val();
	  				newPostId = $("input[name='fdPostIds']").val();
	  			}else{
	  				newDeptId = $("input[name='fdOrgParentId']").val();
	  				newPostId = $("input[name='fdOrgPostIds']").val();
	  			}
	  			var oldDeptId = "${fdParentId}";
	  			var oldPostId = "${fdPostIds}";
	  			if(newDeptId != oldDeptId || newPostId != oldPostId){
	  				dialog.confirm("是否确认修改当前用户部门或岗位，修改后可能影响异动数据不可追溯？", function(ok) {
		  				if(ok == true) {
		  					Com_Submit(form,method);
		  				}
		  			});
	  			}else{
	  				Com_Submit(form,method);
	  			}
	  		}
	  		
	  		window.checkSyncOrg = function(form,method){
	  			debugger;
	  			console.log(111);
	  			debugger;
	  			var fdDeptId = $("input[name='fdParentId']").val();
	  			var fdPostId = $("input[name='fdPostIds']").val();
	  			if(hrToEkpEnable == 'true'){
	  				 $.ajax({
	                    url: '${LUI_ContextPath}/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=checkSyncOrg',
	                    data: {"fdDeptId":fdDeptId, "fdPostId":fdPostId},
	                    dataType: 'json',
	                    type: 'POST',
	                    async:false,
	                    success: function(data) {
	                        if(data.result) {
	                        	Com_Submit(form,method);
	                        }else{
	                        	dialog.confirm("该人员的部门、岗位没有同步到组织架构，请执行定时任务，是否继续新建档案？", function(ok) {
	        		  				if(ok == true) {
	        		  					Com_Submit(form,method);
	        		  				}
	        		  			});
	                        }
	                    },
	                    error: function(req) {
	                        if(req.responseJSON) {
	                            var data = req.responseJSON;
	                            dialog.failure(data.title);
	                        } else {
	                            dialog.failure('操作失败');
	                        }
	                        del_load.hide();
	                    }
	                });
	  			}else{
	  				console.log("1"+form);
	  				Com_Submit(form,method);
	  			}
	  		}
	  		
	  		window.leaveStatus = function(form, method){
	  			var fdLoginName = $("input[name='fdLoginName']").val();
				if(null == fdLoginName || fdLoginName == ""){
		  			dialog.confirm("确认是否做离职操作？", function(ok) {
		  				if(ok == true) {
		  					Com_Submit(form, method);
		  				}
		  			});
	  			}else{
	  				var button = [ {
						name : "注销账号",
						value : true,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}, {
						name : "不注销账号",
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}];
	  				dialog.confirm("是否注销用户系统账号？", function(ok) {
		  				if(ok == true) {
		  					$("#fdAccountFlag").val("1");
		  					Com_Submit(form, method);
		  				}else if (ok == false){
		  					$("#fdAccountFlag").val("0");
		  					Com_Submit(form, method);
		  				}
		  				
		  			}, null , button);
	  			}
			}
			window.onPostStatus = function(form, method){
				var fdLoginName = "${hrStaffPersonInfoForm.fdLoginName}";
				if(null == fdLoginName || fdLoginName == ""){
		  			dialog.confirm("确认是否将员工置为在职状态？", function(ok) {
		  				if(ok == true) {
		  					Com_Submit(form, method);
		  				}
		  			});
	  			}else{
	  				var button = [ {
						name : "恢复账号",
						value : true,
						focus : true,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}, {
						name : "不恢复账号",
						value : false,
						styleClass : 'lui_toolbar_btn_gray',
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}];
	  				dialog.confirm("是否恢复用户系统账号？", function(ok) {
		  				if(ok == true) {
		  					$("#fdAccountFlag").val("2");
		  					Com_Submit(form, method);
		  				}else if(ok ==false){
		  					$("#fdAccountFlag").val("0");
		  					Com_Submit(form, method);
		  				}
		  			}, null , button);
	  			}
			}
			
	  		window.onSubmitUpdate = onSubmitUpdate;
  		});
  		function staffBirthDateChange (value){
  			var birthYear = value.split("-").shift();
  			var nowYear = new Date().getFullYear();
  			$(".hr-person-info-fdage").html(nowYear-birthYear);
  		}
  		function checkPassword(v){
  			var flag = ((v == null) ||v.replace(/^(\s|\u00A0)+|(\s|\u00A0)+$/g,"")==''|| (v.length == 0));
  			if(!flag){
  				$("input[name='fdNewPassword']").attr("validate","required");
				$("input[data-propertyname='fdNewPassword']").attr("validate","required");
				$('#isRequiredFlag').show();
  			}else{
  				$("input[name='fdNewPassword']").attr("validate","");
				$("input[data-propertyname='fdNewPassword']").attr("validate","");
				$('#isRequiredFlag').hide();
  			}
  		}
  		//设置身份校验
  		window.changeStatus = function(value,obj){
			var fdLeaveTimeTd = $('.fdLeaveTimeTd');
			if(value == 'dismissal' || value == 'leave' || value == 'retire'){
				fdLeaveTimeTd.css('display','table-cell');
				$("input[name='fdLeaveTime']").attr("validate","required");
				$("input[data-propertyname='fdLeaveTime']").attr("validate","required");
				$('#isRequiredFlag').show();
			}else{
				fdLeaveTimeTd.css('display','none');
				$("input[name='fdLeaveTime']").attr("validate","");
				$("input[data-propertyname='fdLeaveTime']").attr("validate","");
				$('#isRequiredFlag').hide();
			}
		};
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
				if ((sexcode & 1) == 0) {
					$("input[name='fdSex'][value='F']").prop("checked",true);
				} else {
					$("input[name='fdSex'][value='M']").prop("checked",true);
				}
			}
			var provinces = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" };  
		     var province = provinces[parseInt(value.substr(0, 2))];  
		     $("input[name='fdNativePlace']").val(province);
		}
  		
		$(function(){
			changeStatus('${hrStaffPersonInfoForm.fdStatus}');
		})
		
		LUI.ready(function() {
				
				var __userlandArray=Com_Parameter.Lang.split('-');
				var __userland=__userlandArray[1];
				
				if(__userland=="us"){
					$(".lui-personnel-file-baseInfo-main-aside").css("width","14%");
					$(".lui-personnel-file-baseInfo-main-aside .titleList").css("width","150px");
					//$(".lui-personnel-file-baseInfo-main-aside .titleList li a:before").css("right","-80px");
					$(".lui-personnel-file-baseInfo-main-aside .titleList li a").addClass('ustitleListli');
					$(".titleList li.lui-personnel-file-active a").addClass('ustitleListliafter');
					//$(".titleList li.lui-personnel-file-active c:after").css("right","-84px");
					//$(".lui-personnel-file-baseInfo-main-aside").addRule('a:after','right:-84px');
					$(".lui-personnel-file-baseInfo-main-content").css("width","85%");
					$(".lui-personnel-file-baseInfo-main-content").css("margin-left","7px");
					$(".lui-personnel-file-staffInfo").css("max-width","900px");
				}
				else{
					$(".lui-personnel-file-baseInfo-main-aside .titleList li a").addClass('titleListli');
					$(".titleList li.lui-personnel-file-active a").addClass('titleListliafter');
				}
				
				$(".lui-personnel-file-baseInfo-main-aside .titleList li").click(function(){
					if(__userland=="us"){
						$(".titleList li a").removeClass("ustitleListliafter");
						$(this).find("a").addClass('ustitleListliafter');
					}else{
						$(".titleList li a").removeClass("titleListliafter");
						$(this).find("a").addClass('titleListliafter');
					}
					
				});
			});

  	</script>
	</template:replace>
</template:include>