<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="logAuthDetail" list="${page.list }"
		varIndex="status">
		<list:data-column col="index">
		     ${status+1}
		</list:data-column>
		<!--url-->
		<list:data-column col="url" escape="false">
			<c:if test="${not empty logAuthDetail.fdModelUrl}">
	            ${logAuthDetail.fdModelUrl}
	        </c:if>
		</list:data-column>
		<!--标题-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.docSubject') }"
			escape="false" style="text-align:left;min-width:200px">
			<c:if test="${not empty logAuthDetail.fdModelSubject}">
	             <span class="com_subject">${logAuthDetail.fdModelSubject}</span>
	        </c:if>
		</list:data-column>
		<!--创建者-->
		<list:data-column col="creator"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreatorId') }" headerStyle="width:100px">
			<c:if test="${not empty creatorMap[logAuthDetail.fdId]}">
	             ${creatorMap[logAuthDetail.fdId]}
	        </c:if>
		</list:data-column>
		<!--创建时间-->
		<list:data-column col="createTime"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreateTime') }" headerStyle="width:100px">
			<c:if test="${not empty createTimeMap[logAuthDetail.fdId]}">
	             ${createTimeMap[logAuthDetail.fdId]}
	        </c:if>
		</list:data-column>
		<!--节点-->
		<c:if test="${'authLbpmReaders' eq logAuthDetail.fdAuthType}">
		<list:data-column col="note"
			title="${ lfn:message('sys-handover:sysHandoverConfigLog.fdFactId') }" headerStyle="width:120px">
			${logAuthDetail.fdNodeName}
		</list:data-column>
		</c:if>
	</list:data-columns>
	<list:data-paging currentPage="${page.pageno }"
		pageSize="${page.rowsize }" totalSize="${page.totalrows }">
	</list:data-paging>
</list:data>