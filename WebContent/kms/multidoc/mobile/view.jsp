<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include compatibleMode="true"
	file="/sys/mobile/template/view_tiny.jsp">	
	<template:replace name="head">
		<mui:cache-file name="mui-multidoc-view.js" cacheType="md5"/>	
		<mui:cache-file name="sys-lbpm-note.js" cacheType="md5" />
		<mui:cache-file name="mui-multidoc-view.css" cacheType="md5" />
		<mui:cache-file name="mui-xform.js" cacheType="md5" />
		<script>
				require([ "kms/multidoc/mobile/js/mui-multidoc-view"],function(view){
					var view = new view();
					view.init();
				});
		</script>
	</template:replace>
	<template:replace name="content">
	</template:replace>
</template:include>
