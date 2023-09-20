<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }"
		varIndex="index">

		<list:data-column col="subject"
			title="${lfn:message('kms-lservice:lserviceProducer.readers.subject') }">
			${ readerss[index].subject}
		</list:data-column>

		<list:data-column col="module"
			title="${lfn:message('kms-lservice:lserviceProducer.readers.module') }">
			${readerss[index].module}
		</list:data-column>

		<list:data-column col="url" escape="false">
			${readerss[index].url}
		</list:data-column>

		<list:data-column col="reader" escape="false"
			title="${lfn:message('kms-lservice:lserviceProducer.readers.reader') }">
			<ui:person personId="${readerss[index].readerId}"></ui:person>
		</list:data-column>


	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>

</list:data>
