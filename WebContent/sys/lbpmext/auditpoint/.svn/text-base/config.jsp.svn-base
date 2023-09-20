<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="submitForm();">
</div>
<script type="text/javascript">
function submitForm(){
	Com_Submit(document.sysAppConfigForm, 'update');
}
</script>
<p class="txttitle"><bean:message key="table.lbpmExtAuditPoint.config" bundle="sys-lbpmext-auditpoint" /></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="lbpmExtAuditPoint.config.print.set" bundle="sys-lbpmext-auditpoint" />
		</td><td colspan=1>
			<xform:radio showStatus="edit" property="value(isPrintShow)">
				<xform:enumsDataSource enumsType="common_yesno"/>
			</xform:radio>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>