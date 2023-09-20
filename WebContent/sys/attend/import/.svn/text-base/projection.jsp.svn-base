<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		${ lfn:message('sys-attend:sysAttendMain.projection.title') }
	</template:replace>
	 <template:replace name="head">
	 	<script type="text/javascript">	
			seajs.use(['${KMSS_Parameter_ContextPath}sys/attend/resource/css/import_view.css?s_cache=${LUI_Cache}']);
		</script>
	</template:replace>
	<template:replace name="content">
		<input type="hidden" id="fdCategoryStatus">
		 <div class="lui_attend_projection_bg">
		 	<input type="hidden" id="_fullScreenFlag" value="0">
			<div class="lui_attend_projection_panel">
				<div class="lui_attend_projection_panel_heading">
					<h2 class="lui_attend_projection_panel_title">${ lfn:message('sys-attend:sysAttendMain.projection.tips') }</h2>
				</div>
				<div class="lui_attend_projection_panel_body">
					<div class="lui_attend_projection_panel_left">
						<div class="lui_attend_projection_qr_wrap">
							<div class="lui_attend_projection_qr"></div>
							<div class="lui_attend_projection_qr_tip">
								<span>${ lfn:message('sys-attend:sysAttendMain.projection.totalpeople') }</span>
								<span class="count">0</span><span>${ lfn:message('sys-attend:sysAttendMain.projection.people') }</span>
							</div>
						</div>
					</div>
					<div class="lui_attend_projection_panel_right">
						<ul class="lui_attend_projection_list"></ul>
					</div>
					<button class="lui_attend_projection_btn" onclick="onFullScreenClick()"></button>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		seajs.use(['lui/jquery', 'lui/util/env', 'lui/dialog','lui/qrcode'], function($, env, dialog,QRCode) {
		 	var isFreshWithTemplate = false;
		 	LUI.ready(function(){
		 		renderQRCode();
		 		viewStat();
		 		loadSignList();
		 	})
		 	window.viewStat = function(){
		 		var url = "/sys/attend/sys_attend_category/sysAttendCategory.do?method=stat&appId=${JsParam.fdModelId}&fdCategoryId=${JsParam.fdCategoryId}" ;
		 		$.ajax({
					url: env.fn.formatUrl(url),
					type: 'GET',
					dataType: 'json',
					error: function(data){
						dialog.failure('<bean:message key="return.optFailure" />');
					},
					success: function(data){
						if(data && data.length>0){
							var record = data[0];
							$('.lui_attend_projection_qr_tip .count').html(record.attendcount);
						}
					}
				});	
		 	}
		 	window.renderQRCode = function(options){
		 		options = options || {};
		 		// JsParam默认解码了一次，不用decodeURIComponent
		 		var t = new Date().getTime();
				$.ajax({
					url:env.fn.formatUrl('/sys/attend/sys_attend_main/sysAttendMain.do?method=getDbTimeMillis&forward=lui-source'),
					type:"POST",
					dataType:"json",
					async:false,
					success:function(data){
						if (data.nowTime) {
							t = data.nowTime;
						}
					}
				});
		 		var scanUrl = '${JsParam.fdQRCodeUrl}' + "&t="+t;
		 		var qrCodeUpdateTime = parseInt(Com_GetUrlParameter('${JsParam.fdQRCodeUrl}', 'qrCodeTime')) * 1000;
		 		$('.lui_attend_projection_qr').empty();
		 		QRCode.Qrcode({
					text : scanUrl,
					width:options.width || 300,
					height:options.height || 300,
					element : $('.lui_attend_projection_qr')[0]
				});
		 		var fdCategoryStatus = $('#fdCategoryStatus').val();
		 		if(fdCategoryStatus!='2'){
		 			window.setTimeout(function(){
						var value = $('#_fullScreenFlag').val();
						var p = {width:300,height:300};
						if(value=='1'){
							p = {width:300,height:300};
						}
						renderQRCode(p);
			 		},qrCodeUpdateTime || 60000);
		 		}
				
		 	}

		 	window.loadSignList = function(){
		 		var url = "/sys/attend/sys_attend_main/sysAttendMain.do?method=list&categoryType=custom&rowsize=7&operType=1&orderby=docCreateTime&ordertype=down&appId=${JsParam.fdModelId}&fdCategoryId=${JsParam.fdCategoryId }" ;
		 		$.ajax({
					url: env.fn.formatUrl(url),
					type: 'GET',
					dataType: 'json',
					error: function(data){
					},
					success: function(result){
						if(result && result.datas.length>0){
							var datas = formatData(result.datas);
							$('#fdCategoryStatus').val(datas[0]['fdCategory.fdStatus']);
							$('.lui_attend_projection_qr_tip .count').html(result.page.totalSize);
							var ulNode = $('.lui_attend_projection_list').empty();
							for(var idx in datas){
								var record = datas[idx];
								var liNode = $('<li/>').addClass('lui_attend_projection_listItem');
								
								var imgNode = $('<div class="lui_attend_projection_avatar"><img src="' + record.docCreatorImg + '"></div>');
								var personNode = $('<div/>').addClass('lui_attend_projection_person').html(record['docCreator.fdName']);
								var timeNode = $('<div/>').addClass('lui_attend_projection_time').html(record['docCreateTime']);
								liNode.prependTo(ulNode);
								timeNode.prependTo(liNode);
								personNode.prependTo(liNode);
								imgNode.prependTo(liNode);
							}
							
						}
					}
				});	
		 		var fdCategoryStatus = $('#fdCategoryStatus').val();
		 		if(fdCategoryStatus!='2'){
		 			window.setTimeout(loadSignList,5000);
		 		}
		 	}
		 	window.formatData = function(datas){
				var data = [];
				for(var i = 0 ; i < datas.length;i++){
					var records = datas[i];
					var record = {};
					for(var idx in records){
						record[records[idx].col] = records[idx].value;
					}
					data.push(record);
				}
				return data;
			}
		 	window.onFullScreenClick = function() {
		 		if($('#_fullScreenFlag').val()=='1'){
		 			exitFullscreen();
		 			return;
		 		}
		 		var element = $('.lui_attend_projection_bg')[0];
		 		var value = "1";
		 		if(element.requestFullScreen) { 
		 		  element.requestFullScreen(); 
		 		} else if(element.webkitRequestFullScreen ) { 
		 		   element.webkitRequestFullScreen(); 
		 		} else if(element.mozRequestFullScreen) { 
		 		  element.mozRequestFullScreen(); 
		 		}else if(element.oRequestFullscreen){
		 			element.oRequestFullscreen();
		 		}else{
		 			value = "0";
		 			dialog.alert("${ lfn:message('sys-attend:sysAttendMain.projection.fullscreen.warn') }");
		 		} 
		 		$('#_fullScreenFlag').val(value);
		 	}
		 	window.exitFullscreen = function() {
		 		var value = '0';
		 		if(document.exitFullscreen) {
		 		  document.exitFullscreen();
		 		} else if(document.mozCancelFullScreen) {
		 		  document.mozCancelFullScreen();
		 		} else if(document.webkitExitFullscreen) {
		 		  document.webkitExitFullscreen();
		 		}else{
		 			value = $('#_fullScreenFlag').val();
		 		}
		 		$('#_fullScreenFlag').val(value);
		 	}
		 });
              
		</script>
	</template:replace>
</template:include>