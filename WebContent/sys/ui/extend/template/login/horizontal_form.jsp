<%@page import="com.landray.kmss.sys.profile.model.LoginConfig"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.landray.kmss.web.Globals"%>
<%@page import="com.landray.kmss.sys.config.util.LanguageUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.profile.model.LoginConfig"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:import url="/sys/profile/maintenance/third_login_form.jsp" charEncoding="UTF-8"></c:import>	
<%
String localeLang = request.getParameter("j_lang");
if (localeLang != null) {
	session.setAttribute(Globals.LOCALE_KEY, ResourceUtil.getLocale(localeLang));
}else{
	Locale xlocale = ((Locale)session.getAttribute(Globals.LOCALE_KEY));
	if(xlocale != null)
		localeLang = xlocale.getLanguage();
}
Object _varParams = request.getAttribute("varParams");
boolean isDesign = _varParams != null && "1".equals(((Map) _varParams).get("designed"));
boolean titleShow = true;
if (request.getAttribute("config") != null) {
	JSONObject configObject = (JSONObject)request.getAttribute("config");
	if(configObject.containsKey("login_title_show")) {
		titleShow = "loginTitleShow".equals(configObject.get("login_title_show"));
	}
}
pageContext.setAttribute("isDesign", isDesign);
pageContext.setAttribute("titleShow", titleShow);
%>
<script>
Com_IncludeFile("security.js");
</script>
<%
	String times = com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssVerifycodeTimes();
	String need_validation_code = (String)request.getSession().getAttribute("need_validation_code");
	String verifyCodeEnabled = com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getKmssVerifycodeEnabled();
    if(StringUtil.isNotNull(verifyCodeEnabled)&& verifyCodeEnabled.equalsIgnoreCase("true")&&"0".equals(times)&&StringUtil.isNull(need_validation_code)){
    	request.getSession().setAttribute("need_validation_code","yes");
    }
%>
<script type="text/javascript">
function kmss_onsubmit(){
	<% if(_varParams != null && "1".equals(((Map) _varParams).get("designed"))){ %>
        return false; // 如果是后台定制页面访问，不允许提交
    <% } %>
	var loginInput = "<%=ResourceUtil.getString("login.inupt")%>";
	cleanerErrorMsg();
	if(document.forms[0].j_username.value==""){
		showErrorMsg(loginInput);
		document.forms[0].j_username.focus();
		return false;
	}
	if(document.forms[0].j_password.value==""){
		showErrorMsg(loginInput);
		document.forms[0].j_password.focus();
		return false;
	}
	<c:if test="${ need_validation_code == 'yes' }">
		if(document.forms[0].j_validation_code.value==""){
			// 请输入验证码
			showErrorMsg("<%=ResourceUtil.getString("login.input.code.err")%>");
			document.forms[0].j_validation_code.focus();
			return false;
		}
	</c:if>
	document.getElementsByName("btn_submit")[0].disabled = true;	 
	encryptPassword();

	<%-- 判断是否关闭密码明文传输 --%>
	<% if ("true".equals(com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getLoginPlaintextDisabled())) { %>
	var passwordString = document.forms[0].j_password.value;
	var encoding = false;
	var codeFlags = window.SECURITYKEY?SECURITYKEY.supportEncodings():[];
	if(codeFlags.length>0){
		for (var i = 0; i < codeFlags.length; i++) {
			var secKey = SECURITYKEY.get(codeFlags[i]).security;
			if(passwordString.indexOf(secKey)>-1){
				encoding = true;
				break;
			}
		}
		return encoding;
	}else{//兼容代码
		if(passwordString.indexOf("\u4435\u5320\u4d35") == -1 && passwordString.indexOf("\u4445\u5320\u4d45") == -1) {
			// 当前系统已禁用密码明文传输，请使用密文登录
			showErrorMsg("<%=ResourceUtil.getString("login.error.password.encrypt")%>");
			return false;
		}
	}
	<% } %>
	
	<%-- 判断是否关闭账号明文传输 --%>
	<% if ("true".equals(com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getLoginAccountPlaintextDisabled())) { %>
	var userNameString = document.forms[0].j_username.value;
	var encoding = false;
	var codeFlags = window.SECURITYKEY?SECURITYKEY.supportEncodings():[];
	if(codeFlags.length>0){
		for (var i = 0; i < codeFlags.length; i++) {
			var secKey = SECURITYKEY.get(codeFlags[i]).security;
			if(userNameString.indexOf(secKey)>-1){
				encoding = true;
				break;
			}
		}
		return encoding;
	}else{//兼容代码
		if(userNameString.indexOf("\u4435\u5320\u4d35") == -1 && userNameString.indexOf("\u4445\u5320\u4d45") == -1) {
			// 当前系统已禁用账号明文传输，请使用密文登录
			showErrorMsg("<%=ResourceUtil.getString("login.error.username.encrypt")%>");
			return false;
		}
	}
	<% } %>
	
	// 清除单点cookie
	$.ajax({
		async : false,
		url : Com_Parameter.ResPath+"jsp/clearSsoCookie.jsp"
	});
	
	return true;
}
//显示错误信息
function showErrorMsg(msg) {
	var info = "<%=ResourceUtil.getString("login.info")%>";
	$(".lui_login_message_div").addClass("tip_message").html(info + msg);
}
//清除错误信息
function cleanerErrorMsg() {
	$(".lui_login_message_div").removeClass('tip_message').html("");
}
seajs.use(['lui/jquery'], function($) {
	window.onload = function(){
		var j_username = document.getElementsByName('j_username')[0].value;
		if(j_username==""){ 
			document.getElementsByName('j_username')[0].focus();
		}
		else{
			document.getElementsByName('j_password')[0].focus();
		}
		
		$(document).keypress(function(e) {
			//if (e.which == 13)
			//	$("form").submit();
		});
		
		// ExceptionTranslationFilter对SPRING_SECURITY_TARGET_URL 进行未登录url保持 请求中的hash并不会传递到服务端，故只能前端处理
		
		var urlObj = $('input[name="j_redirectto"]');
		var hash = location.hash;
		if(hash)
			urlObj.val(urlObj.val() + hash);
		
		
	}
});
function encryptPassword(){ 
	document.forms[0].j_password.value = desEncrypt(document.forms[0].j_password.value);
	
	<% if ("true".equals(com.landray.kmss.sys.profile.model.PasswordSecurityConfig.newInstance().getLoginAccountPlaintextDisabled())) { %>
		//用户名也需要加密
		document.forms[0].j_username.value = desEncrypt(document.forms[0].j_username.value);
	<% } %>
}
</script>
<%-- PC扫码登陆相关逻辑 --%>
<%@ include file="/sys/profile/maintenance/third_defaultlogin_form.jsp"%>

<div class="form_content" style="display:${defaultLoginType == 'password' || empty defaultLoginType || not empty param.login_error ? 'block' : 'none'}">
	<div class="form_content_item form_account">
		<form action="${ LUI_CentextPath }j_acegi_security_check" method="POST" onsubmit="return kmss_onsubmit();" autocomplete="off">
			<ul class="lui_login_form_list ${need_validation_code eq 'yes' ? 'lui_login_form_code' : ''}">
				<li class="lui_login_form_list_top">
					<div class="lui_login_form_list_top_left">
						<div class="lui_logo_img" :class="{choose_hover:isLogoActive}">
						    <!-- LOGO -->
							<% if(_varParams != null && "1".equals(((Map) _varParams).get("designed"))){ %>
									<img v-bind:src="'${loginLogoPath}/'+single_logo" >
							<% } else {%>
                                    <img src="${requestScope.loginLogoPath}">
							<% } %>
						</div>
					</div>
					<div class="lui_login_form_list_top_right">
						<div class="header" :class="[login_title_show]" <c:if test="${isDesign eq false and titleShow eq false}">style="visibility:hidden;"</c:if>>
						    <!-- 登录系统 -->
							<% if(isDesign){ 
								pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());
							%>
							{{${lfn:concat('login_form_title_',locale)}}}
							<% } else { 
								String loginTitleText = LoginTemplateUtil.getConfigLangValue(request, "login_form_title", ResourceUtil.getString("login.title"));
								out.print(loginTitleText); 
							} %>
						</div>
						<div class="lui_login_form_opt">
						       <% // KK客戶端下载（根据后台配置判断是否显示）  %>
							   <%
							        java.util.Map mapKK = new java.util.HashMap<String, String>(com.landray.kmss.sys.appconfig.model.BaseAppconfigCache.getCacheData("com.landray.kmss.sys.profile.model.LoginConfig"));
							       	String url =(String) mapKK.get("downloadUrl");
							       	String name = (String)mapKK.get("downLoadName");
							       	String showDownLoad = (String)mapKK.get("showDownLoad");
							      	if(showDownLoad!=null&&showDownLoad.equals("true")){
						       %>
									<a rel="noopener noreferrer" class="kkDownloadLink link" style="display:inline-block;width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" href="<%=url!=null?url:"#" %>" target="_blank" title="<%=name!=null?name:"" %>"><%=name!=null?name:"" %></a>
							   <%} %>
						</div>							
					</div>
				</li>
				<li class="lui_login_form_list_center">
					<div class="lui_login_form_list_center_left">
						<div class="lui_login_form_list_center_left_wrap">
							<div>
								<div class="lui_login_form_list_item">
									<%--用户名--%>
									<div class="lui_login_form_account">
										<div class="lui_login_input_title"><%=ResourceUtil.getString("login.username")%></div>
										<div class="lui_login_input_td">
											<div class="lui_login_input_div">
													<c:choose>
														<c:when test="${not empty param.login_error}">
															 <% 
																 pageContext.setAttribute("login_user_name",StringUtil.getString(StringUtil.XMLEscape((String)session.getAttribute(Globals.SPRING_SECURITY_LAST_USERNAME_KEY))));
															 %> 
														</c:when>
														<c:otherwise>
															<c:set var="login_user_name" scope="page" value="${param.username}" />
														</c:otherwise>
													</c:choose>
												<input type='text' name='j_username' class="lui_login_input_username"  onfocus="this.select();" value='<c:out value="${ login_user_name }"></c:out>' placeholder="<%=ResourceUtil.getString("login.inupt.username")%>" autocomplete="off">
											</div>
										</div>
									</div>
								</div>
								<div class="lui_login_form_list_item">
									<%--密码--%>
									<div class="lui_login_form_password">
										<div class="lui_login_input_title"><%=ResourceUtil.getString("login.password")%></div>
										<div class="lui_login_input_td">
											<div class="lui_login_input_div">
												<input type='password' name='j_password' class="lui_login_input_password" placeholder="<%=ResourceUtil.getString("login.inupt.password")%>" autocomplete="off">
											</div>
										</div>
									</div>
								</div>
								<div class="lui_login_form_list_item">
									<%-- 验证码 --%>
									<c:if test="${ need_validation_code == 'yes' }">
										<div>
											<div class="lui_login_input_title"><%=ResourceUtil.getString("login.verifycode")%></div>
											<div class="lui_login_input_td">
												<div class="lui_login_input_div">				
													<input type='text' name='j_validation_code' class="lui_login_input_vcode" onFocus="this.select();" placeholder="<%=ResourceUtil.getString("login.input.code")%>">
													<img onclick="this.src='${LUI_ContextPath}/vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='${LUI_ContextPath}/vcode.jsp' >
												</div>
											</div>
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</div>
					<div class="lui_login_form_list_center_right">
						<%-- 登录按钮 --%>
						<div>
							<div class="lui_login_button_td"  v-bind:class="{choose_hover:isLoginButtonActive}">
								<a class="lui_login_button_div_wrap" href="javascript:${'1' eq varParams.designed ? 'void(0)':'document.getElementsByName(\'btn_submit\')[0].click()' };">
							          <div class="lui_login_button_div_l">
										   <div class="lui_login_button_div_r">
													<div class="lui_login_button_div_c login_submit_btn" data-color="loginBtn_bgColor" @mouseenter="btnEnter" @mouseleave="btnLeave" v-bind:style="{backgroundColor:loginBtn_bgColor,color:loginBtn_font_color}" >
														<%if(_varParams != null && "1".equals(((Map) _varParams).get("designed"))) {%>
																{{${lfn:concat('loginBtn_text_',locale)}}}
														<%} else {
																String text = LoginTemplateUtil.getConfigLangValue(request, "loginBtn_text", ResourceUtil.getString("login.button.submit"));
																out.print(text);
															}
														%>
													</div>
											</div>
									  </div>
								 </a>
							</div>
						</div>
					</div>
				</li>
				<li class="lui_login_form_list_bottom">
					<div class="lui_login_form_list_bottom_part1">
						<div>
							<div class="lui_login_message_div">
								<c:set var="securityMsg" value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
								<c:if test="${securityMsg!=null}">
								    <%--系统提示--%> 
									<%=ResourceUtil.getString("login.info")%>
								</c:if>
								<c:choose>
									<c:when test="${securityMsg=='Bad credentials'}">
									    <%--用户名或密码不正确--%>
										<%=ResourceUtil.getString("login.error.password")%>
									</c:when>
									<c:otherwise>
										<c:out value="${securityMsg}" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="lui_login_form_list_bottom_part2">
						<div class="lui_login_form_list_bottom_left">
								<div class="lui_login_form_list_bottom_left_wrap">
									<div>
										<div class="lui_login_form_list_item">
											<div>
												<%-- 第三方登录 --%>
												<div class="form_bottom">
												    <%-- <c:import url="/sys/profile/maintenance/third_login_form.jsp" charEncoding="UTF-8"></c:import>	--%>													
												</div>
											</div>
											</div>
											<div class="lui_login_form_list_item">
												<div>
													<% // 忘记密码  %>
													<%
														java.util.Map map = new java.util.HashMap<String, String>(com.landray.kmss.sys.appconfig.model.BaseAppconfigCache.getCacheData("com.landray.kmss.sys.profile.model.PasswordSecurityConfig"));
														boolean retrievePasswordEnable = false;
															if(map!=null){
																String isEnable = (String)map.get("retrievePasswordEnable");
																if("true".equals(isEnable)){
																	retrievePasswordEnable = true;
																}
														}
															if(retrievePasswordEnable){
														%>
															<a class="forgetPswLink link" href="${ LUI_ContextPath }/sys/organization/sys_org_retrieve_password/validateUser.jsp" target="_blank"><%=ResourceUtil.getString("login.forgot.password")%></a>
													<%} %>
												</div>
											</div>
											<div class="lui_login_form_list_item"></div>
									</div>
								</div>
							</div>
					</div> 
				</li>

			 	<%-- 切换语言 --%>
			 	<% if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { %>
					<li>
						<% 
							LoginConfig loginconfig = new LoginConfig();
							String hidelang = loginconfig.getHiddenLang();	
						%>
						<div class="lui_login_input_title"><%=!(hidelang==null||hidelang.equals("false"))?ResourceUtil.getString("login.language"):" "%></div>
						<div class="lui_login_input_td">
							<div class="lui_login_input_div login_radio_wrap">
								<%=!(hidelang==null||hidelang.equals("false"))?LanguageUtil.getLangHtml(request, "j_lang",localeLang):""%>
							</div>
							<script>
								//语言切换
								function changeLang(value){
									if('1' == '${varParams.designed}') {
										window.top.changeFrameLang(value);
									}else {
										var url = document.location.href;
										var temp = location.href.split("#");
										url = Com_SetUrlParameter(temp[0], "j_lang", value);
										url = Com_SetUrlParameter(url, "username", document.getElementsByName("j_username")[0].value);
										if (temp.length > 1) {
											url = url + "#" + temp[1];
										}
										location.href = url;
									}
								}
							</script>
						</div>
					</li>
				<% } %>

			</ul>	
	
			
			<input type=hidden name="j_redirectto" value="<c:out value="${SPRING_SECURITY_TARGET_URL}" />">
			<input type=submit style="border: 0px; width: 0px; height: 0px; background: none;" name="btn_submit">
		</form>
	</div>
	<c:if test="${not empty defaultLoginConfig }">
		<div class="form_content_item form_pcscan">
			<iframe class="form_pcscan_iframe" src="${defaultLoginConfig.url}"></iframe>
			<div class="form_pcscan_tip">使用${defaultLoginConfig.name}扫码登录</div>
		</div>
	</c:if>
</div>