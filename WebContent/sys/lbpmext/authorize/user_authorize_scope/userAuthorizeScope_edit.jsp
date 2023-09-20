<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/lbpmext/authorize/user_authorize_scope/userAuthorizeScope_script.jsp"%>
<html:form action="/sys/lbpmext/authorize/lbpm_authorize/userAuthorizeScope.do">
<%
	String currentUserId = UserUtil.getUser().getFdId();
	pageContext.setAttribute("currentUserId", currentUserId);
%>
<div id="optBarDiv"> 
	<c:if test="${userAuthorizeScopeForm.method_GET=='edit'}">
		<kmss:authShow roles="ROLE_SYSCATEGORY_MAINTAINER">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="validateSubmitForm('update');">
		</kmss:authShow>
	</c:if>
	<c:if test="${userAuthorizeScopeForm.method_GET=='add'}">
		<kmss:authShow roles="ROLE_SYSCATEGORY_MAINTAINER">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="validateSubmitForm('save');">
			</kmss:authShow>
	</c:if>
</div>

<kmss:windowTitle 
	moduleKey="sys-lbpmext-authorize:module.sys.lbpmext.authorize.set"/>

<p class="txttitle"><bean:message  bundle="sys-lbpmext-authorize" key="module.sys.lbpmext.authorize.set"/></p>

<center>
<table class="tb_normal" width=95%>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.user.range"/>
		</td>
		<td width=85% colspan=3>
			<html:hidden property="fdScopeFormAuthorizeCateIds"/>
			<html:hidden property="fdScopeFormAuthorizeCateNames"/>
			<html:hidden property="fdScopeFormModelNames"/>
			<html:hidden property="fdScopeFormModuleNames"/>
			<html:hidden property="fdScopeFormTemplateIds"/>
			<html:hidden property="fdScopeFormTemplateNames"/>
			<html:hidden property="scopeTempValues"/>
			<textarea style="width:90%" readonly name="fdScopeFormAuthorizeCateShowtexts">${userAuthorizeScopeForm.fdScopeFormAuthorizeCateShowtexts}</textarea>
			<br>
			<a href="#"
				onclick="importAuthorizeCateDialog();">
				<bean:message key="dialog.selectOther" /></a><bean:message key="lbpmAuthorize.lbpmAuthorizeScope.note" bundle="sys-lbpmext-authorize"/>
		</td>
	</tr>
	

</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>