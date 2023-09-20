<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.third.ding.service.IThirdDingSendDingService" %>
<%@ page import="com.landray.kmss.third.ding.model.ThirdDingSendDing" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.util.SecureUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%
	String appId= request.getParameter("appId");
	if(StringUtil.isNull(appId)){
		appId=DingConfig.newInstance().getDingAgentid();
	}
	String pg= request.getParameter("pg");
	String id= request.getParameter("id");
	String sendDing= request.getParameter("sendDing");
	if(StringUtil.isNull(pg)){
		if("true".equals(sendDing)){

			IThirdDingSendDingService thirdDingSendDingService=(IThirdDingSendDingService) SpringBeanUtil
					.getBean("thirdDingSendDingService");
			ThirdDingSendDing thirdDingSendDing = (ThirdDingSendDing)thirdDingSendDingService.findByPrimaryKey(id);
			pg=thirdDingSendDing.getFdLink();
		}
	}else{
		pg = SecureUtil.BASE64Decoder(pg);
	}
	String isDingMb = "false";
	int type = MobileUtil.getClientType(request);
	//System.out.println("type:"+type);
	if(type == 11){
		//移动客户端
		isDingMb = "true";
		//request.getRequestDispatcher("/km/forum/km_forum/kmForumPost.do?method=view&fdTopicId=1723a6914e37968983579e04f95acb9e").forward(request, response);
		//如果是移动端全文检索页面需要替换下地址
		String pcSearch = "/sys/ftsearch/searchBuilder.do?method=search";
		String queryString = "queryString";
		//说明是调用全文检索接口
		String redirctUrl = pg;
		if(pg.contains(pcSearch)){
			pg = pg.replace(pcSearch, "/sys/ftsearch/mobile/index.jsp");
			pg = pg.replace(queryString, "keyword");
			if(pg.indexOf("?") == -1){
				if(pg.indexOf("&")!= -1){
					pg = pg.replaceFirst("&", "?");
				}
			}
		}
		response.sendRedirect(pg);
		return;
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

var isDingMobiel = "<%=isDingMb%>";
if(isDingMobiel == "true"){
	window.location.href = "<%=pg %>";
}else{
	authCode();
}

function authCode(){
	 DingTalkPC.runtime.permission.requestAuthCode({
        corpId: "${corpid}",
        onSuccess: function (info) {
            logger.i('authcode: ' + info.code);
			_scode=info.code;
			dingOpen(_scode);
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
            logger.e('fail: ' + JSON.stringify(err));
            document.getElementById("_load").innerHTML = "ready err:"+JSON.stringify(err);  
        }

    });
}

function dingOpen(_scode){
	document.getElementById("_load").innerHTML='<bean:message key="third.ding.pc.view" bundle="third-ding"/>';
	close();
	DingTalkPC.biz.util.openLink({
		url: "<%=pg %>&formDing=1&code="+_scode,
		onSuccess : function(info) {
            logger.i('biz.ding info: ' + $.parseJSON(info));
		},
		onFail : function(err) {
			document.getElementById("_load").innerHTML = $.parseJSON(err);  
            logger.e('biz.ding fail: ' + $.parseJSON(err));
		}
	})
	//timeInteval(); //会有打开页面后会弹回钉钉窗口的问题

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