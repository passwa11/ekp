<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script>
			seajs.use(["theme!list", "sys/profile/resource/css/operations.css#"]);
		</script>
		<div style="padding:8px;">
		<input name="refreshNum" value="0" type="hidden"/>
		<input name="toggle" value="0" type="hidden"/>
		
		<!-- 查询条件  -->
		<list:criteria id="criteria1" cfg-isSetToHash="false">
			<list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-property:sysPropertyCategory.fdName')}"/>
		</list:criteria>
		 <%-- 多选选中组件 --%>
		<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="width: 95%;margin: 10px auto;">
			<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
			refreshCheckbox();
		</script>
		</div>
		<!-- 列表工具栏 -->
		<div class="lui_list_operation">
			<%--排序按钮  --%>
			<input type="hidden" name="fdCategoryId" value="${JsParam.fdCategoryId}"/>
			<div style="float:left;">
				<list:paging layout="sys.ui.paging.top">		
				</list:paging>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
	 	<list:listview id="listview">
	 		<ui:source type="AjaxJson">
					{
						url:'/sys/property/sys_property_filter_setting/sysPropertyFilterSetting.do?method=data&defineIds=${JsParam.defineIds}&choiceFilterSign=1'
					}
			</ui:source>
			
			<!-- 列表视图 -->
			<list:colTable isDefault="false"
				id="subjectCol"
				onRowClick="selectLink('!{fdId}','!{fdName}')"
			   name="columntable">				
			<list:col-checkbox headerStyle="width:1%"/>
			<list:col-serial headerStyle="width:5%"/>
			<list:col-auto props="fdName"></list:col-auto>
				
			</list:colTable>
				<ui:event topic="list.loaded" args="vt">
				
					var mulSelect = ${JsParam.mulSelect}
					if(!mulSelect){
						$("[name='List_Tongle']").css("display","none");
					}

					refreshCheckbox();
						
					//每一行的数据
					var datas = vt.table.kvData;
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
							$("[name='toggle']").val("1"); //设置flag值是因为removeValAll()会触发refreshCheckbox(),这样会导致全选框按钮被提前设置false，数据紊乱
							LUI('selectedBean').removeValAll();
							if (this.checked) { 
								//LUI('selectedBean').addValAll(datas);
								//不能直接用该方法原因为name和fdName的名字不同
								for (var i = 0; i < datas.length; i ++) {
										selectLink(datas[i].fdId,datas[i].fdName);
								}
							} else {
								
							}
							$("[name='toggle']").val("0");
						});
						
						
						LUI.$('#listview [name="List_Selected"]').bind('click', function() { //点击列表某一项的checkbox
						
							if (!this.checked) {
								LUI('selectedBean').removeVal(this.value);
							} else {
								var values = LUI('selectedBean').getValues();
								if(values.length>0&&!${JsParam.mulSelect }){
									LUI('selectedBean').removeValAll();
								}
								var val = getVal(this.value);
								if (val != null)
									selectLink(val.fdId,val.fdName);
							}
						});
						
						//首次刷新列表加载链接带入数据
						var refreshNum = $("[name='refreshNum']").val(); //此处放在列表刷新而不放在	$(function(){})中是因为selectedBean在$(function(){})运行时还没有加载出来
						if(refreshNum.indexOf("0")>-1){
		 			 		var fdIds = top.fdFilterSettingIds;
		 			 		var fdNames = top.fdFilterSettingNames;
		 			 		if(fdIds){
		 			 			fdIds = fdIds.substring(0,fdIds.length-1);
		 			 			fdNames = fdNames.substring(0,fdNames.length-1);
		 			 			var fdId = fdIds.split(";");
		 			 			var fdName = fdNames.split(";");
			 			 		for(var i= 0;i<fdId.length;i++){
									selectLink(fdId[i],fdName[i]);
								}
		 			 		}
		 		 			$("[name='refreshNum']").val("1");
						}
				</ui:event>
		</list:listview> 
		 
	 	<list:paging></list:paging>	 
	 	</div>
	 	<script>
	 	seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
	 		window.refreshCheckbox = function() {
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
					var box_len = $("input[name='List_Selected']:checked").length;
					if(box_len>7){
					
						$('#listview [name="List_Tongle"]').attr('checked', true);

					}else{
						var toggle = $("[name='toggle']").val();
						if($('#listview [name="List_Tongle"]').is(':checked')&&(toggle.indexOf("0")>-1)){
							$('#listview [name="List_Tongle"]').attr('checked', false);
						}
					}
				}
	 				
	 				
	 				window.onSubmit =  function() {
						var mulSelect = ${JsParam.mulSelect} ;
	 					var values = LUI('selectedBean').getValues();
	 					var rtn = [];
	 					//拼装fdName和fdId
	 					for(var i = 0; i < values.length; i++) {
		 					var data = {fdId:"",fdName:""}; //不能放在括号外面，因为这样会造成每次data新值都会修改rtn里面的data
	 						data.fdId = values[i].id;
	 						data.fdName = values[i].name;
	 						
	 						rtn.push(data);
	 					}
	 					if(rtn.length>=0){
	 						return rtn;
	 					}
	 				}
	 				
	 				<%-- 设置选中 --%>
	 				window.selectLink = function(id,fdName) {
	 					var data = {
	 							"id":id,
	 							"name":fdName
	 					};
	 					if (LUI('selectedBean').hasVal(data)) {
	 						LUI('selectedBean').removeVal(data);
							refreshCheckbox();
	 						return;
	 					}
	 					
						var mulSelect = ${JsParam.mulSelect }
	 					var values = LUI('selectedBean').getValues();
						if(values.length>0&&!mulSelect){
							refreshCheckbox();
							return ;
						}
						
	 					LUI('selectedBean').addVal(data);
						refreshCheckbox();
	 				}
	 			});
	 	
	 	</script>
	</template:replace>
</template:include>