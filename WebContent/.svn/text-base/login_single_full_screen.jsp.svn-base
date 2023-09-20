<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.awt.Image,javax.imageio.ImageIO"%>
<%@ page import="java.util.Random,java.io.File,java.io.FilenameFilter"%>
<%@ page import="java.util.Locale,java.util.Date"%>
<%@ page import="com.landray.kmss.web.Globals"%>
<%@ page import="com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.DateUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:import url="/login_single_full_screen_config.jsp" charEncoding="UTF-8"></c:import>
<c:set var="title_image_login_url" scope="session" value="login_single_full_screen"/>
<c:set var="customResourcePath" value="${LUI_ContextPath}/resource/customization/images/login_single_full_screen"/>
<c:set var="loginLogoPath" scope="request" value="${pageScope.customResourcePath}/${config.single_full_screen_logo}"/>
<%
Cookie open = new Cookie("isopen", "close");
open.setMaxAge(0);
open.setPath(request.getContextPath()+"/");
response.addCookie(open);
pageContext.setAttribute("loginImagePath", LoginTemplateUtil.getLoginImagePath());
%>

<template:include ref="default.login">
	<template:replace name="head" >
		<link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
		<link type="text/css" rel="stylesheet" href="${ LUI_ContextPath }/resource/style/default/login_single_full_screen/css/login.css?s_cache=${LUI_Cache}" />
       <script>
			Com_IncludeFile("jquery.js");
			Com_IncludeFile("jquery.fullscreenr.js|custom.js", "style/default/login_single_full_screen/js/");
		</script>
 	</template:replace>
	<template:replace name="body">
	    <div class="login-background-wrap ${config.login_logo_position}">
	    
	        <!-- 背景图片 Starts -->
	        <div class="login-background-img"><img src="" id="login-bgImg" alt=""/></div>
	        <script type="text/javascript">
	            // 您必须指定你的背景图片的大小
	            var FullscreenrOptions = { width: 1920, height: 1080, bgID: '#login-bgImg' };
	            // 此处将会激活全屏背景
	            jQuery.fn.fullscreenr(FullscreenrOptions);
	        </script>
	        <!-- 背景图片 Ends -->
	        
            <!-- 登录框 开始 -->
            <div class="login_iframe ${config.login_form_align} ${config.login_form_background_color}">
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
                                <c:choose>
				            		<c:when test="${appId != null &&  appId ne '' && isLding}">
				            			<!-- 钉钉扫码登录应用ID不为空并且licence是蓝桥的 -->
				            			<%request.setAttribute("loginForm", "login.full.screen.form");%>
				            			<%request.setAttribute("loginPageType", "full_screen");%>
										<%@include file="ding_qr_code_login.jsp"%>
									
				            		</c:when>
				            		<c:otherwise>
				            			<ui:combin ref="login.full.screen.form"></ui:combin>
				            		</c:otherwise>
			            		</c:choose>
                            </div>
                        </div>
                        <!-- 表格内容 ends -->
                        
                        <!-- 页脚（版权信息） starts -->
                        <div class="login_footer">
				        	<%
				        		// 版权信息的年份根据服务器时间自动获取
				        		String s_year = DateUtil.convertDateToString(new Date(), "yyyy");
				        		String footerInfo = ResourceUtil.getString("login.page.footer.info", null, null, s_year);
				        	%>
				            <p>${lfn:loginLangMsg('single_full_screen_footerInfo')}</p>
				        </div>
				        <!-- 页脚（版权信息） ends -->
				        
                </div>
                <!-- 容器布局层 ends -->
            </div>
            <!-- 登录框 结束 -->

        </div>
        <script type="text/javascript">

	     /**
	     * 获取随机登录图片名称
	     * @return 返回随机获取的图片名称 
	     */
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
       	//添加提示“大写锁定已打开”
     	$('body').append('<div class="tipsClass"><%=ResourceUtil.getString("login.page.captial.tip")%></div>');
        </script>
	</template:replace>
</template:include>