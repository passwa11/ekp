<%@page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		 <style type="text/css">
			body {
				font-family: "微软雅黑";
				font-size: 12px;
				color: #333;
				background-color: #f4fcfe;
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
				margin: 15px auto 30px;
				padding: 30px;
				width: 800px;
			  border-radius: 8px;
			  box-shadow: 0 0 20px rgba(240,246,247,.75);
			  background-color: #fff;
			}

			.lui_changepwd_panel h3 {
				font-size: 16px;
				color: #666;
				padding-bottom: 20px;
			}

			.lui_changepwd_panel .content {
				text-align: center;
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
				width: 100%;
				margin: 0 auto;
			}

			.lui_changepwd_panel table .title {
				padding-right: 10px;
			}

			.lui_changepwd_panel table tr {
				margin-bottom: 20px;
				position:relative;
			}
			.lui_changepwd_panel table tr:after{ display:table; content:""; clear:both;}
			.lui_changepwd_panel table tr:first-child {
				margin-bottom: 10px;
			}

			.lui_changepwd_panel table .input {
				border: 1px solid #e8e8e8;
				background-color: #fff;
				line-height: 20px;
				padding: 0;
				position: relative;
			}
			.lui_changepwd_panel table .input.status_error{
				border-color: #fa6835;
				background-color: #fff6f3;
			}
			.lui_changepwd_panel table .input input{
				width: 96%;
				line-height: 20px;
				padding: 10px 2%;
				border:0;
				background-color:transparent;
			}
			.lui_changepwd_panel .icon_correct,
			.lui_changepwd_panel .icon_cancel {
				display: none;
				background-repeat: no-repeat;
				background-position: 50%;
				position: absolute;
				right: 8px;
				top: 50%;
			}
			.lui_changepwd_panel .icon_cancel {
				width: 16px;
				height: 16px;
				background-image: url(../../common/changePwd/images/icon_cancel.png);
				margin-top: -8px;
			}
			.lui_changepwd_panel .icon_correct {
				width: 20px;
				height: 20px;
				background-image: url(../../common/changePwd/images/changePwd-icon-success.png);
				right: -30px;
				margin-top: -10px;
			}

			/*** 输入正确 ***/
			.lui_changepwd_panel .input.status_correct input { }

			.lui_changepwd_panel .input.status_correct i.icon_correct {
				display: inline-block;
			}

			/*** 输入错误 ***/
			.lui_changepwd_panel .input.status_error .input { }

			/*** 焦点进入 ***/
			.lui_changepwd_panel .input.status_focus .input {
				background: #fff;
				border: 1px solid #1d7ad9;
				-webkit-box-sizing:content-box;
								box-sizing:content-box;
			}

			.lui_changepwd_panel .input.status_focus i.icon_cancel {
				display: inline-block !important;
			}

			.btnW{
				margin-top: 20px;
				padding:10px 0;
				text-align: center;
			}
			.lui_changepwd_panel .btnW a {
				display: inline-block;
				cursor: pointer;
				margin: 0px 10px;
				padding: 10px 15px;
				min-width: 100px;
				text-align: center;
				line-height: 20px;
				font-size: 14px;
				color: #20b3ff;
				border-radius: 5px;
				border: 1px solid #20b3ff;
				background-color: transparent;
				transition: all .3s ease-in-out;
			}
			.lui_changepwd_panel .btnW a:hover{
				color: #fff;
				background-color: #20b3ff;
			}
			.lui_changepwd_panel .btnW a.btn_disable {
				color: #333;
				border-color: #ccd1d9;
				background-color: #ccd1d9;
				opacity: .65;
				cursor: not-allowed;
			}
			.lui_changepwd_panel .btnW .btn_cancel {
				color: #333;
				border-color: #b0b0b0;
				background-color: #b0b0b0;
				opacity: .65;
			}

			.content table tr td{
				padding: 8px 5px;
				vertical-align:middle;
				line-height: 20px;
			}
			.content table tr td.td_title {
				width: 25%;
				font-size: 14px;
				text-align: right;
			}
			.content table tr td.td_tips{
				vertical-align:middle;
			}
			.content table tr td.td_input{ width: 50%;}

			    /*** 密码提示 ***/
			.pwdTip p { display: inline-block; }

			.pwdTip .icon {
				width: 20px;
				height: 20px;
				display: inline-block;
			}
			.pwdTip .icon > span {
				display: inline-block;
				width: 20px;
				height: 20px;
				background-repeat: no-repeat;
				background-position: 50%;
				vertical-align: middle;
				position: relative;
				top: -1px;
			}
			.pwdTip .blueIcon,
			.pwdTip .redIcon { background-image: url(../../common/changePwd/images/changePwd-icon-warning.png); }

			.pwdTip .textTip {
				color: #fa6835;
			  font-size: 12px;
			  padding-left: 2px;
			  word-break: break-all;
			  word-wrap: break-word;
			}
			.intension {
				font-size: 0;
				*white-space: -1px;
			}
			.intension .status {
			  display: inline-block;
			  margin: 9px 2% 0 0;
			  padding: 0;
			  width: 32%;
			  height: 6px;
			  background-color: #e3e5e9;
			  position: relative;
			}
			.intension .status:last-child{ margin-right: 0; }
			.intension .status i {
			  display: none;
			  font-style: normal;
			  position: absolute;
			  left: 20px;
			  top: 10px;
			}
			.intension .status.status_warn {
			  background-color: #20b3ff;
			}
			.intension .intension_title {
			  font-size: 12px;
			  margin-right: 8px;
			}
		</style>
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/common/changePwd/css/person.css?v=1.0"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/sys/common/changePwd/js/pwdstrength.js"></script>
	</template:replace>
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.title"/>
	</template:replace>
	<template:replace name="body">
	<%
		SysOrgPerson user = UserUtil.getUser();
		if(null==user.getFdPassword()||StringUtil.isNull(user.getFdPassword().trim())){
			request.setAttribute("d2e", "1");
		}
	%>
	  <div class="lui-changePwd-header">
	    <h1 class="lui-changePwd-header-title"><img src="${KMSS_Parameter_ContextPath }sys/common/changePwd/images/changePwd-header-heading.png" alt=""></h1>
	  </div>
		<div class="lui-changePwd-content">
		 	<!-- 进度栏 Starts -->
		    <ul class="lui-changePwd-step-nav">
		      <li class="active"><span class="num">1</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step1"/></li>
		      <li><span class="num">2</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step2"/></li>
		      <li><span class="num">3</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step3"/></li>
		    </ul>
		    <!-- 进度栏 Ends -->
		    <div class="pwdbox">
				<div class="change_password_form">
					<!-- 修改密码 Starts-->
					<form id="change_password_form" action="<%=request.getContextPath()%>/sys/organization/sys_org_person/chgPersonInfo.do?method=saveMyPwd" method="post" onsubmit="return false;">
						<div class="lui_changepwd_panel">

							<div class="content">
								<table>
									<tr>
										<td colspan="3" style="display:none;">
											<p class="error"><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.compulsoryChangePassword}")%></p>
										</td>
									</tr>
									<tr>
										<td class="td_title">
											<span class="title"><%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.oldPassword}")%></span>
										</td>
										<td class="td_input">
											<div class="input"><input type="password" name="fdOldPassword"/><i class="icon_cancel"></i><i class="icon_correct"></i></div>
										</td>
										<td class="td_tips">
											<div class="pwdTip oldPwdTip">
												<span class="icon"><span></span></span>
												<span class="textTip"></span>
											</div>
											<div style="font-size: 12px"><bean:message bundle="sys-organization" key="sysOrgPerson.old.password.no"/></div>
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
								<div class="btnW"><a class="btn_submit" id="btn_submit"><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.btn1"/></a></div>
							</div>
						</div>
					</form>
				<!-- 修改密码 Ends-->
				</div>
			</div>
		 </div>

		<%@ include file="/sys/common/changePwd/change_pwd_script.jsp" %>
		<script type="text/javascript">
		$(function(){
			//清空浏览器记住的密码
			var d2e = "${d2e}";
			$("input[name=fdOldPassword]").val("");
			// 表单提交
			$("#btn_submit").unbind('click').click(function() {
				var form = $("#change_password_form");
				// 旧密码
				var fdOldPassword = form.find("input[name=fdOldPassword]");
				if(fdOldPassword.val().length < 1 && "1"!=d2e ) {
					fdOldPassword.parent().addClass("status_error");
					setPwdTip('oldPwdTip', 'redIcon', pwdInput);
					fdOldPassword.focus();
					return false;
				}
				// 新密码
				var fdNewPassword = form.find("input[name=fdNewPassword]");
				if(!checkPwd(fdNewPassword, "newPwdTip")) {
					fdNewPassword.parent().addClass("status_error");
					fdNewPassword.focus();
					return false;
				}
				// 确认新密码
				var fdConfirmPassword = form.find("input[name=fdConfirmPassword]");
				if(fdNewPassword.val() != fdConfirmPassword.val()) {
					$("input[name=fdConfirmPassword]").parent().addClass("status_error");
					setPwdTip('confirmPwdTip', 'redIcon', "<bean:message bundle='sys-organization' key='sysOrgPerson.error.comparePwd' />");
					fdConfirmPassword.focus();
					return false;
				} else if (!checkPwd(fdConfirmPassword, "confirmPwdTip")) {
					fdConfirmPassword.parent().addClass("status_error");
					fdConfirmPassword.focus();
					return false;
				}

				// 备份一下原始密码，当修改密码失败时还原备份的密码
				var __fdOldPassword = document.getElementsByName("fdOldPassword")[0].value;
				var __fdNewPassword = document.getElementsByName("fdNewPassword")[0].value;
				var __fdConfirmPassword = document.getElementsByName("fdConfirmPassword")[0].value;
				// 密码加密处理
				document.getElementsByName("fdOldPassword")[0].value = desEncrypt(__fdOldPassword);
				document.getElementsByName("fdNewPassword")[0].value = desEncrypt(__fdNewPassword);
				document.getElementsByName("fdConfirmPassword")[0].value = desEncrypt(__fdConfirmPassword);

				$.ajax({
					type : "POST",
					url : form.attr("action"),
					data : $("#change_password_form").serialize(),
					dataType : 'json',
					success : function(result) {
						if (result.success || result.msg == "<%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.chgMyPwd.success}")%>") {
							$("#change_password_form .error").html("");
							location.href = "<%=basePath%>" + "sys/organization/sys_org_person/chgPersonInfo.do?method=changePerson&setting=person_edit";
						} else {
							$("#change_password_form .error").html(result.message);
						}
					},
					error : function(result) {
						var msg;
						if (result.responseJSON) {
							msg = result.responseJSON.msg;
						} else if(result.responseText) {
							msg = JSON.parse(result.responseText.replace(/(^\s*)|(\s*$)|[\r\n]/ig,''));
						}
						if (msg) {
							var pwdErrorType = msg.msg;
							if ("1" == pwdErrorType) {
								$("#change_password_form input[name=fdOldPassword]").parent().addClass("status_error");
								setPwdTip('oldPwdTip', 'redIcon', pwdInput);
							} else if ("2" == pwdErrorType) {
								$("#change_password_form input[name=fdOldPassword]").parent().addClass("status_error");
								setPwdTip('oldPwdTip', 'redIcon', "<%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.error.curPwd}")%>");
							} else if ("3" == pwdErrorType) {
								$("#change_password_form input[name=fdNewPassword]").parent().addClass("status_error");
								setPwdTip('newPwdTip', 'redIcon', "<%=ResourceUtil.getMessage("{sys-organization:sysOrgPerson.error.same}")%>");
							} else if ("4" == pwdErrorType) {
								$("#change_password_form input[name=fdNewPassword]").parent().addClass("status_error");
								setPwdTip('newPwdTip', 'redIcon', newPwdCanNotSameLoginName);
							} else if ("5" == pwdErrorType) {
								$("#change_password_form input[name=fdNewPassword]").parent().addClass("status_error");
								setPwdTip('newPwdTip', 'redIcon', newPwdCanNotSameOldPwd);
							} else {
								pwdstrengthCheck($("input[name=fdNewPassword]"), "newPwdTip");
								$("input[name=fdNewPassword]").parent().addClass("status_error");
							}

							// 修改密码失败，还原加密密码
							document.getElementsByName("fdOldPassword")[0].value = __fdOldPassword;
							document.getElementsByName("fdNewPassword")[0].value = __fdNewPassword;
							document.getElementsByName("fdConfirmPassword")[0].value = __fdConfirmPassword;
						}
					}
				});
			});
		});

		</script>
	</template:replace>
</template:include>
