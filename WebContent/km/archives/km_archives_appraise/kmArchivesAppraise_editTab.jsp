<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="content"> 
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmArchivesAppraiseForm" method="post" action ="${KMSS_Parameter_ContextPath}km/archives/km_archives_appraise/kmArchivesAppraise.do">
	</c:if>
	<div class='lui_form_title_frame'>
        <div class='lui_form_subject'>
            ${lfn:message('km-archives:table.kmArchivesAppraise')}
        </div>
    </div>
	<html:hidden property="fdId" />
    <html:hidden property="docStatus" />
    <html:hidden property="method_GET" />
    <html:hidden property="_currentFdTemplateId" value = "${kmArchivesAppraiseForm.docTemplateId}" />
    <c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_editContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform"></c:param>
  			</c:import>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmArchivesAppraiseForm" />
					<c:param name="fdKey" value="kmArchivesAppraise" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" var-navwidth="90%" >
				<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="xform"></c:param>
	  			</c:import>
				<c:import url="/km/archives/km_archives_appraise/kmArchivesAppraise_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="contentType" value="other"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	<script>
	   	var _validation = $KMSSValidation(document.kmArchivesAppraiseForm);
	   	_validation.addValidator("archNotNull","${lfn:message('km-archives:kmArchivesBorrow.archNotNull')}",function(v, e, o) {
			if($("#TABLE_DocList tr:not(:first)").length>0){
		         return true;
			}else{
				 return false;
			}
		});
    	_validation.addValidator('noEarlierthanBefore(beforeDate)',"${lfn:message('km-archives:validate.appraiseDate')}",function(v,e,o) {
    		if(o['beforeDate']) {
    			var after = Com_GetDate(v);
    			var before = Com_GetDate(o['beforeDate']);
    			return after.getTime() > before.getTime();
    		}
    	});
    	
    	function _updateDraft(){
	  		var docStatus = document.getElementsByName("docStatus")[0];
        	docStatus.value="10";
			Com_Submit(document.kmArchivesAppraiseForm, 'update');
		}
	  	
		function _saveDraft(){
			var docStatus = document.getElementsByName("docStatus")[0];
        	docStatus.value="10";
        	_validation.removeElements($('#kmArchivesDiv')[0],'required');
        	_validation.removeValidators('archNotNull');
			Com_Submit(document.kmArchivesAppraiseForm, 'save');
		}
    	
    	function commitMethod(commitType, saveDraft){
    		
    		var appraiseTemplateId = "";
    		if ("1" == window.appraiseTemplateSize) {
    			appraiseTemplateId = $("input[name=docTemplateId]").val(); 
    		} else if ("n" == window.appraiseTemplateSize) {
    			appraiseTemplateId = $("#selectTemplet option:checked").val();
    		}
    		
    		// 鉴定流程模板为空时，不能提交
    		if (!appraiseTemplateId) {
    			dialog.alert('<bean:message key="kmArchivesAppraise.tips.noTemplate" bundle="km-archives" />')
    		} else {
    			var formObj = document.kmArchivesAppraiseForm;
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
			var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_appraise_template/kmArchivesAppraiseTemplate.do?method=getTemplete";
			$.post(url,function(results) {
				var DivObj = $('#selectTemplet');
				if (results == null || results.length == 0) {
					DivObj.hide();
				}
				DivObj.html("");
				var fdMethod_Get = "${kmArchivesAppraiseForm.method_GET}";
				//只有一个可用模板时不显示下拉框
				if (results.length == 1) {
					window.appraiseTemplateSize = "1";
					DivObj.append('<input type="hidden" name="docTemplateId" value="'+results[0].fdId+'"/>' + results[0].fdName);
				} else if("add" == fdMethod_Get){
					window.appraiseTemplateSize = "n";
					var selectObj = '<select name="docTemplateId" onchange="if(!onChangeKmArchTemplate(this.value))return;" style="width:80%">'
					var optionHtml = '';
					for (var i = 0; i < results.length; i++) {
						optionHtml += '<option value="'
								+ results[i].fdId + '"';
						if (results[i].fdId == "${kmArchivesAppraiseForm.docTemplateId}") {
							optionHtml += ' selected="selected"';
						}
						optionHtml += '>' + results[i].fdName 
								+ '</option>';
					}
					selectObj += optionHtml;
					selectObj += '</select>';
					DivObj.append(selectObj);
				}else {
					window.appraiseTemplateSize = "1";
					DivObj.append('<input type="hidden" name="docTemplateId" value="${kmArchivesAppraiseForm.docTemplateId}"/>' + "${kmArchivesAppraiseForm.docTemplateName}");
				}
			}, 'json');
		}
	
		function onChangeKmArchTemplate(fdTempId) {
			
			 dialog.confirm('<bean:message key="tips.changeAppraiseTemplate" bundle="km-archives" />',function(value){
			    if(!value){
				    //取消时恢复原先值
			    	document.getElementsByName("docTemplateId")[0].value=document.getElementsByName("_currentFdTemplateId")[0].value;
					return;
				 }
		    	if(value==true){
		    		document.kmArchivesAppraiseForm.action = Com_SetUrlParameter(
							location.href, "method", "add");
					document.kmArchivesAppraiseForm.action = Com_SetUrlParameter(
							document.kmArchivesAppraiseForm.action, "type", "change");
					document.kmArchivesAppraiseForm.action = Com_SetUrlParameter(
							document.kmArchivesAppraiseForm.action, "docTemplateId", fdTempId);
					document.kmArchivesAppraiseForm.submit();
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
				<c:param name="formName" value="kmArchivesAppraiseForm" />
				<c:param name="fdKey" value="kmArchivesAppraise" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>
