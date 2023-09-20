<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page
	import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.code.spring.SpringBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<%
	String attId = request.getParameter("fdId");

	ISysAttMainCoreInnerService service = (ISysAttMainCoreInnerService) SpringBeanUtil
			.getBean("sysAttMainService");
	SysAttMain main = (SysAttMain) service.findByPrimaryKey(attId);

	String contentType = main.getFdContentType();

	if ("video/x-m4v".equals(contentType)) {
		contentType = "video/mp4";
	}

	if (!"video/mp4".equals(contentType)) {
		contentType = "video/x-flv";
	}

	request.setAttribute("contentType", contentType);
	request.setAttribute("attId", attId);
%>

<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link
	href="${ LUI_ContextPath}/sys/attachment/sys_att_main/video/video-js.min.css?s_cache=${ LUI_Cache }"
	rel="stylesheet">

</head>
<body scroll="no" style="margin: 0; padding: 0;"
	onbeforeunload="RunOnBeforeUnload()">

	<div id="video" style="width: 100%; height: 500px;"></div>

	<!--[if IE 8]>
	<script
		src="${LUI_ContextPath }/sys/attachment/sys_att_main/video/videojs-ie8.min.js?s_cache=${ LUI_Cache }"></script>
	<![endif]-->

	<script
		src="${LUI_ContextPath }/sys/attachment/sys_att_main/video/video.min.js?s_cache=${ LUI_Cache }"></script>

	<script>
	
		seajs.use('sys/attachment/viewer/video/video.js',function(video){
			video.init('${attId}','${contentType}')
		})
		
		var authDownload = false;
		<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${param.fdId}" 
				requestMethod="GET">
			authDownload = true;
		</kmss:auth>
		
		// ie关闭浏览器时销毁视频对象
		function RunOnBeforeUnload() {
		  var video = document.getElementById('video-component')
		  if (video) {
		    video.parentNode.removeChild(video)
		  }
		}
		
	</script>

	<c:if test="${empty param.isOpen}">

		<script>
			if (!document.fullscreenEnabled
					&& !document.webkitFullscreenEnabled
					&& !document.mozFullScreenEnabled
					&& !document.msFullscreenEnabled) {

				video.on('fullscreenchange', function() {

					this.pause();

					newwindow = window.open(window.location.href
							+ "&isOpen=true");

					if (window.focus) {
						newwindow.focus()
					}

				});
			}
		</script>

	</c:if>

</body>
</html>
