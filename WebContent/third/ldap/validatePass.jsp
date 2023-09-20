<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@page import="com.landray.kmss.web.filter.ResourceCacheFilter"%>
<%
if(request.getAttribute("LUI_ContextPath")==null){
	String LUI_ContextPath = request.getContextPath();
	request.setAttribute("LUI_ContextPath", LUI_ContextPath);
	request.setAttribute("LUI_Cache",ResourceCacheFilter.cache);
}
%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%> 
<script>
Com_IncludeFile("dialog.js|data.js");
function testValidatePass(){
	var data = new KMSSData();
	var username = encodeURIComponent(document.getElementsByName("username")[0].value);
	var password = encodeURIComponent(document.getElementsByName("password")[0].value);
	var url = Com_Parameter.ContextPath + "third/ldap/setting.do?method=testValidatePass"+"&username="+username+"&password="+password;
	//alert(url);
	data.SendToUrl(url, validateProcessRequest);
}

function validateProcessRequest(request){
	var result = Com_GetUrlParameter(request.responseText, "result");
	var msg = Com_GetUrlParameter(request.responseText, "msg");
	if(!msg){
		msg = '';
	}else{
		msg = "<bean:message bundle='third-ldap' key='kmss.ldap.test.messageTips'/>"+msg;
	}
	if(result=="true"){
		seajs.use('sys/ui/js/dialog',function(dialog){		
			dialog.alert('<bean:message bundle='third-ldap' key='kmss.ldap.test.loginSuccess'/>')
		});
	}else{
		seajs.use('sys/ui/js/dialog',function(dialog){		
			dialog.alert('<bean:message bundle='third-ldap' key='kmss.ldap.test.loginFail'/>')
		});
    }
}
</script>
<br>
<br>
<br>
<center>
<table class="tb_normal" align="center">
		
	<tr>
		<td class="td_normal_title" width="110px;"><bean:message bundle='third-ldap' key='kmss.ldap.test.loginName'/></td>
		<td>
			<input type="text" name="username" ></input>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="110px;"><bean:message bundle='third-ldap' key='kmss.ldap.test.loginPassword'/></td>
		<td>
			<input type="password" name="password" ></input>
		</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center" style="height:60px;">
			<input type="button" value="<bean:message bundle='third-ldap' key='kmss.ldap.test.login'/>" onclick="testValidatePass();" style="width:100px;height:40px;font-size:20px;">
		</td>
		
	</tr>
	
</table> 
</center>