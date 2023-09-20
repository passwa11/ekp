<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
	</template:replace>
	<template:replace name="title"></template:replace>
	<template:replace name="content">
		<div id="tbody-view" style="padding: 10px">
			<table style="width: 100%">
				<tr>
					<td valign="top">
						<!-- 筛选器 -->
						<list:criteria id="criteria1">
							<list:cri-ref key="fdTemplateName" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmservice-support:lbpmSummaryApproval.title.lbpmTemplate') }" ></list:cri-ref>
						</list:criteria>
						<!-- 排序 -->
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
									<ui:toolbar id="Btntoolbar">
										<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=add" requestMethod="GET">
											<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1"></ui:button>
										</kmss:auth>
										<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=deleteall" requestMethod="GET">
											<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="2"></ui:button>
										</kmss:auth>
									</ui:toolbar>
								</div>
							</div>
						</div>
						<ui:fixed elem=".lui_list_operation"></ui:fixed>
						<list:listview id="listview">
							<ui:source type="AjaxJson">
								{url:'/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=list'}
							</ui:source>
							<list:colTable isDefault="true"
								layout="sys.ui.listview.columntable"
								rowHref="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=view&fdId=!{fdId}">
								<list:col-checkbox></list:col-checkbox>
								<list:col-auto></list:col-auto>
							</list:colTable>
							<ui:event topic="list.loaded">
								Dropdown.init();
							</ui:event>
						</list:listview>
						<list:paging></list:paging>
						<script>
						domain.autoResize();
						seajs.use(['lui/topic','lui/dialog'], function(topic,dialog) {
							// 监听新建更新等成功后刷新
							topic.subscribe('successReloadPage', function() {
								topic.publish('list.refresh');
							});
							//新建文档
							window.addDoc = function(){
								Com_OpenWindow('<c:url value="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do" />?method=add');
							}
							// 编辑
					 		window.edit = function(id) {
						 		if(id)
					 				Com_OpenWindow('<c:url value="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do" />?method=edit&fdId=' + id);
					 		};
							//批量删除文档
							window.deleteAll = function(id){
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
								var url = '<c:url value="/sys/lbpmservice/support/lbpm_summary_approval/lbpmSummaryApprovalConfig.do?method=updateOrDelAll"/>';
								dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
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
							}
							
							window.delCallback = function(data){
								if(window.del_load!=null){
									window.del_load.hide();
									topic.publish("list.refresh");
								}
								dialog.result(data);
							};
						});
						</script>
					</td>
				</tr>
			</table>
		</div>
	</template:replace>
</template:include>