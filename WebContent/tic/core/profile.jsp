<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@ page import="com.landray.kmss.tic.core.mapping.plugins.TicCoreMappingIntegrationPlugins"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/profile/profile.tld" prefix="profile"%>

<%
	// 组件解耦分离
	List<Map<String, String>> listMap = TicCoreMappingIntegrationPlugins.getBusinesses();

	for (Map<String, String> map : listMap) {
		if (map.get("businessKey").equals("SAP")) {
			request.setAttribute("sapFlag", "1");
		} else if (map.get("businessKey").equals("K3")) {
			request.setAttribute("k3Flag", "1");
		} else if (map.get("businessKey").equals("EAS")) {
			request.setAttribute("easFlag", "1");
		}else if (map.get("businessKey").equals("U8")) {
			request.setAttribute("u8Flag", "1");
		}else if (map.get("businessKey").equals("BUSINESS")) {
			request.setAttribute("businessFlag", "1");
		}else if (map.get("businessKey").equals("NC")) {
			request.setAttribute("ncFlag", "1");
		}else if(map.get("businessKey").equals("BUSINESS")){
			request.setAttribute("businessFlag", "1");
		}

	}

%>
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

		{
		"pinYin" : "1TICgailan",
		"icon" : "tic_core",
		"messageKey" : "TIC概览",
		"description" : "TIC全名为Third Integration Center，即第三方集成中心，TIC概览主要介绍TIC的整体框架与架构",
		"url" : "/tic/core/overview/index.html",
		"target" : ""
		}
		<c:if test="${businessFlag=='1' }">
			,
			{
			"key": "common",
			"pinYin" : "2TICyewujicheng",
			"icon" : "tic_core",
			"messageKey" : "${lfn:message('tic-core-common:ticCoreCommon.businessIntegrate')}",
			"description" : "${lfn:message('tic-core-common:ticCoreCommon.businessIntegrate.description')}",
			"url" : "/sys/profile/moduleindex.jsp?nav=/tic/business/tree.jsp",
			"target" : ""
			}
		</c:if>
		<c:if test="${sapFlag=='1' }">
			,{
			"key": "sap",
			"pinYin" : "3TICsapjicheng",
			"icon" : "tic_core",
			"messageKey" : "${lfn:message('tic-core-common:ticCoreCommon.SAPIntegrate')}",
			"description" : "${lfn:message('tic-core-common:ticCoreCommon.SAPIntegrate.description')}",
			"url" : "/sys/profile/moduleindex.jsp?nav=/tic/sap/tree.jsp",
			"target" : ""
			}
		</c:if>
		<c:if test="${k3Flag=='1' }">
			,{
			"key": "k3",
			"pinYin" : "4TICk3jicheng",
			"icon" : "tic_core",
			"messageKey" : "${lfn:message('tic-core-common:ticCoreCommon.K3Integrate')}",
			"description" : "${lfn:message('tic-core-common:ticCoreCommon.K3Integrate.description')}",
			"url" : "/sys/profile/moduleindex.jsp?nav=/tic/k3/tree.jsp",
			"target" : ""
			}
		</c:if>
		<c:if test="${easFlag=='1' }">
			,{
			"key": "eas",
			"pinYin" : "5TICeasjicheng",
			"icon" : "tic_core",
			"messageKey" : "${lfn:message('tic-core-common:ticCoreCommon.EASIntegrate')}",
			"description" : "${lfn:message('tic-core-common:ticCoreCommon.EASIntegrate.description')}",
			"url" : "/sys/profile/moduleindex.jsp?nav=/tic/eas/tree.jsp",
			"target" : ""
			}
		</c:if>

		<c:if test="${u8Flag=='1' }">
			,{
			"key": "u8",
			"pinYin" : "6TICu8jicheng",
			"icon" : "tic_core",
			"messageKey" : "${lfn:message('tic-core-common:ticCoreCommon.U8Integrate')}",
			"description" : "${lfn:message('tic-core-common:ticCoreCommon.U8Integrate.description')}",
			"url" : "/sys/profile/moduleindex.jsp?nav=/tic/u8/tree.jsp",
			"target" : ""
			}
		</c:if>

		<c:if test="${ncFlag=='1' }">
			,{
			"key": "nc",
			"pinYin" : "7TICncjicheng",
			"icon" : "tic_core",
			"messageKey" : "${lfn:message('tic-core-common:ticCoreCommon.NCIntegrate')}",
			"description" : "${lfn:message('tic-core-common:ticCoreCommon.NCIntegrate.description')}",
			"url" : "/sys/profile/moduleindex.jsp?nav=/tic/nc/tree.jsp",
			"target" : ""
			}
		</c:if>

		]
	</ui:source>
	<ui:render type="Javascript" ref="sys.profile.listview.default"></ui:render>
</profile:listview>
<template:block name="body"></template:block>
</body>
</html>

