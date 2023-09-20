<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<kmss:ifModuleExist path="/km/signature/">
	<kmss:authShow roles="ROLE_SIGNATURE_DEFAULT">
		<div id="signatureHandlerView" data-dojo-type="sys/lbpmservice/mobile/signature/SignatrueHandlerView" style="display:none"
			data-dojo-props='fdKey:"${param.auditNoteFdId}",
					fdAttType:"pic",
					fdModelId:"${param.modelId}",
					fdModelName:"${param.modelName}",
					lbpmViewName:"${param.lbpmViewName}",
					buttonDiv:"#commonUsagesDiv",
					descriptionDiv: "#descriptionDiv"'>
		</div>
	</kmss:authShow>
</kmss:ifModuleExist>
