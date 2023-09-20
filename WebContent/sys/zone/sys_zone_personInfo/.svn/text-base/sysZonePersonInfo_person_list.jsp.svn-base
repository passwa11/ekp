<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar" >
			<ui:toolbar layout="sys.ui.toolbar.float">
				<kmss:auth requestURL="/sys/zone/sys_zone_person_multi_resume/sysZonePersonMultiResume.do?method=add"
						requestMethod="GET">
					<ui:button text="${lfn:message('sys-zone:sysZonePersonInfo.resume.upload')}" 
							onclick="batchAdd()">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/zone/sys_zone_private_change/sysZonePrivateChange.do?method=editPrivate" requestMethod="GET">
					<ui:button text="${lfn:message('sys-zone:sysZonePersonInfo.modify.privacy')}" onclick="changePrivate();"></ui:button>
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
						<%--
						<sunbor:column property="sysOrgPerson.fdOrder">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdOrder" />
						</sunbor:column>
						  --%>
						<sunbor:column property="sysOrgPerson.fdName">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdName" />
						</sunbor:column>
						<%--
						<sunbor:column property="sysOrgPerson.fdLoginName">
							<bean:message bundle="sys-organization" key="sysOrgPerson.fdLoginName" />
						</sunbor:column>
						--%>
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
							${lfn:message('sys-zone:sysZonePersonInfo.privacy')}
						</td>
						<td>
							${lfn:message('sys-zone:sysZonePersonInfo.resume')}
						</td>
						<td>
							${lfn:message('sys-zone:sysZonePersonInfo.operation')}
						</td>
						<td>
							${lfn:message('sys-zone:sysZonePersonInfo.person')}
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
						<td>
							<kmss:authShow roles="ROLE_SYSZONE_ADMIN">
								<bean:write name="sysOrgPerson" property="fdEmail" />
							</kmss:authShow>
						</td>
						<td>
							<kmss:authShow roles="ROLE_SYSZONE_ADMIN">
								<bean:write name="sysOrgPerson" property="fdMobileNo" />
							</kmss:authShow>
						</td>
						<td width="60px">
								<a target="_blank" class="com_btn_link" style="color:#37ace1;" href="${KMSS_Parameter_ContextPath}sys/zone/sys_zone_private_change/sysZonePrivateChange.do?method=editPrivate&fdIds=${sysOrgPerson.fdId}">
									${lfn:message('sys-zone:sysZonePersonInfo.privacy')}
								</a>
						</td>
						<td>
						<c:choose>
						<c:when test="${resumeJson[sysOrgPerson.fdId] == 'true'}">${lfn:message('sys-zone:sysZonePersonInfo.yes')}</c:when>
						<c:otherwise>${lfn:message('sys-zone:sysZonePersonInfo.no')}</c:otherwise></c:choose>
						</td>
						<td width="120px">
							<a class="com_btn_link" style="color:#37ace1;" href="${KMSS_Parameter_ContextPath}sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=modifyOtherResume&personId=${sysOrgPerson.fdId}"
							   target="_blank" >
								${lfn:message('sys-zone:sysZonePersonInfo.resume.modify')}
							</a>
						</td>
						<kmss:auth requestURL="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=modifyOtherPhoto&personId=${sysOrgPerson.fdId}" requestMethod="GET">
						<td onclick="openUpload('${sysOrgPerson.fdId}');">
							<img alt="<bean:message key="sysZonePersonImg.upload" bundle="sys-zone"/>" title="<bean:message key="sysZonePersonImg.upload" bundle="sys-zone"/>"
								style="width: 24px;height: 24px;" src="${KMSS_Parameter_ContextPath}sys/zone/resource/images/upload_icon_person.png">
						</td>
						</kmss:auth>
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
				seajs.use(["lui/dialog"],function(dialog){
					dialog.alert("<bean:message key="page.noSelect"/>");
				});
			} else {
				Com_OpenWindow("${LUI_ContextPath}/sys/zone/sys_zone_private_change/sysZonePrivateChange.do?method=editPrivate&fdIds=" + values, "_blank");
			}
		}
	</script>
	</template:replace>
</template:include>