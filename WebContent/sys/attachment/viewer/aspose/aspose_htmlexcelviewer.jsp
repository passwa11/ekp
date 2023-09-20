<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%><template:include
	ref="default.simple">
	<template:replace name="title">
		<%
			boolean isWpsoaassist=SysAttWpsoaassistUtil.isEnable();
			request.setAttribute("isWpsoaassist", isWpsoaassist);
		
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
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/common.css?s_cache=${LUI_Cache }" />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/excel.css?s_cache=${LUI_Cache }" />
		<c:choose>
			<c:when test="${toolPosition == 'top'}">
				<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/innerviewer.css?s_cache=${LUI_Cache }" />
			</c:when>
			<c:otherwise>
				<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/attachment/viewer/resource/common/css/newopen.css?s_cache=${LUI_Cache }" />
			</c:otherwise>
		</c:choose>
		<script type="text/javascript" src="${LUI_ContextPath }/sys/attachment/viewer/resource/common/js/commonfuncs.js?s_cache=${LUI_Cache }"></script>
		<script type="text/javascript">
			var toolPosition = "${lfn:escapeJs(toolPosition)}";
			var converterKey = "${converterKey}";
			var newOpen = "${newOpen}";
			var viewerStyle = "${viewerStyle}";
			var fileKeySufix="${fileKeySufix}";
			var fullScreen="${lfn:escapeJs(fullScreen)}";
			var canCopy="${canCopy}";
			var fdId="${fdId}";
			var currentPage=parseInt("${lfn:escapeJs(currentPage)}");
			var dataSrc="${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do";
			var goFullScreenHint = "${lfn:message('sys-attachment:viewer_hint_fullscreen')}";
			var exitFullScreenHint = "${lfn:message('sys-attachment:viewer_hint_cancelfullscreen')}";
			var totalPageNum = parseInt("${htmlPageCount}");
			var viewerParam = ${viewerParam};
			var waterMarkConfig=${waterMarkConfig};
			LUI.ready( function() {
				if(viewerParam && viewerParam.activeSheetNum){
					currentPage = viewerParam.activeSheetNum;
				}
				commonFuncs.initialHandlers();
				var browser=commonFuncs.getBrowser();
				if(browser=="IE"&&fullScreen=="yes"){
					commonFuncs.fsCommonHandler();
					commonFuncs.unBindFsToolHover();
					commonFuncs.bindFsKeyDown();
					commonFuncs.addCss("fullscreencss",Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/css/fullscreen.css");					
					commonFuncs.addJs("exceljs",Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/js/excel.js",currentPage);
				}else{
					/* if(toolPosition=="top"){
						commonFuncs.addCss("innerviewercss",Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/css/innerviewer.css");
					}else{
						commonFuncs.addCss("newopencss",Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/css/newopen.css");
					} */
					commonFuncs.addJs("exceljs",Com_Parameter.ContextPath+"sys/attachment/viewer/resource/common/js/excel.js",currentPage);
				}
			});
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
									<a href="javascript:downloadAttAndLog('${fdId }');" class="download">${lfn:message('sys-attachment:viewer_hint_1')}</a>
								</kmss:auth>
								
								<%
									String hiddenMainDoc = request.getParameter("hiddenMainDoc");
									request.setAttribute("hiddenMainDoc", hiddenMainDoc);
								%>
								<c:choose>
									<c:when test="${hiddenMainDoc == '1'}">
									
									</c:when>
									<c:otherwise>
										<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=findMainDocInfo&fdId=${fdId }">
											<a href="javascript:returnMainDoc('${fdId }');" class="download">${lfn:message('sys-attachment:sysAttMain.returnMainDoc')}</a>
										</kmss:auth>
									</c:otherwise>
								</c:choose>
							</li>
						</ul>
					</div>
				</div>
				<div class="top_right">
					<div class="converterTool">
						<a href="http://www.aspose.com" title="Aspose | Your file format APIs" target="_blank">
							<img src="../viewer/aspose/logo.png" class="logo" height="35" width="122">
						</a>
					</div>
					<div class="changeView">
						<c:choose>
							<c:when test="${isWpsoaassist}">
								<script type="text/javascript" src="${LUI_ContextPath }/sys/attachment/sys_att_main/wps/oaassist/js/wps_utils.js?s_cache=${LUI_Cache }"></script>
								<a class="viewJG" href="javascript:void(0);" onclick="openByWpsOaassist('${fdId}');">${lfn:message('sys-attachment:viewer_hint_2')}</a>
							</c:when>
							<c:otherwise>
								<a class="viewJG" href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${fdId }&viewType=jg">${lfn:message('sys-attachment:viewer_hint_2')}</a>
							</c:otherwise>
						</c:choose>
					
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
					<div class="lui_sheetBottom_bar">
						<!-- 左右切换箭头 -->
						<div class="lui_sheet_switch">
							<ul>
								<li onclick="excel.goTo('start');" class="switch_toHome"><i class="sheet_icon icon_toHome"></i></li>
								<li onclick="excel.goTo('prev');" class="switch_prev"><i class="sheet_icon icon_prev"></i></li>
								<li onclick="excel.goTo('next');" class="switch_next"><i class="sheet_icon icon_next"></i></li>
								<li onclick="excel.goTo('end');" class="switch_toEnd"><i class="sheet_icon icon_toEnd"></i></li>
							</ul>
						</div>
						<!-- 页签列表 -->
						<div class="lui_sheet_doc_list">
							<ul id="sheetUL">
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