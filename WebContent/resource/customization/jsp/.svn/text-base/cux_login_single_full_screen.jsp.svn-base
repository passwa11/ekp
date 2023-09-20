<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.awt.Image,javax.imageio.ImageIO"%>
<%@ page import="java.util.Random,java.io.File,java.io.FilenameFilter"%>
<%@ page import="java.util.Locale,java.util.Date"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.DateUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());
%>
<c:set var="title_image_login_url" scope="session" value="login_single_full_screen"/>
<c:set var="customResourcePath" value="${LUI_ContextPath}/resource/customization/images/login_single_full_screen"/>
<c:set var="loginLogoPath" scope="request" value="${pageScope.customResourcePath}"/>

<template:include ref="default.login">
	<template:replace name="head" >
		<link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css" rel="stylesheet" type="text/css" />
        <link type="text/css" rel="stylesheet" href="${ LUI_ContextPath }/resource/style/default/login_single_full_screen/css/login.css?s_cache=${LUI_Cache}" />
		<script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/vue.min.js"></script>
 		<style>
 			.choose_hover {
 				padding:4px 6px;
 				border:3px dashed #f3b521;
 			}
 		</style>
		<script>
			Com_IncludeFile("jquery.js");
			Com_IncludeFile("jquery.fullscreenr.js|custom.js", "style/default/login_single_full_screen/js/");
		</script>
 	</template:replace>
	<template:replace name="body">
        <div id="app" class="login-background-wrap" :class="[login_logo_position]">
	    
	        <!-- 背景图片 Starts -->
	        <div class="login-background-img"><img id="login-bgImg" alt=""/></div>
	        <script type="text/javascript">
	            // 您必须指定你的背景图片的大小
	            var FullscreenrOptions = { width: 1920, height: 1080, bgID: '#login-bgImg' };
	            // 此处将会激活全屏背景
	            jQuery.fn.fullscreenr(FullscreenrOptions);
	        </script>
	        <!-- 背景图片 Ends -->

            <!-- 登录框 开始 -->
            <div class="login_iframe" :class="[{choose_hover:isLoginBoxActive},login_form_align,login_form_background_color]" >
                <!-- 容器布局层 starts -->
                <div class="login_iframe_wrap">
                     
                        <!-- 多语言选择区域 starts -->
                        <div class="lui_login_lang">
							<div>
					            <%-- 输出多语言选择区域的载体 lui_change_lang_container --%>
					            <div class="lui_change_lang_container"></div>
							</div>
						</div>
						<!-- 多语言选择区域 ends -->
						
                        <!-- 表格内容 starts -->
                        <div class="login_iframe_content ">
                            <div class="login_iframe_content_wrap">
                                <ui:combin ref="login.full.screen.form">
	                               <ui:varParam name="designed">1</ui:varParam>
	                            </ui:combin>
                            </div>
                        </div>
                        <!-- 表格内容 ends -->
                        
                        <!-- 页脚（版权信息） starts -->
                        <div class="login_footer">
				            <p>
				            	<span v-bind:class="{choose_hover:isCopyrightActive}">{{${lfn:concat('single_full_screen_footerInfo_',locale)}}}</span>
				            </p>
				        </div>
				        <!-- 页脚（版权信息） ends -->
				        
                </div>
                <!-- 容器布局层 ends -->
            </div>
            <!-- 登录框 结束 -->

        </div>
        <script type="text/javascript">
         // 获取随机登录图片信息，返回随机获取的图片名称
         function get_random_bg() {
			<%
				String path = request.getSession().getServletContext().getRealPath("/");
				path = path.replaceAll("\\\\", "/");
				if (path.endsWith("/")) {
					path = path.substring(0,path.length()-1);
				}
				String filePath = path + LoginTemplateUtil.getLoginImagePath()+"/login_single_full_screen";
				File file = new File(filePath);
				Random random = new Random();
				File[] files = file.listFiles(new FilenameFilter(){
					public boolean accept(File file, String str) {
						String name = str.toLowerCase();
						return name.startsWith(LoginTemplateUtil.RANDOM_PERFIX) && (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif") || name.endsWith(".png"));
					}
				});
				File bg = files[random.nextInt(files.length)];
			%>
			return "<%=bg.getName()%>?s_cache=${LUI_Cache}";
         }
       	//添加提示
     	$('body').append('<div class="tipsClass"><%=ResourceUtil.getString("login.page.captial.tip")%></div>');
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
        </script>
	</template:replace>
</template:include>

