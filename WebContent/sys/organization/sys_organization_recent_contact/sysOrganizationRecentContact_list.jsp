<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysOrganizationRecentContactForm, 'deleteall');">
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
				<sunbor:column property="sysOrganizationRecentContact.docCreateTime">
					<bean:message bundle="sys-organization" key="sysOrganizationRecentContact.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="sysOrganizationRecentContact.docCreator.fdName">
					<bean:message bundle="sys-organization" key="sysOrganizationRecentContact.docCreator"/>
				</sunbor:column>
				<sunbor:column property="sysOrganizationRecentContact.docAlteror.fdName">
					<bean:message bundle="sys-organization" key="sysOrganizationRecentContact.docAlteror"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysOrganizationRecentContact" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/organization/sys_organization_recent_contact/sysOrganizationRecentContact.do" />?method=view&fdId=${sysOrganizationRecentContact.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysOrganizationRecentContact.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<kmss:showDate value="${sysOrganizationRecentContact.docCreateTime}" />
				</td>
				<td>
					<c:out value="${sysOrganizationRecentContact.docCreator.fdName}" />
				</td>
				<td>
					<c:out value="${sysOrganizationRecentContact.docAlteror.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>