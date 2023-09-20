<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script>
		function refreshCheckbox() {
			var vals = LUI('selectedBean').getValues();
			LUI.$('[name="List_Selected"]').each(function() {
				for (var i = 0; i < vals.length; i ++) {
					if (vals[i].id == this.value) {
						if (!this.checked)
							this.checked = true;
						return;
					}
				}
				if (this.checked)
					this.checked = false;
			});
		}
		function select(id, name,link){
			var data = {
					"id":id,
					"name":name,
					"link":link
			};
			if (LUI('selectedBean').hasVal(data)) {
				LUI('selectedBean').removeVal(data);
				return;
			}
			LUI('selectedBean').addVal(data);
		}
		function submitSelected() {
			window.$dialog.hide(LUI('selectedBean').getValues());
		} 
	
	</script>
		<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" 
					style="padding: 10px; border: 1px #e8e8e8 solid;">
				<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
					refreshCheckbox();
				</script>
			</div>
		<div style="margin: 20px auto; width: 95%;">
			<div
				style="border: 1px #e8e8e8 solid; border-top-width: 0px; padding: 5px; height: 400px;">
				<list:listview id="sourceList">
					<ui:source type="AjaxJson">
						{"url":"/sys_mportal_menu/sysMportalMenu.do?method=select&rowsize=8"}
					</ui:source>
					<list:colTable sort="false" layout="sys.ui.listview.listtable"
						onRowClick="select('!{fdId}','!{fdName}','!{fdSubDocLink}')">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdName"></list:col-auto>
						<list:col-html title="操作">
							{$
								<a class='com_btn_link' href="javascript:void(0)"
								onclick="select('{%row['fdId']%}','{%row['fdName']%}','{%row['fdSubDocLink']%}')">
								${ lfn:message('button.select') }</a>
							$}
						</list:col-html>
					</list:colTable>
					<%-- 列表加载后更新状态和绑定事件 --%>
					<ui:event event="load" args="evt">
						refreshCheckbox();
						var datas = evt.table.kvData;
						function getVal(id) {
							for (var i = 0; i < datas.length; i ++) {
								if (datas[i].fdId == id) {
									return datas[i];
								}
							}
							return null;
						}
						LUI.$('#sourceList [name="List_Tongle"]').bind('click', function() {
							if (this.checked) {
								var vals = [];
								if(datas != null && datas.length != null){
									for(var i=0;i < datas.length;i++){
										var data = {
												"id":datas[i].fdId,
												"name":datas[i].fdName,
												"link":datas[i].fdSubDocLink
										};
										vals.push(data);
									}
								}
								LUI('selectedBean').addValAll(vals);
							} else {
								LUI('selectedBean').removeValAll();
							}
						});
						LUI.$('#sourceList [name="List_Selected"]').bind('click', function() {
							if (!this.checked) {
								LUI('selectedBean').removeVal(this.value);
							} else {
								var val = getVal(this.value);
								if (val != null)
									select(val.fdId, val.fdName, val.fdSubDocLink);
							}
						});
					</ui:event>
				</list:listview>
				<div style="height: 10px;"></div>
				<list:paging layout="sys.ui.paging.simple"></list:paging>
			</div>
		</div>
		<div data-lui-mark="dialog.content.buttons" 
			 style="position: fixed;bottom: 5px;right: 10px;" 
			 class="lui_dialog_common_buttons">
			<ui:button onclick="submitSelected();" text="${lfn:message('button.ok') }" />
		</div>
	</template:replace>
</template:include>