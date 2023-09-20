<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/delDialogRelation.css?s_cache=${LUI_Cache}" rel="stylesheet"/>
	<title></title>
</head>
<body>
<div class="model-del">
	<div class="model-del-content" id="content"></div>
	<div class="toolbar-bottom">
		<ui:button text="${ lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="$dialog.hide();" order="2"/>
	</div>
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

	function buildDependFuncs(dependFuncs, name, depends){
		var html = "";
		if(dependFuncs){
			for(j in dependFuncs){
				html += "<tr>";
				html += "<td>" + dependFuncs[j].text + "</td>";
				html += "<td>" + dependFuncs[j].fdName + "</td>";
				html += "<td onclick='openDialog(\"" + dependFuncs[j].url + "\", \"" + dependFuncs[j].text + "\")'>修改</td>";
				html += "</tr>";
			}
		}
		if(depends){
			html += buildDepends(depends);
		}
		return html;
	}

	function buildDepends(depends){
		if(!depends)
			return;
		var html = "";
		for(i in depends){
			html += buildDependFuncs(depends[i].dependFuncs, depends[i].fdName, depends[i].depends);
		}
		return html;
	}

	function init(){
		var delObjType = $dialog.___params.delObjType;
		var datas = $dialog.___params.relatedDatas;
		if(!datas){
			return;
		}
		var delFdName = (delObjType === 'app' ? "fdAppName" : "fdName");
		var html = "";
		html += "<p>${lfn:message("sys-modeling-base:modelingApplication.fdAppProblem")}</p>";
		var existsAppData=datas['existsAppData'];
		if(typeof (existsAppData)!="undefined"){
			html += "<p>" + existsAppData + "</p>";
		}
		if(datas.depends){
			html += "<p>${lfn:message("sys-modeling-base:modelingApplication.fdAppToDoUnBind")}</p>";
			html += "<div class=\"model-del-table\"><table>";
			//标题
			html += "<thead><tr>";
			html += "<td>${lfn:message("sys-modeling-base:modelingApplication.fdAppFunction")}</td>";
			html += "<td>${lfn:message("sys-modeling-base:modelingApplication.fdAppDetail")} </td>";
			html += "<td>${lfn:message("sys-modeling-base:modelingApplication.fdAppOperation")} </td>";
			html += "</tr></thead>";
			html += buildDepends(datas.depends);
			html += "</tbody>";
			html+="</table></div>";
		}
		$("#content").html(html);
	}

	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog) {
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
