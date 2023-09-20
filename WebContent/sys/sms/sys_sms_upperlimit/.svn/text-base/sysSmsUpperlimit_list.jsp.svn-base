<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do?method=add" requestMethod="GET">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do?method=deleteall" requestMethod="GET">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysSmsUpperlimitForm, 'deleteall');">
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
				<td width="10pt"><input type="checkbox" name="List_Tongle"></td>
				<td width="40pt"><bean:message key="page.serial"/></td>
				<sunbor:column property="sysSmsUpperlimit.fdUpperlimit">
					<bean:message  bundle="sys-sms" key="sysSmsUpperlimit.fdUpperlimit"/>
				</sunbor:column>
				<sunbor:column property="sysSmsUpperlimit.docCreateTime">
					<bean:message  bundle="sys-sms" key="sysSmsUpperlimit.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysSmsUpperlimit" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do" />?method=view&fdId=${sysSmsUpperlimit.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysSmsUpperlimit.fdId}">
				</td>
				<td>${vstatus.index+1}</td>
				<td>
					<c:out value="${sysSmsUpperlimit.fdUpperlimit}" />
				</td>
				<td>
					<kmss:showDate value="${sysSmsUpperlimit.docCreateTime}" type="datetime" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
