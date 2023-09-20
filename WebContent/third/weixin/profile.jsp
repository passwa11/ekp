<%@ page import="com.landray.kmss.third.weixin.model.WeixinConfig"%>
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
				<kmss:authShow roles="ROLE_THIRDWEIXIN_ADMIN">				
				{
					"key" : "config",
					"pinYin" : "JCZJPZ",
					"order":"0",
					"icon" : "weixin_config",
					"messageKey" : "<bean:message key="third.wx.profile.config.messageKey" bundle="third-weixin"/>",
					"description" : "<bean:message key="third.wx.profile.config.description" bundle="third-weixin"/>",
					"url" : "/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.weixin.model.WeixinConfig"
				}
				<%
					String wxEnabled= WeixinConfig.newInstance().getWxEnabled();
					if("true".equals(wxEnabled)){
				%>
				,{
					"key" : "omswxinit",
					"pinYin" : "OMSWXINIT",
					"order":"1",
					"icon" : "weixin_config",
					"messageKey" : "<bean:message key="third.wx.profile.oms.messageKey" bundle="third-weixin"/>",
					"description" : "<bean:message key="third.wx.profile.oms.description" bundle="third-weixin"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/weixin/tree_omsinit.jsp"
				},{
					"key" : "wxMenu",
					"pinYin" : "WXCDPZ",
					"order":"3",
					"icon" : "weixin_menu",
					"messageKey" : "<bean:message key="third.wx.menu.title" bundle="third-weixin"/>",
					"description" : "<bean:message key="third.wx.menu.title" bundle="third-weixin"/>",
					"url" : "/third/weixin/menu/index.jsp"
				},{
					"key" : "weixinAdminConfig",
                    "pinYin" : "WXHT",
                    "order":"4",
                    "icon" : "weixin_admin",
                    "messageKey" : "<bean:message key="third.wx.profile.wxht.messageKey" bundle="third-weixin"/>",
                    "description" : "<bean:message key="third.wx.profile.wxht.description" bundle="third-weixin"/>",
                    "url" : "https://qy.weixin.qq.com/",
                    "target" : "_blank"
                }
				<%} %>
				,{
					"key" : "wxdoc",
					"pinYin" : "PZSC",
					"order":"5",
					"icon" : "weixin_doc",
					"messageKey" : "<bean:message key="third.wx.profile.wxdoc.messageKey" bundle="third-weixin"/>",
					"description" : "<bean:message key="third.wx.profile.wxdoc.description" bundle="third-weixin"/>",
					"url" : "/third/weixin/doc/weixin-doc.docx"
				}]
				</kmss:authShow>				
			</ui:source>		
			<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
		</profile:listview>
	<template:block name="body"></template:block>
</body>
</html>
