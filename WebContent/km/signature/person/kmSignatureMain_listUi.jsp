<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmSignatureMain" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 签章名称
		<list:data-column property="fdMarkName" escape="false" title="${ lfn:message('km-signature:signature.markname') }" style="text-align:left">
		</list:data-column>
		 --%>
		<list:data-column col="fdMarkName" escape="false" title="${ lfn:message('km-signature:signature.markname') }" style="text-align:left;min-width:200px">
			<c:out value="${kmSignatureMain.fdMarkName }"></c:out>
		</list:data-column>
		<%-- 签章分类 --%>
		
		<%-- 签章保存时间 --%>
		<list:data-column headerStyle="width:120px" property="fdMarkDate" escape="false" title="${ lfn:message('km-signature:signature.markdate') }">
		</list:data-column>
		<%-- 签章类型 --%>
		<list:data-column headerStyle="width:80px" col="fdDocType" escape="false" title="${ lfn:message('km-signature:signature.docType') }">
			<c:if test="${kmSignatureMain.fdDocType == 1}">
				<c:out value="${ lfn:message('km-signature:signature.fdDocType.handWrite') }"></c:out>
			</c:if>
			<c:if test="${kmSignatureMain.fdDocType == 2}">
				<c:out value="${ lfn:message('km-signature:signature.fdDocType.companySignature') }"></c:out>
			</c:if>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>