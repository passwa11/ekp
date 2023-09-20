<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.util.ConfigureUtil"%>
<%@page import="net.sf.json.JSONObject"%>

<style>
.title{
border:none;
}
</style>
<script>
	Com_IncludeFile("optbar.js", null, "js");
</script>

<c:import url="/dbcenter/echarts/common/configure/jsp/modeling_model_check.jsp" charEncoding="UTF-8">
	<c:param name="fdAppId" value="${param.fdKey}" />
</c:import>

<body>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/common/configure/css/model_list.css">
<center>

	<!-- 操作栏 -->
	<div id="optBarDiv">
		<input type=button value="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.ok') }" name="ok" onclick="dialog_setModelName();">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<!-- 内容 -->
	<div class="dialog_contentWrap">
		<div class="dialog_selectModule current_view" data-view="0">

		</div>
	</div>
	<script>

		//视图模板
		var viewTemplateHtml = "<div class='dialog_selectModel'>";
		viewTemplateHtml += "<div class='dialog_selectModel_span'>";
		viewTemplateHtml += "<div class='dialog_selectModel_model' style='margin-bottom:8px;'>";
		viewTemplateHtml += "<table class='tb_normal dialog_model_table'>";
		viewTemplateHtml += "</table>";
		viewTemplateHtml += "</div>";

		viewTemplateHtml += "</div>";
		viewTemplateHtml += "</div>";

		//点击确定
		function dialog_setModelName(){
			var resultJson = {};
			var $currentView = $(".current_view");
			var $checkedRadio = $currentView.find("input[type='radio']:checked");
			if($checkedRadio.length > 0){
				resultJson.modelName = $checkedRadio.val();
				resultJson.modelNameText = $checkedRadio.data("text");
				resultJson.isXform = $checkedRadio.data("isxform") + "";
				var url = "${LUI_ContextPath}/dbcenter/echarts/db_echarts_template/dbEchartsConfigureCommon.do?method=findFieldDictByModelName&modelName="+$checkedRadio.val()+"&isxform=" + resultJson.isXform;
				$.ajax({
					url:url,
					type:"POST",
					async:false,
					success:function(result){
						if(result != null){
							resultJson.rs = result;
						}
					}
				});
			}else{
				alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.plzChooseOption') }");
				return;
			}
			this.$dialog.hide(resultJson);
		}

		function dialog_buildModelTd(dataJSON){
			//	debugger;
			var html = "";
			var index = 1;
			for(var i = 0;i < dataJSON.length; i++){
				var data = dataJSON[i];
				if(index == 1){
					html += "<tr>";
				}

				var modelName = data["modelName"];
				html += "<td width='25%'><label class='dialog_model_td_label'><input type='radio' name='key' data-type='model'";
				var isXform = data["isXform"];
				if(isXform && isXform == 'true'){
					html += " data-isxform='true'";
					html +=" data-text='"+data["modelText"]+"' value='"+modelName+"'/>&nbsp;"+data["modelText"]+"</br>( "+data["tableName"]+" )</label></td>";
				}else{
					html += " data-isxform='false'";
					html +=" data-text='"+data["modelText"]+"' value='"+modelName+"'/>&nbsp;"+data["modelText"]+"</br>( "+modelName.substring(modelName.lastIndexOf(".")+1)+" )</label></td>";
				}

				index++;
				if(index == 5){
					index = 1;
					html += "</tr>";
				}
			}

			return html;
		}

		function dialog_findModelAppendSimple($view){
			//debugger;
			var url = "${LUI_ContextPath}/dbcenter/echarts/db_echarts_template/dbEchartsConfigureCommon.do?method=findModelByKey&key=<%=ConfigureUtil.XFORM_MODEL_KEY%>";
			var fdMainModelName = "com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain";
			if(fdMainModelName && fdMainModelName != ''){
				url += "&fdMainModelName="+fdMainModelName;
			}



			$.ajax({
				url:url,
				async:false,
				type:'POST',
				success:function(data){
					var dataJSON = $.parseJSON(data);
					dataJSON = _checkModelingModel(dataJSON);
					$view.find(".dialog_model_table").append("<tr ><td style='border-right-style:none;border-left-style:none;'>低代码平台非流程表单:</td></tr>");
					$view.find(".dialog_model_table").append(dialog_buildModelTd(dataJSON));
				}
			});
		}

		function dialog_findModel($view){

			var url = "${LUI_ContextPath}/dbcenter/echarts/db_echarts_template/dbEchartsConfigureCommon.do?method=findModelByKey&key=<%=ConfigureUtil.XFORM_MODEL_KEY%>";
			var fdMainModelName = "com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain";
			if(fdMainModelName && fdMainModelName != ''){
				url += "&fdMainModelName="+fdMainModelName;
			}
			$.ajax({
				url:url,
				async:false,
				type:'POST',
				success:function(data){
					var dataJSON = $.parseJSON(data);
					dataJSON = _checkModelingModel(dataJSON);
					$view.find(".dialog_model_table").append("<tr ><td style='border-right-style:none;border-left-style:none;'>低代码平台流程表单:</td></tr>");
					$view.find(".dialog_model_table").append(dialog_buildModelTd(dataJSON));


				}
			});
		}

		function initView(){
			var $currentView = $(".current_view");
			$view = $(viewTemplateHtml);
			$currentView.after($view);
			dialog_findModel($view);
			dialog_findModelAppendSimple($view);
			$view.show();
			$view.addClass("current_view");
			$currentView.removeClass("current_view");
			$currentView.hide();
		}

		Com_AddEventListener(window, 'load', function(){
			initView();
		});

	</script>
</center>
</body>