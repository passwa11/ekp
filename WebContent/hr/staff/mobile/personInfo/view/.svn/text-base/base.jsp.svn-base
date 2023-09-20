<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<%@ page import="com.landray.kmss.hr.staff.model.HrStaffAlertIdcardSetting"%>
<%  HrStaffAlertIdcardSetting hrStaffAlertIdcardSetting = new HrStaffAlertIdcardSetting(); 
	   	String isIdcardValidate = hrStaffAlertIdcardSetting.getisIdcardValidate();
	   	request.setAttribute("isIdcardValidate", isIdcardValidate);
%>	
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="mobile.hr.staff.view.2"/>
	</template:replace>
	<template:replace name="head">
  		<link rel="stylesheet" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/view.css">
	</template:replace>
	<template:replace name="content">
		<kmss:authShow roles="ROLE_HRSTAFF_EDIT">
			<c:set var="canEdit" value="true"></c:set>
		</kmss:authShow>
		<%
			request.setAttribute("mobileField", true);
		%>
		<div class="file-view-tips"><bean:message bundle="hr-staff" key="mobile.hr.staff.view.7"/></div>
	
		<form data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" Id="baseInfoForm">
		<html:hidden property="fdOrgPersonId" value="${hrStaffPersonInfoForm.fdOrgPersonId }"/>
		<div class="file-view-content">
		    <div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdName"/></div>
       			<div class="ppc_c_list_body">
       				<%-- ${hrStaffPersonInfoForm.fdName } --%>
       				<xform:text property="fdName" required="true" showStatus="view" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNameUsedBefore"/></div>
       			<div class="ppc_c_list_body">
       				<%-- ${hrStaffPersonInfoForm.fdNameUsedBefore } --%>
       				<xform:text property="fdNameUsedBefore" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdIdCard"/></div>
       			<div class="ppc_c_list_body">
       			<c:if test="${isIdcardValidate == true }">
						<xform:text property="fdIdCard" showStatus="${canEdit?'edit':'view'}" mobile="true" onValueChange="idCardChange" required="true" validators="idCard"></xform:text>
						</c:if>
							<c:if test="${isIdcardValidate == false }">
							<xform:text property="fdIdCard" showStatus="${canEdit?'edit':'view'}" mobile="true" onValueChange="idCardChange" required="true"></xform:text>
						</c:if>
       			</div>
       		</div>
       		<div class="ppc_c_list newcheckbox">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdSex"/></div>
       			<div class="ppc_c_list_body newRadio">
           			<xform:radio property="fdSex" htmlElementProperties="id=fdSex" mobile="true" showStatus="${canEdit?'edit':'view'}">
           				<xform:enumsDataSource enumsType="sys_org_person_sex"></xform:enumsDataSource>
           			</xform:radio>
       			</div>
       		</div>
       		<div class="ppc_c_list newdate">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfBirth"/></div>
       			<div class="ppc_c_list_body">
       				<xform:datetime property="fdDateOfBirth" htmlElementProperties="id=fdDateOfBirth" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:datetime>
       			</div>
       		</div>
<%--        		<div class="ppc_c_list">
       			<div class="ppc_c_list_head">年龄</div>
       			<div class="ppc_c_list_body">
       				${hrStaffPersonInfoForm.fdAge }
       			</div>
       		</div> --%>
       		<div class="ppc_c_list">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNation"/></div>
       			<div class="ppc_c_list_body">
       				<c:choose>
       					<c:when test="${canEdit }">
       						<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
		       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdNation}',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdNation", request)%>',fieldName:'fdNation'"
		       				></div>
       					</c:when>
       					<c:otherwise>
       						 ${hrStaffPersonInfoForm.fdNation }
       					</c:otherwise>
       				</c:choose>
       			</div>
       		</div>
       		<div class="ppc_c_list">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPoliticalLandscape"/></div>
       			<div class="ppc_c_list_body">
       				<c:choose>
       					<c:when test="${canEdit }">
		       				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
		       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdPoliticalLandscape }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdPoliticalLandscape", request)%>',fieldName:'fdPoliticalLandscape'"
		       				></div>
       					</c:when>
       					<c:otherwise>
       						 ${hrStaffPersonInfoForm.fdPoliticalLandscape }
       					</c:otherwise>
       				</c:choose>
       			</div>
       		</div>
       		<div class="ppc_c_list newdate">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfGroup"/></div>
       			<div class="ppc_c_list_body">
       				<xform:datetime property="fdDateOfGroup" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:datetime>
       			</div>
       		</div>
       		<div class="ppc_c_list newdate">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdDateOfParty"/></div>
       			<div class="ppc_c_list_body">
       				<xform:datetime property="fdDateOfParty" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:datetime>
       			</div>
       		</div>
       		<div class="ppc_c_list">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestEducation"/></div>
       			<div class="ppc_c_list_body">
       				<c:choose>
       					<c:when test="${canEdit}">
		      				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
		       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdHighestEducation }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHighestEducation", request)%>',fieldName:'fdHighestEducation'"
		       				></div>
       					</c:when>
       					<c:otherwise>
       						 ${hrStaffPersonInfoForm.fdHighestEducation }
       					</c:otherwise>
       				</c:choose>
       			</div>
       		</div>
       		<div class="ppc_c_list">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHighestEducation"/></div>
       			<div class="ppc_c_list_body">
       				<c:choose>
       					<c:when test="${canEdit }">
		     				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
		       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdHighestEducation }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHighestDegree", request)%>',fieldName:'fdHighestDegree'"
		       				></div> 
       					</c:when>
       					<c:otherwise>
       						 ${hrStaffPersonInfoForm.fdHighestEducation }
       					</c:otherwise>
       				</c:choose>      				
       			</div>

       		</div>
       		<div class="ppc_c_list">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdMaritalStatus"/></div>
       			<div class="ppc_c_list_body">
       				<c:choose>
       					<c:when test="${canEdit }">
		     				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
		       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdMaritalStatus }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdMaritalStatus", request)%>',fieldName:'fdMaritalStatus'"
		       				></div> 
       					</c:when>
       					<c:otherwise>
       						 ${hrStaffPersonInfoForm.fdMaritalStatus }
       					</c:otherwise>
       				</c:choose>        				
       			</div>
       		</div>
       		<div class="ppc_c_list">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHealth"/></div>
       			<div class="ppc_c_list_body">
       				<c:choose>
       					<c:when test="${canEdit }">
		     				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
		       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdHealth }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdHealth", request)%>',fieldName:'fdHealth'"
		       				></div>
       					</c:when>
       					<c:otherwise>
       						 ${hrStaffPersonInfoForm.fdHealth }
       					</c:otherwise>
       				</c:choose>         				
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStature"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text property="fdStature" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWeight"/></div>
       			<div class="ppc_c_list_body">
       			
       				<xform:text property="fdWeight" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLivingPlace"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text property="fdLivingPlace" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNativePlace"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text property="fdNativePlace" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdHomeplace"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text property="fdHomeplace" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdAccountProperties"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text property="fdAccountProperties" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdRegisteredResidence"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text property="fdRegisteredResidence" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdResidencePoliceStation"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text property="fdResidencePoliceStation" showStatus="${canEdit?'edit':'view'}" mobile="true"></xform:text>
       			</div>
       		</div>
       		<div class="ppc_c_list newdate">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTimeOfEnterprise"/></div>
       			<div class="ppc_c_list_body">
       				<xform:datetime required="true" property="fdTimeOfEnterprise" mobile="true" showStatus="${canEdit?'edit':'view'}" validators="timeOfEnterprise" onValueChange="timeOfEnterpriseChange"></xform:datetime>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkingYears"/></div>
       			<div class="ppc_c_list_body">
       				<xform:text  showStatus="${canEdit?'edit':'view'}" property="fdWorkingYearsYear" onValueChange="workYearChange" validators="number digits min(0)" className="workYear"></xform:text><bean:message key="resource.period.type.year.name"/>
					<xform:text  showStatus="${canEdit?'edit':'view'}" property="fdWorkingYearsMonth" onValueChange="workYearChange" validators="number digits min(0) max(11)" className="workYear"></xform:text><bean:message key="resource.period.type.month.name"/>
					<html:hidden property="fdWorkingYearsDiff"/>
					<html:hidden property="fdWorkingYearsValue"/>
					<html:hidden property="fdWorkingYears"/>
       			</div>
       		</div>
       		<div class="ppc_c_list newdate">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkTime"/></div>
       			<div class="ppc_c_list_body">
       				<xform:datetime required="true" property="fdWorkTime" mobile="true" showStatus="${canEdit?'edit':'view'}" validators="workTime" onValueChange="workTimeChange"></xform:datetime>
       				<html:hidden property="fdTrialExpirationTime" value="${hrStaffPersonInfoForm.fdTrialExpirationTime }"/>
       			</div>
       		</div>
       		<div class="ppc_c_list inputstring">
       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdUninterruptedWorkTime" /></div>
       			<div class="ppc_c_list_body">
					<xform:text  showStatus="${canEdit?'edit':'view'}" property="fdUninterruptedWorkTimeYear" onValueChange="uninterruptedWorkTimeChange" validators="number digits min(0)" className="workYear"></xform:text><bean:message key="resource.period.type.year.name"/>
					<xform:text  showStatus="${canEdit?'edit':'view'}" property="fdUninterruptedWorkTimeMonth" onValueChange="uninterruptedWorkTimeChange" validators="number digits min(0) max(11)" className="workYear"></xform:text><bean:message key="resource.period.type.month.name"/>
					<html:hidden property="fdWorkTimeDiff"/>
					<html:hidden property="fdUninterruptedWorkTimeValue"/>
					<html:hidden property="fdUninterruptedWorkTime"/>
       			</div>
       		</div>
  					<!-- 自定义数据 -->
	        			<c:import url="/hr/staff/mobile/personInfo/view/custom_fieldEdit.jsp" charEncoding="UTF-8" />
	        		</div>
       			</form>
       	<c:import url="/hr/staff/mobile/personInfo/view/save.jsp">
       		<c:param name="fdId" value="${hrStaffPersonInfoForm.fdId}" />
       		<c:param name="canEdit" value="${canEdit }"/>
       		<c:param name="key" value="basic"/>
       	</c:import>
	</template:replace>
</template:include>
<%@include file="/hr/staff/mobile/personInfo/view/validation_script.jsp"%>