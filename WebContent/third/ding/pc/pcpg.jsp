<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="com.landray.kmss.util.*" %>
<%@ page import="com.landray.kmss.common.actions.RequestContext" %>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil" %>
<%
	String appId= request.getParameter("appId");
	if(StringUtil.isNull(appId)){
		appId=DingConfig.newInstance().getDingAgentid();
	}
	String pg= request.getParameter("pg");
	if(StringUtil.isNull(pg))
		pg="/";
	String toUrl= request.getParameter("toUrl");
	String viewUrl="/third/ding/sso/pc_redirect.jsp?pg="+pg+"";
	String mobilePg = "";
	if(StringUtil.isNotNull(toUrl)&&"true".equals(toUrl)){
		viewUrl="/third/ding/sso/pc_redirect.jsp?url="+pg+"";
		mobilePg = SecureUtil.BASE64Decoder(pg);
	}
	if (StringUtil.isNotNull(DingConfig.newInstance().getDingDomain())) {
		viewUrl = DingConfig.newInstance().getDingDomain() + viewUrl;
	} else {
		viewUrl = StringUtil.formatUrl(viewUrl);
	}
	String corpid=null;
	String addAppKey = ResourceUtil.getKmssConfigString("kmss.ding.addAppKey");
	if(StringUtil.isNotNull(addAppKey)&&"true".equals(addAppKey)){
		corpid = request.getParameter("dingAppKey");
		if(StringUtil.isNull(corpid)){
			System.out.println("----------F4开启了dingAppKey开关，但是访问url不携带dingAppKey参数----------");
		}
	}else{
		corpid = DingConfig.newInstance().getDingCorpid();
	}

	request.setAttribute("corpid", corpid);
	String lang = request.getHeader("Accept-Language");
	if (StringUtil.isNotNull(lang)&& lang.indexOf(",") > -1) {
		lang = lang.trim();
		lang = lang.substring(0, lang.indexOf(","));
	}
	int type = MobileUtil.getClientType(new RequestContext(request));
	System.out.println("getClientType:"+type);
	if(type != MobileUtil.DING_PC){
		//非钉钉PC客户端打开，统一跳转
		String mobileUrl = pg;
		if(StringUtil.isNotNull(mobilePg)){
			mobileUrl =request.getContextPath()+mobilePg;
		}
		System.out.println("mobileUrl:"+mobileUrl);
		response.sendRedirect(mobileUrl);
		return;
	}
%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
<title><bean:message bundle="third-ding" key="third.ding.ding.loading" /></title>
</head>

<body>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<script type="text/javascript" src="//g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js"></script>
<script type="text/javascript" src="js/zepto.min.js"></script>
<script type="text/javascript" src="js/logger.js"></script>
<script type="text/javascript" src="js/apps.js"></script>

<script type="text/javascript">
	var _ctx = "<%= request.getContextPath() %>";
	var _appId="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml(appId)%>";
	var _scode="";

	$(function(){
		 authCode();
	});

	function authCode(){
		 DingTalkPC.runtime.permission.requestAuthCode({
			corpId: "${corpid}",
			onSuccess: function (info) {
				logger.i('authcode: ' + info.code);
				_scode=info.code;
				dingOpen();
			},
			onFail: function (err) {
				logger.e('fail: ' + JSON.stringify(err));
				document.getElementById("_load").innerHTML = "ready err:"+JSON.stringify(err);
			}
		});
	}

	function dingOpen(){
		close();
		document.getElementById("_load").innerHTML='<bean:message key="third.ding.pc.view" bundle="third-ding"/>';
		DingTalkPC.biz.util.openLink({
			url: "<%=viewUrl%>&formDing=1&code="+_scode+"&j_lang=<%=lang%>&dingAppKey=<%=corpid%>",
			onSuccess : function(info) {
				logger.i('biz.ding info: ' + $.parseJSON(info));
			},
			onFail : function(err) {
				document.getElementById("_load").innerHTML = $.parseJSON(err);
				logger.e('biz.ding fail: ' + $.parseJSON(err));
			}
		})
		//timeInteval();
	}

	//倒计时关闭，暂时不用了
	var time = 0;
	function timeInteval(){
		var _timeArea = document.getElementById("_timeArea");
		if(time==3){
			close();
			return;
		}
		_timeArea.innerHTML = 3 - time;
		time ++;
		setTimeout("timeInteval()",1000);
	}

	//关闭页面
	function close(){
		Com_Parameter.CloseInfo = null;
		Com_CloseWindow();
		dd.biz.navigation.close();
	}

	//手机端页面
	function mobileRedirect(){
		var toUrl = "<%= request.getContextPath()%><%=mobilePg%>";
		window.location.replace(toUrl)
		return;
	}
</script>

<div id="_load">
	<bean:message key="third.ding.pc.view.loading" bundle="third-ding"/>
</div>
<%--<div>
    <span>
      <bean:message key="third.ding.pc.view.close.1" bundle="third-ding"/>
	</sapn>
	<span id="_timeArea" style="color:red">
    3
	</span>
	<span>
	  <bean:message key="third.ding.pc.view.close.2" bundle="third-ding"/>
	</sapn>
</div>--%>

</body>

</html>