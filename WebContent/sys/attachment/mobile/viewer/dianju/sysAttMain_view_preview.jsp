<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.view">
	<template:replace name="head">
		<style>
			.viewContainer: {
				width:100%;
				height:100%;
			}
		</style>		
	</template:replace>
	<template:replace name="content">
		<div id="viewContainer" class="viewContainer">
		</div>	
		<script>	
  		var previewUrl = '${previewUrl}';
  		require(["mui/util", "dojo/_base/lang","dojo/domReady","dojo/dom-construct","dojo/topic"], function(util,lang,domReady,domConstruct,topic) {
  			domReady(function() {
  				var container=document.getElementById("viewContainer");
				var height = util.getScreenSize().h;
				domConstruct.create(
		            "iframe",
		            {
		              id: "dianju-iframe",
		              className: "dianju-iframe",
		              scrolling:"no",
		              frameborder:"0",
		              width:"100%",
		              height:height,
		              src: previewUrl
		            }, container
				);
  			});
  		});
		</script>
	</template:replace>	
</template:include>	