<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseItemConfig" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdCompany" title="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCompany') }">
            ${fsscExpenseItemConfig.fdCompany.fdName}
        </list:data-column>
        <list:data-column col="fdCategory" title="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCategory') }">
            ${fsscExpenseItemConfig.fdCategory.fdName}
        </list:data-column>
        <list:data-column col="fdItemList" title="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdItemList') }">
            <c:forEach var="item" items="${fsscExpenseItemConfig.fdItemList }" varStatus="vstatus1">
            	${item.fdName};
            </c:forEach>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
