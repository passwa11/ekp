<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<kmss:windowTitle moduleKey="sys-lbpmext-businessauth:table.lbpmext.businessAuth" subjectKey="sys-lbpmext-businessauth:lbpmext.businessAuth.set" subject="${lbpmExtBusinessAuthForm.fdName}" />
<html:form action="/sys/lbpmext/businessauth/lbpmBusinessAuth.do">
<div id="optBarDiv">
	<logic:equal name="lbpmExtBusinessAuthForm" property="method_GET" value="edit">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=update&fdId=${lbpmExtBusinessAuthForm.fdId}">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthForm, 'update');">
		</kmss:auth>
	</logic:equal>
	<logic:equal name="lbpmExtBusinessAuthForm" property="method_GET" value="add">
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=save&category=${JsParam.category}">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthForm, 'save');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/lbpmext/businessauth/lbpmBusinessAuth.do?method=saveadd&category=${JsParam.category}">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="Com_Submit(document.lbpmExtBusinessAuthForm, 'saveadd');">
		</kmss:auth>
	</logic:equal>
<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.set"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdCategroy"/>
		</td>
		<td width=85%>
			<html:hidden property="fdCategoryId"/>
			<xform:text style="width:90%" property="fdCategoryName" required="true" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuth.fdCategroy') }" htmlElementProperties="readOnly='true'"/> 
			<a href="javascript:void(0)" onclick="Dialog_Tree(
				false,
				'fdCategoryId',
				'fdCategoryName',
				null,
				'lbpmExtBusinessAuthCateService&parentId=!{value}',
				'<bean:message bundle="sys-lbpmext-businessauth" key="table.lbpmext.businessAuthCate"/>');">
				<bean:message key="dialog.selectOrg"/>
			</a>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdName"/>
		</td>
		<td width=85%>
			<xform:text property="fdName" style="width:90%" required="true"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdNumber"/>
		</td>
		<td width=85%>
			<c:if test="${lbpmExtBusinessAuthForm.fdNumber!=null}">
				<html:text property="fdNumber" readonly="true" style="width:95%"/>
			</c:if>
			<c:if test="${lbpmExtBusinessAuthForm.fdNumber==null}">
				<span class="com_help">(<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdNumber.help"/>)</span>
			</c:if>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdIsNotDelegate"/>
		</td>
		<td width=85%>
			<xform:checkbox property="fdIsNotDelegate">
			   	<xform:simpleDataSource value="true">&nbsp;</xform:simpleDataSource>
			</xform:checkbox>
		</td>
	</tr>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.fdType"/>
		</td>
		<td width=85%>
			<xform:radio property="fdType">
			   	<xform:customizeDataSource className="com.landray.kmss.sys.lbpmext.businessauth.service.spring.LbpmExtBusinessAuthTypeDataSource"></xform:customizeDataSource>
			</xform:radio>
		</td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuth.authEditors"/></td>
		<td width=85%><html:hidden property="authEditorIds" /> <html:textarea
			property="authEditorNames" style="width:90%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null);"><bean:message
			key="dialog.selectOther" /></a>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
</html:form>
<html:javascript formName="lbpmExtBusinessAuthForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>