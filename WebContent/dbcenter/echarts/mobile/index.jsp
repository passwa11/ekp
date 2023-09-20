<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list" canHash="true" tiny="true">
	<template:replace name="head">
		<mui:cache-file name="mui-nav.js" cacheType="md5" />
		<mui:cache-file name="mui-simpleCate.js" cacheType="md5" />
		<mui:cache-file name="mui-dbcenter-echarts.js" cacheType="md5" />
		<mui:cache-file name="mui-dbcenter-echarts.css" cacheType="md5" />
	</template:replace>
	<template:replace name="title">
	    <c:out value="图表中心"></c:out>
	</template:replace>
	<template:replace name="content">
		<c:import url="/dbcenter/echarts/mobile/listview.jsp" charEncoding="UTF-8"></c:import>
	</template:replace>
</template:include>
