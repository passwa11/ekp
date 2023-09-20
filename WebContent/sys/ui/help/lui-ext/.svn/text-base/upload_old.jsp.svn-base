<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
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
			* 点击上传按钮 处理事件
			*/	
		    function uploadTheme(){
		    	var fileValue = $("input[type='file'][name='file']").val();
				var index = fileValue.lastIndexOf("\\");
				var fileName = fileValue.substring(index + 1);
				var ext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase(); // 文件扩展名
	            if($.trim(fileValue)==""){
	            	alert("${lfn:message('sys-ui:ui.help.luiext.selfile')}");
	            	return;
	            }
				// 校验文件类型
		        if(ext!="zip"){
		           alert("${lfn:message('sys-ui:ui.help.luiext.upload.fileType')}");
		           return;
		        }
		    	
		    	$("form[name='sysUiExtendForm']").submit();
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
		<html:form enctype="multipart/form-data" style="padding:5px;" action="/sys/ui/sys_ui_extend/sysUiExtend.do?method=upload" method="post">
   			
   			<!-- 上传ZIP文件 -->
   			<c:if test="${empty themeIsExists}" >  
	   			<a id="upload_excel_link_button" class="a_upload_excel" href="javascript:void(0);">
				     <input type="file" name="file" accept=".zip" onchange="uploadFileChange(this);">${lfn:message('sys-ui:ui.help.luiext.select.zip.file')}
				</a>
				<div class="upload_file_name" ></div>
				<div class="upload_theme_button" >
				    <!-- 上传按钮 -->
					<div class="confirm_upload_button" onclick="uploadTheme()" >${lfn:message('button.zip.upload')}</div>
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
	</template:replace>
</template:include>