<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true"> 
	<template:replace name="title">
		<c:if test="${JsParam.moduleName!=null && JsParam.moduleName!=''}">
			${HtmlParam.moduleName}
		</c:if>
		<c:if test="${JsParam.moduleName==null or JsParam.moduleName==''}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meeting.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="content">
	
		<div data-dojo-type="mui/tabbar/TabBarView">
			<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
			    <%-- Tab页签（注：当使用MobileCfgNavBar构建页签栏时，如果有模块有配置扩展点，则优先通过modelName从“移动办公--移动应用管理--应用页签配置”）读取数据 ，若无相关配置数据则读取defaultUrl配置的静态数据 --%>
				<div data-dojo-type="mui/nav/MobileCfgNavBar" 
					 data-dojo-props="defaultUrl:'/km/imeeting/mobile/nav_summary.jsp'"> 
				</div>
				
				<%-- 搜索 --%>
				<div data-dojo-type="mui/search/SearchButtonBar"
					 data-dojo-props="modelName:'com.landray.kmss.km.imeeting.model.KmImeetingSummary'">
				</div>
			</div>
			
			<div data-dojo-type="mui/header/NavHeader">
			</div>
			
			<div class="mui_ekp_meeting_cardList" data-dojo-type="mui/list/NavView">
			    <ul data-dojo-type="mui/list/HashJsonStoreList" 
			    	data-dojo-mixins="km/imeeting/mobile/resource/js/list/SummaryCardItemListMixin">
				</ul>
			</div>
			
			<div class="lui_db_footer">
			    <div class="lui_db_footer_item" onclick="navigate2View(0,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img my"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.myMeeting" /></p>
			    </div>
			    <div class="lui_db_footer_item" onclick="navigate2View(1,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img place"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.meetingRoom" /></p>
			    </div>
			    <div class="lui_db_footer_item active" onclick="navigate2View(2,'${JsParam.showList}')">
			        <div class="lui_db_footer_item_img summary"></div>
			        <p><bean:message bundle="km-imeeting" key="mobile.meetingSummary" /></p>
			    </div>
			    <div class="lui_db_footer_item" onclick="navigate2View(3,'${JsParam.showList}')">
			        <div class="lui_db_footer_item_img more"></div>
			        <p><bean:message bundle="km-imeeting" key="mobile.other" /></p>
			    </div>
			</div>
			<script>
				window.navigate2View = function(view,showList) {
					var url ="${LUI_ContextPath}/km/imeeting/mobile/";
					if(0 == view){
						url += "index.jsp"
					}else if(1 == view){
						url += "index_place_new.jsp"
					}else if(2 == view){
						url += "index_summary.jsp"
					}else if(3 == view){
						url += "index_other.jsp"
					}
					if(showList == 'true'){
						url += "?showList="+showList;
					}
					window.open(url, '_self');
				}
			</script>
		</div>
		
	</template:replace>
</template:include>