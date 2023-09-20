<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdBorrowEffectiveTime"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdBorrowEffectiveTime') }"
			escape="false">
			<kmss:showDate value="${item.fdBorrowEffectiveTime}" type="date" />
		</list:data-column>
		<list:data-column col="fdDuration"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdDuration') }">
			${item.fdDuration} 天
		</list:data-column>

		<list:data-column col="fdStatus"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdStatus') }"
			escape="false">

			<sunbor:enumsShow value="${item.fdStatus}"
				enumsType="sys_att_borrow_status" bundle="sys-attachment-borrow" />
		</list:data-column>

		<list:data-column property="docCreateTime"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.docCreateTime') }" />

		<list:data-column col="fdAuth" escape="false"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdAuth') }">

			<c:if test="${item.fdReadEnable == true }">
				<span>阅读</span>
			</c:if>

			<c:if test="${item.fdDownloadEnable == true }">
				<span>下载</span>
			</c:if>

			<c:if test="${item.fdCopyEnable == true }">
				<span>拷贝</span>
			</c:if>

			<c:if test="${item.fdPrintEnable == true }">
				<span>打印</span>
			</c:if>
		</list:data-column>

		<list:data-column col="fdBorrowers" escape="false"
			title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdBorrowers') }">
			<kmss:joinListProperty properties="fdName"
				value="${item.fdBorrowers }"></kmss:joinListProperty>
		</list:data-column>

	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>
