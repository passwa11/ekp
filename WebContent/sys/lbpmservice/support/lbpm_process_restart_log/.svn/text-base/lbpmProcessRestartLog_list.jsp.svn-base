<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="lbpmProcessRestartLog" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		     ${status+1}
		</list:data-column>
		<!--操作时间-->
		<list:data-column headerStyle="width:50%" property="docCreateTime"
				title="${ lfn:message('sys-lbpmservice-support:lbpm.lbpmProcessRestartLog.docCreateTime') }">
		</list:data-column>
		<!--操作者-->
		<list:data-column headerStyle="width:50%" property="docCreator.fdName"
				title="${ lfn:message('sys-lbpmservice-support:lbpm.lbpmProcessRestartLog.docCreator') }">
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>