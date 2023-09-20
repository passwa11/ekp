<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do?method=deleteall">
					<ui:button text="${ lfn:message('button.delete') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.hrStaffFileAuthorForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do">
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
				<sunbor:column property="hrStaffFileAuthor.fdName">
					<bean:message bundle="hr-staff" key="hrStaffFileAuthor.fdName"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="hrStaffFileAuthor" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do" />?method=view&fdId=${hrStaffFileAuthor.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${hrStaffFileAuthor.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${hrStaffFileAuthor.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>