<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do" />?method=add&fdtemplatId=${param.categoryId}');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticJdbcMappManageForm, 'deleteall');">
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
				<sunbor:column property="ticJdbcMappManage.docSubject">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.docSubject"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdDataSource">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdDataSource"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdIsEnabled">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdIsEnabledStatus"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdDataSourceSql">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdDataSourceSql"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdTargetSource">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdTargetSource"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.fdTargetSourceSelectedTable">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.fdTargetSourceSelectedTable"/>
				</sunbor:column>
				<sunbor:column property="ticJdbcMappManage.docCategory.fdName">
					<bean:message bundle="tic-jdbc" key="ticJdbcMappManage.docCategory"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticJdbcMappManage" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage.do" />?method=view&fdId=${ticJdbcMappManage.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticJdbcMappManage.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${ticJdbcMappManage.docSubject}" />
				</td>
				<td>
				 <!-- 
					<c:out value="${ticJdbcMappManage.fdDataSource}" />
				  -->
				  <c:out value="${dataSoure[ticJdbcMappManage.fdDataSource]}"/>	
				</td>
				<td>
					   <sunbor:enumsShow value="${ticJdbcMappManage.fdIsEnabled}" enumsType="common_yesno" />
				</td>
				<td>
					<c:out value="${ticJdbcMappManage.fdDataSourceSql}" />
				</td>
				<td>
				<!-- 
					<c:out value="${ticJdbcMappManage.fdTargetSource}" />
				-->
					  <c:out value="${dataSoure[ticJdbcMappManage.fdTargetSource]}"/>	
				</td>
				<td>
					<c:out value="${ticJdbcMappManage.fdTargetSourceSelectedTable}" />
				</td>
				<td>
					<c:out value="${ticJdbcMappManage.docCategory.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
 
</c:if>

</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>