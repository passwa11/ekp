<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ page import="com.landray.kmss.sys.admin.commontools.actions.SysAdminCommontoolsClearLogAction"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	Set<String> tables = SysAdminCommontoolsClearLogAction.TABLE_MAP.keySet();
%>

<template:include ref="default.view" sidebar="auto">
	<template:replace name="title">
		${ lfn:message('sys-admin:sys.admin.commontools.clearLog') }
	</template:replace>
	<template:replace name="head">
		<script>
		seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
			search = function(table, init) {
				$.get("${LUI_ContextPath}/sys/admin/commontools/clearLog.do?method=searchLog&table=" + table, function(res) {
					if(res.success) {
						$("#" + table + "_count").text(res.count);
						if(!init) {
							dialog.success('<bean:message key="return.optSuccess"/>');
						}
					} else {
						dialog.failure('<bean:message key="return.optFailure"/>');
					}
				}, "json");
			};
			
			clear = function(table) {
				dialog.confirm('<bean:message bundle="sys-admin" key="sys.admin.commontools.clearLog.warn"/>', function(value) {
					if (value==true) {
						window._load = dialog.loading();
						$.get("${LUI_ContextPath}/sys/admin/commontools/clearLog.do?method=clearLog&table=" + table, function(res) {
							if (window._load != null) {
								window._load.hide();
							}
							if(res.success) {
								dialog.success('<bean:message key="return.optSuccess"/>');
								$("#" + table + "_count").text(0);
							} else {
								dialog.failure('<bean:message key="return.optFailure"/>');
							}
						}, "json");
					}
				});
			};
			
			$(function() {
				var tables = $("#clearLog-table").find("tr[data-table]");
				tables.each(function(i) {
					search($(this).data("table"), true);
				});
			});
		});
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="7">
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow()"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle">${ lfn:message('sys-admin:sys.admin.commontools.clearLog') }</p>
		<center>
			<p style="color: red;">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.warn') }</p>
			<table id="clearLog-table" class="tb_normal" width=85%>
				<tr>
					<td class="td_normal_title" align="center" width="5%">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.number') }</td>
					<td class="td_normal_title" align="center" width="30%">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.name') }</td>
					<td class="td_normal_title" align="center" width="40%">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.remark') }</td>
					<td class="td_normal_title" align="center" width="10%">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.count') }</td>
					<td class="td_normal_title" align="center" width="15%">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.operation') }</td>
				</tr>
				<%
					int i = 0;
					for (String table: tables) {
						i++;
						String remark = ResourceUtil.getString("sys-admin:sys.admin.commontools.clearLog." + table);
				%>
				<tr data-table="<%=table%>">
					<td align="center"><%=i%></td>
					<td><%=table%></td>
					<td><%=remark%></td>
					<td id="<%=table%>_count">0</td>
					<td align="center">
						<a href="javascript:search('<%=table%>', false);">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.search') }</a>
						&nbsp;&nbsp;&nbsp;
						<a href="javascript:clear('<%=table%>');">${ lfn:message('sys-admin:sys.admin.commontools.clearLog.clear') }</a>
					</td>
				</tr>
				<%
					}
				%>
			</table>
		</center>
	</template:replace>
</template:include>