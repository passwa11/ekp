<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogSystem" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 开始时间 -->
		<list:data-column headerClass="width100" property="fdStartTime" title="${ lfn:message('sys-log:sysLogSystem.fdStartTime') }">
		</list:data-column>
		<!-- 结束时间 -->
		<list:data-column headerClass="width100" property="fdEndTime" title="${ lfn:message('sys-log:sysLogSystem.fdEndTime') }">
		</list:data-column>
		<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
		<!-- 标题 -->
		<list:data-column headerClass="width100" property="fdSubject" title="${ lfn:message('sys-log:sysLogSystem.fdSubject') }">
		</list:data-column>
		<% } %>
		<!-- 服务类 -->
		<list:data-column headerClass="width100" property="fdServiceBean" title="${ lfn:message('sys-log:sysLogSystem.fdServiceBean') }">
		</list:data-column>
		<!-- 执行结果 -->
		<list:data-column headerClass="width100" col="fdSuccess" title="${ lfn:message('sys-log:sysLogSystem.fdSuccess') }">
			<sunbor:enumsShow value="${sysLogSystem.fdSuccess}" enumsType="sysLogSystem_enum_fdSuccess" />
		</list:data-column>
		<!-- IP地址 -->
		<list:data-column headerClass="width80" property="fdClientIp" title="${ lfn:message('sys-log:sysLogSystem.fdIp') }">
		</list:data-column>
		<!-- 类型 -->
		<list:data-column headerClass="width80" col="fdType" title="${ lfn:message('sys-log:sysLogSystem.fdType') }">
			<sunbor:enumsShow value="${sysLogSystem.fdSuccess}" enumsType="sysLogSystem_enum_fdType" />
		</list:data-column>
		<!-- 任务历时 -->
		<list:data-column headerClass="width80" col="fdTaskDuration" title="${ lfn:message('sys-log:sysLogSystem.fdTaskDuration') }">
			${sysLogSystem.fdTaskDuration/1000 }${ lfn:message('sys-log:sysLogSystem.fdTaskDuration.unit') }
		</list:data-column>
		<!-- 备注 -->
		<list:data-column headerClass="width80" property="fdDesc" title="${ lfn:message('sys-log:sysLogSystem.fdDesc') }">
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>