<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref style="width:145px;" key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-imeeting:kmImeetingRes.fdName') }"></list:cri-ref>
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
							<list:sort property="fdOrder" text="${lfn:message('km-imeeting:kmImeetingRes.fdOrder') }" group="sort.list"  value="up"></list:sort>
							<list:sort property="fdName" text="${lfn:message('km-imeeting:kmImeetingRes.fdName') }" group="sort.list"></list:sort>
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
						<%
						 	if(KmImeetingConfigUtil.isBoenEnable()){
						 %>
						 	  <ui:button text="会议室同步" onclick="addSyncBoen();" order="1" ></ui:button>
						 <%} %>
						<c:if test="${not empty JsParam.docCategoryId}">
							<kmss:auth requestURL="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=add&categoryId=${JsParam.docCategoryId}">
							    <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
							</kmss:auth>
						</c:if>
						<c:if test="${empty JsParam.docCategoryId}">
							<kmss:auth requestURL="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=add">
						    	<ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
							</kmss:auth>
						</c:if>
						<kmss:auth requestURL="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=deleteall">
						    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
						     <!-- 快速排序 -->
							<c:import url="/sys/profile/common/change_order_num.jsp" charEncoding="UTF-8">
								<c:param name="modelName" value="com.landray.kmss.km.imeeting.model.KmImeetingRes"></c:param>
								<c:param name="property" value="fdOrder"></c:param>
								<c:param name="min" value="0"></c:param>
							</c:import>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
	
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=list&dataWithAdmin=true&docCategoryId=${JsParam.docCategoryId}&authReaderNoteFlag=${JsParam.authReaderNoteFlag }&contentType=json'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable"
				rowHref="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-auto props="fdOrder,fdName,docCategory.fdName,fdAddressFloor,fdSeats,fdIsSeatPlan,docKeeper.fdName,fdIsAvailable,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging/>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				window.addSyncBoen = function(){
					dialog.confirm('该操作在开启铂恩会议集成之后，首次同步历史数据调用，是否继续?',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							var url = '<c:url value="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=addSyncToBoen"/>';
							$.ajax({
								url: url,
								type: 'GET',
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.result(data.responseJSON);
								},
								success: function(){
									if(window.del_load!=null){
										window.del_load.hide();
									}
									dialog.success("同步成功！");
								}
						   });
						}
					});
				};
				
				//新建
				window.add = function(){
					var categoryId = '${JsParam.docCategoryId}';
					if(!categoryId){
						dialog.simpleCategoryForNewFile(
								'com.landray.kmss.km.imeeting.model.KmImeetingResCategory',
								'/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=add&categoryId=!{id}',false,null,null,'${JsParam.docCategoryId}');
					}else{
						Com_OpenWindow('<c:url value="/km/imeeting/km_imeeting_res/kmImeetingRes.do" />?method=add&categoryId=${JsParam.docCategoryId}');
					}
				};
				//编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/km/imeeting/km_imeeting_res/kmImeetingRes.do" />?method=edit&fdId=' + id);
		 		};
				//删除
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
					var url = '<c:url value="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=deleteall"/>';
					var $data = $.param({"List_Selected":values},true);
					var $type = 'POST';
					if(id) {
						url = '<c:url value="/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=delete"/>';
						$data = $.param({"fdId":id},true);
						$type = 'GET';
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: url,
								type: $type,
								data: $data,
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