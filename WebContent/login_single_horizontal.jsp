<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.Locale,java.util.Date"%>
<%@ page import="com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.DateUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- 引入   《 单屏（横向登录）模式 》  基础 配置 --%>
<c:import url="/login_single_horizontal_config.jsp" charEncoding="UTF-8"></c:import>
<c:set var="title_image_login_url" scope="session" value="login_single_horizontal"/>
<%-- 设置模板静态资源文件存储路径变量  --%>
<c:set var="templatePath" value="${LUI_ContextPath}/resource/style/default/login_single_horizontal"/>
<c:set var="customResourcePath" value="${LUI_ContextPath}/resource/customization/images/login_single_horizontal"/>
<c:set var="loginLogoPath" scope="request" value="${pageScope.customResourcePath}/${config.single_logo}"/>

<template:include ref="default.login">
	<template:replace name="head" >
        <link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
        <link type="text/css" rel="stylesheet" href="${pageScope.templatePath}/css/login.css?s_cache=${LUI_Cache}" />
        <script src="${pageScope.templatePath}/js/jquery.js"></script>
        <script src="${pageScope.templatePath}/js/jquery.fullscreenr.js"></script>
        <script src="${pageScope.templatePath}/js/custom.js"></script>
        <style>
            body{
                /* 与设计稿相近的背景纯色，为了在登录包图片出现时不突兀 */
                background-color: #fff !important;
            }
			.lui_login_button_div_wrap .login_submit_btn {
				background-color:${config.loginBtn_bgColor};
				color:${config.loginBtn_font_color};
			}
			.lui_login_button_div_wrap .login_submit_btn:hover {
				background-color:${config.loginBtn_bgColor_hover};
			}          
        </style>
 	</template:replace>
	<template:replace name="body">
        <script>
            var templatePath = '${pageScope.templatePath}';
            var loginBackgroundImgPath = '${pageScope.customResourcePath}/${config.single_login_bg}';
        </script>
        <div class="login-background-wrap ${config.login_logo_position}">  
        <!-- 背景图片 Starts -->
        <div class="login-background-img"><img id="login-bgImg" alt=""/></div>
        <script type="text/javascript">
            // 您必须指定你的背景图片的大小
            var FullscreenrOptions = { width: 1920, height: 1080, bgID: '#login-bgImg' };
            // 此处将会激活全屏背景
            jQuery.fn.fullscreenr(FullscreenrOptions);
        </script>
        <!-- 背景图片 Ends -->
    
        <!-- 登录部分，背景层 starts -->
        <div class="login_iframe ${config.login_form_align}" >
                <!-- 容器布局层 starts -->
                <div class="login_iframe_wrap">
                    <div>
                        <!-- 表格内容 starts -->
                        <div class="login_iframe_content ${config.login_form_background_color}">
                            <div class="login_iframe_content_wrap">
                               	<div class="lui_login_form_list_lang">
									<div>
							            <%-- 输出多语言选择区域的载体 lui_change_lang_container --%>
							            <div class="lui_change_lang_container"></div>
									</div>
								</div>
								<c:choose>
				            		<c:when test="${appId != null &&  appId ne '' && isLding}">
				            			<!-- 钉钉扫码登录应用ID不为空并且licence是蓝桥的 -->
				            			<%request.setAttribute("loginForm", "login.horizontal.form");%>
				            			<%request.setAttribute("loginPageType", "horizontal");%>
										<%@include file="ding_qr_code_login.jsp"%>
									
				            		</c:when>
				            		<c:otherwise>
				            			  <ui:combin ref="login.horizontal.form"></ui:combin>
				            		</c:otherwise>
				            	</c:choose>
                              
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
        	<%  // Copyright &copy; 2001-{0} 蓝凌软件 版权所有
        		// 版权信息的年份根据服务器时间自动获取
        		String s_year = DateUtil.convertDateToString(new Date(), "yyyy");
        		String footerInfo = ResourceUtil.getString("login.page.footer.info", null, null, s_year);
        	%>
            <p>${lfn:loginLangMsg('single_footerInfo') }</p>
        </div>
        <!-- 页脚 ends -->
        <div class="hiddenDiv"></div>
    </div>        
	</template:replace>
</template:include>

