package com.landray.kmss.sys.transport.model;

/**
 * @author 苏轶
 * 数据导入配置表
 */
public class SysTransportImportConfig extends Config
{
	// 只新增（主数据关键字所确定的记录应该不存在，否则报错）
	public static final int IMPORT_TYPE_ADD_ONLY = 1;
	// 只更新（主数据关键字所确定的记录应该已存在，否则报错）
	public static final int IMPORT_TYPE_UPDATE_ONLY = 2;
	// 新增或更新（如果主数据关键字所确定的记录已存在则更新，否则新增）
	public static final int IMPORT_TYPE_ADD_OR_UPDATE = 3;
	
	private Integer fdImportType;

	public Integer getFdImportType() {
		return fdImportType;
	}

	public void setFdImportType(Integer importType) {
		this.fdImportType = importType;
	}
}
