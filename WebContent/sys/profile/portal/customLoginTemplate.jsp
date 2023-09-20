<%@page import="java.util.Map.Entry"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.view">
<template:replace name="content">
<template:replace name="head">
	<link rel="stylesheet" type="text/css" href="../resource/css/login_upload.css">
</template:replace>
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp");
</script>
<html:form action="/sys/profile/sys_login_template/sysLoginTemplate.do" enctype="multipart/form-data">
	<input type="hidden" name="fdId" value="${config.fdId }"/>
    <table class="tb_normal tb_custom_footer">
    	<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-profile" key="sys.loginTemplate.logo" />
			</td>
			<td colspan="3">
	            <input id="loginFile" subject="Logo" name="file" type="file"/>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=25%>
				<bean:message bundle="sys-profile" key="sys.loginTemplate.copyright" />
			</td>
			<td colspan="3">
				<%
				JSONObject json = (JSONObject)request.getAttribute("config");
				LinkedHashMap<String,String> langs = LoginTemplateUtil.getDesignLangs();
				pageContext.setAttribute("langs",langs);
				%>
				<c:forEach items="${langs }" var="lang">
					<% 
						Entry obj = (Entry)pageContext.getAttribute("lang");
						Object footInfo = json.get("custom_footInfo_"+obj.getKey());
					%>

					<div class="footer-custom-input">
						<label for=""><span>${lang.value }:</span>
						<input class="inputsgl" type="text" placeholder="${lang.value }" title="${lang.value }" name="custom_footInfo_${lang.key }" value="<%=footInfo%>"/>
					</label>
					</div>
					
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<ui:button text="${lfn:message('button.submit') }" onclick="customTemplate()"></ui:button><span class="upload-btn-siplt"></span>	
				<ui:button styleClass="lui_toolbar_btn_gray" text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
			</td>
		</tr>
    </table>
</html:form>
<script>
var _validate = $KMSSValidation(document.sysLoginTemplateForm);
seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
	window.customTemplate = function() {
		var myFile = document.getElementById('loginFile');
		if(myFile.files.length > 0) {
			var fileName = myFile.files[0].name;
			//文件类型校验
			var extFileNames = 'png,jpg,jpeg,gif';
			if(fileName.indexOf('.') > -1){
				var ext = fileName.substring(fileName.indexOf('.')+1);
				if(extFileNames.indexOf(ext)==-1){
					var tip = '<bean:message bundle="sys-attachment" key="sysAttMain.error.onlySupportFileTypes"/>';
					tip = tip.replace("{0}", extFileNames);
					dialog.alert(tip);
					return false;
				}
			}
		}
		Com_Submit(sysLoginTemplateForm,'customTemplate');
	};
})
</script>
</template:replace>
</template:include>
