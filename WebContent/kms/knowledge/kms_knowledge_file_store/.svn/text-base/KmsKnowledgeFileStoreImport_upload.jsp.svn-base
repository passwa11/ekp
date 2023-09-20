<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<% response.setHeader("X-UA-Compatible", "IE=8"); %>
<script type="text/javascript">
  // 请选择要导入的文件
  var alert_upload_file_empty = "<bean:message bundle='sys-transport' key='sysTransport.error.upload.fileEmpty'/>";
  // 所选文件不能为空
  var alert_msg_file_required = "<bean:message bundle='sys-transport' key='sysTransport.import.file.required'/>";
  // 正在执行
  var upload_doing_tip_desc = "<bean:message bundle='sys-transport' key='sysTransport.title.uploadDoing'/>";
  // 请检查模板文件，上传正确的Excel文件
  var upload_doing_notExcel = "<bean:message bundle='kms-common' key='kmsCommonFileStoreExcelImport.error.notExcel'/>";
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
	width:1200px;
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
<form name="successTemplate" action="<%=request.getContextPath() %>/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do" method="post">
	<input type="hidden" name="method" value="downloadImportSuccessData">
	<iframe name="file_success_frame" style="display:none;"></iframe>
</form>

<form name="downloadWithoutSuccessDataTemplate" action="<%=request.getContextPath() %>/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do" method="post">
	<input type="hidden" name="method" value="downloadTemplateWithoutSuccessData">
	<iframe name="file_template_frame" style="display:none;"></iframe>
	<input name="templateProgressKey" type="hidden">
</form>

<html:form action="/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do?method=importExcelAsync" enctype="multipart/form-data" onsubmit="return validateForm(this);">
	<iframe name="file_frame" style="display:none;"></iframe>
	<div id="optBarDiv">
		<input type="button" value="<bean:message bundle="sys-transport" key="sysTransport.button.upload"/>" onclick="upload();">
<%--		<input type=button value="模版下载" onclick="submitExportTemplateForm();">--%>
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>

	<div id="downloadResultDiv" class="upload-container" style="display: none;min-height: 100px;margin-bottom: 50px;">
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
	<div id="uploadResultDiv" class="upload-container">
		<!-- 标题：数据导入 -->
		<p class="txttitle"><bean:message  bundle="sys-transport" key="sysTransport.button.dataImport"/></p>
		<hr>
		<!-- 选择文件 -->
		<div class="upload-select-file-row" style="height: 36px;" >
			<div style="vertical-align: top;margin-top: 6px;"><bean:message  bundle="sys-transport" key="sysTransport.upload.file"/></div>
			<div style="margin-left: 50px;position:relative;height:34px;">
				<a id="upload_excel_link_button" class="a_upload_excel" href="javascript:void(0);">
					<input type="file" name="file" accept=".xls,.xlsx" ><bean:message bundle="sys-transport" key="sysTransport.import.selectFile"/>
				</a>
				<div class="show"></div>
			</div>
			<input name="progressKey" type="hidden">
		</div>
		<!-- 导入提醒描述 -->
		<div style="color: rgb(255, 0, 0);margin-top: 2px;">
			${lfn:message("kms-knowledge:kmsKnowledgeFileStoreExcelImport.tip.adviceCategory")}
		</div>
		<!-- 导入结果 -->
		<div style="margin-top: 8px;"><bean:message bundle="sys-transport" key="sysTransport.tip.uploadResult" /></div>
		<!-- 执行情况 -->
		<div class="upload-select-file-row" style="margin-top: 8px;">
			<div><bean:message bundle="sys-transport" key="sysTransport.title.uploadProcess"/></div>
			<div>
				<span id="div_uploadProcess">
					<bean:message bundle="sys-transport" key="sysTransport.title.uploadNotDo"/>
				</span>
			</div>
		</div>
		<div id="div_uploadProgressDetail" style="display: none;margin-top: 8px;">
			<ui:button text="${lfn:message('kms-knowledge:kmsKnowledgeFsRecord.viewProgressDetail')}" onclick="toViewProgressDetail()"></ui:button>
		</div>
		<!-- 详情 -->
		<div id="div_uploadProgressErrorDetail" style="margin-top: 8px;">
			<div>
				<image src='${KMSS_Parameter_StylePath}msg/plus.gif' onclick='showMoreErrInfo(this);' style='cursor:pointer'>
				<bean:message bundle="sys-transport" key="sysTransport.title.detail"/>
			</div>
			<div id="div_errorCell" style="display:none;margin-top: 5px;"></div>
		</div>
	</div>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>