<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil,com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%
	//ie浏览器识别
	Boolean isIE = Boolean.FALSE;
	if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1
			|| request.getHeader("User-Agent").toUpperCase()
					.indexOf("TRIDENT") > -1) {
		isIE = Boolean.TRUE;
	}
	//判断是不是64位浏览器
	Boolean is64 = Boolean.FALSE;
	if (request.getHeader("User-Agent").toUpperCase().indexOf("X64") > -1) {
		is64 = Boolean.TRUE;
	}

	//edge浏览器识别
	Boolean isEdge = Boolean.FALSE;
	if (request.getHeader("User-Agent").toUpperCase().indexOf("EDGE") > -1) {
		isEdge = Boolean.TRUE;
	}
	//chrome浏览器45版本识别
	Boolean isChrome45 = Boolean.FALSE;
	int index = request.getHeader("User-Agent").toUpperCase().indexOf("CHROME/");
	if(index > 0){
		if(Integer.parseInt(request.getHeader("User-Agent").toUpperCase().substring(index+7, index+9)) > 44){
			isChrome45 = Boolean.TRUE;
		}
	}
	
	//Firefox浏览器
	Boolean isFirefoxNo = Boolean.FALSE;
	int index_Firefox = request.getHeader("User-Agent").toUpperCase().indexOf("FIREFOX/");
	if(index_Firefox > 0){
		if(Integer.parseInt(request.getHeader("User-Agent").toUpperCase().substring(index_Firefox+8, index_Firefox+10)) > 51){
			isFirefoxNo = Boolean.TRUE;
		}
	}
	
	//Mac操作系统识别
	Boolean isMac = Boolean.FALSE;
	if (request.getHeader("User-Agent").toUpperCase().indexOf("MAC") > -1) {
		isMac = Boolean.TRUE;
	}
	if("windows".equals(JgWebOffice.getOSType(request))){
		if (JgWebOffice.getJGBigVersion().equals(JgWebOffice.JG_OCX_BIG_VERSION_2015)) {
			if (isEdge||isMac||isFirefoxNo) {
				request.setAttribute("supportJg", "unsupported");
			}else{
				request.setAttribute("supportJg", "supported");
			}
		} else {
			if ((!JgWebOffice.isJGMULEnabled() && !isIE) || (JgWebOffice.isJGMULEnabled() && (isEdge || isChrome45||isMac))||(isIE&&is64)) {
				request.setAttribute("supportJg", "unsupported");
			}else{
				request.setAttribute("supportJg", "supported");
			}
		}
	} else if ("true".equals(JgWebOffice.getIsJGHandZzkkEnabled())) {
		request.setAttribute("supportJg", "supported");
	}
	
	if(SysAttWpsCloudUtil.isEnable()||SysAttWpsCenterUtil.isEnable()){
		request.setAttribute("supportJg", "unsupported");
	}
%>