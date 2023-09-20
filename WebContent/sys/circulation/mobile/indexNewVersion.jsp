<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-circulation:sysCirculationMain.mobile.lastCirculation')}"></c:out>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-circulate.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
			<div data-dojo-type="sys/circulation/mobile/js/CirculationHeader" 
				data-dojo-props="modelId:'${JsParam.modelId }',modelName:'${JsParam.modelName }'"> 
			</div>
		</div>
		<div data-dojo-type="mui/list/StoreScrollableView">
			<ul class="muiCirculationList"
				data-dojo-type="mui/list/JsonStoreList" 
				data-dojo-mixins="sys/circulation/mobile/js/CirculationItemListNewVersionMixin"
				data-dojo-props="url:'/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=viewAll&forward=mobileList&fdModelId=${JsParam.modelId}&fdModelName=${JsParam.modelName}',lazy:false,isNewVersion:true">
			</ul>
		</div>
	</template:replace>
</template:include>
