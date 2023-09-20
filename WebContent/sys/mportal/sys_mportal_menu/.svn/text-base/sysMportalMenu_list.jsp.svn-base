<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=deleteall">
					<ui:button text="${ lfn:message('button.deleteall') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysMportalMenuForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/sys/mportal/sys_mportal_menu/sysMportalMenu.do">
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
				<sunbor:column property="sysMportalMenu.docSubject">
					<bean:message bundle="sys-mportal" key="sysMportalMenu.docSubject"/>
				</sunbor:column>
				<sunbor:column property="sysMportalMenu.docCreator">
					创建者
				</sunbor:column>
				<sunbor:column property="sysMportalMenu.docCreateTime">
					<bean:message bundle="sys-mportal" key="sysMportalMenu.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysMportalMenu" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/mportal/sys_mportal_menu/sysMportalMenu.do" />?method=edit&fdId=${sysMportalMenu.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysMportalMenu.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysMportalMenu.docSubject}" />
				</td>
				<td style="min-width: 60px">
					<c:out value="${sysMportalMenu.docCreator.fdName}" />
				</td>
				<td style="min-width: 140px">
					<kmss:showDate value="${sysMportalMenu.docCreateTime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>