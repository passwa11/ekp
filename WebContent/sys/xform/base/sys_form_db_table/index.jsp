<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-xform:sysFormDbTable.fdName') }">
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
							<list:sort property="docCreateTime" text="${lfn:message('sys-xform:sysFormDbTable.docCreateTime') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdName" text="${lfn:message('sys-xform:sysFormDbTable.fdName') }" group="sort.list"></list:sort>
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
						<kmss:auth requestURL="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=deleteall&fdModelName=${JsParam.fdModelName}&fdTemplateModel=${JsParam.fdTemplateModel}&fdFormType=${JsParam.fdFormType}&fdModelId=${JsParam.fdModelId}" requestMethod="GET">
						   <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=list&fdModelName=${JsParam.fdModelName}&fdTemplateModel=${JsParam.fdTemplateModel}&fdKey=${JsParam.fdKey}&newUi=true'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" rowHref="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=view&fdId=!{fdId}&fdKey=!{fdKey}&fdModelName=!{fdModelName}&fdFormType=!{fdFormType}&fdTemplateModel=!{fdTemplateModel}&fdFormId=!{fdFormId}&fdModelId=!{fdModelId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdName,fdTable,docCreator.fdName,docCreateTime,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do">
							<c:param name="method" value="add" />
							<c:param name="fdModelName" value="${JsParam.fdModelName}" />
							<c:param name="fdKey" value="${JsParam.fdKey}" />
							<c:param name="fdMainModelName" value="${JsParam.fdMainModelName}" />
					</c:url>');
		 			//Com_OpenWindow('<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=add&fdModelName=${JsParam.fdModelName}&fdKey=${JsParam.fdKey}&fdMainModelName=${JsParam.fdMainModelName}"/>');
		 		};
		 		// 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/xform/base/sys_form_db_table/sysFormDbTable.do" />?method=edit&fdId=' + id);
		 		};
		 		window.deleteAll = function(id){
					var values = [];
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
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/sys/xform/base/sys_form_db_table/sysFormDbTable.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
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
		 	});
	 	</script>
	</template:replace>
</template:include>
