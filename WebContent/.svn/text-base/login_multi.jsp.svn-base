<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- <%@ include file="/login_multi_config.jsp"%> --%>
<c:import url="/login_multi_config.jsp" charEncoding="UTF-8"></c:import>
<c:set var="title_image_login_url" scope="session" value="login_multi"/>
<%pageContext.setAttribute("loginImagePath", LoginTemplateUtil.getLoginImagePath());%>
<template:include ref="default.login">
	<template:replace name="head" >
	    <link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css" rel="stylesheet" type="text/css" />
		<link href="${ LUI_ContextPath }/resource/style/default/login_multi/css/style.css" rel="stylesheet" type="text/css" />
		<script>Com_IncludeFile("jquery.js");</script>
		<style>
			.submit_item .btn_submit {
				background:${config.loginBtn_bgColor};
				color:${config.loginBtn_font_color};
			}
			.submit_item .btn_submit:hover {
				background:${config.loginBtn_bgColor_hover};
			}
		</style>
 	</template:replace>
	<template:replace name="body">
		<div class="login-background-wrap ${config.login_logo_position}">
		<!-- 页眉 开始 -->
	    <div class="login_header">
	        <div class="main_content">
	            <div class="login_logo">
	            	<img alt="" src="<c:url value="${loginImagePath }/login_multi/${config.multi_logo}"/>">
	            </div>
	            <div class="login_info">
	                <a class="btn_login" status="off" onclick="show_shade('login_popup_wapper')">
	                	<%=ResourceUtil.getString("login.page.header.loginBtn")%>
	                </a>
	                <%-- 切换语言  --%>
	                <% if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { %>
	                <% 
	                com.landray.kmss.sys.profile.model.LoginConfig loginMult = new com.landray.kmss.sys.profile.model.LoginConfig();
	               		String langEnable= loginMult.getHiddenLang();
	               		
	               	  if(langEnable!=null&&!langEnable.equals("false")){
	               		 request.setAttribute("langflag", 1);
	               	  }
	               	    
	                %>
	                <c:if test="${langflag==1}">
		                <div class="multi_sel">
		                    <a class="dropdown">
		                    	<span class="dropdown-input"></span><span class="dropdown-icon">&nbsp;</span>
		                    </a>
		                </div>
	                </c:if>	
	                <% } %>
	                <div class="login_code" id="test">	
		                <!-- 二维码 -->
		                <div class="login_code_wapper">
		                    <i></i>
		                    <ul>
		                        <li>
		                            <h3><%=ResourceUtil.getString("login.page.header.kk5")%></h3>
		                            <div class="icon_code code_kk5">
		                            </div>
		                            <p><%=ResourceUtil.getString("login.page.header.kk5.info")%></p>
		                        </li>
		                        <li>
		                            <h3><%=ResourceUtil.getString("login.page.header.cloudlet")%></h3>
		                            <div class="icon_code code_wy">
		                            </div>
		                            <p><%=ResourceUtil.getString("login.page.header.cloudlet.info")%></p>
		                        </li>
		                        <li>
		                            <h3><%=ResourceUtil.getString("login.page.header.service")%></h3>
		                            <div class="icon_code code_wf">
		                            </div>
		                            <p><%=ResourceUtil.getString("login.page.header.service.info")%></p>
		                        </li>
		                    </ul>
		                </div>
		           </div>
	            </div>
	        </div>
	    </div>
	    <!-- 页眉 结束 -->
	    <div id="containter">
	        <!-- 首屏 大连接 -->
	        <div class="section section1" style="background:url('${LUI_ContextPath}${loginImagePath}/login_multi/${config.multi_section1_bg}') no-repeat left top;background-size:cover;">
	            <img src="${ LUI_ContextPath }/resource/style/default/login_multi/images/login_bg_01.jpg" class="section_Bg" />
	            <div class="main_content">
	                <div class="animate_bgW">
						<div class="bg1_0"></div>
						<div class="animate_txt">
							<div class="animate_bg bg1_1 title-h1">
								<em>智</em>慧办公赋能未来
							</div>
							<div class="animate_bg bg1_2 title-h2">
								<p>云上大数据</p>
								<p>小k机器人</p>
								<p>智能设备齐登场</p>
							</div>
						</div>	
	                  
	                </div>
					<!-- 登录框 -->
					<c:choose>
					<c:when test="${appId != null &&  appId ne '' && isLding}">
						<!-- 钉钉扫码登录应用ID不为空并且licence是蓝桥的 -->
						<%request.setAttribute("loginForm", "login.form.v12new");%>
						<%request.setAttribute("loginPageType", "multi");%>
						<%@include file="ding_qr_code_login.jsp"%>
					
					</c:when>
					<c:otherwise>
						<ui:combin ref="login.form.v12new"></ui:combin>
					</c:otherwise>
					</c:choose>
	               
	                <a class="godown" data="1"><span></span></a>
	            </div>
	        </div>
	        <!-- 协同运营平台 -->
	        <div class="section section2" style="background:url('${LUI_ContextPath}${loginImagePath}/login_multi/${config.multi_section2_bg}') no-repeat left top;background-size:cover;">
	            <img src="${ LUI_ContextPath }/resource/style/default/login_multi/images/login_bg_02.jpg" class="section_Bg" />
	            <div class="main_content">
	                <div class="animate_bgW">
	                    <div class="animate_bg bg2_1 title-h1">
							<em>领</em>先的企业级业务流程引擎
	                    </div>
	                    <div class="animate_bg bg2_2 title-h2">
							业务流，自由流，流程仿真……，驱动企业精细化运营
	                       <!--  <a><%=ResourceUtil.getString("login.page.section2.workflow")%></a>
	                        <em>|</em>
	                        <a><%=ResourceUtil.getString("login.page.section2.report")%></a>
	                        <em>|</em>
	                        <a><%=ResourceUtil.getString("login.page.section2.data")%></a>
	                        <em>|</em>
	                        <a><%=ResourceUtil.getString("login.page.section2.knowledge")%></a> -->
	                    </div>
	                </div>
	                <a class="godown" data="2"><span></span></a>
	            </div>
	            <div class="footer_nav">
	                <div class="main_content">
	                    <ul>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon1"></em><%=ResourceUtil.getString("login.page.section2.humanity")%>
	                                <p><%=ResourceUtil.getString("login.page.section2.humanity.info")%></p>
	                            </div>
	                        </a></li>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon2"></em><%=ResourceUtil.getString("login.page.section2.mobile")%>
	                                <p><%=ResourceUtil.getString("login.page.section2.mobile.info")%></p>
	                            </div>
	                        </a></li>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon3"></em><%=ResourceUtil.getString("login.page.section2.cloud")%>
	                                <p><%=ResourceUtil.getString("login.page.section2.cloud.info")%></p>
	                            </div>
	                        </a></li>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon4"></em><%=ResourceUtil.getString("login.page.section2.integration")%>
	                                <p><%=ResourceUtil.getString("login.page.section2.integration.info")%></p>
	                            </div>
	                        </a></li>
	                    </ul>
	                </div>
	            </div>
	        </div>
	        <!-- KMS -->
	        <div class="section section3" style="background:url('${LUI_ContextPath}${loginImagePath}/login_multi/${config.multi_section3_bg}') no-repeat left top;background-size:cover;">
	            <img src="${ LUI_ContextPath }/resource/style/default/login_multi/images/login_bg_03.jpg" class="section_Bg" />
	            <div class="main_content">
	                <div class="animate_bgW">
	                    <div class="animate_bg bg3_1 title-h1">
							<em>企</em>业大连接移动办公平台kk
	                    </div>
	                    <div class="animate_bg bg3_2 title-h2">
							MAC版，PAD版齐亮相，IM更丰富、强大！
	                    </div>
		                <!-- <div class="animate_bg bg3_4">
		                </div> -->
	                </div>
	            </div>
	            <div class="footer_nav">
	                <div class="main_content">
	                    <ul>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon11"></em><%=ResourceUtil.getString("login.page.section3.mobile")%>
	                                <p><%=ResourceUtil.getString("login.page.section3.mobile.info")%></p>
	                            </div>
	                        </a></li>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon12"></em><%=ResourceUtil.getString("login.page.section3.assets")%>
	                                <p><%=ResourceUtil.getString("login.page.section3.assets.info")%></p>
	                            </div>
	                        </a></li>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon13"></em><%=ResourceUtil.getString("login.page.section3.scene")%>
	                                <p><%=ResourceUtil.getString("login.page.section3.scene.info")%></p>
	                            </div>
	                        </a></li>
	                        <li><a>
	                            <div class="link-text-wrapper">
	                                <em class="link-icon footerNav_icon14"></em><%=ResourceUtil.getString("login.page.section3.wisdom")%>
	                                <p><%=ResourceUtil.getString("login.page.section3.wisdom.info")%></p>
	                            </div>
	                        </a></li>
	                    </ul>
	                </div>
	            </div>
	            <a class="godown" data="3"><span></span></a>
	        </div>
        </div>
	    <!-- 登录弹出框 -->
	    <div class="login_popup_wapper">
	        <div class="popup_btnClose" onclick="close_shade('login_popup_wapper')">
	            <span></span>
	        </div>
	        <!-- 登录框 -->
            <div id="login_dialog_form"></div>
	    </div>
	    </div>
		<script>
			var isShowLanguage = "<%=StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))%>";
			var lang = "<%= ResourceUtil.getLocaleByUser()%>".replace("_", "-");
			var loginText = "<%=ResourceUtil.getString("login.page.header.loginBtn")%>";
		    if("null"==lang){
		    	lang = "zh-CN";
		    }
			Com_IncludeFile("jquery-ui/jquery.ui.js");
			Com_IncludeFile("jquery.fullPage.js|login.js", "style/default/login_multi/js/");
		</script>
	</template:replace>
</template:include>