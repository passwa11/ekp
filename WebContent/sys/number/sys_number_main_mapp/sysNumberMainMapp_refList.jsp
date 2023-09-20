<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<script type="text/javascript">
	window.List_CheckSelect=null;//屏蔽“导出为初始数据”按钮
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
<html:form action="/sys/number/sys_number_main_mapp/sysNumberMainMapp.do">
	<%
	if (((Page) request.getAttribute("queryPage")).getTotalrows() == 0) {
	%>
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
	<%
	} else {
	%>
	
	<table id="List_ViewTable">
		<tr>
			<td width="10pt">
				<input type="checkbox" name="List_Tongle">
			</td>
			<td width="40pt">
				<bean:message key="page.serial"/>
			</td>
			<td>${ lfn:message('sys-number:sysNumberMain.templateName') }</td>
			<td>${ lfn:message('sys-number:sysNumberMain.docCreator') }</td>
			<td>${ lfn:message('sys-number:sysNumberMain.docCreateTime') }</td>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysNumberMainMapp" varStatus="vstatus">
			<c:choose>
				<c:when test="${not empty HtmlParam.fdKey}">
					<tr kmss_href="<c:url value="${url}" />?method=view&fdModelName=${sysNumberMainMapp.fdModelName}&fdId=${sysNumberMainMapp.fdModelId}&fdKey=${HtmlParam.fdKey}">
				</c:when>
				<c:otherwise>
					<tr kmss_href="<c:url value="${url}" />?method=view&fdModelName=${sysNumberMainMapp.fdModelName}&fdId=${sysNumberMainMapp.fdModelId}">
				</c:otherwise>
			</c:choose>
				<td>
					<input type="checkbox" name="List_Selected" value="${sysNumberMainMapp.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				
				<td>
					<c:out value="${nameList[vstatus.index]}" />
				</td>
				
				<td>
					<c:out value="${personList[vstatus.index].fdName}" />
				</td>
				
				<td>
					<c:out value="${timeList[vstatus.index]}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
</html:form>

<%@ include file="/resource/jsp/list_down.jsp"%>
