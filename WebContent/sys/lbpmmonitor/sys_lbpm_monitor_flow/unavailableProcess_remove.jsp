<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.sys.lbpmmonitor.service.spring.LbpmMonitorUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
<link rel="stylesheet" href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/listview.css?s_cache=${LUI_Cache}" />
<script type="text/javascript">
Com_IncludeFile("data.js|dialog.js|jquery.js", null, "js");	
</script>
<%
LbpmMonitorUtil monitor = new LbpmMonitorUtil();
Object nameInfo = monitor.getModelNameIsNotExist();
%>
<script>
	function initDocument(){
		var nameList = <%=nameInfo%>;
		$.each(nameList, function(index, name) {
			var tbody = TB_Main.getElementsByTagName('tbody')[0];
			var trObj = tbody.insertRow(-1);
			$(trObj).css("cursor","pointer");
			var tdObj = trObj.insertCell(-1);
			$(tdObj).text(name);
			tdObj.onclick = function(){
				$("input[name='targetModelName']").val(name);
			};
		});
	}
	 		
	function doSubmit(formObj){
		seajs.use([ 'lui/dialog' ], function(dialog) {
		var targetModelNameVal=$("input[name='targetModelName']").val();
		if(!targetModelNameVal){
			dialog.alert('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.handleUnavailableProcess.targetModelName.required" />');
			return false;
		}
		dialog.confirm('<bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.handleUnavailableProcess.targetModelName.confirm" />'.replace("{modelName}", targetModelNameVal),function(value){
			if(value == true){
				formObj.submit();
			} else {
				return;
			}
			});
		});
	}

	$(document).ready(function() {
		initDocument();
	});
</script>
</template:replace>
<template:replace name="content">
		<form
			action="${pageContext.request.contextPath}/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do"
			method="post">
		<p>&nbsp;</p>
		<table class="tb_normal" style="margin: 10px auto 60px; width: 97%;">
			<tr>
				<td>
				<div class="lui_form_content_frame"><bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.handleUnavailableProcess.targetModelName" />： <input type="text"
					name="targetModelName" class="inputSgl" style="width: 82%">
				</div>
				</td>
			</tr>
			<tr>
				<td><span class="txtstrong" style="font-size: 12px"><bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.handleUnavailableProcess.tips" /></span>
				</td>
			</tr>
			<tr>
				<td>
				<table class="tb_normal" width="93%">
					<tr>
						<td>
						<table id="TB_Main" class="lui_listview_columntable_table"
							width="100%">
							<thead>
							<tr>
								<th><bean:message bundle="sys-lbpmmonitor" key="sysLbpmMonitor.handleUnavailableProcess.modelNameList" /></th>
							</tr>
							</thead>
							<tbody></tbody>
						</table>
					</td>
					</tr>
				</table>
			</td>
			</tr>
			<table>
				<center>
				<div data-lui-mark="dialog.content.buttons"
					style="position: fixed; bottom: 0px; left: 15px; width: 95%; background: #fff; padding-bottom: 20px;">
				<ui:button style="width:80px"
					onclick="doSubmit(document.forms[0]);"
					text="${lfn:message('button.submit') }" /></div>
				</center>
				<input type="hidden" name="method" value="handleUnavailableProcess" />
		</form>
	</template:replace>
</template:include>