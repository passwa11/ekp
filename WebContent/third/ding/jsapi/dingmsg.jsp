<%@page import="net.sf.antcontrib.logic.ForEach"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.*,
	java.net.URLDecoder,
	com.landray.kmss.util.*,
	com.landray.kmss.third.ding.action.DingJsapiAction,
	com.landray.kmss.third.ding.constant.*,
	com.landray.kmss.third.ding.util.DingUtil,
	com.landray.kmss.third.ding.oms.*
	" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
<title><bean:message bundle="third-ding" key="third.ding.ding.loading" /></title>
</head>
<%
DingApiService dingApiService = new DingApiServiceImpl();
String fdId = request.getParameter("fdModelId");
String fdModelName = request.getParameter("fdModelName");
String fdAttendPersonIds = request.getParameter("fdAttendPersonIds");
String fdParticipantPersonIds = request.getParameter("fdParticipantPersonIds");
String dingIds = null;
if(StringUtil.isNotNull(fdAttendPersonIds)){
	if(StringUtil.isNotNull(fdParticipantPersonIds)){
		dingIds = DingJsapiAction.getUserInfos(fdAttendPersonIds+";"+fdParticipantPersonIds);
	}else{
		dingIds = DingJsapiAction.getUserInfos(fdAttendPersonIds);
	}
}
Map<String,String> dingInfos = DingJsapiAction.getDingInfos(fdId,fdModelName);
String[] args = new String[]{
	StringUtil.isNull(dingInfos.get("loginUserName"))?"":dingInfos.get("loginUserName"),
	StringUtil.isNull(dingInfos.get("docSubject"))?"":dingInfos.get("docSubject")};
//消息
String docText = ResourceUtil.getString(request,"third.ding.dingmsg","third-ding",args);

//消息URL
String redirctUrl = dingInfos.get("docUrl");
if (StringUtil.isNotNull(DingConfig.newInstance().getDingDomain())) {
	redirctUrl = DingConfig.newInstance().getDingDomain() + redirctUrl;
} else {
	redirctUrl = StringUtil.formatUrl(redirctUrl);
}

String docInfo = dingInfos.get("templateName");
String userIds = null;
if(StringUtil.isNotNull(dingIds)){
	userIds = dingIds;
}else{
	userIds = dingInfos.get("currHandler");
}
%>
<body>

<script type="text/javascript">
var _ctx = "<%= request.getContextPath() %>";
var _config = <%= dingApiService.getConfig(request.getRequestURL().toString(),request.getQueryString()) %>;

function dingBtn(){
	dd.biz.ding.post({
		users : [<%=userIds%>],//用户列表，工号
		corpId: '<%=DingUtil.getCorpId()%>', //企业id
		type: 2, //钉类型 1：image  2：link
		alertType: 2, // 钉发送方式 0:电话, 1:短信, 2:应用内
		//alertDate: {"format":"yyyy-MM-dd HH:mm","value":"2019-11-19 18:00"},
		attachment: {
			title: '<%=dingInfos.get("docSubject")%>',
			url: '<%=redirctUrl%>',
			image: 'http://gtms02.alicdn.com/tps/i2/TB1SlYwGFXXXXXrXVXX9vKJ2XXX-2880-1560.jpg',
			text: '<%=docInfo%>'
		},
		text: '<%=docText%>', //消息
		bizType:0, // 业务类型 0：通知DING；1：任务；2：会议；
		onSuccess : function(info) {
            logger.i('biz.ding info: ' + JSON.stringify(info));
		},
		onFail : function(err) {
            logger.e('biz.ding fail: ' + JSON.stringify(err));
		}
	})

}
</script>
<script type="text/javascript" src="js/zepto.min.js"></script>
<% 
	String dingCorpSecret = DingConfig.newInstance().getDingCorpSecret();
	if(StringUtil.isNotNull(dingCorpSecret)){
%>
<script type="text/javascript" src="//g.alicdn.com/ilw/ding/0.3.9/scripts/dingtalk.js">
</script>
<%
	}else{
%>
<script type="text/javascript" src="//g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js">
</script>
<%
	}
%>
<script type="text/javascript" src="js/logger.js"></script>
<script type="text/javascript" src="js/apps.js">
</script>

</body>

</html>
