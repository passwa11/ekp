<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />

<!-- 表单暂存开始 -->
<c:if test="${sysWfBusinessForm.method_GET=='view'&& (sysWfBusinessForm.docStatus=='20' || sysWfBusinessForm.docStatus=='11')}">
	<ui:button parentId="toolbar"  text="${ lfn:message('sys-lbpmservice:button.saveFormData') }" order="1"  id="saveFormData"
			onclick="_saveFormData();">
	</ui:button>
	<div id="_saveFormDataBtnDiv" style="display:none;">
		<input type="button" value="${ lfn:message('sys-lbpmservice:button.saveFormData') }" onclick="_saveFormData();" />
	</div>
</c:if>
<!-- 表单暂存结束 -->

<!-- 表单数据暂存脚本开始 -->
<script type="text/javascript">
Com_IncludeFile("optbar.js");
lbpm.onLoadEvents.delay.push(function(){
	//使用新UI
	if (typeof LUI !== "undefined" && !document.getElementById("optBarDiv")){
		LUI.ready(function(){
			var currentHandler = lbpm.allMyProcessorInfoObj;
			if(currentHandler && currentHandler.length <= 0){
				var button = LUI("saveFormData");
				if(button){
					LUI("toolbar").removeButton(button);
				}
			}
		});
	}else{
		//老UI
		if(document.getElementById("optBarDiv")){
			if (document.getElementById("saveFormData")){
				document.getElementById("saveFormData").style.display = "none";
			}
			if (document.getElementById("_saveFormDataBtnDiv")){
				OptBar_AddOptBar("_saveFormDataBtnDiv");
			}
		}
	}
})

function _saveFormData(){
	//#48135 增加EKP审批过程中暂存表单的功能（已测试过，提交时把sysWfBusinessForm.canStartProcess字段值置为false即可实现这个功能，希望纳入产品）
	var canStartProcess = document.getElementById("sysWfBusinessForm.canStartProcess");
	var docStatus ="${sysWfBusinessForm.docStatus}";
	var formName = "${param.formName}";
	$(canStartProcess).prop("value","false");
	//触发流程查看页面提交按钮
	if(lbpm){
		lbpm.saveFormData = true;
	}
	if (docStatus === "11" ){
		if(beforeLbpmSubmit){
			beforeLbpmSubmit();
		}
		Com_Submit(document[formName]||document.forms[0], 'update');
	}
	if(docStatus === "20"){
		$("#process_review_button").click();
	}
}
</script>