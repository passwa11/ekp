<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script>
Com_IncludeFile("doclist.js|dialog.js|data.js");
Com_IncludeFile("base64.js");
function testConnect(){
	var data = new KMSSData();
	var webServiceUrl = Com_GetUrlParameter(window.location.href, "webServiceUrl");
	var domain = Com_GetUrlParameter(window.location.href, "domain");
	var version = Com_GetUrlParameter(window.location.href, "version");
	var type = Com_GetUrlParameter(window.location.href, "type");
	var account = Base64.encode(document.getElementsByName("account")[0].value);
	var password = Base64.encode(document.getElementsByName("password")[0].value);
	var url = "ecalendar.do?method=testConnect"+"&domain="+domain+"&account="+account+"&password="+password+"&version="+version+"&type="+type+"&webServiceUrl="+webServiceUrl;
	//alert(url);
	data.SendToUrl(url, exchangeProcessRequest);
}

function exchangeProcessRequest(request){
	var result = Com_GetUrlParameter(request.responseText, "result");
	var msg = Com_GetUrlParameter(request.responseText, "msg");
	if(result=="true"){
		alert("连接成功！"+msg);
	}else{
		alert("连接失败！错误信息："+result);
    }
}
</script>
<br>
<br>
<br>
<center>
<table class="tb_normal" width=60% id="calendar.integrate.ecalendar" align="center">
	
		
	<tr>
		<td class="td_normal_title" width="110px;"><bean:message key="ecalendarBindData.fdAccount" bundle="third-ecalendar"/></td>
		<td>
			<input type="text" name="account" ></input>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="110px;"><bean:message key="ecalendarBindData.fdPassword" bundle="third-ecalendar"/></td>
		<td>
			<input type="password" name="password" ></input>
		</td>
	</tr>
	
	<tr>
		<td colspan="2" align="center">
			<input type="button" class="btnopt" value="<bean:message key='ecalendarBindData.testConnect' bundle='third-ecalendar'/>" onclick="testConnect();">
		</td>
		
	</tr>
	
</table> 
</center>