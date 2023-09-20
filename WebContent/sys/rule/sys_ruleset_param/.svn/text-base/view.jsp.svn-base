<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="tb_normal" width="100%">
	<tr class="tr_normal_title">
		<td width="50px" align="center">
			<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
		</td>
		<td width="250px"><kmss:message key="sys-rule:sysRuleSetParam.fdName" /></td>
		<td width="300px"><kmss:message key="sys-rule:sysRuleSetParam.fdType" /></td>
	</tr>
	<%-- 内容 --%>
	<c:forEach items="${sysRuleSetDocForm.sysRuleSetParams}" var="sysRuleSetParamForm" varStatus="vstatus">
	<tr>
		<td align="center">
			${vstatus.index + 1}
		</td>
		<td align="center">
			<html:hidden property="sysRuleSetParams[${vstatus.index}].fdId" value="${sysRuleSetParamForm.fdId }"/>
			<xform:text property="sysRuleSetParams[${vstatus.index}].fdName"  value="${sysRuleSetParamForm.fdName}" style="width:90%" showStatus="view"></xform:text>
		</td>
		<td align="center">
			<xform:select showStatus="view" property="sysRuleSetParams[${vstatus.index}].fdType" value="${sysRuleSetParamForm.fdType}"  style="width:90%;">
				<xform:enumsDataSource enumsType="sys_rule_param_type" />
			</xform:select>
			<%-- <div class="isMulti" style="display:none">
				<xform:checkbox property="sysRuleSetParams[${vstatus.index}].isMulti" value="${sysRuleSetParamForm.isMulti}" style="display:none">
					<xform:simpleDataSource value="1" ><bean:message key="sysRuleSetParam.isMulti" bundle="sys-rule"/></xform:simpleDataSource>
				</xform:checkbox>
			</div> --%>
			<c:if test="${sysRuleSetParamForm.isMulti == '1'}">
				<span>（多值）</span>
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<script type="text/javascript">
</script>