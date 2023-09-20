<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@page import="com.landray.kmss.third.ding.constant.DingConstant"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.net.URLEncoder" %>
<%
	DingConfig dingConfig = DingConfig.newInstance();
	String url = DingConstant.DING_PREFIX + "/connect/oauth2/sns_authorize?appid="
			+ dingConfig.getDingPcScanappId()
			+ "&response_type=code&scope=snsapi_login&state=STATE";
	String domainName = dingConfig.getDingDomain();
	String viewUrl = "/third/ding/pcScanLogin.do?method=service";
	if (StringUtil.isNotNull(domainName)) {
		viewUrl = domainName + viewUrl;
	} else {
		viewUrl = StringUtil.formatUrl(viewUrl);
	}
	String j_lang = request.getParameter("j_lang");
	if (StringUtil.isNotNull(j_lang))
		viewUrl += "&j_lang=" + request.getParameter("j_lang");
	
	pageContext.setAttribute("redirect_uri", viewUrl);
	pageContext.setAttribute("dingUrl", url);
%>
<div id="ding_login_container" style="display:none;">
</div>
<script src="https://g.alicdn.com/dingding/dinglogin/0.0.5/ddLogin.js"></script>
<script>
		(function() {
			var dingFn = function(containerId) {
				
				var dingUrl = "${dingUrl}";
				var redirect_uri = encodeURIComponent("${redirect_uri}");
				
				var obj = DDLogin({
				     id: containerId,
				     goto: encodeURIComponent(dingUrl + "&redirect_uri=" + redirect_uri), 
				     style: "border:none;background-color:#FFFFFF;",
				     width : "240",
				     height: "300"
				 });
			
				var handleMessage = function (event) {
				        var origin = event.origin;
				        if(origin == "https://login.dingtalk.com") { //判断是否来自ddLogin扫码事件。
				            var loginTmpCode = event.data; //拿到loginTmpCode后就可以在这里构造跳转链接进行跳转了
				            dingUrl += ("&loginTmpCode=" + loginTmpCode + "&redirect_uri=" + redirect_uri);
				            location.href = dingUrl;
				        }
				};
				if (typeof window.addEventListener != 'undefined') {
				    window.addEventListener('message', handleMessage, false);
				} else if (typeof window.attachEvent != 'undefined') {
				    window.attachEvent('onmessage', handleMessage);
				}
			}
			
			window.qrCodeGenerator && window.qrCodeGenerator.registerCreator({
				key : "ding",
				fn : dingFn
			}); 
		})()
</script>