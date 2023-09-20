<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsMedalCategory" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column property="fdName" title="${lfn:message('kms-medal:kmsMedalCategory.fdName') }">
		</list:data-column>
		<list:data-column property="docCreator.fdName"  title="${lfn:message('kms-medal:kmsMedalMain.docCreator') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${lfn:message('kms-medal:kmsMedalMain.docCreateTime') }" >
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
