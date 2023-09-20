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
				text="${ lfn:message('sys-attachment:sysAttMain.statistics.borrow') }">
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
			<p class="lui_form_subject">
				${lfn:message("sys-attachment:sysAttMain.statistics.borrow") }</p>

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
				title="${lfn:message('sys-attachment:sysAttMain.statistics.borrow') }"
				expand="false">

				<list:criteria>

					<list:cri-auto
						modelName="com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow"
						property="fdStatus" />



					<list:cri-criterion
						title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdAuth') }"
						key="fdAuth" expand="false">
						<list:box-select>
							<list:item-select>
								<ui:source type="Static">
				[{text:'阅读', value:'read'},
				{text:'下载',value:'download'},
				{text:'拷贝',value:'copy'},
				{text:'打印',value:'print'}]
			</ui:source>
							</list:item-select>
						</list:box-select>
					</list:cri-criterion>

				</list:criteria>

				<div class="lui_list_operation">

					<div class="lui_list_operation_order_btn">
						<list:selectall></list:selectall>
					</div>

					<div class="lui_list_operation_page_top">
						<list:paging layout="sys.ui.paging.top">
						</list:paging>
					</div>

					<div class="lui_list_operation_toolbar">
						<ui:toolbar>
							<ui:button
								text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.export')}"
								onclick="exportExcel('listview')"></ui:button>

							<script>
								seajs.use(
										[ 'lui/export/export', 'theme!list' ],
										function(exportss) {

											window.exportExcel = function(id) {

												exportss.exportExcel(id);
											}
										})
							</script>
						</ui:toolbar>


					</div>

				</div>

				<list:listview id="listview">

					<ui:source type="AjaxJson">
                    {url:'/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=list&rowsize=10&forward=detail&attId=${sysAttMainForm.fdId }&q.docStatus=30'}
            </ui:source>

					<%-- 列表视图--%>
					<list:colTable layout="sys.ui.listview.columntable"
						name="columntable">
						<list:col-checkbox name="List_Selected" style="width:5%"></list:col-checkbox>
						<list:col-serial title="${ lfn:message('page.serial') }"
							headerStyle="width:5%"></list:col-serial>

						<list:col-auto
							props="fdBorrowEffectiveTime;fdDuration;fdStatus;docCreateTime;fdAuth;fdBorrowers;"></list:col-auto>
					</list:colTable>

				</list:listview>
				<list:paging></list:paging>
			</ui:content>
		</ui:tabpage>


	</template:replace>

</template:include>
