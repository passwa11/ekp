<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="knowledgeForm" value="${requestScope[param.formName]}"/>
<c:set var="smartTagForm" value="${knowledgeForm.smartTagForm}"/>
<td class="td_normal_title" width=15% nowrap>
    ${lfn:message('kms-common:table.SmartTag')}
</td>
<td colspan="3" width="90%">
    <html:hidden property="smartTagForm.fdId"/>
    <html:hidden property="smartTagForm.fdModelId"/>
    <html:hidden property="smartTagForm.fdModelName"/>
    <html:hidden property="smartTagForm.fdTagIds"/>

    <div class="inputselectsgl" style="width:98%">
      <%--  <div class="input">
                <html:text property="smartTagForm.fdTagNames" styleClass=""/>
        </div>--%>
          <xform:text property="smartTagForm.fdTagNames" className="aweferga" showStatus="readOnly"/>

    </div>
</td>