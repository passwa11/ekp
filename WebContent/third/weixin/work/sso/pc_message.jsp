<%@page import="java.net.URLEncoder"%>
<%@page import="com.landray.kmss.util.SecureUtil"%>
<%@page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="com.landray.kmss.third.weixin.work.constant.WxworkConstant"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.third.weixin.work.model.WeixinWorkConfig"%>
<%@ page import="com.landray.kmss.third.weixin.work.util.WxworkUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
<title><bean:message key="pc.sso.name" bundle="third-weixin-work" /></title>
<%
	String domainName = WxworkUtils.getWxworkDomain();
	//微信公众号打开
	int type = MobileUtil.getClientType(request);
	if(type==6 && StringUtil.isNotNull(WeixinConfig.newInstance().getWxDomain())){
		domainName = WeixinConfig.newInstance().getWxDomain();
	}
	//构建待办跳转地址
	String fdTodoId= request.getParameter("fdTodoId");
	String url= request.getParameter("url");
	if(StringUtil.isNotNull(fdTodoId)){
		url = domainName+"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="+fdTodoId+"&oauth=" + WxworkConstant.OAUTH_EKP_FLAG;
	}else if(StringUtil.isNotNull(url)){
		url = SecureUtil.BASE64Decoder(url);
	}else{
		url = domainName+"/third/pda/indexdefault.jsp&oauth=" + WxworkConstant.OAUTH_EKP_FLAG;
	}
	request.setAttribute("ourl", url);
	request.setAttribute("url", URLEncoder.encode(url, "UTF-8"));

	//是否是企业微信PC端
	String isWxWorkPC = "0";
	if(MobileUtil.WXWORK_PC == type){
		isWxWorkPC = "1";
	}
	request.setAttribute("isWxWorkPC",isWxWorkPC);
	//打开方式
	String openType = WeixinWorkConfig.newInstance().getWxWorkTodoPcOpenType();
	if("in".equals(openType)|| "0".equals(isWxWorkPC)){
		//内部打开
		response.sendRedirect(url);
		return;
	}
%>
</head>
<body>

<script type="text/javascript" src="<%= request.getContextPath() %>/sys/mobile/js/lib/weixin/weixin.js"></script>
<script type="text/javascript" src="//rescdn.qqmail.com/node/wwopen/wwopenmng/js/3rd/zepto.min$43c1b894.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<div id="_load">
	<bean:message key="pc.sso.loading" bundle="third-weixin-work" />
</div>
</body>
<script>
	$(function(){
		if("1" == "${isWxWorkPC}"){
			var url = '<c:url value="/third/wxwork/jsapi/wxJsapi.do?method=pcJsapiSignature&pUrl=${url}&url=" />'+encodeURIComponent(window.location.href.split('#')[0]);
			$.ajax({
			   type: "POST",
			   url: url,
			   async:false,
			   dataType: "json",
			   success: function(data){
					if(data && data.appId){
						_config(data);
					}
			   }
			});
		}else{
			window.location.href='${ourl}';
		}
	});

	
	function _config(signInfo){
		wx.config({
			debug : false,
			beta : true,
			appId : signInfo.appId,
			timestamp : signInfo.timestamp,
			nonceStr : signInfo.noncestr,
			signature : signInfo.signature,
			jsApiList : ['openDefaultBrowser']
		});
		wx.ready(function(){
			wx.invoke('openDefaultBrowser', {
		        'url': signInfo.url // 在默认浏览器打开redirect_uri，并附加code参数；也可以直接指定要打开的url，此时不会附带上code参数。
		        }, function(res){
				if((res.err_msg && res.err_msg == "openDefaultBrowser:ok") || (res.errMsg && res.errMsg == "openDefaultBrowser:ok")){
		            document.getElementById("_load").innerHTML='<bean:message key="pc.sso.loading.correct" bundle="third-weixin-work" />';
		            wx.closeWindow();
		            window.close();
		        }else{
		        	document.getElementById("_load").innerHTML='<bean:message key="pc.sso.loading.error" bundle="third-weixin-work"/>';
		        }
		    });
		}); 
	}
</script>
</html>

