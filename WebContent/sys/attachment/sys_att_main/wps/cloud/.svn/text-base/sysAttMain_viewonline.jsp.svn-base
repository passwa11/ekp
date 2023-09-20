<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
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
			$(document).ready(function(){
				var inner="${inner}";
				var fdAttMainId ='${sysAttMainForm.fdId}';
		  		var fdKey = '${sysAttMainForm.fdKey}';
		  		var fdModelId = '${sysAttMainForm.fdModelId}';
		  		var fdModelName = '${sysAttMainForm.fdModelName}';
		  		var isHasHead="normal";
		  		if(inner){
		  			isHasHead="simple";
		  		}
		  		
		  		var wpsofd = '${wpsofd}';
		  		var requestUrl = '${requestUrl}';
                 
		  		if(wpsofd != '' && wpsofd == 'true') //使用wps查看OFD文件
		  		{
		  			//window.location.href="http://xcwps.landray.com.cn/web/reader?file=" + encodeURIComponent(previewId)+"&isPrint=true&isDownload=true&isCopy=false&wpsPreview=1110110"; 
		  			window.location.href= requestUrl;
		  		}else 
		  		{
		  			var wpsObj = new WPSCloudOffice_AttachmentObject(fdAttMainId,fdKey,fdModelId,fdModelName,"read",isHasHead);
					
					wpsObj.load();
					$("#office-iframe").height(window.innerHeight);
		  		}
		  		
			});    
		</script>	
	</template:replace>	
</template:include>	