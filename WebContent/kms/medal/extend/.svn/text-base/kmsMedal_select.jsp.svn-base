<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script>
			seajs.use(["theme!list", "sys/profile/resource/css/operations.css#"]);
		</script>
		<div style="padding: 8px;">
			<input name="refreshNum" value="0" type="hidden" />
			<!-- 查询条件  -->
			<list:criteria id="criteria1" cfg-isSetToHash="false">
				<list:cri-ref key="fdName" ref="criterion.sys.docSubject" />
				<list:cri-ref ref="criterion.sys.simpleCategory" key="docCategory"
					multi="false"
					title="${lfn:message('kms-medal:kmsMedalMain.fdCategory')}">
					<list:varParams
						modelName="com.landray.kmss.kms.medal.model.KmsMedalCategory" />
				</list:cri-ref>
				<list:cri-auto multi="false"
					modelName="com.landray.kmss.kms.medal.model.KmsMedalMain"
					property="docCreateTime" />
			</list:criteria>
			<%-- 多选选中组件 --%>
			<div id="selectedBean"
				data-lui-type="lui/selected/multi_selected!Selected"
				style="width: 95%; margin: 10px auto;">
				<%--已经选中的值，待加 
			<script type="text/config">
				{
					values:[{'id':"ss",name:'ssssssssssss'}]
				}
			</script>
			--%>
				<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
					var curr = evt.curr || [];
					var old = evt.old || [];
					//全选按钮选中状态设为false
					if(curr.length <= 0 && old.length > 0){
						var tongleInput = $("[name='List_Tongle']");
						if(tongleInput && tongleInput[0]){
							tongleInput[0].checked = false;
						}
					}
					refreshCheckbox();
				</script>
			</div>
			<!-- 列表工具栏 -->
			<div class="lui_list_operation">
				<c:if test="${param.mulSelect}">
					<div class="lui_list_operation_order_btn">
						<list:selectall></list:selectall>
					</div>
				</c:if>
				<c:if test="${param.mulSelect}">
					<div class="lui_list_operation_line"></div>
				</c:if>
				<div class="lui_list_operation_sort_btn">
					<div class="lui_list_operation_sort_toolbar">
						<ui:toolbar layout="sys.ui.toolbar.sort">
							<list:sort property="docCreateTime"
								text="${lfn:message('kms-medal:kmsMedalMain.docCreateTime') }"
								group="sort.list" value="down"></list:sort>
						</ui:toolbar>
					</div>
				</div>

				<div class="lui_list_operation_page_top">
					<list:paging layout="sys.ui.paging.top">
					</list:paging>
				</div>
			</div>

			<ui:fixed elem=".lui_list_operation"></ui:fixed>


			<list:listview id="listview">
				<ui:source type="AjaxJson">
				{
						url:'/kms/medal/kms_medal_main/kmsMedalMain.do?method=data&q.medalType=true&orderby=docCreateTime'
				}
			</ui:source>
				<!-- 列表视图 -->
				<list:colTable isDefault="false" id="subjectCol" name="columntable" onRowClick="if(!${param.mulSelect }) return;selectLink('!{fdId}','!{fdName}')">

					<list:col-checkbox></list:col-checkbox>
					<list:col-serial></list:col-serial>
					<list:col-html
						title="${ lfn:message('kms-medal:kmsMedalMain.fdName')}"
						headerStyle="width:35%">
								{$
									{%row['icon']%}
									<span class="com_subject">{%row['fdName']%}</span>
								$}
							</list:col-html>
					<list:col-auto props="fdCategory.fdName"></list:col-auto>
					<list:col-auto props="docCreateTime;docCreatorName"></list:col-auto>

				</list:colTable>
				<ui:event topic="list.loaded" args="vt">
						$("[name='List_Tongle']").css("display","none");				
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
						LUI.$('.lui_listview_selectall input').bind('click', function() {
							$("[name='toggle']").val("1"); //设置flag值是因为removeValAll()会触发refreshCheckbox(),这样会导致全选框按钮被提前设置false，数据紊乱
						
							if (this.checked) {
								//不能直接用该方法原因为name和fdName的名字不同
								for (var i = 0; i < datas.length; i ++) {
									selectLinkWithClickAll(datas[i].fdId, datas[i].fdName);
								}
								
							} else {
								LUI('selectedBean').removeValAll();
							}
						}); 
						
						//绑定每一项列表点击事件
						LUI.$('#listview .lui_listview_columntable_table tr').bind('click', function() { 
							//判断是否单选操作
							if(!${param.mulSelect}){
								$(this).find("input[name='List_Selected']").each(function(){ 
									var val = getVal($(this).val());
									if (val != null){
										if(checkIfExistOtherIdsWithSingleSelect(val.fdId)){
											return;
										}
									}																
									if ($(this).is(':checked')) {
										this.checked = false;
										LUI('selectedBean').removeValAll();
									} else {
										this.checked = true;
										var values = LUI('selectedBean').getValues();
										if(values.length > 0){
											LUI('selectedBean').removeValAll();
										}										
										if (val != null)
											selectLink(val.fdId, val.fdName);
									}	
								});
							}

						});
						
						LUI.$('#listview [name="List_Selected"]').bind('click', function() { //点击列表某一项的checkbox
							if (!this.checked) {
								LUI('selectedBean').removeVal(this.value); 
							} else {
								var values = LUI('selectedBean').getValues();
								if(values.length > 0 && !${param.mulSelect}){
									LUI('selectedBean').removeValAll();
								}
								var val = getVal(this.value);
								if (val != null){
									selectLink(val.fdId, val.fdName);
								}
							}
						});
						
						$('#listview [name="List_Selected"]').each(function(){
							var val = getVal(this.value);
							var selectedIds = "${JsParam.fdSelectedIds}";
							if(selectedIds.indexOf(this.value) > -1){
									selectLink(val.fdId, val.fdName);
							}
						});
						
						//首次刷新列表加载链接带入数据
						var refreshNum = $("[name='refreshNum']").val(); //此处放在列表刷新而不放在	$(function(){})中是因为selectedBean在$(function(){})运行时还没有加载出来
						if(refreshNum.indexOf("0")>-1){
 	 		 				var fdId = top.window.selectMedalId;
	 			 			var docSubject = top.window.selectMedalSubject;
	 			 			if(fdId){
	 			 				var id = fdId.split(";");
	 			 				var name = docSubject.split(";");
	 			 				
	 			 				
	 			 				for(var i=0;i<id.length;i++){
	 			 					selectLink(id[i],name[i]);
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
					var exists = false;
					for (var i = 0; i < vals.length; i ++) {
						if (vals[i].id == this.value) {
							exists = true;
							break;
						}
					}
					if(exists){
						this.checked = true;
					}else{
						var existOthers = false;
						if(top.window.otherMedalIds){
							var otherMedalIds = top.window.otherMedalIds.split(";");
							for (var i = 0; i < otherMedalIds.length; i ++) {
								if (otherMedalIds[i] == this.value) {
									existOthers = true;
									break;
								}
							}
						}
						if(existOthers){
							this.disabled = true;
							this.checked = true;
							if($(this).parent() && $(this).parent()[0] && $(this).parent()[0].style){
								$(this).parent().parent()[0].style.cursor = "auto";		
							}									
							$(this).parent().click(function(){ 	
								
							});	
						}else{
							if (this.checked)
								this.checked = false;
						}												
					}
				});
			}
	 				
	 				var contains = function(arr, item) {
	 					for(var i = 0; i < arr.length; i++) {
	 						if(item.value == arr[i].fdId) {
	 							return arr[i];
	 						}
	 					}
	 				}
	 				
	 				window.onSubmit =  function() {
	 					
	 					var values = LUI('selectedBean').getValues();
						if(values.length>1 && !"${param.mulSelect}"){
							dialog.alert("只能选择一个");
							return false;
	 					}else if(values.length<1){
							dialog.alert("至少选择一个");
							return false;
	 					}
	 					var rtn = [];
	 					
	 					//拼装docSubject和fdId
	 					for(var i = 0; i < values.length; i++) {
		 					var data = {fdId:"",docSubject:""};
	 						data.fdId = values[i].id;
	 						data.docSubject = values[i].name;
	 						rtn.push(data);
	 					}
	 					
	 					if(rtn.length>0){ 
	 						return rtn;
	 					}
	 				}
	 				
	 				<%-- 设置选中 --%>
	 				window.selectLink = function(id, name) {
	 					var data = {
 							"id":id,
 							"name":name
 						
 						};
	 					if (LUI('selectedBean').hasVal(data)) {
	 						LUI('selectedBean').removeVal(data);
	 						return;
	 					}
	 					var values = LUI('selectedBean').getValues();
						if(values.length > 1 && !"${param.mulSelect}"){
							dialog.alert("只能选择一个");
							refreshCheckbox();
							return ;
						}

	 					LUI('selectedBean').addVal(data);
						refreshCheckbox();

	 				}
	 				
	 				window.selectLinkWithClickAll = function(id, name) {
	 					var data = {
	 						"id":id,
	 						"name":name
	 					};
	 					if (LUI('selectedBean').hasVal(data)) {
	 						return;
	 					}
	 					var values = LUI('selectedBean').getValues();
	 					if(values.length > 1 && !"${param.mulSelect}"){
	 						dialog.alert("只能选择一个");
	 						refreshCheckbox();
	 						return ;
	 					}
	 					LUI('selectedBean').addVal(data);
	 					refreshCheckbox();
	 				}
	 				
	 				
	 				//检测单选情况下是否存在其他不能选择的选项
	 				window.checkIfExistOtherIdsWithSingleSelect = function(selectdId){
						if(top.window.otherMedalIds){
							var otherMedalIds = top.window.otherMedalIds.split(";");
							for (var i = 0; i < otherMedalIds.length; i ++) {
								if (otherMedalIds[i] == selectdId) {
									return true;
								}
							}
						}
						return false;
	 				}

	 			});
	 	</script>
	</template:replace>
</template:include>
