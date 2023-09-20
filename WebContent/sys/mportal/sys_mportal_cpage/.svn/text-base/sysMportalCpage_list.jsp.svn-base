<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=add');">
					</ui:button> 
				</kmss:auth>
				<kmss:auth requestURL="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=deleteall">
					<ui:button text="${ lfn:message('button.deleteall') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysMportalCpageForm, 'deleteall');">
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
<html:form action="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do" style="margin:0 7px;"> 
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
				<sunbor:column property="sysMportalCpage.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalCpage.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysMportalCpage.fdEnabled">
					<bean:message bundle="sys-mportal" key="sysMportalCpage.fdEnabled"/>
				</sunbor:column>
				<sunbor:column property="sysMportalCpage.docCreator.fdName">
					<bean:message bundle="sys-mportal" key="sysMportalCpage.docCreator"/>
				</sunbor:column>
				<sunbor:column property="sysMportalCpage.docCreateTime">
					<bean:message bundle="sys-mportal" key="sysMportalCpage.docCreateTime"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysMportalCpage" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do" />?method=edit&fdId=${sysMportalCpage.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysMportalCpage.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysMportalCpage.fdName}" />
				</td>
				<td>
					<sunbor:enumsShow enumsType="common_yesno"
						 value="${sysMportalCpage.fdEnabled}"></sunbor:enumsShow>
				</td>
				<td>
					<c:out value="${sysMportalCpage.docCreator.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysMportalCpage.docCreateTime}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>