package com.landray.kmss.sys.attachment.util;

public interface SysAttConstant {
	/**
	 * 请求类型（webservice）
	 */
	public static final String REQTYPE_WEBSERVICE = "webservice";
	/**
	 * 请求类型（rest）
	 */
	public static final String REQTYPE_REST = "rest";

	/**
	 * 预览编辑工具
	 */
	public static final String ATTCONFIG_ONLINETOOLTYPE_JG = "0";// 金格

	public static final String ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST = "3";// WPS加载项
	
	public static final String ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD = "1"; // WPS云文档

	public static final String ATTCONFIG_ONLINETOOLTYPE_WPSCENTER = "4"; //WPS中台

	//新的附件预览不需要此项--(WPS-WebOffice)
	public static final String ATTCONFIG_ONLINETOOLTYPE_WPSWEBOFFICE = "2";

	/**
	 * 附件传输到其它地方的类型
	 */
	public static final String DOWNLOAD_TO_DING = "0";
	public static final String UPLOAD_TO_DING = "1";	
	
	public static final String WPS_HTML_VIEW="html";//html预览
	public static final String WPS_WINDOW_VIEW="window";//window在线预览
	public static final String WPS_LINUX_VIEW="linux";//linux在线预览
	public static final String I_WEB_VIEW="iWebPDF";//iWebPDF预览
	public static final String WPS_CLOUD_VIEW="cloud";//云文档预览
	public static final String WPS_OAASSIST_VIEW="oaassist";//wps加载项预览
	public static final String JG_VIEW="jg";//金格预览
	public static final String CONVERTING="converting";//正在转换中
	public static final String DOWNLOAD="download";//下载
	
	public static final String MOBILE_KK_WPS="0";//移动端是KK+WPS移动版
	public static final String MOBILE_WPS="1";//移动端是WPS移动版
	public static final String MOBILE_CLOUD="2";//移动端是云文档
	public static final String MOBILE_NONE="3";//移动端是无
	public static final String TYPE_OFF = "1";
	public static final String TYPE_ON = "2";

	public static final String DISABLED_FILE_TYPE = ".js;.bat;.exe;.sh;.cmd;.jsp;.jspx";

	//wps中台可阅读类型
	public static final String[] CENTER_VIEW_TYPE = {"doc","dot","wps","wpt","docx","dotx","docm","dotm","rtf","mht","mhtml","htm","xml","uot",
			"word_xml","uof","xls","xlt","et","ett","xlsx","xltx","csv","xlsm","xltm","html","xlsb","uos","uof","pptx","ppt","pot","potx", "pps",
			"ppsx","dps","dpt","pptm","potm","ppsm","uop","uof","ofd","pdf","jpeg","jpg","png","gif","bmp","tif","tiff","svg","vsd","vsdx","cdr",
			"psd","7z","tar","gz","zip","rar","log","txt","ini","inf","lrc","c","cpp","java","js","css","h","asm","s","asp","bat","bas","prg","cmd","dbf","epub","md","xlc","xlcx","mppx","dwg","dxf"};

	//点聚可阅读类型
	public static final String[] DIANJU_VIEW_TYPE = {"doc","docx","wps","xls","xlsx","et","ppt","pptx","dps","jpg","png","gif","tif","pdf","ofd","ceb","sep","aip","dwf","dwg","xlc","xlcx","mppx","vsd","rtf","dxf"};

	//福昕可阅读类型
	public static final String[] FOXIT_VIEW_TYPE = {"doc","docx","wps","xls","xlsx","et","ppt","pptx","dps","pdf","ofd","xlc","xlcx","mppx","vsd","rtf","dwg","dxf"};
}