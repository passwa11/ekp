<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="versionMap" list="${versionList}" varIndex="status" custom="false">
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
 		<list:data-column col="fdId">
            ${versionMap.fdId}
        </list:data-column>
        <list:data-column col="fdVersion">
            ${versionMap.fdVersion}
        </list:data-column>
 		<list:data-column col="attList" escape="false">
            ${versionMap.attList}
        </list:data-column>
    </list:data-columns>
</list:data>
