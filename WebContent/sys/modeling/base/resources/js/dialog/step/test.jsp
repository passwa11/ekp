<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/panelDialog.css" />
    </template:replace>
    <template:replace name="content">
    	<div>
    		<ui:button onclick="stepDialog();" text="测试两个联动"></ui:button>
    		<ui:button onclick="stepDialog2();" text="测试三个联动"></ui:button>
    		<hr/>
    		<ui:button onclick="modalStepDialog();" text="测试两个联动(模态窗口)"></ui:button>
    		<ui:button onclick="modalStepDialog2();" text="测试三个联动(模态窗口)"></ui:button>
    	</div>
    	<script>
    		Com_IncludeFile("dialog.js", null, "js");
    		seajs.use(["lui/dialog"],function(dialog){
    			
    			window.stepDialog = function(){
    				var url = "/sys/modeling/base/resources/js/dialog/step/stepDialog.jsp";
    				dialog.iframe(url,"选择",function(rtn){
    					console.log(rtn);
    				},{width:900,height:500,close:true});
    			}
    			
    			window.stepDialog2 = function(){
    				var url = "/sys/modeling/base/resources/js/dialog/step/stepDialog.jsp";
    				dialog.iframe(url,"选择",function(rtn){
    					console.log(rtn);
    				},{
    					width:900,
    					height:500,
    					close:true,
    					params : {
    						viewInfos : ["appViewInfo","modelViewInfo",{
    							"index" : "3",
    							"text" : "视图",
    							"sourceUrl" : "/sys/modeling/base/modelingAppListview.do?method=findFormByAppId&fdModelId=!{id}&c.like.fdName=!{searchVal}",
    			   				"renderSrc" : "/sys/modeling/base/resources/js/dialog/step/listRender.html"
    						}]
    					}
    				});
    			}
    			
    			window.modalStepDialog = function(){
    				var dialog = new KMSSDialog();
    				dialog.winTitle = "选择";
    				dialog.URL = Com_Parameter.ContextPath + "sys/modeling/base/resources/js/dialog/step/modalStepDialog.jsp";
    				dialog.SetAfterShow(function(rtn){
    					if(rtn && rtn.data && rtn.data.length > 0){
    						var values = rtn.data[0];
    						console.log(values);
    					}
    				});
    				
    				var size = getSizeForAddress();
    				dialog.Show(size.width, size.height);
    			}
    			
    			window.modalStepDialog2 = function(){
    				var dialog = new KMSSDialog();
    				dialog.winTitle = "选择";
    				dialog.URL = Com_Parameter.ContextPath + "sys/modeling/base/resources/js/dialog/step/modalStepDialog.jsp";
    				dialog.___params = {
    					viewInfos : ["appViewInfo","modelViewInfo",{
    						"index" : "3",
    						"text" : "视图",
    						"sourceUrl" : "/sys/modeling/base/modelingAppListview.do?method=findFormByAppId&fdModelId=!{id}&c.like.fdName=!{searchVal}",
    		   				"renderSrc" : "/sys/modeling/base/resources/js/dialog/step/listRender.html"
    					}]
    				};
    				dialog.SetAfterShow(function(rtn){
    					if(rtn && rtn.data && rtn.data.length > 0){
    						var values = rtn.data[0];
    						console.log(values);
    					}
    				});
    				
    				var size = getSizeForAddress();
    				dialog.Show(size.width, size.height);
    			}
    		});
	    	
    	</script>
	</template:replace>
</template:include>