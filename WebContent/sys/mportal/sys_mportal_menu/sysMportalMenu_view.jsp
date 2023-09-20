<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">

	<template:replace name="toolbar">
		<script>
			function confirmDelete(msg) {
				var del = confirm("<bean:message key="page.comfirmDelete"/>");
				return del;
			}
		</script>

		<link rel="stylesheet" type="text/css"
			href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>

		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"
			var-navwidth="95%">
			<kmss:auth
				requestURL="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=edit&fdId=${param.fdId}"
				requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }"
					onclick="Com_OpenWindow('sysMportalMenu.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth
				requestURL="/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=delete&fdId=${param.fdId}"
				requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }"
					onclick="if(!confirmDelete())return;Com_OpenWindow('sysMportalMenu.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }"
				onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>

	</template:replace>
	<template:replace name="content">


		<div class="lui_content_form_frame" style="padding: 20px 0px">
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-mportal" key="sysMportalMenu.docSubject" /></td>
					<td width="85%" colspan="3"><xform:text property="docSubject"
							style="width:85%" /></td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-mportal" key="sysMportalMenu.docCreateTime" />
					</td>
					<td width="35%"><xform:datetime property="docCreateTime" /></td>
					<td class="td_normal_title" width=15%><bean:message
							bundle="sys-mportal" key="sysMportalMenu.docAlterTime" /></td>
					<td width="35%"><xform:datetime property="docAlterTime" /></td>
				</tr>

				<tr>
					<td colspan="4">
						<table style="width: 95%" class="tb_normal">
							<tr>
								<td width="10%" KMSS_IsRowIndex="1" class="td_normal_title"><bean:message key="page.serial" /></td>
								<td width="50px" class="td_normal_title">图标</td>
								<td width="25%" class="td_normal_title">模块名</td>
								<td width="55%" class="td_normal_title">模块链接</td>
							</tr>
							<c:forEach
								items="${sysMportalMenuForm.fdSysMportalMenuItemForms}"
								var="item" varStatus="vstatus">
								<tr KMSS_IsContentRow="1">
									<td width="10%" KMSS_IsRowIndex="1" id="KMSS_IsRowIndex_View">${vstatus.index+1}</td>
									<td width="50px">
										<div class="mui ${item.fdIcon}" claz="${item.fdIcon}"></div>
									</td>
									<td width="25%"><input
										name="fdSysMportalMenuItemForms[!{index}].fdName"
										class="inputsgl" style="width: 95%" value="${item.fdName}" /></td>
									<td width="55%"><input
										name="fdSysMportalMenuItemForms[!{index}].fdUrl"
										class="inputsgl" style="width: 95%" readonly="readonly"
										value="${item.fdUrl}" /></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>

			</table>
		</div>

	</template:replace>
</template:include>