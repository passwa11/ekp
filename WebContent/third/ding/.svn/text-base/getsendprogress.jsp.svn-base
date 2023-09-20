<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
Long agent_id = Long
.parseLong(DingConfig.newInstance().getDingAgentid());
com.landray.kmss.third.ding.oms.DingApiService dingService = com.landray.kmss.third.ding.util.DingUtils.getDingApiService();

Long task_id = Long.parseLong(request.getParameter("task_id"));


com.dingtalk.api.DingTalkClient client = new com.dingtalk.api.DefaultDingTalkClient(
		 com.landray.kmss.third.ding.constant.DingConstant.DING_PREFIX
				+ "/topapi/message/corpconversation/getsendprogress");
com.dingtalk.api.request.OapiMessageCorpconversationGetsendprogressRequest request1 = new com.dingtalk.api.request.OapiMessageCorpconversationGetsendprogressRequest();
request1.setAgentId(agent_id);
request1.setTaskId(task_id);
com.dingtalk.api.response.OapiMessageCorpconversationGetsendprogressResponse response2 = client
		.execute(request1, dingService.getAccessToken(String.valueOf(agent_id)));
out.println(response2.getBody());

%>