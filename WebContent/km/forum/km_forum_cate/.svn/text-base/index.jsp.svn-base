<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-forum:kmForumCategory.fdName') }">
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
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" count="4">
						<list:sortgroup>
							<list:sort property="fdOrder" text="${lfn:message('km-forum:kmForumCategory.fdDisplayOrder') }" group="sort.list" value="up"></list:sort>
							<list:sort property="docCreateTime" text="${lfn:message('km-forum:kmForumCategory.docCreateTime') }" group="sort.list"></list:sort>
							<list:sort property="fdName" text="${lfn:message('km-forum:kmForumCategory.fdName') }" group="sort.list"></list:sort>
							<c:if test="${param.type=='forum'}">
							 <list:sort property="hbmParent.fdName" text="${lfn:message('km-forum:kmForumCategory.fdParentId') }" group="sort.list"></list:sort>
							</c:if>
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
						<c:if test="${param.type=='directory'}">
							<kmss:auth requestURL="/km/forum/km_forum_cate/kmForumCategory.do?method=add" requestMethod="GET">
								<ui:button text="${lfn:message('button.add')}" 
									onclick="addDirectory();" >
								</ui:button>
							</kmss:auth> 
						</c:if>
						<c:if test="${param.type=='forum'}">
							<kmss:auth requestURL="/km/forum/km_forum_cate/kmForumCategory.do?method=add" requestMethod="GET">
								<ui:button text="${lfn:message('button.add')}" 
									onclick="add();" >
								</ui:button>
							</kmss:auth>
							<ui:button text="${lfn:message('km-forum:kmForumCategory.button.changeDirectory')}" 
									onclick="changeSection();" >
							</ui:button>
						</c:if>
						<kmss:auth requestURL="/km/forum/km_forum_cate/kmForumCategory.do?method=deleteall" requestMethod="GET">
							<ui:button text="${lfn:message('button.deleteall')}" 
									onclick="deleteAll();" >
							</ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<c:if test="${param.type=='directory'}">
			<list:listview>
				<ui:source type="AjaxJson">
					{url:'/km/forum/km_forum_cate/kmForumCategory.do?method=list&type=directory'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				     rowHref="/km/forum/km_forum_cate/kmForumCategory.do?method=viewDirectory&fdId=!{fdId}">
					<list:col-checkbox></list:col-checkbox>
					<list:col-serial></list:col-serial>
					<list:col-auto props="fdName,docCreator.fdName,docCreateTime,operations"></list:col-auto>
				</list:colTable>
			</list:listview>
		</c:if>
		<c:if test="${param.type=='forum'}">
			<list:listview>
				<ui:source type="AjaxJson">
					{url:'/km/forum/km_forum_cate/kmForumCategory.do?method=list&type=forum'}
				</ui:source>
				<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				     rowHref="/km/forum/km_forum_cate/kmForumCategory.do?method=view&fdId=!{fdId}">
					<list:col-checkbox></list:col-checkbox>
					<list:col-serial></list:col-serial>
					<list:col-auto props="fdName,fdParent.fdName,authAllEditors,docCreator.fdName,docCreateTime,operations"></list:col-auto>
				</list:colTable>
			</list:listview>
		</c:if>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<script type="text/javascript">

	 	
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {

		 		topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});

			 	window.addDirectory = function(){
			 		Com_OpenWindow('<c:url value="/km/forum/km_forum_cate/kmForumCategory.do" />?method=addDirectory');
				};

			 	window.add = function(){
			 		Com_OpenWindow('<c:url value="/km/forum/km_forum_cate/kmForumCategory.do" />?method=add');
				};

			 	window.editDirectory = function(id){
					if(id){
						Com_OpenWindow('<c:url value="/km/forum/km_forum_cate/kmForumCategory.do?method=editDirectory&fdId=" />'+id,'_blank');
					}
				};
				
				window.edit = function(id){
					if(id){
						Com_OpenWindow('<c:url value="/km/forum/km_forum_cate/kmForumCategory.do?method=edit&fdId=" />'+id,'_blank');
					}
				};

		 		window.deleteAll = function(id){
		 			var values = [];
		 			if(id) {
		 				values.push(id);
		 			} else {
		 				$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
		 			}
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					var config = {
						url : '<c:url value="/km/forum/km_forum_cate/kmForumCategory.do?method=deleteall" />',
						data : $.param({"List_Selected":values}, true)
					};
					// 通用删除方法
					function delCallback(data){
						if(data.responseJSON) {
							var rep = data.responseJSON;
							var msg = rep.title;
							if(rep.message && rep.message instanceof Array) {
								msg = rep.message[0].msg;
							}
							dialog.failure(msg);
						} else {
							topic.publish("list.refresh");
							dialog.result(data);
						}
					}
					Com_Delete(config, delCallback);
				};

				window.changeSection = function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message bundle="km-forum" key="kmForumCategory.chooseCategory" />');
						return;
					}
					values.join(",");
					Com_OpenWindow('<c:url value="/km/forum/km_forum_cate/kmForumCategory_changDirectory.jsp" />?values='+values);
				};
				
		 	});
	 	</script>
	</template:replace>
</template:include>
