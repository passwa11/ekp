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
			<ui:menu-item text="${ lfn:message('hr-staff:table.hrStaffAttendanceManage')}"></ui:menu-item>
		</ui:menu>
	</template:replace>
	<template:replace name="nav">
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('hr-staff:module.hr.staff') }" />
			<ui:varParam name="button">
				[
					{
						"text": "${ lfn:message('hr-staff:table.hrStaffAttendanceManageDetailed') }",
						"href": "javascript:void(0);",
						"icon": "lui_icon_l_icon_23"
					}
				]
			</ui:varParam>			
		</ui:combin>
		<div class="lui_list_nav_frame">
			<ui:accordionpanel>
				<c:import url="/hr/staff/import/nav.jsp" charEncoding="UTF-8">
					<c:param name="type" value="attendance" />
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
		<c:import url="/hr/staff/hr_staff_attendance_manage/manage_common_criterion.jsp" charEncoding="UTF-8">
			<c:param name="type" value="detailed" />
		</c:import>
		
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=list&fromModule=hr'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="/sys/time/sys_time_leave_detail/sysTimeLeaveDetail.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="fdPerson.fdName;fdLeaveName;fdStartTime;fdEndTime;fdLeaveTime;fdOprType;fdOprDesc;fdReview;operation;"></list:col-auto>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
		<c:import url="/hr/staff/hr_staff_attendance_manage/manage_type_script.jsp" charEncoding="UTF-8">
			<c:param name="downloadTempletUrl" value="${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/detailed/hrStaffAttendanceManageDetailed.do?method=downloadTemplet" />
			<c:param name="uploadActionUrl" value="${LUI_ContextPath}/hr/staff/hr_staff_attendance_manage/detailed/hrStaffAttendanceManageDetailed.do?method=fileUpload" />
			<c:param name="importTitle" value="${lfn:message('hr-staff:table.hrStaffAttendanceManageDetailed') } - ${lfn:message('hr-staff:hrStaff.import.button.import') }" />
		</c:import>
		<c:import url="/sys/time/sys_time_leave_detail/index_script.jsp" charEncoding="UTF-8">
		</c:import>
	</template:replace>
</template:include>
