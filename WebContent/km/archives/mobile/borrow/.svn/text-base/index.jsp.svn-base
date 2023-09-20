<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<bean:message key="py.WoDeJieYue" bundle="km-archives"/>
	</template:replace>
	<template:replace name="head">
        <mui:cache-file name="mui-archives-myborrow.js" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">

		<c:import url="./borrowlist.jsp" charEncoding="UTF-8"></c:import>
		 
		<kmss:auth requestURL="/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add">
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
			<li data-dojo-type="mui/tabbar/CreateButton"
		   		data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"
		   		data-dojo-props="icon1:'',
		   			templURL:'/km/archives/mobile/resource/tmpl/simplecategory.jsp',
		   			url:'/km/archives/km_archives_template/kmArchivesTemplate.do?method=getTemplete',
		   			redirectUrl: '/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add4m&docTemplateId={fdId}'">
				${lfn:message('km-archives:mui.borrow.add')}
			</li>
		</ul>
		</kmss:auth>
	</template:replace>
</template:include>