<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link
	href="${ LUI_ContextPath}/sys/attachment/sys_att_main/resource/style/list.css?s_cache=${ LUI_Cache }"
	rel="stylesheet">

<script type="text/javascript"
	src="${ LUI_ContextPath}/sys/attachment/sys_att_main/resource/js/list.js?s_cache=${ LUI_Cache }"></script>

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

	<list:cri-criterion
		title="${lfn:message('sys-attachment:sysAttMain.fileType')}"
		key="fileType" expand="false" multi="true">
		<list:box-select>
			<list:item-select>
				<ui:source type="Static">
				[{text:'DOC', value:'doc'}, {text:'PPT', value: 'ppt'}, {text:'PDF',value:'pdf'},
				{text:'XLS', value: 'excel'},
				{text:'${lfn:message('sys-attachment:sysAttMain.fileType.image')}', value: 'pic'},
				{text:'${lfn:message('sys-attachment:sysAttMain.fileType.audio')}', value: 'audio'}, 
				{text:'${lfn:message('sys-attachment:sysAttMain.fileType.video')}', value: 'video'},
				{text:'${lfn:message('sys-attachment:sysAttMain.fileType.others')}', value: 'others'}]
			</ui:source>
			</list:item-select>
		</list:box-select>
	</list:cri-criterion>

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

		<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="6">
			<list:sortgroup>

				<list:sort property="sysAttMain.docCreateTime"
					text="${lfn:message('sys-attachment:sysAttMain.docCreateTime') }"
					group="sort.list" value="down"></list:sort>
				<list:sort property="sysAttMain.fdSize"
					text="${lfn:message('sys-attachment:sysAttMain.fdSize') }"
					group="sort.list" value="down"></list:sort>
				<c:if test="${empty JsParam.hideNum }">
					<list:sort property="sysAttMain.downloadSum"
						text="${lfn:message('sys-attachment:sysAttMain.downloadSum') }"
						group="sort.list" value="down"></list:sort>
					<list:sort property="sysAttMain.fdBorrowCount"
						text="${lfn:message('sys-attachment:sysAttMain.fdBorrowCount') }"
						group="sort.list" value="down"></list:sort>
				</c:if>

			</list:sortgroup>
		</ui:toolbar>
	</div>

	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top">
		</list:paging>
	</div>

	<div class="luiAttTip">${lfn:message("sys-attachment:sysAttMain.imageTip") }</div>

	<!-- 操作按钮 -->
	<div style="float: right">
		<div class="lui_table_toolbar_inner">
			<ui:toolbar count="3">

				<kmss:ifModuleExist path="/kms/multidoc">
					<kmss:auth
						requestURL="/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=add"
						requestMethod="GET">
						<ui:button
							text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.transt')}"
							onclick="addShare()"></ui:button>

					</kmss:auth>

				</kmss:ifModuleExist>

				<kmss:auth
					requestURL="/sys/attachment/sys_att_borrow/sysAttBorrow.do?method=add"
					requestMethod="GET">
					<ui:button
						text="${lfn:message('sys-attachment-borrow:sysAttBorrow.button.add')}"
						onclick="addBorrow()"></ui:button>

				</kmss:auth>

				<!-- 导出功能 -->
				<form name="exportData" style="display: none;"></form>
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
                    {url:'/sys/attachment/sys_att_main/sysAttMain.do?method=list&rowsize=8&orderby=sysAttMain.docCreateTime&ordertype=down&hideNum=${JsParam.hideNum }'}
            </ui:source>

	<%-- 列表视图--%>
	<list:colTable layout="sys.ui.listview.columntable" name="columntable">

		<list:col-checkbox></list:col-checkbox>
		<list:col-serial title="${ lfn:message('page.serial') }"
			headerStyle="width:3%"></list:col-serial>

		<list:col-html
			title="${lfn:message('sys-attachment:sysAttRecovery.fdName') }"
			styleClass="luiAttSubject">
					{$
						<img
				src="${KMSS_Parameter_ResPath}style/common/fileIcon/{% GetIconNameByFileName(row['fdFileName']) %}" />
			<span class="com_subject">{%row['fdFileName']%}</span> 
					$}
				</list:col-html>

		<list:col-auto
			props="fdSize;fdBorrowCount;downloadSum;fdCreatorId;docCreateTime;mainName;module" />

		<list:col-html
			title="${lfn:message('sys-attachment:sysAttMain.operations') }"
			styleClass="lui-upload-list-operation" style="width:120px">
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
	seajs.use('theme!module');
</script>
