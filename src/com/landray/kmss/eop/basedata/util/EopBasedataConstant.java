package com.landray.kmss.eop.basedata.util;

import com.landray.kmss.util.ResourceUtil;
public class EopBasedataConstant {
	public static final String REQUEST_METHOD_POST = "POST";
	
	public static final String REQUEST_METHOD_GET = "GET";
	
	public static final String ENCODING_UTF_8 = "UTF-8";
	
	public static final String  SUCCESS_DELETE_CODE = "000";
	
	public static final String ERROR_DELETE_CODE = "999";
	
	public static final String  SUCCESS_DELETE_MSG = ResourceUtil.getString("success.delete.1","eps-common");
	
	public static final String ERROR_DELETE_MSG = ResourceUtil.getString("error.delete.0","eps-common");
	
	public static final String ERROR_DELETE_MSG2 = ResourceUtil.getString("error.delete.1","eps-common");
	
	public static final String PUBLISH_STATUS = "30";
	
	public static final String FD_STATUS_1 = "1";
	
	public static final String REPLACE_TEXT = ResourceUtil.getString("replace.text","eps-common");
	
	public static final String LAST_YEAR = ResourceUtil.getString("last.year","eps-common");
	
	public static final String THIS_YEAR = ResourceUtil.getString("this.year","eps-common");
	
	public static final String IMPORT_DATA_RUNTIMEEXCEPTION = ResourceUtil.getString("import.data.runtimeException","eps-common");
	
	public static final String SHEET_NAME = ResourceUtil.getString("sheet.name","eps-common");
}
