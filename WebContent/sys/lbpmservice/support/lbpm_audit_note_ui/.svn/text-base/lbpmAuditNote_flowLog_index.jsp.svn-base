<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
	<list:listview id="auditNoteTable">
		<ui:source type="AjaxJson">
				{url:'/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=list&fdModelId=${JsParam.fdModelId}&filterType=${JsParam.filterType }'}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
			<list:col-auto props="fdArrivalTime;fdCreateTime;fdFactNodeName;handlerName;fdActionInfo;fdActionName;fdNotifyType;fdCostTimeDisplayString;fdAuditNote"></list:col-auto> 
		</list:colTable>
		<list:paging></list:paging>
	</list:listview>
	<script type="text/javascript">
	function initialPage(){
		try {
			var arguObj = document.getElementById("auditNoteTable");
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				var height = arguObj.offsetHeight + 0;
				if(height>0)
					window.frameElement.style.height = height + "px";
			}
			setTimeout(initialPage, 200);
		} catch(e) {
		}
	}
	Com_AddEventListener(window, "load", initialPage);
	function viewAuditNote(fdAuditNoteId){
		var url = "/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=viewNote&fdModelId=${JsParam.fdModelId}&fdModelName=${JsParam.fdModelName}&fdAuditNoteId="+fdAuditNoteId+"&formBeanName=${JsParam.formName}";
		var width = 600,height = 400;
		seajs.use(['lui/dialog',],function(dialog) {
			dialog.iframe(url, " ", null, 
				{"width" : width,
				"height" : height,
				buttons : [ {
						name : "${lfn:message('button.close') }",
						value : false,
						fn : function(value, dialog) {
							dialog.hide(value);
						}
					} ]}).on('show', function() {
						this.element.find(".lui_dialog_buttons_container").css("float","none").css("text-align","center");
					});
		});
	}
	</script>
	</template:replace>
</template:include>