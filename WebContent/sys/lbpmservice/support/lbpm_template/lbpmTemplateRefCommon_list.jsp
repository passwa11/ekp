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
							/* #134639-当流程模板未被引用时，被引用模板信息下提示展示不全（20px的高度改成了170px） */
							window.frameElement.style.height = (document.forms[0].offsetHeight + 170)+"px";
						}, 200);
			});
</script>
<html:form action="${actionUrl}">
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

				<sunbor:column property="lbpmTemplate.fdName">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.templateName"/>
				</sunbor:column>

				<sunbor:column property="lbpmTemplate.fdCreator">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdCreator"/>
				</sunbor:column>


				<sunbor:column property="lbpmTemplate.fdCreateTime">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdCreateTime"/>
				</sunbor:column>

			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="lbpmTemplate"
			varStatus="vstatus">
			<c:if test="${isSimpleCategory == true}">
				<tr kmss_href="<c:url value="${actionUrl}" />?method=edit&fdModelName=${templateModelName}&fdId=${lbpmTemplate.fdModelId}">
			</c:if>
			<c:if test="${isSimpleCategory != true}">
				<tr kmss_href="<c:url value="${actionUrl}" />?method=view&fdId=${lbpmTemplate.fdModelId}">
			</c:if>
				<td>${vstatus.index+1}</td>
				<td><c:if test="${not empty subjectMap[lbpmTemplate.fdId]}">
	             <span class="com_subject">${subjectMap[lbpmTemplate.fdId]}</span>
	        </c:if></td>
				<td><c:out value="${lbpmTemplate.fdCreator.fdName}" /></td>
				<td><kmss:showDate value="${lbpmTemplate.fdCreateTime}" type="date"/></td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp"%>
	<%
	}
	%>
</html:form>

<%@ include file="/resource/jsp/list_down.jsp"%>
