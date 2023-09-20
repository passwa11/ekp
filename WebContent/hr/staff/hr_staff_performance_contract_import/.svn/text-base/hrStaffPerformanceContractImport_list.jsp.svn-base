<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="PerformanceContractImport" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
	 	<list:data-column col="personInfoId">
			${PerformanceContractImport.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width100" col="staffId" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		  ${PerformanceContractImport.fdPersonInfo.fdStaffNo}
		</list:data-column>
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
		  ${PerformanceContractImport.fdPersonInfo.nameAccount}
		</list:data-column>
		<list:data-column headerClass="width120" property="fdFirstLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdFirstLevelDepartment') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdSecondLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdSecondLevelDepartment') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdThirdLevelDepartment" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdThirdLevelDepartment') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdJobNature" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdJobNature') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdExpiryDate" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdExpiryDate') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdBeginDate" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdBeginDate') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdEvaluationDimension" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdEvaluationDimension') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdEvaluationIndex" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdEvaluationIndex') }">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdTargetValue" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdTargetValue') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdWeight" title="${ lfn:message('hr-staff:hrStaffPerformanceContractImport.fdWeight') }">
		</list:data-column>
	<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${PerformanceContractImport.fdId}')">${ lfn:message('button.edit') }</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${PerformanceContractImport.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>