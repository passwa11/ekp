<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="auto">

	<template:replace name="head">
		<script type="text/javascript">
			var highFidelityNames = "doc;docx;wps;ppt;pptx;pdf;rtf";
			var highFidelityValue="${sysFileConvertQueueParamForm.highFidelity}";
			
			function submitForm(method) {
				Com_Submit(document.sysFileConvertQueueParamForm, method);
			}

			function contains(string, substr, isIgnoreCase) {
				if (isIgnoreCase) {
					string = string.toLowerCase();
					substr = substr.toLowerCase();
				}
				var startChar = substr.substring(0, 1);
				var strLen = substr.length;
				for (var j = 0; j < string.length - strLen + 1; j++) {
					if (string.charAt(j) == startChar)//如果匹配起始字符,开始查找
					{
						if (string.substring(j, j + strLen) == substr)//如果从j开始的字符与str匹配，那ok
						{
							return true;
						}
					}
				}
				return false;
			}

			function initialQueue() {
				$(".tempTB").removeAttr("style");
			}

			$(document).ready(initialQueue);
		</script>
		<style>
			body {
				overflow:hidden;
			}
		</style>
	</template:replace>

	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ sysFileConvertQueueParamForm.method_GET == 'setting'}">
					<ui:button text="${lfn:message('button.submit') }" order="2"
						onclick="submitForm('saveQueueParam');">
					</ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${lfn:message('button.close') }" order="5"
				onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<c:set var="sysFileConvertQueueParamForm"
			value="${sysFileConvertQueueParamForm}" scope="request" />
		<html:form
			action="/sys/filestore/sys_filestore/sysFileConvertQueueParam.do">
			<html:hidden property="fdId" />
			<p class="lui_form_subject">
				<c:out
					value="${ lfn:message('sys-filestore:sysFileConvertQueue.setting') }" />
			</p>
			<div class="lui_form_content_frame" style="padding-top: 20px">
				<table class="tb_normal" style="width: 95%;">
					<c:choose>
						<c:when test="${'true' == sysFileConvertQueueParamForm.containsHighFidelity}">
							<tr>
								<td class="td_normal_title" width="100px">${ lfn:message('sys-filestore:queueParam.picResolution') }</td>
								<td width="450px" colspan="3"><xform:text
										property="picResolution" style="width:60px;"
										subject="${lfn:message('sys-filestore:queueParam.picResolution')}"
										showStatus="edit" validators="min(96) digits" /> <span class="message"><bean:message
											key='queueParam.picResolution.desc' bundle='sys-filestore' /></span></td>
							</tr>
							<tr>
								<td class="td_normal_title" width="100px">${ lfn:message('sys-filestore:queueParam.picRectangle') }</td>
								<td width="450px" colspan="3"><xform:text
										property="picRectangle" style="width:60px;"
										subject="${lfn:message('sys-filestore:queueParam.picRectangle')}"
										showStatus="edit" /> <span class="message"><bean:message
											key='queueParam.picRectangle.desc' bundle='sys-filestore' /></span></td>
							</tr>
						</c:when>
					</c:choose>
					<tr id="tr_2">
						<td class="td_normal_title" width="120px">${ lfn:message('sys-filestore:sysFileConvertConfig.fdConverterType') }</td>
						<td width="200px"><xform:select showStatus="edit" property="converterType"
								style="width:130px;" showPleaseSelect="true">
								<xform:customizeDataSource
									className="com.landray.kmss.sys.filestore.service.spring.SysFileStoreConverterTypeDataSource"></xform:customizeDataSource>
							</xform:select></td>
						<c:choose>
							<c:when test="${'true' == sysFileConvertQueueParamForm.containsHighFidelity}">
								<td id="td_highfidelity_name" class="td_normal_title"
									width="120px">${ lfn:message('sys-filestore:queueParam.highfidelity') }</td>
								<td id="td_highfidelity_value" width="200px"><ui:switch
										property="highFidelity"
										disabledText="${lfn:message('message.no')}"
										enabledText="${lfn:message('message.yes') }"></ui:switch></td>
							</c:when>
						</c:choose>
					</tr>
				</table>
			</div>
		</html:form>
		<script language="JavaScript">
			$KMSSValidation(document.forms['sysFileConvertQueueParamForm']);
		</script>
	</template:replace>
</template:include>