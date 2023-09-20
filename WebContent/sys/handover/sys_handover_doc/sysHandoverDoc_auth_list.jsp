<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="model" list="${page.list}"
		varIndex="status" custom="false">
		<c:if test="${empty dataMap}">
		<list:data-column property="fdId">
		</list:data-column>
		</c:if>
		<c:if test="${!empty dataMap}">
		<list:data-column col="fdId">
			${dataMap[model.noteId]}
		</list:data-column>
		</c:if>
		<list:data-column col="index">
		   ${status+1}
		</list:data-column>
		<!--url-->
		<list:data-column col="url" escape="false">
			<c:if test="${not empty urlMap[model.fdId]}">
	            ${urlMap[model.fdId]}
	        </c:if>
		</list:data-column>
		<!--标题-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.docSubject') }"
			escape="false" style="text-align:left;min-width:200px">
			<c:if test="${not empty subjectMap[model.fdId]}">
	             <span class="com_subject">${subjectMap[model.fdId]}</span>
	        </c:if>
		</list:data-column>
		<!--创建者-->
		<list:data-column col="creator"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreatorId') }" headerStyle="width:100px">
			<c:if test="${not empty creatorMap[model.fdId]}">
	             ${creatorMap[model.fdId]}
	        </c:if>
		</list:data-column>
		<!--创建时间-->
		<list:data-column col="createTime"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.docCreateTime') }" headerStyle="width:100px">
			<c:if test="${not empty createTimeMap[model.fdId]}">
	             ${createTimeMap[model.fdId]}
	        </c:if>
		</list:data-column>
		<c:if test="${!empty noteMap}">
		<!--节点-->
		<list:data-column col="note"
			title="${ lfn:message('sys-handover:sysHandoverConfigLog.fdFactId') }" headerStyle="width:120px">
			<c:if test="${not empty noteMap[model.noteId]}">
	             ${noteMap[model.noteId]}
	        </c:if>
		</list:data-column>
		</c:if>
	</list:data-columns>
	<list:data-paging currentPage="${page.pageno }"
		pageSize="${page.rowsize }" totalSize="${page.totalrows }">
	</list:data-paging>
</list:data>