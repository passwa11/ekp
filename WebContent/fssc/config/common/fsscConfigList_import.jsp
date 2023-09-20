<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/org/io/import.css"/>
<script type="text/javascript">
	Com_IncludeFile("data.js");
</script>
<div class="profile_orgIO_wrapper">
	<div class="profile_orgIO_content" style="text-align:center;padding:5px;">
		<p class="title title01" style="font-size:16px;">
			<ui:button text="${ lfn:message('fssc-config:fsscConfigScore.import.template.download') }" target="_self"
				href="/fssc/config/fssc_config_list/fsscConfigList.do?method=downloadTemplate"/>
			${ lfn:message("fssc-config:fsscConfigScore.import.template.desc") }
		</p>
		<form id="importForm" name="ztbFmOfflineImportInfo" action="${LUI_ContextPath}/fssc/config/fssc_config_list/fsscConfigList.do?method=saveExcel" method="post" enctype="multipart/form-data">
			<!--上传文件 开始-->
			<div class="profile_orgIO_fileUpload_item">
				<div class="fileUpload">
					<div class="file_wrap">
						<input class="input_file" id="input_file" type="file" name="formFile" accept=".xls,.xlsx" onchange="fileChange(this);"/>
						<label class="profile_orgIO_uploadBtn" for="input_file">
							<bean:message bundle="sys-profile" key="sys.profile.orgImport.upload" />
						</label>
						<span class="profile_orgIO_fileTitle"><bean:message bundle="sys-profile" key="sys.profile.orgImport.select.file" /></span>
					</div>
				</div>
			</div>
			<div class="profile_orgIO_btn_wrap" style="margin:15px 0;">
				<a href="javascript:void(0)" class="profile_orgIO_btn btn_def" onclick="importOrgdept();"><bean:message bundle="sys-profile" key="sys.profile.orgImport.import" /></a>
				<a href="javascript:void(0)" class="profile_orgIO_btn btn_gray" onclick="reset();"><bean:message key="button.cancel" /></a>
			</div>
		</form>
	</div>
	<script>
		seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
			var validateObj = $KMSSValidation();
			window.importOrgdept = function() {
				if(!validateObj.validate()) {
					return;
				}
				var file = $('#importForm input[name=formFile]');
				if(file.length == 0 || !file[0].value){
					return;
				}
				dialog.loading();
				$("#importForm").submit();
				// 开启进度条
				window.progress = dialog.progress(false);
				window._progress();
			}
			window._progress = function () {
				var data = new KMSSData();
				data.UseCache = false;
				data.AddBeanData("sysOrgImportXMLDataService");
				var rtn = data.GetHashMapArray()[0];
				
				if(window.progress) {
					var currentCount = parseInt(rtn.currentCount || 0);
					var allCount = parseInt(rtn.totalCount || 0);
					if(rtn.importState == 1 || currentCount >= allCount) {
						window.progress.hide();
					}
					// 设置进度值
					if(allCount == -1 || allCount == 0) {
						window.progress.setProgress(0);
					} else {
						window.progress.setProgressText('<bean:message bundle="sys-profile" key="sys.profile.orgImport.progress" />' + currentCount + '/' + allCount);
						window.progress.setProgress(currentCount, allCount);
					}
				}
				if(rtn.importState != 1) {
					setTimeout("window._progress()", 1000);
				}
			} 
		});
	
		function reset() {
			document.forms[0].reset();
			$(".profile_orgIO_result").html('');
			$(".profile_orgIO_fileTitle").text('<bean:message bundle="sys-profile" key="sys.profile.orgImport.select.file" />');
			Com_CloseWindow();
		}
	
		function fileChange(file) {
			var index = file.value.lastIndexOf("\\");
			var fileName = file.value.substring(index + 1);
			$(".profile_orgIO_fileTitle").text(fileName);
		}
	
		function downloadTemplet() {
			document.templet.submit();
		}
	
		function advancedImport(type) {
			window.top.location.href = "${LUI_ContextPath}/sys/profile/index.jsp#org/baseSetting/" + type;
			window.top.location.reload();
		}
	</script>
</div>
<%@ include file="/resource/jsp/edit_down.jsp" %>