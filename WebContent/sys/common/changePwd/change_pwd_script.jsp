<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>

<script type="text/javascript">
	Com_IncludeFile("security.js");
	var passwordUndercapacity = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.passwordUndercapacity" />';
	var pwdStructure1 = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdStructure1" />';
	var pwdStructure2 = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdStructure2" />';
	var pwdInput = '<bean:message bundle="sys-organization" key="sysOrgPerson.error.pwdInput" />';
	var newPwdCanNotSameOldPwd= '<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPwdCanNotSameOldPwd" />';
	var newPwdCanNotSameLoginName= '<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPwdCanNotSameLoginName" />';
	var newPwdCanNotSpaces= '<bean:message bundle="sys-organization" key="sysOrgPerson.error.newPassword.space" />';
	<% 
		String kmssOrgPasswordlength = com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordlength();
		String kmssOrgPasswordstrength = com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssOrgPasswordstrength();
		System.out.println("length:"+kmssOrgPasswordlength+", strength:"+kmssOrgPasswordstrength);
		// 非三员管理员 或 三员管理员
		String isAdmin = "false";
		if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
			// 开启三员后，判断是否是三员管理员
			if(!TripartiteAdminUtil.isGeneralUser()) {
				isAdmin = "true";
			}
		} else {
			// 非三员，判断是否有超级管理员角色
			isAdmin = UserUtil.getKMSSUser().isAdmin() + "";
		}
	%>
	var pwdlen = <%=StringUtil.isNull(kmssOrgPasswordlength) ? "1" : kmssOrgPasswordlength%>;
	var pwdsth = <%=StringUtil.isNull(kmssOrgPasswordstrength) ? "0" : kmssOrgPasswordstrength%>;
	var isAdmin = "<%=isAdmin%>";
	var loginName = "<%=UserUtil.getUser().getFdLoginName().replace("\"", "\\\"")%>";
	
	if(isAdmin == "true") {
		// 管理员的密码需要加强处理，如果设置的强度大于管理员默认强度，则取更大的强度
		pwdlen = pwdlen < 8 ? 8 : pwdlen;
		pwdsth = pwdsth < 3 ? 3 : pwdsth;
	}
	
	$(function(){
	
		// 旧密码检查
		$("input[name=fdOldPassword]").focus(function() {
			setPwdTip('oldPwdTip', 'blueIcon', pwdInput);
		});
		$("input[name=fdOldPassword]").blur(function() {
			var me = this;
			if (me.value.length > 0) {
				//$(me).parent().addClass("status_correct");
				hidePwdTip('oldPwdTip');
				enableSubmit(true);
			} else {
				$(me).parent().addClass("status_error");
				setPwdTip('oldPwdTip', 'redIcon', pwdInput);
				enableSubmit(false);
			}
			$(me).parent().removeClass("status_focus");
		});
	
	
		// 新密码强度检查
		$("input[name=fdNewPassword]").focus(function() {
			var msg;
			if(pwdsth > 1) {
				msg = pwdlen + pwdStructure2.replace("#len#", pwdsth);
			}else{
				msg = pwdlen + pwdStructure1;
			}
			setPwdTip('newPwdTip', 'blueIcon', msg);
		});
		$("input[name=fdNewPassword]").blur(function() {
			var me = this;
			if(checkPwd($(me), "newPwdTip")){
				$(me).parent().removeClass("status_correct status_error");
				$(me).parent().addClass("status_correct");
				hidePwdTip('newPwdTip');
				enableSubmit(true);
			} else {
				$(me).parent().addClass("status_error");
				enableSubmit(false);
			}
			$(me).parent().removeClass("status_focus");
		});
		// 新密码强度显示
		$("input[name=fdNewPassword]").keyup(function() {
			var value = $(this).val();
			if(value.length == 0) {
				setPwdIntensity(0);
				return false;
			}
			var intension = 0;
			if(value.length < pwdlen || pwdstrength(value) ==1){
				intension = 1;
			} else if(value.length == pwdlen && pwdstrength(value) >=2){
				intension = 2;
			} else if(value.length > pwdlen && pwdstrength(value) >=2){
				intension = 3;
			}
			setPwdIntensity(intension);
		});
	
		// 确认密码检查
		$("input[name=fdConfirmPassword]").focus(function() {
			var msg;
			if(pwdsth > 1) {
				msg = pwdlen + pwdStructure2.replace("#len#", pwdsth);
			}else{
				msg = pwdlen + pwdStructure1;
			}
			setPwdTip('confirmPwdTip', 'blueIcon', msg);
		});
		$("input[name=fdConfirmPassword]").blur(function() {
			var me = this;
	
			// 检查是否与新密码一至
			if($("input[name=fdNewPassword]").val() != me.value){
				setPwdTip('confirmPwdTip', 'redIcon', "<bean:message bundle='sys-organization' key='sysOrgPerson.error.comparePwd' />");
				$(me).parent().addClass("status_error");
				enableSubmit(false);
			} else if(!checkPwd($(me), "confirmPwdTip")) {
				$(me).parent().addClass("status_error");
				enableSubmit(false);
				return false;
			} else {
				$(me).parent().removeClass("status_focus");
				$(me).parent().addClass("status_correct");
				hidePwdTip('confirmPwdTip');
				enableSubmit(true);
			}
		});
	
		// 输入框焦点事件
		$("input").bind({
			focus : function() {
				$(this).parent().removeClass("status_correct status_error");
				$(this).parent().addClass("status_focus");
			}
		});
	
		// 表单提交
		$("#btn_submit").click(function() {
			var form = $("#change_password_form");
			// 旧密码
			var fdOldPassword = form.find("input[name=fdOldPassword]");
			if(fdOldPassword.val().length < 1) {
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
						<%
							String path = request.getContextPath();
							String basePath = "";
							if(80 == request.getServerPort() || 443 == request.getServerPort())
								basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
							else
								basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
							
							if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) {
								// 开启三员后，判断是否是三员管理员
								if(!TripartiteAdminUtil.isGeneralUser()) {
									// 如果是登录强制修改密码，完成后跳到后台配置中心页面
									Boolean compulsoryChangePassword = (Boolean) request.getSession().getAttribute("compulsoryChangePassword");
									if(compulsoryChangePassword!=null && compulsoryChangePassword) {
										basePath += "sys/profile/index.jsp";
									} else {
										basePath += "resource/jsp/success.jsp";
									}
								}
							}
						%>
						location.href = "<%=basePath%>";
					} else {
						$("#change_password_form .error").html(result.message);
					}
				},
				error : function(result) {
					if (result.responseJSON) {
						var pwdErrorType = result.responseJSON.msg;
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

		$(".lui_changepwd_panel .input .icon_cancel").click(function(){
			$($(this).siblings("input")[0]).val('');
			if($(this).siblings("input").attr("name")=="fdNewPassword"){
				setPwdIntensity(0);
			}
			$(this).parent().removeClass("status_correct");
		});
		$(".lui_changepwd_panel .input .icon_cancel").mouseenter(function(){
			$(this).parent().addClass("status_cancel");
		});
		$(".lui_changepwd_panel .input .icon_cancel").mouseleave(function(){
			$(this).parent().removeClass("status_cancel");
		});
	});
	
	// 密码长度与强度检查
	function pwdstrengthCheck(password, pwdTip) {
		var msg;
		if(pwdsth > 1) {
			msg = pwdlen + pwdStructure2.replace("#len#", pwdsth);
		}else{
			msg = pwdlen + pwdStructure1;
		}
		if(password.val().length < pwdlen){
			setPwdTip(pwdTip, 'redIcon', msg);
			return false;
		}
		
		if(pwdsth > 0 && pwdstrength(password.val()) < pwdsth){
			setPwdTip(pwdTip, 'redIcon', msg);
			return false;
		}
	
		return true;
	}
	
	// 新密码检查
	function checkPwd(password, pwdTip) {
		// 密码非空检查
		if(!password.val()){
			setPwdTip(pwdTip, 'redIcon', pwdInput);
			return false;
		}
	
		// 判断密码是否包含空格
		if(password.val().split(" ").length > 1){
			password.parent().addClass("status_error");
			setPwdTip(pwdTip, 'redIcon', newPwdCanNotSpaces);
			return false;
		}
	
		// 强度校验
		if(!pwdstrengthCheck(password, pwdTip)){
			return false;
		}
		
		// 新密码不能与旧密码相同
		if(password.val() == $("input[name=fdOldPassword]").val()) {
			password.parent().addClass("status_error");
			setPwdTip(pwdTip, 'redIcon', newPwdCanNotSameOldPwd);
			return false;
		}
	
		// 新密码不能与用户名相同
		if(loginName == password.val()) {
			password.parent().addClass("status_error");
			setPwdTip(pwdTip, 'redIcon', newPwdCanNotSameLoginName);
			return false;
		}
		
		return true;
	}
	
	// 设置密码强度展现
	function setPwdIntensity(intension) {
		$(".intension span").removeClass("status_warn");
		$.each($(".intension span"), function(i, n){
			if(i >= intension) {
				return;
			}
			$(n).addClass("status_warn");
		});
	}
	
	// 设置提示信息
	function setPwdTip(className, icon, text) {
		$('.' + className + ' .icon span').removeClass().addClass(icon);
		$('.' + className + ' .textTip').html(text);
	}
	
	//隐藏提示信息
	function hidePwdTip(className) {
		$('.' + className + ' .icon span').removeClass();
		$('.' + className + ' .textTip').html("");
	}
	
	function enableSubmit(enable) {
		if(enable)
			$("#btn_submit").removeClass("btn_disable");
		else
			$("#btn_submit").addClass("btn_disable");
	}
</script>