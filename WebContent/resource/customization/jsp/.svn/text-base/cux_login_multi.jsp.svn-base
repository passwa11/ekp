<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());
pageContext.setAttribute("loginImagePath", LoginTemplateUtil.getLoginImagePath());
%>
<template:include ref="default.login">
	<template:replace name="head" >
	    <link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
		<link href="${ LUI_ContextPath }/resource/style/default/login_multi/css/style.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
		<script>Com_IncludeFile("jquery.js");</script>
		<script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/vue.min.js"></script>
 		<style>
 			.choose_hover {
 				padding:4px 6px;
 				border:3px dashed #f3b521!important;
 			}
 		</style>
 	</template:replace>
	<template:replace name="body">
	<div id="app" class="login-background-wrap" :class="[login_logo_position]">
		<!-- 页眉 开始 -->
	    <div class="login_header">
	        <div class="main_content">
	            <div class="login_logo" :class="{choose_hover:isLogoActive}">
	            	<img v-bind:src="'${LUI_ContextPath }${loginImagePath }/login_multi/'+multi_logo"/>
	            </div>
	            <div class="login_info">
	                <a class="btn_login" status="off" onclick="show_shade('login_popup_wapper')">
	                	<%=ResourceUtil.getString("login.page.header.loginBtn")%>
	                </a>
	                <%-- 切换语言  --%>
	                <% if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { %>
	                <div class="multi_sel">
	                    <a class="dropdown">
	                    	<span class="dropdown-input"></span><span class="dropdown-icon">&nbsp;</span>
	                    </a>
	                </div>
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
	    <div id="containter" :class="{choose_hover:isBgImageActive}">
	        <!-- 首屏 大连接 -->
	        <div class="section section1" :style="{background:'url(${LUI_ContextPath }${loginImagePath }/login_multi/'+multi_section1_bg+') left top no-repeat',backgroundSize:'cover'}">
	            <img src="${ LUI_ContextPath }/resource/style/default/login_multi/images/login_bg_01.jpg" class="section_Bg" />
	            <div class="main_content">
	                <div class="animate_bgW">
	                    <div class="animate_bg bg1_1">
	                    </div>
	                    <div class="animate_bg bg1_2">
	                    </div>
	                </div>
	                <!-- 登录框 -->
	               	<ui:combin ref="login.form.v12new">
	               		<ui:varParam name="designed">1</ui:varParam>
	               	</ui:combin>
	                <a class="godown" data="1"><span></span></a>
	            </div>
	        </div>
	        <!-- 协同运营平台 -->
	        <div class="section section2" :style="{background:'url(${LUI_ContextPath }${loginImagePath }/login_multi/'+multi_section2_bg+') left top no-repeat',backgroundSize:'cover'}">
	            <img src="${ LUI_ContextPath }/resource/style/default/login_multi/images/login_bg_02.jpg" class="section_Bg" />
	            <div class="main_content">
	                <div class="animate_bgW">
	                    <div class="animate_bg bg2_1">
	                    </div>
	                    <div class="animate_bg bg2_2">
	                        <a><%=ResourceUtil.getString("login.page.section2.workflow")%></a>
	                        <em>|</em>
	                        <a><%=ResourceUtil.getString("login.page.section2.report")%></a>
	                        <em>|</em>
	                        <a><%=ResourceUtil.getString("login.page.section2.data")%></a>
	                        <em>|</em>
	                        <a><%=ResourceUtil.getString("login.page.section2.knowledge")%></a>
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
	        <div class="section section3" :style="{background:'url(${LUI_ContextPath }${loginImagePath }/login_multi/'+multi_section3_bg+') left top no-repeat',backgroundSize:'cover'}">
	            <img src="${ LUI_ContextPath }/resource/style/default/login_multi/images/login_bg_03.jpg" class="section_Bg" />
	            <div class="main_content">
	                <div class="animate_bgW">
	                    <div class="animate_bg bg3_1">
	                    </div>
	                    <div class="animate_bg bg3_2">
	                    </div>
		                <div class="animate_bg bg3_4">
		                </div>
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
	    <!-- 登录弹出框 -->
	    <div class="login_popup_wapper">
	        <div class="popup_btnClose" onclick="close_shade('login_popup_wapper')">
	            <span></span>
	        </div>
	        <!-- 登录框 -->
            <div id="login_dialog_form">
            	<ui:combin ref="login.form.v12new">
               		<ui:varParam name="designed">1</ui:varParam>
               		<ui:varParam name="justForm">1</ui:varParam>
               	</ui:combin>
            </div>
	    </div>
	    </div>
		<script>
			$(document).ready(function() {
				var iconfig = window.parent.config;
				new Vue({
	                el: '#app',
	                data: iconfig,
	                methods:{
						btnEnter:function(evt) {
							var $target = $(evt.target);
							var prop = $target.data('color');
							if(iconfig[prop+'_hover'] != null)
								$target.css({'background':iconfig[prop+'_hover'],'borderColor':iconfig[prop+'_hover']});
						},
						btnLeave:function(evt) {
							var $target = $(evt.target);
							var prop = $target.data('color');
							if(iconfig[prop] != null)
								$target.css({'background':iconfig[prop],'borderColor':iconfig[prop]});
						}
					}
	            });
			});
			function addBackgroundCover() {
				setTimeout(function() {
					$('.section').css('background-size','cover');
				},100);
			}
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