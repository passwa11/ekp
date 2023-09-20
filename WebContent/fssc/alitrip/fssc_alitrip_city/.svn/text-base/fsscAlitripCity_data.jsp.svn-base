<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscAlitripCity" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('fssc-alitrip:fsscAlitripCity.fdCode')}" />
        <list:data-column property="fdName" title="${lfn:message('fssc-alitrip:fsscAlitripCity.fdName')}" />
        <list:data-column col="fdOwer.name" title="${lfn:message('fssc-alitrip:fsscAlitripCity.fdOwer')}">
            <sunbor:enumsShow value="${fsscAlitripCity.fdOwer}" enumsType="fssc_alitrip_train_cate" />
        </list:data-column>
        <list:data-column col="fdOwer">
            <c:out value="${fsscAlitripCity.fdOwer}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
