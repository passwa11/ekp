<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCommonTransferField" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column col="fdId" >
        	${fsscCommonTransferField['fdModelName']}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdModelName">
        	${fsscCommonTransferField['fdModelName']}
        </list:data-column>
        <list:data-column col="fdModelSubject" title="${lfn:message('fssc-common:py.BiaoMing')}" >
        	${fsscCommonTransferField['fdModelSubject']}
        </list:data-column>
        <list:data-column col="fdModelTable" >
       		${fsscCommonTransferField['fdModelTable']}
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
