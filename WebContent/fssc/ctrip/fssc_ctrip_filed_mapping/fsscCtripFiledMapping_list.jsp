<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do?method=deleteall">
					<ui:button text="${ lfn:message('button.delete') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.fsscCtripFiledMappingForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
		<html:form action="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do">
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
						<sunbor:column property="fsscCtripFiledMapping.fdModelName">
							<bean:message bundle="fssc-ctrip" key="fsscCtripFiledMapping.fdModelName"/>
						</sunbor:column>
						<sunbor:column property="fsscCtripFiledMapping.docTemplateName">
							<bean:message bundle="fssc-ctrip" key="fsscCtripFiledMapping.docTemplateName"/>
						</sunbor:column>
					</sunbor:columnHead>
				</tr>
				<c:forEach items="${queryPage.list}" var="fsscCtripFiledMapping" varStatus="vstatus">
					<tr
						kmss_href="<c:url value="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do" />?method=view&fdId=${fsscCtripFiledMapping.fdId}">
						<td>
							<input type="checkbox" name="List_Selected" value="${fsscCtripFiledMapping.fdId}">
						</td>
						<td>
							${vstatus.index+1}
						</td>
						<td>
							<c:out value="${fsscCtripFiledMapping.fdModelName}" />
						</td>
						<td>
							<c:out value="${fsscCtripFiledMapping.docTemplateName}" />
						</td>
					</tr>
				</c:forEach>
			</table>
			<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
		</c:if>
		</html:form>
	</template:replace>
</template:include>
