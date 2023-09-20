<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<% 
	String pcScanEnable = WeixinConfig.newInstance().getWxPcScanLoginEnabled();
	if(StringUtil.isNull(pcScanEnable) || "false".equals(pcScanEnable) ){
		request.setAttribute("pcScanEnable", false);
	}else{
		String url = "https://qy.weixin.qq.com/cgi-bin/loginpage?";
		String corpId = WeixinConfig.newInstance().getWxCorpid();
		String redirect = URLEncoder.encode(StringUtil.formatUrl("/third/wx/pcScanLogin.do?method=service"));
		url = url + "corp_id=" + corpId + "&redirect_uri=" + redirect + "&usertype=member";
		request.setAttribute("pcScanEnable", true);
		request.setAttribute("url", url);
	}
%>
<c:if test="${ pcScanEnable == true }">
&nbsp;&nbsp;<a class="link" href="${ url }" target="_self">扫码登陆</a>
</c:if>