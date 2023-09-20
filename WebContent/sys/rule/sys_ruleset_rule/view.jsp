<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="tb_normal" width="100%">
	<tr class="tr_normal_title">
		<td width="50px;" align="center">
			<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
		</td>
		<td width="200px;"><kmss:message key="sys-rule:sysRuleSetRule.fdName" /></td>
		<td width="300px"><kmss:message key="sys-rule:sysRuleSetRule.fdCondition"/></td>
		<td width="150px"><kmss:message key="sys-rule:sysRuleSetRule.returnType" /></td>
		<td width="300px"><kmss:message key="sys-rule:sysRuleSetRule.fdResult"/></td>
	</tr>
	<%-- 内容 --%>
	<c:forEach items="${sysRuleSetDocForm.sysRuleSetRules}" var="sysRuleSetRuleForm" varStatus="vstatus">
	<tr>
		<td align="center">
			${vstatus.index + 1}
		</td>
		<td align="center">
			<html:hidden property="sysRuleSetRules[${vstatus.index}].fdId" value="${sysRuleSetRuleForm.fdId }"/>
			<input type="hidden" name="sysRuleSetRules[${vstatus.index}].fdOrder" value="${sysRuleSetRuleForm.fdOrder}">
			<xform:text showStatus="view" property="sysRuleSetRules[${vstatus.index}].fdName" value="${sysRuleSetRuleForm.fdName}" style="width:90%"></xform:text>
		</td>
		<td align="center">
			<xform:text showStatus="view" property="sysRuleSetRules[${vstatus.index}].fdDisCondition" style="width:95%" value='${sysRuleSetRuleForm.fdDisCondition }'></xform:text>
			<html:hidden property="sysRuleSetRules[${vstatus.index}].fdCondition" value='${sysRuleSetRuleForm.fdCondition }'/>
		</td>
		<td align="center">
			<input type="hidden" value="${sysRuleSetRuleForm.returnType }"/>
			<xform:select showStatus="view" property="sysRuleSetRules[${vstatus.index}].returnType" value="${sysRuleSetRuleForm.returnType}" style="width:90%;border:0">
				<xform:enumsDataSource enumsType="sys_rule_return_type" />
			</xform:select>
			<c:if test="${sysRuleSetRuleForm.isMulti == '1'}">
				<span>（多值）</span>
			</c:if>
		</td>
		<td align="center">
			<xform:text showStatus="view" property="sysRuleSetRules[${vstatus.index}].fdDisResult" style="width:95%" value='${sysRuleSetRuleForm.fdDisResult }'></xform:text>
			<html:hidden property="sysRuleSetRules[${vstatus.index}].fdResult" value='${sysRuleSetRuleForm.fdResult }'/>
		</td>
	</tr>
	</c:forEach>
</table>
<script type="text/javascript">
</script>