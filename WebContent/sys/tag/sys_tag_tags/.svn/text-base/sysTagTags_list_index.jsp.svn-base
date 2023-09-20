<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
		<c:if test="${param.fdStatus == null}">
			<list:cri-criterion title="${ lfn:message('sys-tag:sysTagTags.fdStatus')}" key="fdStatus"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-tag:sysTagTags.fdStatus.false')}', value:'0'},
							{text:'${ lfn:message('sys-tag:sysTagTags.fdStatus.true')}',value:'1'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			</c:if>
			<list:cri-auto modelName="com.landray.kmss.sys.tag.model.SysTagTags" 
			property="fdName,docCreateTime,docCreator"
			/>
		</list:criteria>
 		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>  

			<!-- 操作按钮 -->
 			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar" count="8">
						<c:if test="${param.fdStatus == '1' || param.fdCategoryIds != null}">
							<kmss:auth
							requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=editMergerTag&fdCategoryId=${param.fdCategoryId}"
							requestMethod="GET">
							<c:if test="${param.type == 'main' }">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.mergerTag')}" onclick="mergerTagCheckSelect();"></ui:button>
							</c:if>
							<c:if test="${param.type == 'alias' }">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.mergerTags')}" onclick="mergerTagCheckSelect();"></ui:button>
							</c:if>
							</kmss:auth>
							<kmss:auth
							requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=editMoveTag&fdCategoryId=${param.fdCategoryId}&fdId=${param.fdId}"
							requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.updateCategory')}" onclick="moveTagCheckSelect();"></ui:button>
							</kmss:auth>
							<!-- 标签分类调整 -->
							<c:import
								url="/sys/tag/sys_tag_tags/sysTagTags_move_button.jsp?fdCategoryId=${param.fdCategoryId}"
								charEncoding="UTF-8">
								<c:param name="isOptBar" value="false"></c:param>
							</c:import>
							<kmss:auth
								requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=editMergerTag&fdCategoryId=${param.fdCategoryId}"
								requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.mergerTags')}" onclick="mergerTagCheckSelect();"></ui:button>
							</kmss:auth>
							<c:import
								url="/sys/tag/sys_tag_tags/sysTagTags_merger_button.jsp?fdCategoryId=${param.fdCategoryId}"
								charEncoding="UTF-8">
								<c:param
								name="type"
								value="main" />
								<c:param name="isOptBar" value="false"></c:param>
							</c:import>
						</c:if>
						<c:if test="${param.fdStatus == '0' || (param.fdStatus == null && param.fdCategoryIds == null)}">
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveValidateTags&fdCategoryId=${param.fdCategoryId}" requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.saveValidateTags')}" onclick="saveValidateTags();"></ui:button>
							</kmss:auth>
							<c:import
								url="/sys/tag/sys_tag_tags/sysTagTags_validate_button.jsp?fdCategoryId=${param.fdCategoryId}"
								charEncoding="UTF-8">
								<c:param name="isOptBar" value="false"></c:param>
							</c:import>
						</c:if>
						<c:if test="${param.fdStatus == '1' || param.fdCategoryIds != null}">
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveInvalidateTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.saveInvalidateTags')}" onclick="saveInvalidateTags();"></ui:button>
							</kmss:auth>
						</c:if>
						<!--	特殊标签显示置为普通标签	-->
						<c:if test="${param.fdIsSpecial == '1' || param.fdCategoryIds != null}">
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveCommonTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.saveCommonTags')}" onclick="saveCommonTags();"></ui:button>
							</kmss:auth>
						</c:if>
						<!-- 所有标签置为普通标签 -->
						<c:if test="${param.fdIsSpecial == null && param.fdCategoryIds == null}">
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveCommonTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.saveCommonTags')}" onclick="saveCommonTags();"></ui:button>
							</kmss:auth>
						</c:if>
						<c:if test="${param.fdIsSpecial == '0' || (param.fdIsSpecial == null && param.fdCategoryIds == null)}">
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveSpecialTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.saveSpecialTags')}" onclick="saveSpecialTags();"></ui:button>
							</kmss:auth>
						</c:if>
						<c:if test="${param.fdStatus == null && param.fdCategoryIds == null}">
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=saveInvalidateTags&fdCategoryId=${param.fdCategoryIds}" requestMethod="GET">
								<ui:button text="${lfn:message('sys-tag:sysTagTags.button.saveInvalidateTags')}" onclick="saveInvalidateTags();"></ui:button>
							</kmss:auth>
						</c:if>
						<c:if test="${param.fdIsPrivate == '1' }">
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=add&categoryId=${param.fdCategoryId}" requestMethod="GET">
								<ui:button text="${lfn:message('button.add')}" onclick="add()"></ui:button>
							</kmss:auth>
						</c:if>
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=deleteall&fdCategoryId=${param.fdCategoryId}" requestMethod="GET">
								<ui:button text="${lfn:message('button.deleteall')}" onclick="del();" order="7" ></ui:button>
							</kmss:auth>
								<ui:button text="${lfn:message('button.search')}" onclick="search();" order="8" ></ui:button>
						<c:if test="${param.fdStatus == '1' }" >
							<kmss:auth requestURL="/sys/tag/sys_tag_tags/sysTagTags.do?method=exportTagExcel" requestMethod="GET">
								<ui:button text="${ lfn:message('button.export') }" onclick="window.exportTagExcel();"></ui:button>
							</kmss:auth>
						</c:if>
					</ui:toolbar>
				</div>
			</div> 
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview id="listview">
			<c:choose>
				<c:when test="${param.fdIsPrivate == '1'}">
					<c:choose>
						<c:when test="${param.fdStatus == '1'}">
						<ui:source type="AjaxJson">
							{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=1&fdIsPrivate=1'}
						</ui:source>
						</c:when>
						<c:when test="${param.fdStatus == '0'}">
							<ui:source type="AjaxJson">
								{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=0&fdIsPrivate=1'}
							</ui:source>
						</c:when>
						<c:when test="${param.fdCategoryId != null }">
							<ui:source type="AjaxJson">
								{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdCategoryId=${param.fdCategoryId}&fdIsPrivate=1'}
							</ui:source>
						</c:when>
						<c:when test="${param.fdIsSpecial == '1' }">
							<ui:source type="AjaxJson">
								{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsSpecial=1&fdIsPrivate=1'}
							</ui:source>
						</c:when>
						<c:when test="${param.fdIsSpecial == '0' }">
							<ui:source type="AjaxJson">
								{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsSpecial=0&fdIsPrivate=1'}
							</ui:source>
						</c:when>
					<c:otherwise>
						<ui:source type="AjaxJson">
							{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsPrivate=1'}
						</ui:source>
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${param.fdIsPrivate == '0'}">
					<c:choose>
					<c:when test="${param.fdStatus == '1'}">
						<ui:source type="AjaxJson">
							{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=1&fdIsPrivate=0&rowsize=15'}
						</ui:source>
					</c:when>
					<c:when test="${param.fdStatus == '0'}">
						<ui:source type="AjaxJson">
							{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdStatus=0&fdIsPrivate=0&rowsize=15'}
						</ui:source>
					</c:when>
					<c:otherwise>
						<ui:source type="AjaxJson">
							{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list&fdIsPrivate=0&rowsize=15'}
						</ui:source>
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<ui:source type="AjaxJson">
						{url:'/sys/tag/sys_tag_tags/sysTagTags.do?method=list'}
					</ui:source>
				</c:otherwise>
			</c:choose>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/tag/sys_tag_tags/sysTagTags.do?method=view&fdId=!{fdId}" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdAlias,fdCategorys,fdStatus,docCreator,docCreateTime,fdCountQuoteTimes"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event> 
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
		<%@ include file="/sys/tag/sys_tag_tags/sysTagTags_list_index.js.jsp"%>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 搜索
		 		window.search = function() {
		 			Com_OpenWindow('<c:url value="/sys/search/search.do?method=condition&fdModelName=com.landray.kmss.sys.tag.model.SysTagTags"/>');
		 		};
		 		// 添加
		 		window.add = function() {
		 			var categoryId = "${JsParam.fdCategoryId}";
		 			if(categoryId){
		 				Com_OpenWindow('<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do" />?method=add&categoryId=${JsParam.fdCategoryId}&fdIsPrivate=${JsParam.fdIsPrivate}');
		 			}else{
		 				var url = "/sys/tag/sys_tag_tags/sysTagTags_choice_category.jsp?mulSelect=true";
		 				seajs.use(['lui/dialog', 'lui/util/env','lang!sys-tag','lang!sys-ui'],function(dialog, env,lang,ui_lang) {
		 					dialog.iframe(url, lang["sysTag.choiceTagCategory"], null, {
		 						width : 600,
		 						height : 550,
		 						buttons : [{
		 							name : ui_lang["ui.dialog.button.ok"],
		 							value : true,
		 							focus : true,
		 							fn : function(value,_dialog) {
		 								if(_dialog.frame && _dialog.frame.length > 0){
		 									var _frame = _dialog.frame[0];
		 									var contentWindow = $(_frame).find("iframe")[0].contentWindow;
		 									if(contentWindow.onSubmit()) {
		 										var datas = contentWindow.onSubmit().slice(0);
		 										if(datas.length>0){
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
		 						}]	
		 					});
		 				});
		 			}
		 		};
		 		// 删除
		 		window.del = function(id) {
		 			var values = [];
		 			if(id) {
		 				values.push(id);
			 		} else {
						$("input[name='List_Selected']:checked").each(function() {
							values.push($(this).val());
						});
			 		}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url  = '<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do"/>?method=deleteall&fdModelName=${JsParam.fdModelName}';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : url,
								type : 'POST',
								data : $.param({"List_Selected" : values}, true),
								dataType : 'json',
								error : function(data) {
									if(window.del_load != null) {
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(data) {
									if(window.del_load != null){
										window.del_load.hide(); 
										topic.publish("list.refresh");
									}
									dialog.result(data);
								}
						   });
						}
					});
				};
		 	});
		 	function selectWordCallBack(datas){
		 		if (datas != null && typeof (datas) != "undefined") {
		 			var item;
		 			var categoryId="";
		 			for(var i=0; i<datas.length; i++){
		 				item=datas[i];
		 				categoryId=categoryId+item.fdId+";";
		 			}
		 			categoryId = categoryId.substring(0,categoryId.length-1);
		 			Com_OpenWindow('<c:url value="/sys/tag/sys_tag_tags/sysTagTags.do" />?method=add&fdIsPrivate=${JsParam.fdIsPrivate}&categoryId='+categoryId);
		 		}
		 	}
		 	function List_ConfirmSaveValidateTags(checkName){
		 		return List_CheckSelect(checkName) && confirm("<bean:message key="sysTagTags.confirmSaveValidateTags" bundle="sys-tag"/>");
		 	}
		 	function updateFromPriToPubInList(){
		 		var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=updateFromPriToPubInList';
		 		if(!url || typeof url != "string")
		 			return;
		 		var values = [],
		 		     selected,
		 		     select = document.getElementsByName("List_Selected");
		 		for (var i = 0; i < select.length; i++) {
		 			if (select[i].checked) {
		 				values.push(select[i].value);
		 				selected = true;
		 			}
		 		}
		 		if (selected) {
		 			    seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
		 					function(dialog, topic, $) {
		 			    	dialog.confirm("<bean:message key="sysTagTags.updateFromPriToPubInList" bundle="sys-tag"/>", function(flag, d) {
		 			    		if (flag) {
		 			    		var data;
		 						var dataObj = $.extend({},data,{"List_Selected":values});
		 						
		 							
		 								var loading = dialog.loading();
		 								$.ajax({
		 										url : url,
		 										cache : false,
		 										data : $.param(dataObj,true),
		 										type : 'post',
		 										dataType :'json',
		 										success : function(data) {
		 											
		 											if (data.flag) {
		 												loading.hide();
		 												if(data.mesg) {
		 													dialog.success("${lfn:message('return.optSuccess')}" );
		 													window.location.reload();　												
		 												}
		 												
		 											} else {
		 									
		 												
		 												loading.hide();	
		 												dialog.success("${lfn:message('return.optFailure')}")
		 												window.location.reload();
		 												
		 											}
		 										},
		 										error : function(error) {
		 											
		 											loading.hide();	
		 											dialog.failure(
		 													"${lfn:message('return.optFailure')}");
		 										}
		 								}
		 							);
		 						}
		 					});
		 				});
		 	} else {
		 			seajs.use(['lui/dialog'], function(dialog) {
		 						dialog.alert("${lfn:message('page.noSelect')}");
		 					});
		 		}	
		 		
		 		
		 	}
		 	function saveInvalidateTags(){
		 		var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=saveInvalidateTags';
		 		if(!url || typeof url != "string")
		 			return;
		 		var values = [],
		 		     selected,
		 		     select = document.getElementsByName("List_Selected");
		 		for (var i = 0; i < select.length; i++) {
		 			if (select[i].checked) {
		 				values.push(select[i].value);
		 				selected = true;
		 			}
		 		}
		 		if (selected) {
		 			    seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
		 					function(dialog, topic, $) {
		 			    	
		 			    		var data;
		 						var dataObj = $.extend({},data,{"List_Selected":values});
		 						
		 							
		 								var loading = dialog.loading();
		 								$.ajax({
		 										url : url,
		 										cache : false,
		 										data : $.param(dataObj,true),
		 										type : 'post',
		 										dataType :'json',
		 										success : function(data) {
		 											
		 											if (data.flag) {
		 												loading.hide();
		 												if(data.mesg) {
		 													dialog.success("${lfn:message('return.optSuccess')}" );
		 													window.location.reload();　												
		 												}
		 												
		 											} else {
		 									
		 												
		 												loading.hide();	
		 												dialog.success("${lfn:message('return.optFailure')}")
		 												window.location.reload();
		 												
		 											}
		 										},
		 										error : function(error) {
		 											
		 											loading.hide();	
		 											dialog.failure(
		 													"${lfn:message('return.optFailure')}");
		 										}
		 								}
		 							);
		 					
		 				});
		 	} else {
		 			seajs.use(['lui/dialog'], function(dialog) {
		 						dialog.alert("${lfn:message('page.noSelect')}");
		 					});
		 		}	
		 		
		 	}
		 	function saveSpecialTags(){
		 		var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=saveSpecialTags';
		 		if(!url || typeof url != "string")
		 			return;
		 		var values = [],
		 		     selected,
		 		     select = document.getElementsByName("List_Selected");
		 		for (var i = 0; i < select.length; i++) {
		 			if (select[i].checked) {
		 				values.push(select[i].value);
		 				selected = true;
		 			}
		 		}
		 		if (selected) {
		 			    seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
		 					function(dialog, topic, $) {
		 			    	dialog.confirm("<bean:message key="sysTagTags.confirmSaveSpecialTags" bundle="sys-tag"/>", function(flag, d) {
		 			    		if (flag) {
		 			    		var data;
		 						var dataObj = $.extend({},data,{"List_Selected":values});
		 						
		 							
		 								var loading = dialog.loading();
		 								$.ajax({
		 										url : url,
		 										cache : false,
		 										data : $.param(dataObj,true),
		 										type : 'post',
		 										dataType :'json',
		 										success : function(data) {
		 											
		 											if (data.flag) {
		 												loading.hide();
		 												if(data.mesg) {
		 													dialog.success("${lfn:message('return.optSuccess')}" );
		 													window.location.reload();　												
		 												}
		 												
		 											} else {
		 									
		 												
		 												loading.hide();	
		 												dialog.success("${lfn:message('return.optFailure')}")
		 												window.location.reload();
		 												
		 											}
		 										},
		 										error : function(error) {
		 											
		 											loading.hide();	
		 											dialog.failure(
		 													"${lfn:message('return.optFailure')}");
		 										}
		 								}
		 							);
		 						}
		 					});
		 				});
		 	} else {
		 			seajs.use(['lui/dialog'], function(dialog) {
		 						dialog.alert("${lfn:message('page.noSelect')}");
		 					});
		 		}	
		 	}
		 	function saveCommonTags() {
		 		
		 		var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=saveCommonTags';
		 		if(!url || typeof url != "string")
		 			return;
		 		var values = [],
		 		     selected,
		 		     select = document.getElementsByName("List_Selected");
		 		for (var i = 0; i < select.length; i++) {
		 			if (select[i].checked) {
		 				values.push(select[i].value);
		 				selected = true;
		 			}
		 		}
		 		if (selected) {
		 			
		 			
		 			    seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
		 					function(dialog, topic, $) {
		 			    	dialog.confirm("<bean:message key="sysTagTags.confirmSaveCommonTags" bundle="sys-tag"/>", function(flag, d) {
		 			    		if (flag) {
		 			    		var data;
		 						var dataObj = $.extend({},data,{"List_Selected":values});
		 						
		 							
		 								var loading = dialog.loading();
		 								$.ajax({
		 										url : url,
		 										cache : false,
		 										data : $.param(dataObj,true),
		 										type : 'post',
		 										dataType :'json',
		 										success : function(data) {
		 											
		 											if (data.flag) {
		 												loading.hide();
		 												if(data.mesg) {
		 													dialog.success("${lfn:message('return.optSuccess')}" );
		 													window.location.reload();　												
		 												}
		 												
		 											} else {
		 									
		 												
		 												loading.hide();	
		 												dialog.success("${lfn:message('return.optFailure')}")
		 												window.location.reload();
		 												
		 											}
		 										},
		 										error : function(error) {
		 											
		 											loading.hide();	
		 											dialog.failure(
		 													"${lfn:message('return.optFailure')}");
		 										}
		 								}
		 							);
		 						}
		 					});
		 				});
		 	} else {
		 			seajs.use(['lui/dialog'], function(dialog) {
		 						dialog.alert("${lfn:message('page.noSelect')}");
		 					});
		 		}
		 		
		 	}
		 	function delTag() {
		 		
		 		var url = '${LUI_ContextPath}/sys/tag/sys_tag_tags/sysTagTags.do?method=deleteall';
		 		
		 		kms_TagDoc(url);
		 	}
		 	function kms_TagDoc(url) {
		 		
		 		if(!url || typeof url != "string")
		 			return;
		 		var values = [],
		 		     selected,
		 		     select = document.getElementsByName("List_Selected");
		 		for (var i = 0; i < select.length; i++) {
		 			if (select[i].checked) {
		 				values.push(select[i].value);
		 				selected = true;
		 			}
		 		}
		 		if (selected) {
		 			
		 			
		 			    seajs.use(['lui/dialog', 'lui/topic', 'lui/jquery'],
		 					function(dialog, topic, $) {
		 			    	dialog.confirm("<bean:message key="sysTagTags.deleteAll" bundle="sys-tag"/>", function(flag, d) {
		 			    		if (flag) {
		 			    		var data;
		 						var dataObj = $.extend({},data,{"List_Selected":values});
		 						
		 							
		 								var loading = dialog.loading();
		 								$.ajax({
		 										url : url,
		 										cache : false,
		 										data : $.param(dataObj,true),
		 										type : 'post',
		 										dataType :'json',
		 										success : function(data) {
		 											
		 											if (data.flag) {
		 												loading.hide();
		 												if(data.errorMessage) {
		 													dialog.alert("${lfn:message('sys-tag:sysTag.tags.info')}" );
		 													　												
		 												}
		 												
		 											} else {
		 									
		 												
		 												loading.hide();	
		 												dialog.success("${lfn:message('sys-tag:sysTag.tags.info.success')}")
		 												window.location.reload();
		 												
		 											}
		 										},
		 										error : function(error) {//删除失败
		 											
		 											loading.hide();	
		 											dialog.failure(
		 													"${lfn:message('sys-tag:sysTag.tags.info.fail')}");
		 										}
		 								}
		 							);
		 						}
		 					});
		 				});
		 	} else {
		 			seajs.use(['lui/dialog'], function(dialog) {
		 						dialog.alert("${lfn:message('page.noSelect')}");
		 					});
		 		}
		 	}
	 	</script>
	</template:replace>
</template:include>
