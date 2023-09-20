<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="com.landray.kmss.third.ding.util.DingUtil"%>
<%@ page import="com.landray.kmss.util.*,com.landray.kmss.sys.mobile.util.*" %>
<%
	//获取需要跳转的url
	String url = request.getParameter("url");
	url=StringUtil.formatUrl(url);
	String _url=url;
	if(MobileUtil.DING_ANDRIOD == MobileUtil.getClientType(request) || MobileUtil.DING_IPHONE == MobileUtil.getClientType(request)){
		response.sendRedirect(url);
		return;
	}
	// 判断是否是跳出外部浏览器
	String dingTodoPcOpenType = DingConfig.newInstance().getDingTodoPcOpenType();
	System.out.println("dingTodoPcOpenType:"+dingTodoPcOpenType);
	if ("out".equals(dingTodoPcOpenType)) { //外部打开
		String dingAppKey = "";
		if (url.contains("dingAppKey")) {
			dingAppKey = "&dingAppKey=" + StringUtil.getParameter(url.replace("?", "&"), "dingAppKey");
		}
		url = DingUtil.getDingDomin() + "/third/ding/pc/url_out.jsp?pg="+ SecureUtil.BASE64Encoder(url) + dingAppKey;
		response.sendRedirect(url);
		return;
	}

	url=URLEncoder.encode(StringUtil.formatUrl(url),"UTF-8");
	String corpId=null;
	String addAppKey = ResourceUtil.getKmssConfigString("kmss.ding.addAppKey");
	if(StringUtil.isNotNull(addAppKey)&&"true".equals(addAppKey)){
		//试用环境
		//out.println("【来自试用环境】");
		corpId = StringUtil.getParameter(_url, "dingAppKey");
	}else{
		corpId = DingConfig.newInstance().getDingCorpid();
	}

	String appId= request.getParameter("appId");
	if(StringUtil.isNull(appId)){
		if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.ding.proxy"))
				&&"true".equalsIgnoreCase(DingConfig.newInstance().getAttendanceEnabled())){
			appId="25478";
		}else{
			appId=DingConfig.newInstance().getDingAgentid();
		}
	}
	//out.println("appId："+appId);
	//第三方应用和企业自建模式的app_id参数不同
	if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("kmss.ding.proxy"))){
		url="dingtalk://dingtalkclient/action/openapp?corpid="+corpId+"&container_type=work_platform&app_id="+appId+"&redirect_type=jump&redirect_url="+url;
	}else{
		url="dingtalk://dingtalkclient/action/openapp?corpid="+corpId+"&container_type=work_platform&app_id=0_"+appId+"&redirect_type=jump&redirect_url="+url;
	}

	if(StringUtil.isNull(corpId)){
		//out.println("corpId为空");
		url ="dingtalk://dingtalkclient/page/link?url="+URLEncoder.encode(StringUtil.formatUrl(_url),"UTF-8")+"&web_wnd=general&width=1200&height=905";
	}else{
		out.println("corpId:"+corpId);
	}
	response.sendRedirect(url);
	return;
%>