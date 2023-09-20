<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.list">
	<c:if test="${empty JsParam.fdHolidayId}">
		<template:replace name="toolbar">
				<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
					<kmss:auth requestURL="/sys/time/sys_time_holiday_detail/sysTimeHolidayDetail.do?method=add">
						<ui:button text="${ lfn:message('button.add') }" 
							onclick="Com_OpenWindow('${LUI_ContextPath}/sys/time/sys_time_holiday_detail/sysTimeHolidayDetail.do?method=add');">
						</ui:button>
					</kmss:auth>
					<kmss:auth requestURL="/sys/time/sys_time_holiday_detail/sysTimeHolidayDetail.do?method=deleteall">
						<ui:button text="${ lfn:message('button.delete') }"
							onclick="if(!List_ConfirmDel())return;Com_Submit(document.sysTimeHolidayDetailForm, 'deleteall');">
						</ui:button>
					</kmss:auth>
				</ui:toolbar>
		</template:replace>
	</c:if>
 
	<template:replace name="content">
<html:form action="/sys/time/sys_time_holiday_detail/sysTimeHolidayDetail.do">
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr>
			<sunbor:columnHead htmlTag="td">
						<td width="40pt"class="data-lui-mark-row-id">
					<bean:message key="page.serial"/>
				</td>
				<td width="20%" class="data-lui-mark-row-id">
					<bean:message bundle="sys-time" key="sysTimeHolidayDetail.fdName"/>
				</td>
				<c:if test="${param.type=='vacation' }">
				<td width="70%" class="data-lui-mark-row-id">
					<bean:message bundle="sys-time" key="sysTimeHoliday.holiday.day"/>
				</td>
				</c:if>
				<c:if test="${param.type=='pachwork' }">
				<td width="70%" class="data-lui-mark-row-id">
					<bean:message bundle="sys-time" key="sysTimeHoliday.mend.work.day"/>
				</td>
				</c:if>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="sysTimeHolidayDetail" varStatus="vstatus">
			<%-- <tr
				kmss_href="<c:url value="/sys/time/sys_time_holiday_detail/sysTimeHolidayDetail.do" />?method=view&fdId=${sysTimeHolidayDetail.fdId}"> --%>
			<tr>
				<td>
					${vstatus.index+1}
				</td>
				<td align="center">
					<c:out value="${sysTimeHolidayDetail.fdName}" />
				</td>
				<c:if test="${param.type=='vacation' }">
				<td align="center">
					<kmss:showDate value="${sysTimeHolidayDetail.fdStartDay}" type="date"/>
							—
					<kmss:showDate value="${sysTimeHolidayDetail.fdEndDay}" type="date"/>
				</td>
				</c:if>
				<c:if test="${param.type=='pachwork' }">
				<td align="center">
					<c:out value="${sysTimeHolidayDetail.fdPatchDay}" />
				</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	<center>
		<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
	</center>
</c:if>
</html:form>
	<style>
		.pageNav_tb {
		    width: 20%;
		    white-space:nowrap;
		}
	</style>
	</template:replace>
</template:include>