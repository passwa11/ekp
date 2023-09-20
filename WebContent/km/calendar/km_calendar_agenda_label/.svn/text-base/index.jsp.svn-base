<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
		<!-- 筛选器 -->
		<list:criteria id="criteria">
		     <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('km-calendar:kmCalendarAgendaLabel.fdName') }">
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
						    <list:sort property="fdAgendaModelName" text="${lfn:message('km-calendar:kmCalendarAgendaLabel.fdModelName') }" group="sort.list" value="down"></list:sort>
							<list:sort property="fdName" text="${lfn:message('km-calendar:kmCalendarAgendaLabel.fdName') }" group="sort.list"></list:sort>
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
					  	 <kmss:auth requestURL="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=importAgendaLabel">
					  	    <ui:button text="${lfn:message('km-calendar:km.calendar.tree.calendar.label.init')}"  onclick="importAgendaLabel();" order="1" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=add">
						    <ui:button text="${lfn:message('button.add')}"  onclick="add();" order="2" ></ui:button>
						</kmss:auth>
						<kmss:auth requestURL="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=deleteall">
						   <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll();" order="3" ></ui:button>
						</kmss:auth>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=list'}
			</ui:source>
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
			    rowHref="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=view&fdId=!{fdId}">
				<!--<list:col-checkbox name="List_Selected" ></list:col-checkbox>
				<list:col-serial title="${ lfn:message('page.serial')}"></list:col-serial>-->
				<list:col-auto props="fdCheckBox,fdSerial,fdName,fdDescription,fdAgendaModelName,fdIsAvailable,operations"></list:col-auto>
			</list:colTable>
		</list:listview>
		<br>
		<!-- 分页 -->
	 	<list:paging/>
	 	
	 	<script type="text/javascript">
		 	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
			 	// 导入
		 		window.importAgendaLabel = function() {
		 			Com_OpenWindow('<c:url value="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do" />?method=importAgendaLabel');
		 		};
		 		
		 		window.add = function() {
		 			Com_OpenWindow('<c:url value="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do" />?method=add');
		 		};
		 		
		 	    // 编辑
		 		window.edit = function(id) {
			 		if(id)
		 				Com_OpenWindow('<c:url value="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do" />?method=edit&fdId=' + id);
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
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.post('<c:url value="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=deleteall"/>',
									$.param({"List_Selected":values},true),delCallback,'json');
						}
					});
				};

				window.enable = function(id){
					if(id){
						$.post('<c:url value="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=updateIsAvailable"/>',
								$.param({"fdId":id, "fdIsAvailable":"true"}),delCallback,'json');
					}
				};

				window.disable = function(id){
					if(id){
						$.post('<c:url value="/km/calendar/km_calendar_agenda_label/kmCalendarAgendaLabel.do?method=updateIsAvailable"/>',
								$.param({"fdId":id, "fdIsAvailable":"false"}),delCallback,'json');
					}
				};
				
				window.delCallback = function(data){
					if(window.del_load!=null){
						window.del_load.hide();
					}
					topic.publish("list.refresh");
					dialog.result(data);
				};
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				
		 	});
	 	</script>
	</template:replace>
</template:include>
