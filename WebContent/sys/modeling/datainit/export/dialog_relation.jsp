<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/relation.css" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css" rel="stylesheet">
</head>
<body>
<div class="model-mask-panel" id="content"></div>
<div class="toolbar-bottom">
	<div style="display: inline-block; float: left; margin-left: 20px; text-align: center;">
		<div style="line-height: 25px; padding-top: 20px"><label><input type="checkbox" checked="checked" name="isIncludeCharts"/>${lfn:message("sys-modeling-base:modeling.export.data.including.reports")}</label></div>
	</div>
	<ui:button text="${ lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="$dialog.hide();" order="2"/>
	<ui:button onclick="exportAll();" id="exportBtn" text="${lfn:message('sys-modeling-base:modeling.button.ok')}"/>
</div>
<script type="text/javascript">

	var interval = setInterval(beginInit, "50");
	
	function beginInit(){
		if(!window['$dialog'])
			return;
		clearInterval(interval);
		window.init();
	}

	function cancle(){
		$dialog.hide();
	}

	function buildDepends(depends){
		if(!depends)
			return;
		var html = "";
		for(var i=0; i<depends.length; i++){
			var content = "";
			var dependByArr = depends[i].dependBy;
			for (var j = 0; j < dependByArr.length; j++) {
				content += dependByArr[j].fdName + "${lfn:message("sys-modeling-base:modeling.export.data.including.reports")}<br>";
			}
			html += getLIContent(depends[i].fdId, depends[i].fdName, content, false);
		}
		return html;
	}
	
	function init(){
		var datas = $dialog.___params.relatedDatas;
		if(!datas){
			return;
		}

		//页面内容
		var html = "<div><div class=\"model-mask-panel-output\">";
		if(datas.allDependApp.length > 0)
			html += "<div class=\"model-mask-panel-output-title\">${lfn:message("sys-modeling-base:modeling.application.exported.depends.other")}<span>" + datas.allDependApp.length + "</span>${lfn:message("sys-modeling-base:modeling.select.application.to.export")}</div>";
		html += "<div class=\"model-mask-panel-output-content\"><ul>";
		html += getLIContent(datas.fdId, datas.fdName, null, true);
		html += buildDepends(datas.allDependApp);
		html += "</ul></div></div></div>";
		$("#content").html(html);

		//按钮显示带出应用数
		exportBtnText();
		$("input[name='List_Selected']").change(function(){
			exportBtnText();
		});
	}

	function exportBtnText() {
		var appNum = $("input[name='List_Selected']:checked").length;
		$('#exportBtn').find('.lui_widget_btn_txt')[0].innerHTML = "${lfn:message("sys-modeling-base:modeling.button.ok")}（" + appNum + "）";
	}

	function getLIContent(fdId, fdName, content, isDisable){
		var disable = "";
		if(isDisable){
			disable = "disable";
		}
		var contentHtml = "";
		if(content){
			contentHtml = "<p>" + content + "</p>";
		}
		var html = "";
		html += "<li class=\"model-mask-panel-output-select " + disable + "\">";
		html += "<div class=\"model-mask-panel-output-select-left\">";
		html += "<input type=\"checkbox\" checked=checked name=\"List_Selected\" id=\"" + fdId + "\" value=\"" + fdId + "\">";
		html += buildLabelHtml(isDisable, fdId, "", "<i class=\"model-mask-panel-output-i\"></i>");
		html += "</div>";
		html += "<div class=\"model-mask-panel-output-select-right\">";
		html += buildLabelHtml(isDisable, fdId, "textEllipsis", "<div><span>" + fdName + "</span></div>");
		html += contentHtml;
		html += "</div>";
		html += "</li>";
		return html;
	}

	function buildLabelHtml(isDisable, fdId, clazz, contentHtml){
		if(isDisable)
			return "<label>" + contentHtml + "</label>";
		return "<label for=\"" + fdId + "\" class=\"" + clazz + "\">" + contentHtml + "</label>";
	}

	function exportAll(){
		var values = [];
		$("input[name='List_Selected']:checked").each(function() {
			values.push($(this).val());
		});

		seajs.use('lui/dialog', function(dialog){
			window.__exportLoading = dialog.loading(null, null, null, 1001);
		});

		var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method=export";
		//导出数据包含报表
		if($("input[name='isIncludeCharts']:checked").length > 0){
			url += "&isIncludeCharts=true";
		}
		//导出应用ids
		url += "&" + $.param({"fdAppId": values}, true);
		if ($('#exportDownloadIframe').length > 0) {
			$('#exportDownloadIframe')[0].src = url;
		} else {
			var elemIF = document.createElement("iframe");
			elemIF.id = "exportDownloadIframe";
			elemIF.src = url;
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
		}
		setDownloadTimer();
	}

	var downloadTimer;
	function setDownloadTimer() {
		downloadTimer = setInterval(function () {
			var iframe = document.getElementById('exportDownloadIframe');
			var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
			if (iframeDoc.readyState == 'complete' || iframeDoc.readyState == 'interactive') {
				//隐藏loading
				setTimeout("downloadComplete()", 500);
			}
		}, 1000);
	}
	function downloadComplete(){
		var iframe = document.getElementById('exportDownloadIframe');
		var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
		window.__exportLoading.hide();
		if(!iframeDoc.body.innerHTML){
			//开始下载则显示成功弹框
			seajs.use('lui/dialog', function(dialog){
				dialog.success({status: true, title: "${lfn:message("sys-modeling-base:modeling.exporting.wait.download")}"});
			});
		}
		clearInterval(downloadTimer);
	}

	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {
		window.openDialog = function(url, text){
			dialog.iframe(url, text, function(data){
			},{
				width : 1080,
				height : 600
			});
		}
	});
</script>
</body>
</html>
