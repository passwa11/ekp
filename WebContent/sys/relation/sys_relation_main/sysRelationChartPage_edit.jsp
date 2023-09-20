<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.dbcenter.echarts.application.util.ApplicationUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<!-- czk2019 -->
<style type="text/css"> 
ul, li{list-style-type: none;} 
ul{padding:1px;margin:0px 0px 0px 0px;height: 100px;width: 100%;}
li { float:left;width: 50%;}
.clear {clear:both; height: 0px;}
</style>
<center>
<%
	JSONObject jsonChart = ApplicationUtil.getChartMode();
	request.setAttribute("jchart", jsonChart.getJSONObject("chart"));
	request.setAttribute("jcustom", jsonChart.getJSONObject("custom"));
%>

<table class="tb_normal" width="100%" id="tab_person_setting">
	<tr>
		<td width="15%" style="height: 40; padding-left: 15px;">
			<bean:message bundle="sys-relation" key="sysRelationEntry.chart.type" />
		</td>
		<td width="35%" style="height: 40; padding-left: 15px;">
			<select name="fdCCType" onchange="sh(this);">
				<option value="chart"><bean:message bundle="sys-relation" key="sysRelationEntry.chart.type.chart" /></option>
				<option value="custom"><bean:message bundle="sys-relation" key="sysRelationEntry.chart.type.custom" /></option>
			</select>
		</td>
	</tr>
	<tr>
		<td width="15%" style="height: 40; padding-left: 15px;">
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdChartName" />
		</td>
		<td width="35%" style="height: 40; padding-left: 15px;">
			<input type='hidden' name='fdChartId' />
			<input type='text' name='fdChartName' readonly="readonly" class="inputsgl" style="width:80%"  />
			<span class="txtstrong">*</span>
			<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Category_Choose(_Designer_Control_Attr_Dbechart_Category_Cb);'><bean:message key="dialog.selectOrg" /></a>
		</td>
	</tr>
	<tr id="fct">
		<td width="15%" style="height: 40; padding-left: 15px;">
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdChartType" />
		</td>
		<td width="35%" style="height: 40; padding-left: 15px;">
			<input type='text' name='fdChartType' readonly="readonly" class="inputsgl" style="width:80%"  />
		</td>
	</tr>
	<tr>
		<td width="15%" style="height: 40; padding-left: 15px;">
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdDynamicData" />
		</td>
		<td width="35%" style="height: 40; padding-left: 15px;">
			<input type='hidden' name='fdDynamicData' value=""/>
			<div id="dbEchart_Input_wrap"></div>
		</td>
	</tr>	
</table>
<br /><br />
<input type="button" class="btnopt" value="<bean:message key="button.ok"/>" onclick="doOK();" />
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="reset" class="btnopt" value="<bean:message key="button.close" />" onclick="Com_CloseWindow();" />
</center>
<%@ include file="sysRelationChartPage_edit_script.jsp"%>
<%@ include file="/resource/jsp/edit_down.jsp" %>