<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.title" /></title>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<link rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/listview.css?s_cache=${LUI_Cache}" />
<link rel="stylesheet" media="screen" href="../css/simulation.css" type="text/css"/>
<script type="text/javascript">
Com_IncludeFile('jquery.js|dialog.js|formula.js|plugin.js');
</script>
<style>
body, td, input, select, textarea {
	font-size: 12px;
	color: #333333;

}

body {
	margin: 0px;
}

.tb_normal {
  background-color: #ffffff;
  border-collapse: collapse;
  border: 1px #D4D4DC solid;
  padding: 8px !important;
  text-align: left;
  margin: 0 auto;
}
.tb_normal > tbody > .tr_normal_title {
  background-color: #f5f5f5;
  text-align: center;
}
.tb_normal > tbody > tr {
  border-bottom: 1px solid #D4D4DC;
  border-top: 1px solid #D4D4DC;
}
.tb_normal > tbody > tr > td {
  padding: 8px;
  word-break: break-word;
  border:0px;
  /* border-left: 1px #d2d2d2 solid; */
  /* border-right: 1px #d2d2d2 solid; */
}

.tb_normal > tbody >tr > .td_normal_title {
  background-color: #f5f5f5;
}

.inputsgl {
	color: #0066FF;
	border-color: #999999;
	border-style: solid;
	border-width: 0px 0px 1px 0px;
}

.btn {
	color: #0066FF;
	background-color: #F0F0F0;
	border: 1px #999999 solid;
	font-weight: normal;
	padding: 0px 1px 1px 0px;
	height: 18px;
	clip: rect();
}

.td {
	text-align: center;
}

.divTag{
	width: 100%;
	height: 20px; 
	color: white;
}
.divTagPass{
	width: 100%;
	height: 20px; 
	border:1px;
	border-color: #204d74;
	background-color: #337ab7;
	color: white;
	text-align:center;
	vertical-align:middle;
	cursor:pointer;
	border-radius:5px;
}
.divTagError{
	width: 100%;
	height: 20px;  
	border:1px;
	border-color: #d43f3a;
	background-color: #d9534f;
	color: white;
	text-align:center;
	vertical-align:middle;
	cursor:pointer;
	border-radius:5px;
}
.btnBorder{
	border-radius:3px;
}
.tdTitle{
	text-align:center;
}

body{text-align:center;}
#flowSimulation{width:100%;margin:0px;}
.table-head{padding-right:17px;}
.table-body{width:100%; height:300px;overflow-y:scroll;}
.table-head table,.table-body table{width:100%;}
</style>
<script type="text/javascript">
//多语言
var FlowSimulationLang=new Object();
FlowSimulationLang.title='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.title" />';
FlowSimulationLang.starting='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.starting" />';
FlowSimulationLang.startAutomaticFlowTest='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startAutomaticFlowTest" />';
FlowSimulationLang.startNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startNode" />';
FlowSimulationLang.endNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.endNode" />';
FlowSimulationLang.draftNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftNode" />';
FlowSimulationLang.reviewNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.reviewNode" />';
FlowSimulationLang.autoBranchNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.autoBranchNode" />';
FlowSimulationLang.manualBranchNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.manualBranchNode" />';
FlowSimulationLang.signNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.signNode" />';
FlowSimulationLang.sendNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.sendNode" />';
FlowSimulationLang.robotNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.robotNode" />';
FlowSimulationLang.startSubProcessNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startSubProcessNode" />';
FlowSimulationLang.recoverSubProcessNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.recoverSubProcessNode" />';
FlowSimulationLang.splitNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.splitNode" />';
FlowSimulationLang.joinNode='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.joinNode" />';
FlowSimulationLang.voteNode='<bean:message bundle="sys-lbpmservice-node-votenode" key="FlowChartObject.Lang.Operation.Text.ChangeMode.voteNode" />';
FlowSimulationLang.select='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.select" />';
FlowSimulationLang.message_a='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.a" />';
FlowSimulationLang.message_b='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.b" />';
FlowSimulationLang.message_c='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.c" />';
FlowSimulationLang.message_d='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.d" />';
FlowSimulationLang.message_e='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.e" />';
FlowSimulationLang.message_f='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.f" />';
FlowSimulationLang.message_g='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.g" />';
FlowSimulationLang.message_h='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.h" />';
FlowSimulationLang.message_i='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.i" />';

FlowSimulationLang.list_noData='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.list.noData" />';


var  FlowExampleLang=new Object();
FlowExampleLang.formField='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.formField" />';
FlowExampleLang.fieldName='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.fieldName" />';
FlowExampleLang.fieldValue='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.fieldValue" />';
FlowExampleLang.fieldError='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.fieldError" />';
FlowExampleLang.select='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.select" />';
FlowExampleLang.nodeMessage='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.nodeMessage" />';
FlowExampleLang.nodeSeting='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.nodeSeting" />';
FlowExampleLang.nodeName='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.nodeName" />';
FlowExampleLang.nodeAction='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.flowExample.nodeAction" />';

var LogUitlLang=new Object();
LogUitlLang.isNul='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.isNull" />';
LogUitlLang.error='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.error" />';
LogUitlLang.pass='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.pass" />';
LogUitlLang.warn='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.warn" />';

LogUitlLang.message_a='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.a" />';
LogUitlLang.message_b='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.b" />';
LogUitlLang.message_c='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.c" />';
LogUitlLang.message_d='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.d" />';
LogUitlLang.message_e='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.e" />';
LogUitlLang.message_f='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.f" />';
LogUitlLang.message_i='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.i" />';

var ManualSimulationLang=new Object();
ManualSimulationLang.Message_a='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.ManualSimulation.Message.a" />';
</script>

<script type="text/javascript" src="../js/flowSimulation/request_util.js"></script>
<script type="text/javascript" src="../js/flowSimulation/chart_util.js"></script>
<script type="text/javascript" src="../js/flowSimulation/log_util.js"></script>
<script type="text/javascript" src="../js/flowSimulation/node_execption.js"></script>
<script type="text/javascript" src="../js/flowSimulation/node_simulation.js"></script>
<script type="text/javascript" src="../js/flowSimulation/automatic_simulation.js"></script>
<script type="text/javascript" src="../js/flowSimulation/manual_simulation.js"></script>
<script type="text/javascript" src="../js/flowSimulation/flow_example.js"></script>
<script type="text/javascript" src="../js/flowSimulation/flow_simulation.js"></script>
<script type="text/javascript">
//var vKMSSValidation=$KMSSValidation();//添加表单校验框架
var flowChartObject=parent.FlowChartObject;//获取流程全局对象
var vNodes=flowChartObject.Nodes.all;//获得所有节点元素
var vFieldList=new Array();//需要解析的表单字段集合
var vTempFormula="";//所有节点的公式文本
//模板表单文件的地址，用于公式解析用，在加载时通过ajax请求获取 
//例：km/review/xform/define/15ca5daff0fde59cce84c0e489b96d87/15ca5daff0fde59cce84c0e489b96d87_v3_20170808142228
var vExtendFilePath="";

var Data_XMLCatche=parent.Data_XMLCatche;

/**
 * 设置表单模板文件地址
 *modelId:流程模板ID
*/
function setExtendFilePath(modelId,simulType){
    //兼容业务建模
    if (typeof flowChartObject.isModeling != "undefined" && flowChartObject.isModeling) {
        modelId = flowChartObject.FdAppModelId;
    }
	var parameter=new Array();
	parameter.push("RequestType=ModelExtendFilePath");
	parameter.push("ModelId="+modelId);
	parameter.push("SimulType="+simulType);
	var data=FlowSimulation.postRequestServers(parameter.join("&"));
	
	if(data[0].type=="ok"){
		vExtendFilePath=data[0].extendFilePath;
	}
}
function TestLogShowOrHide(){
	$("#divTestLog").slideToggle();
}
function startAutomaticFlowTest(){
	FlowSimulation.startAutomaticFlowTest();
}
function simulationRefresh(){
	FlowSimulation.resetFlowTest(flowChartObject);
	window.location.href=window.location.href;
}
$(document).ready(function(){
	//仿真实例列表全选按钮事件
	  $("#listCheck").click(function(){
	    if($(this).is(':checked')){
	    	$("[name='checkExample'][disabled!=disabled]").prop("checked",true);  
	    }
	    else{
	    	$("[name='checkExample']").prop("checked",false)
	    }
	  });
});
window.onload=function(){
	vTempFormula+=FlowExample.obtainFormulaText(vNodes);//获取所有流程节点中的公式
	vFieldList=FlowExample.obtainFormFieldList(flowChartObject.FormFieldList,vTempFormula);//解析出所有在公式中出现过的字段
	
	setExtendFilePath(Com_GetUrlParameter(window.location.href,"fdId"),Com_GetUrlParameter(window.location.href,"simulType"));
	
	FlowExample.setProcessSimulation();//设置流程实例表格内容，将解析的节点内容显示在表格中
	FlowExample.loadExampleList(Com_GetUrlParameter(window.location.href,"fdId"),$("#dataList_body"));//加载实例列表
	
	//解决地址本第一次打开的时候不是在最外层的问题，提前加载资源
	seajs.use('lui/dialog');
}

</script>
</head>
<body>
	<ui:tabpanel id="tabpanel" layout="sys.ui.tabpanel.simple" >
		<ui:content  title="${ lfn:message('sys-lbpmservice-support:lbpmFlowSimulation.title')}" style="padding:0px">
				<div id="flowSimulation">
					<table class="tb_normal" id="tdProcessSimulation" width="100%">
						<tbody>
							<tr id="buttonBar" data-draftmanType="0">
								<td class="tdTitle">
									<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startAutomaticFlowTest" />" onclick="startAutomaticFlowTest();" class="btnopt btnBorder" id="btnStartFlowTest">
								</td>
								<td style="text-align: left;">
									<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.refresh" />" onclick="simulationRefresh();" class="btnopt btnBorder">
									<div class="manualSimulation">
										<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startManualSimulation" />" onclick="FlowSimulation.startManualSimulation();" class="btnopt btnBorder">&nbsp;&nbsp;
										<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.nextStep" />" onclick="FlowSimulation.nextStep();" class="btnopt btnBorder">&nbsp;&nbsp;
										<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.previousStep" />" onclick="FlowSimulation.previousStep();" class="btnopt btnBorder">&nbsp;&nbsp;
									</div>
								</td>
							</tr>
							<tr>
								<td class="tdTitle">
									<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftmanType" />
								</td>
								<td>
									<select id="draftmanType" onchange="FlowExample.draftmanTypeChange(this);">
									 	<option value="0" selected="selected"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.type.appoint" /></option>
									 	<option value="1"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.type.rule" /></option>
									</select>
								</td>
							</tr>
							<tr class="tr_normal_title">
								<td colspan="2" class="tdTitle"><strong><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftmanSeting" /></strong>
								</td>
							</tr>
							<tr data-draftmanType="0">
								<td style="width: 35%;" class="tdTitle"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman" /></td>
								<td style="width: 65%">
									<div id="auditshow_mouldDetail_html">
										<input data-from="example" data-type="Start_SysOrgPerson_Name" data-id="detail_attr_name" id="detail_attr_name" name="detail_attr_name" readonly="true" class="inputsgl" value="" type="text" onchange="FlowExample.loadFdHandlerRoleInfoIds();"><span class="txtstrong">*</span>
										<input data-from="example" data-type="Start_SysOrgPerson_Value" data-id="detail_attr_value" id="detail_attr_value" name="detail_attr_value" value="" type="hidden">
										<a href="#" id="handlerSelect" onclick="Dialog_Address(false, 'detail_attr_value','detail_attr_name', null,ORG_TYPE_ALL);"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.select" /></a>
									</div>
								</td>
							</tr>
							<tr data-draftmanType="0" id="trFdHandlerRoleInfoIds" style="display:none">
								<!-- 起草人身份 -->
								<td class="tdTitle"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftmanAvatar" /></td>
								<td>
									<select data-from="example" data-type="select" data-id="sFdHandlerRoleInfoIds" id="sFdHandlerRoleInfoIds">
									</select>
									<span class="txtstrong">*</span>
								</td>
							</tr>
							<tr data-draftmanType="1" id="trDraftmanRule" style="display:none">
								<!-- 起草人处理规则 -->
								<td class="tdTitle"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.rule" /></td>
								<td>
									<select data-from="example" data-type="select" data-id="sDraftmanRule" id="sDraftmanRule">
											<option selected="selected" value=""><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.rule.empty" /></option>
											<option value="0"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.rule.all" /></option>
											<option value="1"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.rule.selectRule" /></option>
									</select>
									<span class="txtstrong">*</span>
								</td>	
							</tr>
							<tr data-draftmanType="1" style="display:none">
								<td class="tdTitle">
									<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.range" />
								</td>
								<td>
									<input data-from="example" data-type="sysOrgElement_draftman_name" data-id="address_name_scope_draftman" id="address_name_scope_draftman" name="address_name_scope_draftman" readonly="true" class="inputsgl" value="" type="text"><span class="txtstrong">*</span>
									<input data-from="example" data-type="sysOrgElement_draftman_value" data-id="address_value_scope_draftman" id="address_value_scope_draftman" name="address_value_scope_draftman" value="" type="hidden">
									<a href="#" id="draftmanScopeSelect" onclick="Dialog_Address(true, 'address_value_scope_draftman','address_name_scope_draftman', null,ORG_TYPE_ALL);"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.select" /></a>
								</td>
							</tr>
							<tr data-draftmanType="1" style="display:none">
								<td class="tdTitle">
									<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman.num" />
								</td>
								<td>
									<xform:xtext title="${ lfn:message('sys-lbpmservice-support:lbpmFlowSimulation.draftman.num.help') }" property="sysOrgElement_draftman_num" htmlElementProperties="id='sysOrgElement_draftman_num' data-from='example' data-type='sysOrgElement_draftman_num' data-id='sysOrgElement_draftman_num'"  required="false" showStatus="edit" className="inputsgl xform_inputText"  dataType="Double" validators=" number number scaleLength(0)"/>
									<!-- <span class="txtstrong">*</span> -->
									
								</td>
							</tr>
						</tbody>
					</table>
					<table style="width: 100%;margin-top: 10px;margin-bottom: 10px;">
						<tr>
							<td>
								<input style="width: 120px;" type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.save" />" onclick="FlowExample.save();" class="btnopt btnBorder">
							</td>
						</tr>
					</table>
				</div>			
		</ui:content>
		<ui:content title="${ lfn:message('sys-lbpmservice-support:lbpmFlowSimulation.title.a')}">
			<div>
				<table width="100%">
					<tr>
						<td style="text-align:left;">
							<input style="width: 120px;" type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startBatchSimulation" />" onclick="FlowSimulation.startBatchSimulation();" class="btnopt btnBorder batchSimulation">
							<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.delete" />" onclick="FlowExample.deleteExample();" class="btnopt btnBorder">
						</td>
					</tr>
				</table>
				<table id="dataList" class="lui_listview_columntable_table" width="100%">
					<thead>
						<tr>
							<th width="30px"><input id="listCheck" type="checkbox"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.list.select" /></th>
							<th style="text-align: center;"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.list.title" /></th>
							<%-- <th width="30px"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.list.status" /></th> --%>
						</tr>
					</thead>
					<tbody id="dataList_body">
						<tr>
							<td colspan="3"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.list.noData" /></td>
						</tr>
					</tbody>
				</table>
			</div>			
		</ui:content>
	</ui:tabpanel>
	<div title="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.panel.close" />" style="z-index: 10;position:fixed;right:0px;top:50%;cursor:pointer">
		<img onclick="flowChartObject.FlowSimulation.Toggle();" alt="" src="../images/closePanel.png">
	</div>	
</body>
</html>