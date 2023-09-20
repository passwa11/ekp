<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<ui:button text="${lfn:message('button.save')}" onclick="editSaveWps();" order="1" />
		<c:if test="${isWord=='true'}">
			<ui:button text="打开修订" onclick="enableRevisionStart();" order="2" />
			<ui:button text="关闭修订" onclick="enableRevisionEnd();" order="3" />
			<ui:button text="原始修订" onclick="showRevisionStart();" order="4" />
			<ui:button text="最终修订" onclick="showRevisionEnd();" order="5" />
			<ui:button text="接受所有修订" onclick="acceptAllRevisions();" order="6" />
			<ui:button text="拒绝所有修订" onclick="rejectAllRevisions();" order="7" />
			<%--<ui:button text="打印" onclick="wpsPrint();" order="7" />--%>
		</c:if>
		<ui:button text="${lfn:message('button.close')}" order="8" onclick="Com_CloseWindow();" />
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
				wpslinuxObj = new WPSLinuxOffice_AttachmentObject(fdAttMainId,fdKey,fdModelId,fdModelName,"write",fdFileName);
				wpslinuxObj.load();
			});

			function editSaveWps(){
				seajs.use(['lui/dialog'], function(dialog) {
					if(wpslinuxObj){
						var result=wpslinuxObj.submit();
						if(result)
							dialog.alert("${lfn:message('return.optSuccess')}");
						else
							dialog.alert("${lfn:message('return.optFailure')}");
					}
				});
			}

			function enableRevisionStart(){
				if(wpslinuxObj){
					wpslinuxObj.handleRevision(true);
				}
			}

			function enableRevisionEnd(){
				if(wpslinuxObj){
					wpslinuxObj.handleRevision(false);
				}
			}
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
			function acceptAllRevisions(){
				if(wpslinuxObj){
					wpslinuxObj.accent();
				}
			}
			function rejectAllRevisions(){
				if(wpslinuxObj){
					wpslinuxObj.reject();
				}
			}
			function wpsPrint(){
				if(wpslinuxObj){
					wpslinuxObj.print();
				}
			}
		</script>
	</template:replace>	
</template:include>	