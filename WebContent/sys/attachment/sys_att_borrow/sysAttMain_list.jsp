<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">

		<link
			href="${ LUI_ContextPath}/sys/attachment/sys_att_borrow/resource/style/att_list.css?s_cache=${ LUI_Cache }"
			rel="stylesheet">

		<script type="text/javascript"
			src="${ LUI_ContextPath}/sys/attachment/sys_att_borrow/resource/js/att_list.js?s_cache=${ LUI_Cache }"></script>

		<!-- 筛选器  -->
		<list:criteria id="criteria">

			<list:cri-ref key="fdFileName" ref="criterion.sys.docSubject"
				title="${lfn:message('sys-attachment:sysAttRecovery.fdName') }">
			</list:cri-ref>

			<list:cri-criterion
				title="${lfn:message('sys-attachment:sysAttMain.fdModule') }"
				key="fdModelName">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
							{url:'/sys/attachment/sys_att_main/sysAttMain.do?method=modules'}
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>

			<list:cri-auto
				modelName="com.landray.kmss.sys.attachment.model.SysAttMain"
				property="docCreateTime" />

			<list:cri-ref key="fdCreatorId" title="创建者"
				ref="criterion.sys.person"></list:cri-ref>

		</list:criteria>

		<div id="selectedBean"
			data-lui-type="lui/selected/multi_selected!Selected"
			style="width: 95%; margin: 10px auto;">
			<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
			</script>
		</div>

		<!-- 操作栏 -->
		<div class="lui_list_operation">

			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">

				<ui:toolbar layout="sys.ui.toolbar.sort">

					<list:sortgroup>
						<list:sort property="sysAttMain.docCreateTime"
							text="${lfn:message('sys-attachment:sysAttMain.docCreateTime') }"
							group="sort.list" />
					</list:sortgroup>

				</ui:toolbar>
			</div>

			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>

		</div>

		<list:listview id="listview">
			<ui:source type="AjaxJson">
                    {url:'/sys/attachment/sys_att_main/sysAttMain.do?method=list&rowsize=8&orderby=sysAttMain.docCreateTime&ordertype=down'}
            </ui:source>

			<%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="columntable"
				onRowClick="selectItem('!{fdId}','!{fdFileName}')">
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
			</list:colTable>

		</list:listview>
		<list:paging></list:paging>
		<script>
			Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath
					+ "style/common/fileIcon/", "js", true);
		</script>

	</template:replace>



</template:include>