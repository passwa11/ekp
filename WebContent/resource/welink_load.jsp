<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8>
<title>load...</title> 
<meta name=viewport
	content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta name=format-detection
	content="telephone=no,email=no,address=no,url=no">
<style>body,html{width:100%;height:100%;margin:0;padding:0}#body-loading{position:absolute;bottom:-45%;left:45%;width:50px;height:50px;position:relative;text-align:center;-webkit-animation:spi-rotate 2s infinite linear;animation:spi-rotate 2s infinite linear}#body-loading .dot1,#body-loading .dot2{width:60%;height:60%;display:inline-block;position:absolute;top:0;background-color:#67cf22;border-radius:100%;-webkit-animation:spi-bounce 2s infinite ease-in-out;animation:spi-bounce 2s infinite ease-in-out}#body-loading .dot2{top:auto;bottom:0;-webkit-animation-delay:-1s;animation-delay:-1s}@-webkit-keyframes spi-rotate{100%{-webkit-transform:rotate(360deg)}}@keyframes spi-rotate{100%{transform:rotate(360deg);-webkit-transform:rotate(360deg)}}@-webkit-keyframes spi-bounce{0%,100%{-webkit-transform:scale(0)}50%{-webkit-transform:scale(1)}}@keyframes spi-bounce{0%,100%{transform:scale(0);-webkit-transform:scale(0)}50%{transform:scale(1);-webkit-transform:scale(1)}}</style>
<%
	String callbackUrl = request.getParameter("callbackUrl");
	callbackUrl = callbackUrl.replace("\"", "'");
	request.setAttribute("callbackUrl",  callbackUrl);
	request.getSession().setAttribute("SPRING_SECURITY_TARGET_URL_KEY",  callbackUrl);
%>
<script>window.dojo={}</script>
<c:import url="/sys/mobile/template/com_head.jsp"></c:import>

<script src="https://open-doc.welink.huaweicloud.com/docs/jsapi/2.0.3/hwh5-cloudonline.js"></script>
<!-- 引入Vconsole用于调试，生产环境时请去掉 -->
<script src="https://wechatfe.github.io/vconsole/lib/vconsole.min.js?v=3.2.0"></script>

<script type="text/javascript">
var _ctx = "<%= request.getContextPath() %>";
var callbackUrl = "${callbackUrl}";
$(function(){
	HWH5.getAuthCode().then(function (data) {
			console.log(data);
			//alert(JSON.stringify(data));
			var code = data.code;
			if(code){
				var url = Com_SetUrlParameter(callbackUrl,"welinkCode",code);
				window.location.replace(url);
			}else{
				alert("获取不到code:"+JSON.stringify(data));
			}
			
		}).catch(function (error) {
			alert(error);
			console.log('获取异常', error);
			window.location.replace(_ctx+"/third/pda/login.jsp");
		});
});
</script>
</head>
<body style="font-size: initial">
	<div id=body-loading>
		<div class=dot1></div>
		<div class=dot2></div>
	</div>
</body>
</html>