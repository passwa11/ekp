<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}" varIndex="index">
		<!-- 文件标题 -->
		<list:data-column col="fdFileName" title="${ lfn:message('sys-print:sysPrintLog.fdFileTitle') }">
			<c:out value="${item.fdFileTitle }"></c:out>
		</list:data-column>
		<!-- 操作人 -->
		<list:data-column col="fdName" title="${ lfn:message('sys-print:sysPrintLog.docCreator') }">
			<c:out value="${item.docCreator.fdName }"></c:out>
		</list:data-column>
		<!-- 操作人头像-->
		<list:data-column col="fdIcon" escape="false">
			<person:headimageUrl personId="${item.docCreator.fdId }" size="m" />
		</list:data-column>
		<!-- 部门-->
		<list:data-column col="fdDeptName" title="${ lfn:message('sys-organization:sysOrgElement.dept') }">
			<c:out value="${item.fdDepartment.fdName }"></c:out>
		</list:data-column> 
		<!-- 打印时间 -->
		<list:data-column style="width:200px;" col="fdCreateTime" title="${ lfn:message('sys-print:sysPrintLog.docCreateTime') }">
		     <kmss:showDate value="${item.docCreateTime}" type="datetime"/>
		</list:data-column> 
		<!-- 客户端IP -->
		<list:data-column property="fdIp" title="${ lfn:message('sys-print:sysPrintLog.fdIp') }">
		</list:data-column>
		<list:data-column col="dataType">
			<c:out value="printLog"></c:out>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
	
</list:data>