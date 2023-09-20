<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="xFormTemplateForm" value="${sysFormTemplateHistoryForm}" scope="request"/>
<c:set var="authUrl" value="${actionUrl}?method=edit" />
<kmss:windowTitle moduleKey="sys-xform:xform.title"
	subjectKey="sys-xform:tree.xform.def"
	subject="历史模板" />
<input type="hidden" name="fdId" value="${xFormTemplateForm.fdId}" />
<script>
    Com_AddEventListener(window, "load", function() {
        if("${xFormTemplateForm.fdMode}" == "<%=XFormConstant.TEMPLATE_SUBFORM%>"){
            var mobile = ${mobile};
            var currentFormId = Com_GetUrlParameter(location.href, "fdId");
            var defaultFormId = $("[name='fdId']").val();
            var customIframe = window.frames['IFrame_FormTemplate_${xFormTemplateForm.fdKey}'].contentWindow;
            if(!customIframe){
                customIframe = window.frames['IFrame_FormTemplate_${xFormTemplateForm.fdKey}'];
            }
            if (mobile) {
                var mobileFormId = currentFormId;
                var pcFormId = $("input[name='subformfdId'][value='" + mobileFormId + "']").closest("tr").find("input[name='subformPcId']").val();
                var checkedTr = $("input[name='subformfdId'][value='" + pcFormId + "']").closest("tr");
                checkedTr.find("a").click();
                var checkedTrId = setInterval(function() {
                    if (customIframe.XForm_Design_Has_Init) {
                        checkedTr.find("a").click();
                        clearInterval(checkedTrId);
                    }
                }, 100);
                $("#TABLE_SUBFORM").find("tr").each(function(index, obj){
                    var objId = $(obj).find("input[name='subformfdId']").val();
                    if (objId != mobileFormId && objId != pcFormId) {
                        $(obj).hide();
                    }
                });
            } else {
                var pcFormId = currentFormId;
                var checkedTr = $("input[name='subformfdId'][value='" + pcFormId + "']").closest("tr");
                var mobileTr = $("input[name='subformPcId'][value='" + pcFormId + "']").closest("tr");
                var checkedTrId = setInterval(function() {
                    if (customIframe.XForm_Design_Has_Init) {
                        checkedTr.find("a").click();
                        clearInterval(checkedTrId);
                    }
                }, 100);
                var mobilePcFormId = mobileTr.find("input[name='subformfdId']").val();
                $("#TABLE_SUBFORM").find("tr").each(function(index, obj){
                    var objId = $(obj).find("input[name='subformfdId']").val();
                    if (objId != pcFormId && objId != mobilePcFormId) {
                        $(obj).hide();
                    }
                });
            }
        }
    });
</script>
<div id="optBarDiv">
	<c:if test="${versionType eq null || versionType ne 'new'}">
		<kmss:auth
			requestURL="${actionUrl}?method=edit&fdId=${param.fdModelTemplateId}"
			requestMethod="GET">
			<input type="button" value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/xform/base/sys_form_template_history/sysFormTemplateHistory.do?method=edit&fdId=${param.fdId }&fdMainModelName=${fdMainModelName}&authUrl=${authUrl}&fdModelTemplateId=${param.fdModelTemplateId}','_self');" />
		</kmss:auth>
	</c:if>
	
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();" />
</div>
<p class="txttitle"><bean:message bundle="sys-xform" key="sysFormTemplate.history.formHistoryTemplate"/>_V${xFormTemplateForm.fdTemplateEdition }</p>
<center>
<table id="Label_Tabel" width=95%>
	<tr LKS_LabelName="<bean:message bundle="sys-xform" key="sysFormTemplate.history.templateInformation"/>">
		<td>
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
                    <c:import url="/sys/xform/base/sysSubFormTemplate_view.jsp"	charEncoding="UTF-8">
                        <c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
                        <c:param name="formName" value="sysFormTemplateHistoryForm" />
                        <c:param name="fdKey" value="${xFormTemplateForm.fdKey}" />
                    </c:import>
                </div>
            </div>
			<table class="tb_normal" width="100%" id="TB_FormTemplate_View">
                <c:import url="/sys/xform/base/sysFormTemplateDisplay_view.jsp"	charEncoding="UTF-8">
                    <c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }"></c:param>
                    <c:param name="formName" value="sysFormTemplateHistoryForm"></c:param>
                    <c:param name="fdKey" value="${xFormTemplateForm.fdKey}" />
                </c:import>
			</table>
		</td>
	</tr>
	<%--多语言 --%>
	<%  if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()) {%>
	<c:import url="/sys/xform/lang/include/sysFormHistoryMultiLang_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="sysFormTemplateHistoryForm" />
		<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
	</c:import>
	<% } %>	
	<%--被引用的主文档 --%>
	<c:import url="/sys/xform/base/sys_form_template_history/sysFormTemplateHistoryRefMain_view.jsp"	charEncoding="UTF-8">
			<c:param name="formName" value="sysFormTemplateHistoryForm" />
			<c:param name="fdMainModelName" value="${fdMainModelName}" />
	</c:import>
</table>
	
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>