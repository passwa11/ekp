<%@ page import="com.landray.kmss.third.weixin.mutil.model.WeixinMutilConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/profile/profile.tld" prefix="profile"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
			seajs.use(['theme!profile']);
		</script>
	</template:replace>
	<template:replace name="body">
		<profile:listview>
			<ui:source type="Static">
				[
				<kmss:authShow roles="ROLE_THIRDWXWORK_ADMIN">
				{
					"key" : "config",
					"pinYin" : "JCZJPZ",
					"order":"0",
					"icon" : "weixin_config",
					"messageKey" : "<bean:message key="third.weixin.work.profile.config.messageKey" bundle="third-weixin-mutil"/>",
					"description" : "<bean:message key="third.weixin.work.profile.config.description" bundle="third-weixin-mutil"/>",
					"url" : "/third/weixin/mutil/third_wx_work_config/index.jsp"
				}
				<%
					boolean wxEnabled= WeixinMutilConfig.isWxWorkEnabled();
					if(wxEnabled){
				%>
				,{
					"key" : "omswxinit",
					"pinYin" : "OMSWXINIT",
					"order":"1",
					"icon" : "weixin_config",
					"messageKey" : "<bean:message key="third.weixin.work.profile.oms.messageKey" bundle="third-weixin-mutil"/>",
					"description" : "<bean:message key="third.weixin.work.profile.oms.description" bundle="third-weixin-mutil"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/weixin/mutil/tree_omsinit.jsp"
				},{
					"key" : "wxWorkApp",
					"pinYin" : "WXA",
					"order":"3",
					"icon" : "weixin_work",
					"messageKey" : "<bean:message key="module.third.weixin.work" bundle="third-weixin-mutil"/>",
					"description" : "<bean:message key="third.wx.app.title" bundle="third-weixin-mutil"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/weixin/mutil/tree_workapp.jsp"
				},{
                	"key" : "wxAdminConfig",
                    "pinYin" : "WXHT",
                    "order":"4",
                    "icon" : "weixin_admin",
                    "messageKey" : "<bean:message key="third.weixin.work.profile.wxAdminConfig.messageKey" bundle="third-weixin-mutil"/>",
                    "description" : "<bean:message key="third.weixin.work.profile.wxAdminConfig.description" bundle="third-weixin-mutil"/>",
                    "url" : "https://work.weixin.qq.com/",
                    "target" : "_blank"
                }
				<% } %>
				]
				</kmss:authShow>				
			</ui:source>		
			<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
		</profile:listview>
	</template:replace>
</template:include>
