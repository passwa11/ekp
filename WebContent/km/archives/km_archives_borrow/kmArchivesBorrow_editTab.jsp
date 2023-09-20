<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content">
	<!-- 软删除配置 -->
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmArchivesBorrowForm"></c:param>
	</c:import>
	<div class='lui_form_title_frame'>
		<div class='lui_form_subject'>
			${lfn:message('km-archives:table.kmArchivesBorrow')}</div>
		<div class='lui_form_baseinfo'></div>
	</div>
	<c:if test="${param.approveModel ne 'right'}">
		<form name="kmArchivesBorrowForm" method="post" action="${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchivesBorrow.do">
	</c:if>
	<html:hidden property="fdId" />
	<html:hidden property="docStatus" />
	<html:hidden property="method_GET" />
	 <html:hidden property="_currentFdTemplateId" value = "${kmArchivesBorrowForm.docTemplateId}" />
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform" />
  			</c:import>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
	  			<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>	
			<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editContent.jsp" charEncoding="UTF-8">
 		 		<c:param name="contentType" value="xform" />
  			</c:import>
			<ui:tabpage expand="false" var-navwidth="90%">
				<c:import url="/km/archives/km_archives_borrow/kmArchivesBorrow_editContent.jsp" charEncoding="UTF-8">
	 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
	  			</c:import>
			</ui:tabpage>
			</form>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript">
		$(document).ready(function() {
			initKmArchType();
		});
	
		function initKmArchType() {
			var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_template/kmArchivesTemplate.do?method=getTemplete&fdMainId=${param.fdMainId}";
			$.post(url,function(results) {
				var DivObj = $('#selectTemplet');
				if (results == null || results.length == 0) {
					DivObj.hide();
				}
				DivObj.html("");
				var fdMethod_Get = "${kmArchivesBorrowForm.method_GET}";
				//只有一个可用模板时不显示下拉框
				if (results.length == 1) {
					// broTemplateSize 用来存储可用模板数量 
					window.broTemplateSize = '1'; 
					DivObj.append('<input type="hidden" name="docTemplateId" value="'+results[0].fdId+'"/>' + results[0].fdName);
				} else if ("add" == fdMethod_Get) {
					
					// broTemplateSize 用来存储可用模板数量 
					window.broTemplateSize = 'n';
					var selectObj = '<select name="docTemplateId" onchange="if(!onChangeKmArchTemplate(this.value))return;" style="width:80%">'
					var optionHtml = '';
					for (var i = 0; i < results.length; i++) {
						optionHtml += '<option value="' + results[i].fdId + '"';
						if (results[i].fdId == "${kmArchivesBorrowForm.docTemplateId}") {
							optionHtml += ' selected="selected"';
						}
						optionHtml += '>' + results[i].fdName
								+ '</option>';
					}
					selectObj += optionHtml;
					selectObj += '</select>';
					DivObj.append(selectObj);
					
				} else {
					DivObj.append('<input type="hidden" name="docTemplateId" value="${kmArchivesBorrowForm.docTemplateId}"/>' + "${kmArchivesBorrowForm.docTemplateName}");
				}
			}, 'json');
		}
	
		function onChangeKmArchTemplate(fdTempId) {
			//location.href = '${LUI_ContextPath}/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=add' 
			//		+ '&fdMainId=${param.fdMainId}&docTemplateId=' + fdTempId
	
			 dialog.confirm('<bean:message key="tips.changeBorrowTemplate" bundle="km-archives" />',function(value){
			    if(!value){
				    //取消时恢复原先值
			    	document.getElementsByName("docTemplateId")[0].value=document.getElementsByName("_currentFdTemplateId")[0].value;
					return;
				 }
		    	if(value==true){
		    		document.kmArchivesBorrowForm.action = Com_SetUrlParameter(
							location.href, "method", "add");
					document.kmArchivesBorrowForm.action = Com_SetUrlParameter(
							document.kmArchivesBorrowForm.action, "type", "change");
					document.kmArchivesBorrowForm.action = Com_SetUrlParameter(
							document.kmArchivesBorrowForm.action, "docTemplateId", fdTempId);
					document.kmArchivesBorrowForm.submit();
		    	}
		    });
		}
		var _validation = $KMSSValidation();
		//校验用品不为空
		_validation.addValidator("archNotNull",
				"${lfn:message('km-archives:kmArchivesBorrow.archNotNull')}",
				function(v, e, o) {
					if ($("#TABLE_DocList tr:not(:first)").length > 0) {
						return true;
					} else {
						return false;
					}
				});
		_validation.addValidator(
			'returnDateValidator(validityDate)',
			"${lfn:message('km-archives:kmArchivesBorrow.returnDateValidate')}",
			function(v, e, o) {
				var validityDateStr = o['validityDate'];
				if (validityDateStr != '') {
					var validityDate = Com_GetDate(validityDateStr);
					var returnDate = Com_GetDate(v);
					if (returnDate.getTime() > validityDate.getTime()) {
						return false;
					}
				}
				return true;
			});
	</script>
	
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmArchivesBorrowForm" />
				<c:param name="fdKey" value="kmArchivesBorrow" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>