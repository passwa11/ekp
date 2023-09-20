<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
request.setAttribute("sys.ui.theme", "default");
%>
<template:include ref="default.simple">
	<template:replace name="title">选择表单版本</template:replace>
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
			};
			function selectPage(fdId,fdName){
				var data = {
						"id":fdId,
						"name":fdName
				};
				LUI('selectedBean').removeValAll();
				LUI('selectedBean').addVal(data);
				submitSelected();
			};
			function submitSelected() {
				seajs.use([ 'lui/dialog' ], function(dialog) {
					if(LUI('selectedBean').getValues().length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					if(window.$dialog!=null){
						window.$dialog.hide(LUI('selectedBean').getValues());
					}else{
						dialogReturn(LUI('selectedBean').getValues());
					}
			});
			}
		</script>
		<div style="margin:10px auto 60px;width:93%;">
			<div style="border: 1px #e8e8e8 solid;">
				<div style="width: 98%;margin: 10px auto;">
					<div data-lui-type="lui/search_box!SearchBox">
						<script type="text/config">
						{
						placeholder: "${ lfn:message('sys-print:sysPrintTemplate.select.inputq') }",
						width: '90%'
						}
						</script>
						<ui:event event="search.changed" args="evt">
							LUI('sourceList').tableRefresh({criterions:[{key:"keyword", value: [evt.searchText]}]});
						</ui:event>
					</div>
				</div>
			</div>
			<div style="display: none"> 
				<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="padding: 10px; border: 1px #e8e8e8 solid;">
					<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
					refreshCheckbox();
				</script>
				</div>
			</div>
			<div style="border: 1px #e8e8e8 solid;padding: 5px;height:auto;">
				<list:listview id="sourceList">
					<ui:source type="AjaxJson">
						{"url":"/sys/print/sys_print_template_history/sysPrintTemplateHistory.do?method=listForm&rowsize=8&fdModelName=${JsParam.fdModelName}&fdModelId=${JsParam.fdModelId }&fdKey=${JsParam.fdKey}&fdType=${JsParam.fdType}"}
					</ui:source>
					<list:colTable sort="false" onRowClick="selectPage('!{fdId}','!{fdName}')">
						<list:col-radio></list:col-radio>
						<list:col-serial style="width:60px;"></list:col-serial>
						<list:col-auto props="fdTemplateEdition"></list:col-auto>
						<list:col-auto props="fdAlterorName"></list:col-auto>
						<list:col-auto props="fdAlterTime"></list:col-auto>
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
									for(var i=0;i<datas.length;i++){
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
									selectPage(val.fdId, val.fdName);
							}
						});
					</ui:event>
				</list:listview>
				<div style="height: 10px;"></div>
				<list:paging viewSize="1" style="padding-bottom:17px"></list:paging>
			</div>
		</div>
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;padding-bottom:20px;" class="lui_dialog_common_buttons clearfloat">
			<ui:button onclick="submitSelected();" text="${lfn:message('button.ok') }" />
			<ui:button onclick="window.close();" text="${lfn:message('button.close') }" />
		</div>
	</template:replace>
</template:include>
<script type="text/javascript">
	var dialogRtnValue = null;
	var dialogObject = null;
	var isOpenWindow = true;//弹出形式:弹窗or弹层
	if(window.showModalDialog && window.dialogArguments){
		dialogObject = window.dialogArguments;
	}else if(opener && opener.Com_Parameter.Dialog){
		dialogObject = opener.Com_Parameter.Dialog;
	}else{
		dialogObject = top.Com_Parameter.Dialog;
		isOpenWindow = false;
	}
	if(dialogObject){
		Com_Parameter.XMLDebug = dialogObject.XMLDebug;
		var Data_XMLCatche = dialogObject.XMLCatche;
	}
	//Com_AddEventListener(window, "beforeunload", beforeClose);
	function dialogReturn(value){
		window.dialogRtnValue = value.slice(0);//复制一份新数组,防止window.close时出现无法执行已释放的script代码
		if(isOpenWindow){
			dialogObject.rtnData = dialogRtnValue;
			dialogObject.AfterShow();
			window.close();
		}else if(window.$dialog!=null){
			dialogObject.rtnData = dialogRtnValue;
			dialogObject.AfterShow();
			$dialog.hide();
		}
	}
	function beforeClose(){
		dialogObject.rtnData = dialogRtnValue;
		dialogObject.AfterShow();
	}
	if(dialogObject.winTitle==null)
		dialogObject.winTitle = '选择表单历史版本';
	
	Com_SetWindowTitle(dialogObject.winTitle);
</script>