<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%
	request.setAttribute("currentUserId", UserUtil.getKMSSUser(request).getUserId());
%>
<!-- czk2019 -->
<script type="text/javascript">
Com_IncludeFile("common.js|calendar.js|data.js|dialog.js|jquery.js|formula.js", null, "js");
Com_IncludeFile('DbEchartsApplication_Dialog.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
Com_IncludeFile('userInfo.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
Com_IncludeFile('chartMode.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
var relationEntry = {};
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
		fdModuleModelName:"",fdParameter:"",fdChartId:"",fdChartName:"",fdChartType:"",fdCCType:"",fdDynamicData:""};
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

/**
 * 按下确定按钮
 */
function doOK() {
	var flag = true;
	var fdCCType = document.getElementsByName("fdCCType")[0];
	var fdChartId = document.getElementsByName("fdChartId")[0];
	var fdChartName = document.getElementsByName("fdChartName")[0];
	var fdChartType = document.getElementsByName("fdChartType")[0];
	if (checkValueIsNull("fdChartId") || checkValueIsNull("fdChartName")) {// 人员
		fdChartId.focus();
		alert('<bean:message bundle="sys-relation" key="sysRelationEntry.chart.type.chart.select" />');
		flag = false;
	}
	var fdDynamicData = document.getElementsByName("fdDynamicData")[0];
	/* $("input[name^='dynamic_']").each(function(){
		if(this.value==""&&flag){
			alert('<bean:message bundle="sys-relation" key="sysRelationEntry.chart.type.no" />');
			flag = false;
		}
	}); */
    //将页面录入的值填充到relationEntry对象
	//Relation_GetDataFromField(relationEntry);
    if(flag){
		relationEntry['fdParameter'] = "${currentUserId}";
		relationEntry['fdModuleModelName'] = "com.landray.kmss.dbcenter.echarts.model.DbEchartsChart";
		if("custom"==fdCCType.value){
			relationEntry['fdModuleModelName'] = "com.landray.kmss.dbcenter.echarts.model.DbEchartsCustom";
		}
		relationEntry['fdCCType'] = fdCCType.value;
		relationEntry['fdChartId'] = fdChartId.value;
		relationEntry['fdChartName'] = fdChartName.value;
		relationEntry['fdChartType'] = fdChartType.value;
		relationEntry['fdDynamicData'] = fdDynamicData.value;
		top.Com_DialogReturn(relationEntry);
    }
}
function dyniFrameSize() {
	try {
		// 调整高度
		var arguObj = document.getElementById("tab_person_setting");
		if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			window.frameElement.style.height = (arguObj.offsetHeight + 180) + "px";
			parent.resizeWindowHeight();
		}
	} catch(e) {
	}
}
//页面初始化载入
Com_AddEventListener(window, "load", function(){
	if(relationEntry && relationEntry!=null){
		if(relationEntry.fdCCType==null||relationEntry.fdCCType==""){
			document.getElementsByName("fdCCType")[0].value = "chart";
			$("tr#fct").show();
		}else{
			document.getElementsByName("fdCCType")[0].value = relationEntry.fdCCType;
			if(relationEntry.fdCCType=="chart"){
				$("tr#fct").show();
			}else{
				$("tr#fct").hide();
			}
		}
		document.getElementsByName("fdChartId")[0].value = relationEntry.fdChartId;
		document.getElementsByName("fdChartName")[0].value = relationEntry.fdChartName;
		document.getElementsByName("fdChartType")[0].value = relationEntry.fdChartType;
		document.getElementsByName("fdDynamicData")[0].value = relationEntry.fdDynamicData;
		if(relationEntry.fdDynamicData!=null&&relationEntry.fdDynamicData!=""&&relationEntry.fdDynamicData!="undefined"){
			var dd = JSON.parse(relationEntry.fdDynamicData);
			var html = "";
			var json = {};
			var repeat = "";
			$.each(dd,function(name,value) {
				if(name.indexOf("_tip")!=-1){
					name = name.replace('_tip','');
				}else if(name.indexOf("_type")!=-1){
					name = name.replace('_type','');
				}else if(name.indexOf("_text")!=-1){
					name = name.replace('_text','');
				}else if(name.indexOf("_value")!=-1){
					name = name.replace('_value','');
				}else if(name.indexOf("_param")!=-1){
					name = name.replace('_param','');
				}
				if(repeat.indexOf(name)==-1){
					repeat += name+";";
				}
			});
			var dds = repeat.split(";");
			$.each(dds,function(index,value) {
				if(value!=null&&value!=""){
					json = fieldJson(dd,value);
					html += json.tip+":<input type='text' name='dynamic_"+json.name+"' readonly='readonly' class='inputsgl' style='width:65%' value='"+ json.text +"'>";
					html += "<a href='javascript:void(0)' onclick='_Designer_Control_Attr_Dbechart_Input_Choose(\""+ json.name +"\",\""+ json.type+"\",\""+ json.tip +"\",\""+ json.param +"\");'><bean:message key='button.select' /></a>";
					html += "<br>";
				}
			});
			$("#dbEchart_Input_wrap").html(html);
		}
	} 

	dyniFrameSize();
});

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
	var url = Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=dialog&&echartModelName="
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
		_Designer_Control_Attr_Dbechart_Category_BuildInput(rn,function(config){
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
		async : false,//是否异步
		url : Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=findDynamic",
		data : data,
		dataType : "json",
		success : function(ajaxRn) {
			inputs = ajaxRn;
		}
	});
	if(cb){
		cb(inputs);
	}
}

function _Designer_Control_Attr_Dbechart_BuildTable(config,mapping){
	//图表展示赋值
	var chartType = config.chartType;
	if(chartType){
		$("input[name='fdChartType']").val(chartType);
	}else{
		$("input[name='fdChartType']").val("");
	}
	//图表配置类型的图表入参
	var html = "";
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
	var modelName = "${param.currModelName}";
	var modelId = "${param.currModelId}";
	var flowkey = "${param.flowkey}";
	var vars = [];
	if(flowkey==""){
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
	}else{
		//获取扩展字段
		var varInfo = [];
		try{
			varInfo = window.parent.opener.document.getElementById('IFrame_FormTemplate_'+flowkey).contentWindow.Designer.instance.getObj(true);
		}catch(e){
			varInfo = Formula_GetVarInfoByModelName(modelName);
		}
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
	return vars;
}
//获取表单控件的信息
function _XForm_GetTempExtDictObj(tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
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
