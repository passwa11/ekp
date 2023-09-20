<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
		<kmss:auth requestURL="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('sysSmsUpperlimit.do?method=edit&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/sms/sys_sms_upperlimit/sysSmsUpperlimit.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete())return;Com_OpenWindow('sysSmsUpperlimit.do?method=delete&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message  bundle="sys-sms" key="table.sysSmsUpperlimit"/></p>
<center>
<table class="tb_normal" width=95%>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-sms" key="sysSmsLimitperson.fdPersonsId"/>
		</td><td colspan="3">
			<c:out value="${sysSmsUpperlimitForm.theSmsPersonNames}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-sms" key="sysSmsUpperlimit.fdByperiod"/>
		</td><td width=35%>
			<sunbor:enumsShow value="${sysSmsUpperlimitForm.fdByperiod}" enumsType="sysSmsUpperlimitByperiods" bundle="sys-sms" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-sms" key="sysSmsUpperlimit.fdUpperlimit"/>
		</td><td width=35%>
			<c:out value="${sysSmsUpperlimitForm.fdUpperlimit}" />
		</td>

	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-sms" key="sysSmsUpperlimit.docCreatorId"/>
		</td><td width=35%>
			<c:out value="${sysSmsUpperlimitForm.docCreatorName}" />
		</td>	
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-sms" key="sysSmsUpperlimit.docCreateTime"/>
		</td><td width=35%>
			<c:out value="${sysSmsUpperlimitForm.docCreateTime}" />
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
