<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">请求监控</template:replace>
	<template:replace name="content">
		<div style="text-align:right; line-height: 30px; padding-right:10px;">
			<bean:message key="sysAdminThreadMonitor.text" bundle="sys-admin"  arg0="${urlBlockSize}"/>
		</div>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 操作按钮 -->
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="5">
						<ui:button text="${lfn:message('sys-admin:sysAdminThreadMonitor.blockingAddress') }" onclick="edit();" order="1"></ui:button>
						<ui:button text="${lfn:message('sys-admin:sysAdminThreadMonitor.allThreads') }" onclick="threaddump();" order="2"></ui:button>
						<% if ("true".equalsIgnoreCase(com.landray.kmss.util.ResourceUtil.getKmssConfigString("kmss.jdbc.stat.enabled"))) { %>
						<ui:button text="${lfn:message('sys-admin:sysAdminThreadMonitor.JDBC') }" onclick="jdbc();" order="3"></ui:button>		
						<% } %>
						<ui:button text="${lfn:message('button.refresh') }" onclick="history.go(0);" order="4"></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/admin/threadmonitor.do?method=monitor'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
			    <list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="threadName,url,user,ip,time"></list:col-auto>
			</list:colTable>
		</list:listview>
		
		<script type="text/javascript">
			function edit() {
				window.open('<c:url value="/sys/admin/threadmonitor.do?method=edit" />','_blank');
			}
			function threaddump() {
				window.open('<c:url value="/sys/admin/threadmonitor/threaddump.jsp" />','_blank');
			}
			function jdbc() {
				window.open('<c:url value="/sys/admin/druid/" />','_blank');
			}
			$(function() {
				$.getJSON('<c:url value="/sys/admin/threadmonitor.do?method=urlBlockSize" />', function(json) {
					$("#urlBlockSize").text(json);
				});
			});
		</script>
	</template:replace>
</template:include>
