<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="model" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column col="fdTemplateEdition" title="版本号">
		  V${model.fdTemplateEdition}
		</list:data-column>
		<list:data-column property="fdAlterTime" title="修改时间">
		</list:data-column>
		<list:data-column col="fdAlterorName" title="修改人">
			<c:out value="${model.fdAlteror.fdName}" />
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>