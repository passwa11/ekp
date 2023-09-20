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
	
	function buildHtml(objs, name){
		var html = "";
		if(objs){
			for(j in objs){
				html += "<tr>";
				html += "<td>" + name + "</td>";
				html += "<td>" + objs[j].text + "</td>";
				html += "<td onclick='openDialog(\"" + objs[j].url + "\", \"" + objs[j].text + "\")'>修改</td>";
				html += "</tr>";
			}
		}
		return html;
	}
	
	function init(){
		var datas = $dialog.___params.datas;
		if(!datas){
			return;
		}
		var html = "";
		html += "<p>当前关联模块已被引用，请先解除其关联关系</p>";
		html += "<p>“" + datas[0].subject + "”关联关系详情</p>";
		html += "<div class=\"model-del-table\"><table>";
		//标题
		html += "<thead><tr>";
		html += "<td>功能</td>";
		html += "<td>关联详情</td>";
		html += "<td>操作</td>";
		html += "</tr></thead>";

		html += "<tbody>";
		for(i in datas){
			html += buildHtml(datas[i].navs, "菜单设置");
			html += buildHtml(datas[i].mobileHome, "移动首页设置");
			html += buildHtml(datas[i].opers, "业务操作");
			html += buildHtml(datas[i].views, "查看视图");
			html += buildHtml(datas[i].listviews, "列表视图");
			html += buildHtml(datas[i].scenes, "触发场景");
			html += buildHtml(datas[i].relation, "业务关系");
			html += buildHtml(datas[i].automaticFill, "自动流程发起");
			html += buildHtml(datas[i].validate, "数据提交校验");
		}
		html += "</tbody>";
		html+="</table></div>";
		$("#content").html(html);
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
