<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<title><bean:message key="lbpmFollow.button.follow" bundle="sys-lbpmservice-support" /></title>
<script>
Com_IncludeFile("data.js");
Com_IncludeFile("jquery.js");
Com_IncludeFile("dialog.js");
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
var isFollow = '${param.isFollow}';
var isOldFollow = '${param.oldFollow}';
//是否要取消跟踪
var cancelFollowText = '<bean:message key="lbpmFollow.button.cancelFollow.confirm" bundle="sys-lbpmservice-support" />';
//是否确认跟踪
var followText = '<bean:message key="lbpmFollow.button.follow.confirm" bundle="sys-lbpmservice-support" />';
</script>
<script src="<c:url value="/sys/lbpmservice/support/lbpm_follow/lbpmFollowConfirm_script.js"/>"></script>
<style>
.lbpmFollowWrap{
	height:100px;
	font-size:16px;
	display:table-cell;
	vertical-align:middle;
}
</style>
<link rel="stylesheet" type="text/css" href="<c:url value="/sys/lbpmservice/resource/review.css?s_cache=${LUI_Cache}"/>" />
</head>
<body>
<center>
<div id="lbpmFollowWrap">
	
</div>
<div style="margin-top: 10px;text-align: center;">
	<input type="button" class="btnopt" style="width:70px;" onclick="Follow();" value="<bean:message key='button.ok'/>"></input>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" class="btnopt" style="width:70px;" onclick="CancelFollow()" value="<bean:message  key='button.cancel'/>">
</div>

</center>
</body>
</html>