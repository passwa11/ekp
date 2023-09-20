<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<c:set var="tiny" value="true" scope="request" />

<script>
	require(['dojo/dom', 'dojo/query','dojo/on','dojo/dom-class', 'dojo/dom-attr', 'dojo/dom-style', 'dojo/topic', 'mui/dialog/Tip','mui/syscategory/SysCategoryUtil'],function(dom, query,on, domClass,domAttr, domStyle, topic, Tip,SysCategoryUtil){
		
		window.toggleCreateFrame = function() {
			var ele = query(".muiMeetingDropToggle",document)[0];
			var menuEle = query(".muiMeetingDropMenu",document)[0];
			if(ele && domClass.contains(ele, "on")){
				domClass.remove(ele, "on");
				domStyle.set(menuEle,"display","none");
				domClass.remove(document.body, "create_mask");
			}else{
				domClass.add(ele, "on");
				domStyle.set(menuEle,"display","block");
				domClass.add(document.body, "create_mask");
			}
		};
		
		window.navigate2Place = function() {
			topic.publish('/km/imeeting/changeview', 'place');
		}

	});
</script>
<div id="calendar" 
	data-create-active="false"
	data-dojo-type="mui/calendar/CalendarView"
	data-dojo-props="defaultDisplay:'week'">
	
	<div style="margin: 1rem 1rem 0;">
		<div data-dojo-type="mui/calendar/CalendarHeader"
			data-dojo-props="right:{icon:'mui_ekp_meeting_to_list',href:'/km/imeeting/mobile/index_list_my.jsp?showList=true'}"></div>
	</div>
	
	<div data-dojo-type="mui/calendar/CalendarWeek"></div>
	<div data-dojo-type="mui/calendar/CalendarContent"></div>
	<div data-dojo-type="mui/calendar/CalendarBottom" 
		data-dojo-props="
			url:''
		">
		<div  data-dojo-type="mui/calendar/CalendarListScrollableView">
			<ul data-dojo-type="mui/calendar/CalendarJsonStoreList" class="mui_ekp_meeting_timeline_list"
				data-dojo-mixins="km/imeeting/mobile/resource/js/list/CalendarItemListMixin"
				data-dojo-props="noSetLineHeight:true,nodataImg:'${LUI_ContextPath}/km/imeeting/mobile/resource/images/imeeting_nodata_bg.png',
				nodataText:'<bean:message bundle="km-imeeting" key="mobile.no.data" />',url:'/km/imeeting/km_imeeting_calendar/kmImeetingCalendar.do?method=mobileCalendar&fdStart=!{fdStart}&fdEnd=!{fdEnd}&showNotExam=true'">
			</ul>
		</div>
	</div>
	
	
	
	<c:if test="${canCreateMeeting == true || canCreateCloud == true || canCreateBook == true}">
		<%if(KmImeetingConfigUtil.isVideoMeetingEnable()){ %>
		 <div data-dojo-type="sys/mportal/module/mobile/elements/Button"
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
			</div>
		<%} else {%>
			<div id="ImeetingCreateBtn" style="margin-bottom: 5rem;"
				data-dojo-type="dojox/mobile/TabBarButton"
				data-dojo-props="
					href:'${LUI_ContextPath}/km/imeeting/mobile/index_place_new.jsp'
				"
				data-active="false">
				<i class="mui mui-plus"></i>
			</div>
		<%} %>
	</c:if>
</div>
