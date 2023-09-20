<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			<c:out value="${lfn:message('sys-news:news.moduleName')}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-nav.js" cacheType="md5"/>
		<mui:cache-file name="mui-simpleCate.js" cacheType="md5" />
		<mui:cache-file name="mui-sys-news.js" cacheType="md5" />
		<mui:cache-file name="mui-sys-news.css" cacheType="md5"/>
	</template:replace>	
	<template:replace name="content">
		<c:import url="/sys/news/mobile/listview.jsp" charEncoding="UTF-8"></c:import>
		
		<%--  新建按钮   --%>
		 <kmss:authShow roles="ROLE_SYSNEWS_CREATE">
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
		  		<li data-dojo-type="mui/tabbar/CreateButton"
					data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"
			  		data-dojo-props="icon1:'',createUrl:'/sys/news/mobile/add.jsp?fdTemplateId=!{curIds}',
			  		modelName:'com.landray.kmss.sys.news.model.SysNewsTemplate'"><bean:message  bundle="sys-news"  key="mobile.sysNews.add" /></li>
			</ul>
		 </kmss:authShow>
	</template:replace>
</template:include>
