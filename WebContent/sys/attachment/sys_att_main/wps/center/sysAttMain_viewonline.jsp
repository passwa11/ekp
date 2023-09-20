<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
		<script>Com_IncludeFile("jquery.js");</script>
		<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
		<script>Com_IncludeFile("web-office-sdk-v1.1.16.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
		<script>Com_IncludeFile("wps_center_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
		<div id="WPSCenterOffice_${sysAttMainForm.fdKey}" class="wps-container">
		
		<script>
			$(document).ready(function(){
				//loadAttWps("${fdAttMainId}",'read');
				var inner="${inner}";
				var fdAttMainId ='${sysAttMainForm.fdId}';
		  		var fdKey = '${sysAttMainForm.fdKey}';
		  		var fdModelId = '${sysAttMainForm.fdModelId}';
		  		var fdModelName = '${sysAttMainForm.fdModelName}';
				var isHasHead="normal";
				if(inner){
					isHasHead="simple";
				}
				var directPreview = '${directPreview}';
				var wpsObj = new WPSCenterOffice_AttachmentObject(fdAttMainId,fdKey,fdModelId,fdModelName,"read",isHasHead, null, null,directPreview);
				wpsObj.load();
				var centerHeight = window.innerHeight;
				if(centerHeight>0){
					$("#office-iframe").height(centerHeight);
				}else{
					$("#office-iframe").height(top.window.innerHeight);
				}
			});
		</script>
	</template:replace>
</template:include>
