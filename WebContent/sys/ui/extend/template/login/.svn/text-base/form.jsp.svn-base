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
<%-- 第三方登陆  --%>
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
pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());

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
		if(passwordString.indexOf("\u4435\u5320\u4d35") == -1	
			&& passwordString.indexOf("\u4445\u5320\u4d45") == -1) {
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
		if(userNameString.indexOf("\u4435\u5320\u4d35") == -1	
			&& userNameString.indexOf("\u4445\u5320\u4d45") == -1) {
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
<c:choose>
	<%-- <c:when test="${empty defaultLoginConfig}"> --%>
	<c:when test="true">
		<div class="header_title" :class="[login_title_show]" <c:if test="${isDesign eq false and titleShow eq false}">style="visibility:hidden;height:5px;"</c:if>>
					<%
               			if(isDesign) {
               			%>
               				{{${lfn:concat('login_form_title_',locale)}}}
               			<%} else {
               				String text = LoginTemplateUtil.getConfigLangValue(request, "login_form_title", ResourceUtil.getString("login.title"));
                			out.print(text);
               			}
               		%>
		</div>
	</c:when>
	<c:otherwise>
		<div class="header">
			<span class="header_tab selected" data-login-role="form_account">
				<%=ResourceUtil.getString("login.title.account")%>
			</span>
			<span class="header_tab" data-login-role="form_pcscan">
				<%=ResourceUtil.getString("login.title.scan")%>
			</span>
		</div>
		<script type="text/javascript">
			seajs.use(['lui/jquery'],function($){
				$(function(){
					var tabs = $('.header .header_tab');
					tabs.on('click',function(){
						tabs.removeClass('selected');
						$(this).addClass('selected');
						$('.form_content .form_content_item').hide();
						var role = $(this).attr('data-login-role'),
							selectedContentItem = $('.' + role);
						selectedContentItem.show();
					});
				});
			});
		</script>
	</c:otherwise>
</c:choose>
<div class="form_content"  style="display:${defaultLoginType == 'password' || empty defaultLoginType || not empty param.login_error ? 'block' : 'none'}">
	<div class="form_content_item form_account">
		<form action="${ LUI_CentextPath }j_acegi_security_check" method="POST" onsubmit="return kmss_onsubmit();" autocomplete="off">
			<table class="lui_login_form_table">
				<%--系统提示--%> 
				<tr>
					<td colspan="2" class="lui_login_message_td">
						<div class="lui_login_message_div">
							<c:set var="securityMsg" value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
							<c:if test="${securityMsg!=null}">
								<%=ResourceUtil.getString("login.info")%>
							</c:if>
							<c:choose>
								<c:when test="${securityMsg=='Bad credentials'}">
									<%=ResourceUtil.getString("login.error.password")%>
								</c:when>
								<c:otherwise>
									<c:out value="${securityMsg}" />
								</c:otherwise>
							</c:choose>
						</div>
					</td>
				</tr>
				<%--用户名--%>
				<tr>
					<td class="lui_login_input_title"><%=ResourceUtil.getString("login.username")%></td>
					<td class="lui_login_input_td">
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
					</td>
				</tr>
				
				<%--密码--%>
				<tr>
					<td class="lui_login_input_title"><%=ResourceUtil.getString("login.password")%></td>
					<td class="lui_login_input_td">
						<div class="lui_login_input_div">
							<input type='password' name='j_password' class="lui_login_input_password" placeholder="<%=ResourceUtil.getString("login.inupt.password")%>" autocomplete="off">
						</div>
					</td>
				</tr>
				
				<%-- 验证码 --%>
				<c:if test="${ need_validation_code == 'yes' }">
					<tr>
						<td class="lui_login_input_title"><%=ResourceUtil.getString("login.verifycode")%></td>
						<td class="lui_login_input_td">
							<div class="lui_login_input_div">				
								<input type='text' name='j_validation_code' class="lui_login_input_vcode" onFocus="this.select();" placeholder="<%=ResourceUtil.getString("login.input.code")%>"> <img onclick="this.src='vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='vcode.jsp' >
							</div>
						</td>
					</tr>
				</c:if>
			 	<%-- 切换语言 --%>
			 	<% if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { %>
					<tr>
						<% 
							LoginConfig loginconfig = new LoginConfig();
							String hidelang = loginconfig.getHiddenLang();	
						%>
						<td class="lui_login_input_title"><%=!(hidelang==null||hidelang.equals("false"))?ResourceUtil.getString("login.language"):" "%></td>
						<td class="lui_login_input_td">
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
						</td>
					</tr>
				<% } %>
				<%-- 登录按钮 --%>
				<tr>
					<td class="lui_login_button_td" colspan="2"  v-bind:class="{choose_hover:isLoginButtonActive}">
						<a href="javascript:${'1' eq varParams.designed ? 'void(0)':'document.getElementsByName(\'btn_submit\')[0].click()' };">
					<div class="lui_login_button_div_l" data-color="loginBtn_bgColor" @mouseenter="btnEnter" @mouseleave="btnLeave" v-bind:style="{${'1' eq varParams.designBgColor?'backgroundColor:loginBtn_bgColor,borderColor:loginBtn_bgColor,':'' }color:loginBtn_font_color}">
	                	<div class="lui_login_button_div_r">
	                         	<div class="lui_login_button_div_c">
	                         		<%
	                         			if(_varParams != null && "1".equals(((Map) _varParams).get("designed"))) {
	                         				pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());
	                         			%>
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
					</td>
				</tr>
				<tr>
					<td colspan="2">
						
					</td>
				</tr>
			</table>	
			<div class="lui_login_form_opt">
					<%
				
				        java.util.Map mapKK = new java.util.HashMap<String, String>(
				        		 com.landray.kmss.sys.appconfig.model.BaseAppconfigCache.getCacheData("com.landray.kmss.sys.profile.model.LoginConfig"));
				       	String url =(String) mapKK.get("downloadUrl");
				       	String name = (String)mapKK.get("downLoadName");
				       	String showDownLoad = (String)mapKK.get("showDownLoad");
				      	if(showDownLoad!=null&&showDownLoad.equals("true")){
			       %>
						<a rel="noopener noreferrer" class="kkDownloadLink link" style="display:inline-block;width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" href="<%=url!=null?url:"#" %>" target="_blank" title="<%=name!=null?name:"" %>"><%=name!=null?name:"" %></a>
				  
					<%} %>
			    <%
			        java.util.Map map = new java.util.HashMap<String, String>(
			        		 com.landray.kmss.sys.appconfig.model.BaseAppconfigCache.getCacheData("com.landray.kmss.sys.profile.model.PasswordSecurityConfig"));
			        boolean retrievePasswordEnable = false;
			       	if(map!=null){
			       		String isEnable = (String)map.get("retrievePasswordEnable");
			          	if("true".equals(isEnable)){
			          		retrievePasswordEnable = true;
			          	}
			      	}
			       	if(retrievePasswordEnable){
			       %>
			         <a class="forgetPswLink link" 
			         	href="${ LUI_ContextPath }/sys/organization/sys_org_retrieve_password/validateUser.jsp" 
			         	target="_blank"><%=ResourceUtil.getString("login.forgot.password")%></a>
			     <%} %>
			</div>	
			<div class="form_bottom">
				<%-- 第三方登录 --%>
				<%-- <c:import url="/sys/profile/maintenance/third_login_form.jsp" charEncoding="UTF-8"></c:import> --%>
		   	</div>
			
			<input type=hidden name="j_redirectto" value="<c:out value="${SPRING_SECURITY_TARGET_URL}" />">
			<input type=submit style="border: 0px; width: 0px; height: 0px; background: none;" name="btn_submit">
		</form>
	</div>
	<c:if test="${not empty defaultLoginConfig }">
		<div class="form_content_item form_pcscan">
			<iframe class="form_pcscan_iframe" src="${defaultLoginConfig.url}"></iframe>
			<div class="form_pcscan_tip">使用${defaultLoginConfig.name }扫码登录</div>
		</div>
	</c:if>
</div>
