<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<c:out value="${lfn:message('third-mall:thirdMall.selectTemplate')}"></c:out>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-mall.js" cacheType="md5"/>
		<mui:cache-file name="mui-mall-list.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		<c:if test="${'true' eq network}">
			<input type="hidden" name="createUrl" value="${createUrl}" />
			<input type="hidden" name="isAuth" value="${isAuth}" />
			<c:import url="/third/mall/mobile/template/listview.jsp" charEncoding="UTF-8"></c:import>
		</c:if>
		<c:if test="${'false' eq network}">
			<img src="../mall/mobile/resource/images/img_2_big@2x.png" alt="">
			<span class="muiNoNetworkTip"><bean:message key='third-mall:thirdMall.no_network_tip'/></span>
		</c:if>
	</template:replace>
</template:include>
