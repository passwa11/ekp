<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    String mixins = "";
    if (__config != null) {
        Object loadMapJs = __config.get("loadMapJs");
        if(StringUtil.isNull(chartTheme)){
            chartTheme = "landrayblue";
        }
        //String chartThemeOri = chartTheme;
        String firstChar = chartTheme.substring(0,1);
        chartTheme = chartTheme.replaceFirst(firstChar, firstChar.toUpperCase());
        if(StringUtil.isNotNull(chartTheme)){
            mixins += "sys/mobile/js/mui/chart/Chart"+chartTheme+"Mixin,";
        }
        if(loadMapJs!=null && loadMapJs.toString().indexOf("china") > -1) {
            mixins += "dbcenter/echarts/mobile/resource/js/ChinaMixin,";
        }
        if(StringUtil.isNotNull(mixins)){
            mixins = mixins.substring(0,mixins.length()-1);
            mixins = "data-dojo-mixins='"+mixins+"'";
        }
    }
%>
<div id="dbcenter_${param.fdControlId }" <%=mixins %> data-dojo-type="mui/chart/Chart" data-dojo-props="'isLazy':true,'dbcenter':true"></div>

<script>
    require(["dojo/ready","dijit/registry","dbcenter/echarts/application/common/mobile/DbChart"],function(ready,registry,DbChart) {
        ready(function() {
            setTimeout(function(){
                var executor = registry.byId("dbcenter_${param.fdControlId}");
                var config = {};
                config.name = "${param.fdControlId }";
                config.categoryId = "${param.categoryId }";
                config.showstatus = "${param.showstatus }";
                config.inputs = "${param.inputs }";
                config.chartType = "${param.chartType}";

                var dbcenterChart = new DbChart(config);
                dbcenterChart.executor = executor;
                dbcenterChart.origUrl = "/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=chartData&fdId=${param.categoryId}";
                dbcenterChart.load();
            },50);
        });
    });
</script>
