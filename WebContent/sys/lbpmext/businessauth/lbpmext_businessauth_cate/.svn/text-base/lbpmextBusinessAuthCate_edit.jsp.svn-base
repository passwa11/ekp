<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuthCate" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuthCate.set" subject="${lbpmExtBusinessAuthCateForm.fdName}" />
<html:form action="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do">
<div id="optBarDiv">
	<logic:equal name="lbpmExtBusinessAuthCateForm" property="method_GET" value="edit">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do?method=update&fdId=${lbpmExtBusinessAuthCateForm.fdId}">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthCateForm, 'update');">
		</kmss:auth>
	</logic:equal>
	<logic:equal name="lbpmExtBusinessAuthCateForm" property="method_GET" value="add">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do?method=save">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthCateForm, 'save');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuthCate.do?method=saveadd">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthCateForm, 'saveadd');">
		</kmss:auth>
	</logic:equal>
<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.set"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.fdParent"/>
		</td><td width=85% colspan="3">
			<html:hidden property="fdParentId"/>
			<html:text property="fdParentName" readonly="true" styleClass="inputsgl" style="width:80%"/>
			<a href="#" onclick="Dialog_Tree(
				false,
				'fdParentId',
				'fdParentName',
				null,
				'lbpmExtBusinessAuthCateService&parentId=!{value}',
				'<bean:message bundle="sys-lbpmext-businessauth" key="table.lbpmext.businessAuthCate"/>',
				null,
				null,
				'<bean:write name="lbpmExtBusinessAuthCateForm" property="fdId"/>');">
				<bean:message key="dialog.selectOther"/>
			</a>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.fdName"/>
		</td><td width=85% colspan="3">
			<xform:text property="fdName" style="width:80%" required="true"></xform:text>
		</td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.authEditors"/></td>
		<td width=85% colspan="3"><html:hidden property="authEditorIds" /> <html:textarea
			property="authEditorNames" style="width:90%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null);"><bean:message
			key="dialog.selectOther" /></a><%-- <br>
			<bean:message bundle="sys-rule" key="sysRuleSetCate.authEditors.describe"/> --%>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate.fdNumber"/>
		</td><td width=85% colspan="3">
			<c:import url="/sys/number/import/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="lbpmExtBusinessAuthCateForm" />
				<c:param name="fdKey" value="lbpmExtBusinessAuthCate" />
				<c:param name="modelName" value="com.landray.kmss.sys.lbpmext.businessauth.model.LbpmExtBusinessAuth"/>
			</c:import>
			<script>
				$(function(){
					$("#TR_ID_sysNumberMainMapp_showNumber").parent().children("tr:first-child").hide();
				})
			</script>
		</td>
	</tr>
	<%---新建时，不显示 创建人，创建时间 ---%>
   <c:if test="${lbpmExtBusinessAuthCateForm.method_GET=='edit'}">
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
		<c:if test="${not empty lbpmExtBusinessAuthCateForm.fdAlterorName}">
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
<html:javascript formName="lbpmExtBusinessAuthCateForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>