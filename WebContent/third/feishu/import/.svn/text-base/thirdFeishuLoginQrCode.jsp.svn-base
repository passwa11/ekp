<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.net.URLEncoder" %>
<%@ page import="com.landray.kmss.third.feishu.model.ThirdFeishuConfig" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%
	ThirdFeishuConfig feishuConfig = ThirdFeishuConfig.newInstance();
	String ekp_url = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
	String redirect_uri = ekp_url + "/third/feishu/ssoRedirect.jsp";
	String url = "https://passport.feishu.cn/suite/passport/oauth/authorize?client_id="
			+ feishuConfig.getFeishuAppid()
			+ "&redirect_uri="+URLEncoder.encode(redirect_uri)+"&response_type=code&state=ScanLogin";
	System.out.println(url);
	pageContext.setAttribute("gotoUrl", url);
	pageContext.setAttribute("ekp_url", ekp_url);
%>
<div id="aaa">

</div>
<script src="https://sf3-cn.feishucdn.com/obj/static/lark/passport/qrcode/LarkSSOSDKWebQRCode-1.0.1.js"></script>
<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
<script>

		(function() {
			var feishuFn = function(containerId) {
				//debugger;
				var QRLoginObj = QRLogin({
					id:containerId,
					goto: "${gotoUrl}",
					width: "250",
					height: "250",
				});
				$("#"+containerId+" iframe").css("border","none");

				var handleMessage = function (event) {
					var origin = event.origin;
					// 使用 matchOrigin 方法来判断 message 是否来自飞书页面
					if(QRLoginObj.matchOrigin(origin) ) {
						debugger;
						var loginTmpCode = event.data;
						// 在授权页面地址上拼接上参数 tmp_code，并跳转
						window.location.href = "${gotoUrl}&tmp_code="+loginTmpCode;
					}
				};

				if (typeof window.addEventListener != 'undefined') {
					window.addEventListener('message', handleMessage, false);}
				else if (typeof window.attachEvent != 'undefined') {
					window.attachEvent('onmessage', handleMessage);
				}
			}

			window.qrCodeGenerator && window.qrCodeGenerator.registerCreator({
				key : "feishu",
				fn : feishuFn
			});

		})()
</script>