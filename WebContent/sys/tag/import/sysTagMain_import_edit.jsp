<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>

<!-- 重写标签选择点击绑定事件-->
<c:choose>
	<c:when test="${'new' eq HtmlParam.tagJs}">
		<script type="text/javascript">
			Com_IncludeFile("data.js|dialog.js|jquery.js");
			Com_IncludeFile("tag_select.js","${KMSS_Parameter_ContextPath}sys/tag/resource/js/","js",true);
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			Com_IncludeFile("data.js|dialog.js|jquery.js");
			Com_IncludeFile("tag.js","${KMSS_Parameter_ContextPath}sys/tag/resource/js/","js",true);
		</script>
	</c:otherwise>
</c:choose>

<c:set var="tag_MainForm" value="${requestScope[param.formName]}"/>
<c:set var="sysTagMainForm" value="${tag_MainForm.sysTagMainForm}"/>
<c:set var="tag_modelName" value="${tag_MainForm.modelClass.name}"/>
<Script type="text/javascript">
	var tag_params = {
			"model":"edit",
			"fdQueryCondition":"${HtmlParam.fdQueryCondition}",
			"tag_msg1":"<bean:message bundle='sys-tag' key='sysTagMain.message.1'/>",
			"tag_msg2":"<bean:message bundle='sys-tag' key='sysTagMain.message.2'/>",
			"tree_title":"<bean:message key='sysTagTag.tree' bundle='sys-tag'/>"
			};
	if("${HtmlParam.fdQueryCondition==null||HtmlParam.fdQueryCondition==''}"=="true"){
		delete tag_params['fdQueryCondition'];
	}
	if(window.tag_opt==null){
		window.tag_opt = new TagOpt('${tag_modelName}','${sysTagMainForm.fdModelId}','${JsParam.fdKey}',tag_params);
	}
	Com_AddEventListener(window,'load',function(){
			window.tag_opt.onload(); 
		});
	var __top = Com_Parameter.top || window.top;
	__top.addTagSign = 1;
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
		<html:hidden property="sysTagMainForm.fdId"/> 
		<html:hidden property="sysTagMainForm.fdKey" value="${HtmlParam.fdKey}"/>
		<html:hidden property="sysTagMainForm.fdModelName"/>
		<html:hidden property="sysTagMainForm.fdModelId"/> 
		<html:hidden property="sysTagMainForm.fdQueryCondition"/> 
		<input type="hidden" name="sysTagMainForm.fdTagIds" />
	<c:if test="${HtmlParam.fdInputWidth != null}">
		<div class="inputselectsgl"  style="width:${HtmlParam.fdInputWidth }">
	</c:if>
	<c:if test="${HtmlParam.fdInputWidth == null}">
		<div class="inputselectsgl"  style="width:98%">
	</c:if>
			<div class="input">
				<html:text property="sysTagMainForm.fdTagNames"  styleClass=""/> 
			</div>
			<div class="selectitem sys_tag_select_click" id="tag_selectItem">
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
