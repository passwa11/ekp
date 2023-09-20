<%@page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
<%@page import="com.landray.kmss.third.weixin.work.model.WeixinWorkConfig"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<% 
	String pcScanEnable = WeixinConfig.newInstance().getWxPcScanLoginEnabled();
	String agentId = WeixinWorkConfig.newInstance().getWxAgentid();
	if(StringUtil.isNull(pcScanEnable) || "false".equals(pcScanEnable) ){
		request.setAttribute("pcScanEnable", false);
	}else{
		String url = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect?";
		String corpId = WeixinWorkConfig.newInstance().getWxCorpid();
		String redirect = URLEncoder.encode(StringUtil.formatUrl("/third/wxwork/pcScanLogin.do?method=service"));
		url = url + "appid=" + corpId + "&agentid="+ agentId +"&redirect_uri=" + redirect + "&state=state";
		request.setAttribute("pcScanEnable", true);
		request.setAttribute("url", url);
	}
%>
<c:if test="${ pcScanEnable == true }">
&nbsp;&nbsp;<a class="link" href="${ url }" target="_self">扫码登陆</a>
</c:if>