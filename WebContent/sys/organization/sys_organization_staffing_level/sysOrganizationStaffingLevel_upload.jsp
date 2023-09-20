<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.dialog">
	<template:replace name="content">
		<div style="position:relative;padding:7px 25px 5px 0px;text-align: right">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
				<!-- 模板下载 -->
				<ui:button text="${lfn:message('sys-organization:sysOrganizationStaffingLevel.download') }" onclick="download();" order="1"></ui:button>
				<ui:button text="${lfn:message('sys-organization:sysOrganizationStaffingLevel.import')}" onclick="_submit();" order="2">
				</ui:button>
				<ui:button text="${lfn:message('button.close')}" onclick="_cancel();" order="3">
				</ui:button>
			</ui:toolbar>
		</div>
		<center>
		<!-- 模板下载表单 -->
		<form id="downloadTemplateForm" action="<c:url value="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=downloadTemplate" />" method="post">
		</form>
		<form id="fileUploadForm" action="<c:url value="/sys/organization/sys_organization_staffing_level/sysOrganizationStaffingLevel.do?method=fileUpload" />" method="post" enctype="multipart/form-data">
			<table class="tb_normal" style="margin: 20px 0" width=98%>
				<tr>
					<td width="15%" class="td_normal_title">
						${lfn:message('common.fileUpLoad.selectFile')}
					</td>
					<td colspan="3">
						<input class="input_file" type="file" name="file" accept=".xls,.xlsx"/>
					</td>
				</tr>
			</table>
		</form>
		</center>
		
		<c:if test="${!empty resultMsg}">
			<div style="margin: auto 15px;">
					${lfn:message('sys-organization:sysOrganizationStaffingLevel.import.result')}
				<c:choose>
					<c:when test="${state}">
						<img src="${LUI_ContextPath}/hr/staff/resource/images/status_succ.gif" /> ${resultMsg}
					</c:when>
					<c:otherwise>
						<img src="${LUI_ContextPath}/hr/staff/resource/images/status_faile.gif" /> ${lfn:message('sys-organization:sysOrganizationStaffingLevel.import.failed')}
						<br><br>
						${resultMsg}
					</c:otherwise>
				</c:choose>
			</div>
		</c:if>
		
		<script type="text/javascript">
			seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
				// 模板下载
				window.download = function() {
					$("#downloadTemplateForm").submit();
				};
				
				// 上传
				window._submit = function() {
					$("#fileUploadForm").submit();
				};
	
				// 取消
				window._cancel = function() {
					window.$dialog.hide();
				};
			});
		</script>
	</template:replace>
</template:include>