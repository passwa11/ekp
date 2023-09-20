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
	border-collapse: collapse;
	border: 1px #C0C0C0 solid;
	background-color: #FFFFFF;
}

.td_normal, .tb_normal td {
	border-collapse: collapse;
	border: 1px #C0C0C0 solid;
	padding: 3px;
}

.tr_normal_title {
	background-color: #F0F0F0;
	border-collapse: collapse;
	border: 1px #C0C0C0 solid;
	padding: 3px;
	text-align: center;
	word-break: keep-all;
}

.td_normal_title {
	background-color: #F0F0F0;
	border-collapse: collapse;
	border: 1px #C0C0C0 solid;
	padding: 3px;
	word-break: keep-all;
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
body{text-align:center;}
#flowSimulation{width:90%;margin:0 auto;}
.table-head{padding-right:17px;}
.table-body{width:100%; height:300px;overflow-y:scroll;}
.table-head table,.table-body table{width:100%;}
</style>
<script type="text/javascript">
//多语言
var FlowSimulationLang=new Object();
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

FlowSimulationLang.message_a='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.a" />';
FlowSimulationLang.message_b='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.b" />';
FlowSimulationLang.message_c='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.c" />';
FlowSimulationLang.message_d='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.message.d" />';


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
LogUitlLang.message_a='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.a" />';
LogUitlLang.message_b='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.b" />';
LogUitlLang.message_c='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.c" />';
LogUitlLang.message_d='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.d" />';
LogUitlLang.message_e='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.e" />';
LogUitlLang.message_f='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message.f" />';

var ManualSimulationLang=new Object();
ManualSimulationLang.Message_a='<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.ManualSimulation.Message.a" />';
</script>
<script type="text/javascript" src="../js/flowSimulation/chart_util.js?1.0"></script>
<script type="text/javascript" src="../js/flowSimulation/log_util.js?1.6"></script>
<script type="text/javascript" src="../js/flowSimulation/node_execption.js?1.3"></script>
<script type="text/javascript" src="../js/flowSimulation/node_simulation.js?1.9.3"></script>
<script type="text/javascript" src="../js/flowSimulation/automatic_simulation.js?1.5"></script>
<script type="text/javascript" src="../js/flowSimulation/manual_simulation.js?1.28"></script>
<script type="text/javascript" src="../js/flowSimulation/flow_example.js?1.2"></script>
<script type="text/javascript" src="../js/flowSimulation/flow_simulation.js?1.9.5"></script>
<script type="text/javascript">
var vKMSSValidation=$KMSSValidation();//添加表单校验框架
var flowChartObject=opener.FlowChartObject;//获取流程全局对象
var vNodes=flowChartObject.Nodes.all;//获得所有节点元素
var vFieldList=new Array();//需要解析的表单字段集合
var vTempFormula="";//所有节点的公式文本
//模板表单文件的地址，用于公式解析用，在加载时通过ajax请求获取 
//例：km/review/xform/define/15ca5daff0fde59cce84c0e489b96d87/15ca5daff0fde59cce84c0e489b96d87_v3_20170808142228
var vExtendFilePath="";

var Data_XMLCatche=opener.Data_XMLCatche;

/**
 * 设置表单模板文件地址
 *modelId:流程模板ID
*/
function setExtendFilePath(modelId){
    //兼容业务建模
    if (typeof flowChartObject.isModeling != "undefined" && flowChartObject.isModeling) {
        modelId = flowChartObject.FdAppModelId;
    }
	var parameter=new Array();
	parameter.push("RequestType=ModelExtendFilePath");
	parameter.push("ModelId="+modelId);
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
window.onload=function(){
	vTempFormula+=FlowExample.obtainFormulaText(vNodes);//获取所有流程节点中的公式
	vFieldList=FlowExample.obtainFormFieldList(flowChartObject.FormFieldList,vTempFormula);//解析出所有在公式中出现过的字段
	document.getElementsByName("fdFlowContent")[0].value=flowChartObject.BuildFlowXML();//设置流程图XML
	//导入流程图
	document.getElementById("sysWfTemplateForms.reviewMainDoc.WF_IFrame").src="<c:url value="/sys/lbpm/flowchart/page/panel.html?edit=false&extend=oa&template=true&contentField=fdFlowContent&modelName=com.landray.kmss.km.review.model.KmReviewMain&FormFieldList=WF_FormFieldList_reviewMainDoc"/>";
	setExtendFilePath(Com_GetUrlParameter(window.location.href,"fdId"));
	FlowExample.setProcessSimulation();//设置流程实例表格内容，将解析的节点内容显示在表格中

	//设置日志宽度
	$("#divBottom").width($("#flowSimulation").width()-368);
}

</script>
</head>
<body>
	<div id="flowSimulation">
		<p class="txttitle" style="font-size: 25pt;margin: 10px;">
			<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.title" />
		</p>
		<table class="tb_normal" style="width:100%">
			<tr>
				<td valign="top" width="350px" style="padding:0px;">
					<table class="tb_normal" id="tdProcessSimulation" width="100%">
						<tbody>
							<tr>
								<td colspan="2" style="text-align: left;">
									<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startAutomaticFlowTest" />" onclick="startAutomaticFlowTest();" class="btnopt" id="btnStartFlowTest">&nbsp;&nbsp;
									<!--<input type="button" value="重置信息" onclick="resetFlowTest();" class="btnopt">-->
								</td>
							</tr>
							<tr>
								<td colspan="2" style="text-align: left;">
										<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.startManualSimulation" />" onclick="FlowSimulation.startManualSimulation();" class="btnopt">&nbsp;&nbsp;
										<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.nextStep" />" onclick="FlowSimulation.nextStep();" class="btnopt">&nbsp;&nbsp;
										<input type="button" value="<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.previousStep" />" onclick="FlowSimulation.previousStep();" class="btnopt">&nbsp;&nbsp;
								</td>
							</tr>
							<tr class="tr_normal_title">
								<td colspan="2" style="text-align: center"><strong><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftmanSeting" /></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 35%;text-align: center;"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftman" /></td>
								<td style="width: 65%">
									<div id="auditshow_mouldDetail_html">
										<input id="detail_attr_name" name="detail_attr_name" readonly="true" class="inputsgl" value="" type="text" onchange="FlowExample.loadFdHandlerRoleInfoIds();">
										<input id="detail_attr_value" name="detail_attr_value" value="" type="hidden">
										<a href="#" id="handlerSelect" onclick="Dialog_Address(false, 'detail_attr_value','detail_attr_name', ';',ORG_TYPE_ALL);">选择</a>
									</div>
								</td>
							</tr>
							<tr id="trFdHandlerRoleInfoIds" style="display:none">
								<td style="text-align: center;"><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.draftmanAvatar" /></td>
								<td>
									<select id="sFdHandlerRoleInfoIds">
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
				<td>
					<textarea name="fdFlowContent" style="display:none"></textarea>
					<iframe src="" style="border: 0px;width: 100%; height:300%; z-index: 9999;" scrolling="no" id="sysWfTemplateForms.reviewMainDoc.WF_IFrame">
					</iframe>
				</td>
			</tr>
		</table>

		<div id="divBottom" style="width:90%;z-index: 50;bottom: 0px;position: fixed;background-color:black;margin-left:352px;">
			<table style="width:100%">
				<tr>
					<td>
						<div style="width:100%;text-align: center;background-color:#F0F0F0;border: 1px #C0C0C0 solid" onclick="TestLogShowOrHide();">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.hideOrdisplayLog" />
						</div>
					</td>
				</tr>
				<tr style="background-color:#F0F0F0;">
					<td>
						<div style="width:100%;display:none;" id="divTestLog">
							<div class="table-head">
								<table class="tb_normal" style="width:100%;">
									<colgroup>
										<col style="width:40px;" />
										<col style="width:70px;" />
										<col style="width:120px;" />
										<col style="width:120px;" />
										<col style="width:200px;" />
										<col/>
									</colgroup>
									<thead>
										<tr class="tr_normal_title">
											<td colspan="6" style="text-align: center"><strong><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log" /></strong></td>
										</tr>
										<tr class="tr_normal_title">
											<td><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.orderNumber" /></td>
											<td><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.status" /></td>
											<td><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.nodeName" /></td>
											<td><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.nodeType" /></td>
											<td><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.handle" /></td>
											<td><bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.log.message" /></td>
										</tr>
									</thead>
								</table>
							</div>
							<div class="table-body" id="tableBody">
								<table class="tb_normal" id="tdTestLog" style="width:100%;">
								<colgroup>
										<col style="width:40px;" />
										<col style="width:70px;" />
										<col style="width:120px;" />
										<col style="width:120px;" />
										<col style="width:200px;" />
										<col/>
									</colgroup>
								<tbody>
									
								</tbody>
							</table>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>