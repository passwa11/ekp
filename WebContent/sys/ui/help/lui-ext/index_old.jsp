<%@ page import="com.landray.kmss.sys.config.xml.DesignConfigLoader"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="com.landray.kmss.sys.ui.util.ResourceCacheListener" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	if(request.getParameter("reLoad")!=null&&request.getParameter("reLoad").equals("yes")){
		new ResourceCacheListener().refreshUiCache();
	}
	request.setAttribute("datas",SysUiPluginUtil.getExtends().values());
	
request.setAttribute("sys.ui.theme", "default");
%>
<ui:json var="columns">
	[
		{id:"fdId", name:"ID"},
		{id:"fdName", name:"${lfn:message('sys-ui:ui.help.luiext.name') }"},
		{
		  id:"fdName", name:"${lfn:message('sys-ui:ui.help.luiext.operation') }",
		  type:"__custom__",
		  text:"${lfn:message('sys-ui:ui.help.luiext.action') }",
		  "onclick":"deleteTheme",
		  type2:"__custom2__",
		  text2:"${lfn:message('sys-ui:ui.help.luiext.download') }",
		  "onclick2":"downloadTheme"
		}
	]
</ui:json>
<ui:json var="paths">
	[
		{name:"${lfn:message('sys-ui:ui.help.luiext.extsource') }"}
	]
</ui:json>
<template:include file="/sys/ui/help/common/list.jsp">
	<template:replace name="beforeList">
		<script type="text/javascript"> 
		  	function stopBubble(e) {
		        //如果提供了事件对象，则这是一个非IE浏览器
		        if ( e && e.stopPropagation )
		            //因此它支持W3C的stopPropagation()方法
		            e.stopPropagation();
		        else
		            //否则，我们需要使用IE的方式来取消事件冒泡
		            window.event.cancelBubble = true;
		    }
			function deleteTheme(rid,evt) {
				stopBubble(evt);
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
					dialog.confirm("${lfn:message('sys-ui:ui.help.luiext.deletetheme.tip') }",function(val){
						if(val){
							$.get("${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=deleteExtend&id="+rid+"&uiType=theme",function(txt){
								if(txt=="1"){
									dialog.success("${lfn:message('sys-ui:ui.help.luiext.success') }");
									location.href = Com_SetUrlParameter(location.href, "reLoad", "yes");
								}
							});
						}
					});
				});				
			}
			function downloadTheme(rid,name,evt) {
				stopBubble(evt);
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
					dialog.confirm('<bean:message key="ui.help.luiext.downloadtheme.tip" bundle="sys-ui" arg0="' + name + '"/>',function(val){
						if(val){
							document.themeForm.action = "${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=download&id="+rid;
							//document.themeForm.submit();
							window.location.href="${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=download&id="+rid;
						}
					});
				});
			}
			function uploadTheme(){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.iframe("/sys/ui/help/lui-ext/upload.jsp","${lfn:message('button.zip.upload') }",function(data){
							location.href = Com_SetUrlParameter(location.href, "reLoad", "yes");
					},{"width":800,"height":600});
				});
			}
			function mergeTheme(){
				seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
					$.get("${LUI_ContextPath}/sys/ui/sys_ui_extend/sysUiExtend.do?method=merge",function(txt){
						if(txt=='1'){
							dialog.success("${lfn:message('sys-ui:ui.help.luiext.success') }");
						}
					});
				});
			}
			</script>
		<!-- 打包下载 -->
		<form target="_blank" name="themeForm" action="" method="post">
			<input type="hidden" name="method" value="download">
		</form>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${lfn:message('sys-ui:ui.help.luiext.merge') }" onclick="mergeTheme()" />
		</ui:toolbar>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${lfn:message('button.zip.upload') }" onclick="uploadTheme()" />
		</ui:toolbar>
	</template:replace>
</template:include>
