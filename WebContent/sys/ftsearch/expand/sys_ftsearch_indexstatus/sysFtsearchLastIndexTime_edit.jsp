<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<html:form action="/sys/ftsearch/expand/sys_ftsearch_indexstatus/sysFtsearchIndexStatus.do">
<div id="optBarDiv">
	
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysFtsearchIndexStatusForm, 'update');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.lastIndexTime"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelClass"/>
		</td><td width="35%">
				<xform:text property="fdField" style="width:85%" showStatus="view"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelName"/>
		</td><td width="35%">
			<xform:text property="fdModelName" style="width:85%" showStatus="view"/>
		</td>
	</tr>
	
	<tr>
	
		<td class="td_normal_title" width=15%  >
			<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.indexStatus.modelLastIndexTime"/>
		</td><td width="85%" colspan=3>
			<xform:datetime property="fdValue" /> 
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	Com_IncludeFile("calendar.js");
	$KMSSValidation();
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>