<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	boolean isWpsoaassist=SysAttWpsoaassistUtil.isEnable();
	request.setAttribute("isWpsoaassist", isWpsoaassist);

	String viewerStyle = request.getAttribute("viewerStyle").toString();
	String fileKeySufix = request.getParameter("fileKeySufix");
	boolean isLowerThanIE8 = (SysAttViewerUtil.isLowerThanIE8(request) ? true : false);
	String highFidelity = request.getAttribute("highFidelity").toString();
	
	boolean isHasChangeRead=true;
	if (viewerStyle.toLowerCase().contains("pdf")) {
		isHasChangeRead=false;
		if (isLowerThanIE8) {
			fileKeySufix = "-img";
		} else if (highFidelity.contains("html")) {
			if(StringUtil.isNull(fileKeySufix)){
				fileKeySufix = "-svg";
			}
		} else {
			fileKeySufix = "-img";
		}
	}
	if (viewerStyle.toLowerCase().contains("word") || viewerStyle.toLowerCase().contains("ppt")) {
		if (isLowerThanIE8) {
			fileKeySufix = "-img";
		} else {
			if (highFidelity.contains("html")) {
				if(StringUtil.isNull(fileKeySufix)){
					fileKeySufix = "-svg";
				}
			} else if (highFidelity.contains("pic")) {
				fileKeySufix = "-img";
			} else {
				if(StringUtil.isNull(fileKeySufix)){
					fileKeySufix = "-svg";
				}
			}
		}
	}
	request.setAttribute("fileKeySufix", fileKeySufix);
	request.setAttribute("totalPageCount",
			"-img".equals(fileKeySufix) ? request.getAttribute("picPageCount").toString()
					: request.getAttribute("htmlPageCount").toString());
	request.setAttribute("isLowerThanIE8", (isLowerThanIE8 ? "true" : "false"));
	request.setAttribute("highFidelity", StringUtil.isNotNull(highFidelity)
			&& (highFidelity.contains("html") || highFidelity.contains("pic")) ? "true" : "false");
	
	request.setAttribute("isHasChangeRead", isHasChangeRead);
%>
<template:include ref="default.simple">
	<template:replace name="title">
		<%
			Object obj = request.getAttribute("fileFullName");
					if (obj != null) {
						String fileName = obj.toString();
						if (fileName.indexOf(".") >= 0)
							out.append(fileName.substring(0, fileName
									.lastIndexOf(".")));

					}
		%>
	</template:replace>
	<template:replace name="head">
		<template:super />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/common.css?s_cache=${LUI_Cache }" />
		<c:choose>
			<c:when test="${toolPosition == 'top'}">
				<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/innerviewer.css?s_cache=${LUI_Cache }" />
			</c:when>
			<c:when test="${showAllPage == 'true'}">
				<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/allpageviewer.css?s_cache=${LUI_Cache }" />
			</c:when>
			<c:otherwise>
				<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/newopen.css?s_cache=${LUI_Cache }" />
			</c:otherwise>
		</c:choose>
		
		<script type="text/javascript" src="${LUI_ContextPath }/sys/attachment/viewer/resource/common/js/commonfuncs.js?s_cache=${LUI_Cache }"></script>
		<script type="text/javascript">
			var totalPageNum = parseInt("${totalPageCount}");
			var converterKey = "${converterKey}";
			var viewerStyle = "${viewerStyle}";
			var viewerParam=${viewerParam};
			var isLowerThanIE8 = "${isLowerThanIE8}";
			var fileKeySufix="${fileKeySufix}";
			var fullScreen="${lfn:escapeJs(fullScreen)}";
			var canCopy="${canCopy}";
			var fdId="${fdId}";
			var highFidelity="${highFidelity}";
			var currentPage=parseInt("${lfn:escapeJs(currentPage)}");
			var showAllPage = "${lfn:escapeJs(showAllPage)}";
			var toolPosition = "${toolPosition}";
			var goFullScreenHint = "${lfn:message('sys-attachment:viewer_hint_fullscreen')}";
			var exitFullScreenHint = "${lfn:message('sys-attachment:viewer_hint_cancelfullscreen')}";
			var dataSrc=Com_Parameter.ContextPath+"sys/attachment/sys_att_main/sysAttMain.do";
			var waterMarkConfig=${waterMarkConfig};
			var i = 0;				
			LUI.ready( function() {
				commonFuncs.initialHandlers();
				var browser=commonFuncs.getBrowser();
				if(browser=="IE"&&fullScreen=="yes"){
					commonFuncs.addCss("fullscreencss",Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/css/fullscreen.css");					
					commonFuncs.fsCommonHandler();
					commonFuncs.bindFsKeyDown();
					if(commonFuncs.contains(viewerStyle, "ppt", true)){
						commonFuncs.bindFsPptEvent();
						commonFuncs.addJs("pptfullscreenjs", Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/js/pptfullscreen.js");
					}else{
						commonFuncs.addJs("newopenjs", Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/js/newopen.js",currentPage);
					}
				}else{
					commonFuncs.addJs("newopenjs",Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/js/newopen.js",currentPage);
				}
			});
			
			seajs.use(['lui/jquery'], function($) {
		        $(window).bind("load resize", function () {
		        	if (waterMarkConfig.showWaterMark == "true"){
			        	if(i==0){//首次不加载
			        		i++;
			        		return;
			        	}
			            var _rate = detectZoom();
			            newOpen.reSetWaterMark();
		        	}
		        });
			});
	        
	        // 判断是否缩放
	        function detectZoom() {
	            var ratio = 0,
	                screen = window.screen,
	                ua = navigator.userAgent.toLowerCase();

	            if (window.devicePixelRatio !== undefined) {
	                ratio = window.devicePixelRatio;
	            } else if (~ua.indexOf('msie')) {
	                if (screen.deviceXDPI && screen.logicalXDPI) {
	                    ratio = screen.deviceXDPI / screen.logicalXDPI;
	                }
	            } else if (window.outerWidth !== undefined && window.innerWidth !== undefined) {
	                ratio = window.outerWidth / window.innerWidth;
	            }

	            if (ratio) {
	                ratio = Math.round(ratio * 100);
	            }
	            return ratio;
	        };			
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
								<i class="icon"><img src="${LUI_ContextPath }/resource/style/common/fileIcon/${attIconName }" /></i>
								<strong>${fileFullName }</strong><span class="text-muted">(${fileSize })</span>
								<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${fdId }" requestMethod="GET">
									<a href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${fdId }" class="download">${lfn:message('sys-attachment:viewer_hint_1')}</a>
								</kmss:auth>
							</li>
						</ul>
					</div>
				</div>
				<div class="top_right">
					<div class="converterTool">
						<a href="http://dcs.yozosoft.com/" title="${ lfn:message('sys-attachment:converterTool.dcs') }" target="_blank">
							<img src="../viewer/yozo/logo.png" class="logo" height="35" width="122">
						</a>
					</div>
					<div class="changeView">
						<c:choose>
							<c:when test="${isWpsoaassist}">
								<c:if test="${isHasChangeRead}">
									<script type="text/javascript" src="${LUI_ContextPath }/sys/attachment/sys_att_main/wps/oaassist/js/wps_utils.js?s_cache=${LUI_Cache }"></script>
									<a class="viewJG" href="javascript:void(0);" onclick="openByWpsOaassist('${fdId}');">${lfn:message('sys-attachment:viewer_hint_2')}</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<a class="viewJG" href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${fdId }&viewType=jg">${lfn:message('sys-attachment:viewer_hint_2')}</a>
							</c:otherwise>
						</c:choose>
						
					</div>
				</div>
			</div>
			<div id="readerMain">
				<div id="mainLeft" class="pageBtn">
					<a href="javascript:void(0);" class="btn_icon" title="" onclick="commonFuncs.prev();"></a>
				</div>
				<div id="mainMiddle"></div>
				<div id="mainRight" class="pageBtn">
					<a href="javascript:void(0);" class="btn_icon" title="" onclick="commonFuncs.next();"></a>
				</div>
				<div id="zoomButton">
					<a class="zoom-icon" href="javascript:void(0);" title="${lfn:message('sys-attachment:viewer_hint_cancelfullscreen')}" onclick="commonFuncs.changeScreenStatus();"></a>
				</div>
			</div>
			<div id="readerBottom" class="pageBottom">
				<div id="bottomContainer">
					<div class="pageNavContainer">
						<div class="pageNav">
							<ul>
								<li class="arrow"><a href="javascript:void(0);" id="prevBtn" class="unable prev" onclick="commonFuncs.prev(event);"></a></li>
								<li class="pages"><input id="currentPageIndex" type="text" onkeydown="return commonFuncs.onPageKeyDown(event)" onkeyup="commonFuncs.onPageKeyUp(event);" />/<span id="totalPageCount"></span></li>
								<li class="arrow"><a href="javascript:void(0);" id="nextBtn" class="next" onclick="commonFuncs.next(event);"></a></li>
							</ul>
						</div>
					</div>
					<div class="zoom">
						<ul>
							<li><a class="zoom_a" href="javascript:void(0);" title="${lfn:message('sys-attachment:viewer_hint_fullscreen')}" onclick="commonFuncs.changeScreenStatus(event);"></a></li>
						</ul>						
					</div>
					<div id="seperateOpenTool" class="seperateOpen">
						<ul>
							<li><a href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${fdId }" title="${lfn:message('sys-attachment:viewer_hint_3')}" target="_blank">${lfn:message('sys-attachment:viewer_hint_3')}</a></li>
						</ul>						
					</div>
				</div>
			</div>
		</div>
	</template:replace>
</template:include>