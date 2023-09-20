<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	SysOrgPerson user = UserUtil.getUser(request);
	if(user.getFdParent()!=null){
		pageContext.setAttribute("parent",user.getFdParent());
	}
%>

<!-- 搜索 -->
<div  class="muiCateSearchArea muiAddressSearchArea">
	<div id='_address_search_{categroy.key}'
		data-dojo-type="mui/address/AddressSearchBar"
		data-dojo-props="orgType:{categroy.type},
			key:'{categroy.key}',
			exceptValue:'{categroy.exceptValue}',
			height:'4rem'">
	</div>
</div>

<!-- 默认面板 -->
<div id="defaultView_{categroy.key}" class="muiAddressView" data-dojo-type="dojox/mobile/View">
	<!-- 导航栏: 常用、组织架构、群组 -->
	<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4rem'">
		<div
			data-dojo-type="mui/nav/StaticNavBar" id="categoryNavBar"
			data-dojo-props="defaultUrl:'/sys/mobile/js/mui/address/nav.jsp',key:'{categroy.key}'">
		</div>
	</div>
	
	<!-- 常用，包括同部门、我的下属、最近使用 -->
	<div id="usualScrollableView_{categroy.key}" 
		data-dojo-type="dojox/mobile/ScrollableView"
		data-dojo-mixins="mui/address/AddressViewScrollResizeMixin,mui/category/AppBarsMixin"
		data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'usual'">
		<div id="usualCateView_{categroy.key}" class="muiUsualView" data-dojo-type="dojox/mobile/View" >
			<!-- 同部门入口 -->
			<div data-dojo-type="mui/address/AddressDeptMember"
				 data-dojo-props="key:'{categroy.key}'">
			</div>
			<!-- 我的下属入口 -->
			<div data-dojo-type="mui/address/AddressSubordinate"
				 data-dojo-props="key:'{categroy.key}'">
			</div>
		
			<div class="muiAddressItemTitle muiUsualRecentTitle">${ lfn:message('sys-mobile:mui.mobile.address.recently') }</div>
			<div data-dojo-type="mui/address/AddressCategoryPath"
				data-dojo-props="key:'{categroy.key}',channel:'usual',height:'3rem',visible:false">
			</div>
			<ul data-dojo-type="mui/address/AddressList"
				data-dojo-mixins="mui/address/AddressItemListMixin,mui/address/AddressRecentItemListMixin"
				data-dojo-props="deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'usual',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'">
			</ul>
		</div>
	</div>
	
	<!-- 组织架构 -->
	<div id="allCateScrollableView_{categroy.key}" 
		data-dojo-type="dojox/mobile/ScrollableView"
		data-dojo-mixins="mui/address/AddressViewScrollResizeMixin,mui/category/AppBarsMixin"
		data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'all'">
		
		<div id="allCateView_{categroy.key}" data-dojo-type="dojox/mobile/View" >
			<div data-dojo-type="mui/address/AddressCategoryPath"
				data-dojo-props="key:'{categroy.key}',channel:'all',curId:'${parent.fdId}',curName:'${parent.fdName}',height:'3rem'">
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
	
	<!-- 群组 -->
	<div id="groupScrollableView_{categroy.key}"
		data-dojo-type="dojox/mobile/ScrollableView"
		data-dojo-mixins="mui/address/AddressViewScrollResizeMixin,mui/category/AppBarsMixin"
		data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'group'">
		<!-- 群组视图 -->
		<div id="groupView_{categroy.key}" 
			data-dojo-type="dojox/mobile/View"
			data-dojo-mixins="mui/address/AddressGroupViewMixin,mui/address/AddressViewScrollResizeMixin"
			data-dojo-props="key:'{categroy.key}'">
		</div>
		<!-- 公共群组视图 -->
		<div id="commonGroupView_{categroy.key}" 
			data-dojo-type="dojox/mobile/View"
			data-dojo-mixins="mui/address/AddressViewScrollResizeMixin">
			<div data-dojo-type="mui/address/AddressCommonGroupCategoryPath"
				data-dojo-props="key:'{categroy.key}',channel:'common_group',height:'4rem'">
			</div>
			<ul data-dojo-type="mui/address/AddressList"
				data-dojo-mixins="mui/address/AddressCommonGroupListMixin"
				data-dojo-props="deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'common_group',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}',maxPageSize:{categroy.maxPageSize}">
			</ul>
		</div>
		<!-- 我的群组视图 -->
		<div id="myGroupView_{categroy.key}" 
			data-dojo-type="dojox/mobile/View"
			data-dojo-mixins="mui/address/AddressViewScrollResizeMixin">
			<div data-dojo-type="mui/address/AddressMyGroupCategoryPath"
				data-dojo-props="key:'{categroy.key}',height:'4rem',channel:'my_group'">
			</div>
			<ul data-dojo-type="mui/address/AddressList"
				data-dojo-mixins="mui/address/AddressMyGroupListMixin"
				data-dojo-props="deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'my_group',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}',maxPageSize:{categroy.maxPageSize}">
			</ul>
		</div>
	</div>
	
</div>
	

<!-- 我的部门面板 -->
<div id="deptMemberView_{categroy.key}"
	class="muiAddressView"
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/address/AddressViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'deptMember'">
	<div data-dojo-type="mui/address/AddressCategoryPath"
		data-dojo-props="key:'{categroy.key}',channel:'deptMember',height:'3rem',visible:false">
	</div>
	<ul data-dojo-type="mui/address/AddressList"
		data-dojo-mixins="mui/address/AddressDeptMemberListMixin,mui/address/AddressSearchListMixin"
		data-dojo-props="deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'deptMember',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'">
	</ul>
</div>

<!-- 我的下属面板 -->
<div id="subordinateView_{categroy.key}"
	class="muiAddressView"
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/address/AddressViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'subordinate'">
	<div data-dojo-type="mui/address/AddressCategoryPath"
		data-dojo-props="key:'{categroy.key}',height:'3rem',channel:'subordinate',titleNode:'${ lfn:message('sys-mobile:mui.mobile.address.subordinate') }'">
	</div>
	<ul data-dojo-type="mui/address/AddressList"
		data-dojo-mixins="mui/address/AddressSubordinateListMixin,mui/address/AddressSearchListMixin"
		data-dojo-props="deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'subordinate',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'">
	</ul>
</div>

<!-- 搜索面板 -->
<div id="searchView_{categroy.key}"
	class="muiAddressView"
	data-dojo-type="dojox/mobile/ScrollableView"
	data-dojo-mixins="mui/address/AddressViewScrollResizeMixin"
	data-dojo-props="scrollBar:false,threshold:100,key:'{categroy.key}',channel:'search'">
	<ul data-dojo-type="mui/address/AddressList"
		data-dojo-mixins="mui/address/AddressItemListMixin,mui/address/AddressSearchListMixin"
		data-dojo-props="history:true,deptLimit:'{categroy.deptLimit}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'search',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'">
	</ul>
</div>

<!-- 已选 -->
<div data-dojo-type="mui/address/AddressSelection" 
	data-dojo-props="key:'{categroy.key}' , curIds:'{categroy.curIds}',curNames:'{categroy.curNames}'" fixed="bottom">
</div>
