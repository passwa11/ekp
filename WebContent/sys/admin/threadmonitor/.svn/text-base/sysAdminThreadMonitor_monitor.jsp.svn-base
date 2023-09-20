<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="threadDetail" list="${threadDetails}">
		<!-- 线程名 -->
		<list:data-column headerClass="width100"  property="threadName" title="${lfn:message('sys-admin:sysAdminThreadMonitor.threadName') }">
		</list:data-column>
		<!-- 请求地址 -->
		<list:data-column headerClass="width200" property="url" title="${lfn:message('sys-admin:sysAdminThreadMonitor.requestAddress') }">
		</list:data-column>
		<!-- 用户 -->
		<list:data-column headerClass="width100" property="user" title="${lfn:message('sys-admin:sysAdminThreadMonitor.user') }">
		</list:data-column>
		<!-- IP -->
		<list:data-column headerClass="width100" property="ip" title="IP">
		</list:data-column>
		<!-- 历时（毫秒）-->
		<list:data-column headerClass="width80" col="time" title="${lfn:message('sys-admin:sysAdminThreadMonitor.duration') }">
			<kmss:showDecimalNumber pattern="#,###" value="${threadDetail.timeDuration}" />
		</list:data-column>
	</list:data-columns>
</list:data>