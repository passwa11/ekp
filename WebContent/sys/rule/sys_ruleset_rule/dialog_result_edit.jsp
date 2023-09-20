<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}resource/style/default/doc/document.css">
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/rule/resources/css/buttons.css">
	<script type="text/javascript">
	Com_IncludeFile("doclist.js|data.js|dialog.js|calendar.js|formula.js");
	</script>
	<script type="text/javascript" src="${ KMSS_Parameter_ContextPath }sys/rule/resources/js/common.js"></script>
	<script type="text/javascript" src="${ KMSS_Parameter_ContextPath }sys/rule/resources/js/control.js"></script>
	<script type="text/javascript" src="${ KMSS_Parameter_ContextPath }sys/rule/resources/js/dialog_result_edit.js"></script>
</head>
<body>
	<div style="margin-top:10px">
		<p class="txttitle"><bean:message key="sysRuleSetRule.fdResult.setting" bundle="sys-rule"/></p>
		<table width="95%" class="tb_normal">
			<tr>
				<td width="15%" class="td_normal_title"><bean:message key="sysRuleSetRule.fdResultMode" bundle="sys-rule"/></td>
				<td width="85%" colspan="3">
					<label><input name="fdResultMode" type="radio" checked="checked" value="formula"/><bean:message key="sysRuleSetRule.mode.formula" bundle="sys-rule"/></label>
					<label><input name="fdResultMode" type="radio" value="fixed"/><bean:message key="sysRuleSetRule.mode.fixedValue" bundle="sys-rule"/></label>
					<label style='display:none'><input name="fdResultMode" type="radio" value="orgMatrix"/><kmss:message key="rule.orgMatrix" bundle="sys-rule" /></label>
					<!-- <label><input name="fdResultMode" type="radio" value="extend"/>扩展业务</label> -->
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title"><bean:message key="sysRuleSetRule.fdResult" bundle="sys-rule"/></td>
				<td width="85%" colspan="3" id="resultControlArea">
					
				</td>
				<td width="85%" colspan="3" id="orgMatrixArea" style="display:none">
					<div style="padding-top:6px">
						<kmss:message key="rule.orgMatrix" bundle="sys-rule" /> &nbsp;&nbsp;
						<input name="orgMatrixId" type="hidden">
						<input name="orgMatrixName" class="inputsgl" style="width:75%" readonly>
						<a href="#" id="selectOrgMatrix" onclick="selectOrgMatrix();"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a><br/>
						<kmss:message key="rule.orgMatrix.results" bundle="sys-rule" /> &nbsp;&nbsp;
						<input name="matrixResultId" type="hidden">
						<input name="matrixResultName" class="inputsgl" style="width:75%" readonly>
						<a href="#" id="selectMatrixResultField" onclick="selectMatrixResultField();"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a><br/>
						<div style="margin-top:5px;">
							<kmss:message key="rule.orgMatrix.resultsMoreThanOne" bundle="sys-rule" />
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="1" checked>
								<kmss:message key="rule.orgMatrix.resultOptionOfFirst" bundle="sys-rule" /></label>
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="2">
								<kmss:message key="rule.orgMatrix.resultOptionOfAll" bundle="sys-rule" /></label>
							<label style="margin-right:10px;"><input type="radio" name="matrixResultOption" value="3">
								<kmss:message key="rule.orgMatrix.resultOptionOfError" bundle="sys-rule" /></label>
						</div>
						<div style="margin-top:6px;padding:2px;width:100%;height:80%;">
							<table id="conditionParamList" class="tb_normal" width="97%">
								<tr>
									<td width="75%"><kmss:message key="rule.orgMatrix.conditionParam" bundle="sys-rule" /></td>
									<td width="25%" align="center">
										<a href="javascript:void(0)" onclick="addMatrixOrgConditionParamRow();"><img src="${KMSS_Parameter_StylePath}icons/add.gif" title="<kmss:message key="rule.orgMatrix.operationAdd" bundle="sys-rule" />"></a>
									</td>
								</tr>
								<tr KMSS_IsReferRow="1" style="display:none">
									<td>
										<select name="conditionParam" onchange="conditionParamChange(this);" style="width:100%">
										</select>
										<select name="conditionType" style="width:35%;display:none">
											<option value="fdId">ID</option>
											<option value="fdName">Name</option>
										</select>
									</td>
									<td align="center">
										<a href="javascript:void(0)" onclick="DocList_DeleteRow();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" title="<kmss:message key="rule.orgMatrix.operationDelete" bundle="sys-rule" />"></a>
									</td>
								</tr>
							</table>
						</div>
						<!-- <div id="orgMatrixTip" style="display:none;color: #FF0000;">该规则已经被引用，矩阵组织不能再修改！</div> -->
					</div>
				</td>
				<td width="85%" colspan="3" id="extendArea" style="display:none">
					<!-- <select id="behaviorType" onchange="changeBehaviorType(this);">
						<option value="">请选择</option>
					</select>
					<select id="behavior" onchange="changebehavior(this);">
						<option value="">请选择</option>
					</select> 
					<span id="extendResultArea">
						<input type="hidden" name="fdResult">
						<input type="hidden" name="fdDisResult">
					</span>
					-->
				</td>
			</tr>
		</table>
		<div class="button-group">
			<!-- 确认 -->
			<input class="btn resultBtn" type="button" value="${lfn:message('button.ok')}" onclick="checkContent();">
			<!-- 取消 -->
			<input class="btn resultBtn" type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();">
	   	</div>
	</div>
</body>
</html>