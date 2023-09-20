<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%
	KMSSUser user = UserUtil.getKMSSUser(request);
	request.setAttribute("user", user);
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/statistics.css?s_cache=${MUI_Cache}">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/base/resources/css/boardMobile.css?s_cache=${MUI_Cache}">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/modeling/main/resources/css/calendar.css?s_cache=${MUI_Cache}">
<%-- 导航头部，通常放导航页签、搜索 --%>
<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
	<div data-dojo-type="sys/modeling/main/resources/js/mobile/listView/ModelingCfgNavBar"
		 data-dojo-props="listViewId:'${param.listViewId}',tabId:'${param.tabId}',fdModelId:'${param.fdModelId}',
		viewTabId:'${param.viewTabId}',isNewListview:'${param.isNewListview}',arrIndex:'${param.arrIndex}',
        fdMobileId:'${param.fdMobileId}',area:'${param.area}',nodeType:'${param.nodeType}',order:'${param.order}'">
	</div>

</div>

<%-- 筛选器头部，通常放排序、标签筛选器、重要筛选器、筛选器。
	 	注1: 根据nav.json定义的headerTemplate进行渲染
	 	注2: 考虑到移动端大小问题，业务应该在排序、标签筛选器、重要筛选器三个组件中三选一
--%>
<div class="statistics-body" id="statistics-body">
	<div class="statistics-content more"  id="statistics-content"   data-dojo-type="sys/modeling/main/resources/js/mobile/listView/statisticsList" >
	</div>
</div>
<%--<div class="statistics-content"  data-dojo-type="sys/modeling/main/resources/js/mobile/listView/statisticsNavHeader" >
</div>--%>

<div data-dojo-type="mui/header/NavHeader"  style="background: #ffffff">

</div>
<div data-dojo-type="sys/modeling/main/resources/js/mobile/listView/BoardNavHeader" class="muiHeaderNav">
</div>
<div data-dojo-type="sys/modeling/main/resources/js/mobile/listView/ModelingCalendarView" class="muiHeaderNav">

</div>
<%--  页签内容展示区域，可纵向上下滑动   --%>
<div data-dojo-type="mui/list/NavView" style="background: #ffffff" class="modelListView">
	<%--  默认列表模板   --%>
	<ul data-dojo-type="sys/modeling/main/resources/js/mobile/listView/HashJsonStoreList"
		data-dojo-mixins="sys/modeling/main/resources/js/mobile/listView/DocItemListMixin">
	</ul>
</div>
<script>
</script>