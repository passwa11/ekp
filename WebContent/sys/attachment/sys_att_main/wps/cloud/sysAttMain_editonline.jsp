<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="toolbar">
	</template:replace>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('button.save')}" onclick="editSaveWpsCloud();" order="1" />
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
		</ui:toolbar>
	<template:replace name="body">		
		<script>Com_IncludeFile("jquery.js");</script>
		<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
		<script>Com_IncludeFile("web-office-sdk-1.1.1.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
		<script>Com_IncludeFile("wps_cloud_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/cloud/js/","js",true);</script>
		<div>
			<div id="WPSCloudOffice_${sysAttMainForm.fdKey}">
			</div>
		</div>
		<script>
			var wpsCloudObj;
			$(document).ready(function(){
				var fdAttMainId ='${sysAttMainForm.fdId}';
		  		var fdKey = '${sysAttMainForm.fdKey}';
		  		var fdModelId = '${sysAttMainForm.fdModelId}';
		  		var fdModelName = '${sysAttMainForm.fdModelName}';
		  		wpsCloudObj = new WPSCloudOffice_AttachmentObject(fdAttMainId,fdKey,fdModelId,fdModelName,"write",false);
		  		wpsCloudObj.load();
				$("#office-iframe").height(window.innerHeight);
			}); 
			
			function editSaveWpsCloud(){
				seajs.use(['lui/dialog'], function(dialog) {
					const promise=wpsCloudObj.submit();
					var def = $.Deferred();
					promise.then(function (result) {
						if (!result) {
							def.resolve(false);
						}else{
							def.resolve(true);
						}
					});
					def.then(function (result) {
						if (result) {
							dialog.alert("${lfn:message('return.optSuccess')}");
							//如果是从合同模块进入此页面，判断父页面是否已经关闭
							if ('com.landray.kmss.km.agreement.model.KmAgreementApply' == wpsCloudObj.fdModelName
									&& 'mainOnline' == wpsCloudObj.fdKey) {
								//通知父页面
								if (window.opener != null && window.opener.refreshKmAgreementApplyMain) {
									window.opener.refreshKmAgreementApplyMain();
								}
							}
						}else{
							dialog.alert("${lfn:message('return.optFailure')}");
						}
					});
					
				});
				
			}
		</script>
	</template:replace>	
</template:include>	