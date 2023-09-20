<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.profile.model.LoginConfig"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
	LoginConfig loginConfigObj = new LoginConfig();
	String logintemplatevalue = loginConfigObj.getCustomLoginTemplateEnable();
	if("false".equals(logintemplatevalue)){
		
	}else{
%>
<template:include ref="config.view">
<template:replace name="content">
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp");
</script>
<!-- 上传登录模板-->
<template:replace name="head">
	<link rel="stylesheet" type="text/css" href="../resource/css/index.css">
	<link rel="stylesheet" type="text/css" href="../resource/css/login_upload.css">
</template:replace>
<html:form action="/sys/profile/sys_login_template/sysLoginTemplate.do" enctype="multipart/form-data">
	<html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <div class="ld-upload-login-tel-content">
            <div class="ld-upload-login-tel-content-file clearfix">
                <span>${lfn:message('sys-profile:sys.loginTemplate.file')}</span>
                <div class="uploadContent">
                    <div class="uploadBox">
						<label class="lui_queueList lui_queueList_s lui_queueList_block">
							<div class="lui_upload_container webuploader-container">
								<div class="webuploader-pick">
									<i></i>${lfn:message('sys-attachment:attachment.layout.upload.note2')}<span class="lui_text_primary">${lfn:message('sys-attachment:attachment.layout.upload')}</span>
									<input id="loginFile" type="file" name="file" class="webuploader-element-invisible" accept=".zip" onchange="afterUploadFile(this)">
									<span class="inputfile_result"></span>
								</div>
							</div>
						</label>
						<div class="upload_list_tr upload_list_tr_edit upload_list_tr_edit_block" style="display: none;">
							<div class="upload_list_tr_edit_l">
								<div class="upload_list_icon">
									<i class="icon_zip"></i>
								</div>
								<div class="upload_list_filename_edit">
									<span class="upload_list_filename_title"></span>
								</div>
								<div class="upload_list_size"></div>
							</div>
							<div class="upload_list_tr_edit_r">
								<div class="upload_list_status">
									<div class="upload_opt_status success">
										<i></i>${lfn:message('sys-attachment:sysAttMain.msg.uploadSucess')}
									</div>
								</div>
								<div class="upload_list_operation">
									<div class="upload_opt_icon upload_opt_delete" onclick="deleteFile();">
										<span class="upload_opt_tip"><i class="upload_opt_tip_arrow"></i>
										<i class="upload_opt_tip_inner">${lfn:message('button.delete')}</i></span>
									</div>
								</div>
							</div>
						</div>
					</div>
                    <div class="uploadTipsBox clearfix">
                        <p>
                            <span><a href="${LUI_ContextPath }/resource/login/login.zip">${lfn:message('sys-profile:sys.loginTemplate.download.example')}</a></span>
                            <span><a target="_blank" href="${ LUI_ContextPath }/sys/profile/resource/help/login-upload-helpDoc.pdf">${lfn:message('sys-profile:sys.loginTemplate.helpdoc')}</a></span>
                        </p>
                    </div>
                    <div class="imgPreviewBox" style="display:none">
                        <p>${lfn:message('sys-ui:mall.theme.preview') }</p>
                        <div>
                            <img  id='previewImg' src="${ LUI_ContextPath }/sys/profile/resource/images/u1497.png" alt="">
                            <input type='hidden' id='directoryPath'>
                        </div>
                    </div> 
                </div>
            </div>
            <div class="ld-upload-login-tel-content-isDefault clearfix">
                <span>${lfn:message('sys-profile:sys.loginTemplate.default')}</span>
                <div>
                <ui:switch property="fdDefault" enabledText="${lfn:message('sys-ui:ui.switch.enabled')}"  disabledText="${lfn:message('sys-ui:ui.switch.disabled')}"></ui:switch>
                </div>
            </div>
            <div class="lui_portal_footer_btnGroup" align="center">
				<ui:button styleClass="lui_toolbar_btn_gray" style="border-radius: 4px;" text="${lfn:message('button.close') }" onclick="delFileBeforeClose();Com_CloseWindow();"></ui:button>
				<ui:button text="${lfn:message('button.submit') }" onclick="uploadTemplate()"></ui:button><span class="upload-btn-siplt"></span>
			</div>
    </div>
</html:form>
	<script>
		var _validate = $KMSSValidation(document.sysLoginTemplateForm);
		seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
			window.uploadTemplate = function() {
				var file = document.getElementsByName("file");
				if(file[0].value==null || file[0].value.length==0){
					dialog.alert("${lfn:message('sys-profile:sys.loginTemplate.upload.error') }");
					return false;
				}
				Com_Submit(sysLoginTemplateForm,'uploadTemplate');
			};
			
			window.deleteFile = function() {
				$(".upload_list_tr_edit_block").hide();
				$(".imgPreviewBox").hide();
				$(".lui_queueList_block").show();
				var file = document.getElementById('loginFile');
				file.outerHTML = file.outerHTML;
			}
		})
		function getAttachmentObject(fdKey){
			var top = Com_Parameter.top || window.top;
			try {
				if(top.window){
					return top.window.Attachment_ObjectInfo[fdKey]; 
				}
			} catch (e) {
				return window.Attachment_ObjectInfo[fdKey];
			}
		}
		//关闭窗口前删除预览留下的文件
		function delFileBeforeClose(){
			var path  = $("#directoryPath").val();
			if(path){
				$.ajax({
			    	url: Com_Parameter.ContextPath + "sys/profile/sys_login_template/sysLoginTemplate.do?method=delPreviewFile",
			    	dataType : 'json',
						type : 'post',
						data: {
							"directoryPath":path
						},
						async : false,
						success : function(rtn){
						}
				  }); 
			}
		}
		// 格式化文件大小
		function formatFileSize(fileSize) {
		    if (fileSize < 1024) {
		        return fileSize + 'B';
		    } else if (fileSize < (1024*1024)) {
		        var temp = fileSize / 1024;
		        temp = temp.toFixed(2);
		        return temp + 'KB';
		    } else if (fileSize < (1024*1024*1024)) {
		        var temp = fileSize / (1024*1024);
		        temp = temp.toFixed(2);
		        return temp + 'MB';
		    } else {
		        var temp = fileSize / (1024*1024*1024);
		        temp = temp.toFixed(2);
		        return temp + 'GB';
		    }
		}
		//上传后触发
		function afterUploadFile(){
			seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
			  var formData = new FormData();
			  formData.append('file', $('#loginFile')[0].files[0]);
			  var _loading = dialog.loading("${lfn:message('sys-attachment:mui.sysAttMain.upload') }");
			  $.ajax({
		      	url: Com_Parameter.ContextPath + "sys/profile/sys_login_template/sysLoginTemplate.do?method=getTemplateInfo",
		      	dataType : 'json',
					type : 'post',
					data: formData,
					cache: false,  
			        contentType: false,  
			        processData: false,
					success : function(rtn){
						if(rtn.status=="1"){
							var src = Com_Parameter.ContextPath + rtn.fdThumbnail;
			    			$("#previewImg").attr("src",src);
			    			$("#directoryPath").val(rtn.directoryPath);
			    			$(".imgPreviewBox").show();
			    			
			    			$(".upload_list_tr_edit_block").show();
							$(".lui_queueList_block").hide();
							// 显示文件信息
							var _f = $('#loginFile')[0].files[0];
							$(".upload_list_filename_title").text(_f.name);
							$(".upload_list_size").text(formatFileSize(_f.size));
							console.log(_f.name, formatFileSize(_f.size));
						}else{
							dialog.alert("${lfn:message('sys-profile:sys.loginTemplate.login.config.notexist.log') }");
							var obj = document.getElementById('loginFile') ; 
							obj.outerHTML=obj.outerHTML; 
							$(".imgPreviewBox").hide();
						}
					},
					complete : function() {
						_loading.hide();
					}
			  });
			}); 
		}
	</script>
</template:replace>
</template:include>
<%
	}
%>
