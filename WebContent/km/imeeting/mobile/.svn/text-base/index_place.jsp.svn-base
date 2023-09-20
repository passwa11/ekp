<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.km.imeeting.util.ImeetingCateUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="java.util.Map"%>
<% 
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	request.setAttribute("defaultCateId", ImeetingCateUtil.getfirstCate()); 
	request.setAttribute("nowDate", DateUtil.convertDateToString(new Date(), ResourceUtil.getString("date.format.date")));
%>
<div id="place" data-dojo-type="mui/tabbar/TabBarView" data-dojo-props="selected:localStorage['swapIndex:${LUI_ContextPath}/km/imeeting/mobile']=='place'">

	<div data-dojo-type="mui/header/Header" class="meetingHeader" data-dojo-props="height:'4rem'"  style="border-bottom: none">
		 
		 <div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceCateSelect" class="placeCateSelect" style="height:100%"
		 	  data-dojo-props="url:'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=getAllCate',name:'fdPeriodId',mul:false,showStatus:'edit',value:'${defaultCateId}'">
		 </div>
		 
		 <!--  
		 <div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceDateSelect" class="placeCateSelect" style="height:100%"
		 	  data-dojo-props="url:'/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=generateTime',name:'fdTime',mul:false,showStatus:'edit'">
		 </div>
		 -->
		 
 		 <div data-dojo-type="km/imeeting/mobile/resource/js/list/PlaceDatePicker" class="placeCateSelect" style="height:100%"
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
				<div class="create_title">会议预约</div>
				<div class="create_desc">直接占用会议室,可用于非会议场景</div>
			</div>
		</c:if>
	
		<c:if test="${canCreateCloud == true }">
			<div onclick="createCloudMeeting()">
				<div class="create_title">临时会议</div>
				<div class="create_desc">临时性直接召开,无需走模版流程</div>
			</div>
		</c:if>
		<c:if test="${canCreateMeeting == true }">
			<div data-dojo-type="km/imeeting/mobile/resource/js/CreateButton"
				data-dojo-mixins="mui/syscategory/SysCategoryMixin" 
		   		data-dojo-props="
		   			createUrl:'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{curIds}&fdTime=!{fdTime}&fdStratTime=!{fdStartTime}&fdFinishTime=!{fdFinishTime}&resId=!{resId}',
		   			mainModelName:'com.landray.kmss.km.imeeting.model.KmImeetingMain',
		   			modelName:'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
		   			showFavoriteCate:'true',
		   			authType: '02',
		   			icon1:'',
		   			key: 'normalMeeting'"
				>
				<div class="create_title">会议安排</div>
				<div class="create_desc">内置会议安排模版,提前统筹各项工作</div>
			</div>
		</c:if>
		
		<c:if test="${kmImeetingConfig.useCyclicity eq '2' || kmImeetingConfig.useCyclicity eq '3' && fn:contains(kmImeetingConfig.useCyclicityPersonId,userId) == true}">
			<c:if test="${canCreateMeeting == true }">
				<div data-dojo-type="km/imeeting/mobile/resource/js/CreateButton"
					data-dojo-mixins="mui/syscategory/SysCategoryMixin" 
			   		data-dojo-props="
			   			createUrl:'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{curIds}&fdTime=!{fdTime}&fdStratTime=!{fdStartTime}&fdFinishTime=!{fdFinishTime}&resId=!{resId}&isCycle=true',
			   			mainModelName:'com.landray.kmss.km.imeeting.model.KmImeetingMain',
			   			modelName:'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
			   			showFavoriteCate:'true',
			   			authType: '02',
			   			icon1:'',
			   			key: 'cycleMeeting'"
					>
					<div class="create_title">周期会议</div>
					<div class="create_desc">设置会议周期规则,快捷批量设置</div>
				</div>
			</c:if>
		</c:if>
	</div>
	
	<script>
        require(['dijit/registry', 'dojo/dom', 'dojo/on', 'dojo/dom-attr', 'dojo/topic', 'mui/util', 'dojo/_base/lang',
                 'mui/dialog/Tip', 'dojo/domReady'],
        		function(registry, dom, on, domAttr, topic, util, lang, Tip, ready){
    		
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
				
				window.open(url, '_self');
			}
			
			window.createCloudMeeting = function() {
				
				var url = lang.replace('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&' + 
						'fdTime={fdTime}&fdStratTime={fdStartTime}&fdFinishTime={fdFinishTime}&resId={resId}', window.__MEETING_CREATE_PAYLOAD__ || {});
				
				window.open(url, '_self');
				
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
</div>