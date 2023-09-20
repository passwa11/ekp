<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscAlitripOrder" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="fdName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.fdName')}" escape="false">
            <a class="com_subject textEllipsis" title="${fsscAlitripOrder.fdName}" href="${LUI_ContextPath}/fssc/alitrip/fssc_alitrip_order/fsscAlitripOrder.do?method=view&fdId=${fsscAlitripOrder.fdId}" target="_blank">
                <c:out value="${fsscAlitripOrder.fdName}" />
            </a>
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdType.name" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.fdType')}">
            <sunbor:enumsShow value="${fsscAlitripOrder.fdType}" enumsType="fssc_alitrip_train_cate" />
        </list:data-column>
        <list:data-column col="fdType">
            <c:out value="${fsscAlitripOrder.fdType}" />
        </list:data-column>
        <list:data-column property="corpName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.corpName')}" />
        <list:data-column property="userName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.userName')}" />
        <list:data-column property="costCenterName" title="${lfn:message('fssc-alitrip:fsscAlitripOrder.costCenterName')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
