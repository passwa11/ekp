<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:module.hr.staff') }" href="/hr/staff/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffPayrollIssuance')}"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('hr-staff:table.hrStaffPayrollIssuance') }" />
			<ui:varParam name="button">
				[
					{
						"text": "${ lfn:message('hr-staff:table.hrStaffPayrollIssuance') }",
						"href": "javascript:add();",
						"icon": "lui_icon_l_icon_31"
					}
				]
			</ui:varParam>			
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/hr/staff/import/nav.jsp" charEncoding="UTF-8">
					<c:param name="type" value="payrollIssuance" />
				</c:import>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="body">
	<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
	<!-- 筛选器 -->
	<list:criteria id="criteria">
			<list:cri-ref key="fdMessageName" ref="criterion.sys.docSubject" title="${lfn:message('hr-staff:hrStaffPayrollIssuance.fdmessageName') }">
			</list:cri-ref>
			<list:cri-ref key="fdCreateTime" ref="criterion.sys.calendar" title="${ lfn:message('hr-staff:hrStaffPayrollIssuance.fdCreateTime') }"></list:cri-ref>
		<list:cri-auto modelName="com.landray.kmss.hr.staff.model.HrStaffPayrollIssuance" property="fdCreator"/>		
		</list:criteria>	
	<!-- 排序 -->
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
					<list:sortgroup>
					    <list:sort property="fdCreateTime" text="${lfn:message('hr-staff:hrStaffPayrollIssuance.fdCreateTime') }" group="sort.list" value="down"></list:sort>
					</list:sortgroup>
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
					<ui:toolbar count="5">
						<ui:button text="${lfn:message('button.add') }" onclick="add();" order="1"></ui:button>
						<%--
						<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport')" order="2" ></ui:button>
						 --%>
						<ui:button text="${lfn:message('button.deleteall') }" onclick="deleteAll();" order="2"></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=list'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=view&fdId=!{fdId}" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdMessageName;fdCreateTime;fdCreator.fdName;fdResultMseeage;"></list:col-auto>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
			<script type="text/javascript">
					seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
						// 新建
						window.add = function() {
							Com_OpenWindow('<c:url value="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do" />?method=add');
						};
		
						// 删除
						window.deleteAll = function(id) {
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
							var url  = '<c:url value="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=deleteall"/>';
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
											}
											dialog.result(data);
										}
								   });
								}
							});
						};
					});
				</script>
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					setTimeout(function() {
						seajs.use(['lui/topic'], function(topic) {
							topic.publish('list.refresh');
						});
					}, 100);
				});
			});
		</script>
		
		
		
		
		</template:replace>
</template:include>