<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>	

<list:data>
	<list:data-columns var="sysRecycleLog" list="${queryPage.list }">
		<list:data-column col="fdId" property="fdModelId">
		</list:data-column>
		<list:data-column property="fdModelId" title="${ lfn:message('sys-recycle:sysRecycleLog.fdModelId') }">
		</list:data-column>
		<list:data-column col="fdOptType" headerStyle="width:60px" title="${ lfn:message('sys-recycle:sysRecycleLog.fdOptType') }" >
			<sunbor:enumsShow
				value="${sysRecycleLog.fdOptType}" enumsType="sysRecycle_fdOptType" />
		</list:data-column>
		<list:data-column property="docSubject" title="${ lfn:message('sys-recycle:sysRecycleLog.docSubject') }">
		</list:data-column>
		
		<list:data-column property="operator.fdName" headerStyle="width:60px" title="${ lfn:message('sys-recycle:sysRecycleLog.fdOperator') }">
		</list:data-column>
		
		<list:data-column property="fdOptDate" headerStyle="width:150px" title="${ lfn:message('sys-recycle:sysRecycleLog.fdOptDate') }">
		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>