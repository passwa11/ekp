<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="auto">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/> - <bean:message key="global.init.export.data"/>
	</template:replace>
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
	</template:replace>
	<template:replace name="body">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
    	 	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
		<div style="width: 95%; margin: 20px auto;">
			<!-- 矩阵导入成功 Starts  -->
	        <div class="lui_maxtrix_success_wrap">
	            <!-- 图标 -->
	            <span class="lui_maxtrix_icon">
	                <i class="icon_success"></i>
	            </span>
	            <p class="lui_maxtrix_txt"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.success"/></p>
	        </div>
	        <!-- 矩阵导入成功 Ends  -->
		</div>
		<script language="JavaScript">
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			});
			function refreshNotify() {
				try{
					if(window.opener!=null) {
						try {
							if (window.opener.LUI) {
								window.opener.LUI.fire({ type: "topic", name: "successReloadPage" });
								return;
							}
						} catch(e) {}
						if (window.LUI) {
							LUI.fire({ type: "topic", name: "successReloadPage" }, window.opener);
						}
						var hrefUrl= window.opener.location.href;
						var localUrl = location.href;
						if(hrefUrl.indexOf("/sys/notify/")>-1 && localUrl.indexOf("/sys/notify/")==-1)
							window.opener.location.reload();
					} else if(window.frameElement && window.frameElement.tagName=="IFRAME" && window.parent){
						if (window.parent.LUI) {
							window.parent.LUI.fire({ type: "topic", name: "successReloadPage" });
						}
					}
				}catch(e){}
			}
			Com_AddEventListener(window,"load",refreshNotify);
		</script>
	</template:replace>
</template:include>
