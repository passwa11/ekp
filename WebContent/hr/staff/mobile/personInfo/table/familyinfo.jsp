<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="hrStaffPerson.family"/>
	</template:replace>
	<template:replace name="head">
  		<link rel="stylesheet" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/view.css">
	</template:replace>
	<template:replace name="content">
		<c:set var="canEdit" value="true"></c:set>
		<%
			request.setAttribute("mobileField", true);
		%>
		<div class="file-view-tips"><bean:message bundle="hr-staff" key="mobile.hr.staff.view.7"/></div>
		<form data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" Id="baseInfoForm">
			<input type="hidden" name="fdPersonInfoId" value="${param.personInfoId}" />
			<div class="file-view-content">
			    <div class="ppc_c_list inputstring">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPerson.family.related"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:text property="fdRelated" required="true" showStatus="${canEdit?'edit':'view'}"></xform:text>
	       			</div>
	       		</div>
			    <div class="ppc_c_list inputstring">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPerson.family.name"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:text property="fdName" required="true" showStatus="${canEdit?'edit':'view'}"></xform:text>
	       			</div>
	       		</div>
			    <div class="ppc_c_list inputstring">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPerson.family.occupation"/></div>
	       			<div class="ppc_c_list_body">
						<xform:text property="fdOccupation" showStatus="${canEdit?'edit':'view'}"></xform:text>		       			
	       			</div>
	       		</div>
			    <div class="ppc_c_list inputstring">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPerson.family.company"/></div>
	       			<div class="ppc_c_list_body">
						<xform:text property="fdCompany"  showStatus="${canEdit?'edit':'view'}"></xform:text>		       			
	       			</div>
	       		</div>
			    <div class="ppc_c_list newAddress inputstring">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPerson.family.connect"/></div>
	       			<div class="ppc_c_list_body">
						<xform:text property="fdConnect"  showStatus="${canEdit?'edit':'view'}"></xform:text>		       			
	       			</div>
	       		</div>
			    <div class="ppc_c_list">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPerson.family.memo"/></div>
	       			<div class="ppc_c_list_body">
						<xform:textarea showStatus="edit" mobile="true" property="fdMemo" />		       			
	       			</div>
	       		</div>      			       		       			 	       			       			       		
			</div>
		</form>
       	<c:import url="/hr/staff/mobile/personInfo/table/save.jsp">
       		<c:param name="personInfoId" value="${param.personInfoId}" />
       		<c:param name="canEdit" value="${canEdit }"/>
       		<c:param name="url" value="/hr/staff/hr_staff_person_family/hrStaffPersonFamily.do?method=${hrStaffPersonFamilyForm.method_GET eq 'add'?'save':'update'}&fdId=${param.fdId }"/>
       	</c:import>		
	</template:replace>
</template:include>