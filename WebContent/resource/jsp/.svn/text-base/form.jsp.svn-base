<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="java.util.Map"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.sys.config.util.LanguageUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.profile.model.LoginConfig"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 第三方登录 --%>
<c:import url="/sys/profile/maintenance/third_login_form.jsp" charEncoding="UTF-8"></c:import>
<%
String localeLang = request.getParameter("j_lang");
if (localeLang != null) {
	session.setAttribute(Globals.LOCALE_KEY, ResourceUtil.getLocale(localeLang));
}else{
	Locale xlocale = ResourceUtil.getLocaleByUser();
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
    
    // 设置为多屏登录页面，多语言模式默认为“下拉框”
    request.setAttribute("kmss.lang.select.mode.multiscreen", true);
%>
<script type="text/javascript">
function kmss_onsubmit(){
	var loginInput = "<%=ResourceUtil.getString("login.inupt")%>";
	if(document.forms[0].j_username.value==""){
		showErrorMsg(loginInput);
		setTimeout(function(){document.forms[0].j_username.focus();}, 10);
		return false;
	}
	if(document.forms[0].j_password.value==""){
		showErrorMsg(loginInput);
		setTimeout(function(){document.forms[0].j_password.focus();}, 10);
		return false;
	}
	<c:if test="${ need_validation_code == 'yes' }">
	if(document.forms[0].j_validation_code.value==""){
		showErrorMsg("<%=ResourceUtil.getString("login.input.code.err")%>");
		setTimeout(function(){document.forms[0].j_validation_code.focus();}, 10);
		return false;
	}
	</c:if>
	document.getElementsByName("btn_submit")[0].disabled = true;	 
	encryptPassword();
	return true;
}

seajs.use(['lui/jquery'], function($) {
	window.onload = function(){
		setTimeout(inputFocus, 100);
		
		$(document).keypress(function(e) {
			if (e.which == 13)
				$("form").submit();
		});
	}
});


// 显示错误信息
function showErrorMsg(msg) {
	var info = "<%=ResourceUtil.getString("login.info")%>";
	$(".error_tips").html(info + msg);
}

// 清除错误信息
function cleanerErrorMsg() {
	$(".error_tips").html("");
}

// 显示验证码输入框
function showValidationCode() {
	$("input[name=showValidationCode]").val("true");
	$(".login_form_item_code").show();
	$(".btn_flush").trigger("click"); // 点击刷新验证码
	setTimeout(function(){document.forms[0].j_validation_code.focus();}, 10);
}

// 光标自动定位到输入框
function inputFocus() {
	var username = document.getElementsByName('j_username');
	if(username.length < 1) {
		return;
	}
	var j_username = username[0].value;
	if(j_username==""){ 
		document.getElementsByName('j_username')[0].focus();
	}
	else{
		document.getElementsByName('j_password')[0].focus();
	}
}

// 密码加密处理
function encryptPassword(){ 
	document.forms[0].j_password.value = desEncrypt(document.forms[0].j_password.value);
	//清除单点cookie
	$.ajax({
		async : false,
		url : Com_Parameter.ResPath+"jsp/clearSsoCookie.jsp"
	});
}
</script>
<c:if test="${'1' ne varParams.justForm }">
<div class="login_form_wapper ${config.login_form_align }" :class="[login_form_align]">
	<div class="" id="login_form">
	</c:if>
		<form name="login_form" action="${ LUI_CentextPath }j_acegi_security_check" method="POST" onsubmit="return kmss_onsubmit();">
			<input type="hidden" name="j_redirectto" value="<c:out value="${SPRING_SECURITY_TARGET_URL}" />">
			
			<div class="login_modern">
				<h2 class="login_title" :class="[login_title_show]" <c:if test="${isDesign eq false and titleShow eq false}">style="visibility:hidden;height:20px;"</c:if>>
					<%
               			if(isDesign) {
               			%>
               				{{${lfn:concat('login_form_title_',locale)}}}
               			<%} else {
               				String text = LoginTemplateUtil.getConfigLangValue(request, "login_form_title", ResourceUtil.getString("login.title"));
                			out.print(text);
               			}
               		%>
				</h2>
				<div class="form_content"  style="display:${defaultLoginType == 'password' || empty defaultLoginType || not empty param.login_error ? 'block' : 'none'}">
				<%--用户名--%>
				<div class="login_form_item login_form_item_username">
					<label for="input_user" title="<%=ResourceUtil.getString("login.username")%>" class="icon_user"> 
						<span><%=ResourceUtil.getString("login.username")%></span>
					</label>
					<c:choose>
						<c:when test="${not empty param.login_error}">
							<%
							pageContext.setAttribute("login_user_name", StringUtil.getString(StringUtil.XMLEscape((String) session.getAttribute(Globals.SPRING_SECURITY_LAST_USERNAME_KEY))));
							%>
						</c:when>
						<c:otherwise>
							<c:set var="login_user_name" scope="page" value="${param.username}" />
						</c:otherwise>
					</c:choose>
					<input id="input_user" name='j_username' type="text" placeholder="<%=ResourceUtil.getString("login.inupt.username")%>" class="login_input" onclick="this.select();" value='<c:out value="${ login_user_name }"></c:out>' />
				</div>
				<%--密码--%>
				<div class="login_form_item login_form_item_password">
					<label for="input_pwd" title="<%=ResourceUtil.getString("login.password")%>" class="icon_pwd">
						<span><%=ResourceUtil.getString("login.password")%></span>
					</label>
					<input id="input_pwd" name='j_password' type="password" placeholder="<%=ResourceUtil.getString("login.inupt.password")%>" class="login_input" />
				</div>
				<%-- 验证码 --%>
				<c:if test="${ need_validation_code == 'yes' }">
				<div class="login_form_item login_form_item_code">
					<div class="form_input input_verity">
						<input type="text" name='j_validation_code' placeholder="<%=ResourceUtil.getString("login.input.code")%>" class="login_input" onclick="this.select();" /></div>
					<div class="verity_pic">
		                <img onclick="this.src='vcode.jsp?xx='+Math.random()" style='cursor: pointer;' src='vcode.jsp'>
		                <a class="btn_flush" title="<%=ResourceUtil.getString("login.verifycode.refresh")%>" onclick="$(this).prev().click()"></a>
		            </div>
				</div>
				</c:if>
				<%-- 切换语言 --%>
			 	<% if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { %>
			 			<% 
							LoginConfig loginconfig = new LoginConfig();
							String hidelang = loginconfig.getHiddenLang();	
						%>
			 	<div class="login_form_item login_form_item_lang">
					<label title="<%=ResourceUtil.getString("login.language")%>" class="icon_lang">
						<span><%=ResourceUtil.getString("login.language")%></span>
					</label>
					<div class="lui_login_input_div">
						<%=!(hidelang==null||hidelang.equals("false"))?LanguageUtil.getLangHtml(request, "j_lang",localeLang):""%>
					</div>
					<script>
						//语言切换
						function changeLang(value){
							if('1' == '${varParams.designed}') {
								window.top.changeFrameLang(value);
							}else {
								var url = document.location.href;
								url = Com_SetUrlParameter(url, "j_lang", value);
								url = Com_SetUrlParameter(url, "username", document.getElementsByName("j_username")[0].value);
								location.href = url;
							}
						}
					</script>
				</div>
				<% } %>
				<%--系统提示--%>
				<p class="error_tips">
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
				</p>
				<%-- 登录按钮 --%>
				<div class="login_form_item submit_item" :class="{choose_hover:isLoginButtonActive}">
					<a class="btn_submit" data-color="loginBtn_bgColor" @mouseenter="btnEnter" @mouseleave="btnLeave" 
					   href="javascript:${'1' eq varParams.designed ? 'void(0)':'document.getElementsByName(\'btn_submit\')[0].click()' };"
					   :style="{backgroundColor:loginBtn_bgColor,color:loginBtn_font_color}">
						<%
                   			if(_varParams != null && "1".equals(((Map) _varParams).get("designed"))) {
                   			%>
                   				{{${lfn:concat('loginBtn_text_',locale)}}}
                   			<%} else {
                   				String text = LoginTemplateUtil.getConfigLangValue(request, "loginBtn_text", ResourceUtil.getString("login.button.submit"));
                    			out.print(text);
                   			}
                   		%>
					</a>
				</div>
				<input type=submit style="border: 0px; width: 0px; height: 0px; background: none;" name="btn_submit">
				<div class="form_bottom">
					
					<%
				        java.util.Map mapKK = new java.util.HashMap<String, String>(
				        		 com.landray.kmss.sys.appconfig.model.BaseAppconfigCache.getCacheData("com.landray.kmss.sys.profile.model.LoginConfig"));
				       	String url =(String) mapKK.get("downloadUrl");
				       	String name = (String)mapKK.get("downLoadName");
				       	String showDownLoad = (String)mapKK.get("showDownLoad");
				      	if(showDownLoad!=null&&showDownLoad.equals("true")){
			       %>
				   		<a class="kkDownloadLink link" style="display:inline-block;width:150px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;vertical-align:middle;" href="<%=url!=null?url:"#" %>" target="_blank" title="<%=name!=null?name:"" %>"><%=name!=null?name:"" %></a><em>|</em>
					<%} %>
				   	</a>
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
				       <a class="link"  href="${ LUI_ContextPath }/sys/organization/sys_org_retrieve_password/validateUser.jsp" target="_blank"><%=ResourceUtil.getString("login.forgot.password")%></a>
				     <%} %>
				</div>
				</div>
            </div>
            <div>
				<p class="login_form_other"></p>
			</div>
		</form>
<c:if test="${'1' ne varParams.justForm }">
	</div>
</div>
</c:if>
