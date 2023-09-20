<%@page import="com.landray.kmss.third.ding.util.DingUtils"%>
<%@page import="com.dingtalk.api.DefaultDingTalkClient"%>
<%@page import="com.dingtalk.api.DingTalkClient"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.dingtalk.api.request.OapiCallBackGetCallBackRequest" %>
<%@ page import="com.dingtalk.api.response.OapiCallBackGetCallBackResponse" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="content">
		<center><br><p class="txttitle">钉钉回调查询</p><br></center>

		<%! 
		private StringBuffer str = new StringBuffer();
		private Boolean hasCallBack = false;
		private void getCallBackInfo(String token) throws Exception {
			str.setLength(0);
//			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
//			OapiCallBackGetCallBackRequest req = new OapiCallBackGetCallBackRequest();
//			req.setHttpMethod("GET");
//			OapiCallBackGetCallBackResponse rsp = client.execute(req, token);
			OapiCallBackGetCallBackResponse rsp=DingUtils.getDingApiService().queryCallBackEvent(token);
			System.out.println(rsp.getBody());

			if(rsp.getErrcode() == 0){
				hasCallBack = true;
				str.append("&nbsp;&nbsp;&nbsp;&nbsp;回调地址："+rsp.getUrl()+" <br>");
				str.append("&nbsp;&nbsp;&nbsp;&nbsp;回调事件："+rsp.getCallBackTag()+" <br>");
			}else {
				str.append("&nbsp;&nbsp;&nbsp;&nbsp;查询失败或回调不存在："+rsp.getBody()+" <br>");
			}
		}
		%>
		<%
		 if (UserUtil.getKMSSUser().isAdmin()) {
			 String token = DingUtils.getDingApiService().getAccessToken();
			 getCallBackInfo(token);
			 out.println(str.toString());
			 if(hasCallBack){
		%>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<ui:button text="删除回调" onclick="deleteCallBack();" style="vertical-align: top;"></ui:button>
		<script>
			function deleteCallBack(){
				if(confirm("确定删除吗？")){
					var url ="${LUI_ContextPath}/resource/third/ding/endpoint.do?method=deleteCallBack";
					$.ajax({
						url : url,
						type : 'post',
						async : false,
						dataType : "json",
						success : function(data) {
							console.log(data)
							if(data.success){
								alert(data.message);
								location.reload();
							}else{
								alert(data.message);
							}
						} ,
						error : function(req) {
                            alert(req)
						}
					});
				}
			}
		</script>
		<%
				}
		 }else{
			 out.println("非超级管理员禁止访问！");
		 }
		%>
	</template:replace>
</template:include>
