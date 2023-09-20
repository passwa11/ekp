<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super />
		
	<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/css/analysis.css"/>" media="screen" />

	</template:replace>
	<template:replace name="body">
	
	<div id="retreat">
		<div align="right" id="lui_head_div"><a href="javascript:hide()" class="profile_config_title btn_close" ></a></div>
		<div>
			<p>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.configurationGuidelines.p1') }</p>
			<p>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.configurationGuidelines.p2') }</p>
			<p>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.configurationGuidelines.p3') }</p>
			<p>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.configurationGuidelines.p4') }</p>
			<p>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.configurationGuidelines.p5') }</p>
			<p>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.configurationGuidelines.p6') }</p>
		</div>
	</div>
		<div id="over"></div>
	<html:form action="/sys/lbpmmonitor/sys_lbpmmonitor_flow/LbpmAnalysisConfigAction.do" onsubmit="return validateAppConfigForm(this)">
	<center>
	<h2 align="center" style="margin: 10px 0">
		<span class="profile_config_title">${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.configuration') }</span>
	</h2>
	<center>
	<table class="analysis_config_tb">
		<tr>
			<td class="td_normal_title">${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.statics') }</td>
			<td>
			<table class="analysis_config_tb">
					<tr>
					<td>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.handlerPerson') }</td>
						<td>
						         ${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.skipRules') }
							<table class="analysis_config_tb">
								<tr>
									<td>&nbsp;</td>
									<td>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.transaction.startTimeout') }</td>
									<td>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.node.startTimeout') }</td>
								</tr>
								<tr>
									<td>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.countedObj') }</td>
									<td>
									
									<input type="checkbox" id="transactionDayOfPassRuleType" name="transactionDayOfPassRuleType" value="20" <c:forEach var="analysis" items="${lbpmAnalysisConfigs}">
										<c:if test="${analysis.dayOfPassRuleType==1&&analysis.handlerPersonType=='20' }">
											checked
										</c:if>
									</c:forEach> />${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.currProcessor') }
									
									</td>
									<td>
									<input type="checkbox" name="nodeDayOfPassRuleType" <c:forEach var="analysis" items="${lbpmAnalysisConfigs}">
										<c:if test="${analysis.dayOfPassRuleType==0&&analysis.handlerPersonType=='30' }">
											checked
										</c:if>
									</c:forEach> value="30"/>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.postProcessor') } <br/>
									
									<input type="checkbox" name="nodeDayOfPassRuleType" <c:forEach var="analysis" items="${lbpmAnalysisConfigs}">
										<c:if test="${analysis.dayOfPassRuleType==0&&analysis.handlerPersonType=='20' }">
											checked
										</c:if>
									</c:forEach> value="20"/>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.currProcessor') }<br/>
									
									<input type="checkbox" name="nodeDayOfPassRuleType" <c:forEach var="analysis" items="${lbpmAnalysisConfigs}">
										<c:if test="${analysis.dayOfPassRuleType==0&&analysis.handlerPersonType=='40' }">
											checked
										</c:if>
									</c:forEach> value="40"/>${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.laterProcessor') } <br/>
									
									</td>
							</table>
							<span id="detail_description" onclick="show()" class="profile_config_title" style="cursor:pointer">${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.configurationDetail') }</span>
						</td>
					</tr>
			</table>
			</td>
		</tr>
	</table>
	</center>
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.lbpmAnalysisConfigForm, 'saveLbpmAnalysisConfig');" order="1" ></ui:button>
			</center>
			
		</html:form>
		
		<script type="text/javascript">
		    //$('#transactionDayOfPassRuleType').attr("checked",true);

		    // #79032 兼容ie8
			window.console = window.console || (function(){  
    			var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile  
    			= c.clear = c.exception = c.trace = c.assert = function(){};  
    			return c;  
			})();
			// 
			
            var retreat = document.getElementById('retreat');
            var over = document.getElementById('over');
            function show()
            {
                retreat.style.display = "block";
                over.style.display = "block";
            }
            function hide()
            {
                retreat.style.display = "none";
                over.style.display = "none";
            }


			
		 	function validateAppConfigForm(thisObj){
	 			console.log(thisObj.transactionDayOfPassRuleType.checked);
		 		if(!thisObj.transactionDayOfPassRuleType.checked){
		 			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
		 				dialog.alert("${ lfn:message('sys-lbpmmonitor:sysLbpmMonitor.monitoring.analysis.transaction.mustSet') }");
		 			});
		 			return false;
		 		}
		 		return true;
		 	}
	 	</script>
	 	
	</template:replace>
</template:include>
