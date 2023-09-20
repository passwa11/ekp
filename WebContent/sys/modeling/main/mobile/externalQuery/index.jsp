<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<c:out value="${appName}"></c:out>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/list.css?s_cache=${MUI_Cache}">

		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/externalQuery.css?s_cache=${MUI_Cache}">

		<mui:cache-file name="mui-nav.js" cacheType="md5"/>
		<mui:cache-file name="mui-sysCate.js" cacheType="md5" />
	</template:replace>
	<template:replace name="content">
		<c:import url="/sys/modeling/main/mobile/externalQuery/template/normal.jsp" charEncoding="UTF-8">
			<c:param name="fdModelId" value="${param.fdAppModelId}"></c:param>
		</c:import>
		<script>
			require([ 'dojo/topic','dojo/dom',"dojo/query","dojo/dom-class",'dojo/dom-style','dojo/dom-construct'
						,"dojo/query", "dijit/registry",'dojo/ready'],
					function(topic,dom,query,domClass,domStyle,domConstruct,query,registry,ready) {
						ready(function (){
							var button = registry.byId("button");
							if (button){
								button._onClick();
								setTimeout(function(){
									query(".muiCateClearBtn").on("click",function (){
										window.history.back();
									})
								},500)
							}
						})



					})
		</script>
	</template:replace>
</template:include>
