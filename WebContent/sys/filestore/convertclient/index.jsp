<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<style type="text/css">
			.help li {
				font-weight: bold;
				line-height: 250%;
			}
			
			.help div {
				font-weight: normal;
				line-height: 180%;
			}
			.clientQueueFrame{
				height: 1300px;
			}
		</style>
		<script type="text/javascript">
			seajs.use([ 'theme!list', 'theme!portal' ]);
			LUI.ready(function() {
				//seajs.use();
			});
			
			window.identityType="clientIndex";
			window.dialoging=false;

			domain.register("filestoreClientEvent", function(data) {
				if ("resize" == data.name) {
					var iframeId = "iframe_" + data.target;
					var clientSize = window.clientSize;
					// 没有数据的情况
					if(clientSize != undefined && clientSize == -999) {
						document.getElementById(iframeId).style.height = (data.data.height+60)+ "px";
						window.dialoging=true;
					} else if(clientSize != undefined && clientSize <= 0) { //加载好了数据
						window.dialoging=true;
					} else {  //数据加载过程
						window.clientSize = clientSize - 1;
						document.getElementById(iframeId).style.height = (data.data.height + 5 * clientSize)+ "px";
					}


				}
				if("loadClientQueueInfo"==data.name){
					loadClientQueueInfo(data.clientId);
				}
				if("removeClientQueueInfo"==data.name){
					$("#iframe_queue").removeAttr("src");
					$("#iframe_queue").removeAttr("height");
					$("#div_queue_infos").hide();
				}
			});
			
			var loadClientQueueInfo=function(clientId){
				$("#iframe_queue").removeAttr("src");
				$("#iframe_queue").removeAttr("height");
				var logSrc="${LUI_ContextPath }/sys/filestore/convertqueue/queueList.jsp?LUIID=queue&clientId="+clientId;
				$("#iframe_queue").attr("src", logSrc);
				$("#iframe_queue").load(function(){
					$("#div_queue_infos").show();
					//滑动
				});
			};
			
			var showHelp=function(){
				if("none"==$("#div_help").css("display")){
					$("#div_help").css("display","");
				}else{
					$("#div_help").css("display","none");
				}
			}
		</script>
		<div id="div_clients">
			<iframe name="clientsFrame" id="iframe_client" frameborder="0"
				scrolling="no" style="width: 100%; margin-top: 6px;"
				src="${LUI_ContextPath }/sys/filestore/convertclient/clientList.jsp?LUIID=client"></iframe>
		</div>
		<div id="div_version_desc" style="margin:6px auto;width:95%">
			<br>
			<a href="#" style="display:block;background-color: #f0f0f0; padding:12px; font-size: 14px;"
				onclick="showHelp();">
				<bean:message bundle="sys-filestore" key="converterList.description.version.info" /></a>
			<div id="div_help" class="help" style="marging-left:30px;width:95%;display:none;padding-left:12px;">
				<li><bean:message bundle="sys-filestore" key="sysFileConverter.clientVersion" /></li>
				<div>
					<bean:message bundle="sys-filestore" key="converterList.description.clientVersion.info" />
				</div>
				<li><bean:message bundle="sys-filestore" key="sysFileConverter.converterVersion" /></li>
				<div>
					<bean:message bundle="sys-filestore" key="converterList.description.converterVersion.info" />
				</div>
			</div>
		</div>
		<div id="div_queue_infos" style="padding-top: 16px; display: none;">
			<iframe name="clientQueueFrame" id="iframe_queue" frameborder="0" scrolling="yes"
				style="width: 100%" class="clientQueueFrame"></iframe>
		</div>
	</template:replace>
</template:include>