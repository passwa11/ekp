<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.offline.list">
	<template:replace name="title">
	</template:replace>	
	<template:replace name="head">
	   <mui:min-file name="mui-review-list.css"/>
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="km/review/mobile/resource/js/list/ReviewView">
			<div data-dojo-type="mui/header/Header"
				data-dojo-mixins="km/review/mobile/resource/js/list/ReviewHeaderMixin">
		
				<div data-dojo-type="mui/header/HeaderItem"
					data-dojo-mixins="mui/folder/_Folder,km/review/mobile/resource/js/list/ReviewHomeButton"></div>
				<div
					data-dojo-type="mui/nav/MobileCfgNavBar" 
					data-dojo-props="defaultUrl:'/km/review/mobile/offline/nav.jsp',modelName:'com.landray.kmss.km.review.model.KmReviewMain'">
				</div>
				<div data-dojo-type="mui/header/HeaderItem"
					data-dojo-mixins="mui/folder/Folder"
					data-dojo-props="tmplURL:'/km/review/mobile/offline/query.jsp'"></div>
			</div>
			<div data-dojo-type="mui/list/NavSwapScrollableView"
			     data-dojo-mixins="km/review/mobile/resource/js/list/ReviewListScrollableViewMixin">
			    <ul
			    	data-dojo-type="mui/list/JsonStoreList"
			    	data-dojo-mixins="mui/list/ProcessItemListMixin">
				</ul>
			</div>
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