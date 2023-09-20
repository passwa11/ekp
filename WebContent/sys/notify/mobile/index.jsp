<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil,java.util.*"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null || param.moduleName==''}">
			<c:out value="${lfn:message('sys-notify:module.sys.notify')}"></c:out>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-nav.js" cacheType="md5"/>
		<mui:cache-file name="mui-sys-notify.js" cacheType="md5" />
		<mui:cache-file name="mui-sys-notify.css" cacheType="md5"/>
		<mui:cache-file name="mui-fast-review-todo-list.js" cacheType="md5" />
		<mui:cache-file name="mui-fast-review-todo-list.css" cacheType="md5" />
		<script>
		    require(['sys/notify/mobile/resource/js/search/SearchNotifyMain']);
		    require(['sys/notify/mobile/resource/js/list/ViewChange']);
		</script>
	</template:replace>
	<template:replace name="content">
	
		<div id="notifyMainView" data-dojo-type="dojox/mobile/View" data-dojo-mixins="sys/notify/mobile/resource/js/notifyMainViewMixin" >
		
			<div data-dojo-type="mui/header/Header" class="muiHeaderNav">

			    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
				<div id="_navBar" data-dojo-type="mui/nav/MobileCfgNavBar"
					 data-dojo-props="modelName:'com.landray.kmss.sys.notify.model.SysNotifyTodo'"> 
				</div>
				
				<%--  搜索图标   --%>
				<div data-dojo-type="sys/notify/mobile/resource/js/search/SearchButtonBar"></div> 
				
			</div>
			
			<%--  页签内容展示区域，可纵向上下滑动   --%>
			<div id="scrollView" data-dojo-type="mui/list/NavView" >
			    <ul data-dojo-type="mui/list/HashJsonStoreList" class="muiList notifyCardList"
			    	data-dojo-mixins="sys/notify/mobile/resource/js/list/CardItemListMixin">
				</ul>
			</div>
			
			<%--  快速审批按钮   --%>	
			<%
				boolean isCanFastTodo = false;
				String fastTodoIds = new LbpmSettingDefault().getFastTodoIds();
				if(StringUtil.isNull(fastTodoIds) || UserUtil.checkUserIds(Arrays.asList(fastTodoIds.split(";")))){
					isCanFastTodo = true;
				}
				request.setAttribute("isCanFastTodo",isCanFastTodo);
			%>
			<c:if test="${isCanFastTodo}">
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" id="fastReviewTodo">
					<li data-dojo-type="sys/lbpmperson/mobile/resource/js/FastReviewTodoButton" data-dojo-props="scrollView:'scrollView'">
						<bean:message bundle="sys-notify" key="sysNotifyTodo.process.fastReview"/>
					</li>
				</ul>
			</c:if>	
				
		</div>
		
		<%--  搜索   --%>	
		<div id="cardSearchView" data-dojo-type="dojox/mobile/View">
			   <div id="muiNotifySearchBar" class="muiNotifySearchBar">
				 <form action="" onsubmit="return _onSearch(this)">
					<div class="muiNotifySearchBarContainer" >
						<%--  下拉选择框组件     --%>
						<div class="muiNotifySearchTypeContainer">
						<div data-dojo-type="sys/notify/mobile/resource/js/search/NotifyTypeSelect" class="muiNotifySearchType"
						     data-dojo-props="name:'searchType',optionDatas:[{value:'todo',text:'<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title1"/>'},{value:'toview',text:'<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title2"/>'},{value:'tododone',text:'<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title3"/>'}]" >
						</div>
						</div>
                        <%--  搜索框    --%>
						<div class="muiNotifySearchInputContainer">
						  <div class="muiNotifySearchIcon mui mui-search"></div>
						  <div class="muiNotifySearchClear mui mui-fail" onclick="resetKeyword()"></div>
						  <input class="muiSearchInput muiFontSizeM muiFontColorInfo" autocomplete="off" oninput="checkKeyword(this.value)" id="keyWord" type="search" name="keyWord" placeholder='<bean:message key="button.search"/>'>
						</div>
						<%--  “取消”按钮    --%>
					    <div class="muiCardSearchBtnDiv"><div class="muiCardSearchCancelBtn muiFontSizeM muiFontColorMuted" onclick="onBackToMainView(this)"><bean:message key="button.cancel"/></div></div>
					</div>
				</form>
			  </div>
	    </div>
	</template:replace>
</template:include>
