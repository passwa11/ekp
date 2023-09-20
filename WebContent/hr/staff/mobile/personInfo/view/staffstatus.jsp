<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus"/>
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
	          		<div class="ppc_c_list newdate">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus"/></div>
	          			<div class="ppc_c_list_body">
	          				<xform:select property="fdStatus" required="true" showStatus="${canEdit?'edit':'view'}" mobile="true" >
								<xform:enumsDataSource enumsType="hrStaffPersonInfo_fdStatus"></xform:enumsDataSource>
							</xform:select>
	          				<%-- <bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus.${hrStaffPersonInfoForm.fdStatus}"/> --%>
	          			</div>
	          		</div>
	          		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffType"/></div>
	          			<div class="ppc_c_list_body">
	       				<c:choose>
	       					<c:when test="${canEdit}">
			      				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
			       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdStaffType }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdStaffType", request)%>',fieldName:'fdStaffType'"
			       				></div>	
	       					</c:when>
	       					<c:otherwise>
	       						 ${hrStaffPersonInfoForm.fdStaffType }
	       					</c:otherwise>
	       				</c:choose>		       				          				
	          			</div>
	          		</div>
	          		<div class="ppc_c_list">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNatureWork"/></div>
	          			<div class="ppc_c_list_body">
	       				<c:choose>
	       					<c:when test="${canEdit}">
			      				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
			       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdNatureWork }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdNatureWork", request)%>',fieldName:'fdNatureWork'"
			       				></div>	
	       					</c:when>
	       					<c:otherwise>
	       						 ${hrStaffPersonInfoForm.fdNatureWork }
	       					</c:otherwise>
	       				</c:choose>		          				
	          			</div>
	          		</div>
	          		<div class="ppc_c_list newdate">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEntryTime"/></div>
	          			<div class="ppc_c_list_body">
							<xform:datetime property="fdEntryTime" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:datetime>	          			
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEmploymentPeriod"/></div>
	          			<div class="ppc_c_list_body">
	          				<xform:text property="fdEmploymentPeriod" validators="number" showStatus="${canEdit?'edit':'view'}"></xform:text>	
	          			</div>
	          		</div>
	          		<div class="ppc_c_list newdate">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPositiveTime"/></div>
	          			<div class="ppc_c_list_body">
							<xform:datetime property="fdPositiveTime" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:datetime>	          				
	          			</div>
	          		</div>
	          		<div class="ppc_c_list inputstring">
	          			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdTrialOperationPeriod"/></div>
	          			<div class="ppc_c_list_body">
	          				<xform:text required="true" validators="number" property="fdTrialOperationPeriod" showStatus="${canEdit?'edit':'view'}"></xform:text>	
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
       		<c:param name="key" value="status"/>
       	</c:import>
	</template:replace>
</template:include>