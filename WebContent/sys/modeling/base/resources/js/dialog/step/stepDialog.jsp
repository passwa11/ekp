<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 步骤结构对话框，仅支持通过新UI的dialog.iframe引入-->
<template:include ref="default.dialog">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/panelDialog.css" />
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css" />
    </template:replace>
    <template:replace name="content">
    	<div class="step-dialog">
    		<div class="step-dialog-main">
	    		<div data-lui-type="sys/modeling/base/resources/js/dialog/step/stepDialog!StepDialog" 
	    				style="display:none;height:100%" id="stepDialog"></div>
    		</div>
    		<div class="step-dialog-bottom">
    			<ui:button text="${ lfn:message('button.ok') }" width="80" height="30" onclick="ok();"/>
    			<ui:button text="${ lfn:message('button.cancel') }" width="80" height="30" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
    		</div>
    	</div>
    	<script>
    		/**
    		* 使用事项：
    		* 1、变量appViewInfo、modelViewInfo分别为应用视图信息和业务表单视图信息的配置信息，需要扩展的页签可以参考这两个配置参数
    		* 2、使用方式参考同一目录下的test.jsp
    		* 
    		**/
    		// 应用视图信息
			var appViewInfo = {
    			"index" : "1",	// required
				"text" : "${lfn:message('sys-modeling-base:table.modelingApplication')}",	// required
				"sourceUrl" : "/sys/modeling/base/modelingApplication.do?method=getAllAppInfos&c.eq.fdValid=true",	// required
				"renderSrc" : "/sys/modeling/base/resources/js/dialog/step/appRender.html",	// required
				"curAppId" : "",	// optioned
				"viewWgt" : "AppView"	// optioned
			};
			
			// 业务表单视图信息
			var modelViewInfo = {
				"index" : "2",
   				"text" : "${lfn:message('sys-modeling-base:table.modelingAppModel')}",
   				"sourceUrl" : "/sys/modeling/base/modelingAppModel.do?method=findFormByAppId&fdAppId=!{id}&c.like.fdName=!{searchVal}",
   				"renderSrc" : "/sys/modeling/base/resources/js/dialog/step/modelRender.html",
   				"viewWgt" : "ModelView"
   			};
			
			// 扩展视图信息
			var pluginViewInfo = {
				"index" : "3",
   				"text" : "${lfn:message('sys-modeling-base:sysModelingScenes.fdExtension')}",
   				"sourceUrl" : "",
   				"renderSrc" : ""
   			};
    		seajs.use(["lui/dialog"],function(dialog){
    			
    			function getViewInfos(){
    				var viewInfos = [];
    				$dialog.___params = $dialog.___params || {};
    				var tempInfos = $dialog.___params.viewInfos || [appViewInfo, modelViewInfo];
    				
    				for(var i = 0;i < tempInfos.length;i++){
    					var viewInfo = tempInfos[i];
    					if(typeof(viewInfo) === "string"){
    						viewInfo = window[viewInfo];
    					}
    					viewInfos.push(viewInfo);
    				}
    				
    				// TODO 按index排序
    				
    				return viewInfos;
    			}
    			
    			window.init = function(){
    				var viewInfos = getViewInfos();
    				
    				var stepDialogWgt = LUI("stepDialog");
    				stepDialogWgt.initViewInfos(viewInfos, $dialog.___params.values || {});
    				// 默认切换到第一个视图
    				if(viewInfos.length > 0){
    					stepDialogWgt.switchByIndex("1");
    				}
    				
    			}
    			
    			var interval = setInterval(beginInit, "50");
    	    	function beginInit(){
    	    		if(!window['$dialog'])
    	    			return;
    	    		clearInterval(interval);
    	    		init();
    	    	}
    	    	
    	    	window.ok = function(){
    	    		var wgt = LUI("stepDialog");
    				if($dialog.___params.okFn){
    					$dialog.___params.okFn();
    				} else {
    					if(!wgt.validateData()){
    						dialog.alert("${lfn:message('sys-modeling-base:modeling.form.ChooseData')}");
    						return;
    					}
    					$dialog.hide({
    						data : wgt.getKeyData()
    					});
    				}
    			}
    		});
    		
	    	
    	</script>
	</template:replace>
</template:include>