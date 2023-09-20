<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">选择引导页</template:replace>
	<template:replace name="body">
	<script>seajs.use(['theme!form']);</script>
	<table class="tb_normal" style="margin:20px auto;width:95%;height:460px;">
		<tr>
			<td valign="top">
				<list:listview>
					<ui:source type="AjaxJson">
						{"url":"/sys/portal/sys_portal_guide/sysPortalGuide.do?method=selectGuide"}
					</ui:source>
					<list:colTable sort="false" onRowClick="selectPortletGuide('!{fdId}','!{fdName}')">
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdName"></list:col-auto>
						<list:col-html title="">
							{$
								<a class='com_btn_link' href="javascript:void(0)" onclick="selectPortletGuide('{%row['fdId']%}','{%row['fdName']%}')">${ lfn:message('sys-portal:desgin.msg.select') }</a>
							$}
						</list:col-html>
					</list:colTable>
				</list:listview>
			</td>
		</tr>
	</table>
	<script>
		seajs.use(['lui/jquery'],function($){
			//
			window.selectPortletGuide = function(rid,rname){
				var data = {
					"ref":rid,
					"name":rname
				};
				window.$dialog.hide(data);
			};
		});
	</script>
	</template:replace>
</template:include>