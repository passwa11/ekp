<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script type="text/javascript">
	Com_AddEventListener(
			window,
			'load',
			function() {
				setTimeout(
						function() {
							window.frameElement.style.height = (document.forms[0].offsetHeight + 20)+"px";
						}, 200);
			});
</script>
<html:form action="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do">
	<%
	if (((Page) request.getAttribute("queryPage")).getTotalrows() == 0) {
	%>
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
	<%
	} else {
	%>
	
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
				<td width="40pt"><bean:message key="page.serial" /></td>

				<sunbor:column property="sysFormCommonTempHistory.fdTemplateEdition">
					版本号
				</sunbor:column>
				<sunbor:column property="sysFormCommonTempHistory.fdAlteror">
					修改者
				</sunbor:column>
				<sunbor:column property="sysFormCommonTempHistory.fdAlterTime">
					修改时间
				</sunbor:column>

			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysFormCommonTempHistory"
			varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/xform/sys_form_common_template_history/sysFormCommonTemplateHistory.do" />?method=viewHistory&fdId=${sysFormCommonTempHistory.fdId}&fdModelName=${sysFormCommonTempHistory.fdModelName}&fdMainModelName=${fdMainModelName}">
				<td>${vstatus.index+1}</td>
				<td><span>V</span><c:out value="${sysFormCommonTempHistory.fdTemplateEdition}" /></td>
				<!-- 兼容旧数据，以前的表结构没有修改者这个字段 -->
				<td><c:if test="${sysFormCommonTempHistory.fdAlteror ne null}"><c:out value="${sysFormCommonTempHistory.fdAlteror.fdName}" /></c:if></td>
				<td><kmss:showDate value="${sysFormCommonTempHistory.fdAlterTime}" type="date"/></td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
</html:form>

<%@ include file="/resource/jsp/list_down.jsp"%>
