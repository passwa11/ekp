<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/upload_file.css?s_cache=${LUI_Cache}"/>
	<template:replace name="content">
		<style type="text/css">
			.btn_disabled{
				border:1px solid #ccc !important;
				background-color: #ccc !important;
			}
		</style>
		<form id="uploadForm" action="" method="post" enctype="multipart/form-data">
			<%-- 上传路径 --%>
			<input type="hidden" name="uploadActionUrl">
			<!--  下载-->
			<input type="hidden" name="downLoadUrl">
			<%-- 异常后数据是否需要回滚，默认为true --%>
			<input type="hidden" name="isRollBack">
			<!--============================ Starts======================================= -->
			<div class="lui_batch_process_container">
			    <div class="lui_batch_process-content">
			      <div class="lui_batch_process-content-row">
			        <span class="lui_batch_process-row-title">${lfn:message('sys-time:sysTime.import.stepone_msg')}</span>
			        <span class="lui_batch_process-row-download">
			          <button type="button" onclick="download();">
			            <a href="javascript:;">
			              	${lfn:message('sys-time:sysTime.import.button.download')}
			            </a>
			          </button>
			        </span>
			        <span class="lui_batch_process-row-remark">${lfn:message('sys-time:sysTime.import.stepone_title')}</span>
			      </div>
			      <div class="lui_batch_process-content-row">
			        <span class="lui_batch_process-row-title">${lfn:message('sys-time:sysTime.import.steptwo_msg')}</span>
			       	<div class="lui_batch_process-row-wrap">
				        <span class="lui_batch_process-row-searchFile">
				          <input id="upload_file_temp" type="text" placeholder="${lfn:message('sys-time:sysTime.import.steptwo_title')}" readonly="readonly" />
				        </span>
				        <span class="lui_batch_process-row-browse">
				          <button>${lfn:message('sys-time:sysTime.import.button.view')}</button>
				          <input id="upload_file" type="file" name="file" accept=".xls,.xlsx" value="${lfn:message('sys-time:sysTime.import.button.view')}" onchange="change();">
				        </span>
			        </div>
			      </div>
			      <div class="lui_batch_process-content-confirm">
			        <button id="submitBtn" onclick="_submit();">${lfn:message('sys-time:sysTime.import.button.submit')}</button>
			      </div>
			      <c:if test="${!empty resultMsg}">
			      <div class="lui_batch_process-content-tip">
			        <div class="lui_batch_process-tip-title">
			        	${lfn:message('sys-time:sysTime.import.result')}
						<c:choose>
							<c:when test="${state}">
								<img src="${LUI_ContextPath}/sys/attend/resource/images/status_succ.gif" /> ${resultMsg}
							</c:when>
							<c:otherwise>
								<img src="${LUI_ContextPath}/sys/attend/resource/images/status_faile.gif" /> ${lfn:message('sys-time:sysTime.import.failed')}
								<br><br>
								${resultMsg}
							</c:otherwise>
						</c:choose>
			        </div>
			      </div>
			      </c:if>
			    </div> 
			  </div>
		</form>
		
		<script type="text/javascript">
		
			function checkSubmitBtn(){
				var fileContent = document.getElementById("upload_file").value;
				if(fileContent == "" || fileContent == undefined || fileContent == null){
					$("#submitBtn").attr('disabled',true).addClass("btn_disabled");
				}else{
					$("#submitBtn").attr('disabled',false).removeClass("btn_disabled");
				}
			}
			
			seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
				$(function() {
					var uploadActionUrl = "${JsParam.uploadActionUrl}";
					var downLoadUrl = "${JsParam.downLoadUrl}";
					if(uploadActionUrl.length < 1) {
						uploadActionUrl = "${uploadActionUrl}";
					}
					if(downLoadUrl.length < 1) {
						downLoadUrl = "${downLoadUrl}";
					}
					$("form").attr("action", uploadActionUrl); // 设置form请求路径
					$("form [name=uploadActionUrl]").val(uploadActionUrl); // 保存form请求路径
					$("form [name=downLoadUrl]").val(downLoadUrl); // 保存下载请求路径
					var isRollBack = "${JsParam.isRollBack}";
					if(isRollBack.length < 1) {
						isRollBack = "${isRollBack}";
					}
					$("form [name=isRollBack]").val(isRollBack);
					// 按钮状态
					checkSubmitBtn();
					//上传成功回写数据
					var resultMsg="${resultMsg}";
					if(resultMsg){
						var resultData = eval(${resultData});
						if(resultData){
							var callback = window.parent.importExcelCallback;
							callback && callback(resultData);
						}
					}
					
				});
				
				// 上传
				window._submit = function() {
					$("#uploadForm").submit();
				};
				// 取消
				window._cancel = function() {
					window.$dialog.hide();
				};
				
				window.change = function(){
					document.getElementById("upload_file_temp").value=document.getElementById("upload_file").value; 
					checkSubmitBtn();
				};
				
				window.download = function(){
					var downLoadUrl = $("input[name='downLoadUrl']").val();
					var temp = document.createElement("form");
					  temp.action = downLoadUrl;
					  temp.method = "post";
					  temp.id = "downloadTempletForm";
					  temp.style.display = "none";
					  document.body.appendChild(temp);
					  temp.submit();
					  $('#downloadTempletForm').remove();
				};
			});
		</script>
	</template:replace>
</template:include>