<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscFeeMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

		<list:data-column col="fdIsClosed" title="${lfn:message('fssc-fee:fsscFeeMain.fdIsClosed')}" escape="false">
            <sunbor:enumsShow enumsType="common_yesno" value="${fsscFeeMain.fdIsClosed }"></sunbor:enumsShow>
        </list:data-column>
        <list:data-column property="docSubject" title="${lfn:message('fssc-fee:fsscFeeMain.docSubject')}" />
        <list:data-column property="docNumber" title="${lfn:message('fssc-fee:fsscFeeMain.docNumber')}" />
        <list:data-column col="docStatus.fdName" title="${lfn:message('fssc-fee:fsscFeeMain.docStatus')}">
            <sunbor:enumsShow value="${fsscFeeMain.docStatus}" enumsType="fssc_fee_doc_status" />
        </list:data-column>
        <list:data-column col="docStatus.id">
            <c:out value="${fsscFeeMain.docStatus}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('fssc-fee:fsscFeeMain.docCreator')}" escape="false">
            <c:out value="${fsscFeeMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${fsscFeeMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('fssc-fee:fsscFeeMain.docCreateTime')}">
            <kmss:showDate value="${fsscFeeMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docTemplate.name" title="${lfn:message('fssc-fee:fsscFeeMain.docTemplate')}" escape="false">
            <c:out value="${fsscFeeMain.docTemplate.fdName}" />
        </list:data-column>
        <list:data-column col="docTemplate.id" escape="false">
            <c:out value="${fsscFeeMain.docTemplate.fdId}" />
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_handler" title="${lfn:message('fssc-fee:lbpm.currentHandler') }" escape="false">
            <kmss:showWfPropertyValues var="handlerValue" idValue="${fsscFeeMain.fdId}" propertyName="handlerName" />
            <div style="font-weight:bold;" title="${handlerValue}">
                <c:out value="${handlerValue}"></c:out>
            </div>
        </list:data-column>
        <!-- lbpm_main -->
        <list:data-column col="lbpm_main_listcolumn_summary" title="${lfn:message('fssc-fee:lbpm.currentSummary') }" escape="false">
            <kmss:showWfPropertyValues var="nodevalue" idValue="${fsscFeeMain.fdId}" propertyName="nodeName" />
            <div title="${nodevalue}">
                <c:out value="${nodevalue}"></c:out>
            </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
