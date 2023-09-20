<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do">
	<div id="optBarDiv"><kmss:auth
		requestURL="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do?method=add"
		requestMethod="GET">
		<input type="button" value="<bean:message key="button.add"/>"
			onclick="Com_OpenWindow('<c:url value="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do" />?method=add');">
	</kmss:auth> <kmss:auth
		requestURL="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do?method=deleteall"
		requestMethod="GET">
		<input type="button" value="<bean:message key="button.deleteall"/>"
			onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysSmsSortofpeopleForm, 'deleteall');">
	</kmss:auth></div>
	<c:if test="${queryPage.totalrows==0}">
		<%@ include file="/resource/jsp/list_norecord.jsp"%>
	</c:if>
	<c:if test="${queryPage.totalrows>0}">
		<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
		<table id="List_ViewTable">
			<tr>
				<sunbor:columnHead htmlTag="td">
					<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
					<td width="40pt"><bean:message key="page.serial" /></td>
					<sunbor:column property="sysSmsSortofpeople.fdName">
						<bean:message bundle="sys-sms" key="sysSmsSortofpeople.fdName" />
					</sunbor:column>
					<sunbor:column property="sysSmsSortofpeople.docCreatorId">
						<bean:message bundle="sys-sms" key="sysSmsSortofpeople.docCreatorId" />
					</sunbor:column>
					<sunbor:column property="sysSmsSortofpeople.docCreateTime">
						<bean:message bundle="sys-sms"
							key="sysSmsSortofpeople.docCreateTime" />
					</sunbor:column>
					<sunbor:column property="sysSmsSortofpeople.fdOrder">
						<bean:message bundle="sys-sms" key="sysSmsSortofpeople.fdOrder" />
					</sunbor:column>
					<sunbor:column property="sysSmsSortofpeople.docContent">
						<bean:message bundle="sys-sms" key="sysSmsSortofpeople.docContent" />
					</sunbor:column>
				</sunbor:columnHead>
			</tr>
			<c:forEach items="${queryPage.list}" var="sysSmsSortofpeople"
				varStatus="vstatus">
				<tr
					kmss_href="<c:url value="/sys/sms/sys_sms_sortofpeople/sysSmsSortofpeople.do" />?method=view&fdId=${sysSmsSortofpeople.fdId}">
					<td><input type="checkbox" name="List_Selected"
						value="${sysSmsSortofpeople.fdId}"></td>
					<td>${vstatus.index+1}</td>
					<td><c:out value="${sysSmsSortofpeople.fdName}" /></td>
					<td><c:out value="${sysSmsSortofpeople.fdCreator.fdName}" /></td>
					<td><kmss:showDate value="${sysSmsSortofpeople.docCreateTime}"
						type="datetime" /></td>
					<td><c:out value="${sysSmsSortofpeople.fdOrder}" /></td>
					<td><c:out value="${sysSmsSortofpeople.docContent}" /></td>
				</tr>
			</c:forEach>
		</table>
		<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
