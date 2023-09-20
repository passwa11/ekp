<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
		<style>
			.lui_listview_columntable_table thead th{
				text-align:left !important;
			}
			.lui_listview_columntable_table tbody tr td{
				text-align:left !important;
			}
			.composite_table tr th {
				padding: 10px 2px;
				text-align:left;
			}
			.composite_table tr td {
				padding: 10px 2px;
				text-align:left;
			}
			.composite_table .expand {
				cursor:pointer;
				background: url("${KMSS_Parameter_ResPath}/style/default/icons/expand.gif") no-repeat center center;
			}
			.composite_table .collapse {
				cursor:pointer;
				background: url("${KMSS_Parameter_ResPath}/style/default/icons/collapse.gif") no-repeat center center;
			}
		</style>
		<script>
			//多语言
			var __composite_lang = {
				common_yes: "${lfn:message('message.yes')}",
				common_no: "${lfn:message('message.no')}",
				composite_type_page: "${lfn:message('sys-mportal:sysMportal.msg.page')}",
				composite_type_tab: "${lfn:message('sys-mportal:sysMportal.msg.tab')}",
				composite_mportal: "${lfn:message('sys-mportal:sysMportal.msg.mportal')}",
				composite_mportal_type: "${lfn:message('sys-mportal:sysMportal.msg.mportalType')}",
				btn_edit: "${lfn:message('button.edit')}",			
			};
		</script>
	</template:replace>
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria" expand="true">
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-mportal:sysMportalPage.fdName') }"></list:cri-ref>
			<list:cri-auto modelName="com.landray.kmss.sys.mportal.model.SysMportalComposite" property="docCreateTime,fdEnabled" />
			<list:cri-criterion title="${lfn:message('sys-mportal:sysMportalComposite.docStatus')}" key="docStatus" multi="false">
			<list:box-select>
				<list:item-select>
					<ui:source type="Static">
								[{text:'${ lfn:message('status.draft') }', value:'10'},
								{text:'${ lfn:message('status.publish') }',value:'30'}]
							</ui:source>
				</list:item-select>
			</list:box-select>
		</list:cri-criterion>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 全选 -->
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			<!-- 分割线 -->
			<div class="lui_list_operation_line"></div>
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_order_text">
					${ lfn:message('list.orderType') }：
				</div>
				<div class="lui_list_operation_sort_toolbar">					
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
					<list:sortgroup>
						<list:sort property="fdOrder" text="${lfn:message('sys-mportal:sysMportalCard.fdOrder') }" group="sort.list" value="up"></list:sort>
						<list:sort property="fdName" text="${lfn:message('sys-mportal:sysMportalPage.fdName') }" group="sort.list" ></list:sort>
						<list:sort property="docCreateTime" text="${lfn:message('sys-mportal:sysMportalPage.docCreateTime') }" group="sort.list" ></list:sort>
					</list:sortgroup>
					</ui:toolbar>
				</div>
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
						<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=add">
							<ui:button text="${ lfn:message('button.add') }"  onclick="add();"></ui:button> 
						</kmss:auth>
						<kmss:auth requestURL="/sys/mportal/sys_mportal_page/sysMportalPage.do?method=deleteall">
							<ui:button text="${ lfn:message('button.deleteall') }" onclick="deleteAll();"></ui:button>
							<!-- 快速排序 -->
							<%-- <c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.sys.mportal.model.SysMportalComposite"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
							</c:import> --%>
						</kmss:auth>
						<kmss:authShow roles="ROLE_SYS_MPORTAL_ADMIN">
							<ui:button text="${ lfn:message('sys-mportal:btn.fdIsAvailable.onAll') }" onclick="enableAll()"></ui:button>
							<ui:button text="${ lfn:message('sys-mportal:btn.fdIsAvailable.offAll') }" onclick="disableAll()"></ui:button>
						</kmss:authShow>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
		 		<list:gridTable name="gridtable" columnNum="4">
					<ui:source type="AjaxJson">					
						{url:'/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=list&contentType=json'}
					</ui:source>
					<list:row-template ref="sys.mportal.composite.listview">
						{showCheckbox: false}
					</list:row-template>
				</list:gridTable>
			<%-- <ui:source type="AjaxJson">
				{url:'/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=list&contentType=json'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,fdEnabled,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable> --%>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog','lui/qrcode'],function($,topic,dialog,qrcode){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//新建
				window.add = function(){
					Com_OpenWindow('${LUI_ContextPath}/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=add');
				};
				 // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/mportal/sys_mportal_composite/sysMportalComposite.do" />?method=edit&fdId=' + id);
		 		};
		 		//批量置为无效
				window.disableAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
								values.push($(this).val());
						 });
					}
					if(values.length == 0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=disableAll"/>';
					dialog.confirm('<bean:message key="page.invalid"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
				
				//批量置为无效
				window.enableAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
						 $("input[name='List_Selected']:checked").each(function(){
								values.push($(this).val());
						 });
					}
					if(values.length == 0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=enableAll"/>';
					dialog.confirm('${lfn:message("sys-mportal:sysMportal.enable.confirm") }',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: delCallback
						   });
						}
					});
				};
		 		
				//删除
				window.deleteAll = function(id){
					var values = [];
					if(id) {
		 				values.push(id);
			 		} else{
			 			$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
				 	}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/mportal/sys_mportal_composite/sysMportalComposite.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};
				
				//删除回调函数
				window.delCallback = function(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success('<bean:message key="return.optSuccess" />');
					}else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
				};
				
				
				
				window.del = function(id) {
		 			if(!id) {
		 				dialog.alert('<bean:message key="page.noSelect"/>');
						return;
		 			} else {
		 				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(flag){
							if(flag) {
								var loading = dialog.loading();
								$.get('<c:url value="/sys/mportal/sys_mportal_composite/sysMportalComposite.do" />?method=delete&fdId=' + id,
									function(data) {
										if(loading)
											loading.hide();
										if(data && data.status==true){
											topic.publish("list.refresh");
											dialog.success('<bean:message key="return.optSuccess" />');
										}else{
											dialog.failure('<bean:message key="return.optFailure" />');
										}
									},'json');
							}
						});
		 			}
		 		};
		 		var idList = [];
		 		window.preview = function(id){
		 			 var url = location.origin+"${ LUI_ContextPath}/sys/mportal/mobile/composite/preview.jsp?fdCompositeId="+id;
		 			 var preview = $("#pre_"+id);
		 			 var allPreview = $(".preview_content");
		 			
		 			 if(preview.css('display')=="block"){
		 				 preview.css('display','none')
		 			 }else{
		 				 allPreview.css('display','none');
		 				 preview.css('display','block')
		 			 }
		 			 if(!idList.includes(id)){
			             var isBitch = navigator.userAgent.indexOf("MSIE") > -1 && document.documentMode == null || document.documentMode <= 8;
			             qrcode.Qrcode({
			                 text :url,
			                 element : $("#pre_"+id),
			                 render : isBitch ? 'table' : 'canvas',
			                 width:142,
			                 height:142,
	
			             });
			             idList.push(id);
		             }
		 		};
			});
			seajs.use(["sys/mportal/sys_mportal_card/css/edit.css"]);
			
			
		</script>
	
	</template:replace>
</template:include>