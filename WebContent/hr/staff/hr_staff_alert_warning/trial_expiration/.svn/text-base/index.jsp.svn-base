<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
		
<!-- 排序 -->
<div class="lui_list_operation">
	<!-- 排序 -->
	<div class="lui_list_operation_sort_btn">
		<div class="lui_list_operation_order_text">
			${ lfn:message('list.orderType') }：
		</div>
		<div class="lui_list_operation_sort_toolbar">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
			<list:sortgroup>
			    <list:sort property="hrStaffPersonInfo.fdTrialExpirationTime" text="${lfn:message('hr-staff:hrStaffPersonInfo.fdTrialExpirationTime') }" group="sort.list" value="up"></list:sort>
			</list:sortgroup>
			</ui:toolbar>
		</div>
	</div>
	<!-- 分页 -->
	<div class="lui_list_operation_page_top">
		<list:paging layout="sys.ui.paging.top" >
		</list:paging>
	</div>
</div>
<ui:fixed elem=".lui_list_operation"></ui:fixed>

<!-- 列表 -->
<list:listview id="listview">
	<ui:source type="AjaxJson">
		{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=trialList'}
	</ui:source>
	<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
		rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdId}" name="columntable">
		<list:col-serial></list:col-serial> 
		<list:col-auto props="fdName;fdLoginName;fdDeptName;fdTrialExpirationTime"></list:col-auto>
	</list:colTable>
</list:listview> 
<list:paging></list:paging>
