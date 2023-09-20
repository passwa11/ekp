<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="elecCaInfo" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column property="fdCertType" title="${lfn:message('elec-core-certification:elecCaInfo.fdCertType')}" />
        <list:data-column col="fdCertType.name" title="${lfn:message('elec-core-certification:elecCaInfo.fdCertType')}">
            <sunbor:enumsShow value="${elecCaInfo.fdCertType}" enumsType="elec_ca_authen_type" />
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('elec-core-certification:elecCaInfo.fdName')}" />
        <list:data-column property="fdCertDn" title="${lfn:message('elec-core-certification:elecCaInfo.fdCertDn')}" />
        <list:data-column col="fdStartTime" title="${lfn:message('elec-core-certification:elecCaInfo.fdStartTime')}">
            <kmss:showDate value="${elecCaInfo.fdStartTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdEndTime" title="${lfn:message('elec-core-certification:elecCaInfo.fdEndTime')}">
            <kmss:showDate value="${elecCaInfo.fdEndTime}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdCertStatus.name" title="${lfn:message('elec-core-certification:elecCaInfo.fdCertStatus')}">
            <sunbor:enumsShow value="${elecCaInfo.fdCertStatus}" enumsType="elec_core_cert_cert_status" />
        </list:data-column>
        <list:data-column col="fdCertStatus">
            <c:out value="${elecCaInfo.fdCertStatus}" />
        </list:data-column>
        <list:data-column col="fdCarrier">
            <c:out value="${elecCaInfo.fdCarrier}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
