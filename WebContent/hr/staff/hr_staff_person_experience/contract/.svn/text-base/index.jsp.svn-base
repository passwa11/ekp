<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
<style type='text/css'>
table td {
white-space: nowrap;
}
</style>
	<template:replace name="title">
		<c:out value="${ lfn:message('hr-staff:module.hr.staff') }"></c:out>
	</template:replace>
	<template:replace name="body">
	<script type="text/javascript">
			seajs.use(['theme!list']);	
	</script>
		<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
		<c:import url="/hr/staff/hr_staff_person_experience/experience_common_criteria.jsp" charEncoding="UTF-8">
			<c:param name="experienceType" value="contract" />
		</c:import>
		
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
					    <list:sort property="fdBeginDate" text="${lfn:message('hr-staff:hrStaffPersonExperience.contract.fdBeginDate') }" group="sort.list"></list:sort>
					    <list:sort property="fdEndDate" text="${lfn:message('hr-staff:hrStaffPersonExperience.contract.fdEndDate') }" group="sort.list"></list:sort>
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
						<kmss:authShow roles="ROLE_HRSTAFF_CREATE">
							<ui:button text="${lfn:message('hr-staff:hrStaff.import.button.add') }" onclick="_modify();" order="3"></ui:button>
						</kmss:authShow> 
						<kmss:authShow roles="ROLE_KMREVIEW_TRANSPORT_EXPORT">
									<ui:button
											text="${lfn:message('button.export')}" id="export"
											onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract')"
											order="2" >
									</ui:button>
								</kmss:authShow> 
						<kmss:authShow roles="ROLE_HRSTAFF_DELETE">
							<ui:button text="${lfn:message('button.deleteall') }" onclick="_delete('${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=deleteall');" order="3"></ui:button>
						</kmss:authShow> 
					</ui:toolbar>
				</div>
			</div>
		</div>
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 列表 -->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=list'}
			</ui:source>
			<!-- 列表视图 -->	
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable" 
				rowHref="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId=!{personInfoId}&anchor=experienceContract" name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				<list:col-auto props="staffId;nameAccount;fdDeptName;fdOrgPostNames;fdOrgRank;fdAffiliatedCompany;fdFirstLevelDepartment;fdSecondLevelDepartment;fdThirdLevelDepartment;fdName;fdContType;fdSignType;fdBeginDate;fdEndDate;fdContractPeriod;fdContStatus;operations"></list:col-auto>
			</list:colTable>
		</list:listview> 
		<list:paging></list:paging>
		
		<c:import url="/hr/staff/hr_staff_person_experience/experience_type_script.jsp" charEncoding="UTF-8">
			<c:param name="downloadTempletUrl" value="${LUI_ContextPath}/hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=downloadTemplet" />
			<c:param name="importType" value="contract" />
			<c:param name="importTitle" value="${lfn:message('hr-staff:hrStaffPersonExperience.type.contract') } - ${lfn:message('hr-staff:hrStaff.import.button.modify') }" />
		</c:import>
	</template:replace>
</template:include>
