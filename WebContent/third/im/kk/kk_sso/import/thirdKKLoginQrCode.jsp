<%@page import="com.landray.kmss.third.im.kk.service.*"%>
<%@page import="com.landray.kmss.third.im.kk.constant.*"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.net.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
	String kkScanUrl;
	IKkImConfigService kkImConfigService = (IKkImConfigService) SpringBeanUtil.getBean("kkImConfigService");
	boolean KKqrcodeEnabled=kkImConfigService.isChangeKKqrcodeEnabled();
	if(KKqrcodeEnabled==false) {
		IKkUserService kkUserService = (IKkUserService) SpringBeanUtil.getBean("kkUserService");
		JSONObject json = kkUserService.getSign();
		StringBuffer url = new StringBuffer();
		url.append(json.getString("outerDomain"));
		url.append(json.getString("outerDomain").lastIndexOf("/") == json.getString("outerDomain").length() - 1 ? ""
				: "/");
		url.append(KkQrcodeConstants.KK_QRCODE_INDEX);
		url.append("?sign=").append(json.getString("sign"));
		url.append("&lang=").append(URLEncoder.encode(ResourceUtil.getLocaleStringByUser()));
		url.append("&redirectUrl=").append(json.getString("redirectUrl"));
		kkScanUrl = url.toString();
	}
	else{
		kkScanUrl = request.getContextPath() + "/third/im/kk/kk_sso/pcScanLoginNew.jsp";
	}
%>
<script>
	(function() {
		var kkFn = function(containerId) {
			seajs.use("lui/jquery", function() {
				$("#" + containerId)
					.append("<iframe style='width:100%;height:100%;border:0;' src='<%=kkScanUrl%>'></iframe>");
			});
		}
		
		window.qrCodeGenerator && window.qrCodeGenerator.registerCreator({
			key : "kk",
			fn : kkFn
		});
	})()
</script>