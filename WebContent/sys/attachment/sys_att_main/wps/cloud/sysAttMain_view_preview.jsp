<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">		
		<script>Com_IncludeFile("jquery.js");</script>
		<script>			
			$(document).ready(function(){
		  		var requestUrl = '${requestUrl}';
                 
		  			//window.location.href="http://xcwps.landray.com.cn/web/reader?file=" + encodeURIComponent(previewId)+"&isPrint=true&isDownload=true&isCopy=false&wpsPreview=1110110"; 
		  		window.location.href= requestUrl;
		  		
			});    
		</script>	
	</template:replace>	
</template:include>	