<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/tag/mobile/import/style/select.css">
<c:set var="tag_MainForm" value="${requestScope[param.formName]}"/>
<c:set var="sysTagMainForm" value="${tag_MainForm.sysTagMainForm}"/>
<c:set var="tag_modelName" value="${tag_MainForm.modelClass.name}"/>
<%-- 是否只显示内容，即是否包含tr td元素，默认 false --%>
<c:set var="showOnlyContent" value="${empty JsParam.showOnlyContent? false : JsParam.showOnlyContent}"/>
<%-- 标签内容左右浮动 仅left、right 默认 right --%>
<c:set var="contentAlign" value="${empty JsParam.contentAlign? 'right' : JsParam.contentAlign}"/>
<%-- 是否隐藏输入框，默认 false --%>
<c:set var="inputHidden" value="${empty JsParam.inputHidden? true : JsParam.inputHidden}"/>
<%-- 是否多选，默认 true --%>
<c:set var="isMul" value="${empty JsParam.isMul? true : JsParam.isMul}"/>
<%-- 是否显示标签信息：分类、引用次数 默认 false --%>
<c:set var="showTagInfo" value="${empty JsParam.showTagInfo? false : JsParam.showTagInfo}"/>
<%-- 是否为必填 默认 false --%>
<c:set var="required" value="${empty JsParam.required? false : JsParam.required}"/>
<c:set var="fdQueryCondition" value="${empty JsParam.fdQueryCondition? '' : JsParam.fdQueryCondition}"/>
<c:if test="${showOnlyContent == false}">
    <tr style="height: 3rem">
    <td class="muiTitle">${lfn:message('sys-tag:table.sysTagTags') }</td>
    <td>
</c:if>
<div class="muiFormTagContent">
    <html:hidden property="sysTagMainForm.fdId"/>
    <html:hidden property="sysTagMainForm.fdKey" value="${HtmlParam.fdKey}"/>
    <html:hidden property="sysTagMainForm.fdModelName"/>
    <html:hidden property="sysTagMainForm.fdModelId"/>
    <input type="hidden" name="sysTagMainForm.fdTagIds"/>
    <input type="hidden" name="sysTagMainForm.fdTagNames"/>
    <input type="hidden"
           name="sysTagMainForm.fdQueryCondition"
           value="${sysTagMainForm.fdQueryCondition}"/>
    <div id="muiTagBox"
         data-dojo-type="sys/tag/mobile/import/js/SysTagPda"
         data-dojo-props="
            modelName:'${tag_modelName}',
            modelId:'${sysTagMainForm.fdModelId}',
            inputHidden:'${inputHidden}',
            isMul:'${isMul}',
            contentAlign:'${contentAlign}',
            queryCondition:'${fdQueryCondition}',
            curIds: '${sysTagMainForm.fdTagIds}',
            curNames: '${sysTagMainForm.fdTagNames}',
            required: '${required}',
            showTagInfo: '${showTagInfo}',
            fdKey:'${JsParam.fdKey}'">
    </div>
</div>
<c:if test="${showOnlyContent == false}">
    </td>
    </tr>
</c:if>

