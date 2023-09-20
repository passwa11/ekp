<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirm_delete(msg){
		var del = confirm("<bean:message key='page.comfirmDelete'/>");
		return del;
	}
</script>

<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuthInfoCate" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuthInfoCate.set" subject="${lbpmExtBusinessAuthInfoCateForm.fdName}" />
<div id="optBarDiv">
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfoCate.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.edit"/>"
			onClick="Com_OpenWindow('lbpmBusinessAuthInfoCate.do?method=edit&fdId=<bean:write name="lbpmExtBusinessAuthInfoCateForm" property="fdId" />','_self');">
	</kmss:auth>
	<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfoCate.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button" value="<bean:message key="button.delete"/>"
			onClick="if(!confirm_delete())return;Com_OpenWindow('lbpmBusinessAuthInfoCate.do?method=delete&fdId=<bean:write name="lbpmExtBusinessAuthInfoCateForm" property="fdId" />','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message key='table.lbpmext.businessAuthInfoCate' bundle='sys-lbpmext-businessauth' /></p>
<center>
<table class="tb_normal" width=95%>
	<tr>	
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdParent"/>
		</td>
		<td width=85%  colspan="3">
			<bean:write name="lbpmExtBusinessAuthInfoCateForm" property="fdParentName"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfoCate.fdName"/>
		</td>
		<td width=85% colspan="3">
			<bean:write name="lbpmExtBusinessAuthInfoCateForm" property="fdName"/>
		</td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfoCate.authEditors"/></td>
		<td width=85% colspan="3">
		  <kmss:showText value="${lbpmExtBusinessAuthInfoCateForm.authEditorNames}"/>
		</td>
	</tr>
	<!-- 业务授权-授权分类；新增编号规则开始 -->
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.fdNumber"/>
		</td>
		<td width=85% colspan="3">
			<c:choose>
				<c:when test="${lbpmExtBusinessAuthInfoCateForm.sysNumberMainMappForm.fdType == '2'}">
					<c:import url="/sys/number/import/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="lbpmExtBusinessAuthInfoCateForm" />
						<c:param name="modelName" value="com.landray.kmss.sys.lbpmext.businessauth.model.LbpmExtBusinessAuthInfo"/>
					</c:import>
					<script>
						$(function(){
							$("#TR_ID_sysNumberMainMapp_showNumber").parent().children("tr:first-child").hide();
							number_LoadIframe();
						})
					</script>
				</c:when>
				<c:otherwise>
					<span class="txtstrong">
						<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfoCate.tip"/>
					</span>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<!-- 业务授权-授权分类；新增编号规则结束 -->
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message key="model.fdCreator" />
		</td>
		<td width=35%>
			${ lbpmExtBusinessAuthInfoCateForm.fdCreatorName}
		</td>
		<td width=15% class="td_normal_title">
			<bean:message key="model.fdCreateTime" />
		</td>
		<td width=35%>
			<bean:write name="lbpmExtBusinessAuthInfoCateForm" property="fdCreateTime" format="yyyy-MM-dd HH:mm"/>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message key="model.docAlteror" />
		</td>
		<td width=35%>
			${ lbpmExtBusinessAuthInfoCateForm.fdAlterorName}
		</td>
		<td width=15% class="td_normal_title">
			<bean:message key="model.fdAlterTime" />
		</td>
		<td width=35%>
			<bean:write name="lbpmExtBusinessAuthInfoCateForm" property="fdAlterTime" format="yyyy-MM-dd HH:mm"/>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>