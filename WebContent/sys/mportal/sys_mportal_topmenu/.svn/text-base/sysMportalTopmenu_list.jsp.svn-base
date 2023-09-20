<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do?method=deleteall">
					<ui:button text="${ lfn:message('button.deleteall') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysMportalTopmenuForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do">
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
				<sunbor:column property="sysMportalTopmenu.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysMportalTopmenu.fdOrder">
					<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdOrder"/>
				</sunbor:column>
				<sunbor:column property="sysMportalTopmenu.fdUrl">
					<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdUrl"/>
				</sunbor:column>
				<sunbor:column property="sysMportalTopmenu.fdIcon">
					<bean:message bundle="sys-mportal" key="sysMportalTopmenu.fdIcon"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysMportalTopmenu" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do" />?method=view&fdId=${sysMportalTopmenu.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysMportalTopmenu.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysMportalTopmenu.fdName}" />
				</td>
				<td>
					<c:out value="${sysMportalTopmenu.fdOrder}" />
				</td>
				<td>
					<c:out value="${sysMportalTopmenu.fdUrl}" />
				</td>
				<td>
					<c:out value="${sysMportalTopmenu.fdIcon}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>