<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="model" list="${itemPage.list}" varIndex="status" custom="false">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		   ${status+1}
		</list:data-column>
		<!--url-->
		<list:data-column col="url" escape="false">
	        ${model.url}
		</list:data-column>
		<!--标题-->
		<list:data-column col="subject"
			title="${ lfn:message('sys-handover-support-config-doc:sysHandoverDoc.docSubject') }"
			escape="false" style="text-align:left;min-width:200px">
	        <span class="com_subject">${model.subject}</span>
		</list:data-column>
		<!--自定义字段-->
		<c:forEach items="${model.item}" var="item" varStatus="index">
			<list:data-column col="item_${index}" title="${item.name}">
		        ${item.text}
			</list:data-column>
		</c:forEach>
	</list:data-columns>
	<list:data-paging currentPage="${itemPage.pageno }"
		pageSize="${itemPage.rowsize }" totalSize="${itemPage.totalrows }">
	</list:data-paging>
</list:data>