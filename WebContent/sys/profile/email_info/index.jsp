<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-profile:sys.email.info.docSubject') }">
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
			
			<div class="lui_list_operation_order_text">
                <%-- 排序文本 --%>
                ${ lfn:message('list.orderType') }：
            </div>
			
			<div class="lui_list_operation_sort_toolbar">
				<ui:toolbar layout="sys.ui.toolbar.sort">
					<list:sort property="docSubject" text="${lfn:message('sys-profile:sys.email.info.docSubject') }"></list:sort>
				</ui:toolbar>
			</div>
			
			<div  class="lui_list_operation_page_top">
                <list:paging layout="sys.ui.paging.top" ></list:paging>
            </div>
			
			<!-- 操作按钮 -->
 			<div class="lui_list_operation_toolbar">
				<div class="lui_table_toolbar_inner">
				 	<kmss:authShow roles="ROLE_HRRECRUIT_EMAIL_SETTING">
						<ui:toolbar id="Btntoolbar" count="2">
							<!-- 新建 -->				
							<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
							<!-- 批量删除 -->
							<ui:button text="${lfn:message('button.deleteall')}" onclick="delAll();" order="2" ></ui:button>
						</ui:toolbar>
					</kmss:authShow>
				</div>
			</div> 
		</div> 
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/sender/sender_email_info/sysSenderEmailInfo.do?method=data&orderby=docCreateTime&ordertype=down&checkAuth=0'}
			</ui:source>
            
            <%-- 列表视图--%>
			<list:colTable layout="sys.ui.listview.columntable"
				name="columntable"
				rowHref="/sys/sender/sender_email_info/sysSenderEmailInfo.do?method=view&fdId=!{fdId}">
				<list:col-checkbox headerStyle="width:1%"/>
				<list:col-serial/>
				<list:col-html
					title="${ lfn:message('sys-profile:sys.email.info.docSubject')}"
					headerStyle="width:15%">
						{$
							{%row['icon']%}
							<span class="com_subject">{%row['docSubject']%}</span>
						$}
				</list:col-html>
				<list:col-auto props="fdEmailUsername,docCreator.fdName,docCreateTime"></list:col-auto>
			</list:colTable>
		</list:listview>
	 	<list:paging></list:paging>
	</template:replace>
</template:include>

<script type="text/javascript">
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/util/env'], function($, dialog, topic, env) {
	// 监听新建更新等成功后刷新
	topic.subscribe('successReloadPage', function() {
		topic.publish("list.refresh");
	});
 	// 增加
	window.add = function() {
		var url = env.fn.formatUrl("/sys/sender/sender_email_info/sysSenderEmailInfo.do?method=add");
		Com_OpenWindow(url);
 	};
	// 删除
	window.delAll = function(id) {
		var comfirmMsg = Com_Parameter.ComfirmDelete;
		
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
		var url  = '<c:url value="/sys/sender/sender_email_info/sysSenderEmailInfo.do?method=deleteall"/>';
		dialog.confirm(comfirmMsg, function(value) {
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
						dialog.success("${lfn:message('return.optSuccess')}");
					}
			   });
			}
		});
	};
});
</script>
