<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.hr.staff.util.HrStaffPersonUtil,
				com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-staff" key="mobile.hr.staff.view.4"/>
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
		            <div class="ppc_c_list">
		              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgParentOrg"/></div>
		              <div class="ppc_c_list_body">
		        		${hrStaffPersonInfoForm.fdOrgParentOrgName }
		              </div>
		            </div>
		            <%-- <div class="ppc_c_list newdate">
		              <div class="ppc_c_list_head">入职日期</div>
		              <div class="ppc_c_list_body">
		            	<xform:datetime property="fdEntryTime" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:datetime>
		              </div>
		            </div> --%>
		            <div class="ppc_c_list newAddress">
		              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgParent"/></div>
		              <div class="ppc_c_list_body">              	
	       				<c:choose>
								<c:when test="${hrToEkpEnable == true }">
									<xform:address isHrAddress="true" subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }" propertyName="fdOrgParentName" required="true" orgType="ORG_TYPE_DEPT" propertyId="fdOrgParentId" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:address>	
								</c:when>
								<c:otherwise>
									<xform:address subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }" propertyName="fdOrgParentName" required="true" orgType="ORG_TYPE_DEPT" propertyId="fdOrgParentId" mobile="true" showStatus="${canEdit?'edit':'view'}"></xform:address>
								</c:otherwise>
						</c:choose>		              	
		              </div>
		            </div>
	            	<div class="ppc_c_list newAddress newaddresspost">
		              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdOrgPosts"/></div>
		              <div class="ppc_c_list_body">
						  <c:choose>
							  <c:when test="${hrToEkpEnable == true }">
								  <xform:address isExternal="false" isHrAddress="true" subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }" propertyId="fdPostIds" propertyName="fdPostNames"  showStatus="${canEdit?'edit':'view'}" mobile="true" orgType="ORG_TYPE_POST"></xform:address>
							  </c:when>
							  <c:otherwise>
								  <xform:address isExternal="false" subject="${lfn:message('hr-staff:hrStaffPersonInfo.fdOrgPosts') }" propertyId="fdPostIds" propertyName="fdPostNames"  showStatus="${canEdit?'edit':'view'}" mobile="true" orgType="ORG_TYPE_POST"></xform:address>
							  </c:otherwise>
						  </c:choose>
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
		              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdReportLeader"/></div>
		              <div class="ppc_c_list_body">
							<xform:address isHrAddress="true" showStatus="${canEdit?'edit':'view'}" orgType="ORG_TYPE_PERSON" propertyName="fdReportLeaderName" mobile="true" propertyId="fdReportLeaderId"></xform:address>		         
		              </div>
		            </div>		            			            			       
		            <div class="ppc_c_list inputstring">
		              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStaffNo"/></div>
		              <div class="ppc_c_list_body">
		              	<xform:text property="fdStaffNo" required="true" showStatus="${canEdit?'edit':'view'}"></xform:text>
		              </div>
		            </div>
		            <div class="ppc_c_list inputstring">
		              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdLoginName"/></div>
		              <div class="ppc_c_list_body">
		              	<xform:text property="fdLoginName" showStatus="${canEdit?'edit':'view'}" required="true"></xform:text>
		              </div>
		            </div>
		            <div class="ppc_c_list inputstring">
		              <div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdWorkAddress"/></div>
		              <div class="ppc_c_list_body">
		              	<c:choose>
	       					<c:when test="${canEdit }">
			     				<div data-dojo-type="<%=request.getContextPath()%>/hr/staff/mobile/resource/js/dialogSpinWheel/dialogSpinWheel.js"
			       					data-dojo-props="value:'${hrStaffPersonInfoForm.fdWorkAddress }',data:'<%=HrStaffPersonUtil.buildPersonInfoSettingHtml("fdWorkAddress", request)%>',fieldName:'fdWorkAddress'"
			       				></div>
	       					</c:when>
	       					<c:otherwise>
	       						 ${hrStaffPersonInfoForm.fdWorkAddress }
	       					</c:otherwise>
	       				</c:choose>
		              </div>
		            </div>
					<div class="ppc_c_list inputstring">
						<div class="ppc_c_list_head"><bean:message bundle="hr-organization" key="hrOrganizationElement.fdIsBusiness"/></div>
						<div class="ppc_c_list_body">
							<xform:radio property="fdIsBusiness" mobile="true" showStatus="${canEdit?'edit':'view'}">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</div>
					</div>
				<div class="ppc_c_list inputstring">
					<div class="ppc_c_list_head"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdCanLogin"/></div>
					<div class="ppc_c_list_body">
						<xform:radio property="fdCanLogin" mobile="true" showStatus="${canEdit?'edit':'view'}">
							<xform:enumsDataSource enumsType="common_yesno" />
						</xform:radio>
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
       		<c:param name="key" value="offical"/>
       	</c:import>
	</template:replace>
</template:include>