<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="content">
		<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" >
	  		<li data-dojo-type="mui/back/BackButton"></li>
		    <li data-dojo-type="mui/tabbar/TabBarButton" data-dojo-props="icon1:'mui mui-more'" onclick="window.getPois();"></li>
		</ul>
	</template:replace>
</template:include>
<script>
	require(['mui/device/adapter'],function(adapter){
		
		window.getPois = function(){
			adapter.getPois(function(data){console.log(data);});
		}
	});
</script>