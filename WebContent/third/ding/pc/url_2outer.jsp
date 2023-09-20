<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.third.ding.util.DingUtil" %>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig" %>
<%
    String pg= request.getParameter("pg");
    String corpid=null;
    String addAppKey = ResourceUtil.getKmssConfigString("kmss.ding.addAppKey");
    if (StringUtil.isNotNull(addAppKey) && "true".equals(addAppKey)) {
        corpid = request.getParameter("dingAppKey");
        if (StringUtil.isNull(corpid)) {
            System.out.println("----------F4开启了dingAppKey开关，但是访问url不携带dingAppKey参数----------");
            corpid= DingUtil.getCorpId();
        }
    } else {
        corpid = DingConfig.newInstance().getDingCorpid();
    }
    request.setAttribute("corpid", corpid);
%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport"
		  content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
	<title>加载中...</title>
</head>

<body>
<script type="text/javascript" src="<%= request.getContextPath() %>/resource/js/jquery.js"></script>
<script type="text/javascript" src="//g.alicdn.com/dingding/dingtalk-pc-api/2.3.1/index.js"></script>
<script type="text/javascript" src="js/zepto.min.js"></script>
<script type="text/javascript" src="js/logger.js"></script>
<script type="text/javascript" src="js/apps.js"></script>
<script type="text/javascript">

	var _ctx = "<%= request.getContextPath() %>";
	var _scode="";

    authCode();

	function authCode(){
		DingTalkPC.runtime.permission.requestAuthCode({
			corpId: "${corpid}",
			onSuccess: function (info) {
				logger.i('authcode: ' + info.code);
				_scode=info.code;
				dingOpen(_scode);
                close();
			},
			onFail: function (err) {
				logger.e('fail: ' + JSON.stringify(err));
				document.getElementById("_load").innerHTML = "ready err:"+JSON.stringify(err);
			}

		});
	}

	function dingOpen(_scode){
		document.getElementById("_load").innerHTML='<bean:message key="third.ding.pc.view" bundle="third-ding"/>';
		DingTalkPC.biz.util.openLink({
			url: "<%=pg %>&formDing=1&code="+_scode+"&dingAppKey=<%=corpid %>",
			onSuccess : function(info) {
				logger.i('biz.ding info: ' + $.parseJSON(info));
			},
			onFail : function(err) {
				document.getElementById("_load").innerHTML = $.parseJSON(err);
				logger.e('biz.ding fail: ' + $.parseJSON(err));
			}
		})

	}
	function close(){
        Com_Parameter.CloseInfo = null;
        Com_CloseWindow();
	}
    //重写window.close方法
    window.close = function(){
        dd.biz.navigation.close();
    };
</script>

<div id="_load">
	<bean:message key="third.ding.pc.view.loading" bundle="third-ding"/>
</div>

</body>

</html>