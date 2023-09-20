<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
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
	
	String convertFileStatus = JgWebOffice.getConvertFileStatus(request);
	String promptInfo = "";
	if("-2".equals(convertFileStatus)){
		promptInfo = "文件转换服务未开启，暂无法在线查看文件，请联系管理员!";
	}else if("-1".equals(convertFileStatus)){
		promptInfo = "文件转换失败，请联系管理员!";
	}else if("0".equals(convertFileStatus)|| "1".equals(convertFileStatus)){
		promptInfo = "文件正在转换中，请稍后再打开!";
	}
	
	if (JgWebOffice.getPDFBigVersion().equals(JgWebOffice.PDF_OCX_BIG_VERSION_2018)) {
		if (isEdge||isMac||isFirefoxNo) {
			%>
			<div class="jg_tip_container">
				<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_prompt.jsp" charEncoding="UTF-8">
					<c:param name="promptInfo" value="<%=promptInfo%>"/>
				</c:import>
			</div>
			<%
			return;
		}
	} else {		
		if ((!JgWebOffice.isJGMULEnabled() && !isIE) || (JgWebOffice.isJGMULEnabled() && (isEdge || isChrome45 || isMac)) || (isIE&&is64)) {
			%>
			<div class="jg_tip_container">
				<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_prompt.jsp" charEncoding="UTF-8">
					<c:param name="promptInfo" value="<%=promptInfo%>"/>
				</c:import>
			</div>
			<%
			return;
		}
	}
%>