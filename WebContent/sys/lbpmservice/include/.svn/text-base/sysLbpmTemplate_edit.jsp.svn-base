<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil" %>
<%@ include file="/resource/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<script>
Com_IncludeFile("jquery.js|dialog.js|formula.js");

</script>
<c:set var="lbpmTemplateForm" value="${requestScope[param.formName].sysWfTemplateForms[param.fdKey]}" />
<c:set var="lbpmTemplateFormPrefix" value="sysWfTemplateForms.${param.fdKey}." />
<c:set var="lbpmTemplate_ModelName" value="${requestScope[param.formName].modelClass.name}" />
<c:set var="lbpmTemplate_Key" value="${param.fdKey}" />
<%
	pageContext.setAttribute("lbpmTemplate_MainModelName",
			LbpmTemplateUtil.getMainModelName(
					(String)pageContext.getAttribute("lbpmTemplate_ModelName"),
					(String)pageContext.getAttribute("lbpmTemplate_Key")));
				
%>
<tr id="WF_TR_ID_${lbpmTemplate_Key}" style="display:none" LKS_LabelId="WF_TR_${lbpmTemplate_Key}"
	LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-lbpmservice-support:lbpmTemplate.tab.label'}" />">
	<td>
		<table class="tb_normal" width="100%" id="TB_LbpmTemplate_${lbpmTemplate_Key}">
			<tr>
				<td width="15%" class="td_normal_title" valign="top">
					<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType" />
				</td>
				<td width="85%">
					<xform:radio property="${lbpmTemplateFormPrefix}fdType"
						onValueChange="LBPM_Template_TypeChg(this.value, '${lbpmTemplate_Key}', '${lbpmTemplateFormPrefix}', true);" showStatus="edit">
						<xform:enumsDataSource enumsType="lbpmTemplate_fdType"></xform:enumsDataSource>
					</xform:radio>
					<a href="javascript:void(0)" id="A_LbpmTemplate_${lbpmTemplate_Key}" class="com_btn_link"
						onclick="LBPM_Template_Select_${param.fdKey}('${lbpmTemplate_Key}', '${lbpmTemplateFormPrefix}', true);">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.define.fromTemplate" />
					</a>
					&nbsp;&nbsp;
					<a href="javascript:void(0)" id="A_Flowchart_${lbpmTemplate_Key}" class="com_btn_link"
						onclick="LBPM_Template_import_${param.fdKey}('${lbpmTemplate_Key}', '${lbpmTemplateFormPrefix}');">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.define.fromExcel" />
					</a>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-lbpmservice-support" key="table.lbpmTemplate"/>
				</td>
				<td>
					<xform:dialog propertyId="${lbpmTemplateFormPrefix}fdCommonId" propertyName="${lbpmTemplateFormPrefix}fdCommonName" showStatus="edit" style="width:97%">
						LBPM_Template_Select_${param.fdKey}('${lbpmTemplate_Key}', '${lbpmTemplateFormPrefix}');
					</xform:dialog>
				</td>
			</tr>
			<%@ include file="/sys/lbpmservice/support/lbpm_template/lbpmTemplate_sub_edit.jsp"%>
		</table>
	</td>
</tr>
<script>
if(window.LBPM_Template_Type == null) {
	LBPM_Template_Type = new Array();
}
LBPM_Template_Type["${lbpmTemplate_Key}"] = "${lbpmTemplateForm.fdType}";
// 切换引用方式
function LBPM_Template_TypeChg(value, key, prefix, isClick) {
	var tbObj = $("#TB_LbpmTemplate_"+key);
	if(value == "3") {
		// 自定义
		$("#A_LbpmTemplate_"+key).show();
		$("#A_Flowchart_"+key).show();
		tbObj.find("tr:eq(1)").hide();
		tbObj.find("tr:gt(1)").show();
		$("[id='"+prefix+"WF_IFrame']").show();
		$("[id='"+prefix+"WF_IFrame_Default']").hide();
		$("#freeFlowOptionSettingRow",tbObj).hide();
	} else if (value == "4") {
		// 自由流
		$("#A_LbpmTemplate_"+key).hide();
		$("#A_Flowchart_"+key).hide();
		tbObj.find("tr:eq(1)").hide();
		tbObj.find("tr:gt(1)").show();
		//$("#notifyOptionsRow",tbObj).hide();
		$("#processPopedomRow",tbObj).hide();
		$("#auditnoteRow",tbObj).hide();
		$("#processOptionsRow",tbObj).hide();
		$("#optionSettingRow",tbObj).hide();
		$("#freeFlowOptionSettingRow",tbObj).show();
		//$("#flowContentRow",tbObj).hide();
		$("[id='"+prefix+"WF_IFrame']").hide();
		$("[id='"+prefix+"WF_IFrame_Default']").show();
	} else {
		$("#A_LbpmTemplate_"+key).hide();
		$("#A_Flowchart_"+key).hide();
		if(value == "" || value =="1") {
			tbObj.find("tr:eq(1)").hide();
		} else {
			if(isClick) {
				$("input[name='"+prefix+"fdCommonId']").attr("value", "");
				$("input[name='"+prefix+"fdCommonName']").val("");
				//$("input[name='"+prefix+"fdCommonName']").attr("value", "");
			}
			tbObj.find("tr:eq(1)").show();
		}
		tbObj.find("tr:gt(1)").hide();
	}
}
Com_AddEventListener(window, "load", function() {
	var key = "${lbpmTemplate_Key}", prefix = "${lbpmTemplateFormPrefix}";
	LBPM_Template_TypeChg("${lbpmTemplateForm.fdType}", key, prefix, false);
	// 添加标签切换事件
	var table = document.getElementById("WF_TR_ID_"+key);
	while((table != null) && (table.tagName.toLowerCase() != "table")){
		table = table.parentNode;
	}
	if(table != null && window.Doc_AddLabelSwitchEvent){
		Doc_AddLabelSwitchEvent(table, "LBPM_Template_OnLabelSwitch_"+key);
	}
});
//选择模板
function LBPM_Template_Select_${param.fdKey}(key, prefix, callback) {
	var idField = null, nameField = null, action = null;
	if(callback) {
		action = function(rtnVal) {
			if(rtnVal == null) {
				return;
			}
			var data = new KMSSData();
			data.AddBeanData('lbpmTemplateService&fdId='+rtnVal.data[0].id);
			data.PutToField("fdFlowContent", prefix+"fdFlowContent");
			var iframe = document.getElementById(prefix+"WF_IFrame").contentWindow;
			//chrome无法刷新iframe?
			setTimeout(function(){
				iframe.location.reload();
			},0);
			LBPM_Template_LoadProcessData(key, prefix);
			setTimeout(LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key},500);
		};
	} else {
		idField = prefix+'fdCommonId';
		nameField = prefix+'fdCommonName';
	}
	var typeVe = $("input[name='"+prefix+"fdType']:checked").val();
	/*Dialog_Tree(false, idField, nameField, null,
		'lbpmTemplateService&fdModelName=${lbpmTemplate_ModelName}&fdKey='+key+'&fdType='+typeVe,
		'<bean:message bundle="sys-lbpmservice-support" key="table.lbpmTemplate.common" />',
		null, action, null, null, true);*/
	var dialog = new KMSSDialog(false);
	dialog.BindingField(idField, nameField, null, null);
	dialog.SetAfterShow(action);
	dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_template_new/lbpmTemplate_select.jsp?fdModelName=${lbpmTemplate_ModelName}&fdKey=${JsParam.fdKey}&fdType="+typeVe;
	dialog.Show(710, 550);
}

function LBPM_Template_import_${param.fdKey}(key, prefix) {
	var method = Com_GetUrlParameter(window.top.location.href,'method');
	var url = "sys/lbpmservice/support/lbpm_flowchartimport/sysflowchartImportDialog.jsp?method="+method+"&fdKey=${lbpmTemplate_Key}";
	var height = document.documentElement.clientHeight * 0.5;
	var width = document.documentElement.clientWidth * 0.7;
	if(typeof(seajs) != 'undefined'){
		seajs.use(['lui/dialog'], function(dialog) {
			var dialog = dialog.iframe("/"+url,'<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdType.excel" />',null,{width:width,height : height,close:false});
			var prefix = "${lbpmTemplateFormPrefix}";
			dialog.flowChartXml = $("textarea[name='"+prefix+"fdFlowContent']").val();
		});	
	}else{
		var dialog = new KMSSDialog(false);
		dialog.URL = Com_Parameter.ContextPath + url;
		dialog.Parameters = {
			flowChartXml : $("textarea[name='"+prefix+"fdFlowContent']").val()
		};
		dialog.SetAfterShow(function(rtn) {
			if(rtn && rtn.data && rtn.data[0]){
				LBPM_Template_setFlowChart_${JsParam.fdKey}(rtn.data[0]);
			}
		});
		dialog.Show(width, height);
	}
}

function LBPM_Template_setFlowChart_${JsParam.fdKey}(process){
	var prefix = "${lbpmTemplateFormPrefix}";
	$("textarea[name='"+prefix+"fdFlowContent']").val(process);
	var iframe = document.getElementById(prefix+"WF_IFrame").contentWindow;
	iframe.location.reload();
	LBPM_Template_LoadProcessData('${lbpmTemplate_Key}', prefix);
	setTimeout(LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key},500);
}

//提交校验
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
	var prefix = "${lbpmTemplateFormPrefix}";
	var typeVe = $("input[name='"+prefix+"fdType']:checked").val();
	if(typeVe == "2") {
		// 引用其他模板
		if($("input[name='"+prefix+"fdCommonId']").val() == "") {
			seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
				dialog.confirm('<bean:message bundle="sys-lbpmservice-support" key="lbpmTemplate.fdCommon.validate.isNull" />', function(flag) {
	 	 	 		},
					null,
			        [{
						name : "${lfn:message('button.close')}",
						value : false,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					}]
				);
			});
		 return false;
		}
	} else {
		$("input[name='"+prefix+"fdCommonId']").attr("value", "");
		$("input[name='"+prefix+"fdCommonName']").attr("value", "");
	}
	if(LBPM_Template_Type["${lbpmTemplate_Key}"] != typeVe) {
		// 引用方式发生改变，标记流程定义需要修改
		$("input[name='"+prefix+"fdIsModified']").attr("value", "true");
	}
	return true;
}

//标签切换时加载公式信息
function LBPM_Template_OnLabelSwitch_${lbpmTemplate_Key}(tableName, index) {
	var trs = document.getElementById(tableName).rows;
	if(trs[index].id!="WF_TR_ID_${lbpmTemplate_Key}")
		return;
	LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key}();
}

function LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key}(){
	var iframe = document.getElementById("${lbpmTemplateFormPrefix}WF_IFrame").contentWindow;
	var iframeDefault = document.getElementById("${lbpmTemplateFormPrefix}WF_IFrame_Default").contentWindow;
	if(iframe && iframe.FlowChartObject && iframeDefault && iframeDefault.FlowChartObject){
		var LBPM_Template_FormFieldList = null;
		var LBPM_Template_SubFormInfoList = null;
		var LBPM_Template_SubPrintInfoList = null;
		var LBPM_Template_xform_mode = null;
		var LBPM_Template_RuleTemplate = null;
		var LBPM_Template_Rule_Key = null;
		if(window.XForm_getXFormDesignerObj_${lbpmTemplate_Key}) {
			LBPM_Template_FormFieldList = XForm_getXFormDesignerObj_${lbpmTemplate_Key}();
		} else {
			LBPM_Template_FormFieldList = Formula_GetVarInfoByModelName("${lbpmTemplate_MainModelName}");
		}
		if(window.XForm_getSubFormInfo_${lbpmTemplate_Key}){
			LBPM_Template_SubFormInfoList = XForm_getSubFormInfo_${lbpmTemplate_Key}();
		}
		if(window.Print_getSubPrintInfo_${lbpmTemplate_Key}){
			LBPM_Template_SubPrintInfoList = Print_getSubPrintInfo_${lbpmTemplate_Key}();
		}
		if(window.Form_getModeValue){
			LBPM_Template_xform_mode = Form_getModeValue("${lbpmTemplate_Key}");
		}
		if(window.sysRuleTemplate){
			LBPM_Template_RuleTemplate = window.sysRuleTemplate;
			LBPM_Template_Rule_Key = "${lbpmTemplate_Key}";
		}
		//获取表单设计器对象和主文档对象
		iframe.FlowChartObject.Designer = iframeDefault.FlowChartObject.Designer = null;
		iframe.FlowChartObject.modelName = 
			iframe.FlowChartObject["MODEL_NAME"];
		iframeDefault.FlowChartObject.modelName = 
			iframeDefault.FlowChartObject["MODEL_NAME"];
		if (window.XForm_getXFormDesignerInstance_${lbpmTemplate_Key}){
			var isFormTemplateMode = (LBPM_Template_xform_mode === "<%=XFormConstant.TEMPLATE_OTHER %>"
										|| LBPM_Template_xform_mode === "<%=XFormConstant.TEMPLATE_DEFINE %>"
											|| LBPM_Template_xform_mode === "<%=XFormConstant.TEMPLATE_SUBFORM %>");
			//点击流程标签时,如果表单未加载就先加载表单
			 if (!XForm_XformIframeIsLoad(window) && isFormTemplateMode ){
				LoadXForm('TD_FormTemplate_${JsParam.fdKey}');
				var frame = document.getElementById('IFrame_FormTemplate_${JsParam.fdKey}');
				Com_AddEventListener(frame, 'load', function(){
					var obj = XForm_getXFormDesignerInstance_${lbpmTemplate_Key}();
					if (obj){
						iframe.FlowChartObject.FormFieldList = iframeDefault.FlowChartObject.FormFieldList = XForm_getXFormDesignerObj_${lbpmTemplate_Key}();
						iframe.FlowChartObject.Designer = iframeDefault.FlowChartObject.Designer = obj["designer"];
						iframe.FlowChartObject.modelName = iframeDefault.FlowChartObject.modelName = obj["modelName"];
					}
				});
			 }else{
				 var obj = XForm_getXFormDesignerInstance_${lbpmTemplate_Key}();
				 if (obj){
					iframe.FlowChartObject.Designer = iframeDefault.FlowChartObject.Designer  = obj["designer"];
					iframe.FlowChartObject.modelName = iframeDefault.FlowChartObject.modelName  = obj["modelName"];
				 } 
			 }
		}
		iframe.FlowChartObject.FormFieldList = iframeDefault.FlowChartObject.FormFieldList = LBPM_Template_FormFieldList;
		iframe.FlowChartObject.SubFormInfoList = iframeDefault.FlowChartObject.SubFormInfoList = LBPM_Template_SubFormInfoList;
		iframe.FlowChartObject.SubPrintInfoList = iframeDefault.FlowChartObject.SubPrintInfoList = LBPM_Template_SubPrintInfoList;
		iframe.FlowChartObject.xform_mode = iframeDefault.FlowChartObject.xform_mode = LBPM_Template_xform_mode?LBPM_Template_xform_mode:0;
		iframe.FlowChartObject.SysRuleTemplate = LBPM_Template_RuleTemplate;
		iframe.FlowChartObject.LbpmTemplateKey = LBPM_Template_Rule_Key;
	}else{
		setTimeout(LBPM_Template_Load_FlowChartObject_${lbpmTemplate_Key},500);
	}
}

// 对外提供流程节点
function LBPM_Template_getNodes${lbpmTemplate_Key}(isContainSubProcessNode) {
	var typeVe = $("input[name='${lbpmTemplateFormPrefix}fdType']:checked").val();
	if(typeVe == "3" || typeVe == "4") {// 自定义
		var FlowChartObject = {};
		if(typeVe == "3"){
			FlowChartObject = document.getElementById("${lbpmTemplateFormPrefix}WF_IFrame").contentWindow.FlowChartObject;
		}else{
			FlowChartObject = document.getElementById("${lbpmTemplateFormPrefix}WF_IFrame_Default").contentWindow.FlowChartObject;
		}
		return LBPM_Template_getNodes(FlowChartObject.BuildFlowData(),isContainSubProcessNode);
	} else if (typeVe == "2") {// 其它
		var commonId = $("input[name='${lbpmTemplateFormPrefix}fdCommonId']").val();
		if (commonId == '') {
			return [];
		}
		var rtn = [];
		var data = new KMSSData();
		var url = "<c:url value='/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do'/>?method=findNodes&tempId=";
		data.SendToUrl(url + commonId, function(rq){
			var xml = rq.responseText;
			if (xml.indexOf('<error>') > -1) {
				alert(xml);
				rtn = [];
			} else {
				rtn = LBPM_Template_getNodes(WorkFlow_LoadXMLData(xml),isContainSubProcessNode);
			}
		}, false);
		return rtn;
	} else if (typeVe == "1") {// 取默认
		var rtn = [];
		var data = new KMSSData();
		var url = "<c:url value='/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do'/>?method=findNodes&modelName=${lbpmTemplate_ModelName}&key=${lbpmTemplate_Key}";
		data.SendToUrl(url, function(rq){
			var xml = rq.responseText;
			if (xml.indexOf('<error>') > -1) {
				alert(xml);
				rtn = [];
			} else {
				rtn = LBPM_Template_getNodes(WorkFlow_LoadXMLData(xml),isContainSubProcessNode);
			}
		}, false);
		return rtn;
	}
}
function LBPM_Template_getNodes(processData,isContainSubProcessNode) {
	var nodes = [];
	if(processData.nodes){
		for(var i=0; i<processData.nodes.length; i++) {
			var node = processData.nodes[i];
			var desc = lbpm.nodedescs[lbpm.nodeDescMap[node.XMLNODENAME]];
			if(node.XMLNODENAME == "embeddedSubFlowNode"){
				if(lbpm){
					var fdContent = getContentByRefId(node.embeddedRefId);
					if(fdContent){
						//嵌入的流程图对象
						var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
						for(var j = 0;j<embeddedFlow.nodes.length;j++){
							var eNode = embeddedFlow.nodes[j];
							if(_isHandler(eNode) || _isDraftNode(eNode) || _isSendNode(eNode)){
								nodes.push({value:node.id+"-"+eNode.id, name:node.id+"."+node.name+"("+eNode.id+"."+eNode.name+")",type:eNode.XMLNODENAME});
							}
						}
					}
				}
			}else if(node.XMLNODENAME == "dynamicSubFlowNode"){
				if(lbpm){
					var _groups = getGroupsByFdId(node.dynamicGroupId);
					if(_groups){
						var __groups = JSON.parse(_groups);
						for(var h=0;h<__groups.length;h++){
							var param = __groups[h];
							var fdContent = param.fdContent;
							if(fdContent){
								//动态子流程的流程图对象
								var embeddedFlow = WorkFlow_LoadXMLData(fdContent);
								for(var j = 0;j<embeddedFlow.nodes.length;j++){
									var eNode = embeddedFlow.nodes[j];
									if(_isHandler(eNode) || _isDraftNode(eNode) || _isSendNode(eNode)){
										nodes.push({value:node.id+"-"+param.fdId+"-"+eNode.id, name:node.id+"."+node.name+"["+param.fdAlias+"]"+"("+eNode.id+"."+eNode.name+")",type:eNode.XMLNODENAME});
									}
								}
							}
						}
					}
				}
			}else if(node.XMLNODENAME == "adHocSubFlowNode"){
				if(lbpm){
					var fdContent = node.adHocSubFlowData;
					if(fdContent){
						//即席的流程图对象
						var adHocSubFlow = WorkFlow_LoadXMLData(fdContent);
						for(var j = 0;j<adHocSubFlow.nodes.length;j++){
							var sNode = adHocSubFlow.nodes[j];
							if(_isHandler(sNode) || _isDraftNode(sNode) || _isSendNode(sNode)){
								nodes.push({value:node.id+"-"+sNode.id, name:node.name+"("+sNode.id+"."+sNode.name+")",type:sNode.XMLNODENAME});
							}
						}
					}
				}
			}else if (desc.isHandler(node) && node.groupNodeId == null) {
				nodes.push({value:node.id,name:node.id+"."+node.name,type:node.XMLNODENAME});
			}else if(typeof isContainSubProcessNode != 'undefined' && isContainSubProcessNode && node.XMLNODENAME === "recoverSubProcessNode"){
				nodes.push({value:node.id,name:node.id+"."+node.name,type:node.XMLNODENAME,recoverSubProcessNote:node.recoverSubProcessNote});
			}
		}
	}
	return nodes;
}

// 兼容旧流程
var WorkFlow_getWfNodes_${lbpmTemplate_Key} = LBPM_Template_getNodes${lbpmTemplate_Key};

<c:import url="/sys/lbpm/flowchart/page/plugin_descs_loader.jsp" charEncoding="UTF-8">
	<c:param name="modelName">${lbpmTemplate_MainModelName}</c:param>
</c:import>
</script>