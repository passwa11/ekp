<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%
	String appId= request.getParameter("appId");
	if(StringUtil.isNull(appId)){
		appId=DingConfig.newInstance().getDingAgentid();
	}
	String temp = "";
	if (StringUtil.isNotNull(DingConfig.newInstance().getDingDomain())) {
		temp=DingConfig.newInstance().getDingDomain();
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

var _url = window.location.href;
var _pgUrl = _url.substr(_url.indexOf('?'));
_pgUrl = _pgUrl.substr(_pgUrl.indexOf('pg'));
_pgUrl = _pgUrl.substr(_pgUrl.indexOf('=')+1);
_pgUrl = window.btoa(_pgUrl);  //Base64加密
_pgUrl = "<%=temp %>/third/ding/sso/pc_redirect.jsp?url="+_pgUrl;

$(function(){
	authCode();	
});
 
function authCode(){
	 DingTalkPC.runtime.permission.requestAuthCode({
		corpId: "${corpid}",
        onSuccess: function (info) {
        	
            logger.i('authcode: ' + info.code);
			_scode=info.code;
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
        },
        onFail: function (err) {
            logger.e('fail: ' + JSON.stringify(err));
            document.getElementById("_load").innerHTML = "ready err:"+JSON.stringify(err);  
        }

    });
}

function dingOpen(){
	document.getElementById("_load").innerHTML='<bean:message key="third.ding.pc.view" bundle="third-ding"/>';
	DingTalkPC.biz.util.openLink({
		url: _pgUrl+"&formDing=1&scode="+_scode,
		onSuccess : function(info) {
            logger.i('biz.ding info: ' + $.parseJSON(info));
		},
		onFail : function(err) {
			document.getElementById("_load").innerHTML = $.parseJSON(err);  
            logger.e('biz.ding fail: ' + $.parseJSON(err));
		}
	})

}
</script>

<div id="_load"> 
	<bean:message key="third.ding.pc.view.loading" bundle="third-ding"/>
</div>
</body>

</html>

