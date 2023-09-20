<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link
	href="${ LUI_ContextPath}/sys/portal/sys_portal_map_tpl/resource/css/tree.css?s_cache=${ LUI_Cache }"
	rel="stylesheet">

<style>
.tb_normal .lui_tree_title {
	background-color: #f6f6f6;
}

.imgbox {
	width: 400px;
	height: 150px
}
</style>

<script>
	window.trees = []
	seajs.use(
			[ 'lui/jquery', 'sys/portal/sys_portal_map_tpl/resource/js/Tree' ],
			function($, Tree) {

				$(document).on('table-add', function(e, row) {

					var container = $(row).find(".container")

					if (container.length == 0) {
						return;
					}
					var tree = new Tree.Tree({
						'container' : container,
						'bindElement' : $(row).find("textarea")
					})
					trees.push(tree)
				})

				$(document).on(
						'table-move',
						function(e, currentIndex, lastIndex) {

							currentIndex--;
							lastIndex--;
							trees[currentIndex] = trees.splice(lastIndex, 1,
									trees[currentIndex])[0]
						});

				$(document).on('table-delete', function(e, row) {

					var index = $(row).data('index');
					trees.splice(index, 1)
				});

			})
</script>


<table class="tb_normal ${hasAtt?'':'luiPortalMapTplHideAtt'}" width="100%" id="TABLE_DocCustom" 
	tbdraggable="true" showindex="true">

	<tr type="optRow" class="tr_normal_opt" invalidrow="true">
		<td colspan="4" align="right" column="0" row="3" coltype="optCol">
			<div class="customToolbar">
				<ui:button text="${lfn:message('sys-portal:sysPortalPage.desgin.msg.addportlet') }" styleClass="lui_toolbar_btn_gray"
					onclick="DocList_AddRow()"></ui:button>
				<ui:button text="${lfn:message('sys-portal:sysPortalPage.desgin.msg.delportlet') }" styleClass="lui_toolbar_btn_gray"
					onclick="DocList_BatchDeleteRow()"></ui:button>
				<ui:button text="${lfn:message('sys-portal:sysPortalPage.desgin.opt.moveup') }" styleClass="lui_toolbar_btn_gray"
					onclick="DocList_MoveRowBySelect(-1)"></ui:button>
				<ui:button text="${lfn:message('sys-portal:sysPortalPage.desgin.opt.movedown') }" styleClass="lui_toolbar_btn_gray"
					onclick="DocList_MoveRowBySelect(1)"></ui:button>
			</div>
		</td>
	</tr>

	<tr KMSS_IsReferRow="1" style="display: none" data-index="!{index}">
		<td class="lui_tree_title" align="center" style="width: 3%"><input
			type="checkbox" name="DocList_Selected"></td>
		<td align="center" class="lui_tree_title" style="width: 7%"
			KMSS_IsRowIndex=1>${lfn:message('sys-portal:sysPortalMapTplNavCustom.lable') }{1}</td>
		<td style="width: 100%; padding: 0px !important;">
			<table width="100%" class="tb_normal">
				<tr>
					<td class="lui_tree_title" align="center">
						${lfn:message("sys-portal:sysPortalMapTplNavCustom.fdName") }</td>
					<td><xform:text value=""
							property="fdNavCustomForms[!{index}].fdName" required="true"
							showStatus="edit" style="width:90%;" /></td>
				</tr>
				<tr>
					<td class="lui_tree_title" align="center">
						${lfn:message("sys-portal:sysPortalMapTplNavCustom.fdContent") }</td>
					<td>
						<div class="container"></div> <textarea style="display: none"
							name="fdNavCustomForms[!{index}].fdContent"></textarea>
					</td>
				</tr>
				<tr class="lui_upload_container">
					<td class="lui_tree_title" align="center">
						${lfn:message("sys-portal:sysPortalMapTplNavCustom.fdAttachmentId") }
					</td>
					<td colspan="3"><input
						name="fdNavCustomForms[!{index}].fdAttachmentId" value=""
						type="hidden" /> <c:import
							url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
							charEncoding="UTF-8">
							<c:param name="dtable" value="true" />
							<c:param name="fdKey" value="TABLE_DocCustom.fdAttachment" />
							<c:param name="fdAttType" value="pic" />
							<c:param name="fdShowMsg" value="true" />
							<c:param name="fdMulti" value="false" />
							<c:param name="fdLayoutType" value="pic" />
							<c:param name="fdPicContentWidth" value="400" />
							<c:param name="fdPicContentHeight" value="150" />
							<c:param name="fdViewType" value="pic_single" />
							<c:param name="idx" value="!{index}" />
						</c:import></td>
				</tr>
			</table>
		</td>
	</tr>

	<c:forEach items="${sysPortalMapTplForm.fdNavCustomForms}" var="nav"
		varStatus="status">
		<tr KMSS_IsContentRow="1" data-index="${status.index}">

			<td class="lui_tree_title" align="center" style="width: 3%"><input
				type="checkbox" name="DocList_Selected"></td>
			<td class="lui_tree_title" align="center" style="width: 7%"
				KMSS_IsRowIndex=1>${lfn:message("sys-portal:sysPortalMapTplNavCustom.lable ") }${status.index+1}</td>
			<td style="width: 100%; padding: 0px !important;">
				<table width="100%" class="tb_normal">
					<tr>
						<td align="center" class="lui_tree_title">
							${lfn:message("sys-portal:sysPortalMapTplNavCustom.fdName") }</td>
						<td><xform:text value="${nav.fdName }"
								property="fdNavCustomForms[${status.index}].fdName"
								required="true" showStatus="edit" style="width:90%;" /></td>
					</tr>
					<tr>
						<td align="center" class="lui_tree_title">
							${lfn:message("sys-portal:sysPortalMapTplNavCustom.fdContent") }
						</td>
						<td><div class="container"></div> <textarea
								style="display: none"
								name="fdNavCustomForms[${status.index}].fdContent">${nav.fdContent }</textarea>
							<script>
								seajs
										.use(
												[
														'lui/jquery',
														'sys/portal/sys_portal_map_tpl/resource/js/Tree' ],
												function($, Tree) {

													var textarea = $('textarea[name="fdNavCustomForms[${status.index}].fdContent"]')
													var container = textarea
															.prev('.container')

													var tree = new Tree.Tree(
															{
																'container' : container,
																'bindElement' : textarea
															})

													trees.push(tree)
												})
							</script></td>
					</tr>
					<tr class="lui_upload_container">
						<td align="center" class="lui_tree_title">
							${lfn:message("sys-portal:sysPortalMapTplNavCustom.fdAttachmentId") }
						</td>
						<td colspan="3"><input
							name="fdNavCustomForms[${status.index}].fdAttachmentId"
							value="${nav.fdAttachmentId }" type="hidden" /> <c:import
								url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
								charEncoding="UTF-8">
								<c:param name="dtable" value="true" />
								<c:param name="fdKey" value="TABLE_DocCustom.fdAttachmentId" />
								<c:param name="fdAttType" value="pic" />
								<c:param name="fdShowMsg" value="true" />
								<c:param name="fdMulti" value="false" />
								<c:param name="fdLayoutType" value="pic" />
								<c:param name="fdPicContentWidth" value="400" />
								<c:param name="fdPicContentHeight" value="150" />
								<c:param name="fdViewType" value="pic_single" />
								<c:param name="dTableType" value="nonxform" />
								<c:param name="formName" value="sysPortalMapTplForm" />
								<c:param name="formListAttribute" value="fdNavCustomForms" />
								<c:param name="idx" value="${status.index}" />
							</c:import></td>
					</tr>
				</table>
			</td>
		</tr>
	</c:forEach>

</table>