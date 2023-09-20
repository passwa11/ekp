<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataPayWay" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataPayWay.fdName')}"  escape="false"/>
        <list:data-column col="fdIsTransfer">
            ${eopBasedataPayWay.fdIsTransfer}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column col="fdDefaultPayBank.fdId">
			${eopBasedataPayWay.fdDefaultPayBank.fdId}
		</list:data-column>
        <list:data-column property="fdDefaultPayBank.fdBankName" title="${lfn:message('eop-basedata:eopBasedataPayWay.fdDefaultPayBank')}"  escape="false"/>
        <list:data-column property="fdDefaultPayBank.fdBankAccount" title="${lfn:message('eop-basedata:eopBasedataPayBank.fdBankAccount')}" />
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
