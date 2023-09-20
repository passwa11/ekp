<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/rest/connector/tic_rest_setting/ticRestSetting.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/rest/connector/tic_rest_setting/ticRestSetting.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/rest/connector/tic_rest_setting/ticRestSetting.do" />?method=add&categoryId=${param.categoryId}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/rest/connector/tic_rest_setting/ticRestSetting.do?method=deleteall">
			<input type="button" value="<bean:message key="button.delete"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticRestSettingForm, 'deleteall');">
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
				<sunbor:column property="ticRestSetting.docSubject">
					<bean:message bundle="tic-rest-connector" key="ticRestSetting.docSubject"/>
				</sunbor:column>
				
				<sunbor:column property="ticRestSetting.settCategory">
					<bean:message bundle="tic-rest-connector" key="ticRestSetting.settCategory"/>
				</sunbor:column>
				
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticRestSetting" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/rest/connector/tic_rest_setting/ticRestSetting.do" />?method=view&fdId=${ticRestSetting.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticRestSetting.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticRestSetting.docSubject}" />
				</td>			
				<td>
				      <c:out value="${ticRestSetting.settCategory.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
