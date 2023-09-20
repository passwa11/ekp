<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<bean:message bundle="sys-ftsearch-db" key="search.ftsearch.mobile" />
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/search/SearchBar"
      		data-dojo-props='modelName:"${lfn:escapeHtml(JsParam.modelName)}",keyword:"${lfn:escapeHtml(JsParam.keyword)}",searchFields:"${JsParam.searchFields }",docStatus:"${JsParam.docStatus}",height:"3.8rem",forPage:true'>
    	</div>
	</template:replace>
</template:include>
