<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysFormTemplateForm" value="${requestScope[param.formName]}" />
<c:set var="xFormTemplateForm" value="${sysFormTemplateForm.sysFormTemplateForms[param.fdKey]}" />
<c:set var="sysFormTemplateFormPrefix" value="sysFormTemplateForms.${param.fdKey}." />
<c:set var="entityName" value="${param.fdMainModelName}_${sysFormTemplateForm.fdId}" />
<style>
.free_flow_nodeAttribute_tip {
    height: 4rem;
    line-height: 4rem;
    font-size: 1.5rem;
    padding-left: 1.6rem;
    color: #adafba
}
.preview {
	display:block;
	width:100%;
	min-height: 40rem;
}
</style>
<div class="free_flow_nodeAttribute_tip">
	<div class="muiFormTemplatePrompt"><bean:message bundle="sys-xform-base" key="mui.xform.mobile.edit.tip" /></div>
	<c:if test="${not empty previewUrl}">
		<div class="muiFormPreView fontmuis muis-preview-label">
			<span class="previewText">
				<bean:message bundle="sys-xform-base" key="mui.xform.mobile.edit.prevewMess" />
			</span>
			<img src="${previewUrl}"  class="preview"/>
		</div>
	</c:if>
</div>

<html:hidden property="${sysFormTemplateFormPrefix}fdDesignerHtml" />
<html:hidden property="${sysFormTemplateFormPrefix}fdMetadataXml" />
<html:hidden property="${sysFormTemplateFormPrefix}fdIsChanged"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdSaveAsNewEdition" />
<html:hidden property="${sysFormTemplateFormPrefix}fdIsUpTab" />
<html:hidden property="${sysFormTemplateFormPrefix}fdCss"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdCssDesigner"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdName"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdId"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdIsDefWebForm" value="false"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdFragmentSetIds"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdMainModelName"/>
<html:hidden property="${sysFormTemplateFormPrefix}fdMode"/>

<div id='__xform_${param.fdKey}' class="muiXformArea">
</div>

<script type="text/javascript">
require(["mui/util", "dojo/ready","dojo/query"], function(util, ready, query) {
	ready(function() {
		var fdDesignerHtmlObj = query("[name='${sysFormTemplateFormPrefix}fdDesignerHtml']")[0];
		var fdDesignerHtml = fdDesignerHtmlObj.value;
		if(fdDesignerHtml && fdDesignerHtml.indexOf("\u4645\u5810\u4d40") < 0){ 
			var encodeHtml = util.base64Encode(fdDesignerHtml,true);
			fdDesignerHtmlObj.value = encodeHtml;
		}
	});
});
</script>

