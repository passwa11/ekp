<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar" >
			<ui:toolbar layout="sys.ui.toolbar.float">
				<kmss:auth requestURL="/sys/zone/sys_zone_person_multi_resume/sysZonePersonMultiResume.do?method=add"
						requestMethod="GET">
					<ui:button text="${lfn:message('hr-staff:hrStaff.employee.resume.bulk.upload') }" 
							onclick="batchAdd()">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/zone/sys_zone_private_change/sysZonePrivateChange.do?method=editPrivate">
					<ui:button text="${lfn:message('hr-staff:hrStaff.employee.privacy.update') }" onclick="changePrivate();"></ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<style>
			.txtlistpath {
				width: 98%;
				margin:0 auto;
				padding-top : 5px;
			}
			.tr_listrowcur td a:hover{
				color:inherit;
				text-decoration: none;
			}
			.tr_listrowcur td a{
				color: inherit;
				text-decoration: none;
			}
			.linkAClass{
				color: #4285f4;
			}
		</style>
		<html:form action="/sys/organization/sys_org_person/sysOrgPerson.do">
			<div style="width:98%;margin:0 auto;">
				<div style="margin:5px 0">
					<%@ include file="/sys/zone/address/sysOrg_listtop.jsp"%>
				</div>
				<script>Com_IncludeFile("dialog.js");</script>
				<c:if test="${queryPage.totalrows==0}">
					<%@ include file="/resource/jsp/list_norecord.jsp"%>
				</c:if>
				<c:if test="${queryPage.totalrows > 0}">
		
				<table id="List_ViewTable">
					<tr>
					<sunbor:columnHead htmlTag="td">
						<td width="15px">
							<input type="checkbox" name="List_Tongle">
						</td>
						<sunbor:column property="sysOrgPerson.fdName">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdName" />
						</sunbor:column>
						<sunbor:column property="sysOrgPerson.hbmParent.fdName">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdParent" />
						</sunbor:column>
						<sunbor:column property="sysOrgPerson.fdSex">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdSex" />
						</sunbor:column>								
						<sunbor:column property="sysOrgPerson.fdEmail">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdEmail" />
						</sunbor:column>
						<sunbor:column property="sysOrgPerson.fdMobileNo">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo" />
						</sunbor:column>
						<td>
							<bean:message key="hrStaff.employee.privacy" bundle="hr-staff"/>
						</td>
						<td>
							<bean:message key="hrStaff.employee.resume" bundle="hr-staff"/>
						</td>
						<td>
							<bean:message key="hrStaff.employee.operating" bundle="hr-staff"/>
						</td>
						<td>
							<bean:message key="hrStaff.employee.avatar" bundle="hr-staff"/>
						</td>
					</sunbor:columnHead>
				</tr>
				<logic:iterate id="sysOrgPerson" name="queryPage" property="list"
					indexId="index">
					<tr
						kmss_href="<c:url value="/sys/person/sys_person_zone/sysPersonZone.do" />?method=view&fdId=<bean:write name="sysOrgPerson" property="fdId"/>">
						<td>
							<input type="checkbox" name="List_Selected" value="${sysOrgPerson.fdId}">
						</td>
						<%--
						<td width="35px"><bean:write name="sysOrgPerson" property="fdOrder" /></td>
						  --%>
						<td width="80px"><bean:write name="sysOrgPerson" property="fdName" /></td>
						<%--
						<td width="100px"><bean:write name="sysOrgPerson" property="fdLoginName" /></td>
						  --%>
						<td width="160px"><c:out value="${sysOrgPerson.fdParent.fdName}" /></td>
						<td width="15px">
							<c:if test="${sysOrgPerson.fdSex eq 'M'}">
								<bean:message bundle="sys-organization" key="sysOrgPerson.fdSex.M" />
							</c:if>
							<c:if test="${sysOrgPerson.fdSex eq 'F'}">
								<bean:message bundle="sys-organization" key="sysOrgPerson.fdSex.F" />
							</c:if>
						</td>							
						<td><bean:write name="sysOrgPerson" property="fdEmail" /></td>
						<td><bean:write name="sysOrgPerson" property="fdMobileNo" /></td>
						<td width="60px">
							<a class="linkAClass" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}hr/staff/hr_staff_private_change/hrStaffPrivateChange.do?method=editPrivate&fdIds=${sysOrgPerson.fdId}')">
								<bean:message key="hrStaff.employee.privacy" bundle="hr-staff"/>
							</a>
						</td>
						<td>
							<c:choose>
								<c:when test="${resumeJson[sysOrgPerson.fdId] == 'true'}">
									<bean:message key="hrStaff.employee.resume.yes" bundle="hr-staff"/>
								</c:when>
								<c:otherwise>
									<bean:message key="hrStaff.employee.resume.no" bundle="hr-staff"/>
								</c:otherwise>
							</c:choose>
						</td>
						<td width="120px">
							<a class="linkAClass" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=modifyOtherResume&personId=${sysOrgPerson.fdId}')"
							   >
								<bean:message key="hrStaff.employee.resume.update" bundle="hr-staff"/>
							</a>
						</td>
						<td onclick="openUpload('${sysOrgPerson.fdId}');">
							<img alt="<bean:message key="sysZonePersonImg.upload" bundle="sys-zone"/>" title="<bean:message key="sysZonePersonImg.upload" bundle="sys-zone"/>"
								style="width: 24px;height: 24px;" src="${KMSS_Parameter_ContextPath}sys/zone/resource/images/upload_icon_person.png">
						</td>
					</tr>
				</logic:iterate>
			</table>
			<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
		</c:if>
		</div>
	</html:form>
	<script>
		function openUpload(personId){
			var url = '<c:url value="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=modifyOtherPhoto"/>';
			Com_OpenWindow(url + "&personId=" + personId,'_blank');
			Com_EventStopPropagation();
		}
		function batchAdd() {
			var url = '<c:url value="/sys/zone/sys_zone_person_multi_resume/sysZonePersonMultiResume.do?method=add"/>';
			Com_OpenWindow(url,'_blank');
		}
		
		
		function changePrivate() {
			var values = "";
			var checked = false;
			var	select = document.getElementsByName("List_Selected");
			for(var i=0; i<select.length; i++) {
				if(select[i].checked){
					values+=select[i].value;
					if(i != select.length - 1) {
						values+=";";
					}
					checked = true;
				}
			}
			if(!checked) {
				alert("<bean:message key="page.noSelect"/>");
				return;
			} else {
				Com_OpenWindow("${LUI_ContextPath}/hr/staff/hr_staff_private_change/hrStaffPrivateChange.do?method=editPrivate&fdIds=" + values, "_blank");
			}
		}
	</script>
	</template:replace>
</template:include>