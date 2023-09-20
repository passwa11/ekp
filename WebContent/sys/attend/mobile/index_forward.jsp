<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil,com.landray.kmss.sys.attend.util.AttendUtil,com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,java.util.Map" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
		ISysAttendCategoryService cateService = (ISysAttendCategoryService) SpringBeanUtil
						.getBean("sysAttendCategoryService");
		String url = request.getContextPath()+"/sys/attend/mobile/index.jsp";
		if(cateService.isOnlyDingAttend() && MobileUtil.getClientType(request)==11){
			Map<String,String> map = AttendUtil.getDingAttendConfig();
			if(!map.isEmpty()){
				String corpid = map.get("corpid");
				String agentid = map.get("agentid");
				url = "dingtalk://dingtalkclient/action/switchtab?index=2&name=work&scene=1&corpid="+corpid +"&agentid="+agentid;
			}
		}
		request.setAttribute("__url", url);
%>
<script>
location.href = "${__url}";
</script>