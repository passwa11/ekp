<%@page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
// 判断是否开启日志服务
boolean isopenLog = "true".equals(ResourceUtil.getKmssConfigString("log.openLogService"));
request.setAttribute("_year", Calendar.getInstance().get(Calendar.YEAR));
request.setAttribute("_month", Calendar.getInstance().get(Calendar.MONTH) + 1);
%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 内容列表 -->
		<list:listview>
			<% if(isopenLog) { %>
			<ui:source type="AjaxJson">
				{url:'/sys/log/sys_log_system/sysLogSystem.do?method=list&q.fdServiceBean=${JsParam.fdJobService}.${JsParam.fdJobMethod}()&q.fdYear=${_year}&q.fdMonth=${_month}&rowsize=${JsParam.rowsize}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="/sys/log/sys_log_system/sysLogSystem.do?method=view&fdId=!{fdId}">
				<% if(TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { %>
				<list:col-auto props="fdStartTime,fdEndTime,fdTaskDuration,fdSuccess"></list:col-auto>
				<% } else { %>
				<list:col-auto props="fdStartTime,fdEndTime,fdTaskDuration,fdSubject,fdSuccess"></list:col-auto>
				<% } %>
			</list:colTable>
			<% } else { %>
			<ui:source type="AjaxJson">
				{url:'/sys/log/sys_log_job/sysLogJob.do?method=list&fdJobService=${JsParam.fdJobService}&fdJobMethod=${JsParam.fdJobMethod}&fdSubject=${param.fdSubject}&rowsize=${JsParam.rowsize}'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/log/sys_log_job/sysLogJob.do?method=view&fdId=!{fdId}">
				<list:col-auto props="fdStartTime,fdEndTime,fdTaskDuration,fdSubject,fdSuccess"></list:col-auto>
			</list:colTable>
			<% } %>
			<ui:event topic="list.loaded">  
				seajs.use(['lui/jquery'],function($){
					try {
						if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
							if($(".lui_listview_columntable_table").length > 0)
								window.frameElement.style.height = ($(document.body).height() + 20) + "px";
							else
								window.frameElement.style.height = ($(document.body).height() + 170) + "px";
						}
					}catch(e) {
					}
				});
			</ui:event>
		</list:listview>
		<br>
		<list:paging/>
	</template:replace>
</template:include>
