package com.landray.kmss.eop.basedata.imp.context;

import java.util.List;

import com.landray.kmss.eop.basedata.imp.validator.EopBasedataValidateContext;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;

public class EopBasedataImportColumn {
	/**
	 * 导入列数据类型：对象
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_OBJECT = "Object";
	/**
	 * 导入列数据类型：列表
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_LIST = "List";
	/**
	 * 导入列数据类型：字段串
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_STRING = "String";
	/**
	 * 导入列数据类型：整数
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_INTEGER = "Integer";
	/**
	 * 导入列数据类型：日期
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_DATE = "Date";
	/**
	 * 导入列数据类型：浮点数
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_DOUBLE = "Double";
	/**
	 * 导入列数据类型：枚举
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_ENUMS = "Enums";
	/**
	 * 导入列数据类型：枚举(单选)
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_RADIO = "Radio";
	/**
	 * 导入列数据类型：未使用(该类型仅导出时用到)
	 */
	public static final String FSSC_BASE_IMPORT_COLUMN_TYPE_UNUSED = "Unused";
	private SysDictCommonProperty fdProperty;
	public SysDictCommonProperty getFdProperty() {
		return fdProperty;
	}
	public void setFdProperty(SysDictCommonProperty fdProperty) {
		this.fdProperty = fdProperty;
	}
	private String fdName;
	private Integer fdColumn;
	private String fdType;
	private EopBasedataImportReference fdRel;
	private List<EopBasedataValidateContext> fdValidators;
	private String fdSwitchField;  //增加是否需要根据开关设置来判断
	public String getFdName() {
		return fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	public Integer getFdColumn() {
		return fdColumn;
	}
	public void setFdColumn(Integer fdColumn) {
		this.fdColumn = fdColumn;
	}
	public String getFdType() {
		return fdType;
	}
	public void setFdType(String fdType) {
		this.fdType = fdType;
	}
	public EopBasedataImportReference getFdRel() {
		return fdRel;
	}
	public void setFdRel(EopBasedataImportReference fdRel) {
		this.fdRel = fdRel;
	}
	public List<EopBasedataValidateContext> getFdValidators() {
		return fdValidators;
	}
	public void setFdValidators(List<EopBasedataValidateContext> fdValidators) {
		this.fdValidators = fdValidators;
	}
	
	public String getFdSwitchField() {
		return fdSwitchField;
	}
	
	public void setFdSwitchField(String fdSwitchField) {
		this.fdSwitchField = fdSwitchField;
	}
	public boolean hasValidator(String name){
		for(EopBasedataValidateContext ctx:getFdValidators()){
			if(ctx.getFdName().equals(name)){
				return true;
			}
		}
		return false;
	}
}
