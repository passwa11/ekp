<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCommonTransferField" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdSourceTableName" title="${lfn:message('fssc-common:fsscCommonTransferField.fdSourceTableName')}" />
        <list:data-column property="fdSourceModelName" title="${lfn:message('fssc-common:fsscCommonTransferField.fdSourceModelName')}" />
        <list:data-column property="fdSourceModelSubject" title="${lfn:message('fssc-common:fsscCommonTransferField.fdSourceModelSubject')}" />
        <list:data-column property="fdTargetModelSubject" title="${lfn:message('fssc-common:fsscCommonTransferField.fdTargetModelSubject')}" />
        <list:data-column property="fdTargetTableName" title="${lfn:message('fssc-common:fsscCommonTransferField.fdTargetTableName')}" />
        <list:data-column property="fdTargetModelName" title="${lfn:message('fssc-common:fsscCommonTransferField.fdTargetModelName')}" />
        <list:data-column col="fdIsProcessed.name" title="${lfn:message('fssc-common:fsscCommonTransferField.fdIsProcessed')}">
            <sunbor:enumsShow value="${fsscCommonTransferField.fdIsProcessed}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdIsProcessed">
            <c:out value="${fsscCommonTransferField.fdIsProcessed}" />
        </list:data-column>
        <list:data-column col="fdFinishedTime" title="${lfn:message('fssc-common:fsscCommonTransferField.fdFinishedTime')}">
            <kmss:showDate value="${fsscCommonTransferField.fdFinishedTime}" type="date"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
