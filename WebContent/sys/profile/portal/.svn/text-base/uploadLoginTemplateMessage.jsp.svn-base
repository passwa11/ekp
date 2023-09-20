<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
	    <style type="text/css" >
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
			   margin-left: 10px;
               margin-right: 10px;
			   color: #ea4335;
			   text-align: center;
			   font-weight: bold;
			}
	    </style>
	    <script>
		    /**
			* 点击确认（替换自定义登录页模板）按钮 处理事件
			*/
		    function replaceTemplate(){
				var replaceUrl = '<c:url value="/sys/profile/sys_login_template/sysLoginTemplate.do?method=replaceTemplate"/>';
				var templateId = "${lfn:escapeHtml(requestScope.templateId)}";  // 自定义登录页模板ID
				var folderName = "${lfn:escapeHtml(requestScope.folderName)}";  // 自定义登录页模板临时存放目录文件夹名称
				var isDefault = "${requestScope.isDefault}"; // 是否作为默认登录模板
				if(templateId!=""&&folderName!=""){
					var params = {"templateId":templateId,"folderName":folderName,"isDefault":isDefault}
					$.ajax({
						url: replaceUrl,
						async: false,
						data: params,
						type: "POST",
						dataType: 'json',
						success: function (data) {
							if(data && data=="1"){
		                       // 替换成功，提示用户
		                       var msgText = "${lfn:message('sys-profile:sys.loginTemplate.upload.replace.success')}";
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
		        window.location.href= Com_Parameter.ContextPath+"sys/profile/portal/uploadLoginbao.jsp";
		    }		    
		</script>
		       
            <c:if test="${not empty requestScope.templateIsExists && requestScope.templateIsExists==true && not empty requestScope.errorMessage}" >
                <!-- 通过自定义登录包ID检查到登录包已经存在，提示用户 -->
	            <div id="uploadMessageDiv" class="messageContent" >
		 		    ${ requestScope.errorMessage }
		 		</div>
		 		<c:if test="${not empty requestScope.isCriterionTemplate && requestScope.isCriterionTemplate==true}" >
		 		    <!-- 上传的登录包ID与标准登录包ID有重复时,不允许替换 -->
			 		<div style="text-align:center;margin-top:40px;">
		               <div id="closeButtonContent">
		                  <!-- 继续上传按钮 -->
		                  <div class="continue_upload_button" onclick="continueUpload()">${lfn:message('button.continue.upload')}</div>
		                  <!-- 关闭按钮 -->
		                  <div class="confirm_close_button" onclick="closeUploadWindow()">${lfn:message('button.close')}</div>
		               </div>
			 		</div>
		 		</c:if>
		 		<c:if test="${empty requestScope.isCriterionTemplate}" >
		 		    <!-- 通过自定义登录包ID检查到登录包已经存在，提供确认替换按钮 -->
			 		<div style="text-align:center;margin-top:40px;">
			 		   <div id="replaceButtonContent">
			 		      <!-- 确定(替换)按钮 -->
				          <div class="confirm_replace_button" onclick="replaceTemplate()" >${lfn:message('button.ok')}</div>
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
            </c:if>

            <!-- 上传成功，提示用户 -->
            <c:if test="${not empty requestScope.templateIsExists && requestScope.templateIsExists==false && not empty requestScope.successMessage}" >
	            <div class="messageContent">
		 		    ${ requestScope.successMessage }
		 		</div>          
		 		<div style="text-align:center;margin-top:40px;">
	               <!-- 继续上传按钮 -->
	               <div class="continue_upload_button" onclick="continueUpload()">${lfn:message('button.continue.upload')}</div>		 		
	               <!-- 关闭按钮 -->
	               <div class="confirm_close_button" onclick="closeUploadWindow()">${lfn:message('button.close')}</div>
		 		</div>
            </c:if>
            
	</template:replace>
</template:include>