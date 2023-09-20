<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
	    <c:out value="${lfn:message('sys-lbpmext-authorize:module.sys.lbpmext.authorize')}"></c:out>
	</template:replace>
	<template:replace name="head">
	    <mui:cache-file name="mui-nav.js" cacheType="md5"/>
		<mui:cache-file name="mui-sys-lbpmext-authorize.js" cacheType="md5" />
		<mui:cache-file name="mui-sys-lbpmext-authorize.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
	
		<%-- 导航头部，通常放导航页签、搜索 --%>
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
		    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
			<div data-dojo-type="mui/nav/MobileCfgNavBar" 
				 data-dojo-props="modelName:'com.landray.kmss.sys.lbpmext.authorize.model.LbpmAuthorize'"> 
			</div>
		</div>
		
		<%-- 筛选器头部，通常放排序、标签筛选器、重要筛选器、筛选器。  
			 	注1: 根据nav.json定义的headerTemplate进行渲染
			 	注2: 考虑到移动端大小问题，业务应该在排序、标签筛选器、重要筛选器三个组件中三选一
		--%>
		<div data-dojo-type="mui/header/NavHeader">
		    <%-- 排序（发布时间）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'fdCreateTime',subject:'${lfn:message('sys-lbpmext-authorize:lbpmAuthorizeHistory.fdCreateTime')}',value:'down'">
			</div>
		    <%-- 排序（授权开始时间）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'fdStartTime',subject:'${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.authorizationStart')}',value:''">
			</div>			
		    <%-- 排序（授权开始时间）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'fdEndTime',subject:'${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.authorizationEnd')}',value:''">
			</div>
			<%-- 默认筛选器头部模板 --%>
			<div class="muiHeaderItemRight" 
				 data-dojo-type="mui/property/FilterItem"
				 data-dojo-mixins="sys/lbpmext/authorize/mobile/js/list/header/LbpmAuthorizePropertyMixin"></div>			
			</div>			
		</div>
		
	    <%--  页签内容展示区域，可纵向上下滑动   --%>
		<div data-dojo-type="mui/list/NavView">
			<%--  默认列表模板   --%>
			<ul data-dojo-type="mui/list/HashJsonStoreList" class="muiList muiLbpmAuthorizeList"
				data-dojo-mixins="sys/lbpmext/authorize/mobile/js/list/LbpmAuthorizeListMixin"
				data-dojo-props="nodataText:'<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.nodata" />'">
			</ul>
		</div>
	
        <%--  流程授权新增按钮   --%>
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
		  	<li data-dojo-type="mui/tabbar/TabBarButton" 
		  	    data-dojo-props="label:'${lfn:message('sys-lbpmext-authorize:module.sys.lbpmext.authorize')}',href:'/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=add'">
			</li>
		</ul>
		
	</template:replace>
</template:include>
