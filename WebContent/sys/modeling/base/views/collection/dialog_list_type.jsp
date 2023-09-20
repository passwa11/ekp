<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta charset="utf-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
  	<%@ include file="/sys/ui/jsp/common.jsp"%>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}" rel="stylesheet">
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/delDialogRelation.css?s_cache=${LUI_Cache}" rel="stylesheet"/>
	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/card/dialog.css?s_cache=${LUI_Cache}" rel="stylesheet"/>
	<title></title>
</head>
<body>
<div class="model-del">
	<div class="addViewPopContent">
		<div class="addViewContentListBox">
			<ul class="addViewContentList">
				<li class="clearfix selected">
					<div class="addViewContentItemIcon normal"></div>
					<div class="addViewContentItemInfo">
						<p>${lfn:message('sys-modeling-base:listview.normal.list')}</p>
						<span>${lfn:message('sys-modeling-base:listview.normal.list.explan')}</span>
					</div>
				</li>
				<li class="clearfix">
					<div class="addViewContentItemIcon card"></div>
					<div class="addViewContentItemInfo">
						<p>${lfn:message('sys-modeling-base:listview.cardView')}</p>
						<span>${lfn:message('sys-modeling-base:listview.cardView.explan')}</span>
					</div>
				</li>
				<li class="clearfix">
					<div class="addViewContentItemIcon board"></div>
					<div class="addViewContentItemInfo">
						<p>${lfn:message('sys-modeling-base:listview.boardView')}</p>
						<span>${lfn:message('sys-modeling-base:listview.boardView.explan')}</span>
					</div>
				</li>
				<!--<li class="clearfix">
					<div class="addViewContentItemIcon calendar"></div>
					<div class="addViewContentItemInfo">
						<p>日历视图</p>
						<span>按时间维度进行数据查看</span>
					</div>
				</li>
				<li class="clearfix">
					<div class="addViewContentItemIcon resource"></div>
					<div class="addViewContentItemInfo">
						<p>资源面板</p>
						<span>将数据以卡片形式显示</span>
					</div>
				</li>
				<li class="clearfix">
					<div class="addViewContentItemIcon gante"></div>
					<div class="addViewContentItemInfo">
						<p>甘特图</p>
						<span>在看板上以卡片形式展示数据，将数据通过看板进行分类</span>
					</div>
				</li>
				<li class="clearfix">
					<div class="addViewContentItemIcon mind"></div>
					<div class="addViewContentItemInfo">
						<p>思维导图</p>
						<span>按时间维度进行数据查看</span>
					</div>
				</li>-->
			</ul>
		</div>
	</div>
	<div class="toolbar-bottom">
		<ui:button text="${ lfn:message('button.ok')}" onclick="ok();" order="1"/>
		<ui:button text="${ lfn:message('button.cancel')}" styleClass="lui_toolbar_btn_gray" onclick="cancle();" order="2"/>
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
		$dialog.hide('cancle');
	}
	
	function init(){

	}
	var type ='0';
	function ok(){
		//type = $("#list_type").val() || "0";
		$dialog.hide(type);
	}
	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic) {

		$('.addViewContentList>li').click(function(){
			if(!$(this).hasClass('selected')){
				$(this).siblings().removeClass('selected');
				$(this).addClass('selected');
				type = $(this).index() || '0';
				//console.log($(this).index());
			}
		});
	});
	
</script>
</body>
</html>
