<%@page import="com.landray.kmss.sys.lbpmext.attention.forms.LbpmExtAttentionForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<style>
.lui_businessauth_frame{
	height: 38px;
    line-height: 38px;
} 
.lui_businessauth_frame .lui_toolbar_btn_l{
	text-align: center;
    border: 1px #bbbbbb solid;
    background-color: #fff;
}
.lui_businessauth_frame .lui_widget_btn_txt{
	color: #666 !important;
}
.lui_businessauth_frame .lui_widget_btn:hover .lui_widget_btn_txt {
    color: #fff !important;
}
.btn_txt{
    color: #2574ad;
    border-bottom: 1px solid transparent
}
</style>

<html:form action="/sys/lbpmext/attention/lbpmExtAttention.do" onsubmit="return true;">
<%
	String currentUserId = UserUtil.getUser().getFdId();
	pageContext.setAttribute("currentUserId", currentUserId);
%>
<div id="optBarDiv"> 
	<c:if test="${lbpmExtAttentionForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="validateSubmitForm('update');">
	</c:if>
	<c:if test="${lbpmExtAttentionForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="validateSubmitForm('save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<kmss:windowTitle 
	moduleKey="sys-lbpmext-attention:table.lbpmExtAttention"/>

<p class="txttitle"><bean:message  bundle="sys-lbpmext-attention" key="table.lbpmExtAttention"/></p>

<center>
<table class="tb_normal" width=95%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" id="fdAttentionPersonTitle" width=15%>
			<bean:message  bundle="sys-lbpmext-attention" key="lbpmExtAttention.attention.fdPerson"/>
		</td>
		<td width=35% id='fdAttentionPerson'>
			<xform:address propertyId="fdPersonId" propertyName="fdPersonName" orgType="ORG_TYPE_PERSON" 
				showStatus="edit" subject="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.attention.fdPerson') }" validators="comparePerson checkUniquePerson" required="true" style="width:90%">
			</xform:address>
		</td>
	</tr>
	<tr class="lbpm_attention_row" id="lbpmAttentionRow">
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmext-attention" key="table.lbpmExtAttentionScope"/>
		</td>
		<td width=85% colspan=3>
			<html:hidden property="fdScopeFormAttentionCateIds"/>
			<html:hidden property="fdScopeFormAttentionCateNames"/>
			<html:hidden property="fdScopeFormModelNames"/>
			<html:hidden property="fdScopeFormModuleNames"/>
			<html:hidden property="fdScopeFormTemplateIds"/>
			<html:hidden property="fdScopeFormTemplateNames"/>
			<html:hidden property="scopeTempValues"/>
			<textarea style="width:90%" readonly required name="fdScopeFormAttentionCateShowtexts">${lbpmExtAttentionForm.fdScopeFormAttentionCateShowtexts}</textarea>
			<br>
			<a href="#"
				onclick="importAttentionCateDialog();">
				<bean:message key="dialog.selectOther" /></a>
			<bean:message key="message.lbpmExtAttentionScope.note" bundle="sys-lbpmext-attention"/>
		</td>
	</tr>
	
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
</html:form>
<html:javascript formName="lbpmExtAttentionForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<script>
</script>
<%@ include file="/sys/lbpmext/attention/lbpmext_attention/lbpmExtAttention_script.jsp"%>
<%@ include file="/resource/jsp/edit_down.jsp"%>