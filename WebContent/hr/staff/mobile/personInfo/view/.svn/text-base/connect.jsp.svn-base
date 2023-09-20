<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.contactInfo"/>
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
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdMobileNo"/></div>
	          			<div class="ppc_c_list_body">
	          				<xform:text required="true" validators="phoneNumber uniqueMobileNo" property="fdMobileNo" showStatus="${canEdit?'edit':'view'}"></xform:text>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmail"/></div>
	          			<div class="ppc_c_list_body">
	          			<xform:text property="fdEmail" validators="email" showStatus="${canEdit?'edit':'view'}"></xform:text>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOfficeLocation"/></div>
	          			<div class="ppc_c_list_body">
	          			<xform:text property="fdOfficeLocation" showStatus="${canEdit?'edit':'view'}"></xform:text>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkPhone"/></div>
	          			<div class="ppc_c_list_body">
	          			<xform:text property="fdWorkPhone" showStatus="${canEdit?'edit':'view'}" validators="workPhone"></xform:text>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmergencyContact"/></div>
	          			<div class="ppc_c_list_body">
	          			<xform:text property="fdEmergencyContact" showStatus="${canEdit?'edit':'view'}"></xform:text>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmergencyContactPhone"/></div>
	          			<div class="ppc_c_list_body">
	          			<xform:text property="fdEmergencyContactPhone" validators="phoneNumber" showStatus="${canEdit?'edit':'view'}"></xform:text>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOtherContact"/></div>
	          			<div class="ppc_c_list_body">
	          			<xform:text property="fdOtherContact" showStatus="${canEdit?'edit':'view'}"></xform:text>
	          			</div>
	          		</div>
          	       	 <!-- 自定义数据 -->
		            <c:if test="${canEdit==true}">
			            <div style="display:none">
			            	<c:import url="/hr/staff/mobile/personInfo/view/custom_fieldEdit.jsp" charEncoding="UTF-8" >
			            		<c:param name="oper" value="readonly"></c:param>
			            	</c:import>
			            </div>  
		            </c:if>  	
			</div>
		</form>
       	<c:import url="/hr/staff/mobile/personInfo/view/save.jsp">
       		<c:param name="fdId" value="${hrStaffPersonInfoForm.fdId}" />
       		<c:param name="canEdit" value="${canEdit }"/>
       		<c:param name="key" value="connect"/>
       	</c:import>
	</template:replace>
</template:include>
<%@include file="/hr/staff/mobile/personInfo/view/validation_script.jsp"%>