<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.calendar.model.KmCalendarPersonGroup"%>
<%@page import="com.landray.kmss.km.calendar.service.IKmCalendarPersonGroupService"%>
<%
	String currentUserId = UserUtil.getUser().getFdId();
	pageContext.setAttribute("currentUserId", currentUserId);
	IKmCalendarPersonGroupService personGroupService = (IKmCalendarPersonGroupService)SpringBeanUtil.getBean("kmCalendarPersonGroupService");
	List<KmCalendarPersonGroup> groups = personGroupService.getUserPersonGroup(currentUserId);
	boolean hasPersonGroup = !groups.isEmpty();
	request.setAttribute("hasPersonGroup", hasPersonGroup);
	if(hasPersonGroup){
		request.setAttribute("personGroupId", groups.get(0).getFdId());
	}
%>
<template:include ref="mobile.list" tiny="true">
	<template:replace name="title">
		<c:if test="${param.moduleName!=null && param.moduleName!=''}">
			<c:out value="${param.moduleName}"></c:out>
		</c:if>
		<c:if test="${param.moduleName==null or param.moduleName==''}">
			<bean:message bundle="km-calendar"  key="module.km.calendar.tree.share.calendar"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/calendar.css?s_cache=${MUI_Cache}"></link>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/mobile/resource/css/group.css?s_cache=${MUI_Cache}" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/mobile/resource/css/list.css?s_cache=${MUI_Cache}" />
		<mui:min-file name="mui-calendar.js"/>
	</template:replace>
	<template:replace name="content">
	
		<div class="muiCalendarContainer">
			<div id="calendar" class="muiCalendarListView" data-dojo-type="mui/calendar/CalendarView">
				<div style="margin: 1rem 1rem 0;">
					<div data-dojo-type="mui/calendar/CalendarHeader"></div>
				</div>
				<div data-dojo-type="mui/calendar/CalendarWeek"></div>
				<div data-dojo-type="mui/calendar/CalendarContent"></div>
				<div data-dojo-type="mui/calendar/CalendarBottom" data-dojo-props="url:''">
					<div data-dojo-type="mui/calendar/CalendarBottomOpt" class="muiCalendarBottomOpt">
						<c:set var="groupId" value="${param.groupId }"></c:set>
						<c:if test="${empty groupId }">
							<c:set var="groupId" value="defaultGroup"></c:set>
						</c:if>
						<div data-dojo-type="km/calendar/mobile/resource/js/GroupSelect"
							data-dojo-props="value:'${groupId}',isShowAuthBtn:true,
							url:'${LUI_ContextPath}/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listUserGroupJson',
							openUrl:'${LUI_ContextPath}/km/calendar/mobile/group.jsp'">
						</div>
						<div class="mui_share_person" data-dojo-type="km/calendar/mobile/resource/js/SharePersonSelect"
							data-dojo-props="groupId:'${groupId}',personIds:'${JsParam.personIds }'"></div>
					</div>
					<div class="CalendarListView" data-dojo-type="mui/calendar/CalendarListScrollableView">
						<%
							if ("zh".equals(UserUtil.getKMSSUser().getLocale().getLanguage())) {
								request.setAttribute("nodataImgPath", request.getContextPath() + 
										"/km/calendar/mobile/resource/images/calendar_nodate.png");
							} else {
								request.setAttribute("nodataImgPath", request.getContextPath() + 
										"/km/calendar/mobile/resource/images/calendar_nodate_en.png");
							}
						%>
						<ul class="mui_ekp_portal_date_datails"
							data-dojo-type="km/calendar/mobile/resource/js/GroupJsonStoreList"
							data-dojo-mixins="km/calendar/mobile/resource/js/list/GroupItemListMixin"
							data-dojo-props="noSetLineHeight:true,nodataImg:'${nodataImgPath}',nodataText:'',
							url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=listGroupCalendar&loadAll=true&groupId=${groupId}&fdStart=!{fdStart}&fdEnd=!{fdEnd}&personIds=${param.personIds }'">
						</ul>
					</div>
				</div>
			</div>
			
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
			   	<li class="calendarUnSelected calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-my-schedule muiFontSizeM'" onclick="changePage(1)">
			   		<bean:message bundle="km-calendar"  key="kmCalendarMain.calendar.header.title"/>
			   	</li>
				<li class="calendarLi"
			   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-apply muiFontSizeM'" onclick="changePage(2)">
			   		<bean:message bundle="km-calendar"  key="module.km.calendar.tree.share.calendar"/>
			   	</li>
			   	<c:if test="${hasPersonGroup}">
				   	<li class="calendarUnSelected calendarLi"
				   		data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'calendarIcon fontmuis muis-cluster muiFontSizeM'" onclick="changePage(3)">
				   		<bean:message bundle="km-calendar"  key="kmCalendarMain.group.header.title"/>
				   	</li>
			   	</c:if>
			</ul>
			
		</div>
		<div style="display: none;">
			<div id="sharePersonScrollView" data-dojo-type="mui/list/StoreScrollableView" class="muiSharePersonPanel">
				<div class="muiSharePersonHead">
					<div class="muiSharePersonSelAll"><div class="muiSelBoxMul"></div></div><div class="muiSharePersonCheckbox">全选</div>
				</div>
				
				<ul data-dojo-type="mui/list/JsonStoreList"
					data-dojo-mixins="km/calendar/mobile/resource/js/list/SharePersonListMixin"
					data-dojo-props="url:'/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listGroupSearch&groupId=${groupId}&loadAll=true&checkType=read',lazy:false">
				</ul>
			</div>
		</div>

	</template:replace>
</template:include>
<script>
	require(["dojo/dom-construct","dojo/dom",'mui/util', 'dojo/topic','mui/calendar/CalendarUtil','mui/device/adapter','dijit/registry','dojo/ready','dojo/on','dojo/query','dojo/touch',"dojo/dom-class"],
			function(domConstruct,dom,util,topic,cutil,adapter,registry,ready,on,query,touch,domClass){
		var currentDate=new Date();
		topic.subscribe('/mui/calendar/valueChange',function(widget,args){
			currentDate=args.currentDate;
		});
		topic.subscribe('/calendar/group/resetShareperson',function(widget,args){
			var params = util.getUrlParameter(location.href,'personIds');
			var personIds = args.personIds;
			
			if(!personIds){//清空共享人员
				if(params){
					var url = util.setUrlParameter(location.href,'personIds','')
					location.href = url;
					return;
				}
			}else{
				var url = util.setUrlParameter(location.href,'personIds',encodeURIComponent(personIds));
				location.href = url;
				return;
			}
			
			var selectedNode = query('.muiSharePersonHead .muiSelBoxMul')[0];
			domClass.remove(selectedNode, "muiBoxSeled");
			domConstruct.destroy(query('.muiSharePersonHead .muiSelBoxMul i.muiBoxSelected')[0]);
			var nodes = query('.muiSharePersonPanel li');
			for(var i=0;i<nodes.length;i++){
				selectSharePerson(nodes[i].id,false);
			}
		});

		var contentDom = dom.byId("sharePersonScrollView");
		contentDom.style.overflow = "initial";

		window.sharePersonSelectAll = function(){
			var selectedNode = query('.muiSharePersonHead .muiSelBoxMul')[0];
			var selected = false;
			if (!domClass.contains(selectedNode, "muiBoxSeled")) {
				domClass.add(selectedNode, "muiBoxSeled");
				domConstruct.create("i", {
					className : "mui mui-checked muiBoxSelected"
				},selectedNode)
				selected = true;
			} else {
				domClass.remove(selectedNode, "muiBoxSeled");
				domConstruct.destroy(query('.muiSharePersonHead .muiSelBoxMul i.muiBoxSelected')[0]);
			}
			var nodes = query('.muiSharePersonPanel li');
			for(var i=0;i<nodes.length;i++){
				selectSharePerson(nodes[i].id,selected);
			}
		}
		window.selectSharePerson = function(fdId,selected){
			var liNode = dom.byId(fdId);
			var selectedNode = query('.muiSharePersonItem .muiSelBoxMul',liNode)[0];
			
			if (selected) {
				if(domClass.contains(selectedNode, "muiBoxSeled")){
					return;
				}
				domClass.add(selectedNode, "muiBoxSeled");
				domConstruct.create("i", {
					className : "mui mui-checked muiBoxSelected"
				},selectedNode);
				domClass.add(liNode, "mblListItemSelected");
			} else {
				domClass.remove(selectedNode, "muiBoxSeled");
				domClass.remove(liNode, "mblListItemSelected");
				domConstruct.destroy(query('.muiSelBoxMul i.muiBoxSelected',liNode)[0]);
			}
		}
		on(query('.muiSharePersonHead')[0],touch.press,sharePersonSelectAll);
		//新建日程
		window.create=function(){
			var url='${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=addEvent&moduleName=${param.moduleName}',
				currentStr=cutil.formatDate(currentDate);
			if( currentStr ){
				url+='&startTime='+currentStr+'&endTime='+currentStr;
			}
			url+='&ownerId=${pageScope.currentUserId}';
			window.open(url,'_self');
		};
		//覆盖默认的后退(防止删除页面后列表页面后退到不存在的页面)
		window.doback=function(){
			adapter.closeWindow();
		};
		//请求他人权限
		window.requestauth=function(){
			var url='${LUI_ContextPath}/km/calendar/km_calendar_request_auth/kmCalendarRequestAuth.do?method=add&moduleName=${param.moduleName}';
			url+='&ownerId=${pageScope.currentUserId}';
			window.open(url,'_top');
		};
		
		window.changePage = function (type) {
			if(type == 1) {
				location.href = util.formatUrl('/km/calendar/mobile/self.jsp');
			} else if(type == 2) {
				location.href = util.formatUrl('/km/calendar/mobile/group.jsp');
			} else {
				location.href = util.formatUrl('/km/calendar/mobile/personGroup.jsp' + '?personGroupId=${personGroupId}');
			}
		}
	});

</script>