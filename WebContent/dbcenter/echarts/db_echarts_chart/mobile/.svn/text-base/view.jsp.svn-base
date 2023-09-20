<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view">
	<template:replace name="head">
		<link rel="Stylesheet" href="<%=request.getContextPath() %>/sys/mobile/css/themes/default/view.css?s_cache=${MUI_Cache}"/>
		<style>
			.mblScrollableViewContainer {
				min-width:100%;
			}
			.muiNormal td {
				padding:4px 6px;
				border:1px solid #eee;
			}
		</style>
	</template:replace>
	<template:replace name="title">
		
	</template:replace>
	<template:replace name="content">
		<script type="text/javascript" src="<%=request.getContextPath() %>/dbcenter/echarts/common/echartschart.js?s_cache=${MUI_Cache}"></script>
		<%-- <c:if test="${ loadMapJs eq '[\"china.js\"]'}">data-dojo-mixins="mui/chart/ChinaMixin"</c:if> --%>
		<%
			String mixins = "";
			String chartTheme = (String)request.getAttribute("chartTheme");
			if(StringUtil.isNull(chartTheme)){
				chartTheme = "landrayblue";
			}
			//String chartThemeOri = chartTheme;
			String firstChar = chartTheme.substring(0,1);
			chartTheme = chartTheme.replaceFirst(firstChar, firstChar.toUpperCase());
			if(StringUtil.isNotNull(chartTheme)){
				mixins += "sys/mobile/js/mui/chart/Chart"+chartTheme+"Mixin,";
			}
			String loadMapJs = (String) request.getAttribute("loadMapJs");
			if(loadMapJs.indexOf("china") > -1) {
				mixins += "dbcenter/echarts/mobile/resource/js/ChinaMixin,";
			}
			if(StringUtil.isNotNull(mixins)){
				mixins = mixins.substring(0,mixins.length()-1);
				mixins = "data-dojo-mixins='"+mixins+"'";
			}

			String lang_comeBack = ResourceUtil.getString("sys-ui:echart.comeBack");
			String lang_restore = ResourceUtil.getString("sys-ui:echart.restore");
			String lang_dataView = ResourceUtil.getString("sys-ui:echart.dataView");
			JSONObject obj = new JSONObject();
			obj.put("lang_comeBack",lang_comeBack);
			obj.put("lang_restore",lang_restore);
			obj.put("lang_dataView",lang_dataView);
			String lang_props = obj.toString().replaceAll("\"","'");
		%>
		
		<div id="chart"
			data-dojo-type="mui/chart/Chart" <%=mixins %>
			data-dojo-props="url:'/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=chartData&fdId=${param.fdId}',dbcenter:true,chartTheme:'${chartTheme}',lang_props:<%=lang_props %>">
		</div>
		<script>
			require(["dojo/ready","dojo/request","dojo/window"],function(ready,request,win) {
				ready(function() {
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						window.frameElement.style.height = "400px";
					}
				});
			});
		</script>
	</template:replace>
</template:include>
