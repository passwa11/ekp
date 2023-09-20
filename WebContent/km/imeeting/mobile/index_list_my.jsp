<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%
	request.setAttribute("useCloud", KmImeetingConfigUtil.isUseCloudMng());
%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" canHash="true" bodyClass="lui_imeeting_list_body"> 
	<template:replace name="title">
		<c:if test="${JsParam.moduleName!=null && JsParam.moduleName!=''}">
			${HtmlParam.moduleName}
		</c:if>
		<c:if test="${JsParam.moduleName==null or JsParam.moduleName==''}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<style>
			.lui_imeeting_list_body .muiTop{
				bottom: 16rem;
			}
		</style>
		<mui:cache-file name="mui-module.css" cacheType="md5" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/index.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meeting.css?s_cache=${MUI_Cache}" />
		<mui:min-file name="mui-imeeting.js"/>
		
		<script type="text/javascript">
			require(['dijit/registry', 'dijit/registry', 'dojo/topic','mui/util','mui/syscategory/SysCategoryUtil'],function(registry, registry, topic, util,SysCategoryUtil){
				
				var currentDate=new Date();
				topic.subscribe('/mui/calendar/valueChange',function(widget,args){
					currentDate=args.currentDate;
				});
				
				topic.subscribe('/km/imeeting/changeview', function(view) {
					if(view){
						var viewObj = registry.byId(view);
						viewObj.resize();
					}
		    	});
				
				//新建会议预约(无模板模式)
				window.create = function(type){
					var url="${LUI_ContextPath}/km/imeeting/mobile/index_book.jsp?moduleName=${JsParam.moduleName }&createType="+type;
					window.open(url,'_self');
				};
				
				window.backToCalendar=function(){
					location.href=util.formatUrl('/km/imeeting/mobile/index.jsp');
				};
				
			});
		</script>
	</template:replace>
	<template:replace name="content">
	
		<c:set var="canCreateMeeting" value="false"></c:set>
		<c:set var="canCreateCloud" value="false"></c:set>
		<c:set var="canCreateBook" value="false"></c:set>
		
		<kmss:authShow roles="ROLE_KMIMEETING_CREATE">
			<c:set var="canCreateMeeting" value="true"></c:set>
		</kmss:authShow>
		<c:if test="${useCloud}"> 
			<kmss:authShow roles="ROLE_KMIMEETING_CREATE_CLOUD">
				<c:set var="canCreateCloud" value="true"></c:set>
			</kmss:authShow>
		</c:if>
		<kmss:authShow roles="ROLE_KMIMEETING_BOOKCREATE">
			<c:set var="canCreateBook" value="true"></c:set>
		</kmss:authShow>
		
		<div data-dojo-type="mui/tabbar/TabBarView">
			
			<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
				<div
					data-dojo-type="mui/nav/MobileCfgNavBar" 
					data-dojo-props=" defaultUrl:'/km/imeeting/mobile/nav_my.jsp'">
				</div>
				
				<div
					data-dojo-type="mui/search/SearchButtonBar" 
					data-dojo-props="modelName:'com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary' ">
				</div>
				
				<div class="fontmuis muis-view-calendar" onclick="backToCalendar();"></div>
				
				
				<c:if test="${canCreateMeeting == true || canCreateCloud == true || canCreateBook == true}">
					<%if(KmImeetingConfigUtil.isVideoMeetingEnable()){ %>
				 		<div data-dojo-type="sys/mportal/module/mobile/elements/Button" style="z-index:110"
						data-dojo-props="icon:'muis-new',datas:[
							<c:if test="${canCreateBook == true}">
							{
							'icon':'muis-conference-order',
							'text':'<bean:message bundle="km-imeeting" key="mobile.meetingBook" />',
							'click': function(){
					   	    		window.open(dojoConfig.baseUrl + 'km/imeeting/km_imeeting_book/kmImeetingBook.do?method=add', '_self');
					   	    	}
							},
							</c:if>
							<c:if test="${canCreateMeeting == true}">
							{
							'icon':'muis-conference-arrange',
							'text':'<bean:message bundle="km-imeeting" key="mobile.meetingArrangements" />',
							'click':function(){
									require(['mui/syscategory/SysCategoryUtil'], function(SysCategoryUtil){
										SysCategoryUtil.create({
											  key: 'normalMeeting',
							                  createUrl: '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{curIds}&mobile=true',
							                  modelName: 'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
							                  mainModelName: 'com.landray.kmss.km.imeeting.model.KmImeetingMain',
							                  showFavoriteCate:'true',
							         		  authType: '02'
							            });
									});
						    	}
							},
							<%if("true".equals(KmImeetingConfigUtil.isCycle())){ %>
							{
							'icon':'muis-conference-period',
							'text':'<bean:message bundle="km-imeeting" key="mobile.recurringMeeting" />',
							'click':function(){
									require(['mui/syscategory/SysCategoryUtil'], function(SysCategoryUtil){
										SysCategoryUtil.create({
											  key: 'cycleMeeting',
							                  createUrl: '/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{curIds}&isCycle=true&mobile=true',
							                  modelName: 'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
							                  mainModelName: 'com.landray.kmss.km.imeeting.model.KmImeetingMain',
							                  showFavoriteCate:'true',
							         		  authType: '02'
							            });
									});
						    	}
							},
							<%} %>
							
							{
							'icon':'muis-conference-video',
							'text':'<bean:message bundle="km-imeeting" key="mobile.videoMeeting" />',
							'click':function(){
					   	    		window.open(dojoConfig.baseUrl + 'km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&noTemplate=true', '_self');
					   	    	}
							}
							
							</c:if>
						]"></div>
					<%} else {%>
						<div id="ImeetingCreateBtn" style="margin-bottom: 5rem;position:fixed"
							data-dojo-type="mui/tabbar/TabBarButton"
							data-dojo-props="
								href:'/km/imeeting/mobile/index_place_new.jsp?showList=${JsParam.showList}'
							"
							data-active="false">
							<i class="mui mui-plus"></i>
						</div>
					<%} %>
				</c:if>
			</div>
			
			<div data-dojo-type="mui/header/NavHeader">
			</div>
			
			<div class="mui_ekp_meeting_dateList" data-dojo-type="mui/list/NavView">
			    <ul  data-dojo-type="mui/list/HashJsonStoreList" 
			    	data-dojo-mixins="km/imeeting/mobile/resource/js/list/MyMeetingCardItemListMixin">
				</ul>
			</div>
			
			<div class="lui_db_footer">
			    <div class="lui_db_footer_item active" onclick="navigate2View(0,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img my"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.myMeeting" /></p>
			    </div>
			    <div class="lui_db_footer_item" onclick="navigate2View(1,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img place"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.meetingRoom" /></p>
			    </div>
			    <div class="lui_db_footer_item " onclick="navigate2View(2,'${JsParam.showList}')">
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
						if(showList == 'true'){
							url += "index_list_my.jsp"
						}else{
							url += "index.jsp"
						}
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