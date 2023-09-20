<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysFormJdbcDataSetCategoryForm, 'deleteall');">
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
				<sunbor:column property="sysFormJdbcDataSetCategory.fdName">
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSetCategory.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysFormJdbcDataSetCategory.fdHierarchyId">
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSetCategory.fdHierarchyId"/>
				</sunbor:column>
				<sunbor:column property="sysFormJdbcDataSetCategory.fdOrder">
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSetCategory.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="sysFormJdbcDataSetCategory.fdParent.fdName">
					<bean:message bundle="sys-xform-maindata" key="sysFormJdbcDataSetCategory.fdParent"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFormJdbcDataSetCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do" />?method=view&fdId=${sysFormJdbcDataSetCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysFormJdbcDataSetCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysFormJdbcDataSetCategory.fdName}" />
				</td>
				<td>
					<c:out value="${sysFormJdbcDataSetCategory.fdHierarchyId}" />
				</td>
				<td>
					<c:out value="${sysFormJdbcDataSetCategory.fdOrder}" />
				</td>
				<td>
					<c:out value="${sysFormJdbcDataSetCategory.fdParent.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>