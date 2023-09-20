<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	Com_IncludeFile("calendar.js");
</script>
<!-- 动态列表表格，组件，ID根据DocList_Info -->
<table id="ruleSetting" class="tb_normal" width="100%">
	<tr class="tr_normal_title">
		<td width="70px;" align="center">
			<span style="white-space:nowrap;"><kmss:message key="sys-rule:sysRuleSetRule.executionOrder" /></span>
		</td>
		<td width="170px;"><kmss:message key="sys-rule:sysRuleSetRule.fdName" /></td>
		<td width="275px;"><kmss:message key="sys-rule:sysRuleSetRule.fdCondition"/></td>
		<td width="110px"><kmss:message key="sys-rule:sysRuleSetRule.returnType" /></td>
		<td width="275px"><kmss:message key="sys-rule:sysRuleSetRule.fdResult"/></td>
		<td width="100px">
			<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="DocList_AddRow('ruleSetting');" style="cursor:pointer">
		</td>
	</tr>
	<%-- 基准行，KMSS_IsReferRow = 1 --%>
	<tr style="display:none;" KMSS_IsReferRow="1">
		<!-- 序号列，KMSS_IsRowIndex = 1 -->
		<td KMSS_IsRowIndex="1" align="center">
			<xform:text property="sysRuleSetRules[!{index}].fdOrder" value="!{index}"></xform:text>
		</td>
		<td>
			<input type="hidden" name="sysRuleSetRules[!{index}].fdId">
			<input type="hidden" name="sysRuleSetRules[!{index}].fdOrder">
			<input type="hidden" name="sysRuleSetRules[!{index}].sysRuleSetParamIds" />
			<input type="hidden" name="sysRuleSetRules[!{index}].sysRuleSetParamNames" />
			<xform:text showStatus="edit" property="sysRuleSetRules[!{index}].fdName" subject="${lfn:message('sys-rule:sysRuleSetRule.fdName') }" required="true" style="width:90%"></xform:text>
		</td>
		<td class="controlArea">
			<xform:text property="sysRuleSetRules[!{index}].fdDisCondition" showStatus="readOnly" style="width: 80%;"></xform:text>
			<a href="#" onclick="editConditionContent()"><bean:message key="button.setting" bundle="sys-rule"/></a>
			<input name="sysRuleSetRules[!{index}].fdCondition" type="hidden"/>
			<input name="sysRuleSetRules[!{index}].fdConditionMode" type="hidden"/>
		</td>
		<td>
			<xform:select showStatus="edit" property="sysRuleSetRules[!{index}].returnType" required="true" style="width:90%;" subject="${lfn:message('sys-rule:sysRuleSetRule.returnType') }" onValueChange="switchRtnType">
				<xform:enumsDataSource enumsType="sys_rule_return_type" />
			</xform:select>
			<div class="isMulti" style="display:none">
				<xform:checkbox showStatus="edit" property="sysRuleSetRules[!{index}].isMulti" style="display:none"  onValueChange="switchMulti">
					<xform:simpleDataSource value="1" ><bean:message key="sysRuleSetRule.isMulti" bundle="sys-rule"/></xform:simpleDataSource>
				</xform:checkbox>
			</div>
		</td>
		<td class="controlArea">
			<xform:text property="sysRuleSetRules[!{index}].fdDisResult" showStatus="readOnly" style="width: 80%;"></xform:text>
			<a href="#" onclick="editResultContent()"><bean:message key="button.setting" bundle="sys-rule"/></a>
			<input name="sysRuleSetRules[!{index}].fdResult" type="hidden"/>
			<input name="sysRuleSetRules[!{index}].fdResultMode" type="hidden"/>
		</td>
		<td>
			<div style="text-align:center">
				<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="DocList_AddRow('ruleSetting');" style="cursor:pointer">
				<img src="<c:url value="/resource/style/default/icons/up.gif"/>" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
				<img src="<c:url value="/resource/style/default/icons/down.gif"/>" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer;margin-left:2px;">
				<img src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">
			</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
DocList_Info.push('ruleSetting');
//切换返回值类型
function switchRtnType(value, obj){
	ruleSetRule.switchRtnType(value, obj);
}
//设置条件内容
function editConditionContent(){
	ruleSetRule.editConditionContent();
}
//设置结果内容
function editResultContent(){
	ruleSetRule.editResultContent();
}
//切换多值
function switchMulti(value, obj){
	ruleSetRule.switchMulti(value, obj[0]);
}
</script>