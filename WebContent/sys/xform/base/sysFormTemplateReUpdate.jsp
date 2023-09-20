<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form id="templateReUpdate" action="<c:url value="/sys/xform/sys_form_template/sysFormTemplate.do?method=reWriteAllTempates"/>" method="post"></form>
<script type="text/javascript">
	Com_IncludeFile("data.js");
	var msg = '<div style="text-align: left">'
			+ '<kmss:message key="sys-xform:sysFormTemplate.reUpdate.hit" />'
			+ '<br>'
			+ '<kmss:message key="sys-xform:sysFormTemplate.reUpdate.desc" />'
			+ '</div>';
	seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
		window.DoUpdate = function (btn) {
	 		dialog.build({
				config : {
					width : 555,
					cahce : false,
					title : "${lfn:message('sys-xform:sysFormTemplate.reUpdate.alert')}",
					content : {
						type : "common",
						html : msg,
						iconType : 'question',
						buttons : [ {
							name : "${lfn:message('sys-ui:ui.dialog.button.ok')}",
							value : true,
							focus : true,
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						}, {
							name : "${lfn:message('sys-ui:ui.dialog.button.cancel')}",
							value : false,
							styleClass : 'lui_toolbar_btn_gray',
							fn : function(value, dialog) {
								dialog.hide(value);
							}
						} ]
					}
				},
				callback : function(value) {
					if(value == true) {
						$.post('<c:url value="/sys/xform/sys_form_template/sysFormTemplate.do?method=reWriteAllTempates"/>');
		 				//$('#templateReUpdate').submit();
		 				// 开启进度条
						window.progress = dialog.progress(false);
						window._progress("", "", "", "");
		 			}
				}
			}).show();
	 		
	 		var waitTime = 1000;
	 		var progressText = "${lfn:message('sys-ui:ui.dialog.progress.text')}";
	 		window._progress = function (timePoint, totalCount, currentCount, waitCount) {
				var data = new KMSSData();
				data.UseCache = false;
				data.AddBeanData("sysFormTempateJspUpdateXMLDataServiceImp&currentCount="+currentCount+"&totalCount="+totalCount+"&timePoint="+timePoint+"&waitCount="+waitCount);
				var rtn = data.GetHashMapArray()[0];
				var currentCount = parseInt(rtn.currentCount || 0);
				var allCount = parseInt(rtn.totalCount || 0);
				
				if(window.progress) {
					if(rtn.updateState == 1 || currentCount > allCount) {
						// 重新生成JSP模板时，可能会有很多模板出现异常，而导致进度到一半时突然就结束了，这是里处理一下，结束后的显示更直观一些
						if(currentCount < allCount) {
							updateProgress(currentCount, allCount);
						} else {
							window.progress.hide();
						}
					}
					// 设置进度值
					if(allCount == -1 || allCount == 0) {
						window.progress.setProgress(0);
					} else {
						window.progress.setProgressText(progressText + "(" + currentCount + "/" + allCount + ")");
						window.progress.setProgress(currentCount, allCount);
					}
				}
				if(rtn.updateState != 1) {
					if(allCount > 300 && allCount <= 1000)
						waitTime = 2000;
					if(allCount > 1000 && allCount <= 3000)
						waitTime = 3000;
					if(allCount > 3000 && allCount <= 5000)
						waitTime = 4000;
					if(allCount > 5000)
						waitTime = 5000;
					setTimeout(function() {
						window._progress(rtn.timePoint, rtn.totalCount, rtn.currentCount, rtn.waitCount);
					}, waitTime);
				}
			}
	 		window.updateProgress = function(currentCount, allCount) {
	 			currentCount += 100;
	 			if(currentCount > allCount) {
	 				window.progress.hide();
				} else {
					window.progress.setProgressText(progressText + "(" + currentCount + "/" + allCount + ")");
					window.progress.setProgress(currentCount, allCount);
					setTimeout(function() {
						window.updateProgress(currentCount, allCount);
					}, 1500);
				}
	 		}
	 	}
	});
</script>
