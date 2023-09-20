package com.landray.kmss.hr.staff.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.hr.staff.model.HrStaffSecurity;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.web.action.ActionMapping;

public class HrStaffSecurityForm extends HrStaffBaseForm {
	private static final long serialVersionUID = 1L;

	private String fdName;
	private String fdIdCard;

	private String fdDisabilityInsurance;
	private String fdPlaceOfInsurancePayment;
	private String fdRemark;
	private String fdStaffId;
	private String docMainId;
	private String docMainName;
	private Double fdAmountReceivableTotalReceivable;
	private Double fdAmountReceivablePersonalTotal;
	private Double fdAmountReceivableUnitTotal;
	private String fdSocialInsuranceCompany;
	private Double fdEndowmentInsurancePaymentBase;
	private Double fdEndowmentInsurancePersonalDelivery;
	private Double fdEndowmentInsuranceUnitDelivery;
	private Double fdIndustrialAndCommercialInsurancePaymentBase;
	private Double fdIndustrialAndCommercialInsuranceUnitDelivery;
	private Double fdUnemploymentInsurancePaymentBase;
	private Double fdUnemploymentInsurancePersonalDelivery;
	private Double fdUnemploymentInsuranceUnitDelivery;
	private Double fdReproductiveMedicinePaymentBase;
	private Double fdReproductiveMedicinePersonalDelivery;
	private Double fdReproductiveMedicineUnitDelivery;
	private String fdDeliveryDate;
	private String fdAccount;
	private Double fdBirthUnitDelivery;
	private Double fdBirthPaymentBase;
	public Double getFdBirthUnitDelivery() {
		return fdBirthUnitDelivery;
	}

	public void setFdBirthUnitDelivery(Double fdBirthUnitDelivery) {
		this.fdBirthUnitDelivery = fdBirthUnitDelivery;
	}

	public Double getFdBirthPaymentBase() {
		return fdBirthPaymentBase;
	}

	public void setFdBirthPaymentBase(Double fdBirthPaymentBase) {
		this.fdBirthPaymentBase = fdBirthPaymentBase;
	}

	public String getFdAccount() {
		return fdAccount;
	}

	public void setFdAccount(String fdAccount) {
		this.fdAccount = fdAccount;
	}

	public String getFdDeliveryDate() {
		return fdDeliveryDate;
	}

	public void setFdDeliveryDate(String fdDeliveryDate) {
		this.fdDeliveryDate = fdDeliveryDate;
	}

	public Double getFdAmountReceivableTotalReceivable() {
		return fdAmountReceivableTotalReceivable;
	}

	public void setFdAmountReceivableTotalReceivable(Double fdAmountReceivableTotalReceivable) {
		this.fdAmountReceivableTotalReceivable = fdAmountReceivableTotalReceivable;
	}

	public Double getFdAmountReceivablePersonalTotal() {
		return fdAmountReceivablePersonalTotal;
	}

	public void setFdAmountReceivablePersonalTotal(Double fdAmountReceivablePersonalTotal) {
		this.fdAmountReceivablePersonalTotal = fdAmountReceivablePersonalTotal;
	}

	public Double getFdAmountReceivableUnitTotal() {
		return fdAmountReceivableUnitTotal;
	}

	public void setFdAmountReceivableUnitTotal(Double fdAmountReceivableUnitTotal) {
		this.fdAmountReceivableUnitTotal = fdAmountReceivableUnitTotal;
	}

	public String getFdSocialInsuranceCompany() {
		return fdSocialInsuranceCompany;
	}

	public void setFdSocialInsuranceCompany(String fdSocialInsuranceCompany) {
		this.fdSocialInsuranceCompany = fdSocialInsuranceCompany;
	}

	public Double getFdEndowmentInsurancePaymentBase() {
		return fdEndowmentInsurancePaymentBase;
	}

	public void setFdEndowmentInsurancePaymentBase(Double fdEndowmentInsurancePaymentBase) {
		this.fdEndowmentInsurancePaymentBase = fdEndowmentInsurancePaymentBase;
	}

	public Double getFdEndowmentInsurancePersonalDelivery() {
		return fdEndowmentInsurancePersonalDelivery;
	}

	public void setFdEndowmentInsurancePersonalDelivery(Double fdEndowmentInsurancePersonalDelivery) {
		this.fdEndowmentInsurancePersonalDelivery = fdEndowmentInsurancePersonalDelivery;
	}

	public Double getFdEndowmentInsuranceUnitDelivery() {
		return fdEndowmentInsuranceUnitDelivery;
	}

	public void setFdEndowmentInsuranceUnitDelivery(Double fdEndowmentInsuranceUnitDelivery) {
		this.fdEndowmentInsuranceUnitDelivery = fdEndowmentInsuranceUnitDelivery;
	}

	public Double getFdIndustrialAndCommercialInsurancePaymentBase() {
		return fdIndustrialAndCommercialInsurancePaymentBase;
	}

	public void setFdIndustrialAndCommercialInsurancePaymentBase(Double fdIndustrialAndCommercialInsurancePaymentBase) {
		this.fdIndustrialAndCommercialInsurancePaymentBase = fdIndustrialAndCommercialInsurancePaymentBase;
	}

	public Double getFdIndustrialAndCommercialInsuranceUnitDelivery() {
		return fdIndustrialAndCommercialInsuranceUnitDelivery;
	}

	public void setFdIndustrialAndCommercialInsuranceUnitDelivery(Double fdIndustrialAndCommercialInsuranceUnitDelivery) {
		this.fdIndustrialAndCommercialInsuranceUnitDelivery = fdIndustrialAndCommercialInsuranceUnitDelivery;
	}

	public Double getFdUnemploymentInsurancePaymentBase() {
		return fdUnemploymentInsurancePaymentBase;
	}

	public void setFdUnemploymentInsurancePaymentBase(Double fdUnemploymentInsurancePaymentBase) {
		this.fdUnemploymentInsurancePaymentBase = fdUnemploymentInsurancePaymentBase;
	}

	public Double getFdUnemploymentInsurancePersonalDelivery() {
		return fdUnemploymentInsurancePersonalDelivery;
	}

	public void setFdUnemploymentInsurancePersonalDelivery(Double fdUnemploymentInsurancePersonalDelivery) {
		this.fdUnemploymentInsurancePersonalDelivery = fdUnemploymentInsurancePersonalDelivery;
	}

	public Double getFdUnemploymentInsuranceUnitDelivery() {
		return fdUnemploymentInsuranceUnitDelivery;
	}

	public void setFdUnemploymentInsuranceUnitDelivery(Double fdUnemploymentInsuranceUnitDelivery) {
		this.fdUnemploymentInsuranceUnitDelivery = fdUnemploymentInsuranceUnitDelivery;
	}

	public Double getFdReproductiveMedicinePaymentBase() {
		return fdReproductiveMedicinePaymentBase;
	}

	public void setFdReproductiveMedicinePaymentBase(Double fdReproductiveMedicinePaymentBase) {
		this.fdReproductiveMedicinePaymentBase = fdReproductiveMedicinePaymentBase;
	}

	public Double getFdReproductiveMedicinePersonalDelivery() {
		return fdReproductiveMedicinePersonalDelivery;
	}

	public void setFdReproductiveMedicinePersonalDelivery(Double fdReproductiveMedicinePersonalDelivery) {
		this.fdReproductiveMedicinePersonalDelivery = fdReproductiveMedicinePersonalDelivery;
	}

	public Double getFdReproductiveMedicineUnitDelivery() {
		return fdReproductiveMedicineUnitDelivery;
	}

	public void setFdReproductiveMedicineUnitDelivery(Double fdReproductiveMedicineUnitDelivery) {
		this.fdReproductiveMedicineUnitDelivery = fdReproductiveMedicineUnitDelivery;
	}

	public String getDocMainId() {
		return docMainId;
	}

	public void setDocMainId(String docMainId) {
		this.docMainId = docMainId;
	}

	public String getDocMainName() {
		return docMainName;
	}

	public void setDocMainName(String docMainName) {
		this.docMainName = docMainName;
	}

	public String getFdStaffId() {
		return fdStaffId;
	}

	public void setFdStaffId(String fdStaffId) {
		this.fdStaffId = fdStaffId;
	}

	public String getFdDisabilityInsurance() {
		return fdDisabilityInsurance;
	}

	public void setFdDisabilityInsurance(String fdDisabilityInsurance) {
		this.fdDisabilityInsurance = fdDisabilityInsurance;
	}

	public String getFdPlaceOfInsurancePayment() {
		return fdPlaceOfInsurancePayment;
	}

	public void setFdPlaceOfInsurancePayment(String fdPlaceOfInsurancePayment) {
		this.fdPlaceOfInsurancePayment = fdPlaceOfInsurancePayment;
	}

	public String getFdRemark() {
		return fdRemark;
	}

	public void setFdRemark(String fdRemark) {
		this.fdRemark = fdRemark;
	}

	public String getFdIdCard() {
		return fdIdCard;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.fdIdCard = null;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}




	public void setFdIdCard(String fdIdCard) {
		this.fdIdCard = fdIdCard;
	}

	@Override
	public Class getModelClass() {
		// TODO Auto-generated method stub
		return HrStaffSecurity.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;
	@Override
	public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docMainId", new FormConvertor_IDToModel(
					"docMain", SysOrgElement.class));

		}
		return toModelPropertyMap;
	}
}
