<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="logDetail" list="${page.list }"
		varIndex="status">
		<list:data-column col="index">
		     ${status+1}
		</list:data-column>
		<!--url-->
		<list:data-column col="url" escape="false">
			<c:if test="${not empty logDetail.fdUrl}">
	            ${logDetail.fdUrl}
	        </c:if>
		</list:data-column>
		<!--标题-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.docSubject') }"
			escape="false" style="text-align:left;min-width:200px">
			<c:if test="${not empty logDetail.fdDescription}">
	             <span class="com_subject">${logDetail.fdDescription}</span>
	        </c:if>
		</list:data-column>
		<!--模块名-->
		<list:data-column col="module"
			title="${ lfn:message('sys-handover:sysHandoverConfigLog.fdModuleName') }">
				${logDetail.fdLog.fdModuleName}
		</list:data-column>
		<!--交接项-->
		<list:data-column col="item"
			title="${ lfn:message('sys-handover:sysHandoverConfigLog.fdItemName') }">
				${logDetail.fdLog.fdItemName}
		</list:data-column>
		<!--交接人-->
		<list:data-column col="form"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdFromName') }">
				${logDetail.fdLog.fdMain.fdFromName}
		</list:data-column>
		<!--接收人-->
		<list:data-column col="to"
			title="${ lfn:message('sys-handover:sysHandoverConfigMain.fdToName') }">
				${logDetail.fdLog.fdMain.fdToName}
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${page.pageno }"
		pageSize="${page.rowsize }" totalSize="${page.totalrows }">
	</list:data-paging>
</list:data>