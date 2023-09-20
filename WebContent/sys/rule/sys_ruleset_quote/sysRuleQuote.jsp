<%@page import="com.landray.kmss.sys.rule.forms.ISysRuleTemplateForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<div class="rule ${param.key }" style="display:none;">
	<input name="reference.type" value="${param.type }" type="hidden"/>
	<input name="mapContent" type="hidden"/>
	<input name="lastMapContent" type="hidden"/>
	<input name="sysRuleQuoteInfoId" type="hidden">
	<input name="ruleId" type="hidden"/>
	<input name="ruleName" class="inputsgl" readonly style="width:80%"/>
	<a href="#" onclick="selectRule('${param.returnType}','${param.mode }', '${param.key }')">选择</a><br/>
	<span class="alreadyMapType" style="display:none;">
		<input name="alreadyMapId" type="hidden""/>
		<input name="alreadyMapName" class="inputsgl" readonly style="width:80%"/>
		<a href="#" style="color: inherit;text-decoration: none;background-color: transparent">映射内容</a><br/>
	</span>
	<span class="mapArea" style="display:none;padding-top:10px;">
		<table class="tb_normal mapTable" width="100%">
			<tr class="tr_normal_title">
				<td width="300px;">参数</td>
				<td width="200px">字段</td>
			</tr>
			<tr class="pivotRow">
				<td>
					<input type="hidden" name="mapId">
					<input type="hidden" name="ruleSetParamId">
					<xform:text property="ruleSetParamName" showStatus="readOnly" style="width:90%;color:black;border:0">
					</xform:text>
				</td>
				<td>
					<input type="hidden" name="xformFieldId">
					<input type="hidden" readonly="readonly" class="inputsgl" name="xformFieldName">
					<xform:select property="xformField" showStatus="edit" style="width:90%" htmlElementProperties="onchange=updateMapContent(this.value,this,'${param.key }')">
					</xform:select>
				</td>
			</tr>
		</table>
	</span>
</div>