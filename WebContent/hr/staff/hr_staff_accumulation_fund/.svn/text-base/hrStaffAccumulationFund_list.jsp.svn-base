<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="AccumulationFund" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
	 	<list:data-column col="personInfoId">
			${AccumulationFund.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column headerClass="width100" col="staffId" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		  ${AccumulationFund.fdPersonInfo.fdStaffNo}
		</list:data-column>
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
		  ${AccumulationFund.fdPersonInfo.nameAccount}
		</list:data-column>
		<list:data-column headerClass="width220" property="fdIdCard" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdIdCard') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdIndividualProvidentFundAccount" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdIndividualProvidentFundAccount') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdPlaceOfInsurancePayment" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdPlaceOfInsurancePayment') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdRemark" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdRemark') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdProvidentFundInsuranceCompany" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdProvidentFundInsuranceCompany') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdPaymentBase" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdPaymentBase') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdPersonalDelivery" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdPersonalDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdUnitDelivery" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdUnitDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdDeliveryAmount" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdDeliveryAmount') }">
		</list:data-column>
		<list:data-column headerClass="width120" col="fdDeliveryDate" title="${ lfn:message('hr-staff:hrStaffAccumulationFund.fdDeliveryDate') }">
			<kmss:showDate value="${AccumulationFund.fdDeliveryDate }" type="date"></kmss:showDate>
		</list:data-column>
	<!-- 其它操作 -->
		<list:data-column headerClass="width220" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${AccumulationFund.fdId}')">${ lfn:message('button.edit') }</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${AccumulationFund.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>