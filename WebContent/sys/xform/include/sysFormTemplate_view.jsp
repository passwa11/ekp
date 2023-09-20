<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>

<c:set var="sysFormTemplateForm" value="${requestScope[param.formName]}" />
<c:set var="xFormTemplateForm" value="${sysFormTemplateForm.sysFormTemplateForms[param.fdKey]}" />
<c:set var="sysFormTemplateFormPrefix" value="sysFormTemplateForms.${param.fdKey}." />
<%--
ApplicationContext _ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
ISysFormCommonTemplateService commonTemplateService = (ISysFormCommonTemplateService)_ctx.getBean("sysFormCommonTemplateService");
commonTemplateService.setTemplateForm(new RequestContext(request));
--%>
<c:if test="${xFormTemplateForm.fdMode != 1}">
<c:if test="${param.useLabel != 'false'}">
<tr LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-xform:sysForm.tab.label'}" />" style="display:none">
	<td>
</c:if>
		<%-- 表单映射按钮 --%>
		<c:import url="/sys/xform/include/sysFormMappingBtn.jsp" charEncoding="UTF-8">
			<c:param name="fdModelId" value="${sysFormTemplateForm.fdId}" />
			<c:param name="fdModelName" value="${param.fdMainModelName}" />
			<c:param name="fdTemplateModel" value="${sysFormTemplateForm.modelClass.name}" />
			<c:param name="fdKey" value="${param.fdKey}" />
			<c:param name="fdTemplateId" value="${xFormTemplateForm.fdId}" />
			<c:param name="fdFormType" value="template" />
			<c:param name="fdModeType" value="${xFormTemplateForm.fdMode}" />
		</c:import>
		<div id="DIV_SubForm_View" style="width:12.7%;height:100%;float:left;display:none;border:1px #d2d2d2 solid;">
			<div style="height:32px;border-bottom:1px solid #d2d2d2;">
				<table class="subTable" style="width:100%">
					<tr>
						<td style="width: 38%;padding:8px">
							<div style="font-size:12px;font-weight:bold;"><bean:message bundle="sys-xform" key="sysSubFormTemplate.multiform_configuration" /></div>
						</td>
					</tr>
				</table>
			</div>
			<div id="SubFormDiv" style="overflow-x:hidden;overflow-y:auto;text-align: center;"> 
				<%@include file="/sys/xform/base/sysSubFormTemplate_view.jsp"%>
			</div>
		</div>
		<table class="tb_normal" width="100%" id="TB_FormTemplate_View">
			<tr>
				<td width="15%" class="td_normal_title" valign="top">
					<bean:message bundle="sys-xform" key="sysFormTemplate.fdMode" />
				</td>
				<td width="85%">
					<div class="xFormTemplateFdMode">
						<sunbor:enumsShow value="${xFormTemplateForm.fdMode}" enumsType="sysFormTemplate_fdMode" />
					</div>
				</td>
			</tr>
			<c:if test="${xFormTemplateForm.fdMode == 2}">
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="sys-xform" key="sysFormTemplate.fdCommonName"/>
				</td>
				<td>
					<c:out value="${xFormTemplateForm.fdCommonTemplateName}" />
					<a href="<c:url value="/sys/xform/sys_form_common_template/sysFormCommonTemplate.do"/>?method=view&fdId=${xFormTemplateForm.fdCommonTemplateId}&fdModelName=${xFormTemplateForm.fdModelName}&fdKey=${xFormTemplateForm.fdKey}&fdMainModelName=${param.fdMainModelName}" id="Frorm_OtherTemplate_${HtmlParam.fdKey}_view" style="padding-left:10px;display:inline" target="_blank">
						<bean:message bundle="sys-xform" key="XFormTemplate.viewTemplate" />
					</a>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<iframe width="100%" height="500" FRAMEBORDER=0 id="${sysFormTemplateFormPrefix}fdCommonTemplateView"
					src="<c:url value="/sys/xform/designer/designPreview.jsp"/>"></iframe>
					<script>
					Com_IncludeFile("data.js");
					function Form_ShowCommonTemplatePreview() {
						var data = new KMSSData();
						data.AddBeanData('sysFormCommonTemplateTreeService&fdId=${xFormTemplateForm.fdCommonTemplateId}');
						var html = data.GetHashMapArray()[0]['fdDesignerHtml'];
						var iframe = document.getElementById('${sysFormTemplateFormPrefix}fdCommonTemplateView');
						iframe.contentWindow.document.body.innerHTML = html;
					}
					Com_AddEventListener(window, "load", Form_ShowCommonTemplatePreview);
					</script>
				</td>
			</tr>
			</c:if>
			<c:if test="${xFormTemplateForm.fdMode == 3||xFormTemplateForm.fdMode == 4}">
				<%@ include file="/sys/xform/base/sysFormTemplateDisplay_view.jsp"%>
			</c:if>
			<%@ include file="/sys/xform/base/sys_form_template_history/sysFormTemplateHistoryDisplay_view.jsp"%>
		</table>
<c:if test="${param.useLabel != 'false'}">
	</td>
</tr>
</c:if>
</c:if>
<script>
Com_AddEventListener(window, "load", function(){
	// 表单引用方式 选项初始化
	Form_Template_View_OptionInit("${xFormTemplateForm.fdMode}");
})

function Form_Template_View_OptionInit(fdMode){
	// 先判断是否有需要变更的传入参数
	var addOptionType = "${JsParam.addOptionType}";
	if(addOptionType){
		var optionType = addOptionType;
		var options = optionType.split(";");
		for(var i = 0;i < options.length;i++){
			var option = options[i];
			var val, text;
			if(option.indexOf("|") > -1){
				var array = option.split("|");
				text = Data_GetResourceString(array[0]);
				if(!text){
					text = array[0];
				}
				val = array[1];
			}else{
				text = option;
				val = option;
			}
			if(val == ''){
				continue;
			}
			if(val == fdMode){
				$(".xFormTemplateFdMode").html(text);
				break;
			}
		}
	}
}
</script>