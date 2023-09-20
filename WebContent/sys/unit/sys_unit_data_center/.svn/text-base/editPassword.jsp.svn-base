<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<script type="text/javascript">
function check(){
    if (form.oldPassword.value==""){
        alert("<bean:message key='sysRestService.user.editPassword.alert1' bundle='sys-restservice-server'/>");
        form.oldPassword.focus();
        return false;
    }
    if (form.newPassword.value==""){
        alert("<bean:message key='sysRestService.user.editPassword.alert2' bundle='sys-restservice-server'/>");
        form.newPassword.focus();
        return false;
    }
    if (form.confirmPassword.value==""){
        alert("<bean:message key='sysRestService.user.editPassword.alert3' bundle='sys-restservice-server'/>");
        form.confirmPassword.focus();
        return false;
    }
    if(form.confirmPassword.value != form.newPassword.value){
    	alert("<bean:message key='sysRestService.user.editPassword.alert4' bundle='sys-restservice-server'/>");
   	    return false;
    }
}
</script>
<html>
<head>
<title><bean:message key="sysRestService.user.editPassword.title" bundle="sys-restservice-server"/></title>
</head>
<body>
<form name="form" onsubmit="return check()" action="sysUnitDataCenter.do?method=editPassword"
	method="post">
<input type=hidden name=fdId value="${requestScope.fdId}">
<p class="txttitle">修改密码</p>
<center>
<span class="txtstrong">${alertPassword}</span>
<table class="tb_normal" width=265>
	<tr>
		<td class="td_normal_title" width=15%>
			原密码：
		</td><td width=85%>
			<input id=passowrd type=Password name=oldPassword class="inputSgl">
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			新密码:
		</td><td width=85%>
			<input id=passowrd type=Password name=newPassword class="inputSgl">
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			确认密码：
		</td><td width=85%>
			<input id=passowrd type=Password name=confirmPassword class="inputSgl">
		</td>
	</tr>
	<tr>
		<td colspan="2" align=center>
			<input type=submit value="确认" class="btnopt">
			<input type=reset value="重置" class="btnopt">
		</td>
	</tr>
</table>
</center>
</form>
</body>
</html>
