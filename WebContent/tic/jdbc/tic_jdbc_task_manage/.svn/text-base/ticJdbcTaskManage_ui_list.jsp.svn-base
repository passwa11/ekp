<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="ticJdbcTaskManage" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column col="fdSubject" title="${ lfn:message('tic-jdbc:ticJdbcTaskManage.fdSubject') }" style="text-align:center" escape="false">
			<span class="com_subject"><c:out value="${ticJdbcTaskManage.fdSubject}" /></span>
		</list:data-column>
		<list:data-column col="fdCronExpression" title="${ lfn:message('sys-quartz:sysQuartzJob.fdCronExpression') }" escape="false" style="text-align:center;">
			<c:import url="/tic/jdbc/tic_jdbc_task_manage/ticJdbcTaskManage_showCronExpression.jsp" charEncoding="UTF-8">
				<c:param name="value" value="${ticJdbcTaskManage.fdCronExpression}" />
			</c:import>
		</list:data-column>
		<list:data-column col="fdLink" title="${ lfn:message('sys-quartz:sysQuartzJob.fdLink') }" escape="false" style="text-align:center;">
			<c:if test="${ticJdbcTaskManage.fdLink!=null && ticJdbcTaskManage.fdLink!=''}">
				<a href="<c:url value="${ticJdbcTaskManage.fdLink}" />" target="_blank">
					<bean:write name="ticJdbcTaskManage" property="fdUseExplain"/> 
				</a>
			</c:if>
		</list:data-column>
		<list:data-column col="fdRunType" title="${ lfn:message('sys-quartz:sysQuartzJob.fdRunType') }" escape="false" style="text-align:center;">
			<sunbor:enumsShow value="${ticJdbcTaskManage.fdRunType}" enumsType="sysQuartzJob_fdRunType" />
		</list:data-column>
		<list:data-column col="fdIsEnabled" title="${ lfn:message('sys-quartz:sysQuartzJob.fdEnabled') }">
			<sunbor:enumsShow value="${ticJdbcTaskManage.fdIsEnabled}" enumsType="common_yesno" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
