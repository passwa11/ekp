<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/resources/css/collection.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet"
			  href="${KMSS_Parameter_ContextPath}sys/modeling/base/externalQuery/css/externalQuery.css?s_cache=${LUI_Cache}"/>
		<style>
		</style>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/modeling/base/externalQuery.do">
			<div class="external-content">
				<div class="external-content-head">
					<div class="external-content-head-btn">
						<div class="head-btn btn-update" onclick="submitForm('save');">${ lfn:message('button.save') }</div>
					</div>
				</div>
				<div class="external-form-main">
					<div class="external-form-main-head">
						<div class="external-form-main-title">
							<span></span>
							<strong>${lfn:message('sys-modeling-base:modeling.public.inquiry')}</strong>
						</div>
						<ui:switch property="isEnable"
								   checked = "${isEnable}"
								   checkVal="1"
								   unCheckVal="0"
								   onValueChange="isEnableChange()"></ui:switch>
					</div>

					<div class="external-query-dec">
							${lfn:message('sys-modeling-base:modeling.open.public.inquiry')}
					</div>
					<div class="external-query-on" style="display: none">

					</div>
				</div>

				<div class="success-message" style="display: none" >
					<i class="success-icon"></i>
						${lfn:message('sys-modeling-base:modeling.saved.success')}
				</div>

				<div class="fail-message" style="display: none">
					<i class="fail-icon"></i>
						${lfn:message('sys-modeling-base:modeling.saved.failed')}
				</div>
			</div>

			<html:hidden property="fdId"/>
			<html:hidden property="fdConfig"/>
			<html:hidden property="fdModelId"/>
		</html:form>
		<script>
			Com_IncludeFile('calendar.js');
			var listviewOption = {
				token:'${modelingExternalQueryForm.fdToken}',
				modelId:'${modelingExternalQueryForm.fdModelId}',
				<c:if test="${baseInfo!=null}">baseInfo:${baseInfo},</c:if>
				<c:if test="${attachments!=null}">attachments:${attachments}</c:if>
			}
			seajs.use(["sys/modeling/base/externalQuery/js/externalQuery","lui/jquery", "sys/ui/js/dialog","lui/topic"],
					function (externalQuery,$, dialog,topic) {
						function checkInit(){
							var url = '<c:url value="/sys/modeling/base/modelingAppFlow.do" />?method=hasXForm&fdModelId=${param.fdModelId}';
							$.ajax({
								url: url,
								type: 'GET',
								dataType: 'json',
								success: function(rtn){
									if(rtn.hasXForm === "true"){
										init()
									}else{
										var nurl = Com_Parameter.ContextPath + "sys/modeling/base/noxform/noxform.jsp?page=externalQuery";
										window.location.href = nurl;
									}
								}
							});
						}
						function init() {
							var currentData = {};
							if (listviewOption.modelId) {
								var url = "${LUI_ContextPath}/sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + listviewOption.modelId;
								$.ajax({
									url: url,
									type: "get",
									async: false,
									success: function (data, status) {
										if (data) {
											if(typeof(data) === 'object'){
												currentData = data;
											} else{
												currentData = JSON.parse(data);
											}
										}
									}
								});
							}
							$("[id='cfg_iframe']", parent.document).css("height","500px");
							var fdConfig =JSON.stringify(${modelingExternalQueryForm.fdConfig})
							if(!fdConfig || fdConfig == ''){
								fdConfig = '{}';
							}
							var cfg = {
								fdConfig:JSON.parse(fdConfig),
								container:$(".external-query-on"),
								currentData:currentData,
								channel:"pc"
							};
							window.externalQueryWgt = new externalQuery.ExternalQuery(cfg);
							externalQueryWgt.draw();
						}
						checkInit();

						//点击二维码框之外时事件，关闭二维码框
						$(document).on('click',function (e) {
							//判断事件点击的元素为画布canvas、div.detailQrCode时，不隐藏二维码框
							if(!($(e)[0].target.localName == "canvas" || $(e)[0].target.className == "detailQrCode" )) {
								$("#qrcodeDiv").css("display", "none");
							}

							$('.titleSelectList').css('display',"none");
						});


						window.isEnableChange = function () {
							if("0"==$("[name='isEnable']").val()){
								$(".external-query-on").css("display","none");
							}else{
								$(".external-query-on").css("display","block");
							}
						}

						window.submitForm = function(method){
							var keyData = window.externalQueryWgt.getKeyData();
							$("[name='fdConfig']").val(JSON.stringify(keyData).replace(/&quot;/g,"\\\""));

							//校验参数
							var validateResult = window.ExternalQueryValidate.validate(keyData)
							if (!validateResult) {
								checkParamAndCommit()
							}else {
								dialog.alert(validateResult)
							}
						}

						function ajaxCommit(){
							var width = $(window).width();
							$.ajax({
								//几个参数需要注意一下
								type: "POST",//方法类型
								dataType: "json",//预期服务器返回的数据类型
								async : true,
								url: Com_Parameter.ContextPath + "sys/modeling/base/externalQuery.do?method=save" ,//url
								data: $('[name="modelingExternalQueryForm"]').serialize(),
								success: function (result) {
									if(result){
										listviewOption.token = result.token;
										updateLinkText();
									}
									$('.success-message').css({
										"left": width/2,
										"top": "60px",
										"display": "block"
									});
									var interval = setInterval(function () {
										$('.success-message').css({
											"display": "none"
										});
										clearInterval(interval);
									}, "2000");
								},
								error : function() {
									$('.fail-message').css({
										"left": width/2,
										"top": "60px",
										"display": "block"
									});
									var interval = setInterval(function () {
										$('.fail-message').css({
											"display": "none"
										});
										clearInterval(interval);
									}, "2000");
								}
							});
						}

						window.checkParamAndCommit = function(){
							// 预提交处理
							var url = Com_Parameter.ContextPath + 'sys/ui/resources/user.jsp';
							$.ajax({
								url: url,
								type: 'POST',
								dataType: 'text',
								data: $('[name="modelingExternalQueryForm"]').serialize(),
								async: false,
								error: function (data) {
									//dialog.alert('会话校验失败，'+data);
									dialog.alert(Com_Parameter.ServiceNotAvailTip + data.statusText);
									return false;
								},
								success: function (data) {
									if (data) {
										data = data.replace(/[\r\n]/g, "");
									}
									if (data.indexOf("invalid_value") == 0) { // 校验非法值
										if (data.substr("invalid_value".length).indexOf("<script>") != -1 || data.substr("invalid_value".length).indexOf("<link>") != -1 || data.substr("invalid_value".length).indexOf("<iframe>") != -1
												|| data.substr("invalid_value".length).indexOf("<input>") != -1 || data.substr("invalid_value".length).indexOf("<select>") != -1 || data.substr("invalid_value".length).indexOf("<option>") != -1
												|| data.substr("invalid_value".length).indexOf("<form>") != -1){
											dialog.alert(Data_GetResourceString("sys-config:sysConfig.rtf.illegal"));
										}else{
											// dialog.alert(data.substr("invalid_value".length));
											dialog.alert("${lfn:message('sys-modeling-base:modeling.saved.failed')}");
										}
										return false;
									} else if (data != "" && data != "anonymous") {
										ajaxCommit();
										return true;
									} else {
										dialog.alert("${lfn:message('sys-modeling-base:modeling.saved.failed')}")
										return false;
									}
								}
							})
							return false;
						}
					}
			)

			//datetime控件触发
			function xform_main_data_triggleSelectdatetime(event){
				var input=  $("[name='endTime']");
				if(!input.val()){
					var datToday = new Date();
					//初始化开始日期，为空串则设为明天0点
					var datBDate = new Date(datToday.getFullYear(),datToday.getMonth(),datToday.getDate()+1);
					input.val(datBDate.format(Com_Parameter.DateTime_format));
				}
				selectDateTime(event,input);
			}


		</script>
	</template:replace>
</template:include>