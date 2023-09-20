<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('dbcenter-echarts:portlet.dbcustom')}</template:replace>
	<template:replace name="body">
		<div style="width: 95%; margin: 10px auto;">
			<div data-lui-type="lui/search_box!SearchBox"><script
					type="text/config">
						{
							placeholder: "${lfn:message('sys-ui:ui.criteria.search')}",
							width: '90%'
						}
					</script><ui:event event="search.changed" args="evt">
				LUI('listview').tableRefresh({
				criterions:[{key:"key", value: [evt.searchText]}]
				});
			</ui:event></div>
		</div>
		<script>

			function submitSelected(fdId,chartName) {

				window.$dialog.hide({
					"fdId":fdId,
					"fdName":chartName
				});

				//window.$dialog.hide(LUI('selectedBean').getValues());
			}
		</script>

		<table class="tb_normal"
			   style="margin: 10px auto; width: 95%; height: 460px;">
			<tr>
				<td valign="top"><list:listview id="listview">
					<ui:source type="AjaxJson">
						{"url":"/dbcenter/echarts/db_echarts_custom/dbEchartsCustomPortlet.do?method=list"}
					</ui:source>
					<list:colTable sort="false"
								   onRowClick="submitSelected('!{fdId}','!{chartName}')">
						<list:col-serial style="width:25%;"></list:col-serial>
						<list:col-html
								title="${lfn:message('dbcenter-echarts:portlet.dbcustom')}"
								style="width:75%;">
							{$<div style="overflow: hidden; text-overflow: ellipsis;">{%row['chartName']%}</div>$}
						</list:col-html>
					</list:colTable>
				</list:listview> <list:paging></list:paging></td>
			</tr>
		</table>

	</template:replace>
</template:include>