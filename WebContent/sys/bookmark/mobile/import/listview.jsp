<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

	<%-- 导航头部，通常放导航页签、搜索 --%>
	<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
	    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
		<div data-dojo-type="mui/nav/MobileCfgNavBar" 
			 data-dojo-props="modelName:'com.landray.kmss.sys.bookmark.model.SysBookmarkMain'"> 
		</div>
	</div>

	<%-- 筛选器头部，通常放排序、标签筛选器、重要筛选器、筛选器。  
		 	注1: 根据nav.json定义的headerTemplate进行渲染
		 	注2: 考虑到移动端大小问题，业务应该在排序、标签筛选器、重要筛选器三个组件中三选一
	--%>
	<div data-dojo-type="mui/header/NavHeader">
	    <%-- 排序（发布时间）  --%>
		<div data-dojo-type="mui/sort/SortItem" 
		    data-dojo-props="name:'docCreateTime',subject:'收藏时间',value:'down'">
		</div>
		
		<%-- 默认筛选器头部模板 --%>
		<div class="muiHeaderItemRight" 
			 data-dojo-type="mui/property/FilterItem"
			 data-dojo-mixins="sys/bookmark/mobile/js/header/BookmarkPropertyMixin"></div>
		<%-- 默认分类筛选头部模板 --%>
		<div class="muiHeaderItemRight" 
			 data-dojo-type="mui/catefilter/FilterItem"
			 data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin"
			 data-dojo-props="modelName: 'com.landray.kmss.sys.bookmark.model.SysBookmarkCategory',catekey: 'cateid',prefix:''"></div>
	</div>


	<%--  页签内容展示区域，可纵向上下滑动   --%>
	<div data-dojo-type="mui/list/NavView">
		<%--  默认列表模板   --%>
		<ul data-dojo-type="mui/list/HashJsonStoreList" 
			data-dojo-mixins="sys/bookmark/mobile/js/SysBookListMixin">
		</ul>
	</div>