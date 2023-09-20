<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.design.SysConfigs" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.alibaba.fastjson.JSON"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.config.design.SysCfgModuleInfo"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	List<SysCfgModuleInfo> moduleList = SysConfigs.getInstance().getModuleInfoList();
	Map<String, String> moduleMap = new HashMap<String, String>();
	for(SysCfgModuleInfo module : moduleList){
		if(StringUtil.isNotNull(module.getMessageKey())){
			if(module.getMessageKey().contains(":")){
				moduleMap.put(module.getUrlPrefix(), module.getMessageKey().split(":")[1]);
			}
			else{
				moduleMap.put(module.getUrlPrefix(), module.getMessageKey());
			}
		}
	}
	String allModulesString = JSON.toJSONString(moduleMap);
%>

<template:include ref="default.edit" sidebar="no" showQrcode="false">
	<template:replace name="content">
		<%--#139056 很多页面，偶发点击快编按钮，字段都无法正常加载（个别浏览器的html页面规范问题）--%>
		<style type="text/css">
			div.loadMore{
				background-color: #F1F3F4;
				text-align: center;
				height: 50px;
				line-height: 40px;
				cursor: pointer;
			}
		</style>

		<div id="iframeList">
		</div>
	 	<div class="loadMore">
		 	${ lfn:message('sys-profile:sys.profile.i18n.multilang.loadmore') }
		 	(<span>0</span>)
	 	</div>
		<div class="lui_handover_btn_w" style="text-align:center;">
			<ui:button style="width:100px" text="${ lfn:message('sys-profile:sys.profile.i18n.seach.submit') }" onclick="submitOperation();"/>
	 	</div>
		<%--#139056 很多页面，偶发点击快编按钮，字段都无法正常加载（个别浏览器的html页面规范问题）--%>
		<script type="text/javascript">
			//渲染多语言列表
			function renderMessagList(){
				var url = '<c:url value="/sys/profile/i18n/module_message_list.jsp?showRow=N&bundle="/>';
				var pageSize = 5, $loadMore = $("div.loadMore"), sortedList = $("div.loadMore").data("sortedList"),
						currentIndex = $loadMore.data("currentIndex"),  loadedIframes = $loadMore.data("loadedIframes");
				if(typeof(currentIndex) === 'undefined'){
					currentIndex = 0;
				}
				if(typeof(loadedIframes) === 'undefined'){
					loadedIframes = new Array();
				}

				for(var index = currentIndex; index < sortedList.length; index++){
					if((index - currentIndex) == pageSize){
						$loadMore.data("currentIndex", index);
						$loadMore.children("span").text(sortedList.length - index);
						break;
					}
					var module = sortedList[index];
					var iframeId = encodeURIComponent(module);
					var iframe = document.getElementById(iframeId);
					if(iframe === null){
						iframe = document.createElement("iframe");
						iframe.id = iframeId;
						iframe.setAttribute("frameborder", "0");
						iframe.width = "100%";
						iframe.src = (url + encodeURIComponent(module));
						document.getElementById("iframeList").appendChild(iframe);
						loadedIframes.push(iframe);
					}
				}
				if(sortedList.length === loadedIframes.length){
					$loadMore.css("display", "none");
				}
				$loadMore.data("loadedIframes", loadedIframes);
				return loadedIframes;
			}
			function submitOperation(){
				seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
					window.del_load = dialog.loading();
					var loadedIframes = $("div.loadMore").data("loadedIframes");
					loadedIframes.forEach(function(iframe, index){
						if(iframe && iframe.contentWindow){
							iframe.contentWindow.submitOperation();
						}
					});
					var isAllUpdated = false;
					var t = setInterval(function(){
						if(isAllUpdated){
							window.clearInterval(t);
							if(window.del_load != null) {
								window.del_load.hide();
							}
							delete top.quicklyEditDialog;
							dialog.success("${ lfn:message('sys-profile:sys.profile.i18n.saveMessage.success') }");
							setTimeout(function(){
								top.location.reload();
							}, 2500);
							return;
						}
						loadedIframes.forEach(function(iframe, index){
							if(iframe && "true" === iframe.contentWindow.document.postCompleted){
								isAllUpdated = true;
							}
							else{
								isAllUpdated = false;
								return;
							}
						});
					},1000);
				});
			}
			function start2Load(dialog){
				window.del_load = dialog.loading();
				var loadedIframes = renderMessagList();
				var isAllLoded = false;
				var t = setInterval(function(){
					if(isAllLoded){
						window.clearInterval(t);
						if(window.del_load != null) {
							window.del_load.hide();
						}
						return;
					}
					loadedIframes.forEach(function(iframe, index){
						if(iframe && "true" === iframe.contentWindow.document.loaded){
							isAllLoded = true;
						}
						else{
							isAllLoded = false;
							return;
						}
					});
				},1000);
			}
			function quicklyMultiLangEditOnload(){
				seajs.use( [ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
					sortModules(top.moduleMessages,function(list){
						$("div.loadMore").data("sortedList", list);
						start2Load(dialog);
					});
					if(typeof $("div.loadMore").data("events") === 'undefined'){
						$("div.loadMore").click(function(){
							start2Load(dialog);
						});
					}
				});
			}

			function convertStrToJsonObj(data){
				try{
					if(typeof JSON === 'object'){
						return JSON.parse(data);
					}
					else{
						return eval(data);
					}
				}
				catch(err)
				{
					return null;
				}
			}

			function sortModules(modules,callBackFun){
				var newModules = JSON.parse(JSON.stringify(modules));
				var sortedModules = new Array();
				var length = Object.keys(newModules).length;
				while(length > 0){
					var max = 0, maxModule, maxMessagekeys ;
					for(var module in newModules){
						var messageKeys = newModules[module];
						if(max < messageKeys.length){
							max = messageKeys.length;
							maxModule = module;
						}
					}
					sortedModules.push(maxModule);
					delete newModules[maxModule];
					length = Object.keys(newModules).length;
				}
				if(!!callBackFun){
					callBackFun(sortedModules);
				}
				//return sortedModules;
			}

			function messageFilter(modules){
				var allModuleList = convertStrToJsonObj('<%=allModulesString%>');
				var newModules = {};
				for(var module in modules){
					var messageKeys = modules[module];
					if(messageKeys.length == 1 && allModuleList[module] === messageKeys[0]){
						continue;
					}
					if(allModuleList[module]){
						newModules[module] = messageKeys;
					}
				}
				return newModules;
			}

			if(typeof top.messageKeyDocs === 'object'){
				var modules = {};
				for(var i in top.messageKeyDocs){
					var messageKeys = top.messageKeyDocs[i].messageKeys;
					if(typeof messageKeys === 'object'){
						var module, message;
						for(var j in messageKeys){
							module = messageKeys[j].bundle;
							message = messageKeys[j].key;
							if(typeof modules[module] === 'undefined'){
								modules[module] = [];
							}
							modules[module].push(message);
						}
					}
				}
				delete top.messageKeyDocs;
				top.moduleMessages = messageFilter(modules);
			}
		</script>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEditHeader.jsp"></c:import>
 	</template:replace>
</template:include>