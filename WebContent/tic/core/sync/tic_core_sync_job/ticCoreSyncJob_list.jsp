<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="
	java.util.Date,
	com.landray.kmss.util.DateUtil,
	com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.tic.core.sync.model.TicCoreSyncJob"%>
<%@ page import="com.landray.kmss.sys.quartz.scheduler.CronExpression"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="TicCorepSyncJob" list="${queryPage.list }">
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--标题--%>
		<list:data-column property="fdSubject" title="${ lfn:message('sys-quartz:sysQuartzJob.fdSubject') }" style="text-align:center" escape="false">
			<span class="com_subject">
			</span>
		</list:data-column>
		<list:data-column col="fdCronExpression" title="${ lfn:message('sys-quartz:sysQuartzJob.fdCronExpression') }" escape="false" style="text-align:center;">
			<c:import url="/tic/core/sync/tic_core_sync_job/ticCoreSyncJob_showCronExpression.jsp" charEncoding="UTF-8">
				<c:param name="value" value="${TicCorepSyncJob.fdCronExpression}" />
			</c:import>
		</list:data-column>
<%-- 		<list:data-column col="fdLink" title="${ lfn:message('sys-quartz:sysQuartzJob.fdLink') }" escape="false" style="text-align:center;">
			<c:if test="${TicCorepSyncJob.fdLink!=null && TicCorepSyncJob.fdLink!=''}">
				<a href="<c:url value="${TicCorepSyncJob.fdLink}" />" target="_blank">
					<bean:write name="TicCorepSyncJob" property="fdUseExplain"/> 
				</a>
			</c:if>
		</list:data-column> --%>
		<list:data-column col="fdCategory.fdName" title="${ lfn:message('tic-core-sync:ticCoreSyncJob.fdCategory') }" escape="false" style="text-align:center;">
			<c:out value="${TicCorepSyncJob.fdCategory.fdName}" />
		</list:data-column>
		<list:data-column  property="fdUseExplain" title="${ lfn:message('tic-core-sync:ticCoreSyncJob.fdUseExplain') }" escape="false" style="text-align:center;">
		</list:data-column>
		<list:data-column col="fdEnabled" title="${ lfn:message('sys-quartz:sysQuartzJob.fdEnabled') }">
			<sunbor:enumsShow value="${TicCorepSyncJob.fdEnabled}" enumsType="common_yesno" />
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows  }">
	</list:data-paging>
</list:data>

