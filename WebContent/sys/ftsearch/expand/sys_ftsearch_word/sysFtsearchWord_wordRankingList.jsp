<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do">
	<div id="optBarDiv">
	</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="40pt">
					<bean:message key="page.serial"/>
				</td>
				<sunbor:column property="sysFtsearchWord.fdSearchWord">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchWord.fdSearchWord"/>
				</sunbor:column>
				<sunbor:column property="sysFtsearchWord.fdNum">
					<bean:message bundle="sys-ftsearch-expand" key="sysFtsearchWord.num"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFtsearchWord" varStatus="vstatus">
			<tr>
				<td>
					${vstatus.index+1}
				</td>
				<td width="30%">
					<c:out value="${sysFtsearchWord.fdSearchWord}" />
				</td>
				<td>
					<c:out value="${sysFtsearchWord.fdNum}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>