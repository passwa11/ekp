<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}" varIndex="index">
		<list:data-column style="width:35px;" col="index" title="${ lfn:message('page.serial') }">
			${index+1}
		</list:data-column>
		<!-- 文件标题 -->
		<list:data-column property="fdFileTitle" title="${ lfn:message('sys-print:sysPrintLog.fdFileTitle') }">
		</list:data-column>
		<!-- 操作人 -->
		<list:data-column property="docCreator.fdName" title="${ lfn:message('sys-print:sysPrintLog.docCreator') }">
		</list:data-column>
		<!-- 部门-->
		<list:data-column property="fdDepartment.fdName" title="${ lfn:message('sys-organization:sysOrgElement.dept') }">
		</list:data-column> 
		<!-- 打印时间 -->
		<list:data-column style="width:200px;" col="docCreateTime" title="${ lfn:message('sys-print:sysPrintLog.docCreateTime') }">
		     <kmss:showDate value="${item.docCreateTime}" type="datetime"/>
		</list:data-column> 
		<!-- 客户端IP -->
		<list:data-column property="fdIp" title="${ lfn:message('sys-print:sysPrintLog.fdIp') }">
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
	
</list:data>