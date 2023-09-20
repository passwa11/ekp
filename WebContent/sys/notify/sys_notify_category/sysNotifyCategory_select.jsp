<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysNotifyCategory" list="${queryPage.list}">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdCateType" title="${ lfn:message('sys-notify:sysNotifyCategory.fdCateType') }">
			 <c:if test="${sysNotifyCategory.fdCateType==0}">
				${ lfn:message('sys-notify:sysNotifyCategory.type.module')}
			 </c:if>
			<c:if test="${sysNotifyCategory.fdCateType==1}">
				${ lfn:message('sys-notify:sysNotifyCategory.type.system') } 
			</c:if>
		</list:data-column>		
		<list:data-column property="fdName" title="${ lfn:message('sys-notify:sysNotifyCategory.fdName') }">
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}"></list:data-paging>
</list:data>