<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table id="TABLE_DocList3" class="tb_normal" width="100%">
	<tr class="td_normal_title" >
	    <td width="10%" rowspan="100" align="center"><bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.lang.robot"/></td>
		<td width="5%"><bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.lang.serialNumber"/></td>
		<td width="20%"><bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.lang.funcName"/></td>
		<td width="30%"><bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.lang.applicationExplain"/></td>
		<td width="10%"><bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.lang.integrationType"/></td>
		
		<td width="10%">
			<img
			src="${KMSS_Parameter_StylePath}icons/add.gif" alt="add"
				onclick="addRowEvent_Robot('TABLE_DocList3');" style="cursor: hand">
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1">
		</td>
		<td>
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdMainId" value="${ticCoreMappingMainForm.fdId}"/>
		<!--<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdRfcSettingId" value=""/>
		--><input type="hidden" name="fdRobotFunctionListForms[!{index}].fdInvokeType"  value="3"/><!--3表示机器人节点-->
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdOrder" value="!{index}"/><!--从0开始，注意不是显示的序号-->
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdRfcParamXml"/><!--函数参数xml格式文件-->
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdJspSegmen"  value=""/><!--jsp片段-->
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdTemplateId"  value="${param.templateId}"/>
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdExtendFormsView"  value=""/>
		<!--<input type="text" name="fdRobotFunctionListForms[!{index}].fdRfcSettingName" value="" readOnly class="inputread"  style="width:100%">
		
		--><input type="hidden" name="fdRobotFunctionListForms[!{index}].fdRefId" value=""/>
		<input type="text" name="fdRobotFunctionListForms[!{index}].fdRefName" value="" readOnly class="inputread"  style="width:100%">
		
		</td>
		<td><input type="text" name="fdRobotFunctionListForms[!{index}].fdFuncMark" value="" readOnly class="inputread"  style="width:100%"></td>
		
		<td>
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdIntegrationType" value="" readOnly class="inputread" style="width:100%">
		<input type="hidden" name="fdRobotFunctionListForms[!{index}].fdMapperJsp"/>
		<input type="text" name="fdRobotFunctionListForms[!{index}].fdIntegrationTypeShow" value="" readOnly class="inputread" style="width:100%">
		</td>
		<td>
			<img
			src="${KMSS_Parameter_StylePath}icons/edit.gif" alt="edit"
			onclick="editRobotFunction(this.parentNode.parentNode.rowIndex-1);" style="cursor: hand">
			<img src="${KMSS_Parameter_StylePath}icons/up.gif"
			alt="up" onclick="DocList_MoveRow(-1);switchRobotFdOrder(-1,this,'TABLE_DocList3');" style="cursor: hand">
			<img src="${KMSS_Parameter_StylePath}icons/down.gif" alt="down"
			onclick="DocList_MoveRow(1);switchRobotFdOrder(1,this,'TABLE_DocList3');" style="cursor: hand">
				<img
			src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del"
			onclick="DocList_DeleteRow();resetRobotFdOrder('TABLE_DocList3');" style="cursor: hand">
		</td>
	</tr>
	
	<!--内容行-->
	<c:forEach items="${ticCoreMappingMainForm.fdRobotFunctionListForms}" var="fdRobotFunctionForm" varStatus="vstatus">
	<tr KMSS_IsContentRow="1">
		<td>${vstatus.index+1}</td>
		<td>
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdId" value="${fdRobotFunctionForm.fdId}"/>
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdMainId" value="${fdRobotFunctionForm.fdMainId}"/>
		
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdRefId" value="${fdRobotFunctionForm.fdRefId }"/>
		
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdInvokeType"  value="${fdRobotFunctionForm.fdInvokeType}"/><!--3表示机器人节点-->
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdOrder" value="${fdRobotFunctionForm.fdOrder}"/><!--从0开始，注意不是显示的序号-->
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdRfcParamXml" value="${fdRobotFunctionForm.fdRfcParamXmlView}"/><!--函数参数xml格式文件-->
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdJspSegmen" value="${fdRobotFunctionForm.fdJspSegmenView}"/><!--jsp片段-->
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdTemplateId"  value="${fdRobotFunctionForm.fdTemplateId}"/>
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdExtendFormsView"  value="${fdRobotFunctionForm.fdExtendFormsView}"/>
		
		<input type="text" name="fdRobotFunctionListForms[${vstatus.index}].fdRefName" value="${fdRobotFunctionForm.fdRefName}" readOnly class="inputread"  style="width:100%">
		
		</td>
		
		<td><input name="fdRobotFunctionListForms[${vstatus.index}].fdFuncMark" value="${fdRobotFunctionForm.fdFuncMark}"readOnly class="inputread"  style="width:100%"></td>
		<td>
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdIntegrationType" value="${fdRobotFunctionForm.fdIntegrationType }"  readOnly class="inputread" style="width:100%">
		<input type="hidden" name="fdRobotFunctionListForms[${vstatus.index}].fdMapperJsp" readOnly value="${fdRobotFunctionForm.fdMapperJsp }"   class="inputread" style="width:100%"/>
		<input type="text" name="fdRobotFunctionListForms[${vstatus.index}].fdIntegrationTypeShow" readOnly value="${fdRobotFunctionForm.fdIntegrationTypeShow }"  class="inputread" style="width:100%">
		</td>
		<td>
			<img src="${KMSS_Parameter_StylePath}icons/edit.gif" alt="edit"
			onclick="editRobotFunction(this.parentNode.parentNode.rowIndex-1);" style="cursor: hand">
			<img src="${KMSS_Parameter_StylePath}icons/up.gif"
			alt="up" onclick="DocList_MoveRow(-1);switchRobotFdOrder(-1,this,'TABLE_DocList3');" style="cursor: hand">
			<img src="${KMSS_Parameter_StylePath}icons/down.gif" alt="down"
			onclick="DocList_MoveRow(1);switchRobotFdOrder(1,this,'TABLE_DocList3');" style="cursor: hand">
		    <img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del"
			onclick="DocList_DeleteRow();resetRobotFdOrder('TABLE_DocList3');" style="cursor: hand">
		</td>
	</tr>
	</c:forEach>
</table>
<script>

function addRowEvent_Robot(tbId){
	dialogObject['tbId'] = tbId;
	dialogObject['fdFuncForms'] = 'fdRobotFunctionListForms';
	var settingId=$("input[name='settingId']").val();
	if(!settingId){
		alert("<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.lang.noIntegrationType"/>");
		return ;
		}
	var data = new KMSSData();
	data.SendToBean("ticCoreMappingSettingService&settingId="+settingId,
			function(rtnData){
		if (rtnData.GetHashMapArray().length == 0)
			return;
		if(rtnData.GetHashMapArray()[0]["errMsg"]){
		   alert(rtnData.GetHashMapArray()[0]["errMsg"]);
		   return ;
		}
		//处理返回值 itype iname ikey idialogLink
		if(rtnData.GetHashMapArray()[0]["iJson"]);
			var rtnVal= rtnData.GetHashMapArray()[0]["iJson"];
			 var rtnJson= eval("("+rtnVal+")");
			var fieldValues={};
			var pop_i_type=rtnJson[0]["itype"];
			var pop_i_name=rtnJson[0]["iname"];
			var pop_i_link=rtnJson[0]["idialogLink"];
			if(rtnJson.length>1){
				showTypeDialog_Robot(rtnJson);
			}else{
				fieldValues["fdRobotFunctionListForms[!{index}].fdIntegrationType"]=pop_i_type;
				fieldValues["fdRobotFunctionListForms[!{index}].fdIntegrationTypeShow"]=pop_i_name; 
				fieldValues["fdRobotFunctionListForms[!{index}].fdMapperJsp"]=pop_i_link;
				var n_row=DocList_AddRow(tbId,null,fieldValues);
			}
			
		});
}

function addRowEvent_Robot_callback(itype){
	var rtnJson = dialogObject["rtnJson"];
	var pop_i_type=rtnJson[0]["itype"];
	var pop_i_name=rtnJson[0]["iname"];
	var pop_i_link=rtnJson[0]["idialogLink"];
	if(itype){
		var s_rtn=findRtnJson_Robot(rtnJson,itype);
		pop_i_type=s_rtn["itype"];
		pop_i_name=s_rtn["iname"];
		pop_i_link=s_rtn["idialogLink"];
	}
	var fieldValues={};
	
	ffieldValues["fdRobotFunctionListForms[!{index}].fdIntegrationType"]=pop_i_type;
	fieldValues["fdRobotFunctionListForms[!{index}].fdIntegrationTypeShow"]=pop_i_name; 
	fieldValues["fdRobotFunctionListForms[!{index}].fdMapperJsp"]=pop_i_link;
	var n_row=DocList_AddRow(dialogObject['tbId'] ,null,fieldValues);
	
}

function showTypeDialog_Robot(rtnJson){
	var title="${KMSS_Parameter_ContextPath}";
	var link="tic/core/resource/jsp/simpleType_dialog.jsp";

	var width = 500;//500==null?640:width;
	var height =220; //height==null?820:height;
	var left = (screen.width-width)/2;
	var top = (screen.height-height)/2;
	var winStyle = "resizable:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;

	//var rtnData={};
	dialogObject["rtnJson"]=rtnJson;
	dialogObject["stype"]="";
	//window.showModalDialog(title+link, rtnData, winStyle);
	winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=500px,height=220px,screenX=10px,screenY=10px,top=300px,left=400px";
	window.open(title+link,"",winStyle);
	
	//return rtnData["stype"];
}

function findRtnJson_Robot(rtnJson ,type){
	for(var i=0,len=rtnJson.length;i<len;i++){
		if(rtnJson[i]["itype"]==type){
             return rtnJson[i];
			}
		}
	return null;
	}



//编辑函数信息
function editRobotFunction(index){
	dialogObject={"fdJspSegmen":$('input[name="fdRobotFunctionListForms['+index+'].fdJspSegmen"]').val(),
			"fdFuncMark":$('input[name="fdRobotFunctionListForms['+index+'].fdFuncMark"]').val(),

			"fdId":$('input[name="fdRobotFunctionListForms['+index+'].fdId"]').val(),
			//"fdRfcSettingId":$('input[name="fdRobotFunctionListForms['+index+'].fdRfcSettingId"]').val(),

			"fdRefName":$('input[name="fdRobotFunctionListForms['+index+'].fdRefName"]').val(),
			"fdRefId":$('input[name="fdRobotFunctionListForms['+index+'].fdRefId"]').val(),
			"mappingFuncId":$('input[name="fdRobotFunctionListForms['+index+'].fdId"]').val(),
			"fdInvokeType":$('input[name="fdRobotFunctionListForms['+index+'].fdInvokeType"]').val(),
			"fdRfcParamXml":$('input[name="fdRobotFunctionListForms['+index+'].fdRfcParamXml"]').val(),//函数参数xml格式文件
			"fdExtendFormsView":$('input[name="fdRobotFunctionListForms['+index+'].fdExtendFormsView"]').val()
			};
	dialogObject["index"] = index;
	dialogObject['fdFuncForms'] = 'fdRobotFunctionListForms';
	var url=$('input[name="fdRobotFunctionListForms['+index+'].fdMapperJsp"]').val();
	
	var title="${KMSS_Parameter_ContextPath}";
	if(url){
		
		//window.showModalDialog(title+url+"?fdInvokeType=3&mainModelName=${param.mainModelName}&fdFormFileName=${fdFormFileName}", funcObject,"dialogWidth=900px;dialogHeight=600px");
		var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=900px,height=600px,screenX=10px,screenY=10px,top=100px,left=200px";
		window.open(title+url+"?fdInvokeType=3&mainModelName=${param.mainModelName}&fdFormFileName=${fdFormFileName}","",winStyle);
		//重置字段值
	 	//resetRobotField(funcObject,index);
	}
	else{
 		alert("<bean:message bundle="tic-core-mapping" key="ticCoreMappingFunc.lang.noAddressConn"/>");
 		return ;
	}
}


function editRobotFunction_callback(){
	//重置字段值
	resetFlowRejectField(dialogObject,dialogObject["index"]);
}

//编辑函数后重置字段值
function resetRobotField(funcObject,index){
	$('input[name="fdRobotFunctionListForms['+index+'].fdJspSegmen"]').val(funcObject.fdJspSegmen);
	$('input[name="fdRobotFunctionListForms['+index+'].fdFuncMark"]').val(funcObject.fdFuncMark);

	//$('input[name="fdRobotFunctionListForms['+index+'].fdRfcSettingName"]').val(funcObject.fdRfcSettingName);
	//$('input[name="fdRobotFunctionListForms['+index+'].fdRfcSettingId"]').val(funcObject.fdRfcSettingId);

	$('input[name="fdRobotFunctionListForms['+index+'].fdRefName"]').val(funcObject.fdRefName);
	$('input[name="fdRobotFunctionListForms['+index+'].fdRefId"]').val(funcObject.fdRefId);
	
	$('input[name="fdRobotFunctionListForms['+index+'].fdRfcParamXml"]').val(funcObject.fdRfcParamXml);
	$('input[name="fdRobotFunctionListForms['+index+'].fdExtendFormsView"]').val(funcObject.fdExtendFormsView);
}
//当上移或下移时
function switchRobotFdOrder(position,_this,tableId){
	var tbInfo = DocList_TableInfo[tableId];
	//此行交换后的行index,注意这里去到的是交换后的index
	var rowIndex=_this.parentNode.parentNode.rowIndex;
	if((position==-1&&rowIndex<tbInfo.firstIndex-1)||(position==1&&rowIndex>tbInfo.lastIndex-1))return;
	//改变当前移动行的fdOrder
	$('input[name="fdRobotFunctionListForms['+(rowIndex-1)+'].fdOrder"]').val(rowIndex-1);
	//改变交换行的fdOrder
	$('input[name="fdRobotFunctionListForms['+(rowIndex-position-1)+'].fdOrder"]').val(rowIndex-position-1);
}

//用于删除时重设fdOrder
function resetRobotFdOrder(tableId){
	var tbInfo = DocList_TableInfo[tableId];
	for(var i=0;i<tbInfo.lastIndex-1;i++){
		$('input[name="fdRobotFunctionListForms['+(i)+'].fdOrder"]').val(i);
	}
}
</script>
