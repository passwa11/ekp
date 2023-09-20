<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<center>
			<div style="width:100%;align:center;padding-top:100px;">
				<div style="width:100%;align:center;">
					<div class="qrcode_canvas" style="width:100%;align:center;"></div>
				</div>
				${lfn:message('sys-modeling-base:modeling.code.payment')}
			</div>
			<div id="payResult" style="">

			</div>
		</center>
		<script type="text/javascript">
			seajs.use([ 'lui/jquery', 'lui/qrcode' ], function($, qrcode) {
				window.qrcodeShow = function(url) {
					qrcode.Qrcode({
						text : url,
						element : $(".qrcode_canvas")[0],
						render : 'canvas',
						width : 150,
						height : 150
					});
				};

				var time = 0;
				var getOrderPayResultInterval;

				window.getOrderPayResult = function (){
					var payResultDiv = $("#payResult");
					if(time>=180){
						clearInterval(getOrderPayResultInterval);
						setTimeout(function(){
							$dialog.hide("close");
						}, 2000);
						return;
					}
					time ++;
					var url = '<c:url value="/sys/modeling/main/sysModelingOperation.do?method=getPaymentOrderStatus" />'+'&modelName=${param.modelName}&modelId=${param.modelId}&fdKey=${param.fdKey}';
					$.ajax({
						type: "POST",
						url: url,
						async:false,
						dataType: "json",
						success: function(data){
							if(data.status=="1"){
								var payResult = data.data;
								var paymentStatus = payResult.paymentStatus;
								if(paymentStatus=="SUCCESS"){
									payResultDiv.text("${lfn:message('sys-modeling-base:modeling.code.payment.success') }");
									clearInterval(getOrderPayResultInterval);
									setTimeout(function(){
										$dialog.hide("close");
									}, 2000);

								}else if(paymentStatus=="CLOSED"){
									payResultDiv.text("${lfn:message('sys-modeling-base:modeling.code.payment.close') }");
									clearInterval(getOrderPayResultInterval);
								}else if(paymentStatus=="PAYERROR"){
									payResultDiv.text("${lfn:message('sys-modeling-base:modeling.code.payment.fail') }");
									clearInterval(getOrderPayResultInterval);
									setTimeout(function(){
										$dialog.hide("close");
									}, 2000);
								}
							}else{
								alert("获取支付结果失败，"+data.msg);
							}
						},
						error: function(err){
							alert(JSON.stringify(err));
						}
					});
				}

				$(function() {
					qrcodeShow("${param.codeUrl}");
					getOrderPayResultInterval = setInterval("getOrderPayResult()",1000);
				});
			});
		</script>
	</template:replace>
</template:include>