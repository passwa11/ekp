<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
if (((com.sunbor.web.tag.Page) request.getAttribute("queryPage")).getTotalrows() == 0) {
%>
<%@ include file="/resource/jsp/list_norecord.jsp"%>
<%
} else {
%>
<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
<table id="List_ViewTable">
	<tr>
		<sunbor:columnHead htmlTag="td">
			<td width="10px"><input
				type="checkbox"
				name="List_Tongle"></td>
			<td width="40px"><bean:message key="page.serial" /></td>
			<sunbor:column property="kmArchivesMain.docSubject">
				<bean:message
					bundle="km-archives"
					key="kmArchivesMain.docSubject" />
			</sunbor:column>
			<sunbor:column property="kmArchivesMain.docNumber">
				<bean:message
					bundle="km-archives"
					key="kmArchivesMain.docNumber" />
			</sunbor:column>
			<sunbor:column property="kmArchivesMain.fdUnit">
				<bean:message
					bundle="km-archives"
					key="kmArchivesMain.fdUnit" />
			</sunbor:column>
			<sunbor:column property="kmArchivesMain.fdStorekeeper.fdName">
				<bean:message
					bundle="km-archives"
					key="kmArchivesMain.fdStorekeeper" />
			</sunbor:column>
			<sunbor:column property="kmArchivesMain.fdValidityDate">
				<bean:message
					bundle="km-archives"
					key="kmArchivesMain.fdValidityDate" />
			</sunbor:column>
			<sunbor:column property="kmArchivesMain.docStatus">
				<bean:message
					bundle="km-archives"
					key="kmArchivesMain.docStatus" />
			</sunbor:column>
			<sunbor:column property="kmArchivesMain.docCreateTime">
				<bean:message
					bundle="km-archives"
					key="kmArchivesMain.docCreateTime" />
			</sunbor:column>
		</sunbor:columnHead>
	</tr>
	<c:forEach
		items="${queryPage.list}"
		var="kmArchivesMain"
		varStatus="vstatus">
		<tr kmss_href="<c:url value="/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=${kmArchivesMain.fdId}" />" 
			kmss_target="_blank">
			<td><input
				type="checkbox"
				name="List_Selected"
				value="${kmArchivesMain.fdId}"></td>
			<td>${vstatus.index+1}</td>
			<td kmss_wordlength="50"><c:out value="${kmArchivesMain.docSubject}" /></td>
			<td><c:out value="${kmArchivesMain.docNumber}" /></td>
			<td><c:out value="${kmArchivesMain.fdUnit.fdName}" /></td>
			<td><c:out value="${kmArchivesMain.fdStorekeeper.fdName}" /></td>
			<td><kmss:showDate value="${kmArchivesMain.fdValidityDate}" type="date" /></td>
			<td><sunbor:enumsShow value="${kmArchivesMain.docStatus}" enumsType="km_archives_doc_status" /></td></td>
			<td><kmss:showDate value="${kmArchivesMain.docCreateTime}" type="datetime" /></td>
		</tr>
	</c:forEach>
</table>
<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
<%
}
%>
