<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="head">
		<mui:min-file name="mui-zone-tag-filter.css"/>
	</template:replace>
	<template:replace name="title">
		人员筛选
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="sys/zone/mobile/js/FilterSearchBar" class="muiFilterSearchBar"
			 data-dojo-props="height:'4.5rem'">
		</div>
		<div data-dojo-type="mui/header/Header" class="muiFilterSearchSecHeader"
		     data-dojo-props="height:'4.2rem'">
		    <div data-dojo-type="mui/tabfilter/TabfilterListSelection">
		    </div>
			<div data-dojo-type="mui/header/HeaderItem" 
				class="muiTabfilterDiaItem"
				data-dojo-mixins="mui/folder/_Folder,
						  mui/tabfilter/tag/TabfilterTagDialogMixin"
				data-dojo-props="icon:'mui mui-attachment',
						 isInit:'${param.tagFilter}',
						 modelName:'com.landray.kmss.sys.zone.model.SysZonePersonInfo',
						 key:'zoneTag'">
			</div>
			<div data-dojo-type="mui/header/HeaderItem"
				data-dojo-mixins="sys/zone/mobile/js/ZoneFilterHeaderMixin"
				class="muiTabfilterAddressItem"
				data-dojo-props="icon:'mui mui-address-list',
						 		 href:'/sys/zone/mobile/'"></div>
		</div>
		<h3 class="muiTabfilterResultBar">已经为您查出<em id="_resulte_num"></em>条记录</h3>
		<div data-dojo-type="mui/list/StoreScrollableView"
			 data-dojo-mixins="sys/zone/mobile/js/ZoneFilterViewTopMixin">
			<ul data-dojo-type="sys/zone/mobile/js/list/FilterSearchList" 
				 data-dojo-mixins="sys/zone/mobile/js/list/PersonSearchItemListMixin"
				 data-dojo-props="lazy:false,resultNumNode:'_resulte_num'">
			</ul>
		</div>
	</template:replace>
</template:include>

