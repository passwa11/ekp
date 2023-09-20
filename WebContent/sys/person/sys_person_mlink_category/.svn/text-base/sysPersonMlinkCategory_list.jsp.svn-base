<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do?method=deleteall">
					<ui:button text="${ lfn:message('button.deleteall') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysPersonMlinkCategoryForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do">
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
				<sunbor:column property="sysPersonMlinkCategory.fdName">
					<bean:message bundle="sys-person" key="sysPersonMlinkCategory.fdName"/>
				</sunbor:column>
				
				<sunbor:column property="sysPersonMlinkCategory.fdEnabled">
					<bean:message bundle="sys-person" key="sysPersonMlinkCategory.fdEnabled"/>
				</sunbor:column>
				<sunbor:column property="sysPersonMlinkCategory.fdOrder">
					<bean:message bundle="sys-person" key="sysPersonMlinkCategory.fdOrder"/>
				</sunbor:column>
				
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysPersonMlinkCategory" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do" />?method=edit&fdId=${sysPersonMlinkCategory.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysPersonMlinkCategory.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysPersonMlinkCategory.fdName}" />
				</td>
				<td>
					<sunbor:enumsShow enumsType="common_yesno" value="${sysPersonMlinkCategory.fdEnabled}"></sunbor:enumsShow>
				</td>
				<td>
					<c:out value="${sysPersonMlinkCategory.fdOrder}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>