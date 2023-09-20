<%@page import="com.landray.kmss.third.ding.util.DingUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse"%>
<%@page import="com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.PageResult"%>
<%@page import="com.dingtalk.api.response.OapiProcessWorkrecordTaskQueryResponse.WorkRecordVo"%>
<%@page import="com.dingtalk.api.DefaultDingTalkClient"%>
<%@page import="com.dingtalk.api.request.OapiProcessWorkrecordTaskQueryRequest"%>
<%@page import="com.dingtalk.api.DingTalkClient"%>
<%@page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="content">
		<center><br><p class="txttitle">钉钉待办数据查询</p><br></center>
		<%! 
		private StringBuffer str = new StringBuffer();
		private void getQueryWork(String userid,String token,long pg,long status) throws Exception {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/workrecord/task/query");
			OapiProcessWorkrecordTaskQueryRequest req = new OapiProcessWorkrecordTaskQueryRequest();
			req.setUserid(userid);
			req.setOffset(pg*20L);
			req.setCount(20L);
			req.setStatus(status);
			OapiProcessWorkrecordTaskQueryResponse response = client.execute(req, token);
			if(response.getErrcode()==0){
				PageResult result = response.getResult();
				List<WorkRecordVo> vos = result.getList();
				if (vos != null) {
					for(WorkRecordVo vo:vos){
						str.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;钉钉实例Id="+vo.getInstanceId()+"；钉钉任务Id="+vo.getTaskId()+"；标题："+vo.getTitle()+"("+vo.getUrl()+")<br>");
					}
					if(result.getHasMore()){
						getQueryWork(userid,token,pg+1,status);
					}
				}
			}else{
				str.append(response.getBody());
			}
		}
		%>
		<%
		String userid = request.getParameter("userId");
		if(StringUtil.isNull(userid)){
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;userId参数不能为空");
		}else{
			str.setLength(0);
			String token = DingUtils.getDingApiService().getAccessToken();
			out.println("&nbsp;&nbsp;&nbsp;&nbsp;待处理的待办：<br>");
			getQueryWork(userid,token,0l, 0l);
			out.println(str.toString());
			str.setLength(0);
			out.println("<br>&nbsp;&nbsp;&nbsp;&nbsp;移除的待办：<br>");
			getQueryWork(userid,token,0l, -1l);
			out.println(str.toString());
		}
		%>
	</template:replace>
</template:include>
