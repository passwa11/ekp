<!DOCTYPE html>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.landray.kmss.sys.config.design.SysCfgModule"%>
<%@page import="com.landray.kmss.sys.config.design.SysCfgModuleInfo"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="net.sf.json.JSONObject"%>

<script>
	Com_IncludeFile("optbar.js", null, "js");
</script>
<body>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/css/xFormMainDataInsystem_edit_dialog.css">
<center>
	<% 
		SysConfigs configs = SysConfigs.getInstance();
		List modules = configs.getModuleInfoList();
		HashMap moduleMap = new HashMap();
		for (int i = 0; i < modules.size(); i++) {
			SysCfgModule module = configs.getModule(((SysCfgModuleInfo) modules
					.get(i)).getUrlPrefix());
			String text = module.getMessageKey();
			if (StringUtil.isNull(text))
				continue;
			text = ResourceUtil.getString(text, Locale.getDefault());
			if (StringUtil.isNull(text)){
				text = module.getMessageKey();
			}
			String path = module.getUrlPrefix().replace("/", ".");
			moduleMap.put(path, text);
		}
		JSONObject moduleMapJSON = JSONObject.fromObject(moduleMap);
		request.setAttribute("moduleMap", moduleMap);
	%>
	<!-- 操作栏 -->
	<div id="optBarDiv">
		<input type=button value="${lfn:message('sys-xform-maindata:sysFormMainData.select.next') }" name="nextStep" onclick="xform_main_data_dialog_skip(1);">
		<input type=button value="${lfn:message('sys-xform-maindata:sysFormMainData.select.back') }" name="lastStep" style="display:none;" onclick="xform_main_data_dialog_skip(-1);">
		<input type=button value="${lfn:message('sys-xform-maindata:sysFormMainData.sure') }" name="ok" style="display:none;" onclick="xform_main_data_dialog_setModelName();">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<!-- 内容 -->
	<div class="main_data_dialog_contentWrap">
		<div class="main_data_dialog_selectModule">
			<!-- 搜索 -->
			<div class="main_data_dialog_selectBlock">
				<span>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.selectModule') }:</span>
				<div class="main_data_dialog_select">
					<input type='text' class='main_data_dialog_select_input' title='${lfn:message('sys-xform-maindata:sysFormMainDataCustom.search') }' onkeyup='enterTrigleSelect(event);' placeholder='${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.enter.moduleName') }'></input>
					<a href="javascript:xform_main_data_dialog_moduleSelect();">${lfn:message('sys-xform-maindata:sysFormMainDataCustom.search') }</a>
				</div>
			</div>
			<!-- 数据列表 -->
			<div class="main_data_dialog_content" style="padding:8px 0px;">
				<table id="main_data_dialog_table" class="tb_normal" width="90%">
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
						<label class="main_data_dialog_td_label">
							<input type="radio" name="key" value="<%=key%>" data-text="<%=module %>"/>&nbsp;<%=module%>
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
		<div class="main_data_dialog_selectModel">
			<div class="main_data_dialog_selectModel_span">
				<span>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.currentModule') } >> <span class="currentModelText"></span></span>
				<div style="margin-top:10px;">
					<span>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.select.module') }:</span>
				</div>
			</div>
			<div class="main_data_dialog_selectModel_model" style="margin-bottom:8px;">
				<table id="main_data_dialog_model_table" class="tb_normal" width="90%">
				</table>
			</div>
		</div>
		<div class="main_data_dialog_selectModelingApp">
			<div class="main_data_dialog_selectModel_span">
				<span>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.currentModule') } >> <span class="currentModelText"></span></span>
				<div style="margin-top:10px;">
					<span>${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.selectApp') }:</span>
				</div>
			</div>
			<div class="main_data_dialog_selectModel_model" style="margin-bottom:8px;">
				<table id="main_data_dialog_model_app_table" class="tb_normal" width="90%">
				</table>
			</div>
		</div>
	</div>
<script>
	//记录模块的值，用于判断下一步的时候值是否有改变
	var main_data_dialog_key = ""

	//点击确定
	function xform_main_data_dialog_setModelName(){
		var resultJson = {};
		var $modelName = $("input[name='modelName']:checked");
		if($modelName.length > 0){
			resultJson.modelName = $modelName.val();
			resultJson.modelNameText = $modelName.attr("data-text");

			var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=findFieldDictByModelName&modelName="+$modelName.val();
			if($modelName.val().indexOf("com.landray.kmss")==-1){
				url+="&isxform=true";
			}

			$.ajax({
				url:url,
				type:"GET",
				async:false,
				success:function(result){
					if(result != null){
						resultJson.data = result;					
					}
				}
			});
		}
		this.$dialog.hide(resultJson);
	}
	
	function xform_main_data_dialog_buildModelTd(dataJSON){
		var html = "";
		var index = 1;
		for(var i = 0;i < dataJSON.length; i++){
			var data = dataJSON[i];
			if(index == 1){
				html += "<tr>";
			}
			var modelName = data["modelName"];
			//构建td
			html += "<td width='25%'><label class='main_data_dialog_model_td_label'><input type='radio' name='modelName' data-text='"+data["modelText"]+"' value='"+modelName+"'/>&nbsp;"+data["modelText"]+"</br>( "+modelName.substring(modelName.lastIndexOf(".")+1)+" )</label></td>";
			index++;
			if(index == 5){
				index = 1;
				html += "</tr>";
			}
		}
		return html;
	}
	
	//获取对应模块下面的所有model
	function xform_main_data_dialog_findModel(key,fdAppId){
		if(key==".sys.modeling.base."){
			//查询业务建模应用
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
					$("#main_data_dialog_model_app_table").html(dialog_buildModelTdModeling(dataArr));
				}
			});
		}else if(key==".sys.modeling.base.app."){
			//查询业务建模model
			findModelingModelByIds($("#main_data_dialog_model_table"),fdAppId);
		}else{
			var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=findModelByKey&key="+key;
			$.ajax({
				url:url,
				async:false,
				type:'GET',
				success:function(data){
					var dataJSON = $.parseJSON(data);
					$("#main_data_dialog_model_table").html(xform_main_data_dialog_buildModelTd(dataJSON));
				}
			});
		}
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
			html += "<td width='25%'><label class='main_data_dialog_model_td_label'><input type='radio' name='key' data-type='module' data-fdmainmodelname='com.landray.kmss.sys.modeling.base.model.ModelingAppModel'";
			html += " data-isxform='true'";
			html +=" data-text='"+data["modelText"]+"' value='.sys.modeling.base.app.' fdappid='"+modelName+"'/>&nbsp;"+data["modelText"]+"</br>( "+modelName+" )</label></td>";

			index++;
			if(index == 5){
				index = 1;
				html += "</tr>";
			}
		}
		return html;
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
		if(!modelIds){
			$("#main_data_dialog_model_table").html(xform_main_data_dialog_buildModelTd({}));
			return;
		}
		var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=findModelByKey&key=.sys.modeling.base.app.";
		url += "&fdMainModelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel&fdModelIds="+modelIds;
		$.ajax({
			url:url,
			async:false,
			type:'POST',
			success:function(data){
				var dataJSON = $.parseJSON(data);
				$("#main_data_dialog_model_table").html(xform_main_data_dialog_buildModelTd(dataJSON));
			}
		});


	}

	//跳转
	function xform_main_data_dialog_skip(type){
		debugger;
		// type : ‘1’为下一步，‘-1’为上一步
		if(type == '1'){
			var $key = $("input[name='key']:checked");
			if(main_data_dialog_key == ".sys.modeling.base." && arguments[1] === true && $key.val() == '.sys.modeling.base.'){
				alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.select.applicationFirst') }");
			}
			if($key.length > 0){
				if($key.val() != main_data_dialog_key){
					main_data_dialog_key = $key.val();
					var fdAppId =  $key.attr('fdappid');
					//$("#main_data_dialog_model_table").show();
					xform_main_data_dialog_findModel(main_data_dialog_key,fdAppId);
				}
				if(main_data_dialog_key == ".sys.modeling.base."){
					//如果是选择业务建模模块
					$(".main_data_dialog_selectModule").hide();
					$(".main_data_dialog_selectModelingApp").show();
					$("input[name='lastStep']").show();
					$("input[name='ok']").show();
					$("input[name='nextStep']").attr("onclick","xform_main_data_dialog_skip(1,true)")
					$(".currentModelText").html($key.attr("data-text"));
				}else if(main_data_dialog_key == ".sys.modeling.base.app.") {
					//如果是选择业务建模应用
					$(".main_data_dialog_selectModule").hide();
					$(".main_data_dialog_selectModelingApp").hide();
					$(".main_data_dialog_selectModel").show();
					$("input[name='nextStep']").hide();
					$("input[name='lastStep']").show();
					$("input[name='ok']").show();
					$(".currentModelText").html($key.attr("data-text"));
				}else{
					$(".main_data_dialog_selectModule").hide();
					$(".main_data_dialog_selectModel").show();
					$("input[name='nextStep']").hide();
					$("input[name='lastStep']").show();
					$("input[name='ok']").show();
					$(".currentModelText").html($key.attr("data-text"));
				}
			}else{
				alert("${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.select.moduleFirst') }");
			}	
		}else if(type == '-1'){
			if(main_data_dialog_key == ".sys.modeling.base."){
				//如果是选择业务建模模块
				$(".main_data_dialog_selectModule").show();
				$(".main_data_dialog_selectModelingApp").hide();
				$(".main_data_dialog_selectModel").hide();
				$("input[name='nextStep']").attr("onclick","xform_main_data_dialog_skip(1,false)");
				$("input[name='nextStep']").show();
				$("input[name='lastStep']").hide();
			}else if(main_data_dialog_key == ".sys.modeling.base.app.") {
				//如果是业务建模应用
				$(".main_data_dialog_selectModule").hide();
				$(".main_data_dialog_selectModelingApp").show();
				$(".main_data_dialog_selectModel").hide();
				$("input[name='nextStep']").show();
				$("input[name='lastStep']").show();
				main_data_dialog_key = ".sys.modeling.base.";
			}else{
				$(".main_data_dialog_selectModule").show();
				$(".main_data_dialog_selectModel").hide();
				$("input[name='nextStep']").show();
				$("input[name='lastStep']").hide();
			}
		}
		OptBar_Refresh(true);
	}

	//搜索框按enter即可触发搜索
	function enterTrigleSelect(event){
		if (event && event.keyCode == '13') {
			xform_main_data_dialog_moduleSelect();
		}
	}

	//模块搜索
	function xform_main_data_dialog_moduleSelect(){
		var moduleMapJSON = <%=moduleMapJSON%>;
		var $selectInput = $(".main_data_dialog_select_input");
		var selectValue = $selectInput.val();
		var $table = $("#main_data_dialog_table");
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
				html += "<td width='25%'><label class='main_data_dialog_td_label'><input type='radio' name='key' value='"+key+"' data-text='" + value + "'/>&nbsp;"+value+"</label></td>";
				indexFlag++;
				if(indexFlag == 5){
					indexFlag = 1;
					html += "</tr>";
				}
			}
		}
		$table.html(html);
	}
	
	/*
	 * 停止冒泡
	 * */
	function xform_main_data_dialog_stopBubble(e) {
	    // 如果提供了事件对象，则这是一个非IE浏览器
	    if ( e && e.stopPropagation ) {
	        // 因此它支持W3C的stopPropagation()方法 
	        e.stopPropagation();
	    } else { 
	        // 否则，我们需要使用IE的方式来取消事件冒泡
	        window.event.cancelBubble = true;
	    }
	}
	
	Com_AddEventListener(window, 'load', function(){
		//冒泡 双击下一步
		$("#main_data_dialog_table").delegate(".main_data_dialog_td_label","dblclick",function(event){
			xform_main_data_dialog_skip(1);
			xform_main_data_dialog_stopBubble(event);
		});
		//冒泡 双击确认
		$("#main_data_dialog_model_table").delegate(".main_data_dialog_model_td_label","dblclick",function(event){
			xform_main_data_dialog_setModelName();
			xform_main_data_dialog_stopBubble(event);
		});
	});
	
</script>
</center>
</body>