<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="com.landray.kmss.third.weixin.work.constant.WxworkConstant"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.SecureUtil"%>
<%@page import="com.landray.kmss.third.weixin.work.model.WeixinWorkConfig"%>
<%@ page import="com.landray.kmss.third.weixin.work.util.WxworkUtils" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String domainName = WxworkUtils.getWxworkDomain();
	//微信公众号打开
	int type = MobileUtil.getClientType(request);
	if(type==6 && StringUtil.isNotNull(WeixinConfig.newInstance().getWxDomain())){
		domainName = WeixinConfig.newInstance().getWxDomain();
	}

	String isBase64 = request.getParameter("base64");
	request.setAttribute("base64",isBase64);

	String mUrl = request.getParameter("mUrl");
	//pc端内打开，pc端地址覆盖移动端地址
	if(type==-1){
		mUrl = request.getParameter("pUrl");
	}
	//base64解密
	if("true".equals(isBase64) && StringUtil.isNotNull(mUrl)){
		mUrl = SecureUtil.BASE64Decoder(mUrl);
	}
	if(StringUtil.isNotNull(mUrl)){
		if(!mUrl.startsWith("http")||!mUrl.startsWith("https")){
			mUrl = StringUtil.formatUrl(mUrl, domainName);
			if(type!=-1){
				if(mUrl.indexOf("?")==-1){
					mUrl = mUrl + "?oauth=" + WxworkConstant.OAUTH_EKP_FLAG;
				}else{
					mUrl = mUrl + "&oauth=" + WxworkConstant.OAUTH_EKP_FLAG;
				}
			}
		}
	}
	if(StringUtil.isNull(mUrl)){
		mUrl = domainName+"/third/pda/indexdefault.jsp&oauth=" + WxworkConstant.OAUTH_EKP_FLAG;
		if(type==-1){
			mUrl = domainName;
		}
	}
	String pUrl = request.getParameter("pUrl");
	//base64解密
	if("true".equals(isBase64)&&StringUtil.isNotNull(pUrl)){
		pUrl = SecureUtil.BASE64Decoder(pUrl);
	}
	request.setAttribute("pUrl", URLEncoder.encode(pUrl, "UTF-8"));

	//是否内部打开
	boolean innerEnable = false;
	String inner = request.getParameter("inner");
	if(StringUtil.isNotNull(inner)&&"true".equals(inner)){
		innerEnable = true;
	}
	request.setAttribute("innerEnable",innerEnable);

	//关闭窗口
	String close = request.getParameter("close");
	if(StringUtil.isNotNull(close)&&"1".equals(close)){
		request.setAttribute("cs", "1");
	}else{
		request.setAttribute("cs", "0");
	}

	//是否是企业微信PC端
	String isWxWorkPC = "0";
	if(MobileUtil.WXWORK_PC == type){
		isWxWorkPC = "1";
	}
	request.setAttribute("isWxWorkPC",isWxWorkPC);
	//System.out.println("isWxWorkPC:"+isWxWorkPC);

	//移动端打开
	if("0".equals(isWxWorkPC)){
		response.sendRedirect(mUrl);
		return;
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
	<title><bean:message key="pc.sso.name" bundle="third-weixin-work" /></title>
</head>
<body>

<script type="text/javascript" src="<%= request.getContextPath() %>/sys/mobile/js/lib/weixin/weixin.js"></script>
<script type="text/javascript" src="//rescdn.qqmail.com/node/wwopen/wwopenmng/js/3rd/zepto.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<div id="_load">
	<bean:message key="pc.sso.loading" bundle="third-weixin-work" />
</div>
</body>
<script>
	$(function(){
		if("1" == "${isWxWorkPC}"){
			var innerEnable ="<%=innerEnable%>";
			if(innerEnable == "true"){
				window.location.href="<%=StringUtil.formatUrl(pUrl, domainName)%>";
			}else{
				var url = '<c:url value="/third/wxwork/jsapi/wxJsapi.do?method=pcJsapiSignature&base64=${base64}&pUrl=${pUrl}&url=" />'+encodeURIComponent(window.location.href.split('#')[0]);
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
			}
		}else{
			window.location.href="<%=mUrl%>";
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
					// 关闭页面
					window.close();
					wx.closeWindow();
				}else{
					document.getElementById("_load").innerHTML='<bean:message key="pc.sso.loading.error" bundle="third-weixin-work" />';
					window.location.href="<%=StringUtil.formatUrl(pUrl, domainName)%>";
				}
			});
		});
	}
</script>
</html>
