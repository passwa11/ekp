<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.person.service.plugin.*" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="person.cfg">
	<template:replace name="content">
	<ui:tabpanel layout="sys.ui.tabpanel.list">
	  <ui:content title="${lfn:message('sys-follow:sysFollow.config.category')}">
		<ui:toolbar layout="sys.ui.toolbar.default" style="float:right;" count="5">
			<ui:button onclick="submitCategory();" title="${lfn:message('button.save')}" text="${lfn:message('button.save')}" />
			<ui:button onclick="AddNewRow();" title="${lfn:message('button.create')}" text="${lfn:message('button.create')}" />
			<ui:button onclick="DeleteCheckedRow();" title="${lfn:message('button.deleteall')}" text="${lfn:message('button.deleteall')}" />
			<%-- <ui:button onclick="CategoryRowUp();" title="上移" text="上移" />
			<ui:button onclick="CategoryRowDown();" title="下移" text="下移" /> --%>
		</ui:toolbar>
		<table id="categoryTable" class="tb_normal " width="100%">
			<tr class="tr_normal_title">
				<td width="5%">
					<input type="checkbox" name="all">
				</td>
				<td width=30%>
					${lfn:message('sys-follow:sysFollow.config.module')}
				</td>
				<td width=50%>
					${lfn:message('sys-follow:sysFollow.config.myCategory')}
				</td>
			</tr>
			<!--基准行 -->
			<tr style="display:none;" KMSS_IsReferRow="1">
				<td align="center">
					<input type="checkbox" name="categories[!{index}].selected" value="1">
					<!--  
					<input type="hidden" name="categories[!{index}].fdId" value="">-->
				</td>
				<td>
					<select name="categoryModule[!{index}].fdModule" onchange="FixCategoryModelSelector();">
						<option value="">${lfn:message('page.firstOption')}</option>
					</select>
				</td>
				<td>
					<xform:dialog 
							propertyId="categoryIds[!{index}]" 
							propertyName="categoryNames[!{index}]" 
							style="width:100%"
							textarea="true"
							showStatus="edit"
							validators="required"
							subject="${lfn:message('sys-follow:sysFollow.config.myCategory')}">
						selectCategorys(this);
					</xform:dialog>
				</td>
			</tr>
			<c:forEach items="${rtnCategoryMap}" var="category" varStatus="vstatus">
			<tr KMSS_IsContentRow="1" >
				<td align="center">
					<input type="checkbox" name="categories[${vstatus.index}].selected" value="1">
					<%-- 
					<input type="hidden" name="categories[${vstatus.index}].fdId" value="${category.fdId }">
					--%>
				</td>
				<td>
					<input type="hidden" name="categoryModule[${vstatus.index}].fdModule" 
						value="${category.value.fdModelName}">
					${category.value.messageKey }
				</td>
				<td>
					<xform:dialog 
							propertyId="categoryIds[${vstatus.index}]" 
							propertyName="categoryNames[${vstatus.index}]" 
							style="width:100%"
							textarea="true"
							showStatus="edit"
							idValue="${category.value.fdFollowId}"
							nameValue="${category.value.fdFollowName}">
						selectCategorys(this);
					</xform:dialog>
				</td>
			</tr>
			</c:forEach>
		</table>
	  </ui:content>
	  <ui:content title="${lfn:message('sys-follow:sysFollow.config.tag')}">
	  		<ui:toolbar layout="sys.ui.toolbar.default" style="float:right;" count="5">
				<ui:button onclick="submitTag();"  title="${lfn:message('button.save')}" text="${lfn:message('button.save')}" />
			</ui:toolbar>
	  		<table class="tb_normal" width="100%" id="tagTable">
	  			<tr>
	  				<td width="100%" data-tag="1">
		  				<script>Com_IncludeFile('dialog.js');</script>
						<div class="inputselectmul" 
							 style="width:100%">
							<input name="fdFollowId"
								   type="hidden"
								   value="${lfn:escapeHtml(rtnTagMap.fdFollowName)}">
							 <div class="textarea">
							 	<textarea name="fdFollowName" >${rtnTagMap.fdFollowName}</textarea>
							 </div>
							 <div class="selectitem" 
							 	  onclick="selectTags()">
							 </div>
						</div>
						<div>${lfn:message('sys-follow:sysFollow.config.tag.tips')}</div>
		  			</td>
	  			</tr>
	  		</table>
	  </ui:content>
	</ui:tabpanel>
		
		<script>Com_IncludeFile("dialog.js");</script>
		<script>Com_IncludeFile('jquery.js');</script>
		<script>Com_IncludeFile('doclist.js|validator.jsp|validation.js|plugin.js|validation.jsp');</script>
		<script>DocList_Info.push('categoryTable');</script>
		<script>
		seajs.use(['theme!module']);
		var cateValidator = $KMSSValidation(document.getElementById("categoryTable"));
		
		$(document).ready(function() {
			$('#categoryTable').delegate("tr[class!='tr_normal_title']", "click", function(event) {
				var row = this;
				if (event.target.tagName == 'TD') {
					var cell = event.target;
					if (cell.cellIndex < 2) {
						var ckb = $(row).find('[name$="selected"]')[0];
						ckb.checked = !ckb.checked;
					}
				}
			});
			//全选
			$('#categoryTable').delegate("[name='all']", "click", function(event) {
				var selected = this.checked;
				$('#categoryTable [name$="selected"]').each(function() {
					this.checked = selected;
				});
			});
			//临时数组保存分类订阅的模块
			$('input[name$="fdModule"]').each(function() {
				followedArray.push($(this).val());
			});
			
			$('#categoryTable').delegate('select[name$=".fdModule"]', 'change', function(event) {
				var tr = $(event.target).closest('tr');
				tr.find('[name^="categoryIds"], [name^="categoryNames"]').val("");
			});
		});
		
		// 重置模块数组
		function resetFollowedArray(){
			followedArray = [];
			$('[name$="fdModule"]').each(function() {
				followedArray.push($(this).val());
			});
		}
		
		function CheckSelectedItems() {
			if ($('#categoryTable [name$="selected"]:checked').length == 0) {
				seajs.use(["lui/dialog"], function(dialog) {
					dialog.alert(window.placeSelectOptionDatas ? placeSelectOptionDatas : "${lfn:message('page.noSelect')}");
				});
				return false;
			}
			return true;
		}
		function submitTag() {
			seajs.use(['lui/dialog'],function(dialog){
				var loading = dialog.loading();
				var config = {
					followConfig: getFollowTagConfig(),
					followType:"tag"
				};
				$.ajax({
					url: '${ LUI_ContextPath}/sys/follow/sys_follow_person_config/sysFollowPersonConfig.do?method=update',
					type: 'POST',
					dataType: 'json',
					async: false,
					data: config,
					success: function(data, textStatus, xhr) {
						loading.hide();
						if (data && data['flag'] === true) {
							dialog.success("${lfn:message('sys-follow:sysFollow.save.success')}");
						}else {
							dialog.failure("${lfn:message('sys-follow:sysFollow.save.failure')}");
						}
					},
					error: function(xhr, textStatus, errorThrown) {
						loading.hide();
						dialog.failure("${lfn:message('sys-follow:sysFollow.save.failure')}");
					}
				});
			});
		}
		function getFollowTagConfig(){
			var followConfig = [];
			followConfig.push({
				fdFollowIds: $("td[data-tag=1]").find("input[name=fdFollowId]").val(),
				fdFollowNames: $("td[data-tag=1]").find("textarea[name=fdFollowName]").val()
			});
			return JSON.stringify(followConfig);
		}
		//计算被删除分类订阅的模块
		function getDeleteModelNames(){
			
			//被删除的分类的订阅数组
			if (followedArray.length <= 0)
				return [];
			var followDeleteModelNames = [];
			var saveString = "";
			$('[name$="fdModule"]').each(function() {
				saveString += $(this).val() + ",";
			});
			for(var i = 0; i < followedArray.length; i ++) {
				if(saveString.indexOf(followedArray[i]) == -1) {
					followDeleteModelNames.push(followedArray[i]);
				}
			}
			return followDeleteModelNames;
		
		}
		function submitCategory() {
			if(!cateValidator.validate())
				return;
			seajs.use(['lui/dialog'],function(dialog){
				var _followConfig = getFollowCategoryConfig();
				var loading = dialog.loading();
				var config = {	
						followConfig: _followConfig,
						followType:"category",
						followDeleteModelNames:getDeleteModelNames()
				};
				$.ajax({
					url: '${ LUI_ContextPath}/sys/follow/sys_follow_person_config/sysFollowPersonConfig.do?method=update',
					type: 'POST',
					dataType: 'json',
					async: false,
					data: config,
					success: function(data, textStatus, xhr) {
						loading.hide();
						if (data && data['flag'] === true) {
							dialog.success("${lfn:message('return.optSuccess')}");
							resetFollowedArray();
						}else {
							dialog.failure("${lfn:message('return.optFailure')}");
						}
					},
					error: function(xhr, textStatus, errorThrown) {
						loading.hide();
						dialog.failure("${lfn:message('return.optFailure')}");
					}
				});
			});
		}
		function getFollowCategoryConfig(){
			var followConfig = [];
			var hasNoSelect = false;
			$("#categoryTable").find('tr[class!=tr_normal_title]').each(
				function() {
					var moduleName = $(this).find("[name$='fdModule']").val(),
					    _fdFollowIds = $(this).find("[name^='categoryIds']").val(),
					    _fdFollowNames = $(this).find("[name^='categoryNames']").val();
					if(moduleName) {
						followConfig.push({
							fdFollowModel:moduleName,
							fdFollowIds: _fdFollowIds,
							fdFollowNames: _fdFollowNames
						});
					}
				}
			);
			if(hasNoSelect) 
				return "hasNoSelect";
			return JSON.stringify(followConfig);
		}
		
		function AddNewRow() {
			DocList_AddRow('categoryTable');
			BuildCategoryModelSelector();
		}
		function DeleteCheckedRow() {
			if(!CheckSelectedItems())
				return false;
			seajs.use(["lui/dialog"], function(dialog) {
				dialog.confirm("${lfn:message('sys-follow:sysFollow.config.confirm.delete')}", function(rtn) {
					if(!rtn) return;
					$($('#categoryTable [name$="selected"]:checked').get().reverse()).each(function() {
						DocList_DeleteRow($(this).closest('tr')[0]);
					});
					submitCategory();
					FixCategoryModelSelector();
				});
			});
		}
		function CategoryRowUp() {
			if(!CheckSelectedItems())
				return false;
			$('#categoryTable [name$="selected"]:checked').each(function() {
				DocList_MoveRow(-1, $(this).closest('tr')[0]);
			});
		}
		function CategoryRowDown() {
			if(!CheckSelectedItems())
				return false;
			$('#categoryTable [name$="selected"]:checked').each(function() {
				DocList_MoveRow(1, $(this).closest('tr')[0]);
			});
		}
		//让已选的失效
		function FixCategoryModelSelector() {
			var selectedVal = [];
			$('[name$="fdModule"]').each(function() {
				selectedVal.push($(this).val());
			});
			var constains = function(array, item) {
				for (var i = 0; i < array.length; i ++) {
					if (array[i] == item) {
						return true;
					}
				}
				return false;
			};
			$('select[name$="fdModule"]').each(function() {
				var si = this.options.selectedIndex;
				for (var i=0 ; i<this.options.length; i ++) {
					var opt = this.options[i];
					if (si != i && constains(selectedVal, opt.value)) {
						opt.disabled = true;
					} else if(!constains(selectedVal, opt.value)) {
						opt.disabled = false;
					}
				}
			});
		}
		function BuildCategoryModelSelector() {
			$('select[name$="fdModule"]').each(function() {
				var self = this;
				if ($(self).attr('data-loaded') != 'true') {
					var ms = mArray;
					for (var i = 0; i < ms.length; i ++) {
						var m = ms[i];
						var opt = new Option(m.messageKey , m.modelName);
						self.options.add(opt);
					}
					$(self).attr('data-loaded', 'true');
				}
			});
			//设置已选的无效
			FixCategoryModelSelector();
		}
		function selectCategorys(sel) {
			var ms = mArray;
			var select = $(sel).closest('tr').find('[name$="fdModule"]');
			var modelName = select.val();
			var idfield = $(sel).find('[name^=categoryIds]').attr('name');
			var namefield = $(sel).find('[name^=categoryNames]').attr('name');
			var categoryModelName;
			var categoryType;
			for(var i = 0; i < ms.length; i++) {
				var m = ms[i];
				if(m.modelName == modelName) {
					categoryModelName = m.categoryModelName;
					categoryType = m.categoryType;
				}
			}
			if(categoryModelName && categoryModelName != "") {
				if("sysSimpleCategory" == categoryType) {
					seajs.use(['lui/dialog'],function (dialog) {
											dialog
													.simpleCategory({
														modelName : categoryModelName,
														idField : idfield,
														nameField : namefield,
														mulSelect : true,
														winTitle : "${lfn:message('sys-follow:sysFollow.config.dialog.category')}",
														canClose : true,
														authType : 0
													});
										});
					} else if ("sysCategory" == categoryType) {
						seajs
								.use(
										[ 'lui/dialog' ],
										function(dialog) {
											
											dialog
													.category({
														modelName : categoryModelName,
														idField : idfield,
														nameField : namefield,
														mulSelect : true,
														winTitle : "${lfn:message('sys-follow:sysFollow.config.dialog.category')}",
														canClose : true,
														authType : 0
													});

										});
					}
				}
			}

			function selectTags() {
				var tags = $("[name='fdFollowName']").val();
				$("input[name='fdFollowId']").val(tags);
				var url = "/sys/tag/import/addTag.jsp?mulSelect=true&addTagSign=1";
				top.window.selectTagNames = tags;
				
				seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
				dialog.iframe(url, lang["sysTag.choiceTag"], null, {
					width : 900,
					height : 550,
					buttons : [
						{
							name : ui_lang["ui.dialog.button.ok"],
							value : true,
							focus : true,
							fn : function(value,_dialog) {
								if(_dialog.frame && _dialog.frame.length > 0){
									var _frame = _dialog.frame[0];
									var contentWindow = $(_frame).find("iframe")[0].contentWindow;
									if(contentWindow.onSubmit()) {
										var datas = contentWindow.onSubmit().slice(0);
										if(datas.length>=0){
											selectWordCallBack(datas);	
	
											setTimeout(function() {
												_dialog.hide(value);
											}, 200);
											
										}
									}
								}
								
							}
						}
						,{
							name :ui_lang["ui.dialog.button.cancel"],
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}
					]	
				});});
			}
			
			function selectWordCallBack(datas) {
				if (datas != null && typeof (datas) != "undefined") {
					var item;
					var str="";
					for(var i=0; i<datas.length; i++){
						item=datas[i];
						str=str+item.fdName+";";
					}
					str = str.substring(0,str.length-1);
					$("input[name='fdFollowId']").val(str);
					$("textarea[name='fdFollowName']").val(str);
				}
			}
		</script>
		
		<script>
			var data = <% out.print(request.getAttribute("data").toString()); %>;
			var mArray = data.array;
			//已经订阅的分类的modelname数组
			var followedArray = [];
			
		</script>
	</template:replace>
</template:include>