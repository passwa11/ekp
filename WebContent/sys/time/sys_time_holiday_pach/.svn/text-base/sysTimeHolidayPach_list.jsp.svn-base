<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<kmss:auth requestURL="/sys/time/sys_time_holiday_pach/sysTimeHolidayPach.do?method=add">
					<ui:button text="${ lfn:message('button.add') }" 
						onclick="Com_OpenWindow('${LUI_ContextPath}/sys/time/sys_time_holiday_pach/sysTimeHolidayPach.do?method=add');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/sys/time/sys_time_holiday_pach/sysTimeHolidayPach.do?method=deleteall">
					<ui:button text="${ lfn:message('button.delete') }"
						onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysTimeHolidayPachForm, 'deleteall');">
					</ui:button>
				</kmss:auth>
			</ui:toolbar>
	</template:replace>
 
	<template:replace name="content">
	
<html:form action="/sys/time/sys_time_holiday_pach/sysTimeHolidayPach.do">
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
				<sunbor:column property="sysTimeHolidayPach.fdName">
					<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdName"/>
				</sunbor:column>
				<sunbor:column property="sysTimeHolidayPach.fdPachTime">
					<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdPachTime"/>
				</sunbor:column>
				<sunbor:column property="sysTimeHolidayPach.fdHoliday.fdName">
					<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdHoliday"/>
				</sunbor:column>
				<sunbor:column property="sysTimeHolidayPach.fdDetail.fdName">
					<bean:message bundle="sys-time" key="sysTimeHolidayPach.fdDetail"/>
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysTimeHolidayPach" varStatus="vstatus">
			<tr
				kmss_href="<c:url value="/sys/time/sys_time_holiday_pach/sysTimeHolidayPach.do" />?method=view&fdId=${sysTimeHolidayPach.fdId}">
				<td>
					<input type="checkbox" name="List_Selected" value="${sysTimeHolidayPach.fdId}">
				</td>
				<td>
					${vstatus.index+1}
				</td>
				<td>
					<c:out value="${sysTimeHolidayPach.fdName}" />
				</td>
				<td>
					<kmss:showDate value="${sysTimeHolidayPach.fdPachTime}" />
				</td>
				<td>
					<c:out value="${sysTimeHolidayPach.fdHoliday.fdName}" />
				</td>
				<td>
					<c:out value="${sysTimeHolidayPach.fdDetail.fdName}" />
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
</html:form>

	</template:replace>
</template:include>