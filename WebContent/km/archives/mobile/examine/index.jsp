<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<bean:message key="py.DangAnShenHe" bundle="km-archives"/>
	</template:replace>
	<template:replace name="head">
        <mui:cache-file name="mui-archives-examine.js" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">

		<c:import url="./listview.jsp" charEncoding="UTF-8"></c:import>
		 
	</template:replace>
</template:include>