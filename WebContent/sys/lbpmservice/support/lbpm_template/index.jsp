<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-lbpmservice-support:lbpmTemplate.fdName') }">
			</list:cri-ref>
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
							<list:sort property="fdCreateTime" text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.fdCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdName" text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.fdName') }" group="sort.list"></list:sort>
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
					<ui:toolbar id="Btntoolbar">
						<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=add&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}">
						    <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=deleteall&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}">
							<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
					    <kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=updateAuditor&type=1&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}" requestMethod="GET">
							 <ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.updateAuditor.button')}" onclick="updateAuditor();" order="3" ></ui:button>
						</kmss:auth>
				
						<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do?method=updatePrivileger&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}" requestMethod="GET">
							<ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.updatePrivileger.button')}" onclick="updatePrivileger();" order="4" ></ui:button>
						</kmss:auth>

						<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do?method=doExportNodesExcel&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}" requestMethod="GET">
							<ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.nodes2Excel.button')}" onclick="doExportNodesExcel();" order="5" ></ui:button>
							<ui:button text="${lfn:message('sys-lbpmservice-support:lbpmTemplate.flowchart2Excel.button')}" onclick="doExportFlowChartExcel();" order="5" ></ui:button>
						</kmss:auth>

					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/workflow/sys_wf_common_template/sysWfCommonTemplate.do?method=list&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}&newUi=true'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			     rowHref="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=view&fdModelName=${JsParam.fdModelName}&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdIsDefault,fdCreator.fdName,fdCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do" />?method=add&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}');
		 		};
		 	// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do" />?method=edit&fdModelName=${JsParam.fdModelName}&fdId=' + id);
		 		};
		 		window.deleteAll = function(id){
					var values = [];
					if(id){
						values.push(id);
					}else{
					   $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
					   });
					}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url = '<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=deleteall&fdModelName=${JsParam.fdModelName}"/>';
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
				};
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
						topic.publish("list.refresh");
					}
					dialog.result(data);
				};
				
				window.updateAuditor = function () {
					if($("input[name='List_Selected']:checked").length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var s = "", c = 0;
					var obj = document.getElementsByName("List_Selected");
					for(var i=0; i<obj.length; i++) {
						if(obj[i].checked) {
							if(c>0){
								s+=";";
							}
							c++;
							s += obj[i].value;
						}
					}
					var url='<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmTemplate.do" />?method=updateAuditor&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}';
					url+="&fdIds="+s;
					Com_OpenWindow(url);
				};
				
				window.updatePrivileger = function () {
					if($("input[name='List_Selected']:checked").length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var s = "", c = 0;
					var obj = document.getElementsByName("List_Selected");
					for(var i=0; i<obj.length; i++) {
						if(obj[i].checked) {
							if(c>0){
								s+=";";
							}
							c++;
							s += obj[i].value;
						}
					}
					var url='<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do" />?method=updatePrivileger&type=1&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}';
					url+="&fdIds="+s;
					Com_OpenWindow(url);
				};

				window.doExportNodesExcel = function () {
					if($("input[name='List_Selected']:checked").length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var s = "", c = 0;
					var obj = document.getElementsByName("List_Selected");
					for(var i=0; i<obj.length; i++) {
						if(obj[i].checked) {
							if(c>0){
								s+=";";
							}
							c++;
							s += obj[i].value;
						}
					}
					var url='<c:url value="/sys/lbpmservice/support/lbpm_template/lbpmNodes2Excel.do" />?method=doExportNodesExcel&type=1&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}';
					url+="&fdIds="+s;
					Com_OpenWindow(url);
				};
				
				window.doExportFlowChartExcel = function () {
					if($("input[name='List_Selected']:checked").length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var url='<c:url value="/sys/lbpmservice/flowchartimport/LbpmFlowchartImportAction.do" />?method=doExportNodesExcel&type=1&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}';
					$("input[name='List_Selected']:checked").each(function(i){
						Com_OpenWindow(url + "&fdIds=" + this.value);
					});
				};

				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
		 	});
	 	</script>
	</template:replace>
</template:include>
