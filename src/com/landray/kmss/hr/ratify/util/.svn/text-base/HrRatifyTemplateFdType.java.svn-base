package com.landray.kmss.hr.ratify.util;

public enum HrRatifyTemplateFdType {
	
	Entry("HrRatifyEntryDoc", "HrRatifyEntryTemplateForm"), Positive(
			"HrRatifyPositiveDoc",
			"HrRatifyPositiveTemplateForm"), Transfer("HrRatifyTransferDoc",
					"HrRatifyTransferTemplateForm"), Leave("HrRatifyLeaveDoc",
							"HrRatifyLeaveTemplateForm"), Fire(
									"HrRatifyFireDoc",
									"HrRatifyFireTemplateForm"), Retire(
											"HrRatifyRetireDoc",
											"HrRatifyRetireTemplateForm"), ReHire(
													"HrRatifyRehireDoc",
													"HrRatifyRehireTemplateForm"), Salary(
															"HrRatifySalaryDoc",
															"HrRatifySalaryTemplateForm"), Sign(
																	"HrRatifySignDoc",
																	"HrRatifySignTemplateForm"), Change(
																			"HrRatifyChangeDoc",
																			"HrRatifyChangeTemplateForm"), Remove(
																					"HrRatifyRemoveDoc",
																					"HrRatifyRemoveTemplateForm"), Other(
																							"HrRatifyOtherDoc",
																							"HrRatifyOtherTemplateForm");
	
	String fdKey;
	String fdValue;
	
	private HrRatifyTemplateFdType(String fdKey,String fdValue){
		this.fdKey = fdKey;
		this.fdValue = fdValue;
	}

	public String getFdKey() {
		return fdKey;
	}

	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}

	public String getFdValue() {
		return fdValue;
	}

	public void setFdValue(String fdValue) {
		this.fdValue = fdValue;
	}

	public static String getValue(String key) {
		String value = null;
		for (HrRatifyTemplateFdType type : HrRatifyTemplateFdType.values()) {
			if (key.equals(type.getFdKey())) {
				value = type.getFdValue();
				break;
			}
		}
		return value;
	}
}
