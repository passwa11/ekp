<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.list" canHash="true" tiny="true">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			<c:out value="${lfn:message('km-forum:module.km.forum')}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-forum.js" cacheType="md5" />
		<mui:cache-file name="mui-nav.js" cacheType="md5" />
		<mui:cache-file name="newForum.css" cacheType="md5" />
		<mui:cache-file name="mui-simpleCate.js" cacheType="md5" />
	</template:replace>
	
	<template:replace name="content">
		<%--导航 --%>
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
		    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
			<div data-dojo-type="mui/nav/MobileCfgNavBar" 
				data-dojo-props="modelName:'com.landray.kmss.km.forum.model.KmForumTopic'"> 
			</div>
			<%-- 搜索 --%>
			<div data-dojo-type="mui/search/SearchButtonBar"
				data-dojo-props="modelName:'com.landray.kmss.km.forum.model.KmForumTopic'">
			</div>
		</div>	  
		<%--筛选器 --%>
		<div data-dojo-type="mui/header/NavHeader">
			<%-- 排序（发布时间）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'docCreateTime',subject:'<bean:message bundle="km-forum"  key="kmForumTopic.release.time"/>',value:'down'">
			</div>
			<%-- 排序（评论数）  --%>
			<div data-dojo-type="mui/sort/SortItem" 
			    data-dojo-props="name:'fdReplyCount',subject:'<bean:message bundle="km-forum"  key="kmForumTopic.comment.number"/>',value:''">
			</div>
			<%-- 默认筛选器头部模板 --%>
			<div class="muiHeaderItemRight"  
				data-dojo-type="mui/catefilter/simplecategory/FilterItem"
				data-dojo-props="modelName:'com.landray.kmss.km.forum.model.KmForumCategory','parentId':'${param.docCategory }'"></div>
		</div>
		
		<%--  页签内容展示区域，可纵向上下滑动   --%>
		<div data-dojo-type="mui/list/NavView">
			<%--  默认列表模板   --%>
			<ul class="muiList"
				data-dojo-type="mui/list/HashJsonStoreList" 
				data-dojo-mixins="km/forum/mobile/resource/js/list/ForumTopicItemListMixin">
			</ul>
		</div>	  	  
		
		<kmss:authShow roles="ROLE_KMFORUMPOST_POSTCREATE">
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
			 	<li data-dojo-type="km/forum/mobile/resource/js/list/ForumTopicCreate">
			 		<bean:message bundle="km-forum"  key="kmForumTopic.create"/>
			 	</li>
			</ul>
		</kmss:authShow>
	</template:replace>
</template:include>
