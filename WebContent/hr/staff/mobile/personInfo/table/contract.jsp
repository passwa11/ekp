<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="hrStaffPersonExperience.type.contract"/>
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
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdBeginDate"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:datetime property="fdBeginDate" dateTimeType="date" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:datetime>
	       			</div>
	       		</div>
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdEndDate"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:datetime property="fdEndDate" htmlElementProperties="id=fdEndDate" dateTimeType="date" mobile="true" showStatus="${canEdit?'edit':'view'}" validators="compareDate checkLongterm"></xform:datetime>
	       			</div>
	       		</div>
				<div class="ppc_c_list newdate">
					<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdIsLongtermContract"/></div>
					<div class="ppc_c_list_body">
						<xform:checkbox property="fdIsLongtermContract" htmlElementProperties="id=fdIsLongtermContract" mobile="true" onValueChange="cancelEndDate" value="${hrStaffPersonExperienceContractForm.fdIsLongtermContract}" showStatus="edit">
							<xform:simpleDataSource value="true"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdIsLongtermContract.1"/></xform:simpleDataSource>
						</xform:checkbox>
					</div>
				</div>
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdHandleDate"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:datetime property="fdHandleDate" dateTimeType="date" mobile="true" showStatus="${canEdit?'edit':'view'}" validators="compareDate"></xform:datetime>
	       			</div>
	       		</div>	       		
			    <div class="ppc_c_list inputstring">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdName"/></div>
	       			<div class="ppc_c_list_body">
						<xform:text property="fdName"  required="true"  showStatus="${canEdit?'edit':'view'}"></xform:text>	       			
	       			</div>
	       		</div>
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContType"/></div>
	       			<div class="ppc_c_list_body">
          				<xform:select property="fdContType"  showStatus="${canEdit?'edit':'view'}" mobile="true" >
          					<xform:beanDataSource serviceBean="hrStaffContractTypeService" selectBlock="fdName" orderBy="fdOrder" />
						</xform:select>	       				
	       			</div>
	       		</div>
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdSignType"/></div>
	       			<div class="ppc_c_list_body">
						<c:choose>
							<c:when test="${hrStaffPersonExperienceContractForm.method_GET eq 'add' }">
								<xform:radio property="fdSignType" showStatus="${canEdit?'edit':'view'}" mobile="true">
									<xform:enumsDataSource enumsType="hrStaffPersonExperienceContract_fdSignType" />
								</xform:radio>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${hrStaffPersonExperienceContractForm.fdSignType eq '1' or hrStaffPersonExperienceContractForm.fdSignType eq '2' or empty hrStaffPersonExperienceContractForm.fdSignType}">
										<xform:radio property="fdSignType" showStatus="${canEdit?'edit':'view'}" mobile="true">
											<xform:enumsDataSource enumsType="hrStaffPersonExperienceContract_fdSignType" />
										</xform:radio>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${fn:endsWith(hrStaffPersonExperienceContractForm.fdSignType,'null') }">
												<xform:radio property="fdSignType" showStatus="${canEdit?'edit':'view'}" mobile="true">
													<xform:enumsDataSource enumsType="hrStaffPersonExperienceContract_fdSignType" />
												</xform:radio>
											</c:when>
											<c:otherwise>
												<html:hidden property="fdSignType" />
												<c:out value="${signType }" />
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>          				     				
	       			</div>
	       		</div>	       		
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.contract.fdContStatus"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:select property="fdContStatus"  showStatus="${canEdit?'edit':'view'}" mobile="true" >
	       					<xform:enumsDataSource enumsType="hrStaffPersonExperienceContract_fdContStatus" />
						</xform:select>	
	       			</div>
	       		</div>
			    <div class="ppc_c_list">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.fdMemo"/></div>
	       			<div class="ppc_c_list_body">
						<xform:textarea showStatus="edit" mobile="true" property="fdMemo" />		       			
	       			</div>
	       		</div>      			       		       			 	       			       			       		
			</div>
		</form>
       	<c:import url="/hr/staff/mobile/personInfo/table/save.jsp">
			<c:param name="fdId" value="${param.fdId }" />
       		<c:param name="personInfoId" value="${param.personInfoId}" />
       		<c:param name="canEdit" value="${canEdit }"/>
       		<c:param name="url" value="/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=${hrStaffPersonExperienceContractForm.method_GET eq 'add'?'save':'update'}&fdId=${param.fdId }"/>
       	</c:import>
       	<%@ include file="/hr/staff/mobile/personInfo/table/experience_common_add.jsp"%>
	</template:replace>
</template:include>