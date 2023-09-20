<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.offline.list">
	<template:replace name="title"></template:replace>
	<template:replace name="head">
	   <mui:min-file name="mui-review-list.css"/>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/header/Header" data-dojo-props="height:'3.8rem'">
			<div data-dojo-type="mui/header/HeaderItem" 
				data-dojo-mixins="mui/folder/_Folder,mui/back/HrefBackMixin"
				data-dojo-props="href:'/km/review/mobile/offline/index.jsp'">
			</div>
			<div data-dojo-type="mui/header/HeaderItem" 
				data-dojo-props="label:window.offlineParam('moduleName'),referListId:'_filterDataList'">
			</div>
			<div 
				data-dojo-type="mui/header/HeaderItem" 
				data-dojo-mixins="mui/folder/_Folder,mui/syscategory/SysCategoryDialogMixin"
				data-dojo-props="icon:'mui mui-ul',
			    	getTemplate:1,
			    	selType: 0|1,
					modelName:'com.landray.kmss.km.review.model.KmReviewTemplate',
					redirectURL:'/km/review/mobile/index.jsp?moduleName=!{curNames}&filter=1',
					filterURL:'/km/review/km_review_index/kmReviewIndex.do?method=list&q.fdTemplate=!{curIds}&orderby=docCreateTime&ordertype=down'">
			</div> 
		</div>
		<div id="scroll" data-dojo-type="mui/list/StoreScrollableView">
			<div
				data-dojo-type="mui/search/SearchBar"
				data-dojo-props="placeHolder:'<bean:message key="button.search"/>',modelName:'com.landray.kmss.km.review.model.KmReviewMain',needPrompt:false,height:'3.8rem'">
			</div>
			<ul id="_filterDataList"
		    	data-dojo-type="mui/list/JsonStoreList" 
		    	data-dojo-mixins="mui/list/ProcessItemListMixin"
		    	data-dojo-props="url:window.offlineParam('queryStr'),lazy:false">
			</ul>
		</div>
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
	  		<li data-dojo-type="mui/back/BackButton"></li>
	  		<span data-dojo-type="mui/authenticate/AuthenticateShow"
	  			data-dojo-props="roles:'ROLE_KMREVIEW_CREATE'">
	  			<li data-dojo-type="mui/tabbar/CreateButton" 
		  			data-dojo-mixins="mui/syscategory/SysCategoryMixin"
		  			data-dojo-props="createUrl:'/km/review/mobile/offline/edit.jsp?method=add&fdTemplateId=!{curIds}',modelName:'com.landray.kmss.km.review.model.KmReviewTemplate'"></li>
	  		</span>
	    	<li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
	    		<div data-dojo-type="mui/back/HomeButton"></div>
	    	</li>
		</ul>
	</template:replace>
</template:include>