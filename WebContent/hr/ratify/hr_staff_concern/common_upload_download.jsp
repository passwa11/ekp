<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/hr/ratify/resource/css/maincss.css?s_cache=${LUI_Cache}"/>
	</template:replace>
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
			        <span class="lui_batch_process-row-title">${lfn:message('hr-staff:hrStaff.import.stepone_msg')}</span>
			        <span class="lui_batch_process-row-download">
			          <button type="button" onclick="download();">
			            <a href="javascript:;">
			              	${lfn:message('hr-staff:hrStaff.import.button.download')}
			            </a>
			          </button>
			        </span>
			        <span class="lui_batch_process-row-remark">${lfn:message('hr-staff:hrStaff.import.stepone_title')}</span>
			      </div>
			      <div class="lui_batch_process-content-row">
			        <span class="lui_batch_process-row-title">${lfn:message('hr-staff:hrStaff.import.steptwo_msg')}</span>
			       	<div class="lui_batch_process-row-wrap">
				        <span class="lui_batch_process-row-searchFile">
				          <input id="upload_file_temp" type="text" placeholder="${lfn:message('hr-staff:hrStaff.import.steptwo_title')}" readonly="readonly" />
				        </span>
				        <span class="lui_batch_process-row-browse">
				          <button>${lfn:message('hr-staff:hrStaff.import.button.view')}</button>
				          <input id="upload_file" type="file" name="file" accept=".xls,.xlsx" value="${lfn:message('hr-staff:hrStaff.import.button.view')}" onchange="change();">
				        </span>
			        </div>
			      </div>
			      <div class="lui_batch_process-content-confirm">
			        <button id="submitBtn" onclick="_submit();" disabled="disabled">${lfn:message('hr-staff:hrStaff.import.button.submit')}</button>
			      </div>
			      <c:if test="${!empty resultMsg}">
			      <div class="lui_batch_process-content-tip">
			        <div class="lui_batch_process-tip-title">
			        	${lfn:message('hr-staff:hrStaff.import.result')}
						<c:choose>
							<c:when test="${state}">
								<img src="${LUI_ContextPath}/hr/staff/resource/images/status_succ.gif" />
								<c:out value="${resultMsg}"></c:out>
							</c:when>
							<c:otherwise>
								<img src="${LUI_ContextPath}/hr/staff/resource/images/status_faile.gif" /> ${lfn:message('hr-staff:hrStaff.import.failed')}
								<br><br>
                			<c:out value="${resultMsg}"></c:out>
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
				
				var  msg = $(".lui_batch_process-tip-title").text();
				
				if(msg){
					var  msgArr = msg.split("<br>")
					var tipTitle = $(".lui_batch_process-tip-title");
					tipTitle.text("")
					for(var msg of msgArr){
						var el = $("<p></p>").text(msg);
						tipTitle.append(el)
					}
				}
			}
			
			seajs.use([ 'lui/jquery','lui/dialog'],function($, dialog) {
				$(function() {
					var uploadActionUrl = "${JsParam.uploadActionUrl}&ratify=true";
					console.log(uploadActionUrl)
					var downLoadUrl = "${JsParam.downLoadUrl}";
					var uploadActionUrl_new="${JsParam.uploadActionUrl}";
					if(!uploadActionUrl_new) {
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
				});
				
				// 上传
				window._submit = function() {
					if(window.console){
						window.console.log('提交导入');
					}
					$("#submitBtn").attr('disabled',true).addClass("btn_disabled");
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