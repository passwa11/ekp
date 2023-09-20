<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.dbcenter.echarts.model.DbEchartsChart"%>
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
		<c:out value="${(empty config.title)?(model.docSubject):(config.title)}"/>
	</template:replace>
	<template:replace name="content">
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
			if(loadMapJs!=null && loadMapJs.indexOf("china") > -1) {
				mixins += "dbcenter/echarts/mobile/resource/js/ChinaMixin";
			}
			if(StringUtil.isNotNull(mixins)){
				mixins = mixins.substring(0,mixins.length()-1);
				mixins = "data-dojo-mixins='"+mixins+"'";
			}
		%>
		<script type="text/javascript" src="<%=request.getContextPath() %>/dbcenter/echarts/common/echartschart.js?s_cache=${MUI_Cache}"></script>
		<c:forEach items="${model.fdChartList}" var="chartModel" varStatus="vstatus">
			<div style="display:inline-block;border:1px solid #d2d2d2;width:100%;margin-bottom:30px;">
				<div id="chart_${vstatus.index }" data-dojo-type="mui/chart/Chart" <%=mixins %>
						data-dojo-props="url:'/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=chartData&fdId=${chartModel.fdId}${chartParams}',dbcenter:true,chartTheme:'${chartTheme}'"></div>
			</div>
		</c:forEach>
		<script>
			require(["dojo/ready","dojo/request","dojo/window"],function(ready,request,win) {
				ready(function() {
					if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
						//145876 移动门户选择图表集，部分手机最后一个图表底部展示不全
						//每个图表390高度  390*图表个数总高度  多出0.2的高度 适配图表底部展示不全
						window.frameElement.style.height = (390*${fn:length(model.fdChartList)+0.5})+"px";
					}
				});
			});
		</script>
	</template:replace>
</template:include>
