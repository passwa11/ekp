<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"
			var-navwidth="95%">
			<script>
				function confirmDelete(msg) {
					var del = confirm("<bean:message key="page.comfirmDelete"/>");
					return del;
				}
			</script>
			<kmss:auth
				requestURL="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do?method=edit&fdId=${param.fdId}"
				requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }"
					onclick="Com_OpenWindow('dbEchartsTemplate.do?method=edit&fdModelName=com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth
				requestURL="/dbcenter/echarts/db_echarts_template/dbEchartsTemplate.do?method=delete&fdId=${param.fdId}"
				requestMethod="GET">
				<ui:button text="${ lfn:message('button.delete') }"
					onclick="if(!confirmDelete())return;Com_OpenWindow('dbEchartsTemplate.do?method=delete&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }"
				onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">

		<p class="txttitle">
			<bean:message bundle="dbcenter-echarts" key="table.dbEchartsTemplate" />
		</p>

		<center>
			<table class="tb_normal" id="Label_Tabel" width=95%>
				<c:import url="/sys/simplecategory/include/sysCategoryMain_view.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="dbEchartsTemplateForm" />
					<c:param name="fdModelName"
						value="com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate" />
					<c:param name="fdKey" value="dbEchartsTemplate" />
				</c:import>

				<tr
					LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
					<td>
						<table class="tb_normal" width=100%>
							<c:import url="/sys/right/tmp_right_view.jsp"
								charEncoding="UTF-8">
								<c:param name="formName" value="dbEchartsTemplateForm" />
								<c:param name="moduleModelName"
									value="com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate" />
							</c:import>
						</table>
					</td>
				</tr>


				<tr LKS_LabelName='${ lfn:message('config.baseinfo') }'>
					<td>
						<table class="tb_normal" width=100%>
							<tr>
								<td class="td_normal_title" width=15%><bean:message
										bundle="dbcenter-echarts" key="dbEchartsTemplate.fdName" /></td>
								<td width="35%"><xform:text property="fdName"
										style="width:85%" /></td>
								<td class="td_normal_title" width=15%><bean:message
										bundle="dbcenter-echarts" key="dbEchartsTemplate.fdOrder" /></td>
								<td width="35%"><xform:text property="fdOrder"
										style="width:85%" /></td>
							</tr>
							<tr>
								<td class="td_normal_title" width=15%><bean:message
										bundle="dbcenter-echarts" key="dbEchartsTemplate.docCreateTime" />
								</td>
								<td colspan="3"><xform:datetime property="docCreateTime" />
								</td>

							</tr>
							<tr>
								<td class="td_normal_title" width=15%><bean:message
										bundle="dbcenter-echarts" key="dbEchartsTemplate.fdParent" /></td>
								<td width="35%"><c:out
										value="${dbEchartsTemplateForm.fdParentName}" /></td>
								<td class="td_normal_title" width=15%><bean:message
										bundle="dbcenter-echarts" key="dbEchartsTemplate.docCreatorName" />
								</td>
								<td width="35%"><c:out
										value="${dbEchartsTemplateForm.docCreatorName}" /></td>
							</tr>
						</table>
					</td>
				</tr>
				
			</table>
		</center>

	</template:replace>
</template:include>