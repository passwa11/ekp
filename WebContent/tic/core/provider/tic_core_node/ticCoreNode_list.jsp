<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/core/provider/tic_core_node/ticCoreNode.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/provider/tic_core_node/ticCoreNode.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/provider/tic_core_node/ticCoreNode.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/core/provider/tic_core_node/ticCoreNode.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreNodeForm, 'deleteall');">
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
				<sunbor:column property="ticCoreNode.fdNodeLevel">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeLevel"/>
				</sunbor:column>
				<sunbor:column property="ticCoreNode.fdNodeName">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreNode.fdNodePath">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodePath"/>
				</sunbor:column>
				<sunbor:column property="ticCoreNode.fdDataType">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdDataType"/>
				</sunbor:column>
				<sunbor:column property="ticCoreNode.fdNodeEnable">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeEnable"/>
				</sunbor:column>
				<sunbor:column property="ticCoreNode.fdDefName">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdDefName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreNode.fdNodeContent">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdNodeContent"/>
				</sunbor:column>
				<sunbor:column property="ticCoreNode.fdIface.fdId">
					<bean:message bundle="tic-core-provider" key="ticCoreNode.fdIface"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreNode" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/provider/tic_core_node/ticCoreNode.do" />?method=view&fdId=${ticCoreNode.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreNode.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticCoreNode.fdNodeLevel}" />
				</td>
				<td>
					<c:out value="${ticCoreNode.fdNodeName}" />
				</td>
				<td>
					<c:out value="${ticCoreNode.fdNodePath}" />
				</td>
				<td>
					<c:out value="${ticCoreNode.fdDataType}" />
				</td>
				<td>
					<xform:radio value="${ticCoreNode.fdNodeEnable}" property="fdNodeEnable" showStatus="view">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
				<td>
					<c:out value="${ticCoreNode.fdDefName}" />
				</td>
				<td>
					<c:out value="${ticCoreNode.fdNodeContent}" />
				</td>
				<td>
					<c:out value="${ticCoreNode.fdIface.fdId}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>