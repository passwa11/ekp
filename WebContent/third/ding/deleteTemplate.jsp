<%@page import="com.landray.kmss.third.ding.util.DingUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.dingtalk.api.response.OapiProcessDeleteResponse"%>
<%@page import="com.dingtalk.api.DefaultDingTalkClient"%>
<%@page import="com.dingtalk.api.request.OapiProcessDeleteRequest"%>
<%@page import="com.dingtalk.api.request.OapiProcessDeleteRequest.DeleteProcessRequest"%>
<%@page import="com.dingtalk.api.DingTalkClient"%>
<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="content">
		<center><br><p class="txttitle">删除钉钉模板</p><br></center>
		<%! 
		private StringBuffer str = new StringBuffer();
		private void getQueryWork(String process_code,String token) throws Exception {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/delete");
			OapiProcessDeleteRequest req = new OapiProcessDeleteRequest();
			DeleteProcessRequest obj1 = new DeleteProcessRequest();
			obj1.setAgentid(843956607L);
			obj1.setProcessCode(process_code);
			req.setRequest(obj1);
			OapiProcessDeleteResponse response = client.execute(req, token);
			str.append(response.getBody());
			
		}
		%>
		<%
		String process_code = request.getParameter("process_code");
		if(StringUtil.isNull(process_code)){
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;process_code参数不能为空");
		}else{
			str.setLength(0);
			String token = DingUtils.getDingApiService().getAccessToken();
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;删除待办情况：<br>process_code："+process_code+"<br>");
			getQueryWork(process_code,token);
			out.println("返回结果："+str.toString());
		}
		%>
	</template:replace>
</template:include>
