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
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare')}"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('hr-staff:module.hr.staff') }" />
			<ui:varParam name="button">
				[
					{
						"text": "${ lfn:message('hr-staff:table.hrStaffEmolumentWelfare') }",
						"href": "javascript:add();",
						"icon": "lui_icon_l_icon_86"
					}
				]
			</ui:varParam>			
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/hr/staff/import/nav.jsp" charEncoding="UTF-8">
					<c:param name="type" value="benefits" />
				</c:import>
			</ui:accordionpanel>
		</div>
	</template:replace>
	<template:replace name="body">
	<script type="text/javascript">
			seajs.use(['theme!list']);	
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/data.js?s_cache=${LUI_Cache}"></script>
		<!-- 筛选器 -->
		<list:criteria id="criteria">
			<list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffEmolumentWelfare.criteria.fdKey') }" style="width:400px;">
			</list:cri-ref>
			<list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus" multi="true">
				<list:box-select>
					<list:item-select cfg-defaultValue="official">
						<ui:source type="Static">
							[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trial') }', value:'trial'},
							{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.practice') }', value:'practice'},
							{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.official') }',value:'official'},
							{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.temporary') }',value:'temporary'},
							{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trialDelay') }',value:'trialDelay'},
							{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.dismissal') }',value:'dismissal'},
							{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'leave'},
							{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.retire') }',value:'retire'}]
						</ui:source>
					</list:item-select>
				</list:box-select> 
			</list:cri-criterion>
			<list:cri-ref key="_fdDept" ref="criterion.sys.dept" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdOrgParent') }"></list:cri-ref>
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
					    <list:sort property="fdCreateTime" text="${lfn:message('model.fdCreateTime') }" group="sort.list" value="down"></list:sort>
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
						<ui:button text="${lfn:message('hr-staff:hrStaff.import.button.modify') }" onclick="_modify();" order="2"></ui:button>
						<ui:button text="${lfn:message('button.add') }" onclick="add();" order="3"></ui:button>
						<%--
						<ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport')" order="2" ></ui:button>
						 --%>
						<ui:button text="${lfn:message('button.deleteall') }" onclick="_delete();" order="4"></ui:button>
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=list'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{personInfoId}&anchor=emolumentWelfare" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="nameAccount;fdPayrollName;fdPayrollBank;fdPayrollAccount;fdSurplusAccount;fdSocialSecurityNumber;operations"></list:col-auto>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
		<!-- 模板下载表单 -->
		<form id="downloadTempletForm" action="${LUI_ContextPath}/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=downloadTemplet" method="post"></form>
		
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
			
				// 模板下载
				window.download = function() {
					$("#downloadTempletForm").submit();
				};
				
				// 新建
				window.add = function() {
					Com_OpenWindow('<c:url value="/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do" />?method=add');
				};

				// 编辑
				window.edit = function(id) {
					Com_OpenWindow('<c:url value="/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do" />?method=edit&fdId=' + id);
				};
			
				// 导入
				window._modify = function() {
					var actionUrl = "${LUI_ContextPath}/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=fileUpload";
					var downLoadUrl = "${LUI_ContextPath}/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=downloadTemplet";
					dialog.iframe('/hr/staff/upload_files/common_upload_download.jsp?uploadActionUrl=' + actionUrl + '&isRollBack=false'+'&downLoadUrl='+downLoadUrl, 
							'<bean:message key="table.hrStaffEmolumentWelfare" bundle="hr-staff"/>', function(data) {
							topic.publish('list.refresh');
					}, {
						width : 680,
						height : 380
					});
				};
			
				// 删除
				window._delete = function(id) {
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
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
						if(value == true) {
							window.del_load = dialog.loading();
							$.ajax({
								url : "${LUI_ContextPath}/hr/staff/hr_staff_emolument_welfare/hrStaffEmolumentWelfare.do?method=deleteall",
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
	</template:replace>
</template:include>
