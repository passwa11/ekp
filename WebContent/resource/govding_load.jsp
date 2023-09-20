<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.landray.kmss.third.govding.model.GovDingConfig"%>
<%@page import="com.landray.kmss.util.*"%>
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
	String corpid = GovDingConfig.newInstance().getDingCorpid();
	request.setAttribute("corpid", corpid);
	String callbackUrl = request.getParameter("callbackUrl");
	request.setAttribute("callbackUrl",  callbackUrl);
	request.getSession().setAttribute("SPRING_SECURITY_TARGET_URL_KEY",  request.getParameter("callbackUrl"));
	boolean hasLogin = !(UserUtil.getUser().isAnonymous());
	request.setAttribute("hasLogin",  hasLogin+"");
	String lang = request.getHeader("Accept-Language");
	if (StringUtil.isNotNull(lang)&& lang.indexOf(",") > -1) {
		lang = lang.trim();
		lang = lang.substring(0, lang.indexOf(","));	
	}
%>
<script>window.dojo={}</script>
<c:import url="/sys/mobile/template/com_head.jsp"></c:import>
<script type="text/javascript" src="https://g.alicdn.com/gdt/jsapi/1.8.2/index.js"></script>
<script type="text/javascript">
var _ctx = "<%= request.getContextPath() %>";
var url = "${callbackUrl}";
var hasLogin = "${hasLogin}";
$(function(){
	if("true"==hasLogin){
		dd.ready(function() {
			dd.closePage();
		});
		return;
	}
	
	dd.ready(function() {
		dd.getAuthCode({corpId:"${corpid}"}).then((result) => {
			  if (result) {
				  var code = "";
				  if(result.code){
					  code = result.code;
				  }else if(result.auth_code){
					  code = result.auth_code;
				  }
				  $.ajax({
		                url: _ctx+'/third/govding/jsapi.do?method=userLogin&code=' + code+'&j_lang=<%=lang%>',
		                type: 'GET',
		                success: function (data, status, xhr) {
		                	if(data.error==0 && (url || url.indexOf("resource/govding_load.jsp")>0)){
		                		window.location.replace(url);
		                	}else{
		                		window.location.replace(_ctx+"/third/pda/login.jsp");
		                	}
		                },
		                error: function (xhr, errorType, error) {
		                	 alert("load fail.."+JSON.stringify(error));
		                	 window.location.replace(_ctx+"/third/pda/login.jsp");
		                }
		            });
			  }
		})
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