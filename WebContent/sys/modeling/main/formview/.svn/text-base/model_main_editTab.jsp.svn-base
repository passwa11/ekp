<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:replace name="content">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/main/resources/css/edit.css?s_cache=${LUI_Cache }"/>
		<c:if test="${param.approveModel ne 'right'}">
			<form name="modelingAppModelMainForm" method="post" action="<c:url value="/sys/modeling/main/modelingAppModelMain.do"/>">
		</c:if>
		<html:hidden property="listviewId" value="${param.listviewId}"/>
		<html:hidden property="fdId" value="${modelingAppModelMainForm.fdId}"/>
		<html:hidden property="docStatus" />
		<html:hidden property="fdModelId" />
		<html:hidden property="fdTreeNodeData"/>
        <html:hidden property="docCreateTime"/>
        <html:hidden property="docCreatorId"/>
		<br/>

		<c:choose>
			<c:when test="${param.approveModel eq 'right'}">
				<ui:tabpage collapsed="true" id="reviewTabPage">
					<ui:event event="layoutDone">
						this.element.find(".lui_tabpage_float_collapse").hide();
						this.element.find(".lui_tabpage_float_navs_mark").hide();
				    </ui:event>
					<c:import url="/sys/modeling/main/formview/model_main_editContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="contentType" value="xform"></c:param>
						<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
				</ui:tabpage>
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-average='false' var-useMaxWidth='true'>
					<%--流程--%>
						<%--<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppModelMainForm" />
							<c:param name="fdKey" value="modelingApp" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="isExpand" value="true" />
							<c:param name="approveType" value="right" />
						</c:import>--%>
					<c:import url="/sys/modeling/main/formview/model_main_editContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="contentType" value="baseInfo"></c:param>
						<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
					<c:import url="/sys/modeling/main/formview/model_main_editContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="contentType" value="other"></c:param>
		 		 		<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
				</ui:tabpanel>
			</c:when>
			<c:otherwise>
				<ui:tabpage expand="false" var-navwidth="90%" >
					<c:import url="/sys/modeling/main/formview/model_main_editContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="contentType" value="xform"></c:param>
						<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
					<c:import url="/sys/modeling/main/formview/model_main_editContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="contentType" value="baseInfo"></c:param>
						<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
					<c:import url="/sys/modeling/main/formview/model_main_editContent.jsp" charEncoding="UTF-8">
		 		 		<c:param name="contentType" value="other"></c:param>
						<c:param name="approveModel" value="${param.approveModel}"></c:param>
		  			</c:import>
				</ui:tabpage>
			</c:otherwise>
		</c:choose>
	<c:if test="${param.approveModel ne 'right'}">
		</form>
	</c:if>
	<html:hidden property="detailOperationAuthConfig" value="${detailOperationAuthConfig}" />
	<script>
		Com_IncludeFile("detailOperationAuth.js", "${LUI_ContextPath}/sys/modeling/main/resources/js/", 'js', true);

		var _reviewValdate = $KMSSValidation(document.modelingAppModelMainForm);
		function submitForm(method,status){
			if(status){
				document.getElementsByName("docStatus")[0].value = status;
			}
			if(method === 'saveDraft'){//新增页面暂存时，移除表单的必填校验
				_reviewValdate.removeElements($('#sysModelingXform')[0],'required');
				//移除附件必填
				_lbpmChangeAttValidate(true);
			}else if(method === 'updateDraft'){//编辑页面暂存时，移除表单的必填校验，并修改方法为更新
				_reviewValdate.removeElements($('#sysModelingXform')[0],'required');
				//移除附件必填
				_lbpmChangeAttValidate(true);
				method = 'update';
			}else if(method === 'save'){//保存时，恢复校验
				_reviewValdate.resetElementsValidate($('#sysModelingXform')[0]);
				//重置附件必填
				_lbpmChangeAttValidate(false);
			}
			Com_Submit(document.modelingAppModelMainForm, method);
		}
		//附件暂存移除
		function _lbpmChangeAttValidate(remove){
			if(window.Attachment_ObjectInfo){
				for(var tmpKey in window.Attachment_ObjectInfo){
					if(window.Attachment_ObjectInfo[tmpKey]){
						if(remove){
							if(!window.Attachment_ObjectInfo[tmpKey]._reqired){
								window.Attachment_ObjectInfo[tmpKey]._reqired = window.Attachment_ObjectInfo[tmpKey].required;
							}
							window.Attachment_ObjectInfo[tmpKey].required = false;
						}else{
							if(window.Attachment_ObjectInfo[tmpKey]._reqired!=null){
								window.Attachment_ObjectInfo[tmpKey].required = window.Attachment_ObjectInfo[tmpKey]._reqired;
								delete window.Attachment_ObjectInfo[tmpKey]._reqired;
							}
						}
					}
				}
			}
		}
		$(function(){
			$("#lui_validate_message").css("display","none");
			initDetailOperationAuth($("input[name='detailOperationAuthConfig']").val(), true, "${param.method}", "${nodeId}");
		})
	</script>
</template:replace>

<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:when>
	<c:otherwise>

	</c:otherwise>
</c:choose>