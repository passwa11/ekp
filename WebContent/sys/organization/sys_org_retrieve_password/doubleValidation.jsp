<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ page import="java.util.Locale,com.landray.kmss.web.Globals,com.landray.kmss.util.UserUtil,com.landray.kmss.sys.authentication.user.KMSSUser"%>

<%
    String lang = request.getParameter("j_lang");
	if (lang == null) {
		Locale xlocale = ((Locale) session.getAttribute(Globals.LOCALE_KEY));
		if (xlocale != null) {
			lang = xlocale.getLanguage();
		} else {
			KMSSUser user = UserUtil.getKMSSUser(request);
			if (user != null && !user.isAnonymous()) {
				xlocale = user.getLocale();
			}
		}
	}
	pageContext.setAttribute("j_lang", lang);
%>
		
<!DOCTYPE html>
<html class="verify_html">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="renderer" content="webkit">
		<title><bean:message bundle="sys-organization" key="sysOrgRetrievePassword.doubleValidation.title" /></title>
		<link rel="stylesheet" href="css/verify.css" />
		<link rel="shortcut icon" href="${LUI_ContextPath}/favicon.ico">
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
		<script type="text/javascript">
			$(function() {
				bdHeight = "230px";
				if("${fn:contains(j_lang, 'en')}" == "true") {
	             	//英文环境
	                 $('body').addClass('multi_eng_bd');
	                 bdHeight = "260px";
	            }
				
				// 如果用户没有设置手机号，此功能将不能使用
				if("${!empty userNoMobileNo}" == "true") {
					// 禁用获取密码按钮
					$("#button_sendCode").removeClass("getcheckcode").addClass("verify_btn_disable");
					$("#button_sendCode").attr("disabled", "disabled");
					$(".verify_form_submit_item").hide();
					showMsg("${userNoMobileNo}", true);
				}
			});
		
			var s = parseInt("${reSentIntervalTime}"), t, j_redirectto = "${j_redirectto}";
			
			function times() {
			 	s--;
			 	$("#button_sendCode_time").show().text(s + ' <bean:message bundle="sys-organization" key="sysOrgRetrievePassword.message.send.countdown" />');
				t = setTimeout('times()', 1000);
				if (s <= 0) {
					 s = parseInt("${reSentIntervalTime}");
					 clearTimeout(t);
					 $("#button_sendCode").removeClass("verify_btn_disable").addClass("getcheckcode");
					 $("#button_sendCode").removeAttr("disabled");
					 $("#button_sendCode_time").hide().text("");
				}
			}
		
			function sendMobileValidationCode() {
				hideMsg();
				$("#button_sendCode").removeClass("getcheckcode").addClass("verify_btn_disable");
				$("#button_sendCode").attr("disabled", "disabled");

				$.ajax({
					type : "POST",
					dataType : 'json',
					url : "<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=sendDoubleValidationCode",
					success : function(data) {
						if (data.result) {
							showMsg('<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.message.sent" />');
						} else {
							handleError(data);
						}
					},
					error : function(data) {
						showMsg(data.errMsg, true);
					}
				});
				times();
			}
			
			function validateDoubleCode() {
				hideMsg();
				var code = $("input[name=m_code]").val();
				if(code.length < 6 && code.length > 1) {
					showMsg('<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.error.mobileCodeError" />', true);
					return;
				}
				$.ajax({
					type : "POST",
					dataType : 'json',
					data : {m_code : code},
					url : "<%=request.getContextPath()%>/sys/organization/sys_org_retrieve_password/sysOrgRetrievePassword.do?method=validateDoubleCode",
					success : function(data) {
						if (data.result) {
							// 短信验证成功，页面跳转
							if(j_redirectto.length < 1) {
								j_redirectto = "<%=request.getContextPath()%>/sys/portal/page.jsp";
							}
							location.href = j_redirectto;
						} else {
							handleError(data);
						}
					},
					error : function(data) {
						showMsg(data.errMsg, true);
					}
				});
			}
			
			function handleError(data) {
				if(data.errCode == "-1")
					location.href = "<%=request.getContextPath()%>/login.jsp";
				else
					showMsg(data.errMsg, true);
			}
			
			function showMsg(msg, isError) {
				$(".verify_containter").css("height", bdHeight);
				if(isError) {
					$(".verify_form_error_tips").parent().show();
					msg = "<i class='verify_tips'></i><span>" + msg + "</span>";
					$(".verify_form_error_tips").html(msg);
				} else {
					$(".verify_form_success_tips").parent().show();
					msg = "<i class='verify_success_tips'></i><span>" + msg + "</span>";
					$(".verify_form_success_tips").html(msg);
				}
			}
			
			function hideMsg() {
				$(".verify_containter").css("height", "180px");
				$(".verify_form_error_tips").html("");
				$(".verify_form_error_tips").parent().hide();

				$(".verify_form_success_tips").html("");
				$(".verify_form_success_tips").parent().hide();
			}
		</script>
	</head>

	<body class="verify_body">
		<!-- 短信验证 Starts -->
		<div class="verify_containter">
			<div class="lui_verify_tips"><bean:message bundle="sys-organization" key="sysOrgRetrievePassword.validate.mobileNo" /></div>
			<div class="lui_verify_form">
				<div class="verify_form_item">
					<label class="verify_label"><bean:message bundle="sys-organization" key="sysOrgRetrievePassword.mobileNo.name" /><em>*</em>：</label>
					<input type="text" disabled="disabled" class="verify_input verify_input_disable" value="${mobileNo}" />
					<button id="button_sendCode" class="getcheckcode verify_btn" onclick="sendMobileValidationCode();"><bean:message bundle="sys-organization" key="sysOrgRetrievePassword.mobile.getCode" /></button>
				</div>
				<div class="verify_form_item">
					<label class="verify_label"><bean:message bundle="sys-organization" key="sysOrgRetrievePassword.mobile.code" /><em>*</em>：</label>
					<input type="text" name="m_code" class="verify_input" placeholder='<bean:message bundle="sys-organization" key="sysOrgRetrievePassword.mobile.code.placeholder" />'
						onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
					<button id="button_sendCode_time" class="verify_btn verify_btn_disable" style="display: none;"></button>
				</div>
				<div class="verify_form_item error_tips" style="display: none;">
					<label class="verify_label"></label>
					<div class="verify_form_error_tips"></div>
				</div>
				<div class="verify_form_item success_tips" style="display: none;">
					<label class="verify_label"></label>
					<div class="verify_form_success_tips"></div>
				</div>
			</div>
			<div class="verify_form_item verify_form_submit_item">
				<div class="verify_submit_btn verify_btn" onclick="validateDoubleCode();"><bean:message key="button.ok" /></div>
			</div>
		</div>
		<!-- 短信验证 Ends-->
	</body>

</html>
