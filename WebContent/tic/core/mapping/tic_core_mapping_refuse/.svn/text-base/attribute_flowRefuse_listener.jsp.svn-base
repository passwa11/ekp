<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/htmlhead.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<script>
var Com_Parameter = {
	ContextPath:parent.dialogObject.Window.Com_Parameter.ContextPath,
	ResPath:parent.dialogObject.Window.Com_Parameter.ResPath,
	Style:parent.dialogObject.Window.Com_Parameter.Style,
	JsFileList:new Array,
	StylePath:parent.dialogObject.Window.Com_Parameter.StylePath
};
Com_IncludeFile('json2.js');
// 多语言对象
LangObject = parent.FlowChartObject.Lang;
var fdTemplateId;
//流程模版取ID
var fdIdObj = window.parent.parent.document.getElementById('fdId');
if (fdIdObj == null) {
	fdIdObj = window.parent.parent.dialogObject.Window.parent.parent.document.getElementsByName('fdId')[0];
}
if(fdIdObj){
	fdTemplateId=fdIdObj.value;
}
// 特权人取模版ID
var hrefFlag = window.parent.parent.dialogObject.Window.parent.parent.document.location.href;
if (hrefFlag != null && hrefFlag.indexOf("template=false") != -1) {
	//fdIdObj = window.parent.dialogObject.Window.parent.parent.dialogObject.Window.dialogObject.Window.document.getElementsByName("fdTemplateId")[0];
	if (parent.NodeContent != null && parent.NodeContent != '') {
		json =eval('('+ parent.NodeContent + ')');
		fdTemplateId=json.fdTemplateId;
	}
}
//fdTemplateId=fdIdObj.value;
// 当前节点多语言对象
LangNodeObject = LangObject.Node;
// 数据类型对象
DataTypeLangObject = LangObject.DataType;
</script>
<script type="text/javascript" src="../../../../resource/js/common.js"></script>
<script type="text/javascript">
Com_IncludeFile("document.js", "style/" + Com_Parameter.Style + "/doc/");
Com_IncludeFile("docutil.js|doclist.js|dialog.js|data.js|formula.js");
</script>
<link href="../../resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="../../resource/js/jquery.treeTable.js" type="text/javascript"></script>
<style type="text/css">
    .inputsgl, .tb_normal .inputsgl{
    	width:100px
     }
</style>
<script>
// 必须实现的方法，供父窗口(attribute_robotnode.html)调用。
function returnValue() {
	return getFuncParamsToJson();
}

//表单传出参数
var outParamJson;
//表单传入参数
var inParamJson;
//公式定义器参数
var formulaJson;

var splitTableSetTag = false;

function getFuncParamsToJson(){
	var rtnJson = {};
 	rtnJson.fdFunctionId =$("input[name='fdFunctionId']")[0].value;
	rtnJson.fdFunctionName =$("input[name='fdFunctionName']")[0].value;
	if(!$("input[name=xformRest]").prop('checked')){
	var rtnParamJsonArray = [];
	$.each(outParamJson, function(idx, obj) {
		var rtnParamJson = {};
		var id = obj.name;
		recursionGetValueToJson(obj,rtnParamJson,id);
		rtnParamJsonArray.push(rtnParamJson);
	});

	var trans_info_Array=[];
	var trans=transFormFieldList();
	for(var i=0;i<trans.length;i++){
		var trans_info = {};
		trans_info.name=trans[i].id;
		trans_info.title=trans[i].label;
		trans_info.type=trans[i].type;
 		trans_info.display_value=$($("#"+trans[i].id.replace(".","-")+"").find("textarea")[0]).val();
		trans_info.hidden_value=$($("#"+trans[i].id.replace(".","-")+"").find("input")[0]).val();
		trans_info_Array.push(trans_info);
	}
	rtnJson.xformRest =false;
	rtnJson.import_ = rtnParamJsonArray;
	rtnJson.export_ = trans_info_Array;
	var b_val = $('input[name="business_exception"]:checked').val();
	var p_val = $('input[name="procedure_exception"]:checked').val();
	var business_set;
	var procedure_set;
	if (b_val)//业务异常配置
	{
		business_set = {};
		if (b_val == 0) {//流程停止
			business_set.isStop = true;
		} else {//流程继续并赋值具体表单具体字段
			business_set.isStop = false;
			business_set.display_value = getByName(
					$(".business_exception_class"),
					"business_display_value").val();
			business_set.hidden_value = getByName(
					$(".business_exception_class"),
					"business_hidden_value").val();
			business_set.display_field_value = getByName(
					$(".business_exception_class"),
					"business_fieldValue_display").val();
			business_set.hidden_field_value = getByName(
					$(".business_exception_class"),
					"business_fieldValue_hidden").val();
		}
	}
	if (p_val)//程序异常配置
	{
		procedure_set = {};
		if (p_val == 0) {
			procedure_set.isStop = true;
		} else {
			procedure_set.isStop = false;
			procedure_set.display_value = getByName(
					$(".procedure_exception_class"),
					"procedure_display_value").val();
			procedure_set.hidden_value = getByName(
					$(".procedure_exception_class"),
					"procedure_hidden_value").val();
			procedure_set.field_value = getByName(
					$(".procedure_exception_class"), "fieldValue")
					.val();
		}
	}

	//异常设置信息校验
	if (b_val || p_val) {//如果有填值必须做校验
		var tag = true;
		if (b_val && b_val == 1) {
			if (!business_set.hidden_value
					|| !business_set.display_field_value) {
				tag = false;
			}
		}
		if (p_val && p_val == 1) {
			if (!procedure_set.hidden_value
					|| !procedure_set.field_value) {
				tag = false;
			}
		}
		if (tag) {//校验成功
			if (business_set) {
				rtnJson.businessException = business_set;
			}
			if (procedure_set) {
				rtnJson.procedureException = procedure_set;
			}
			return JSON.stringify(rtnJson)
		} else {
			alert("表单赋值信息必填!");
		}
	} else {
		if(splitTableSetTag){
			return JSON.stringify(rtnJson);
		}else{
			//alert("需要拆分调用函数的明细表必填!");
		}
	}
 	rtnJson.import = rtnParamJsonArray;
 	rtnJson.export = trans_info_Array;
 	rtnJson.import_ = rtnParamJsonArray;
 	rtnJson.export_ = trans_info_Array;
	return JSON.stringify(rtnJson)
	}else{
		rtnJson.xformRest =true;
		rtnJson.xformParams=buildParaJson();
		return JSON.stringify(rtnJson);
	}
}
function recursionGetValueToJson(paramJson,rtnParamJson,id){
	rtnParamJson.name = paramJson.name;
	rtnParamJson.type = paramJson.type;
	rtnParamJson.title = paramJson.title;
	if(paramJson.children){
		var childArray = [];
		var childObj = paramJson.children;
		$.each(childObj, function(idx, obj) {
			var childParamJson = {};
			var childId = id+"-"+obj.name;
			recursionGetValueToJson(obj,childParamJson,childId);
			childArray.push(childParamJson);
		});
		rtnParamJson.children = childArray;}
	else{	
    	var displayValue = $($("#"+id.replace(":","_")).find('textarea')[0]).val();
    	var hiddenValue = $($("#"+id.replace(":","_")).find('input')[0]).val();
    	rtnParamJson.display_value = displayValue;
    	rtnParamJson.hidden_value = hiddenValue;
	}
}

//初始化
function initValue(config, eventType, nodeObj){
	//debugger;
	// config返回的保存信息
	if (config != null) {
		var json= JSON.parse(config);
		console.log("json:"+JSON.stringify(json));
		// 获得内容对象
		$("input[name=xformRest]").attr('checked',json.xformRest);
		 xformRest_select($("input[name=xformRest]")[0]);
	 	$("input[name='fdFunctionId']")[0].value=json.fdFunctionId;
		$("input[name='fdFunctionName']")[0].value=json.fdFunctionName ;
		if(!json.xformRest){
			if(json.import_){
				outParamJson = json.import_;
			}else{
				outParamJson=json.import;
			}
			if(json.export_){
				inParamJson=json.export_;
			}else{
				inParamJson=json.export;
			}
			
			var data = new KMSSData();
			data.SendToBean("ticCoreFindFormulaJsonService&funcId="+json.fdFunctionId,after_initValue);
			
			//异常设置信息回显
			if (json.businessException) {
				var exceptionInfo = json.businessException;
				if (exceptionInfo.isStop) {
					$('input[name="business_exception"]').eq(0).attr(
							"checked", true);
					$(".business_exception_class").hide();
				} else {
					$('input[name="business_exception"]').eq(1).attr(
							"checked", true);
					$(".business_exception_class").find(
							"input[name='business_display_value']").val(
							exceptionInfo.display_value);
					$(".business_exception_class").find(
							"input[name='business_hidden_value']").val(
							exceptionInfo.hidden_value);
					$(".business_exception_class").find(
							"input[name='business_fieldValue_display']")
							.val(exceptionInfo.display_field_value);
					$(".business_exception_class").find(
							"input[name='business_fieldValue_hidden']")
							.val(exceptionInfo.hidden_field_value);
				}
			}
			if (json.procedureException) {
				var exceptionInfo = json.procedureException;
				if (exceptionInfo.isStop) {
					$('input[name="procedure_exception"]').eq(0).attr(
							"checked", true);
					$(".procedure_exception_class").hide();
				} else {
					$('input[name="procedure_exception"]').eq(1).attr(
							"checked", true);
					$(".procedure_exception_class").find(
							"input[name='procedure_display_value']").val(
							exceptionInfo.display_value);
					$(".procedure_exception_class").find(
							"input[name='procedure_hidden_value']").val(
							exceptionInfo.hidden_value);
					$(".procedure_exception_class").find(
							"input[name='fieldValue']").val(
							exceptionInfo.field_value);
				}
			}
			
		}else{
			trs= new Array();
			$.each(json.xformParams, function(idx, obj) {
				genParaInHtml(obj,"");
			});
			createTableForRest(trs);
			xformArray=trs;
		}		
	}	
	
};
var outParamArray;
function after_initValue(rtn){
	if (!rtn) {
		return;
	}
	var data = rtn.GetHashMapArray();
	if(data){
		formulaJson=JSON.parse(data[0]["paraOut"]);
	}
	outParamArray=[];
	var fieldList=transFormFieldList();
	 for(var i=0;i<fieldList.length;i++){
	      for(var j=0;j<inParamJson.length;j++){
	        	if(fieldList[i].id==inParamJson[j].name){
	        		fieldList[i].display_value=inParamJson[j].display_value;
	        		fieldList[i].hidden_value=inParamJson[j].hidden_value;
	        	}       	
	     }
	 }
	createTableInElementHTML(fieldList);
	createTableOutElementHTML(outParamJson);
}
function getOutParamArray(obj,name){
	var children =obj.children;
	   if(name!="")
	     obj.name=name+ "-"+obj.name;
	if(children){
	   $.each(children, function(index,child) {  
		   getOutParamArray(child,obj.name); 
		});
	}else{
		outParamArray.push(obj);
	}
}
function createTableInElementHTML(tr_list) {
	for(var i = 0;i <  tr_list.length; i++){
		var tr=tr_list[i];
		tr.id=tr.id||tr.name;
		tr.label=tr.label||tr.title;
		tr.type=tr.businessType||tr.type;
		if(tr.type=="array"||tr.type=="detailsTable"){
			var tr_new = $("<tr id='"+tr.id+"'><td>"+tr.id+"</td><td>"+tr.label+"</td><td colspan='2' ></td></tr>");
		}else if(tr.id.indexOf(".")!=-1){
			tr.id=tr.id.replace(".","-");
			var parentId=tr.id.substr(0, tr.id.indexOf('.'));
			var tr_new = $("<tr id='"+tr.id+"' class='child-of-"+parentId+"'><td>"+tr.id+"</td><td>"+tr.label+"</td><td><textarea style='width: 160px;height: 25px' readonly='readonly' name='"+tr.id+"_input'></textarea><input type='hidden' name='"+tr.id+"_hidden'></input></td><td align='center'><img  class='td_img' src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0' onclick=\"mapping('"+tr.id+"_hidden','"+tr.id+"_input','in');\"></td></tr>");
		}else{
			var tr_new = $("<tr id='"+tr.id+"'><td>"+tr.id+"</td><td>"+tr.label+"</td><td><textarea style='width: 160px;height: 25px' readonly='readonly' name='"+tr.id+"_input'></textarea><input type='hidden' name='"+tr.id+"_hidden'></input></td><td align='center'><img  class='td_img' src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0' onclick=\"mapping('"+tr.id+"_hidden','"+tr.id+"_input','in');\"></td></tr>");
		}
		if(tr.isShow=="none"){
			tr_new.hide();
		}	
	    $("#table_paraIn").append(tr_new);
	    if(tr.display_value){
			$($("#"+tr.id).find('textarea')[0]).val(tr.display_value.replace(/\\n/g,"\n"));
        	$($("#"+tr.id).find('input')[0]).val(tr.hidden_value.replace(/\\n/g,"\n"));
	    } 
     }
	$("#table_paraIn").treeTable({ initialState: true,indent:15 }).expandAll();
}
	
/**
 * 获取自定义表单字段
 */
function transFormFieldList(containAll) {
	var rtnResult = new Array();
	var fieldList = parent.FlowChartObject.FormFieldList;
	if (!fieldList)
		return rtnResult;
	// 转换成option支持的格式
	for ( var i = 0, length = fieldList.length; i < length; i++) {
		fieldList[i].isShow="";
		if(!containAll){
			if(!fieldList[i].controlType)
				fieldList[i].isShow="none";
			var type=fieldList[i].type.toLowerCase().replace(/\[|]/g,'');
			if(fieldList[i].controlType=="detailsTable"){
				fieldList[i].type="arrayObject";
			}else if(type=="string"||type=="int"||type=="boolean"||type=="long"||type=="double"||type=="object"||type=="array"){
				fieldList[i].type=type;
			}else if(type.indexOf("date")>-1||type.indexOf("time")>-1){
				fieldList[i].type="string";
			}else if(type.indexOf("bigdecimal")>-1){
				fieldList[i].type="double";
			}else{
				continue;
			}
		}
		rtnResult.push( {
				id:fieldList[i].name,
				businessType: fieldList[i].type,
				label: fieldList[i].label,
				len:200,
				length:0,
				name: fieldList[i].name,
				type:fieldList[i].type,
				isShow:fieldList[i].isShow,
				isTemplateRow:fieldList[i].isTemplateRow||false
			});
	}
	return rtnResult;
};

function selectFunction() {
	if($("input[name=xformRest]").prop('checked')){
		Dialog_TreeList(false,"fdFunctionId","fdFunctionName",";",
		           "ticCoreFindFunctionService&selectId=!{value}&type=cate&fdAppType=${param.fdAppType}&isRest=true",
		           "业务分类",
		           "ticCoreFindFunctionService&selectId=!{value}&type=func&fdAppType=${param.fdAppType}&isRest=true",
		           after_dialogSelect,
		           "ticCoreFindFunctionService&type=search&keyword=!{keyword}&fdAppType=${param.fdAppType}&isRest=true",
		           null,null,null,
		           "选择函数");
	}else{
		Dialog_TreeList(false,"fdFunctionId","fdFunctionName",";",
		           "ticCoreFindTransSettService&selectId=!{value}&type=cate&fdAppType=${param.fdAppType}",
		           "业务分类",
		           "ticCoreFindTransSettService&selectId=!{value}&type=func&fdAppType=${param.fdAppType}",
		           after_dialogSelect,
		           "ticCoreFindTransSettService&type=search&keyword=!{keyword}&fdAppType=${param.fdAppType}",
		           null,null,null,
		           "选择函数");
	}
}

function after_dialogSelect(rtn) {
	$("#table_paraOut").empty();$("#table_paraIn").empty();
	$("#table_paraOut").append("<tr><td class='td_normal_title' width='40%'>转换函数出参</td><td class='td_normal_title' width='20%'>参数说明</td><td class='td_normal_title' width='20%'>表单映射字段</td><td class='td_normal_title' width='20%'><a href='javascript:void(0)' style='color:#47b5ea;' onclick='adapt();' title='自动匹配映射关系'>适配 </a></td></tr>");	
	$("#table_paraIn").append("<tr><td class='td_normal_title' width='40%'>转换函数入参</td><td class='td_normal_title' width='20%'>参数说明</td><td class='td_normal_title' width='20%'>映射名称</td><td class='td_normal_title' width='20%'><span style='color:#47b5ea' onclick='expand();'> 展开 </span></td></tr>");	
	
	if (!rtn) {
		return;
	}
	var data = rtn.GetHashMapArray();
	if (data && data.length > 0) {
		var info = data[0]["import"];
		var infoObj = JSON.parse(info);
		//表单传出参数为转换配置传入参数
		outParamJson =infoObj;
		formulaJson=JSON.parse(data[0]["export"]);
	}
	createTableOutElementHTML(outParamJson);	
	createTableInElementHTML(transFormFieldList());	
}
function createTableOutElementHTML(infoObj){
	trs= new Array();
	$.each(infoObj, function(idx, obj) {
		genParaInHtml(obj,"");
	});
	for(i=0;i<trs.length;i++){
		var tr = trs[i];
		var tr_new;
		if(tr.child)//判断如果有子节点不显示填值框
		{
			tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td colspan='2'></td></tr>");
		}else
		{
			tr_new = $("<tr id='"+tr.id+"'><td>"+tr.name+"</td><td>"+tr.title+"</td><td><textarea style='width: 160px;height: 25px' readonly='readonly' name='"+tr.id+"_input'></textarea><input type='hidden' name='"+tr.id+"_hidden'></input></td><td align='center'><img  class='td_img' src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0' onclick=\"mapping('"+tr.id+"_hidden','"+tr.id+"_input','out');\"></td></tr>");
		} 
		 if(tr.parentId){
			tr_new.addClass("child-of-"+tr.parentId);
		}
		$("#table_paraOut").append(tr_new);	
		if(tr.display_value){
			$($("#"+tr.id).find('textarea')[0]).val(tr.display_value.replace(/\\n/g,"\n"));
        	$($("#"+tr.id).find('input')[0]).val(tr.hidden_value.replace(/\\n/g,"\n"));
	}
	}
	$("#table_paraOut").treeTable({ initialState: true,indent:15 }).expandAll();
}

function genParaInHtml( obj,  parentId){
	var name = obj.name;
	var title = obj.title;
	var id = name;
	if(obj.type)
		var type = obj.type;
	if(parentId){
		id = parentId+"-"+name;
	}
	var tr = {};
	//判断是否有children,做个标识用于页面展示去除填值信息显示
	if(obj.children)
	{
		tr.child = 1;
	}
	//用于编辑页面时数据回显
	if(obj.display_value)
	{
		tr.display_value = obj.display_value;
		tr.hidden_value = obj.hidden_value;
	}
	
	tr.id = id.replace(":","_");
	tr.name = name;
	tr.title = title;
	if(type)
		tr.type= type;
	tr.parentId = parentId;
	trs.push(tr);
	var children = obj.children;
	if(children){
		$.each(children, function(idx2, obj2) {
			genParaInHtml(obj2,tr.id);
		});
	}
}

function mapping(idField, nameField,type){
	var varInfo = new Array();
	var idFieldObj = idField;
	var nameFieldObj = nameField;
	if(type=="out"){
		varInfo = transFormFieldList(true);
		idFieldObj = $("#table_paraOut").find("input[name='"+idField+"']")[0];
		nameFieldObj = $("#table_paraOut").find("textarea[name='"+nameField+"']")[0];
	} else if (type == "exception") {
		varInfo = getFormFieldInfo();
	}  else  if (type == "in"){
		varInfo=genXformVarInfoByJson(formulaJson);
		idFieldObj = $("#table_paraIn").find("input[name='"+idField+"']")[0];
		nameFieldObj = $("#table_paraIn").find("textarea[name='"+nameField+"']")[0];
	}else{
		varInfo = transFormFieldList(true);
	}
	if(!idFieldObj && !nameFieldObj){
		idFieldObj = idField;
		nameFieldObj = nameField;
	}
	Formula_Dialog(idFieldObj,nameFieldObj,varInfo,'Object',null,null);
}
function genXformVarInfoByJson(fields){
	var varInfo = new Array();
	if(!fields){
		return varInfo;
	}
	 for(var i=0;i<fields.length;i++){
		genVarInfo(fields[i],varInfo,"","");
	 }
	return varInfo;
}
function genVarInfo(field,varInfo,parentName,parentLabel){
	 var m = new Object();
		m.businessType='inputText';
		var title = field.title;
		if(!title || title==""){
			title = field.name;
		}
		if(parentName && parentName!=""){
			m.label= parentLabel+"."+title;
			m.name=parentName+"."+field.name;
		}else{
			m.label= title;
			m.name=field.name;
		}
        
        m.len=200;
        m.length=0;
        m.type=field.type.substring(0, 1).toUpperCase() +field.type.substring(1);
       
		varInfo.push(m);
		var children = field.children;
		if(children){
			for(var i=0;i<children.length;i++){
	     		genVarInfo(children[i],varInfo,m.name,m.label);
	     	 }
		}
}

function expand(){
	$("#table_paraIn  tr").each(function(){
		$(this).show();
	});
}
function adapt(){
	var trans=transFormFieldList();
	$("#table_paraOut tr:not(:first)").each(function(){
		if(!$(this).hasClass("parent"))
		var name=$(this).find("td:first-child").text();
		var title=$(this).find("td:eq(1)").text();
		for(var i=0;i< trans.length;i++){
			var label = trans[i].label;
			if (trans[i].id == name || label == title || label.substring(label.indexOf('.')+1) == title) {
				var value="$"+trans[i].label+"$";
				var id="$"+trans[i].id+"$";
				$(this).find("textarea").val(value);
				$(this).find("input").val(id);
			}
		}
	});
}
function updateMapping(){
	$("#table_paraOut tr:not(:first)").remove(); 
	var  fdFunctionId=$("input[name='fdFunctionId']")[0].value;
	if(!fdFunctionId){
		return ;
	}
	var data = new KMSSData();
	data.SendToBean("ticCoreFindFormulaJsonService&funcId="+fdFunctionId,after_updateMapping);
}

function after_updateMapping(rtn){
	if (!rtn) {
		return;
	}
	var data = rtn.GetHashMapArray();
	if(data){
		paraIn=JSON.parse(data[0]["paraIn"]);
	}
	createTableOutElementHTML(paraIn);
	//updateFieldType(outParamJson,paraIn);
	//更新映射关系并重新赋值原值
	updateAndSetResourceValue(paraIn,outParamJson);
	//将更新好的json重新赋值给outParamJson
	outParamJson = paraIn;
	//console.info(JSON.stringify(outParamJson));
	var array=$.extend(true,{},outParamJson);//复制而不是引用
	outParamArray=[];
	$.each(array, function(idx, obj) {
		getOutParamArray(obj,"");
	});
	$.each(outParamArray, function() {
		 $("#table_paraOut").find("textarea[name="+this.name+"_input]").val(this.display_value);
	     $("#table_paraOut").find("input[name="+this.name+"_hidden]").val(this.hidden_value);
	});
}

//用于更新映射操作时更新新增字段并重新赋值回原来映射
function updateAndSetResourceValue(paraIn,outParamJson){
	$.each(paraIn, function(idx, obj) {
		var name = obj.name;
		var resourceObj = getFieldObj(outParamJson,name);
		delete obj['tr_id'];
		if(resourceObj){
			if(resourceObj.display_value){
				obj['display_value'] = resourceObj.display_value;
			}
			if(resourceObj.hidden_value){
				obj['hidden_value'] = resourceObj.hidden_value;
			}
			if(obj.children && resourceObj.children){
				updateAndSetResourceValue(obj.children,resourceObj.children);
			}
		}
	});
}

function updateFieldType(outParamJson,paraIn){
	$.each(outParamJson, function(idx, obj) {
		var name = obj.name;
		var inObj = getFieldObj(paraIn,name);
		if(inObj){
			obj.type = inObj.type;
			if(obj.children && inObj.children){
				updateFieldType(obj.children,inObj.children);
			}
		}
	});
}

function getFieldObj(paraIn,name){
	for(var i=0;i<paraIn.length;i++){
		var thisName = paraIn[i].name; 
		if(name==thisName){
			return paraIn[i];
		}
	}
	return null;
}

function xformRest_select(dom){
 	$("input[name='fdFunctionId']").val("");
	$("input[name='fdFunctionName']").val("");
	$("#table_paraOut  tbody ").find("tr:not(:first)").remove();
	$("#table_paraIn tbody ").find("tr:not(:first)").remove();
	$("#table_xform tbody ").find("tr:not(:first)").remove();
	if(dom.checked){
		$('#addRestFunc').show();
		$('#ipForm_btn').show();
		$('#udmp_btn').hide();
		$('#mapping_div').hide();
		$('#xformRest_div').show();
	}else{
		$('#addRestFunc').hide();
		$('#ipForm_btn').hide();
		$('#udmp_btn').show();
		$('#mapping_div').show();
		$('#xformRest_div').hide();
	}
}
 function importFormFields(){
		seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
			dialog.iframe("/tic/core/mapping/xformRestTree.jsp","",function(rtnData){
				if(rtnData)
					createTableForRest(rtnData);
			},{width:400,height:250,buttons:[{
				name : "确定",
				value:true,
				fn : function(value,_dialog) {
					var iframe = _dialog.element.find('iframe').get(0);
					iframe.contentWindow.onSelectOk();
				}
			},{
				name : "取消",
				value:true,
				fn : function(value,_dialog) {
					_dialog.hide();
				}
			} ]});
		});
} 
 var xformArray;
 function createTableForRest(tr_list) {
	    clearFormField();
	    if(xformArray){
	    	var concatArray = xformArray.concat(tr_list);//合并成一个数组
	        temp = {},//用于name判断重复
	        result = [];//最后的新数组
		    $.each(concatArray, function(index,item) {
		        if(!temp[item.name]){
		            result.push(item);
		            temp[item.name] = true;
		        }
		    });
		    tr_list=result;
	    }
		for(var i = 0;i <  tr_list.length; i++){
			var tr=tr_list[i];
			if(!tr.type){
				var trans=transFormFieldList();
				trans.find(function(value) {
					if(tr.name==value.id)
						tr.type=value.businessType;
				});
			}
			    var id=tr.name.replace(".","-");
				if(tr.type!="arrayObject"&&tr.type!="object"){
					var tr_new= $("<tr id='"+id+"'><td><input type='text' name='name' validate='required'  value='"+tr.name+"'  class='inputsgl'></input></td><td><input type='text' name='title' validate='required'  value='"+tr.title+"'  class='inputsgl'></td><td><select name='type'>"+
					    	"<option value='string'>字符串</option><option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点型</option>"+
					    	"<option value='object'>对象</option><option value='arrayObject'>对象数组</option><option value='arrayInt'>整型数组</option>"+
					    	"<option value='arrayDouble'>浮点数组</option><option value='arrayBoolean'>布尔数组</option><option value='arrayString'>字符串数组</option>"+
					    	"</select></td><td><textarea style='width: 160px;height: 25px' readonly='readonly' name='xform_"+tr.name+"_input'></textarea><input type='hidden' name='xform_"+id+"_hidden'></input></td>"+
					    	"<td align='center'><img  class='td_img' src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0' onclick=\"mapping('xform_"+id+"_hidden','xform_"+id+"_input','out');\"> <img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteFormField(this);\"/></td></tr>");
				}else{
					var tr_new= $("<tr id='"+id+"'><td><input type='text' name='name' validate='required'  value='"+tr.name+"'  class='inputsgl'></input></td><td><input type='text' name='title' validate='required'  value='"+tr.title+"'  class='inputsgl'></td><td  colspan='2'></td>"
							+"<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\" addFormField(this);\"/> <img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteFormField(this);\"/></td></tr>");			
				}
		    	tr_new.find("select").on("change",function(){
		    		var type=$(this).val();
		    		var img_td=$(this).parent().parent().find("img").parent();
		    		img_td.find('#img_add').remove();
		    		if( type=="object"|| type=="arrayObject"){
		    			img_td.prepend("<img id='img_add'  src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\" addFormField(this);\"/> " );
		    		}
		    	});
	 		if(tr.parentId)
	 			tr_new.addClass("child-of-"+tr.parentId);
		    tr_new.find("select").val(tr.type);
		    $("#table_xform").append(tr_new);
			if(tr.display_value){
				$($("#"+id).find('textarea[name^=xform_]')[0]).val(tr.display_value);
		        $($("#"+id).find('input[name^=xform_]')[0]).val(tr.hidden_value);
			}else{
				$($("#"+id).find('textarea[name^=xform_]')[0]).val('$'+tr.title+'$');
		        $($("#"+id).find('input[name^=xform_]')[0]).val('$'+tr.name+'$');
			}
	     }
		$("#table_xform").treeTable({ initialState: true,indent:15 }).expandAll();
	}
 var field_id = 0;
 function addFormField(parent){
	 var val=$(parent).parent().parent().find('[name="type"]').val()||"object";
	 var tableObj = $('#table_xform');
 	tableObj.removeClass("treeTable").find("tbody tr").each(function() {
         $(this).removeClass('initialized');
 	});
 	var id = field_id++;
		if(parent){
			id = $(parent).parent().parent().attr("id") + "-" + id;
		}else{
			id = "para"+id;
		}
		var tr_new = $("<tr id='"+id+"'><td><input  type='text' name='name' validate='required' class='inputsgl'></input></td><td><input type='text' name='title' validate='required' class='inputsgl'></input></td>"+
				"<td><select name='type'><option value='string'>字符串</option>"+
		    	"<option value='boolean'>布尔</option><option value='int'>整型</option><option value='long'>长整型</option><option value='double'>浮点型</option>"+
		    	"<option value='object'>对象</option><option value='arrayObject'>对象数组</option><option value='arrayInt'>整型数组</option>"+
		    	"<option value='arrayDouble'>浮点数组</option><option value='arrayBoolean'>布尔数组</option><option value='arrayString'>字符串数组</option></select></td>"+
				"<td><textarea style='width: 160px;height: 25px' readonly='readonly' name='xform_"+id+"_input'></textarea><input type='hidden' name='xform_"+id+"_hidden'></input></td>"+
		    	"<td align='center'><img  class='td_img' src='${KMSS_Parameter_StylePath}icons/edit.gif' border='0' onclick=\"mapping('xform_"+id+"_hidden','xform_"+id+"_input','out');\"> <img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteFormField(this);\"/></td></tr>");
    	tr_new.find("select").on("change",function(){
    		var type=$(this).val();
    		var img_td=$(this).parent().parent().find("img").parent();
    		img_td.find('#img_add').remove();
    		if( type=="object"|| type=="arrayObject"){
    			img_td.prepend("<img  id='img_add' src=\"${KMSS_Parameter_StylePath}icons/add.gif\" title=\"新增\" onClick=\" addFormField(this);\"/> " );
    		}
    	});
		if(!parent){

 	}else{
 		tr_new.addClass("child-of-"+$(parent).parent().parent().attr("id"));
 	}
		if(parent){
			$(parent).parent().parent().after(tr_new);
		}else{
			tableObj.append(tr_new);
		}
 	tableObj.treeTable({ initialState: true,indent:15 }).expandAll();
 }
 function clearFormField(){
 	$("#xformRest_div").find("tbody tr").each(function(index) {
		if(index!=0){
			$(this).remove();
		}
	});
 }
 function deleteFormField(obj) {
	 var id = $(obj).parent().parent().attr("id");
		id = id.replace(new RegExp("/","g"), "\\/");
		$(obj).parent().parent().siblings("tr.child-of-" + id).each(function(index) {
			$(this).remove();    
	});
	$(obj).parent().parent().remove();
 }
 function buildParaJson(){
 	var paras = [];
 	 $("#table_xform").find("tbody tr").each(function(index) {
 		  if(index>0){
 			  var isRootNode = ($(this)[0].className.search("child-of") == -1);
               if(isRootNode) {
             	  paras.push(buildParaField($(this)));
               }
 		  }
       });
 	return paras;
 }
 function buildParaField(node) {
     
 	var name = node.find("input[name='name']").val();
 	var title = node.find("input[name='title']").val();
 	var type = node.find("select[name='type'] option:selected").val();
 	var para_value = node.find("textarea[name^='xform_']").val();
 	var para_value_hidden = node.find("input[name^='xform_']").val();
 	//debugger;
 	var field = {};
 	field.name = name;
 	field.type = type;
 	field.title = title;
 	if(para_value){
 		field.display_value = para_value;
     	field.hidden_value = para_value_hidden;
 	}
 	
 	var childNodes = getChildrenOf(node);
       
       if(node.hasClass("parent")) {
			var children = [];
         childNodes.each(function() {
         	children.push(buildParaField($(this)));
         });
         field.children = children;
       }
       if((field.type=="object"||field.type=="arrayObject")&&!field.children){
           alert(field.name+":对象必须有子节点");    
           return ;
       }
       return field;
   }
 
 function getChildrenOf(node) {
	 var id = node.attr("id");
		id = id.replace(new RegExp("/","g"), "\\/");
     return $(node).siblings("tr.child-of-" + id);
   };
function addRest(){
	 var url="/tic/rest/connector/tic_rest_main/ticRestMain.do?method=add&fdAppType=${param.fdAppType}";
	 window.open("${LUI_ContextPath}"+url,"_blank");
}


$(function() {
	$(":radio").click(function() {
		//alert("您是..." + $(this).val()+$(this).attr("name"));
		if ($(this).val() == 0) {
			$("." + $(this).attr("name") + "_class").hide();
		} else {
			$("." + $(this).attr("name") + "_class").show();
		}
	});
});

//根据jq对象获取input
function getByName($parent, name) {
	if (!Array.isArray(name))
		name = [ name ];
	var selector = name.map(function(item) {
		return 'input[name="' + item + '"]';
	});
	return $parent.find(selector.join(','))
}
</script>

<body>
<table width="100%" class="tb_normal">
	<tr >
       <td colspan='2'><input type="checkbox" style="vertical-align: middle;" name="xformRest"  onclick="xformRest_select(this);">
           ${lfn:message('tic-core-common:ticCoreCommon.RESTOriginalFuncMapping')}  <span><span id="addRestFunc" style="color:#47b5ea;cursor: pointer;display:none;" onclick="addRest();">${lfn:message('tic-core-common:ticCoreCommon.addRestFunc')}</span></span>
       </td></tr>
    <tr>
		<td width="15%" class="td_normal_title">${lfn:message('tic-core-common:ticCoreCommon.funcName')}</td>
		<td>
		 <xform:dialog required="true" propertyId="fdFunctionId" propertyName="fdFunctionName" showStatus="edit"
				subject="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}" dialogJs="selectFunction()" style="width:35%;float:left">
		</xform:dialog>
		<input type="button" id="udmp_btn" class="btnopt" value="${lfn:message('tic-core-common:ticCoreTransSett.updateMappingRelation')}" onclick=" updateMapping();"/>
		<input type="button" id="ipForm_btn" class="btnopt" value="${lfn:message('tic-core-common:ticCoreTransSett.selectFormFieldImport')}" onclick="importFormFields();"  style="display:none;"/>
		</td>
	</tr>
</table>
<div style="width:100%" id="mapping_div">
       <br/>
           ${lfn:message('tic-core-common:ticCoreTransSett.transferToFuncFieldMapping')} 
		<table class="tb_normal" style="width:100%" id="table_paraOut">
						<tr>
			            	<td class="td_normal_title" width="40%">
									${lfn:message('tic-core-common:ticCoreTransSett.transFuncInParam')}
							</td>
			            	<td class="td_normal_title" width="20%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
							</td>
							<td class="td_normal_title" width="20%">
									${lfn:message('tic-core-common:ticCoreTransSett.formMappingField')}
							</td>
							<td class="td_normal_title" width="20%">
								<a href='javascript:void(0)' style='color:#47b5ea;' onclick='adapt();' title='自动匹配映射关系'>${lfn:message('tic-core-common:ticCoreTransSett.adapter')} </a>
							</td>		
						</tr>
       </table>
       <br/>
       ${lfn:message('tic-core-common:ticCoreTransSett.transFuncReturnMapping')} 
       <table class="tb_normal" style="width:100%" id="table_paraIn">
						<tr>
			            	<td class="td_normal_title" width="40%">
									${lfn:message('tic-core-common:ticCoreTransSett.transFuncOutParam')}
							</td>
			            	<td class="td_normal_title" width="20%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
							</td>
							<td class="td_normal_title" width="20%">
									${lfn:message('tic-core-common:ticCoreTransSett.mappingName')}
							</td>
							<td class="td_normal_title" width="20%">
									${lfn:message('tic-core-common:ticCoreCommon.oper')} <span style="color:#47b5ea" onclick="expand();"> ${lfn:message('tic-core-common:ticCoreCommon.extend')} </span>
							</td>		
						</tr>
       </table> 
       <br>
       
       ${lfn:message('tic-core-common:ticCoreCommon.exceptionHandleSetting')}
       <table class="tb_normal" style="width:100%" id="table_exception_set">
		<tr>
           	<td class="td_normal_title" width="12%">
					${lfn:message('tic-core-common:ticCoreCommon.exceptionType')}
			</td>
           	<td class="td_normal_title" width="28%">
					${lfn:message('tic-core-common:ticCoreCommon.exceptionHandleWay')}
			</td>
			<td class="td_normal_title" width="32%">
					${lfn:message('tic-core-common:ticCoreTransSett.formField')}
			</td>
			<td class="td_normal_title" width="28%">
					${lfn:message('tic-core-common:ticCoreCommon.formFieldSetValue')}
			</td>		
		</tr>
		<tr>
			<td>
				${lfn:message('tic-core-common:ticCoreCommon.busiException')}
			</td>
			<td>
				<div style="line-height: 25px;width: 100%">
					<label><input style="vertical-align: middle;" name="business_exception" type="radio" value="0" /><font style="vertical-align: middle;">${lfn:message('tic-core-common:ticCoreCommon.flowStop')}</font></label> 
					<label><input style="vertical-align: middle;" name="business_exception" type="radio" value="1" /><font style="vertical-align: middle;">${lfn:message('tic-core-common:ticCoreCommon.formSetValue')}</font></label> 
				</div>
			</td>
			<td>
				<div class="business_exception_class" style="line-height: 25px;width: 100%;">
					<input class="inputsgl" style="width: 160px;height: 18px;vertical-align: middle;" readonly="readonly" name="business_display_value"/>
					<input type="hidden" name="business_hidden_value"/>
					<img style="vertical-align: middle;" border='0' src="${KMSS_Parameter_StylePath}icons/edit.gif" onclick="mapping('business_hidden_value','business_display_value','out')">
				</div>
			</td>
			<td>
				<div class="business_exception_class" style="line-height: 25px;">
					<!-- <input style="height: 18px;" class="inputsgl" type="text" name="fieldValue"/> -->
					<input style="width: 135px;height: 18px;vertical-align: middle;" class="inputsgl" type="text" readonly="readonly" name="business_fieldValue_display"/>
					<input type="hidden" name="business_fieldValue_hidden">
					<img style="vertical-align: middle;" border='0' src="${KMSS_Parameter_StylePath}icons/edit.gif" border="0" onclick="mapping('business_fieldValue_hidden','business_fieldValue_display','in')">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				${lfn:message('tic-core-common:ticCoreCommon.programException')}
			</td>
			<td>
				<div style="line-height: 25px;width: 100%">
					<label><input style="vertical-align: middle;" name="procedure_exception" type="radio" value="0" /><font style="vertical-align: middle;">${lfn:message('tic-core-common:ticCoreCommon.flowStop')}</font></label> 
					<label><input style="vertical-align: middle;" name="procedure_exception" type="radio" value="1" /><font style="vertical-align: middle;">${lfn:message('tic-core-common:ticCoreCommon.formSetValue')}</font></label> 
				</div>
			</td>
			<td>
				<div class="procedure_exception_class" style="line-height: 25px;">
					<input class="inputsgl" style="width: 160px;height: 18px;vertical-align: middle;" readonly="readonly" name="procedure_display_value"/>
					<input type="hidden" name="procedure_hidden_value"/>
					<img style="vertical-align: middle;" border='0' src="${KMSS_Parameter_StylePath}icons/edit.gif" border="0" onclick="mapping('procedure_hidden_value','procedure_display_value','out')">
				</div>
			</td>
			<td>
				<div class="procedure_exception_class" style="line-height: 25px;">
					<input style="width: 135px;height: 18px;" class="inputsgl" type="text" name="fieldValue"/>
				</div>
			</td>
		</tr>
       </table>
       <br/>
</div>
<div style="width:100%;display:none;" id="xformRest_div">
		<table class="tb_normal" style="width:100%" id="table_xform">
						<tr>
			            	<td class="td_normal_title" width="20%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
							</td>
			            	<td class="td_normal_title" width="20%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramExplain')}
							</td>
							<td class="td_normal_title" width="15%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramType')}
							</td>
							<td class="td_normal_title" width="30%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramValue')}
							</td>
							<td class="td_normal_title" align='center'>
										<img  src="${KMSS_Parameter_StylePath}icons/add.gif" title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addFormField();"/> <img  src="../../resource/images/recycle.png" title="${lfn:message('tic-core-common:ticCoreCommon.clear')}" onClick="clearFormField();"/>
							</td>	
						</tr>
       </table>
</div>
</body>
</html>