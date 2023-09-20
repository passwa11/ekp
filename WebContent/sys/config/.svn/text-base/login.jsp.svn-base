<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/config/resource/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("security.js");
function check(){
    if (form.password.value==""){
      alert("请输入密码!!!");
      form.password.focus();
      return false;
    }
    if (form.validation_code.value==""){
        alert("请输入验证码!!!");
        form.validation_code.focus();
        return false;
      }
    document.forms[0].passowrd.value = desEncrypt(document.forms[0].passowrd.value);
}
</script>
<html>
<head>
<title>管理员登录</title>
</head>
<body>
<form name="form" onsubmit="return check()" action="admin.do?method=config"
	method="post">
<p class="txttitle" style="margin: 40px auto 10px auto;">管理员登录</p>
<center>
<span class="txtstrong">${alertPassword}</span>
<table class="tb_normal">
	<tr>
		<td class="td_normal_title" width="60px" style="text-align: right;">
			密码：
		</td><td width="160px">
			<input id=passowrd type=Password name=password class="inputSgl" autocomplete="off" style="width:100%">
		</td>
	</tr>
	<tr>
		<td>
			 <img onclick="this.src='vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='vcode.jsp'>
		     <a class="btn_flush" 
		     	title="<%=ResourceUtil.getString(request.getSession(), "login.verifycode.refresh")%>" 
		     	onclick="$(this).prev().click()"></a>
		</td>
		<td>
			<input type="text" name='validation_code' style="width:100%"
					placeholder="<%=ResourceUtil.getString(request.getSession(), "login.input.code")%>" 
					class="inputSgl" onclick="this.select();" />
		</td>
	</tr>
	<tr>
		<td width=85% colspan=2 align="center">
			<input type=submit value=登录 class="btnopt">&nbsp;&nbsp;
			<input type=button value=修改密码 class="btnopt" onclick="Com_OpenWindow('admin.do?method=editPassWord','_blank');">
		</td>
	</tr>
</table>
</center>
</form>
</body>
</html>
