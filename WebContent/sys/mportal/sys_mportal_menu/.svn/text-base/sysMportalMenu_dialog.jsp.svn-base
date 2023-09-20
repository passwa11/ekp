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
				<c:if test="${ param.selection_type eq 'single' }">
				   LUI('selectedBean').removeValAll(); // 仅支持单选
				</c:if>
				LUI('selectedBean').addVal(data);
			}
			function submitSelected() {
				window.$dialog.hide(LUI('selectedBean').getValues());
			} 
			seajs.use(['theme!form']);
		</script>
		<table style="width: 95%;margin: 3px auto;" border="0">
			<tr>
				<td width="30%;">
					<div style="text-align: left">
					<select name="categories" onchange="DoCategoryFilter();">
						<option value=""><bean:message key='sysMportal.moudle.select' bundle='sys-mportal'/></option>
						<option value="module"><bean:message key='sysMportal.moudle.homePage' bundle='sys-mportal'/></option>
						<option value="fast"><bean:message key='sysMportal.moudle.quickHandle' bundle='sys-mportal'/></option>
					</select>
					<script>
						function DoCategoryFilter() {
							seajs.use(["lui/jquery"], function($) {
								var val = $('[name="categories"]').val();
								LUI('sourceList').tableRefresh({criterions:[{key:"type", value: [val]}]});
							});
						}
					</script>
					</div>
				</td>
				<td width="*">
				<div style="width: 100%;">
					<div data-lui-type="lui/search_box!SearchBox">
						<script type="text/config">
						{
							placeholder: "${lfn:message('sys-ui:ui.criteria.search')}",
							width: '90%'
						}
					</script>
						<ui:event event="search.changed" args="evt">
							LUI('sourceList').tableRefresh({criterions:[{key:"key", value: [evt.searchText]}]});
						</ui:event>
					</div>
					</div>
				</td>
			</tr>
		</table>
		<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" 
					style="padding:10px 20px;">
				<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
					refreshCheckbox();
				</script>
			</div>
		<div style="margin: 5px auto 20px; width: 95%;">
			<div
				style="border: 1px #e8e8e8 solid; padding: 5px; height: 380px;">
				<list:listview id="sourceList">
					<ui:source type="AjaxJson">
						{"url":"/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=select&rowsize=8"}
					</ui:source>
					<list:colTable sort="false" layout="sys.ui.listview.listtable" onRowClick="select('!{fdId}','!{name}','!{url}')">
					    <c:if test="${ param.selection_type ne 'single' }"><list:col-checkbox></list:col-checkbox></c:if>
						<list:col-serial></list:col-serial>
						<list:col-auto props="name"></list:col-auto>
						<list:col-html title="${lfn:message('sys-mportal:sysMportal.moudle.handle')}">
							{$
								<a class='com_btn_link' href="javascript:void(0)"
								onclick="select('{%row['fdId']%}','{%row['name']%}','{%row['url']%}')">
								${ lfn:message('button.select') }</a>
							$}
						</list:col-html>
					</list:colTable>
					<%-- 列表加载后更新状态和绑定事件 --%>
					<ui:event event="load" args="evt">
						refreshCheckbox();
						var datas = evt.table.kvData;
						if(datas && datas.length){
							for(var i = 0;i < datas.length;i++){
								if(datas[i].url){
									datas[i].url = datas[i].url.replace(/&amp;/g,"\&");
								}
							};
						}
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
												"name":datas[i].name,
												"link":datas[i].url
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
									select(val.fdId, val.name, val.url);
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