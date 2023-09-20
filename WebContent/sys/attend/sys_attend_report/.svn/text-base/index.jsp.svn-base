<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple4list">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('sys-attend:module.sys.attend') }" />
			<ui:varParam name="button">
				[
					{
						"text": "${ lfn:message('sys-attend:module.sys.attend') }",
						"href": "javascript:void(0)",
						"icon": "lui_icon_l_icon_89"
					}
				]
			</ui:varParam>				
		</ui:combin>
		<div class="lui_list_nav_frame">
			 <ui:accordionpanel>
			 	<c:import url="/sys/attend/nav.jsp" charEncoding="UTF-8">
				   <c:param name="key" value="sysAttendReport"></c:param>
				   <c:param name="criteria" value="sysAttendReportCriteria"></c:param>
				</c:import>		 
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="content">
		<ui:tabpanel layout="sys.ui.tabpanel.list">
			<ui:content title="${ lfn:message('sys-attend:sysAttendReport.report') }">
				<!-- 查询条件  -->
				<list:criteria id="sysAttendReportCriteria">
					<list:cri-ref title="${lfn:message('sys-attend:sysAttendReport.fdName') }" key="fdName" ref="criterion.sys.docSubject"/>
				</list:criteria>
				<!-- 列表工具栏 -->
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
							<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
							   <list:sort property="docCreateTime" text="${ lfn:message('sys-attend:sysAttendReport.docCreateTime') }" group="sort.list" value="down"></list:sort>
							</ui:toolbar>
						</div>
					</div>
					<!-- 分页 -->
					<div class="lui_list_operation_page_top">	
						<list:paging layout="sys.ui.paging.top" > 		
						</list:paging>
					</div>
					<div style="float:right">
						<div style="display: inline-block;vertical-align: middle;">
							<ui:toolbar count="3">
								<kmss:auth requestURL="/sys/attend/sys_attend_report/sysAttendReport.do?method=add">
									<ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2"></ui:button>
								</kmss:auth>
								<kmss:auth requestURL="/sys/attend/sys_attend_report/sysAttendReport.do?method=deleteall">
									<ui:button text="${lfn:message('button.delete')}" onclick="delDoc()" order="4"></ui:button>
								</kmss:auth>
							</ui:toolbar>
						</div>
					</div>
				</div>
				
				<ui:fixed elem=".lui_list_operation"></ui:fixed>
				 
				 
			 	<list:listview id="listview">
					<ui:source type="AjaxJson">
							{url:'/sys/attend/sys_attend_report/sysAttendReport.do?method=data'}
					</ui:source>
					<list:colTable isDefault="false"
						rowHref="/sys/attend/sys_attend_report/sysAttendReport.do?method=view&fdId=!{fdId}"  name="columntable">
						<list:col-checkbox></list:col-checkbox>
						<list:col-serial></list:col-serial>
						<list:col-auto props="fdName;docCreator.fdName;docCreateTime;"></list:col-auto>
					</list:colTable>
				</list:listview> 
				 
			 	<list:paging></list:paging>	 
			</ui:content>
		</ui:tabpanel>
		
	 	
	 	<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});	
				
				window.addDoc = function(){
					Com_OpenWindow("${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=add");
				}
				window.delDoc = function(){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
							values.push($(this).val());
						});
					if(values.length==0){
						dialog.alert('<bean:message key="page.noSelect"/>');
						return;
					}
					dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(value){
						if(value==true){
							var del_load = dialog.loading();
							$.post('${LUI_ContextPath}/sys/attend/sys_attend_report/sysAttendReport.do?method=deleteall',$.param({"List_Selected":values},true),function(data){
								if(del_load!=null){
									del_load.hide();
									topic.publish("list.refresh");
								}
								dialog.result(data);
							},'json');
						}
					});
				}
				
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});
				window.switchAttendPage = function(url,hash){
					url = Com_SetUrlParameter(url,'j_iframe','true');
					url = Com_SetUrlParameter(url,'j_aside','false');
					if(hash){
						url = url + hash;
					}
					LUI.pageOpen(url,'_rIframe');
				}
			});
		</script>
	</template:replace>
</template:include>
