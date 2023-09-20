<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/process.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css" rel="stylesheet">
<%
	//防跨域攻击
	String type = request.getParameter("type");
	String fdAppId = request.getParameter("fdAppId");
	if(type != null) {
		type = URLEncoder.encode(type, "utf-8");
	}
	if(fdAppId != null) {
		fdAppId = URLEncoder.encode(fdAppId, "utf-8");
	}
	request.setAttribute("type", type);
	request.setAttribute("fdAppId", fdAppId);
%>
<script>
	Com_IncludeFile("jquery.js");
</script>
<script>
	var appInfos = {};
	$(function () {
		$('.model-upload-content-btn').on('click', function () {
			$('#initfile').trigger("click");
		})
		$('#initfile').on('change', function () {
			var filePath = this.value;
			//选择文件
			if (filePath) {
				if (filePath.indexOf('fakepath') !== -1)
					filePath = filePath.substring(filePath.indexOf('fakepath\\') + 'fakepath\\'.length);
				$('#showfile')[0].innerHTML = filePath;
				$('#showfile').addClass("active");
				$('.model-upload-content-btn')[0].innerHTML = '${lfn:message('sys-modeling-base:modeling.Change.file')}';
			} else {
				//取消选择的文件
				$('#showfile')[0].innerHTML = '${lfn:message('sys-modeling-base:modeling.file.format.zip')}';
				$('#showfile').removeClass("active");
				$('.model-upload-content-btn')[0].innerHTML = '${lfn:message('sys-modeling-base:modeling.select.file')}';
			}
		})
	});

	function getAllForms() {
		var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=findFormNumAndAppIsSelfBuild&fdAppId=${JsParam.fdAppId}";
		$.ajax({
			url:url,
			async:false,
			type:'GET',
			dataType:'json',
			success:function(result){
				if (result){
					appInfos =result ;
				}else{
					dialog.failure(result.errmsg);
				}
			}
		});
		return appInfos;
	}

	function submitForm() {
		seajs.use("lui/dialog", function (dialog) {
			var value = $.trim($('input[name="initfile"]').val());
			if (value.length > 0) {
				if (value.toLowerCase().substring(value.length - 4) != '.zip') {
					dialog.alert('${lfn:message('sys-modeling-base:modeling.import.file.zip')}');
					return;
				}
				var type='${type}';
				if(type && type === 'form'){
					importFormZip();
				}else{
					importAppZip();
				}

				return;
			}
			dialog.alert('${lfn:message('sys-modeling-base:modeling.select.import.file')}');
		});
	}

	function importFormZip(){
		getAllForms();
		if (appInfos.FormNums >= appInfos.maxNum){
			seajs.use("lui/dialog", function (dialog) {
				dialog.alert("${lfn:message('sys-modeling-base:modelingLicense.up.to.30.forms')}");
			});
			return;
		}
		seajs.use("lui/dialog", function (dialog) {
			document.forms[0].submit();
			$(".model-mask-panel").hide();
			$(".toolbar-bottom").hide();
			dialog.loading();
			window.fitSize('status');
		});
	}

	function importAppZip() {
		seajs.use("lui/dialog", function (dialog) {
			//弹窗提示导入应用的版本信息
			$.ajax({
				url: Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method=getImportAppVersionInfo",
				type: 'POST',
				async: false,
				data: new FormData($('#uploadInitForm')[0]),
				processData: false,
				contentType: false,
				success: function (data) {
					if (data) {
						var jsonData = JSON.parse(data);
						//兼容旧应用的导入
						if ("{}" == JSON.stringify(jsonData)) {
							document.forms['uploadInitForm'].submit();
							$(".model-mask-panel").hide();
							$(".toolbar-bottom").hide();
							dialog.loading();
							window.fitSize('status');
						}else{
							if (jsonData['status'] == 0) {
								var showVersion = jsonData['moduleVersion'];
								if (!showVersion) {
									showVersion = jsonData['coreVersion'];
								}
								dialog.confirm('${lfn:message('sys-modeling-base:modeling.currapp.version')}' + showVersion + '${lfn:message('sys-modeling-base:modeling.confirm.import')}',
										function (value) {
											if (value == true) {
												document.forms[0].submit();
												$(".model-mask-panel").hide();
												$(".toolbar-bottom").hide();
												dialog.loading();
												window.fitSize('status');
											} else {
												$dialogHide();
											}
										});
							}else{
								$(".model-mask-panel").hide();
								$(".toolbar-bottom").hide();
								dialog.loading();
								var href = Com_Parameter.ContextPath+'sys/modeling/base/modelingDatainitMain.do?method=importPage';
								location.href = href;
								window.fitSize('status');
							}
						}
					}
				}
			});
		})
	}

	function $dialogHide() {
		$dialog.hide();
	}

	var interval = setInterval(beginInit, "50");

	function beginInit() {
		if (!window['$dialog'])
			return;
		clearInterval(interval);
		window.fitSize();
	}

	window.fitSize = function (type) {
		if(type==='status')
			$($dialog.frame).find(".lui_dialog_content").css("height", 398-50);
		else
			$($dialog.frame).find(".lui_dialog_content").css("height", 232-50);
	}
</script>
<html:form styleId="uploadInitForm" action="/sys/modeling/base/modelingDatainitMain.do?method=startImport&type=${type}&fdAppId=${fdAppId}" method="post" enctype="multipart/form-data" >
	<div class="model-mask-panel">
		<div class="model-upload">
			<p class="model-upload-desc">${lfn:message('sys-modeling-base:modeling.select.import.file')}</p>
			<div class="model-upload-content">
				<div class="model-upload-content-top">
					<input type="hidden" name="fdAppId" value="${param.fdAppId}"/>
					<input type="file" class="upload" id="initfile" name="initfile" style="display: none"/>
					<div class="model-upload-content-show"><p id="showfile">${lfn:message('sys-modeling-base:modeling.file.format.zip')}</p></div>
					<div class="model-upload-content-btn">
							${lfn:message('sys-modeling-base:modeling.select.file')}
					</div>
				</div>
				<div class="model-upload-content-bottom">
					<p class="model-upload-content-info">${lfn:message('sys-modeling-base:modeling.mustimport.zip')}</p>
					<c:if test="${type == 'form' }">
					<p class="model-upload-Tip">${lfn:message('sys-modeling-base:modeling.nofund.pass.model.tip')}</p>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</html:form>
<div class="toolbar-bottom">
	<ui:button text="${ lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="$dialog.hide('noReload');" order="2"/>
	<ui:button text="${ lfn:message('button.ok')}" onclick="submitForm()" order="2"/>
</div>
<%@ include file="/resource/jsp/view_down.jsp"%>
