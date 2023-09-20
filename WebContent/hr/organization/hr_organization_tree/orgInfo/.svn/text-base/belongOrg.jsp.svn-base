<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
	<template:replace name="head">
	<style>
	.lui_tree_container{
		max-height:initial!important;
	}
	.lui_tree_container .tree-node-text-inside{
		width:auto;
		max-width:300px;
	}
	.icon-node-type{
		width:16px;
		height:16px;
	}
	.nodata{
		background:url("../../resource/images/empty.png") no-repeat top center;
		width:69px;
		height:70px;
		margin:200px auto;
		padding-top:48px;
		text-align:center;
	}
	</style>
	</template:replace>
    <template:replace name="body">
	<script type="text/javascript">
		seajs.use(['theme!form']);
		Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
		Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|data.js", null, "js");
	</script>    
	
	<script>
	seajs.use(["lui/dialog","lui/topic","hr/organization/resource/js/tree/dialogTree"],function(dialog,topic,widgetTree){
		var treeObj = new widgetTree.dialogTree({
			installRootDom:$(document.body),
			filterId:'${param.fdId}',
			orgTypeFilter:'${param.orgType}',
			icon:true
		});
		if(!treeObj.firstNode){
			treeObj.element.find(".lui_tree_container").append($("<div class='nodata'>暂无数据</di>"))
		}
		$(".lui_tree_container").on("click",function(e){
			if($(e.target).is("input[type=radio]")){
				var name =$(e.target).attr("data-name");
				var id=	$(e.target).attr("data-id");
				window.$dialog.hide({
					name:name,
					id:id
				});
			}
		})
	})

			
	</script>
    </template:replace>
</template:include>