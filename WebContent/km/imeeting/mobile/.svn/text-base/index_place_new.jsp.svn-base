<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.km.imeeting.util.ImeetingCateUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<% 
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	request.setAttribute("defaultCateId", ImeetingCateUtil.getfirstCate()); 
	request.setAttribute("nowDate", DateUtil.convertDateToString(new Date(), ResourceUtil.getString("date.format.date")));
	request.setAttribute("useCycle", KmImeetingConfigUtil.isCycle());
%>
<c:set var="tiny" value="true" scope="request" />
<template:include ref="mobile.list" > 
	<template:replace name="title">
		<c:if test="${JsParam.moduleName!=null && JsParam.moduleName!=''}">
			${HtmlParam.moduleName}
		</c:if>
		<c:if test="${JsParam.moduleName==null or JsParam.moduleName==''}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/index.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/meeting.css?s_cache=${MUI_Cache}" />
		<mui:min-file name="mui-imeeting.js"/>
		<style>.muiFormSelectText{overflow: hidden;white-space: nowrap;text-overflow: ellipsis;}</style>
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
		
		<div id="place" data-dojo-type="mui/tabbar/TabBarView">

			<div data-dojo-type="mui/header/Header" class="meetingHeader" data-dojo-props="height:'4rem'"  style="border-bottom: none">
				 
				 <div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceCateSelect" class="placeCateSelect"
				 	  data-dojo-props="url:'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=getAllCate',name:'fdPeriodId',mul:false,showStatus:'edit',value:'${defaultCateId}'"
				 	  style="height:100%;width: -webkit-calc(100% - 15rem);width: -moz-calc(100% - 15rem);width: calc(100% - 15rem);position: absolute;">
				 </div>
				 
				 <!--  
				 <div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceDateSelect" class="placeCateSelect" style="height:100%"
				 	  data-dojo-props="url:'/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=generateTime',name:'fdTime',mul:false,showStatus:'edit'">
				 </div>
				 -->
				 
		 		 <div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceDatePicker" class="placeCateSelect" style="height:100%;min-width: 15rem;float: right;"
		 		 		data-dojo-mixins="mui/datetime/_DateMixin"
						data-dojo-props="canClear: false, value:'${nowDate }'">
				 </div>
				 
			</div> 
			
			<div class="muiMeetingBookContainer">
				<div id="timeBar" data-dojo-type="km/imeeting/mobile/resource/js/list/TimeBar" ></div>
				<div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceBar"  data-dojo-props="curCateId:'${defaultCateId}'">
				</div>
				<div id="placeContent" data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceContentScrollView">
					<div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceContent" class="placeContentContainer"></div>
				</div>
			</div>
			
			
			<div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceBottom"  
				data-dojo-mixins="km/imeeting/mobile/resource/js/PlaceBottomMixin"
				data-dojo-props="height:'27.5rem'">
			</div>
			
			<div id="ImeetingCreateFrame">
			
				<c:if test="${canCreateBook == true }">
					<div onclick="createBook()">
						<div class="create_title"><bean:message bundle="km-imeeting" key="mobile.meetingBook.v" /></div>
						<div class="create_desc"><bean:message bundle="km-imeeting" key="mobile.meetingBook.desc" /></div>
					</div>
				</c:if>
			
				<c:if test="${canCreateCloud == true }">
					<div onclick="createCloudMeeting()">
						<div class="create_title"><bean:message bundle="km-imeeting" key="mobile.temp.meeting" /></div>
						<div class="create_desc"><bean:message bundle="km-imeeting" key="mobile.temp.meeting.desc" /></div>
					</div>
				</c:if>
				<c:if test="${canCreateMeeting == true }">
					<div onclick="createNormalMeeting()">
						<div class="create_title"><bean:message bundle="km-imeeting" key="mobile.meetingArrangements.v" /></div>
						<div class="create_desc"><bean:message bundle="km-imeeting" key="mobile.meetingArrangements.desc" /></div>
					</div>
				</c:if>
				
				<c:if test="${useCycle eq 'true'}">
					<c:if test="${canCreateMeeting == true }">
						<div onclick="createCycleMeeting()">
							<div class="create_title"><bean:message bundle="km-imeeting" key="mobile.recurringMeeting.v" /></div>
							<div class="create_desc"><bean:message bundle="km-imeeting" key="mobile.recurringMeeting.desc" /></div>
						</div>
					</c:if>
				</c:if>
			</div>
			
			<script>
		        require(['dijit/registry', 'dojo/dom', 'dojo/on', 'dojo/dom-attr', 'dojo/topic', 'mui/util', 'dojo/_base/lang',
		                 'mui/dialog/Tip', 'dojo/domReady','mui/syscategory/SysCategoryUtil'],
		        		function(registry, dom, on, domAttr, topic, util, lang, Tip, ready,SysCategoryUtil){
		    		
					var placeNode = dom.byId('place');
					
					window.__MEETING_CREATE_PAYLOAD__ = {};
					
					on(dom.byId('ImeetingCreateFrame'), 'click', function(e) {
						var target = e.target;
						if(target && target.id == 'ImeetingCreateFrame') {
							domAttr.set(placeNode, 'data-create-active', 'false');
						}
					});
		        	
		        	topic.subscribe('/km/imeeting/create', function(ctx, payload) {
		        		
		        		if('${canCreateCloud}' == 'true' || '${canCreateMeeting}' == 'true' || '${canCreateBook}' == 'true') {        			
			        		domAttr.set(placeNode, 'data-create-active', 'true');
			        		window.__MEETING_CREATE_PAYLOAD__ = payload;
		        		} else {
		        			Tip.tip({icon:'mui mui-warn', text:'很抱歉，您没有预约会议权限！',width:'260',height:'60'});
		        		}
		        		
		        	});
		        	
		        	topic.subscribe('/km/imeeting/changeview', function(view) {
		        		
		        		if(view != 'place') {
		        			return;
		        		}
		        		
		        		topic.publish('/km/imeeting/place/resize');
		    			topic.publish('/km/imeeting/timeNavBar/resetTransform');
		    			topic.publish('/km/imeeting/placeNavBar/resetTransform');
		        	});
		        	
					window.createBook = function() {
						
						var url = lang.replace('${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=add&' + 
								'fdTime={fdTime}&fdStratTime={fdStartTime}&fdFinishTime={fdFinishTime}&resId={resId}', window.__MEETING_CREATE_PAYLOAD__ || {});
						domAttr.set(placeNode, 'data-create-active', 'false');
						window.open(url, '_self');
					}
					
					window.createCloudMeeting = function() {
						
						var url = lang.replace('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&' + 
								'fdTime={fdTime}&fdStratTime={fdStartTime}&fdFinishTime={fdFinishTime}&resId={resId}', window.__MEETING_CREATE_PAYLOAD__ || {});
						
						window.open(url, '_self');
						
					}
					
					window.createNormalMeeting = function(){
						var url = lang.replace('/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&' + 
								'fdTime={fdTime}&fdStratTime={fdStartTime}&fdFinishTime={fdFinishTime}&resId={resId}', window.__MEETING_CREATE_PAYLOAD__ || {});
						var createUrl = url + '&fdTemplateId=!{curIds}';
						domAttr.set(placeNode, 'data-create-active', 'false');
						SysCategoryUtil.create({
							  key: "normalMeeting",
			                  createUrl: createUrl,
			                  modelName: "com.landray.kmss.km.imeeting.model.KmImeetingTemplate",
			                  mainModelName: "com.landray.kmss.km.imeeting.model.KmImeetingMain",
			                  showFavoriteCate:'true',
			         		  authType: '02'
			            });
					}
					
					window.createCycleMeeting = function(){
						var url = lang.replace('/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&' + 
								'fdTime={fdTime}&fdStratTime={fdStartTime}&fdFinishTime={fdFinishTime}&resId={resId}', window.__MEETING_CREATE_PAYLOAD__ || {});
						var createUrl = url + '&fdTemplateId=!{curIds}&isCycle=true';
						SysCategoryUtil.create({
							  key: "cycleMeeting",
			                  createUrl: createUrl,
			                  modelName: "com.landray.kmss.km.imeeting.model.KmImeetingTemplate",
			                  mainModelName: "com.landray.kmss.km.imeeting.model.KmImeetingMain",
			                  showFavoriteCate:'true',
			         		  authType: '02'
			            });
					}
		
					window.onresize = function(){
		    	        	
						var timeBar = registry.byId('timeBar');
						var placeContent = registry.byId('placeContent');
		    			 
						timeBar.setTransformMaxHeight();
						placeContent.resize();
		    			 
		    	    };
		    	    
		    	    // 解决首次进入会议室列表错位情况
		    	    ready(function() {
		    	    	topic.publish('/km/imeeting/changeview', 'place');
		    	    });
		    	    
		    	});
			</script>
			
			<div class="lui_db_footer">
			    <div class="lui_db_footer_item" onclick="navigate2View(0,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img my"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.myMeeting" /></p>
			    </div>
			    <div class="lui_db_footer_item active" onclick="navigate2View(1,'${JsParam.showList}')">
			      <div class="lui_db_footer_item_img place"></div>
			      <p><bean:message bundle="km-imeeting" key="mobile.meetingRoom" /></p>
			    </div>
			    <div class="lui_db_footer_item" onclick="navigate2View(2,'${JsParam.showList}')">
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