<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.view">
	<template:replace name="title">
		人脸校验
	</template:replace>
	<c:choose>
		<c:when test="${isAuth}">
			<template:replace name="head">
				<link charset="utf-8" rel="stylesheet"
					href="<%=request.getContextPath()%>/elec/core/authentication/mobile/css/face_very.css">
				<script type="text/javascript"
					src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
				<script type="text/javascript">
					var taskFdId = '';
					var qrCodeUrl = "${qrCodeUrl}";
					var canDataValidate = false;

					require(
							['mui/dialog/Tip'],
							function(tip) {
								var url = "${KMSS_Parameter_ContextPath}"
										+ qrCodeUrl;
								setTimeout(isSweep, 2000);
								window.open(url, '_blank');
								function isSweep() {
									var ajaxUrl = "${KMSS_Parameter_ContextPath}elec/core/elec_core_verify/elecCoreVerify.do?method=isSweep";
									$.ajax({
										url : ajaxUrl,
										type : 'post',
										data : {
											timeMillis : "${timeMillis}"
										},
										dataType : 'json',
										async : true,
										error : function() {
											$(".qrcode_canvas").hide();
											$('.lui-fail-text').text(
													"发起校验时出现了问题");
											$('.lui-fail-back-tips').text(
													"请稍后再试或联系管理员");
											$('.very_fail').show();
											canDataValidate = false;
										},
										success : function(data) {
											if (data.resp) {
												$(".qrcode_canvas").hide();
												$(".validating").show();
												taskFdId = data.taskFdId;
												//已扫码，轮询校验结果
												validateResult(taskFdId);
											} else {
												setTimeout(isSweep, 2000);
											}
										}
									});
								}

								//轮询校验结果
								function validateResult(taskFdId) {
									var ajaxUrl = "${KMSS_Parameter_ContextPath}elec/core/elec_core_verify/elecCoreVerify.do?method=faceResult&fdId="
											+ taskFdId;
									$.ajax({
										url : ajaxUrl,
										type : 'post',
										data : {},
										dataType : 'json',
										async : true,
										error : function() {
											$('.lui-fail-text').text(
													"校验过程中出现了问题");
											$('.lui-fail-back-tips').text(
													"请稍后再试或联系管理员");
											$('.very_fail').show();
											canDataValidate = false;
										},
										success : function(data) {
											if (data.resp) {
												$('.validating').hide();
												if(data.resp == "success"){
													canDataValidate = true;
													$('.very_success').show();
												}else{
													canDataValidate = false;
													$('.very_fail').show();
												}
												
											} else {
												setTimeout(validateResult(
														taskFdId),
														2000);
											}
										}
									});
								}

							});

					//用户是否设置有对应的验证信息
					function isHasSetting() {
						return true;
					}

					function buildSubmitData() {
						var data = {};
						data.taskFdId = taskFdId;
						return data;
					}
					function dataValidate() {
						return canDataValidate;
					}
				</script>
			</template:replace>
			<template:replace name="content">
				<div class="identity_mobile">
					<div class="muiVerify-container">
						<h3 class="muiVerify-title">人脸校验</h3>
						<center>
							<div class="qrcode_canvas">
							<br><br><br><br>
								页面跳转中...</br>
								</br>
							</div>
							<div class="validating" style="display: none">
							<br><br><br><br>
							在新页面完成校验...<br>
							请勿关闭此页面
							</div>
							<div class="very_success" style="display: none">
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
							<div class="very_fail" style="display: none">
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
						<div class="muiVerify-head">
							<div class="muiVerify-form"></div>
							<p class="muiVerify-error" id="msg_block"></p>
						</div>
						<p class="muiVerify-tips"></p>
					</div>
				</div>
			</template:replace>
		</c:when>
		<c:otherwise>
			<template:replace name="head">
				<template:super />
				<script>
					function isHasSetting(){
						return false;
					}
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
			<template:replace name="content">
				未进行实名认证，请先完成实名认证，再进行操作。
			</template:replace>

		</c:otherwise>
	</c:choose>

</template:include>
