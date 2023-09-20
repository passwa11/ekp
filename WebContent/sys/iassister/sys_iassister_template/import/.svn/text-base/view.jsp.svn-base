<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	
%>
<kmss:authShow roles="ROLE_SYSIASSISTER_SETTING">
	<c:set var="templateForm" value="${requestScope[param.formName]}" />
	<c:set var="lblMsgKey" value="sys-iassister:msg.template.label"></c:set>
	<c:if test="${not empty param.messageKey }">
		<c:set var="lblMsgKey" value="${param.messageKey }"></c:set>
	</c:if>
	<script
		src="${LUI_ContextPath }/sys/iassister/resource/js/vue.min.js?s_cache=${LUI_Cache}"></script>
	<script
		src="${LUI_ContextPath }/sys/iassister/resource/js/element.js?s_cache=${LUI_Cache}"></script>
	<link rel="stylesheet" type="text/css"
		href="${ LUI_ContextPath}/sys/iassister/resource/css/element.css?s_cache=${LUI_Cache}" />
	<script type="text/javascript">
		Com_IncludeFile("formula.js");
		var frontEnd = null;
		var langHere = null;
		var switchToIassister = null;
		function luiReady() {
			var jsPath = 'sys/iassister/sys_iassister_template/js/view.js';
			seajs.use([ jsPath ], function(front) {
				front.init({
					ctxPath : "${LUI_ContextPath}",
					fdKey : "${param.fdKey}",
					checkTemplates : "${templateForm.checkTemplates}",
					checkGroups : "${templateForm.checkGroups}",
					templateId : "${templateForm.fdId}",
					templateName : "${param.templateName}",
					modelName : "${param.modelName}"
				});
				frontEnd = front;
				langHere = front.lang;
				switchToIassister = front.switchToIassister;
				front.initContent();
			})
		}
		LUI.ready(luiReady);
	</script>
	<html:hidden property="checkTemplates" />
	<html:hidden property="checkGroups" />
	<tr id="IA_${param.fdKey}" style="display: none"
		LKS_LabelId="IA_${param.fdKey}"
		LKS_LabelName="${lfn:message(lblMsgKey) }"
		LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
			<div id="checkTemplateWrapper" class="check_template_wrapper">
				<check-templates :templates="checkTemplates" :groups="checkGroups" />
			</div>
		</td>
	</tr>
</kmss:authShow>