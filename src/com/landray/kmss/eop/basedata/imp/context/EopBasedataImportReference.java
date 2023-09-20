package com.landray.kmss.eop.basedata.imp.context;

import java.util.List;
import java.util.Map;

public class EopBasedataImportReference {
	/**
	 * 关联类型：弱关联
	 */
	public static final String REL_TYPE_WEAK = "weak";
	/**
	 * 关联类型：强关联
	 */
	public static final String REL_TYPE_STRONG = "strong";
	/**
	 * 值类型：静态固定值
	 */
	public static final String FIELD_TYPE_STATIC = "static";
	/**
	 * 值类型：值引用
	 */
	public static final String FIELD_TYPE_REF = "ref";
	private String fdKey;
	private List<Map<String,Object>> fdFields;
	private String fdType;
	private String fdReference;
	private String fdForeign;
	public String getFdForeign() {
		return fdForeign;
	}
	public void setFdForeign(String fdForeign) {
		this.fdForeign = fdForeign;
	}
	public String getFdKey() {
		return fdKey;
	}
	public void setFdKey(String fdKey) {
		this.fdKey = fdKey;
	}
	public List<Map<String, Object>> getFdFields() {
		return fdFields;
	}
	public void setFdFields(List<Map<String, Object>> fdFields) {
		this.fdFields = fdFields;
	}
	public String getFdType() {
		return fdType;
	}
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
	public String getFdReference() {
		return fdReference;
	}
	public void setFdReference(String fdReference) {
		this.fdReference = fdReference;
	}
}
