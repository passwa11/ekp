<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list">
	<template:replace  name="title">
		<bean:message  bundle="sys-ftsearch-db" key="search.ftsearch.mobile"/>
	</template:replace>
	<template:replace name="head">
		<mui:cache-file name="mui-sys-notify.js" cacheType="md5" />
		<mui:cache-file name="mui-sys-notify.css" cacheType="md5"/>
	</template:replace>
	<template:replace name="content">
		<script type="text/javascript">
		    require(['sys/notify/mobile/resource/js/search/SearchNotifyMain']);
			require(["dojo/topic","dojo/dom","dojo/dom-style"], function(topic , dom ,domStyle){
				try {
					// 监听搜索完成事件，并显示“搜索到N项结果”
					topic.subscribe("/mui/list/loaded",function(evt){
						var count = 0;
						if(evt)
							count = evt.totalSize;
						var titleDiv = dom.byId("_searchTitle");
					    var tip = '<bean:message bundle="sys-notify" key="sysNotifyTodo.searching.result"/>';
						var counttxt = '<span>' + count + '</span>';
						titleDiv.innerHTML = tip.replace('%s',counttxt);
						domStyle.set(titleDiv,{display:'block'});
					});
					// 监听下拉框change事件，重新触发按搜索关键字和选中的消息类型查询
					topic.subscribe("/mui/notify/select/change",function(value){
						window.location.href='${KMSS_Parameter_ContextPath}sys/notify/mobile/searchresult.jsp?keyword=${JsParam.keyword}&moduleName=${JsParam.moduleName}&searchType='+value+location.hash;
					});
				}catch (e) {
					if(window.console)
						console.error(e);
				}
			});
			window.onSearchListBack = function(){
				window.location.href='${KMSS_Parameter_ContextPath}sys/notify/mobile/index.jsp?moduleName=${JsParam.moduleName}'+location.hash;
			}
		</script>
	   <div id="cardSearchView">
	     <div id="muiNotifySearchBar" class="muiNotifySearchBar">
			 <form action="" onsubmit="return _onSearch(this)">
					<div class="muiNotifySearchBarContainer" >
						<%--  下拉选择框组件     --%>
						<div class="muiNotifySearchTypeContainer">
						<div data-dojo-type="sys/notify/mobile/resource/js/search/NotifyTypeSelect" class="muiNotifySearchType"
						     data-dojo-props="name:'searchType',value:'${JsParam.searchType}',optionDatas:[{value:'todo',text:'<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title1"/>'},{value:'toview',text:'<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title2"/>'},{value:'tododone',text:'<bean:message bundle="sys-notify" key="sysNotifyTodo.tab.title3"/>'}]" >
						</div>
						</div>
	                       <%--  搜索框    --%>
						<div class="muiNotifySearchInputContainer">
						  <div class="muiNotifySearchIcon mui mui-search"></div>
						  <div class="muiNotifySearchClear mui mui-fail" onclick="resetKeyword()"></div>
						  <input class="muiSearchInput muiFontSizeM muiFontColorInfo" autocomplete="off" oninput="checkKeyword(this.value)" id="keyWord" type="search" name="keyWord" value='${HtmlParam.keyword}' placeholder='<bean:message key="button.search"/>' />
						</div>
						<%--  “取消”按钮    --%>
					    <div class="muiCardSearchBtnDiv"><div class="muiCardSearchCancelBtn muiFontSizeM muiFontColorMuted" onclick="onSearchListBack(this)"><bean:message key="button.cancel"/></div></div>
					</div>			
			  </form>
		  </div>
		</div>
		<c:if test="${param.keyword!=''&&param.keyword!=null}">
			<div id="scroll" data-dojo-type="mui/list/StoreScrollableView" class="white">
					<div class="muiSearchTitle muiNotifySearchTitle" id='_searchTitle'>
						<bean:message bundle="sys-notify" key="sysNotifyTodo.button.searching"/>
					</div>
					<c:set var="oprType" value="doing" />
					<c:set var="fdType" value="13" />
					<c:if test="${JsParam.searchType=='toview'}">
						<c:set var="fdType" value="2" />
					</c:if>
					<c:if test="${JsParam.searchType=='tododone'}">
						<c:set var="fdType" value="" />
						<c:set var="oprType" value="done" />
					</c:if>
				    <ul 
				    	data-dojo-type="mui/list/JsonStoreList"  class="muiList notifyCardList"
				    	data-dojo-mixins="sys/notify/mobile/resource/js/list/CardItemListMixin"
						data-dojo-props="lazy:false,url:encodeURI('/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&oprType=${pageScope.oprType}&fdType=${pageScope.fdType}&q.fdSubject=${JsParam.keyword}'),nodataImg:'${LUI_ContextPath}/sys/mportal/mobile/css/imgs/nodata.png'">
					</ul>
			</div>
		</c:if>
		<c:if test="${param.keyword==null || param.keyword =='' }">
			<div id="scroll" data-dojo-type="mui/view/DocScrollableView">
			</div>
		</c:if>
	</template:replace>
</template:include>
