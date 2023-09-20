<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no">
	<template:replace name="title">
		<bean:message bundle="kms-common" key="table.kmsCommonDocErrorCorrectionFlow"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar layout="sys.ui.toolbar.float">
			<ui:button onclick="commitMethod ('save');" text="${lfn:message('button.submit')}">
			</ui:button>
			<ui:button onclick="Com_CloseWindow();" text="${lfn:message('button.close')}">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form action="/kms/common/kms_common_doc_error_correction/kmsCommonDocErrorCorrection.do">
		<p class="txttitle">
			<bean:message bundle="kms-common" key="table.kmsCommonDocErrorCorrectionFlow"/>
		</p>
		<div class="lui_form_content_frame" >
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.docSubject"/>
					</td><td width="85%" colspan="3">
					<div style="cursor:hand" onclick="openMainWindow()"><U> <c:out value="${ kmsCommonDocErrorCorrectionForm.docSubject}" /> </U></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.dept"/>
					</td><td width="85%" colspan="3">
						${kmsCommonDocErrorCorrectionForm.docDeptName }
					</td>
				</tr>	
				<tr style="border-bottom: 0px;">
					<td class="td_normal_title" width=15%  colspan="4">
						<div style="line-height: 30px;">
							<img alt="请描述纠错原因" src="../resource/img/warning.png" /><SPAN style="padding-bottom: 12px;height:30px"><bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.fdCorrectionOpinions.reason"/></SPAN><br/>
						</div>
						<xform:textarea property="docDescription" style="width:98%" showStatus="edit" required="true" validators="maxLength(1500)"/> 
						<br/>
					</td>
				</tr>
				<tr style="border-top: 0px;">
					<td class="td_normal_title" colspan="4">
						<br/>
						<a href="javascript:void(0);" onclick="openDocErrorReason();" style="color:blue;"><bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.fdCorrectionOpinions.error.reason"/></a>
						<br/><br/>
						<div id="docErrorReasonSpan" style="display:none;">
							<kmss:editor property="docErrorReason" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.docCreator"/>
					</td><td width="35%">
						${kmsCommonDocErrorCorrectionForm.docCreatorName}
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.docCreateTime"/>
					</td><td width="35%">
						${kmsCommonDocErrorCorrectionForm.docCreateTime }
					</td>
				</tr>
			</table>
			<div >
				
			</div>
		</div>
		<ui:tabpage expand="false">
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsCommonDocErrorCorrectionForm" />
				<c:param name="fdKey" value="knowledgeErrorCorrectionFlow" />
			</c:import>
		</ui:tabpage>
		<%-- 以下代码为嵌入流程标签的代码
		<c:import url="/sys/workflow/include/sysWfProcess_edit.jsp"
			charEncoding="UTF-8"> 
			<c:param name="formName" value="kmsKnowledgeErrorCorrectionForm" />
			<c:param name="fdKey" value="knowledgeErrorCorrectionFlow" />
		</c:import> --%>
		<%-- 以上代码为嵌入流程标签的代码 --%>
		<html:hidden property="fdId" /> 
		<html:hidden property="fdModelId" />
		<html:hidden property="docSubject"/> 
		<html:hidden property="docStatus" />
		<html:hidden property="docCreateTime" />
		<html:hidden property="docCreatorId" />
		<html:hidden property="docCreatorName" />
		<html:hidden property="fdType"/>
		<html:hidden property="method_GET" />
		<script>
			Com_IncludeFile("calendar.js");
			Com_IncludeFile("dialog.js");
			$KMSSValidation();
			// 提交表单
			function commitMethod(method, saveDraft){
				var docStatus = document.getElementsByName("docStatus")[0];
				if (saveDraft != null && saveDraft == 'true'){
					docStatus.value = "10";
				} else {
					docStatus.value = "20";
				}
				Com_Submit(document.kmsCommonDocErrorCorrectionForm, method);
			}
			//打开文档
			function openMainWindow(){
				Com_OpenWindow("<c:url value='${url}'/>");
			}
			function openDocErrorReason(){
				var docErrorReasonSpan = document.getElementById("docErrorReasonSpan") ;
				if(docErrorReasonSpan.style.display == 'none') {
					docErrorReasonSpan.style.display = "block";
				}else {
					docErrorReasonSpan.style.display = "none";
				}
			}
		</script>
		</html:form>
	</template:replace>
</template:include>