<%@ page import="com.landray.kmss.third.payment.util.ThirdPaymentPayUtil" %>
<%@ page import="com.landray.kmss.third.payment.model.ThirdPaymentOrder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String modelName = request.getParameter("modelName");
	String modelId = request.getParameter("modelId");
	String fdKey = request.getParameter("fdKey");
	ThirdPaymentOrder order = ThirdPaymentPayUtil.getOrder(modelName,modelId,fdKey);
	String codeUrl = order.getFdCodeUrl();
	request.setAttribute("codeUrl",codeUrl);
%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
		<center>
			<div style="width:100%;align:center;padding-top:100px;">
				<div style="width:100%;align:center;">
					<div class="qrcode_canvas" style="width:100%;align:center;"></div>
				</div>
				${lfn:message('third-payment:payment.weixin.scanCode')}
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
						return;
					}
					time ++;
					var url = '<c:url value="/third/payment/third_payment_order/thirdPaymentOrder.do?method=getOrderStatus" />'+'&modelName=${HtmlParam.modelName}&modelId=${HtmlParam.modelId}&fdKey=${HtmlParam.fdKey}';
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
									payResultDiv.text("支付成功，请关闭本页面");
									clearInterval(getOrderPayResultInterval);
								}else if(paymentStatus=="CLOSED"){
									payResultDiv.text("支付已关闭");
									clearInterval(getOrderPayResultInterval);
								}else if(paymentStatus=="PAYERROR"){
									payResultDiv.text("支付失败");
									clearInterval(getOrderPayResultInterval);
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
					//weixin://wxpay/bizpayurl?pr=1pUgBmSzz
					qrcodeShow("${codeUrl}");
					getOrderPayResultInterval = setInterval("getOrderPayResult()",1000);
				});
			});
		</script>
	</template:replace>
</template:include>