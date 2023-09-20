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
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/maindata/resource/css/xFormMainDataShow_edit_dialog.css">
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
		request.setAttribute("index", request.getParameter("index"));
	%>
	<!-- 操作栏 -->
	<div id="optBarDiv">
		<input type=button value="下一步" name="nextStep" onclick="xform_main_data_dialog_skip(1);">
		<input type=button value="上一步" name="lastStep" style="display:none;" onclick="xform_main_data_dialog_skip(-1);">
		<input type=button value="确定" name="ok" style="display:none;" onclick="xform_main_data_dialog_setModelName();">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<!-- 内容 -->
	<div class="main_data_dialog_contentWrap">
		<div class="main_data_dialog_selectModule">
			<!-- 搜索 -->
			<div class="main_data_dialog_selectBlock">
				<span>请选择模块:</span>
				<div class="main_data_dialog_select">
					<input type='text' class='main_data_dialog_select_input' title='搜索' onkeyup='enterTrigleSelect(event);' placeholder='请输入模块名'></input>
					<a href="javascript:xform_main_data_dialog_moduleSelect();">搜索</a>
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
				<span>当前模块 >> <span class="currentModelText"></span></span>
				<div style="margin-top:10px;">
					<span>请选择model:</span>
				</div>
			</div>
			<div class="main_data_dialog_selectModel_model" style="margin-bottom:8px;">
				<table id="main_data_dialog_model_table" class="tb_normal" width="90%">
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
		resultJson.index = '${index}';
		//alert(resultJson.index);
		var $modelName = $("input[name='modelName']:checked");
		if($modelName.length > 0){
			resultJson.modelName = $modelName.val();
			resultJson.modelNameText = $modelName.attr("data-text");
			var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=findFieldDictByModelName&modelName="+$modelName.val();
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
		
		xform_main_check_unique(resultJson,this.$dialog);
	}
	
	
	//关闭、确定对话框的回调函数
	function xform_main_check_unique(value,thisDialog){
		if(value){
			var modelNameId = 'fdModelName';
			var modelNameTextId = 'fdModelNameText';
			var index = value.index;
			var data;
			if(value.data){
				try{
					data = $.parseJSON(value.data);
				}catch(e){
					alert("列举表数据出错，详情请看后台日志！");
					return false;
				}
			}
			//alert(index);
			if(index && index!=''){
				
				thisDialog.hide(value);
					
			}else{
				//校验是否重复
				$.ajax({
					url : Com_Parameter.ContextPath+"sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=checkUniqueAndLastModifyTime",
					type : 'post',
					async : false,//是否异步
					data : {'modelName' : value.modelName,'fdId':'${param.fdId}'},
					success : function(data){
						if("noUnique"==data){
							alert(value.modelNameText+"已经配置过，每个model只能配置一次");
							return;
						}else if("unreadable"==data){
							alert(value.modelNameText+",model中没有fdLastModifiedTime属性，全文索引会有问题，不允许配置该model为主数据");
							return;
						}else if("no fdLastModifiedTime in database"==data){
							alert(value.modelNameText+",数据库中不存在fdLastModifiedTime字段，请先检查数据库表结构，否则会影响全文索引");
							return;
						}else if("fdLastModifiedTime contains null"==data){
							alert(value.modelNameText+",数据库中存在fdLastModifiedTime为空的记录，请先检查数据库数据，确保fdLastModifiedTime的值正确，否则会影响全文索引");
							return;
						}else if("true"==data){
							thisDialog.hide(value);
						}else{
							alert(data);
							return;
						}
					}
				})
			}
		}
	}
	
	function hideDialog(value){
		this.$Dialog.hide(value);
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
	function xform_main_data_dialog_findModel(key){
		var url = "${LUI_ContextPath}/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=findModelByKey&key="+key;
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
	
	//跳转
	function xform_main_data_dialog_skip(type){
		// type : ‘1’为下一步，‘-1’为上一步
		if(type == '1'){
			var $key = $("input[name='key']:checked");
			if($key.length > 0){
				$(".main_data_dialog_selectModule").hide();
				$(".main_data_dialog_selectModel").show();
				$("input[name='nextStep']").hide();
				$("input[name='lastStep']").show();
				$("input[name='ok']").show();
				$(".currentModelText").html($key.attr("data-text"));
				if($key.val() != main_data_dialog_key){
					main_data_dialog_key = $key.val();
					//$("#main_data_dialog_model_table").show();
					xform_main_data_dialog_findModel(main_data_dialog_key);
				}
			}else{
				alert("请先选择一个模块！");
			}	
		}else if(type == '-1'){
			$(".main_data_dialog_selectModule").show();
			$(".main_data_dialog_selectModel").hide();
			$("input[name='nextStep']").show();
			$("input[name='lastStep']").hide();
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