<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.view">
	<template:replace name="head">
		<style>
			.wpsCloudView: {
				width:100%;
				height:100%;
			}
		</style>		
	</template:replace>
	<template:replace name="content">
		<div id="WPSCENTER_${sysAttMainForm.fdKey}" class="wpsCloudView">
		</div>	
		<script src="${LUI_ContextPath}/sys/attachment/mobile/viewer/wps/center/js/web-office-sdk-v1.1.11.umd.js?s_cache=${MUI_Cache}"></script>
		<script>	
		var fdAttMainId ='${sysAttMainForm.fdId}';
  		var fdKey = '${sysAttMainForm.fdKey}';
  		var fdModelId = '${sysAttMainForm.fdModelId}';
  		var fdModelName = '${sysAttMainForm.fdModelName}';
  		var previewId = '${previewId}';
  		var directPreview = '${directPreview}';
  		{
  			require(["sys/attachment/mobile/viewer/wps/center/js/WpsCenterAattachment"], function(WpsCenterAattachment) {
  				
				var wpsAattachment = new WpsCenterAattachment({
					fdId: fdAttMainId,
					fdKey: fdKey,
					fdModelId: fdModelId,
					fdModelName: fdModelName,
					fdMode: "read",
					directPreview: directPreview,
				});
				wpsAattachment.load();
  			}); 
  		}
		
		</script>
	</template:replace>	
</template:include>	