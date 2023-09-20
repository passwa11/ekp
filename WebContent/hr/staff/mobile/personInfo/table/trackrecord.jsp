<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<template:include ref="mobile.view" compatibleMode="true">
	<%
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
	%>
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="table.hrStaffTrackRecord"/>
	</template:replace>
	<template:replace name="head">
  		<link rel="stylesheet" href="<%=request.getContextPath()%>/hr/staff/mobile/resource/css/view.css">
	</template:replace>
	<template:replace name="content">
		<c:set var="canEdit" value="true"></c:set>
		<%
			request.setAttribute("mobileField", true);
		%>
		<div class="file-view-tips"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.fdBeginDate"/></div>
		<form data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" Id="baseInfoForm">
			<input type="hidden" name="fdPersonInfoId" value="${param.personInfoId}" />
			<div class="file-view-content">
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.fdBeginDate"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:datetime required="true" property="fdEntranceBeginDate" dateTimeType="date" mobile="true" showStatus="${canEdit?'edit':'view'}" validators="compareEntranceDate"></xform:datetime>
	       			</div>
	       		</div>
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonExperience.fdEndDate"/></div>
	       			<div class="ppc_c_list_body">
	       				<xform:datetime required="true" property="fdEntranceEndDate" dateTimeType="date" mobile="true" showStatus="${canEdit?'edit':'view'}" validators="compareEntranceDate"></xform:datetime>
	       			</div>
	       		</div>
			    <div class="ppc_c_list newdate">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffingLevel"/></div>
	       			<div class="ppc_c_list_body">
						<xform:select property="fdStaffingLevelId" showStatus="${canEdit?'edit':'view'}" showPleaseSelect="true" mobile="true">
							<xform:beanDataSource serviceBean="sysOrganizationStaffingLevelService" selectBlock="fdId,fdName"></xform:beanDataSource>
						</xform:select>			       			
	       			</div>
	       		</div>
			    <div class="ppc_c_list newAddress">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffTrackRecord.fdRatifyDept"/></div>
	       			<div class="ppc_c_list_body">
	       				<c:choose>
								<c:when test="${hrToEkpEnable == true }">
									<xform:address isHrAddress="true" subject="${lfn:message('hr-staff:hrStaffTrackRecord.fdRatifyDept') }" propertyName="fdRatifyDeptName" required="true" orgType="ORG_TYPE_DEPT" propertyId="fdRatifyDeptId" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:address>
								</c:when>
								<c:otherwise>
									<xform:address subject="${lfn:message('hr-staff:hrStaffTrackRecord.fdRatifyDept') }" propertyName="fdRatifyDeptName" required="true" orgType="ORG_TYPE_DEPT" propertyId="fdRatifyDeptId" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:address>
								</c:otherwise>
						</c:choose>	       			
	       			</div>
	       		</div>
			    <div class="ppc_c_list newAddress newaddresspost">
	       			<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffTrackRecord.fdOrgPosts"/></div>
	       			<div class="ppc_c_list_body">
						<c:choose>
								<c:when test="${hrToEkpEnable == true }">
									<xform:address isHrAddress="true" subject="${lfn:message('hr-staff:hrStaffTrackRecord.fdOrgPosts') }" propertyId="fdOrgPostsIds" propertyName="fdOrgPostsNames"  showStatus="${canEdit?'edit':'view'}" mobile="true" orgType="ORG_TYPE_POST"></xform:address>
								</c:when>
								<c:otherwise>
									<xform:address subject="${lfn:message('hr-staff:hrStaffTrackRecord.fdOrgPosts') }" propertyId="fdOrgPostsIds" propertyName="fdOrgPostsNames"  showStatus="${canEdit?'edit':'view'}" mobile="true" orgType="ORG_TYPE_POST"></xform:address>
								</c:otherwise>
						</c:choose>		       			
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
       		<c:param name="personInfoId" value="${param.personInfoId}" />
       		<c:param name="canEdit" value="${canEdit }"/>
       		<c:param name="url" value="/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=${hrStaffTrackRecordForm.method_GET eq 'add'?'save':'update'}&fdId=${param.fdId }"/>
       	</c:import>
       	<%@ include file="/hr/staff/mobile//personInfo/table/experience_common_add.jsp"%>		
	</template:replace>
</template:include>