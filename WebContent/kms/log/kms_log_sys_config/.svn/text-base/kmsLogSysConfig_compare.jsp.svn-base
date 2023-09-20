<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		var fdContentArr = new Array();
		fdContentArr[0] = '${data1.fdContent}';
		fdContentArr[1] = '${data2.fdContent}';
		
		for (var i = 0; i <= fdContentArr.length; i++) {
			var json = JSON.parse(fdContentArr[i]);//将json字符串格式化为json对象
			var convertJson = JSON.stringify(json, null, 4);
			var tdDom = document.getElementById("jsonId" + i);
			tdDom.innerHTML = convertJson;
		}
	}
</script>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="kms-log" key="kmsLogSysConfig.compare"/></p>

<center>
<table class="tb_normal" width=90% style="table-layout: fixed;word-break:break-all; word-wrap: break-word;">
	<tr>
		<td class="td_normal_title" width=14%>
			&nbsp;&nbsp;&nbsp;&nbsp;
		</td>
		<td width="43%">
			<bean:message bundle="kms-log" key="kmsLogSysConfig.first"/>
		</td>
		<td width="43%">
			<bean:message bundle="kms-log" key="kmsLogSysConfig.second"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=14%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdCreateTime"/>
		</td>
		<td width="43%">
			<fmt:formatDate value="${data1.fdCreateTime}" pattern="yyyy-MM-dd HH:mm" />
		</td>
		<td width="43%">
			<fmt:formatDate value="${data2.fdCreateTime}" pattern="yyyy-MM-dd HH:mm" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=14%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdCreatorName"/>
		</td>
		<td width="43%">
			<c:out value="${data1.fdCreator.fdName}" />
		</td>
		<td width="43%">
			<c:out value="${data2.fdCreator.fdName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=14%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdModuleName"/>
		</td>
		<td width="43%">
			<c:out value="${data1.fdModuleName}" />
		</td>
		<td width="43%">
			<c:out value="${data2.fdModuleName}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=14%>
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdOprateMethod"/>
		</td>
		<td width="43%">
		    <c:out value="${data1.fdOprateMethod}" />
		</td>
		<td width="43%">
		    <c:out value="${data2.fdOprateMethod}" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=14%  style="height: auto;vertical-align: top;">
			<bean:message bundle="kms-log" key="kmsLogSysConfig.fdContent"/>
		</td>
		<td width="43%" style="height: auto;vertical-align: top;">
			<pre id="jsonId0"></pre>
		</td>
		<td width="43%" style="height: auto;vertical-align: top;">
			<pre id="jsonId1"></pre>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
