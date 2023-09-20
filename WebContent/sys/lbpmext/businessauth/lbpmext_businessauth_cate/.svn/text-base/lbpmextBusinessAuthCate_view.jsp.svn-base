<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirm_delete(msg){
		var del = confirm("<bean:message key='page.comfirmDelete'/>");
		return del;
	}
</script>

<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuthCate" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuthCate.set" subject="${lbpmExtBusinessAuthCateForm.fdName}" />
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>"
			onClick="Com_OpenWindow('lbpmBusinessAuthCate.do?method=edit&fdId=<bean:write name="lbpmExtBusinessAuthCateForm" property="fdId" />','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.delete"/>"
			onClick="if(!confirm_delete())return;Com_OpenWindow('lbpmBusinessAuthCate.do?method=delete&fdId=<bean:write name="lbpmExtBusinessAuthCateForm" property="fdId" />','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message key='table.lbpmext.businessAuthCate' bundle='sys-lbpmext-businessauth' /></p>
<center>
<table class="tb_normal" width=95%>
	<tr>	
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdParent"/>
		</td>
		<td width=85%  colspan="3">
			<bean:write name="lbpmExtBusinessAuthCateForm" property="fdParentName"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.fdName"/>
		</td>
		<td width=85% colspan="3">
			<bean:write name="lbpmExtBusinessAuthCateForm" property="fdName"/>
		</td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.authEditors"/></td>
		<td width=85% colspan="3">
		  <kmss:showText value="${lbpmExtBusinessAuthCateForm.authEditorNames}"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.fdNumber"/>
		</td><td width=85% colspan="3">
			<c:import url="/sys/number/import/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="lbpmExtBusinessAuthCateForm" />
				<c:param name="modelName" value="com.landray.kmss.sys.lbpmext.businessauth.model.LbpmExtBusinessAuth"/>
			</c:import>
			<script>
				$(function(){
					$("#TR_ID_sysNumberMainMapp_showNumber").parent().children("tr:first-child").hide();
					number_LoadIframe();
				})
			</script>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message key="model.fdCreator" />
		</td>
		<td width=35%>
			${ lbpmExtBusinessAuthCateForm.fdCreatorName}
		</td>
		<td width=15% class="td_normal_title">
			<bean:message key="model.fdCreateTime" />
		</td>
		<td width=35%>
			<bean:write name="lbpmExtBusinessAuthCateForm" property="fdCreateTime" format="yyyy-MM-dd HH:mm"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message key="model.docAlteror" />
		</td>
		<td width=35%>
			${ lbpmExtBusinessAuthCateForm.fdAlterorName}
		</td>
		<td width=15% class="td_normal_title">
			<bean:message key="model.fdAlterTime" />
		</td>
		<td width=35%>
			<bean:write name="lbpmExtBusinessAuthCateForm" property="fdAlterTime" format="yyyy-MM-dd HH:mm"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>