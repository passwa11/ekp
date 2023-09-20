<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<template:include ref="default.view" sidebar="no">

	<template:replace name="title">
		<c:out
			value="${sysAttMainForm.fdFileName} - ${ lfn:message('sys-attachment:sysAttMain.statistics.download')  }"></c:out>
	</template:replace>

	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item
				text="${ lfn:message('sys-attachment:sysAttMain.statistics') }">
			</ui:menu-item>
			<ui:menu-item
				text="${ lfn:message('sys-attachment:sysAttMain.statistics.download') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>

	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('button.close')}" order="5"
				onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>

	<template:replace name="content">

		<div class="lui_form_content_frame">

			<script type="text/javascript"
				src="${ LUI_ContextPath}/sys/attachment/sys_att_main/resource/js/list.js?s_cache=${ LUI_Cache }"></script>

			<p class="lui_form_subject">
				${lfn:message("sys-attachment:sysAttMain.statistics.download") }</p>

			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>${lfn:message("sys-attachment:sysAttRecovery.fdName") }</td>
					<td width="35%"><c:out value="${ sysAttMainForm.fdFileName}" /></td>
					<td class="td_normal_title" width=15%>${lfn:message("sys-attachment:sysAttRecovery.fdSize") }</td>
					<td width="35%">${fdSize }</td>
				</tr>

				<tr>
					<td class="td_normal_title" width=15%>${lfn:message("sys-attachment:sysAttMain.fdMain") }</td>
					<td width="35%"><a href="${ LUI_ContextPath}${fdMainUrl }"
						target="_blank"><c:out value="${ fdMainName}" /></a></td>
					<td class="td_normal_title" width=15%>${lfn:message("sys-attachment:sysAttMain.fdModule") }</td>
					<td width="35%">${fdModuleName }</td>
				</tr>

			</table>

		</div>


		<ui:tabpage>

			<ui:content
				title="${ lfn:message('sys-attachment:sysAttMain.statistics.download') }"
				expand="false">

				<div class="lui_list_operation">

					<div class="lui_list_operation_order_btn">
						<list:selectall></list:selectall>
					</div>

					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>


					<div class="lui_list_operation_toolbar">


						<ui:toolbar count="1">
							<ui:button
								text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.export')}"
								onclick="exportExcel('listview')"></ui:button>
						</ui:toolbar>

					</div>
				</div>

				<list:listview id="listview">
					<ui:source type="AjaxJson">
				{"url":"/sys/attachment/sys_att_download_log/sysAttDownloadLog.do?method=list&rowsize=10&q.fdAttId=${ sysAttMainForm.fdId}"}
			</ui:source>
					<list:colTable isDefault="true" layout="sys.ui.listview.listtable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto
							props="fdFileName;docCreatorName;fdDeptName;docCreateTime;fdIp"></list:col-auto>
					</list:colTable>
				</list:listview>
				<list:paging>
				</list:paging>
			</ui:content>
		</ui:tabpage>

	</template:replace>

</template:include>
