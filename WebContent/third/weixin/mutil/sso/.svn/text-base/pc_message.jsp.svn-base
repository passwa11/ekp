<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.landray.kmss.util.SecureUtil"%>
<%@page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="com.landray.kmss.third.weixin.mutil.constant.WxmutilConstant"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig"%>
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
	String fdTodoId= request.getParameter("fdTodoId");
	String url= request.getParameter("url");
	String key = request.getParameter("wxkey");
	request.setAttribute("wxkey", key);
	String domainName = WeixinMutilConfig.newInstance(key).getWxDomain();
	int type = MobileUtil.getClientType(request);
	if(type==6){
		domainName = WeixinConfig.newInstance().getWxDomain();
	}
	if(StringUtil.isNull(domainName))
		domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
	if(domainName.endsWith("/"))
		domainName = domainName.substring(0, domainName.length()-1);
	if(StringUtil.isNotNull(fdTodoId)){
		url = domainName+"/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="+fdTodoId+"&oauth=" + WxmutilConstant.OAUTH_EKP_FLAG + "&wxkey=" + key;;
	}else if(StringUtil.isNotNull(url)){
		url = SecureUtil.BASE64Decoder(url);
	}else{
		url = domainName+"/third/pda/indexdefault.jsp&oauth=" + WxmutilConstant.OAUTH_EKP_FLAG;
	}
	request.setAttribute("ourl", url);
	request.setAttribute("url", URLEncoder.encode(url, "UTF-8"));
%>
</head>
<body>

<script type="text/javascript" src="//res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="//rescdn.qqmail.com/node/wwopen/wwopenmng/js/3rd/zepto.min$43c1b894.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<div id="_load">
	<bean:message key="pc.sso.loading" bundle="third-weixin-mutil" />
</div>
</body>
<script>
	$(function(){
		if(isWeiXin()){
			var url = '<c:url value="/third/wxwork/mutil/jsapi/wxJsapi.do?method=pcJsapiSignature&wxkey=${wxkey}&pUrl=${url}&url=" />'+encodeURIComponent(window.location.href.split('#')[0])+"&w";
			
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
	
	function isWeiXin() {
		var ua = window.navigator.userAgent.toLowerCase();
		if (('windowswechat'==ua.match(/windowswechat/i)||'wechat'==ua.match(/wechat/i))&&'wxwork'==ua.match(/wxwork/i)) {
			return true;
		} else {
			return false;
		}
	}

	
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
		        if(res.err_msg == "openDefaultBrowser:ok"){
		            document.getElementById("_load").innerHTML='<bean:message key="pc.sso.loading.correct" bundle="third-weixin-mutil" />';
		            wx.closeWindow();
		            window.close();
		        }else{
		        	document.getElementById("_load").innerHTML='<bean:message key="pc.sso.loading.error" bundle="third-weixin-mutil" />';
		        }
		    });
		}); 
	}
</script>
</html>

