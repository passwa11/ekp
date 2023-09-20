<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="security" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
	 	<list:data-column col="personInfoId">
			${security.fdPersonInfo.fdId}
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column headerClass="width100" col="staffId" title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStaffNo') }">
		  ${security.fdPersonInfo.fdStaffNo}
		</list:data-column>
		<list:data-column headerClass="width100" col="nameAccount" title="${ lfn:message('hr-staff:hrStaffPersonExperience.nameAccount') }">
		  ${security.fdPersonInfo.nameAccount}
		</list:data-column>
		<list:data-column headerClass="width220" property="fdIdCard" title="${ lfn:message('hr-staff:hrStaffSecurity.fdIdCard') }">
		</list:data-column>
		<list:data-column headerClass="width220" property="fdAccount" title="${ lfn:message('hr-staff:hrStaffSecurity.fdAccount') }">
		</list:data-column>
		<list:data-column headerClass="width220" col="fdDeliveryDate" title="${ lfn:message('hr-staff:hrStaffSecurity.fdDeliveryDate') }">
			<kmss:showDate value="${security.fdDeliveryDate }" type="date"></kmss:showDate>
		</list:data-column>
		<list:data-column headerClass="width120" property="fdDisabilityInsurance" title="${ lfn:message('hr-staff:hrStaffSecurity.fdDisabilityInsurance') }">
		</list:data-column>
		<list:data-column headerClass="width220" property="fdBirthPaymentBase" title="${ lfn:message('hr-staff:hrStaffSecurity.fdBirthPaymentBase') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdBirthUnitDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdBirthUnitDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdPlaceOfInsurancePayment" title="${ lfn:message('hr-staff:hrStaffSecurity.fdPlaceOfInsurancePayment') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdRemark" title="${ lfn:message('hr-staff:hrStaffSecurity.fdRemark') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdAmountReceivableTotalReceivable" title="${ lfn:message('hr-staff:hrStaffSecurity.fdAmountReceivableTotalReceivable') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdAmountReceivablePersonalTotal" title="${ lfn:message('hr-staff:hrStaffSecurity.fdAmountReceivablePersonalTotal') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdAmountReceivableUnitTotal" title="${ lfn:message('hr-staff:hrStaffSecurity.fdAmountReceivableUnitTotal') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdSocialInsuranceCompany" title="${ lfn:message('hr-staff:hrStaffSecurity.fdSocialInsuranceCompany') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdEndowmentInsurancePaymentBase" title="${ lfn:message('hr-staff:hrStaffSecurity.fdEndowmentInsurancePaymentBase') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdEndowmentInsurancePersonalDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdEndowmentInsurancePersonalDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdEndowmentInsuranceUnitDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdEndowmentInsuranceUnitDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdIndustrialAndCommercialInsurancePaymentBase" title="${ lfn:message('hr-staff:hrStaffSecurity.fdIndustrialAndCommercialInsurancePaymentBase') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdIndustrialAndCommercialInsuranceUnitDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdIndustrialAndCommercialInsuranceUnitDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdUnemploymentInsurancePaymentBase" title="${ lfn:message('hr-staff:hrStaffSecurity.fdUnemploymentInsurancePaymentBase') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdUnemploymentInsurancePersonalDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdUnemploymentInsurancePersonalDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdUnemploymentInsuranceUnitDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdUnemploymentInsuranceUnitDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdReproductiveMedicinePaymentBase" title="${ lfn:message('hr-staff:hrStaffSecurity.fdReproductiveMedicinePaymentBase') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdReproductiveMedicinePersonalDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdReproductiveMedicinePersonalDelivery') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="fdReproductiveMedicineUnitDelivery" title="${ lfn:message('hr-staff:hrStaffSecurity.fdReproductiveMedicineUnitDelivery') }">
		</list:data-column>
		<!-- 其它操作 -->
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${security.fdId}')">${ lfn:message('button.edit') }</a>
					<!-- 删除 -->
					<a class="btn_txt" href="javascript:_delete('${security.fdId}')">${ lfn:message('button.delete') }</a>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>