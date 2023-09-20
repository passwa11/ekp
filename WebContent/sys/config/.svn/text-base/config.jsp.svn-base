<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<% response.addHeader("X-UA-Compatible", "IE=5"); %>
<%@ page import="com.landray.kmss.sys.config.form.*" %>
<script>
Com_IncludeFile("dialog.js");
</script>
<style>
.message{
	color: #003366; 
}
.tb_normal{
	word-break:break-all;
}
</style>
<html>
	<div id="optBarDiv">
		<input type="button" class="btnopt" value="备份配置文件" onclick="Dialog_PopupWindow(Com_Parameter.ContextPath+'resource/jsp/frame.jsp?url='+encodeURIComponent(Com_Parameter.ContextPath+'admin.do?method=openBackup'),'700','300')"/>
		<input type="button" class="btnopt" value="保存" onclick="config_submitForm()"/>
	</div>
<head>
<title>系统配置</title>
<script>
//校验函数，通用函数，请使用该函数进行校验
var config_checkFuncList = new Array();
function config_addCheckFuncList(func){
	config_checkFuncList[config_checkFuncList.length]=func;
}

//唯一性校验，通用函数，请使用该函数进行唯一性校验
var config_validateUniqueParameter = new Array(); 
function config_addUniqueParameter(id, title){
	if(config_validateUniqueParameter[id]==null)
		config_validateUniqueParameter[id] = new Array();
	config_validateUniqueParameter[id][config_validateUniqueParameter[id].length] = title;
}
//页面加载,通用函数，页面加载请使用该函数
var config_onloadFuncList = new Array();
function config_addOnloadFuncList(func){
	config_onloadFuncList[config_onloadFuncList.length]=func;
}
//提交校验函数
function config_submitForm(){
	if(warnOracle()){
		alert("当前配置Oracle的连接帐号(sys、system)为系统帐号,请勿使用！");
		return false;
	}
	config_validateUniqueParameter = new Array();
	for(var i=0; i<config_checkFuncList.length; i++){
		if(!config_checkFuncList[i]()){
			return false;
		}
	}
	for (var i in config_validateUniqueParameter) { 
		var parameter = config_validateUniqueParameter[i];
		var alertMessage="";
		if(parameter.length>1){
			for(var j=0; j<parameter.length; j++){
				if(alertMessage==""){
					alertMessage = parameter[j];
				}else{
					if(alertMessage.indexOf(parameter[j])==-1){
						alertMessage += "\n" + parameter[j];
					}
				}
			}
			alert("不能同时选择:"+"\n"+alertMessage);
			return false;
		}
	}
	Com_Submit(document.forms[0], 'saveConfig');
}

//打开高级基础配置的高级选项
function config_openBaseAdvance(){
	var baseAdvance = document.getElementById("baseAdvance");
	var advanceCheckbox = document.getElementsByName("advanceCheckbox")[0];
	if(advanceCheckbox.checked){
		baseAdvance.style.display = 'block';
	}else{
		baseAdvance.style.display = 'none';
	}
}
<%
boolean showIntegrateBlock = true;
boolean showAuthenticationBlock = true;
boolean showEnterpriseBlock = true;
boolean showApplicationBlock = true;
SysConfigAdminForm sysConfigAdminForm = (SysConfigAdminForm)request.getAttribute("sysConfigAdminForm");
if(sysConfigAdminForm!=null){
	java.util.List integrates = (java.util.List)sysConfigAdminForm.getJspMap().get("integrate");
	if(integrates==null || integrates.size()==0){
		showIntegrateBlock = false;
	}
	java.util.List authentications = (java.util.List)sysConfigAdminForm.getJspMap().get("authentication");
	if(authentications==null || authentications.size()==0){
		showAuthenticationBlock = false;
	}
	java.util.List enterprises = (java.util.List)sysConfigAdminForm.getJspMap().get("enterprise");
	if(enterprises==null || enterprises.size()==0){
		showEnterpriseBlock = false;
	}
	java.util.List applications = (java.util.List)sysConfigAdminForm.getJspMap().get("application");
	java.util.List others = (java.util.List)sysConfigAdminForm.getJspMap().get("other");
	if((applications==null || applications.size()==0)&&(others==null || others.size()==0)){
		showApplicationBlock = false;
	}
}
request.setAttribute("showIntegrateBlock",showIntegrateBlock);
request.setAttribute("showAuthenticationBlock",showAuthenticationBlock);
request.setAttribute("showEnterpriseBlock",showEnterpriseBlock);
request.setAttribute("showApplicationBlock",showApplicationBlock);
%>

</script>
</head>
<body style="overflow-x: hidden;">
<html:form action="/admin.do?method=saveConfig">
<p class="txttitle">系统配置</p>
<center>
<table id="Label_Tabel" width="95%">
	<tr LKS_LabelName="基础配置">
		<td>
			<c:forEach items="${sysConfigAdminForm.jspMap['base']}" var="jspList" varStatus="vstatus">
				<c:import url="${jspList}" charEncoding="UTF-8"/>
				<br>
			</c:forEach>
			<c:forEach items="${sysConfigAdminForm.jspMap['baseAdvance']}" var="jspList" varStatus="vstatus">
				<c:import url="${jspList}" charEncoding="UTF-8"/>
				<br>
			</c:forEach>
		</td>
	</tr>
	<c:if test="${showAuthenticationBlock}">
	<tr LKS_LabelName="系统安全">
		<td>
			<c:forEach items="${sysConfigAdminForm.jspMap['authentication']}" var="integrateList" varStatus="vstatus">
				<c:import url="${integrateList}" charEncoding="UTF-8"/>
				<br>
			</c:forEach>
		</td>
	</tr>
	</c:if>
	<c:if test="${showEnterpriseBlock}">
	<tr LKS_LabelName="集团应用">
		<td>
			<c:forEach items="${sysConfigAdminForm.jspMap['enterprise']}" var="integrateList" varStatus="vstatus">
				<c:import url="${integrateList}" charEncoding="UTF-8"/>
				<br>
			</c:forEach>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${showIntegrateBlock}">
	<tr LKS_LabelName="集成配置">
		<td>
			<c:forEach items="${sysConfigAdminForm.jspMap['integrate']}" var="jspList" varStatus="vstatus">
				<c:import url="${jspList}" charEncoding="UTF-8"/>
				<br>
			</c:forEach>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${showApplicationBlock}">
	<tr LKS_LabelName="应用配置">
		<td>
			<c:forEach items="${sysConfigAdminForm.jspMap['application']}" var="otherList" varStatus="vstatus">
				<c:import url="${otherList}" charEncoding="UTF-8"/>
				<br>
			</c:forEach>
			<c:forEach items="${sysConfigAdminForm.jspMap['other']}" var="otherList" varStatus="vstatus">
				<c:import url="${otherList}" charEncoding="UTF-8"/>
				<br>
			</c:forEach>
		</td>
	</tr>
	</c:if>
</table>
</center>
<script>
window.onload = function(){
	for(var i=0; i<config_onloadFuncList.length; i++){
		config_onloadFuncList[i]();
	}
}
$KMSSValidation();
</script>
</html:form>
</body>
</html>
