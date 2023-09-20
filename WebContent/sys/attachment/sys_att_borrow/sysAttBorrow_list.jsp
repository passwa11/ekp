<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp"
	spa="true">
	<template:replace name="content">

		<link
			href="${ LUI_ContextPath}/sys/attachment/sys_att_borrow/resource/style/list.css?s_cache=${ LUI_Cache }"
			rel="stylesheet">

		<script type="text/javascript"
			src="${ LUI_ContextPath}/sys/attachment/sys_att_borrow/resource/js/list.js?s_cache=${ LUI_Cache }"></script>

		<!-- 筛选器  -->
		<list:criteria id="criteria">

			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject"
				title="${lfn:message('sys-attachment-borrow:sysAttBorrow.docSubject') }">
			</list:cri-ref>

			<list:cri-criterion
				title="${lfn:message('sys-attachment-borrow:sysAttBorrow.docStatus') }"
				key="docStatus" expand="false">
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
				[{text:'${ lfn:message('status.discard') }', value:'00'},
				{text:'${ lfn:message('status.draft') }',value:'10'},
				{text:'${ lfn:message('status.refuse') }',value:'11'},
				{text:'${ lfn:message('status.examine') }',value:'20'},
				{text:'${ lfn:message('status.publish') }',value:'30'},
				{text:'${ lfn:message('status.expire') }',value:'40'}]
			</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>


			<list:cri-criterion
				title="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdStatus') }"
				key="fdStatus" expand="false">
				<list:box-select>
					<list:item-select cfg-if="criteria('docStatus')[0] =='30' ">
						<ui:source type="Static">
				[{text:'${ lfn:message('sys-attachment-borrow:sysAttBorrow.fdStatus.undo') }', value:'0'},
				{text:'${ lfn:message('sys-attachment-borrow:sysAttBorrow.fdStatus.doing') }',value:'1'},
				{text:'${ lfn:message('sys-attachment-borrow:sysAttBorrow.fdStatus.done') }',value:'2'},
				{text:'${ lfn:message('sys-attachment-borrow:sysAttBorrow.fdStatus.close') }',value:'3'}]
			</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>


			<list:cri-auto cfg-if="!param.docStatus"
				modelName="com.landray.kmss.sys.attachment.borrow.model.SysAttBorrow"
				property="docCreateTime;docCreator" />
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
						<list:sort property="sysAttBorrow.docCreateTime"
							text="${lfn:message('sys-attachment-borrow:sysAttBorrow.docCreateTime') }"
							group="sort.list" value="down"></list:sort>
						<list:sort property="sysAttBorrow.fdBorrowEffectiveTime"
							text="${lfn:message('sys-attachment-borrow:sysAttBorrow.fdBorrowEffectiveTime') }"
							group="sort.list"></list:sort>
						<list:sort property="sysAttMain.fdSize"
							text="${lfn:message('sys-attachment:sysAttMain.fdSize') }"
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

						<!-- 新建借阅 -->
						<kmss:auth
							requestURL="/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=add"
							requestMethod="GET">
							<ui:button
								text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.add')}"
								onclick="add()"></ui:button>
						</kmss:auth>

						<!-- 关闭借阅 -->
						<kmss:auth
							requestURL="/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=close"
							requestMethod="GET">
							<ui:button cfg-if="criteria('docStatus')[0]=='30'"
								text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.close')}"
								onclick="close()"></ui:button>
						</kmss:auth>
						
						<!-- 导出功能 -->
						<ui:button
							text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.export')}"
							onclick="exportExcel('listview')"></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>

		<ui:fixed elem=".lui_list_operation"></ui:fixed>

		<list:listview id="listview">

			<ui:source type="AjaxJson">
                    {url:'/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=list&rowsize=8&type=${JsParam.type }'}
            </ui:source>

			<%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="columntable"
				rowHref="/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=view&fdId=!{fdId}">
				<list:col-checkbox name="List_Selected" style="width:5%"></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial') }"
					headerStyle="width:5%"></list:col-serial>

				<list:col-html
					title="${lfn:message('sys-attachment-borrow:sysAttBorrow.docSubject') }"
					styleClass="luiAttSubject">
					{$
						<img
						src="${KMSS_Parameter_ResPath}style/common/fileIcon/{% GetIconNameByFileName(row['docSubject']) %}" />
					<span class="com_subject">{%row['docSubject']%}</span> 
					$}
				</list:col-html>
				<list:col-auto
					props="fdBorrowEffectiveTime;fdDuration;fdSize;fdModule"></list:col-auto>
			</list:colTable>

		</list:listview>
		<list:paging></list:paging>

		<script>
			Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath
					+ "style/common/fileIcon/", "js", true);
		</script>
		<script>
			seajs.use(
					[ 'lui/export/export', 'theme!list' ],
					function(exportss) {

						window.exportExcel = function(id) {

							exportss.exportExcel(id);
						}
					})
		</script>
		
	</template:replace>
</template:include>
