<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="title"><bean:message key="sysRuleSetRule.fdCondition.setting" bundle="sys-rule"/></template:replace>
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link rel="stylesheet" href="${LUI_ContextPath}/resource/style/default/doc/document.css">
		<link rel="stylesheet" href="${LUI_ContextPath}/sys/rule/resources/css/buttons.css">
		<script src="${ LUI_ContextPath }/sys/ui/extend/template/module/simple.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript">
		Com_IncludeFile("doclist.js|data.js|dialog.js|calendar.js|formula.js");
		</script>
		<script type="text/javascript" src="${ LUI_ContextPath }/sys/rule/resources/js/common.js"></script>
		<script type="text/javascript" src="${ LUI_ContextPath }/sys/rule/resources/js/control.js"></script>
		<script type="text/javascript" src="${ LUI_ContextPath }/sys/rule/resources/js/dialog_condition_edit.js"></script>
	</template:replace>
	<template:replace name="body">
		<div style="margin-top:10px">
			<p class="txttitle"><bean:message key="sysRuleSetRule.fdCondition.setting" bundle="sys-rule"/></p>
			<table width="95%" class="tb_normal">
				<tr>
					<td width="15%" class="td_normal_title"><bean:message key="sysRuleSetRule.fdConditionMode" bundle="sys-rule"/></td>
					<td width="85%" colspan="3">
						<label><input name="fdConditionMode" type="radio" checked="checked" value="formula"/><bean:message key="sysRuleSetRule.mode.formula" bundle="sys-rule"/></label>
						<label><input name="fdConditionMode" type="radio" value="fixed"/><bean:message key="sysRuleSetRule.mode.fixedValue" bundle="sys-rule"/></label>
						<label style='display:none'><input name="fdConditionMode" type="radio" value="org"/><bean:message key="sysRuleSetRule.mode.org" bundle="sys-rule"/></label>
					</td>
				</tr>
				<tr>
					<td width="15%" class="td_normal_title"><bean:message key="sysRuleSetRule.fdCondition" bundle="sys-rule"/></td>
					<td width="85%" colspan="3" id="cdControlArea">
						
					</td>
					<td width="85%" colspan="3" id="orgArea" style="display:none">
						<xform:select property="param" required="true" onValueChange="selectParam" showStatus="edit" style="width:200px"></xform:select>
						<span><xform:select property="orgCompare" showPleaseSelect="false" showStatus="edit" onValueChange="switchFun">
							<xform:enumsDataSource enumsType="sys_rule_org_compare"></xform:enumsDataSource>
						</xform:select></span>
						<span id="pControlArea"></span>
						<br><span id="funcDescArea"></span>
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
	</template:replace>
</template:include>