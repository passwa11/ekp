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
		<div id="WPSCLOUD_${sysAttMainForm.fdKey}" class="wpsCloudView">
		</div>	
		<script>	
  		var requestUrl = '${requestUrl}';
  		require(["mui/util", "dojo/_base/lang","dojo/domReady","dojo/dom-construct","dojo/topic"], function(util,lang,domReady,domConstruct,topic) {
  			domReady(function() {
  				var wps_key_id=document.getElementById("WPSCLOUD_${sysAttMainForm.fdKey}");
				var height = util.getScreenSize().h -60;
				var wpsIframe=domConstruct.create(
		            "iframe",
		            {
		              id: "office-iframe",
		              className: "web-office-iframe",
		              scrolling:"no",
		              frameborder:"0",
		              width:"100%",
		              height:height,
		              src: requestUrl
		            },
		            wps_key_id
		          );
				setTimeout(function(){topic.publish('/sys/attachment/wpsCloud/loaded', {iframe:wpsIframe}, {height:height})},1000);
  			});
  			
			
  		});
		</script>
	</template:replace>	
</template:include>	