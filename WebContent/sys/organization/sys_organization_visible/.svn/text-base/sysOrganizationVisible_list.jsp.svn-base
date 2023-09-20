<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/organization/sys_organization_visible/sysOrganizationVisible.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/organization/sys_organization_visible/sysOrganizationVisible.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/organization/sys_organization_visible/sysOrganizationVisible.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/organization/sys_organization_visible/sysOrganizationVisible.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysOrganizationVisibleForm, 'deleteall');">
		</kmss:auth>
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="10pt">
					<input type="checkbox" name="List_Tongle">
				</td>
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="sysOrganizationVisible.docCreateTime">
					<bean:message bundle="sys-organization" key="sysOrganizationVisible.visiblePrincipals"/>
				</sunbor:column>
				<sunbor:column property="sysOrganizationVisible.docAlterTime">
					<bean:message bundle="sys-organization" key="sysOrganizationVisible.visibleSubordinates"/>
				</sunbor:column>
				<sunbor:column property="sysOrganizationVisible.fdDescription">
					<bean:message bundle="sys-organization" key="sysOrganizationVisible.fdDescription"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysOrganizationVisible" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/organization/sys_organization_visible/sysOrganizationVisible.do" />?method=view&fdId=${sysOrganizationVisible.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysOrganizationVisible.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:forEach items="${sysOrganizationVisible.visiblePrincipals}" var="principal" varStatus="status">
						${principal.fdName};
					</c:forEach>
				</td>
				<td>
					<c:forEach items="${sysOrganizationVisible.visibleSubordinates}" var="subordinate" varStatus="status">
						${subordinate.fdName};
					</c:forEach>
				</td>
				<td>
					<c:out value="${sysOrganizationVisible.fdDescription}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>