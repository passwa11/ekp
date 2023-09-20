<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">${ lfn:message('sys-mportal:sysMportalCard.select') }</template:replace>
	<template:replace name="body">
		<script>
			seajs.use(['theme!form']);
			LUI.ready(function(){
				LUI.$("#searchInput").keyup(function(e){
					var code = e.keyCode || e.which;
					if(code == 13) { 
						search(LUI.$(this).val());
					}
				});
			});
			function search(val){				 
				seajs.use(['lui/topic'],function(topic){
					//var evt = {"a":"a"};
					var topicEvent = {
							criterions : [],
							query : []
						};
					topicEvent.query.push({"key":"__seq","value":[(new Date()).getTime()]});
					topicEvent.criterions.push({"key":"keyword","value":[val]});
					topic.publish("criteria.changed", topicEvent);				
				});
			}
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
			function selectCard(fdId,fdName){
				var data = {
						"id":fdId,
						"name":fdName
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
		<div style="margin:10px auto;width:95%;">
			<div style="border: 1px #e8e8e8 solid;">
				<div style="width: 98%;margin: 10px auto;">
					<div data-lui-type="lui/search_box!SearchBox">
						<script type="text/config">
						{
						placeholder: "${ lfn:message('sys-mportal:sysMportalPage.cards.msg.search') }",
						width: '90%'
						}
						</script>
						<ui:event event="search.changed" args="evt">
							LUI('sourceList').tableRefresh({
								criterions:[{key:"keyword", value: [evt.searchText]}]});
						</ui:event>
					</div>
				</div>
			</div> 
			<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" 
					style="padding: 10px; border: 1px #e8e8e8 solid;">
				<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
					refreshCheckbox();
				</script>
			</div>
			<div style="border: 1px #e8e8e8 solid;padding: 5px;height:355px;">
				<list:listview id="sourceList">
					<ui:source type="AjaxJson">
						{"url":"/sys/mportal/sys_mportal_main/sysMportalMain.do?method=data&rowsize=7"}
					</ui:source>
					<list:colTable sort="false" onRowClick="selectCard('!{fdId}','!{fdName}')">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial style="width:60px;"></list:col-serial>
						<list:col-auto props="fdName"></list:col-auto>
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
												"name":datas[i].fdName
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
									selectCard(val.fdId, val.fdName);
							}
						});
					</ui:event>
				</list:listview>
				<div style="height: 10px;"></div>
				<list:paging layout="sys.ui.paging.simple" ></list:paging>
			</div>
		</div>
		<div data-lui-mark="dialog.content.buttons" 
			 style="position: fixed;bottom: 5px;right: 10px;" 
			 class="lui_dialog_common_buttons clearfloat">
			<ui:button onclick="submitSelected();" text="${lfn:message('button.ok') }" />
		</div>
	</template:replace>
</template:include>