<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="content"> 
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmArchivesDestroyForm" method="post" action ="${KMSS_Parameter_ContextPath}km/archives/km_archives_destroy/kmArchivesDestroy.do">
	</c:if>
	<div class='lui_form_title_frame'>
        <div class='lui_form_subject'>
            ${lfn:message('km-archives:table.kmArchivesDestroy')}
        </div>
    </div>
	<html:hidden property="fdId" />
    <html:hidden property="docStatus" />
    <html:hidden property="method_GET" />
    <html:hidden property="_currentFdTemplateId" value = "${kmArchivesDestroyForm.docTemplateId}" />
    <c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:import url="/km/archives/km_archives_destroy/kmArchivesDestroy_editContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform"></c:param>
  			</c:import>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesDestroyForm" />
					<c:param name="fdKey" value="kmArchivesDestroy" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				<c:import url="/km/archives/km_archives_destroy/kmArchivesDestroy_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/km/archives/km_archives_destroy/kmArchivesDestroy_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/km/archives/km_archives_destroy/kmArchivesDestroy_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	<script>
		    var _validation = $KMSSValidation(document.kmArchivesDestroyForm);
		   	_validation.addValidator("archNotNull","${lfn:message('km-archives:kmArchivesBorrow.archNotNull')}",function(v, e, o) {
				if($("#TABLE_DocList tr:not(:first)").length>0){
			         return true;
				}else{
					 return false;
				}
			});
		   	
		    function _updateDraft(){
		  		var docStatus = document.getElementsByName("docStatus")[0];
	        	docStatus.value="10";
				Com_Submit(document.kmArchivesDestroyForm, 'update');
			}
		  	
			function _saveDraft(){
				var docStatus = document.getElementsByName("docStatus")[0];
	        	docStatus.value="10";
	        	_validation.removeElements($('#kmArchivesDiv')[0],'required');
	        	_validation.removeValidators('archNotNull');
				Com_Submit(document.kmArchivesDestroyForm, 'save');
			}
			
		    function commitMethod(commitType, saveDraft){

		    	var destroyTemplateId = "";
		    	if ("1" == window.destroyTemplateSize) {
		    		destroyTemplateId = $("input[name=docTemplateId]").val(); 
		    	} else if("n" == window.destroyTemplateSize) {
		    		destroyTemplateId = $("#selectTemplet option:checked").val();
		    	}
		    	
		    	// 销毁流程为空时，不允许提交
		    	if (!destroyTemplateId) {
		    		dialog.alert('<bean:message key="kmArchivesDestroy.tips.noTemplate" bundle="km-archives" />');
		    	} else {
		    		var formObj = document.kmArchivesDestroyForm;
					var docStatus = document.getElementsByName("docStatus")[0];
					if(saveDraft=="true"){
						docStatus.value="10";
					}else{
						docStatus.value="20";
					}
					if('save'==commitType){
						Com_Submit(formObj, commitType,'fdId');
				    }else{
				    	Com_Submit(formObj, commitType); 
				    }
		    	}
		    	
			}
	    	
	        $(document).ready(function() {
				initKmArchType();
			});
		
			function initKmArchType() {
				var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_destroy_template/kmArchivesDestroyTemplate.do?method=getTemplete";
				$.post(url,function(results) {
					var DivObj = $('#selectTemplet');
					if (results == null || results.length == 0) {
						DivObj.hide();
					}
					DivObj.html("");
					var fdMethod_Get = "${kmArchivesDestroyForm.method_GET}";
					//只有一个可用模板时不显示下拉框
					if (results.length == 1) {
						window.destroyTemplateSize = "1";
						DivObj.append('<input type="hidden" name="docTemplateId" value="'+results[0].fdId+'"/>' + results[0].fdName);
					} else if("add" == fdMethod_Get){
						window.destroyTemplateSize = "n";
						var selectObj = '<select name="docTemplateId" onchange="if(!onChangeKmArchTemplate(this.value))return;" style="width:80%">'
						var optionHtml = '';
						for (var i = 0; i < results.length; i++) {
							optionHtml += '<option value="'
									+ results[i].fdId + '"';
							if (results[i].fdId == "${kmArchivesDestroyForm.docTemplateId}") {
								optionHtml += ' selected="selected"';
							}
							optionHtml += '>' + results[i].fdName
									+ '</option>';
						}
						selectObj += optionHtml; 
						selectObj += '</select>';
						DivObj.append(selectObj);
					} else {
						window.destroyTemplateSize = "1";
						DivObj.append('<input type="hidden" name="docTemplateId" value="${kmArchivesDestroyForm.docTemplateId}"/>' + "${kmArchivesDestroyForm.docTemplateName}");
					}
				}, 'json');
			}
		
			function onChangeKmArchTemplate(fdTempId) {
				dialog.confirm('<bean:message key="tips.changeDestroyTemplate" bundle="km-archives" />',function(value){
				    if(!value){
					    //取消时恢复原先值
				    	document.getElementsByName("docTemplateId")[0].value=document.getElementsByName("_currentFdTemplateId")[0].value;
						return;
					 }
			    	if(value==true){
			    		document.kmArchivesDestroyForm.action = Com_SetUrlParameter(
								location.href, "method", "add");
						document.kmArchivesDestroyForm.action = Com_SetUrlParameter(
								document.kmArchivesDestroyForm.action, "type", "change");
						document.kmArchivesDestroyForm.action = Com_SetUrlParameter(
								document.kmArchivesDestroyForm.action, "docTemplateId", fdTempId);
						document.kmArchivesDestroyForm.submit();
			    	}
			    });
			}
	    </script>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesDestroyForm" />
				<c:param name="fdKey" value="kmArchivesDestroy" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>
