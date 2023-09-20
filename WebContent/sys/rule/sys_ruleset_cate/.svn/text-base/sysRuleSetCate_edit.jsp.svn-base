<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<html:form action="/sys/rule/sys_ruleset_cate/sysRuleSetCate.do">
<div id="optBarDiv">
	<logic:equal name="sysRuleSetCateForm" property="method_GET" value="edit">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysRuleSetCateForm, 'update');">
	</logic:equal>
	<logic:equal name="sysRuleSetCateForm" property="method_GET" value="add">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysRuleSetCateForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysRuleSetCateForm, 'saveadd');">
	</logic:equal>
<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="sys-rule" key="table.sysRuleSetCate"/></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdParent"/>
		</td><td width=35%>
			<html:hidden property="fdParentId"/>
			<html:text property="fdParentName" readonly="true" styleClass="inputsgl"/>
			<a href="#" onclick="Dialog_Tree(
				false,
				'fdParentId',
				'fdParentName',
				null,
				'sysRuleSetCateService&parentId=!{value}&item=fdName:fdId',
				'<bean:message bundle="sys-rule" key="table.sysRuleSetCate"/>',
				null,
				null,
				'<bean:write name="sysRuleSetCateForm" property="fdId"/>');">
				<bean:message key="dialog.selectOther"/>
			</a>
		</td>
		<td width=15% class="td_normal_title">
			<bean:message bundle="sys-rule" key="sysRuleSetCate.fdName"/>
		</td><td width=35%>
			<xform:text property="fdName" style="width:80%" required="true"></xform:text>
		</td>
	</tr>
	<!-- 可使用者 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message bundle="sys-rule" key="sysRuleSetCate.authReaders"/></td>
		<td  width=90% colspan="3"><html:hidden property="authReaderIds" /> <html:textarea
			property="authReaderNames" style="width:90%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authReaderIds','authReaderNames', ';',null);"><bean:message
			key="dialog.selectOther" /></a><br>
			          <% if (com.landray.kmss.sys.organization.util.SysOrgEcoUtil.IS_ENABLED_ECO) { %>
			          <c:set var="formName" value="sysRuleSetCateForm" scope="request"/>
   					 <% if(com.landray.kmss.sys.organization.util.SysOrgEcoUtil.isExternal()) { %>
       				 <!-- （为空则本组织人员可使用） -->
      					  <bean:message  bundle="sys-rule" key="sysRuleSetCate.reader.null.orgnization" arg0="${ecoName}" />
    					<% } else { %>
    				    <!-- （为空则所有内部人员可使用） -->
     					   <bean:message  bundle="sys-rule" key="sysRuleSetCate.reader.null.allUse" />			
   						 <% } %>
					<% } else { %>
   					 <!-- （为空则所有人可使用） -->
    						<bean:message  bundle="sys-rule" key="sysRuleSetCate.authReaders.describe" />
<% } %>
	   </td>
	</tr>
	<!-- 可维护者 -->
	<tr>
		<td class="td_normal_title" width=10%><bean:message bundle="sys-rule" key="sysRuleSetCate.authEditors"/></td>
		<td width=90% colspan="3"><html:hidden property="authEditorIds" /> <html:textarea
			property="authEditorNames" style="width:90%" readonly="true" /> <a
			href="#"
			onclick="Dialog_Address(true, 'authEditorIds','authEditorNames', ';',null);"><bean:message
			key="dialog.selectOther" /></a><br>
			<bean:message bundle="sys-rule" key="sysRuleSetCate.authEditors.describe"/>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
</html:form>
<html:javascript formName="sysRuleSetCateForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>