<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" width="95%" sidebar="no">
	<template:replace name="body">
	<table style="margin:20px auto;width:95%;max-height:460px;">
		<tr>
			<td>
				<list:listview>
					<ui:source type="AjaxJson">
						{url:'/sys/portal/sys_portal_nav/sysPortalNav.do?method=list&config=${param.config}'}
					</ui:source>
					<list:colTable sort="false" onRowClick="selectCustomPage('!{fdId}','!{fdName}')">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdName"></list:col-auto>
						<list:col-html title="">
							{$
								<a class='com_btn_link' href="javascript:void(0)" onclick="selectCustomPage('{%row['fdId']%}','{%row['fdName']%}')"><bean:message bundle="sys-portal" key="sysPortal.select"/></a>
							$}
						</list:col-html>
					</list:colTable>
				</list:listview>
				<list:paging></list:paging>
			</td>
		</tr>
	</table>

		<script>
			
			function selectCustomPage(rid,rname){
				var data = {
						"fdId":rid,
						"fdName":rname
				}
				window.parent.selectSysData(data);
				window.$dialog.hide(data);
			}
		</script>
	</template:replace>
</template:include>
