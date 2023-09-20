<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscExpenseDidiDetail" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="fdPassenger" title="${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdPassenger')}" >
        	${fsscExpenseDidiDetail.fdPassengerName}
        </list:data-column>
        <list:data-column col="fdCarLevelShow" title="${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdCarLevel')}" >
        	<sunbor:enumsShow value="${fsscExpenseDidiDetail.fdCarLevel}" enumsType="fssc_expense_didi_level"/>
        </list:data-column>
        <list:data-column col="fdStartPlace" title="${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdStartPlace')}" >
        	${fsscExpenseDidiDetail.fdActualStartName}
        </list:data-column>
        <list:data-column col="fdEndPlace" title="${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdEndPlace')}" >
        	${fsscExpenseDidiDetail.fdActualEndName}
        </list:data-column>
        <list:data-column col="fdStartTime" title="${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdStartTime')}" >
        	${fsscExpenseDidiDetail.fdDepartureTime}
        </list:data-column>
        <list:data-column col="fdEndTime" title="${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdEndTime')}" >
        	${fsscExpenseDidiDetail.fdFinishTime}
        </list:data-column>
        <list:data-column col="fdMoney" title="${lfn:message('fssc-expense:fsscExpenseDidiDetail.fdMoney')}" >
        	${fsscExpenseDidiDetail.fdActualPrice}
        </list:data-column>
        <list:data-column col="fdCarLevel">
        	${fsscExpenseDidiDetail.fdCarLevel}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
