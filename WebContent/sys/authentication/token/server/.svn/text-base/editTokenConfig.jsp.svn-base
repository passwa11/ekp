<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="title">token系统变量设置</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet"
			  href="${KMSS_Parameter_ContextPath}sys/authentication/token/server/resource/css/editTokenConfig.css?cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/token/sys_token_config/sysTokenConfig.do">
		<div>
			<div class="settingContent">
				<table>
					<tr>
						<td class="tokenConfigTitle">最大访问次数限制：</td>
						<td class="tokenConfigContent">
							<div >
								<input  type="text" class="" name="fdMaxVisitCount" value="${sysTokenConfigForm.fdMaxVisitCount}" placeholder="请输入">
								<span class="span-red">*</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="tokenConfigTitle">盐值：</td>
						<td class="tokenConfigContent">
							<div >
								<input  type="text" class="" name="fdSalt" value="${sysTokenConfigForm.fdSalt}"  placeholder="请输入">
								<span class="span-red">*</span>
							</div>
						</td>
					</tr>
					<tr>
						<td class="tokenConfigTitle">加密算法类型：</td>
						<td class="tokenConfigContent">
							<div >
								<select name="fdAlgorithm" style="color: #333">
									<option value="MD5">MD5</option>
									<option value="SHA1">SHA1</option>
									<option value="SHA256">SHA256</option>
								</select>
							</div>
						</td>
					</tr>
					<tr>
						<td class="tokenConfigTitle">token检查定时任务配置:</td>
						<td class="tokenConfigContent">
							<div >
									<textarea id="reminderNotice" name="fdCron" value="${sysTokenConfigForm.fdCron}"
											  placeholder="定时任务cron表达式" ></textarea>

								<span class="span-red">*</span>
							</div>
							<div class="reminderNoticeMark" >
								格式：cron表达式
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="settingFoot">
				<a class="settingFootBlueBtn" href="javascript:void(0)" onclick="ok()">${lfn:message('button.ok') }</a>
<%--				<a class="settingFootWhiteBtn" href="javascript:void(0)" onclick="cancle()">${lfn:message('button.cancel') }</a>--%>
			</div>
		</div>
			<html:hidden property="fdId"/>
	</html:form>
	<script>
		var optionParam = {
			fdAlgorithm  : '${sysTokenConfigForm.fdAlgorithm}',
			fdCron  :  '${sysTokenConfigForm.fdCron}'
		}
		seajs.use(["lui/dialog", "lui/topic"], function (dialog, topic) {

			$('[name="fdAlgorithm"]').val(optionParam.fdAlgorithm);

			$('[name="fdCron"]').val(optionParam.fdCron);

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
				Com_Submit(document.sysTokenConfigForm, 'update')
			}

			function getData(){
				var data = {};
				//最大访问次数限制
				var fdMaxVisitCount = $('[name="fdMaxVisitCount"]').val();
				data.fdMaxVisitCount = fdMaxVisitCount;
				//盐值
				var fdSalt = $('[name="fdSalt"]').val();
				data.fdSalt = fdSalt;
				//加密算法类型
				var fdAlgorithm = $('[name="fdAlgorithm"]').val();
				data.fdAlgorithm = fdAlgorithm;
				//token检查定时任务cron表达式
				var fdCron = $('[name="fdCron"]').val();
				data.fdCron = fdCron;
				return data;
			}

			function verificationData(data){
				if (!data) {
					return "请先配置";
				}
				if (!data.fdMaxVisitCount ) {
					return "最大访问次数限制不能为空";
				}
				var isNumberResult = isNumber(data.fdMaxVisitCount);
				if (isNumberResult) {
					return "最大访问次数"+isNumberResult;
				}
				if (!data.fdSalt) {
					return "盐值不能为空";
				}
				if (!data.fdAlgorithm) {
					return "加密算法类型不能为空";
				}
				if (!data.fdCron) {
					return "token检查定时任务配置不能为空";
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
	</script>
	</template:replace>
</template:include>
