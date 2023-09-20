<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%-- <%@ include file="/login_single_config.jsp"%> --%>
<c:import url="/login_single_config.jsp" charEncoding="UTF-8"></c:import>
<c:set var="title_image_login_url" scope="session" value="login_single"/>
<%pageContext.setAttribute("loginImagePath", LoginTemplateUtil.getLoginImagePath());%>
<!-- 蓝桥扫码登录页改造，不影响原有ekp登录页 -->
<template:include ref="default.login">
	<template:replace name="head" >
	    <link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
        <link href="${ LUI_ContextPath }/resource/style/default/login_single/css/reset.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
  		<link href="${ LUI_ContextPath }/resource/style/default/login_single/css/main.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
  		<link href="${ LUI_ContextPath }/resource/style/default/login_single/css/login.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
 		<style>
 			.lui_login_button_div_c {
 				color:${config.loginBtn_font_color};
 			}

 		</style>
 	</template:replace>
	<template:replace name="body">
	
	<div class="login-background-wrap ${config.login_logo_position}">
	<!--头部信息 Starts-->
    <div class="v11_login_header">
        <div class="mWidth clr">
            <div class="logoBox">
            	<div class="logoImg">
            		<img alt="" src="<c:url value="${loginImagePath }/login_single/${config.single_logo}"/>">
            	</div>
            	<span class="logoTxt">${lfn:loginLangMsg('single_logo_text') }</span>
            </div>
        </div>
    </div>
    <!--头部信息 Ends-->
    <!--中间主体 Starts-->
    <div class="v11_loginWrapper" style="background:url(${LUI_ContextPath }${loginImagePath }/login_single/${config.single_login_bg}) no-repeat center;background-size:cover;">
        <div class="v11_loginBox" style="background:none;">
            <!--登录框 Starts-->
            <div class="v11_loginBar ${config.login_form_align }">
                 <div class="v11_loginBar_headL">
                    <div class="v11_loginBar_headR">
                        <div class="v11_loginBar_headC">
                            
                        </div>
                    </div>
                </div>
                        <div class="v11_loginBar_tabContent">
                        	<table border="0" cellpadding="0" cellspacing="0">
                        		<tr>
                        			<td valign="middle">
                        				<c:choose>
						            		<c:when test="${appId != null &&  appId ne '' && isLding}">
						            			<!-- 钉钉扫码登录应用ID不为空并且licence是蓝桥的 -->
						            			<%request.setAttribute("loginForm", "login.form");%>
						            			<%request.setAttribute("loginPageType", "single");%>
												<%@include file="ding_qr_code_login.jsp"%>
											
						            		</c:when>
						            		<c:otherwise>
						            			 <ui:combin ref="login.form"></ui:combin>
						            		</c:otherwise>
						            	</c:choose>
                            		</td>
                            	</tr>
                            </table>
                        </div>
                      <div class="v11_loginBar_footerL">
                    <div class="v11_loginBar_footerR">
                        <div class="v11_loginBar_footerC">
                        </div>
                    </div>
                </div>
            </div>
            <!--登录框 Ends-->
			 <!-- 扫一扫 Starts -->
			 <div class="v11_richScan_w">
                <a class="v11_richScan_c"></a>
                <div class="v11_richScan_mask">
                    <div class="v11_richScan_mask_headL">
                        <div class="v11_richScan_mask_headR">
                            <div class="v11_richScan_mask_headC">
                            </div>
                        </div>
                    </div>
                    <div class="v11_richScan_mask_contentL">
                        <div class="v11_richScan_mask_contentR">
                            <div class="v11_richScan_mask_contentC">
                                <ul class="clr">
                                    <li>
                                        <h3>
                                            iPone/iPad
                                        </h3>
                                        <img src="${ LUI_ContextPath }/resource/style/default/login_single/codes/code_iphone.png" alt="" /><p>
                                            下载iPhone客户端
                                        </p>
                                    </li>
                                    <li>
                                        <h3>
                                            Android
                                        </h3>
                                        <img src="${ LUI_ContextPath }/resource/style/default/login_single/codes/code_android.png" alt="" /><p>
                                            下载Android客户端
                                        </p>
                                    </li>
                                    <li>
                                        <h3>
                                           蓝凌微云
                                        </h3>
                                        <img src="${ LUI_ContextPath }/resource/style/default/login_single/codes/micooffice.jpeg" alt="" /><p>
                                            开启微信移动办公之旅
                                        </p>
                                    </li>
                                    <li class="last">
                                        <h3>
                                            微服务平台
                                        </h3>
                                        <img src="${ LUI_ContextPath }/resource/style/default/login_single/codes/code_service.png" alt="" /><p>
                                           获得蓝凌实时在线服务
                                        </p>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="v11_richScan_mask_footL">
                        <div class="v11_richScan_mask_footR">
                            <div class="v11_richScan_mask_footC">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
			 <!-- 扫一扫 Ends -->

        </div>
    </div>
    <!--中间主体 Ends-->
    <!--链接信息 Starts-->
    <div class="mWidth clr">
        <!--产品信息 Starts-->
        <ul class="v11_proInfo clr">
            <li><a title="${lfn:loginLangMsg('single_im') }"><span class="icon_pro_KK" style="background:url(${LUI_ContextPath }${loginImagePath }/login_single/${config.single_im_icon}) no-repeat center;background-size:cover;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${LUI_ContextPath }${loginImagePath }/login_single/${config.single_im_icon}',sizingMethod='scale');"></span>${lfn:loginLangMsg('single_im') }</a></li>
            <li><a title="${lfn:loginLangMsg('single_kms') }"><span class="icon_pro_KMS" style="background:url(${LUI_ContextPath }${loginImagePath }/login_single/${config.single_kms_icon}) no-repeat center;background-size:cover;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${LUI_ContextPath }${loginImagePath }/login_single/${config.single_kms_icon}',sizingMethod='scale');"></span>${lfn:loginLangMsg('single_kms') }</a></li>
            <li><a title="${lfn:loginLangMsg('single_ekp') }"><span class="icon_pro_EKP" style="background:url(${LUI_ContextPath }${loginImagePath }/login_single/${config.single_ekp_icon}) no-repeat center;background-size:cover;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='${LUI_ContextPath }${loginImagePath }/login_single/${config.single_ekp_icon}',sizingMethod='scale');"></span>${lfn:loginLangMsg('single_ekp') }</a></li>
        </ul>
        <!--产品信息 Ends-->
    </div>
    <!--链接信息 Ends-->
    <!--注脚 Starts-->
    <div class="v11_loginFooter">
        <p>为了获得最佳操作体验，建议您使用最新版本的浏览器，支持1024 X 768以上分辨率</p>
        <p>${lfn:loginLangMsg('single_footerInfo') }</p>
    </div>
    <!--注脚 Ends-->
	</div>
<script>		
/*** 扫一扫 ***
*********************************************************************************************************************************************************/

seajs.use(['lui/jquery'], function($) {
	$(document).ready(function () {
//	    var MoreLinksShowHandles = null;
//	    var NoneHandles = null;
//	    var w = $(".v11_richScan_mask").width();
//	    var h = $(".v11_richScan_mask").height();
//	    $(".v11_richScan_mask").css({ "width": "0px", "height": "0px", "display": "none" });
//	    function showMoreLinks() {
//	        clearTimeout(MoreLinksShowHandles);
//	        clearTimeout(NoneHandles);
//	        $(".v11_richScan_mask").css("display", "block");
//	        $('.v11_richScan_mask').stop().animate({ "width": w, "height": h }, 1500);
//	    }
//	    function hideMoreLinks() {
//	        MoreLinksShowHandles = setTimeout(function () {
//	            $('.v11_richScan_mask').stop().animate({ "width": "0px", "height": "0px" }, 1000);
//	        }, 500);
//	        NoneHandles = setTimeout(function () { $(".v11_richScan_mask").css("display", "none"); }, 1500);
//	    }
//	    $(".v11_richScan_c").mouseover(function () {
//	        showMoreLinks();
//	    });
//	    $(".v11_richScan_c").mouseout(function () {
//	        hideMoreLinks();
//	    });
//	    $(".v11_richScan_mask").mouseover(function () {
//	        showMoreLinks();
//	    });
//	    $(".v11_richScan_mask").mouseout(function () {
//	        hideMoreLinks();
//	    });
			var login_iframe = $(".v11_richScan_mask");
			var iframe_height = login_iframe.height();
			//二维码
			$(".v11_richScan_w").hover(function() {
				iframe_height = login_iframe.height();
				$(this).find(".v11_richScan_mask")
					.css("height", iframe_height).stop(true, true).show(300);
			}, function() {
				$(this).find(".v11_richScan_mask")
					.stop(true, true).hide(300);
			});
	});
});
		
</script> 	
	</template:replace>
</template:include>