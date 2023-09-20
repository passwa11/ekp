<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>

<%
	// 跳到移动端
	if(MobileUtil.PC != MobileUtil.getClientType(request)) {
		request.getRequestDispatcher("/sys/common/changePwd/mobile/change_pwd.jsp").forward(request, response);
	}
%>

<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<title><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.button.changePassword}")%></title>

<% if(UserUtil.getKMSSUser().isAnonymous()) { %>
<script type="text/javascript">
	// 匿名用户不可访问
	location.href = '<%=request.getContextPath()%>/login.jsp';
</script>
<% } %>
<style type="text/css">
body {
	font-family: "微软雅黑";
	font-size: 12px;
	color: #333;
	background: #f7f6f6;
}

table,tr,h3,p {
	margin: 0px;
	padding: 0px;
}

table,tr {
	list-style: none;
}

input {
	outline: none;
	box-sizing: content-box;
	-webkit-box-sizing: content-box;
	-moz-box-sizing: content-box;
}

.lui_changepwd_panel {
	width: 900px;
	padding: 18px;
	border: 1px solid #dfdfdf;
	border-top: 2px solid #005ca1;
	background: #fff;
	margin: 80px auto 0px;
}

.lui_changepwd_panel h3 {
	font-size: 16px;
	color: #666;
	padding-bottom: 20px;
}

.lui_changepwd_panel .content {
	background: #f9f9f9;
	text-align: center;
	padding-top: 30px;
	padding-bottom: 100px;
}

.lui_changepwd_panel .error {
	padding-left: 28px;
	color: #f00;
	font-size: 14px;
	margin-bottom: 15px;
	text-align: center;
}

.lui_changepwd_panel table {
	text-align: left;
	display: inline-block;
	width: 600px;
	margin: 0px auto;
}

.lui_changepwd_panel table .title {
	display: inline-block;
	font-size: 14px;
	width: 90px;float:left;
}

.lui_changepwd_panel table .input {
	position: relative;
	width: 300px;
}

.lui_changepwd_panel table tr {
	margin-bottom: 20px; position:relative;
}
.lui_changepwd_panel table tr:after{ display:table; content:""; clear:both;}
.lui_changepwd_panel table tr:first-child {
	margin-bottom: 10px;
}

.lui_changepwd_panel table .input {
	border: 1px solid #dfdfdf;
	background: #f0f0f0;
	height: 24px;
	line-height: 24px;
	padding: 5px 8px;
	width: 280px;
}
.lui_changepwd_panel table .input input{
	border:none;
	background-color:transparent;
	height: 24px;
	line-height: 24px;
	width: 245px;
}
.lui_changepwd_panel .icon_correct,.lui_changepwd_panel .icon_cancel {
	display: none;
	position: absolute;
	right: 10px;
}

.lui_changepwd_panel .icon_cancel {
	width: 16px;
	height: 16px;
	cursor: pointer;
	background: url(images/icon_cancel.png) no-repeat left top;
	top:10px;
}
.lui_changepwd_panel .icon_cancel:hover{ background-image: url(images/icon_cancel_red.png);}

.lui_changepwd_panel .icon_correct {
	width: 17px;
	height: 12px;
	background: url(images/icon_correct.png) no-repeat left top;
	top: 13px;
}

/*** 输入正确 ***/
.lui_changepwd_panel .input.status_correct input {
	
}

.lui_changepwd_panel .input.status_correct i.icon_correct {
	display: inline-block;
}
/*** 点击× ***/
.lui_changepwd_panel .input.status_cancel i.icon_cancel{
	display:inline-block;
}
.lui_changepwd_panel .input.status_cancel i.icon_correct{
	display:none;
}
/*** 输入错误 ***/
.lui_changepwd_panel .input.status_error .input {
	border: 1px solid #f00 !important;
}

/*** 焦点进入 ***/
.lui_changepwd_panel .input.status_focus .input {
	background: #fff;
	border: 1px solid #1d7ad9; box-sizing:content-box; -webkit-box-sizing:content-box;
}

.lui_changepwd_panel .input.status_focus i.icon_cancel {
	display: inline-block;
}

.lui_changepwd_panel .btnW{ padding:20px 0px;}
.lui_changepwd_panel .btnW a {
	display: inline-block;
	cursor: pointer;
	margin: 0px 10px;
	width: 125px;
	height: 35px;
	line-height: 35px;
	font-size: 14px;
	color: #fff;
	background: #1d7ad9;
}
.lui_changepwd_panel .btnW a.btn_disable {
	background:#afafaf;
	cursor:default;
}
.lui_changepwd_panel .btnW .btn_cancel {
	background: #b0b0b0;
}

.content table tr td{ vertical-align:middle; height: 60px; box-sizing: content-box; -webkit-box-sizing: content-box;}
.content table tr td.td_title { width:80px;  text-align:right; padding-right:10px; }
.content table tr td.td_tips{ width:200px;  vertical-align:middle;}
.content table tr td.td_input{ width:302px;}

    /*** 密码提示 ***/
.pwdTip p { display: inline-block; }

.pwdTip .icon {
	width: 14px;
	height: 16px; 
}

.pwdTip .icon span {
	display: inline-block;
	width: 14px;
	height: 16px; 
	vertical-align: middle;
	/*margin-bottom: -5px; position:relative; top:-2px;*/
}

.pwdTip .blueIcon {
	background: url(images/blue.png) no-repeat center;
}

.pwdTip .redIcon {
	background: url(images/red.png) no-repeat center;
}

.pwdTip .textTip {
	font-size:12px; padding-left:2px;word-break:break-all;word-wrap:break-word;
}

.intension { padding-bottom: 5px; font-size: 0px; }
.intension .status { display: inline-block; margin:0px; margin-top: 8px; margin-right: 5px; padding:0px;  width:70px; height: 12px; background: #eee; position: relative; }
.intension .status i { display:none; font-style: normal; position: absolute; left: 20px; top: 10px; }
.intension .status.status_warn { background: #ffd038; }
.intension .intension_title { font-size:12px; margin-right:8px; }
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/common/changePwd/js/pwdstrength.js"></script>
</head>
<body style="text-align:center">
	<!-- 修改密码 Starts-->
	<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN || TripartiteAdminUtil.isGeneralUser()) { // 未开启三员 或 非三员管理 才显示的内容 %>
	<form id="change_password_form" action="<%=request.getContextPath()%>/sys/organization/sys_org_person/chgPersonInfo.do?method=saveMyPwd" method="post" onsubmit="return false;">
	<% } else { %>
	<form id="change_password_form" action="<%=request.getContextPath()%>/sys/profile/tripartiteAdminChangePwd.do?method=saveMyPwd" method="post" onsubmit="return false;">
	<% } %>
		<div class="lui_changepwd_panel">
			<h3><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.button.changePassword}")%></h3>
			<div class="content">
				<table>
					<tr>
						<td colspan="3">
							<p class="error">
								<% 
									Boolean ccp = (Boolean) request.getSession().getAttribute("compulsoryChangePassword");
									if(TripartiteAdminUtil.isGeneralUser() || (ccp!=null && ccp)) { // 非三员管理 才显示的内容
								%>
								<%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.compulsoryChangePassword}")%>
								<% } %>
							</p>
						</td>
					</tr>
					<tr>	
						<td class="td_title">
							<span class="title"><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.oldPassword}")%></span>
						</td>
						<td class="td_input">
							<div class="input"><input type="password" name="fdOldPassword" /><i class="icon_cancel"></i><i class="icon_correct"></i></div>
						</td>
						<td class="td_tips">
							<div class="pwdTip oldPwdTip">
								<span class="icon"><span></span></span>
								<span class="textTip"></span>
							</div>
						</td>
					</tr>
					<tr>	
                        <td class="td_title">
                            <span class="title"><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.newPassword}")%></span>
                        </td>
                        <td class="td_input">
                            <div class="input"><input type="password" name="fdNewPassword" /><i class="icon_cancel"></i><i class="icon_correct"></i></div>
                        </td>
                        <td class="td_tips">
                            <div class="pwdTip newPwdTip">
                                <span class="icon"><span></span></span>
                                <span class="textTip"><i class="status status_r"></i></span>
                            </div>
                        </td>
					</tr>
                    <tr>
                        <td class="td_title">
                            <span class="title"><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.pwdIntensity}")%></span>
                        </td>
                        <td class="td_input">
                            <div class="intension">
                                <span class="status"></span>
                                <span class="status"></span>
                                <span class="status"></span>
                            </div>
                        </td>
                        <td class="td_tips">
                        </td>
                    </tr>
					<tr>
                        <td class="td_title">
                            <span class="title"><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.confirmPassword}")%></span>
                        </td>
                        <td class="td_input">
                            <div class="input"><input type="password" name="fdConfirmPassword" /><i class="icon_cancel"></i><i class="icon_correct"></i></div>
                        </td>
                        <td class="td_tips">
                            <div class="pwdTip confirmPwdTip">
                                <span class="icon"><span></span></span>
                                <span class="textTip"></span>
                            </div>
                        </td>
					</tr>
				</table>
				<div class="btnW"><a class="btn_submit" id="btn_submit"><%=ResourceUtil.getMessage("{button.submit}")%></a></div>
			</div>
		</div>
	</form>
	<!-- 修改密码 Ends-->
	<%@ include file="/sys/common/changePwd/change_pwd_script.jsp" %>
</body>
</html>
