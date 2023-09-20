<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use([ 'theme!list', 'theme!portal' ]);
			LUI.ready(function() {
				//seajs.use();
			});
			
			window.identityType="queueIndex";
			window.dialoging=false;

			domain.register("filestoreQueueEvent", function(data) {
				if ("resize" == data.name) {
					var iframeId = "iframe_" + data.target;
					document.getElementById(iframeId).style.height = data.data.height+ "px";
				}
				if("loadQueueLogInfo"==data.name){
					loadQueueLogInfo(data.queueId,data.queueFileName)
				}
				if("removeQueueLogInfo"==data.name){
					$("#iframe_log").removeAttr("src");
					$("#iframe_log").removeAttr("height");
					$("#div_log_infos").hide();
				}
			});
			
			var loadQueueLogInfo=function(queueId,queueFileName){
				$("#iframe_log").removeAttr("src");
				$("#iframe_log").removeAttr("height");
				$("#detailSubject").html(queueFileName+"-"+"${lfn:message('sys-filestore:table.sysFileConvertLog')}");
				var logSrc="${LUI_ContextPath }/sys/filestore/convertlog/logList.jsp?LUIID=log&queueId="+queueId;
				$("#div_log_infos").show();
				$("#iframe_log").attr("src", logSrc);
				$("#iframe_log").load(function(){
					$('html, body').animate({
			            scrollTop: $("#div_log_infos").offset().top
			        }, 1500);
					setTimeout("$('html, body').stop();",1000);
				});
			}
		</script>
		<div id="div_queues">
			<iframe name="queuesFrame" id="iframe_queue" frameborder="0"
				scrolling="no" style="width: 100%; margin-top: 6px;"
				src="${LUI_ContextPath }/sys/filestore/convertqueue/queueList.jsp?LUIID=queue"></iframe>
		</div>
		<div id="div_log_infos" style="padding-top: 16px; display: none;">
			<div align="center" id="detailSubject" class="lui_form_subject" style="font-size:26px;margin:6px 10px 16px 10px;"></div>
			<iframe name="queueLogFrame" id="iframe_log" frameborder="0" scrolling="no"
				style="width: 100%"></iframe>
		</div>
	</template:replace>
</template:include>