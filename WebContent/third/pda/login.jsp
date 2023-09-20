<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.awt.Image,javax.imageio.ImageIO"%>
<%@ page import="java.util.Random,java.io.File,java.io.FilenameFilter"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%
	session.setAttribute("S_PADFlag","1");
	String localeLang = request.getParameter("lang");
	if (localeLang != null) {
		session.setAttribute(Globals.LOCALE_KEY, ResourceUtil.getLocale(localeLang));
	}
%>
<%
	String times = ResourceUtil.getKmssConfigString("kmss.verifycode.times");
	String need_validation_code = (String)request.getSession().getAttribute("need_validation_code");
	String verifyCodeEnabled = ResourceUtil.getKmssConfigString("kmss.verifycode.enabled");
    if(StringUtil.isNotNull(verifyCodeEnabled)&& verifyCodeEnabled.equalsIgnoreCase("true")&&"0".equals(times)&&StringUtil.isNull(need_validation_code)){
    	request.getSession().setAttribute("need_validation_code","yes");
    }    
%>
 <!-- securityMsg为后台传过来的登录校验消息文本 -->
<c:set var="securityMsg" value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
<template:include file="/third/pda/template_tiny.jsp" compatibleMode="true">

	<template:replace name="title">
		<c:out value="${lfn:message('third-pda:phone.login.title') }"></c:out>
	</template:replace>
	
	<template:replace name="head">
		<link type="text/css" rel="Stylesheet" href="${LUI_ContextPath}/third/pda/resource/style/login.css?s_cache=${MUI_Cache}" />
	</template:replace>
	
	<template:replace name="content">
	
	    <!-- 移动登录页表单 -->
		<form id="_form" action="<c:url value='/j_acegi_security_check'/>" method="POST" onsubmit="return kmss_onsubmit();" autocomplete="off">
		    <%-- 移动端登录标识属性（该属性决定登录成功后跳转移动端门户还是PC端门户） --%>
		    <input type="hidden" name="S_PADFlag" value="-1" />
		
		    <div class="mui_ekp_portal_login_box clearfix">
		        <div class="mui_ekp_portal_login_bgImg">
		            <%-- 背景图片 --%>
			        <%
						String path = request.getSession().getServletContext().getRealPath("/");
						path = path.replaceAll("\\\\", "/");
						if (path.endsWith("/")) {
							path = path.substring(0,path.length()-1);
						}
						String filePath = path + "/third/pda/resource/images/login_bg_img";
						File file = new File(filePath);
						Random random = new Random();
						File[] files = file.listFiles(new FilenameFilter(){
							public boolean accept(File file, String str) {
								String name = str.toLowerCase();
								return (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png"));
							}
						});
						String bgImgName = "";
						if(files!=null && files.length>0){
							File bg = files[random.nextInt(files.length)];
							bgImgName = bg.getName();
						}
					 %>
		            <img src="${LUI_ContextPath}/third/pda/resource/images/login_bg_img/<%=bgImgName%>?s_cache=${LUI_Cache}" >
		        </div>
		        <div class="mui_ekp_portal_login_content ${not empty securityMsg?'edit':''}">
			        <div class="mui_ekp_portal_login_content_wrap">
			            <ul class="mui_ekp_portal_login_content_bar clearfix">
			                <%-- 登陆账号 --%>
			                <li>
			                    <input type="text" name="j_username" class="mui_ekp_portal_login_input muiFontSizeM ${not empty securityMsg?'error':''}" placeholder="${lfn:message('third-pda:phone.login.enter.account') }" autocomplete="off" />
			                    <div class="mui_ekp_portal_login_btn_delete"></div>
			                </li>
			                <%-- 登陆密码 --%>
			                <li>
                                <input type="password" style="display: none"/><%-- 此处隐藏的密码框作用为避免浏览器自动填充密码 --%>
			                    <input type="text" name="j_password" class="mui_ekp_portal_login_input muiFontSizeM ${not empty securityMsg?'error':''}" placeholder="${lfn:message('third-pda:phone.login.enter.password') }" autocomplete="off"/>
			                    <div class="mui_ekp_portal_login_btn_delete"></div>
			                </li>
			                <%-- 登陆验证码 --%>
			                <% if (request.getSession().getAttribute("need_validation_code") != null && request.getSession().getAttribute("need_validation_code").toString().equals("yes")) { %>
			                <li>
			                    <input type="text" name="j_validation_code" class="mui_ekp_portal_login_input muiFontSizeM ${not empty securityMsg?'error':''}" maxlength="6" placeholder="${lfn:message('third-pda:phone.login.validation.code') }" />
			                    <div class="mui_ekp_portal_verification_code">
			                        <img onclick="this.src='<%=request.getContextPath()%>/vcode.jsp?xx='+Math.random()" src='<c:url value="/vcode.jsp"/>' align="middle"/>
			                    </div>
			                </li>
			                <% } %>
			            </ul>
			            <%-- 登陆成功后需要跳转的目标URL --%>
			            <div style="display: none">
							<input type="hidden" name="j_redirectto" value='<c:out value="${SPRING_SECURITY_TARGET_URL}"/>'/> 
						</div>
						<%-- 错误提醒文字展示区域 --%>
			            <div class="mui_ekp_portal_login_error muiFontSizeS">
							<c:if test="${not empty securityMsg }">
								<c:choose>
									<c:when test="${securityMsg=='Bad credentials'}">
										<bean:message key="login.error.password"/>
									</c:when>
									<c:otherwise>
										<span><c:out value="${securityMsg}"/></span>
									</c:otherwise>
								</c:choose>
							</c:if>
			            </div>
			            <%-- “忘记密码”超链接 --%>
				        <%
			               com.landray.kmss.sys.appconfig.service.ISysAppConfigService sysAppConfigService = (com.landray.kmss.sys.appconfig.service.ISysAppConfigService) com.landray.kmss.util.SpringBeanUtil.getBean("sysAppConfigService");
			               java.util.Map map = sysAppConfigService.findByKey("com.landray.kmss.sys.profile.model.PasswordSecurityConfig");
			               boolean retrievePasswordEnable = false;
			               if(map!=null){
				             	String isEnable = (String)map.get("retrievePasswordEnable");
				             	if("true".equals(isEnable)){
				             		retrievePasswordEnable = true;
				             	}
			               }
			               if(retrievePasswordEnable){
			            %>			            
			            <a href="javascript:void(0);" class="mui_ekp_portal_retrieve_password muiFontSizeS" onclick="forgetPwd()" title="${lfn:message('third-pda:login.forgetPassword')}">
			                ${lfn:message('third-pda:login.forgetPassword')}
			            </a>
			            <%} %>
			            <%-- 登录按钮 --%>
			            <div class="mui_ekp_portal_login_content_btn_submit">
			                <button id="login_submit_btn" class="muiFontSizeXL" type="submit">${lfn:message('third-pda:phone.login') }</button>
			            </div>
			            
			             <%-- 切换登陆 --%>
					     <div class="muiLoginFooter">
					        <ul>
					            <li>
					            	<a href="javascript:void(0);" class="muiFontSizeM" title="${lfn:message('third-pda:phone.login.pc') }" onclick="toPCLoginPage();">
					            		<i class="mui mui-loginPC"></i>${lfn:message('third-pda:phone.login.pc') }
					            	</a>
					            </li>
					        </ul>
						 </div>	
						 
			        </div>
		        </div>
		    </div>
		    
		 <script type="text/javascript" charset="utf-8" src="${LUI_ContextPath}/third/pda/resource/script/login.js?s_cache=${MUI_Cache}"></script>
		 <script type="text/javascript">Com_IncludeFile("security.js");</script>
	</template:replace>
	
</template:include>