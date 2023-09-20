<%@page import="com.landray.kmss.sys.profile.util.LoginTemplateUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
pageContext.setAttribute("locale", ResourceUtil.getLocaleByUser().getCountry());
pageContext.setAttribute("loginImagePath", LoginTemplateUtil.getLoginImagePath());
%>
<template:include ref="default.login">
	<template:replace name="head" >
	    <link href="${ LUI_ContextPath }/resource/style/default/login_com/font/iconfont.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
		<link href="${ LUI_ContextPath }/resource/style/default/login_single/css/reset.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
  		<link href="${ LUI_ContextPath }/resource/style/default/login_single/css/main.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
  		<link href="${ LUI_ContextPath }/resource/style/default/login_single/css/login.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
 		<script type="text/javascript" src="${LUI_ContextPath }/resource/customization/js/vue.min.js?s_cache=${LUI_Cache}"></script>
 		<style>
 			.choose_hover {
 				padding:4px 6px;
 				border:3px dashed #f3b521;
 			}
 			body {
 				background:#fff !important;
 			}
            .bg_corver{
                background-size: cover !important;
            }
 		</style>
 	</template:replace>
	<template:replace name="body">
	<div id="app" class="login-background-wrap" :class="[login_logo_position]">
	
	<!--头部信息 Starts-->
    <div class="v11_login_header">
        <div class="mWidth clr">
            <div class="logoBox" :class="{choose_hover:isLogoActive}">
            	<div class="logoImg">
            		<img v-bind:src="'${LUI_ContextPath }${loginImagePath }/login_single/'+single_logo"/>
            	</div>
            	<span class="logoTxt">{{${lfn:concat('single_logo_text_',locale)}}}</span>
            </div>
        </div>
    </div>
    <!--头部信息 Ends-->
    <!--中间主体 Starts-->
    <div class="v11_loginWrapper" :class="{choose_hover:isBgImageActive}" :style="{background:'url(${LUI_ContextPath }${loginImagePath }/login_single/'+single_login_bg+') no-repeat 0',backgroundSize:'cover'}">
        <div class="v11_loginBox" style="background:none;">
            <!--登录框 Starts-->
            <div class="v11_loginBar" :class="[login_form_align]">
                 <div class="v11_loginBar_headL">
                    <div class="v11_loginBar_headR">
                        <div class="v11_loginBar_headC">
                        </div>
                    </div>
                </div>
                        <div class="v11_loginBar_tabContent">
                        	<table border="0" cellpadding="0" cellspacing="0">
                        		<tr>
                        			<td style="height: 240px;" valign="middle">
                            			<ui:combin ref="login.form">
                            				<ui:varParam name="designed">1</ui:varParam>
                            			</ui:combin>
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
    <div :class="{choose_hover:isProductActive}">
        <div clas s="mWidth clr">
            <!--产品信息 Starts-->
            <ul class="v11_proInfo clr">
                <li><a :title="${lfn:concat('single_im_',locale)}"><span class="icon_pro_KK bg_corver" :style="{background:'url(${LUI_ContextPath }${loginImagePath }/login_single/'+single_im_icon+') no-repeat 0 center'}"></span>{{${lfn:concat('single_im_',locale)}}}</a></li>
                <li><a :title="${lfn:concat('single_kms_',locale)}"><span class="icon_pro_KMS bg_corver" :style="{background:'url(${LUI_ContextPath }${loginImagePath }/login_single/'+single_kms_icon+') no-repeat 0 center'}"></span>{{${lfn:concat('single_kms_',locale)}}}</a></li>
                <li><a :title="${lfn:concat('single_ekp_',locale)}"><span class="icon_pro_EKP bg_corver" :style="{background:'url(${LUI_ContextPath }${loginImagePath }/login_single/'+single_ekp_icon+') no-repeat 0 center'}"></span>{{${lfn:concat('single_ekp_',locale)}}}</a></li>
            </ul>
            <!--产品信息 Ends-->
        </div>
    </div>
    <!--链接信息 Ends-->
    <!--注脚 Starts-->
    <div class="v11_loginFooter">
        <p>为了获得最佳操作体验，建议您使用最新版本的浏览器，支持1024 X 768以上分辨率</p>
        <p><span :class="{choose_hover:isCopyrightActive}">{{${lfn:concat('single_footerInfo_',locale)}}}</span></p>
    </div>
    <!--注脚 Ends-->
    </div>
<script>		
/*** 扫一扫 ***
*********************************************************************************************************************************************************/
function addBackgroundCover() {
	setTimeout(function() {
		$('.v11_loginWrapper').css('background-size','cover');
	},100);
}
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