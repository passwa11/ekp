<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 步骤结构对话框，模态窗口 -->
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
    		/************ 模态窗口的兼容处理 start ****************/
	    	var dialogRtnValue = null;
	    	var dialogObject = null;
	    	var isOpenWindow = true;//弹出形式:弹窗or弹层
	    	if(window.showModalDialog && window.dialogArguments){
	    		dialogObject = window.dialogArguments;
	    	}else if(opener && opener.Com_Parameter.Dialog){
	    		dialogObject = opener.Com_Parameter.Dialog;
	    	}else{
	    		dialogObject = (Com_Parameter.top || window.top).Com_Parameter.Dialog;
	    		isOpenWindow = false;
	    	}
	    	if(dialogObject){
	    		Com_Parameter.XMLDebug = dialogObject.XMLDebug;
	    		var Data_XMLCatche = new Object();
	    	}
	    	Com_AddEventListener(window, "beforeunload", beforeClose);
	    	function dialogReturn(value){
	    		window.dialogRtnValue = $.extend(true, {}, value);//复制一份新数组,防止window.close时出现无法执行已释放的script代码
	    		if(isOpenWindow){
	    			window.close();
	    		}else if(window.$dialog!=null){
	    			dialogObject.rtnData = dialogRtnValue;
	    			dialogObject.AfterShow();
	    			$dialog.hide();
	    		}
	    	}
	    	
	    	function beforeClose(){
	    		dialogObject.rtnData = dialogRtnValue;
	    		dialogObject.AfterShow();
	    	}
	    	Com_SetWindowTitle(dialogObject.winTitle);
	    	
	    	/************ 模态窗口的兼容处理 start ****************/
	    	
    		/**
    		* 使用事项：
    		* 1、变量appViewInfo、modelViewInfo分别为应用视图信息和业务表单视图信息的配置信息，需要扩展的页签可以参考这两个配置参数
    		* 2、使用方式参考同一目录下的test.jsp
    		* 
    		**/
    		// 应用视图信息
			var appViewInfo = {
    			"index" : "1",	// required
				"text" : "应用",	// required
				"sourceUrl" : "/sys/modeling/base/modelingApplication.do?method=getAllAppInfos",	// required
				"renderSrc" : "/sys/modeling/base/resources/js/dialog/step/appRender.html",	// required
				"curAppId" : "",	// optioned
				"viewWgt" : "AppView"	// optioned
			};
			
			// 业务表单视图信息
			var modelViewInfo = {
				"index" : "2",
   				"text" : "表单",
   				"sourceUrl" : "/sys/modeling/base/modelingAppModel.do?method=findFormByAppId&fdAppId=!{id}&c.like.fdName=!{searchVal}",
   				"renderSrc" : "/sys/modeling/base/resources/js/dialog/step/modelRender.html",
   				"viewWgt" : "ModelView"
   			};
			
			// 扩展视图信息
			var pluginViewInfo = {
				"index" : "3",
   				"text" : "扩展",
   				"sourceUrl" : "",
   				"renderSrc" : ""
   			};
    		seajs.use(["lui/dialog"],function(dialog){
    	    	window.ok = function(){
    	    		var wgt = LUI("stepDialog");
   					if(!wgt.validateData()){
   						dialog.alert("请先选择数据！");
   						return;
   					}
   					var rtn = wgt.getKeyData();
   					dialogReturn({
   						data : rtn
   					});
    			}
    	    	
    		});
    		
    		function getViewInfos(){
				var viewInfos = [];
				dialogObject.___params = dialogObject.___params || {};
				var tempInfos = dialogObject.___params.viewInfos || [appViewInfo, modelViewInfo];
				
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
    		
    		function init(){
				var viewInfos = getViewInfos();
				
				var stepDialogWgt = LUI("stepDialog");
				stepDialogWgt.initViewInfos(viewInfos, dialogObject.___params.values || {});
				// 默认切换到第一个视图
				if(viewInfos.length > 0){
					stepDialogWgt.switchByIndex("1");
				}
				
			}
    		
    		LUI.ready(init);	    	
    	</script>
	</template:replace>
</template:include>