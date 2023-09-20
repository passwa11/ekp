<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/core/provider/tic_core_iface_impl/ticCoreIfaceImpl.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/provider/tic_core_iface_impl/ticCoreIfaceImpl.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/provider/tic_core_iface_impl/ticCoreIfaceImpl.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/core/provider/tic_core_iface_impl/ticCoreIfaceImpl.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreIfaceImplForm, 'deleteall');">
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
				<sunbor:column property="ticCoreIfaceImpl.fdName">
					<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.fdName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreIfaceImpl.fdImplRefName">
					<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.fdImplRefName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreIfaceImpl.ticCoreIface.fdId">
					<bean:message bundle="tic-core-provider" key="ticCoreIfaceImpl.ticCoreIface"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreIfaceImpl" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/provider/tic_core_iface_impl/ticCoreIfaceImpl.do" />?method=view&fdId=${ticCoreIfaceImpl.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreIfaceImpl.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticCoreIfaceImpl.fdName}" />
				</td>
				<td>
					<c:out value="${ticCoreIfaceImpl.fdImplRefName}" />
				</td>
				<td>
					<c:out value="${ticCoreIfaceImpl.ticCoreIface.fdIfaceName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>