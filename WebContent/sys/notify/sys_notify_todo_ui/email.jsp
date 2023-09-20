<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.notify.interfaces.ISysNotifyEmailService,com.landray.kmss.sys.notify.service.spring.SysNotifyEmailServiceImp"%>
<%
	//主页待办窗口显示邮件数扩展点Id以及参数名称
	//获取主页待办窗口显示邮件数扩展点的扩展
	ISysNotifyEmailService service = new SysNotifyEmailServiceImp();
	if(service.isShowEmails()){
		request.setAttribute("_showEmail",true);
	}
%>
<c:if test="${_showEmail eq true}">
	<c:catch var="exception">
		<span id="lui_notify_todo_line" class="lui_notify_todo_line"></span>
		<span class="lui_notify_email_link">
			<c:import url="/sys/notify/sys_notify_todo/sysMail_home_mid.jsp" charEncoding="UTF-8"/>
		 </span>
	</c:catch> 
	 <c:if test="${not empty exception}">
	 	<script type="text/javascript">
			document.getElementById('lui_notify_todo_line').style.display="none";
		</script>
	 </c:if>
</c:if>
