<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script>
Com_IncludeFile("security.js");
function checkInput(){
	var pwdNewVal = document.getElementsByName("fdNewPassword")[0].value;
	var pwdConVal = document.getElementsByName("fdConfirmPassword")[0].value;
	if(pwdNewVal=="" || pwdConVal=="" || pwdNewVal!=pwdConVal){
		alert("<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPwdIsNotAvailable"/>");
		return false;
	}
	// 判断密码是否包含空格
	if(pwdNewVal.split(" ").length > 1){
		alert("<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPassword.space"/>");
		return false;
	}
	document.getElementsByName("fdNewPassword")[0].value = desEncrypt(document.getElementsByName("fdNewPassword")[0].value);
	document.getElementsByName("fdConfirmPassword")[0].value = desEncrypt(document.getElementsByName("fdConfirmPassword")[0].value);
	return true;
}
</script>
<html:form action="/sys/organization/sys_org_person/chgPersonInfo.do?method=savePwd" onsubmit="return checkInput();">
<div id="optBarDiv">
	<input type=submit value="<bean:message key="button.submit"/>">
	<input type="button" value="<bean:message key="button.close"/>" onClick="Com_CloseWindow();">
</div>
<center>
<table class="tb_normal" width=300px style="border: #c0c0c0 1px solid">
	<tr>
		<td width=30% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.newPassword"/>
		</td><td width=70%>
			<input type="password" name="fdNewPassword" style="width:100%" class="inputsgl"/>
		</td>
	</tr>
	<tr>
		<td width=30% class="td_normal_title">
			<bean:message bundle="sys-organization" key="sysOrgPerson.confirmPassword"/>
		</td><td width=70%>
			<input type="password" name="fdConfirmPassword" style="width:100%" class="inputsgl"/>
		</td>
	</tr>
</table>
</center>
<html:hidden property="fdId"/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>