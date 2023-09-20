<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCommonTransferField" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column col="fdId" >
        	${fsscCommonTransferField['name']}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="column">
        	${fsscCommonTransferField['column']}
        </list:data-column>
        <list:data-column col="subject" title="${lfn:message('fssc-common:py.ZiDuanMing')}" >
        	${fsscCommonTransferField['subject']}
        </list:data-column>
        <list:data-column col="type" >
       		${fsscCommonTransferField['type']}
        </list:data-column>
        <list:data-column col="hbmType" >
       		${fsscCommonTransferField['hbmType']}
        </list:data-column>
        <list:data-column col="fetch-table" >
       		${fsscCommonTransferField['fetch-type']}
        </list:data-column>
        <list:data-column col="fetch-source" >
       		${fsscCommonTransferField['fetch-source']}
        </list:data-column>
        <list:data-column col="fetch-target" >
       		${fsscCommonTransferField['fetch-target']}
        </list:data-column>
        <list:data-column col="name" >
       		${fsscCommonTransferField['name']}
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
