<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<style type="text/css">
	pre{ 
	white-space:pre-wrap; /* css3.0 */ 
	white-space:-moz-pre-wrap; /* Firefox */ 
	white-space:-pre-wrap; /* Opera 4-6 */ 
	white-space:-o-pre-wrap; /* Opera 7 */ 
	word-wrap:break-word; /* Internet Explorer 5.5+ */ 
	} 
</style>
<script>
	window.onload = function(){
		var fdContent = '${kmsLogSysConfigForm.fdContent}';
		var json = JSON.parse(fdContent);//将json字符串格式化为json对象
		var convertJson = JSON.stringify(json, null, 4);
		var tdDom = document.getElementById("jsonId");
		tdDom.innerHTML = convertJson;
	}
</script>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message bundle="kms-log" key="kmsLogSysConfig.content"/></p>

<center>
<table class="tb_normal" width=70% style="table-layout: fixed;word-break:break-all; word-wrap: break-word;">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdCreateTime"/>
		</td><td width="85%">
			<xform:text property="fdCreateTime" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdCreatorName"/>
		</td><td width="85%">
			<xform:text property="fdCreatorName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdModuleName"/>
		</td><td width="85%">
			<xform:text property="fdModuleName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdOprateMethod"/>
		</td><td width="85%">
		    <xform:text property="fdOprateMethod" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15% style="height: auto;vertical-align: top; ">
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdContent"/>
		</td>
		<td width="85%">
			<div style="width:100%;overflow:hidden;word-wrap:break-word;white-space:normal;">
				<pre id="jsonId" />
			</div>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
