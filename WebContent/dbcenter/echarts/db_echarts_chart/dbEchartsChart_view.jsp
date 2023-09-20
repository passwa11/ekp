<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.Map,com.landray.kmss.util.StringUtil,net.sf.json.JSONObject,java.util.Iterator,java.net.URLDecoder,java.net.URLEncoder" %>
<template:include ref="config.view">
<%
	String dynamic = request.getParameter("db_dynamic");// 约定db_dynamic存储入参数据
	String db_dynamic = "";
	// 支持入参
	if(StringUtil.isNotNull(dynamic)){
		JSONObject dy = JSONObject.fromObject(URLDecoder.decode(dynamic,"UTF-8"));
		Iterator ite = dy.keys();
		while(ite.hasNext()){
			String key = (String)ite.next();
			db_dynamic += "&dy." + key + "=" + URLEncoder.encode(dy.getString(key),"UTF-8");
		}
	}
	String fdId = request.getParameter("fdId");
	String dbEchartsChartUrl = "/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=chartData&fdId="+fdId;
	dbEchartsChartUrl += db_dynamic;
%>
	<c:if test="${'0'!=param.showButton}">
		<template:replace name="toolbar">
			<script>
				seajs.use(["lui/dialog"],function(dialog){
					window.deleteDoc = function(delUrl){
						dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
							if(isOk){
								Com_OpenWindow(delUrl,'_self');
							}	
						});
						return;
					};
					
					//取消关注方法方法
					window.deleteMyAttentionEcharts = function(confirmUrl){
						dialog.confirm("${lfn:message('dbcenter-echarts:module.echarts.my.following.noConfirm') }",function(isOk){
							if(isOk){
								Com_OpenWindow(confirmUrl,'_self');
							}
						});
						return;
					};

					//关注方法
					window.createMyAttentionEcharts = function(confirmUrl){
						dialog.confirm("${lfn:message('dbcenter-echarts:module.echarts.my.following.confirm') }",function(isOk){
							if(isOk){
								Com_OpenWindow(confirmUrl,'_self');
							}
						});
						return;
					};
					
				});
				//导出excel
				function exportInfo() {
					seajs.use(["lui/dialog"],function(dialog) {
						dialog.confirm("${lfn:message('dbcenter-echarts:dbEchartsTable.confirmExport')}",function(rtnVal) {
							if(rtnVal) {
								var option = LUI('main_chart').getEchart().getOption();
								var jsonObj = {};
								if(option.title)
									jsonObj.text = option.title[0].text;
								if(option.xAxis) {
									var xDatas = option.xAxis[0].data;
									jsonObj.xAxisData = xDatas;
									var series = new Array();
									for (var i = 0; i < option.series.length; i++) {
										var seriesData = {
											name:option.series[i].name,
											data:option.series[i].data
										};
										series.push(seriesData);
									};
									jsonObj.series = series;
								}else {//有些图表没有xAxis属性，例如饼图
									var xDatas = new Array();
									var series = new Array();
									for (var i = 0; i < option.series.length; i++) {
										var sDatas = new Array();
										var valueDatas = option.series[i].data;
										for (var j = 0; j < valueDatas.length; j++) {
											xDatas.push(valueDatas[j].name);
											sDatas.push(valueDatas[j].value);
										}
										var seriesData = {
											name:option.series[i].name,
											data:sDatas
										};
										series.push(seriesData);
									}
									jsonObj.xAxisData = xDatas;
									jsonObj.series = series;
								}
								document.getElementById("dataStr").value = JSON.stringify(jsonObj);
								document.dbEchartsChartForm.submit();
							}
						});
					});
				}
			</script>
			<form name="dbEchartsChartForm" action="<%=request.getContextPath() %>/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do" method="post">
				<input type="hidden" name="method" value="exportInfo">
				<input type="hidden" name="dataStr" id="dataStr" />
			</form>
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:if test='${isAttention=="1"}'><!--已关注-->
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}">
						<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.nofollowing.title')}" order="1" onclick="deleteMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=deleteMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
					</kmss:auth>
				</c:if>
				<c:if test='${isAttention=="0"}'><!--未关注-->
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}">
						<ui:button text="${lfn:message('dbcenter-echarts:module.echarts.following.title')}" order="1" onclick="createMyAttentionEcharts('${LUI_ContextPath}/dbcenter/echarts/db_echarts_total/dbEchartsTotal.do?method=createMyAttentionEcharts&fdId=${param.fdId}');"></ui:button>
					</kmss:auth>
				</c:if>

				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('dbEchartsChart.do?method=edit&fdId=${param.fdId}','_self');">
					</ui:button>
				</kmss:auth>
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${ lfn:message('button.copy') }" onclick="Com_OpenWindow('dbEchartsChart.do?method=clone&cloneModelId=${param.fdId}','_blank');">
					</ui:button>
				</kmss:auth>
				
				<kmss:auth requestURL="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=exportInfo&fdId=${param.fdId}" requestMethod="GET">
					<ui:button text="${lfn:message('dbcenter-echarts:dbEchartsTable.exportInfo')}" onclick="exportInfo();"></ui:button>
				</kmss:auth>
				<kmss:auth
					requestURL="/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=delete&fdId=${param.fdId}"
					requestMethod="GET">
					<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=delete&fdId=${param.fdId}');">
					</ui:button>
				</kmss:auth>
				<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
			</ui:toolbar>
		</template:replace>
		<!-- 占位DIV -->
		<div style="height:10px" ></div>
	</c:if>
	<template:replace name="content">
	<kmss:windowTitle moduleKey="dbcenter-echarts:module.dbcenter.piccenter"  subjectKey="dbcenter-echarts:table.dbEchartsChartm" subject="${dbEchartsChartForm.docSubject}" />
	<% /**---------------- 设置图表宽度、高度（chartWidth、chartHeight）  ----------------**/ %>
	<c:set var="chartWidth" value="${ not empty info.config.width && fn:trim(info.config.width)!='' ? info.config.width : param.width }"/>
	<c:set var="chartHeight" value="${ not empty info.config.height && fn:trim(info.config.height)!='' ? info.config.height : param.height }"/>
	<% /**---------------- 设置 图表是否不显示边框 ----------------**/ %>
	<c:set var="chartNoBorder" value="${ not empty param.noBorder && param.noBorder eq 'true' ? true : false }"/>
	
	<center>
		<c:if test="${info.showCriteria}">
			<div style="width:90%; margin:0px auto;display: <c:if test="${!empty param.showCriteria }">none;</c:if><c:if test="${!empty param.showCriteria }">block;</c:if>">
				<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/criteria.jsp" />
			</div>
			<script>
			var loadChartTimeout = -1;
			LUI.ready(function(){
				seajs.use(['lui/topic'], function(topic) {
					var loadChart = function(data){
							if(loadChartTimeout>-1){
								clearTimeout(loadChartTimeout);
								loadChartTimeout=-1;
							}
							var url = "<%=dbEchartsChartUrl%>";
							if(data!=null){
								for(var i=0; i<data.criterions.length; i++){
									for(var j=0; j<data.criterions[i].value.length; j++){
										if(data.criterions[i].value[j]){
											var val = data.criterions[i].value[j];
											url += '&q.'+data.criterions[i].key+'='+encodeURIComponent(val);
										}
									}
								}
							}
							var chart = LUI('main_chart');
							chart.replaceDataSource({type:'AjaxJson', url:url});
						};
					var timeout = 10;
					if(location.hash && location.hash.indexOf('#cri')>-1){
						timeout = 3000;
					}
					loadChartTimeout = setTimeout(loadChart, timeout);
					topic.subscribe('criteria.changed', loadChart);
				}); 
			});
			</script>
			<div class="mainChartContainer ${pageScope.chartNoBorder ? 'noBorder': ''}" >
				<ui:chart var-themeName="${empty chartTheme ? '' : chartTheme }" width="${pageScope.chartWidth}" height="${pageScope.chartHeight}" id="main_chart"></ui:chart>
			</div>
		</c:if>
		<c:if test="${not info.showCriteria}">
			<div class="mainChartContainer ${pageScope.chartNoBorder ? 'noBorder': ''}">
				<ui:chart var-loadMapJs="${loadMapJs}" var-themeName="${empty chartTheme ? '' : chartTheme }" className="macarons" width="${pageScope.chartWidth}" height="${pageScope.chartHeight}" id="main_chart">
					<ui:source type="AjaxJson">
						{url:'<%=dbEchartsChartUrl%>'}
					</ui:source>
				</ui:chart>
			</div>
		</c:if>
		<c:if test="${('0'!=param.showButton) || ('1'==param.showTheme)}">
			<div style="margin-top:40px;">
				<label><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.choose.theme.tip"/></label>
				<select id="themeChoose">
					<option value="default" ${'default' eq chartTheme ? 'selected':'' } ><bean:message bundle="dbcenter-echarts" key="dbcenterEcharts.theme.default"/></option>
					<c:forEach var="theme" items="${themes }">
						<option value="${theme }" ${theme eq chartTheme ? 'selected':'' }>${theme }</option>
					</c:forEach>
				</select>
			</div>
			<script>
				seajs.use(['lui/jquery'],function($) {
					$(document).ready(function() {
						//绑定切换主题事件
						$("#themeChoose").bind('change',function() {
							var theme = $(this).val();
							var url = window.location.href;
							var re = new RegExp();
							re.compile("([\\?&]theme=)[^&]*", "i");
							theme = encodeURIComponent(theme);
							//已经有theme参数
							if(re.test(url)){
								url = url.replace(re, "$1"+theme);
							}else{
								//没有theme参数，必须在前面添加theme
								//不放前面如果有筛选项参数会传不过去
								url = url.replace("&fdId=","&theme="+theme+"&fdId=");
							}
							window.location.href = url;
						});
					});
				});
			</script>
		</c:if>
	</center>
	<c:if test="${'0'!=param.showButton}">
		<ui:tabpage expand="false" collapsed="true">
			<!--权限机制 -->
			<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="dbEchartsChartForm" />
			<c:param name="moduleModelName" value="com.landray.kmss.dbcenter.echarts.model.DbEchartsChart" />
			</c:import>
		</ui:tabpage>
	</c:if>
	<script>
		Com_IncludeFile("echartschart.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
		domain.autoResize();
	</script>
	</template:replace>
</template:include>