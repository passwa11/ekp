<%@ page import="com.landray.kmss.third.weixin.work.model.WeixinWorkConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/profile/profile.tld" prefix="profile"%>

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="renderer" content="webkit" />
	<template:block name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	</template:block>
	<title>
		<template:block name="title"></template:block>
	</title>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/profile/resource/css/homepage.css" />
	<script type="text/javascript">seajs.use(['theme!profile'])</script>
</head>
<body class="lui_profile_listview_body">
	<c:set var="type" scope="page" value="${empty param.type ? 'ekp' : param.type}"/>  
	<profile:listview>
			<ui:source type="Static">
				[
				<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
				{
					"key" : "config",
					"pinYin" : "JCZJPZ",
					"order":"0",
					"icon" : "weixin_config",
					"messageKey" : "<bean:message key="third.weixin.work.profile.config.messageKey" bundle="third-weixin-work"/>",
					"description" : "<bean:message key="third.weixin.work.profile.config.description" bundle="third-weixin-work"/>",
					"url" : "/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.weixin.work.model.WeixinWorkConfig"
				}
				<%
					String wxEnabled= WeixinWorkConfig.newInstance().getWxEnabled();
					if("true".equals(wxEnabled)){
				%>
				,{
					"key" : "omswxinit",
					"pinYin" : "OMSWXINIT",
					"order":"1",
					"icon" : "weixin_config",
					"messageKey" : "<bean:message key="third.weixin.work.profile.oms.messageKey" bundle="third-weixin-work"/>",
					"description" : "<bean:message key="third.weixin.work.profile.oms.description" bundle="third-weixin-work"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/weixin/work/tree_omsinit.jsp"
				},{
					"key" : "notify",
					"pinYin" : "NOTIFY",
					"order":"2",
					"icon" : "weixin_config",
					"messageKey" : "<bean:message key="third.weixin.work.profile.notify.messageKey" bundle="third-weixin-work"/>",
					"description" : "<bean:message key="third.weixin.work.profile.notify.description" bundle="third-weixin-work"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/weixin/work/tree_notify.jsp"
				},{
					"key" : "wxWorkApp",
					"pinYin" : "WXA",
					"order":"3",
					"icon" : "weixin_work",
					"messageKey" : "<bean:message key="module.third.weixin.work" bundle="third-weixin-work"/>",
					"description" : "<bean:message key="third.wx.app.title" bundle="third-weixin-work"/>",
					"url" : "/third/weixin/work/third_weixin_work/thirdWeixinWork.do?method=list"
				},{
                	"key" : "wxAdminConfig",
                    "pinYin" : "WXHT",
                    "order":"4",
                    "icon" : "weixin_admin",
                    "messageKey" : "<bean:message key="third.weixin.work.profile.wxAdminConfig.messageKey" bundle="third-weixin-work"/>",
                    "description" : "<bean:message key="third.weixin.work.profile.wxAdminConfig.description" bundle="third-weixin-work"/>",
                    "url" : "https://work.weixin.qq.com/",
                    "target" : "_blank"
                }
				<%} %>
				,{
					"key" : "wxworkdoc",
					"pinYin" : "PZSC",
					"order":"5",
					"icon" : "weixin_doc",
					"messageKey" : "<bean:message key="third.weixin.work.profile.wxworkdoc.messageKey" bundle="third-weixin-work"/>",
					"description" : "<bean:message key="third.weixin.work.profile.wxworkdoc.description" bundle="third-weixin-work"/>",
					"url" : "/third/weixin/work/doc/wxwork-doc.docx"
				}]
				</kmss:authShow>				
			</ui:source>		
			<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
		</profile:listview>
	<template:block name="body"></template:block>
</body>
</html>
