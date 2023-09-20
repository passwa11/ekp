<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<template:include ref="default.simple">
	<template:replace name="title">分享设置</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet"
			  href="${KMSS_Parameter_ContextPath}sys/authentication/token/server/resource/css/setShareCfgDialog.css?cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="body">
	<div>
		<div class="settingContent">
			<table>
				<tr>
					<td class="shareConfigTitle">访问次数：</td>
					<td class="shareConfigContent">
						<div >
							<input  type="text" class="" name="fdVisitMaxCount" placeholder="请输入">
							<span class="span-red">*</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="shareConfigTitle">访问期限：</td>
					<td class="shareConfigContent">
						<div class="inputselectsgl " style="width:200px;float: left;" onclick="xform_main_data_selectDateTime(event,this,'startTime');">
							<div class="input"><input name="fdVisitEndPeriod" type="text" validate="__datetime" value="" ></div>
							<div class="inputdatetime"></div>

						</div>
						<span class="span-red">*</span>
					</td>
				</tr>
				<tr>
					<td class="shareConfigTitle">访问频率类型：</td>
					<td class="shareConfigContent">
						<div >
							<select name="fdVisitFrequencyType"  style="color: #333">
								<option value="0">不设置</option>
								<option value="1">每分钟</option>
								<option value="2">每小时</option>
								<option value="3">每天</option>

							</select>
						</div>
					</td>
				</tr>
				<tr id="fdVisitFrequencyCountTr" style="display: none">
					<td class="shareConfigTitle">访问频率次数：</td>
					<td class="shareConfigContent">
						<div >
							<input  type="text" name="fdVisitFrequencyCount" class="" validators="number" placeholder="请输入">
							<span class="span-red">*</span>
						</div>
					</td>
				</tr>
				<tr>
					<td class="shareConfigTitle">访问方法配置:</td>
					<td class="shareConfigContent">
						<div >
								<textarea id="reminderNotice" name="fdVisitMethod"
										  style="width: 400px;height: 80px;color:#333;padding: 8px"
										  placeholder="请设置方法的请求次数限制" ></textarea>

							<span class="span-red">*</span>
						</div>
						<div >
							格式：方法名:次数,多个配置使用分号(;)隔开 如：【view:0;add:1;edit:2】  次数为0表示不限制
						</div>
					</td>
				</tr>
				<tr>
					<td class="shareConfigTitle">跳转地址:</td>
					<td class="shareConfigContent">
						<div >
							<input type="text" name="fdTargetUrl" class="" placeholder="请输入">
							<span class="span-red">*</span>
						</div>
						<div >
							不带协议头默认跳转系统内链接
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="settingFoot">
			<a class="settingFootBlueBtn" href="javascript:void(0)" onclick="ok()">${lfn:message('button.ok') }</a>
			<a class="settingFootWhiteBtn" href="javascript:void(0)" onclick="cancle()">${lfn:message('button.cancel') }</a>
		</div>
	</div>
	<script>
		Com_IncludeFile('calendar.js');

		var optionParam = {
			fdModelName  : '',
			fdModelId  : ''
		}

		seajs.use(["lui/dialog", "lui/topic"], function (dialog, topic) {
			var _param;
			var intervalEndCount = 10;
			var interval = setInterval(__interval, "50");

			function __interval() {
				if (intervalEndCount == 0) {
					console.error("数据解析超时。。。");
					clearInterval(interval);
				}
				intervalEndCount--;
				if (!window['$dialog']) {
					return;
				}
				_param = $dialog.___params;
				init(_param);
				clearInterval(interval);
			}


			function init(param) {
				console.log(param)
				var method = param.method;
				var fdVisitMethodVal = method + ":0";
				$('[name="fdVisitMethod"]').val(fdVisitMethodVal);
				var targetUrl = param.targetUrl;
				$('[name="fdTargetUrl"]').val(targetUrl);
				optionParam.fdModelName = param.fdModelName;
				optionParam.fdModelId = param.fdModelId;
			}

			$('[name="fdVisitFrequencyType"]').on("change",function (evt) {
					if('0'==$(this).val()){
						$('#fdVisitFrequencyCountTr').css("display","none");
					}else{
						$('#fdVisitFrequencyCountTr').css("display","table-row");
					}
			});

			window.cancle = function(){
				$dialog.hide();
			}

			window.ok = function(){
				var self = this;
				//获取请求参数
				var data = getData();
				//验证参数
				var verificationResult = verificationData(data);
				if(verificationResult){
					dialog.alert(verificationResult);
					return;
				}
				//获取token
				$.ajax({
					url : Com_Parameter.ContextPath + "sys/token/sys_token_info/sysTokenInfo.do?method=ajaxGetToken",
					type : "post",
					async : false,    //用同步方式
					data : data,
					success : function(data) {
						if(!data){
							self.dialog.alert("获取token失败!")
						}
						$dialog.hide(data);
					},
					error : function () {
						self.dialog.alert("获取token失败!")
					}
				});
			}

			function getData(){
				var data = {};
				data.fdModelName = optionParam.fdModelName;
				data.fdModelId = optionParam.fdModelId;
				//访问次数
				var fdVisitMaxCount = $('[name="fdVisitMaxCount"]').val();
				data.fdVisitMaxCount = fdVisitMaxCount;
				//访问期限
				var fdVisitEndPeriod = $('[name="fdVisitEndPeriod"]').val();
				data.fdVisitEndPeriod = fdVisitEndPeriod;
				//访问频率类型
				var fdVisitFrequencyType = $('[name="fdVisitFrequencyType"]').val();
				data.fdVisitFrequencyType = fdVisitFrequencyType;
				//访问频率次数
				var fdVisitFrequencyCount = $('[name="fdVisitFrequencyCount"]').val();
				data.fdVisitFrequencyCount = fdVisitFrequencyCount;
				//访问方法
				var fdVisitMethod = $('[name="fdVisitMethod"]').val();
				data.fdVisitMethod = fdVisitMethod;
				//目标地址
				var fdTargetUrl = $('[name="fdTargetUrl"]').val();
				data.fdTargetUrl = fdTargetUrl;
				return data;
			}

			function verificationData(data){
				if (!data) {
					return "请先配置";
				}
				if (!data.fdModelName ) {
					return "fdModelName不能为空";
				}
				if (!data.fdModelId) {
					return "fdModelId不能为空";
				}
				if (!data.fdVisitMaxCount && !data.fdVisitEndPeriod) {
					return "访问次数和访问时间至少设置其中一项";
				}
				if(data.fdVisitMaxCount){
					var isNumberResult = isNumber(data.fdVisitMaxCount);
					if (isNumberResult) {
						return "访问次数"+isNumberResult;
					}
				}

				//访问频率类型
				if ('0' == data.fdVisitFrequencyType) {
					//访问频率次数
					data.fdVisitFrequencyCount = 0;
				}else{
					if (!data.fdVisitFrequencyCount) {
						return "访问频率次数不能为空";
					}
					isNumberResult = isNumber(data.fdVisitFrequencyCount);
					if (isNumberResult) {
						return "访问频率次数"+isNumberResult;
					}
				}

				//访问方法
				if (!data.fdVisitMethod) {
					return "访问方法配置不能为空";
				}
				var methods = data.fdVisitMethod.split(';');
				for(var i = 0;i<methods.length;i++){
					var methodParams = methods[i].split(':')
					if(2 == methodParams.length){
						isNumberResult = isNumber(methodParams[1]);
						if (isNumberResult) {
							return "访问方法配置格式有误";
						}
					}else{
						return "访问方法配置格式有误";
					}
				}
				//跳转地址
				if (!data.fdTargetUrl) {
					return "目标地址配置不能为空";
				}
				return "";
			}

			function isNumber(val) {
				if (!isNaN(val)
						&& !/^\s+$/.test(val)
						&& /^.{1,20}$/.test(val)
						&& /(\.)?\d$/.test(val)) {
					if(val.indexOf('-') >-1){
						return "不是一个正整数";
					}
				}else{
					return "不是一个整数";
				}
				return "";
			}


		});

		//datetime控件触发
		function xform_main_data_selectDateTime(event,dom,name){
			var input =  $("[name='fdVisitEndPeriod']");
			selectDateTime(event,input);
		}
	</script>
	</template:replace>
</template:include>
