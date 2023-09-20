<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.io.FilenameUtils" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="fdFileName" value="${sysAttMainForm.fdFileName}" />
<%
	String fdFileName=(String)pageContext.getAttribute("fdFileName");
	Boolean isWord=false;
	String ext= FilenameUtils.getExtension(fdFileName);
	if("doc".equals(ext.toLowerCase())||"docx".equals(ext.toLowerCase())||"wps".equals(ext.toLowerCase())){
		isWord=true;
	}
	pageContext.setAttribute("isWord", isWord);
%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="toolbar">
	</template:replace>
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="10">
		<c:if test="${isWord=='true'}">
			<ui:button text="原始修订" onclick="showRevisionStart();" order="4" />
			<ui:button text="最终修订" onclick="showRevisionEnd();" order="5" />
		</c:if>
		<ui:button text="${lfn:message('button.close')}" order="7" onclick="Com_CloseWindow();" />
	</ui:toolbar>
	<template:replace name="body">
		<script>Com_IncludeFile("jquery.js");</script>
		<script>Com_IncludeFile("wps_linux_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/oaassist/linux/js/","js",true);</script>
		<div style="width: 100%;height: 100%">
			<div id="wpsLinux_${sysAttMainForm.fdKey}" style="width: 100%;height: 700px;">
			</div>
		</div>
		<script>
			var wpslinuxObj;
			$(document).ready(function(){
				var fdAttMainId ='${sysAttMainForm.fdId}';
				var fdKey = '${sysAttMainForm.fdKey}';
				var fdModelId = '${sysAttMainForm.fdModelId}';
				var fdModelName = '${sysAttMainForm.fdModelName}';
				var fdFileName = '${sysAttMainForm.fdFileName}';
				wpslinuxObj = new WPSLinuxOffice_AttachmentObject(fdAttMainId,fdKey,fdModelId,fdModelName,"read",fdFileName);
				wpslinuxObj.load();
			});

			function showRevisionStart(){
				if(wpslinuxObj){
					wpslinuxObj.showRevision(0);
				}
			}
			function showRevisionEnd(){
				if(wpslinuxObj){
					wpslinuxObj.showRevision(2);
				}
			}
		</script>
	</template:replace>
</template:include>