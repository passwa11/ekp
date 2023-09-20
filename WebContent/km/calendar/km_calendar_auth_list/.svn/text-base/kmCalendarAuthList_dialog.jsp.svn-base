<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<div style="margin:5px 10px;">
			<!-- 筛选器 -->
			<list:criteria id="criteria">
				<list:cri-ref style="width:145px;" key="keyword" ref="criterion.sys.docSubject" title="${lfn:message('km-calendar:kmCalendarAuthList.fdPerson') }"></list:cri-ref>
			</list:criteria>
			<!-- 操作栏 -->
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
						<ui:toolbar id="toolbar">
							<kmss:auth requestURL="/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do?method=add">
							    <ui:button text="${lfn:message('button.add')}" onclick="add();" order="1" ></ui:button>
							</kmss:auth>
							<kmss:auth requestURL="/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do?method=deleteall">
							    <ui:button id="del" text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="2" ></ui:button>
							</kmss:auth>
						</ui:toolbar>
					</div>
				</div>
			</div>
		
			<ui:fixed elem=".lui_list_operation"></ui:fixed>
			
			<!-- 列表 -->
	        <list:listview id="listview">
	            <ui:source type="AjaxJson">
	                {url:'/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do?method=data&fdPersonId=${param.fdPersonId }'}
	            </ui:source>
	            <!-- 列表视图 -->
	            <list:colTable isDefault="false" name="columntable">
	                <list:col-checkbox />
	                <list:col-serial/>
	                <list:col-auto props="fdPerson;fdAuthType;fdShareDate;operations" url="" /></list:colTable>
	        </list:listview>
	        <!-- 翻页 -->
	        <list:paging />
	    </div>
	
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog','lui/toolbar'],function($,topic,dialog,toolbar){
				if(!window.location.hash){
					window.location.hash="type=dialog";
				}
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
				//编辑
				window.edit = function(id) {
			 		if(id){
			 			var url="/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do?method=edit&fdId=" + id+ "&fdPersonId=${param.fdPersonId }";
			 			dialog.iframe(url, "${lfn:message('km-calendar:kmCalendarAuthList.text.edit')}", function(value){
							if (typeof value == "undefined") {
								topic.publish('list.refresh');
							}
						},{width:800,height:550});
			 		}
		 		};
				
				//新建
				window.add = function(){
					var url="/km/calendar/km_calendar_auth_list/kmCalendarAuthList_add.jsp?fdPersonId=${param.fdPersonId }";
					dialog.iframe(url, "${lfn:message('km-calendar:kmCalendarAuthList.text.add')}", function(value){
						if (typeof value == "undefined") {
							topic.publish('list.refresh');
						}
					},{width:800,height:550});
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
					var url = '<c:url value="/km/calendar/km_calendar_auth_list/kmCalendarAuthList.do?method=deleteall"/>';
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							
							$.ajax({
								url: url,
								type: 'POST',
								data:$.param({"List_Selected":values, "fdAuthCreatorId":"${param.fdPersonId }"},true),
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