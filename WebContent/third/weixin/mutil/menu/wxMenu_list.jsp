<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/third/wxwork/mutil/menu/wxworkMenuDefine.do">
	<div id="optBarDiv">
	<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/third/wxwork/mutil/menu/wxworkMenuDefine.do" />?method=add');">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.wxMenuForm, 'deleteall');">
	</kmss:authShow>
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
				<sunbor:column property="wxMenuModel.fdAgentId">
					<bean:message bundle="third-weixin-mutil" key="third.wx.menu.agentId"/>
				</sunbor:column>
				<sunbor:column property="wxMenuModel.fdAgentName">
					<bean:message bundle="third-weixin-mutil" key="third.wx.menu.agentName"/>
				</sunbor:column>
				<td>
					<bean:message bundle="third-weixin-mutil" key="third.wx.menu.btn.publish.title"/>
				</td>

			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="wxMenuModel" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/third/wxwork/mutil/menu/wxworkMenuDefine.do" />?method=view&fdId=${wxMenuModel.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${wxMenuModel.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${wxMenuModel.fdAgentId}" />
				</td>
				<td>
					<c:out value="${wxMenuModel.fdAgentName}" />
				</td>

				<td>
					<c:if test="${wxMenuModel.fdPublished eq '1'}">
						<bean:message bundle="third-weixin-mutil" key="third.wx.menu.btn.publish.yes"/>
					</c:if>
					<c:if test="${empty wxMenuModel.fdPublished}">
						<bean:message bundle="third-weixin-mutil" key="third.wx.menu.btn.publish.no"/>
					</c:if>
				</td>

			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
