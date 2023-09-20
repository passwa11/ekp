<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.edit">
    <template:replace name="head">
    	<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/help/sys_help_catelog/css/editContent.css?s_cache=${LUI_Cache}">
    	<script>
    		function selectTarget(){
    			seajs.use(['lui/jquery', 'lui/dialog', 'lang!sys-help', 'lang!sys-ui', 'lui/topic'], function($, dialog, lang, ui_lang, topic){
    				var url = '/sys/help/sys_help_config/sysHelpConfig_select.jsp#cri.q=fdModulePath:'+'${fdModulePath}';
    				dialog.iframe(url, lang["sysHelpConfig.select.target"], null, {
    					width : 900,
        				height: 500,
        				buttons : [
               					{
               						name : ui_lang['ui.dialog.button.ok'],
               						fn : function(value,_dialog) {
               							var body = $(_dialog.content.iframeObj[0].contentDocument);
               							var list = body.find('input[type="radio"]:checked').parent().siblings();
               							if(list && list.length == 6){
               								var fdName = list[3].innerHTML;
               								var fdId = list[5].children[0].innerHTML;
               								if(fdName && fdId){
               									$('#fdTargetName').html(fdName);
               									$('input[name="fdConfigId"]')[0].value = fdId
		               							_dialog.hide(value);
               								}
               							}
               						}
               					},
               					{
               						name : ui_lang['ui.dialog.button.cancel'],
               						styleClass : 'lui_toolbar_btn_gray',
               						fn : function(value, _dialog) {
               							_dialog.hide(value);
               						}
               					}
               				]
    				});
    			});
    		}
    		function cancel(){
    			$('#fdTargetName').html('');
    			$('input[name="fdConfigId"]')[0].value = '';
    		}
    	</script>
    </template:replace>

    <template:replace name="title">
		<c:out value="${sysHelpCatelogForm.docSubject}" />
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <ui:button text="${ lfn:message('button.update') }" onclick="Com_Submit(document.sysHelpCatelogForm, 'update');" />
            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('sys-help:table.sysHelpCatelog') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/help/sys_help_catelog/sysHelpCatelog.do">
			<div class="lui_form_subject">
				<xform:text property="docSubject" style="width:98%;height:30px;font-size:20px;" showStatus="edit"></xform:text>
			</div>
			<div style="margin:10px 0 10px 0;height:22px;">
				<div class="fdTargetBtn" onclick="selectTarget()">
					${lfn:message('sys-help:sysHelpConfig.select.btn')}：
				</div>
				<div>
					<span id="fdTargetName" class="sys_help_catelog_fdConfigFdName">${sysHelpCatelogForm.fdConfigFdName}</span>
					<i onclick="cancel()" class="sys_help_catelog_cancel"></i>
				</div>
				<input type="hidden" name="fdConfigId" value="${sysHelpCatelogForm.fdConfigId}"/>
			</div>	
			<div style="margin:10px 0 10px 0;">
				<xform:rtf property="docContent" showStatus="edit" width="100%"></xform:rtf>
			</div>		
        </html:form>
        <script>
        	$KMSSValidation();
        </script>
    </template:replace>
</template:include>