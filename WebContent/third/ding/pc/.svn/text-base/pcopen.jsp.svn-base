<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="com.landray.kmss.third.ding.util.DingUtil" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.SecureUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%
	String fdTodoId= request.getParameter("fdTodoId");
	if(MobileUtil.DING_ANDRIOD == MobileUtil.getClientType(request)){
		String todoUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="+fdTodoId+"&oauth=ekp";
		todoUrl= StringUtil.formatUrl(todoUrl);
		response.sendRedirect(todoUrl);
		return;
	}
	String url= request.getParameter("url");
	String viewUrl="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="+fdTodoId+"&oauth=ekp";
	if(StringUtil.isNotNull(url)&&!"null".equalsIgnoreCase(url)){
		viewUrl = SecureUtil.BASE64Decoder(url);
	}
	if(!viewUrl.startsWith("http")){
		if (StringUtil.isNotNull(DingConfig.newInstance().getDingDomain())) {
			viewUrl = DingConfig.newInstance().getDingDomain() + viewUrl;
		} else {
			viewUrl = StringUtil.formatUrl(viewUrl);
		}
	}
	String appId= request.getParameter("appId");
	String corpid=null;
	String addAppKey = ResourceUtil.getKmssConfigString("kmss.ding.addAppKey");
	if(StringUtil.isNotNull(addAppKey)&&"true".equals(addAppKey)){
		corpid = request.getParameter("dingAppKey");
		if(StringUtil.isNull(corpid)){
			System.out.println("----------F4开启了dingAppKey开关，但是访问url不携带dingAppKey参数----------");
		}else{
			viewUrl=viewUrl+"&dingAppKey="+corpid;
		}
	}else{
		corpid = DingConfig.newInstance().getDingCorpid();
	}
	request.setAttribute("corpid", corpid);
	String lang = request.getHeader("Accept-Language");
	if (StringUtil.isNotNull(lang)&&lang.indexOf(",")>-1) {
		lang = lang.trim();
		lang = lang.substring(0, lang.indexOf(","));
	}

	String dingTodoPcOpenType = DingConfig.newInstance().getDingTodoPcOpenType();
	//判断最新开关，如果是内部打开，则使用内部打开方式
	if ("in".equals(dingTodoPcOpenType)) { //外部打开
		String redirectUrl = DingUtil.getDingDomin() + "/third/ding/pc/web_wnd.jsp?url="
				+ URLEncoder.encode(viewUrl,"UTF-8");
		System.out.println("-------redirectUrl------"+redirectUrl);
		response.sendRedirect(redirectUrl);
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
var _code="";

/* $(function(){
    var url = '<c:url value="/third/ding/jsapi.do?method=pcJsapiSignature&url=" />'+encodeURIComponent(window.location.href.split('#')[0]);
    $.ajax({
		   type: "POST",
		   url: url,
		   async:true,
		   dataType: "json",
		   success: function(data){
			   dingConfig(data);
		   },
		   error:function(err){
			   document.getElementById("_load").innerHTML = "load fail: "+JSON.stringify(err); 
		   }
		});
}); */

$(function(){
	authCode();
});

function authCode(){
	 DingTalkPC.runtime.permission.requestAuthCode({
        corpId: "${corpid}",
        onSuccess: function (info) {
            logger.i('authcode: ' + info.code);
			_code=info.code;
			dingOpen();
			/*
            $.ajax({
                url: _ctx+'/third/ding/jsapi.do?method=userinfo&from=dingpc&code=' + info.code,
                type: 'GET',
                success: function (data, status, xhr) {
                    var info = $.parseJSON(data);
					if (info.errcode === 0) {
                        logger.i('user id: ' + info.userid);
 						dingOpen();
						//history.back();
                   }else {
                	   document.getElementById("_load").innerHTML = "user id parse fail:"+JSON.stringify(info);  
                        logger.e('auth error: ' + data);
						//history.back();
                    }
                },
                error: function (xhr, errorType, error) {
                	document.getElementById("_load").innerHTML = "user id get fail:"+JSON.stringify(error);  
                    logger.e(errorType + ', ' + error);
                }
            });
			*/
        },
        onFail: function (err) {
            logger.e('fail: ' + $.parseJSON(err));
            document.getElementById("_load").innerHTML = "ready err:"+JSON.stringify(err);  
        }

    });
}


function dingOpen(){
	close();
	document.getElementById("_load").innerHTML='<bean:message key="third.ding.pc.view" bundle="third-ding"/>';
	DingTalkPC.biz.util.openLink({
		url: "<%=viewUrl%>&formDing=1&code="+_code+"&j_lang=<%=lang%>",
		onSuccess : function(info) {
            logger.i('biz.ding info: ' + JSON.stringify(info));
		},
		onFail : function(err) {
            logger.e('biz.ding fail: ' + JSON.stringify(err));
            document.getElementById("_load").innerHTML = JSON.stringify(err);  
		}
	});
	//timeInteval();
}

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
function close(){
	DingTalkPC.biz.navigation.quit({
	    message: "quit message",//退出信息，传递给openModal或者openSlidePanel的onSuccess函数的result参数
	    onSuccess : function(result) {
	    },
	    onFail : function() {}
	})
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
