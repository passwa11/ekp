<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSupplier" list="${queryPage.list}" varIndex="status">
        <list:data-column col="fdId">
        	${eopBasedataSupplier['FD_ID']}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdName" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdName')}">
        	${eopBasedataSupplier['FD_NAME']}
        </list:data-column>
        <list:data-column col="fdCode" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdCode')}">
        	${eopBasedataSupplier['FD_CODE']}
        </list:data-column>
        <list:data-column col="fdIsAvailable" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdIsAvailable')}">
        	<sunbor:enumsShow value="${eopBasedataSupplier['FD_IS_AVAILABLE']}" enumsType="common_yesno" />
        </list:data-column>
        <list:data-column col="fdTaxNo" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdTaxNo')}" >
        	${eopBasedataSupplier['FD_TAX_NO']}
        </list:data-column>
        <list:data-column col="type" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdType')}" >
        	<sunbor:enumsShow value="${eopBasedataSupplier['TYPE']}" enumsType="eop_basedata_supplier_customer" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
