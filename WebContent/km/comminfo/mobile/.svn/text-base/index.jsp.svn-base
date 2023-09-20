<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">

    <%-- 浏览器title --%>
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			<c:out value="${lfn:message('km-comminfo:module.km.comminfo')}"></c:out>
		</c:if>
	</template:replace>
	
	<%-- 导入JS、CSS --%>
	<template:replace name="head">
	    <mui:cache-file name="mui-nav.js" cacheType="md5"/>
	    <mui:cache-file name="mui-simpleCate.js" cacheType="md5" />
	    <mui:cache-file name="mui-comminfo-list.js" cacheType="md5"/>
		<mui:cache-file name="mui-comminfo-list.css" cacheType="md5"/>
	</template:replace>
	
	<%-- 页面内容 --%>
	<template:replace name="content">
	
		<%-- 导航头部，通常放导航页签、搜索 --%>
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
		    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
			<div data-dojo-type="mui/nav/MobileCfgNavBar" 
				 data-dojo-mixins="km/comminfo/mobile/resource/js/list/IndexListNavMixin"> 
			</div>
			<%-- 搜索 --%>
			<div data-dojo-type="mui/search/SearchButtonBar"
				data-dojo-props="modelName:'com.landray.kmss.km.comminfo.model.KmComminfoMain'">
			</div>
		</div>
		
		<%-- 筛选器头部，通常放排序、标签筛选器、重要筛选器、筛选器。  
		 	  注1: 根据nav.json定义的headerTemplate进行渲染
		 	  注2: 考虑到移动端大小问题，业务应该在排序、标签筛选器、重要筛选器三个组件中三选一
		--%>
		<div data-dojo-type="mui/header/NavHeader">
		    <%-- 排序（创建时间）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'docCreateTime',subject:'<bean:message bundle="km-comminfo" key="kmComminfoMain.docCreateTime" />',value:'down'">
			</div>	
			<%-- 分类  --%>					
	        <div class="muiHeaderItemRight" 
		        data-dojo-type="mui/catefilter/FilterItem"
		        data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"
		        data-dojo-props="modelName: 'com.landray.kmss.km.comminfo.model.KmComminfoCategory',catekey: 'docCategoryId',prefix:''"></div>				
		    </div>
		
	    <%--  页签内容展示区域，可纵向上下滑动   --%>
		<div data-dojo-type="mui/list/NavView">
			<%--  默认列表模板   --%>
			<ul data-dojo-type="mui/list/HashJsonStoreList" class="muiList"
				data-dojo-mixins="km/comminfo/mobile/resource/js/list/ComminfoItemListMixin">
			</ul>
		</div>
	
	</template:replace>
</template:include>


