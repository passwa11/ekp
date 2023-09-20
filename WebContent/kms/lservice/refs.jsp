<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">

		<list:data-column property="fdId">
		</list:data-column>

		<list:data-column col="docSubject"
			title="${lfn:message('kms-lservice:lserviceProducer.refs.subject') }">
			${ refss[item.fdId].subject}
		</list:data-column>

		<list:data-column col="module"
			title="${lfn:message('kms-lservice:lserviceProducer.refs.module') }">
			${refss[item.fdId].module}
		</list:data-column>

		<list:data-column col="url" escape="false">
			${refss[item.fdId].url}
		</list:data-column>

		<list:data-column property="docCreateTime"
			title="${lfn:message('kms-lservice:lserviceProducer.docCreateTime') }">
		</list:data-column>

	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>

</list:data>
