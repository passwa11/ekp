<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no" spa="true">
	<template:replace name="body">
	<script type="text/javascript">
		seajs.use(['theme!module']);	
	</script>
<script type="text/javascript">
var fdId = "";
//记录节点类型（模板和分类）
var fdNodeType = "";
var hideDiv = null;
function showCreateDiv(id,nodeType,obj) {
	var url = "${LUI_ContextPath}/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=checkAuth&fdTempid="+id;
	if("TEMPLATE"==nodeType){
		//如果是模板则判断权限
	$.ajax({     
	     type:"post",    
	     url:url,     
	     async:true,     
	     success:function(data){
	     var a = LUI.toJSON(data);
	     if(a["value"] == "true"){
	    	 showQuickIcon(id,nodeType,obj);
	     }
	   }
     });
	}else{
		//如果是分类则显示快速新建图标
		showQuickIcon(id,nodeType,obj);
	}
 }

function showQuickIcon(id,nodeType,obj){
	fdId = id;
	fdNodeType = nodeType;
	var newDiv = document.getElementById("newDiv");
	var p = LUI.$(obj).position();
	LUI.$(newDiv).css('display','block');
	LUI.$(newDiv).css('left',p.left+LUI.$(obj).width()+26);
	LUI.$(newDiv).css('top', p.top);
	LUI.$(newDiv).css('z-index', 1000);
	if(hideDiv){
		clearTimeout(hideDiv);
	}
}
function quickCreate(){
	seajs.use(['lui/dialog'], function(dialog) {
		if("TEMPLATE"==fdNodeType){
			window.open("${LUI_ContextPath}/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=add&i.docTemplate="+fdId, '_blank');
		}else{
		dialog.categoryForNewFile(
				'com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
				'/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=add&i.docTemplate=!{id}',false,null,null,fdId,null,null,true);
		}
	});
}
function openUrl(id,nodeType){
	
	parent.moduleAPI.hrRatify.openPreview(id);
	
}

function hideCreateDiv(){
	hideDiv =  setTimeout(function(){
		  document.getElementById("newDiv").style.display="none";
	 },2000);
}

window.pageResize=function(){
	if(parent.document.all("mainIframe")){
		parent.document.all("mainIframe").style.height=(document.body.offsetHeight+30)+'px';
	}
};

</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}hr/ratify/resource/style/prestyle/previewnew.css" />
<div id="newDiv" style="display:none;position:absolute;">
<a href="javascript:;" onclick="quickCreate();"><img  src="../resource/style/images/tips.png">
</a>
</div>
		<div style="margin: 0 auto; padding-top:10px; background-color: #f7f7f7;">
			<!-- <h1 style="text-align:center;font-size:20px;margin-top:0px">人事流程概览</h1> -->
		    <ui:dataview>
				<ui:source type="AjaxJson">
				    {"url":"/hr/ratify/hr_ratify_overview/hrRatifyOverview.do?method=preview"}
				</ui:source>
				<ui:render type="Template">
					<c:import url="/hr/ratify/resource/tmpl/treemenu2.tmpl"></c:import>
				</ui:render>
				<ui:event event="load">
					window.pageResize();
				</ui:event>
			</ui:dataview>
		</div>
	</template:replace> 
</template:include>
