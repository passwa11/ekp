<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
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
				<%--
				{
					"key" : "guide",
					"pinYin" : "BSYD",
					"icon" : "lui_icon_l_profile_dingding_guide",
					"order" : "0",
					"messageKey" : "部署引导",
					"description" : "引导部署钉钉集成组件",
					"url" : "/sys/profile/building.jsp"
				},
				 --%>
				{
					"key" : "omsdinginit",
					"pinYin" : "OMSDINGINIT",
					"order":"1",
					"icon" : "dingding_config",
					"messageKey" : "<bean:message key="third.ding.profile.oms.messageKey" bundle="third-ding"/>",
					"description" : "<bean:message key="third.ding.profile.oms.description" bundle="third-ding"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/ding/tree_omsinit.jsp"
				},{
					"key" : "config",
					"pinYin" : "JCZJPZ",
					"order" : "0",
					"icon" : "dingding_config",
					"messageKey" : "<bean:message key="third.ding.profile.config.messageKey" bundle="third-ding"/>",
					"description" : "<bean:message key="third.ding.profile.config.description" bundle="third-ding"/>",
					"url" : "/sys/appconfig/sys_appconfig/sysAppConfig.do?method=edit&modelName=com.landray.kmss.third.ding.model.DingConfig"
				},{
					"key" : "dnotify",
					"pinYin" : "DNOTIFY",
					"order" : "3",
					"icon" : "dingding_config",
					"messageKey" : "<bean:message key="module.third.ding.notify.profile" bundle="third-ding"/>",
					"description" : "<bean:message key="module.third.ding.notify.profile.description" bundle="third-ding"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/ding/tree_notify.jsp"
				},
				<%
					if(DingConfig.newInstance().getDingFlowEnabled().equals("true") && DingConfig.newInstance().getDingEnabled().equals("true")){
				%>
					<%-- {
						"key" : "template",
						"pinYin" : "template",
						"icon" : "dingding_template",
						"order" : "2",
						"messageKey" : "<bean:message key="table.thirdDingOrmTemp" bundle="third-ding"/>",
						"description" : "<bean:message key="table.thirdDingOrmTemp.desc" bundle="third-ding"/>",
						"url" : "/third/ding/third_ding_orm_temp/index.jsp"
					},{
						"key" : "event",
						"pinYin" : "event",
						"icon" : "dingding_event",
						"order" : "3",
						"messageKey" : "<bean:message key="table.thirdDingEvent" bundle="third-ding"/>",
						"description" : "<bean:message key="table.thirdDingEvent.desc" bundle="third-ding"/>",
						"url" : "/third/ding/third_ding_event/index.jsp"
					}, --%>
				<%} %>
				<%-- {
					"key" : "error",
					"pinYin" : "ERR",
					"icon" : "kk_notify",
					"order" : "4",
					"messageKey" : "<bean:message key="table.thirdDingError" bundle="third-ding"/>",
					"description" : "<bean:message key="table.thirdDingError.desc" bundle="third-ding"/>",
					"url" : "/third/ding/third_ding_error/index.jsp"
				}, --%>{
					"pinYin" : "PZSC",
					"icon" : "dingding_doc",
					"order" : "6",
					"messageKey" : "<bean:message key="third.ding.profile.pzsc.messageKey" bundle="third-ding"/>",
					"description" : "<bean:message key="third.ding.profile.pzsc.description" bundle="third-ding"/>",
					"url" : "/third/ding/doc/ding-doc.docx"
				},{
					"pinYin" : "ZDDHTGL",
					"icon" : "dingding_admin",
					"order" : "7",
					"messageKey" : "<bean:message key="third.ding.profile.zddhtgl.messageKey" bundle="third-ding"/>",
					"description" : "<bean:message key="third.ding.profile.zddhtgl.description" bundle="third-ding"/>",
					"url" : "https://oa.dingtalk.com/#/login",
					"target" : "_blank"
				},{
					"key" : "bussdata",
					"pinYin" : "BUSSDATA",
					"icon" : "dingding_event",
					"order" : "5",
					"messageKey" : "<bean:message key="third.ding.buss.data" bundle="third-ding"/>",
					"description" : "<bean:message key="third.ding.buss.data.desc" bundle="third-ding"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/ding/tree_bussdata.jsp"
				},{
					"key" : "dingApplication",
					"pinYin" : "dingdingyingyong",
					"order":"4",
					"icon" : "dingding_event",
					"messageKey" : "<bean:message key="thirdDingWork.app" bundle="third-ding"/>",
					"description" : "<bean:message key="thirdDingWork.app.tip" bundle="third-ding"/>",
					"url" : "/third/ding/third_ding_work/thirdDingWork.do?method=list"
				}

				<kmss:ifModuleExist path="/third/dingrobot">
				,{
					"key" : "dingrobot",
					"pinYin" : "dingdingjiqiren",
					"order":"8",
					"icon" : "lui_icon_s_profile_navLeft_dingding",
					"messageKey" : "<bean:message key="module.third.dingrobot" bundle="third-ding"/>",
					"description" : "<bean:message key="module.third.dingrobot.description" bundle="third-ding"/>",
					"url" : "/sys/profile/moduleindex.jsp?nav=/third/dingrobot/tree.jsp",
					"homePageUrl":"/third/dingrobot/index.jsp"
				}
				</kmss:ifModuleExist>
				,{
					"key" : "dingScenegroup",
					"pinYin" : "dingScenegroup",
					"order":"9",
					"icon" : "dingding_event",
					"messageKey" : "<bean:message key="dingScenegroup" bundle="third-ding"/>",
					"description" : "<bean:message key="dingScenegroup.tip" bundle="third-ding"/>",
					"url" : "/moduleindex_notopic.jsp?nav=/third/ding/tree_scenegroup.jsp"
				}
				,{
				"key" : "dingInteractiveCard",
				"pinYin" : "hudongkapian",
				"order":"9",
				"icon" : "dingding_event",
				"messageKey" : "<bean:message key="table.thirdDingCardConfig" bundle="third-ding"/>",
				"description" : "<bean:message key="table.thirdDingCardConfig.tip" bundle="third-ding"/>",
				"url" : "/moduleindex_notopic.jsp?nav=/third/ding/tree_card.jsp"
				}
				<kmss:ifModuleExist path="/third/ocr">
					<kmss:auth requestURL="/third/ocr/tree.jsp">
					,{
					"key" : "dingdingOcr",
					"pinYin" : "dingdingOcr",
					"order":"9",
					"icon" : "dingding_ocr",
					"messageKey" : "<bean:message key="dingdingOcr" bundle="third-ding"/>",
					"description" : "<bean:message key="dingdingOcr.description" bundle="third-ding"/>",
					"url" : "/sys/profile/moduleindex.jsp?nav=/third/ocr/tree.jsp"
				}
				   </kmss:auth>
				</kmss:ifModuleExist>
				]
			</ui:source>		
			<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
		</profile:listview>
	<template:block name="body"></template:block>
</body>
</html>

