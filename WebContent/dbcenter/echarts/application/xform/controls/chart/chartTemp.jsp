<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.service.IDbEchartsChartService,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.dbcenter.echarts.util.ChartUtil" %>
<%@page import="com.landray.kmss.dbcenter.echarts.model.DbEchartsChart" %>
<%@ page import="com.landray.kmss.dbcenter.echarts.forms.DbEchartsChartInfo" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="net.sf.json.JSONArray" %>
<%
	IDbEchartsChartService dbEchartsChartService = (IDbEchartsChartService) SpringBeanUtil.getBean("dbEchartsChartService");
	DbEchartsChart chart = (DbEchartsChart)dbEchartsChartService.findByPrimaryKey(request.getParameter("categoryId"));
	String chartTheme = ChartUtil.getChartSettingTheme(chart);
	DbEchartsChartInfo dbEchartsChartInfo=new DbEchartsChartInfo(chart);
	JSONObject __config = dbEchartsChartInfo.getConfig();
	if (__config != null) {
		Object loadMapJs = __config.get("loadMapJs");
		if (loadMapJs instanceof String) {
			request.setAttribute("loadMapJs", loadMapJs);
		} else if (loadMapJs instanceof JSONArray) {
			request.setAttribute("loadMapJs",
					((JSONArray) loadMapJs).toString());
		} else {
			// 先这样写，后面考虑多种地图考虑抽出来
			String mapsJs = "[]";
			JSONObject code = JSONObject
					.fromObject(chart.getFdCode());
			if (code.get("chartOption") != null) {
				JSONObject cOption = JSONObject
						.fromObject(code.get("chartOption"));
				if ("map-china"
						.equals(cOption.get("chartType"))) {
					mapsJs = "['china.js']";
				}
			}
			request.setAttribute("loadMapJs", mapsJs);
		}
	}else{
		request.setAttribute("loadMapJs", "[]");
	}
%>
<div style="display:inline-block;border:1px solid #d2d2d2;">
	<ui:chart className="macarons" var-loadMapJs="${loadMapJs}" var-themeName="<%=chartTheme %>" width="${param.width }" height="${param.height }" id="main_chart_${param.fdControlId }">
		<ui:source type="AjaxJson">
		</ui:source>
	</ui:chart>
</div>
<script>
	Com_IncludeFile("chartControl.js",Com_Parameter.ContextPath + 'dbcenter/echarts/application/xform/controls/','js',true);
	LUI.ready(function(){
		var config = {};
		config.controlId = "${JsParam.fdControlId }";
		config.domNode = $("xformflag[flagid='${JsParam.fdControlId }']");
		config.showStatus = "${JsParam.showstatus}";
		config.chartType = "${JsParam.chartType}";

		var inputs = "${JsParam.inputs}";
		if(inputs){
			config.inputs = JSON.parse(inputs.replace(/quot;/g,"\""));
		}

		var ajaxUrl = "/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=chartData&fdId=${JsParam.categoryId}";
		config.executor = LUI("main_chart_${JsParam.fdControlId }");
		config.executor.chartdata.url = ajaxUrl;

		var ChartControlObj = new ChartControl(config);
		ChartControlObj.load();
	});

</script>