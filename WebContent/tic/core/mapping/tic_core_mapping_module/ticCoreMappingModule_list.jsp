<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<html:form action="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do">
	<div id="optBarDiv">
		<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="Com_OpenWindow('<c:url value="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do" />?method=add');">
		</kmss:auth>
		<kmss:auth requestURL="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do?method=deleteall">
			<input type="button" value="<bean:message key="button.deleteall"/>"
				onclick="if(!List_ConfirmDel())return;Com_Submit(document.ticCoreMappingModuleForm, 'deleteall');">
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
				<%-- 
				<sunbor:column property="ticCoreMappingModule.fdServerName">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdServerName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingModule.fdServerIp">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdServerIp"/>
				</sunbor:column>
				 --%>
				<sunbor:column property="ticCoreMappingModule.fdModuleName">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdModuleName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingModule.fdTemplateName">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdTemplateName"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingModule.fdMainModelName">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdMainModelName"/>
				</sunbor:column>
					<sunbor:column property="ticCoreMappingModule.fdCate">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.cate.type"/>
				</sunbor:column>
				<sunbor:column property="ticCoreMappingModule.fdUse">
					<bean:message bundle="tic-core-mapping" key="ticCoreMappingModule.fdUse"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="ticCoreMappingModule" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/tic/core/mapping/tic_core_mapping_module/ticCoreMappingModule.do" />?method=view&fdId=${ticCoreMappingModule.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${ticCoreMappingModule.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<%--
				<td>
					<c:out value="${ticCoreMappingModule.fdServerName}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingModule.fdServerIp}" />
				</td>
				--%>
				<td>
					<c:out value="${ticCoreMappingModule.fdModuleName}" />
				</td>
				<td>
					<c:out value="${ticCoreMappingModule.fdTemplateName }" />
				</td>
					<td>
					<c:out value="${ticCoreMappingModule.fdMainModelName}" />
				</td>
					<td>
					<sunbor:enumsShow value="${ticCoreMappingModule.fdCate}" enumsType="ticCoreMappingModule_cate"  />
				</td>
				<td>
					<sunbor:enumsShow value="${ticCoreMappingModule.fdUse}" enumsType="common_yesno" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>
<%@ include file="/resource/jsp/list_down.jsp"%>
