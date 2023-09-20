<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<!DOCTYPE HTML>
<html lang="en">
<!--PDF阅读-->
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
    <!-- default(white), black, black-translucent -->
    <meta name="apple-mobile-web-app-status-bar-style" content="default" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-touch-fullscreen" content="yes" />
    <meta name="apple-mobile-web-app-title" content="${filename}"> 
    <meta name="App-Config" content="fullscreen=yes,useHistoryState=no,transition=no">
    <meta name="format-detaction" content="telephone=no,email=no">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <meta name="HandheldFriendly" content="true">
    <meta name="MobileOptimized" content="750">
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <meta name="full-screen" content="yes">
    <meta name="x5-fullscreen" content="true">
    <meta name="browsermode" content="application">
    <meta name="x5-page-mode" content="app">
    <meta name="msapplication-tap-highlight" content="no">
    <meta name="renderer" content="webkit">

    <title>${filename}</title>
    <!-- 访问浏览器的图标 -->
    <link rel="shortcut icon" href="foxit_resource/imgs/favicon.ico" type="image/x-icon" />
    <!-- <link rel="stylesheet" href="foxit_resource/lib/UIExtension.css"> -->
    <link
		href="${ LUI_ContextPath}/sys/attachment/sys_att_main/foxit/foxit_resource/lib/UIExtension.css?s_cache=${ LUI_Cache }"
		rel="stylesheet">
     <!-- <link rel="stylesheet" href="foxit_resource/css/foxit.css"> -->
     <link
		href="${ LUI_ContextPath}/sys/attachment/sys_att_main/foxit/foxit_resource/css/foxit.css?s_cache=${ LUI_Cache }"
		rel="stylesheet">
   <!--  <script src="foxit_resource/lib/adaptive.js"></script> -->
   <script type="text/javascript"
					src="${ LUI_ContextPath}/sys/attachment/sys_att_main/foxit/foxit_resource/lib/adaptive.js?s_cache=${ LUI_Cache }"></script>
  <%--  <script type="text/javascript"
					src="${ LUI_ContextPath}/sys/attachment/sys_att_main/foxit/foxit_resource/js/jquery.ui.js?s_cache=${ LUI_Cache }"></script> --%>
</head>

<body>
<input type="hidden" name="downLoadUrl" value="${downLoadUrl}"> 
<input type="hidden" name="filename" value="${filename}"> 
<input type="hidden" name="download" value="${download}"> 
<input type="hidden" name="canPrint" value="${canPrint}"> 
<input type="hidden" name="licenseSN" value="${licenseSN}"> 
<input type="hidden" name="licenseKey" value="${licenseKey}"> 
<input type="hidden" name="markWordVar" value="${markWordVar}">
<c:if test="${isMobile != true }">
   <!-- 标题栏 -->
     <div class="readerTop">
        <div class="top_left">
			<div class="attachment">
				<ul>
					<li style="list-style-type:none;">
						<i class="icon"><img src="${LUI_ContextPath }/resource/style/common/fileIcon/pdf.png"></i>
						<strong>${filename}</strong><span class="text-muted">(${fileSize })</span>
						<c:if test="${download == 'true' }">
						    <a href="javascript:downloadAttAndLog('${fdId}');" class="download">${lfn:message('sys-attachment:viewer_hint_1')}</a>
						</c:if>
					    <a href="javascript:returnMainDoc('${fdId}');" class="download">${lfn:message('sys-attachment:sysAttMain.returnMainDoc')}</a>
					    
					 </li>
				</ul>
			</div>
		</div>
    </div>
   </c:if>
   <!--  <div id="readerTop">
    </div> -->
    <div id="pdf-ui"></div>
    <!-- <script src="foxit_resource/lib/preload-jr-worker.js"></script> -->
     <script type="text/javascript"
					src="${ LUI_ContextPath}/sys/attachment/sys_att_main/foxit/foxit_resource/lib/preload-jr-worker.js?s_cache=${ LUI_Cache }"></script>
   <!--  <script src="foxit_resource/lib/UIExtension.full.js"></script> -->
   <script type="text/javascript"
					src="${ LUI_ContextPath}/sys/attachment/sys_att_main/foxit/foxit_resource/lib/UIExtension.full.js?s_cache=${ LUI_Cache }"></script>
    <!-- <script src="foxit_resource/js/foxit.js"></script> -->
     <script type="text/javascript"
					src="${ LUI_ContextPath}/sys/attachment/sys_att_main/foxit/foxit_resource/js/foxit.js?s_cache=${ LUI_Cache }"></script>
					
	<script type="text/javascript">

	function downloadAttAndLog(fdId) {
		var downloadUrl = "${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId="+fdId+"&downloadType=manual&downloadFlag="+(new Date()).getTime();
		Com_OpenWindow(downloadUrl, "_blank");
	};
	
	function returnMainDoc(fdId) {
		
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog ) {
			$.ajax({
				url : "${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=findMainDocInfo",
				data : {
					'fdId':fdId
				},
				type : 'get',
				dataType: 'json',
				async: false,
				success: function(data) {
					if(data){
						if(data.url){
							var url = "${LUI_ContextPath }"+data.url;
							Com_OpenWindow(url, "_blank");
						}
						
					}else{
						dialog.alert("${lfn:message('sys-attachment:sysAttMain.returnMainDoc.error.get')}");
					}
					
				},
				error : function() {
					dialog.alert("${lfn:message('sys-attachment:sysAttMain.returnMainDoc.error.return')}");
				}
			});
		});
	}
	</script>
</body>

</html>