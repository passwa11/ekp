<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 搜索 -->
<div  class="muiCateSearchArea muiAddressSearchArea">
	<div id='_address_search_{categroy.key}'
		data-dojo-type="mui/address/AddressSearchBar"
		data-dojo-props="searchUrl:'{categroy.searchUrl}',orgType:{categroy.type},key:'{categroy.key}',exceptValue:'{categroy.exceptValue}',height:'4rem'">
	</div>
</div>


<!-- 默认面板 -->
<div id="defaultView_{categroy.key}" class="muiAddressView" data-dojo-type="dojox/mobile/View">
	<div id="allCateScrollableView_{categroy.key}" 
		data-dojo-type="dojox/mobile/ScrollableView"
		data-dojo-mixins="mui/address/AddressViewScrollResizeMixin,mui/category/AppBarsMixin"
		data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'all'">
		
		<div id="allCateView_{categroy.key}" data-dojo-type="dojox/mobile/View" >
			<div data-dojo-type="mui/address/AddressCategoryPath"
				data-dojo-props="key:'{categroy.key}',channel:'all',height:'3rem',visible:false">
			</div>
			<ul data-dojo-type="mui/address/AddressList"
				data-dojo-mixins="mui/address/AddressItemListMixin"
				data-dojo-props="dataUrl:'{categroy.dataUrl}',deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'all',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}',maxPageSize:{categroy.maxPageSize}">
			</ul>
		</div>
		<div data-dojo-type="mui/category/CategoryScrollNav" 
			data-dojo-mixins="mui/address/AddressScrollNavMixin"
			data-dojo-props="containerDom:'allCateScrollableView_{categroy.key}',refrenceDom:'allCateView_{categroy.key}',key:'{categroy.key}',channel:'all',scope:'{categroy.scope}'">
		</div>
	</div>
</div>


<!-- 搜索面板 -->
<div id="searchView_{categroy.key}"
	class="muiAddressView"
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/address/AddressViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'search'">
	<ul data-dojo-type="mui/address/AddressList"
		data-dojo-mixins="mui/address/AddressItemListMixin,mui/address/AddressSearchListMixin"
		data-dojo-props="history:true,dataUrl:'{categroy.dataUrl}',searchUrl:'{categroy.searchUrl}',deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'search',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'">
	</ul>
</div>
<!-- 已选 -->
<div data-dojo-type="mui/address/AddressSelection" 
	data-dojo-props="key:'{categroy.key}' ,beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}', curIds:'{categroy.curIds}',curNames:'{categroy.curNames}'" fixed="bottom">
</div>
