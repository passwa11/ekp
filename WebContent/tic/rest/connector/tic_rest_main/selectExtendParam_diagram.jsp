<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
<script type="text/javascript">
Com_IncludeFile("jquery.js|data.js|json2.js");
Com_IncludeFile("doclist.js|dialog.js", null, "js");
Com_IncludeFile("json2.js");
</script>
</template:replace>
<template:replace name="content">
		<p class="txttitle">${lfn:message('tic-rest-connector:ticRestMain.selectExtendParam.edit.note')}'${param.title}'${lfn:message('tic-rest-connector:ticRestMain.selectExtendParam.define')}</p>

		<table id="TABLE_DocList" class="tb_normal" width=95% >
			<tr>
				<td width="32%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/></td>
				<td width="32%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/></td>
				<td width="24%" align="center" class="td_normal_title"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.type"/></td>
				<td width="12%" align="center" class="td_normal_title">
					<img src="${KMSS_Parameter_ContextPath}resource/style/default/icons/add.gif" alt="<bean:message key="doclist.add"/>" onclick="DocList_AddRow();" style="cursor:pointer">
				</td>
			</tr>
			<!--基准行-->
			<tr KMSS_IsReferRow="1" style="display:none">
				<td width="32%">
					<input validate="required" class="inputsgl" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.name"/>" style="width:75%" name="param[!{index}].name">
					<span class="txtstrong">*</span>
				</td>
				<td width="32%">
					<input validate="required" class="inputsgl" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/>" style="width:75%" name="param[!{index}].title">
					<span class="txtstrong">*</span>
				</td>
				<td width="24%">
					<input type="hidden" name="param[!{index}].children" value="">
					<select validate="required" onchange="doParamsTypeChange(this,'param');" subject="<bean:message bundle="tic-rest-connector" key="ticRestMain.fdReqBizParam.set.title"/>" style="width:72%" name="param[!{index}].type">
						<option value="string">${lfn:message('tic-core-common:ticCoreCommon.baseType.string')}</option>
						<option value="object">${lfn:message('tic-core-common:ticCoreCommon.baseType.object')}</option>
						<option value="array">${lfn:message('tic-core-common:ticCoreCommon.baseType.array')}</option>
						<option value="int">${lfn:message('tic-core-common:ticCoreCommon.baseType.int')}</option>
						<option value="long">${lfn:message('tic-core-common:ticCoreCommon.baseType.long')}</option>
						<option value="double">${lfn:message('tic-core-common:ticCoreCommon.baseType.double')}</option>
						<option value="boolean">${lfn:message('tic-core-common:ticCoreCommon.baseType.boolean')}</option>
					</select>
					<span class="txtstrong">*</span>
				</td>
				<td width="12%">
					<center>
							<img class="editPd" style="display:none;" src="${KMSS_Parameter_ContextPath}resource/style/default/icons/edit.gif" title="${lfn:message('tic-rest-connector:ticRestMain.selectExtendParam.edit.note1')}" onclick="doExendParamsDefine('param');" style="cursor:pointer">
							<img src="${KMSS_Parameter_ContextPath}resource/style/default/icons/delete.gif" alt="<bean:message key="doclist.delete"/>" onclick="DocList_DeleteRow();" style="cursor:pointer">
					</center>
				</td>
			</tr>
		</table>

<center>
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;padding-bottom:5px;">
			<ui:button style="width:80px" onclick="doOK();" text="${lfn:message('button.ok') }" />
			<ui:button style="width:80px" onclick="Com_CloseWindow();" text="${lfn:message('button.close') }" />
		</div>
</center>

<script>

	function doParamsTypeChange(curr,type){
		var optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var currRowIndex = Com_ArrayGetIndex(optTB.rows, optTR); 
		var index = currRowIndex-1;
		if(!(curr.value=="object" || curr.value=="array")){
			$($(".editPd")[index]).hide();
			return ;
		}
		$($(".editPd")[index]).show();
		_selectExendParamsDefine(index,type);
	}
	
	function _selectExendParamsDefine(index,type){
		var cacheParamDefine = parent.document.getElementsByName("cacheParamDefine")[0];
		var title = document.getElementsByName(type+"["+index+"].title")[0].value;
		var pn = type+"["+index+"].children";
		cacheParamDefine.value = document.getElementsByName(pn)[0].value;
		seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
			dialog.iframe('/tic/rest/connector/tic_rest_main/selectExtendParam_diagram.jsp?title='+title+'&type='+type+'&index='+index,"编辑对象或数组类型属性'"+title+"'定义",function(value){
			if(value!=null){
					var pn = type+"["+index+"].children";
					var extps = document.getElementsByName(pn)[0];
					extps.value = JSON.stringify(value);
					cacheParamDefine.value="";
				}
			 },{height:'400',width:'650'});
		});
	}

	function doExendParamsDefine(type){
		var optTR = DocListFunc_GetParentByTagName("TR");
		var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
		var currRowIndex = Com_ArrayGetIndex(optTB.rows, optTR); 
		var index = currRowIndex-1;
		_selectExendParamsDefine(index,type)
	}

Com_AddEventListener(window, "load", function(){
		var pn = "${param.type}[${param.index}].children";
		
		var extps = parent.document.getElementsByName(pn)[0];
		if(typeof extps =="undefined"){
			extps =  parent.document.getElementsByName("cacheParamDefine")[0];
		}

		if(extps.value==""){
			return ;
		}
		var arr = JSON.parse(extps.value);
		var fields = [];
		for(var i=0;i<arr.length;i++){
			var fieldValues = {
				"param[!{index}].name":arr[i]["name"],
				"param[!{index}].title":arr[i]["title"],
				"param[!{index}].type":arr[i]["type"],
				"param[!{index}].children":arr[i]["children"]
			};
			DocList_AddRow("TABLE_DocList", null, fieldValues);
			
			var tdh = document.getElementById("TABLE_DocList");
			var len = tdh.rows.length;
			var index = len-2;
			if(!(arr[i]["type"]=="object" || arr[i]["type"]=="array")){
				$($(".editPd")[index]).hide();
			}else{
				$($(".editPd")[index]).show();
			}
		}
});

var ret=[];
function doOK(){
	var tdh = document.getElementById("TABLE_DocList");
	var len = tdh.rows.length;
	for(i=0;i<len-1;i++){
		var param={
			name:document.getElementsByName("param["+i+"].name")[0].value,
			title:document.getElementsByName("param["+i+"].title")[0].value,
			type:document.getElementsByName("param["+i+"].type")[0].value,
			children:document.getElementsByName("param["+i+"].children")[0].value
		}
		ret.push(param);
	}
	window.$dialog.hide(ret);
}

</script>
</template:replace>
</template:include>