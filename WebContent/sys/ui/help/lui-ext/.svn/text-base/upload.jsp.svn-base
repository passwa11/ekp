<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.view">
<template:replace name="head">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/profile/resource/css/index.css">
	<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/profile/resource/css/login_upload.css">
	<style type="text/css" >
		.a_upload_excel {
			float: left;
		    padding: 4px 10px;
		    height: 20px;
		    line-height: 20px;
		    position: relative;
		    cursor: pointer;
		    color: #4285f4;
		    background: #fff;
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
		    background: #4285f4;
		    border-color: #ccc;
		    text-decoration: none;
		}
		.upload_file_name {
		    float: left;
		    margin-top: 6px;
		    margin-left: 16px;		
		}	    
		.upload_theme_button {
		    margin-right: 10px;
		    float:right;
		}
		.confirm_upload_button{
		    padding: 4px 20px;
		    height: 20px;
		    line-height: 20px;
		    cursor: pointer;
		    color: #fff;
		    background: #4285f4;
		    border: 1px solid #4285f4;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1;				
		}
		
		.confirm_replace_button{
		    padding: 4px 20px;
		    height: 20px;
		    line-height: 20px;
		    cursor: pointer;
		    color: #fff;
		    background: #4285f4;
		    border: 1px solid #4285f4;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1;	
		}
		.cancel_replace_button{
		    margin-left: 20px;
		    padding: 4px 20px;
		    height: 20px;
		    line-height: 20px;
		    cursor: pointer;
		    color: #999;
		    background-color: #fafafa;
		    border: 1px solid #d5d5d5;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1;
		}
		.continue_upload_button{
		    padding: 4px 20px;
		    height: 20px;
		    line-height: 20px;
		    cursor: pointer;
		    color: #fff;
		    background: #4285f4;
		    border: 1px solid #4285f4;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1;			
		}
		.confirm_close_button{
		    padding: 4px 20px;
		    height: 20px;
		    line-height: 20px;
		    cursor: pointer;
		    color: #999;
		    background-color: #fafafa;
		    border: 1px solid #d5d5d5;
		    border-radius: 4px;
		    overflow: hidden;
		    display: inline-block;
		    *display: inline;
		    *zoom: 1;
		    margin-left: 30px;		
		}
		.messageContent{
		   margin-top: 20px;
		   color: #ea4335;
		   text-align: center;
		   font-weight: bold;
		}
		.ld-upload-login-tel-content .uploadContent .imgPreviewBox{
    		height: 380px;
		}
		.imgPreviewBox div{
    		height: 320px;
		}
		.no_thumb{
			background: #f8f8f8 url(${ LUI_ContextPath }/sys/profile/resource/images/resource@2x.png) no-repeat center;
			    margin-top: 23px;
			    height: 380px;
			    border: 1px solid #EEEEEE;
			    border-radius: 4px;
		}
    </style>
    <script>
	    /**
		* 点击上传zip 处理事件
		* @param fileDom  HTML DOM Element对象
		* @return
		*/	
		function uploadFileChange(fileDom){		
			var index = fileDom.value.lastIndexOf("\\");
			var fileName = fileDom.value.substring(index + 1);
			// 显示上传文件名称
			$(".upload_file_name").text(fileName); 
		}	
	    
	    /**
		* 点击确认（替换主题）按钮 处理事件
		*/
	    function replaceTheme(){
			var replaceUrl = '<c:url value="/sys/ui/sys_ui_extend/sysUiExtend.do?method=replaceExtend"/>';
			var extendId = "${lfn:escapeHtml(extendId)}";     // 主题包ID
			var folderName = "${lfn:escapeHtml(folderName)}"; // 主题包临时存放目录文件夹名称
			if(extendId!=""&&folderName!=""){
				var params = {"extendId":extendId,"folderName":folderName}
				$.ajax({
					url: replaceUrl,
					async: false,
					data: params,
					type: "POST",
					dataType: 'json',
					success: function (data) {
						if(data && data=="1"){
		                       // 替换成功，提示用户
		                       var msgText = "${lfn:message('sys-ui:ui.help.luiext.upload.replace.success')}";
		                       $("#uploadMessageDiv").text(msgText);
		                       $("#replaceButtonContent").hide();
		                       $("#closeButtonContent").show();

						}
					}
				});
			}    	
	    }
	    
	    /**
		* 点击取消按钮 处理事件
		*/
	    function cancelReplace(){
	    	seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
	              window.$dialog.hide();
	        });		    	
	    }
	    /**
		* 点击关闭按钮 处理事件
		*/		    
	    function closeUploadWindow(){
  		    	seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
  		              window.$dialog.hide();
  		        });		    	
	    }
	    /**
		* 点击继续上传 处理事件
		*/	
	    function continueUpload(){
	        window.location.href= Com_Parameter.ContextPath+"sys/ui/help/lui-ext/upload.jsp";
	    }
	</script>
</template:replace>
<template:replace name="content">
<script>
Com_IncludeFile("validation.js|plugin.js|validation.jsp");
</script>
<!-- 上传登录模板-->
<html:form action="/sys/ui/sys_ui_extend/sysUiExtend.do?method=upload" enctype="multipart/form-data">
    <c:if test="${empty themeIsExists}" >  
    <div class="ld-upload-login-tel-content">
         <div class="ld-upload-login-tel-content-file clearfix">
             <span>${lfn:message('sys-ui:mall.theme.add') }</span>
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
                 <div class="imgPreviewBox" style="display:none">
                     <p>${lfn:message('sys-ui:mall.theme.preview') }</p>
                     <div>
                         <img  id='previewImg' src="" alt="">
                         <input type='hidden' id='directoryPath'>
                     </div>
                 </div>
                 <div class="thumb_img_bg no_thumb" style="display:none;"></div>
             </div>
         </div>
         <div class="lui_portal_footer_btnGroup" align="center">
			<ui:button styleClass="lui_toolbar_btn_gray" style="border-radius: 4px;" text="${lfn:message('button.close') }" onclick="delFileBeforeClose();Com_CloseWindow();"></ui:button>
			<ui:button text="${lfn:message('button.submit') }" onclick="uploadTemplate()"></ui:button><span class="upload-btn-siplt"></span>
		</div>
    </div>
    </c:if>
    <!-- 通过主题ID检查到主题已经存在，提示用户 -->
    <c:if test="${not empty themeIsExists && themeIsExists==true && not empty errorMessage}" >
    <div id="uploadMessageDiv" class="messageContent" >
	    ${ errorMessage }
	</div>
	<div style="text-align:center;margin-top:40px;">
	   <div id="replaceButtonContent">
	      <!-- 确定按钮 -->
         <div class="confirm_replace_button" onclick="replaceTheme()" >${lfn:message('button.ok')}</div>
         <!-- 取消按钮 -->
         <div class="cancel_replace_button" onclick="cancelReplace()">${lfn:message('button.cancel')}</div>
            </div>
            <div id="closeButtonContent" style="display:none;">
               <!-- 继续上传按钮 -->
               <div class="continue_upload_button" onclick="continueUpload()">${lfn:message('button.continue.upload')}</div>
               <!-- 关闭按钮 -->
               <div class="confirm_close_button" onclick="closeUploadWindow()">${lfn:message('button.close')}</div>
            </div>
	</div>
        </c:if>

        <!-- 上传成功，提示用户 -->
        <c:if test="${not empty themeIsExists && themeIsExists==false && not empty successMessage}" >
         <div class="messageContent">
	    ${ successMessage }
	</div>          
	<div style="text-align:center;margin-top:40px;">
            <!-- 继续上传按钮 -->
            <div class="continue_upload_button" onclick="continueUpload()">${lfn:message('button.continue.upload')}</div>		 		
            <!-- 关闭按钮 -->
            <div class="confirm_close_button" onclick="closeUploadWindow()">${lfn:message('button.close')}</div>
	</div>
    </c:if>
</html:form>
<script>
var _validate = $KMSSValidation(document.sysUiExtendForm);
seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
	window.uploadTemplate = function() {
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			dialog.alert("${lfn:message('sys-ui:sys.template.upload.error') }");
			return false;
		}
		Com_Submit(sysUiExtendForm,'upload');
	};
	window.deleteFile = function() {
		$(".upload_list_tr_edit_block").hide();
		$(".no_thumb").hide();
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
	    	url: Com_Parameter.ContextPath + "sys/ui/sys_ui_extend/sysUiExtend.do?method=delPreviewFile",
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
//格式化文件大小
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
      	url: Com_Parameter.ContextPath + "sys/ui/sys_ui_extend/sysUiExtend.do?method=getThemeInfo",
      	dataType : 'json',
			type : 'post',
			data: formData,
			//async : false,
			cache: false,  
	        contentType: false,  
	        processData: false,
			success : function(rtn){
				console.log(rtn);
				if(rtn.status=="1"){
					if(rtn.fdThumbnail && rtn.fdThumbnail != 'null') {
						var src = Com_Parameter.ContextPath + rtn.fdThumbnail;
		    			$("#previewImg").attr("src",src);
		    			$("#directoryPath").val(rtn.directoryPath);
		    			$(".imgPreviewBox").show();
					} else {
						$(".no_thumb").show();
					}
	    			
	    			$(".upload_list_tr_edit_block").show();
					$(".lui_queueList_block").hide();
					// 显示文件信息
					var _f = $('#loginFile')[0].files[0];
					$(".upload_list_filename_title").text(_f.name);
					$(".upload_list_size").text(formatFileSize(_f.size));
					console.log(_f.name, formatFileSize(_f.size));
				}else{
					seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
						if (rtn.msg){
							dialog.alert(rtn.msg);
						}else{
							dialog.alert("${lfn:message('sys-ui:sys.ui.upload.error') }");
						}
						var obj = document.getElementById('loginFile') ;
						obj.outerHTML=obj.outerHTML; 
						$(".imgPreviewBox").hide();
					})
				}
			},
			complete : function() {
				_loading.hide();
			}
	  });
	});
}
Com_AddEventListener(window,'load',function(){
})
</script>
</template:replace>
</template:include>
