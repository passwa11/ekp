<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">${ lfn:message('sys-tag:sysTag.tree.title') }</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria1">
			<list:cri-auto modelName="com.landray.kmss.sys.tag.model.SysTagCategory" 
			property="fdName"
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
					<ui:toolbar id="Btntoolbar" count="4">
							<kmss:auth requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=add" requestMethod="GET">
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
							</kmss:auth>
							<kmss:auth requestURL="/sys/tag/sys_tag_category/sysTagCategory.do?method=deleteall" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteall();" order="2" ></ui:button>
							</kmss:auth>
					</ui:toolbar>
				</div>
			</div> 
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/tag/sys_tag_category/sysTagCategory.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/sys/tag/sys_tag_category/sysTagCategory.do?method=view&fdId=!{fdId}" >
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,authEditors,fdTagQuoteTimes"></list:col-auto>
			</list:colTable>
			<ui:event topic="list.loaded">
				Dropdown.init();
			</ui:event>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish("list.refresh");
				});
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/tag/sys_tag_category/sysTagCategory.do" />?method=add&fdCategoryId=${param.categoryId}');
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
					var url  = '<c:url value="/sys/tag/sys_tag_category/sysTagCategory.do"/>?method=deleteall&fdModelName=${JsParam.fdModelName}';
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
		 	function deleteall() {
		 		
		 		var url = '${LUI_ContextPath}/sys/tag/sys_tag_category/sysTagCategory.do?method=deleteall';
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
		 			    	dialog.confirm("<bean:message key="page.comfirmDelete" />", function(flag, d) {
		 			    		if (flag) {
		 			    		        var data;
		 			    		    	var dataObj = $.extend({},data,{"List_Selected":values});
		 					    		var str=$.param(dataObj,true);
		 					    		var idStr=str.split("&");
		 					    		var id="${id}";
		 					    		for(var str in idStr){
		 					    			var uuid=idStr[str].substring(14);
		 					    			if(uuid==id){
		 					    				dialog.alert("${lfn:message('sys-tag:sysTagCategory.detall.unable')}");
		 					    				return
		 					    			}
		 					    			
		 					    		}
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
		 											dialog.alert(
		 													"${lfn:message('error.constraintViolationException')}");
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