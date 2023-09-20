<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<bean:message 
			bundle="kms-common" 
			key="kmsCommonDocErrorCorrectionFlow.info"/> - ${kmsCommonDocErrorCorrectionForm.docSubject}
	</template:replace>
	<template:replace name="head">
		<script>
			//打开文档
			function openMainWindow(){
				Com_OpenWindow('<c:url value="${url}" />');
			}
		</script>
		<style type="text/css">
			.docErrorReasonTd ul {
				padding-inline-start: 40px;
			}
			.docErrorReasonTd ol {
				padding-inline-start: 40px;
			}
		</style>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
		</ui:toolbar>
	</template:replace>
	<template:replace name="content"> 
		<div class="lui_form_content">
			<div class='lui_form_content_frame' style="margin-top: 20px;padding-top:15px;">
				<div class="lui_form_title_frame">
					<div class="lui_form_subject">
						<bean:message 
							bundle="kms-common" 
							key="kmsCommonDocErrorCorrectionFlow.info"/> -  ${kmsCommonDocErrorCorrectionForm.docSubject}
					</div>
				</div>
				<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="kms-common" 
								key="kmsCommonDocErrorCorrection.docSubject"/>
							</td>
							<td width="85%" colspan="3">
								<a style="text-decoration: underline;"  href="javascript:;"
									onclick="openMainWindow()">
									 ${kmsCommonDocErrorCorrectionForm.docSubject}
								</a>
							</td>
						</tr>
					
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.fdCorrectionOpinions"/>
							</td><td width="85%" colspan="3">
								<xform:textarea property="docDescription" style="width:85%" showStatus="view" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="kms-common" key="kmsCommonDocErrorCorrection.fdCorrectionOpinions.error.reason"/>
							</td><td width="85%" colspan="3" class="docErrorReasonTd">
								${ kmsCommonDocErrorCorrectionForm.docErrorReason}
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
			</div>
		</div>
		<ui:tabpage expand="false">
			<%--流程机制 --%>
			<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmsCommonDocErrorCorrectionForm" />
				<c:param name="fdKey" value="knowledgeErrorCorrectionFlow" />
			</c:import>
		</ui:tabpage>
	</template:replace>
</template:include>