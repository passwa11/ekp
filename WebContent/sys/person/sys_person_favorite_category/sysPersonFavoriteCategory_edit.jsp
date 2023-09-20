<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.*" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.cfg">

	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/person/sys_person_favorite_category/resource/css/edit.css?s_cache=${MUI_Cache}"></link>
	</template:replace>

	<template:replace name="content">
	
		<ui:tabpanel layout="sys.ui.tabpanel.list">
		<ui:content title="${lfn:message('sys-person:favorite.setting') }">
		<ui:toolbar layout="sys.ui.toolbar.default" style="float:right;" count="5">
			<ui:button onclick="AddNewRow();" title="${lfn:message('sys-person:favorite.setting.addnew')}" text="${lfn:message('sys-person:favorite.setting.addnew')}" />
			<ui:button onclick="DeleteCheckedRow();" title="${lfn:message('button.deleteall') }" text="${lfn:message('button.deleteall') }" />
		</ui:toolbar>

		<html:form action="/sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=update">
		<script type="text/template" id="categoryTableRowTemplate">
			<tr KMSS_IsContentRow="1" style="cursor: pointer;" class="categoryTableRow" data-model-name="!{modelName}">
				<td align="center">
					<input type="checkbox" class="categoryTableRowInput" data-name="categories[!{idx}].selected" name="categories[!{index}].selected" value="1">
					<input type="hidden" class="categoryTableRowInput" data-name="categories[!{idx}].fdId" name="categories[!{index}].fdId" value="">
				</td>
				<td style="text-align: center;">
					<input type="hidden" name="categories[!{index}].fdCategoryModel" class="categoryTableRowInput" data-name="categories[!{idx}].fdCategoryModel"
						value="!{model}">
					!{modelName}
				</td>
				<td name="categories[!{index}].selectedCategories" style="cursor: default;" 
					class="categoryTableRowInput" data-name="categories[!{idx}].selectedCategories" >
					
				</td>
				<td style="cursor: default; text-align: center;">
					<div class="btn btnSetting" onclick="OnSelectClicked(this);" data-index="!{index}">
					  	设置分类项
						<input name="categories[!{index}].fdCategoryIds" value="!{categoryIds}" type="hidden"
							class="categoryTableRowInput" data-name="categories[!{idx}].fdCategoryIds" />
					  	<input name="categories[!{index}].fdCategoryNames" value="!{categoryNames}" type="hidden"
					  		class="categoryTableRowInput" data-name="categories[!{idx}].fdCategoryNames" />
					</div>
					<div class="btn btnDelete" onclick="OnDeleteRow(this);">删除</div>
				</td>
			</tr>
		</script>
		<table id="categoryTable" class="tb_normal " width="100%">
			<tr class="tr_normal_title">
				<td width="5%">
					<input type="checkbox" name="all">
				</td>
				<td width="20%" style="text-align: center;"><bean:message key="sysPerson.config.module" bundle="sys-person"/></td>
				<td width="55%" style="text-align: center;"><bean:message key="favorite.setting.selected" bundle="sys-person"/></td>
				<td width="20%" style="text-align: center;"><bean:message key="favorite.setting.operate" bundle="sys-person"/></td>
			</tr>

			<c:forEach items="${sysPersonFavoriteCategoryAllForm.categories}" var="category" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" style="cursor: pointer;" class="categoryTableRow" data-model-name="${modelNameMap[category.fdCategoryModel] }">
				<td align="center">
					<input type="checkbox" class="categoryTableRowInput" data-name="categories[!{idx}].selected" name="categories[${vstatus.index}].selected" value="1">
					<input type="hidden" class="categoryTableRowInput" data-name="categories[!{idx}].fdId" name="categories[${vstatus.index}].fdId" value="${category.fdId }">
				</td>
				<td style="text-align: center;">
					<input type="hidden" name="categories[${vstatus.index}].fdCategoryModel" class="categoryTableRowInput" data-name="categories[!{idx}].fdCategoryModel"
						value="${category.fdCategoryModel}">
					${modelNameMap[category.fdCategoryModel] }
				</td>
				<td name="categories[${vstatus.index}].selectedCategories" style="cursor: default;" 
					class="categoryTableRowInput" data-name="categories[!{idx}].selectedCategories" >
					<c:if test="${category.fdCategoryNames != '' }">
						<c:forEach items="${category.fdCategoryNames.split(';') }" var="selectedCategory">
							<span class="selectedCategory">${selectedCategory }</span>
						</c:forEach>
					</c:if>
				</td>
				<td style="cursor: default; text-align: center;">
					<div class="btn btnSetting" onclick="OnSelectClicked(this);" data-index="${vstatus.index}">
					  	<bean:message key="favorite.setting.item" bundle="sys-person"/>
						<input name="categories[${vstatus.index}].fdCategoryIds" value="${category.fdCategoryIds }" type="hidden"
							class="categoryTableRowInput" data-name="categories[!{idx}].fdCategoryIds" />
					  	<input name="categories[${vstatus.index}].fdCategoryNames" value="${category.fdCategoryNames }" type="hidden"
					  		class="categoryTableRowInput" data-name="categories[!{idx}].fdCategoryNames" />
					</div>
					<div class="btn btnDelete" onclick="OnDeleteRow(this);"><bean:message key="favorite.setting.delete" bundle="sys-person"/></div>
				</td>
			</tr>
			</c:forEach>
		</table>
		</html:form>
		</ui:content>
		</ui:tabpanel>
		
		
		<script>Com_IncludeFile('jquery.js');</script>
		<script>Com_IncludeFile('doclist.js|validator.jsp|validation.js|plugin.js|validation.jsp');</script>
		<script>DocList_Info.push('categoryTable');</script>
		<script>

		var valid = $KMSSValidation(document.forms['sysPersonFavoriteCategoryAllForm']);
		
		$(document).ready(function() {
			$('#categoryTable').delegate("tr[class!='tr_normal_title']", "click", function(event) {
				var row = this;
				if (event.target.tagName == 'TD') {
					var cell = event.target;
					if (cell.cellIndex < 2) {
						var ckb = $(row).find('[name$="selected"]')[0];
						ckb.checked = !ckb.checked;
						FixSelectedCheckbox();
					}
				}
			});
			$('#categoryTable').delegate("[name='all']", "click", function(event) {
				var selected = this.checked;
				$('#categoryTable [name$="selected"]').each(function() {
					this.checked = selected;
				});
			});
			
			$('#categoryTable').delegate('[name$="fdCategoryModel"]', 'change', function(event) {
				var tr = $(event.target).closest('tr');
				tr.find('[name$="fdCategoryIds"], [name$="fdCategoryNames"]').val("");
			});
		});
		function CheckSelectedItems() {
			if ($('#categoryTable [name$="selected"]:checked').length == 0) {
				seajs.use(["lui/dialog"], function(dialog) {
					dialog.alert(window.placeSelectOptionDatas ? placeSelectOptionDatas : "${lfn:escapeJs(lfn:message('page.noSelect'))}");
				});
				return false;
			}
			return true;
		}
		function FixSelectedCheckbox() {
			var all = $('#categoryTable [name="all"]')[0];
			var $items = $('#categoryTable [name$="selected"]');			
			var allchecked =$items.length<=0 ? false : true;
			$items.each(function() {
				if (!this.checked) {
					allchecked = false;
					return false;
				}
			});
			all.checked = allchecked;
		}
		function SubmitCategoryForm() {
			//var form = document.forms['sysPersonFavoriteCategoryAllForm'];
			//form.submit();
			if (!valid.validate()) {
				return;
			}
			seajs.use(["lui/dialog"], function(dialog) {
				var loading = dialog.loading('');
				$.ajax({
					  type: "POST",
					  url: $('form[name="sysPersonFavoriteCategoryAllForm"]').attr('action'),
					  data: $('form[name="sysPersonFavoriteCategoryAllForm"]').serialize(),
					  dataType: 'json',
					  success: function(data, textStatus, jqXHR) {
						  loading.hide();
						  dialog.success(data.title);
					  },
					  error: function(jqXHR, textStatus, errorThrown) {
						  loading.hide();
						  var msg = result.responseJSON || {title:''};
						  dialog.failure(msg.title);
					  }
				});
			});
		}
		
		/** 重新排序列表 **/
		function ResortRow() {
			
			$('.categoryTableRow').each(function(idx){
				
				$(this).find('.categoryTableRowInput').each(function(){
					$(this).attr('name', $(this).attr('data-name').replace(/\!\{idx\}/g, idx));
				});
				
				$(this).find('.btnSetting').each(function(){
					$(this).attr('data-index', idx);
				});
				
				
			});
			
		}
		
		/** 新增列表 **/
		function AddNewRow(){
			
			ResortRow();
			
			seajs.use(['lui/dialog', 'lui/dragdrop'], function(dialog, dragdrop) {
				
				var size = dialog.getSizeForNewFile()
				var w = size.width, h = size.height;
				
				var buttons = [ {
					name : '<bean:message key="button.ok"/>',
					styleClass: 'lui_toolbar_btn_def lui_toolbar_btn_check',
					fn : function(value, d) {
						
						try {
							var ifm = $(d.element.find('iframe').get(0).contentDocument).find('#categories');
							
							var model = ifm.attr('data-model');
							var modelName = ifm.attr('data-model-name');
							var t = ifm.get(0).contentWindow.urlParams;
							
							d.hide({
								check: true,
								params: {
									model: model,
									modelName: modelName,
									ids: t[0],
									names: t[1]
								}
							});
						} catch(err){
							//dialog["failure"]('请选择关注的分类', $('body'));
							d.hide({
								check: true,
								params: {
									model: model,
									modelName: modelName,
									ids: '',
									names: ''
								}
							});
						}
						
					}
				}, {
					name: '<bean:message key="button.back"/>',
					styleClass: 'lui_toolbar_btn_def lui_toolbar_btn_cancel lui_toolbar_btn_back',
					fn: function(value, dialog) {
					}
				}, {
					name: '<bean:message key="button.cancel"/>',
					styleClass: 'lui_toolbar_btn_def lui_toolbar_btn_cancel lui_toolbar_btn_close',
					fn: function(value, dialog) {
						dialog.hide({
							check: false
						});
					}
				}];
				
				var selectedModels = [];
				$('.categoryTableRow').each(function(){
					selectedModels.push(encodeURI($(this).attr('data-model-name') || ''));					
				});
				
				var d = new dialog.build({
					config : {
						width : w,
						height : h,
						lock : true,
						cache : false,
						title : '<bean:message key="favorite.setting.addnew" bundle="sys-person"/>',
						scroll : true,
						close : true,
						content : {
							id : 'dialog_category',
							scroll : true,
							type : "iframe",
							url : '/sys/person/sys_person_favorite_category/sysPersonFavoriteCategory_new.jsp?selectedModels=' + selectedModels.join(';'),
							buttons : buttons
						}
					},
					callback : function(res){
						if(!res || !res.check){
							return;
						}
						
						var idx = $('.categoryTableRow').get().length;
						
						var tpl = $('#categoryTableRowTemplate').html();
						tpl = tpl.replace(/\!\{index\}/g, idx);
						tpl = tpl.replace(/\!\{categoryIds\}/g, res.params.ids || '');
						tpl = tpl.replace(/\!\{categoryNames\}/g, res.params.names || '');
						tpl = tpl.replace(/\!\{model\}/g, res.params.model);
						tpl = tpl.replace(/\!\{modelName\}/g, res.params.modelName);
						
						var newRow = $(tpl).appendTo($('#categoryTable'));
						var td = newRow.find('[name="categories[' + idx + '].selectedCategories"]');
						$.each((res.params.names || '').split(';'), function(i, n){
							if(!n){
								return;
							}
							$('<span class="selectedCategory">' + n + '</span>').appendTo(td);
						});
						
						SubmitCategoryForm();
						
					}
				});
				
				d.on('layoutDone', function() {
					new dragdrop.Draggable({
						handle : d.head,
						element : d.element,
						isDrop : false,
						isClone : false
					});
				});
				
				d.show();
			});
			
		}
		
		function OnDeleteRow(sel){
			
			seajs.use(["lui/dialog"], function(dialog) {
				dialog.confirm("${lfn:escapeJs(lfn:message('page.comfirmDelete'))}", function(rtn) {
					if (!rtn)
						return;
                    setTimeout(function(){ // 此处添加setTimeOut是为兼容Edge浏览器（#79670）
						$($(sel).closest('tr')[0]).remove();
					    ResortRow();
					    SubmitCategoryForm();
					    FixSelectedCheckbox();
					},10);
				});
			});
			
		}
		
		function DeleteCheckedRow() {
			if (!CheckSelectedItems()) {
				return;
			}
			seajs.use(["lui/dialog"], function(dialog) {
				dialog.confirm("${lfn:escapeJs(lfn:message('page.comfirmDelete'))}", function(rtn) {
					if (!rtn)
						return;
					setTimeout(function(){ // 此处添加setTimeOut是为兼容Edge浏览器（#79670）
						$('#categoryTable [name$="selected"]:checked').each(function() {
							$($(this).closest('tr')[0]).remove();
						});
						ResortRow();
						SubmitCategoryForm();
						FixSelectedCheckbox();
					},10);
				});
			});
		}
		
		window.OnSelectChanged = function(idx, params) {
			
			if(!params){
				return;
			}
			
			console.log(params);
			
			var selectedCategories = $('[name="categories[' + idx + '].selectedCategories"]');
			selectedCategories.html('');
			$.each((params.name || '').split(';'), function(i, n){
				if(!n){
					return;
				}
				$('<span class="selectedCategory">' + n + '</span>').appendTo(selectedCategories);		
			});
			
			SubmitCategoryForm();
		}
		
		function OnSelectClicked(sel) {
			
			var idx = $(sel).attr('data-index');
			
			var ms = categoryModels;
			var select = $(sel).closest('tr').find('[name$="fdCategoryModel"]');
			var modelName = select.val();
			var name = select[0].name;
			var namePre = name.substring(0, name.lastIndexOf('.'));
			for (var i = 0; i < ms.length; i ++) {
				var m = ms[i];
				if (m.model == modelName) {
					var dialogjs = m.dialogJS || '';
					
					if (dialogjs == null) {
						break;
					}
					
					// 以下model需有新建权限
					var authType = 0;
					switch(modelName) {
						case 'com.landray.kmss.km.imissive.model.KmImissiveSendTemplate': 
						case 'com.landray.kmss.km.imissive.model.KmImissiveReceiveTemplate':
						case 'com.landray.kmss.km.imissive.model.KmImissiveSignTemplate':
						case 'com.landray.kmss.km.review.model.KmReviewTemplate':
							authType = 2;
							break;
						default: 
							authType = 0; 
							break;	
					}
					
					var opt = {
					  modelName: modelName,
					  idField: namePre + '.fdCategoryIds',
					  nameField: namePre + '.fdCategoryNames',
					  mulSelect: true,
					  authType: authType,
					  noFavorite: true,
					  notNull: false,
					  action: function(params) {
						  OnSelectChanged(idx, params);
					  }
					};

					if (/dialog\.simpleCategory/.test(dialogjs)) {
					  seajs.use(['lui/dialog'], function(dialog) {
					    dialog.simpleCategory(opt);
					  });
					} else if (/dialog\.category/.test(dialogjs)) {
					  seajs.use(['lui/dialog'], function(dialog) {
					    dialog.category(opt);
					  });
					}

					break;
				}
			}
		}
		</script>
		<script>
			var categoryModels = <%=FavoriteCategoryHelp.buildCategoryJson() %>;
		</script>
	</template:replace>
</template:include>