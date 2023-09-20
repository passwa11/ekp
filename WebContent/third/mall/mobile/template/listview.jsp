<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%-- 导航头部，通常放导航页签、搜索 --%>
<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
	<div data-dojo-type="mui/nav/MobileCfgNavBar" 
		data-dojo-props="url:'/third/mall/thirdMallTemplate.do?method=listIndustry&type=0'"> 
	</div>
</div>

<%--  页签内容展示区域，可纵向上下滑动   --%>
<div data-dojo-type="mui/list/NavView">
	<%--  默认列表模板   --%>
	<ul class="muiList reuseTemplateItem"
		data-dojo-type="mui/list/HashJsonStoreList"
		data-dojo-mixins="third/mall/mobile/resource/js/ReuseTemplateItemListMixin">
	</ul>
</div>
