<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/kms/common/mobile/css/view.css" />
	</template:replace>
	<template:replace name="title">
		${kmsCommonDocErrorCorrectionForm.docSubject}
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" id="scrollView">
			
			<div data-dojo-type="${LUI_ContextPath}/kms/common/mobile/js/ErrorCorrectionViewContent.js"
				 data-dojo-props="fdModelId:'${kmsCommonDocErrorCorrectionForm.fdModelId}',
				 				  fdModelName:'${kmsCommonDocErrorCorrectionForm.fdModelName}',
				 				  docSubject:'${kmsCommonDocErrorCorrectionForm.docSubject}',
				 				  docDescription:'${kmsCommonDocErrorCorrectionForm.docDescription}'">
			</div>
			
			<div class="muiErrorCorrectionDetail">
				<div class="muiErrorCorrectionDetailTitle">${lfn:message('kms-common:kmsCommonDocErrorCorrection.mobile.detail') }:</div>
				<div class="muiErrorCorrectionDetailMsg">
					<c:if test="${ empty kmsCommonDocErrorCorrectionForm.docErrorReason }">
						${lfn:message('kms-common:kmsCommonDocErrorCorrection.mobile.nodetail') }
					</c:if>
					<c:if test="${ not empty kmsCommonDocErrorCorrectionForm.docErrorReason }">
						<xform:rtf property="docErrorReason" mobile="true"></xform:rtf>		
					</c:if>	
				</div>
			</div>
			
			
			<!-- 流程日志 -->
			<div data-dojo-type="mui/panel/AccordionPanel">
				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidoc.4m.lbpmlog') }',icon:'mui-ul'">
					<c:import
						url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="fdModelId"
							value="${kmsCommonDocErrorCorrectionForm.fdId }"></c:param>
						<c:param name="fdModelName"
							value="com.landray.kmss.kms.common.model.KmsCommonDocErrorCorrection"></c:param>
						<c:param name="formBeanName" value="kmsCommonDocErrorCorrectionForm"></c:param>
					</c:import>
				</div>
			</div>
			
			<!-- 流程审批 -->
			<c:if test="${ kmsCommonDocErrorCorrectionForm.docStatus<'30'}">
				<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp"
					allowReview="true" editUrl="javascript:building();"
					viewName="lbpmView" formName="kmsCommonDocErrorCorrectionForm">
				</template:include>
			</c:if>
			
		</div>
		
		<c:if test="${ kmsCommonDocErrorCorrectionForm.docStatus<'30'}">
			<c:import url="/sys/lbpmservice/mobile/import/view.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="kmsCommonDocErrorCorrectionForm" />
				<c:param name="fdKey" value="mainDoc" />
				<c:param name="viewName" value="lbpmView" />
				<c:param name="backTo" value="scrollView" />
			</c:import>
		</c:if> 
	
	</template:replace>
</template:include>
