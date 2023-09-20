<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscFeeExpenseItem" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="fdCompany" title="${lfn:message('fssc-fee:fsscFeeExpenseItem.fdCompany') }">
            ${fsscFeeExpenseItem.fdCompany.fdName}
        </list:data-column>
        <list:data-column col="fdTemplate" title="${lfn:message('fssc-fee:fsscFeeExpenseItem.fdTemplate') }">
            ${fsscFeeExpenseItem.fdTemplate.fdName}
        </list:data-column>
        <kmss:ifModuleExist path="/fssc/budget">
        <list:data-column col="fdIsNeedBudget" title="${lfn:message('fssc-fee:fsscFeeExpenseItem.fdIsNeedBudget') }">
            <sunbor:enumsShow enumsType="common_yesno" value="${fsscFeeExpenseItem.fdIsNeedBudget}"/>
        </list:data-column>
        </kmss:ifModuleExist>
        <list:data-column col="fdItemList" title="${lfn:message('fssc-fee:fsscFeeExpenseItem.fdItemList') }">
            <c:forEach var="item" items="${fsscFeeExpenseItem.fdItemList }" varStatus="vstatus1">
            	${item.fdName};
            </c:forEach>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
