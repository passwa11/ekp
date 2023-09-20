<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataSupplier" list="${queryPage.list}" varIndex="status">
        <list:data-column col="fdId">
        	${eopBasedataSupplier['fd_id']}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdAccountName" title="${lfn:message('eop-basedata:eopBasedataSupplierAccount.fdAccountName')}" escape="false">
        	${eopBasedataSupplier['fd_account_name']}
        </list:data-column>
        <list:data-column col="fdBankName" title="${lfn:message('eop-basedata:eopBasedataSupplierAccount.fdBankName')}" escape="false">
        	${eopBasedataSupplier['fd_bank_name']}
        </list:data-column>
        <list:data-column col="fdBankAccount" title="${lfn:message('eop-basedata:eopBasedataSupplierAccount.fdBankAccount')}" >
        	${eopBasedataSupplier['fd_bank_account']}
        </list:data-column>
        <list:data-column col="type" title="${lfn:message('eop-basedata:eopBasedataSupplier.fdType')}" >
        	<sunbor:enumsShow value="${eopBasedataSupplier['type']}" enumsType="eop_basedata_supplier_customer" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
