<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/ftsearch/expand/sys_ftsearch_amb_participle/sysFtsearchAmbParticiple.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_amb_participle/sysFtsearchAmbParticiple.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/ftsearch/expand/sys_ftsearch_amb_participle/sysFtsearchAmbParticiple.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_amb_participle/sysFtsearchAmbParticiple.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysFtsearchAmbParticipleForm, 'deleteall');">
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
				<sunbor:column property="sysFtsearchAmbParticiple.fdParticiple">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchAmbParticiple.fdParticiple"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchAmbParticiple.fdParticipleResult">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchAmbParticiple.fdParticipleResult"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchAmbParticiple.docCreateTime">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchAmbParticiple.docCreateTime"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchAmbParticiple.docCreator.fdName">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchAmbParticiple.docCreator"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFtsearchAmbParticiple" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/ftsearch/expand/sys_ftsearch_amb_participle/sysFtsearchAmbParticiple.do" />?method=view&fdId=${sysFtsearchAmbParticiple.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysFtsearchAmbParticiple.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysFtsearchAmbParticiple.fdParticiple}" />
				</td>
				<td>
					<c:out value="${sysFtsearchAmbParticiple.fdParticipleResult}" />
				</td>
				<td>
					<kmss:showDate value="${sysFtsearchAmbParticiple.docCreateTime}" />
				</td>
				<td>
					<c:out value="${sysFtsearchAmbParticiple.docCreator.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>