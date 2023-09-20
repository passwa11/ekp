<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<form method="post" action="<c:url value="/sys/admin/threadmonitor.do?method=update" />">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.forms[0], 'update');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<script>Com_IncludeFile("doclist.js");</script>
<p class="txttitle">
	<bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.title"/>
</p>
<xform:config isLoadDataDict="false" showStatus="edit">
<div style="line-height: 30px; text-align:center;"><bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.info"/></div>
<center>
<table id="TABLE_DocList" class="tb_normal" width="90%">
	<tr class="tr_normal_title">
		<td style="width:5%"><bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.serial"/></td>
		<td style="width:45%"><bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.prefix"/></td>
		<td style="width:45%"><bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.prompt"/></td>
		<td style="width:5%">
			<a href="#" onclick="DocList_AddRow();"><bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.add"/></a>
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1"></td>
		<td>
			<xform:textarea property="fdUrl" subject="${lfn:message('sys-admin:sysAdmin.threadmonitor.serial') }" validators="required maxLength(2000)" value="" style="width:100%;height:50px;"/>
		</td>
		<td>
			<xform:textarea property="fdDescription" subject="${lfn:message('sys-admin:sysAdmin.threadmonitor.prompt') }" validators="maxLength(2000)" value="" style="width:100%;height:50px;"/>
		</td>
		<td>
			<center><a href="#" onclick="DocList_DeleteRow();"><bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.delete"/></a></center>
		</td>
	</tr>
	<!--内容行-->
	<c:forEach items="${urlBlocks}" var="urlBlock" varStatus="status">
	<tr KMSS_IsContentRow="1">
		<td>${status.index+1}</td>
		<td>
			<xform:textarea property="fdUrl" subject="${lfn:message('sys-admin:sysAdmin.threadmonitor.serial') }" validators="required maxLength(2000)" value="${urlBlock.fdUrl}" style="width:100%;height:50px;"/>
		</td>
		<td>
			<xform:textarea property="fdDescription" subject="${lfn:message('sys-admin:sysAdmin.threadmonitor.prompt') }" validators="maxLength(2000)" value="${urlBlock.fdDescription}" style="width:100%;height:50px;"/>
		</td>
		<td>
			<center><a href="#" onclick="DocList_DeleteRow();"><bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.delete"/></a></center>
		</td>
	</tr>
	</c:forEach>
	<tr>
		<td></td>
		<td style="vertical-align: top;">
			<bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.prefix.info"/>
		</td>
		<td style="vertical-align: top;">
			<bean:message bundle="sys-admin" key="sysAdmin.threadmonitor.prompt.info"/> <bean:message key="global.service.unavalable"/>
		</td>
		<td></td>
	</tr>
</table>
</center>
</xform:config>
<script>$KMSSValidation(document.forms[0]);</script>
</form>
<%@ include file="/resource/jsp/edit_down.jsp"%>