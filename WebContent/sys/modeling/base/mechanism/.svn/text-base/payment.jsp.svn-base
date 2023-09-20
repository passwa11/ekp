<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%
	if("false".equals(request.getAttribute("enableFlow"))){
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain");
	}else{
		pageContext.setAttribute("modelName","com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
	}
%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/mechanism.css?s_cache=${LUI_Cache}"/>
		<style>
			body{
				overflow: hidden;
			}
		</style>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/modeling/base/modelingAppModel.do">
			<div class="mechanism_div">
				<html:hidden property="fdMechanismConfig"/>
				<div class="switch_submit_div" align="left">
					<div class="switch_div">
						<ui:switch property="isPaymentEnable" checked="${mechanism.payment}"
								   onValueChange="changePaymentEnable();"
								   enabledText="${lfn:message('sys-modeling-base:modeling.model.mechanism.payment.set')}"
								   disabledText="${lfn:message('sys-modeling-base:modeling.model.mechanism.payment.set')}"
								   id ="isPaymentEnableId" />
					</div>
					<div class="submit_div">
						<ui:button text="${ lfn:message('button.save') }" order="1" onclick="checkSubmit('updatePayment');"/>
					</div>
				</div>
				<div class="config_div">

				</div>
				<script>
					function changePaymentEnable() {
						// 再次开启，清空选项
						if($("input[name='isPaymentEnable']").val() == 'true') {

						}
					}

					LUI.ready(function() {
						beforeSubmitFuncArr.push(paymentSave);
					});
				</script>
			</div>
		</html:form>
		<script>
			var _validation = new $KMSSValidation();
			var _numberSet = false;
			var beforeSubmitFuncArr = new Array();

			function paymentSave(cfg) {
				cfg["payment"] = {};
				if($("input[name='isPaymentEnable']").val() == 'true'){
					cfg["payment"]="true";
				}else{
					cfg["payment"]="false";
				}
			}

			window.submitForm = function(method){
				var cfg = {};
				var cfgStr = document.getElementsByName("fdMechanismConfig")[0].value;
				if(cfgStr!=""){
					cfg = LUI.toJSON(cfgStr);
				}
				console.log(beforeSubmitFuncArr);
				for (var i = 0; i < beforeSubmitFuncArr.length; i++) {
					beforeSubmitFuncArr[i].call(this,cfg);
				}
				document.getElementsByName("fdMechanismConfig")[0].value = LUI.stringify(cfg);

				Com_Submit(document.modelingAppModelForm, method);
			}

			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
				//提交前确认是否提示
				window.checkSubmit = function (method) {
					//是否开已开启支付机制,true-是
					var paymentEnable = '${mechanism.payment}';
					//如果页面加载时支付机制已经开启，此时要关闭，则提示确认信息
					if($("input[name='isPaymentEnable']").val()=='false' && paymentEnable === 'true' ) {
						var content = {
							"html": "${lfn:message('sys-modeling-base:modeling.model.set.check.content')}",
							"title": "${lfn:message('sys-modeling-base:modeling.model.set.check.title.pre')}"
									+" ["+"${lfn:message('sys-modeling-base:table.mechanismPayment')}"+"] "
									+"${lfn:message('sys-modeling-base:modeling.model.set.check.title.suf')}",
							"width": "500px", "height": "200px"
						};
						content.callback = function (isOk) {
							if (isOk) {
								//确认-提交并提示成功信息
								submitForm(method);
							}else {
								//取消-则重新打开样式
								$("input[name='isPaymentEnable']").val(true);
								$("#isPaymentEnableId .weui_switch").click();
							}
						}
						dialog.confirm(content);
					}else {
						//默认提交-无需确认
						submitForm(method);
					}
				}
			});

		</script>
	</template:replace>
</template:include>