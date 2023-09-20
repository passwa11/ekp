<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script>
		function select(id,name) {
			var _decodeHTML = function(str){
				if (str==null || str.length == 0)
					return "";
				return str.replace(/&amp;/g, "&")
						.replace(/&lt;/g, "<")
						.replace(/&gt;/g, ">")
						.replace(/&#39;/g,"\'")
						.replace(/&quot;/g, "\"");
		    };
			
			var list = LUI("_menuList").table.kvData;
			var data = {};
			if(list && list.length > 0) {
				for(var i = 0 ; i < list.length ; i++) {
					if(list[i].fdId == id) {
						data = {
							"name" : _decodeHTML(list[i].docSubject),
							"id" : list[i].fdId
						};
					}
				}
			}
			window.$dialog.hide(data);
		}
	</script>
		<div style="margin: 20px auto; width: 95%;">
			<div
				style="border: 1px #e8e8e8 solid; border-top-width: 0px; padding: 5px; height: 430px;">
				<list:listview id="_menuList">
					<ui:source type="AjaxJson">
						{"url":"/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=menus"}
					</ui:source>
					<list:colTable sort="false" layout="sys.ui.listview.listtable"
						onRowClick='select("!{fdId}")'>
						<list:col-serial></list:col-serial>
						<list:col-auto props="docSubject"></list:col-auto>
						<list:col-html title="操作" style="min-width:50px;">
							{$
								<a class='com_btn_link' href="javascript:void(0)"
								onclick="select('{%row['fdId']%}')">
								${ lfn:message('button.select') }</a>
							$}
						</list:col-html>
					</list:colTable>
				</list:listview>
				<div style="height: 10px;"></div>
				<list:paging layout="sys.ui.paging.simple"></list:paging>
			</div>
		</div>
	</template:replace>
</template:include>