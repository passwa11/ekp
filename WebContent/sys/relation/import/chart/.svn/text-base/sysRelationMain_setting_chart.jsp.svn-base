<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.dbcenter.echarts.application.util.ApplicationUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.design.SysCfgRelation"%>
<%@page import="com.landray.kmss.sys.relation.web.RelationEntry"%>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<!-- czk2019 -->
<template:include ref="default.dialog">
	<template:replace name="content"> 
	<%
		JSONObject jsonChart = ApplicationUtil.getChartMode();
		request.setAttribute("jchart", jsonChart.getJSONObject("chart"));
		request.setAttribute("jcustom", jsonChart.getJSONObject("custom"));
		request.setAttribute("currentUserId", UserUtil.getKMSSUser(request).getUserId());
	%>
	<script type="text/javascript" >
		Com_IncludeFile("validator.jsp|validation.jsp|validation.js|plugin.js|jquery.js|dialog.js|formula.js", null, "js");
		Com_IncludeFile("rela_chart.js","${KMSS_Parameter_ContextPath}sys/relation/import/resource/","js",true);
	</script>
	<script type="text/javascript">
		var _param={"tempId":'<%=com.landray.kmss.util.IDGenerator.generateID()%>',
				"varName":"rela_opt",
				'fdModuleName.isNull':"<bean:message bundle="sys-relation" key="sysRelationMain.fdModuleName.isNull"/>",
				'preview.title':'<bean:message key="sys-relation:button.setting.preview"/>',
				'button.cancel':'<bean:message key="button.close"/>'
			};
		
		new RelationChartSetting(_param);
		var currentUserId = "${currentUserId}";
	</script>
	<div class="rela_config_subset" style="height:300px;overflow:auto;">
		<table class="tb_simple" style="width:85%">
			<tr>
				<td width="20%"  class="td_normal_title" style="padding-right: 20px">
				     <bean:message key="sys-relation:sysRelationEntry.person.subject"/>
				</td>
				<td>
					<xform:text property="fdModuleName" required="true" validators="maxLength(200)" value="${sysRelationMain.relation.fdModelName}" 
						showStatus="edit" subject="${lfn:message('sys-relation:sysRelationEntry.person.subject')}" style="width:75%"></xform:text>
				</td>
			</tr>
			<tr>
				<td width="20%"  class="td_normal_title" style="padding-right: 20px">
					<bean:message bundle="sys-relation" key="sysRelationEntry.chart.type" />
				</td>
				<td>
					<select name="fdCCType" onchange="sh(this);">
						<option value="chart"><bean:message bundle="sys-relation" key="sysRelationEntry.chart.type.chart" /></option>
						<option value="custom"><bean:message bundle="sys-relation" key="sysRelationEntry.chart.type.custom" /></option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="20%"  class="td_normal_title" style="padding-right: 20px">
					<bean:message bundle="sys-relation" key="sysRelationEntry.fdChartName" />
				</td>
				<td>
					<input type='hidden' name='fdParameter' />
					<input type='hidden' name='fdChartId' />
					<input type='text' name='fdChartName' readonly="readonly" class="inputsgl" style="width:80%" subject="${lfn:message('sys-relation:sysRelationEntry.fdChartName')}"/>
					<span class="txtstrong">*</span>
					<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Category_Choose(_Designer_Control_Attr_Dbechart_Category_Cb);'><bean:message key="dialog.selectOrg" /></a>
				</td>
			</tr>
			<tr id="fct">
				<td width="20%"  class="td_normal_title" style="padding-right: 20px">
					<bean:message bundle="sys-relation" key="sysRelationEntry.fdChartType" />
				</td>
				<td>
					<input type='text' name='fdChartType' readonly="readonly" class="inputsgl" style="width:80%"  />
				</td>
			</tr>
			<tr>
				<td width="20%"  class="td_normal_title" style="padding-right: 20px">
					<bean:message bundle="sys-relation" key="sysRelationEntry.fdDynamicData" />
				</td>
				<td>
					<input type='hidden' name='fdDynamicData' value=""/>
					<div id="dbEchart_Input_wrap"></div>
				</td>
			</tr>	

			<tr><td colspan="5" align="center" class="rela_scope_button">
					<ui:button text="${lfn:message('button.ok')}" id="rela_config_save"></ui:button>
					&nbsp;&nbsp;&nbsp;
					<ui:button styleClass="lui_toolbar_btn_gray"  text="${lfn:message('button.cancel')}" id="rela_config_close"></ui:button>
					&nbsp;&nbsp;&nbsp;
					
				</td></tr>
		</table>
	</div>
	<script type="text/javascript">
		Com_IncludeFile("common.js|calendar.js|data.js|dialog.js|jquery.js", null, "js");
		Com_IncludeFile('DbEchartsApplication_Dialog.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
		Com_IncludeFile('userInfo.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
		Com_IncludeFile('chartMode.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
		var relationEntry = {};
		var top = Com_Parameter.top || window.top;
		//初始化relationEntry
		if(top.dialogObject == null || top.dialogObject.relationEntry==null
				|| parent.SysRelation_IsChangeType){
			// 新建，修改类型
			relationEntry = createRelationEntry();
		} else {
			// 编辑
			relationEntry = top.dialogObject.relationEntry;
		}
		function createRelationEntry(){
			return {fdId:"<%=com.landray.kmss.util.IDGenerator.generateID()%>",fdType:"9",fdModuleName:"图表中心",
				fdParameter:"${currentUserId}",fdChartId:"",fdChartName:"",fdChartType:"",fdCCType:"",fdDynamicData:""};
		}
		
		
		//清空字段
		function clearField(fields){
			if (!fields) {
				return;
			}
			var fieldArr = fields.split(";");
			for (var i = 0, len = fieldArr.length; i < len; i++) {
				document.getElementsByName(fieldArr[i])[0].value="";
			}
		}
		// 替换所有字符串
		String.prototype.replaceAll  = function(s1,s2){
		    return this.replace(new RegExp(s1,"gm"), s2);
		};
		
		// 判断DOM元素是否有值
		function checkValueIsNull(str) {
			if(str && document.getElementsByName(str)[0] && document.getElementsByName(str)[0].value == ""){
				return true;
			}
			return false;
		}
		
		function fieldJson(dd,fd){
			var json = {};
			$.each(dd,function(name,value) {
				if(name.indexOf(fd+"_")==0){
					if(name.indexOf("_tip")!=-1){
						name = name.replace('_tip','');
						json.tip = value;
						json.name = name;
					}else if(name.indexOf("_type")!=-1){
						json.type = value;
					}else if(name.indexOf("_text")!=-1){
						json.text = value;
					}else if(name.indexOf("_value")!=-1){
						json.value = value;
					}else if(name.indexOf("_param")!=-1){
						json.param = value;
					}
				}
			});
			return json;
		}
		
		
		//选择图表
		function _Designer_Control_Attr_Category_Choose(cb){
			var val = document.getElementsByName("fdCCType")[0].value;
			if(val == ""){
				alert('<bean:message bundle="sys-relation" key="sysRelationEntry.chart.type.select" />');
				return;
			}
			var item = $.parseJSON('${jchart}');
			if(val=="custom"){
				item = $.parseJSON('${jcustom}');
			}
			var url = Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=dialog&echartModelName="
						+ item.templateModelName + "&modeType=chart;custom";
			var dialog = new DbEchartsApplication_Dialog(url,item,cb);
			dialog.show();
		}
		//图表权限选择
		function Designer_Control_Dbechart_IsVisibel(){
			var flag = false;
			var dbechartData = {};
			dbechartData.role = "ROLE_DBCENTERECHARTS_DEFAULT";
			var url = Com_Parameter.ContextPath + 'sys/xform/sys_form_template/sysFormTemplate.do?method=checkAuth';
			$.ajax({ 
				url: url, 
				data: dbechartData,
				async: false, 
				dataType: "json", 
				cache: false, 
				success: function(rtn){
					if("1" == rtn.status){
						flag = true;
					}
				}
			});
			return flag;
		}
		//选择图表之后的回调	rn : {value: ,text: ,item}
		function _Designer_Control_Attr_Dbechart_Category_Cb(rn){
			if(rn){
				var control = {};
				_Designer_Control_Attr_Dbechart_Category_FillVal(control,rn);
				console.log(rn);
				_Designer_Control_Attr_Dbechart_Category_BuildInput(rn,function(config){
					console.log(config);
					var html = _Designer_Control_Attr_Dbechart_BuildTable(config);
					$("#dbEchart_Input_wrap").html(html);
				});
			}
		}
		
		//塞值
		function _Designer_Control_Attr_Dbechart_Category_FillVal(control,rn){
			// 设置业务名称
			document.getElementsByName("fdChartName")[0].value = rn.text;
			document.getElementsByName("fdChartId")[0].value = rn.value;
			control.categoryId = rn.value;
			control.mainUrl = rn.item.mainUrl;
			control.mobileUrl = rn.item.mobileUrl;
		}
		
		//处理入参
		function _Designer_Control_Attr_Dbechart_Category_BuildInput(rn,cb){
			var inputs = [];
			var data = {};// modelName id 
			data.modelName = rn.item.mainModelName;
			data.id = rn.value;
			$.ajax({
				type : "post",
				async : true,//是否异步
				url : Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=findDynamic",
				data : data,
				dataType : "json",
				success : function(ajaxRn) {
					inputs = ajaxRn;
					if(cb){
						cb(inputs);
					}
				},
				error:function(err){
			        //请求出错处理
			        console.log(err);
			    }		
			});
				
		}
		
		function _Designer_Control_Attr_Dbechart_BuildTable(config,mapping){
			var chartType = config.chartType;
			if(chartType){
				$("input[name='fdChartType']").val(chartType);
			}else{
				$("input[name='fdChartType']").val("");
			}
			var html = "";
			//图表配置类型的图表入参
			if(config.type=="01"){
				if(config.tables){
					var tables = config.tables;
					if(tables.length > 0){
						for ( var i = 0; i < tables.length; i++) {
							var table = tables[i];
							if(table.dynamic && table.dynamic.length > 0){
								for(var j = 0;j < table.dynamic.length;j++){
									var dynamic = table.dynamic[j];
									var input = {};
									var fieldName = dynamic.field.name;
									var fieldType = dynamic.field.type;
									var fieldText = dynamic.field.text;
									var fieldVal = dynamic.fieldVal.val;
									var param = "01";
									if(fieldVal=="!{dynamic}"){
										html += fieldText+":<input type='text' name='dynamic_"+fieldName+"' readonly='readonly' class='inputsgl' style='width:65%' value=''>";
										html += "<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Dbechart_Input_Choose(\""+ fieldName +"\",\""+ fieldType+"\",\""+ fieldText +"\",\""+ param +"\");'><bean:message key='button.select' /></a>";
										html += "<br>";
									}
									initDync(fieldName,fieldType,fieldText,param);
								}
							}
						}
					}
				}
			}else{
				//图表普通编程模式和高级编程模式的图表入参
				if(config.inputs){
					var inputs = config.inputs;
					if(inputs.length > 0){
						for ( var i = 0; i < inputs.length; i++) {
							var input = {};
							var fieldName = inputs[i].key;
							var fieldType = inputs[i].format;
							var fieldText = inputs[i].name;
							var fieldVal = "";
							var param = "11";
							if(fieldVal=="!{dynamic}"){
								html += fieldText+":<input type='text' name='dynamic_"+fieldName+"' readonly='readonly' class='inputsgl' style='width:65%' value=''>";
								html += "<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Dbechart_Input_Choose(\""+ fieldName +"\",\""+ fieldType+"\",\""+ fieldText +"\",\""+ param +"\");'><bean:message key='button.select' /></a>";
								html += "<br>";
							}else if(fieldVal==""){
								html += fieldText+":<input type='text' name='dynamic_"+fieldName+"' readonly='readonly' class='inputsgl' style='width:65%' value=''>";
								html += "<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Dbechart_Input_Choose(\""+ fieldName +"\",\""+ fieldType+"\",\""+ fieldText +"\",\""+ param +"\");'><bean:message key='button.select' /></a>";
								html += "<br>";
							}
							initDync(fieldName,fieldType,fieldText,param);
						}
					}
				}
			}
			return html;
		}
		
		function _Designer_Control_Attr_Dbechart_Input_GetLastFieldBySepa(str,separator){
			var arr = str.split(separator);
			return arr[arr.length - 1];
		}
		
		//获取表单控件的信息
		function _Designer_Control_Attr_Dbechart_GetObj(){
			var modelName = "${JsParam.currModelName}";
			var modelId = "${JsParam.currModelId}";
			var pro = "${JsParam.pro}";
			var vars = [];
			if(!(modelName==null||modelName=="")){
				var varInfo = Formula_GetVarInfoByModelName(modelName);
				for(var i = 0;i < varInfo.length;i++){
					var controlInfo = varInfo[i];
					var item = {};
					item.field = controlInfo.name;
					item.fieldText = controlInfo.label;
					item.fieldType = controlInfo.type;
					item.controlType = controlInfo.controlType;
					vars.push(item);
				}
			}
			//获取扩展字段
			var modelType = new KMSSData().AddBeanData("sysRelationChartService&fdId="+modelId+"&fdModelName="+modelName+"&pro="+pro).GetHashMapArray();
			if(modelType.length>0){
				if(modelType[0].fileName){
					var varInfo = _XForm_GetExitFileDictObj(modelType[0].fileName);
					for(var i = 0;i < varInfo.length;i++){
						var controlInfo = varInfo[i];
						var item = {};
						item.field = controlInfo.name;
						item.fieldText = controlInfo.label;
						item.fieldType = controlInfo.type;
						item.controlType = controlInfo.controlType;
						vars.push(item);
					}
				}
			}
			return vars;
		}
		
		function _XForm_GetExitFileDictObj(fileName) {
			return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=file&fileName="+fileName).GetHashMapArray();
		}
		//初始化字段
		function initDync(name,type,tip,param){
			var fdDynamicData = {};
			var dd = document.getElementsByName("fdDynamicData")[0].value;
			if(dd==null||dd==""||dd=="undefined"){
				fdDynamicData = {};
			}else{
				fdDynamicData = JSON.parse(dd);
			}
			fdDynamicData[name+"_tip"] = tip;
			fdDynamicData[name+"_type"] = type;
			fdDynamicData[name+"_text"] = "";
			fdDynamicData[name+"_value"] = "";
			fdDynamicData[name+"_param"] = param;
			document.getElementsByName("fdDynamicData")[0].value = JSON.stringify(fdDynamicData);
		}
		//选择控件
		function _Designer_Control_Attr_Dbechart_Input_Choose(fieldName,inputType,fieldText,param){
			var type = _Designer_Control_Attr_Dbechart_Input_GetLastFieldBySepa(inputType,"|");
			var url = Com_Parameter.ContextPath + "dbcenter/echarts/application/common/fields_tree.jsp?inputType="+ type;
			var data = [];
			data.push({"text":"<bean:message bundle="sys-relation" key="sysRelationEntry.dialog.type" />","vars":_Designer_Control_Attr_Dbechart_GetObj(),"braces":false});
			var fdDynamicData = {};
			var items = userInfo.getItems(type); // 获取人员相关
			data.push({"text":"<bean:message bundle="sys-relation" key="sysRelationEntry.dialog.user" />","vars":items,"braces":true});
			data.push({"text":"<bean:message bundle="sys-relation" key="sysRelationEntry.dialog.time" />","vars":[{'field':'date_datetime' ,'fieldText':'<bean:message bundle="sys-relation" key="sysRelationEntry.dialog.curtime" />','fieldType':'dateTime'}],"braces":true});
			var dialog = new DbEchartsApplication_Dialog(url,data,function(rtn){
				if(rtn){
					//document.getElementsByName("fdDynamicData")[0].value = "";
					document.getElementsByName("dynamic_"+fieldName)[0].value = rtn.text;
					var dd = document.getElementsByName("fdDynamicData")[0].value;
					if(dd==null||dd==""||dd=="undefined"){
						fdDynamicData = {};
					}else{
						fdDynamicData = JSON.parse(dd);
					}
					fdDynamicData[fieldName+"_tip"] = fieldText;
					fdDynamicData[fieldName+"_type"] = inputType;
					fdDynamicData[fieldName+"_text"] = rtn.text;
					fdDynamicData[fieldName+"_value"] = rtn.value;
					fdDynamicData[fieldName+"_param"] = param;
					document.getElementsByName("fdDynamicData")[0].value = JSON.stringify(fdDynamicData);
				}
			});
			dialog.setWidth("300");
			dialog.setHeight("380");
			dialog.show();
		}
		function Relation_HtmlEscape(s){
			if(s==null || s=="")
				return "";
			if(typeof s != "string")
				return s;
			var re = /\"/g;
			s = s.replace(re, "&quot;");
			re = /'/g;
			s = s.replace(re, '&#39;');
			re = /</g;
			s = s.replace(re, "&lt;");
			re = />/g;
			return s.replace(re, "&gt;");
		}
		function sh(v){
			if(v.value=='chart'){
				$("tr#fct").show();
			}else{
				$("tr#fct").hide();
			}
		}
		</script>
	</template:replace>
</template:include>