<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/mportal/sys_mportal_html/sysMportalHtml.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/mportal/sys_mportal_html/sysMportalHtml.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/mportal/sys_mportal_html/sysMportalHtml.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/mportal/sys_mportal_html/sysMportalHtml.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysMportalHtmlForm, 'deleteall');">
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
				<sunbor:column property="sysMportalHtml.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalHtml.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysMportalHtml.docCreator.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalHtml.docCreator"/>
				</sunbor:column>
				<sunbor:column property="sysMportalHtml.docCreateTime">
					<bean:message bundle="sys-mportal" key="sysMportalHtml.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysMportalHtml" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/mportal/sys_mportal_html/sysMportalHtml.do" />?method=edit&fdId=${sysMportalHtml.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysMportalHtml.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysMportalHtml.fdName}" />
				</td>
				<td>
					<c:out value="${sysMportalHtml.docCreator.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysMportalHtml.docCreateTime}" />
				</td>
				
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>