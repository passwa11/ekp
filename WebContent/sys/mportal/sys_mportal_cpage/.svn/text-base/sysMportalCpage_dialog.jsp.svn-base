<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.mportal.plugin.MportalMportletUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="java.util.List" %>
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
			function search(){	
				seajs.use(["lui/topic"], function(topic) {
					//var evt = {"a":"a"};
					var val = LUI.$("#moduleList").val();
					var keyword = LUI.$("#searchInput :text").val();
					var topicEvent = {
							criterions : [],
							query : []
						};
					
					topicEvent.query.push({"key":"__seq","value":[(new Date()).getTime()]});
					if(val)
						topicEvent.criterions.push({"key":"module","value":[val]});
					if(keyword)
						topicEvent.criterions.push({"key":"keyword","value":[keyword]});
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
			function selectPage(fdId,fdName,fdIcon,fdImg,fdType,temporaryFdId){
				seajs.use(["lui/util/str"], function(str) {
					//对url解码
					fdImg = str.decodeHTML(fdImg);
					var data = {
						"id": fdId,
						"name": fdName,
						"icon": fdIcon,
						"img": fdImg,
						"type": fdType,
						"temporaryFdId": temporaryFdId
					};
					if (LUI('selectedBean').hasVal(data)) {
						LUI('selectedBean').removeVal(data);
						return;
					}
					LUI('selectedBean').addVal(data);
				})
			}
			function submitSelected() {
				window.$dialog.hide(LUI('selectedBean').getValues());
			} 

			
		</script>
		<div style="margin:10px auto;width:95%;">
			<div style="border: 1px #e8e8e8 solid;">
				<table class="tb_noborder" style="width: 100%">
					<tr>
						<td width="100px;" align="center">
							${ lfn:message('sys-mportal:sysMportalPage.cards.msg.keyword') }
						</td>
						<td>
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
						</td>
					</tr>
				</table>
				
			</div> 
			<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" 
					style="padding: 10px; border: 1px #e8e8e8 solid;word-break: break-all;">
				<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
					refreshCheckbox();
				</script>
			</div>
			<div style="border: 1px #e8e8e8 solid;padding: 5px;height:355px;">
				<list:listview id="sourceList">
					<ui:source type="AjaxJson">
						{"url":"/sys/mportal/sys_mportal_cpage/sysMportalCpage.do?method=data&rowsize=8&q.fdEnabled=true"}
					</ui:source>
					<list:colTable sort="false" onRowClick="selectPage('!{fdId}','!{fdName}','!{fdIcon}','!{fdImg}','!{fdType}','!{temporaryFdId}')">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial style="width:60px;"></list:col-serial>
						<list:col-auto props="fdName,fdTypeLang"></list:col-auto>
					</list:colTable>
					<%-- 列表加载后更新状态和绑定事件 --%>
					<ui:event event="load" args="evt">
						refreshCheckbox();
						var datas = evt.table.kvData;
						debugger
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
												"icon":datas[i].fdIcon,
												"img":datas[i].fdImg,
												"type":datas[i].fdType,
												"temporaryFdId": datas[i].temporaryFdId
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
									selectPage(val.fdId, val.fdName, val.fdIcon,val.fdImg, val.fdType, val.temporaryFdId);
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
			 class="lui_dialog_common_buttons">
			<ui:button onclick="submitSelected();" text="${lfn:message('button.ok') }" />
		</div>
	</template:replace>
</template:include>