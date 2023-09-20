<%@ page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<%
	SysOrgPerson user = UserUtil.getUser();
	if(null == user.getFdPassword() || StringUtil.isNull(user.getFdPassword().trim())) {
		request.setAttribute("d2e", "1");
	}
%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		${ lfn:message('sys-organization:sysOrgPerson.changePerson.welcome') }
	</template:replace>
	
	<template:replace name="head">
		<link type="text/css" rel="Stylesheet" href="${LUI_ContextPath}/sys/common/changePwd/mobile/resource/person.css?s_cache=${MUI_Cache}" />
		<script type="text/javascript" src="${LUI_ContextPath}/sys/common/changePwd/js/pwdstrength.js"></script>
	</template:replace>
	
	<template:replace name="content">
		<div class="pwdbox">
			<div class="change_password_form">
				<h1>${ lfn:message('sys-organization:sysOrgPerson.changePerson.step1') }</h1>
				<!-- 修改密码 Starts-->
				<form id="change_password_form" action="${LUI_ContextPath}/sys/organization/sys_org_person/chgPersonInfo.do?method=saveMyPwd" method="post" onsubmit="return false;">
					<div class="lui_changepwd_panel">
						<div class="content">
							<table>
								<!-- 原密码 -->
								<tr class="tr-opt">
									<td class="td_title">
										<span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.oldPassword"/></span>
									</td>
									<td class="td_input">
										<div class="input"><input type="password" name="fdOldPassword" placeholder="${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip1') }"/><i class="icon_cancel"></i><i class="icon_correct"></i></div>
									</td>
									<td><div class="password-img"></div></td>
								</tr>
								<tr class="oldPwdTip">
									<td></td>
									<td colspan="2">
										<div class="info-words">
											<p class="textTip">${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip1') }</p>
											<p class="textTip"><bean:message bundle="sys-organization" key="sysOrgPerson.old.password.no"/></p>
										</div>
									</td>
								</tr>
								<!-- 新密码 -->
								<tr class="tr-opt">
			                        <td class="td_title">
			                            <span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.newPassword"/></span>
			                        </td>
			                        <td class="td_input">
			                            <div class="input"><input type="password" name="fdNewPassword" placeholder="${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip2') }"/><i class="icon_cancel"></i><i class="icon_correct"></i></div>
			                        </td>
			                        <td><div class="password-img"></div></td>
								</tr>
								<tr class="newPwdTip">
									<td></td>
									<td colspan="2">
										<div class="info-words">
											<p class="textTip">${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip2') }</p>
				                            <p class="textTip"></p>
										</div>
									</td>
								</tr>
								<!-- 密码强度提示 -->
			                    <tr>
			                        <td class="td_title">
			                            <span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.pwdIntensity"/></span>
			                        </td>
			                        <td class="td_input" colspan="2">
			                            <div class="intension">
			                                <span class="status">${ lfn:message('sys-organization:sysOrgPerson.pwdIntensity.tip1') }</span>
			                                <span class="status">${ lfn:message('sys-organization:sysOrgPerson.pwdIntensity.tip2') }</span>
			                                <span class="status">${ lfn:message('sys-organization:sysOrgPerson.pwdIntensity.tip3') }</span>
			                            </div>
			                        </td>
			                    </tr>
			                    <!-- 密码确认 -->
								<tr class="tr-opt">
			                        <td class="td_title">
			                            <span class="title"><bean:message bundle="sys-organization" key="sysOrgPerson.confirmPassword"/></span>
			                        </td>
			                        <td class="td_input">
			                            <div class="input"><input type="password" name="fdConfirmPassword" placeholder="${ lfn:message('sys-organization:sysOrgPerson.pwdInput.tip3') }"/><i class="icon_cancel"></i><i class="icon_correct"></i></div>
			                        </td>
			                       <td><div class="password-img"></div></td>
								</tr>
								<tr class="confirmPwdTip">
									<td></td>
									<td colspan="2">
										<div class="info-words">
											<p class="textTip"></p>
										</div>
									</td>
								</tr>
							</table>
							<div class="btnW"><a class="btn_submit_1" id="btn_submit"><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.btn1"/></a></div>
						</div>
					</div>
				</form>
				<!-- 修改密码 Ends-->
	    	</div>
	    </div>
	    <%@ include file="/sys/common/changePwd/mobile/change_pwd_script.jsp" %>
		<script type="text/javascript">
			$(function() {
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
						setPwdTip('oldPwdTip', pwdInput);
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
						setPwdTip('confirmPwdTip', "<bean:message bundle='sys-organization' key='sysOrgPerson.error.comparePwd' />");
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
							console.log("success-result:", result);
							if (result.success || result.msg == "<bean:message bundle='sys-organization' key='sysOrgPerson.chgMyPwd.success' />") {
								$("#change_password_form .error").html("");
								location.href = "${LUI_ContextPath}/sys/organization/sys_org_person/chgPersonInfo.do?method=changePerson&setting=person_edit";
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
									setPwdTip('oldPwdTip', pwdInput);
								} else if ("2" == pwdErrorType) {
									$("#change_password_form input[name=fdOldPassword]").parent().addClass("status_error");
									setPwdTip('oldPwdTip', "<bean:message bundle='sys-organization' key='sysOrgPerson.error.curPwd' />");
								} else if ("3" == pwdErrorType) {
									$("#change_password_form input[name=fdNewPassword]").parent().addClass("status_error");
									setPwdTip('newPwdTip', "<bean:message bundle='sys-organization' key='sysOrgPerson.error.same' />");
								} else if ("4" == pwdErrorType) {
									$("#change_password_form input[name=fdNewPassword]").parent().addClass("status_error");
									setPwdTip('newPwdTip', newPwdCanNotSameLoginName);
								} else if ("5" == pwdErrorType) {
									$("#change_password_form input[name=fdNewPassword]").parent().addClass("status_error");
									setPwdTip('newPwdTip', newPwdCanNotSameOldPwd);
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