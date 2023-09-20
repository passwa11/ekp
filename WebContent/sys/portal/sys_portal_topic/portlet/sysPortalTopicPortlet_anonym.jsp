<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('sys-portal:sysPortalTopic.choseTopic')}</template:replace>
	<template:replace name="body">
	<div style="width: 95%;margin: 10px auto;">
					<div data-lui-type="lui/search_box!SearchBox">
						<script type="text/config">
						{
							placeholder: "${lfn:message('sys-ui:ui.criteria.search')}",
							width: '90%'
						}
					</script>
						<ui:event event="search.changed" args="evt">
							LUI('listview').tableRefresh({
								criterions:[{key:"key", value: [evt.searchText]}]
							});
						</ui:event>
					</div>
		</div>
	<%-- 多选选中组件 --%>
	<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="width: 95%;margin: 10px auto;">
		<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
			refreshCheckbox();
		</script>
	</div>
	<script>

		<%-- 刷新checkbox状态 --%>
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
		function submitSelected() {
			var values = LUI('selectedBean').getValues();
			//拼装fdName和fdId
			var idsTmpArray = [];
			var namesTmpArray = [];
			for(var i = 0; i < values.length; i++) {
				if(values[i].id){
					idsTmpArray.push(values[i].id);
					namesTmpArray.push(values[i].name);
				}
			}
			var fdId = idsTmpArray.join(",");
			var fdName = namesTmpArray.join(",");
			window.$dialog.hide({
				"fdId":fdId, 
				"fdName":fdName
			});
			
		}
		<%-- 设置选中 --%>
		function selectLink(id, name) {
			var data = {
					"id":id,
					"name":name
			};
			if (LUI('selectedBean').hasVal(data)) {
				LUI('selectedBean').removeVal(data);
				return;
			}
			LUI('selectedBean').addVal(data);
		}
		LUI.ready(function(){
			var fdIds = '${param.fdIds}';
			var fdNames = '${param.fdNames}';
			fdNames = decodeURIComponent(fdNames);
			var ids = fdIds.split(',');
			var names = fdNames.split(',');
			for(var idx in ids){
				var data = {
					"id":ids[idx],
					"name":names[idx]
				};
				LUI('selectedBean').addVal(data);
			}
		});
	</script>
	
	<table class="tb_normal" style="margin:10px auto;width:95%;height:460px;">
		<tr>
			<td valign="top">
				<list:listview id="listview">
					<ui:source type="AjaxJson">
						{"url":"/sys/portal/sys_portal_topic/portlet/sysPortalTopicPortlet.do?method=list&orderby=docAlterTime&ordertype=down&fdAnonymous=1"}
					</ui:source>
					<list:colTable sort="false" onRowClick="selectLink('!{fdId}','!{name}')">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial style="5%;"></list:col-serial>
						<list:col-html title="${lfn:message('sys-portal:sysPortalTopic.subject')}" style="width:35%;">
							{$<div style="overflow: hidden;text-overflow: ellipsis;">{%row['name']%}</div>$}
						</list:col-html>
						<list:col-html title="${lfn:message('sys-portal:sysPortalTopic.link')}" style="width:35%;">
							{$<div style="overflow: hidden;text-overflow: ellipsis;">{%row['fdTopUrl']%}</div>$}
						</list:col-html>
						<list:col-html title="${lfn:message('sys-portal:sysPortalTopic.updateDate')}" style="width:18%;">
							{$ {%row['docAlterTime']%}  $}
						</list:col-html>
					</list:colTable>
					<%-- 列表加载后更新状态和绑定事件 --%>
					<ui:event event="load" args="evt">
						refreshCheckbox();
						//每一行的数据
						var datas = evt.table.kvData;
						function getVal(id) {
							for (var i = 0; i < datas.length; i ++) {
								if (datas[i].fdId == id) {
									return datas[i];
								}
							}
							return null;
						}
						//全选
						LUI.$('#listview [name="List_Tongle"]').bind('click', function() {
							if (this.checked) {
								LUI('selectedBean').addValAll(datas);
							} else {
								LUI('selectedBean').removeValAll();
							}
						});
						LUI.$('#listview [name="List_Selected"]').bind('click', function() {
							if (!this.checked) {
								LUI('selectedBean').removeVal(this.value);
							} else {
								var val = getVal(this.value);
								if (val != null)
									selectLink(val.fdId, val.name);
							}
						});
						
						$('#listview [name="List_Selected"]').each(function(){
							var val = getVal(this.value);
							var selectedIds = "${JsParam.fdSelectedIds}";
							if(selectedIds.indexOf(this.value) > -1){
								selectLink(val.fdId, val.name);
							}
						});
					</ui:event>
				</list:listview>
				<list:paging></list:paging>
			</td>
		</tr>
	</table>
	
	<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom: 5px;right: 5px;" class="lui_dialog_common_buttons clearfloat">
		<ui:button onclick="submitSelected();" text="${lfn:message('button.ok')}" />
	</div>
	
	</template:replace>
</template:include>