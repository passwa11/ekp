package com.landray.kmss.third.ldap;

public class LdapSettingLang {

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public String getLangCountry() {
		return langCountry;
	}

	public void setLangCountry(String langCountry) {
		this.langCountry = langCountry;
	}

	public String getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(String fieldKey) {
		this.fieldKey = fieldKey;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	private String langName;

	private String langCountry;

	private String fieldKey;

	private String fieldValue;

}
