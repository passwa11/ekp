<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Locale,java.util.Date"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());
%>
<c:set var="templatePath" value="${LUI_ContextPath}/resource/style/default/login_single_horizontal"/>
<c:set var="customResourcePath" value="${LUI_ContextPath}/resource/customization/images/login_single_horizontal"/>
<c:set var="loginLogoPath" scope="request" value="${pageScope.customResourcePath}"/>

<template:include ref="default.login">
	<template:replace name="head" >
		<link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
        <link type="text/css" rel="stylesheet" href="${pageScope.templatePath}/css/login.css?s_cache=${LUI_Cache}" />
        <script src="${pageScope.templatePath}/js/jquery.js?s_cache=${LUI_Cache}"></script>
        <script src="${pageScope.templatePath}/js/jquery.fullscreenr.js?s_cache=${LUI_Cache}"></script>
 		<script type="text/javascript" src="${LUI_ContextPath}/resource/customization/js/vue.min.js?s_cache=${LUI_Cache}"></script>
 		<style>
 		     /* 可配置项的悬停凸显样式 */
 			.choose_hover {
 				padding:4px 6px;
 				border:3px dashed #f3b521 !important;
 			}
            body{
                /* 与设计稿相近的背景纯色，为了在登录包图片出现时不突兀 */
                background-color: #fff !important;
            }
            #app{
                height: 100%;
            }
 		</style>

 	</template:replace>
	<template:replace name="body">
        <div id="app" class="login-background-wrap" :class="[login_logo_position]" >
        
        <!-- 背景图片 Starts -->
        <div class="login-background-img" :class="{choose_hover:isBgImageActive}" ><img id="login-bgImg" :src="'${customResourcePath}/'+single_login_bg" alt=""/></div>
        <script type="text/javascript">
            // 您必须指定你的背景图片的大小
            var FullscreenrOptions = { width: 1920, height: 1080, bgID: '#login-bgImg' };
            // 此处将会激活全屏背景
            jQuery.fn.fullscreenr(FullscreenrOptions);
        </script>
        <!-- 背景图片 Ends -->
             
        <!-- 登录部分，背景层 starts -->
        <div class="login_iframe" :class="[login_form_align]">
                <!-- 容器布局层 starts -->
                <div class="login_iframe_wrap">
                    <div>
                        <!-- 表格内容 starts -->
                        <div class="login_iframe_content" :class="[{choose_hover:isLoginBoxActive},login_form_background_color]">
                            <div class="login_iframe_content_wrap">
                                <div class="lui_login_form_list_lang">
									<div>
							            <%-- 输出多语言选择区域的载体 lui_change_lang_container --%>
							            <div class="lui_change_lang_container"></div>
									</div>
								</div>
	                            <% // 引入横向登录表单（designed参数代表当前页面为后台配置页，提供给表单JSP进行判断）   %>
	                            <ui:combin ref="login.horizontal.form">
	                               <ui:varParam name="designed">1</ui:varParam>
	                            </ui:combin>
                            </div>
                        </div>
                        <!-- 表格内容 ends -->
                    </div>
                </div>
                <!-- 容器布局层 ends -->
        </div>
        <!--登录部分， 登录背景层 ends -->
        
        <!-- 页脚 starts -->
        <div class="login_footer">
           <p><span :class="{choose_hover:isCopyrightActive}">{{${lfn:concat('single_footerInfo_',locale)}}}</span></p>
        </div>
        <!-- 页脚 ends -->
        
        </div>
		<script>		
		seajs.use(['lui/jquery'], function($) {
			$(document).ready(function () {
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
			mulit_lang();
		});
		//多语言
		function mulit_lang() {
			var select = $(".lui_login_input_div select");
			var radio = $(".lui_login_input_div input[type='radio']");
			if(select!=null && select.length > 0) {
				mulit_select();
			}
			if(radio!=null && radio.length > 0) {
				mulit_radio();
			}
		}
		//多语言 为下拉框时
		function mulit_select() {
			var select = $(".lui_login_input_div select");
			if(select.length>0){
				$(".lui_change_lang_container").css("display", "block");
				var options = select.find("option");
				var uli = "",
					val = "";
				var ul = $("<ul class='header_lan'>");
				options.each(function(i) {
					val = $(this).val();
					txt = $(this).text();
					uli += "<li><a onclick=changeLang('" + val + "') data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
				});
				ul.html(uli);
				$(".lui_change_lang_container").prepend(ul);
			}
		}
		//多语言 为单选框时
		function mulit_radio() {
			var radio = [];
			radio = $(".lui_login_input_div input[type='radio']");
			if(radio.length){
				$(".lui_change_lang_container").css("display", "block");
				var uli = "",
					val = "";
				var ul = $("<ul class='header_lan'>");
				radio.each(function(i) {
					val = $(this).val();
					txt = $(this).next("label").text();
					uli += "<li><a onclick=changeLang('" + val + "') data-value='" + val + "' href='javascript:void(0);'>" + txt + "</a></li>";
				});
				ul.html(uli);
				$(".lui_change_lang_container").prepend(ul);
			}
		}
		</script>         
	</template:replace>
</template:include>