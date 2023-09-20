<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/process.css"/>
	<style>
		.model-mask-panel-rate-title{
			font-size: 14px;
			color: #333333;
			line-height: 14px;
		}
	</style>
</head>
<body>
<div id="temp" class="model-mask-panel-rate">
	${lfn:message('sys-modeling-base:modelingAppVersion.PleaseWaitFor')}
</div>
<div class="model-mask-panel" style="display: none">
	<div>
		<div class="model-mask-panel-rate" style="overflow: hidden; height: 75px">
			<p class="model-mask-panel-rate-title">${lfn:message('sys-modeling-base:modelingAppVersion.PleaseWaitFor')}</p>
			<p class="model-mask-panel-rate-title-checking" style="display: none">${lfn:message('sys-modeling-base:modelingAppVersion.copyingChecking')}</p>
			<div class="model-mask-panel-rate-img">
				<div class="model-mask-panel-rate-img-wrap">
					<div class="model-mask-panel-rate-bg"></div>
					<div class="model-mask-panel-rate-cur" style="width: 0%;"></div>
				</div>
				<p class="model-mask-panel-rate-num">0%</p>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
	Com_IncludeFile("dialog.js");
</script>
<script type="text/javascript">

	$(function(){
		init();
	});

	function init(){
		var fdAppId = '${param.fdAppId}';
		getAddNewVersionStatusReq(fdAppId);
		$('.model-mask-panel').show();
		$('#temp').hide();
	}

	function getAddNewVersionStatusReq(fdAppId) {
		var url = '${LUI_ContextPath}/sys/modeling/base/modelingAppVersion.do?method=getAddNewVersionStatus';
		$.ajax({
			url: url,
			type: 'POST',
			data: $.param({"List_Selected": fdAppId}, true),
			dataType: 'json',
			async: false,
			error: function (data) {
				dialog.result(data);
			},
			success: function (data) {
				refreshNewVersionStatus(fdAppId, data);
				if (data.status && data.result.percent == 100) {
					//#142941 进度到100%，再获取进度的值时，最后获取到进度为0，不再重新请求进度
				}else{
					setTimeout("getAddNewVersionStatusReq('" + fdAppId + "')", 1000);
				}
			}
		});
	}

	function refreshNewVersionStatus(fdAppId, data) {
		if (data.status) {
			var percent = data.result.percent;
			var appName = data.result.appName;
			if(appName){
				$('.model-mask-panel-rate-title')[0].innerHTML = appName + '${lfn:message("sys-modeling-base:modelingAppVersion.CopyingInProgress")}';
			}
			if(percent == 0){
				$('.model-mask-panel-rate-img').css("display", "none");
				$('.model-mask-panel-rate-title-checking').css("display", "");
				var checkTitle = $('.model-mask-panel-rate-title-checking')[0].innerHTML;
				var newCheckTitle = '${lfn:message('sys-modeling-base:modelingAppVersion.copyingChecking')}';
				if(-1 < checkTitle.indexOf("...")){
					$('.model-mask-panel-rate-title-checking')[0].innerHTML = newCheckTitle.substring(0,newCheckTitle.length-3);
				}else if(-1 < checkTitle.indexOf("..")){
					$('.model-mask-panel-rate-title-checking')[0].innerHTML = newCheckTitle.substring(0,newCheckTitle.length-2);
				}else if(-1 < checkTitle.indexOf(".")){
					$('.model-mask-panel-rate-title-checking')[0].innerHTML = newCheckTitle.substring(0,newCheckTitle.length-1);
				}else {
					$('.model-mask-panel-rate-title-checking')[0].innerHTML = newCheckTitle;
				}
			}else{
				percent = percent == 100 ? 99 : percent;
				$('.model-mask-panel-rate-img').css("display", "");
				$('.model-mask-panel-rate-title-checking').css("display", "none");
				$('.model-mask-panel-rate-num')[0].innerHTML = percent + "%";
				$('.model-mask-panel-rate-cur').css("width", percent + "%");
			}
			//页面的关闭由外层调用方负责
		} else {
			//Error
			dialog.result(data);
		}
	}

</script>
</body>
</html>
