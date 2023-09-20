<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="head">
		<mui:cache-file name="mui-nav.js" cacheType="md5"/>
		<mui:cache-file name="mui-simpleCate.js" cacheType="md5" />
		<mui:cache-file name="mui-km-smissive.js" cacheType="md5" />
		<mui:cache-file name="mui-km-smissive.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			<c:out value="${lfn:message('km-smissive:module.km.smissive')}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="content">
		<c:import url="/km/smissive/mobile/listview.jsp" charEncoding="UTF-8"></c:import>
	</template:replace>
</template:include>
