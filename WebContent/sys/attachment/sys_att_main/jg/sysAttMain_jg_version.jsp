<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%
	
	//判断金格控件大版本号2009 or 2015
	String JGBigVersion = JgWebOffice.getJGBigVersion();
	request.setAttribute("JGBigVersion", JGBigVersion);	
	
	//判断金格pdf控件大版本号iWebPDF or iWebPDF2018
	String JGBigWebPDFVersion = JgWebOffice.getPDFBigVersion();
	request.setAttribute("JGBigWebPDFVersion", JGBigWebPDFVersion);
	
	//判断当前操作系统
	String osType = JgWebOffice.getOSType(request);
	request.setAttribute("osType", osType);
	
	//判断是否开启国产化控件
	String isEnabled = "false";
	String isEnabledZZKK = JgWebOffice.getIsJGHandZzkkEnabled();
	if (null != isEnabledZZKK && isEnabledZZKK.equals("true")) {
		isEnabled = "true";
	}
	request.setAttribute("isEnabled", isEnabled);
	
	//判断配置的金格控件类型
	String jgPluginType = JgWebOffice.getJGPluginType();
	request.setAttribute("jgPluginType", jgPluginType);
	
	//判断金格2003版本使用的办公软件类型
	String jgOfficeType = JgWebOffice.getJGOfficeType();
	request.setAttribute("jgOfficeType", jgOfficeType);
	
%>

var jgBigVersionParam = "${JGBigVersion}";

var jgBigWebPDFVersionParam = "${JGBigWebPDFVersion}";

var userOsTypeParam = "${osType}";

var isEnabledParam = "${isEnabled}";

var jgPluginType = "${jgPluginType}";

var jgOfficeType = "${jgOfficeType}";
