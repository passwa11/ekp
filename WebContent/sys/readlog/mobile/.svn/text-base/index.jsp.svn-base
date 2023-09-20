<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-readlog:sysReadLog.accessStatistics')}"></c:out>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-nav.js" cacheType="md5"/>
		<mui:cache-file name="mui-sysCate.js" cacheType="md5" />
		<mui:cache-file name="mui-readlog-index.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		 <c:import url="/sys/readlog/mobile/view.jsp" charEncoding="UTF-8">
		 	<c:param name="modelName" value="${param.modelName }"></c:param>
		 	<c:param name="modelId" value="${param.modelId }"></c:param>
		 	<c:param name="type" value="${param.type }"></c:param>
		 </c:import>
	</template:replace>
</template:include>
