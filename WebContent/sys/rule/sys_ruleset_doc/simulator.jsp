<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link rel="stylesheet" href="${LUI_ContextPath}/sys/rule/resources/css/buttons.css">
<script type="text/javascript" src="${LUI_ContextPath}/sys/rule/resources/js/common.js"></script>
<script type="text/javascript" src="${LUI_ContextPath}/sys/rule/resources/js/formula_common.js"></script>
<script type="text/javascript" src="${LUI_ContextPath}/sys/rule/resources/js/simulator.js"></script>
<script type="text/javascript">
seajs.use(['theme!form']);
Com_IncludeFile("document.css", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile('common.js|jquery.js|plugin.js|data.js|dialog.js|calendar.js');
var message_unknowfunc = '<bean:message bundle="sys-formula" key="validate.unknowfunc"/>';
var message_unknowvar = '<bean:message bundle="sys-formula" key="validate.unknowvar"/>';
var message_wait = '<bean:message bundle="sys-formula" key="validate.wait"/>';
var message_eval_error = '<bean:message bundle="sys-formula" key="validate.failure.evalError"/>';
var message_insert_formula = '<bean:message bundle="sys-formula" key="formula.link.insertFormula"/>';
</script>
<title>
</title>
</head>
<body class="lui_form_body" style="background-color:white">
	<!-- 错误信息返回页面 -->
	<c:import url="/resource/jsp/error_import.jsp" charEncoding="UTF-8" ></c:import>
	<p class="txttitle" style="margin-top:10px"><bean:message key="sysRuleSetDoc.simulator.title" bundle="sys-rule"/></p>
	<table class="tb_normal" width=95% style="margin-top:10px">
		<tr>
			<td width=15% class="td_normal_title">
					<bean:message bundle="sys-rule" key="button.select.sysRuleSetDoc"/>
			</td>
			<td width=85% colspan="3">
				<input name="sysRuleSetDoc.fdId" type="hidden"/>
				<input name="sysRuleSetDoc.fdName" class="inputsgl" readonly style="width:80%"/>
				<a href="#" onclick="selectRuleSet()"><bean:message bundle="sys-rule" key="button.select"/></a><br/>
			</td>
		</tr>
		<tr>
			<td width=15% class="td_normal_title">
				<bean:message key="sysRuleSetDoc.simulator.returnType" bundle="sys-rule"/>
			</td>
			<td width=85% colspan="3">
				<xform:select showStatus="edit" property="sysRuleSetRule.returnType" htmlElementProperties="id=returnType" value="" style="width:80%" onValueChange="switchRtnType">
					<xform:enumsDataSource enumsType="sys_rule_return_type" />
				</xform:select>
			</td>
		</tr>
		<tr>
			<td width=15% class="td_normal_title">
				<bean:message bundle="sys-rule" key="simulator.mode"/>
			</td>
			<td width=85% colspan="3">
				<div class="resultOption" style="margin-top:5px">
					<bean:message bundle="sys-rule" key="simulator.result.tip"/>
					<label style="margin-right:10px;"><input type="radio" name="fdResultOption" value="1" checked="">
						<bean:message bundle="sys-rule" key="simulator.mode.one"/></label>
					<label style="margin-right:10px;"><input type="radio" name="fdResultOption" value="2">
						<bean:message bundle="sys-rule" key="simulator.mode.all"/></label>
				</div>
			</td>
		</tr>
   		<tr id="variable">
	    	<td width="10%" class="td_normal_title"><bean:message bundle="sys-formula" key="formula.simulaVars"/>:</td>
	    	<td width="90%">
	    		<table width="100%" class="tb_normal" border="0">
	    			
	    		</table>
	    	</td>
	    </tr>
	    <!-- 结果区域 -->
	    <tr id="result">
	    	<td width="10%"  class="td_normal_title"><bean:message bundle="sys-formula" key="formula.simulaResult"/>:</td>
	    	<td width="90%">
	    		<table width="100%" class="tb_normal" border="0">
	    			<textarea id="resultArea" name="resultArea" readonly style="width:90%;height:80px;"></textarea>
	    		</table>
	    	</td>
	    </tr>
	    <!-- 按钮 -->
	    <tr>
	    	<td align=center colspan="2">
	    		<div class="button-group">
					<!-- 开始模拟按钮 -->
					<input class="btn resultBtn" id="startSimulaFormula" type="button" value="${lfn:message('sys-rule:button.simulator.start')}" onclick="startSimulateFormulaNew(this);">
			   	</div>
	    	</td>
	    </tr>
	</table>
</body>
</html>