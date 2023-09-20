<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp"
	spa="true">
	<template:replace name="content">

		<link
			href="${ LUI_ContextPath}/sys/attachment/sys_att_main/resource/style/list.css?s_cache=${ LUI_Cache }"
			rel="stylesheet">

		<script type="text/javascript"
			src="${ LUI_ContextPath}/sys/attachment/sys_att_main/resource/js/list.js?s_cache=${ LUI_Cache }"></script>

		<!-- 筛选器  -->
		<list:criteria id="criteria">

			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"
				title="${lfn:message('sys-attachment:sysAttRecovery.fdName') }">
			</list:cri-ref>

			<list:cri-auto
				modelName="com.landray.kmss.sys.attachment.model.SysAttMain"
				property="docCreateTime" />

			<list:cri-ref key="fdCreatorId"
				title="${lfn:message('sys-attachment:sysAttMain.fdCreatorId') }"
				ref="criterion.sys.person"></list:cri-ref>

		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">

			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">

				<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left"
					count="6">
					<list:sortgroup>
						<list:sort property="sysAttMain.docCreateTime"
							text="${lfn:message('sys-attachment:sysAttFile.docCreateTime') }"
							group="sort.list" value="down"></list:sort>
					</list:sortgroup>
				</ui:toolbar>
			</div>

			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>

			<!-- 操作按钮 -->
			<div style="float: right">
				<div class="lui_table_toolbar_inner">
					<ui:toolbar count="3">
						<ui:button text="知识转化" onclick=""></ui:button>
						<ui:button
							text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.add')}"
							onclick="addBorrow()"></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>

		<ui:fixed elem=".lui_list_operation"></ui:fixed>

		<list:listview id="listview">
			<ui:source type="AjaxJson">
                    {url:'/sys/attachment/sys_att_main/sysAttMain.do?method=list&rowsize=8&orderby=sysAttMain.docCreateTime&ordertype=down'}
            </ui:source>

			<%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="columntable">
				
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial') }"
					headerStyle="width:5%"></list:col-serial>

				<list:col-html
					title="${lfn:message('sys-attachment:sysAttRecovery.fdName') }"
					styleClass="luiAttSubject">
					{$
						<img
						src="${KMSS_Parameter_ResPath}style/common/fileIcon/{% GetIconNameByFileName(row['fdFileName']) %}" />
					<span class="com_subject">{%row['fdFileName']%}</span> 
					$}
				</list:col-html>

				<list:col-auto props="fdSize;fdCreatorId;docCreateTime;module" />

				<list:col-html
					title="${lfn:message('sys-attachment:sysAttMain.operations') }"
					styleClass="lui-upload-list-operation">
					{$
					
						{%GetOperations(row['fdFileName'],row['fdId']) %}
					$}
				</list:col-html>
			</list:colTable>

		</list:listview>

		<list:paging></list:paging>

		<script>
			Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath
					+ "style/common/fileIcon/", "js", true);
		</script>
	</template:replace>
</template:include>
