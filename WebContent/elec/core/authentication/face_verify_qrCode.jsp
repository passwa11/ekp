<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<c:choose>
		<c:when test="${isAuth}">
			<template:replace name="head">
				<template:super />
				<link rel="Stylesheet"
					href="${LUI_ContextPath}/elec/core/authentication/resource/css/face_very.css" />
				<script>
					var taskFdId = '';
					var qrCodeUrl = "${qrCodeUrl}";
					var canDataValidate = false;
					console.log("***"+qrCodeUrl);
					Com_IncludeFile('jquery.js');
					seajs.use(
							[ 'lui/jquery', 'lui/qrcode', 'lui/dialog'],
							function($,  qrcode, dialog) {
							var url = Com_GetCurDnsHost()
									+ "${KMSS_Parameter_ContextPath}"+qrCodeUrl;
							qrcode.Qrcode({
								text : url,
								element : $(".qrcode_canvas")[0],
								render : 'canvas',
								width : 150,
								height : 150
							});
							
							//轮询是否扫码
							setTimeout(isSweep, 2000);
							function isSweep(){
								var ajaxUrl = Com_Parameter.ContextPath+"elec/core/elec_core_verify/elecCoreVerify.do?method=isSweep";
								$.ajax({
									url : ajaxUrl,
									type : 'post',
									data : {timeMillis:"${timeMillis}"},
									dataType : 'json',
									async : true,     
									error : function(){
										$(".qrcode_canvas")[0].style.display = "none";
										$('.lui-fail-text').text("发起校验时出现了问题");
										$('.lui-fail-back-tips').text("请稍后再试或联系管理员");
										$('.very_fail').show();
										canDataValidate = false;
									} ,   
									success : function(data) {
										if(data.resp){
											$(".qrcode_canvas")[0].style.display = "none";
											$(".validating")[0].style.display = "block";
											taskFdId = data.taskFdId;
											//已扫码，轮询校验结果
											var loading = dialog.loading();
											validateResult(taskFdId,loading);
										}else{
											setTimeout(isSweep, 2000);
										}
									}
								});
							}
							
							//轮询校验结果
							function validateResult(taskFdId,loading){
								var ajaxUrl = Com_Parameter.ContextPath+"elec/core/elec_core_verify/elecCoreVerify.do?method=faceResult&fdId="+taskFdId;
								$.ajax({
									url : ajaxUrl,
									type : 'post',
									data : {},
									dataType : 'json',
									async : true,     
									error : function(){
										$('.lui-fail-text').text("校验过程中出现了问题");
										$('.lui-fail-back-tips').text("请稍后再试或联系管理员");
										$('.very_fail').show();
										canDataValidate = false;
									} ,   
									success : function(data) {
										if(data.resp){
											loading.hide();
											$('.validating').hide();
											if(data.resp == "success"){
												canDataValidate = true;
												$('.very_success').show();
											}else{
												canDataValidate = false;
												$('.very_fail').show();
											}
										}else{
											setTimeout(validateResult(taskFdId,loading), 2000);
										}
									}
								});
							}
							
					});	
					function buildSubmitData(){
						var data = {};
						data.taskFdId = taskFdId;
						return data;
					}
					function dataValidate(){
						return canDataValidate;
					}
				</script>
			</template:replace>
			<template:replace name="body">
				<center>
					<div class="qrcode_canvas"></br>请用手机扫码完成人脸校验</br></br></div>
					<div class="validating" style="display:none">请在手机完成校验，校验过程中请勿关闭此页面</div>
					<div class="very_success" style="display:none">
						<div class="lui-certification">
					        <div class="lui-certification-main">
					            <div class="lui-success-tips">
					                <div class="lui-success-icon"></div>
					                <p class="lui-success-text">恭喜你，校验成功</p>
					                <p class="lui-success-back-tips">点击确定完成校验</p>
					            </div>
					        </div>
					    </div>
					</div>
					<div class="very_fail" style="display:none">
						<div class="lui-certification">
					        <div class="lui-certification-main">
					            <div class="lui-fail-tips">
					                <div class="lui-fail-icon"></div>
					                <p class="lui-fail-text">人脸校验失败</p>
					                <p class="lui-fail-back-tips"></p>
					            </div>
					        </div>
					    </div>
					</div>
				</center>
			</template:replace>
		</c:when>
		<c:otherwise>
			<template:replace name="head">
				<template:super />
				<script>
					function buildSubmitData(){
						var data = {};
						data.taskFdId = '';
						return data;
					}
					function dataValidate(){
						return true;
					}
				</script>
			</template:replace>
			<template:replace name="body">
				未进行实名认证，请先完成实名认证，再进行操作。
			</template:replace>
		</c:otherwise>
	</c:choose>
	
</template:include>
