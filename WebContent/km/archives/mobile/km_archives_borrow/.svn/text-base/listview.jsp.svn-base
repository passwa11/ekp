<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<%-- 导航头部，通常放导航页签、搜索 --%>
<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
	<div data-dojo-type="mui/nav/MobileCfgNavBar"
		 data-dojo-mixins="km/archives/mobile/km_archives_borrow/js/header/IndexListNavMixin">
	</div>
	
	<%-- 搜索 --%>
	<div data-dojo-type="mui/search/SearchButtonBar"
		 data-dojo-props="modelName:'com.landray.kmss.km.archives.model.KmArchivesMain'">
	</div>
</div>

<%-- 筛选器头部，通常放排序、标签筛选器、重要筛选器、筛选器。  
	 	注1: 根据nav.json定义的headerTemplate进行渲染
	 	注2: 考虑到移动端大小问题，业务应该在排序、标签筛选器、重要筛选器三个组件中三选一
--%>
<div data-dojo-type="mui/header/NavHeader">
    <%-- 排序  --%>
	<div data-dojo-type="mui/sort/SortItem" 
	    data-dojo-props="name:'docCreateTime',subject:'<bean:message key="kmArchivesBorrow.docCreateTime" bundle="km-archives" />',value:'down'">
	</div>

	<%-- 默认筛选器头部模板 ,catekey: 'fdTemplate'--%>
	<div class="muiHeaderItemRight" 
		data-dojo-type="mui/property/FilterItem"
		data-dojo-mixins="km/archives/mobile/km_archives_borrow/js/header/ArchivesBorrowPropertyMixin"></div>
	<div class="muiHeaderItemRight" 
		data-dojo-type="mui/catefilter/FilterItem"
		data-dojo-mixins="mui/listcategory/ListCategoryMixin"
		data-dojo-props="dataUrl:'/km/archives/km_archives_template/kmArchivesTemplate.do?method=criteria',titleNode:'<bean:message key="table.kmArchivesTemplate" bundle="km-archives" />',catekey: 'docTemplate'"></div>
</div>

<%--  页签内容展示区域，可纵向上下滑动   --%>
<div data-dojo-type="mui/list/NavView">
	<%--  默认列表模板   --%>
	<ul data-dojo-type="mui/list/HashJsonStoreList" class="muiList"
		data-dojo-mixins="mui/list/TextItemListMixin">
	</ul>
</div>
