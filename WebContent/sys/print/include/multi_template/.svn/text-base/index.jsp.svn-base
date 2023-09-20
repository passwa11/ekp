<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript"
            src="${ LUI_ContextPath }/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
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
				<list:sort property="docCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="up"></list:sort>
				<list:sort property="fdName" text="${lfn:message('model.fdName') }" group="sort.list"></list:sort>
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
			<ui:toolbar id="Btntoolbar" count="3">
				<!-- 增加 -->
				<ui:button text="${lfn:message('button.add')}" onclick="add()" order="1" ></ui:button>
				<!-- 批量删除 -->
				<ui:button text="${lfn:message('button.deleteall')}" onclick="del()" order="2" ></ui:button>
			</ui:toolbar>
		</div>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>
<!-- 内容列表 -->
<list:listview>
	<ui:source type="AjaxJson">
		{url:'${param.listUrl }&fdModelId=${param.fdModelId }'}
	</ui:source>
	<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
		rowHref="${param.viewUrl }&fdId=${param.fdModelId }&fdPrintId=!{fdId}">
		<list:col-checkbox></list:col-checkbox>
		<list:col-auto></list:col-auto>
	</list:colTable>
	<ui:event topic="list.loaded">
		Dropdown.init();
		if(resizeIframe){
			resizeIframe();
		}
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
 			Com_OpenWindow('<c:url value="${param.addUrl}" />&fdModelId=${param.fdModelId }');
 		};
 		// 编辑
 		window.edit = function(id) {
	 		if(id)
 				Com_OpenWindow('<c:url value="${param.editUrl}"/>&fdId=${param.fdModelId }&fdPrintId='+id);
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
			var url  = '<c:url value="/sys/print/sys_print_template/sysPrintTemplate.do?method=deleteall"/>';
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
								setTimeout(function(){
									if($("input[name='List_Selected']").length <= 0){
										topic.publish("delSuccessReloadPage");
									}
								}, 500);
							}
							dialog.result(data);
						}
				   });
				}
			});
		};
 	});
	</script>
