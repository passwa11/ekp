<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do?method=deleteall">
					<ui:button text="${ lfn:message('button.delete') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysWebserviceRestConfigForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do">
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
				<sunbor:column property="sysWebserviceRestConfig.fdName">
					<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysWebserviceRestConfig.fdPrefix">
					<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.fdPrefix"/>
				</sunbor:column>
				<sunbor:column property="sysWebserviceRestConfig.docCreator.fdName">
					<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docCreator"/>
				</sunbor:column>				
				<sunbor:column property="sysWebserviceRestConfig.docCreateTime">
					<bean:message bundle="sys-webservice2" key="sysWebserviceRestConfig.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysWebserviceRestConfig" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/webservice2/sys_webservice_rest_config/sysWebserviceRestConfig.do" />?method=view&fdId=${sysWebserviceRestConfig.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysWebserviceRestConfig.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysWebserviceRestConfig.fdName}" />
				</td>
				<td>
					<c:out value="${sysWebserviceRestConfig.fdPrefix}" />
				</td>
				<td>
					<c:out value="${sysWebserviceRestConfig.docCreator.fdName}" />
				</td>				
				<td>
					<kmss:showDate value="${sysWebserviceRestConfig.docCreateTime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>