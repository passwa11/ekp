<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do?method=deleteall">
					<ui:button text="${ lfn:message('button.delete') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.fsscCtripAppMessageForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do">
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
				<sunbor:column property="fsscCtripAppMessage.fdDockingMarkText">
					<bean:message bundle="fssc-ctrip" key="fsscCtripAppMessage.fdDockingMark"/>
				</sunbor:column>
				<sunbor:column property="fsscCtripAppMessage.fdAppKey">
					<bean:message bundle="fssc-ctrip" key="fsscCtripAppMessage.fdAppKey"/>
				</sunbor:column>
				<sunbor:column property="fsscCtripAppMessage.fdCorpId">
					<bean:message bundle="fssc-ctrip" key="fsscCtripAppMessage.fdCorpId"/>
				</sunbor:column>
				<sunbor:column property="fsscCtripAppMessage.fdEmText">
					<bean:message bundle="fssc-ctrip" key="fsscCtripAppMessage.fdEmId"/>
				</sunbor:column>
				<sunbor:column property="fsscCtripAppMessage.fdCompanyName">
					<bean:message bundle="fssc-ctrip" key="fsscCtripAppMessage.fdCompanyName"/>
				</sunbor:column>
				<sunbor:column property="fsscCtripAppMessage.docCreator.fdName">
					<bean:message bundle="fssc-ctrip" key="fsscCtripAppMessage.docCreator"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="fsscCtripAppMessage" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do" />?method=view&fdId=${fsscCtripAppMessage.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${fsscCtripAppMessage.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${fsscCtripAppMessage.fdDockingMarkText}" />
				</td>
				<td>
					<c:out value="${fsscCtripAppMessage.fdAppKey}" />
				</td>
				<td>
					<c:out value="${fsscCtripAppMessage.fdCorpId}" />
				</td>
				<td>
					<c:out value="${fsscCtripAppMessage.fdEmText}" />
				</td>
				<td>
					<c:out value="${fsscCtripAppMessage.fdCompanyName}" />
				</td>
				<td>
					<c:out value="${fsscCtripAppMessage.docCreator.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>
