<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
	String url = request.getParameter("parent");
	url = "/sys/organization/sys_org_org/sysOrgOrg.do?"+(url==null?"":"parent="+url+"&");
	pageContext.setAttribute("actionUrl", url);
%>
<template:include ref="default.simple" spa="true">
	<template:replace name="head">
		<link rel="stylesheet" href="${LUI_ContextPath}/hr/organization/resource/css/staffingLevel.css" />
	</template:replace>
    <template:replace name="body">
   		<c:if test="${!param.stopOrgInfo }">
			<div id="org-info-content">
				<div class="org-info-title">
					<div class="org-info-title-text"> 
					${lfn:message('hr-organization:hr.organization.info.orgUnit')}
					</div>
					<c:if test="${hrToEkpEnable }">
						<kmss:auth requestURL="/hr/organization/hr_organization_element/hrOrganizationElement.do?method=add">
							<div class="org-info-title-btn">
								<ui:button id="edit-org" onclick="window.eidtOrg('${param.fdId }')" 
								text="${lfn:message('hr-organization:hr.organization.info.editOrgUnit')}"></ui:button>
								<ui:button id="stop-org" onclick="window.stopOrg('${param.fdId }','${hrOrganizationElementForm.fdOrgType }','${hrOrganizationElementForm.fdName }')" 
								text="${lfn:message('hr-organization:hr.organization.info.stopOrg')}"></ui:button>
							</div>
						</kmss:auth>
					</c:if>
				</div>
				<div class="org-info-box">
				<c:if test="${not empty hrOrganizationElementForm.fdId }">
					<table class="org-info-table">
						<tr>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdNo')}</td>
							<td><c:out value="${hrOrganizationElementForm.fdNo}"></c:out></td>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdName')}</td>
							<td><c:out value="${hrOrganizationElementForm.fdName}"></c:out></td>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdOrgType')}</td>
							<td>
						        <xform:select property="fdOrgType" showStatus="view" value="${hrOrganizationElementForm.fdOrgType }">
									<xform:enumsDataSource enumsType="hr_organization_type" />
								</xform:select>
							</td>
						</tr>
						<tr>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdParent')}</td>
							<td>${hrOrganizationElementForm.fdParentName }</td>
							<td>${lfn:message('hr-organization:hrOrganizationElement.hbmThisLeader')}</td>
							<td>${hrOrganizationElementForm.fdThisLeaderName }</td>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdBranLeader')} </td>
							<td>${hrOrganizationElementForm.fdBranLeaderName }</td>
						</tr>
						<tr>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdNameAbbr')}</td>
							<td>${hrOrganizationElementForm.fdNameAbbr }</td>
							<td>${lfn:message('hr-organization:hr.organization.info.startData')}</td>
							<td><xform:datetime property="fdCreateTime" dateTimeType="date" showStatus="view" /></td>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdOrder')}</td>
							<td>${hrOrganizationElementForm.fdOrder }</td>
						</tr>
						<tr>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdIsVirOrg')}</td>
							<td>${hrOrganizationElementForm.fdIsVirOrg eq 'true'?'是':'否'}</td>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdIsBusiness')}</td>
							<td>${hrOrganizationElementForm.fdIsBusiness eq 'true'?'是':'否'}</td>
						<%-- 	<td>法人公司</td>
							<td>${hrOrganizationElementForm.fdIsCorp eq 'true'?'是':'否' }</td> --%>
						</tr>
						<tr>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdMemo')}</td>
							<td><c:out value="${hrOrganizationElementForm.fdMemo }"></c:out></td>
						</tr>															
					</table>
				</c:if>
				</div>
				<kmss:authShow roles="ROLE_HRORGANIZATION_ORG_ADMIN">
					<div class="org-info-title org-change-title">
						<div class="org-info-title-text">
						${lfn:message('hr-organization:hr.organization.info.orgChangeLog')} 
						</div>
					</div>
					<div class="org-change">
						<ui:fixed elem=".lui_list_operation"></ui:fixed>
						<list:listview>
							<ui:source type="AjaxJson">
								{url:'/hr/organization/hr_organization_log/hrOrganizationLog.do?method=list&ownId=${hrOrganizationElementForm.fdId }&orgType=${hrOrganizationElementForm.fdOrgType}'}
							</ui:source>
							<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
								rowHref="/hr/organization/hr_organization_log/hrOrganizationLog.do?method=view&fdId=!{fdId}">
								<list:col-auto props="fdParaMethod,fdParaDesc,fdDetails,fdOperator,fdCreateTime"></list:col-auto>
							</list:colTable>
						</list:listview>
						<list:paging/>					 
					</div>
				</kmss:authShow>			
			</div> 	  		
   		</c:if>
   		<c:if test="${param.stopOrgInfo }">
			<%@ include file="/hr/organization/hr_organization_tree/orgInfo/stopOrgInfo.jsp"%>
   		</c:if>
		<script src="${LUI_ContextPath}/hr/organization/resource/js/tree/orgInfo.js"></script>
    </template:replace>
</template:include>