<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.List,java.util.Date" %>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendMain" %>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil" %>
<%@ page import="com.landray.kmss.util.DateUtil,com.landray.kmss.util.UserUtil" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/map/mobile/resource/css/location.css?s_cache=${MUI_Cache}"></link>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>
		<script type="text/javascript" src="https://api.map.baidu.com/api?v=2.0&ak=cnG6G1wW70lQ36H693uVOyOXiwvMaph3&s=1"></script>
	</template:replace>
	<template:replace name="title">
		签到轨迹
	</template:replace>
	<template:replace name="content">
	<%
		String trailDate =  request.getParameter("trailDate");
		Date signTime = DateUtil.convertStringToDate(trailDate, DateUtil.TYPE_DATETIME,request.getLocale());
		DateTimeFormatUtil df = new DateTimeFormatUtil();
		String _signTime = df.getDateTime(signTime,"yyyy'年'M'月'd'日'");
		String week = df.getDateTime(signTime,"E");	
		String username = UserUtil.getUser().getFdName();
		String userId = UserUtil.getUser().getFdId();
	%>
		<ul class="muiSignInList">
		      <li>
		        <a class="row" href="#">
		          <div class="contentLeft">
		          	<span class="muipersonHead">
		          		<img class="muiImg" src='<person:headimageUrl contextPath="true" personId="<%=userId %>" size="m" />'/>
		          	</span>
		          	<span class="muiPersonInfo">
		          		 <span> <%=username %></span>
		          		 <span> <%=_signTime %> <%=week %></span>
		          	</span>
		          </div>
		        </a>
		      </li>
		</ul>
		<ul class="attendTabBar" data-dojo-type="mui/tabbar/TabBar" fixed="bottom" style="z-index:1050">
		  	<li data-dojo-type="mui/back/BackButton"></li>
		    <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
		    	<div data-dojo-type="mui/back/HomeButton"></div>
		    </li>
		</ul>
	<script>
		var _datas = '${traildatas}';
		_datas = _datas.replace(/\n/g,'<br>');
		var traildatas = JSON.parse(_datas);
		require(['dojo/topic','dojo/ready','dojo/dom-construct','sys/attend/mobile/resource/js/list/LocationMulDialog'],
				function(topic,ready,domConstruct,LocationMulDialog){
			ready(function(){
				openLocationDialog();
			});
			
			window.openLocationDialog = function(){
				var self = this;
				if(window['muiLocationMulDialogIsUsing']){
					return;
				}
				window['muiLocationMulDialogIsUsing'] = true;
				if(!window['muiLocationMulDialog']){
					var dialog = window['muiLocationMulDialog'] = new LocationMulDialog({
						showStatus : 'view'
					});
					dialog.startup();
					domConstruct.place(dialog.domNode,document.body,'last');
				}
				var evt = {
					showStatus : 'view',				
					datas : traildatas
				};
				setTimeout(function(){
					window['muiLocationMulDialog'].show(evt);
				},400);
				topic.publish('/attend/location/swapinfo/datas',this,{
					datas : traildatas
				});
			}
			
		});
	</script>
	</template:replace>
</template:include>
