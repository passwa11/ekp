<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<%

			request.setAttribute("redirectUri", session.getAttribute("redirectUri"));
			session.removeAttribute("redirectUri");//用完这个session的值 再清空
		%>
		<style>
			.login_type_head{
			    margin-left: 32px;
   				height:20px;
   				padding-top: 10px;
			}
			.login_iframe_ding_scode{
    			border: none;
    			background: #FFFFFF;
    			height: 340px !important;
    			width: 365px !important;
    			margin-top: -2px;
    			margin-left: -2px;
    			padding-top: 14px;
			}
			.header_title,.login_title,.lui_logo_img,.lui_login_form_list_top_right{display:none!important}
		</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script src="${LUI_ContextPath}/resource/js/jquery.js"></script>
<script src="https://g.alicdn.com/dingding/dinglogin/0.0.5/ddLogin.js"></script>
</head>
<body>
	<div class="login_iframe_ding login_iframe_ding_scode">
	<!-- 钉钉扫码登录应用ID不为空并且licence是蓝桥的 -->
	<div id="login_type_head_id">
		<div class="login_type_head">
			<div class="loginType" id="dingLoginId" onclick="switchLoginType(0)"
						style="float: left; cursor: pointer; color: #4285f4;">钉钉登录</div>
						<div class="loginType accoutLoginId" onclick="switchLoginType(1)"
						style="float: left; margin-left: 20px; cursor: pointer">账号登录</div>
		
		</div>
	</div>
	<div id="accountLoginType" style="display: none;">
		<ui:combin ref="${loginForm}"></ui:combin>
	</div>

	<div id="login_container"></div>
	</div>
</body>

 <!-- 钉钉扫码登录，蓝桥系统特有 -->
        <script>
        var loginPageType = "${loginPageType}";
       	$(function(){
       		if(loginPageType == "multi"){
       			$(".login_iframe_ding_scode").css("margin-top","50%");
           		$(".login_iframe_ding_scode").css("margin-left","75%");
       		}else if(loginPageType == "horizontal"){
       			$(".login_iframe_content").removeClass("whiteBackground");
       		}
       		
       	});
        //切换登录方式 loginType=0 钉钉 1账号
        function switchLoginType(loginType){
        	if(loginType == 0){
        		$(".login_iframe_ding").addClass("login_iframe_ding_scode");
        		$("#login_container").show();
        		$("#login_type_head_id").show();
        		$("#accountLoginType").hide();
        		$(".login_type_head").css("margin-left","32px");
        		$(".login_type_head").css("padding-top","0px");
        		$(".login_type_head").css("padding-bottom","0px");
        		$(".loginType").css("color","#333");
       			$("#dingLoginId").css("color","#4285f4");
       			if(loginPageType == "multi"){
           			$(".login_iframe_ding_scode").css("margin-top","50%");
               		$(".login_iframe_ding_scode").css("margin-left","75%");
           		}else if(loginPageType == "horizontal"){
           			$(".login_iframe_content").removeClass("whiteBackground");
           		}
        	}else{
        		$(".login_iframe_ding").removeClass("login_iframe_ding_scode");
        		$("#login_container").hide();
        		$("#accountLoginType").show();
        		$(".login_type_head").css("padding-top","10px");
        		$(".login_type_head").css("padding-bottom","10px");
        		if(loginPageType == "single_random"){
        			$(".loginType").css("color","#333");
           			$(".accoutLoginId").css("color","#4285f4");
           		}else if (loginPageType == "single") {
           			$(".accoutLoginId").css("color","#FFFFFF");
    			}else if(loginPageType == "multi"){
    				if($(".login_modern").find(".login_type_head").length == 0){
    					$(".login_modern").prepend($('#login_type_head_id').html());
    				}
    				$(".login_iframe_ding").css("margin-top","0px");
               		$(".login_iframe_ding").css("margin-left","0px");
    				$("#login_type_head_id").hide();
    				$(".loginType").css("color","#333");
    				$(".accoutLoginId").css("color","#4285f4");
    			}else if (loginPageType == "full_screen") {
    				$(".login_type_head").css("margin-left","0px");
    				$(".loginType").css("color","#FFFFFF");
           			$(".accoutLoginId").css("color","#4285f4");
    			}else if(loginPageType == "horizontal"){
           			$(".login_iframe_content").addClass("whiteBackground");
           			$(".loginType").css("color","#333");
           			$(".accoutLoginId").css("color","#4285f4");
           		}
        	}
        }
        
    	/*
		* 解释一下goto参数，参考以下例子：
		* var url = encodeURIComponent('http://localhost.me/index.php?test=1&aa=2');
		* var goto = encodeURIComponent('https://oapi.dingtalk.com/connect/oauth2/sns_authorize?appid=appid&response_type=code&scope=snsapi_login&state=STATE&redirect_uri='+url)
		*/
		var appId = "${appId}";
/* 			var sourceUrl = "${sourceUrl}"; */
		var redirect_uri = encodeURIComponent("${redirectUri}");
		var dingUrl = "https://oapi.dingtalk.com/connect/oauth2/sns_authorize?response_type=code&scope=snsapi_login&state=state&appid="+appId;
 	/* 	alert(dingUrl);
		alert("${redirectUri}");
		alert(appId);
		alert(corpid);  */
		var obj = DDLogin({
		     id:"login_container",
		     goto: encodeURIComponent(dingUrl+"&redirect_uri="+redirect_uri), 
		     style: "border:none;background-color:#FFFFFF;",
		     width : "365",
		     height: "400"
		 });
	
	
		var handleMessage = function (event) {
		        var origin = event.origin;
		        console.log("origin", event.origin);
		        if( origin == "https://login.dingtalk.com" ) { //判断是否来自ddLogin扫码事件。
		            var loginTmpCode = event.data; //拿到loginTmpCode后就可以在这里构造跳转链接进行跳转了
		            dingUrl += ("&loginTmpCode="+loginTmpCode+"&redirect_uri="+redirect_uri);
		            console.log(dingUrl);
		            location.href= dingUrl;
		     
		        }
		};
		if (typeof window.addEventListener != 'undefined') {
		    window.addEventListener('message', handleMessage, false);
		} else if (typeof window.attachEvent != 'undefined') {
		    window.attachEvent('onmessage', handleMessage);
		}
        </script>
</html>