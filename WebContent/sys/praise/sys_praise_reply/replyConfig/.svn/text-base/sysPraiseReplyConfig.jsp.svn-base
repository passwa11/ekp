<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit">

	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/praise/sys_praise_reply/replyConfig/style/replyConfig.css" />
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<ui:button text="${lfn:message('button.save') }" onclick="saveConfig()"></ui:button> 
		</ui:toolbar>
	</template:replace>

	<template:replace name="content">
	
		<table class="tb_normal replayConfigTable">
			<tr>
				<td width="25%"  class="td_normal_title">
					<bean:message key="sysPraiseReplyConfig.isReply" bundle="sys-praise" />
				</td>
				<td width="75%">
					<ui:switch id="replyConfigSwitch"
							   property="isOpenReply"
							   onValueChange="replyConfigChange(this);"
							   checked="${isOpenReply}" 
							   enabledText="${lfn:message('sys-ui:ui.switch.enabled')}" 
							   disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
				</td>
			</tr>
			<tr>
				<td width="25%" class="td_normal_title">
					<bean:message key="sysPraiseReplyConfig.replyText" bundle="sys-praise" />
				</td>
				<td width="75%">
					<c:if test="${isOpenReply == 'true'}">
						<xform:textarea property="replyText" showStatus="edit" style="width:99%;height:100px;" value="${replyText}"></xform:textarea>
					</c:if>
					<c:if test="${isOpenReply != 'true'}">
						<xform:textarea property="replyText" showStatus="readOnly" style="width:99%;height:100px;" value="${replyText}"></xform:textarea>
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="100%" class="td_normal_title" colspan="2">
					<bean:message key="sysPraiseReplyConfig.replyTextTips" bundle="sys-praise" />
				</td>
			</tr>
		</table>
		
		<script>
			// 获取内容框
			var isOpenReply = '${isOpenReply}';
			var domName = '_replyText';
			if(isOpenReply=='true'){
				domName = 'replyText';
			}
			var $textArea = $('textarea[name="'+domName+'"]');
			
			if(isOpenReply==''){
				var defaultList = '${lfn:message("sys-praise:sysPraiseReplyConfig.replyTextDefault1")}'+'\n';
				defaultList += '${lfn:message("sys-praise:sysPraiseReplyConfig.replyTextDefault2")}'+'\n';
				defaultList += '${lfn:message("sys-praise:sysPraiseReplyConfig.replyTextDefault3")}'+'\n';
				defaultList += '${lfn:message("sys-praise:sysPraiseReplyConfig.replyTextDefault4")}'+'\n';
				defaultList += '${lfn:message("sys-praise:sysPraiseReplyConfig.replyTextDefault5")}';
				$textArea.html(defaultList);
			}
			
			function replyConfigChange(self){
				var checked = self.checked;
				if(checked){
					$textArea.removeAttr('readonly');
				}else{
					$textArea.attr('readonly', '');
				}
			}
			
			function saveConfig(){
				seajs.use(['lui/dialog'], function(dialog){
					var isOpenReply = $('input[name="isOpenReply"]')[0].value;
					var replyResultList = $textArea.val();
					$.ajax('${LUI_ContextPath}/sys/praise/sys_praise_reply_config/sysPraiseReplyConfig.do?method=saveConfig',{
						type : "post",
						data  : {
							'isOpenReply' : isOpenReply,
							'replyResultList' : replyResultList
						},
						success : function(data) {
							dialog.result(data);
						},
						error : function(data) {
							dialog.failure(text);
						},
						dataType : "json"
					});
				});
			}
		</script>
	</template:replace>
</template:include>