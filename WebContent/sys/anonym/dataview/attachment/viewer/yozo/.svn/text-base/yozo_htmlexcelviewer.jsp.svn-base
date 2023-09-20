<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%><template:include
	ref="default.simple">
	<template:replace name="title">
		<%
			Object obj = request.getAttribute("fileFullName");
					if (obj != null) {
						String fileName = obj.toString();
						if (fileName.indexOf(".") >= 0)
							out.append(fileName.substring(0, fileName
									.lastIndexOf(".")));

					}
			request.setAttribute("fileKeySufix", "noSufix");
		%>
	</template:replace>
	<template:replace name="head">
		<template:super />
		<link rel="stylesheet" type="text/css" href="${ fullContextPath}/sys/anonym/dataview/attachment/viewer/resource/common/css/common.css?s_cache=${LUI_Cache }" />
		<link rel="stylesheet" type="text/css" href="${ fullContextPath}/sys/anonym/dataview/attachment/viewer/resource/common/css/excel.css?s_cache=${LUI_Cache }" />
		<c:choose>
			<c:when test="${toolPosition == 'top'}">
				<link rel="stylesheet" type="text/css" href="${ fullContextPath}/sys/anonym/dataview/attachment/viewer/resource/common/css/innerviewer.css?s_cache=${LUI_Cache }" />
			</c:when>
			<c:otherwise>
				<link rel="stylesheet" type="text/css" href="${ fullContextPath}/sys/anonym/dataview/attachment/viewer/resource/common/css/newopen.css?s_cache=${LUI_Cache }" />
			</c:otherwise>
		</c:choose>
		
		<script type="text/javascript" src="${fullContextPath }/sys/anonym/dataview/attachment/viewer/resource/common/js/commonfuncs.js?s_cache=${LUI_Cache }"></script>
		<script type="text/javascript">
			var toolPosition = "${toolPosition}";
			var converterKey = "${converterKey}";
			var newOpen = "${newOpen}";
			var viewerStyle = "${viewerStyle}";
			var fileKeySufix="${fileKeySufix}";
			var fullScreen="${lfn:escapeJs(fullScreen)}";
			var canCopy="${canCopy}";
			var fdId="${fdId}";
			var currentPage=parseInt("${lfn:escapeJs(currentPage)}");
			var dataSrc=Com_Parameter.ContextPath+"sys/anonym/sysAnonymData.do";
			var goFullScreenHint = "${lfn:message('sys-attachment:viewer_hint_fullscreen')}";
			var exitFullScreenHint = "${lfn:message('sys-attachment:viewer_hint_cancelfullscreen')}";
			var totalPageNum = parseInt("${htmlPageCount}");
			var viewerParam = ${viewerParam};
			var waterMarkConfig=${waterMarkConfig};
			LUI.ready( function() {
				commonFuncs.initialHandlers();
				commonFuncs.addCss("commoncss",Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/css/common.css");
				commonFuncs.addCss("excelcss",Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/css/excel.css");
				var browser=commonFuncs.getBrowser();
				if(browser=="IE"&&fullScreen=="yes"){
					commonFuncs.fsCommonHandler();
					commonFuncs.unBindFsToolHover();
					commonFuncs.bindFsKeyDown();
					commonFuncs.addCss("fullscreencss",Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/css/fullscreen.css");					
					commonFuncs.addJs("exceljs",Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/js/excel.js",currentPage);
				}else{
					commonFuncs.addJs("exceljs",Com_Parameter.ContextPath+"sys/anonym/dataview/attachment/viewer/resource/common/js/excel.js",currentPage);
				}
			});
</script>
	</template:replace>
	<template:replace name="body">
<c:if test="${!canPrint}">
  <style>
    @media print { 
      #readerOuterContainer { display:none; } 
    } 
</style>
</c:if>
		<div id="readerOuterContainer">
			<div id="readerTop">
				<div class="top_left">
					<div class="attachment">
						<ul>
							<li>
								<i class="icon"><img src="${fullContextPath }/resource/style/common/fileIcon/${attIconName }" /></i>
								<strong>${fileFullName }</strong><span class="text-muted">(${fileSize })</span>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="readerMain">				
				<div id="mainMiddle" class="excelMainMiddle">
					
				</div>
				<div id="zoomButton">
					<a class="zoom-icon" href="javascript:void(0);" title="${lfn:message('sys-attachment:viewer_hint_cancelfullscreen')}" onclick="commonFuncs.changeScreenStatus();"></a>
				</div>
			</div>
			<div id="readerBottom" class="excelBottom">
				<div id="bottomContainer">
					<div class="zoom">
						<ul>
							<li><a class="zoom_a" href="javascript:void(0);" title="${lfn:message('sys-attachment:viewer_hint_fullscreen')}" onclick="commonFuncs.changeScreenStatus(event);"></a></li>
						</ul>						
					</div>
				</div>
			</div>
		</div>
	</template:replace>
</template:include>