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
Com_IncludeFile('jquery.js');
</script>
<style>
body, td, input, select, textarea {
	font-size: 12px;
	color: #333333;

}

body {
	margin: 0px;
	background-color:#F5F5F5;
}

.tb_normal {
	border-collapse: collapse;
	border: 1px #D4D4DC solid;
	background-color: #FFFFFF;
}

.td_normal, .tb_normal td {
	border-collapse: collapse;
	border: 1px #D4D4DC solid;
	padding: 3px;
}

.tr_normal_title {
	background-color: #F5F5F5;
	border-collapse: collapse;
	border: 1px #D4D4DC solid;
	padding: 3px;
	text-align: center;
	word-break: keep-all;
}

.td_normal_title {
	background-color: #F5F5F5;
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
	background-color: #F5F5F5;
	border: 1px #999999 solid;
	font-weight: normal;
	padding: 0px 1px 1px 0px;
	height: 18px;
	clip: rect();
}

.td {
	text-align: center;
}

.table-body{width:100%; height:230px;}
.table-head table,.table-body table{width:100%;}
</style>
<script type="text/javascript">
function TestLogShowOrHide(){
	var FlowChartObject=parent.FlowChartObject;
	var vHeight=$(FlowChartObject.FlowSimulation.LogIframe).height();
	if(vHeight==21){
		$(FlowChartObject.FlowSimulation.LogIframe).height(300);
	}
	else{
		$(FlowChartObject.FlowSimulation.LogIframe).height(21);
	}
	$("#divTestLog").toggle();
}
$(document).ready(function(){ 
	//避免打开仿真界面时，日志面板挡住流程图
	var FlowChartObject=parent.FlowChartObject;
	var vHeight=$(FlowChartObject.FlowSimulation.LogIframe).height();
	if(vHeight==300){
		$(FlowChartObject.FlowSimulation.LogIframe).height(21);
		$("#divTestLog").hide();
	}
}); 
</script>
</head>
<body>
	<table style="width:99.8%">
				<tr>
					<td>
						<div style="width:99.7%;text-align: center;background-color:#F5F5F5;border: 1px #D4D4DC solid" onclick="TestLogShowOrHide();">
							<bean:message bundle="sys-lbpmservice-support" key="lbpmFlowSimulation.hideOrdisplayLog" />
						</div>
					</td>
				</tr>
				<tr style="background-color:#F5F5F5;"><!-- display:none; -->
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
</body>
</html>