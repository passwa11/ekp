<%@page import="com.landray.kmss.third.ding.util.DingUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.dingtalk.api.DingTalkClient"%>
<%@page import="com.dingtalk.api.DefaultDingTalkClient"%>
<%@page import="com.dingtalk.api.request.OapiProcessTemplateListRequest"%>
<%@page import="com.dingtalk.api.response.OapiProcessTemplateListResponse"%>
<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="content">
		<center><br><p class="txttitle">获取token</p><br></center>		
		<%		
			String token = DingUtils.getDingApiService().getAccessToken();		
			out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;token：<br>"+token);
			
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/template/list");
			OapiProcessTemplateListRequest req = new OapiProcessTemplateListRequest();
			req.setUserid("084513240237889847");
			req.setOffset(0L);
			req.setSize(20L);
			OapiProcessTemplateListResponse rsp = client.execute(req, token);
			System.out.println(rsp.getBody());
		%>
	</template:replace>
</template:include>
