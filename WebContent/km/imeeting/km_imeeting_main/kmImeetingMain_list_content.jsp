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
				<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
				<td width="40pt"><bean:message key="page.serial" /></td>
				<sunbor:column property="kmImeetingMain.fdName">
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdName" />
				</sunbor:column>
				<sunbor:column property="kmImeetingMain.docCreator.fdName">
					<bean:message bundle="km-imeeting" key="kmImeetingMain.docCreator" />
				</sunbor:column>
				<sunbor:column property="kmImeetingMain.fdImeetingTemplate.docCategory.fdName">
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdTemplate" />
				</sunbor:column>
				<sunbor:column property="kmImeetingMain.docStatus">
					<bean:message bundle="km-imeeting" key="kmImeetingMain.docStatus" />
				</sunbor:column>
				<sunbor:column property="kmImeetingMain.fdHoldDate">
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDate" />
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="kmImeetingMain"
			varStatus="vstatus">
			<tr kmss_href="<c:url value="/km/imeeting/km_imeeting_main/kmImeetingMain.do" />?method=view&fdId=${kmImeetingMain.fdId}">
				<td><input type="checkbox" name="List_Selected" value="${kmImeetingMain.fdId}"></td>
				<td>${vstatus.index+1}</td>
				<td kmss_wordlength="50"><c:out value="${kmImeetingMain.fdName}" /></td>
				<td><c:out value="${kmImeetingMain.docCreator.fdName}" /></td>
				<td><c:out value="${kmImeetingMain.fdTemplate.docCategory.fdName}" /></td>
				<td><sunbor:enumsShow value="${kmImeetingMain.docStatus}" enumsType="km_imeeting_main_doc_status" /></td>
				<td><kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="datetime" /></td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
