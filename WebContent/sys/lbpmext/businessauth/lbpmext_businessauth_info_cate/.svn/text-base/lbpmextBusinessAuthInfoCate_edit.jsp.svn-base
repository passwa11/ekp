<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuthInfoCate" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuthInfoCate.set" subject="${lbpmExtBusinessAuthInfoCateForm.fdName}" />
<html:form action="/sys/lbpmext/businessauth/lbpmBusinessAuthInfoCate.do">
<div id="optBarDiv">
	<logic:equal name="lbpmExtBusinessAuthInfoCateForm" property="method_GET" value="edit">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfoCate.do?method=update&fdId=${lbpmExtBusinessAuthInfoCateForm.fdId}">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthInfoCateForm, 'update');">
		</kmss:auth>
	</logic:equal>
	<logic:equal name="lbpmExtBusinessAuthInfoCateForm" property="method_GET" value="add">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfoCate.do?method=save">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthInfoCateForm, 'save');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthInfoCate.do?method=saveadd">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthInfoCateForm, 'saveadd');">
		</kmss:auth>
	</logic:equal>
<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfoCate.set"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfoCate.fdParent"/>
		</td><td width=85% colspan="3">
			<html:hidden property="fdParentId"/>
			<html:text property="fdParentName" readonly="true" styleClass="inputsgl" style="width:80%"/>
			<a href="#" onclick="Dialog_Tree(
				false,
				'fdParentId',
				'fdParentName',
				null,
				'lbpmExtBusinessAuthInfoCateService&parentId=!{value}',
				'<bean:message bundle="sys-lbpmext-businessauth" key="table.lbpmext.businessAuthInfoCate"/>',
				null,
				null,
				'<bean:write name="lbpmExtBusinessAuthInfoCateForm" property="fdId"/>');">
				<bean:message key="dialog.selectOther"/>
			</a>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfoCate.fdName"/>
		</td><td width=85% colspan="3">
			<xform:text property="fdName" style="width:80%" required="true"></xform:text>
		</td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfoCate.authEditors"/></td>
		<td width=85% colspan="3"><html:hidden property="authEditorIds" /> <html:textarea
			property="authEditorNames" style="width:90%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null);"><bean:message
			key="dialog.selectOther" /></a><%-- <br>
			<bean:message bundle="sys-rule" key="sysRuleSetCate.authEditors.describe"/> --%>
		</td>
	</tr>
	<!-- 业务授权-授权分类；新增编号规则开始 -->
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.fdNumber"/>
		</td>
		<td width=85% colspan="3">
				<c:import url="/sys/number/import/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="lbpmExtBusinessAuthInfoCateForm" />
					<c:param name="fdKey" value="lbpmExtBusinessAuthInfoCate" />
					<c:param name="modelName" value="com.landray.kmss.sys.lbpmext.businessauth.model.LbpmExtBusinessAuthInfo"/>
				</c:import>
				<script>
					$(function(){
						$("#TR_ID_sysNumberMainMapp_showNumber").parent().children("tr:first-child").hide();
					})
				</script>
		</td>
	</tr>
	<!-- 业务授权-授权分类；新增编号规则结束 -->
	<%---新建时，不显示 创建人，创建时间 ---%>
   <c:if test="${lbpmExtBusinessAuthInfoCateForm.method_GET=='edit'}">
		<tr>
			<!-- 创建人员 -->
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdCreator" />
			</td>
			<td width=35%>
				<html:text property="fdCreatorName" readonly="true" style="width:50%;" />
			</td>
			
			<!-- 创建时间 -->
			<td class="td_normal_title" width=15%>
				<bean:message key="model.fdCreateTime" />
			</td>
			<td width=35%>
				<html:text property="fdCreateTime" readonly="true" style="width:50%;" />
			</td>
		</tr>
		<c:if test="${not empty lbpmExtBusinessAuthInfoCateForm.fdAlterorName}">
			<tr>
				<!-- 修改人 -->
				<td class="td_normal_title" width=15%>
					<bean:message key="model.docAlteror" />
				</td>
				<td width=35%>
					<html:text property="fdAlterorName" readonly="true" style="width:50%;" />
				</td>
				
				<!-- 修改时间 -->
				<td class="td_normal_title" width=15%>
					<bean:message key="model.fdAlterTime" />
				</td>
				<td width=35%>
					<html:text property="fdAlterTime" readonly="true" style="width:50%;" />
				</td>
			</tr>
		</c:if>
	</c:if> 
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
</html:form>
<html:javascript formName="lbpmExtBusinessAuthInfoCateForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>