<%@page import="com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService"%>
<%@page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>


<%
	ISysFileLocationDirectService directService =
			SysFileLocationUtil.getDirectService();
	request.setAttribute("methodName", directService.getMethodName());
	request.setAttribute("uploadUrl", directService.getUploadUrl(request.getHeader("User-Agent")));
	request.setAttribute("isSupportDirect", directService.isSupportDirect(request.getHeader("User-Agent")));
	request.setAttribute("fileVal", directService.getFileVal());
%>
define(function(require, exports, module) {
	 var attachmentConfig = {
		// 上传路径
		uploadurl: '${uploadUrl}',
		// 上传方法名
		methodName: '${methodName}',
		// 是否支持直连模式
		isSupportDirect: ${isSupportDirect},
		// 文件key
		fileVal: '${fileVal}'|| null,
		//注册before-send-file事件
		beforeSendFile:true
	 }
	 
	 module.exports = attachmentConfig;
})