<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.third.intell.model.IntellConfig"%>
<%
	IntellConfig intellConfigEdit = new IntellConfig();
	if("true".equalsIgnoreCase(intellConfigEdit.getItEnabled()) 
			&& "true".equalsIgnoreCase(intellConfigEdit.getSmartTag()) ){
		String aipUrl = intellConfigEdit.getItDomain();
		if (aipUrl!=null && aipUrl.endsWith("/")){
			aipUrl = aipUrl.substring(0, aipUrl.length()-1);
		}
		String systemId = intellConfigEdit.getSystemName();
%>
<script type="text/javascript">
	Com_IncludeFile("data.js|dialog.js|jquery.js");
	Com_IncludeFile("tag.js","${KMSS_Parameter_ContextPath}third/intell/resource/js/","js",true);
</script>

<c:set var="kmsWikiMainForm" value="${requestScope[param.formName]}"/>
<Script type="text/javascript">
	var tag_params = {
			"model":"edit",
			"modelName":"${HtmlParam.modelName}",
			"fdQueryCondition":"${HtmlParam.fdQueryCondition}",
			"tag_msg1":"<bean:message bundle='sys-tag' key='sysTagMain.message.1'/>",
			"tag_msg2":"<bean:message bundle='sys-tag' key='sysTagMain.message.2'/>",
			"tree_title":"<bean:message key='sysTagTag.tree' bundle='sys-tag'/>"
			};
	if("${HtmlParam.fdQueryCondition==null||HtmlParam.fdQueryCondition==''}"=="true"){
		delete tag_params['fdQueryCondition'];
	}
	if(window.intell_opt==null){
		window.intell_opt = new IntellOpt('${tag_modelName}','${sysTagMainForm.fdModelId}','<%=aipUrl%>','<%=systemId%>',tag_params);
	}
	Com_AddEventListener(window,'load',function(){
			window.intell_opt.onload(); 
		});
	top.addTagSign = 1;
</script>
<c:set var="useTab" value="true"></c:set>
<c:if test="${param.useTab!=null && param.useTab==false}">
	<c:set var="useTab" value="false"/>
</c:if>

<c:if test="${ useTab=='true'}">
<tr class="lui_tag_tr_content">
	<td class="td_normal_title" width=15% nowrap>
		<bean:message bundle="sys-tag" key="table.sysTagTags"/>
	</td>
	<td colspan="3">
</c:if>
	<c:if test="${HtmlParam.fdInputWidth != null}">
		<div class="intell_inputselectsgl"  style="width:${HtmlParam.fdInputWidth }">
	</c:if>
	<c:if test="${HtmlParam.fdInputWidth == null}">
		<div class="intell_inputselectsgl"  style="width:98%">
	</c:if>
			<div class="intell_selectItem" id="intell_selectItem">
				<ui:button text="${lfn:message('third-intell:edit.smartTag') } "  />
				<input type="hidden" value = "${HtmlParam.modelName}" name="intellTagModelName">
				<input type="hidden" value = "${HtmlParam.modelId}" name="intellTagModelId">
			</div>
			
		</div>
		<div class="tag_prompt_area" id="id_application_div">
            <p><bean:message bundle="sys-tag" key="sysTagMain.message.0"/></p>
            <p id="hot_id"><bean:message bundle="sys-tag" key="sysTagMain.message.1"/></p>
            <p id="used_id"><bean:message bundle="sys-tag" key="sysTagMain.message.2"/></p>
        </div>
<c:if test="${ useTab=='true'}">
	</td>	
</tr>
</c:if>
<% 
	}
%>