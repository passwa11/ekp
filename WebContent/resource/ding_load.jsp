<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@page import="com.landray.kmss.util.*"%>

<%
	String callbackUrl = request.getParameter("callbackUrl");
	callbackUrl = callbackUrl.replace("\"", "'");
	//if(callbackUrl!=null && callbackUrl.contains("&")){
	//	callbackUrl = URLEncoder.encode(callbackUrl);
	//}
	request.setAttribute("callbackUrl",  callbackUrl);
	request.getSession().setAttribute("SPRING_SECURITY_TARGET_URL_KEY",  request.getParameter("callbackUrl"));
    String corpid = null;
    String addAppKey = ResourceUtil.getKmssConfigString("kmss.ding.addAppKey");
    if(StringUtil.isNotNull(addAppKey)&&"true".equals(addAppKey)){
    	if (StringUtil.isNotNull(callbackUrl)&& callbackUrl.contains("dingAppKey")) {
    		corpid = StringUtil.getParameter(callbackUrl.replace("?", "&"),
					"dingAppKey");
    		System.out.println("----------F4  corpid:"+corpid);
		}
    	if(StringUtil.isNull(corpid)){
    		System.out.println("----------F4开启了dingAppKey开关，但是访问url不携带dingAppKey参数----------");
    	}
    }else{
    	corpid = DingConfig.newInstance().getDingCorpid();
    }
    //System.out.println("----------corpid:"+corpid);
	request.setAttribute("corpid", corpid);
	
	boolean hasLogin = !(UserUtil.getUser().isAnonymous());
	request.setAttribute("hasLogin",  hasLogin+"");
	String lang = request.getHeader("Accept-Language");
	if (StringUtil.isNotNull(lang)&& lang.indexOf(",") > -1) {
		lang = lang.trim();
		lang = lang.substring(0, lang.indexOf(","));	
	}
	
	request.setAttribute("__title__", request.getParameter("title"));
	
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8>
<title>${__title__}</title> 
<meta name=viewport
	content="width=device-width,user-scalable=no,initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta name=format-detection
	content="telephone=no,email=no,address=no,url=no">
<style>body,html{width:100%;height:100%;margin:0;padding:0}#body-loading{position:absolute;bottom:-45%;left:45%;width:50px;height:50px;position:relative;text-align:center;-webkit-animation:spi-rotate 2s infinite linear;animation:spi-rotate 2s infinite linear}#body-loading .dot1,#body-loading .dot2{width:60%;height:60%;display:inline-block;position:absolute;top:0;background-color:#67cf22;border-radius:100%;-webkit-animation:spi-bounce 2s infinite ease-in-out;animation:spi-bounce 2s infinite ease-in-out}#body-loading .dot2{top:auto;bottom:0;-webkit-animation-delay:-1s;animation-delay:-1s}@-webkit-keyframes spi-rotate{100%{-webkit-transform:rotate(360deg)}}@keyframes spi-rotate{100%{transform:rotate(360deg);-webkit-transform:rotate(360deg)}}@-webkit-keyframes spi-bounce{0%,100%{-webkit-transform:scale(0)}50%{-webkit-transform:scale(1)}}@keyframes spi-bounce{0%,100%{transform:scale(0);-webkit-transform:scale(0)}50%{transform:scale(1);-webkit-transform:scale(1)}}</style>

<script>window.dojo={}</script>
<c:import url="/sys/mobile/template/com_head.jsp"></c:import>
<script type="text/javascript" src="//g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
<script type="text/javascript">


if (document.addEventListener) {
    window.addEventListener('pageshow', function (event) {
        if (event.persisted || window.performance &&
            window.performance.navigation.type == 2)
        {
            location.reload();
        }
    },
   false);
}


var _ctx = "<%= request.getContextPath() %>";
var url = "${callbackUrl}";
var hasLogin = "${hasLogin}";
var _callbackUrl="${callbackUrl}";

var jingHao = "";
var temp=window.location.href;
if(temp.indexOf("#")>-1){
	jingHao = temp.substring(temp.indexOf("#"));
}

$(function(){
	if("true"==hasLogin){
		dd.ready(function() {
			dd.biz.navigation.close();
		});
		return;
	}
	dd.ready(function() {
	    dd.runtime.permission.requestAuthCode({
	        corpId: "${corpid}", 
	        onSuccess: function (info) {
	           var code = info.code; 
	           $.ajax({
	                url: _ctx+'/third/ding/jsapi.do?method=userLogin&code=' + code+'&j_lang=<%=lang%>',
	                type: 'GET',
	                data:{callbackUrl:_callbackUrl},
	                success: function (data, status, xhr) {
	                	if(data.error==0 && (url || url.indexOf("resource/ding_load.jsp")>0)){
	                		window.location.replace(url+jingHao);
	                	}else{
	                		alert(data.msg);
	                		window.location.replace(_ctx+"/third/pda/login.jsp");
	                	}
	                },
	                error: function (xhr, errorType, error) {
	                	 alert("load fail.."+JSON.stringify(error));
	                	 window.location.replace(_ctx+"/third/pda/login.jsp");
	                }
	            });
	        },
	        onFail : function (err){	
	        	alert("onFail.."+JSON.stringify(err));
	        	window.location.replace(_ctx+"/third/pda/login.jsp");
	        } 
	    });
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