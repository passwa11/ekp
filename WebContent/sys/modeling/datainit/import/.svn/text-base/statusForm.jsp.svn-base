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
var status = '${status}';
var formMap = '${formMap}';
function refreshInfo(){
	if (status === "-2") {
		//STATUS_ERROR
		setDisplay({
			"div_success": "none",
			"div_failure": "block",
            "btn_back_success": "none",
            "btn_back_failure": "inline",
            "model-errorFile-info": "block",
		});
	}else if (status === "-6"){
		//STATUS_NOT_IMPORTZIP
		setDisplay({
			"div_success": "none",
			"div_failure": "block",
			"btn_back_success": "none",
			"btn_back_failure": "inline",
			"model-not-import-ZIP": "block",
		});
	}else if (status === "-1") {
		//STATUS_FINISH
		setDisplay({
			"div_success": "block",
			"div_failure": "none",
            "btn_back_success": "inline",
            "btn_back_failure": "none"
		});

		if(formMap){
			var json = JSON.parse(formMap);
			for(fdId in json) {
				var fdName = json[fdId].fdName;
				var url = "${LUI_ContextPath}/sys/modeling/base/modelingAppModel.do?method=frame&fdId=" + fdId;
				var li = $("<li class=\"model-mask-panel-prompt-desc\"><p>" + fdName + "</p><a href=\"" + url + "\" target=\"_blank\">进入</a></li>");
				$(".model-mask-panel-prompt-wrap ul:first").append(li);
			}
		}
	}
}

function showMoreErrInfo(divId) {
	var obj = document.getElementById(divId);

	if (obj.style.display === "none") {
		obj.style.display = "block";
		if (status === "-6"){
			$(".model-mask-panel-error-info").html("${lfn:message('sys-modeling-datainit:modelingDatainitMain.upload.invalid.form')}") ;
		}
	} else {
		obj.style.display = "none";
	}
}

function setDisplay(json) {
	if (json) {
		for (var id in json) {
			document.getElementById(id).style.display = json[id];
		}
	}
}

function backToUpload() {
	$(".model-mask-panel").hide();
	$(".toolbar-bottom").hide();
	location.href = Com_Parameter.ContextPath + 'sys/modeling/datainit/import/upload.jsp?type=${JsParam.type}&fdAppId=${JsParam.fdAppId}';
	window.fitSize();
}

var interval = setInterval(beginInit, "50");

function beginInit() {
	if (!window['$dialog'])
		return;
	clearInterval(interval);
	window.fitSize('status');
	$(window).resize(function(){
		window.fitSize('status');
	});
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
});
</script>
</head>
<body>
<div class="model-mask-panel">
	<%--导入成功--%>
	<div id="div_success" style="display: none">
		<div class="model-mask-panel-prompt suc">
			<div class="model-mask-panel-prompt-wrap">
				<div class="model-mask-panel-prompt-result">
					<div></div>
					<p>成功导入表单</p>
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
					<div></div>
					<p>导入表单失败</p>
				</div>
				<div id="model-errorFile-info" style="display:none;">
				<ul>
					<li class="model-mask-panel-prompt-desc">
						<p id="div_fileName"></p><a onclick="showMoreErrInfo('div_errorFileAll');">查看错误信息</a>
					</li>
				</ul>
				<div class="model-mask-panel-error"><ul id="div_errorFileAll" style="display:none;" ><li class="model-mask-panel-error-info">${errorFileFull}</li></ul></div>
				</div>
				<div id="model-not-import-ZIP" style="display:none;">${lfn:message('sys-modeling-datainit:modelingDatainitMain.upload.invalid.form')}</div>
			</div>
		</div>
	</div>
</div>
<div class="toolbar-bottom">
	<div id="btn_back_success" style="display: none">
		<ui:button   target="_self" onclick="backToUpload()" styleClass="lui_toolbar_btn_gray " text="继续上传"/>
		<ui:button onclick="$dialog.hide()" text="确定"/>
	</div>
    <div id="btn_back_failure"  style="display: none">
        <ui:button target="_self" onclick="backToUpload()" styleClass="lui_toolbar_btn_gray " text="重新上传"/>
        <ui:button onclick="$dialog.hide()" text="确定"/>
    </div>
</div>

</body>
</html>