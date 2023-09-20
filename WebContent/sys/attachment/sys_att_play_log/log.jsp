<%@page
	import="com.landray.kmss.sys.attachment.service.spring.SysAttPlayLogTypeFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">

		<!-- 筛选器 -->
		<list:criteria id="criteria">

			<list:cri-ref key="fdName" ref="criterion.sys.docSubject">
			</list:cri-ref>

			<list:cri-criterion
				title="${lfn:message('sys-attachment:sysAttachmentPlayLog.fdType') }"
				key="fdType" expand="false" multi="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							<%
								out.print(SysAttPlayLogTypeFactory
																	.getTypeData());
							%>
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-auto
				modelName="com.landray.kmss.sys.attachment.model.SysAttPlayLog"
				property="docCreator" />
		</list:criteria>


		<!-- 操作栏 -->
		<div class="lui_list_operation">


			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort">
						<list:sort property="sysAttPlayLog.docCreateTime"
							text="${lfn:message('sys-attachment:sysAttachmentPlayLog.docCreateTime')}"
							group="sort.list" />
						<list:sort property="sysAttPlayLog.docAlterTime"
							text="${lfn:message('sys-attachment:sysAttachmentPlayLog.docAlterTime')}"
							group="sort.list" value="down" />
					</ui:toolbar>
				</div>
			</div>
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
		</div>

		<ui:fixed elem=".lui_list_operation"></ui:fixed>

		<list:listview>

			<ui:source type="AjaxJson">
				{url:'/sys/attachment/sys_att_play_log/sysAttPlayLog.do?method=list'}
			</ui:source>

			<list:colTable isDefault="true" layout="sys.ui.listview.columntable">

				<list:col-serial></list:col-serial>
				<list:col-auto
					props="fdName;docCreator.fdName;docCreateTime;docAlterTime;fdParam"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
		<list:paging />
	</template:replace>
</template:include>
