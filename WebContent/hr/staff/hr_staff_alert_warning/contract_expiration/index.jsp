<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 排序 -->
<div class="lui_list_operation">
	<div class="lui_list_operation_sort_btn">
		<div class="lui_list_operation_order_text">
			${ lfn:message('list.orderType') }：
		</div>
		<div class="lui_list_operation_sort_toolbar">
			<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
			<list:sortgroup>
			    <list:sort property="fdEndDate" text="${lfn:message('hr-staff:hr.staff.contract.expires.date') }" group="sort.list" value="up"></list:sort>
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
		{url:'/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=contractList'}
	</ui:source>
	<!-- 列表视图 -->	
	<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
			rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{fdPersonInfo.fdId}&anchor=experienceContract"  name="columntable" >
		<list:col-serial></list:col-serial> 
		<list:col-auto props="fdPersonName;fdLoginName;fdName;fdBeginDate;fdEndDate"></list:col-auto>
	</list:colTable>
</list:listview> 
<list:paging></list:paging>
