<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="toolbar">
	</template:replace>
	<template:replace name="content">
		<center>
		<p class="txttitle"><bean:message  bundle="sys-authorization" key="sysAuthAreaVisitor.update"/></p>
		<br>
		<html:form action="/sys/authorization/sys_auth_area/sysAuthArea.do" method="post">
			<table class="tb_normal" width="700" align="center">
				<tr>
					<td class="td_normal_title" width="20%"><bean:message  bundle="sys-authorization" key="sysAuthAreaVisitor.operate"/></td>
					<td>
						<label><input type="radio" name="operate" value="add" checked/> <bean:message  bundle="sys-authorization" key="sysAuthAreaVisitor.operate.add"/> </label>
						<label><input type="radio" name="operate" value="reset"/> <bean:message  bundle="sys-authorization" key="sysAuthAreaVisitor.operate.reset"/></label>
						<label><input type="radio" name="operate" value="delete"/> <bean:message  bundle="sys-authorization" key="sysAuthAreaVisitor.operate.delete"/> </label>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%"><bean:message  bundle="sys-authorization" key="sysAuthArea.authAreaVisitor"/></td>
					<td>
						<xform:address propertyId="visitorIds" propertyName="visitorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:85%"/>
					</td>
				</tr>
			</table>
			<html:hidden property="method" value="changeVisitor"/>
			<html:hidden property="fdIds" value="${param.fdIds }"/>
		</html:form>
		<br><br>
		<ui:button text="${lfn:message('button.save')}" height="35" width="100" onclick="Com_Submit(document.sysAuthAreaForm);"></ui:button>
		</center>
	</template:replace>
</template:include>
