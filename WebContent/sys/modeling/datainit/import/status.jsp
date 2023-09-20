<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/process.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css" rel="stylesheet">
<script>
Com_IncludeFile("msg.js", "style/"+Com_Parameter.Style+"/msg/");
Com_IncludeFile("data.js");
function refreshInfo(){
	var statusInfo = ['<bean:message bundle="sys-datainit" key="sysDatainitMain.status.stoped"/>',
					'<bean:message bundle="sys-datainit" key="sysDatainitMain.status.error"/>',
					'<bean:message bundle="sys-datainit" key="sysDatainitMain.status.finish"/>',
					'<bean:message bundle="sys-datainit" key="sysDatainitMain.status.ready"/>',
					'<bean:message bundle="sys-datainit" key="sysDatainitMain.status.starting"/>',
					'<bean:message bundle="sys-datainit" key="sysDatainitMain.status.running"/>',
					'<bean:message bundle="sys-datainit" key="sysDatainitMain.status.stopping"/>'];

	var data = new KMSSData();
	data.UseCache = false;
	data.AddBeanData("modelingDatainitXMLDataBean");
	var rtnInfo = data.GetHashMapArray()[0];
	var status = parseInt(rtnInfo.status);
	rtnInfo.processCount = parseInt(rtnInfo.processCount);
	rtnInfo.successCount = parseInt(rtnInfo.successCount);
	rtnInfo.ignoreCount = parseInt(rtnInfo.ignoreCount);
	rtnInfo.failureCount = parseInt(rtnInfo.failureCount);
	var handleCount = 0;
	if(rtnInfo.processCount>0){
		handleCount = rtnInfo.successCount+rtnInfo.ignoreCount+rtnInfo.failureCount;
		rtnInfo.process = Math.round(handleCount * 100 / rtnInfo.processCount);
		//进度条最后完成时的ajax中status会返回-1，此时不再显示99而是100
		if (status != -1) {
			rtnInfo.process = (rtnInfo.process == 100 ? 99 : rtnInfo.process)  + "%";
		}
	}else{
		rtnInfo.process = "0";
	}
	rtnInfo.status = statusInfo[status+3];
	for(var o in rtnInfo){
		var obj = document.getElementById("div_"+o);
		if(obj!=null)
			obj.innerHTML = rtnInfo[o];
	}
	//更新进度条
	if (status > 0) {
		$(".model-mask-panel-rate-cur")[0].style.width = rtnInfo.process;
		$(".model-mask-panel-rate-title")[0].innerHTML = rtnInfo.fileName;
		setTimeout("refreshInfo()", 2000);
	}
	console.log("fdasfsdafasfasdfasdfasdffdsafdsaf:",status)
	if (status === -2 || status === -3 || status === 3) {
		//STATUS_ERROR or STATUS_STOPED or STATUS_STOPING
		setDisplay({
			"div_processing": "none",
			"div_success": "none",
			"div_failure": "block",
			"btn_process": "none",
			"btn_success": "none",
			"btn_error": "inline"
		});
		//导入中止类型相关的图标及按钮隐藏
		if (status === -3 || status === 3) {
			$('#div_errorIcon').css("visibility", "hidden");
			setDisplay({
				"btn_stoped": "inline",
				"btn_error": "none"
			});
		} else {
			$('#div_errorIcon').css("visibility", "");
			setDisplay({
				"btn_stoped": "none",
				"btn_error": "inline"
			});
		}

		//错误标题
		var errorDescDom = $("#errorDesc")[0];
		if (status === -3) {
			errorDescDom.innerHTML = "${lfn:message('sys-modeling-base:modeling.abortedImport.app')}";
		} else if(status === 3) {
			errorDescDom.innerHTML = "${lfn:message('sys-modeling-base:modeling.being.abortedImport.app')}";
		}  else if (rtnInfo.errorTitle) {
			errorDescDom.innerHTML = rtnInfo.errorTitle;
		} else {
			errorDescDom.innerHTML = "${lfn:message('sys-modeling-base:modeling.failedImport.app')}";
		}
	} else if (status === -5) {
		setDisplay({
			"div_processing": "none",
			"div_success": "none",
			"div_failure": "block",
			"btn_process": "none",
			"btn_success": "none",
			"btn_error": "none",
			"div_fileName": "none",
			"btn_stoped": "block",
			"errorTip": "block"
		});
		$(".model-mask-panel-prompt-wrap").css("padding-top","15px");
		$("#errorTip").css("margin-bottom","10px");
		var errorDescDom = $("#errorDesc")[0];
		errorDescDom.innerHTML = "${lfn:message('sys-modeling-base:modelingLicense.number.less.tips')}";
	} else if (status === -1) {
		//STATUS_FINISH
		setDisplay({
			"div_processing": "none",
			"div_success": "block",
			"div_failure": "none",
			"btn_process": "none",
			"btn_success": "inline",
			"btn_error": "none"
		});
		if(rtnInfo.appMap){
			var json = JSON.parse(rtnInfo.appMap);
			for(fdId in json) {
				var fdAppName = json[fdId].fdAppName;
				var fdAppVersionText = json[fdId].fdVersionText;
				var url = "${LUI_ContextPath}/sys/modeling/base/modelingApplication.do?method=appIndex&fdId=" + fdId;
				var li = $("<li class=\"model-mask-panel-prompt-desc\"><p>" + fdAppName + fdAppVersionText + "</p><a href=\"" + url + "\" target=\"_blank\">${lfn:message('sys-modeling-base:modeling.page.enter')}</a></li>");
				$(".model-mask-panel-prompt-wrap ul:first").append(li);
			}
		}
	} else {
		setDisplay({
			"div_processing": "block",
			"div_success": "none",
			"div_failure": "none",
			"btn_process": "inline",
			"btn_success": "none",
			"btn_error": "none"
		});
	}
}

function setDisplay(json) {
	if (json) {
		for (var id in json) {
			document.getElementById(id).style.display = json[id];
		}
	}
}

function stopAppImport() {
	seajs.use("lui/dialog", function (dialog) {
		dialog.confirm("${lfn:message('sys-modeling-base:modeling.abortedImport.tips')}", function (value) {
			if (value) {
				location.href = Com_Parameter.ContextPath + 'sys/modeling/base/modelingDatainitMain.do?method=stopAppImport&type=${JsParam.type}';
			}
		});
	});
}

function download(method) {
	var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method=" + method;
	if ($('#exportDownloadIframe').length > 0) {
		$('#exportDownloadIframe')[0].src = url;
	} else {
		var elemIF = document.createElement("iframe");
		elemIF.id = "exportDownloadIframe";
		elemIF.src = url;
		elemIF.style.display = "none";
		document.body.appendChild(elemIF);
	}
}

function backToUpload() {
	$(".model-mask-panel").hide();
	$(".toolbar-bottom").hide();
	location.href = Com_Parameter.ContextPath + 'sys/modeling/datainit/import/upload.jsp?type=${JsParam.type}';
	window.fitSize();
}

var interval = setInterval(beginInit, "50");

function beginInit() {
	if (!window['$dialog'])
		return;
	clearInterval(interval);
	window.fitSize('status');
}

window.fitSize = function (type) {
	if(type==='status')
		$($dialog.frame).find(".lui_dialog_content").css("height", 398-50);
	else
		$($dialog.frame).find(".lui_dialog_content").css("height", 232-50);
}

seajs.use("lui/jquery", function ($) {
	$(function () {
		refreshInfo();
	});
	window.goToHelp = function () {
		Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/help/docs/index.jsp#/pages/page-1/precautions", "_blank");
	}
});

</script>
</head>
<body>
<div class="model-mask-panel">
	<%--导入过程--%>
	<div id="div_processing" style="display: none">
		<div class="model-mask-panel-rate">
			<p class="model-mask-panel-rate-title"></p>
			<div class="model-mask-panel-rate-img">
				<div class="model-mask-panel-rate-img-wrap">
					<div class="model-mask-panel-rate-bg"></div>
					<div class="model-mask-panel-rate-cur" style="width: 95%;"></div>
				</div>
				<p class="model-mask-panel-rate-num"><span id="div_process"></span></p>
			</div>
			<ul>
				<li class="model-mask-panel-rate-desc">
					<p>${lfn:message("sys-modeling-base:modeling.successes.number")}：<span id="div_successCount"></span>个</p>
				</li>
				<li class="model-mask-panel-rate-desc">
					<p>${lfn:message("sys-modeling-base:modeling.ignore.number")}：<span id="div_ignoreCount"></span>个</p>
				</li>
				<li class="model-mask-panel-rate-desc">
					<p>${lfn:message("sys-modeling-base:modeling.error.number")}：<span id="div_failureCount"></span>个</p>
				</li>
			</ul>
		</div>
	</div>
	<%--导入成功--%>
	<div id="div_success" style="display: none">
		<div class="model-mask-panel-prompt suc">
			<div class="model-mask-panel-prompt-wrap">
				<div class="model-mask-panel-prompt-result">
					<div></div>
					<p>${lfn:message("sys-modeling-base:modeling.success.import.app")}</p>
				</div>
				<ul>
				</ul>
			</div>
		</div>
	</div>
	<%--导入失败--%>
	<div id="div_failure" style="display: none">
		<div class="model-mask-panel-prompt fail">
			<div class="model-mask-panel-prompt-wrap">
				<div class="model-mask-panel-prompt-result">
					<div id="div_errorIcon"></div>
					<p id="errorTip" style="display: none">${lfn:message("sys-modeling-base:modeling.failedImport.app")}</p>
					<p id="errorDesc"></p>
				</div>
				<ul>
					<li class="model-mask-panel-prompt-desc">
						<p id="div_fileName"></p><div id="model-mask-panel-error-switch" style="display: inline-block"></div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="toolbar-bottom">
	<div id="btn_process" style="display: none">
		<ui:button onclick="stopAppImport()" styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-base:modeling.Cancel')}"/>
		<ui:button onclick="$dialog.hide()" text="${lfn:message('sys-modeling-base:modeling.background.process')}"/>
	</div>
	<div id="btn_success" style="display: none">
		<div class="precaution" onclick="goToHelp()">
			<span class="rocket"></span>
			${lfn:message('sys-modeling-base:modeling.import.application.FAQ')}
		</div>
		<ui:button target="_self" onclick="backToUpload()" styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-base:modeling.continue.import')}"/>
		<ui:button onclick="$dialog.hide()" text="${lfn:message('sys-modeling-base:modeling.button.ok')}"/>
	</div>
	<div id="btn_stoped" style="display: none">
		<ui:button onclick="$dialog.hide()" text="${lfn:message('sys-modeling-base:modeling.button.ok')}"/>
	</div>
	<div id="btn_error" style="display: none">
		<ui:button target="_self" onclick="backToUpload()" styleClass="lui_toolbar_btn_gray" text="${lfn:message('sys-modeling-base:modeling.re-upload')}"/>
		<ui:button onclick="download('downloadErrorFile')" target="_self" text="${lfn:message('sys-modeling-base:modeling.download.details')}"/>
	</div>
</div>
</body>
</html>