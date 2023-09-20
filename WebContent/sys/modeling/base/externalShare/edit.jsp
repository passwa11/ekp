<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet"
			  href="${KMSS_Parameter_ContextPath}sys/modeling/base/externalShare/css/externalShare.css?s_cache=${LUI_Cache}"/>
		<script>
			Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|dialog.js", null, "js");
		</script>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/modeling/base/externalShare.do" style="overflow:hidden">
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
							<strong>${lfn:message('sys-modeling-base:modeling.Free.registration.form')}</strong>
						</div>
						<ui:switch property="isEnable"
								   checked = "${isEnable}"
								   checkVal="1"
								   unCheckVal="0"
								   onValueChange="isEnableChange()"></ui:switch>
					</div>

					<div class="external-share-off">
						<ul>
							<li>${lfn:message('sys-modeling-base:modeling.open.free.registration')}</li>
							<li>${lfn:message('sys-modeling-base:modeling.questionnaire.surveys')}</li>
						</ul>
					</div>
					<div class="external-share-on" style="display: none">
						<table>
							<tr>
								<td>
									<div class="link-title">
										<ul>
											<li>${lfn:message('sys-modeling-base:modeling.getlink.free.registration')}</li>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div class="link">
										<div class="link-text">
										</div>
										<i class="copy-icon"></i>
										<i class="jump-icon"></i>
										<i class="qrcode-icon"></i>
									</div>
									<div id="qrcodeDiv" >
										<div class="detailQrCode">
										</div>
									</div>
									<div class="externalShareIconPop" name="externalShareIconPop" style="display: none"></div>
								</td>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title"><span class="span-red">*</span>${lfn:message('sys-modeling-base:modeling.Deadline.form')}</td>
										<td class="external-share-on-td-content">
											<div class="inputselectsgl " style="width:200px;float: left;" onclick="xform_main_data_triggleSelectdatetime(event);">
												<div class="input"><input name="endTime" type="text" validate="__datetime" value="" ></div>
												<div class="inputdatetime"></div>
											</div>
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title"><span class="span-red">*</span>${lfn:message('sys-modeling-base:modeling.overtime.submit')}</td>
										<td class="external-share-on-td-content">
											<xform:text property="endNotification" validators="required maxLength(200)" className ="external-share-on-input"
														value="${lfn:message('sys-modeling-base:modeling.submit.endtime.pass')}"
														subject="${lfn:message('sys-modeling-base:modeling.overtime.submit') }"
														htmlElementProperties='placeholder="${lfn:message("sys-modeling-base:modeling.please.enter")}"'/>
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title"><span class="span-red">*</span>${lfn:message('sys-modeling-base:modeling.submit.number')}</td>
										<td class="external-share-on-td-content">
											<input name="commitTotalCount" type="text" class="external-share-on-input" value="10000" placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}">
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title">${lfn:message('sys-modeling-base:modelingExternalShare.isRunBehavior')}</td>
										<td class="external-share-on-td-content">
											<ui:switch property="isRunBehavior"
													   checked = "${isRunBehavior}"
													   checkVal="1"
													   unCheckVal="0"></ui:switch>
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title"><span class="span-red">*</span>${lfn:message('sys-modeling-base:modeling.single.submit.limit')}</td>
										<td class="external-share-on-td-content external-share-on-td-center">
											<div style="width: 550px">
												<div class="external-share-on-radio">
													<input type="radio" value="0" name="isCommitLimitEnable" onchange="isCommitLimitEnableChange()" checked><span class="commit-limit-span" >${lfn:message('sys-modeling-base:modeling.single.submit.maxnum.unlimit')}</span>
													<input type="radio" value="1" name="isCommitLimitEnable" onchange="isCommitLimitEnableChange()">${lfn:message('sys-modeling-base:modeling.single.submit.maxnum.limit')}
												</div>
												<div class="everyCommitCountTr">
													<input name="everyOneCommitCount" type="text" class="external-share-on-input-count" value="" placeholder="${lfn:message('sys-modeling-base:modeling.single.submit.maxnum')}">
												</div>
											</div>
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table >
									<tr>
										<td class="external-share-on-td-desc" colspan="2">
											<i class="file-limit-desc-icon"></i>
											<span class="file-limit-desc">${lfn:message('sys-modeling-base:modeling.file.fileLimitDesc')}</span>
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title"><span class="span-red">*</span>${lfn:message('sys-modeling-base:modeling.file.fileLimitCount')}</td>
										<td class="external-share-on-td-content">
											<xform:text property="fileLimitCount" value="15" showStatus="edit" validators="required digits max(15) min(0)" className="external-share-on-input" htmlElementProperties='placeholder="${lfn:message(\'sys-modeling-base:modeling.file.fileLimitCount.placeholder\')}"'></xform:text>
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title"><span class="span-red">*</span>${lfn:message('sys-modeling-base:modeling.file.singleFileSize')}</td>
										<td class="external-share-on-td-content">
											<xform:text property="singleFileSize" value="2" showStatus="edit" validators="required number max(2) min(0)" className="external-share-on-input" htmlElementProperties='placeholder="${lfn:message(\'sys-modeling-base:modeling.file.singleFileSize.placeholder\')}"'></xform:text>
											MB
										</td>
									</tr>
								</table>
							</tr>
							<tr>
								<table>
									<tr>
										<td class="external-share-on-td-title"><span class="span-red">*</span>${lfn:message('sys-modeling-base:modeling.file.fileMaxSize')}</td>
										<td class="external-share-on-td-content">
											<xform:text property="fileMaxSize" value="10" showStatus="edit" validators="required number max(10) min(0)" className="external-share-on-input" htmlElementProperties='placeholder="${lfn:message(\'sys-modeling-base:modeling.file.fileMaxSize.placeholder\')}"'></xform:text>
											MB
										</td>
									</tr>
								</table>
							</tr>
						</table>
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
			var modeling_validation = $KMSSValidation();
			var options = {
				token:'${modelingExternalShareForm.fdToken}'
			}
			seajs.use(["sys/modeling/base/externalShare/js/externalShare","lui/jquery", "sys/ui/js/dialog","lui/topic"],
					function (externalShare,$, dialog,topic) {
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
							var fdConfig =JSON.stringify(${modelingExternalShareForm.fdConfig})
							if(!fdConfig || fdConfig == ''){
								fdConfig = '{}';
							}
							var cfg = JSON.parse(fdConfig);
							window.externalShare = new externalShare.ExternalShare(cfg);
							window.externalShare.startup();
						}
						checkInit();

						//点击二维码框之外时事件，关闭二维码框
						$(document).on('click',function (e) {
							//判断事件点击的元素为画布canvas、div.detailQrCode时，不隐藏二维码框
							if(!($(e)[0].target.localName == "canvas" || $(e)[0].target.className == "detailQrCode" )) {
								$("#qrcodeDiv").css("display", "none");
							}
						});

						window.submitForm = function(method){
							if (!modeling_validation.validate()) {
								return
							}

							var keyData = window.externalShare.getKeyData();
							$("[name='fdConfig']").val(JSON.stringify(keyData).replace(/&quot;/g,"\\\""));

							//校验参数
							var validateResult = window.ExternalShareValidate.validate(keyData)
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
								url: Com_Parameter.ContextPath + "sys/modeling/base/externalShare.do?method=save" ,//url
								data: $('[name="modelingExternalShareForm"]').serialize(),
								success: function (result) {
									if(result){
										options.token = result.token;
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
								data: $('[name="modelingExternalShareForm"]').serialize(),
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

						window.isEnableChange = function () {
							if("0"==$("[name='isEnable']").val()){
								$(".external-share-on").css("display","none");
								$(".external-share-off").css("display","block");
							}else{
								$(".external-share-on").css("display","block");
								$(".external-share-off").css("display","none");
							}
						}

						window.isCommitLimitEnableChange = function () {
							if("0"==$("[name='isCommitLimitEnable']:checked").val()){
								$(".everyCommitCountTr").css("display","none");
							}else{
								$(".everyCommitCountTr").css("display","block");
							}
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