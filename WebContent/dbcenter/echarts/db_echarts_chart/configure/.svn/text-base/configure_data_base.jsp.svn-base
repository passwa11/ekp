<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/dbcenter/echarts/common/configure/jsp/SQLCommonJS.jsp"%>
<%@ include file="/dbcenter/echarts/common/configure/jsp/echartCommonJS.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.util.ConfigureUtil" %>
<%@page import="net.sf.json.JSONObject" %>
<%
	JSONObject relationObj = ConfigureUtil.getRelationDiagram();
	String relation = relationObj.toString();
%>
<script>
    Com_IncludeFile("json2.js", "${LUI_ContextPath}/resource/js/", null, true);
	Com_IncludeFile("configure.css",Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/css/','css',true);
	Com_IncludeFile("config.js", "${LUI_ContextPath}/dbcenter/echarts/common/", null, true);
	Com_IncludeFile("mutext.js", "${LUI_ContextPath}/dbcenter/echarts/common/configure/js/", null, true);
	Com_IncludeFile("chart.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart/configure/js/", null, true);
	Com_IncludeFile("configure_data_js.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart/configure/js/", null, true);
	Com_IncludeFile("CategoryComponent.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart/configure/js/", null, true);
	Com_IncludeFile("SQLSeries.js", "${LUI_ContextPath}/dbcenter/echarts/db_echarts_chart/configure/js/", null, true);
	//用于时间选择框
	Com_IncludeFile("calendar.js");
	Com_IncludeFile("doclist.js");
	//jquery文件上传控件
	Com_IncludeFile("jquery.form.js", "${LUI_ContextPath}/resource/js/jquery-plugin/", null, true);
</script>

<!-- 基本信息配置区域 -->
<center id="echart_config_area_base" >
  	<div style="display:inline-block;*display:inline;*zoom:1;width:95%;">
		<table class="tb_normal" width=100%> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="dbcenter-echarts" key="dbEchartsChart.docSubject"/>
				</td><td width="85%">
					<xform:text property="docSubject" style="width:98%" />
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.category') }
				</td>
				<td width="85%">
					<xform:dialog required="true" subject="${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory') }" propertyId="fdDbEchartsTemplateId" style="width:50%"
							propertyName="fdDbEchartsTemplateName" dialogJs="dbEcharts_treeDialog()">
					</xform:dialog>
				</td>
			</tr>

			<!-- 图表展现 -->
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.type') }
				</td>
				<td width="85%" colspan="3">
					<xform:radio property="fdConfigType" showStatus="edit" onValueChange="dbEcharts_changeChartShow" >
						<xform:simpleDataSource value="01" textKey="dbEchartsChart.configType.systemData" bundle="dbcenter-echarts"></xform:simpleDataSource>
						<xform:simpleDataSource value="10" textKey="dbEchartsChart.configType.staticData" bundle="dbcenter-echarts"></xform:simpleDataSource>
					</xform:radio>
				</td>
	        </tr>

			<!-- 数据来源 -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.dataSource') }</td>
				<td>
				   <div class="chartDataSource">
					<c:import charEncoding="UTF-8" url="/dbcenter/echarts/common/configure/jsp/model.jsp">
						<c:param name="callback" value="initSQLStructure"></c:param>
						<c:param name="fdKey" value="${dbEchartsChartForm.fdKey}"></c:param>
						<c:param name="fdModelName" value="${dbEchartsChartForm.fdModelName}"></c:param>
					</c:import>
				   </div>
				</td>
			</tr>

			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.listMainTitle') }
				</td>
				<td>
					<input class="inputsgl" name="chartOption.title.text" style="width:50%;" data-dbecharts-config="fdCode">
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.listSubTitle') }
				</td>
				<td>
					<input class="inputsgl" name="chartOption.title.subtext" style="width:50%;" data-dbecharts-config="fdCode">
				</td>
			</tr>

			<tr>
			    <td class="td_normal_title" width=15%>${lfn:message('dbcenter-echarts:echart.static.data.format')}</td>
			    <td class="staticData">
			        <xform:radio property="fdStaticType" showStatus="edit" onValueChange="dbEcharts_switchStateDataType" >
						<xform:simpleDataSource value="excel">EXCEL ${lfn:message('dbcenter-echarts:echart.format')}</xform:simpleDataSource>
						<xform:simpleDataSource value="json">JSON ${lfn:message('dbcenter-echarts:echart.format')}</xform:simpleDataSource>
					</xform:radio>
			    </td>
			<tr>
			<!-- Excel模板下载（静态数据配置） -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.templateDownload') }</td>
				<td>
                     <div class="excelDownload">
                        <a href="javascript:void(0);" class="a_download_excel" onclick="downloadExcelTemplate();">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.excelTemplateDownload') }</a>
                        <div class="chart_template_type" chart_type="" ></div>
                     </div>
				</td>
			</tr>
			<!-- Excel模板上传（静态数据配置）  -->
			<tr>
				<td class="td_normal_title" width=15%>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.templateUpload') }</td>
				<td>
				<div class="excelUpload"> 
				        <a id="upload_excel_link_button" class="a_upload_excel" href="javascript:void(0);">
						     <input type="file" name="importFile" accept=".xls,.xlsx" onchange="uploadFileChange(this);">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.clickToUploadExcel') }
						</a>
						<div class="upload_file_name" ></div>
                     </div>
				</td>
			</tr>
			<!-- 导入Excel消息（导入失败时显示） -->
			<tr>
			    <td colspan="2" >
			       <div class="upload_fail_title">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.uploadError') }</div>
			       <div class="upload_fail_error_content"></div>
			    </td>
			<tr>
			<!-- JSON格式 -->
			<!-- JSON模板下载（静态数据配置） -->
			<tr>
				<td class="td_normal_title" width=15%>${lfn:message('dbcenter-echarts:echart.json.template')}</td>
				<td>
                     <div class="jsonView">
                        <a href="javascript:void(0);" class="view_json" onclick="showJSON()"><font color="blue">${lfn:message('dbcenter-echarts:echart.json.view')}</font></a>
                     </div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>${lfn:message('dbcenter-echarts:echart.json.input')}</td>
				<td>
                     <div class="jsonInfo">
                        <xform:textarea property="fdStaticData" style="width:85%;height:350px;" />
                        <input type="button" onclick="json2charst()" style="background-color:#87CEFA; color:white;margin-top: 320px;width:50px" value="${lfn:message('dbcenter-echarts:echart.json.save')}"/>
                     </div>
                     
				</td>
			</tr>
			
			<!-- JSON格式异常（导入失败时显示） -->
			<tr>
			    <td colspan="2" >
			       <div class="json_fail_title">${lfn:message('dbcenter-echarts:echart.json.error')}:</div>
			       <div class="json_fail_error_content"></div>
			    </td>
			<tr>

		</table>
	</div>	

	</center>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
	<html:hidden property="fdCode" />
	<html:hidden property="fdConfig" />
	<html:hidden property="fdType" />
	<html:hidden property="fdStaticData" />
	<html:hidden property="fdKey" />
	<html:hidden property="fdModelName" />

	<script>
		var g_validator = $KMSSValidation();
		// 可选主题集合
		window.echart_themes = eval('${themes}');
		// 默认主题
		window.defaultTheme = "${defaultTheme}";
		// 图表中心的类型关系图
		window.DbEcharts_RelationDiagram = eval(<%=relation%>);
		
		function dbEcharts_switchStateDataType(){
			var staticDataType = $("input[type='radio'][name='fdStaticType']:checked").val();  // 选择的图表配置方式
			if(staticDataType == "json"){
				//隐藏excel相关
				$(".excelDownload").closest("tr").hide();     //  “EXCEL模板下载”
				$(".excelUpload").closest("tr").hide();       //  “点击上传EXCEL”  隐藏
				
				//显示json
				$(".jsonView").closest("tr").show();
				$(".jsonInfo").closest("tr").show();
			}else{
				$(".excelDownload").closest("tr").show();     //  “EXCEL模板下载”
				$(".excelUpload").closest("tr").show();       //  “点击上传EXCEL”  隐藏
				
				$(".jsonView").closest("tr").hide();
				$(".jsonInfo").closest("tr").hide();
				$(".json_fail_title").closest("tr").hide();
			}
		}
		function json2charst(){
			value = $("textarea[name='data_pie']").val();
			//previewChart.load("10",value,false,false);
			renderPreviewEchart("static_json_data"); // 重新渲染预览图表
		}
		
		function showJSON() {
			var type= $(".chart_template_type").attr("chart_type");
			var typeName= $(".chart_template_type").text();
			//alert(typeName);
			var value="";
			if(type == "pie"){
				value = $("textarea[name='data_pie']").val();
			}else if("gauge" == type){
				value = $("textarea[name='data_gauge']").val();
			}else if("map" == type) {
				value = $("textarea[name='data_map']").val();
			} else{
				value = $("textarea[name='data_line_area_bar']").val();
			}
			var jsonInfo = "<textarea style='width:93%;height:250px;'>"+value+"</textarea><br/><p style='color:red'>注：关键字如“value，name，data”等不可更改，只能改变值</p>";
			seajs.use([ 'sys/ui/js/dialog' ],function(dialog){
				dialog.build({
								config : {
									width : 550,
									height : 500,
									lock : true,
									cache : false,
									title : "<span style='font-size: 16px;font-weight: bold;'>JSON模板:"+typeName+"</span>",
									content : {
										type : "Html",
										html : jsonInfo,
										// url|element|html:"",
										iconType : "",
										buttons : [ {
											name : "关闭",
											value : true,
											focus : true,
											fn : function(value,dialog) {
												dialog.hide();
											}
										} ]
									}
								},
								callback : function(value,
										dialog) {

								},
								actor : {
									type : "default",
									animate : false
								},
								trigger : {
									type : "AutoClose",
									timeout : 35
								}

							}).show();
		   });
		}

		$(function(){
			setTimeout(function(){
				dbEcharts_switchStateDataType();
			},800);
		});
		
	</script>