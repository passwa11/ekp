<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<% response.setHeader("X-UA-Compatible", "IE=8"); %>
<script type="text/javascript">
	// 请选择要导入的文件
	var alert_upload_file_empty = "<bean:message bundle='sys-transport' key='sysTransport.error.upload.fileEmpty'/>";
	// 所选文件不能为空
	var alert_msg_file_required = "<bean:message bundle='sys-transport' key='sysTransport.import.file.required'/>";
	// 正在执行
	var upload_doing_tip_desc = "<bean:message bundle='sys-transport' key='sysTransport.title.uploadDoing'/>";
	// 其他错误
	var import_info_other_errors = "<bean:message bundle='sys-transport' key='sysTransport.import.otherErrors'/>";
	// 未执行
	var upload_not_do_desc = "<bean:message bundle='sys-transport' key='sysTransport.title.uploadNotDo'/>";
</script>
<script type="text/javascript" src="${LUI_ContextPath}/kms/knowledge/kms_knowledge_file_store/resource/js/import_upload.js"></script>
<style>
	html,body{
		background-color:#efefef;
	}

	.a_upload_excel {
		float: left;
		padding: 4px 10px;
		height: 20px;
		line-height: 20px;
		position: relative;
		cursor: pointer;
		color: #fff;
		background: rgb(0, 179, 238);
		border: 1px solid #ddd;
		border-radius: 4px;
		overflow: hidden;
		display: inline-block;
		*display: inline;
		*zoom: 1
	}

	.a_upload_excel input {
		position: absolute;
		font-size: 100px;
		right: 0;
		top: 0;
		opacity: 0;
		filter: alpha(opacity=0);
		cursor: pointer
	}

	.a_upload_excel:hover {
		color: #fff;
		background: rgb(0, 141, 226);
		text-decoration: none;
	}

	/*显示上传文件夹名的Div*/
	.show {
		margin-left: 100px;
		color:#1b83d8;
		font:normal normal normal 14px/30px 'Microsoft YaHei';
	}
	.upload-container {
		margin: 0 auto;
		overflow:auto;
		width:80%;
		padding:10px 20px;
		background-color:#fff;
		border-radius:10px;
		-webkit-border-radius:10px;
		-ms-border-radius:10px;
		-o-border-radius:10px;
		-moz-border-radius:10px;
		box-shadow: 3px 4px 10px 1px #888888;
		-webkit-box-shadow:3px 4px 10px 1px #888888;
		-ms-box-shadow:3px 4px 10px 1px #888888;
		-o-box-shadow:3px 4px 10px 1px #888888;
		-moz-box-shadow:3px 4px 10px 1px #888888;
	}
	.upload-select-file-row>div{
		display: inline-block;
		*display: inline;
		*zoom: 1;
	}
</style>
<form name="downloadWithoutSuccessDataTemplate" action="<%=request.getContextPath() %>/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do" method="post">
	<input type="hidden" name="method" value="downloadTemplateWithoutSuccessData">
	<input name="templateProgressKey" type="hidden">
	<iframe name="file_template_frame" style="display:none;"></iframe>
</form>
<div id="optBarDiv">
	<!-- 模板下载按钮 -->
	<input type=button value="<bean:message bundle="sys-transport" key="sysTransport.button.download.templet"/>"
		onclick="submitExportTemplateForm();">
	<!-- 数据导入按钮 -->
	<input type="button" value="<bean:message bundle="sys-transport" key="sysTransport.button.dataImport"/>"
				onclick="Com_OpenWindow('<%=request.getContextPath() %>/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do?method=showUploadForm');">
	<c:if test="${empty param.hideCloseBtn}">
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</c:if>
</div>
<div id="downloadResultDiv" class="upload-container" style="display: none;min-height: 100px;">
	<!-- 标题：数据导入 -->
	<p class="txttitle">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.progress.download.title")}</p>
	<hr>
	<!-- 执行情况 -->
	<div class="upload-select-file-row" style="margin-top: 8px;padding-top: 15px;">
		<div><bean:message bundle="sys-transport" key="sysTransport.title.uploadProcess"/></div>
		<div>
			<span id="div_downloadProcess">
			</span>
		</div>
	</div>
</div>
<div id="tipDiv">
	<table id="Label_Tabel" width="95%" style="margin: auto;">
		<%--基本信息--%>
		<tr LKS_LabelName="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.title')}">
			<td>
				<table class="tb_normal" style="margin: auto;margin-top: 20px;margin-bottom: 30px;" width="90%" align="center">
					<tr>
						<td colspan="2">
							<div style="color:red;padding: 10px">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.adviceCategory")}</div>
							<div style="padding: 10px;font-weight: bold;">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.stepDescribe")}</div>
							<div style="padding: 10px">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.ftp.step1")}</div>
							<div style="padding: 10px">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.ftp.step2")}</div>
							<div style="padding: 10px">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.ftp.step3")}</div>
							<div style="padding: 10px">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.ftp.step4")}</div>
							<div style="padding: 10px">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.ftp.step5")}</div>
						</td>
					</tr>
				</table>
			<td>
		</tr>
		<%--导入字段说明--%>
		<tr LKS_LabelName="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.property.title')}" >
			<td>
				<table class="tb_normal" width="90%" style="margin: auto;margin-top: 20px;margin-bottom: 30px;" align="center" id="transportTable2">
					<tr>
						<td class="td_normal_title">
							${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docCategoryName")}<span style="color:red">(*)</span>
						</td>
						<td>${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docCategoryName.describe")}</td>
					</tr>
					<tr>
						<td class="td_normal_title">
							${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.filePath")}<span style="color:red">(*)</span>
						</td>
						<td>${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.filePath.describe")}</td>
					</tr>
					<tr>
						<td class="td_normal_title">${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docSubject")}<span style="color:red">(*)</span></td>
						<td>${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docSubject.describe")}</td>
					</tr>
					<tr>
						<td class="td_normal_title">
							${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docAuthorType")}<span style="color:red">(*)</span>
						</td>
						<td>${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docAuthorType.describe")}</td>
					</tr>
					<tr>
						<td class="td_normal_title">
							${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docAuthor")}<span style="color:red">(*)</span>
						</td>
						<td>${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.docAuthor.describe")}</td>
					</tr>
					<tr>
						<td class="td_normal_title">
							${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.authReaders")}
						</td>
						<td>${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.authReaders.describe")}</td>
					</tr>
					<tr>
						<td class="td_normal_title">
							${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.authEditors")}
						</td>
						<td>${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.authEditors.describe")}</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<%--<div id="tipDiv">--%>
<%--	<p class="txttitle">--%>
<%--		${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.title')}--%>
<%--	</p>--%>

<%--</div>--%>
<%@ include file="/resource/jsp/view_down.jsp"%>