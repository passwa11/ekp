<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.dbcenter.echarts.util.ConfigureUtil"%>
<%@page import="net.sf.json.JSONObject"%>

<script>
	Com_IncludeFile("optbar.js", null, "js");
</script>
<body>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/common/configure/css/model_list.css">
<center>
	<% 
		Map moduleMap = ConfigureUtil.getSysModuleNodes();
		JSONObject moduleMapJSON = JSONObject.fromObject(moduleMap);
		request.setAttribute("moduleMap", moduleMap);
	%>
	<!-- 操作栏 -->
	<div id="optBarDiv">
		<input type=button value="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.last') }" name="lastStep" style="display:none;" onclick="dialog_skip(-1);">
		<input type=button value="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.next') }" name="nextStep" onclick="dialog_skip(1);">
		<input type=button value="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.ok') }" name="ok" style="display:none;" onclick="dialog_setModelName();">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<!-- 内容 -->
	<div class="dialog_contentWrap">
		<div class="dialog_selectModule current_view" data-view="0">
			<!-- 搜索 -->
			<div class="dialog_selectBlock">
				<span>${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.plzChooseModule') }</span>
				<div class="dialog_select">
					<input type='text' class='dialog_select_input' title="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.search') }" onkeyup='enterTrigleSelect(event);' placeholder="${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.plzInputName') }"></input>
					<a href="javascript:dialog_moduleSelect();">${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.search') }</a>
				</div>
			</div>
			<!-- 数据列表 -->
			<div class="dialog_content" style="padding:8px 0px;">
				<table id="dialog_table" class="tb_normal" width="90%">
					<% 
						int i=1;
						for (Object key : moduleMap.keySet()) {
						String module = moduleMap.get(key).toString();
					%>
					<% 
					if(i==1){
					%>
						<tr>			
					<%		
						}
					%>
					<td width="25%">
						<label class="dialog_td_module_label">
							<input type="radio" name="key" value="<%=key%>" data-text="<%=module %>" data-type="module"/>&nbsp;<%=module%>
							<% i++; %>
						</label>
					</td>
					<% 
					if(i==5){
						i=1;
					%>
						</tr>			
					<%		
						}
					%>
					<%
					} 
					%>
				</table>
			</div>
		</div>
	</div>
<script>

	//视图模板 
	var viewTemplateHtml = "<div class='dialog_selectModel'>";
	viewTemplateHtml += "<div class='dialog_selectModel_span'>";
	
	viewTemplateHtml += "<span class='currentModelPath'></span>";
	
	viewTemplateHtml += "<div class='dialog_selectModel_model' style='margin-bottom:8px;'>";
	viewTemplateHtml += "<table class='tb_normal dialog_model_table'>";
	viewTemplateHtml += "</table>";
	viewTemplateHtml += "</div>";
	
	viewTemplateHtml += "</div>";
	viewTemplateHtml += "</div>";
	
	//记录模块的值，用于判断下一步的时候值是否有改变
	var dialog_current_key = "";
	var dialog_current_fd_app_id = "";

	var dialog_path_texts = [];
	
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
		var html = "";
		var index = 1;
		for(var i = 0;i < dataJSON.length; i++){
			var data = dataJSON[i];
			if(index == 1){
				html += "<tr>";
			}
			var modelName = data["modelName"];
			//构建td
			if(modelName == '<%=ConfigureUtil.XFORM_MODEL_KEY%>'){
				html += "<td width='25%'><label class='dialog_td_module_label'><input type='radio' name='key' data-type='module' data-fdmainmodelname='"+data["fdMainModelName"]+"' data-text='"+data["modelText"]+"' value='"+modelName+"'/>&nbsp;"+data["modelText"]+" </label></td>";
			}else{
				html += "<td width='25%'><label class='dialog_model_td_label'><input type='radio' name='key' data-type='model'";
				var isXform = data["isXform"];
				if(isXform && isXform == 'true'){
					html += " data-isxform='true'";
					html +=" data-text='"+data["modelText"]+"' value='"+modelName+"'/>&nbsp;"+data["modelText"]+"</br>( "+data["tableName"]+" )</label></td>";	
				}else{
					html += " data-isxform='false'";
					html +=" data-text='"+data["modelText"]+"' value='"+modelName+"'/>&nbsp;"+data["modelText"]+"</br>( "+modelName.substring(modelName.lastIndexOf(".")+1)+" )</label></td>";	
				}
			}
			
			index++;
			if(index == 5){
				index = 1;
				html += "</tr>";
			}
		}
		return html;
	}

	function dialog_buildModelTdModeling(dataJSON){
		var html = "";
		var index = 1;
		for(var i = 0;i < dataJSON.length; i++){
			var data = dataJSON[i];
			if(index == 1){
				html += "<tr>";
			}
			var modelName = data["modelName"];
			//构建td
			html += "<td width='25%'><label class='dialog_td_module_label'><input type='radio' name='key' data-type='module' data-fdmainmodelname='com.landray.kmss.sys.modeling.base.model.ModelingAppModel'";
			html += " data-isxform='true'";
			html +=" data-text='"+data["modelText"]+"' value='sys.xform.mapping' fdappid='"+modelName+"'/>&nbsp;"+data["modelText"]+"</br>( "+modelName+" )</label></td>";

			index++;
			if(index == 5){
				index = 1;
				html += "</tr>";
			}
		}
		return html;
	}
	
	//获取对应模块下面的所有model
	function dialog_findModel($view,$checkedRadio){
		if('.sys.modeling.base.' != $checkedRadio.val()){
			var url = "${LUI_ContextPath}/dbcenter/echarts/db_echarts_template/dbEchartsConfigureCommon.do?method=findModelByKey&key="+$checkedRadio.val();
			var fdMainModelName = $checkedRadio.data("fdmainmodelname");
			if(fdMainModelName && fdMainModelName != ''){
				url += "&fdMainModelName="+fdMainModelName;
			}
			if(fdMainModelName && fdMainModelName == 'com.landray.kmss.sys.modeling.base.model.ModelingAppModel'){
				dialog_findModelingModel($view,$checkedRadio);
				return;
			}

			$.ajax({
				url:url,
				async:false,
				type:'POST',
				success:function(data){
					var dataJSON = $.parseJSON(data);
					$view.find(".dialog_model_table").html(dialog_buildModelTd(dataJSON));
				}
			});
		}else{
			//业务建模,查询所有应用
			var url = "${LUI_ContextPath}/sys/modeling/base/modelingApplication.do?method=getAllAppInfos&s_ajax=true";
			$.ajax({
				url:url,
				async:false,
				type:'GET',
				success:function(data){
					var apps = data.apps;
					var dataArr = [];
					for (var i = 0; i < apps.length; i++) {
						dataArr.push({"modelText":apps[i].name,"modelName":apps[i].id,"isXform":"true"})
					}
					$view.find(".dialog_model_table").html(dialog_buildModelTdModeling(dataArr));
				}
			});
		}

	}

	function dialog_findModelingModel($view,$checkedRadio){
		var fdAppId = $checkedRadio.attr("fdappid");
		findModelingModelByIds($view,fdAppId);
	}



	function findModelingModelByIds($view,fdAppId){
		var url = "${LUI_ContextPath}/sys/modeling/base/modelingAppModel.do?method=findFormByAppIdAjax&fdAppId="+fdAppId;
		$.ajax({
			url:url,
			async:false,
			type:'POST',
			success:function(data){
				var modelIds = $.parseJSON(data);
				findModelingModelByModelIds($view,modelIds)
			}
		});

	}

	function findModelingModelByModelIds($view,modelIds){
		var url = "${LUI_ContextPath}/dbcenter/echarts/db_echarts_template/dbEchartsConfigureCommon.do?method=findModelByKey&key=sys.xform.mapping";
		url += "&fdMainModelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel&fdModelIds="+modelIds;
		$.ajax({
			url:url,
			async:false,
			type:'POST',
			success:function(data){
				var dataJSON = $.parseJSON(data);
				$view.find(".dialog_model_table").html(dialog_buildModelTd(dataJSON));
			}
		});


	}
	
	function updataCurrentPath($currentView){
		var $path = $currentView.find(".currentModelPath");
		if($path.length > 0){
			$path.html(dialog_path_texts.join(" >> "));	
		}
	}
	
	//跳转
	function dialog_skip(type){
		// type : ‘1’为下一步，‘-1’为上一步
		// 视图切换，用class(current_view)来区分当前是哪个视图
		var $currentView = $(".current_view");
		//当前视图的序号
		var index = $currentView.data("view");
		var nextIndex = parseInt(index) + type;
		var $nextView = $("[data-view='"+ nextIndex +"']");
		if(type == '1'){
			var $checkedRadio = $currentView.find("input[type='radio']:checked");
			if($checkedRadio.length > 0){
				// 如果是module，则往下找
				if($checkedRadio.data("type") == "module"){
					if($checkedRadio.val() != dialog_current_key || $checkedRadio.attr("fdappid") != dialog_current_fd_app_id){
						if($nextView.length > 0){
							// 清空现有的数据
							$nextView.find(".dialog_model_table").html("");
						}else{
							// 构建视图
							$nextView = $(viewTemplateHtml);
							$nextView.attr("data-view",nextIndex);
							$currentView.after($nextView);
						}
						dialog_current_key = $checkedRadio.val();
						dialog_current_fd_app_id = $checkedRadio.attr("fdappid");
						dialog_findModel($nextView,$checkedRadio);
					}
					
					// 添加路径变量
					dialog_path_texts.push($checkedRadio.data("text"));
					// 更新上下一步按钮
					$("input[name='lastStep']").show();
					$("input[name='nextStep']").hide();
				}else if($checkedRadio.data("type") == "model"){
					// 理论上无法来到这里
					alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.canNotSubdivide') }");
					return;
				}else{
					// 理论上无法来到这里
					alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.error') }");
					return;
				}
			}else{
				alert("${ lfn:message('dbcenter-echarts:dbEcharts.echart.configure.plzChooseModuleFirst') }");
				return;
			}
		}else if(type == '-1'){
			// 判断是不是第一个视图，是的话隐藏上一步，显示下一步
			if(nextIndex == '0'){
				$("input[name='lastStep']").hide();
			}
			$("input[name='nextStep']").show();
			if(dialog_path_texts.length > 0){
				// 删除最后一个路径
				dialog_path_texts.pop();
			}
		}
		// 更新路径
		updataCurrentPath($nextView);
		$("input[name='ok']").hide();
		$nextView.show();
		$nextView.addClass("current_view");
		$currentView.removeClass("current_view");
		$currentView.hide();
		OptBar_Refresh(true);
	}

	//搜索框按enter即可触发搜索
	function enterTrigleSelect(event){
		if (event && event.keyCode == '13') {
			dialog_moduleSelect();
		}
	}

	//模块搜索
	function dialog_moduleSelect(){
		var moduleMapJSON = <%=moduleMapJSON%>;
		var $selectInput = $(".dialog_select_input");
		var selectValue = $selectInput.val();
		var $table = $("#dialog_table");
		// 索引  只有1到4
		var indexFlag = 1;
		// 设置到table里面的HTML
		var html = "";
		for(var key in moduleMapJSON){
			var value = moduleMapJSON[key];
			//模糊匹配
			if(value.toUpperCase().indexOf(selectValue.toUpperCase()) > -1){
				if(indexFlag == 1){
					html += "<tr>";
				}
				//构建td
				html += "<td width='25%'><label class='dialog_td_module_label'><input type='radio' name='key' data-type='module' value='"+key+"' data-text='" + value + "'/>&nbsp;"+value+"</label></td>";
				indexFlag++;
				if(indexFlag == 5){
					indexFlag = 1;
					html += "</tr>";
				}
			}
		}
		$table.html(html);
	}
	
	Com_AddEventListener(window, 'load', function(){
		$(document).on("click",".dialog_td_module_label",function(){
			$("input[name='nextStep']").show();
			$("input[name='ok']").hide();
			OptBar_Refresh(true);
		});
		$(document).on("click",".dialog_model_td_label",function(){
			$("input[name='nextStep']").hide();
			$("input[name='ok']").show();
			OptBar_Refresh(true);
		});
		$(document).on("dblclick",".dialog_td_module_label",function(){
			dialog_skip(1);
		});
		$(document).on("dblclick",".dialog_model_td_label",function(){
			dialog_setModelName();
		});
	});
	
</script>
</center>
</body>