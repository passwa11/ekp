<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="sysPrintTemplateForm" value="${templateForm.sysPrintTemplateForm}" />
<c:set var="sysPrintPrefix" value="sysPrintTemplateForm.${param.fdKey}." />
<c:set var="_isHide" value="${param._isHide }"/>
<c:set var='_isHidePrintDesc' value='${param._isHidePrintDesc }'/>
<c:set var='_isHideDefaultSetting' value='${param._isHideDefaultSetting }'/>
<c:set var='_isShowName' value='${param._isShowName }'/>
<c:set var='_isHidePrintMode' value='${param._isHidePrintMode }'></c:set>
<c:set var='_isXForm' value='${param._isXForm }'/>
<c:set var='_isModeling' value='${param._isModeling }'/>
<c:set var='_printKey' value='${param.fdKey }'/>
<script>
	var printKey = '${param.fdKey}';
	var enableFlow = '${param.enableFlow}';
    var isModeling = '${param._isModeling}';
	Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|xform.js|security.js");
	Com_IncludeFile("designer.css","${KMSS_Parameter_ContextPath}sys/print/designer/style/",'css',true)
	Com_IncludeFile("document.css","${KMSS_Parameter_ContextPath}resource/style/default/doc/",'css',true)
	Com_IncludeFile("print_template.css","${KMSS_Parameter_ContextPath}sys/print/resource/css/",'css',true)
	Com_IncludeFile("printExtend.js","${KMSS_Parameter_ContextPath}sys/print/resource/js/",'js',true)
	<%@ include file="/sys/print/include/lang.jsp" %>
	<%@ include file="/sys/print/include/multi_template/lang.jsp"%>
</script>
<!-- 表单内容隐藏域，给js提供数据 -->
<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdId" id="_xFormTemplateId" value="${templateForm.sysFormTemplateForms[param.fdKey].fdId}">
<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdMode" value="${templateForm.sysFormTemplateForms[param.fdKey].fdMode}">
<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdDesignerHtml" value='<c:out value="${templateForm.sysFormTemplateForms[param.fdKey].fdDesignerHtml }"></c:out>'>
<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdMetadataXml" value='<c:out value="${templateForm.sysFormTemplateForms[param.fdKey].fdMetadataXml }"></c:out>'>
<!-- 内置多表单的数据 -->
<table class="tb_normal subTable"  width=100% id="TABLE_DocList_SubForm" align="center" style="table-layout:fixed;display: none" frame=void>
<c:forEach items="${templateForm.sysFormTemplateForms[param.fdKey].fdSubForms}" var="item" varStatus="vstatus">
	<tr id="${item.fdId}" defaultWebForm="<c:out value="${item.fdIsDefWebForm}" />">
		<td>
			<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdSubForms[${vstatus.index}].fdId" value="${item.fdId}" /> 
			<xform:text property="sysFormTemplateForms.${param.fdKey }.fdSubForms[${vstatus.index}].fdName" value="${item.fdName}" style="width:90%;display:none"/>
			<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdSubForms[${vstatus.index}].fdDesignerHtml" value="<c:out value='${item.fdDesignerHtml}'/>"/>
			<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdSubForms[${vstatus.index}].fdMetadataXml" value="<c:out value='${item.fdMetadataXml}'/>"/>
			<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdSubForms[${vstatus.index}].fdCss" value="<c:out value='${item.fdCss}'/>"/>
			<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdSubForms[${vstatus.index}].fdCssDesigner" value="<c:out value='${item.fdCssDesigner}'/>"/>
			<input type="hidden" name="sysFormTemplateForms.${param.fdKey }.fdSubForms[${vstatus.index}].fdIsDefWebForm" value="<c:out value='${item.fdIsDefWebForm}'/>"/>
			
			<!-- 打印预存 -->
			<input type="hidden" name="fdPrintForms[${vstatus.index}].fdPrintDesignerHtml" value="<c:out value='${item.fdDesignerHtml}'/>"/>
			<input type="hidden" name="fdPrintForms[${vstatus.index}].fdPrintMetadataXml" value="<c:out value='${item.fdMetadataXml}'/>"/>
		</td>
	</tr>
</c:forEach>
</table>
<!-- 打印机制dom获取或者解析的容器，一般执行完就清空 -->
<div id='printContainer' style="display: none"></div>
<%@ include file="/sys/print/include/sysPrintTemplate_edit_script.jsp"%>
<%@ include file="/sys/print/include/sysPrintTemplate_edit_content.jsp"%>
