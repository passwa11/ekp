<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="sysLogNotify" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 通知标题 -->
		<list:data-column headerClass="width100" property="fdSubject" title="${ lfn:message('sys-log:sysLogNotify.fdSubject') }">
		</list:data-column>
		<!-- 通知对象 -->
		<list:data-column headerClass="width100" property="fdNotifyTargets" title="${ lfn:message('sys-log:sysLogNotify.fdNotifyTargets') }">
		</list:data-column>
		<!-- 通知类型 -->
		<list:data-column headerClass="width100" property="fdNotifyType" title="${ lfn:message('sys-log:sysLogNotify.fdNotifyType') }">
		</list:data-column>
		<!-- 通知时间 -->
		<list:data-column headerClass="width100" property="fdCreateTime" title="${ lfn:message('sys-log:sysLogNotify.fdCreateTime') }">
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>