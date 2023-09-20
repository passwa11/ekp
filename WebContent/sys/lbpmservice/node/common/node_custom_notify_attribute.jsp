<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTagNew"%>
<%
	pageContext.setAttribute("isLangSuportEnabled", MultiLangTextGroupTagNew.isLangSuportEnabled());
%>
<tr LKS_LabelName="<kmss:message key="lbpmProcess.customNotify.customNotify" bundle="sys-lbpmservice-support" />">
	<td>
		<table id="customNotifyList" class="tb_normal" width="100%">
			<tr>
				<td width="30px"><kmss:message key="lbpmProcess.customNotify.serialNumber" bundle="sys-lbpmservice-support" /></td>
				<td width="120px"><kmss:message key="lbpmProcess.customNotify.type" bundle="sys-lbpmservice-support" /></td>
				<td width="500px"><kmss:message key="lbpmProcess.customNotify.messageDefinition" bundle="sys-lbpmservice-support" /></td>
				<td width="100px" align="center">
					<a href="javascript:void(0)" onclick="DocList_AddRow();"><img src="${KMSS_Parameter_StylePath}icons/add.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationAdd" bundle="sys-lbpmservice" />"></a>
				</td>
			</tr>
			<tr KMSS_IsReferRow="1" style="display:none">
				<td KMSS_IsRowIndex="1">
					{1}
				</td>
				<td>
					<xform:select property="notifyType" subject="${lfn:message('sys-lbpmservice-support:lbpmProcess.customNotify.type')}" style="width:100px;" showStatus="edit" validators="required" required="true" onValueChange="LbpmSetNotifyCustomMsg(this);">
						<xform:customizeDataSource className="com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmCustomNotifyDataSource"></xform:customizeDataSource>
					</xform:select>
				</td>
				<td>
					<%-- <input name="notify_content" class="inputsgl" style="width:65%"><span class=txtstrong>*</span>
					<xlang:lbpmlang property="notify_content" style="width:65%" langs=""/> --%>
					<c:if test="${!isLangSuportEnabled }">
						<input name="notify_content" class="inputsgl" style="width:65%"><span class=txtstrong>*</span>
					</c:if>
					<c:if test="${isLangSuportEnabled }">
						<xlang:lbpmlangNew validators="required" property="notify_content" style="width:60%" langs="" className="inputsgl"/>
					</c:if>
					<input type="button" value="<kmss:message key="lbpmProcess.customNotify.custom" bundle="sys-lbpmservice-support" />" class="btnopt notifyCustomBtn" onclick="LbpmToNotifyCustom(this);" />
					<br>
					<span name="notify_msg" style="width:90%;word-break:break-all;"></span>
				</td>
				<td>
					<a href="javascript:void(0)" onclick="DocList_DeleteRow();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationDelete" bundle="sys-lbpmservice" />"></a>
					<a href="javascript:void(0)" onclick="DocList_MoveRow(-1);"><img src="${KMSS_Parameter_StylePath}icons/up.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveUp" bundle="sys-lbpmservice" />"></a>
					<a href="javascript:void(0)" onclick="DocList_MoveRow(1);"><img src="${KMSS_Parameter_StylePath}icons/down.gif" title="<kmss:message key="FlowChartObject.Lang.Operation.operationMoveDown" bundle="sys-lbpmservice" />"></a>
				</td>
			</tr>
		</table>
		<input type="hidden" name="ext_customNotify" />
	</td>
</tr>
<script>
DocList_Info.push("customNotifyList");

//校验事件
AttributeObject.CheckDataFuns.push(function(){
	var customNotifyList = document.getElementById("customNotifyList");
	for(var i = 1;i<customNotifyList.rows.length;i++){
		var row = customNotifyList.rows[i];
		var notifyType = $(row).find("select[name='notifyType']").val();
		var notify_content = $(row).find("input[name='notify_content']").val();
		if(!notifyType){
			var notifyTypeMsg = "<kmss:message key='lbpmProcess.customNotify.notifyTypeMsg' bundle='sys-lbpmservice-support' />";
			alert(notifyTypeMsg.replace("{0}",i));
			return false;
		}
		if(!notify_content){
			var contentMsg = "<kmss:message key='lbpmProcess.customNotify.contentMsg' bundle='sys-lbpmservice-support' />";
			alert(contentMsg.replace("{0}",i));
			return false;
		}
	}
	return true;
});

//提交事件(因写入XML也在提交事件中，故这里需要将提交事件放到最前面)
AttributeObject.SubmitFuns.unshift(function(){
	var customNotifyList = document.getElementById("customNotifyList");
	var content = {};
	for(var i = 1;i<customNotifyList.rows.length;i++){
		var row = customNotifyList.rows[i];
		var notifyType = $(row).find("select[name='notifyType']").val();
		var notify_content = $(row).find("input[name='notify_content']").val();
		var notify_content_value = $(row).find("input[name='notify_content_value']").val();
		var langVal = {};
		if(!langVal["default"]){
			langVal["default"] = {};
		}
		langVal["default"]["id"] = notify_content_value;
		langVal["default"]["name"] = notify_content;
		if(isLangSuportEnabled){
			for(var j=0;j<langJson["support"].length;j++){
				var lang = langJson["support"][j]["value"];
				if(!langVal[lang]){
					langVal[lang] = {};
				}
				langVal[lang]["id"] = $(row).find("input[name='notify_content_"+lang+"_value']").val();
				langVal[lang]["name"] = $(row).find("input[name='notify_content_"+lang+"']").val();
			}	
		}
		content[notifyType] = langVal;
	}
	$("input[name='ext_customNotify']").val(JSON.stringify(content));
});

var LbpmCustomNotifys = null;

AttributeObject.Init.AllModeFuns.push(function(){
	var extAttributes = AttributeObject.NodeData.extAttributes;
	if(extAttributes){
		for(var i = 0;i<extAttributes.length;i++){
			if(extAttributes[i].name == "customNotify"){
				LbpmCustomNotifys = JSON.parse(extAttributes[i].value);
				for(var key in LbpmCustomNotifys){
					var fieldValues = new Object();
					fieldValues["notifyType"]=key;
					fieldValues["notify_content"]=LbpmCustomNotifys[key]["default"]["name"]||"";
					if(isLangSuportEnabled){
						for(var h=0;h<langJson["support"].length;h++){
							var lang = langJson["support"][h]["value"];
							if(LbpmCustomNotifys[key][lang])
								fieldValues["notify_content_"+lang] = LbpmCustomNotifys[key][lang]["name"]||"";
						}	
					}
					DocList_AddRow("customNotifyList",null,fieldValues);
				}
				break;
			}
		}
	}
	//查看页面隐藏自定义按钮
	if(!AttributeObject.isEdit()){
		$(".notifyCustomBtn").hide();
		//查看页面需要隐藏按钮，并且显示所有的语言
		$(".multiLang.notify_content").children().find(".multi_lang_icon").hide();
		$(".multiLang.notify_content").children().find(".lang_item").show();
	}
});

//消息类型切换事件
function LbpmSetNotifyCustomMsg(dom){
	if(dom.value){
		$(dom).closest("tr").find("span[name='notify_msg']").text("<kmss:message key='lbpmProcess.customNotify.default' bundle='sys-lbpmservice-support' />"+Data_GetResourceString(dom.value+".subject"));
	}else{
		$(dom).closest("tr").find("span[name='notify_msg']").text("");
	}
	$(dom).closest("tr").find("input[name*='notify_content']").val("");
	if(isLangSuportEnabled){
		for(var j=0;j<langJson["support"].length;j++){
			var lang = langJson["support"][j]["value"];
			$(dom).closest("tr").find("input[name*='notify_content_"+lang+"']").val("")
		}	
	}
}

//因span标签无法在明细表新增行时赋予初始值，故监听明细表新增行事件修改span的text
$(document).on("table-add",function(event,source){
	if($(source).closest("table").attr("id") == "customNotifyList"){
		var messKey = $(source).find("select[name='notifyType']").val();
		if(messKey){
			$(source).find("span[name='notify_msg']").text("<kmss:message key='lbpmProcess.customNotify.default' bundle='sys-lbpmservice-support' />"+Data_GetResourceString(messKey+".subject"));
		}
		$(source).find("input[name*='notify_content']").each(function(){
			// 添加隐藏值，并使文本框只读
			$(this).prop("readOnly","false");
			$(this).closest("td").append("<input name='"+$(this).attr("name")+"_value' type='hidden' />");
		});
		if(messKey && LbpmCustomNotifys && LbpmCustomNotifys[messKey]){
			for(var key in LbpmCustomNotifys[messKey]){
				// 初始化隐藏域的值
				if(key=="default"){
					$(source).find("input[name='notify_content_value']").val(LbpmCustomNotifys[messKey][key]['id']);
				}else{
					$(source).find("input[name='notify_content_"+key+"_value']").val(LbpmCustomNotifys[messKey][key]['id']);
				}
			}
		}
	}
});

//打开消息自定义窗口
function LbpmToNotifyCustom(dom){
	var tr = $(dom).closest("tr");
	var dialog = new KMSSDialog();
	var funcBean = "sysFormulaFuncTree";
	var funcInfo = [];
	var funcs = new KMSSData().AddBeanData(funcBean).GetHashMapArray();
	for(var i = 0;i<funcs.length;i++){
		if(funcs[i].text && funcs[i].text.indexOf("${lfn:message('sys-lbpmservice-support:lbpmProcess.customNotify.func')}")==0){
			funcInfo.push(funcs[i]);
		}
	}
	var messKey = tr.find("select[name='notifyType']").val();
	var onloadInfo = {};
	if(isLangSuportEnabled){
		for(var j=0;j<langJson["support"].length;j++){
			var lang = langJson["support"][j]["value"];
			if(!onloadInfo[lang]){
				onloadInfo[lang] = {};
			}
			onloadInfo[lang]["name"] = tr.find("input[name='notify_content_"+lang+"']").val();
			onloadInfo[lang]["id"] = tr.find("input[name='notify_content_"+lang+"_value']").val();
		}	
	}
	var valueInfo = {};
	valueInfo["id"] = tr.find("input[name='notify_content_value']").val();
	valueInfo["name"] = tr.find("input[name='notify_content']").val();
	dialog.formulaParameter = {
			varInfo: FlowChartObject.FormFieldList, 
			returnType: "String",
			isLangSuportEnabled:isLangSuportEnabled,
			langJson:langJson,
			valueInfo : valueInfo,
			onloadInfo : onloadInfo,
			funcInfo : funcInfo,
			messKey : messKey,
			model: FlowChartObject.ModelName};
	//dialog.BindingField(idField, nameField);
	//因dialog只能帮定一个idFiled和一个nameField,故开启多语言时需手动设置多语言返回值
	dialog.SetAfterShow(function(rtn){
		if(rtn && rtn.data){
			tr.find("input[name='notify_content']").val(rtn.data[0]["name"]);
			tr.find("input[name='notify_content_value']").val(rtn.data[0]["id"]);
			if(isLangSuportEnabled){
				for(var j=0;j<langJson["support"].length;j++){
					var lang = langJson["support"][j]["value"];
					if(rtn.data[0]["id_"+lang] !== undefined  && rtn.data[0]["name_"+lang] !== undefined ){
						tr.find("input[name='notify_content_"+lang+"']").val(rtn.data[0]["name_"+lang]);
						tr.find("input[name='notify_content_"+lang+"_value']").val(rtn.data[0]["id_"+lang]);
					}
				}	
			}
		}
	});
	dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/node/common/node_custom_notify_dialog_edit.jsp";
	dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
}
</script>