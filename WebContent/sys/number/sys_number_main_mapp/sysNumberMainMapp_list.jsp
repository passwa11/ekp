<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysNumberMainMappForm, 'deleteall');">
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
				<sunbor:column property="sysNumberMainMapp.fdModelId">
					<bean:message bundle="sys-number" key="sysNumberMainMapp.fdModelId"/>
				</sunbor:column>
				<sunbor:column property="sysNumberMainMapp.fdModelName">
					<bean:message bundle="sys-number" key="sysNumberMainMapp.fdModelName"/>
				</sunbor:column>
				<sunbor:column property="sysNumberMainMapp.fdNumber.fdName">
					<bean:message bundle="sys-number" key="sysNumberMainMapp.fdNumber"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysNumberMainMapp" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do" />?method=view&fdId=${sysNumberMainMapp.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysNumberMainMapp.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysNumberMainMapp.fdModelId}" />
				</td>
				<td>
					<c:out value="${sysNumberMainMapp.fdModelName}" />
				</td>
				<td>
					<c:out value="${sysNumberMainMapp.fdNumber.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>