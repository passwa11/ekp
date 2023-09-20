<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div class="stop-org-info-box">
<ui:tabpanel>
	<ui:layout ref="sys.ui.tabpanel.simple"></ui:layout>
		<ui:content title="组织信息">
			<div id="org-info-content">
				<div class="org-info-box">
					<table class="org-info-table">
						<tr>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdNo')}</td>
							<td>${hrOrganizationElementForm.fdNo}</td>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdName')}</td>
							<td>${hrOrganizationElementForm.fdName}</td>
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
							<%-- <td>法人公司</td>
							<td>${hrOrganizationElementForm.fdIsCorp }</td> --%>
						</tr>
						<tr>
							<td>${lfn:message('hr-organization:hrOrganizationElement.fdMemo')}</td>
							<td>${hrOrganizationElementForm.fdIsCorp }</td>
						</tr>															
					</table>
				</div>		
			</div> 	 					
		</ui:content>
		<ui:content title="${lfn:message('hr-organization:hr.organization.info.orgChangeLog')}">
				<div class="org-change stop-org-change">
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
		</ui:content>
</ui:tabpanel>
</div>