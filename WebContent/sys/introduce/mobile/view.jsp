<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/sys/introduce/mobile/css/view.css" />
	</template:replace>
	<template:replace name="title">
		${sysIntroduceMainForm.docSubject}
	</template:replace>
	<template:replace name="content">
		<div data-dojo-type="mui/view/DocScrollableView" id="scrollView">
        <input type="hidden" id="_docSubStr" value="<c:out value='${sysIntroduceMainForm.docSubject}'/>"/>
        <input type="hidden" id="_introStr" value="<c:out value='${fn:escapeXml(sysIntroduceMainForm.fdIntroduceReason)}'/>"/>
            <script type="text/javascript">
               window._docSubStr = document.getElementById("_docSubStr").value;
               window._introStr = document.getElementById("_introStr").value;
            </script>
			<div data-dojo-type="${LUI_ContextPath}/sys/introduce/mobile/js/IntroduceViewContent.js"
				 data-dojo-props="url:'${LUI_ContextPath}${sysIntroduceMainForm.fdLinkUrl}',
				 				  docSubject:window._docSubStr,
				 				  fdIntroduceReason:window._introStr">
			</div>
			
			
			<!-- 流程日志 -->
			<div data-dojo-type="mui/panel/AccordionPanel">
				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'${lfn:message('kms-multidoc:kmsMultidoc.4m.lbpmlog') }',icon:'mui-ul'">
					<c:import
						url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp"
						charEncoding="UTF-8">
						<c:param name="fdModelId"
							value="${sysIntroduceMainForm.fdId }"></c:param>
						<c:param name="fdModelName"
							value="com.landray.kmss.sys.introduce.model.SysIntroduceMain"></c:param>
						<c:param name="formBeanName" value="sysIntroduceMainForm"></c:param>
					</c:import>
				</div>
			</div>
			
			<!-- 流程审批 -->
			<c:if test="${ sysIntroduceMainForm.docStatus<'30'}">
				<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp"
					allowReview="true" editUrl="javascript:building();"
					viewName="lbpmView" formName="sysIntroduceMainForm">
				</template:include>
			</c:if>
			
		</div>
		
		<c:if test="${ sysIntroduceMainForm.docStatus<'30'}">
			<c:import url="/sys/lbpmservice/mobile/import/view.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="sysIntroduceMainForm" />
				<c:param name="fdKey" value="mainDoc" />
				<c:param name="viewName" value="lbpmView" />
				<c:param name="backTo" value="scrollView" />
			</c:import>
		</c:if> 
	
	</template:replace>
</template:include>
