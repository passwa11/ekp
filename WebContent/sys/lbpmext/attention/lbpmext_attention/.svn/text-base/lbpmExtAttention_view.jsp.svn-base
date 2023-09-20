<%@page import="com.landray.kmss.sys.lbpmext.attention.forms.LbpmExtAttentionForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
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
	<c:if test="${ currentUserId ne lbpmExtAttentionForm.fdPersonId}">
		<kmss:auth requestURL="/sys/lbpmext/attention/lbpmExtAttention.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				   value="<bean:message key="button.edit"/>"
				   onclick="Com_OpenWindow('lbpmExtAttention.do?method=edit&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
	</c:if>

	<%-- 	<kmss:auth requestURL="/sys/lbpmext/attention/lbpmExtAttention.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<input type="button" 
				value="<bean:message key="button.cancel" bundle="sys-lbpmext-attention"/>"
				onclick="if(!confirmDelete('${JsParam.fdId}'))return;Com_OpenWindow('lbpmExtAttention.do?method=delete&fdId=${JsParam.fdId}','_self');">
		</kmss:auth> --%>
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
				showStatus="view" subject="${lfn:message('sys-lbpmext-attention:lbpmExtAttention.attention.fdPerson') }" required="true" style="width:90%">
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
<%@ include file="/resource/jsp/view_down.jsp"%>