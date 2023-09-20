<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="content">
<html:form action="/hr/staff/hr_staff_file_author/hrStaffFileAuthor.do">
 <div style="margin-top:25px">
<p class="txttitle"><bean:message bundle="hr-staff" key="hrStaffFileAuthor.setting"/></p>
<center>
<table class="tb_normal" width=85%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="hr-staff" key="hrStaffFileAuthor.view"/>
		</td><td width="85%" colspan="3">
			<xform:text property="fdName" style="width:85%" showStatus="noShow" />
			<xform:address propertyId="authorDetailIds" propertyName="authorDetailNames" showStatus="edit" mulSelect="true" orgType="ORG_TYPE_PERSON|ORG_TYPE_POST" textarea="true" style="width:85%" />
		</td>
	</tr>
</table>
<div style="margin-bottom: 10px;margin-top:25px">
  <ui:button text="${ lfn:message('button.save') }" onclick="Com_Submit(document.hrStaffFileAuthorForm, 'update');"></ui:button>
</div>
</center>
</div>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("dialog.js");
	$KMSSValidation();
</script>
</html:form>
	</template:replace>
</template:include>