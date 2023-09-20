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
		<script src="${LUI_ContextPath}/sys/attachment/mobile/viewer/wps/js/web-office-sdk-1.1.1.umd.js?s_cache=${MUI_Cache}"></script>
		<script>	
		var fdAttMainId ='${sysAttMainForm.fdId}';
  		var fdKey = '${sysAttMainForm.fdKey}';
  		var fdModelId = '${sysAttMainForm.fdModelId}';
  		var fdModelName = '${sysAttMainForm.fdModelName}';
  		var previewId = '${previewId}';
  		var wpsofd = '${wpsofd}';
  		var requestUrl = '${requestUrl}';
		var fdFileName = '${sysAttMainForm.fdFileName}';
         
  		if(wpsofd != '' && wpsofd == 'true') //使用wps查看OFD文件
  		{
  			//window.location.href="http://xcwps.landray.com.cn/web/reader?file=" + encodeURIComponent(previewId)+"&isPrint=true&isDownload=true&isCopy=false&wpsPreview=1110110"; 
  			/* require(['mui/device/adapter'], function(adapter) {
  				if(typeof(eval(adapter.open)) == 'function' ){
					adapter.open(requestUrl,'_blank');
				}else{
					location.href = requestUrl;
				}
  			}); */
  			
  			require(["mui/util", "dojo/_base/lang","dojo/domReady!","dojo/dom-construct","dojo/topic"], function(util,lang,domReady,domConstruct,topic) {
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
  			
  		}else
  		{
  			require(["sys/attachment/mobile/viewer/wps/js/WpsCloudAattachment", "dojo/domReady!" ], function(WpsCloudAattachment) {

  				console.log(WpsCloudAattachment);
  					 var wpsCloudAattachment = new WpsCloudAattachment({
  						 fdId: fdAttMainId,
  						 fdKey: fdKey,
  						 fdModelId: fdModelId,
  						 fdModelName: fdModelName,
  						 fdMode: "read",
						 fdFileName:fdFileName
  					});
  					wpsCloudAattachment.load();
  			}); 
  		}
		
		</script>
	</template:replace>	
</template:include>	