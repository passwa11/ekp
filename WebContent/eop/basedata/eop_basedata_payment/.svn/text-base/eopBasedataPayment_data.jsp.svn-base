<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fssc" uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataPayment" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdModelId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdModelName" title="${lfn:message('eop-basedata:eopBasedataPayment.fdModelName')}">
        	<fssc:showModelName modelName="${eopBasedataPayment.fdModelName }"/>
        </list:data-column>
        <list:data-column property="fdSubject" title="${lfn:message('eop-basedata:eopBasedataPayment.fdSubject')}" />
        <list:data-column property="fdModelNumber" title="${lfn:message('eop-basedata:eopBasedataPayment.fdModelNumber')}" />
        <list:data-column property="fdPaymentMoney" title="${lfn:message('eop-basedata:eopBasedataPayment.fdPaymentMoney')}" />
        <list:data-column col="fdPaymentTime" title="${lfn:message('eop-basedata:eopBasedataPayment.fdPaymentTime')}">
            <kmss:showDate value="${eopBasedataPayment.fdPaymentTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataPayment.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataPayment.fdStatus}" enumsType="eop_basedata_payment_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataPayment.fdStatus}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
