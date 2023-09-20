<%@page import="java.net.URLEncoder"%>
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
<title><bean:message key="pc.sso.name" bundle="third-weixin-mutil" /></title>
<%
	String domainName = WeixinMutilConfig.newInstance().getWxDomain();
	int type = MobileUtil.getClientType(request);
	if(type==6){
		domainName = WeixinConfig.newInstance().getWxDomain();
	}
	if(StringUtil.isNull(domainName))
		domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
	if(domainName.endsWith("/"))
		domainName = domainName.substring(0, domainName.length()-1);
	String mUrl = request.getParameter("mUrl");
	if(type==-1){
		mUrl = request.getParameter("pUrl");
	}
	if(StringUtil.isNotNull(mUrl)){
		if(!mUrl.startsWith("http")||!mUrl.startsWith("https")){
			mUrl = StringUtil.formatUrl(mUrl, domainName);
			if(type!=-1){
				if(mUrl.indexOf("?")==-1){
					mUrl = mUrl + "?oauth=" + WxmutilConstant.OAUTH_EKP_FLAG;
				}else{
					mUrl = mUrl + "&oauth=" + WxmutilConstant.OAUTH_EKP_FLAG;
				}
			}
		}
	}
	if(StringUtil.isNull(mUrl)){
		mUrl = domainName+"/third/pda/indexdefault.jsp&oauth=" + WxmutilConstant.OAUTH_EKP_FLAG;
		if(type==-1){
			mUrl = domainName;
		}
	}
	String wxkey = request.getParameter("wxkey");
	String pUrl = request.getParameter("pUrl");
	request.setAttribute("wxkey",wxkey);
	request.setAttribute("pUrl", URLEncoder.encode(pUrl, "UTF-8"));
	//关闭窗口
	String close = request.getParameter("close");
	if(StringUtil.isNotNull(close)&&"1".equals(close)){
		request.setAttribute("cs", "1");
	}else{
		request.setAttribute("cs", "0");
	}
%>
</head>
<body>

<script type="text/javascript" src="//res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript" src="//rescdn.qqmail.com/node/wwopen/wwopenmng/js/3rd/zepto.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<div id="_load">
	<bean:message key="pc.sso.loading" bundle="third-weixin-mutil" />
</div>
</body>
<script>
	$(function(){
		if(isWeiXin()){
			var url = '<c:url value="/third/wxwork/mutil/jsapi/wxJsapi.do?method=pcJsapiSignature&pUrl=${pUrl}&url=" />'+encodeURIComponent(window.location.href.split('#')[0])+'&wxkey=${wxkey}';
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
			window.location.href="<%=mUrl%>";
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
		            var cs = '${cs}';
		            if(cs=='1'){
		            	wx.closeWindow();
		            	window.close();
		            }
		        }else{
		        	document.getElementById("_load").innerHTML='<bean:message key="pc.sso.loading.error" bundle="third-weixin-mutil" />';
		        }
		    });
		}); 
	}
</script>
</html>

