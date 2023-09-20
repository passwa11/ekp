<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_page/sysMportalPage.do?method=add');">
					</ui:button> 
				</kmss:auth>
				<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=deleteall">
					<ui:button text="${ lfn:message('button.deleteall') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysMportalPageForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	<style>
		.txtlistpath {
			padding-left: 7px;
		}
	</style>
<html:form action="/sys/mportal/sys_mportal_page/sysMportalPage.do" style="margin:0 7px;"> 
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
				<sunbor:column property="sysMportalPage.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalPage.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysMportalPage.fdEnabled">
					<bean:message bundle="sys-mportal" key="sysMportalPage.fdEnabled"/>
				</sunbor:column>
				<sunbor:column property="sysMportalPage.docCreator.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalPage.docCreator"/>
				</sunbor:column>
				<sunbor:column property="sysMportalPage.docCreateTime">
					<bean:message bundle="sys-mportal" key="sysMportalPage.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysMportalPage" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/mportal/sys_mportal_page/sysMportalPage.do" />?method=edit&fdId=${sysMportalPage.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysMportalPage.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysMportalPage.fdName}" />
				</td>
				<td>
					<sunbor:enumsShow enumsType="common_yesno"
						 value="${sysMportalPage.fdEnabled}"></sunbor:enumsShow>
				</td>
				<td>
					<c:out value="${sysMportalPage.docCreator.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysMportalPage.docCreateTime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>