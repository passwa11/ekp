<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<template:include ref="person.home">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-bookmark:header.msg.myfavorite') }"/>
	</template:replace>
	<template:replace name="content">
		
		<!-- 筛选器 -->
		<list:criteria id="criteria" expand="false">
		     <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${lfn:message('sys-bookmark:sysBookmarkMain.docSubject') }">
			</list:cri-ref>
		</list:criteria>
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：</div>
			<%--排序按钮  --%>
			<div class="lui_list_operation_order_btn">
				<ui:toolbar>
					<list:sortgroup>
						<list:sort property="sysBookmarkMain.docCreateTime" text="${lfn:message('sys-bookmark:sysBookmarkMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
						<list:sort property="sysBookmarkMain.docSubject" text="${lfn:message('sys-bookmark:sysBookmarkMain.docSubject') }" group="sort.list"></list:sort>
					</list:sortgroup>
				</ui:toolbar>
			</div>
			
			<div class="lui_list_operation_order_btn">
				<list:selectall></list:selectall>
			</div>
			
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top">
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div class="lui_list_operation_toolbar">
				<ui:toolbar count="2">
				    <ui:button text="${lfn:message('sys-bookmark:button.setCategory')}"  onclick="ToSetCategory();" order="1" ></ui:button>
					<ui:button text="${lfn:message('button.add')}"  onclick="add();" order="2" ></ui:button>
				    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="3" ></ui:button>
				</ui:toolbar>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=list&category=&mydoc=true'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=view&fdId=!{fdId}">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="docSubject,docCategory.fdName,docCreateTime,fdUrl,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		 		
		 		window.ToSetCategory =function (){
					var cBox=document.getElementsByName("List_Selected");
					var cateIds="";
					
					for(var i=0;i<cBox.length;i++){
						if(!(cBox[i].checked)) continue;
						if(cateIds=="") cateIds=cBox[i].value;
						else cateIds+=";"+cBox[i].value;
					}
					
					if(cateIds=="" || cateIds==null) 
					{
						dialog.alert('<bean:message  bundle="sys-bookmark" key="sysBookmarkCategory.change.select"/>');
						return;
					}
					
					dialog.tree('sysBookmarkCategoryTreeService&parentId=!{value}&type=all','<bean:message key="dialog.tree.title" bundle="sys-bookmark"/>',afterSelectCategory);  
					
				};
			
				window.afterSelectCategory = function(rtnVal){
					if(rtnVal.value!=null){
						var docCategoryId=rtnVal.value;
						var values = [];
					    $("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
						
					    window.del_load = dialog.loading();
			           setTimeout(function(){
			        	   $.post('<c:url value="/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=setCategory"/>',
									$.param({"List_Selected":values,"docCategoryId":docCategoryId},true),delCallback,'json');
			           }, 100);
					} 
				};
		 		
		 		
			 	// 增加
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do" />?method=add');
		 		};
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do" />?method=edit&fdId=' + id);
		 		};
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
					var url = '<c:url value="/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=deleteall"/>';
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
		 	});
	 	</script>
		
	</template:replace>
</template:include>
