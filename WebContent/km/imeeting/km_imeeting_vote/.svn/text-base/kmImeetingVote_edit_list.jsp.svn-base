<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp" >
	<%-- 右侧内容区域 --%>
	<template:replace name="content"> 
		
		<%-- 操作栏 --%>
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
			
			<div style="float:right">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar count="3" id="Btntoolbar">
						<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="1" ></ui:button>
						<ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<%-- 列表视图 --%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/km/imeeting/km_imeeting_vote/kmImeetingVote.do?method=data&fdTemporaryId=${JsParam.fdMeetingId}'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" cfg-norecodeLayout="simple"
				rowHref="/km/imeeting/km_imeeting_vote/kmImeetingVote.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="docSubject;operations"></list:col-auto>				
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
		<script>
			seajs.use(['lui/jquery','lui/topic','lui/dialog','lui/toolbar'],function($,topic,dialog,toolbar){
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				window.addDoc = function() {
					Com_OpenWindow('<c:url value="/km/imeeting/km_imeeting_vote/kmImeetingVote.do" />?method=add&fdTemporaryId=${JsParam.fdMeetingId}');
		 		};
		 		
		 		window.edit = function(fdId){
		 			if(fdId)
		 				Com_OpenWindow('<c:url value="/km/imeeting/km_imeeting_vote/kmImeetingVote.do" />?method=edit&fdId='+fdId);
		 		}
		 		
		 		
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
					var url = '<c:url value="/km/imeeting/km_imeeting_vote/kmImeetingVote.do?method=deleteall"/>';
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